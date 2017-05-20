package tw.fc.gui;

//import java.awt.Graphics;
//import java.awt.event.MouseEvent;
//import java.awt.Color;
//import tw.fc.*;
import javax.swing.ImageIcon;
//import java.awt.event.MouseListener;
//import javax.swing.*;
//import static tw.fc.gui.GrPanel.*;

public class GrToggleButton extends GrButton {

   private static final long serialVersionUID = 2010062900L;

   public GrToggleButton(double x, double y, ImageIcon icon1, GrPanel own){
       super(x, y, icon1, own);
   }

   public GrToggleButton(ImageIcon icon1, GrPanel own){
      super(icon1, own);
   }

   public GrToggleButton(double x, double y, ImageIcon icon1, String text, GrPanel own){
       super(x, y, icon1, text, own);
   }

   public GrToggleButton(ImageIcon icon1, String text, GrPanel own){
      super(icon1, text, own);
   }

   public GrToggleButton(double x, double y, String name1, GrPanel own){
       super(x, y, name1, own);
   }

    public GrToggleButton(String name1, GrPanel own){
      super(name1,own);
    }

   public GrToggleButton(double x, double y, String name1, String text, GrPanel own){
      super(x, y, name1, text, own);
   }

   public GrToggleButton(String name1, String text, GrPanel own){
      super(name1, text, own);
   }

   public void mousePressed(java.awt.event.MouseEvent e) {
      if(!isClick) return;
      state = !state;
      repaint();
      _owner.repaint();
   }
   public void mouseReleased(java.awt.event.MouseEvent e) {
      if(!isClick) return;
      _owner.stepChanged(0, this);
      repaint();
      _owner.repaint();
   }
}