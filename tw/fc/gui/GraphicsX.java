package tw.fc.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import static java.lang.Math.*;

//========================================================
public class GraphicsX {

   public Graphics2D g2;

   int textPosX=30, textPosY=30, textDY=20;  
        //: used in setTextPos(), setTextDY(), pn()
   int posX, posY;  
        //: used in moveTo(..), lineTo(..)

   double arrowLength=4, arrowHalfWidth=2; 
        //: used in arrowTo(..)
   //----------------------------------
   public GraphicsX(Graphics g) {
      g2=(Graphics2D)g;
   }

   //----------------------------------
   public GraphicsX create() {  return new GraphicsX(g2.create()); }

   //----------------------
   private static int round(double x) {
      return (int)Math.round(x);
   }
   public static int[] round(double[] xA) {
      int[] ans=new int[xA.length];
      for(int i=0; i<xA.length; i++) ans[i]= (int)Math.round(xA[i]);
      return ans;
   }

   public int getTextX() {  return textPosX; }
   public int getTextY() {  return textPosY; }
   public int getTextDY() {  return textDY; }
   public void setTextXY(int x, int y) {  textPosX=x;  textPosY=y;  }
   public void setTextPos(int x, int y) {  textPosX=x;  textPosY=y;  }
   public void setTextDY(int dy) {  textDY=dy; }

   public GraphicsX pn(String s) {
      g2.drawString(s, textPosX, textPosY);  
      textPosY+=textDY;
      return this;
   }
   
   //
   public void antialias(boolean b) {
      if(b) {  // 開啟去鋸齒化功能
      	g2.setRenderingHint(
           RenderingHints.KEY_ANTIALIASING,
 		       RenderingHints.VALUE_ANTIALIAS_ON
        );
      }
      else {  // 關閉去鋸齒化功能
      	g2.setRenderingHint(
           RenderingHints.KEY_ANTIALIASING,
 		       RenderingHints.VALUE_ANTIALIAS_OFF
        );
   	  }
   }

   //--------------------
   public Color getColor() { return g2.getColor(); }
   public GraphicsX setColor(Color c) {  g2.setColor(c); return this; }
   public Font getFont() { return g2.getFont(); }
   public GraphicsX setFont(Font f) {  g2.setFont(f); return this; } 

   // A30110 xie
   public void setStrokeWidth(float w){
		g2.setStroke(new BasicStroke(w));
	}
	
	public float getStrokeWidth(){
		return ((BasicStroke)g2.getStroke()).getLineWidth();
	}


   //--------------------

   //public GraphicsX plot(int x1, int y1) { //: before A30522L
   public GraphicsX plot(double x, double y) {
      int x1=round(x), y1=round(y);
      g2.drawLine(x1, y1, x1, y1);  return this;
   }
   public GraphicsX plot(ImuXY p) {
      return this.plot(p.x(), p.y());  
   }
   public GraphicsX plot(ImuV2D p) {
      return this.plot(p.ix(), p.iy());  
   }

   //public GraphicsX drawLine(int x1, int y1, int x2, int y2) { //: before A30522L
   public GraphicsX drawLine(double x1, double y1, double x2, double y2) {
      g2.drawLine(round(x1), round(y1), round(x2), round(y2));  
      return this;
   } 
   public GraphicsX drawLine(ImuXY p1, ImuXY p2) {
      g2.drawLine(p1.x(), p1.y(), p2.x(), p2.y());  return this;
   }
   public GraphicsX drawLine(ImuV2D p1, ImuV2D p2) {
      g2.drawLine(p1.ix(), p1.iy(), p2.ix(), p2.iy());  return this;
   }

   //public GraphicsX moveTo(int x, int y) {  //: before A30522L
   public GraphicsX moveTo(double x, double y) {
      posX=round(x);  posY=round(y);  return this;
   }
   public GraphicsX moveTo(ImuXY p) {
      posX=p.x();  posY=p.y();  return this;
   }
   public GraphicsX moveTo(ImuV2D p) {
      posX=p.ix();  posY=p.iy();  return this;
   }

   //public GraphicsX lineTo(int x, int y) { //: before A30522L
   public GraphicsX lineTo(double x0, double y0) {
      int x=round(x0), y=round(y0);
      g2.drawLine(posX, posY, x, y);       
      posX=x;  posY=y;  return this;
   }
   public GraphicsX lineTo(ImuXY p) {
      return this.lineTo(p.x(), p.y());       
   }
   public GraphicsX lineTo(ImuV2D p) {
      return this.lineTo(p.ix(), p.iy());       
   }

   public GraphicsX setArrowSize(double len, double width) {
      if(len<=0 || width<=0) 
         throw new IllegalArgumentException(len+","+width); 
      arrowLength=len; arrowHalfWidth=width/2;  return this; 
   }
   public GraphicsX arrowTo(double x, double y) {
      throw new Error("not implement");


   }
   public GraphicsX arrowTo(ImuV2D p) {
      throw new Error("not implement");


   }

   //public GraphicsX drawRect(int x, int y, int w, int h) { //: before A30522L
   public GraphicsX drawRect(double x, double y, double w, double h) { 
      g2.drawRect(round(x),round(y),round(w),round(h));  return this;
   } 
   public GraphicsX drawRect(ImuXYWH r) {
      g2.drawRect(r.x(), r.y(), r.w(), r.h());   return this;
   }

   //public GraphicsX fillRect(int x, int y, int w, int h) { //: before A30522L
   public GraphicsX fillRect(double x, double y, double w, double h) {
      g2.fillRect(round(x),round(y),round(w),round(h));  return this;
   } 
   public GraphicsX fillRect(ImuXYWH r) {
      g2.fillRect(r.x(), r.y(), r.w(), r.h());   return this;
   }

   public GraphicsX drawRect(ImuXY p1, ImuXY p2) { // p1, p2為任意對角點
      return this.drawRect(new ImuXYWH(p1,p2));
   }
   public GraphicsX drawRect(ImuV2D p1, ImuV2D p2) { // p1, p2為任意對角點
      return this.drawRect(new ImuXYWH(p1,p2));
   }

   public GraphicsX drawRect(ImuXY c, int halfW, int halfH) { 
      g2.drawRect(c.x(), c.y(), halfW*2, halfH*2);   return this;
   }
   public GraphicsX drawRect(ImuV2D c, double halfW, double halfH) { 
      g2.drawRect(c.ix(), c.iy(), round(halfW*2), round(halfH*2));   
      return this;
   }

   public GraphicsX fillRect(ImuXY p1, ImuXY p2) {
      return this.fillRect(new ImuXYWH(p1,p2));
   }
   public GraphicsX fillRect(ImuV2D p1, ImuV2D p2) {
      return this.fillRect(new ImuXYWH(p1,p2));
   }
   public GraphicsX fillRect(ImuXY c, int halfW, int halfH) { 
      g2.fillRect(c.x(), c.y(), halfW*2, halfH*2);   return this;
   }
   public GraphicsX fillRect(ImuV2D c, double halfW, double halfH) { 
      g2.fillRect(c.ix(), c.iy(), round(halfW*2), round(halfH*2));   
      return this;
   }

   //public GraphicsX drawOval(int x, int y, int w, int h) { //: before A30522L
   public GraphicsX drawOval(double x, double y, double w, double h) {
      g2.drawOval(round(x),round(y),round(w),round(h));  return this;
   } 
   public GraphicsX drawOval(ImuXYWH r) {
      g2.drawOval(r.x(), r.y(), r.w(), r.h());   return this;
   }
   public GraphicsX drawOval(ImuV2D c, double halfW, double halfH) {
      final int x=round(c.x()-halfW); 
      final int y=round(c.y()-halfH); 
      final int w=round(halfW*2);   
      final int h=round(halfH*2);  
      g2.drawOval(x, y, w, h);  return this;
   }
 
   //public GraphicsX fillOval(int x, int y, int w, int h) { //: before A30522L
   public GraphicsX fillOval(double x, double y, double w, double h) {
      g2.fillOval(round(x),round(y),round(w),round(h));  return this;
   } 
   public GraphicsX fillOval(ImuXYWH r) {
      g2.fillOval(r.x(), r.y(), r.w(), r.h());   return this;
   }
   public GraphicsX fillOval(ImuV2D c, double halfW, double halfH) {
      final int x=(int)Math.round(c.x()-halfW); 
      final int y=(int)Math.round(c.y()-halfH); 
      final int w=(int)Math.round(halfW*2);   
      final int h=(int)Math.round(halfH*2);  
      g2.fillOval(x, y, w, h);  return this;
   }

   public GraphicsX drawCircle(ImuV2D center, double R) {
      if(R<0) return this;  // NOP
      if(R<0.5) return this.plot(center); 
      final int iR=(int)Math.round(R);
      final int i2R=(int)Math.round(2*R);
      g2.drawOval(center.ix()-iR, center.iy()-iR, i2R, i2R);
      return this;
   }

   public GraphicsX fillCircle(ImuV2D center, double R) {
      if(R<0) return this;
      if(R<0.5) return this.plot(center); 
      final int iR=(int)Math.round(R);
      final int i2R=(int)Math.round(2*R);
      g2.fillOval(center.ix()-iR, center.iy()-iR, i2R, i2R);
      return this;
   }

   //public GraphicsX drawString(String s, int x, int y) { //: before A30522L
   public GraphicsX drawString(String s, double x, double y) {
      g2.drawString(s, round(x), round(y));   return this;
   }
   public GraphicsX drawString(String s, ImuV2D p) {
      g2.drawString(s, p.ix(), p.iy());  return this;
   }

   public GraphicsX drawName(String name, ImuV2D p, int dx, int dy) {
      g2.drawString(name, p.ix()+dx, p.iy()+dy);
      return this;
   }

   //[ Draws a sequence of connected lines defined by arrays of 
   //[ x and y coordinates. 
   public GraphicsX drawPolyline(double[] xP, double[] yP, int nP) {
      g2.drawPolyline(round(xP), round(yP), nP);
      return this;
   } 

   //[ Draws a sequence of connected lines defined by arrays of 
   //[ x and y coordinates. 
   //[ need overloading since int[] cannot be cast to double[]
   public GraphicsX drawPolyline(int[] xP, int[] yP, int nP) {
      g2.drawPolyline(xP, yP, nP);
      return this;
   } 

//[ client code should use moveTo and line To
// public GraphicsX drawPolyline(ImuXY[] P, int nP) {

//[ client code should use moveTo and lineTo
// public GraphicsX drawPolyline(ImuV2D[] P, int nP) {

   //[ draws the polygon defined by nP line segments
   public GraphicsX drawPolygon(double[] xP, double[] yP, int nP) {
      g2.drawPolygon(round(xP), round(yP), nP);
      return this;
   } 

   //[ draws the polygon defined by nP line segments
   //[ need overloading since int[] cannot be cast to double[]
   public GraphicsX drawPolygon(int[] xP, int[] yP, int nP) {
      g2.drawPolygon(xP, yP, nP);
      return this;
   } 

//[ client code should use moveTo and lineTo
// public GraphicsX drawPolygon(ImuXY[] P, int nP) 

//[ client code should use moveTo and line To
// public GraphicsX drawPolygon(ImuV2D[] P, int nP) 

   //[ Fills a closed polygon defined by arrays of x and y coordinates.
   public GraphicsX fillPolygon(double[] xP, double[] yP, int nP) {
      g2.fillPolygon(round(xP), round(yP), nP); 
      return this;
   }
   public GraphicsX fillPolygon(int[] xP, int[] yP, int nP) {
      g2.fillPolygon(xP, yP, nP); 
      return this;
   }

   public GraphicsX fillPolygon(ImuXY[] P, int nP) {
      final int[] xP=new int[nP], yP=new int[nP];
      for(int i=0; i<nP; i++) {
         xP[i]=P[i].x();  yP[i]=P[i].y();
      }
      g2.fillPolygon(xP, yP, nP);
      return this;
   }
   public GraphicsX fillPolygon(ImuXY[] P) {
      return this.fillPolygon(P, P.length);
   }

   public GraphicsX fillPolygon(ImuV2D[] P, int nP) {
      final int[] xP=new int[nP], yP=new int[nP];
      for(int i=0; i<nP; i++) {
         xP[i]=P[i].ix();  yP[i]=P[i].iy();
      }
      g2.fillPolygon(xP, yP, nP);
      return this;
   }
   public GraphicsX fillPolygon(ImuV2D[] P) {
      return this.fillPolygon(P, P.length);
   }

   public GraphicsX fillTriangle(ImuV2D p1, ImuV2D p2, ImuV2D p3) {
      final int[] xP=new int[3], yP=new int[3];
      xP[0]=p1.ix();  yP[0]=p1.iy();
      xP[1]=p2.ix();  yP[1]=p2.iy();
      xP[2]=p3.ix();  yP[2]=p3.iy();
      g2.fillPolygon(xP, yP, 3);
      return this;
   }
   public GraphicsX fill4gon(ImuV2D p1, ImuV2D p2, ImuV2D p3, ImuV2D p4) {
      final int[] xP=new int[4], yP=new int[4];
      xP[0]=p1.ix();  yP[0]=p1.iy();
      xP[1]=p2.ix();  yP[1]=p2.iy();
      xP[2]=p3.ix();  yP[2]=p3.iy();
      xP[3]=p4.ix();  yP[3]=p4.iy();
      g2.fillPolygon(xP, yP, 4);
      return this;
   }

   public GraphicsX drawRegPolygon(
      ImuV2D center, double r, int n, double angle) 
   {
      if(n<3) throw new IllegalArgumentException("number of sides:"+n);
      if(r<0) return this;
      final double dA=2.0*Math.PI/n;
      double A=angle;
      int xFr=(int)Math.round(center.x()+r*cos(A)),
          yFr=(int)Math.round(center.y()+r*sin(A));
      for(int i=1; i<=n; i++) {
         A+=dA;
         int xTo=(int)Math.round(center.x()+r*cos(A)),
             yTo=(int)Math.round(center.y()+r*sin(A));
         this.g2.drawLine(xFr,yFr, xTo,yTo);
         xFr=xTo; yFr=yTo;
      }
      return this;
   }

   public GraphicsX fillRegPolygon(
      ImuV2D center, double r, int n, double angle) 
   {
      if(n<3) throw new IllegalArgumentException("number of sides:"+n);
      if(r<0) return this;
      final int[] _xArr=new int[n],_yArr=new int[n]; 
      final double dA=2.0*Math.PI/n;
      double A=angle;
      for(int i=0; i<n; i++) {
         _xArr[i]=(int)Math.round(center.x()+r*cos(A)); 
         _yArr[i]=(int)Math.round(center.y()+r*sin(A));
         A+=dA; 
      }
      this.g2.fillPolygon(_xArr,_yArr,n);
      return this;
   }

}	 