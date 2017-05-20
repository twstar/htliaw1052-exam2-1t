package tw.fc.gui;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
//import tw.fc.TxOStream;
import tw.fc.Std;
//import tw.fc.EpsilonException;
//import static java.lang.Math.sqrt;

//[ mutable row-type affine matrix for 3D transformation
//[
public class MuAr3D extends ImuAr3D
{

   //----------------------------------
   public     MuAr3D() {  super();   }

   public     MuAr3D(ImuM3D baseMtx, ImuV3D orgVec) {
      super(baseMtx,orgVec);
   }

   public     MuAr3D(ImuV3D b1, ImuV3D b2, ImuV3D b3, ImuV3D c) {
      super(b1, b2, b3, c);
   }

   public     MuAr3D(
      double b11, double b12, double b13,
      double b21, double b22, double b23,
      double b31, double b32, double b33,
      double c1,  double c2,  double c3
   ) {
      super(
         b11, b12, b13,
         b21, b22, b23,
         b31, b32, b33,
          c1,  c2,  c3
      );
   }

   public     MuAr3D(ImuV3D c) {  super(c);   }

   public     MuAr3D(double c1, double c2, double c3) {
      super(c1, c2, c3);
   }

   public     MuAr3D(ImuM3D baseMtx) {
      super(baseMtx);
   }

   public     MuAr3D(ImuV3D b1, ImuV3D b2, ImuV3D b3) {
      super(b1, b2, b3);
   }

   public     MuAr3D(
      double b11, double b12, double b13,
      double b21, double b22, double b23,
      double b31, double b32, double b33
   ) {
      super(
         b11, b12, b13,
         b21, b22, b23,
         b31, b32, b33
      );
   }

   public MuAr3D(ImuAr3D src) {  super(src);  }

   //----------------------------------
   public MuAr3D setBy(ImuAr3D src) {
      super._vec.setBy(src._vec);   super._mtx.setBy(src._mtx);
   	  return this;
   }
   public MuAr3D setBy(ImuM3D m, ImuV3D v) {
      super._vec.setBy(v);   super._mtx.setBy(m);
   	  return this;
   }
   public MuAr3D setBy(ImuV3D b1, ImuV3D b2, ImuV3D b3, ImuV3D c) {
      super._vec.setBy(c);   super._mtx.setBy(b1, b2, b3);
   	  return this;
   }
   public MuAr3D setBy(
      double b11, double b12, double b13,
      double b21, double b22, double b23,
      double b31, double b32, double b33,
      double c1,  double c2,  double c3
   ){
      super._vec.setBy(c1, c2, c3);   
      super._mtx.setBy(
         b11, b12, b13,
         b21, b22, b23,
         b31, b32, b33
      );
   	  return this;
   }

   @Deprecated public MuV3D vec() {  return super._vec;  }
//   @Override public final MuV3D center() {  return super._vec;  }
   @Override public final MuV3D org() {  return super._vec;  }
   @Override public final MuM3D mtx() {  return super._mtx;  }
   @Override public MuV3D axis1() {  return _mtx._r1; }
   @Override public MuV3D axis2() {  return _mtx._r2; }
   @Override public MuV3D axis3() {  return _mtx._r3; }
//   @Override public ImuV3D negAxis1() {  return _mtx.negRow1(); }
//   @Override public ImuV3D negAxis2() {  return _mtx.negRow2(); }
//   @Override public ImuV3D negAxis3() {  return _mtx.negRow3(); }

   public final void replaceMtx(MuM3D m) { super._mtx=m; }

   public final MuAr3D orgSetBy(ImuV3D v) {
   	  super._vec.setBy(v);   return this;
   }
//   public final MuAr3D centerSetBy(ImuV3D v) {  return orgSetBy(v);   }
   public final MuAr3D orgSetBy(double x, double y, double z) {
//   public final MuAr3D centerSetBy(double x, double y, double z) {
   	  super._vec.setBy(x, y, z);   return this;
   }
   public final MuAr3D orgAddBy(ImuV3D v) {
   	  super._vec.addBy(v);   return this;
   }
//   public final MuAr3D centerAddBy(ImuV3D v) {  return orgAddBy(v);  }
   public final MuAr3D orgAddBy(double x, double y, double z) {
//   public final MuAr3D centerAddBy(double x, double y, double z) {
   	  super._vec.addBy(x, y, z);   return this;
   }
   public final MuAr3D orgAddByMul(double k, ImuV3D v) {
   	  super._vec.addByMul(k, v);   return this;
   }

   public final MuAr3D axesSetBy(ImuM3D m) {
   	  super._mtx.setBy(m);   return this;
   }
   public final MuAr3D axis1SetBy(ImuV3D v) {
   	  super._mtx.row1SetBy(v);   return this;
   }
   public final MuAr3D axis2SetBy(ImuV3D v) {
   	  super._mtx.row2SetBy(v);   return this;
   }
   public final MuAr3D axis3SetBy(ImuV3D v) {
   	  super._mtx.row3SetBy(v);   return this;
   }

   public final MuAr3D axesMulBy(double k) {
   	  super._mtx.mulBy(k);   return this;
   }
   public final MuAr3D axis1MulBy(double k) {
   	  super._mtx.row1MulBy(k);   return this;
   }
   public final MuAr3D axis2MulBy(double k) {
   	  super._mtx.row2MulBy(k);   return this;
   }
   public final MuAr3D axis3MulBy(double k) {
   	  super._mtx.row3MulBy(k);   return this;
   }

   public final MuAr3D mulBy(ImuAr3D A2) { //: this := this*A2
   //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
   //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      this._vec.mulBy(A2._mtx).addBy(A2._vec);
      this._mtx.mulBy(A2._mtx);
      return this;
   }
   public final MuAr3D swapMulBy(ImuAr3D A1) { //:  this:= A1*this
   //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
   //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      this._vec.addByMul(A1._vec,this._mtx); //! 下式M2會變, 須在下式之前
      this._mtx.setByMul(A1._mtx,this._mtx);
      return this;
   }
   public final MuAr3D setByMul(ImuAr3D A1, ImuAr3D A2) { //:  this:= A1*A2
   //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
   //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
   //!!  POSSIBLE A1 or A2 is equals to this
   //!!  _vec or _mul may be changed before use
      final ImuV3D old_v2=(A2!=this)? A2._vec: new ImuV3D(A2._vec);
      this._vec.setByMul( A1._vec, A2._mtx ).addBy(old_v2); //! 下式M會變, 須在下式之前
      this._mtx.setByMul(  A1._mtx, A2._mtx  );
      return this;
   }

   public final MuAr3D invert() {                        // 未測
   //  [ I   o ] = [ M1  o ] [ M2  o ] = [ M1*M2     o ]
   //  [ o   1 ]   [ v1  1 ] [ v2  1 ]   [ v1*M2+v2  1 ]
   //  i.e.    I=M1*M2,  o=v1*M2+v2
   //  so,     M2=M1.inverse(),  v2=-v1*M2=-v1*M1.inverse()  
   //  also,   M1=M2.inverse(),  v1=-v2*M2.inverse() 
   //  [ M2   o ] = [ M1  o ].inverse() = [ M1.inverse()      o ]
   //  [ v2   1 ]   [ v1  1 ]             [ -v1*M1.inverse()  1 ]
      this._mtx.invert();
      this._vec.negate().mulBy(this._mtx);
      return this;
   }

   public final MuAr3D setByInverse(ImuAr3D A2) { //:  this:= A2.inverse()
   // if(this==A2)   //: 一樣
      this._mtx.setByInverse(A2._mtx); 
      this._vec.setByNeg(A2._vec).mulBy(this._mtx);
      return this;                                     // 未測
   }

  //----------------------------
   public final MuAr3D axesRotBy(ImuV3D drx, double A) {
      this._mtx.rotBy(drx, A);   return this;
   }
   public final MuAr3D axesXRotBy(double angle) {
      this._mtx.xRotBy(angle);   return this;
   }
   public final MuAr3D axesYRotBy(double angle) {
      this._mtx.yRotBy(angle);   return this;
   }
   public final MuAr3D axesZRotBy(double angle) {
      this._mtx.zRotBy(angle);   return this;
   }
   public final MuAr3D axesSinRotBy(ImuV3D drx, double sinA) {
      this._mtx.sinRotBy(drx, sinA);  return this;
   }

// public final MuAr3D axesRotUp(double A) {
// .... 
//    沒有這些!! 因為矩陣或座標系都無所謂前後.

   public final MuAr3D rotBy(ImuV3D drx, double A, ImuV3D center) {
      final MuV3D O=new MuV3D(this.org());
      final MuV3D ptA=new MuV3D(O).addBy(this.axis1());
      final MuV3D ptB=new MuV3D(O).addBy(this.axis2());
      final MuV3D ptC=new MuV3D(O).addBy(this.axis3());
      O.rotBy(drx, A, center);
      ptA.rotBy(drx, A, center);
      ptB.rotBy(drx, A, center);
      ptC.rotBy(drx, A, center);
      this.org().setBy(O);
      this.axis1().setBySub(ptA,O);
      this.axis2().setBySub(ptB,O);
      this.axis3().setBySub(ptC,O);
      return this;
   }

   public final MuAr3D sinRotBy(ImuV3D drx, double sinA, ImuV3D center) {
      final MuV3D O=new MuV3D(this.org());
      final MuV3D A=new MuV3D(O).addBy(this.axis1());
      final MuV3D B=new MuV3D(O).addBy(this.axis2());
      final MuV3D C=new MuV3D(O).addBy(this.axis3());
      O.sinRotBy(drx, sinA, center);
      A.sinRotBy(drx, sinA, center);
      B.sinRotBy(drx, sinA, center);
      C.sinRotBy(drx, sinA, center);
      this.org().setBy(O);
      this.axis1().setBySub(A,O);
      this.axis2().setBySub(B,O);
      this.axis3().setBySub(C,O);
      return this;
   }

   //[ 對this作沿大圓轉動, 轉動中心在 this.org()
   public final void sphereRotate(  
      ImuV3D dragFrom, ImuV3D dragTo
   ) {
      final ImuV3D cent=this.org();
      final double r=cent.distance(dragFrom);
       //: assume r==cent.distance(dragTo)
      final ImuV3D v1=dragFrom.sub(cent);
      final ImuV3D v2=dragTo.sub(cent);
      final ImuV3D axis=v1.cross(v2);  // axis.norm()==r*r*sin(A)
      if(v1.dot(v2)>=0) {  //: 銳角
         final double sinA=axis.norm()/(r*r);
         this.axesSinRotBy(axis, sinA);    
      }
      else {  //: 鈍角
         final double A=Math.PI-tw.fc.Std.a_sin(axis.norm()/(r*r));
         this.axesRotBy(axis, A);       
      }
   }

   //[ 沿緯圈轉動, 法線通過this.org()且平行於axisV.
   //[ 假設 dragFrom與dragTo在同一緯圈 
   public final void sphereLatRotate( 
      ImuV3D dragFrom, ImuV3D dragTo, ImuV3D axisV
   ) {
      final ImuV3D center=this.org();
      final ImuV3D pV=(dragFrom.sub(center)).proj(axisV);
      final ImuV3D rotPt=center.add(pV);
      final ImuV3D rotFV=dragFrom.sub(rotPt);
      final ImuV3D rotTV=dragTo.sub(rotPt);
      final double r=rotPt.distance(dragFrom);
      //[ cross與axisV平行, 但不知同向或反向. 所以還是要用cross轉
      final ImuV3D cross=rotFV.cross(rotTV);  
      // corss.norm()==r*r*sin(A)
      final double n=cross.norm();
      if(n<Std.epsilon()) {  return;   }
      if(rotFV.dot(rotTV)>=0) { //: 銳角
         final double sinA=n/(r*r);
         this.axesSinRotBy(cross, sinA);  
      }
      else{  //: 鈍角
         final double A=Math.PI-tw.fc.Std.a_sin(n/(r*r));
         this.axesRotBy(cross, A);  
      }
   }

   //[ 沿軸PQ轉動, this.org()不必在軸上, 會校正dragTo
   public final void circleRotate(  
      ImuV3D P, ImuV3D Q, ImuV3D dragFrom, ImuV3D dragTo
   ){   
      final ImuV3D rotCenter=dragFrom.lineProj(P,Q);
      final ImuV3D v1=dragFrom.sub(rotCenter);
      final ImuV3D v2=dragTo.sub(rotCenter);
      final ImuV3D v2Fixed=v2.coProj(Q.sub(P)); //: 校正到旋轉面 
      final double r=v1.norm();
      final double r1=v2Fixed.norm();
      final ImuV3D axis=v1.cross(v2Fixed);  
         // axis.norm()==r*r1*sin(A)
      final double n=axis.norm();
      if(n<Std.epsilon()) {  return;   }
      if(v1.dot(v2Fixed)>=0.0) { //: 銳角
         final double sinA=n/(r*r1);
         this.sinRotBy(axis, sinA, rotCenter);   
      }
      else {
         final double A=Math.PI-tw.fc.Std.a_sin(n/(r*r1));  //: 鈍角
         this.rotBy(axis, A, rotCenter);   
      }
   }



}


