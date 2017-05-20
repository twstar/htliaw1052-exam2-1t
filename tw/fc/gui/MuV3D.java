package tw.fc.gui;
import java.io.IOException;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;
import tw.fc.Std;
import tw.fc.EpsilonException;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
//import static java.lang.Math.round;

//**********    MuV3D.java    *********************
//
//  floating-point 3D vector
//

// Note: 本系統以row vector為主, 這比較接近高中幾何學.

public class MuV3D extends ImuV3D
    implements SetableI<ImuV3D>, ScannableI // SetableI
{
   //I   double x, y, z;
   //-------------------------------------------------

   //[ default ctor, a zero matrix
   public MuV3D() {  super(); }
   
   public MuV3D(double d1, double d2, double d3) {  
      super(d1,d2,d3); 
   }

   //[ copy-ctor
   public MuV3D(ImuV3D src)  { super(src); }

   //[ cast-ctor
   public MuV3D(ImuV3Df src) { super(src); }

   //[ cast-ctor
   public MuV3D(ImuV3Di src) {  super(src);  }

   //[ convert-ctor
   public MuV3D(ImuP3D src) {  super(src); } //: 球座標轉正交座標
   //public   MuV3D(ImuV3Di src) { super(src); }

//--------------------------------------------------
//I   public MuV3Df toV3Df()
//I   public MuV3Di toV3Di()

//I   public String toString() { return "(" + x + ", " + y + ", " + z + ")" ;  }
//I   public final boolean equals(MuV3D v2) { return (x==v2.x && y==v2.y && z==v2.z);  }
//I   public final boolean equals(MuV3D v2, double epsilon) {
//I      return (abs(x-v2.x)+abs(y-v2.y)+abs(z-v2.z)<epsilon) ;
//I   }
//I   public final DuplicableI duplicate() {  ...  }

//I   public final double x() {  return x; }
//I   public final double y() {  return y; }
//I   public final double z() {  return z; }
//I   public final double comp(int axis) {  

   public final MuV3D setBy(ImuV3D src) {
      if(src==null) throw new IllegalArgumentException("\n... null vector\n");
      this.x=src.x;  this.y=src.y;  this.z=src.z; return this;
   }

   public final MuV3D setBy(ImuV3Df src) {
      if(src==null) throw new IllegalArgumentException("\n... null vector\n");
      this.x=src.x;  this.y=src.y;  this.z=src.z;  return this;
   }

   //[ newSys: the new cordSys described by old cordSys,
   //[ cordNew: the new cordinate of obj
   //[ find the old coordinate of obj, then set into this
   public final MuV3D setByOldCord(ImuAr3D newSys, ImuV3D cordNew) {
   // Let newSys==new ImuAr3D(b1',b2',b3',c')
   // oldCord = (x,y,z) = x'b1'+y'b2'+z'b3'+c'
   //         = (x',y',z') * Mtx(b1',b2',b3') + c';
      return this.setBy(cordNew).mulBy(newSys.mtx())
                                .addBy(newSys.org());
   }

   //[ set by polar coordinate w.r.t standard system
   public final MuV3D setBy(ImuP3D src) {
      return this.setByPolar(src.radius, src.longitude, src.latitude);
   }
   //[ set by polar coordinate w.r.t standard system
   public final MuV3D setBy(ImuP3Df src) {
      return this.setByPolar(src.radius, src.longitude, src.latitude);
   }

   //[ set by polar coordinate w.r.t localSys
   public final MuV3D setBy(ImuAr3D localSys, ImuP3D objPolar) {
      final ImuV3D cordNew=new ImuV3D(objPolar);
      this.setByOldCord(localSys, cordNew);
      return this;
   }

   //>>>> 待調
   //[ set by polar coordinate w.r.t localSys
   public final MuV3D setBy(ImuAr3D localSys, ImuP3Df src) {
      return this.setByPolar(localSys, src.radius, src.longitude, src.latitude);
   }

   public final MuV3D setBy(double xx, double yy, double zz) {
      x=xx;  y=yy;  z=zz;  return this;
   }

   public final void xSetBy(double val) {  x=val;  }  //: 原setX
   public final void ySetBy(double val) {  y=val;  }  //: 原setY
   public final void zSetBy(double val) {  z=val;  }  //: 原setZ
   //[ A5.039L.K add for iterating
   public final void compSetBy(int axis, double val) { 
      switch(axis) {
      case 0:  x=val;   break;
      case 1:  y=val;   break;
      case 2:  z=val;   break;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }


//   public final SetableI setBy(DuplicableI src) {
//      return setBy((ImuV3D)src);
//   }

   public final MuV3D setByPolar(
      double r, double lon, double lat
   ) {
      //[ from center to surface of a unit ball w.r.t. standard global system
      this.setBy(r*cos(lat)*cos(lon), r*cos(lat)*sin(lon),r*sin(lat));
      return this;
   }
   public final MuV3D setByLonLat(double lon, double lat) {
      //[ from center to surface of a unit ball w.r.t. global world syste,
      return this.setByPolar(1.0, lon, lat);
   }
   public final MuV3D setByPolar(
      ImuV3D axis1, ImuV3D axis2, ImuV3D axis3, 
      double r, double lon, double lat 
   ) { //: 原點自行 addBy即可
      //[ a ball located at ImuV3D.ZERO w.r.t. axes
      this.setByMul(axis1,r*cos(lat)*cos(lon))
          .addByMul(axis2,r*cos(lat)*sin(lon))
          .addByMul(axis3,r*sin(lat));
      return this;
   }
   public final MuV3D setByPolar(
      ImuM3D axes, double r, double lon, double lat 
   ) {
      //[ a ball located at ImuV3D.ZERO w.r.t. axes
      return this.setByPolar(
                axes.row1(),axes.row2(),axes.row3(), r, lon, lat
             );
   }
   public final MuV3D setByPolar(
      ImuAr3D localSys, double r, double lon, double lat 
   ) {
      //[ a ball w.r.t. local system
      this.setBy(localSys.org())
          .addByMul(localSys.axis1(),r*cos(lat)*cos(lon))
          .addByMul(localSys.axis2(),r*cos(lat)*sin(lon))
          .addByMul(localSys.axis3(),r*sin(lat));
      return this;
   }

   public void scanFrom(TxIStream iii) throws IOException {
         iii.skipWS().expect('(').skipWS();
         x=iii.get_double();
         iii.skipWS().expect(',').skipWS();
         y=iii.get_double();
         iii.skipWS().expect(',').skipWS();
         z=iii.get_double();
         iii.skipWS().expect(')');
   }

   public static MuV3D parseV3D(String s) {
//      try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuV3D x=new MuV3D();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }

//--------

//I  public final ImuV3D add(ImuV3D v2) {  ...  }
//I  public final ImuV3D sub(ImuV3D v2) {  ...  }

   public final MuV3D addBy(ImuV3D v2) {  x+=v2.x;  y+=v2.y;  z+=v2.z;  return this; }
   public final MuV3D subBy(ImuV3D v2) {  x-=v2.x;  y-=v2.y;  z-=v2.z;  return this; }

   public final MuV3D addBy(ImuV3Df v2) {  x+=v2.x;  y+=v2.y;  z+=v2.z;  return this; }
   public final MuV3D subBy(ImuV3Df v2) {  x-=v2.x;  y-=v2.y;  z-=v2.z;  return this; }

//   public final MuV3D addBy(ImuV3Di v2) {  x+=v2.x;  y+=v2.y;  z+=v2.z;  return this; }
//   public final MuV3D subBy(ImuV3Di v2) {  x-=v2.x;  y-=v2.y;  z-=v2.z;  return this; }


   public final MuV3D addBy(double dx, double dy, double dz) {
      x+=dx;  y+=dy;  z+=dz;  return this;
   }
// 不必寫 addBy(float, float, float)
//     及 addBy(int, int, int)

   public final MuV3D subBy(double dx, double dy, double dz) {
      x-=dx;  y-=dy;  z-=dz;  return this;
   }

   public final MuV3D setByAdd(ImuV3D v1, ImuV3D v2) {
   // this.setBy(v1).addBy(v2);  return this;
           //: Terrable Bug!! 若(this==v2)就會錯掉
      this.setBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }

   public final MuV3D setBySub(ImuV3D v1, ImuV3D v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }

   public final MuV3D addByAdd(ImuV3D v1, ImuV3D v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }

   public final MuV3D addBySub(ImuV3D v1, ImuV3D v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }

   public final MuV3D subByAdd(ImuV3D v1, ImuV3D v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }

   public final MuV3D subBySub(ImuV3D v1, ImuV3D v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }

   public final MuV3D xAddBy(double dx) {  x+=dx;  return this; }
   public final MuV3D xSubBy(double dx) {  x-=dx;  return this; }

   public final MuV3D yAddBy(double dy) {  y+=dy;  return this; }
   public final MuV3D ySubBy(double dy) {  y-=dy;  return this; }


   public final MuV3D zAddBy(double dz) {  z+=dz;  return this; }
   public final MuV3D zSubBy(double dz) {  z-=dz;  return this; }

//I  public final ImuV3D neg() {  ... }

   public final MuV3D negate() {  x=-x; y=-y;  z=-z; return this;  }

   public final MuV3D xNegate() {  x=-x;  return this;  }
   public final MuV3D yNegate() {  y=-y;  return this;  }
   public final MuV3D zNegate() {  z=-z; return this;  }

   public final MuV3D xyNegate() {  x=-x; y=-y; return this;  }
   public final MuV3D yzNegate() {  y=-y;  z=-z; return this;  }
   public final MuV3D xzNegate() {  x=-x;  z=-z; return this;  }
   public final MuV3D zxNegate() {  x=-x;  z=-z; return this;  }

   public final MuV3D setByNeg(ImuV3D v) {
      this.setBy(-v.x, -v.y, -v.z);  return this;
   }

// 不必有addByNeg, 用subBy即可

   public final MuV3D setByXNeg(ImuV3D v) {
      this.setBy(-v.x, v.y, v.z);  return this;
   }

   public final MuV3D setByYNeg(ImuV3D v) {
      this.setBy(v.x, -v.y, v.z);  return this;
   }

   public final MuV3D setByZNeg(ImuV3D v) {
      this.setBy(v.x, v.y, -v.z);  return this;
   }

   public final MuV3D setByXYNeg(ImuV3D v) {
      this.setBy(-v.x, -v.y, v.z);  return this;
   }

   public final MuV3D setByYZNeg(ImuV3D v) {
      this.setBy(v.x, -v.y, -v.z);  return this;
   }

   public final MuV3D setByXZNeg(ImuV3D v) {
      this.setBy(-v.x, v.y, -v.z);  return this;
   }

   public final MuV3D setByZXNeg(ImuV3D v) {
      this.setBy(-v.x, v.y, -v.z);  return this;
   }

//I   public final MuV3D mul(double k) {  ...  }

   public final MuV3D mulBy(double k) {  x*=k;  y*=k;  z*=k;  return this; }

@Deprecated
public final MuV3D sMulBy(double k) {  x*=k;  y*=k;  z*=k;  return this; }

@Deprecated
public final MuV3D smulBy(double k) {  x*=k;  y*=k;  z*=k;  return this; }

   public final MuV3D divBy(double k) throws EpsilonException {   
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      x/=k;  y/=k;  z/=k;  return this;
   }

   public final MuV3D setByMul(double k, ImuV3D v) {
      this.setBy(k*v.x, k*v.y, k*v.z);  return this;
   }

   public final MuV3D setByMul(ImuV3D v, double k) {
      this.setBy(k*v.x, k*v.y, k*v.z);  return this;
   }

@Deprecated
public final MuV3D setBySMul(double k, ImuV3D v) {
      this.setBy(k*v.x, k*v.y, k*v.z);  return this;
}

@Deprecated
public final MuV3D setBySMul(ImuV3D v, double k) {
      this.setBy(k*v.x, k*v.y, k*v.z);  return this;
}

   public final MuV3D setByDiv(ImuV3D v, double k) throws EpsilonException {   
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      this.setBy(v.x/k, v.y/k, v.z/k);  return this;
   }

   public final MuV3D addByMul(double k, ImuV3D v) {
      this.addBy(k*v.x, k*v.y, k*v.z);  return this;
   }

   public final MuV3D addByMul(ImuV3D v, double k) {
      this.addBy(k*v.x, k*v.y, k*v.z);  return this;
   }

   public final MuV3D addByDiv(ImuV3D v, double k) throws EpsilonException {   
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      this.addBy(v.x/k, v.y/k, v.z/k);  return this;
   }

@Deprecated
public final MuV3D addBySMul(double k, ImuV3D v) {
      this.addBy(k*v.x, k*v.y, k*v.z);  return this;
}

@Deprecated
public final MuV3D addBySMul(ImuV3D v, double k) {
      this.addBy(k*v.x, k*v.y, k*v.z);  return this;
}

   public final MuV3D subByMul(double k, ImuV3D v) {
      this.subBy(k*v.x, k*v.y, k*v.z);  return this;
   }

   public final MuV3D subByMul(ImuV3D v, double k) {
      this.subBy(k*v.x, k*v.y, k*v.z);  return this;
   }

   public final MuV3D subByDiv(ImuV3D v, double k) throws EpsilonException {   
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException("divided by epsilonSq");
      this.subBy(v.x/k, v.y/k, v.z/k);  return this;
   }

@Deprecated
public final MuV3D subBySMul(double k, ImuV3D v) {
      this.subBy(k*v.x, k*v.y, k*v.z);  return this;
}

@Deprecated
public final MuV3D subBySMul(ImuV3D v, double k) {
      this.subBy(k*v.x, k*v.y, k*v.z);  return this;
}

   //[ ---- 矩陣
   public final MuV3D mulBy(ImuM3D m) { //  this*=m;
      this.setBy(
         this.x*m._r1.x+this.y*m._r2.x+this.z*m._r3.x,
         this.x*m._r1.y+this.y*m._r2.y+this.z*m._r3.y,
         this.x*m._r1.z+this.y*m._r2.z+this.z*m._r3.z
//         this.x*m._11+this.y*m._21+this.z*m._31,
//         this.x*m._12+this.y*m._22+this.z*m._32,
//         this.x*m._13+this.y*m._23+this.z*m._33
      );
      return this;
   }
   public final MuV3D swapMulBy(ImuM3D m) {  //  this:= m*this
      this.setBy(
         m._r1.x*this.x+m._r1.y*this.y+m._r1.z*this.z,
         m._r2.x*this.x+m._r2.y*this.y+m._r2.z*this.z,
         m._r3.x*this.x+m._r3.y*this.y+m._r3.z*this.z
//         m._11*this.x+m._12*this.y+m._13*this.z,
//         m._21*this.x+m._22*this.y+m._23*this.z,
//         m._31*this.x+m._32*this.y+m._33*this.z
      );
      return this;
   }

   //  m*=v 無意義: type mismatch
   public final MuV3D rMulBy(ImuM3D m) {  // this*=m
      return this.mulBy(m);
   }
   public final MuV3D lMulBy(ImuM3D m) {  //  this:= m*this
      return this.swapMulBy(m);
   }

   public final MuV3D setByMul(ImuV3D v, ImuM3D m) {
      return this.setBy(
         v.x*m._r1.x + v.y*m._r2.x + v.z*m._r3.x,
         v.x*m._r1.y + v.y*m._r2.y + v.z*m._r3.y,
         v.x*m._r1.z + v.y*m._r2.z + v.z*m._r3.z
//         v.x*m._11 + v.y*m._21 + v.z*m._31,
//         v.x*m._12 + v.y*m._22 + v.z*m._32,
//         v.x*m._13 + v.y*m._23 + v.z*m._33
      );
   }

   public final MuV3D setByMul(ImuM3D m, ImuV3D v) {
      this.setBy(
         m._r1.x*v.x+m._r1.y*v.y+m._r1.z*v.z,
         m._r2.x*v.x+m._r2.y*v.y+m._r2.z*v.z,
         m._r3.x*v.x+m._r3.y*v.y+m._r3.z*v.z
//         m._11*v.x+m._12*v.y+m._13*v.z,
//         m._21*v.x+m._22*v.y+m._23*v.z,
//         m._31*v.x+m._32*v.y+m._33*v.z
      );
      return this;
   }

   public final MuV3D addByMul(ImuV3D v, ImuM3D m) {
      return this.addBy(
         v.x*m._r1.x + v.y*m._r2.x + v.z*m._r3.x,
         v.x*m._r1.y + v.y*m._r2.y + v.z*m._r3.y,
         v.x*m._r1.z + v.y*m._r2.z + v.z*m._r3.z
//         v.x*m._11 + v.y*m._21 + v.z*m._31,
//         v.x*m._12 + v.y*m._22 + v.z*m._32,
//         v.x*m._13 + v.y*m._23 + v.z*m._33
      );
   }

   public final MuV3D addByMul(ImuM3D m, ImuV3D v) {
      this.addBy(
         m._r1.x*v.x+m._r1.y*v.y+m._r1.z*v.z,
         m._r2.x*v.x+m._r2.y*v.y+m._r2.z*v.z,
         m._r3.x*v.x+m._r3.y*v.y+m._r3.z*v.z
//         m._11*v.x+m._12*v.y+m._13*v.z,
//         m._21*v.x+m._22*v.y+m._23*v.z,
//         m._31*v.x+m._32*v.y+m._33*v.z
      );
      return this;
   }

   public final MuV3D subByMul(ImuV3D v, ImuM3D m) {
      return this.subBy(
         v.x*m._r1.x + v.y*m._r2.x + v.z*m._r3.x,
         v.x*m._r1.y + v.y*m._r2.y + v.z*m._r3.y,
         v.x*m._r1.z + v.y*m._r2.z + v.z*m._r3.z
//         v.x*m._11 + v.y*m._21 + v.z*m._31,
//         v.x*m._12 + v.y*m._22 + v.z*m._32,
//         v.x*m._13 + v.y*m._23 + v.z*m._33
      );
   }

   public final MuV3D subByMul(ImuM3D m, ImuV3D v) {
      this.subBy(
         m._r1.x*v.x+m._r1.y*v.y+m._r1.z*v.z,
         m._r2.x*v.x+m._r2.y*v.y+m._r2.z*v.z,
         m._r3.x*v.x+m._r3.y*v.y+m._r3.z*v.z
//         m._11*v.x+m._12*v.y+m._13*v.z,
//         m._21*v.x+m._22*v.y+m._23*v.z,
//         m._31*v.x+m._32*v.y+m._33*v.z
      );
      return this;
   }

//I  public final double dotMul(ImuV3D v2) {  ... }
//I  public final ImuV3D crossMul(ImuV3D v2) {  ... }

   public final MuV3D crossBy(ImuV3D v2) {  // this:= (this*v2)
      this.setBy(
         this.y*v2.z-this.z*v2.y,
         this.z*v2.x-this.x*v2.z,
         this.x*v2.y-this.y*v2.x
      );
      return this;
   }

@Deprecated public final MuV3D crossMulBy(ImuV3D v2) {
      return this.crossBy(v2);
}

   public final MuV3D setByCross(ImuV3D v1, ImuV3D v2) {
      // this:=(v1*v2),
      // 若用 this.setBy(v1).crossBy(v2), 在this==v2時會錯掉!
      this.setBy(
         v1.y*v2.z-v1.z*v2.y,
         v1.z*v2.x-v1.x*v2.z,
         v1.x*v2.y-v1.y*v2.x
      );
      return this;
   }

   public final MuV3D addByCross(ImuV3D v1, ImuV3D v2) {
      // this+=(v1*v2)
      this.addBy(v1.y*v2.z-v1.z*v2.y,
                 v1.z*v2.x-v1.x*v2.z,
                 v1.x*v2.y-v1.y*v2.x);
      return this;
   }

   public final MuV3D subByCross(ImuV3D v1, ImuV3D v2) {
      // this-=(v1*v2)
      this.subBy(v1.y*v2.z-v1.z*v2.y,
                 v1.z*v2.x-v1.x*v2.z,
                 v1.x*v2.y-v1.y*v2.x);    return this;
   }

@Deprecated
public final MuV3D setByCrossMul(ImuV3D v1, ImuV3D v2) {
      return this.setByCross(v1,v2);
}

@Deprecated
public final MuV3D addByCrossMul(ImuV3D v1, ImuV3D v2) {
      return this.addByCross(v1, v2);
}

@Deprecated
public final MuV3D subByCrossMul(ImuV3D v1, ImuV3D v2) {
      return this.subByCross(v1, v2);
}

//I  public final double norm() {  return sqrt(x*x+y*y+z*z); }
//I  public final double normSquare() {  return (x*x+y*y+z*z); }
//I  public final double distance(ImuV3D x) {  ...  }

   public final MuV3D normalize() throws EpsilonException {   
      final double n=this.norm();
      if(n<Std.epsilon()) {  throw
         //new RuntimeException("attempt to normalize very short vecter");
         new EpsilonException(n, "attempt to normalize very short vecter");
      }
      if(n==1.0) {   return this;  }
      else {  this.divBy(n);   return this;   }
   }

   public final MuV3D setByDirection(ImuV3D v){   
   // setBy(v.drx());
      return this.setBy(v).normalize();
   }

//I  public static final ImuV2D linearCombination(
//I     double a1, ImuV2D v1, double a2, ImuV2D v2) { ... }
//I  public static final ImuV3D linePoint(
//I     ImuV3D p1, ImuV3D p2, double t) { ... }
//I  public static final ImuV3D midPoint(ImuV3D p1, ImuV3D p2) {  ...  }

   public final MuV3D setByLinearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2
   ){ //:  this:=(a1*v1+a2*v2)
   // this.setBy(v1.mul(a1)).addBy(v2.mul(a2));  return this;
              //: bug!! 若(v2==this)就會錯掉
      this.setBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }
   public final MuV3D setByLinearCombination(
      double a1, double a2, ImuV3D v1, ImuV3D v2
   ){ //:  this:=(a1*v1+a2*v2)
      this.setBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }

   public final MuV3D setByLinearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2, double a3, ImuV3D v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! 若(v3==this)就會錯掉
      this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3D setByLinearCombination(
      double a1, double a2, double a3,  
      ImuV3D v1, ImuV3D v2, ImuV3D v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! 若(v3==this)就會錯掉
      this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3D setByLinearCombination(
      double[] a, ImuV3D[] v
   ){  
      return this.setBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }

   public final MuV3D setByLinearCoef(
      ImuV3D v1, ImuV3D v2, ImuV3D v3
   ){     
   //: let  this=p*v1+q*v2+r*v3, solve and set (p,q,r) into this
      return this.solveLinearCoef(this, v1, v2, v3);
   }

   public final MuV3D addByLinearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2
   ){ //:  this+=(a1*v1+a2*v2)
      this.addBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }
   public final MuV3D addByLinearCombination(
      double a1, double a2, ImuV3D v1, ImuV3D v2
   ){ //:  this+=(a1*v1+a2*v2)
      this.addBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }

   public final MuV3D addByLinearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2, double a3, ImuV3D v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      this.addBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3D addByLinearCombination(
      double a1, double a2, double a3,   
      ImuV3D v1, ImuV3D v2, ImuV3D v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      this.addBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3D addByLinearCombination(
      double[] a, ImuV3D[] v
   ){  
      return this.addBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }

   public final MuV3D subByLinearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2
   ){ //:  this-=(a1*v1+a2*v2)
      this.subBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }
   public final MuV3D subByLinearCombination(
      double a1, double a2, ImuV3D v1, ImuV3D v2
   ){ //:  this-=(a1*v1+a2*v2)
      this.subBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }

   public final MuV3D subByLinearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2, double a3, ImuV3D v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      this.subBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3D subByLinearCombination(
      double a1, double a2, double a3, 
      ImuV3D v1, ImuV3D v2, ImuV3D v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      this.subBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3D subByLinearCombination(
      double[] a, ImuV3D[] v
   ){  
      return this.subBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }


   public final MuV3D setByLinePoint(ImuV3D v1, ImuV3D v2, double t) {
//      this.setByAdd(v1.mul(1-t), v2.mul(t));  return this;
      return this.setByLinearCombination(1-t, v1, t, v2);
   }

   public final MuV3D setByMidPoint(ImuV3D v1, ImuV3D v2) {
      //return p1.add(p2).smul(0.5);
      this.setByAdd(v1,v2).mulBy(0.5);  return this;
   }

//I   public final ImuV3D proj(ImuV3D d) {  ...   }
//I   public final ImuV3D coProj(ImuV3D d) {  ...  }
//I   public final ImuV3D refl(ImuV3D d) {  ...  }
//I   public final ImuV3D coRefl(ImuV3D d) {  ...  }

   public final MuV3D setByProj(ImuV3D src, ImuV3D drx) {
      //:  src project into drx 
      if(drx.normSq()<Std.epsilonSq()) {  this.setBy(0,0,0); }
      else {  this.setByMul(src.dot(drx)/drx.dot(drx), drx);  }
///Bug      else {  this.setByMul(src.dot(drx)/drx.dot(drx), src);  }
      return this;
   }
   public final MuV3D projBy(ImuV3D drx) {
      return this.setByProj(this,drx);
   }
   public final MuV3D addByProj(ImuV3D v, ImuV3D d) {  
      //:  project into direction d
      if(d.normSq()<Std.epsilonSq()) {  ;  }
      else {  this.addByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }
   public final MuV3D subByProj(ImuV3D v, ImuV3D d) {  
      //:  project into direction d
      if(d.normSq()<Std.epsilonSq()) {  ;  }
      else {  this.subByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }

   //public final MuV3D setByCoProj(ImuV3D src, ImuV3D d) {
   //   if(d.normSq()<Std.epsilonSq()) this.setBy(src);
   //   else this.setByLinearCombination(
   //      1.0,this, -(this.dot(d)/d.dot(d)),d
   //   );
   //   return this;
   //}
   public final MuV3D setByCoProj(ImuV3D v, ImuV3D d) {
      if(d.normSq()<Std.epsilonSq()) this.setBy(v);
      else this.setByLinearCombination(
         1.0,v, -(v.dot(d)/d.dot(d)),d
      );
      return this;
   }
   public final MuV3D coProjBy(ImuV3D drx) {
      return this.setByCoProj(this,drx);
   }
   public final MuV3D addByCoProj(ImuV3D v, ImuV3D d) {  
      if(d.normSq()<Std.epsilonSq()) this.addBy(v);
      else this.addByLinearCombination(
         1.0,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV3D subByCoProj(ImuV3D v, ImuV3D d) {  
      if(d.normSq()<Std.epsilonSq()) this.subBy(v);
      else this.subByLinearCombination(
         1.0,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }

   //public final MuV3D setByRefl(ImuV3D v, ImuV3D d) {
   //   if(d.normSq()<Std.epsilonSq()) this.setByNeg(v);
   //   else this.setByLinearCombination(
   //      2.0*(this.dot(d)/d.dot(d)),d, -1.0,this
   //   );
   //   return this;
   //}
   public final MuV3D setByRefl(ImuV3D v, ImuV3D d) {
      if(d.normSq()<Std.epsilonSq()) this.setByNeg(v);
      else this.setByLinearCombination(
         2.0*(v.dot(d)/d.dot(d)),d, -1.0,v
      );
      return this;
   }
   public final MuV3D reflBy(ImuV3D drx) {
      return this.setByRefl(this,drx);
   }
   public final MuV3D addByRefl(ImuV3D v, ImuV3D d) { 
      if(d.normSq()<Std.epsilonSq()) this.subBy(v);
      else this.addByLinearCombination(
         2.0*v.dot(d)/d.dot(d),d, -1.0,v
      );
      return this;
   }
   public final MuV3D subByRefl(ImuV3D v, ImuV3D d) { 
      if(d.normSq()<Std.epsilonSq()) this.addBy(v);
      else this.subByLinearCombination(
         2.0*v.dot(d)/d.dot(d),d, -1.0,v
      );
      return this;
   }

   //public final MuV3D setByCoRefl(ImuV3D v, ImuV3D d) {
   //   if(d.normSq()<Std.epsilonSq()) this.setBy(v);
   //   else this.setByLinearCombination(
   //      1d,this, 2d*(this.dot(d)/d.dot(d)),d
   //   );
   //   return this;
   //}
   public final MuV3D setByCoRefl(ImuV3D v, ImuV3D d) {
      if(d.normSq()<Std.epsilonSq()) this.setBy(v);
      else this.setByLinearCombination(
         1d,v, 2d*(v.dot(d)/d.dot(d)),d
      );
      return this;
   }
   public final MuV3D coReflBy(ImuV3D drx) {
      return this.setByCoRefl(this,drx);
   }
   public final MuV3D addByCoRefl(ImuV3D v, ImuV3D d) { 
      if(d.normSq()<Std.epsilonSq()) this.addBy(v);
      else this.addByLinearCombination(
         1.0,v, -2.0*v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV3D subByCoRefl(ImuV3D v, ImuV3D d) { 
      if(d.normSq()<Std.epsilonSq()) this.subBy(v);
      else this.subByLinearCombination(
         1.0,v, -2.0*v.dot(d)/d.dot(d),d
      );
      return this;
   }

   //----------------------------------
//I   public final ImuV3D rot(double angle, ImuV3D drx) { ...  }
//I   public final ImuV3D rot(double angle, ImuV3D drx, ImuV3D center) {  ... }

   //[ this 設為 src以drx為軸轉角A的結果,
   //[ 適用場合: 現成的資料是cos(A),sin(A)而非A.
   final MuV3D setByCSRot(
      ImuV3D src, ImuV3D drx, double cosA, double sinA
   ) {
   // LET U=drx/|drx|, p=(src,U)U, 
   // THEN ans=p+(cosA)(src-p)+(sinA)(U x src)
      if(src==drx) return this.setBy(src);  //: 以策安全
      final double n=drx.norm();   
      if(n<Std.epsilon()) return this.setBy(src);
      final ImuV3D U=drx.mul(1.0/n);
      final ImuV3D proj=U.mul(src.dot(U));
      return this.setByLinearCombination(
         1,proj, cosA,src.sub(proj), sinA,U.cross(src)
      );
   }
   //[ this以drx為軸轉角A 
   //[ 適用場合: 現成的資料是cos(A),sin(A)而非A.
   final MuV3D csRotBy(ImuV3D drx, double cosA, double sinA) {
      return this.setByCSRot(this,drx,cosA,sinA);
   }
   final MuV3D addByCSRot(
      ImuV3D src, ImuV3D drx, double cosA, double sinA
   ) {
      final MuV3D t=new MuV3D()
                    .setByCSRot(src, drx, cosA, sinA); 
      return this.addBy(t);
   }
   final MuV3D subByCSRot(
      ImuV3D src, ImuV3D drx, double cosA, double sinA
   ) {
      final MuV3D t=new MuV3D()
                    .setByCSRot(src, drx, cosA, sinA); 
      return this.subBy(t);
   }
   final MuV3D setByCSRot(
      ImuV3D src, ImuV3D drx, double cosA, double sinA, 
      ImuV3D center
   ) {
      //[ must! 
      if(center==this) center=new ImuV3D(center);
      if(drx==this) drx=new ImuV3D(drx);
      //]
      return this.setBy(src).subBy(center)
                 .csRotBy(drx, cosA, sinA)
                 .addBy(center);
   }
   final MuV3D csRotBy(
      ImuV3D drx, double cosA, double sinA, 
      ImuV3D center
   ) {
      //[ must! 
      if(drx==this) drx=new ImuV3D(drx);
      if(center==this) center=new ImuV3D(center);
      //]
      return this.subBy(center)
                 .csRotBy(drx, cosA, sinA)
                 .addBy(center);      
   }
   final MuV3D addByCSRot(
      ImuV3D src, ImuV3D drx, double cosA, double sinA, 
      ImuV3D center
   ) {
      final MuV3D t=new MuV3D()
                    .setByCSRot(src, drx, cosA, sinA, center); 
      return this.addBy(t);
   }
   final MuV3D subByCSRot(
      ImuV3D src, ImuV3D drx, double cosA, double sinA, 
      ImuV3D center
   ) {
      final MuV3D t=new MuV3D()
                    .setByCSRot(src, drx, cosA, sinA, center); 
      return this.subBy(t);
   }

   //----------
   //[ this 設為 src以drx為軸轉角A的結果
   public final MuV3D setByRot(ImuV3D src, ImuV3D drx, double A) {
      return this.setByCSRot(src,drx,cos(A),sin(A));
   }
@Deprecated 
   public final MuV3D setByRot(ImuV3D src, double A, ImuV3D drx) {
      return this.setByCSRot(src,drx,cos(A),sin(A));
   }
   public final MuV3D setByXRot(ImuV3D src, double angle) {
      return this.setBy( src.xRot(angle) );  
   }
   public final MuV3D setByYRot(ImuV3D src, double angle) {
      return this.setBy( src.yRot(angle) );   
   }
   public final MuV3D setByZRot(ImuV3D src, double angle) {
      return this.setBy( src.zRot(angle) ); 
   }

   //[ this以drx為軸旋轉角A
   public final MuV3D rotBy(ImuV3D drx, double A) {
      return this.setByCSRot(this,drx,cos(A),sin(A));
   }
@Deprecated
public final MuV3D rotBy(double A, ImuV3D drx) {
      return this.rotBy(drx,A); 
}
   public final MuV3D xRotBy(double angle) {
      final double cosA=cos(angle), sinA=sin(angle);
      return this.setBy(x, y*cosA-z*sinA, y*sinA+z*cosA);
   }
   public final MuV3D yRotBy(double angle) {
      final double cosA=cos(angle), sinA=sin(angle);
      return this.setBy(x*cosA+z*sinA, y, -x*sinA+z*cosA);
   }
   public final MuV3D zRotBy(double angle) {
      final double cosA=cos(angle), sinA=sin(angle);
      return this.setBy(x*cosA-y*sinA, x*sinA+y*cosA, z);
   }

   public final MuV3D addByRot(ImuV3D v, ImuV3D drx, double A) {
      return this.addByCSRot(v, drx, cos(A), sin(A));
   }
   public final MuV3D subByRot(ImuV3D v, ImuV3D drx, double A) {
      return this.subByCSRot(v, drx, cos(A), sin(A));
   }
   //---
   public final MuV3D setByRot(
      ImuV3D v, ImuV3D drx, double angle, ImuV3D center
   ){
      //[ 否則會錯
      if(center==this) center=new ImuV3D(center);
      if(drx==this) drx=new ImuV3D(drx);
      //]
      this.setBy(v).subBy(center)
          .rotBy(drx, angle).addBy(center);
      return this;
   }
@Deprecated
   public final MuV3D setByRot(
      ImuV3D v, double angle, ImuV3D drx, ImuV3D center
   ) {
      return this.setByRot(v, drx, angle, center);
   }

   public final MuV3D setByXRot(ImuV3D v, double angle, ImuV3D center) {
      this.setBy( v.xRot(angle, center) );   return this;
   }
   public final MuV3D setByYRot(ImuV3D v, double angle, ImuV3D center) {
      this.setBy( v.yRot(angle, center) );   return this;
   }
   public final MuV3D setByZRot(ImuV3D v, double angle, ImuV3D center) {
      this.setBy( v.zRot(angle, center) );   return this;
   }

   public final MuV3D rotBy(ImuV3D drx, double angle, ImuV3D center) {
      if(this==center || this==drx) return this;
                          //: 否則就會錯掉
      this.subBy(center).rotBy(drx,angle).addBy(center);
      return this;
   }
@Deprecated
   public final MuV3D rotBy(double angle, ImuV3D drx, ImuV3D center) {
      return this.rotBy(drx, angle, center);
   }
   public final MuV3D xRotBy(double angle, ImuV3D center) {
      if(this==center) return this; //: 否則就會錯掉
      return this.subBy(center).xRotBy(angle).addBy(center);
   }
   public final MuV3D yRotBy(double angle, ImuV3D center) {
      if(this==center) return this; //: 否則就會錯掉
      return this.subBy(center).yRotBy(angle).addBy(center);
   }
   public final MuV3D zRotBy(double angle, ImuV3D center) {
      if(this==center) return this; //: 否則就會錯掉
      return this.subBy(center).zRotBy(angle).addBy(center);
   }

   public final MuV3D addByRot(
      ImuV3D src, ImuV3D drx, double A, ImuV3D center
   ) {
      final MuV3D t=new MuV3D()
                      .setByRot(src, drx, A, center);
      return this.addBy(t);
   }
   public final MuV3D subByRot(
      ImuV3D src, ImuV3D drx, double A, ImuV3D center
   ) {
      final MuV3D t=new MuV3D()
                      .setByRot(src, drx, A, center);
      return this.subBy(t);
   }

   //[ 適用場合: 客戶現成的資料是sin(A)而非A
   //[ -PI/2 <= A <= +PI/2, -1 <= sin(A) <= +1  
   public final MuV3D setBySinRot(ImuV3D src, ImuV3D drx, double sinA) {
      return setByCSRot(src, drx, sqrt(1-sinA*sinA), sinA);
   }
   public final MuV3D sinRotBy(ImuV3D drx, double sinA) { 
      return csRotBy(drx, sqrt(1-sinA*sinA), sinA);      
   }
   public final MuV3D addBySinRot(ImuV3D src, ImuV3D drx, double sinA) {
      return addByCSRot(src, drx, sqrt(1-sinA*sinA), sinA);
   }
   public final MuV3D subBySinRot(ImuV3D src, ImuV3D drx, double sinA) {
      return subByCSRot(src, drx, sqrt(1-sinA*sinA), sinA);
   }

   public final MuV3D setBySinRot(
      ImuV3D v, ImuV3D drx, double sinA, ImuV3D center
   ) {
      return this.setByCSRot(v, drx, sqrt(1-sinA*sinA), sinA, center);
   }
   public final MuV3D sinRotBy(
      ImuV3D drx, double sinA, ImuV3D center
   ) {
      return this.csRotBy(drx, sqrt(1-sinA*sinA), sinA, center);
   }
   public final MuV3D addBySinRot(
      ImuV3D v, ImuV3D drx, double sinA, ImuV3D center
   ) {
      final MuV3D t=new MuV3D()
                    .setBySinRot(v, drx, sinA, center);
      return this.addBy(t);
   }
   public final MuV3D subBySinRot(
      ImuV3D v, ImuV3D drx, double sinA, ImuV3D center
   ) {
      final MuV3D t=new MuV3D()
                    .setBySinRot(v, drx, sinA, center);
      return this.subBy(t);
   }

   //-------------------------------
//I   public static double triple(MuV3D v1, MuV3D v2, MuV3D v3) {  ... }
//I   public ImuV3D linear_coef(ImuV3D v1, ImuV3D v2, ImuV3D v3) {  ...  }

   public final MuV3D solveLinearCoef(
      ImuV3D v, ImuV3D v1, ImuV3D v2, ImuV3D v3
   ) throws EpsilonException {
      //  LET  v=p*v1+q*v2+r*v3, D=[v1,v2,v3] 
      //  THEN: p=[v,v2,v3]/D, q=[v1,v,v3]/D, r=[v1,v2,v]/D
      //  this.setBy(p,q,r)
      final double D=triple(v1,v2,v3);
      if(Math.abs(D)<Std.epsilonCb()) throw 
         //new IllegalArgumentException("\n v1, v2, v3 are near coplane");
         new EpsilonException(D, "\n v1, v2, v3 are near coplane");
      return this.setBy(triple(v,v2,v3)/D, triple(v1,v,v3), triple(v1,v2,v));
   }


}
