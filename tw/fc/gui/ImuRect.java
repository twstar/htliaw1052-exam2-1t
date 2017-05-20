/*

package tw.fc.gui ;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.WidthPrintableI;
   import tw.fc.TxOStream;

//**********    ImuRect.java    ****************
//
//  top-left and bottom-right, to represent a screen rectangle
//

@Deprecated            
public class ImuRect implements DuplicableI<MuRect>, PrintableI, WidthPrintableI
{
   MuXY  tl;  //: top-left
   MuXY  br;  //: bottom-right

   //-------------------------------------------------
   
   public ImuRect() {  tl=new MuXY(); br=new MuXY(); }
   public ImuRect(int x1, int y1, int x2, int y2) {  
      tl=new MuXY(x1,y1); br=new MuXY(x2,y2); 
   }
   public ImuRect(ImuXY p1, ImuXY p2) {  
      tl=new MuXY(p1); br=new MuXY(p2); 
   }
   public ImuRect(ImuRect src) {  
      tl=new MuXY(src.tl); br=new MuXY(src.br); 
   }
   public ImuRect(ImuXYWH src) {  
      tl=new MuXY(src.x,src.y); 
      br=new MuXY(src.x+src.w, src.y+src.h); 
   }
   public ImuRect(ImuXYW src) {  
      tl=new MuXY(src.x,src.y); 
      br=new MuXY(src.x+src.w, src.y+src.w); 
   }
   
   //------------------------------------------------
   public final ImuXY tl() {  return tl; }
   public final ImuXY br() {  return br; }

   //--------   
   public String toString() { 
      return "(" + tl + "," + br + ")" ;  
   }
   public final boolean equals(ImuRect v) { 
      return (tl.equals(v.tl) && br.equals(v.br)); 
   }
   public final int hashCode() {  
      return tl.hashCode()*37+br.hashCode();  
   }
   
   //[-------- implements DuplicableI ------------------
   public final MuRect duplicate() {  return new MuRect(this); }
   //]-------- implements DuplicableI ------------------

   //[-------- implements PrintableI ------------------- 
   public final void printTo(TxOStream ooo) throws java.io.IOException {  
      ooo.p(" (").pc(tl).p(br).p(") ");
   }
   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {  
      ooo.p(" (").wpc(w,tl).ps().wp(w,br).p(") ");
   }

   //]-------- implements PrintableI -------------------    

//   public final ImuXYWH toXYWH() {
//      return new ImuXYWH(this);                      
//   }
   
   public final ImuRect add(ImuXY v) {  return new ImuRect(tl.add(v), br.add(v));  }
   public final ImuRect sub(ImuXY v) {  return new ImuRect(tl.sub(v), br.sub(v)); }

}

*/