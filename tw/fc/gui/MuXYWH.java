package tw.fc.gui;
   import java.io.IOException;
   import tw.fc.*;
//**********    MuXYWH.java    ****************
//
//  x, y, w, h, to represent a screen rectangle
//

//[  extending MuXY would cause Add's return type conflict 
//[  containing MuXY would decrese performance
public class MuXYWH extends ImuXYWH    
   implements SetableI<ImuXYWH>, ScannableI 
{
//I   int x, y, w, h;  
//I   public final int x() {  return x; }
//I   public final int y() {  return y; }
//I   public final int w() {  return w; }
//I   public final int h() {  return h; }

   public final void xSetBy(int v) {  x=v;  }  
   public final void ySetBy(int v) {  y=v;  }  
   public final void wSetBy(int v) {  w=v;  }  
   public final void hSetBy(int v) {  h=v;  }

//[  deprecated ----
   public final void setX(int v) {  x=v;  }  
   public final void setY(int v) {  y=v;  }  
   public final void setW(int v) {  w=v;  }  
   public final void setH(int v) {  h=v;  }
//] ----- 

//------------------------------------------------------------
//I   public String toString() 
//I   public final boolean equals(ImuXYWH o2)  
//I   public final int hashCode() 


//--------------------------------------------------
   public     MuXYWH() {  super(); }
   public     MuXYWH(int xx, int yy, int ww, int hh) {  
      super(xx,yy,ww,hh); 
   }
   public     MuXYWH(double xx, double yy, double ww, double hh) { 
      super(xx,yy,ww,hh); 
   }
   public    MuXYWH(ImuXYWH src) {  super(src);  }
// public    MuXYWH(ImuRect r) {  super(r); }
   public    MuXYWH(ImuXY p1, ImuXY p2) {  
      super();  super._setBy(p1, p2); 
   }
   public    MuXYWH(ImuV2D p1, ImuV2D p2) {  
      super();  super._setBy(p1.toXY(), p2.toXY()); 
   }

//--------------------------------------------------

   public final MuXYWH setBy(ImuXYWH src) {  
      x=src.x;  y=src.y;  w=src.w;  h=src.h;  return this; 
   }
   public final MuXYWH setBy(int xx, int yy, int ww, int hh) {  
      x=xx;  y=yy; w=ww;  h=hh;  return this; 
   }

// public final MuXYWH setBy(ImuRect s) {  
//    x=s.tl.x;  y=s.tl.y;  w=s.br.x-s.tl.x;  h=s.br.y-s.tl.y;   
//    return this; 
// }

   public final MuXYWH setBy(ImuV2D c, double a, double b) {
      // 由橢圓中心及長短半軸設定外接長方形
      x=(int)Math.round(c.x()-a); y=(int)Math.round(c.y()-b); 
      w=(int)Math.round(a*2);     h=(int)Math.round(b*2);  
      return this;
   } 

   public final MuXYWH setBy(ImuXY p1, ImuXY p2) {
   //: p1, p2為任意對角點
      super._setBy(p1, p2);  return this;
   }

   public final MuXYWH setBy(ImuV2D p1, ImuV2D p2) {
   //: p1, p2為任意對角點
      super._setBy(p1.toXY(), p2.toXY());  return this;
   }

//------------------------------------------------------------
      
//[--------   implement OperatableI
//I   public final DuplicableI duplicate() {  return new MuXYWH(this); }

/////   public final SetableI setBy(DuplicableI s) { 
/////      return setBy((ImuXYWH)s); 
/////   }

   public void scanFrom(TxIStream iii) throws IOException {
      iii.skipWS().expect('(').skipWS();
/////      x=(int)TwLong._scan(iii,iii.getRadix());
      x=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      y=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      w=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      h=iii.get_int(iii.getRadix());
      iii.skipWS().expect(')');
   }

//]--------   implement OperatableI

//-----------------------------------------------------   
   
//I   public final ImuRect toRect() {  ...  }
//I   public final ImuXY toXY() {  return new ImuXY(x,y); }

//I   public void drawString(java.awt.Graphics g, int xPos, int yPos) {  ...  }
  
//---------------

//I   public final ImuXYWH add(ImuXY v2) {  ...   }
//I   public final ImuXYWH sub(ImuXY v2) {  ...   }
   
   public final MuXYWH addBy(ImuXY v2) { x+=v2.x; y+=v2.y; return this; }
   public final MuXYWH subBy(ImuXY v2) {  x-=v2.x;  y-=v2.y;  return this; }
   
}
