package tw.fc.gui;

//import jav/a.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
//import javax.swing.JFrame;
//import tw.fc.gui.*;
import java.text.DecimalFormat;
import static tw.fc.gui.GrPanel.*;
import java.awt.*;
import javax.swing.event.MouseInputListener;

public class RatioRuler extends GrComponent implements MouseInputListener
{
   private static final long serialVersionUID = 2010062902L;

   protected double ruleX, ruleY, ruleW, ruleH;
   protected int winX=0,winY=0;
   final GrPanel _owner;
   double ruleZeroPos;
   double ruleSize;
/////   int direction;
   int pressedDir;
   private int state =0; //:  0雙尺  1右尺  2左尺

   protected double currentUnit;  //// =0.000000001;
   protected double currentScale;

   protected final int ALLOC_SIZE=50;
   protected final double[][] scale={
      new double[ALLOC_SIZE],
      new double[ALLOC_SIZE*2],
      new double[ALLOC_SIZE*10]
   };
   protected final int[] lenScale= { 0, 0, 0 };
   protected final double[] scaleFactor= { 1.0, 0.5, 0.1 };

   protected final double[] labelScale=new double[ALLOC_SIZE];
   protected int labelCount;
   protected final double[] labelFactor={ 0.1, 0.2, 0.5, 1.0, 2.0, 5.0 };

   protected final MuV2D[] scalePtLow={ new MuV2D(), new MuV2D(), new MuV2D() };
   protected final MuV2D[] scalePtHigh={ new MuV2D(), new MuV2D(), new MuV2D() };
   protected final MuV2D   lablePt=new MuV2D();

   protected final Stroke stroke2 = new BasicStroke(1.0f);
   protected final static Color lineColor=Color.BLACK;
   protected final static Color scaleColor=Color.BLUE;
   protected final static Color rulerColor=Color.YELLOW;

////   protected final MuV2D last=new MuV2D();
   //protected double lastRulePos; //: 記錄上一個滑鼠在尺上的位置
   protected double lastDiff; //: 記錄上一個滑鼠與ruleZeroPos的距離
////   final MuV2D t0=new MuV2D(), t1=new MuV2D(),
   //: 計算用

   //-----------------------
   public RatioRuler(double x, double y, double w, double h, GrPanel own){//預設為雙尺
      setRuleXYWH(x, y, w, h);
      _owner = own;
      setCurrentUnit(0.000000001);  //////  createScale();
      setRule();
      //direction=1;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }

   public RatioRuler(double w, double h, GrPanel own){
      this(0.0, 0.0, w, h, own);
   }

   //-------
   public void setLeftRight(){
      state =0;
   }

   public void setRight(){
      state =1;
   }

   public void setLeft(){
      state =2;
   }

   public void setRuleXYWH(double x, double y, double w, double h){
      if(state == 0){
         ruleX = x;  ruleY = y;  ruleW=w;  ruleH=h;
         ruleZeroPos = ruleX+ruleW/2;
         ruleSize = ruleW/2;
      }
      if(state == 1){
         ruleX = x;  ruleY = y;  ruleW=w;  ruleH=h;
         ruleZeroPos=ruleX;
         ruleSize = ruleW;
      }
      if(state == 2){
         ruleX = x;  ruleY = y;  ruleW=w;  ruleH=h;
         ruleZeroPos=ruleX+ruleW-1;
         ruleSize = ruleW;
      }
   }

   protected void createScale() {
      final double SWITCHBOUND=1.1;
      // Require:   SB*10**p >= u,  p>=log10(u/SB)
      // Then:      p=ceil(log10(u/SB))
      final double p=Math.ceil(
                        Math.log10(currentUnit/SWITCHBOUND)
                     );
      currentScale=Math.pow(10, p);
      final double bound=10*currentUnit;
      for(int type=0; type<3; type++) {
         final double scaleUnit= currentScale*scaleFactor[type];
         int i=1;
         while(true) {
            final double val=i*scaleUnit;
            if(val>bound) break;
            scale[type][i-1]=val;
            i++;
         }
         lenScale[type]=i-1;
      }
      // Require:   10**p >= u,  p>=log10(u)
      // Then:      p=ceil(log10(u))
      // Find k's such that  u <= k*10**p <= 10*u
      final double p1=Math.ceil(Math.log10(currentUnit));
      final double nameUnit=Math.pow(10, p1);
      labelCount=0;
/////      clearLabelScale();
      for(int i=0; i<labelFactor.length; i++) {
         final double t=labelFactor[i]*nameUnit;
         if((currentUnit/SWITCHBOUND)<=t && t<=bound) {
            labelScale[labelCount++]=t;//刻度
         }
      }
      for(int i=labelCount; i<ALLOC_SIZE; i++){
         labelScale[i]=0;
      }
   }

////   private void clearLabelScale(){
////      for(int i=0; i<ALLOC_SIZE; i++){
////         labelScale[i]=0;
////      }
////   }

   ////public void setWinXY(int x, int y){
   ////   winX=x;  winY=y;
   ////}


   private void setRule() {
// cout.p("\nruleH=").pc(ruleH).p("ruleW=").pn(ruleW);
      scalePtHigh[0].ySetBy(ruleH*0.25); scalePtLow[0].ySetBy(ruleH*0.75);
      scalePtHigh[1].ySetBy(ruleH*0.35); scalePtLow[1].ySetBy(ruleH*0.75);
      scalePtHigh[2].ySetBy(ruleH*0.5); scalePtLow[2].ySetBy(ruleH*0.75);
      lablePt.ySetBy(ruleH*0.25);
   }

   public void setCurrentUnit(double Cu) {
      currentUnit=Cu;   createScale();
   }
   public final double getCurrentUnit() {  return currentUnit; }

   public int getLabelCount() {  return labelCount;  }
   public double getLabelScale(int index) {
      return labelScale[index];
   }
   public double getMaxLabelScale() {
      return labelScale[labelCount-1];
   }


   protected final static double WINCOVER=10.2;
       //: WINCOVER是currentUnit的倍率, 物理尺度
       //: ruleW是尺寬的視窗尺度
   public final double winToRule(double winPos) {
/////      final double rulePos=winPos*(WINCOVER/ruleW);
      return winPos*(WINCOVER/ruleSize);
   }
   public final double ruleToWin(double rulePos) {
/////      final double winPos=rulePos*(ruleW/WINCOVER);
      return rulePos*(ruleSize/WINCOVER);
   }

   strictfp void drawName(Graphics g, double scaleVal, int dir) {//strictfp確保每台電腦數值一樣
      final double rulePos=scaleVal/currentUnit;
      final double winPos=ruleToWin(rulePos);
      lablePt.xSetBy(ruleZeroPos+dir*winPos);
      DecimalFormat Digits=new DecimalFormat("#.#E0");
      String num=Digits.format(scaleVal);
      MuV2D lablePt1 = new MuV2D(lablePt);
      //lablePt1.addBy(ruleX,ruleY);
      lablePt1.addBy(0,ruleY);
      GrDrawName(g, num, lablePt1, -5, -2);
//D   cout.pn(scaleVal);
   }

   strictfp void drawScale(Graphics g, int i, double scaleVal, int dir) {
      final double rulePos=scaleVal/currentUnit;
      final double winPos=ruleToWin(rulePos);
      scalePtLow[i].xSetBy(ruleZeroPos+dir*winPos);
      scalePtHigh[i].xSetBy(ruleZeroPos+dir*winPos);
      MuV2D scalePtLow1 = new MuV2D(scalePtLow[i]);
      //scalePtLow1.addBy(ruleX,ruleY);
      scalePtLow1.addBy(0,ruleY);
      MuV2D scalePtHigh1 = new MuV2D(scalePtHigh[i]);
      //scalePtHigh1.addBy(ruleX,ruleY);
      scalePtHigh1.addBy(0,ruleY);
      GrDrawLine(g, scalePtLow1, scalePtHigh1);
   }

   void _drawAllScale(Graphics g, int dir) {
      //g.setColor(scaleColor);
      double[] scaleRow; int len;
      scaleRow=scale[0]; len=lenScale[0];
      for(int i=0; i<len; i++) {  //: 全格
         drawScale(g, 0, scaleRow[i], dir);
      }
      scaleRow=scale[1]; len=lenScale[1];
      for(int i=0; i<len; i+=2) { //: 半格
         drawScale(g, 1, scaleRow[i], dir);
      }
      scaleRow=scale[2]; len=lenScale[2];
      for(int i=0; i<len; i++) { //: 1/10格
         if(i%5==4) continue;
         drawScale(g, 2, scaleRow[i], dir);
      }
      for(int i=0; i<labelCount; i++) {
         drawName(g, labelScale[i], dir);
      }
   }

   void drawAllScale(Graphics g) {
      if(state == 0){
         _drawAllScale(g, -1);
         _drawAllScale(g, +1);
      }
      if(state == 1){
         _drawAllScale(g, +1);
      }
      if(state == 2){
         _drawAllScale(g, -1);
      }
   }

   void drawCenterLine(Graphics g){
      for(int i=-1; i<=1; i++) {
         //g.drawLine((int)(i+ruleX+ruleZeroPos), (int)ruleY, (int)(i+ruleX+ruleZeroPos), (int)(ruleH+ruleY));
         g.drawLine((int)(i+ruleZeroPos), (int)ruleY, (int)(i+ruleZeroPos), (int)(ruleH+ruleY));
      }
   }

   //void drawBackGround(Graphics g){
   //   g.setColor(Color.YELLOW);
   //   int winX1 = (winX-70)/2;
   //   g.fillRect(70+winX1, winY-50, winX1, 50);
   //   g.setColor(Color.GRAY);
   //   for(int i=0; i<2; i++) {
   //      g.drawLine(70+winX1,winY-50+i,winX,winY-50+i);
   //      g.drawLine(70+winX1,winY-i,winX,winY-i);
   //      g.drawLine(winX-i,winY-50,winX-i,winY);
   //   }
   //}

   public void paintComponent(java.awt.Graphics g){
      //int x=getX(), y=getY(); //A31208X not use
      int w=getWidth(), h=getHeight();
      g.setColor(Color.BLACK);  g.fillRect(0, 0, w, h);
      Graphics2D g2d=(Graphics2D) g;
      g2d.setStroke(stroke2);
      if(ruleH<0 || ruleW<0) return;
      //drawBackGround(g1);
      g.setColor(Color.WHITE);
      drawAllScale(g);
      g.setColor(Color.GRAY);
      drawCenterLine(g);
   }

   public boolean contains(int x, int y) {
      ImuV2D at = new ImuV2D(x,y);
      return ruleX<=at.x() && ruleY<=at.y()
             && at.x()<ruleX+ruleW
             && at.y()<ruleY+ruleH;
   }

   public void mouseEntered(java.awt.event.MouseEvent e) {
      setCursor(handCursor);
      repaint();
   }

   public void mouseExited(java.awt.event.MouseEvent e) {
      setCursor(normalCursor);
      repaint();
   }

/*
   public void mousePressed(java.awt.event.MouseEvent e) {
      double diff=e.getX()-ruleX;
      //lastRulePos=winToRule(ruleZeroPos+direction*diff);
      double dir=direction, rulePos=ruleZeroPos;
      if(e.getX()>ruleX + ruleSize){////BiRatioRuler用
         dir= -direction ;
         rulePos = 0;
         diff-=ruleSize;
      }
      lastRulePos=winToRule(rulePos+dir*diff);
      if(lastRulePos>10) lastRulePos=10;
      if(lastRulePos<1) lastRulePos=1;
      repaint();
   }
*/

   public void mousePressed(java.awt.event.MouseEvent e) {
      double diff=e.getX()-ruleZeroPos;
      //lastRulePos=winToRule(ruleZeroPos+direction*diff);
      if(diff>=0){////BiRatioRuler用
         pressedDir= 1 ;
      }
      else {
         pressedDir= -1 ;
         diff= -diff;
      }
      lastDiff= winToRule(diff);
      if(lastDiff>10) lastDiff=10;
      if(lastDiff<1) lastDiff=1;
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
      double diff=e.getX()-ruleZeroPos;
      boolean crossCenter= false;
      //double newPos=winToRule(ruleZeroPos+direction*diff);
      if(diff>0){//BiRatioRuler用
         if(pressedDir<0) crossCenter=true;
      }
      else {
         diff=-diff;
         if(pressedDir>0) crossCenter=true;
      }
      double newDiff=winToRule(diff);
      ////double newPos=winToRule(e.getX()-ruleX);
      if(newDiff>10) newDiff=10;
      if(newDiff<1 || crossCenter) newDiff=1;
      setCurrentUnit(currentUnit*(lastDiff/newDiff));
      lastDiff=newDiff;
      if(_owner!=null) _owner.horizontalChanged(currentUnit, this);
      repaint();
   }
}



