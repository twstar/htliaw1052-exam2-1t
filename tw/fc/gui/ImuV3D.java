package tw.fc.gui;
import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;
import tw.fc.Std;
import tw.fc.EpsilonException;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
//import static java.lang.Math.round;


//**************    ImuV3D.java    *********************
//
//  floating-point 3D vector
//
//  Imu(immutable)

// Note: 本系統以row vector為主, 這比較接近高中幾何學.

public class ImuV3D
   implements DuplicableI<MuV3D>, PrintableI
{
   double x, y, z;

   //-------------------------------------------------
   public ImuV3D() {  x=0.0; y=0.0; z=0.0; }

   public ImuV3D(double xx, double yy, double zz) {  x=xx; y=yy; z=zz; }

   public ImuV3D(ImuV3D src) {  x=src.x;  y=src.y;  z=src.z;  }
   public ImuV3D(ImuV3Df src) {  x=src.x;  y=src.y;  z=src.z;  }
   public ImuV3D(ImuV3Di src) {  x=src.x;  y=src.y;  z=src.z;  }
//   public ImuV3D(ImuV3Di src) { x=src.x;  y=src.y;  z=src.z; }

   public ImuV3D(ImuP3D src) { //: polar coordinate to orthogonal coordinate
      x=src.radius*cos(src.latitude)*cos(src.longitude);
      y=src.radius*cos(src.latitude)*sin(src.longitude);
      z=src.radius*sin(src.latitude);
   }

   //--------------------------------------------------
   public final boolean hasNaN() {
      return Double.isNaN(x)||Double.isNaN(y)||Double.isNaN(z);
   }
   public final boolean hasInfinite() { //: +-infinity
      return Double.isInfinite(x)||Double.isInfinite(y)||Double.isInfinite(z);
   } 
   public final boolean abNormal() { //: +NaN or -infinity
      return Double.isNaN(x)||Double.isNaN(y)||Double.isNaN(z)||
             Double.isInfinite(x)||Double.isInfinite(y)||Double.isInfinite(z);
   } 

   public final double x() {  return x; }
   public final double y() {  return y; }
   public final double z() {  return z; }
   public final int ix() {  return (int)Math.round(x); }
   public final int iy() {  return (int)Math.round(y); }
   public final int iz() {  return (int)Math.round(z); }
   //[ A5.039L.J add, so that they can be iterated
   public final double comp(int axis) {  
      switch(axis) {
      case 0:  return x;
      case 1:  return y;
      case 2:  return z;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

   //public final MuV3Di toXYZ() {  return new MuV3Di(ix(),iy(),iz()); }
   public final MuV3Di toV3Di() {  return new MuV3Di(ix(),iy(),iz()); }

   public final MuV3Df toV3Df() {
      return new MuV3Df((float)x, (float)y, (float)z);
   }
//   public MuV3Df toV3Di() {
//      return new MuV3Di((int)round(x), (int)round(y), (int)round(z);
//   }

   public MuP3D toP3D() {  return new MuP3D(this);  }

   public String toString() { return "(" + x + ", " + y + ", " + z + ")";  }

   public final boolean equals(ImuV3D v2) {
      return (x==v2.x && y==v2.y && z==v2.z);
   }

   public final boolean equals(ImuV3D v2, double errSq) {
      final double dx=x-v2.x, dy=y-v2.y, dz=z-v2.z;
      return (dx*dx+dy*dy+dz*dz<errSq) ;
   }

   public final boolean notEquals(ImuV3D v2) {  return !equals(v2); }

   public final boolean notEquals(ImuV3D v2, double errSq) {
      return !equals(v2, errSq);
   }

   public final boolean equals(Object v2) {
      return equals((ImuV3D)v2);
   }

   public final int hashCode() {
      long tL; int tI=17;
      tL=Double.doubleToLongBits(x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(y); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(z); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero() {  return (x==0 && y==0 && z==0);  }

   public final boolean isZero(double errSq) {
      return (x*x+y*y+z*z<errSq);
   }

   public final boolean notZero() {  return (x!=0 || y!=0 || z!=0);  }

   public final boolean notZero(double errSq) {
      //////return !isZero();
      return !isZero(errSq);
   }

   //[ -----------  implement DuplicableI
   public final MuV3D duplicate() {  return new MuV3D(this); }
   //] -----------  implement DuplicableI

   //[-------- implements PrintableI -------------------
   //[ 不可final, SolidVertex 要override 
   public void printTo(TxOStream ooo) throws java.io.IOException {
      ooo.p("(").pc(x).pc(y).p(z).p(")");
   }
   public void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException 
   {
      ooo.p("(").wpc(w,x).wpc(w,y).wp(w,z).p(")");
   }
   //]-------- implements PrintableI -------------------

   public final ImuV3D add(ImuV3D v2) {
      return new MuV3D(x+v2.x, y+v2.y, z+v2.z);
   }

   public final ImuV3D sub(ImuV3D v2) {
      return new MuV3D(x-v2.x, y-v2.y, z-v2.z);
   }

   public final ImuV3D add(ImuV3Df v2) {
      return new MuV3D(x+v2.x, y+v2.y, z+v2.z);
   }

   public final ImuV3D sub(ImuV3Df v2) {
      return new MuV3D(x-v2.x, y-v2.y, z-v2.z);
   }
//   public final ImuV3D add(ImuV3Di v2) {
//      return new MuV3D(x+v2.x, y+v2.y, z+v2.z);
//   }
//   public final ImuV3D sub(ImuV3Di v2) {
//      return new MuV3D(x-v2.x, y-v2.y, z-v2.z);
//   }

   //[ 去final讓SolidVertex覆寫 
   public ImuV3D neg() {  return new MuV3D(-x,-y,-z); }

   public final ImuV3D xyNeg() {  return new MuV3D(-x,-y,z); }
   public final ImuV3D yzNeg() {  return new MuV3D(x,-y,-z); }
   public final ImuV3D xzNeg() {  return new MuV3D(-x,y,-z); }
   public final ImuV3D zxNeg() {  return new MuV3D(-x,y,-z); }

   public final ImuV3D xNeg() {  return new MuV3D(-x,y,z); }
   public final ImuV3D yNeg() {  return new MuV3D(x,-y,z); }
   public final ImuV3D zNeg() {  return new MuV3D(x,y,-z); }

   public final ImuV3D mul(double k) {  //:  scalar product
      return new MuV3D(x*k, y*k, z*k);
   }

   //[ 鼓勵用mul
   public final ImuV3D sMul(double k) {  //:  scalar product
      return new MuV3D(x*k, y*k, z*k);
   }

@Deprecated
public final ImuV3D smul(double k) {  //:  scalar product
      return new MuV3D(x*k, y*k, z*k);
}

   public final ImuV3D div(double k) throws EpsilonException {     //:  scalar product
      if(Math.abs(k)<Std.epsilonSq()) 
         //throw new IllegalArgumentException("divided by epsilonSq");
         throw new EpsilonException(k, "divided by epsilonSq");
      return new MuV3D(x/k, y/k, z/k);
   }

   public final double dot(ImuV3D v2) {     //:  dot product
      return x*v2.x + y*v2.y + z*v2.z;
   }


@Deprecated
public final double dotMul(ImuV3D v2) {  //:  dot product
   return x*v2.x + y*v2.y + z*v2.z;
}

@Deprecated
public final double dotmul(ImuV3D v2) {  //:  dot product
      return x*v2.x + y*v2.y + z*v2.z;
}

   public final ImuV3D cross(ImuV3D v2) {  //:  cross product
      return new MuV3D(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
   }


@Deprecated
public final ImuV3D crossMul(ImuV3D v2) {  //:  cross product
      return new MuV3D(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
}

@Deprecated
public final ImuV3D crossmul(ImuV3D v2) {  //:  cross product
      return new MuV3D(y*v2.z-z*v2.y, z*v2.x-x*v2.z, x*v2.y-y*v2.x);
}

   public double triple(ImuV3D v2, ImuV3D v3) {
      // 三向量積, 即this,v2,v3拼成行列式
      return  this.x*v2.y*v3.z + v2.x*v3.y*this.z + v3.x*v2.z*this.y
            - this.z*v2.y*v3.x - v2.z*v3.y*this.x - v3.z*v2.x*this.y ;
   }

   public final double normSq() {  return (x*x+y*y+z*z); }

   public final double norm() {  return sqrt(x*x+y*y+z*z); }

   public final ImuV3D direction(){   
      return (this.isZero())? ZERO: this.div(this.norm());
   }
   public final ImuV3D normalization(){   
      return (this.isZero())? ZERO: this.div(this.norm());
   }

   public final double distanceSq(ImuV3D v) {
      final double dx=x-v.x, dy=y-v.y, dz=z-v.z;
      return (dx*dx+dy*dy+dz*dz);
   }

   public final double distance(ImuV3D v) {
      final double dx=x-v.x, dy=y-v.y, dz=z-v.z;
      return sqrt(dx*dx+dy*dy+dz*dz);
   }

   public final double distanceSq(double x, double y, double z) {
      x-=this.x;  y-=this.y;  z-=this.z; 
      return x*x+y*y+z*z;
   }

   public final double distance(double x, double y, double z) {
      x-=this.x;  y-=this.y;  z-=this.z;   return sqrt(x*x+y*y+z*z);
   }

 // 3D 不存在 lineDiDistance(ImuV3D A, ImuV3D B)

   public final double lineDistance(ImuV3D A, ImuV3D B) {  
   // return the distance from this to line AB 
      final ImuV3D vAB=B.sub(A);
      final double n=vAB.norm();
      if(n<Std.epsilon()) return this.distance(A);
      return vAB.cross(this.sub(A)).norm()/n;
   }

   public final double planeDiDistance(ImuV3D A, ImuV3D B, ImuV3D C) { 
   // return the directed-distance from this to plane ABC 
      final ImuV3D vAB=B.sub(A), vAC=C.sub(A);
      final double D=vAB.cross(vAC).norm();
      if(D>=Std.epsilonSq()) {
         return triple(vAB, vAC, this.sub(A))/D;
      }
      else {   
         if(vAB.normSq()>vAC.normSq()) {
            //: vAC very short, or the angle very small
            return this.lineDistance(A, B);
         }
         else {
            return this.lineDistance(A, C);
         }
      }
   }
   public final double planeDistance(ImuV3D A, ImuV3D B, ImuV3D C) {  
   // return the distance from this to plane ABC 
      return Math.abs(this.planeDiDistance(A, B, C));
   }

   public final ImuV3D proj(ImuV3D d) {  //:  project into direction d
      if(d.normSq()<Std.epsilonSq()) return new MuV3D(0,0,0);
      return d.mul(this.dot(d)/d.dot(d));
   }
   public final double comp(ImuV3D d) {
   //:  component of project into direction d
      if(d.normSq()<Std.epsilonSq()) return 0.0;
      return this.dot(d)/d.norm();
   }
   public final ImuV3D coProj(ImuV3D d) {
   // this-proj(this,d)
      if(d.normSq()<Std.epsilonSq()) return new MuV3D(this);
      return linearCombination(
         1.0, this, -(this.dot(d)/d.dot(d)), d
      );
   }

@Deprecated
public final ImuV3D co_proj(ImuV3D d) {
             return this.sub(this.proj(d));
}

   public final ImuV3D refl(ImuV3D d) {
   //:  reflect point of this wrt d
   //:  2*this.proj(d)-this
      if(d.normSq()<Std.epsilonSq()) return this.neg();
      return linearCombination(
         2.0*(this.dot(d)/d.dot(d)),d, -1.0,this
      );
   }

   public final ImuV3D coRefl(ImuV3D d) {  //: -this.refl(d)
   //:  this-2*this.proj(d)
      if(d.normSq()<Std.epsilonSq()) return new MuV3D(this);
      return linearCombination(
         1.0,this, -2.0*(this.dot(d)/d.dot(d)),d
      );
   }

@Deprecated
public final ImuV3D co_refl(ImuV3D d) {
                return this.sub(this.proj(d).mul(2d));
}

 //[ 改用negateX, ... 等
 //public final ImuV3D xReflect() {  x= -x; return this; }
 //public final ImuV3D yReflect() {  y= -y; return this; }
 //public final ImuV3D zReflect() {  z= -z; return this; }
 //public final ImuV3D xyReflect() {  x= -x; y= -y; return this; }
 //public final ImuV3D xzReflect() {  x= -x; z= -z; return this; }
 //public final ImuV3D yzReflect() {  z= -z; y= -y; return this; }
 //public final ImuV3D xyzReflect() // 即negate();


   //[ this 到直線AB的投影點, 假設B!=A
   public final ImuV3D lineProj(ImuV3D A, ImuV3D B) throws EpsilonException {   
      // [解] 令 ans=A+t(B-A)=(1-t)A+tA, 條件(B-A)*(ans-this)=0
      //      代入: (B-A)*(A+t(B-A)-this)=0
      //       t=(B-A)*(this-A)/((B-A)*(B-A))
      //       1-t=...=(B-A)*(B-this)/((B-A)*(B-A))
      //      ans=分子/分母,
      //      分母=((B-A)*(B-A)),
      //      分子=... =((B-A)*(B-this))A+((A-B)*(A-this))B // 對稱型公式
      // -------------------
      // [另解] ans=A+(ans-A)=A+(this-A).proj(B-A)
      //           =A+((this-A)*(B-A))(B-A)/((B-A)*(B-A))
      final ImuV3D vAB=B.sub(A);  
      final double D=vAB.normSq();
      if(D<Std.epsilonSq()) {
          //throw new IllegalArgumentException("A, B too closed to compute");
          throw new EpsilonException(D, "A, B too closed to compute");
      //   return A;   //: 不對: 可能很偏.
      }
      final ImuV3D vAT=this.sub(A);  
   // final double t=vAB.dot(vAT)/D;
      return new MuV3D(A).addByMul(vAB.dot(vAT)/D, vAB);
   // return linePoint(A, B, t);
   }

   //[ this 到直線AB的鏡射點, 假設B!=A
   public final ImuV3D lineRefl(ImuV3D A, ImuV3D B){   
      // this+this.lineRefl(A,B)=2*this.lineProj(A,B)
      // this.lineRefl(A,B)=2*this.lineProj(A,B)-this
      return ((MuV3D)(this.lineProj(A,B))).mulBy(2).subBy(this);
   }

   //[ this 到平面ABC的投影點, 假設A,B,C不共線
   public final ImuV3D planeProj(ImuV3D A, ImuV3D B, ImuV3D C)
   throws EpsilonException {   
      // 令 ans=C+x(vCA)+y(vCB)  // =xA+yB+zC, x+y+z=1
      // 條件 (vCA)*(ans-this)=0, (vCB)*(ans-this)=0.
      // 代入:   (vCA)*((C-this)+x(vCA)+y(vCB))=0, 
      //         (vCB)*((C-this)+x(vCA)+y(vCB))=0.
      // 移項:    x(vCA*vCA)+y(vCA*vCB)=vCA*(this-C), 
      //          x(vCB*vCA)+y(vCB*vCB)=vCB*(this-C).
      // 解得 x=Dx/D, y=Dy/D,
      //       D=(vCA*vCA)(vCB*vCB)-(vCA*vCB)^2  
                    //: = vCA.cross(vCB).normSq() 
      //       Dx=(vCA*(this-C))(vCB*vCB)-(vCB*(this-C))(vCA*vCB)
      //       Dy=(vCA*vCA)(vCB*(this-C))-(vCA*vCB)(vCA*(this-C))
      final ImuV3D vCA=A.sub(C), vCB=B.sub(C);  
      final double vCA_vCA=vCA.dot(vCA), 
                   vCA_vCB=vCA.dot(vCB), 
                   vCB_vCB=vCB.dot(vCB);
      final double D=vCA_vCA*vCB_vCB-vCA_vCB*vCA_vCB;
      if(Math.abs(D)<Std.epsilonSq()) {
          //throw new IllegalArgumentException("A,B,C too closed to compute");
          throw new EpsilonException(D, "A,B,C too closed to compute");
      }
      final ImuV3D vCT=this.sub(C);
      final double Dx=vCA.dot(vCT)*vCB_vCB-vCB.dot(vCT)*vCA_vCB;
      final double Dy=vCB.dot(vCT)*vCA_vCA-vCA.dot(vCT)*vCA_vCB;
   // return planePoint(C,A,B,Dx/D,Dy/D);
   // return new MuV3D(C).addByMul(Dx/D,vCA).addByMul(Dy/D,vCB);
      return new MuV3D(C).addByMul(Dx/D,vCA).addByMul(Dy/D,vCB);
   }
// 因向量與點都是ImuV3D, 容易誤用.
// //[ this 到平面的投影點, 平面通過C, N是法向量
// public final ImuV3D planeProj(ImuV3D C, ImuV3D N) {
//    ImuV3D vCT=this.sub(C);
//    return C.add(vCT.coProj(N));
// }

   //[ this 到平面ABC的鏡射點, 假設A,B,C不共線
   public final ImuV3D planeRefl(ImuV3D A, ImuV3D B, ImuV3D C){   
      return ((MuV3D)(this.planeProj(A,B,C))).mulBy(2).subBy(this);
   }

   //---------------
   public final ImuV3D rot(ImuV3D drx, double angle) {
/////   public final ImuV3D rot(double angle, ImuV3D drx) {
      return new MuV3D(this).rotBy(drx, angle);
   }
@Deprecated public final ImuV3D rot(double angle, ImuV3D drx) {
   return this.rot(drx, angle);
}

   public final ImuV3D xRot(double angle) {
      final double cosA=cos(angle), sinA=sin(angle);
      return new MuV3D(x, y*cosA-z*sinA, y*sinA+z*cosA);
   }
   public final ImuV3D yRot(double angle) {
      final double cosA=cos(angle), sinA=sin(angle);
      return new MuV3D(x*cosA+z*sinA, y, -x*sinA+z*cosA);
   }
   //[ 去final讓SolidVertex覆寫 
   public ImuV3D zRot(double angle) {
      final double cosA=cos(angle), sinA=sin(angle);
      return new MuV3D(x*cosA-y*sinA, x*sinA+y*cosA, z);
   }

   public final ImuV3D rot(ImuV3D drx, double angle, ImuV3D center) {
      return new MuV3D(this).rotBy(drx, angle, center);
   }
@Deprecated public final ImuV3D rot(
   double angle, ImuV3D drx, ImuV3D center
) {
   return this.rot(drx, angle, center);
}

   public final ImuV3D xRot(double angle, ImuV3D center) {
      return this.sub(center).xRot(angle).add(center);
   }
   public final ImuV3D yRot(double angle, ImuV3D center) {
      return this.sub(center).yRot(angle).add(center);
   }
   public final ImuV3D zRot(double angle, ImuV3D center) {
      return this.sub(center).zRot(angle).add(center);
   }

   public final double angle(ImuV3D till) {
      //[ the undirected angle from this to till, [0,+PI]
      final ImuV3D v1=new ImuV3D(this), v2=new ImuV3D(till);
      final double n1=v1.norm(), n2=v2.norm();
      if(n1<Std.epsilon() || n2<Std.epsilon()) return 0.0;
      final double dm=v1.dot(v2)/n1/n2;
      if(dm>1.0 && dm<1.000001) {
         //:  fix the fault [ Math.acos(dm) == -1.#IND000000000 ]
         //:  which appears when this=(12,12),till=(22,22)
         return 0.0;
      }
      return tw.fc.Std.a_cos(dm);  //: 無向角
   }

   //--------------
   public ImuV3D linearCoef(ImuV3D v1, ImuV3D v2, ImuV3D v3) 
   throws EpsilonException {   
   //[  let  F=this=p*v1+q*v2+r*v3, solve and return (p,q,r)
   //[  ans: D=[v1 v2 v3], p=[F v2 v3]/D, q=[v1 F v3]/D, r=[v1 v2 F]/D.
   //[       assume D != 0
   //[  proof: [F v2 v3]=p*[v1 v2 v3]+q*[v2 v2 v3]+r*[v3 v2 v3]=p*D
   //[         [v1 F v3]=p*[v1 v1 v3]+q*[v1 v2 v3]+r*[v1 v3 v3]=q*D
   //[         [v1 v2 F]=p*[v1 v2 v1]+q*[v1 v2 v2]+r*[v1 v2 v3]=r*D
      final double D=triple(v1, v2, v3);
      if(Math.abs(D)<Std.epsilonCb()) throw 
         //new IllegalArgumentException("the triple is epsilonCb()");
         new EpsilonException(D, "the triple is epsilonCb()");
      return new MuV3D(triple(this,v2,v3)/D,
                       triple(v1,this,v3)/D,
                       triple(v1,v2,this)/D );
   }
@Deprecated
public ImuV3D linear_coef(ImuV3D v1, ImuV3D v2, ImuV3D v3){   
   return linearCoef(v1, v2, v3);
}

   private double[] _lPI_para(ImuV3D v, ImuV3D a, ImuV3D b) {
// public double[] linePlaneIntersect_para((ImuV3D v, ImuV3D a, ImuV3D b) {
   //[ the line: from F(==this) throurh vector v
   //[ the plane: pass ZERO and spanned by vectors a and b.
   //[ ans: [t, x, y], such that
   //[      intersection point is  F+t*v = x*a+y*b
   //[      t= -[F a b]/[v a b], x=[v F b]/[v a b], y=[v a F]/[v a b]
   //[ solvable iff not-parallel iff [v a b]!=0
   //[ proof:          [F a b]+t*[v a b]=x*[a a b]+y*[b a b]=0
   //[         [v F b]=[v F b]+t*[v v b]=x*[v a b]+y*[v b b]=x*[v a b]
   //[         [v a F]=[v a F}+t*[v a v]=x*[v a a]+y*[v a b]=y*[v a b]
      final double D=triple(v, a, b);
      if(D<Std.epsilonCb()) return null;
      return new double[]{ -triple(this,a,b)/D,
                           triple(v,this,b)/D,
                           triple(v,a,this)/D };
   }
   public double[] linePlaneIntersect_para(
      ImuV3D v, ImuV3D A, ImuV3D C, ImuV3D B
   ) {
   //[ the line: from 'this' throurh vector v
   //[ the plane: pass points A,C,B.
   //[ let a=A-C, b=B-C, F='this'-C
   //[ ans: [t, x, y], such that intersection point 
   //[        is 'this'+t*v = C+x*(A-C)+y*(B-C),
   //[        i.e.  F+t*v = x*a+y*b,
   //[      return null if the line parallel to the plane
   //[ solvable iff not-parallel iff [v a b]!=0
   //[      t= -[F a b]/[v a b], x=[v F b]/[v a b], y=[v a F]/[v a b]
      return this.sub(C)._lPI_para(v, A.sub(C), B.sub(C));
   }
   public ImuV3D linePlaneIntersect(
      ImuV3D v, ImuV3D A, ImuV3D C, ImuV3D B
   ) {
   //[ the line: from 'this' throurh vector v
   //[ the plane: pass points A,C,B.
   //[ ans: the intersection point or null
      double[] p=linePlaneIntersect_para(v, A, C, B);
      if(p==null) return null;
      return new MuV3D(this).addByMul(p[0],v);
   }

   public ImuV3D linePlaneIntersect(
      ImuV3D d, ImuV3D C, ImuV3D NV
   ) {
   //[ the line: from 'this' throurh vector d
   //[ the plane: pass point C with normal vector NV
   //[ ans: the intersection point or null
   //[     ans=this+t*d belongs to the plane,
   //[     i.e. NV dot (this+t*d-C)=0    
   //[     i.e.  t*( NV dot d ) = NV dot (C - this)    
      final double D=NV.dot(d);
      if(D<Std.epsilonSq()) return null;
      final double t=NV.dot(C.sub(this))/D;
      return new MuV3D(this).addByMul(t, d);
   }

   public final ImuV3D mul(ImuM3D m2) {
      // this 視為 1 by 3 矩陣, return this*m2 
//      return new MuV3D(
//         x*m2._11 + y*m2._21 + z*m2._31,
//         x*m2._12 + y*m2._22 + z*m2._32,
//         x*m2._13 + y*m2._23 + z*m2._33
//      );
      return m2.swapMul(this);
   }
   public final ImuV3D swapMul(ImuM3D m1) { // this 視為 3 by 1 矩陣
      return m1.mul(this);
   }

   public final ImuV3D mul(ImuAr3D A2) {  
   //           [ M2  0 ]
   //  [ v1  1 ][ v2  1 ]=[ v1*M2+v2  1 ]
      return new MuV3D(A2._vec).addByMul(this, A2._mtx);
   }


 //[ =========  static part  ===========================
   public static final ImuV3D ZERO=new ImuV3D(0.0, 0.0, 0.0);
   public static final ImuV3D E1  =new ImuV3D(1.0, 0.0, 0.0);
   public static final ImuV3D E2  =new ImuV3D(0.0, 1.0, 0.0);
   public static final ImuV3D E3  =new ImuV3D(0.0, 0.0, 1.0);
   public static final ImuV3D NegE1 =new ImuV3D(-1.0, 0.0, 0.0);
   public static final ImuV3D NegE2 =new ImuV3D(0.0, -1.0, 0.0);
   public static final ImuV3D NegE3 =new ImuV3D(0.0, 0.0, -1.0);

   //---------------
   public static final ImuV3D linearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2)
   {  //:  a1*v1+a2*v2
      return new MuV3D(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
   }
   public static final ImuV3D linearCombination(
      double a1, double a2, ImuV3D v1, ImuV3D v2)
   {  //:  a1*v1+a2*v2
      return new MuV3D(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y,
         a1*v1.z+a2*v2.z
      );
   }
   public static final ImuV3D linearCombination(
      double a1, ImuV3D v1, double a2, ImuV3D v2, double a3, ImuV3D v3)
   {  //: compute a1*v1+a2*v2+a3*v3
      return new MuV3D(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
   }
   public static final ImuV3D linearCombination(
      double a1, double a2, double a3,
      ImuV3D v1, ImuV3D v2,  ImuV3D v3
   ) {  //: compute a1*v1+a2*v2+a3*v3
      return new MuV3D(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y,
         a1*v1.z+a2*v2.z+a3*v3.z
      );
   }
   public static final ImuV3D linearCombination(
      double[] a, ImuV3D[] v
   ) {  //:  a1*v1+a2*v2+a3*v3
      if(a.length!=v.length) throw
         new IllegalArgumentException("\n size mismatch");
      final MuV3D ans=new MuV3D();
      for(int i=0; i<v.length; i++) {
         ans.addByMul(a[i],v[i]);
      }
      return ans;
   }

   //------

   //[ A+t(B-A)=(1-t)A+(t)B
   public static final ImuV3D linePoint(ImuV3D A, ImuV3D B, double t) {
      return linearCombination(1-t, A, t, B);
   }

   //[ 中點
   public static final ImuV3D midPoint(ImuV3D A, ImuV3D B) {
      return new MuV3D(A).addBy(B).mulBy(0.5);
   }

   //[ A+s(B-A)+t(C-A)=(1-s-t)A+(s)B+(t)C
   public static final ImuV3D planePoint(
      ImuV3D A, ImuV3D B, ImuV3D C, double s, double t
   ) {
      return linearCombination(1-s-t, A, s, B, t, C);
   }

   //[ 重心
   public static final ImuV3D centroid(ImuV3D A, ImuV3D B, ImuV3D C) {
      return new MuV3D(A).addBy(B).addBy(C).mulBy(1.0/3.0);
   }

   public static double triple(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      // 三向量積, 即v1,v2,v3拼成行列式
      return  v1.x*v2.y*v3.z + v2.x*v3.y*v1.z + v3.x*v2.z*v1.y
            - v1.z*v2.y*v3.x - v2.z*v3.y*v1.x - v3.z*v2.x*v1.y ;
   }
@Deprecated
public static double tripleMul(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      return triple(v1, v2, v3);
}
@Deprecated
public static double triplemul(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      return triple(v1, v2, v3);
}

/*
   public static double[] linePlaneIntersect_para(
                      ImuV3D P, ImuV3D Q,
                      ImuV3D A, ImuV3D C, ImuV3D B
   ) {
   //[ the line: throu P, Q
   //[ the plane: pass A, C, B.
   //[ ans: [t, x, y], such that
   //[      intersection point is  P+t*(Q-P) = C+x*(A-C)+y*(B-C),
   //[                         or  (P-c)+t*(Q-P) = x*(A-C)+y*(B-C)
   //[ solvable iff not-parallel iff [Q-P A-C B-C]!=0
      return P.sub(C).linePlaneIntersect_para(Q.sub(P),A.sub(C),B.sub(C));
   }
*/

   //[ 求三平面交點 (a1)dot(v)=d1, a2(dot)v=d2, a3(dot)v=d3 
   //[ 非恰一解時傳回null
   public static ImuV3D solve3PlaneIntersection(
      ImuV3D a1, double d1, ImuV3D a2, double d2, ImuV3D a3, double d3
   ) {
      final double D=ImuV3D.triple(a1,a2,a3);
      if(D<Std.epsilonCb()) return null;
      final double Dx=ImuM3D.det(
         d1, a1.y(), a1.z(),
         d2, a2.y(), a2.z(),
         d3, a3.y(), a3.z()
      );
      final double Dy=ImuM3D.det(
         a1.x(), d1, a1.z(),
         a2.x(), d2, a2.z(),
         a3.x(), d3, a3.z()
      );
      final double Dz=ImuM3D.det(
         a1.x(), a1.y(), d1, 
         a2.x(), a2.y(), d2, 
         a3.x(), a3.y(), d3 
      );
      return new ImuV3D(Dx/D, Dy/D, Dz/D); 
   }

   //[ v0, v1 之中垂面
   //     (v1-v0)dot(v-m)=0, m=(v1+v0)/2.
   // 即  (v1-v0)dot(v)=(v1-v0)dot((v1+v0)/2)
   // 即  (v1-v0)dot(v)=((v1)dot(v1)-(v0)dot(v0))/2
   //]
   //[ 通過 v0, v1, v2 之平面
   //     (v1-v0)cross(v2-v0)dot(v-v0)=0
   // 即  (v1-v0)cross(v2-v0)dot(v)=(v1-v0)cross(v2-v0)dot(v0)
   //]

   //[ 3D中,三角形求外心, 或 大圓弧求球心
   //[ 呼叫者須檢查函數值是否為null
   public static ImuV3D CircumCenter(ImuV3D v1, ImuV3D v0, ImuV3D v2) {
      final ImuV3D v1_sub_v0=v1.sub(v0), v2_sub_v0=v2.sub(v0); 
      return solve3PlaneIntersection(
                v1_sub_v0, (v1.normSq()-v0.normSq())/2,
                v2_sub_v0, (v2.normSq()-v0.normSq())/2,
                v1_sub_v0.cross(v2_sub_v0), triple(v1_sub_v0, v2_sub_v0, v0)
             );
   }
   //] =========  static part  ===========================

}
