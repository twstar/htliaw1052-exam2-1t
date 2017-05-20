package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.WidthPrintableI;
   import tw.fc.TxOStream;

//**********    ImuV2Di.java    ****************
//
//  Integer pair, to represent a screen position
//
public class ImuV2Di  implements DuplicableI<MuV2Di>, PrintableI, WidthPrintableI
{
   //=====================================================
   int x, y;   

   //------------------------------------------------------   
   public  ImuV2Di() {  x=0; y=0; }
   public  ImuV2Di(int xx, int yy) {  x=xx; y=yy; }
   public  ImuV2Di(double xx, double yy) { 
      x=(int)(Math.round(xx)); y=(int)(Math.round(yy)); 
   }
   public  ImuV2Di(ImuV2Di src) {  x=src.x;  y=src.y;  }
//   public  ImuV2Di(ImuV2D v) {  
//      x=(int)(Math.round(v.x())); y=(int)(Math.round(v.y())); 
//   }  //: 用src.toXY()

   public  ImuV2Di(java.awt.event.MouseEvent e) {  
      x=e.getX(); y=e.getY(); 
   }

   //------------------------------------------------------   

   public final int x() {  return x; }
   public final int y() {  return y; }
   public final int comp(int axis) {  //: A5.039L.J add
      switch(axis) {
      case 0:  return x;
      case 1:  return y;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

   public String toString() { return " ( " + x + ", " + y + " ) " ;  }
   public final boolean equals(ImuV2Di v2) { return (x==v2.x && y==v2.y); }
   public final boolean notEquals(ImuV2Di v2) { return !equals(v2); }
   public final boolean equals(Object v2) { return equals((ImuV2Di)v2); }
                                       // v2可能剛從 collection 拿出來  
   public final int hashCode() {  return x*37+y;  }
   public final boolean isZero() {  return (x==0 && y==0);  }
   public final boolean notZero() {  return (x!=0 || y!=0);  }
   
   //[--------  implement DuplicableI   ---------   
   public final MuV2Di duplicate() {  return new MuV2Di(this);   }
   //]--------  implement DuplicableI   ---------   
   //[-------- implements PrintableI ------------------- 
   public final void printTo(TxOStream ooo) throws java.io.IOException {  
      ooo.p("(").pc(x).p(y).p(")");
   }
   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {  
      ooo.p("(").wpc(w,x).wp(w,y).p(")");
   }
   //]-------- implements PrintableI -------------------    
   
   public final ImuV2Di add(ImuV2Di v2) {  return new ImuV2Di(x+v2.x, y+v2.y);    }
   public final ImuV2Di add(int x2, int y2) {  return new ImuV2Di(x+x2, y+y2);    }

   public final ImuV2Di sub(ImuV2Di v2) {  return new ImuV2Di(x-v2.x, y-v2.y);   }
   public final ImuV2Di sub(int x2, int y2) {  return new ImuV2Di(x-x2, y-y2);   }

   public final ImuV2Di neg() {  return new ImuV2Di(-x,-y); }
   public final ImuV2Di xNeg() {  return new ImuV2Di(-x,y); }
   public final ImuV2Di yNeg() {  return new ImuV2Di(x,-y); }
   
   public final ImuV2Di mul(int k) {  //:  scalar product
      return  new MuV2Di(x*k, y*k);   
   }
//   public final ImuV2Di mul(double k) {  //:  scalar product
//      return  new MuV2Di(x*k, y*k);   
//   }
@Deprecated 
public final ImuV2Di smul(int k) {  //:  scalar product
      return  new ImuV2Di(x*k, y*k);   
}
   public final int dot(ImuV2Di v2) {  //:  dot product
      return x*v2.x+ y*v2.y;  
   }
@Deprecated 
public final int dotmul(ImuV2Di v2) {  //:  dot product
      return x*v2.x+ y*v2.y;  
}
   public final int wedge(ImuV2Di v2) {  //:  wedge product
      return  (x*v2.y-y*v2.x);
   }
@Deprecated 
public final int wedgemul(ImuV2Di v2) {  //:  wedge product
      return  (x*v2.y-y*v2.x);
}
      
   public final int normSq() {  return (x*x+y*y);   }
   public final double norm() {  return Math.sqrt(x*x+y*y); }
   public final int distanceSq(ImuV2Di v) {  
      final int dx=x-v.x, dy=y-v.y;
      return (dx*dx+dy*dy); 
   }
   public final double distance(ImuV2Di v) {  
      final int dx=x-v.x, dy=y-v.y;
      return Math.sqrt(dx*dx+dy*dy); 
   }
   public final int distanceSq(int x, int y) {  
      x-=this.x;  y-=this.y;   return x*x+y*y; 
   }
   public final double distance(int x, int y) {  
      x-=this.x;  y-=this.y;   return Math.sqrt(x*x+y*y); 
   }

//   public final ImuV2Di proj(ImuV2Di d) {  //:  project into direction d
//      if(d.normSq()==0) return ImuV2Di.ZERO;
//      return d.mul(this.dot(d)/(double)d.dot(d));
//   }
//   public final float comp(ImuV2Di d) {
//   //:  component of project into direction d
//      if(d.normSq()<Std.epsilonSq()) return 0.0f;
//      return this.dot(d)/d.norm();
//   }
//   public final ImuV2Di coProj(ImuV2Di d) { 
//   //:  this-proj(this,d) 
//      if(d.normSq()==0) return new MuV2Di(this);
//      return linearCombination(
//         1, this, -(this.dot(d)/(double)d.dot(d)), d
//      );
//   }
//   public final ImuV2Di refl(ImuV2Di d) { //:  reflect point of this wrt d
//   //:   2*this.proj(d)-this   
//      if(d.normSq()==0) return this.neg();
//      return linearCombination(
//         2.0*this.dot(d)/d.dot(d),d, -1,this
//      );
//   }
//   public final ImuV2Di coRefl(ImuV2Di d) {   //: -this.refl(d)
//   //:  this-2*this.proj(d)   
//      if(d.normSq()==0) return new MuV2Di(this);
//      return linearCombination(
//         1.0,this, 2.0*this.dot(d)/d.dot(d),d
//      );
//   }

 //[=========   static part  =================================

   public static final ImuV2Di ZERO=new ImuV2Di(0,0);
   //public static final ImuV2Di E1  =new ImuV2Di(1.0,0.0);  //: b4 A50617
   //public static final ImuV2Di E2  =new ImuV2Di(0.0,1.0);  //: b4 A50617
   public static final ImuV2Di E1  =new ImuV2Di(1,0);
   public static final ImuV2Di E2  =new ImuV2Di(0,1);

   //-------------------
   public static final ImuV2Di linearCombination(
      int a1, ImuV2Di v1, int a2, ImuV2Di v2
   ) { //: compute a1*v1+a2*v2
      return new MuV2Di(
         a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y
      );
   }

   public static final ImuV2Di linearCombination(
      int a1, ImuV2Di v1, int a2, ImuV2Di v2, int a3, ImuV2D v3) 
   {  //:  a1*v1+a2*v2+a3*v3
      return new MuV2Di(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }

//   public static final ImuV2Di linePoint(
//      ImuV2Di p1, ImuV2Di p2, double t
//   ) {
//      return linearCombination(1-t, p1, t, p2);
//   }
//   public static final ImuV2Di midPoint(ImuV2Di p1, ImuV2Di p2) {  
//      return p1.add(p2).mul(0.5);   
//   }

//]=========   static part  =================================

}
