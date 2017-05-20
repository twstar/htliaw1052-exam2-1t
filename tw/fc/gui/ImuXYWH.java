package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.TxOStream;

//**********    ImuXYWH.java    ****************
//
//  x, y, width, height, to represent a screen rectangle
//

public class ImuXYWH        
   implements DuplicableI<MuXYWH>, PrintableI
{
   int x, y, w, h;  
   public final int x() {  return x; }
   public final int y() {  return y; }
   public final int w() {  return w; }
   public final int h() {  return h; }

//------------------------------------------------------------
   public String toString() { return " ( " + x + ", " + y + ", " + w + ", " + h + " ) " ;  }
   public final boolean equals(ImuXYWH o2) { 
      return (x==o2.x && y==o2.y && w==o2.w && h==o2.h);  
   }
   public final int hashCode() {  return ((x*37+y)*37+w)*37+h;  }


//------------------------------------------------------------
   public     ImuXYWH() {  x=0; y=0; w=0; h=0;}
   public     ImuXYWH(int xx, int yy, int ww, int hh) {  
      x=xx; y=yy; w=ww; h=hh; 
   }
   public     ImuXYWH(double xx, double yy, double ww, double hh) { 
      x=(int)(Math.round(xx)); y=(int)(Math.round(yy));  
      w=(int)(Math.round(ww)); h=(int)(Math.round(hh)); 
   }
   public    ImuXYWH(ImuXYWH src) {  
      x=src.x; y=src.y; w=src.w; h=src.h; 
   }
//   public    ImuXYWH(ImuRect s) {  
//      x=s.tl.x; y=s.tl.y; w=s.br.x-s.tl.x; h=s.br.y-s.tl.y; 
//   }
   public     ImuXYWH(ImuV2D c, double a, double b) {
      // 由橢圓中心及x,y半軸造外接長方形
      x=(int)Math.round(c.x()-a); y=(int)Math.round(c.y()-b); 
      w=(int)Math.round(a*2);   ; h=(int)Math.round(b*2);  
   } 

   public    ImuXYWH(ImuXY p1, ImuXY p2) { 
      this(); _setBy(p1, p2); 
   }
   public    ImuXYWH(ImuV2D p1, ImuV2D p2) { 
      this(); _setBy(p1.toXY(), p2.toXY()); 
   }


//------------------------------------------------------------
   @SuppressWarnings("unused")      
   final void _setBy(ImuXY p1, ImuXY p2) {
   //: p1, p2為任意對角點. 實作用,不public.
      int minX, minY, W, H;
      if(p1.x()<=p2.x()) {
         minX=p1.x();  W=p2.x()-p1.x();
      }
      else {
         minX=p2.x();  W=p1.x()-p2.x();
      }

      if(p1.y()<=p2.y()) {
         minY=p1.y();  H=p2.y()-p1.y();
      }
      else {
         minY=p2.y();  H=p1.y()-p2.y();
      }
      this.x=minX; this.y=minY;  this.w=W;  this.h=H;  //: A31210 補漏
   }

   //[--------  implement PrintableI   ---------   
   public final MuXYWH duplicate() {  return new MuXYWH(this); }
   //]--------  implement PrintableI   ---------   

   //[-------- implements PrintableI ------------------- 
   public final void printTo(TxOStream ooo) throws java.io.IOException {  
      ooo.p("(").pc(x).pc(y).pc(w).p(h).p(")");
   }
   public final void widthPrintTo(int i, TxOStream ooo) 
      throws java.io.IOException
   {  
      ooo.p("(").wpc(i,x).wpc(i,y).wpc(i,w).wp(i,h).p(")");
   }
   //]-------- implements PrintableI -------------------    

//-----------------------------------------------------   
   
//   public final ImuRect toRect() {  
//      return new ImuRect(this); 
//   }
   
   public final ImuXY toXY() {  return new ImuXY(x,y); }
   
   public final ImuXY center() {  return new ImuXY(x+w/2, y+h/2); }
   public void drawString(java.awt.Graphics g, int xPos, int yPos) {
      g.drawString(toString(),xPos,yPos);
   }
  
//---------------
   
   public final ImuXYWH add(ImuXY v2) {  return new ImuXYWH(x+v2.x, y+v2.y,w,h);    }
   public final ImuXYWH sub(ImuXY v2) {  return new ImuXYWH(x-v2.x, y-v2.y,w,h);   }
   
}
