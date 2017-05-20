package tw.fc.gui;
//import tw.fc.gui.*;

import java.io.IOException;

import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.WidthPrintableI;
import tw.fc.TxOStream;

// ***********************************
public class ImuV3Di 
   implements DuplicableI<MuV3Di>, PrintableI, WidthPrintableI
{
   //=====================================================
   int x, y, z;

   //------------------------------------------------------
   public ImuV3Di() { x=0; y=0; z=0; }
   public ImuV3Di(int xx, int yy, int zz) { x=xx; y=yy; z=zz;}
   public ImuV3Di(double xx, double yy, double zz) {
	   x=(int)(Math.round(xx)); y=(int)(Math.round(yy)); z=(int)(Math.round(zz));
   }
   public ImuV3Di(ImuV3Di src) { x=src.x;  y=src.y; z=src.z; }
 
   
   //------------------------------------------------------
   
   public final int x() {  return x; }
   public final int y() {  return y; }
   public final int z() {  return z; }
   //[ A5.039L.J add, so that they can be iterated
   public final int comp(int axis) {  
      switch(axis) {
      case 0:  return x;
      case 1:  return y;
      case 2:  return z;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

   
   public String toString() { return " ( " + x + ", " + y + ", "+z+" ) " ;  }
   public final boolean equals(ImuV3Di v2) { return (x==v2.x && y==v2.y && z==v2.z); }
   public final boolean notEquals(ImuV3Di v2) { return !equals(v2); }
   public final boolean equals(Object v2) { return equals((ImuV3Di)v2); }
   
  // public final int hashCode() {  return x*37+y;  }   //not
   public final boolean isZero() {  return (x==0 && y==0 && z==0);  }
   public final boolean notZero() {  return (x!=0 || y!=0 || z!=0);  }
   
 //[--------  implement DuplicableI   ---------   
   public final MuV3Di duplicate() {  return new MuV3Di(this);  }     
 //]--------  implement DuplicableI   ---------  
 //[-------- implements PrintableI ------------------- 
   public final void printTo(TxOStream ooo) throws java.io.IOException {  
      ooo.p("(").pc(x).pc(y).p(z).p(")");
   }
   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {  
      ooo.p("(").wpc(w,x).wpc(w,y).wp(w,z).p(")");
   }
   //]-------- implements PrintableI -------------------
   
   public final ImuV3Di add(ImuV3Di v2) {  return new ImuV3Di(x+v2.x, y+v2.y, z+v2.z);    }
   public final ImuV3Di add(int x2, int y2, int z2) {  return new ImuV3Di(x+x2, y+y2, z+z2);    }

   public final ImuV3Di sub(ImuV3Di v2) {  return new ImuV3Di(x-v2.x, y-v2.y, z-v2.z);   }
   public final ImuV3Di sub(int x2, int y2, int z2) {  return new ImuV3Di(x-x2, y-y2, z-z2);   }

   public final ImuV3Di neg() {  return new ImuV3Di(-x,-y,-z); }
   public final ImuV3Di xNeg() {  return new ImuV3Di(-x,y,z); }
   public final ImuV3Di yNeg() {  return new ImuV3Di(x,-y,z); }
   public final ImuV3Di zNeg() {  return new ImuV3Di(x,y,-z); }
   
   public final ImuV3Di mul(int k) {  //:  scalar product  
      return  new MuV3Di(x*k, y*k, z*k);   
   } 
   @Deprecated 
   public final ImuV3Di smul(int k) {    
      return  new ImuV3Di(x*k, y*k, z*k);   
   }
   public final int dot(ImuV3Di v2) {   
      return x*v2.x+ y*v2.y+ z*v2.z;  
   }
   @Deprecated 
   public final int dotmul(ImuV3Di v2) {  
      return x*v2.x+ y*v2.y+ z*v2.z;    
   }
   public final ImuV3Di cross(ImuV3Di v2) {  //:  cross product
	      return new MuV3Di(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
	   }
   @Deprecated
   public final ImuV3Di crossMul(ImuV3Di v2) {  //:  cross product
         return new MuV3Di(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
   }
  /* public final int wedge(ImuV3Di v2) {  
      return  (x*v2.y-y*v2.x);
   }  */
   @Deprecated 
 /*  public final int wedgemul(ImuXY v2) {  
         return  (x*v2.y-y*v2.x);
   }   */
   
   public final int normSq() {  return (x*x+y*y+z*z);   }
   public final double norm() {  return Math.sqrt(x*x+y*y+z*z); }
   public final int distanceSq(ImuV3Di v) {  
      final int dx=x-v.x, dy=y-v.y, dz=z-v.z;
      return (dx*dx+dy*dy+dz*dz); 
   }
   public final double distance(ImuV3Di v) {  
      final int dx=x-v.x, dy=y-v.y, dz=z-v.z;
      return Math.sqrt(dx*dx+dy*dy+dz*dz); 
   }
   public final int distanceSq(int x, int y, int z) {  
      x-=this.x;  y-=this.y;  z-=this.z;   return x*x+y*y+z*z; 
   }
   public final double distance(int x, int y, int z) {  
      x-=this.x;  y-=this.y;  z-=this.z; return Math.sqrt(x*x+y*y+z*z); 
   }

   
   //[=========   static part  =================================
   
   public static final ImuV3Di ZERO=new ImuV3Di(0,0,0);
   public static final ImuV3Di E1  =new ImuV3Di(1,0,0);
   public static final ImuV3Di E2  =new ImuV3Di(0,1,0);
   public static final ImuV3Di E3  =new ImuV3Di(0,0,1);
   
   //-------------------
   public static final ImuV3Di linearCombination(
      int a1, ImuV3Di v1, int a2, ImuV3Di v2
   ) { //: compute a1*v1+a2*v2
	  return new MuV3Di(
         a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y, a1*v1.z+a2*v2.z
      );
   }     //not
   
   public static final ImuV3Di linearCombination(
      int a1, ImuV3Di v1, int a2, ImuV3Di v2, int a3, ImuV3D v3) 
   {  //:  a1*v1+a2*v2+a3*v3
      return new MuV3Di(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
   }

// public static final ImuV3Di linePoint(
// ImuXY p1, ImuXY p2, double t
//) {
// return linearCombination(1-t, p1, t, p2);
//}
//public static final ImuV3Di midPoint(ImuV3Di p1, ImuV3Di p2) {  
// return p1.add(p2).mul(0.5);   
//}

//]=========   static part  =================================
   
}
