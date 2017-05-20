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
//  �����O���\��: ��superclass
//
//  �o�i�v: ��superclass
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
   private boolean watchMouse=false;          //: �ƹ��i�X���e�ؾ���
   public void setWatching(boolean watch) { //: �o�u�O�n�����ĪG
      watchMouse=watch;  //: �O�_�n�ʵ��ƹ��i�X
   }
   //]

   protected GrObjManager GrObjM=null;        //: ø�Ϫ���޲z

   //------------------------------------------------
   public GraphicPanel() { //: JApplet ���~��Window, �ҥH��������o��ctor
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
      ob.setPanel(this);    //: �l��ø�Ϫ����ob�ۦ�B�z
   }
   public void removeObj(GraphicObj ob) {
      if(GrObjM != null){
         GrObjM.remove(ob);
         ob.setPanel(null);   //: �l��ø�Ϫ����ob�ۦ�B�z
      }
   }
   public GraphicObj getObj(int idx) {
      if(GrObjM != null){
         return (GraphicObj)GrObjM.get(idx);
      }
      else
         return null;
   }
   public final int indexOf(GraphicObj ob) {  //: ���ݩ�h-1
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
   //: ��protected, ���o�i��n�t�X�Ѥ�t�Τ@�_��.
      super.paintComponent(g);  //: �w�e�I��, �Ʈ�, �ӽ�focus
      if(GrObjM !=null){//980819
         if(watchMouse && GrObjM.mouseInside()) { //[ �e�g��
            final Color oldColor=g.getColor(); 
            g.setColor(Color.black);
            g.drawRect(0,0,getWidth()-1, getHeight()-1);
            g.setColor(oldColor);
         }
      }
      //[ �H�U�� subclass �Ѧ�
      // this.paintAllObj(g)  //! subclass�ۤv�eGraphicObj
      //! subclass�ۤv�e�䥦�����e.
   }

   //[ ---------  implements  MouseInputListener  -------------
   //[  should be overrided to proper actions
   //[  MouseEvent�����W�O���gsubclass�ɫK��ۻs
   public void mouseEntered(java.awt.event.MouseEvent e) {
      this.requestFocusInWindow();  //: in JComponent
        //: jdkdoc�����@�w�|���\.
        //: �n������ FocusEvent.FOCUS_GAINED �~�T�w���\.
      if(GrObjM!=null) GrObjM.mouseEntered(e);
      if(watchMouse) repaint();   //: �Ϧ����|�����e�L�e���
   }
   public void mouseExited(java.awt.event.MouseEvent e) {
      if(GrObjM!=null) GrObjM.mouseExited(e);
      if(watchMouse) repaint();   //: �Ϧ����|�����e�L�����
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
