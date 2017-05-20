package tw.fc.gui;
import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;
//import static java.lang.Math.sqrt;


//[ immutable row-type affine matrix for 3D transformation
//[ 內含M3D及V3D欄位, 不展開以利運算.
public class ImuAr3Df implements DuplicableI<MuAr3Df>, PrintableI
{
   final MuM3Df _mtx;    final MuV3Df _vec;
   //  [ M  o ]
   //  [ v  1 ]
   //           4x4

   //-------------------------------------------------

   //[ describe a new cordinate system in row-type
   public     ImuAr3Df(ImuM3Df baseMtx, ImuV3Df centerVec) {
      this._mtx=new MuM3Df(baseMtx);  this._vec=new MuV3Df(centerVec);
   }
   public     ImuAr3Df(ImuV3Df b1, ImuV3Df b2, ImuV3Df b3, ImuV3Df c) {
      this._mtx=new MuM3Df(b1, b2, b3);
      this._vec=new MuV3Df(c);
   }
   public     ImuAr3Df(
      float b11, float b12, float b13,
      float b21, float b22, float b23,
      float b31, float b32, float b33,
      float c1,  float c2,  float c3
   ) {
      this._mtx=new MuM3Df(
                       b11, b12, b13,
                       b21, b22, b23,
                       b31, b32, b33
                    );
      this._vec=new MuV3Df(c1, c2, c3);
   }
   //]

   //[ create a pure translation
   public     ImuAr3Df(ImuV3Df c) {
      this._mtx=MuM3Df.scalar(1.0f);
      this._vec=new MuV3Df(c);
   }
   public     ImuAr3Df(float c1, float c2, float c3) {
      this._mtx=MuM3Df.scalar(1.0f);
      this._vec=new MuV3Df(c1, c2, c3);
   }
   //]

   //[ create a center-fixed system
   public     ImuAr3Df(ImuM3Df baseMtx) {
      this._mtx=new MuM3Df(baseMtx);
      this._vec=new MuV3Df();
   }
   public     ImuAr3Df(ImuV3Df b1, ImuV3Df b2, ImuV3Df b3) {
      this._mtx=new MuM3Df(b1, b2, b3);
      this._vec=new MuV3Df();
   }
   public     ImuAr3Df(
      float b11, float b12, float b13,
      float b21, float b22, float b23,
      float b31, float b32, float b33
   ) {
      this._mtx=new MuM3Df(
                       b11, b12, b13,
                       b21, b22, b23,
                       b31, b32, b33
                    );
      this._vec=new MuV3Df();
   }
   //]

   //[ default-ctor, creatie an identity system.
   public     ImuAr3Df() {
      this(ImuM3Df.ONE, ImuV3Df.ZERO);
   }

   //[ copy-ctor
   public     ImuAr3Df(ImuAr3Df src) {
      this(src._mtx,src._vec);
   }

   //-------------

   public ImuM3Df mtx() {  return _mtx; }
   public ImuV3Df vec() {  return _vec; }

   public final ImuAr3Df mul(ImuAr3Df a2) {
   //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
   //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      return new ImuAr3Df( this._mtx.mul(a2._mtx),
                          this._vec.mul(a2._mtx).add(a2._vec) );
   }
   //] 連續描述的複合
   //] If Sys0 describe Sys1 as RA3D(B1,c1)
   //] and Sys1 describe Sys2 as RA3D(B2,c2)
   //] then Sys0 describe Sys2 as RA3D(B2,c2)*RA3D(B1,c1), 注意要反向!

   public final ImuAr3Df inverse() {
   //  [ I   o ] = [ M1  o ] [ M2  o ] = [ M1*M2     o ]
   //  [ o   1 ]   [ v1  1 ] [ v2  1 ]   [ v1*M2+v2  1 ]
   //  i.e.    I=M1*M2,  o=v1*M2+v2
   //  so,     M2=M1.inverse(),  v2=-v1*M2=-v1*M1.inverse()
   //  also,   M1=M2.inverse(),  v1=-v2*M2.inverse()
   //  [ M2   o ] = [ M1  o ].inverse() = [ M1.inverse()      o ]
   //  [ v2   1 ]   [ v1  1 ]             [ -v1*M1.inverse()  1 ]
      final ImuM3Df M1_inv=this._mtx.inverse();
      return new ImuAr3Df(M1_inv, this._vec.neg().mul(M1_inv));
   }

   //[ oldSys is standard cordSys
   //[ this: the new cordSys described by old cordSys,
   //[ cordNew: the new cordinate of obj
   //[ find the old coordinate of obj
   public final ImuV3Df findOldCord(ImuV3Df cordNew) {
   // Let this==new ImuAr3Df(b1',b2',b3',c')
   // oldSystem described by itself ==
   //              new ImuAr3Df((1,0,0),(0,1,0),(0,0,1),(0,0,0))
   // ans = (x,y,z) = x'b1'+y'b2'+z'b3'+c'
   //     = (x',y',z') * Mtx(b1',b2',b3') + c';
      return cordNew.mul(this._mtx).add(this._vec);
   //  Theory in matrix form:
   //                             [ B    o ]
   //  [ ans  1 ] = [ cordNew  1 ][ c    1 ]=[ cordNew*B+c  1 ]
   }

   //[ oldSys is standard cordSys
   //[ this: the new cordSys described by old cordSys,
   //[ cordOld: the old cordinate of obj
   //[ find the new coordinate of obj ( w.r.t this )
   public final ImuV3Df findNewCord(ImuV3Df cordOld) {
   // Let this==new ImuAr3Df(b1',b2',b3',c')
   // oldSystem described by itself ==
   //              new ImuAr3Df((1,0,0),(0,1,0),(0,0,1),(0,0,0))
   // cordOld = (x,y,z)
   //         = x'b1'+y'b2'+z'b3'+c'
   //         = (x',y',z') * Mtx(b1',b2',b3') + c';
   // ans = (x',y',z') = (cordOld-c')* Mtx(b1',b2',b3').inv
      return cordOld.sub(this._vec).mul(this._mtx.inverse());
              //: exception when this.mtx() is not invertible
   //  Theory in matrix form:
   //                             [ B    o ]
   //  [ cordOld  1 ] = [ ans  1 ][ c    1 ]=[ ans*B+c  1 ]
   //
   //                             [ B.inv     o ]
   //  [ ans  1 ] = [ cordOld  1 ][ -c*B.inv  1 ] = [ (cordOld-c)*B.inv  1 ]
   }

   public String toString() {
      return "[" + _mtx + " " + _vec + "] " ;
   }

   public final boolean equals(ImuAr3Df m2) {
      return (this._mtx.equals(m2._mtx)
           && this._vec.equals(m2._vec));
   }
   public final boolean notEquals(ImuAr3Df m2) {
      return !equals(m2);
   }

   public final boolean equals(Object v2) {
      return equals((ImuAr3Df)v2);
   }

/*>>>>>
   public final boolean equals(ImuAr3Df m2, float errSq) {
      return (this.distanceSq(m2)<errSq);
   }
   public final boolean notEquals(ImuAr3Df m2, float errSq) {
      return !equals(m2, errSq);
   }
*/

   public final int hashCode() {
      long tL;  int tI=17;
      tL=_mtx.hashCode(); tI=tI*37+(int)(tL^(tL>>32));
      tL=_vec.hashCode(); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isIdentity() {
      return (  this.equals(IDENTITY)  );
   }

   public final boolean notIdentity() {
      return (!isIdentity());
   }

   //[-------- implements DuplicableI ------------------
   public final MuAr3Df duplicate() {  return new MuAr3Df(this); }
   //]-------- implements DuplicableI ------------------

/*>>>>
   public final MuM3Df toRA3Df() {
   }
*/

   //[-------- implements PrintableI -------------------
   public final void printTo(TxOStream ooo) throws java.io.IOException {
      ooo.p("[").ps(this._mtx)
                .p(this._vec)
                .p("] ");
   }

   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {
      printTo(ooo);   //<<<<<<
   }
   //]-------- implements PrintableI -------------------

/* >>>>
   //---------------------------------------------------
   public final ImuAr3Df add(ImuAr3Df m2) {
      return new MuM3Df(this).addBy(m2);
   }

   public final ImuAr3Df sub(ImuAr3Df m2) {
      return new MuM3Df(this).subBy(m2);
   }

   public final ImuAr3Df neg() {
   }

   public final ImuV3Df lMul(ImuV3Df v) {
      //: return  v * this ;
   }

   public final ImuAr3Df transposition() {
      return new MuM3Df(this).transpose();
   }

   //------------------------------
*/

   public final float det() {
      return this._mtx.det();
   }

   //[==========   static part ===============================
/////   public static final ImuAr3Df ZERO = new ImuAr3Df();
   public static final ImuAr3Df ONE=new ImuAr3Df();
   public static final ImuAr3Df IDENTITY=ONE;

/*  >>>>
   //----------------
   public static final ImuAr3Df findMap(
      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3,
      ImuV3Df w1, ImuV3Df w2, ImuV3Df w3
   ) {
      //[ 求M,使 v1*M=w1, v2*M=w2, v3*M=w3;
      //: 假設v1,v2,v3 線性獨立, 否則會exception於inverse
      final ImuAr3Df A= new ImuAr3Df(v1,v2,v3);
      final ImuAr3Df B= new ImuAr3Df(w1,w2,w3);
      // 解 A*X=B
      return A.inverse().mul(B);
   }

*/

   //[ oldSys: the new cordSys described by standard cordSys,
   //[ oldSys: the old cordSys described by standard cordSys,
   //[ cordNew: the new cordinate of obj
   //[ find the old coordinate of obj
   public static ImuV3Df findOldCord(ImuAr3Df oldSys, ImuAr3Df newSys, ImuV3Df cordNew) {
      return oldSys.findNewCord(newSys.findOldCord(cordNew));
   }

   //[ oldSys: the new cordSys described by standard cordSys,
   //[ oldSys: the old cordSys described by standard cordSys,
   //[ cordOld: the old cordinate of obj
   //[ find the new coordinate of obj
   public static ImuV3Df findNewCord(ImuAr3Df oldSys, ImuAr3Df newSys, ImuV3Df cordOld) {
      return newSys.findNewCord(oldSys.findOldCord(cordOld));
   }

   //]==========   static part ===============================

   //================================================

}