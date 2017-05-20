package tw.fc.gui;
   import java.awt.Graphics;

//[  依Container的規律, 0 在頂層, 隨index漸增而漸深. 後進的在底層.
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

   public   GraphicObjManager(GraphicObj p, int capacity) {//新增
      super(p,capacity);
   }
   public   GraphicObjManager(GraphicObj p) {//新增
      super(p);
   }
   //---------------------

   public GraphicObj getObj(int i) {
      return (GraphicObj)super.get(i);
   }

   public final void paintAllObj(Graphics g) {
      for(int i=size()-1; i>=0; i--) { //: 底層先paint
         final GraphicObj ob=this.getObj(i);
         if(ob.visible) {  ob.paintTo(g);  }
      }
   }

}