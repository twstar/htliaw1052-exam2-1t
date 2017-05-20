package tw.fc.gui;

import java.awt.Graphics;
//import java.awt.event.MouseEvent;
import java.awt.Color;
//import tw.fc.*;
import javax.swing.ImageIcon;
import java.awt.event.MouseListener;
//import javax.swing.*;
import static tw.fc.gui.GrPanel.*;

public class GrButton extends GrComponent implements MouseListener {

   private static final long serialVersionUID = 2010062906L;

   protected MuV2D recXY = new MuV2D();
   protected ImageIcon icon = null;
   protected int w,h;
   protected boolean state = false;
   protected String name="";
   protected boolean visible = true;
   protected boolean isClick = true;//是否可按
   protected GrPanel _owner;

   //--------------------------------------------------------
   // AddBy: Thomas
   // Date:  2010/0121
   protected static final Color COLOR_TIP_BG = new Color(184, 207, 229);
   protected static final Color COLOR_TIP_BOUND = new Color(99, 130, 191);
   protected static final Color COLOR_TIP_WORD = new Color(0, 0, 0);
   protected static final int TIP_HEIGHT = 21;
   protected static final int TIP_WORD_WIDTH = 5;
   protected static final int TIP_OFFSET = 5;

   //--------------------
   protected boolean _isInside;
   protected int _mouseX, _mouseY;
   protected String _tipText;
   //--------------------------------------------------------

   public GrButton(double x, double y, ImageIcon icon1, GrPanel own){
      recXY.setBy(x,y);
      icon = icon1;
      w = icon.getIconWidth();
      h = icon.getIconHeight();
      this.addMouseListener(this);

      _owner = own;

      this._isInside = false;
   }

   public GrButton(ImageIcon icon1, GrPanel own){
      this(0, 0, icon1, own);
   }

   public GrButton(double x, double y, ImageIcon icon1, String text, GrPanel own){
      recXY.setBy(x,y);
      icon = icon1;
      w = icon.getIconWidth();
      h = icon.getIconHeight();
      this.addMouseListener(this);

      this.setToolTipText(text);
      _owner = own;

      this._tipText = text;
      this._isInside = false;
   }

   public GrButton(ImageIcon icon1, String text, GrPanel own){
      this(0, 0, icon1, text, own);
   }

   public GrButton(double x, double y, String name1, GrPanel own){
      recXY.setBy(x,y);
      name = name1;
      w = name.length()*12+5;
      h = 20;
      _owner = own;
      this.addMouseListener(this);


      this._isInside = false;
   }

    public GrButton(String name1, GrPanel own){
      this(0,0,name1,own);
    }

   public GrButton(double x, double y, String name1, String text, GrPanel own){
      recXY.setBy(x,y);
      name = name1;
      w = name.length()*12+5;
      h = 20;
      _owner = own;
      this.setToolTipText(text);
      this.addMouseListener(this);


      this._tipText = text;
      this._isInside = false;
   }

   public GrButton(String name1, String text, GrPanel own){
      this(0,0,name1, text, own);
   }

   public void setXY(double x, double y){
      recXY.setBy(x,y);
   }

   public boolean contains(int x, int y) {
     return (recXY.x()<=x && recXY.y()<=y
             && x<recXY.x()+w && y<recXY.y()+h);
   }

   public void setState(boolean state1){
     state = state1;
     repaint();
      _owner.repaint();
   }

   public void mouseEntered(java.awt.event.MouseEvent e) {
      if(!visible) return;
      setCursor(handCursor);

      if (this._tipText != null) {
         this._isInside = true;
         this._mouseX = e.getX(); this._mouseY = e.getY();
      }
//D   System.out.println("mouseEntered");

      _owner.repaint();
   }
   public void mouseExited(java.awt.event.MouseEvent e) {
      if(!visible) return;
      setCursor(normalCursor);

      if (this._tipText != null) { this._isInside = false; }
//D   System.out.println("mouseExited");

       _owner.repaint();
   }
   public void mousePressed(java.awt.event.MouseEvent e) {
      if(!visible) return;
      if(!isClick) return;
      state = !state;
      repaint();
      _owner.repaint();
   }
   public void mouseReleased(java.awt.event.MouseEvent e) {
      if(!visible) return;
      if(!isClick) return;
      _owner.stepChanged(0, this);
      state = !state;
      repaint();
      _owner.repaint();
   }
   public void mouseClicked(java.awt.event.MouseEvent e) {
      if(!visible) return;
      repaint();
   }

   public void drawName(Graphics g){
     g.setColor(Color.DARK_GRAY);
     g.fillRect(recXY.ix(),recXY.iy()+2,w,h);
     g.setColor(Color.WHITE);
     g.drawRect(recXY.ix(), recXY.iy(), w, h);
     g.drawString(name,recXY.ix()+3,recXY.iy()+16);
   }

   public void setVisible(boolean newState){//將改成setVisible
      visible = newState;
   }

   public void setClick(boolean click){//將改成setVisible
      isClick = click;
   }

   //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   public void drawTip(Graphics g) {
      if (this._isInside) {
         int w = _owner.getWidth();
         int h = _owner.getHeight();
         Color orgColor = g.getColor();
         int drawTipW = TIP_WORD_WIDTH * this._tipText.getBytes().length + TIP_OFFSET * 3 ;
         int drawTipH = TIP_HEIGHT;
         if(this._mouseX+drawTipW>w){
             this._mouseX = w - drawTipW;
         }
         if(this._mouseY+drawTipH>h){
             this._mouseY = h - drawTipH;
         }
         g.setColor(COLOR_TIP_BG);
         g.fillRect(this._mouseX, this._mouseY,
                    drawTipW, drawTipH);
         g.setColor(COLOR_TIP_BOUND);
         g.drawRect(this._mouseX, this._mouseY,
                    drawTipW, drawTipH);
         g.setColor(COLOR_TIP_WORD);
         g.drawString(this._tipText,
                      this._mouseX + TIP_OFFSET-3,
                      this._mouseY + TIP_OFFSET * 3);

         g.setColor(orgColor);
      }
   }

   public void setIcon(ImageIcon i){
      icon = i;
   }

   public void paintComponent(Graphics g){
      if(visible){
         if(icon !=null){
            g.drawImage(icon.getImage(),  recXY.ix(), recXY.iy(), null, null);
         }
         else{
            drawName(g);
         }
         //System.out.println(recXY);
         if(state){
            g.setColor(Color.BLUE);
            g.drawRect(recXY.ix(), recXY.iy(), w, h);
            g.drawRect(recXY.ix()+1, recXY.iy()+1, w-2, h-2);
            g.drawRect(recXY.ix()+2, recXY.iy()+2, w-4, h-4);
         }
      }
   }
}