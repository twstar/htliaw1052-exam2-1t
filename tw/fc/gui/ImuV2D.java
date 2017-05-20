package tw.fc.gui;

import java.io.Serializable;
import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;
import tw.fc.EpsilonException;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import tw.fc.Std;

//**************    ImuV2D.java    ********************
//
//  floating-point 2D vector 
//
public class ImuV2D  
       implements DuplicableI<MuV2D>, PrintableI, Serializable
{
   private static final long serialVersionUID = 2014120809L;
   double x, y;  
  
   //------------------------------------------------   
   public   ImuV2D() {  x=0.0; y=0.0; }
   public   ImuV2D(double xx, double yy) {  x=xx; y=yy; }
   public   ImuV2D(ImuV2D src) {  x=src.x;  y=src.y;  }
   public   ImuV2D(ImuV2Df src) { x=src.x;  y=src.y; }
   public   ImuV2D(ImuV2Di src) { x=src.x;  y=src.y; }
   public   ImuV2D(ImuXY src) { x=src.x;  y=src.y;  }
   
   //[ A21112 xie add
   public ImuV2D(ImuP2D src) {  //: 球座標轉正交座標
      x=src.radius*cos(src.angle);
      y=src.radius*sin(src.angle);      
   }

   public   ImuV2D(java.awt.event.MouseEvent e) {  
      x=e.getX(); y=e.getY(); 
   }
   //-----------------------------------------------
   public final double x() {  return x; }
   public final double y() {  return y; }
   public final double comp(int axis) {  //: A5.039L.J add
      switch(axis) {
      case 0:  return x;
      case 1:  return y;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }
   public final int ix() {  return (int)round(x); }
   public final int iy() {  return (int)round(y); }

   public final MuV2Df toV2Df() {  return new MuV2Df((float)x(),(float)y()); }
   public final MuV2Di toV2Di() {  return new MuV2Di(ix(),iy()); }
   public final MuXY toXY() {  return new MuXY(ix(),iy()); }

   public MuP2D toP2D() {  return new MuP2D(this);  }

   public String toString() { return "(" + x + ", " + y + ")" ;  }
   public final boolean equals(ImuV2D v2) { return (x==v2.x && y==v2.y);  }
   public final boolean equals(ImuV2D v2, double errSq) {
      final double dx=x-v2.x, dy=y-v2.y;
      return (dx*dx+dy*dy<errSq) ;
   }
   public final boolean notEquals(ImuV2D v2) { return !equals(v2);  }
   public final boolean notEquals(ImuV2D v2, double errSq) { 
      return !equals(v2, errSq);  
   }
   public final boolean equals(Object v2) {
      return equals((ImuV2D)v2);
   } 
   public final int hashCode() {  
      long tL; int tI=17;  // as is recommended in "Effective Java"
      tL=Double.doubleToLongBits(x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(y); tI=tI*37+(int)(tL^(tL>>32));
      return tI;  
   }

   public final boolean isZero() {  return (x==0 && y==0);  }
   public final boolean isZero(double errSq) {
      return (x*x+y*y<errSq);  
   }
   public final boolean notZero() {  return (x!=0 || y!=0);  }
   public final boolean notZero(double errSq) {  return !isZero();  }
    
   //[ -----------  implement DuplicableI
   public final MuV2D duplicate() { return new MuV2D(this); }
   //] -----------  implement DuplicableI
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

   public final ImuV2D add(ImuV2D v2) {  return new MuV2D(x+v2.x, y+v2.y);    }
   public final ImuV2D sub(ImuV2D v2) {  return new MuV2D(x-v2.x, y-v2.y);   }
   public final ImuV2D add(ImuXY v2) {  return new MuV2D(x+v2.x, y+v2.y);    }
   public final ImuV2D sub(ImuXY v2) {  return new MuV2D(x-v2.x, y-v2.y);   }

   public final ImuV2D neg() {  return new MuV2D(-x,-y); }
   public final ImuV2D xNeg() {  return new MuV2D(-x,y); }
   public final ImuV2D yNeg() {  return new MuV2D(x,-y); }
   
   public final ImuV2D mul(double k) {  //:  scalar product
      return new MuV2D(x*k, y*k);   
   }
   //[ 鼓勵用mul
   public final ImuV2D sMul(double k) {  //:  scalar product
      return new MuV2D(x*k, y*k);   
   }
@Deprecated
public final ImuV2D smul(double k) {  //:  scalar product
   return  new MuV2D(x*k, y*k);   
}
   public final ImuV2D div(double k) throws EpsilonException {     //:  scalar product
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq"); 
         new EpsilonException(k, "divided by epsilonSq");       
      return new MuV2D(x/k, y/k);   
   }

   public final double dot(ImuV2D v2) {  //:  dot product
      return this.x*v2.x+ this.y*v2.y;  
   }
@Deprecated
public final double dotMul(ImuV2D v2) {  //:  dot product
      return this.x*v2.x+ this.y*v2.y;  
}
@Deprecated
public final double dotmul(ImuV2D v2) {  //:  dot product
   return x*v2.x+ y*v2.y;  
}
   public final double wedge(ImuV2D v2) {  //:  wedge product
      return (this.x*v2.y-this.y*v2.x);
   }
@Deprecated
public final double wedgeMul(ImuV2D v2) {  //:  wedge product
      return (x*v2.y-y*v2.x);
}
@Deprecated
public final double wedgemul(ImuV2D v2) {  //:  wedge product
   return (x*v2.y-y*v2.x);
}
//]
      
   public final double normSq() {  return (x*x+y*y); }
   public final double norm() {  return sqrt(x*x+y*y); }
   public final ImuV2D direction() {
      final double n=this.norm();
      return (n<Std.epsilon())? new MuV2D(0,0) : new MuV2D(x/n, y/n);
   }
   public final double distanceSq(ImuV2D v) {  
      final double dx=x-v.x, dy=y-v.y;
      return (dx*dx+dy*dy); 
   }
   public final double distance(ImuV2D v) {  
      final double dx=x-v.x, dy=y-v.y;
      return sqrt(dx*dx+dy*dy); 
   }
   public final double distanceSq(double x, double y) {  
      final double dx=this.x-x, dy=this.y-y;
      return dx*dx+dy*dy; 
   }
   public final double distance(double x, double y) {  
      final double dx=this.x-x, dy=this.y-y;
      return sqrt(dx*dx+dy*dy); 
   }

   public final double lineDiDistance(ImuV2D A, ImuV2D B) {  
   // return the directed-distance from this to line AB 
      final ImuV2D vAB=B.sub(A);
      final double n=vAB.norm();
      if(n<Std.epsilon()) return this.distance(A);
      // final double R=vAT.coProj(vAB).norm();
      return vAB.wedge(this.sub(A))/n;
   }
   public final double lineDistance(ImuV2D A, ImuV2D B) {  
   // return the distance from this to line AB 
      return Math.abs(this.lineDiDistance(A,B));
   }

   public final ImuV2D proj(ImuV2D d) {  //:  project into direction d
      return new MuV2D().setByProj(this, d); 
   }
   public final double comp(ImuV2D d) {
   //:  component of project into direction d
      if(d.normSq()<Std.epsilonSq()) return 0.0;
      return this.dot(d)/d.norm();
   }

   public final ImuV2D coProj(ImuV2D d) { 
   //:  this-proj(this,d) 
      return new MuV2D().setByCoProj(this, d); 
//      if(d.normSq()==0) return new MuV2D(this);
//      return linearCombination(
//         1d, this, -(this.dot(d)/d.dot(d)), d
//      );
   }
@Deprecated
public final ImuV2D co_proj(ImuV2D d) {  //: DEPRECATED
      return new MuV2D().setByCoProj(this, d); 
}
   public final ImuV2D refl(ImuV2D d) { //:  reflect point of this wrt d
   //:   2*this.proj(d)-this   
      return new MuV2D().setByRefl(this, d); 
//      if(d.normSq()==0d) return this.neg();
//      return linearCombination(
//         2*(this.dot(d)/d.dot(d)),d, -1f,this
//      );
   }
   public final ImuV2D coRefl(ImuV2D d) {   //: -this.refl(d)
   //:  this-2*this.proj(d)   
      return new MuV2D().setByCoRefl(this, d); 
//      if(d.normSq()==0f) return new MuV2D(this);
//      return linearCombination(
//         1f,this, 2*(this.dot(d)/d.dot(d)),d
//      );
   }
@Deprecated
public final ImuV2D co_refl(ImuV2D d) { //: DEPRECATED 
      return new MuV2D().setByCoRefl(this, d); 
}

   //----------------
   public final ImuV2D rot90() {  return new MuV2D(-y,x);  }
   public final ImuV2D rotNeg90() {  return new MuV2D(y,-x);  }
      
   public final ImuV2D csRot(double cosA, double sinA) {
      return new MuV2D(x*cosA-y*sinA, x*sinA+y*cosA);
   }
   public final ImuV2D csRot(double cosA, double sinA, ImuV2D center) {
//   return new MuV2D(this).subBy(center).rotBy(angle).addBy(center); 
//     bug!! 萬一center==this就錯了
      return new MuV2D().setByCSRot(this, cosA, sinA, center);
   }
   public final ImuV2D rot(double A) {
      return csRot(cos(A), sin(A));
   }
   public final ImuV2D rot(double A, ImuV2D center) {
//   return new MuV2D(this).subBy(center).rotBy(angle).addBy(center); 
//     bug!! 萬一center==this就錯了
      return csRot(cos(A), sin(A), center);
   }
   public final ImuV2D sinRot(double sinA) { 
      //: 適用場合: 銳角, 且現成的資料是sin(angle)而非angle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1  
      final double cosA=Math.sqrt(1-sinA*sinA);
      return csRot(cosA, sinA);
   }
   public final ImuV2D sinRot(double sinA, ImuV2D center) {
      //: 適用場合: 銳角, 且現成的資料是sin(angle)而非angle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1  
      final double cosA=Math.sqrt(1-sinA*sinA);
      return csRot(cosA, sinA, center);
   }

   public final double angle(ImuV2D till) {
      //[ the angle from this to till, (-PI,+PI]
      final double n1=this.norm(), n2=till.norm();
      if(n1==0.0 || n2==0.0) return 0.0;
      final double dm=this.dot(till)/n1/n2;  
      if(dm>1.0 && dm<1.000001) { 
         //:  fix the fault [ Math.acos(dm) == -1.#IND000000000 ]  
         //:  which appears when this=(12,12),till=(22,22)  
         return 0.0; 
      }
      final double A=tw.fc.Std.a_cos(dm);
      return (this.wedge(till)<0.0)?(-A):A; 
   }

   public boolean convexInside(int orient, ImuV2D[] vertices, int numVer) {
      //: 已知轉向, 檢驗this是否在凸多邊形的內部
      //[ 純量版不使用 free store
      if(orient<Std.epsilon()) return false;
      double v1x, v1y, v2x, v2y;
      for(int i=1; i< numVer; ++i) { 
         v1x=vertices[i].x()-vertices[i-1].x();
         v1y=vertices[i].y()-vertices[i-1].y();
         v2x=this.x()-vertices[i-1].x();
         v2y=this.y()-vertices[i-1].y();
         if(!( (v1x*v2y-v2x*v1y)*orient>0)) return false;
      }  
      v1x=vertices[0].x()-vertices[numVer-1].x();
      v1y=vertices[0].y()-vertices[numVer-1].y();
      v2x=this.x()-vertices[numVer-1].x();
      v2y=this.y()-vertices[numVer-1].y();
      if(!( (v1x*v2y-v2x*v1y)*orient>0)) return false;
      return true;
//    //[ 向量版
//    final MuV2D v1=new MuV2D(), v2=new MuV2D();
//    for(int i=1; i< numVer; ++i) { 
//       v1.setBySub(vertices[i],vertices[i-1]);
//       v2.setBySub(       this,vertices[i-1]);
//       if(!(v1.wedge(v2)*orient>0)) return false;
//    }  
//    v1.setBySub(vertices[0],vertices[numVer-1]);
//    v2.setBySub(       this,vertices[numVer-1]);
//    if(!(v1.wedge(v2)*orient>0)) return false;
//    return true;
   }
   public boolean convexInside(int orient, ImuV2D[] vertices) {
      //: 已知轉向, 檢驗this是否在凸多邊形的內部
      return convexInside(orient, vertices, vertices.length);
   }

   public int convexInside(ImuV2D[] vertices, int numVer) {
   //: 逆時系中this全在左側則+1,全在右側則-1, 否則為0
   //: 順時系中this全在右側則+1,全在左側則-1, 否則為0
      final int orient=detectConvex(vertices, numVer);
      if(convexInside(orient, vertices, numVer)) return orient;
      else return 0;
   }

   public int convexInside(ImuV2D[] vertices) {
   //: 逆時系中this全在左側則+1,全在右側則-1, 否則為0
   //: 順時系中this全在右側則+1,全在左側則-1, 否則為0
      return convexInside(vertices, vertices.length);
   }
   
   public int winding(ImuV2D[] vertices, int numVer) {
   //: 客戶碼可能陣列只用一部份   
      final MuV2D v1=new MuV2D(), v2=new MuV2D();
      double acc=0.0;
      for(int i=1; i< numVer; ++i) { 
         v1.setBySub(vertices[i-1],this);
         v2.setBySub(vertices[i],this);
         acc+=v1.angle(v2);
//D      System.out.print(acc*180/Math.PI);
      }  
      v1.setBySub(vertices[numVer-1],this);
      v2.setBySub(vertices[0],this);
      acc+=v1.angle(v2);
//D   System.out.print(acc*180/Math.PI);
      return (int)Math.round(acc/(2.0*Math.PI)) ;
   }
   public int winding(ImuV2D[] vertices) {
      return winding(vertices, vertices.length);
   }

   //----------
   public ImuV2D linearCoef(ImuV2D v1, ImuV2D v2) throws EpsilonException {   
      //  let  this=h*v1+k*v2,  solve and return (h,k)
      //  ans: h=this^v2/v1^v2, k=v1^this/v1^v2
      final double D=v1.wedge(v2);
      if(Math.abs(D)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("the wedge is epsilonSq");
         new EpsilonException(D, "the wedge is epsilonSq");
      return new MuV2D(this.wedge(v2)/D, v1.wedge(this)/D);
   }
@Deprecated  
public ImuV2D linear_coef(ImuV2D v1, ImuV2D v2){   
      return this.linearCoef(v1, v2);
}

   //---
   public final ImuV2D mul(ImuM2D m2) {  
      // this 視為 1 by 2 矩陣, 做右乘. 
      return m2.swapMul(this);
///      return new MuV2D(
///         x*m2._11 + y*m2._21 ,
///         x*m2._12 + y*m2._22 
///      );    
   }
   public final ImuV2D swapMul(ImuM2D m1) {  // this 視為 2 by 1 矩陣
      return m1.mul(this);
   }

 //[=========   static part  =================================
   public static final ImuV2D ZERO=new ImuV2D(0.0,0.0);
   public static final ImuV2D E1=new ImuV2D(1.0,0.0);
   public static final ImuV2D E2=new ImuV2D(0.0,1.0);

   //-------------------
   public static final ImuV2D linearCombination(
      double a1, ImuV2D v1, double a2, ImuV2D v2
   ) { //: compute a1*v1+a2*v2
      return new MuV2D(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y
      );
   }
   public static final ImuV2D linearCombination(
      double a1, double a2, 
      ImuV2D v1, ImuV2D v2
   ) { //: compute a1*v1+a2*v2
      return new MuV2D(
         a1*v1.x+a2*v2.x,
         a1*v1.y+a2*v2.y
      );
   }
   public static final ImuV2D linearCombination(
      double a1, ImuV2D v1, double a2, ImuV2D v2, double a3, ImuV2D v3
   ) {  //:  a1*v1+a2*v2+a3*v3
      return new MuV2D(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }
   public static final ImuV2D linearCombination(
      double a1, double a2, double a3, 
      ImuV2D v1, ImuV2D v2, ImuV2D v3
   ) {  //:  a1*v1+a2*v2+a3*v3
      return new MuV2D(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }

   public static final ImuV2D linearCombination(
      double[] a, ImuV2D[] v
   ) {  //:  a1*v1+a2*v2+a3*v3
      if(a.length!=v.length) throw 
         new IllegalArgumentException("\n size mismatch");
      final MuV2D ans=new MuV2D();
      for(int i=0; i<v.length; i++) {
         ans.addByMul(a[i],v[i]);
      }
      return ans;
   }

   public static final ImuV2D linePoint(
      ImuV2D p1, ImuV2D p2, double t
   ) {
      return linearCombination(1-t, p1, t, p2);
   }
   public static final ImuV2D midPoint(ImuV2D p1, ImuV2D p2) {  
      return new MuV2D((p1.x+p2.x)*0.5, (p1.y+p2.y)*0.5);   
   }

   public static ImuV2D intersectPara(
      ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D
   ){
      // suppose <AB>, <CD> not parallel
      //  let  <AE>=h<AB>, <CE>=k<CD>,  solve and return (h,k)
      //  ans: h=<AC>^<CD>/<AB>^<CD>, k=<AC>^<AB>/<AB>^<CD>
      //  須h,k都檢查才知道交點在哪一截!
      return new MuV2D().solveIntersectPara(A,B,C,D);
   }
@Deprecated
   public static ImuV2D intersect_para(
      ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D
   ){
      return intersectPara(A,B,C,D);
   }

   public static ImuV2D intersect(
      ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D
   ){   
      return new MuV2D().setByIntersect(A,B,C,D);
   }

   //[ 求二直線交點 (a1)dot(v)=d1, a2(dot)v=d2 
   //[ 非恰一解時傳回null
   public static ImuV2D solve2LineIntersection(
      ImuV2D a1, double d1, ImuV2D a2, double d2
   ) {
      final double D=a1.wedge(a2);
      if(D<Std.epsilonSq()) return null;
      final double Dx= d1*a2.y()-d2*a1.y();
      final double Dy= a1.x()*d2-a2.x()*d1;
      return new ImuV2D(Dx/D, Dy/D); 
   }

   //[ v0, v1 之中垂線
   //     (v1-v0)dot(v-m)=0, m=(v1+v0)/2.
   // 即  (v1-v0)dot(v)=(v1-v0)dot((v1+v0)/2)
   // 即  (v1-v0)dot(v)=((v1)dot(v1)-(v0)dot(v0))/2
   //]

   //[ 2D中,三角形求外心, 或 圓弧求球心
   //[ 呼叫者須檢查函數值是否為null
   public static ImuV2D CircumCenter(ImuV2D v1, ImuV2D v0, ImuV2D v2) {
      final ImuV2D v1_sub_v0=v1.sub(v0), v2_sub_v0=v2.sub(v0); 
      return solve2LineIntersection(
                v1_sub_v0, (v1.normSq()-v0.normSq())/2,
                v2_sub_v0, (v2.normSq()-v0.normSq())/2
             );
   }


   public static int detectConvex(ImuV2D[] vertices, int numVer) { 
      //: 偵測是否為凸多邊形並判定轉向, 可能只用一部份的陣列.   
      //: 逆時針座標系中全左轉為+1,全右轉為-1, 否則為0
      //: 順時針座標系中全右轉為+1,全左轉為-1, 否則為0
      //[ v[0], v[1], ... v[n-1]
      final MuV2D v0=new MuV2D(), v1=new MuV2D();
      v0.setBySub(vertices[0],vertices[numVer-1]);
      v1.setBySub(vertices[1],vertices[0]);
      double w=v0.wedge(v1);
      if(w<Std.epsilonSq()) {  return 0;   }
      final int orient=(w>0)? 1: -1;
      //[ check else angles
      for(int i=1; i<=numVer-2; i++) {
         v0.setBy(v1);
         v1.setBySub(vertices[i+1], vertices[i]);
         w=v0.wedge(v1);
         if(w<Std.epsilonSq() || orient*w<0) {
            return 0;
         }
      }
      v0.setBy(v1);
      v1.setBySub(vertices[0], vertices[numVer-1]);
      w=v0.wedge(v1);
      if(w<Std.epsilonSq() || orient*w<0) {
         return 0;
      }
      return orient;
   }
   public static int detectConvex(ImuV2D[] vertices) { 
      return detectConvex(vertices, vertices.length);
   }

   //[ 判斷this是否在基底三角形的內部 
   public final boolean insideBasicTriangle() {
      final double x=this.x(), y=this.y(); 
      return (0<=x && 0<=y && x+y<1);
   }

   //[ 判斷(x,y)是否在基底三角形的內部 
   public static final boolean insideBasicTriangle(double x, double y) {
      return (0<=x && 0<=y && x+y<1);
   }

   //[ 判斷this是否在基底正方形的內部 
   public final boolean insideBasicSquare() {
      final double x=this.x(), y=this.y(); 
      return (0<=x && 0<=y && x<1 && y<1);
   }

   //[ 判斷(x,y)是否在基底正方形的內部 
   public final static boolean insideBasicSquare(double x, double y) {
      return (0<=x && 0<=y && x<1 && y<1);
   }

/*
F:\_upload\Prof_Liaw\GraphicMathWorking\ProgCh07.952.982\
RegPolygon.java
   boolean inside(ImuV2D at) {
//    return at.distance(_center)<_R;  //: 暫用外接圓    
      final ImuV2D pressV=at.sub(RegPlg_center);
      final double pressA=
         Math.atan2(pressV.y(), pressV.x());
      final double dA=2.0*Math.PI/RegPlg_vertexNo;
      final double q=(int)Math.floor(
         (pressA-RegPlg_angle)/dA
      );
      final double bA=RegPlg_angle+q*dA;
      final ImuV2D v0=
         new ImuV2D(RegPlg_R*cos(bA),RegPlg_R*sin(bA)); 
      final ImuV2D v1=
         new ImuV2D(RegPlg_R*cos(bA+dA),RegPlg_R*sin(bA+dA)); 
      return v1.sub(v0).wedge(pressV.sub(v0))>0;
   }
*/

   //[ 判斷this是否在標準正n邊形(中心在原點, 某頂點在(1,0))的內部 
   public final boolean insideStdRegPolygon(int n) {
  //D tw.fc.Std.cout.setDecimalPattern("#.##").p("at:").pn(this);
      if(n<3) throw new IllegalArgumentException("\npolygon side number: "+n);
      final double atA= Math.atan2(this.y(), this.x());
      final double dA=2.0*Math.PI/n;
      final double q=(int)Math.floor(atA/dA);
      final double bA=q*dA;  //: base angle 
      final ImuV2D v0= new ImuV2D(cos(bA),sin(bA)); 
      final ImuV2D v1= new ImuV2D(cos(bA+dA),sin(bA+dA)); 
  //D tw.fc.Std.cout.setDecimalPattern("#.##").p("v0:").pcs(v0).p("v1:").pn(v1);
      final double w= v1.sub(v0).wedge(this.sub(v0));
  //D tw.fc.Std.cout.pn(w);
      return w>0;
   }

   //[ 判斷this是否在正n邊形(中心在原點, v0是某頂點)的內部 
   public final boolean insideRegPolygon(int n, ImuV2D v0) { 
  //D tw.fc.Std.cout.pcs(n);
  //D tw.fc.Std.cout.setDecimalPattern("#.##").pcs(this).pn(v0);
      final double D=v0.normSq();
      return new ImuV2D(this.dot(v0)/D, v0.wedge(this)/D)
                                    .insideStdRegPolygon(n);
   }

//   //[ 怕亂, A10629 取消 static版
//   //[ 判斷at是否在標準正n邊形(中心在原點, 某頂點在(1,0))的內部 
//   public static final boolean insideStdRegPolygon(int n, ImuV2D at) {
//      return at.insideStdRegPolygon(n);
//   }
//
//   //[ 怕亂, A10629 取消 static版
//   //[ 判斷at是否在正n邊形(中心在原點, v0是某頂點)的內部 
//   public static final boolean insideRegPolygon(
//      int n, ImuV2D at, ImuV2D relE1 
//   ) {
//      return at.insideRegPolygon(n, relE1);
//   }

   //]=========   static part  =================================


   // A21121 xie
   public final boolean hasNaN() {
      return Double.isNaN(x)||Double.isNaN(y);
   }
   public final boolean hasInfinite() { //: +-infinity
      return Double.isInfinite(x)||Double.isInfinite(y);
   } 
   public final boolean abNormal() { //: +NaN or -infinity
      return Double.isNaN(x)||Double.isNaN(y)||
             Double.isInfinite(x)||Double.isInfinite(y);
   }
}
