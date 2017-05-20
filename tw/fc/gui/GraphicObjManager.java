package tw.fc.gui;
   import java.awt.Graphics;

//[  ��Container���W��, 0 �b���h, �Hindex���W�Ӻ��`. ��i���b���h.
public class GraphicObjManager
       extends tw.fc.gui.GrObjManager
{

   private static final long serialVersionUID= 2005111614L;

   //--------------------------------------------
   public   GraphicObjManager(GrPanel p, int capacity) {
      super(p,capacity);
   }
   public   GraphicObjManager(GrPanel p) {
      super(p);
   }

   public   GraphicObjManager(GraphicObj p, int capacity) {//�s�W
      super(p,capacity);
   }
   public   GraphicObjManager(GraphicObj p) {//�s�W
      super(p);
   }
   //---------------------

   public GraphicObj getObj(int i) {
      return (GraphicObj)super.get(i);
   }

   public final void paintAllObj(Graphics g) {
      for(int i=size()-1; i>=0; i--) { //: ���h��paint
         final GraphicObj ob=this.getObj(i);
         if(ob.visible) {  ob.paintTo(g);  }
      }
   }

}