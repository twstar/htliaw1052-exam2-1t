package tw.fc.gui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
//import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import static tw.fc.gui.GrPanel.*;

//[ 對數尺本身只記位置(thumb下緣)和值.
//[ 值的單位由客戶碼自己記錄並解釋
//[ 初始設定用 setMinMaxPos(min, max)
//[ 設定用 defineThumb(pos, value)
//[ 換單位用 defineValue(value)


public class LogRuler extends GrComponent
       implements MouseInputListener, ComponentListener
{
   private static final long serialVersionUID = 2010062904L;

   private final GrPanel _owner ;
   private final int thumbSize=25;
   private int minPos, maxPos;

   private int pos;           //: 主資料
   private double value;      //: 主資料

   private int thumbX, thumbW;
      //: thumbH恆為thumbSize, thumbY恆為pos-thumbSize

   private final int AREA_ELSE=0, AREA_THUMB=1, AREA_RULE=2;
   private int pressedArea; //: 功能判別用

   private int lastMouseY;  //: 拖曳用

   //[ 暫用
   //private final MuXY tmp=new MuXY(); //A31208X not use
   private final MuV2D pt1=new MuV2D(), pt2=new MuV2D();
   //]

   //[ thumbColor及scaleColor開放直接使用
   //[ 背景色用 Component的 setBackground(.), getBackground()
   public Color thumbColor=Color.GREEN;
   public Color scaleColor=Color.WHITE;
   //]

   private final BasicStroke thickStroke= new BasicStroke(1.5f);
   private final BasicStroke orgStroke= new BasicStroke(1.0f);

   public static final Cursor NS_cursor=
                              new Cursor(Cursor.N_RESIZE_CURSOR);

   //--------------------------------
   public LogRuler(double w, double h, GrPanel own){
      this(0.0, 0.0, w, h, own);
   }

   public LogRuler(double x, double y, double w, double h, GrPanel own){
      super.setBounds((int)Math.round(x),(int)Math.round(y),
                      (int)Math.round(w),(int)Math.round(h));
          //: 裝入JScrollPane成為row header時,
          //: XYWH隨時會被改, Y還可能捲成負值.   -- htliaw
      _owner = own;
      super.setBackground(new Color(0,0,80));  //: 深藍, 客戶可自改
      this.thumbX=(int)Math.round(super.getWidth()*0.55);
      this.thumbW=(int)Math.round(super.getWidth()*0.2);

      this.setMinMaxComponentPos();
      this.defineThumb(maxPos, 1.0);
      super.addMouseListener(this);
      super.addMouseMotionListener(this);
      //if(_owner!=null) _owner.addComponentListener(this);
   }

   //-----
   private boolean isRowHeader() {
      return (super.getParent() instanceof javax.swing.JViewport);
   }

   protected void setMinMaxPos(int min, int max) {
      if(min>max || min<0 ) {
         throw new IllegalArgumentException(
            "\nsetMinMax("+min+","+max+")"
         );
      }
//D   System.out.println("setMinMaxPos("+min+","+max+")");
      this.minPos=min;   this.maxPos=max;
      if(this.pos<min || this.pos>max) {  //: 出界調整
         this.defineThumb_slideBack(this.pos, this.value);
      }
   }

   protected void setMinMaxComponentPos() {
      this.setMinMaxPos(thumbSize, super.getHeight()-thumbSize);
   }

   protected int posInBound(int mouseY) {
      //[ 滑鼠不受scroll影響
      final int vy=(this.isRowHeader())? 0: super.getY();
                                               //: virtual y
      if(mouseY>vy+maxPos) { //[ thumb的下緣出界
         return vy+maxPos;  //: 調整為最大可能值
      }
      if(mouseY-thumbSize<vy+minPos) { //[ thumb的上緣出界
         return vy+minPos+thumbSize; //: 調整為最小可能值
      }
      return mouseY;
   }

   protected double getPos() {  return this.pos;   }
   public double getValue() {  return this.value;   }

   //[ 設定thumb目前的位置與值, 允許出界.
   //[ this.pos 變, this.value 變, 不發事件.
   public void defineThumb(int p, double v) {
      if(Double.isNaN(v)) throw new IllegalArgumentException("NaN");
      if(Double.isInfinite(v)) throw new IllegalArgumentException("infinite");
      this.pos=p;
      this.value=v;
   }

   //[ 設定thumb目前的位置與值, 若出界會滑動整個尺使thumb移入.
   //[ this.pos 變, this.value 變, 不發事件.
   public void defineThumb_slideBack(int p, double v) {
      if(Double.isNaN(v)) throw new IllegalArgumentException("NaN");
      if(Double.isInfinite(v)) throw new IllegalArgumentException("infinite");
      this.pos=posInBound(p);
      this.value=v;
   }

   //[ ******    another version, 目前未使用    **********
   //[ 設定thumb目前的位置與值, 若出界會(相對於scale)拉回thumb.
   //[ this.pos 變, this.value 變, 不發事件.
   protected void defineThumb_dragBack(int p, double v) {
      if(Double.isNaN(v)) throw new IllegalArgumentException("NaN");
      this.pos=posInBound(p);
      if(this.pos!=p) { //[ outside adjust
         //[ p太上面的情況: p太小, v太大
         v/= Math.pow(10, (this.pos-p)/(double)thumbSize );
      }
      if(Double.isInfinite(v)) throw new IllegalArgumentException("infinite");
      this.value=v;
   }

   //[ 換單位時用. 客戶碼應自行記錄原單位及新單位.
   //[ this.pos 不變, this.value 變, 不發事件.
   public void defineValue(double v) {
      this.defineThumb(this.pos, v);
   }

   //[ (外界)用新值要求移動thumb
   //[ this.pos 變, this.value 變, 不發事件.
   public void moveValue(double v) {
      final int d=(int)Math.round(Math.log10(v/this.value)*thumbSize);
      defineThumb(this.pos-d, v); //: pos向下為增量
   }

   //[ 用新位置要求移動thumb
   //[ this.pos 變, this.value 變, 不發事件.
   protected void movePos(int p) {
      final double r=Math.pow(10, (p-this.pos)/(double)thumbSize);
      this.defineThumb(p, this.value/r);  //: pos向下為增量
   }

   //----------------
   private void drawBackGround(Graphics g){
      //[ 裝進 JScrollPane 當 row header 時, getY()可因上捲而變負.
      //[ 直接add進contentPane時getY()可能為正.
      int vx, vy;
      if(this.isRowHeader()) {   vx=0;  vy=0;  }
      else {   vx=super.getX();  vy=super.getY();  }
//D   vy=super.getY();  除錯時用來顯示上捲情況
      final int w=super.getWidth(), h=super.getHeight();
      g.setColor(super.getBackground());
      g.fillRect(vx, vy, w, h);
   }

   private void drawScale(Graphics g){
   //[ 值向上增, pos向上減.
      final double valueLog=Math.log10(this.value);
      final double valueLogFloor=Math.floor(valueLog);
      final int posFloor=
         this.pos+(int)Math.round(
                     (valueLog-valueLogFloor)*thumbSize
                   );
      final int downCount=
         (int)Math.floor((maxPos-posFloor)/(double)thumbSize);
      final int lowestScalePos= posFloor + downCount*thumbSize;
      final int lowestExp= (int)valueLogFloor - downCount;
//D   if(lowestScalePos>maxPos) {
//D      System.out.println("<"+lowestScalePos+", "+maxPos+">");
//D   }
      g.setColor(this.scaleColor);
      int vx, vy;  //: virtual x,y
      if(this.isRowHeader()) {  vx=0; vy=0;  }
      else {   vx=super.getX();  vy=super.getY();   }
      final int w=super.getWidth();
      for(int p=lowestScalePos, e=lowestExp;
          p>=minPos;
          p-=thumbSize, e++)
      {  //: 由下往上畫
         pt1.setBy(vx+w*0.2, vy+p);
         GrDrawName(g, "E"+e, pt1, -10, 5);
         pt1.setBy(vx+w*0.5, vy+p);
         pt2.setBy(vx+w*0.8, vy+p);
         GrDrawLine(g, pt1, pt2);
      }
      pt1.setBy(vx+w*0.65, vy+minPos);  pt2.setBy(vx+w*0.65, vy+maxPos);
      GrDrawLine(g, pt1, pt2);
   }

   private void drawThumb(Graphics g){
      int vx, vy;
      if(this.isRowHeader()) {  vx=0;  vy=0;   }
      else {  vx=super.getX();  vy=super.getY();  }
      g.setColor(this.thumbColor);
      g.fillRect(vx+thumbX, vy+(pos-thumbSize), thumbW, thumbSize);
   }

   @Override
   public void paintComponent(java.awt.Graphics g){
      this.drawBackGround(g);
      this.drawThumb(g);
      final Graphics2D g2=(Graphics2D)g;
      g2.setStroke(thickStroke);
      this.drawScale(g2);
      g2.setStroke(orgStroke);
   }

   //---------

   @Override public boolean contains(int mouseX, int mouseY) {
      if(this.isRowHeader()) {
         return super.contains(mouseX, mouseY);
      }
      else {
         final int x=super.getX(), y=super.getY(),
                   w=super.getWidth(), h=super.getHeight();
         return (x<=mouseX && mouseX<x+w && y<=mouseY && mouseY<y+h);
      }
   }

   protected int determineArea(int mouseX, int mouseY) {
      //: 滑鼠不受scroll影響
      int vx, vy; //: virtual x, y
      if(this.isRowHeader()) {  vx=0;  vy=0;  }
      else {  vx=super.getX();  vy=super.getY();  }
      if(vx+thumbX<=mouseX && mouseX<vx+thumbX+thumbW) {
         if(vy+this.pos-thumbSize<=mouseY && mouseY<vy+this.pos) {
            return AREA_THUMB;
         }
         else {
            return AREA_RULE;
         }
      }
      else {
         return AREA_ELSE;
      }
   }

   private void setAreaCursor(java.awt.event.MouseEvent e) {
      final int at=determineArea(e.getX(), e.getY());
      if(at==AREA_THUMB) {
         super.setCursor(GrPanel.handCursor);
      }
      else if(at==AREA_RULE) {
         super.setCursor(NS_cursor);
      }
      else {
         super.setCursor(GrPanel.normalCursor);
      }
   }

   @Override
   public void mouseEntered(java.awt.event.MouseEvent e) {
      this.setAreaCursor(e);
   }

   @Override
   public void mouseMoved(java.awt.event.MouseEvent e) {
      this.setAreaCursor(e);
   }

   @Override
   public void mouseExited(java.awt.event.MouseEvent e) {
      super.setCursor(GrPanel.normalCursor);
   }

   @Override
   public void mousePressed(java.awt.event.MouseEvent e) {
      final int mouseX=e.getX(), mouseY=e.getY();
      this.pressedArea=determineArea(mouseX, mouseY);
      this.lastMouseY=mouseY;
   }

   @Override
   public void mouseReleased(java.awt.event.MouseEvent e) {  }

   @Override
   public void mouseClicked(java.awt.event.MouseEvent e) {  }

   private void dragThumb(int mouseY) {
   //[ pos 變, value變, 發事件.
      this.movePos(this.pos+(mouseY-this.lastMouseY));
      this.lastMouseY=mouseY;
      repaint();
      if(_owner !=null)_owner.verticalChanged(value, this);
   }

   //[ 整個尺滑動, 允許thumb出界
   private void dragRule(int mouseY) {
   //[ pos 變, value不變, 不發事件.
      this.pos=(this.pos+(mouseY-this.lastMouseY));
      this.lastMouseY=mouseY;
      repaint();
   }

   //[ ****  another version, 目前未用  ****
   //[ 整個尺滑動, 但不會讓thumb出界
   @SuppressWarnings("unused") //:A31208X not use
   private void dragRule_inBound(int mouseY) {
   //[ pos 變, value不變, 不發事件.
      this.pos=posInBound(this.pos+(mouseY-this.lastMouseY));
      this.lastMouseY=mouseY;
      repaint();
   }

   @Override
   public void mouseDragged(java.awt.event.MouseEvent e) {
      if(pressedArea==AREA_THUMB) {  dragThumb(e.getY());   }
      else if(pressedArea==AREA_RULE) {  dragRule(e.getY()); }
      else {  ;  }
   }

   //[ implements ComponentListener
   @Override public void componentShown(ComponentEvent e) { }
   @Override public void componentHidden(ComponentEvent e) { }
   @Override public void componentMoved(ComponentEvent e) {
//D   System.out.print("!Moved, "+super.getX()+", "+super.getY()+"  ");
//D   System.out.println("pos,value="+this.pos+","+this.value);
   }
   @Override public void componentResized(ComponentEvent e) {
System.out.println("!Resized, "+super.getWidth()+", "+super.getHeight());
      setMinMaxComponentPos();
//D   System.out.println("pos,value="+this.pos+","+this.value);
   }

}