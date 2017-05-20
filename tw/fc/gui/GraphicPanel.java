package tw.fc.gui;
import java.awt.Frame;
//import java.awt.Window;
import java.awt.Color;
//import java.awt.Cursor;
import java.awt.Graphics;
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//import javax.swing.JFrame;
//import java.awt.event.MouseEvent;
//import java.awt.event.InputEvent;
//import javax.swing.event.MouseInputListener;
//import java.awt.event.KeyListener;
//import java.awt.event.ComponentListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.ComponentEvent;
//import java.awt.event.WindowListener;
//import java.awt.event.WindowEvent;

//**************   GraphicPanel.java    ********************//
//
//  本類別的功能: 見superclass
//
//  發展史: 見superclass
//
public class GraphicPanel 
   extends GrPanel 
      //     + GrComponent
      //         + javax.swing.JComponent
      //            + java.awt.Container
      //                 + java.awt.Component
{
   private static final long serialVersionUID= 2005111613L;

   //[ only a funny mechenism for a beginner
   private boolean watchMouse=false;          //: 滑鼠進出的畫框機制
   public void setWatching(boolean watch) { //: 這只是好玩的效果
      watchMouse=watch;  //: 是否要監視滑鼠進出
   }
   //]

   protected GrObjManager GrObjM=null;        //: 繪圖物件管理

   //------------------------------------------------
   public GraphicPanel() { //: JApplet 不繼承Window, 所以不能取消這個ctor
      super();
      //graphicObjM=new GraphicObjManager(this);
   }
   public GraphicPanel(Frame mf) {
      super(mf);
      //graphicObjM=new GraphicObjManager(this);
   }

   //-----------------------------------------------

//I  public int getObjCount() {  return GrObjM.size(); }
   public int getObjCount() {  return GrObjM.size(); }

   public void addObj(GraphicObj ob) {
      if(GrObjM == null) GrObjM = new GraphicObjManager(this);
      GrObjM.add(ob);  //: last-most
      ob.setPanel(this);    //: 子類繪圖物件由ob自行處理
   }
   public void removeObj(GraphicObj ob) {
      if(GrObjM != null){
         GrObjM.remove(ob);
         ob.setPanel(null);   //: 子類繪圖物件由ob自行處理
      }
   }
   public GraphicObj getObj(int idx) {
      if(GrObjM != null){
         return (GraphicObj)GrObjM.get(idx);
      }
      else
         return null;
   }
   public final int indexOf(GraphicObj ob) {  //: 不屬於則-1
      if(GrObjM != null){
         return GrObjM.indexOf(ob);
      }
      else
         return -1;
   }
   public GraphicObj findObjAt(ImuV2D at) {
      if(GrObjM != null){
         return (GraphicObj)GrObjM.findObjAt(at);
      }
      else
         return null;
   }
   public void toTop(GraphicObj ob) {
      if(GrObjM != null){
         GrObjM.toTop(ob);
      }
   }


   public void paintAllObj(Graphics g) {
      if(GrObjM != null){
         ((GraphicObjManager)GrObjM).paintAllObj(g);
      }
   }

   public void paintComponent(Graphics g) {
   //: 應protected, 但這可能要配合天文系統一起改.
      super.paintComponent(g);  //: 已畫背景, 滑框, 申請focus
      if(GrObjM !=null){//980819
         if(watchMouse && GrObjM.mouseInside()) { //[ 畫週框
            final Color oldColor=g.getColor(); 
            g.setColor(Color.black);
            g.drawRect(0,0,getWidth()-1, getHeight()-1);
            g.setColor(oldColor);
         }
      }
      //[ 以下供 subclass 參考
      // this.paintAllObj(g)  //! subclass自己畫GraphicObj
      //! subclass自己畫其它的內容.
   }

   //[ ---------  implements  MouseInputListener  -------------
   //[  should be overrided to proper actions
   //[  MouseEvent給全名是為寫subclass時便於抄製
   public void mouseEntered(java.awt.event.MouseEvent e) {
      this.requestFocusInWindow();  //: in JComponent
        //: jdkdoc說不一定會成功.
        //: 要等收到 FocusEvent.FOCUS_GAINED 才確定成功.
      if(GrObjM!=null) GrObjM.mouseEntered(e);
      if(watchMouse) repaint();   //: 使有機會為內容盤畫邊框
   }
   public void mouseExited(java.awt.event.MouseEvent e) {
      if(GrObjM!=null) GrObjM.mouseExited(e);
      if(watchMouse) repaint();   //: 使有機會為內容盤消邊框
   }
   public void mousePressed(java.awt.event.MouseEvent e) {
//D   System.out.print("GrPanel.mousePressed! ");
      if(GrObjM!=null) GrObjM.mousePressed(e);
   }
   public void mouseReleased(java.awt.event.MouseEvent e) {
//D   System.out.print("GrPanel.mouseReleased! ");
      if(GrObjM!=null) GrObjM.mouseReleased(e);
   }
   public void mouseClicked(java.awt.event.MouseEvent e) {
//D   System.out.print("GrPanel.mouseClicked! ");
      if(GrObjM!=null) GrObjM.mouseClicked(e);
   }
   public void mouseMoved(java.awt.event.MouseEvent e) {
//D   System.out.print("GrPanel.mouseMoved! ");
      if(GrObjM!=null) GrObjM.mouseMoved(e);
   }
   public void mouseDragged(java.awt.event.MouseEvent e) {
//D   System.out.print("GrPanel.mouseDragged! ");
      if(GrObjM!=null) GrObjM.mouseDragged(e);
   }
   //] ---------  implements  MouseInputListener  -------------

   //[ ---------  implements MouseWheelListener  -------------
   public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
      if(GrObjM!=null) GrObjM.mouseWheelMoved(e);
   }
   //] ---------  implements MouseWheelListener  -------------



} //]  class GraphicPanel   ===============================
