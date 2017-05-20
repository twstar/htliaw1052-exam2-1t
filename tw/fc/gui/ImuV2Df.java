package tw.fc.gui;

import java.io.Serializable;

import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.WidthPrintableI;
import tw.fc.TxOStream;
import tw.fc.Std;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;

//**************    ImuV2Df.java    ********************
//
//floating-point 2D vector
//
public class ImuV2Df
implements DuplicableI<MuV2Df>, PrintableI, WidthPrintableI, Serializable
{
   private static final long serialVersionUID = 2014120809L;
   
   float x, y;

   //------------------------------------------------
   public ImuV2Df() {  x=0f; y=0f; }
   public ImuV2Df(float xx, float yy) {  x=xx; y=yy; }
   public ImuV2Df(ImuV2Df src) {  x=src.x;  y=src.y;  }
   //public   ImuV2Df(ImuV2Df src) { x=src.x;  y=src.y; }
   //public   ImuV2Df(ImuV2Di src) { x=src.x;  y=src.y; }
   public   ImuV2Df(ImuXY src) { x=src.x;  y=src.y;  }
   //public   ImuV2Df(ImuP2D src) { //: ?y?y??????y??
   //
   //}

   public ImuV2Df(ImuP2Df src) { //: 極座標轉正交座標
      x=(float) (src.radius*cos(src.angle));
      y=(float) (src.radius*sin(src.angle));      
   }

   public ImuV2Df(java.awt.event.MouseEvent e) {
      x=e.getX(); y=e.getY();
   }

   //-----------------------------------------------
   public final float x() {  return x; }
   public final float y() {  return y; }
   public final float comp(int axis) {  //: A5.039L.J add
      switch(axis) {
      case 0:  return x;
      case 1:  return y;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }
   public final int ix() {  return (int)round(x); }
   public final int iy() {  return (int)round(y); }
   public final MuXY toXY() {  return new MuXY(ix(),iy()); }
   //public MuP3D toP2D() {  return new MuP2D(this);  }

   public String toString() { return "(" + x + ", " + y + ")" ;  }
   public final boolean equals(ImuV2Df v2) { return (x==v2.x && y==v2.y);  }
   public final boolean equals(ImuV2Df v2, float errSq) {
      final float dx=x-v2.x, dy=y-v2.y;
      return (dx*dx+dy*dy<errSq) ;
   }
   public final boolean notEquals(ImuV2Df v2) { return !equals(v2);  }
   public final boolean notEquals(ImuV2Df v2, float errSq) {
      return !equals(v2, errSq);
   }
   public final boolean equals(Object v2) {
      return equals((ImuV2Df)v2);
   }
   public final int hashCode() {
      long tL; int tI=17;  // as is recommended in "Effective Java"
      tL=Double.doubleToLongBits(x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(y); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero() {  return (x==0 && y==0);  }
   public final boolean isZero(float errSq) {
      return (x*x+y*y<errSq);
   }
   public final boolean notZero() {  return (x!=0 || y!=0);  }
   public final boolean notZero(float errSq) {  return !isZero();  }

   //[ -----------  implement DuplicableI
   public final MuV2Df duplicate() { return new MuV2Df(this); }
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

   public final ImuV2Df add(ImuV2Df v2) { return new MuV2Df(x+v2.x, y+v2.y); }
   public final ImuV2Df sub(ImuV2Df v2) { return new MuV2Df(x-v2.x, y-v2.y); }
   public final ImuV2Df add(ImuXY v2) { return new MuV2Df(x+v2.x, y+v2.y); }
   public final ImuV2Df sub(ImuXY v2) { return new MuV2Df(x-v2.x, y-v2.y); }

   public final ImuV2Df neg() {  return new MuV2Df(-x,-y); }
   public final ImuV2Df xNeg() {  return new MuV2Df(-x,y); }
   public final ImuV2Df yNeg() {  return new MuV2Df(x,-y); }

   public final ImuV2Df mul(float k) {  //:  scalar product
      return new MuV2Df(x*k, y*k);
   }
   //[ ???y??mul
   public final ImuV2Df sMul(float k) {  //:  scalar product
      return new MuV2Df(x*k, y*k);
   }
   @Deprecated
   public final ImuV2Df smul(float k) {  //:  scalar product
      return  new MuV2Df(x*k, y*k);
   }
   public final ImuV2Df div(float k) {  //:  scalar product
      if(k==0) throw new IllegalArgumentException("divided by 0");
      return new MuV2Df(x/k, y/k);
   }

   public final float dot(ImuV2Df v2) {  //:  dot product
      return this.x*v2.x+ this.y*v2.y;
   }
   @Deprecated
   public final float dotMul(ImuV2Df v2) {  //:  dot product
      return this.x*v2.x+ this.y*v2.y;
   }
   @Deprecated
   public final float dotmul(ImuV2Df v2) {  //:  dot product
      return x*v2.x+ y*v2.y;
   }
   public final float wedge(ImuV2Df v2) {  //:  wedge product
      return (this.x*v2.y-this.y*v2.x);
   }
   @Deprecated
   public final float wedgeMul(ImuV2Df v2) {  //:  wedge product
      return (x*v2.y-y*v2.x);
   }
   @Deprecated
   public final float wedgemul(ImuV2Df v2) {  //:  wedge product
      return (x*v2.y-y*v2.x);
   }
   //]

   public final float normSq() {  return (float)(x*x+y*y); }
   public final float norm() {  return (float)sqrt(x*x+y*y); }
   public final ImuV2Df direction() {                         //: ??V?V?q
      final float n=this.norm();
      return (n==0.0)? new MuV2Df(0,0) : new MuV2Df(x/n, y/n);
   }
   public final float distanceSq(ImuV2Df v) {
      final float dx=x-v.x, dy=y-v.y;
      return (dx*dx+dy*dy);
   }
   public final float distance(ImuV2Df v) {
      final float dx=x-v.x, dy=y-v.y;
      return (float)sqrt(dx*dx+dy*dy);
   }
   public final float distanceSq(float x, float y) {
      final float dx=this.x-x, dy=this.y-y;
      return dx*dx+dy*dy;
   }
   public final float distance(float x, float y) {
      final float dx=this.x-x, dy=this.y-y;
      return (float)sqrt(dx*dx+dy*dy);
   }
   public final float lineDistance(ImuV2Df A, ImuV2Df B) {                   //: ?Ithis ?? ???uAB ???Z??
      // return the distance from this to line AB
      final ImuV2Df vAB=B.sub(A);
      final float n=vAB.norm();
      if(n==0) return this.distance(A); //: A?PB???|
      // final float R=vAT.coProj(vAB).norm();
      return Math.abs(vAB.wedge(this.sub(A))/n);
   }

   public final ImuV2Df proj(ImuV2Df d) {  //:  project into direction d      //: ??v?V?q
      /////      if(d.normSq()==0) return new MuV2Df(0,0);
      /////      return d.mul(this.dot(d)/d.dot(d));
      return new MuV2Df().setByProj(this, d);
   }
   public final float comp(ImuV2Df d) {
   //:  component of project into direction d
      if(d.normSq()<Std.epsilonSq()) return 0.0f;
      return this.dot(d)/d.norm();
   }



   public final ImuV2Df coProj(ImuV2Df d) {                                   //: ?l??v?V?q
      //:  this-proj(this,d)
      return new MuV2Df().setByCoProj(this, d);
      //   if(d.normSq()==0) return new MuV2Df(this);
      //   return linearCombination(
      //      1d, this, -(this.dot(d)/d.dot(d)), d
      //   );
   }
   @Deprecated
   public final ImuV2Df co_proj(ImuV2Df d) {  //: DEPRECATED
      return new MuV2Df().setByCoProj(this, d);
   }
   public final ImuV2Df refl(ImuV2Df d) { //:  reflect point of this wrt d     //: ??g?V?q
      //:   2*this.proj(d)-this
      return new MuV2Df().setByRefl(this, d);
      //   if(d.normSq()==0d) return this.neg();
      //   return linearCombination(
      //      2*(this.dot(d)/d.dot(d)),d, -1f,this
      //   );
   }
   public final ImuV2Df coRefl(ImuV2Df d) {   //: -this.refl(d)                //: ?l??g?V?q
      //:  this-2*this.proj(d)
      return new MuV2Df().setByCoRefl(this, d);
      //   if(d.normSq()==0f) return new MuV2Df(this);
      //   return linearCombination(
      //      1f,this, 2*(this.dot(d)/d.dot(d)),d
      //   );
   }
   @Deprecated
   public final ImuV2Df co_refl(ImuV2Df d) { //: DEPRECATED
      return new MuV2Df().setByCoRefl(this, d);
   }

   //----------------
   public final ImuV2Df rot90() {  return new MuV2Df(-y,x);  }                  //: ????90??
   public final ImuV2Df rotNeg90() {  return new MuV2Df(y,-x);  }               //: ????90??

   public final ImuV2Df csRot(float cosA, float sinA) {                      //: ????, ?w?? cosA?M sinA
      return new MuV2Df(x*cosA-y*sinA, x*sinA+y*cosA);
   }
   public final ImuV2Df csRot(float cosA, float sinA, ImuV2Df center) {       //: ????, ???I?? center, ?w?? cosA?M sinA
      //return new MuV2Df(this).subBy(center).rotBy(angle).addBy(center);
      //  bug!! ?U?@center==this?N??F
      return new MuV2Df().setByCSRot(this, cosA, sinA, center);
   }
   public final ImuV2Df rot(float A) {                                        //: ???? A??
      return csRot((float)cos(A), (float)sin(A));
   }
   public final ImuV2Df rot(float A, ImuV2Df center) {                         //: ???? A??, ???I?? center
      //return new MuV2Df(this).subBy(center).rotBy(angle).addBy(center);
      //  bug!! ?U?@center==this?N??F
      return csRot((float)cos(A), (float)sin(A), center);
   }
   public final ImuV2Df sinRot(float sinA) {                                  //: ???? sin(A), ??T???@?? rot()??o?C, ?e???O angle???U??! ?Y???w?? sin?禡?L?k?????t??, ??X??cos(A)?N?|??.
      //: ?A?γ??X: ?U??, ?B?{???????Osin(angle)??Dangle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1
      final float cosA=(float)Math.sqrt(1-sinA*sinA);
      return csRot(cosA, sinA);
   }
   public final ImuV2Df sinRot(float sinA, ImuV2Df center) {                   //: ???? sin(A), ???I?? center
      //: ?A?γ??X: ?U??, ?B?{???????Osin(angle)??Dangle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1
      final float cosA=(float)Math.sqrt(1-sinA*sinA);
      return csRot(cosA, sinA, center);
   }

   public final float angle(ImuV2Df till) {                                    //: ?D this?P till??????????
      //[ the angle from this to till, (-PI,+PI]
      final float n1=this.norm(), n2=till.norm();
      if(n1==0f || n2==0f) return 0f;
      final float dm=this.dot(till)/n1/n2;
      if(dm>1.0 && dm<1.000001) {
         //:  fix the fault [ Math.acos(dm) == -1.#IND000000000 ]
         //:  which appears when this=(12,12),till=(22,22)
         return 0f;
      }
      final float A=(float)tw.fc.Std.a_cos(dm);
      return (this.wedge(till)<0f)?(-A):A; //:?H wedge produce?P?_??????V
   }

   public boolean convexInside(int orient, ImuV2Df[] vertices, int numVer) {     //: orient???y??t??V
      //: ?w????V, ????this?O?_?b?Y?h????????
      //[ ??q??????? free store
      if(orient==0f) return false;
      float v1x, v1y, v2x, v2y;
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
      if(!( (v1x*v2y-v2x*v1y)*orient>0)) return false;     //: v1.wedge(v2)?P orient???P??V?h?^?? false
      return true;
      // //[ ?V?q??
      // final MuV2Df v1=new MuV2Df(), v2=new MuV2Df();
      // for(int i=1; i< numVer; ++i) {
      //    v1.setBySub(vertices[i],vertices[i-1]);
      //    v2.setBySub(       this,vertices[i-1]);
      //    if(!(v1.wedge(v2)*orient>0)) return false;
      // }
      // v1.setBySub(vertices[0],vertices[numVer-1]);
      // v2.setBySub(       this,vertices[numVer-1]);
      // if(!(v1.wedge(v2)*orient>0)) return false;
      // return true;
   }
   public boolean convexInside(int orient, ImuV2Df[] vertices) {
      //: ?w????V, ????this?O?_?b?Y?h????????
      return convexInside(orient, vertices, vertices.length);
   }

   public int convexInside(ImuV2Df[] vertices, int numVer) {
      //: ?f??t??this???b?????h+1,???b?k???h-1, ?_?h??0
      //: ????t??this???b?k???h+1,???b?????h-1, ?_?h??0
      final int orient=detectConvex(vertices, numVer);
      if(convexInside(orient, vertices, numVer)) return orient;
      else return 0;
   }

   public int convexInside(ImuV2Df[] vertices) {
      //: ?f??t??this???b?????h+1,???b?k???h-1, ?_?h??0
      //: ????t??this???b?k???h+1,???b?????h-1, ?_?h??0
      return convexInside(vertices, vertices.length);
   }

   public int winding(ImuV2Df[] vertices, int numVer) {                            //: ?p???????
      //: ???X?i??}?C?u?Τ@????
      final MuV2Df v1=new MuV2Df(), v2=new MuV2Df();
      float acc=0f;                              //: ?b??q
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
      return (int)Math.round(acc/(2.0*Math.PI)) ;  //: ?????
   }
   public int winding(ImuV2Df[] vertices) {
      return winding(vertices, vertices.length);
   }

   //----------
   public ImuV2Df linearCoef(ImuV2Df v1, ImuV2Df v2) {                                //: ?? wedge product?D?u???X???Y?? h,k
      //  let  this=h*v1+k*v2,  solve and return (h,k)
      //  ans: h=this^v2/v1^v2, k=v1^this/v1^v2
      final float D=v1.wedge(v2);
      if(D==0) throw new IllegalArgumentException("the wedge is zero");
      return new MuV2Df(this.wedge(v2)/D, v1.wedge(this)/D);
   }
   @Deprecated
   public ImuV2Df linear_coef(ImuV2Df v1, ImuV2Df v2) {
      return this.linearCoef(v1, v2);
   }

   //[=========   static part  =================================
   public static final ImuV2Df ZERO=new ImuV2Df(0f,0f);
   public static final ImuV2Df E1=new ImuV2Df(1f,0f);
   public static final ImuV2Df E2=new ImuV2Df(0f,1f);

   //-------------------
   public static final ImuV2Df linearCombination(                                    //: ?u???X
         float a1, ImuV2Df v1, float a2, ImuV2Df v2
         ) { //: compute a1*v1+a2*v2
      return new MuV2Df(
            a1*v1.x+a2*v2.x,
            a1*v1.y+a2*v2.y
            );
   }
   public static final ImuV2Df linearCombination(                                    //: ?u???X
         float a1, float a2,
         ImuV2Df v1, ImuV2Df v2
         ) { //: compute a1*v1+a2*v2
      return new MuV2Df(
            a1*v1.x+a2*v2.x,
            a1*v1.y+a2*v2.y
            );
   }
   public static final ImuV2Df linearCombination(                                    //: ?u???X
         float a1, ImuV2Df v1, float a2, ImuV2Df v2, float a3, ImuV2Df v3
         ) {  //:  a1*v1+a2*v2+a3*v3
      return new MuV2Df(
            a1*v1.x+a2*v2.x+a3*v3.x,
            a1*v1.y+a2*v2.y+a3*v3.y
            );
   }
   public static final ImuV2Df linearCombination(                                    //: ?u???X
         float a1, float a2, float a3,
         ImuV2Df v1, ImuV2Df v2, ImuV2Df v3
         ) {  //:  a1*v1+a2*v2+a3*v3
      return new MuV2Df(
            a1*v1.x+a2*v2.x+a3*v3.x,
            a1*v1.y+a2*v2.y+a3*v3.y
            );
   }

   public static final ImuV2Df linearCombination(                                    //: ?u???X
         float[] a, ImuV2Df[] v
         ) {  //:  a1*v1+a2*v2+a3*v3
      if(a.length!=v.length) throw
      new IllegalArgumentException("\n size mismatch");
      final MuV2Df ans=new MuV2Df();
      for(int i=0; i<v.length; i++) {
         ans.addByMul(a[i],v[i]);
      }
      return ans;
   }

   public static final ImuV2Df linePoint(                                            //: ????I
         ImuV2Df p1, ImuV2Df p2, float t
         ) {
      return linearCombination(1-t, p1, t, p2);
   }
   public static final ImuV2Df midPoint(ImuV2Df p1, ImuV2Df p2) {
      return new MuV2Df((float)((p1.x+p2.x)*0.5), (float)((p1.y+p2.y)*0.5));
   }

   public static ImuV2Df intersectPara(                                              //: ?D???u(AB, CD)???I?? h,k?Y??
         ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D
         ){
      // suppose <AB>, <CD> not parallel
      //  let  <AE>=h<AB>, <CE>=k<CD>,  solve and return (h,k)
      //  ans: h=<AC>^<CD>/<AB>^<CD>, k=<AC>^<AB>/<AB>^<CD>
      //  ??h,k????d?~???D???I?b???@?I!
      return new MuV2Df().solveIntersectPara(A,B,C,D);
   }
   @Deprecated
   public static ImuV2Df intersect_para(
         ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D
         ){
      return intersectPara(A,B,C,D);
   }

   public static ImuV2Df intersect(                                                  //: ?D AB, CD???u???I
         ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D
         ){
      return new MuV2Df().setByIntersect(A,B,C,D);
   }

   //[ ?D?G???u???I (a1)dot(v)=d1, (a2)dot(v)=d2      (??1)
   //[ ?D??@????^null
   //[ ?] v=(x,y,z) a1=(a1x,a1y,a1z) a2=(a2x,a2y,a2z)
   //[ ?N?J??1??, ?i?}?o?G???@????{??, ?b?H??????D?o x,y,
   public static ImuV2Df solve2LineIntersection(                                      //: ??????L?{?i??? ImuV3D???O.
         ImuV2Df a1, float d1, ImuV2Df a2, float d2
         ) {
      final float D=a1.wedge(a2);
      if(D==0) return null;
      final float Dx= d1*a2.y()-d2*a1.y();
      final float Dy= a1.x()*d2-a2.x()*d1;
      return new ImuV2Df(Dx/D, Dy/D);
   }

   //[ v0, v1 ???????u
   //     (v1-v0)dot(v-m)=0, m=(v1+v0)/2.
   // ?Y  (v1-v0)dot(v)=(v1-v0)dot((v1+v0)/2)
   // ?Y  (v1-v0)dot(v)=((v1)dot(v1)-(v0)dot(v0))/2
   //]

   //[ 2D??,?T???ΨD?~??, ?? ???D?y??
   //[ ?I?s?????d????O?_??null
   public static ImuV2Df CircumCenter(ImuV2Df v1, ImuV2Df v0, ImuV2Df v2) {               //: ?Dv1, v0, v2?~??
      final ImuV2Df v1_sub_v0=v1.sub(v0), v2_sub_v0=v2.sub(v0);
      return solve2LineIntersection(
            v1_sub_v0, (v1.normSq()-v0.normSq())/2, //: v1_sub_v0?Ev0_sub_?~?? = ||v1_sub_v0||^2 / 2
            v2_sub_v0, (v2.normSq()-v0.normSq())/2
            );
   }


   public static int detectConvex(ImuV2Df[] vertices, int numVer) {
      //: ????O?_???Y?h??ΨcP?w??V, ?i??u?Τ@???????}?C.
      //: ?f??w?y??t????????+1,???k??-1, ?_?h??0
      //: ????w?y??t?????k??+1,??????-1, ?_?h??0
      //[ v[0], v[1], ... v[n-1]
      final MuV2Df v0=new MuV2Df(), v1=new MuV2Df();
      v0.setBySub(vertices[0],vertices[numVer-1]);
      v1.setBySub(vertices[1],vertices[0]);
      float w=v0.wedge(v1);
      if(w==0) {  return 0;   }
      final int orient=(w>0)? 1: -1;               //: ?? v0.wedge(v1)?P?_?y??t??V
      //[ check else angles
      for(int i=1; i<=numVer-2; i++) {
         v0.setBy(v1);
         v1.setBySub(vertices[i+1], vertices[i]);
         w=v0.wedge(v1);
         if(w==0 || orient*w<0) {                  //: ?Q?? orient?P?_ w?O?_??????V, ????V?N??Y?h??, ????h?D return 0
            return 0;
         }
      }
      v0.setBy(v1);
      v1.setBySub(vertices[0], vertices[numVer-1]);
      w=v0.wedge(v1);
      if(w==0 || orient*w<0) {
         return 0;
      }
      return orient;
   }
   public static int detectConvex(ImuV2Df[] vertices) {
      return detectConvex(vertices, vertices.length);
   }

   //[ ?P?_this?O?_?b???T?????????
   public final boolean insideBasicTriangle() {                                         //: x(1,0) + y(0,1) x+y<1
      final float x=this.x(), y=this.y();
      return (0<=x && 0<=y && x+y<1);
   }

   //[ ?P?_(x,y)?O?_?b???T?????????
   public static final boolean insideBasicTriangle(float x, float y) {
      return (0<=x && 0<=y && x+y<1);
   }

   //[ ?P?_this?O?_?b????????????
   public final boolean insideBasicSquare() {                                           //: x(1,0) + y(0,1) 0<=x<=1, 0<=y<=1
      final float x=this.x(), y=this.y();
      return (0<=x && 0<=y && x<1 && y<1);
   }

   //[ ?P?_(x,y)?O?_?b????????????
   public final static boolean insideBasicSquare(float x, float y) {
      return (0<=x && 0<=y && x<1 && y<1);
   }

   /*
F:\_upload\Prof_Liaw\GraphicMathWorking\ProgCh07.952.982\
RegPolygon.java
boolean inside(ImuV2Df at) {
// return at.distance(_center)<_R;  //: ??Υ~????
   final ImuV2Df pressV=at.sub(RegPlg_center);
   final float pressA=
      Math.atan2(pressV.y(), pressV.x());
   final float dA=2.0*Math.PI/RegPlg_vertexNo;
   final float q=(int)Math.floor(
      (pressA-RegPlg_angle)/dA
   );
   final float bA=RegPlg_angle+q*dA;
   final ImuV2Df v0=
      new ImuV2Df(RegPlg_R*cos(bA),RegPlg_R*sin(bA));
   final ImuV2Df v1=
      new ImuV2Df(RegPlg_R*cos(bA+dA),RegPlg_R*sin(bA+dA));
   return v1.sub(v0).wedge(pressV.sub(v0))>0;
}
    */

   //[ ?P?_this?O?_?b????n???(????b???I, ?Y???I?b(1,0))??????
   public final boolean insideStdRegPolygon(int n) {
      //D tw.fc.Std.cout.setDecimalPattern("#.##").p("at:").pn(this);
      if(n<3) throw new IllegalArgumentException("\npolygon side number: "+n); //: ???u
      final float atA= (float)Math.atan2(this.y(), this.x());                        //: ?D(x, y)?T?? +PI~-PI
      final float dA=(float)(2f*Math.PI/n);
      final float q=(int)Math.floor(atA/dA);
      final float bA=q*dA;  //: base angle
      final ImuV2Df v0= new ImuV2Df((float)cos(bA),(float)sin(bA));                            //: base angle???V?q
      final ImuV2Df v1= new ImuV2Df((float)cos(bA+dA),(float)sin(bA+dA));                      //: bA+dA ???V?q
      //D tw.fc.Std.cout.setDecimalPattern("#.##").p("v0:").pcs(v0).p("v1:").pn(v1);
      final float w= v1.sub(v0).wedge(this.sub(v0));                          //: ?H?L?n?P?_ this????V
      //D tw.fc.Std.cout.pn(w);
      return w>0;                                                              //: ?L?n?????h?b????
   }

   //[ ?P?_this?O?_?b??n???(????b???I, v0?O?Y???I)??????
   public final boolean insideRegPolygon(int n, ImuV2Df v0) {                             //: v0?????N?V?q, this = (h?? v0.x, k?? v0.y), at = (h, k)
      //D tw.fc.Std.cout.pcs(n);
      //D tw.fc.Std.cout.setDecimalPattern("#.##").pcs(this).pn(v0);
      final float D=v0.normSq();
      //return this.insideStdRegPolygon( //: A31208X insideStdRegPolygon($$) is static method
      return insideStdRegPolygon(
            n, new ImuV2Df(this.dot(v0)/D, v0.wedge(this)/D)               //:v(at) = ( Comp(this,v0)/||v0|| , Distance(this,v0)/||v0||:?????t?? )
            );
   }

   //[ ?P?_at?O?_?b????n???(????b???I, ?Y???I?b(1,0))??????
   public static final boolean insideStdRegPolygon(int n, ImuV2Df at) {
      return at.insideStdRegPolygon(n);
   }

   //[ ?P?_at?O?_?b??n???(????b???I, v0?O?Y???I)??????
   public static final boolean insideRegPolygon(
         int n, ImuV2Df at, ImuV2Df relE1
         ) {
      return at.insideRegPolygon(n, relE1);
   }

   //]=========   static part  =================================

}

