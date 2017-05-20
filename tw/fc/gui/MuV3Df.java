package tw.fc.gui;
import java.io.IOException;
import tw.fc.Std;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
//import static java.lang.Math.round;

//**********    MuV3Df.java    *********************
//
//  floating-point 3D vector
//

// Note: 本系統以row vector為主, 這比較接近高中幾何學.

public class MuV3Df extends ImuV3Df
    implements SetableI<ImuV3Df>, ScannableI // SetableI
{
   //I   float x, y, z;

   //-------------------------------------------------
   public     MuV3Df() {  super(); }
   public     MuV3Df(float xx, float yy, float zz) {  super(xx,yy,zz); }
   public     MuV3Df(ImuV3D src)  { super(src); }
   public     MuV3Df(ImuV3Df src) {  super(src); }
   public     MuV3Df(ImuP3Df src) {  super(src); } //: 球座標轉正交座標
   //public   MuV3Df(ImuV3Di src) { super(src); }

//--------------------------------------------------
//   public MuV3Df toV3Di() {
//      return new MuV3Di(round(x), round(y), round(z) );
//   }

//I   public String toString() { return "(" + x + ", " + y + ", " + z + ")" ;  }
//I   public final boolean equals(MuV3Df v2) { return (x==v2.x && y==v2.y && z==v2.z);  }
//I   public final boolean equals(MuV3Df v2, float epsilon) {
//I      return (abs(x-v2.x)+abs(y-v2.y)+abs(z-v2.z)<epsilon) ;
//I   }
//I   public final DuplicableI duplicate() {  ...  }

//I   public final float x() {  return x; }
//I   public final float y() {  return y; }
//I   public final float z() {  return z; }

   public final MuV3Df setBy(ImuV3D src) {
      x=(float)src.x;  y=(float)src.y;  z=(float)src.z; return this;
   }
   public final MuV3Df setBy(ImuV3Df src) {
      x=src.x;  y=src.y;  z=src.z; return this;
   }


   //[ this: the new cordSys described by old cordSys,
   //[ cord: the new cordinate of obj
   //[ find the old coordinate of obj
   public final MuV3Df setByOldCord(ImuAr3Df newSys, ImuV3Df cordNew) {
   // Let this==new ImuAr3D(b1',b2',b3',c')
   // oldCord = (x,y,z) = x'b1'+y'b2'+z'b3'+c'
   //         = (x',y',z') * Mtx(b1',b2',b3') + c';
      return this.setBy(cordNew).mulBy(newSys.mtx()).addBy(newSys.vec());
   }

   //[ set by polar coordinate w.r.t standard system
   public final MuV3Df setBy(ImuP3Df src) {
      return this.setByPolar(src.radius, src.longitude, src.latitude);
   }
   //[ set by polar coordinate w.r.t standard system
   public final MuV3Df setBy(ImuP3D src) {
      return this.setByPolar(
                (float)src.radius, (float)src.longitude, (float)src.latitude
             );
   }

   //[ set by polar coordinate w.r.t localSys
   public final MuV3Df setBy(ImuAr3Df localSys, ImuP3Df objPolar) {
      final ImuV3Df cordNew=new ImuV3Df(objPolar);
      this.setByOldCord(localSys, cordNew);
      return this;
   }

   //>>>> 待調
   //[ set by polar coordinate w.r.t localSys
   public final MuV3Df setBy(ImuAr3Df localSys, ImuP3D src) {
      return this.setByPolar(
                localSys,
                (float)src.radius, (float)src.longitude, (float)src.latitude
             );
   }


   public final MuV3Df setBy(float xx, float yy, float zz) {
      x=xx;  y=yy;  z=zz;  return this;
   }

   public final void xSetBy(float v) {  x=v;  }  //: 原setX
   public final void ySetBy(float v) {  y=v;  }  //: 原setY
   public final void zSetBy(float v) {  z=v;  }  //: 原setZ
   //[ A5.039L.K add for iterating
   public final void compSetBy(int axis, float val) { 
      switch(axis) {
      case 0:  x=val;   break;
      case 1:  y=val;   break;
      case 2:  z=val;   break;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }


/////   public final SetableI setBy(DuplicableI src) {
/////      return setBy((ImuV3Df)src);
/////   }

   public final MuV3Df setByPolar(
      float r, float lon, float lat
   ) {
      //[ from center to surface of a unit ball w.r.t. standard global system
      this.setBy(
         (float)(r*cos(lat)*cos(lon)),
         (float)(r*cos(lat)*sin(lon)),
         (float)(r*sin(lat))
      );
      return this;
   }
   public final MuV3Df setByLonLat(float lon, float lat) {
      //[ from center to surface of a unit ball w.r.t. global world syste,
      return this.setByPolar(1.0f, lon, lat);
   }

   public final MuV3Df setByPolar(
      ImuAr3Df localSys, float r, float lon, float lat
   ) {
      //[ from center to surface of a unit ball w.r.t. local system
      this.setBy(localSys.vec())
          .addByMul(localSys.mtx().row1(), (float)(r*cos(lat)*cos(lon)))
          .addByMul(localSys.mtx().row2(), (float)(r*cos(lat)*sin(lon)))
          .addByMul(localSys.mtx().row3(), (float)(r*sin(lat)));
      return this;
   }


/*>>> 待刪
   public final MuV3Df setBy(ImuP3D src) {
      x=(float)(src.radius*cos(src.latitude)*cos(src.longitude));
      y=(float)(src.radius*cos(src.latitude)*sin(src.longitude));
      z=(float)(src.radius*sin(src.latitude));
      return this;
   }
   public final MuV3Df setBy(ImuP3Df src) {
      x=(float)(src.radius*cos(src.latitude)*cos(src.longitude));
      y=(float)(src.radius*cos(src.latitude)*sin(src.longitude));
      z=(float)(src.radius*sin(src.latitude));
      return this;
   }
   public final MuV3Df setByLonLat(float lon, float lat) {
      //[ from center to surface of a unit ball
      x=(float)(cos(lat)*cos(lon));
      y=(float)(cos(lat)*sin(lon));
      z=(float)(sin(lat));
      return this;
   }
*/

   public void scanFrom(TxIStream iii) throws IOException {
         iii.skipWS().expect('(').skipWS();
         x=iii.get_float();
         iii.skipWS().expect(',').skipWS();
         y=iii.get_float();
         iii.skipWS().expect(',').skipWS();
         z=iii.get_float();
         iii.skipWS().expect(')');
   }
   public static MuV3Df parseV3D(String s) {
//      try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuV3Df x=new MuV3Df();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }

//--------

//I  public final ImuV3Df add(ImuV3Df v2) {  ...  }
//I  public final ImuV3Df sub(ImuV3Df v2) {  ...  }

   public final MuV3Df addBy(ImuV3D v2) {
      x+=(float)v2.x;  y+=(float)v2.y;  z+=(float)v2.z;  return this;
   }
   public final MuV3Df subBy(ImuV3D v2) {
      x-=(float)v2.x;  y-=(float)v2.y;  z-=(float)v2.z;  return this;
   }

   public final MuV3Df addBy(ImuV3Df v2) {  x+=v2.x;  y+=v2.y;  z+=v2.z;  return this; }
   public final MuV3Df subBy(ImuV3Df v2) {  x-=v2.x;  y-=v2.y;  z-=v2.z;  return this; }

//[ 不implicitly縮小
//   public final MuV3Df addBy(ImuV3D v2) {  x+=(float)v2.x;  y+=(float)v2.y;  z+=(float)v2.z;  return this; }
//   public final MuV3Df subBy(ImuV3D v2) {  x-=(float)v2.x;  y-=(float)v2.y;  z-=(float)v2.z;  return this; }
   public final MuV3Df addBy(float dx, float dy, float dz) {
      x+=dx;  y+=dy;  z+=dz;  return this;
   }
   public final MuV3Df subBy(float dx, float dy, float dz) {
      x-=dx;  y-=dy;  z-=dz;  return this;
   }
   public final MuV3Df setByAdd(ImuV3Df v1, ImuV3Df v2) {
   // this.setBy(v1).addBy(v2);  return this;
           //: Bug= 若(this==v2)就會錯掉
      this.setBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }
   public final MuV3Df setBySub(ImuV3Df v1, ImuV3Df v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }
   public final MuV3Df addByAdd(ImuV3Df v1, ImuV3Df v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }
   public final MuV3Df addBySub(ImuV3Df v1, ImuV3Df v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }
   public final MuV3Df subByAdd(ImuV3Df v1, ImuV3Df v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);  return this;
   }
   public final MuV3Df subBySub(ImuV3Df v1, ImuV3Df v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);  return this;
   }

   public final MuV3Df xAddBy(float dx) {  x+=dx;  return this; }
   public final MuV3Df xSubBy(float dx) {  x-=dx;  return this; }

   public final MuV3Df yAddBy(float dy) {  y+=dy;  return this; }
   public final MuV3Df ySubBy(float dy) {  y-=dy;  return this; }

   public final MuV3Df zAddBy(float dz) {  z+=dz;  return this; }
   public final MuV3Df zSubBy(float dz) {  z-=dz;  return this; }

//I  public final ImuV3Df neg() {  ... }
   public final MuV3Df negate() {  x=-x; y=-y;  z=-z; return this;  }

   public final MuV3Df xNegate() {  x=-x;  return this;  }
   public final MuV3Df yNegate() {  y=-y;  return this;  }
   public final MuV3Df zNegate() {  z=-z; return this;  }

   public final MuV3Df xyNegate() {  x=-x; y=-y; return this;  }
   public final MuV3Df yzNegate() {  y=-y;  z=-z; return this;  }
   public final MuV3Df xzNegate() {  x=-x;  z=-z; return this;  }
   public final MuV3Df zxNegate() {  x=-x;  z=-z; return this;  }

   public final MuV3Df setByNeg(ImuV3Df v) {
      this.setBy(-v.x, -v.y, -v.z);  return this;
   }

   public final MuV3Df setByXNeg(ImuV3Df v) {
      this.setBy(-v.x, v.y, v.z);  return this;
   }
   public final MuV3Df setByYNeg(ImuV3Df v) {
      this.setBy(v.x, -v.y, v.z);  return this;
   }
   public final MuV3Df setByZNeg(ImuV3Df v) {
      this.setBy(v.x, v.y, -v.z);  return this;
   }

   public final MuV3Df setByXYNeg(ImuV3Df v) {
      this.setBy(-v.x, -v.y, v.z);  return this;
   }
   public final MuV3Df setByYZNeg(ImuV3Df v) {
      this.setBy(v.x, -v.y, -v.z);  return this;
   }
   public final MuV3Df setByXZNeg(ImuV3Df v) {
      this.setBy(-v.x, v.y, -v.z);  return this;
   }
   public final MuV3Df setByZXNeg(ImuV3Df v) {
      this.setBy(-v.x, v.y, -v.z);  return this;
   }

//I   public final MuV3Df mul(float k) {  ...  }
   public final MuV3Df mulBy(float k) {  x*=k;  y*=k;  z*=k;  return this; }
   public final MuV3Df divBy(float k) {
      if(k==0f) throw new IllegalArgumentException("divided by 0");
      x/=k;  y/=k;  z/=k;  return this;
   }
   public final MuV3Df setByMul(float k, ImuV3Df v) {
      this.setBy(k*v.x, k*v.y, k*v.z);  return this;
   }
   public final MuV3Df setByMul(ImuV3Df v, float k) {
      this.setBy(k*v.x, k*v.y, k*v.z);  return this;
   }
   public final MuV3Df setByDiv(ImuV3Df v, float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      this.setBy(v.x/k, v.y/k, v.z/k);  return this;
   }

   public final MuV3Df addByMul(float k, ImuV3Df v) {
      this.addBy(k*v.x, k*v.y, k*v.z);  return this;
   }
   public final MuV3Df addByMul(ImuV3Df v, float k) {
      this.addBy(k*v.x, k*v.y, k*v.z);  return this;
   }
   public final MuV3Df addByDiv(ImuV3Df v, float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      this.addBy(v.x/k, v.y/k, v.z/k);  return this;
   }

   public final MuV3Df subByMul(float k, ImuV3Df v) {
      this.subBy(k*v.x, k*v.y, k*v.z);  return this;
   }
   public final MuV3Df subByMul(ImuV3Df v, float k) {
      this.subBy(k*v.x, k*v.y, k*v.z);  return this;
   }
   public final MuV3Df subByDiv(ImuV3Df v, float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      this.subBy(v.x/k, v.y/k, v.z/k);  return this;
   }

   //[ ---- 矩陣
   public final MuV3Df mulBy(ImuM3Df m) {  //  this*=m;
      final float tx=(this.x*m._11+this.y*m._21+this.z*m._31);
      final float ty=(this.x*m._12+this.y*m._22+this.z*m._32);
      final float tz=(this.x*m._13+this.y*m._23+this.z*m._33);
      this.setBy(tx, ty, tz);
      return this;
   }
   public final MuV3Df swapMulBy(ImuM3Df m) {  //  this:= m*this
      this.setBy(
         m._11*this.x+m._12*this.y+m._13*this.z,
         m._21*this.x+m._22*this.y+m._23*this.z,
         m._31*this.x+m._32*this.y+m._33*this.z
      );
      return this;
   }

   //  m*=v 無意義
   public final MuV3Df rMulBy(ImuM3Df m) {  // this*=m
      return mulBy(m);
   }
   public final MuV3Df lMulBy(ImuM3Df m) {  //  this:= m*this
      return this.swapMulBy(m);
   }

   public final MuV3Df setByMul(ImuV3Df v, ImuM3Df m) {
      return this.setBy(
         v.x*m._11 + v.y*m._21 + v.z*m._31,
         v.x*m._12 + v.y*m._22 + v.z*m._32,
         v.x*m._13 + v.y*m._23 + v.z*m._33
      );
   }
   public final MuV3Df setByMul(ImuM3Df m, ImuV3Df v) {
      this.setBy(
         m._11*v.x+m._12*v.y+m._13*v.z,
         m._21*v.x+m._22*v.y+m._23*v.z,
         m._31*v.x+m._32*v.y+m._33*v.z
      );
      return this;
   }
   public final MuV3Df addByMul(ImuV3Df v, ImuM3Df m) {
      return this.addBy(
         v.x*m._11 + v.y*m._21 + v.z*m._31,
         v.x*m._12 + v.y*m._22 + v.z*m._32,
         v.x*m._13 + v.y*m._23 + v.z*m._33
      );
   }
   public final MuV3Df addByMul(ImuM3Df m, ImuV3Df v) {
      this.addBy(
         m._11*v.x+m._12*v.y+m._13*v.z,
         m._21*v.x+m._22*v.y+m._23*v.z,
         m._31*v.x+m._32*v.y+m._33*v.z
      );
      return this;
   }

   public final MuV3Df subByMul(ImuV3Df v, ImuM3Df m) {
      return this.subBy(
         v.x*m._11 + v.y*m._21 + v.z*m._31,
         v.x*m._12 + v.y*m._22 + v.z*m._32,
         v.x*m._13 + v.y*m._23 + v.z*m._33
      );
   }
   public final MuV3Df subByMul(ImuM3Df m, ImuV3Df v) {
      this.subBy(
         m._11*v.x+m._12*v.y+m._13*v.z,
         m._21*v.x+m._22*v.y+m._23*v.z,
         m._31*v.x+m._32*v.y+m._33*v.z
      );
      return this;
   }

//I  public final float dotMul(ImuV3Df v2) {  ... }
//I  public final ImuV3Df crossMul(ImuV3Df v2) {  ... }

   public final MuV3Df crossBy(ImuV3Df v2) {
     // (*this)=(*this)*(*v2)
      final float tx=this.y*v2.z-this.z*v2.y,
                   ty=this.z*v2.x-this.x*v2.z,
                   tz=this.x*v2.y-this.y*v2.x;
      return this.setBy(tx, ty, tz);
   }

   public final MuV3Df setByCross(ImuV3Df v1, ImuV3Df v2) {
      // (*this)=(*v1)*(*v2)
      this.setBy(
         v1.y*v2.z-v1.z*v2.y,
         v1.z*v2.x-v1.x*v2.z,
         v1.x*v2.y-v1.y*v2.x
      );
      return this;
   }
   public final MuV3Df addByCross(ImuV3Df v1, ImuV3Df v2) {
      // (*this)+=((*v1)*(*v2))
      this.addBy(v1.y*v2.z-v1.z*v2.y,
                 v1.z*v2.x-v1.x*v2.z,
                 v1.x*v2.y-v1.y*v2.x);    return this;
   }
   public final MuV3Df subByCross(ImuV3Df v1, ImuV3Df v2) {
      // (*this)-=((*v1)*(*v2))
      this.subBy(v1.y*v2.z-v1.z*v2.y,
                 v1.z*v2.x-v1.x*v2.z,
                 v1.x*v2.y-v1.y*v2.x);    return this;
   }

//I  public final float norm() {  return sqrt(x*x+y*y+z*z); }
//I  public final float normSquare() {  return (x*x+y*y+z*z); }
//I  public final float distance(ImuV3Df x) {  ...  }

   public final MuV3Df normalize() {
      final float n=this.norm();
      if(n==0.0f) {  throw
           new RuntimeException("attempt to normalize zero vecter");
      }
      if(n==1.0f) {   return this;  }
      else {  this.mulBy(1f/n);   return this;   }
   }
   public final MuV3Df setByDirection(ImuV3Df v) {
   // setBy(v.direction());
      return this.setBy(v).normalize();
   }

//I  public static final ImuV2D linearCombination(
//I     float a1, ImuV2D v1, float a2, ImuV2D v2) { ... }
//I  public static final ImuV3Df linePoint(
//I     ImuV3Df p1, ImuV3Df p2, float t) { ... }
//I  public static final ImuV3Df midPoint(ImuV3Df p1, ImuV3Df p2) {  ...  }

   public final MuV3Df setByLinearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2
   ){
   // this.setBy(v1.mul(a1)).addBy(v2.mul(a2));  return this;
              //: bug!! 若(v2==this)就會錯掉
      this.setBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }
   public final MuV3Df setByLinearCombination(
      float a1, float a2, ImuV3Df v1, ImuV3Df v2
   ){ //:  this:=(a1*v1+a2*v2)
      this.setBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }

   public final MuV3Df setByLinearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2, float a3, ImuV3Df v3
   ){
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! 若(v3==this)就會錯掉
      this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3Df setByLinearCombination(
      float a1, float a2, float a3,
      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3
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

   public final MuV3Df setByLinearCoef(
      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3
   ) {  
   //: let  this=p*v1+q*v2+r*v3, solve and set (p,q,r) into this
      return this.solveLinearCoef(this, v1, v2, v3);
   }


   public final MuV3Df addByLinearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2
   ){
      this.addBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }
   public final MuV3Df addByLinearCombination(
      float a1, float a2, ImuV3Df v1, ImuV3Df v2
   ){ //:  this+=(a1*v1+a2*v2)
      this.addBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }

   public final MuV3Df addByLinearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2, float a3, ImuV3Df v3
   ){
      this.addBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3Df addByLinearCombination(
      float a1, float a2, float a3,
      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      this.addBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3Df addByLinearCombination(
      float[] a, ImuV3Df[] v
   ){
      return this.addBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }

   public final MuV3Df subByLinearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2
   ){
      this.subBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }
   public final MuV3Df subByLinearCombination(
      float a1, float a2, ImuV3Df v1, ImuV3Df v2
   ){ //:  this-=(a1*v1+a2*v2)
      this.subBy(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
      return this;
   }

   public final MuV3Df subByLinearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2, float a3, ImuV3Df v3
   ){
      this.subBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3Df subByLinearCombination(
      float a1, float a2, float a3,
      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      this.subBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
      return this;
   }
   public final MuV3Df subByLinearCombination(
      float[] a, ImuV3Df[] v
   ){
      return this.subBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }


   public final MuV3Df setByLinePoint(ImuV3Df v1, ImuV3Df v2, float t) {
//      this.setByAdd(v1.mul(1-t), v2.mul(t));  return this;
      return this.setByLinearCombination(1-t, v1, t, v2);
   }
   public final MuV3Df setByMidPoint(ImuV3Df v1, ImuV3Df v2) {
      //return p1.add(p2).smul(0.5);
      this.setByAdd(v1,v2).mulBy(0.5f);  return this;
   }

//I   public final ImuV3Df proj(ImuV3Df d) {  ...   }
//I   public final ImuV3Df coProj(ImuV3Df d) {  ...  }
//I   public final ImuV3Df refl(ImuV3Df d) {  ...  }
//I   public final ImuV3Df coRefl(ImuV3Df d) {  ...  }

   public final MuV3Df setByProj(ImuV3Df src, ImuV3Df drx) {
      //:  project into direction d
      if(drx.normSq()==0f) {  this.setBy(0,0,0); }
      else {  this.setByMul(src.dot(drx)/drx.dot(drx), drx);  }
      return this;
   }
   public final MuV3Df projBy(ImuV3Df drx) {
      return this.setByProj(this, drx);
   }
   public final MuV3Df addByProj(ImuV3Df v, ImuV3Df d) {
      //:  project into direction d
      if(d.normSq()==0f) {  ;  }
      else {  this.addByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }
   public final MuV3Df subByProj(ImuV3Df v, ImuV3Df d) {
      //:  project into direction d
      if(d.normSq()==0f) {  ;  }
      else {  this.subByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }

   //public final MuV3Df setByCoProj(ImuV3Df v, ImuV3Df d) {
   //   if(d.normSq()==0f) this.setBy(v);
   //   else this.setByLinearCombination(
   //      1f,this, -(this.dot(d)/d.dot(d)),d
   //   );
   //   return this;
   //}
   public final MuV3Df setByCoProj(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()<Std.epsilonSq()) this.setBy(v);
      else this.setByLinearCombination(
         1f,v, -(v.dot(d)/d.dot(d)),d
      );
      return this;
   }

   public final MuV3Df coProjBy(ImuV3Df drx) {
      return this.setByCoProj(this,drx);
   }
   public final MuV3Df addByCoProj(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0.0f) this.addBy(v);
      else this.addByLinearCombination(
         1.0f,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV3Df subByCoProj(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0.0f) this.subBy(v);
      else this.subByLinearCombination(
         1.0f,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }

   public final MuV3Df setByRefl(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0f) this.setByNeg(v);
      else this.setByLinearCombination(
         2f*(v.dot(d)/d.dot(d)),d, -1f,v
      );
      return this;
   }
   public final MuV3Df reflBy(ImuV3Df drx) {
      return this.setByRefl(this,drx);
   }
   public final MuV3Df addByRefl(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0.0f) this.subBy(v);
      else this.addByLinearCombination(
         2.0f*v.dot(d)/d.dot(d),d, -1.0f,v
      );
      return this;
   }
   public final MuV3Df subByRefl(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0f) this.addBy(v);
      else this.subByLinearCombination(
         2.0f*v.dot(d)/d.dot(d),d, -1.0f,v
      );
      return this;
   }

   public final MuV3Df setByCoRefl(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0f) this.setBy(v);
      else this.setByLinearCombination(
         1f,v, 2f*(v.dot(d)/d.dot(d)),d
      );
      return this;
   }
   public final MuV3Df coReflBy(ImuV3Df drx) {
      return this.setByCoRefl(this,drx);
   }
   public final MuV3Df addByCoRefl(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0.0f) this.addBy(v);
      else this.addByLinearCombination(
         1.0f,v, -2.0f*v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV3Df subByCoRefl(ImuV3Df v, ImuV3Df d) {
      if(d.normSq()==0f) this.subBy(v);
      else this.subByLinearCombination(
         1.0f,v, -2.0f*v.dot(d)/d.dot(d),d
      );
      return this;
   }

   //------------------------------------
//I   public final ImuV3Df rot(float angle, ImuV3Df direction) { ...  }
//I   public final ImuV3Df rot(float angle, ImuV3Df direction, ImuV3Df center) {  ... }

   //[ this 設為 src以drx為軸轉角A的結果,
   //[ 現成的資料是cos(A),sin(A)而非A
   final MuV3Df setByCSRot(
      ImuV3Df src, ImuV3Df drx, float cosA, float sinA
   ) {
   // LET U=drx/|drx|, p=(src,U)U,
   // THEN ans=p+(cosA)(src-p)+(sinA)(U x src)
      if(src==drx) return this.setBy(src);  //: 以策安全
      final float n=drx.norm();
      if(n==0.0f) return this.setBy(src);
      final ImuV3Df U=drx.mul(1.0f/n);
      final ImuV3Df proj=U.mul(src.dot(U));
      return this.setByLinearCombination(
         1f,proj, cosA,src.sub(proj), sinA,U.cross(src)
      );
   }
   //[ this以drx為軸轉角A
   //[ 適用場合: 現成的資料是cos(A),sin(A)而非A
   final MuV3Df csRotBy(ImuV3Df drx, float cosA, float sinA) {
      return this.setByCSRot(this,drx,cosA,sinA);
   }
   final MuV3Df addByCSRot(
      ImuV3Df src, ImuV3Df drx, float cosA, float sinA
   ) {
      final MuV3Df t=new MuV3Df()
                    .setByCSRot(src, drx, cosA, sinA);
      return this.addBy(t);
   }
   final MuV3Df subByCSRot(
      ImuV3Df src, ImuV3Df drx, float cosA, float sinA
   ) {
      final MuV3Df t=new MuV3Df()
                    .setByCSRot(src, drx, cosA, sinA);
      return this.subBy(t);
   }
   final MuV3Df setByCSRot(
      ImuV3Df src, ImuV3Df drx, float cosA, float sinA,
      ImuV3Df center
   ) {
      //[ must!
      if(center==this) center=new ImuV3Df(center);
      if(drx==this) drx=new ImuV3Df(drx);
      //]
      return this.setBy(src).subBy(center)
                 .csRotBy(drx, cosA, sinA)
                 .addBy(center);
   }
   final MuV3Df csRotBy(
      ImuV3Df drx, float cosA, float sinA,
      ImuV3Df center
   ) {
      //[ must!
      if(drx==this) drx=new ImuV3Df(drx);
      if(center==this) center=new ImuV3Df(center);
      //]
      return this.subBy(center)
                 .csRotBy(drx, cosA, sinA)
                 .addBy(center);
   }
   final MuV3Df addByCSRot(
      ImuV3Df src, ImuV3Df drx, float cosA, float sinA,
      ImuV3Df center
   ) {
      final MuV3Df t=new MuV3Df()
                    .setByCSRot(src, drx, cosA, sinA, center);
      return this.addBy(t);
   }
   final MuV3Df subByCSRot(
      ImuV3Df src, ImuV3Df drx, float cosA, float sinA,
      ImuV3Df center
   ) {
      final MuV3Df t=new MuV3Df()
                    .setByCSRot(src, drx, cosA, sinA, center);
      return this.subBy(t);
   }


//----------
   //[ this 設為 src以drx為軸轉角A的結果
   public final MuV3Df setByRot(ImuV3Df src, ImuV3Df drx, float A) {
      return this.setByCSRot(src,drx,(float)cos(A),(float)sin(A));
   }
@Deprecated
   public final MuV3Df setByRot(ImuV3Df src, float A, ImuV3Df drx) {
      return this.setByCSRot(src,drx,(float)cos(A),(float)sin(A));
   }
   public final MuV3Df setByXRot(ImuV3Df src, float angle) {
      return this.setBy( src.xRot(angle) );
   }
   public final MuV3Df setByYRot(ImuV3Df src, float angle) {
      return this.setBy( src.yRot(angle) );
   }
   public final MuV3Df setByZRot(ImuV3Df src, float angle) {
      return this.setBy( src.zRot(angle) );
   }

   //[ this 設為 src以drx為軸轉角A的結果
   public final MuV3Df rotBy(ImuV3Df drx, float A) {
      return this.setByCSRot(this,drx,(float)cos(A),(float)sin(A));
   }
@Deprecated
   public final MuV3Df rotBy(float A, ImuV3Df drx) {
      return this.rotBy(drx,A);
   }
   public final MuV3Df xRotBy(float angle) {
      final float cosA=(float)cos(angle), sinA=(float)sin(angle);
      return this.setBy(x, y*cosA-z*sinA, y*sinA+z*cosA);
   }
   public final MuV3Df yRotBy(float angle) {
      final float cosA=(float)cos(angle), sinA=(float)sin(angle);
      return this.setBy((x*cosA+z*sinA), y, (-x*sinA+z*cosA));
   }
   public final MuV3Df zRotBy(float angle) {
      final float cosA=(float)cos(angle), sinA=(float)sin(angle);
      return this.setBy((x*cosA-y*sinA), (x*sinA+y*cosA), z);
   }

   public final MuV3Df addByRot(ImuV3Df v, ImuV3Df drx, float A) {
      return this.addByCSRot(v, drx, (float)cos(A), (float)sin(A));
   }
   public final MuV3Df subByRot(ImuV3Df v, ImuV3Df drx, float A) {
      return this.subByCSRot(v, drx, (float)cos(A), (float)sin(A));
   }
   //---
   public final MuV3Df setByRot(
      ImuV3Df v, ImuV3Df drx, float angle, ImuV3Df center
   ){
      //[ 否則會錯
      if(center==this) center=new ImuV3Df(center);
      if(drx==this) drx=new ImuV3Df(drx);
      //]
      this.setBy(v).subBy(center)
          .rotBy(drx, angle).addBy(center);
      return this;
   }
@Deprecated
   public final MuV3Df setByRot(
      ImuV3Df v, float angle, ImuV3Df drx, ImuV3Df center
   ) {
      return this.setByRot(v, drx, angle, center);
   }

   public final MuV3Df setByXRot(ImuV3Df v, float angle, ImuV3Df center) {
      this.setBy( v.xRot(angle, center) );   return this;
   }
   public final MuV3Df setByYRot(ImuV3Df v, float angle, ImuV3Df center) {
      this.setBy( v.yRot(angle, center) );   return this;
   }
   public final MuV3Df setByZRot(ImuV3Df v, float angle, ImuV3Df center) {
      this.setBy( v.zRot(angle, center) );   return this;
   }

   public final MuV3Df rotBy(ImuV3Df drx, float angle, ImuV3Df center) {
      if(this==center || this==drx) return this;
                          //: 否則就會錯掉
      this.subBy(center).rotBy(drx,angle).addBy(center);
      return this;
   }
@Deprecated
   public final MuV3Df rotBy(float angle, ImuV3Df drx, ImuV3Df center) {
      return this.rotBy(drx, angle, center);
   }
   public final MuV3Df xRotBy(float angle, ImuV3Df center) {
      if(this==center) return this; //: 否則就會錯掉
      return this.subBy(center).xRotBy(angle).addBy(center);
   }
   public final MuV3Df yRotBy(float angle, ImuV3Df center) {
      if(this==center) return this; //: 否則就會錯掉
      return this.subBy(center).yRotBy(angle).addBy(center);
   }
   public final MuV3Df zRotBy(float angle, ImuV3Df center) {
      if(this==center) return this; //: 否則就會錯掉
      return this.subBy(center).zRotBy(angle).addBy(center);
   }

   public final MuV3Df addByRot(
      ImuV3Df src, ImuV3Df drx, float A, ImuV3Df center
   ) {
      final MuV3Df t=new MuV3Df()
                      .setByRot(src, drx, A, center);
      return this.addBy(t);
   }
   public final MuV3Df subByRot(
      ImuV3Df src, ImuV3Df drx, float A, ImuV3Df center
   ) {
      final MuV3Df t=new MuV3Df()
                      .setByRot(src, drx, A, center);
      return this.subBy(t);
   }

   //[ 適用場合: 客戶現成的資料是sin(A)而非A
   //[ -PI/2 <= A <= +PI/2, -1 <= sin(A) <= +1
   public final MuV3Df setBySinRot(ImuV3Df src, ImuV3Df drx, float sinA) {
      return setByCSRot(src, drx, (float)sqrt(1-sinA*sinA), sinA);
   }
   public final MuV3Df sinRotBy(ImuV3Df drx, float sinA) {
      return csRotBy(drx, (float)sqrt(1-sinA*sinA), sinA);
   }
   public final MuV3Df addBySinRot(ImuV3Df src, ImuV3Df drx, float sinA) {
      return addByCSRot(src, drx, (float)sqrt(1-sinA*sinA), sinA);
   }
   public final MuV3Df subBySinRot(ImuV3Df src, ImuV3Df drx, float sinA) {
      return subByCSRot(src, drx, (float)sqrt(1-sinA*sinA), sinA);
   }

   public final MuV3Df setBySinRot(
      ImuV3Df v, ImuV3Df drx, float sinA, ImuV3Df center
   ) {
      return this.setByCSRot(v, drx, (float)sqrt(1-sinA*sinA), sinA, center);
   }
   public final MuV3Df sinRotBy(
      ImuV3Df drx, float sinA, ImuV3Df center
   ) {
      return this.csRotBy(drx, (float)sqrt(1-sinA*sinA), sinA, center);
   }
   public final MuV3Df addBySinRot(
      ImuV3Df v, ImuV3Df drx, float sinA, ImuV3Df center
   ) {
      final MuV3Df t=new MuV3Df()
                    .setBySinRot(v, drx, sinA, center);
      return this.addBy(t);
   }
   public final MuV3Df subBySinRot(
      ImuV3Df v, ImuV3Df drx, float sinA, ImuV3Df center
   ) {
      final MuV3Df t=new MuV3Df()
                    .setBySinRot(v, drx, sinA, center);
      return this.subBy(t);
   }

   //----------------------------------
//I   public static float triple(MuV3Df v1, MuV3Df v2, MuV3Df v3) {  ... }
//I   public ImuV3Df linear_coef(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {  ...  }

   public final MuV3Df solveLinearCoef(
      ImuV3Df v, ImuV3Df v1, ImuV3Df v2, ImuV3Df v3
   ) {
      //  LET  v=p*v1+q*v2+r*v3, D=[v1,v2,v3]
      //  THEN: p=[v,v2,v3]/D, q=[v1,v,v3]/D, r=[v1,v2,v]/D
      //  this.setBy(p,q,r)
      final float D=triple(v1,v2,v3);
      if(D==0) throw new IllegalArgumentException("\n v1, v2, v3 are coplane");
      return this.setBy(triple(v,v2,v3)/D, triple(v1,v,v3), triple(v1,v2,v));
   }

}
