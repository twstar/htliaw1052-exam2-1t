/*

package tw.fc.gui ;
   import java.io.IOException;
   import tw.fc.*;

//**********    MuRect.java    ****************
//
//  top-left and bottom-right, to represent a screen rectangle
//
            
@Deprecated            
public class MuRect extends ImuRect 
       implements SetableI<ImuRect>, ScannableI
{
//I   MuXY  tl;  //: top-left
//I   MuXY  br;  //: bottom-right

//I   public final ImuXY tl() {  return tl; }
//I   public final ImuXY br() {  return br; }
   public final void set_tl(ImuXY v) {  tl.setBy(v); }
   public final void set_br(ImuXY v) {  br.setBy(v); }
   public final void set_tl_x(int v) {  tl.x=v; }
   public final void set_tl_y(int v) {  tl.y=v; }
   public final void set_br_x(int v) {  br.x=v; }
   public final void set_br_y(int v) {  br.y=v; }

   //--------   
//I   public String toString() { ...  }
//I   public final boolean equals(ImuRect v) {  ...  }
   
   //--------   
/////   public final SetableI setBy(DuplicableI s) {
/////      final MuRect src=(MuRect)s;
/////      tl.setBy(src.tl);  br.setBy(src.br); 
/////      return this; 
/////   }

   public void scanFrom(TxIStream iii) throws IOException {
      iii.skipWS().expect('(');
      tl.scanFrom(iii);   //: iii.g(tl); 
      iii.skipWS().expect(',');
      br.scanFrom(iii);  //:  iii.g(br);
      iii.skipWS().expect(')');
   }

//I   public final DuplicableI duplicate() {  return new MuRect(this); }
//--------   
   
   public MuRect() {  super(); }
   public MuRect(int x1, int y1, int x2, int y2) {  
      super(x1,y1,x2,y2); 
   }
   public MuRect(ImuXY p1, ImuXY p2) {  super(p1,p2);  }
   public MuRect(ImuRect src) {  super(src);   }
   public MuRect(ImuXYWH src) {  super(src);   }
   
   //------------------------------------------------

   public final MuRect setBy(ImuRect src) {  
      tl.setBy(src.tl);  br.setBy(src.br); 
      return this; 
   }
   public final MuRect setBy(ImuXYWH src) {  
      tl.setBy(src.x,src.y);  br.setBy(src.x+src.w, src.y+src.h); 
      return this;
   }
   
//I   public final ImuXYWH toXYWH() {  ...  }

//I   public void drawString(java.awt.Graphics g, int xPos, int yPos) {

//I   public final ImuRect add(ImuXY v) {  ...  }
//I   public final ImuRect sub(ImuXY v) {  ...  }
   
   public final MuRect addBy(ImuXY v) {  tl.addBy(v); br.addBy(v); return this; }
   public final MuRect subBy(ImuXY v) {  tl.subBy(v); br.subBy(v); return this; }

}

*/
