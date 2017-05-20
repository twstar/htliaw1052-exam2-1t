package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.WidthPrintableI;
   import tw.fc.TxOStream;

//**********    ImuXY.java    ****************
//
//  Integer pair, to represent a screen position
//
public class ImuXY  implements DuplicableI<MuXY>, PrintableI, WidthPrintableI
{
   //=====================================================
   int x, y;   

   //------------------------------------------------------   
   public  ImuXY() {  x=0; y=0; }
   public  ImuXY(int xx, int yy) {  x=xx; y=yy; }
   public  ImuXY(double xx, double yy) { 
      x=(int)(Math.round(xx)); y=(int)(Math.round(yy)); 
   }
   public  ImuXY(ImuXY src) {  x=src.x;  y=src.y;  }
//   public  ImuXY(ImuV2D v) {  
//      x=(int)(Math.round(v.x())); y=(int)(Math.round(v.y())); 
//   }  //: 用src.toXY()

   public  ImuXY(java.awt.event.MouseEvent e) {  
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
   public final boolean equals(ImuXY v2) { return (x==v2.x && y==v2.y); }
   public final boolean notEquals(ImuXY v2) { return !equals(v2); }
   public final boolean equals(Object v2) { return equals((ImuXY)v2); }
                                       // v2可能剛從 collection 拿出來  
   public final int hashCode() {  return x*37+y;  }
   public final boolean isZero() {  return (x==0 && y==0);  }
   public final boolean notZero() {  return (x!=0 || y!=0);  }
   
   //[--------  implement DuplicableI   ---------   
   public final MuXY duplicate() {  return new MuXY(this);   }
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
   
   public final ImuXY add(ImuXY v2) {  return new ImuXY(x+v2.x, y+v2.y);    }
   public final ImuXY add(int x2, int y2) {  return new ImuXY(x+x2, y+y2);    }

   public final ImuXY sub(ImuXY v2) {  return new ImuXY(x-v2.x, y-v2.y);   }
   public final ImuXY sub(int x2, int y2) {  return new ImuXY(x-x2, y-y2);   }

   public final ImuXY neg() {  return new ImuXY(-x,-y); }
   public final ImuXY xNeg() {  return new ImuXY(-x,y); }
   public final ImuXY yNeg() {  return new ImuXY(x,-y); }
   
   public final ImuXY mul(int k) {  //:  scalar product
      return  new MuXY(x*k, y*k);   
   }
//   public final ImuXY mul(double k) {  //:  scalar product
//      return  new MuXY(x*k, y*k);   
//   }
@Deprecated 
public final ImuXY smul(int k) {  //:  scalar product
      return  new ImuXY(x*k, y*k);   
}
   public final int dot(ImuXY v2) {  //:  dot product
      return x*v2.x+ y*v2.y;  
   }
@Deprecated 
public final int dotmul(ImuXY v2) {  //:  dot product
      return x*v2.x+ y*v2.y;  
}
   public final int wedge(ImuXY v2) {  //:  wedge product
      return  (x*v2.y-y*v2.x);
   }
@Deprecated 
public final int wedgemul(ImuXY v2) {  //:  wedge product
      return  (x*v2.y-y*v2.x);
}
      
   public final int normSq() {  return (x*x+y*y);   }
   public final double norm() {  return Math.sqrt(x*x+y*y); }
   public final int distanceSq(ImuXY v) {  
      final int dx=x-v.x, dy=y-v.y;
      return (dx*dx+dy*dy); 
   }
   public final double distance(ImuXY v) {  
      final int dx=x-v.x, dy=y-v.y;
      return Math.sqrt(dx*dx+dy*dy); 
   }
   public final int distanceSq(int x, int y) {  
      x-=this.x;  y-=this.y;   return x*x+y*y; 
   }
   public final double distance(int x, int y) {  
      x-=this.x;  y-=this.y;   return Math.sqrt(x*x+y*y); 
   }

//   public final ImuXY proj(ImuXY d) {  //:  project into direction d
//      if(d.normSq()==0) return ImuXY.ZERO;
//      return d.mul(this.dot(d)/(double)d.dot(d));
//   }
//   public final ImuXY coProj(ImuXY d) { 
//   //:  this-proj(this,d) 
//      if(d.normSq()==0) return new MuXY(this);
//      return linearCombination(
//         1, this, -(this.dot(d)/(double)d.dot(d)), d
//      );
//   }
//   public final ImuXY refl(ImuXY d) { //:  reflect point of this wrt d
//   //:   2*this.proj(d)-this   
//      if(d.normSq()==0) return this.neg();
//      return linearCombination(
//         2.0*this.dot(d)/d.dot(d),d, -1,this
//      );
//   }
//   public final ImuXY coRefl(ImuXY d) {   //: -this.refl(d)
//   //:  this-2*this.proj(d)   
//      if(d.normSq()==0) return new MuXY(this);
//      return linearCombination(
//         1.0,this, 2.0*this.dot(d)/d.dot(d),d
//      );
//   }

 //[=========   static part  =================================

   public static final ImuXY ZERO=new ImuXY(0,0);
   //public static final ImuXY E1  =new ImuXY(1.0,0.0);  //: b4 A50617
   //public static final ImuXY E2  =new ImuXY(0.0,1.0);  //: b4 A50617
   public static final ImuXY E1  =new ImuXY(1,0);
   public static final ImuXY E2  =new ImuXY(0,1);

   //-------------------
   public static final ImuXY linearCombination(
      int a1, ImuXY v1, int a2, ImuXY v2
   ) { //: compute a1*v1+a2*v2
      return new MuXY(
         a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y
      );
   }

   public static final ImuXY linearCombination(
      int a1, ImuXY v1, int a2, ImuXY v2, int a3, ImuV2D v3) 
   {  //:  a1*v1+a2*v2+a3*v3
      return new MuXY(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }

//   public static final ImuXY linePoint(
//      ImuXY p1, ImuXY p2, double t
//   ) {
//      return linearCombination(1-t, p1, t, p2);
//   }
//   public static final ImuXY midPoint(ImuXY p1, ImuXY p2) {  
//      return p1.add(p2).mul(0.5);   
//   }

//]=========   static part  =================================

}
