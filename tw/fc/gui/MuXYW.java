/*

package tw.fc.gui;
   import java.io.IOException;
   import tw.fc.*;
//**********    MuXYW.java    ****************
//
//  x, y, w, w, to represent a screen square
//

//[  extending MuXY would cause Add's return type conflict 
//[  containing MuXY would decrese performance
public class MuXYW extends ImuXYW    
   implements SetableI<ImuXYW>, ScannableI 
{
//I   int x, y, w;  
//I   public final int x() {  return x; }
//I   public final int y() {  return y; }
//I   public final int w() {  return w; }
//I   public final int h() {  return w; }

   public final void xSetBy(int v) {  x=v;  }  
   public final void ySetBy(int v) {  y=v;  }  
   public final void wSetBy(int v) {  w=v;  }  

//[  deprecated ----
   public final void setX(int v) {  x=v;  }  
   public final void setY(int v) {  y=v;  }  
   public final void setW(int v) {  w=v;  }  
//]---------------
 

//------------------------------------------------------------
//I   public String toString() 
//I   public final boolean equals(ImuXYW o2)  
//I   public final int hashCode() 

//--------------------------------------------------
   public     MuXYW() {  super(); }
   public     MuXYW(int xx, int yy, int ww) {  
      super(xx,yy,ww); 
   }
   public     MuXYW(double xx, double yy, double ww) { 
      super(xx,yy,ww); 
   }
   public     MuXYW(ImuXYW src) {  super(src);  }

//--------------------------------------------------

   public final MuXYW setBy(ImuXYW src) {  
      x=src.x;  y=src.y;  w=src.w;  return this; 
   }
   public final MuXYW setBy(int xx, int yy, int ww) {  
      x=xx;  y=yy; w=ww;  return this; 
   }
   public final MuXYW setBy(ImuV2D c, double r) {
      // 由圓心及半徑設定外接正方形
      x=(int)Math.round(c.x()-r); y=(int)Math.round(c.y()-r); 
      w=(int)Math.round(r*2);  return this;
   } 

//------------------------------------------------------------
      
//[--------   implement OperatableI
//I   public final DuplicableI duplicate() {  return new MuXYW(this); }

/////   public final SetableI setBy(DuplicableI s) { 
/////      return setBy((ImuXYW)s); 
/////   }

   public void scanFrom(TxIStream iii) throws IOException {
      iii.skipWS().expect('(').skipWS();
      x=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      y=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      w=iii.get_int(iii.getRadix());
      iii.skipWS().expect(')');
   }

//]--------   implement OperatableI

//-----------------------------------------------------   
   
//I   public final ImuRect toRect() {  ...  }
//I   public final ImuXY toXY() {  return new ImuXY(x,y); }

//I   public void drawString(java.awt.Graphics g, int xPos, int yPos) {  ...  }
  
//---------------

//I   public final ImuXYW add(ImuXY v2) {  ...   }
//I   public final ImuXYW sub(ImuXY v2) {  ...   }
   
   public final MuXYW addBy(ImuXY v2) { x+=v2.x; y+=v2.y; return this; }
   public final MuXYW subBy(ImuXY v2) {  x-=v2.x;  y-=v2.y;  return this; }
   
}

*/