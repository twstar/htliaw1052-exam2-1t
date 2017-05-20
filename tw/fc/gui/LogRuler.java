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

//[ ��Ƥإ����u�O��m(thumb�U�t)�M��.
//[ �Ȫ����ѫȤ�X�ۤv�O���ø���
//[ ��l�]�w�� setMinMaxPos(min, max)
//[ �]�w�� defineThumb(pos, value)
//[ ������ defineValue(value)


public class LogRuler extends GrComponent
       implements MouseInputListener, ComponentListener
{
   private static final long serialVersionUID = 2010062904L;

   private final GrPanel _owner ;
   private final int thumbSize=25;
   private int minPos, maxPos;

   private int pos;           //: �D���
   private double value;      //: �D���

   private int thumbX, thumbW;
      //: thumbH��thumbSize, thumbY��pos-thumbSize

   private final int AREA_ELSE=0, AREA_THUMB=1, AREA_RULE=2;
   private int pressedArea; //: �\��P�O��

   private int lastMouseY;  //: �즲��

   //[ �ȥ�
   //private final MuXY tmp=new MuXY(); //A31208X not use
   private final MuV2D pt1=new MuV2D(), pt2=new MuV2D();
   //]

   //[ thumbColor��scaleColor�}�񪽱��ϥ�
   //[ �I����� Component�� setBackground(.), getBackground()
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
          //: �ˤJJScrollPane����row header��,
          //: XYWH�H�ɷ|�Q��, Y�٥i�౲���t��.   -- htliaw
      _owner = own;
      super.setBackground(new Color(0,0,80));  //: �`��, �Ȥ�i�ۧ�
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
      if(this.pos<min || this.pos>max) {  //: �X�ɽվ�
         this.defineThumb_slideBack(this.pos, this.value);
      }
   }

   protected void setMinMaxComponentPos() {
      this.setMinMaxPos(thumbSize, super.getHeight()-thumbSize);
   }

   protected int posInBound(int mouseY) {
      //[ �ƹ�����scroll�v�T
      final int vy=(this.isRowHeader())? 0: super.getY();
                                               //: virtual y
      if(mouseY>vy+maxPos) { //[ thumb���U�t�X��
         return vy+maxPos;  //: �վ㬰�̤j�i���
      }
      if(mouseY-thumbSize<vy+minPos) { //[ thumb���W�t�X��
         return vy+minPos+thumbSize; //: �վ㬰�̤p�i���
      }
      return mouseY;
   }

   protected double getPos() {  return this.pos;   }
   public double getValue() {  return this.value;   }

   //[ �]�wthumb�ثe����m�P��, ���\�X��.
   //[ this.pos ��, this.value ��, ���o�ƥ�.
   public void defineThumb(int p, double v) {
      if(Double.isNaN(v)) throw new IllegalArgumentException("NaN");
      if(Double.isInfinite(v)) throw new IllegalArgumentException("infinite");
      this.pos=p;
      this.value=v;
   }

   //[ �]�wthumb�ثe����m�P��, �Y�X�ɷ|�ưʾ�Ӥب�thumb���J.
   //[ this.pos ��, this.value ��, ���o�ƥ�.
   public void defineThumb_slideBack(int p, double v) {
      if(Double.isNaN(v)) throw new IllegalArgumentException("NaN");
      if(Double.isInfinite(v)) throw new IllegalArgumentException("infinite");
      this.pos=posInBound(p);
      this.value=v;
   }

   //[ ******    another version, �ثe���ϥ�    **********
   //[ �]�wthumb�ثe����m�P��, �Y�X�ɷ|(�۹��scale)�Ԧ^thumb.
   //[ this.pos ��, this.value ��, ���o�ƥ�.
   protected void defineThumb_dragBack(int p, double v) {
      if(Double.isNaN(v)) throw new IllegalArgumentException("NaN");
      this.pos=posInBound(p);
      if(this.pos!=p) { //[ outside adjust
         //[ p�ӤW�������p: p�Ӥp, v�Ӥj
         v/= Math.pow(10, (this.pos-p)/(double)thumbSize );
      }
      if(Double.isInfinite(v)) throw new IllegalArgumentException("infinite");
      this.value=v;
   }

   //[ �����ɥ�. �Ȥ�X���ۦ�O������ηs���.
   //[ this.pos ����, this.value ��, ���o�ƥ�.
   public void defineValue(double v) {
      this.defineThumb(this.pos, v);
   }

   //[ (�~��)�ηs�ȭn�D����thumb
   //[ this.pos ��, this.value ��, ���o�ƥ�.
   public void moveValue(double v) {
      final int d=(int)Math.round(Math.log10(v/this.value)*thumbSize);
      defineThumb(this.pos-d, v); //: pos�V�U���W�q
   }

   //[ �ηs��m�n�D����thumb
   //[ this.pos ��, this.value ��, ���o�ƥ�.
   protected void movePos(int p) {
      final double r=Math.pow(10, (p-this.pos)/(double)thumbSize);
      this.defineThumb(p, this.value/r);  //: pos�V�U���W�q
   }

   //----------------
   private void drawBackGround(Graphics g){
      //[ �˶i JScrollPane �� row header ��, getY()�i�]�W�����ܭt.
      //[ ����add�icontentPane��getY()�i�ର��.
      int vx, vy;
      if(this.isRowHeader()) {   vx=0;  vy=0;  }
      else {   vx=super.getX();  vy=super.getY();  }
//D   vy=super.getY();  �����ɥΨ���ܤW�����p
      final int w=super.getWidth(), h=super.getHeight();
      g.setColor(super.getBackground());
      g.fillRect(vx, vy, w, h);
   }

   private void drawScale(Graphics g){
   //[ �ȦV�W�W, pos�V�W��.
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
      {  //: �ѤU���W�e
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
      //: �ƹ�����scroll�v�T
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
   //[ pos ��, value��, �o�ƥ�.
      this.movePos(this.pos+(mouseY-this.lastMouseY));
      this.lastMouseY=mouseY;
      repaint();
      if(_owner !=null)_owner.verticalChanged(value, this);
   }

   //[ ��Ӥطư�, ���\thumb�X��
   private void dragRule(int mouseY) {
   //[ pos ��, value����, ���o�ƥ�.
      this.pos=(this.pos+(mouseY-this.lastMouseY));
      this.lastMouseY=mouseY;
      repaint();
   }

   //[ ****  another version, �ثe����  ****
   //[ ��Ӥطư�, �����|��thumb�X��
   @SuppressWarnings("unused") //:A31208X not use
   private void dragRule_inBound(int mouseY) {
   //[ pos ��, value����, ���o�ƥ�.
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