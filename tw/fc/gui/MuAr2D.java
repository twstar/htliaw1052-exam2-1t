package tw.fc.gui;

//import java.io.IOException;

//import tw.fc.ScannableI;
//import tw.fc.SetableI;
//import tw.fc.TxIStream;

//: A20501 wu

public class MuAr2D extends ImuAr2D{

   private static final long serialVersionUID = 2014120811L;

   //[ -------------------- constrouctors --------------------

   public MuAr2D() { super(); }

   public MuAr2D(ImuM2D baseMtx, ImuV2D orgVec){
      super(baseMtx, orgVec);
   }

   public MuAr2D(ImuV2D b1, ImuV2D b2, ImuV2D c){
      super(b1, b2, c);
   }

   public MuAr2D(double b11, double b12, 
         double b21, double b22, 
         double c1,  double c2){
      super(b11, b12, b21, b22, c1, c2);
   }

   //: create a pure translation
   public MuAr2D(ImuV2D c){
      super(c);
   }

   public MuAr2D(double c1, double c2){
      super(c1, c2);
   }

   //: create a center-fixed system
   public MuAr2D(ImuM2D baseMtx){
      super(baseMtx);		
   }

   public MuAr2D(ImuV2D b1, ImuV2D b2){
      super(b1, b2);		
   }

   public MuAr2D(double b11, double b12, double b21, double b22){
      super(b11, b12, b21, b22);
   }

   //: copy ctor
   public MuAr2D(ImuAr2D src) {
      super(src);
   }

   //] -------------------- constrouctors --------------------

   public MuAr2D setBy(ImuAr2D src){
      super._vec.setBy(src._vec);
      super._mtx.setBy(src._mtx);
      return this;
   }

   public MuAr2D setBy(ImuM2D m, ImuV2D v){
      super._vec.setBy(v);
      super._mtx.setBy(m);
      return this;
   }

   public MuAr2D setBy(ImuV2D b1, ImuV2D b2, ImuV2D c){
      super._vec.setBy(c);
      super._mtx.setBy(b1, b2);
      return this;
   }

   public MuAr2D setBy(double b11, double b12,
         double b21, double b22,
         double c1, double c2){
      super._vec.setBy(c1, c2);
      super._mtx.setBy(b11, b12, b21, b22);
      return this;
   }

   @Override public final MuV2D org(){ return super._vec; }
   @Override public final MuM2D mtx(){ return super._mtx; }
   @Override public final MuV2D axis1(){ return _mtx._r1; }
   @Override public final MuV2D axis2(){ return _mtx._r2; }

   public final void replaceMtx(MuM2D m){ super._mtx=m; }

   public final MuAr2D orgSetBy(ImuV2D v){
      super._vec.setBy(v); return this;
   }

   public final MuAr2D orgSetBy(double x, double y){
      super._vec.setBy(x, y); return this;
   } 

   public final MuAr2D orgAddBy(ImuV2D v){
      super._vec.addBy(v); return this;
   }

   public MuAr2D orgAddBy(double x, double y){
      super._vec.addBy(x, y); return this;
   }

   public final MuAr2D orgAddByMul(double k, ImuV2D v){
      super._vec.addByMul(k, v); return this;
   }

   public final MuAr2D axesSetBy(ImuM2D m) {
      super._mtx.setBy(m); return this;
   }

   public final MuAr2D axis1SetBy(ImuV2D v){
      super._mtx.row1SetBy(v); return this;
   }

   public final MuAr2D axis2SetBy(ImuV2D v){
      super._mtx.row2SetBy(v); return this;
   }

   public final MuAr2D axesMulBy(double k){
      super._mtx.mulBy(k); return this;   	
   }

   public final MuAr2D axis1MulBy(double k) {
      super._mtx.row1MulBy(k); return this;
   }

   public final MuAr2D axis2MulBy(double k) {
      super._mtx.row2MulBy(k); return this;
   }

   public final MuAr2D mulBy(ImuAr2D A2) { //: this := this*A2
      //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
      //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      super._vec.mulBy(A2._mtx).addBy(A2._vec);
      super._mtx.mulBy(A2._mtx);
      return this;
   }

   public final MuAr2D swapMulBy(ImuAr2D A1) { //:  this:= A1*this
      //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
      //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      super._vec.addByMul(A1._vec, super._mtx);
      super._mtx.setByMul(A1._mtx, super._mtx);
      return this;	
   }

   public final MuAr2D setByMul(ImuAr2D A1, ImuAr2D A2){ //: this:=A1*A2
      //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
      //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      //!!  POSSIBLE A1 or A2 is equals to this
      //!!  _vec or _mul may be changed before use
      final ImuV2D old_v2=(A2!=this)? A2._vec: new ImuV2D(A2._vec);
      super._vec.setByMul( A1._vec, A2._mtx ).addBy(old_v2); 
      super._mtx.setByMul(  A1._mtx, A2._mtx  );
      return this;
   }

   public final MuAr2D invert(){
      //  [ I   o ] = [ M1  o ] [ M2  o ] = [ M1*M2     o ]
      //  [ o   1 ]   [ v1  1 ] [ v2  1 ]   [ v1*M2+v2  1 ]
      //  i.e.    I=M1*M2,  o=v1*M2+v2
      //  so,     M2=M1.inverse(),  v2=-v1*M2=-v1*M1.inverse()  
      //  also,   M1=M2.inverse(),  v1=-v2*M2.inverse() 
      //  [ M2   o ] = [ M1  o ].inverse() = [ M1.inverse()      o ]
      //  [ v2   1 ]   [ v1  1 ]             [ -v1*M1.inverse()  1 ]
      super._mtx.invert();
      super._vec.negate().mulBy(this._mtx);
      return this;
   }

   public final MuAr2D setByInverse(ImuAr2D A2){
      super._mtx.setByInverse(A2._mtx);
      super._vec.setByNeg(A2._vec).mulBy(super._mtx);
      return this;
   }

   public final MuAr2D axesRotBy(ImuV2D drx, double A) {
      super._mtx.rotBy(drx, A);   return this;
   }
   
   public final MuAr2D axesSinRotBy(ImuV2D drx, double sinA) {
      super._mtx.sinRotBy(drx, sinA);  return this;
   }
   
   //[ A21111 xie
   public final MuAr2D axesXRotBy(double angle){
      this._mtx.xRotBy(angle);   return this;
   }

   public final MuAr2D axesYRotBy(double angle){
      this._mtx.yRotBy(angle);   return this;
   }
   //]
}
   

