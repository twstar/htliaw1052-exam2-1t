package tw.fc.gui;
//import tw.fc.gui.*;

import java.io.IOException;
import tw.fc.SetableI;
import tw.fc.ScannableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;


// ***********************************
public class MuV3Di extends ImuV3Di  
       implements SetableI<ImuV3Di>, ScannableI
{
//I    int x,y,z;
	
   //------------------------------------------------------   
   public  MuV3Di() {  super(); }
   public  MuV3Di(int xx, int yy, int zz) {  super(xx,yy,zz); }
   public  MuV3Di(double xx, double yy, double zz) {  super(xx,yy,zz);   }
   public  MuV3Di(ImuV3Di src) {  super(src);  }
   // public  MuV3Di(ImuV3D v)  //: ¥Îv.toXYZ()
   
   //------------------------------------------------------ 
	   
//I   public String toString() { return " ( ... }
//I   public final boolean equals(ImuV3Di v2) { ...  }  
	   
   public final MuV3Di setBy(ImuV3Di src) {  x=src.x;  y=src.y; z=src.z; return this; }
   public final MuV3Di setBy(int xx, int yy, int zz) {  x=xx;  y=yy;  z=zz; return this; }
   public final MuV3Di setBy(double xx, double yy, double zz) {  
      x=(int)(Math.round(xx));  y=(int)(Math.round(yy));  z=(int)(Math.round(zz));  
	  return this; 
   }
   public final MuV3Di setBy(ImuV3D src) {  
      return this.setBy(src.x, src.y, src.z);
   }
   public final void xSetBy(int v) {  x=v;  }  
   public final void ySetBy(int v) {  y=v;  }  
   public final void zSetBy(int v) {  z=v;  }  
   //[ A5.039L.K add for iterating
   public final void compSetBy(int axis, int val) { 
      switch(axis) {
      case 0:  x=val;   break;
      case 1:  y=val;   break;
      case 2:  z=val;   break;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

	   
   
//I   public final DuplicableI duplicate() {  return new MuV3Di(this);   }   
   
   public final void scanFrom(TxIStream iii) 
   throws IOException {
      iii.skipWS().expect('(').skipWS();
      x=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      y=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      z=iii.get_int(iii.getRadix());
      iii.skipWS().expect(')');
   }
   
   public static MuV3Di parseXY(String s) {
//     try {
     TxIStrStream inputS=new TxIStrStream(s);
     final MuV3Di x=new MuV3Di();  
     inputS.g(x);
     return x;
//     }catch (IOException xpt) {
//        throw new TxInputException(xpt.toString());
//     }
  } 

//I   public final ImuV3Di add(ImuV3Di v2) {  ...  }
//I   public final ImuV3Di add(int x2, int y2, int z2) {  ...  }
//I   public final ImuV3Di sub(ImuV3Di v2) {  ...   }
//I   public final ImuV3Di sub(int x2, int y2, int z2) {  ...  }
	
   public final MuV3Di addBy(ImuV3Di v2) {  x+=v2.x;  y+=v2.y;  z+=v2.z;  return this; }
   public final MuV3Di subBy(ImuV3Di v2) {  x-=v2.x;  y-=v2.y;  z-=v2.z;  return this; }
   public final MuV3Di addBy(int dx, int dy, int dz){  x+=dx;  y+=dy;  z+=dz;  return this; }
   public final MuV3Di subBy(int dx, int dy, int dz){  x-=dx;  y-=dy;  z-=dz;  return this; }
   public final MuV3Di addBy(double dx, double dy, double dz) {
      x+=(int)(Math.round(dx)); y+=(int)(Math.round(dy)); z+=(int)(Math.round(dz)); 
      return this;
   }
   public final MuV3Di subBy(double dx, double dy, double dz) {
      x-=(int)(Math.round(dx)); y-=(int)(Math.round(dy)); z-=(int)(Math.round(dz)); 
      return this;
   }
   public final MuV3Di addBy(ImuV3D d) {  return addBy(d.x, d.y, d.z);  }
   public final MuV3Di subBy(ImuV3D d) {  return subBy(d.x, d.y, d.z);  }
   public final MuV3Di setByAdd(ImuV3Di v1, ImuV3Di v2) {
      this.setBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }
   public final MuV3Di setBySub(ImuV3Di v1, ImuV3Di v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }
   public final MuV3Di addByAdd(ImuV3Di v1, ImuV3Di v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }
   public final MuV3Di addBySub(ImuV3Di v1, ImuV3Di v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }
   public final MuV3Di subByAdd(ImuV3Di v1, ImuV3Di v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }
   public final MuV3Di subBySub(ImuV3Di v1, ImuV3Di v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }
   
   public final MuV3Di xAddBy(int dx) {  x+=dx;  return this; }
   public final MuV3Di yAddBy(int dy) {  y+=dy;  return this; }
   public final MuV3Di zAddBy(int dz) {  z+=dz;  return this; }
   public final MuV3Di xSubBy(int dx) {  x-=dx;  return this; }
   public final MuV3Di ySubBy(int dy) {  y-=dy;  return this; }
   public final MuV3Di zSubBy(int dz) {  z-=dz;  return this; }
   
//I   public final ImuV3Di neg() {  ...  }
   public final MuV3Di negate() {  x=-x; y=-y; z=-z;  return this;  }
   public final MuV3Di xNegate() {  x=-x;  return this;  }
   public final MuV3Di yNegate() {  y=-y;  return this;  }
   public final MuV3Di zNegate() {  z=-z;  return this;  }
   public final MuV3Di setByNeg(ImuV3Di v) {  setBy(-v.x,-v.y,-v.z);  return this;  }
   public final MuV3Di setByXNeg(ImuV3Di v) {  setBy(-v.x,v.y,v.z);  return this;  }
   public final MuV3Di setByYNeg(ImuV3Di v) {  setBy(v.x,-v.y,v.z);  return this;  }
   public final MuV3Di setByZNeg(ImuV3Di v) {  setBy(v.x,v.y,-v.z);  return this;  }
   
//I  public final ImuV3Di smul(int k) {  ...  }   
   public final MuV3Di mulBy(int k) {  x*=k;  y*=k;  z*=k;  return this; }
   @Deprecated
   public final MuV3Di smulBy(int k) {  x*=k;  y*=k;  z*=k;  return this; }
      public final MuV3Di mulBy(double k) {  
         return this.setBy(x*k, y*k, z*k); 
      }

      public final MuV3Di setByMul(int k, ImuV3Di v) {  
         setBy(k*v.x,k*v.y,k*v.z);  return this;  
      }
      public final MuV3Di setByMul(ImuV3Di v, int k) {  
         setBy(k*v.x,k*v.y,k*v.z);  return this;  
      }
   @Deprecated
   public final MuV3Di setBySMul(int k, ImuV3Di v) {  
      setBy(k*v.x,k*v.y,k*v.z);  return this;  
   }
   @Deprecated
   public final MuV3Di setBySMul(ImuV3Di v, int k) {  
      setBy(k*v.x,k*v.y,k*v.z);  return this;  
   } 
      public final MuV3Di addByMul(int k, ImuV3Di v) {  
	     addBy(k*v.x,k*v.y,k*v.z);  return this;  
	  }
	  public final MuV3Di addByMul(ImuV3Di v, int k) {  
	     addBy(k*v.x,k*v.y,k*v.z);  return this;  
	  }
	  public final MuV3Di subByMul(int k, ImuV3Di v) {  
	     subBy(k*v.x,k*v.y,k*v.z);  return this;  
	  }
	  public final MuV3Di subByMul(ImuV3Di v, int k) {  
	     subBy(k*v.x,k*v.y,k*v.z);  return this;  
	  }
	  
//I   public final int dotmul(ImuV3Di v2) {  ...  }
//I   public final int wedgemul(ImuV3Di v2) {  ...  }

//I   public int normSquare() {  ...   }
//I   public final double norm() {  ...  }
	  
   public final MuV3Di setByLinearCombination(
      int a1, ImuV3Di v1, int a2, ImuV3Di v2
   ){ //:  this:=(a1*v1+a2*v2)
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y, a1*v1.z+a2*v2.z);
   }
   public final MuV3Di setByLinearCombination(
      int a1, ImuV3Di v1, int a2, ImuV3Di v2, int a3, ImuV3Di v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
		 a1*v1.y+a2*v2.y+a3*v3.y,
		 a1*v1.z+a2*v2.z+a3*v3.z
      );
   }
   
// public final MuXY setByLinePoint(ImuV3Di p1, ImuV3Di p2, double t) {
// return this.setByLinearCombination(1-t, p1, t, p2);
//}
//public final MuV3Di setByMidPoint(ImuV3Di p1, ImuV3Di p2) {  
// return this.setByAdd(p1,p2).mulBy(0.5);  
//}
}
