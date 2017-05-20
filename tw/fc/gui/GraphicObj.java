package tw.fc.gui;

//import java.awt.Graphics;
//import java.awt.Color;
//import java.awt.event.*;

public abstract class GraphicObj extends GrObj {

   protected GraphicObjManager graphicObjM = null;//新增

   protected GraphicPanel thePanel=null;  
   protected GraphicObj parentObj=null;   //: child-level Object用

   public ImuV2D getPosition() { 
      //: 可能不用. 若使用就須override. 例如 Eye.paintTo 會用到
      throw new RuntimeException("\n\nnot overrided in subclass of GrObj\n");
   }

   public abstract void paintTo(java.awt.Graphics g);

   public void setPanel(GraphicPanel p) {
      thePanel=p;
      this.parentObj = null;
      setPanelForChildren();
   }
   public void setParent(GraphicObj p) {  //: child-level Object 用
      this.thePanel = p.thePanel;
      parentObj=p;
   }

   public void repaint() {  
      if(thePanel!=null) {  thePanel.repaint();  }
   }

   public void toTop() {  
      if(thePanel!=null) {  thePanel.GrObjM.toTop(this); }
      else {
         if (parentObj!=null) 
            throw new RuntimeException("not implement yet");
         else 
            throw new RuntimeException("illegal object");
      }
   }

   public void toBottom() {  
      if(thePanel!=null) {  thePanel.GrObjM.toBottom(this); }
      else {
         if (parentObj!=null) 
            throw new RuntimeException("not implement yet");
         else 
            throw new RuntimeException("illegal object");
      }
   }

   public void indexOf() {  
      if(thePanel!=null) {  thePanel.GrObjM.indexOf(this); }
      else if (parentObj!=null) {
         throw new RuntimeException("not implement yet");
      }
      else {
         throw new RuntimeException("unadded object");
      }
   }

   public void addObj(GraphicObj ob) {   //新增
      if(graphicObjM == null)
         graphicObjM = new GraphicObjManager(this);
      graphicObjM.add(ob); 
      ob.setParent(this);
   }

}

