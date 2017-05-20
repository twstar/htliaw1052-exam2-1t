package tw.fc.gui;

//import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
//import java.awt.*;
import static tw.fc.gui.GrPanel.*;
import javax.swing.event.MouseInputListener;

public class ListControl extends GrComponent implements MouseInputListener
{
   private static final long serialVersionUID = 2010062905L;

   // 1天文單位=149,600,000公里
   // 1光年=63240天文單位=9,460,704,000,000公里
   public static long AU2Km=149600000,
                      LY2AU=63240,
                      LY2Km=9460704000000L; // LY2AU*AU2Km
//                            10000 000000
   private MuXY list1 = new MuXY();
   private MuXY list2 = new MuXY();
   private MuXY list3 = new MuXY();
   private MuXY recWH = new MuXY();
   private long unitRatio=1;
   private GrPanel _owner;

   //-----------
   public ListControl(double x, double y, double w, double h, GrPanel own){
      list1.setBy(x,y);
      list2.setBy(x,y+h+3);
      list3.setBy(x,y+(h+3)*2);
      recWH.setBy(w,h);
      _owner = own;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }

   public long getUnitRatio(){
      return unitRatio;
   }

   public void setUnitRatio(long temp){
      unitRatio = temp;
   }

   public boolean contains(int x, int y) {
      ImuV2D at = new ImuV2D(x,y);
      if(list1.x()<=at.x() && list1.y()<=at.y()
              && at.x()<list1.x()+recWH.x()
              && at.y()<list1.y()+recWH.y()){
         return true;
      }
      else if(list2.x()<=at.x() && list2.y()<=at.y()
              && at.x()<list2.x()+recWH.x()
              && at.y()<list2.y()+recWH.y()){
         return true;
      }
      else if(list3.x()<=at.x() && list3.y()<=at.y()
              && at.x()<list3.x()+recWH.x()
              && at.y()<list3.y()+recWH.y()){
         return true;
      }
      else
         return false;
   }

   public void mouseEntered(java.awt.event.MouseEvent e) {
      setCursor(handCursor);
      repaint();
   }
   public void mouseExited(java.awt.event.MouseEvent e) {
      setCursor(normalCursor);
      repaint();
   }
   public void mousePressed(java.awt.event.MouseEvent e) {
      if(list1.x()<=e.getX() && list1.y()<=e.getY()
             && e.getX()<list1.x()+recWH.x()
             && e.getY()<list1.y()+recWH.y()){
         unitRatio = 1;
         _owner.stepChanged(1, this);
      }
      else if(list2.x()<=e.getX() && list2.y()<=e.getY()
             && e.getX()<list2.x()+recWH.x()
             && e.getY()<list2.y()+recWH.y()){
         unitRatio = LY2Km;
         _owner.stepChanged(2, this);
      }
      else if(list3.x()<=e.getX() && list3.y()<=e.getY()
             && e.getX()<list3.x()+recWH.x()
             && e.getY()<list3.y()+recWH.y()){
         unitRatio = LY2AU;
         _owner.stepChanged(3, this);
      }
      _owner.repaint();
      repaint();
   }
   public void mouseReleased(java.awt.event.MouseEvent e) {
      repaint();
   }
   public void mouseClicked(java.awt.event.MouseEvent e) {
      repaint();
   }

   public void mouseMoved(java.awt.event.MouseEvent e) {
      repaint();
   }
   public void mouseDragged(java.awt.event.MouseEvent e) {
      repaint();
   }


   public void paintName(Graphics g){
      g.setColor(Color.GRAY);
      g.fillRect(list1.x(), list1.y(), recWH.x(), recWH.y());
      g.fillRect(list2.x(), list2.y(), recWH.x(), recWH.y());
      g.fillRect(list3.x(), list3.y(), recWH.x(), recWH.y());
      g.setColor(Color.BLUE);
      if(unitRatio == 1)
         g.fillRect(list1.x(), list1.y(), recWH.x(), recWH.y());
      else if(unitRatio == LY2Km)
         g.fillRect(list2.x(), list2.y(), recWH.x(), recWH.y());
      else if(unitRatio == LY2AU)
         g.fillRect(list3.x(), list3.y(), recWH.x(), recWH.y());
      g.setColor(Color.WHITE);
      g.drawString("光年",list1.x()+12, list1.y()+12);
      g.drawString("公里",list2.x()+12, list2.y()+12);
      g.drawString("天文單位",list3.x(), list3.y()+12);
      repaint();
   }

   public void paintComponent(java.awt.Graphics g){
      //int x=getX(), y=getY(); //A31208X not use
      int w=getWidth(), h=getHeight();
      g.setColor(Color.BLACK);  g.fillRect(0, 0, w, h);
      paintName(g);
   }
}