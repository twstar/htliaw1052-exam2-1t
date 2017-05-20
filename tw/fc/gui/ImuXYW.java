/*

package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.TxOStream;

//**********    ImuXYW.java    ****************
//
//  x, y, width, height=wigth, to represent a screen square
//

public class ImuXYW        
   implements DuplicableI<MuXYW>, PrintableI
{
   int x, y, w;  
   public final int x() {  return x; }
   public final int y() {  return y; }
   public final int w() {  return w; }
   public final int h() {  return w; }

//------------------------------------------------------------
   public String toString() { return " ( " + x + ", " + y + ", " + w + " ) " ;  }
   public final boolean equals(ImuXYW o2) { 
      return (x==o2.x && y==o2.y && w==o2.w );  
   }
   public final int hashCode() {  return (x*37+y)*37+w;  }

//------------------------------------------------------------
   public     ImuXYW() {  x=0; y=0; w=0; }
   public     ImuXYW(int xx, int yy, int ww) {  
      x=xx; y=yy; w=ww;  
   }
   public    ImuXYW(ImuXYW src) {  
      x=src.x; y=src.y; w=src.w;  
   }
   public     ImuXYW(double xx, double yy, double ww) { 
      x=(int)(Math.round(xx)); y=(int)(Math.round(yy));  
      w=(int)(Math.round(ww));  
   }
   public     ImuXYW(ImuV2D c, double r) {
      // 由圓心及半徑造外接正方形
      x=(int)Math.round(c.x()-r); y=(int)Math.round(c.y()-r); 
      w=(int)Math.round(r*2);  
   } 

//------------------------------------------------------------
      
   //[--------  implement PrintableI   ---------   
   public final MuXYW duplicate() {  return new MuXYW(this); }
   //]--------  implement PrintableI   ---------   

   //[-------- implements PrintableI ------------------- 
   public final void printTo(TxOStream ooo) throws java.io.IOException {  
      ooo.p("(").pc(x).pc(y).p(w).p(")");
   }
   public final void widthPrintTo(int i, TxOStream ooo) 
      throws java.io.IOException
   {  
      ooo.p("(").wpc(i,x).wpc(i,y).wp(i,w).p(")");
   }
   //]-------- implements PrintableI -------------------    

//-----------------------------------------------------   
   
//   public final ImuRect toRect() {  
//      return new ImuRect(this); 
//   }
   
   public final ImuXY toXY() {  return new ImuXY(x,y); }
   
   public void drawString(java.awt.Graphics g, int xPos, int yPos) {
      g.drawString(toString(),xPos,yPos);
   }
  
//---------------
   
   public final ImuXYW add(ImuXY v2) {  return new ImuXYW(x+v2.x, y+v2.y,w);    }
   public final ImuXYW sub(ImuXY v2) {  return new ImuXYW(x-v2.x, y-v2.y,w);   }
   
}

*/