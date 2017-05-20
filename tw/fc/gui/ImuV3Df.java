package tw.fc.gui;
import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;
import tw.fc.Std;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
//import static java.lang.Math.round;

//**********    ImuV3Df.java    *********************
//
//  floating-point 3D vector
//
//  Imu(immutable)

// Note: 本系統以row vector為主, 這比較接近高中幾何學.

public class ImuV3Df
     implements DuplicableI<MuV3Df>, PrintableI
{
   float x, y, z;

   //-------------------------------------------------
   public   ImuV3Df() {  x=0.0f; y=0.0f; z=0.0f; }
   public   ImuV3Df(float xx, float yy, float zz) {  x=xx; y=yy; z=zz; }
   public   ImuV3Df(ImuV3D src)  {  x=(float)src.x;  y=(float)src.y;  z=(float)src.z; }
   public   ImuV3Df(ImuV3Df src) {  x=src.x;  y=src.y;  z=src.z;  }
//   public   ImuV3Df(ImuV3Di src) {  x=src.x;  y=src.y;  z=src.z;  }
   public   ImuV3Df(ImuP3Df src) { //: 球座標轉正交座標
      x=(float)(src.radius*cos(src.latitude)*cos(src.longitude));
      y=(float)(src.radius*cos(src.latitude)*sin(src.longitude));
      z=(float)(src.radius*sin(src.latitude));
   }

   //--------------------------------------------------
   public final float x() {  return x; }
   public final float y() {  return y; }
   public final float z() {  return z; }
   public final float comp(int axis) {  //: A5.039L.J add
      switch(axis) {
      case 0:  return x;
      case 1:  return y;
      case 2:  return z;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

//[ in class Math
//[ static long round(double a)
//[ static int round(float a)
   public final int ix() {  return Math.round(x); }
   public final int iy() {  return Math.round(y); }
   public final int iz() {  return Math.round(z); }

   public final MuV3D toV3D() { return new MuV3D(x,y,z); }

//   public MuV3Df toV3Di() {
//      return new MuV3Di((int)round(x), (int)round(y), (int)round(z);
//   }
   public MuP3Df toP3Df() {  return new MuP3Df(this);  }

   public String toString() { return "(" + x + ", " + y + ", " + z + ")";  }

   public final boolean equals(ImuV3Df v2) {
      return (x==v2.x && y==v2.y && z==v2.z);
   }
   public final boolean equals(ImuV3Df v2, float errSq) {
      final float dx=x-v2.x, dy=y-v2.y, dz=z-v2.z;
      return (dx*dx+dy*dy+dz*dz<errSq) ;
   }

   public final boolean notEquals(ImuV3Df v2) {  return !equals(v2); }
   public final boolean notEquals(ImuV3Df v2, float epsilon) {
      return !equals(v2, epsilon);
   }

   public final boolean equals(Object v2) {
      return equals((ImuV3Df)v2);
   }
   public final int hashCode() {
      long tL; int tI=17;
      tL=Double.doubleToLongBits(x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(y); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(z); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero()  {  return (x==0f && y==0f && z==0f);  }
   public final boolean isZero(float errSq) { return (x*x+y*y+z*z < errSq); }

   public final boolean notZero() {  return (x!=0f || y!=0f || z!=0f);  }
   public final boolean notZero(float errSq) {  return !isZero(errSq);  }

   //[ -----------  implement DuplicableI
   public final MuV3Df duplicate() {  return new MuV3Df(this); }
   //] -----------  implement DuplicableI



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

   public final ImuV3Df add(ImuV3D v2) {
      return new MuV3Df(x + (float)v2.x, y + (float)v2.y, z + (float)v2.z);
   }

   public final ImuV3Df sub(ImuV3D v2) {
      return new MuV3Df(x - (float)v2.x, y - (float)v2.y, z - (float)v2.z);
   }

   public final ImuV3Df add(ImuV3Df v2) {
      return new MuV3Df(x+v2.x, y+v2.y, z+v2.z);
   }
   public final ImuV3Df sub(ImuV3Df v2) {
      return new MuV3Df(x-v2.x, y-v2.y, z-v2.z);
   }
//   public final ImuV3Df add(ImuV3Di v2) {
//      return new MuV3Df(x+v2.x, y+v2.y, z+v2.z);
//   }
//   public final ImuV3Df sub(ImuV3Di v2) {
//      return new MuV3Df(x-v2.x, y-v2.y, z-v2.z);
//   }
   public final ImuV3Df neg() {  return new MuV3Df(-x,-y,-z); }

   public final ImuV3Df xyNeg() {  return new MuV3Df(-x,-y,z); }
   public final ImuV3Df yzNeg() {  return new MuV3Df(x,-y,-z); }
   public final ImuV3Df xzNeg() {  return new MuV3Df(-x,y,-z); }
   public final ImuV3Df zxNeg() {  return new MuV3Df(-x,y,-z); }

   public final ImuV3Df xNeg() {  return new MuV3Df(-x,y,z); }
   public final ImuV3Df yNeg() {  return new MuV3Df(x,-y,z); }
   public final ImuV3Df zNeg() {  return new MuV3Df(x,y,-z); }

   public final ImuV3Df mul(float k) {  //:  scalar product
      return new MuV3Df(x*k, y*k, z*k);
   }
   public final ImuV3Df div(float k) {  //:  scalar product
      if(k==0) throw new IllegalArgumentException("divided by 0");
      return new MuV3Df(x/k, y/k, z/k);
   }
   public final float dot(ImuV3Df v2) {  //:  dot product
      return x*v2.x + y*v2.y + z*v2.z;
   }
   public final ImuV3Df cross(ImuV3Df v2) {  //:  cross product
      return new ImuV3Df(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
   }
   public float triple(ImuV3Df v2, ImuV3Df v3) {
      // 三向量積, 即this,v2,v3拼成行列式
      return  this.x*v2.y*v3.z + v2.x*v3.y*this.z + v3.x*v2.z*this.y
            - this.z*v2.y*v3.x - v2.z*v3.y*this.x - v3.z*v2.x*this.y ;
   }

   public final float normSq() {  return (x*x+y*y+z*z); }
   public final float norm() {  return (float)sqrt(x*x+y*y+z*z); }
   public final ImuV3Df direction() {
      return (this.isZero())? ZERO: this.div(this.norm());
   }
   public final float distanceSq(ImuV3Df v) {
      final float dx=x-v.x, dy=y-v.y, dz=z-v.z;
      return (dx*dx+dy*dy+dz*dz);
   }
   public final float distance(ImuV3Df v) {
      final float dx=x-v.x, dy=y-v.y, dz=z-v.z;
      return (float)sqrt(dx*dx+dy*dy+dz*dz);
   }
   public final float distanceSq(float x, float y, float z) {
      x-=this.x;  y-=this.y;  z-=this.z;  return x*x+y*y+z*z;
   }
   public final float distance(float x, float y, float z) {
      x-=this.x;  y-=this.y;  z-=this.z;  return (float)sqrt(x*x+y*y+z*z);
   }

   public final ImuV3Df proj(ImuV3Df d) {  //:  project into direction d
      if(d.normSq()==0) return new MuV3Df(0f,0f,0f);
      return d.mul(this.dot(d)/d.dot(d));
   }
   public final float comp(ImuV3Df d) {  //: A5.039L.J add
   //:  component of project into direction d
      if(d.normSq()<Std.epsilonSq()) return 0.0f;
      return this.dot(d)/d.norm();
   }
   public final ImuV3Df coProj(ImuV3Df d) {
/////      return this.sub(this.proj(d));
   // this-proj(this,d)
      if(d.normSq()==0f) return new MuV3Df(this);
      return linearCombination(
         1f, this, -(this.dot(d)/d.dot(d)), d
      );
   }
   public final ImuV3Df refl(ImuV3Df d) {
   //:  reflect point of this wrt d
   //:  2*this.proj(d)-this
      if(d.normSq()==0f) return this.neg();
      return linearCombination(
         2f*(this.dot(d)/d.dot(d)),d, -1f,this
      );
   }
   public final ImuV3Df coRefl(ImuV3Df d) {
   //:  this-2*this.proj(d)
      if(d.normSq()==0f) return new MuV3Df(this);
      return linearCombination(
         1f,this, -2f*(this.dot(d)/d.dot(d)),d
      );
   }

   public final ImuV3Df rot(ImuV3Df drx, float angle) {
      return new MuV3Df(this).rotBy(drx, angle);
   }

@Deprecated public final ImuV3Df rot(float angle, ImuV3Df drx) {
   return this.rot(drx, angle);
}

   public final ImuV3Df xRot(float angle) {
      final float cosA=(float)cos(angle), sinA=(float)sin(angle);
      return new ImuV3Df(x, y*cosA-z*sinA, y*sinA+z*cosA);
   }
   public final ImuV3Df yRot(float angle) {
      final float cosA=(float)cos(angle), sinA=(float)sin(angle);
      return new ImuV3Df(x*cosA+z*sinA, y, -x*sinA+z*cosA);
   }
   public final ImuV3Df zRot(float angle) {
      final float cosA=(float)cos(angle), sinA=(float)sin(angle);
      return new ImuV3Df(x*cosA-y*sinA, x*sinA+y*cosA, z);
   }

   public final ImuV3Df rot(ImuV3Df drx, float angle, ImuV3Df center) {
      return new MuV3Df(this).rotBy(drx, angle, center);
   }

@Deprecated public final ImuV3Df rot(
   float angle, ImuV3Df drx, ImuV3Df center
) {
   return this.rot(drx, angle, center);
}

   public final ImuV3Df xRot(float angle, ImuV3Df center) {
      return this.sub(center).xRot(angle).add(center);
   }
   public final ImuV3Df yRot(float angle, ImuV3Df center) {
      return this.sub(center).yRot(angle).add(center);
   }
   public final ImuV3Df zRot(float angle, ImuV3Df center) {
      return this.sub(center).zRot(angle).add(center);
   }

   public final float angle(ImuV3Df till) {
      //[ the undirected angle from this to till, [0,+PI]
      final ImuV3Df v1=new ImuV3Df(this), v2=new ImuV3Df(till);
      final float n1=v1.norm(), n2=v2.norm();
      if(n1==0.0f || n2==0.0f) return 0.0f;
      final double dm=v1.dot(v2)/n1/n2;
      if(dm>1.0f && dm<1.000001f) {
         //:  fix the fault [ Math.acos(dm) == -1.#IND000000000 ]
         //:  which appears when this=(12,12),till=(22,22)
         return 0.0f;
      }
      return (float)tw.fc.Std.a_cos(dm);  //: 無向角
   }

   public ImuV3Df linearCoef(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      //  let  this=p*v1+q*v2+r*v3, solve and return (p,q,r)
      //  ans: D=[v1,v2,v3], p=[t,v2,v3]/D,
      //       q=[v1,t,v3]/D, r=[v1,v2,t]/D.
      final float D=triple(v1, v2, v3);
      if(D==0) throw new IllegalArgumentException("the triple is zero");
      return new MuV3Df(triple(this,v2,v3)/D,
                        triple(v1,this,v3)/D,
                        triple(v1,v2,this)/D );
   }

@Deprecated
public ImuV3Df linear_coef(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
   return linearCoef(v1, v2, v3);
}

   public final ImuV3Df mul(ImuM3Df m2) {
      // this 視為 1 by 3 矩陣, 做右乘. (左乘寫在ImuM3D)
      return new MuV3Df(
         (x*m2._11 + y*m2._21 + z*m2._31),
         (x*m2._12 + y*m2._22 + z*m2._32),
         (x*m2._13 + y*m2._23 + z*m2._33)
      );
   }


 //[ =========  static part  ===========================
   public static final ImuV3Df ZERO=new ImuV3Df(0.0f, 0.0f, 0.0f);
   public static final ImuV3Df E1  =new ImuV3Df(1.0f, 0.0f, 0.0f);
   public static final ImuV3Df E2  =new ImuV3Df(0.0f, 1.0f, 0.0f);
   public static final ImuV3Df E3  =new ImuV3Df(0.0f, 0.0f, 1.0f);
   public static final ImuV3Df NegE1 =new ImuV3Df(-1.0f,  0.0f,  0.0f);
   public static final ImuV3Df NegE2 =new ImuV3Df( 0.0f, -1.0f,  0.0f);
   public static final ImuV3Df NegE3 =new ImuV3Df( 0.0f,  0.0f, -1.0f);

   //---------------
   public static final ImuV3Df linearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2)
   {  //:  a1*v1+a2*v2
      return new MuV3Df(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
   }
   public static final ImuV3D linearCombination(
      float a1, float a2, ImuV3D v1, ImuV3D v2)
   {  //:  a1*v1+a2*v2
      return new MuV3D(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
   }
   public static final ImuV3Df linearCombination(
      float a1, ImuV3Df v1, float a2, ImuV3Df v2, float a3, ImuV3Df v3)
   {  //:  a1*v1+a2*v2+a3*v3
      return new MuV3Df(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
   }
   public static final ImuV3D linearCombination(
      float a1, float a2, float a3,
      ImuV3D v1, ImuV3D v2,  ImuV3D v3
   ) {  //: compute a1*v1+a2*v2+a3*v3
      return new MuV3D(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
   }

   public static final ImuV3Df linearCombination(
      float[] a, ImuV3Df[] v
   ) {  //:  a1*v1+a2*v2+a3*v3
      if(a.length!=v.length) throw
         new IllegalArgumentException("\n size mismatch");
      final MuV3Df ans=new MuV3Df();
      for(int i=0; i<v.length; i++) {
         ans.addByMul(a[i], v[i]);
      }
      return ans;
   }

   //------------------
   public static final ImuV3Df linePoint(ImuV3Df v1, ImuV3Df v2, float t) {
      return linearCombination(1-t, v1, t, v2);
   }
   public static final ImuV3Df midPoint(ImuV3Df v1, ImuV3Df v2) {
      return new MuV3Df(v1).addBy(v2).mulBy(0.5f);
   }
   public static float triple(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      // 三向量積, 即v1,v2,v3拼成行列式
      return  v1.x*v2.y*v3.z + v2.x*v3.y*v1.z + v3.x*v2.z*v1.y
            - v1.z*v2.y*v3.x - v2.z*v3.y*v1.x - v3.z*v2.x*v1.y ;
   }
   //] =========  static part  ===========================

}
