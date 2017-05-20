package tw.fc.gui;
//import java.awt.Graphics;
import java.awt.event.MouseEvent;
//import javax.swing.event.MouseInputListener;

//[  依Container的規律, 0 在頂層, 隨index漸增而漸深. 後進的在底層.
public class GrObjManager
       extends java.util.ArrayList<GrObj>
    // implements MouseInputListener //: 由GraphicPanel呼叫反應函數
{
   private static final long serialVersionUID = 2010062907L;

   protected final GrPanel thePanel;
   protected final GrObj theObj;//新增

   private boolean _mouseInside=false;
   //protected static boolean _mouseInside=false;
   private GrObj _passedObj=null;
   private GrObj _holdedObj=null;
   private GrObj _lastHoldedObj=null;
     //: _lastHoldedObj專供mouseClicked用, 由mouseReleased設定. 
   //private int _GraphicObjCount =0; //A31208X not use

   //--------------------------------------------
   public   GrObjManager(GrPanel p, int capacity) {
      super(capacity);
      thePanel=p;
      theObj = null;//新增
   }
   public   GrObjManager(GrPanel p) {  this(p,50); }

   public   GrObjManager(GrObj p, int capacity) {//新增
      super(capacity);
      theObj=p;
      thePanel=null;
   }
   public   GrObjManager(GrObj p) {  this(p,50);  }

   public   GrObjManager(int capacity) {
      super(capacity);
      thePanel=null;
      theObj = null;
   }

   public void toTop(GrObj ob) {
      final int idx=indexOf(ob);
      if(idx<0) {
         throw new IllegalArgumentException("object not in the panel");
      }
      if(idx==0) return;  // 本來就在first
      // remove(idx);   add(0,ob);
      for(int i=idx-1; i>=0; i--) {
         set(i+1,get(i));    //: replace
      }  set(0,ob);
   }

   public void toBottom(GrObj ob) {
      final int idx=indexOf(ob);
      if(idx<0) {
         throw new IllegalArgumentException("object not in the panel");
      }
      if(idx==size()-1) return;  // 本來就在last
      // remove(idx);   add(ob);
      for(int i=idx+1; i<size(); i++) {
         set(i-1,get(i));    //: replace
      }  set(size()-1,ob);
   }

   public GrObj findObjAt(ImuV2D at) { //: 頂層先找
      for(int i=0; i<size(); i++) {
        final GrObj ob=get(i);
        if(ob.visible && ob.contains(at)) return ob;
      }
      return null;
   }

   //[--------------------------------------------

   public final boolean mouseInside() {  return _mouseInside; }

   public void mouseEntered(MouseEvent e) {  
               //: 由GraphicPanel 呼叫
      GrObj.lastMousePos.setBy(e);  //>>>>>
      _mouseInside=true;
   }
   public void mouseExited(MouseEvent e) {
      if(_passedObj!=null) {
         _passedObj.forExited(e);
         _passedObj=null;
      }
      _mouseInside=false;
   }
   public void mousePressed(MouseEvent e) {
//D   System.out.print("GrObjManager.mousePressed! ");

      GrObj.lastMousePos.setBy(e);
                      //: 因未變位而不必, 但怕未manage move
      final GrObj ob=findObjAt(GrObj.lastMousePos);
      if(ob!=null) {
         _holdedObj=ob;
         _holdedObj.forPressed(e);  // obj可自己呼叫toTop
         GrObj.lastMousePos.setBy(e);
      }
   }
   public void mouseReleased(MouseEvent e) {
      if(_holdedObj!=null) {
         _holdedObj.forReleased(e);
         _lastHoldedObj=_holdedObj;  //: 給click用
         _holdedObj=null;
      }
      GrObj.lastMousePos.setBy(e);
                     //: 因未變位而不必, 但怕未manage drag
   }
   public void mouseClicked(MouseEvent e) {
      //: clicked只能緊接發生於pressed-released之後
      if(_lastHoldedObj!=null) {
         _lastHoldedObj.forClicked(e);
         _lastHoldedObj=null;
      }
      GrObj.lastMousePos.setBy(e);
                     //: 因未變位而不必, 但怕未manage 其它動作
   }
   public void mouseMoved(MouseEvent e) {
      GrObj atObj=findObjAt(new ImuV2D(e));
      if(atObj!=null) {
         if(_passedObj==atObj) {
            _passedObj.forMoved(e);
         }
         else {
            if(_passedObj!=null) {
               _passedObj.forExited(e);
            }
            (_passedObj=atObj).forEntered(e);
         }
      }
      else{
         if(_passedObj!=null) {
            _passedObj.forExited(e);
            _passedObj=null;
         }
      }
      GrObj.lastMousePos.setBy(e);
   }
   public void mouseDragged(MouseEvent e) {
      if(_holdedObj!=null) {
         _holdedObj.forDragged(e);
         GrObj.lastMousePos.setBy(e);
      }
      GrObj.lastMousePos.setBy(e);
   }
   public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
//    if(_holdedObj!=null) {
//       _holdedObj.forWheeled(e);  //: wheel可當動詞
//       GrObj.lastMousePos.setBy(e);
//    }
//    GrObj.lastMousePos.setBy(e);
      if(_passedObj!=null) {  //: 980811改
         _passedObj.forWheeled(e);  //: wheel可當動詞
         GrObj.lastMousePos.setBy(e);
      }
      GrObj.lastMousePos.setBy(e);
   }
   //]--------------------------------------------


}