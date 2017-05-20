package tw.fc.gui;
import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;
//import static java.lang.Math.sqrt;
   
   
//[ immutable row-type affine matrix for 3D transformation
//[ 內含M3D及V3D欄位, 不展開以利運算.
public class ImuAr3D implements DuplicableI<MuAr3D>, PrintableI
{
   //  [ M  o ]
   //  [ v  1 ] 
   //           4x4
   MuM3D _mtx;       //: MuAr3D 可以 replaceMtx(.)
   final MuV3D _vec;

   //-------------------------------------------------

   //[ describe a new cordinate system in row-type
   public     ImuAr3D(ImuM3D baseMtx, ImuV3D centerVec) {
      this._mtx=new MuM3D(baseMtx);  this._vec=new MuV3D(centerVec);
   }
   public     ImuAr3D(ImuV3D b1, ImuV3D b2, ImuV3D b3, ImuV3D c) {
      this._mtx=new MuM3D(b1, b2, b3);
      this._vec=new MuV3D(c);
   }
   public     ImuAr3D(
      double b11, double b12, double b13,
      double b21, double b22, double b23,
      double b31, double b32, double b33,
      double c1,  double c2,  double c3
   ) {
      this._mtx=new MuM3D(
                       b11, b12, b13,
                       b21, b22, b23,
                       b31, b32, b33
                    );  
      this._vec=new MuV3D(c1, c2, c3);
   }
   //] 

   //[ create a pure translation
   public     ImuAr3D(ImuV3D c) {
      this._mtx=MuM3D.scalar(1.0);
      this._vec=new MuV3D(c);
   }
   public     ImuAr3D(double c1, double c2, double c3) {
      this._mtx=MuM3D.scalar(1.0);
      this._vec=new MuV3D(c1, c2, c3);
   }
   //]

   //[ create a center-fixed system 
   public     ImuAr3D(ImuM3D baseMtx) {
      this._mtx=new MuM3D(baseMtx);  
      this._vec=new MuV3D();
   }
   public     ImuAr3D(ImuV3D b1, ImuV3D b2, ImuV3D b3) {
      this._mtx=new MuM3D(b1, b2, b3);
      this._vec=new MuV3D();
   }
   public     ImuAr3D(
      double b11, double b12, double b13,
      double b21, double b22, double b23,
      double b31, double b32, double b33
   ) {
      this._mtx=new MuM3D(
                       b11, b12, b13,
                       b21, b22, b23,
                       b31, b32, b33
                    );  
      this._vec=new MuV3D();
   }
   //]

   //[ default-ctor, creatie an identity system.
   public     ImuAr3D() {
      this(ImuM3D.ONE, ImuV3D.ZERO);
   }

   //[ copy-ctor
   public     ImuAr3D(ImuAr3D src) { 
      this(src._mtx,src._vec);
   }

   //-------------------------------------------------------

   //[-------- implements DuplicableI ------------------
   public final MuAr3D duplicate() {  return new MuAr3D(this); }
   //]-------- implements DuplicableI ------------------


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

   public String toString() {
      return "{ " + _mtx + " " + _vec + " } " ;
   }

   public final boolean equals(ImuAr3D m2) {
      return (this._mtx.equals(m2._mtx) 
           && this._vec.equals(m2._vec));
   }
   public final boolean notEquals(ImuAr3D m2) {
      return !equals(m2);
   }

   public final boolean equals(Object v2) {
      return equals((ImuAr3D)v2);
   }

/*>>>>>
   public final boolean equals(ImuAr3D m2, double errSq) {
      return (this.distanceSq(m2)<errSq);
   }
   public final boolean notEquals(ImuAr3D m2, double errSq) {
      return !equals(m2, errSq);
   }
*/

   public final int hashCode() {
      long tL;  int tI=17;
      tL=_mtx.hashCode(); tI=tI*37+(int)(tL^(tL>>32));
      tL=_vec.hashCode(); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isStandard() {
// public final boolean isIdentity() {
      return (  this.equals(STANDARD)  );
   }
   public final boolean notStandard() {
// public final boolean notIdentity() {
      return (!isStandard());
   }

// >>>>
//   public final MuM3Df toAr3Df() {   }

   @Deprecated public ImuV3D vec() {  return new ImuV3D(_vec); }
//   public ImuV3D center() {  return _vec; }
   public ImuV3D org() {  return new ImuV3D(_vec); }
   public ImuM3D mtx() {  return new ImuM3D(_mtx); }
   public ImuV3D axis1() {  return new ImuV3D(_mtx._r1); }
   public ImuV3D axis2() {  return new ImuV3D(_mtx._r2); }
   public ImuV3D axis3() {  return new ImuV3D(_mtx._r3); }
   public final ImuV3D negAxis1() {  return _mtx.negRow1(); }
   public final ImuV3D negAxis2() {  return _mtx.negRow2(); }
   public final ImuV3D negAxis3() {  return _mtx.negRow3(); }
   public double axis1NormSq() {  return _mtx._r1.normSq(); }
   public double axis2NormSq() {  return _mtx._r2.normSq(); }
   public double axis3NormSq() {  return _mtx._r3.normSq(); }
   public double axis1Norm() {  return _mtx._r1.norm(); }
   public double axis2Norm() {  return _mtx._r2.norm(); }
   public double axis3Norm() {  return _mtx._r3.norm(); }
   public double axesMeanSqNorm() { //: 平均軸長  
// public double axisMeanSqNorm() { //: 平均軸長  
   	  return _mtx.rowMeanSqNorm(); 
   } 

    
   public final ImuAr3D mul(ImuAr3D A2) {  
   //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
   //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]
      return new ImuAr3D( this._mtx.mul(A2._mtx),
                          this._vec.mul(A2._mtx).add(A2._vec) );
   }
   //] 連續描述的複合
   //] If Sys0 describe Sys1 as Ar3D(B1,c1)
   //] and Sys1 describe Sys2 as Ar3D(B2,c2)
   //] then Sys0 describe Sys2 as Ar3D(B2,c2)*Ar3D(B1,c1), 注意要反向!

   public final ImuAr3D swapMul(ImuAr3D a1) {  
      return a1.mul(this);
   }

   public final ImuAr3D inverse() {
      return new MuAr3D(this).invert();
   // final ImuM3D M1_inv=this._mtx.inverse();
   // return new ImuAr3D(M1_inv, this._vec.neg().mul(M1_inv));
   }

   public final ImuV3D swapMul(ImuV3D v1) {  
   //           [ M2  0 ]
   //  [ v1  1 ][ v2  1 ]=[ v1*M2+v2  1 ]
      return new MuV3D(this._vec).addByMul(v1,this._mtx);
   }


   //[ -------- 座標變換非常易錯! 客戶程式勿自乘 ---------------------

   //[ this:     the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newCord:  the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final void pos_updateOldCord(MuV3D oldCord, ImuV3D newCord) {
      // The throry:
      //   this == new ImuAr3D(b1',b2',b3',c')
      //   old system described by itself == ImuAr3D(E1,E2,E3,ZERO)
      //   Let oldCord==(x,y,z), newCord=(x',y',z'), 
      //   Then  obj == x*E1+y*E2+z*E3+ZERO , 
      //         obj == x'*b1'+y'*b2'+z'*b3'+c'            //: vector form
      //             == (x',y',z') * Mtx(b1',b2',b3') + c';    
      //   oldCord = newCord*B'+c'                         //: matrix form  
   // oldCord.setBy( newCord.mul(this._mtx).add(this._vec) );  //: 原式
      oldCord.setByMul( newCord, this._mtx ).addBy(this._vec); //: 優化
                                 //: 同指標oldCord==newCord時的優化要小心
      //                                 [ B'  o ]
      //   [ oldCord 1 ] = [ newCord  1 ][ c'  1 ]         //: affine form:
   }
   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final void vec_updateOldCord(MuV3D oldCord, ImuV3D newCord) {
   // oldCord.setBy( newCord.mul(this._mtx) );  //: 原式
      oldCord.setByMul( newCord, this._mtx );   //: 優化
   }
   //[ this:     the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newCord:  the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final ImuV3D pos_oldCord(ImuV3D cordNew) {
      return cordNew.mul(this._mtx).add(this._vec);
   }
   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final ImuV3D vec_oldCord(ImuV3D cordNew) {
      return cordNew.mul(this._mtx);
   }

   //-------------------
   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final void pos_updateNewCord(ImuV3D oldCord, MuV3D newCord) {
      //   oldCord = newCord*B'+c'                         //: matrix form  
      //   newCord = (oldCord-c')*B'.inv                   //: matrix form  
      newCord.setBySub(oldCord,_vec).mulBy(_mtx.inverse()); //: 優化
                             //: 同指標oldCord==newCord時的優化要小心
   // newCord.setBy( 
   //    oldCord.sub(this._vec).mul(this._mtx.inverse())    //: 原式 
   // );
      //                                 [ B'    o ]
      //  [ oldCord  1 ] = [ newCord  1 ][ c'    1 ]       //: affine form
      //
      //                                 [    B'.inv  o ]
      //  [ newCord  1 ] = [ oldCord  1 ][ -c*B'.inv  1 ]  //: affine form
   }

   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final void vec_updateNewCord(ImuV3D oldCord, MuV3D newCord) {
      newCord.setByMul(oldCord,_mtx.inverse());     //: 優化
   // newCord.setBy( oldCord.mul(_mtx.inverse()) ); //: 原式 
   }

   //[ this:     the new system described by old system, 
   //[ ans:      the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final ImuV3D pos_newCord(ImuV3D oldCord) {
      return oldCord.sub(this._vec).mul(this._mtx.inverse());
                   //: exception when this.mtx() is not invertible
   }   
   //[ this:     the new system described by old system, 
   //[ ans:      the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final ImuV3D vec_newCord(ImuV3D oldCord) {
      return oldCord.mul(this._mtx.inverse());
   }

   //-------------------------------------
   //[ this:    the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newDsc:  the new descreption of a system in new system 
   //[ oldCord: the old descreption of a system in old System
   public final void updateOldDescription(MuAr3D oldDesc, ImuAr3D newDesc) {
      if(this==oldDesc) throw new IllegalArgumentException("Not support");
   //  this.vec_updateOldCord(oldDesc.axis1(), newCord.axis1()); 
   //  this.vec_updateOldCord(oldDesc.axis2(), newCord.axis2()); 
   //  this.vec_updateOldCord(oldDesc.axis3(), newCord.axis3()); 
   //  this.pos_updateOldCord(oldDesc.org(), newCord.org()); 
      //
      //  LET   this==ImuAr3D(b1',b2',b3',c')==ImuAr3D(B',c') 
      //  THEN  oldAxis1 = newAxis1*B',
      //        oldAxis2 = newAxis2*B', 
      //        oldAxis3 = newAxis3*B', 
      //        oldOrg   = newOrg*B'+c'.      //: vector form
      //  I.E.  oldMtx = newMtx*B',  
      //        oldOrg = newOrg*B'+c'.        //: matrix form
      //  I.E.
      //        [ oldMtx  o ]   [ newMtx  o ] [  B'  o ]
      //        [ oldOrg  1 ] = [ newOrg  1 ] [  c'  1 ]  //: affine form
 
   // oldDesc._vec.setBy( newDesc._vec.mul(this._mtx).add(this._vec) ); //:原式
   // oldDesc._mtx.setBy( newDesc._mtx.mul(this._mtx) );                //:原式
   //                           //: 可能 oldDesc==newDesc, 所以mtx要後動 
      oldDesc._vec.setBy(this._vec).addByMul(newDesc._vec,this._mtx); //: 優化
      oldDesc._mtx.setByMul( newDesc._mtx, this._mtx );               //: 優化
                                //: 可能 oldDesc==newDesc, 所以mtx要後動 
   // oldDesc.setByMul( newDesc, this );   //: affine form
   }
   //[ this:    the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newDsc:  the new descreption of a system in new system 
   //[ ans:     the old descreption of a system in old System
   public final ImuAr3D oldDescription(ImuAr3D newDesc) {
      return new ImuAr3D(
         newDesc._mtx.mul(this._mtx), 
         newDesc._vec.mul(this._mtx).add(this._vec)
      );                                 //: matrix form
   }

   //-------------------------------------
   //[ this:    the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newDsc:  the new descreption of a system in new system 
   //[ oldCord: the old descreption of a system in old System
   public final void updateNewDescription(ImuAr3D oldDesc, MuAr3D newDesc) {
      if(this==newDesc) throw new IllegalArgumentException("Not support");
      //
      //  LET   this==ImuAr3D(b1',b2',b3',c')==ImuAr3D(B',c') 
      //  THEN  oldAxis1 = newAxis1*B',
      //        oldAxis2 = newAxis2*B', 
      //        oldAxis3 = newAxis3*B', 
      //        oldOrg   = newOrg*B'+c'. 
      //  THEN  newAxis1 = oldAxis1*B'.inv ,
      //        newAxis2 = oldAxis2*B'.inv , 
      //        newAxis3 = oldAxis3*B'.inv , 
      //        newOrg   = (oldOrg-c')*B'.inv . 
      //  I.E.  newMtx = oldMtx*B'.inv,  
      //        newOrg = (oldOrg-c')*B'.inv .
 
   // newDesc._vec.setBy( 
   //    oldDesc._vec.sub(this._vec).mul(this._mtx.inverse())  //:原式 
   // );                                                       
   // newDesc._mtx.setBy( 
   //    oldDesc._mtx.mul(this._mtx.inverse())                 //:原式
   // );              //: 可能 oldDesc==newDesc, 所以mtx要後動 
      newDesc._vec.setBySub(oldDesc._vec,this._vec) 
                  .mulBy(this._mtx.inverse());                 //: 優化  
      newDesc._mtx.setByMul( 
         oldDesc._mtx, this._mtx.inverse()                     //: 優化
      );              //: 可能 oldDesc==newDesc, 所以mtx要後動 
   }

   public final ImuAr3D newDescription(ImuAr3D oldDesc) {
      return new ImuAr3D(
         oldDesc._mtx.mul(this._mtx.inverse()),
         oldDesc._vec.sub(this._vec).mul(this._mtx.inverse())
      );
   }


//[ 由仿射變換的角度看, 不該支援這些----------------
//   public final ImuAr3D add(ImuAr3D m2) 
//   public final ImuAr3D sub(ImuAr3D m2) 
//   public final ImuAr3D neg() 
//]  ------------------------------

//   public final ImuAc3D transposition() {
//   }

   public final double det() {  return this._mtx.det();  }

   //[==========   static part ===============================
/////   public static final ImuAr3D ZERO = new ImuAr3D();
//   public static final ImuAr3D ONE=new ImuAr3D();
//   public static final ImuAr3D IDENTITY=ONE;
   public static final ImuAr3D STANDARD=new ImuAr3D();

/*
   //[ oldSys: the new cordSys described by standard cordSys, 
   //[ oldSys: the old cordSys described by standard cordSys, 
   //[ cordNew: the new cordinate of obj 
   //[ find the old coordinate of obj
   public static ImuV3D pos_oldCord(
      ImuAr3D oldSys, ImuAr3D newSys, ImuV3D cordNew
   ) {
//[   return oldSys.pos_newCord(newSys.pos_oldCord(cordNew));
      return cordNew.mul(newSys._mtx).add(newSys._vec)
                    .sub(oldSys._vec).mul(oldSys._mtx.inverse());
   }
   public static ImuV3D vec_oldCord(
      ImuAr3D oldSys, ImuAr3D newSys, ImuV3D cordNew
   ) {
//[   return oldSys.vec_newCord(newSys.vec_oldCord(cordNew));
      return cordNew.mul(newSys._mtx).mul(oldSys._mtx.inverse());
   }

   //[ oldSys: the new cordSys described by standard cordSys, 
   //[ oldSys: the old cordSys described by standard cordSys, 
   //[ cordOld: the old cordinate of obj 
   //[ find the new coordinate of obj
   public static ImuV3D pos_newCord(
      ImuAr3D oldSys, ImuAr3D newSys, ImuV3D cordOld
   ) {
//[   return newSys.pos_newCord(oldSys.pos_oldCord(cordOld));
      return cordOld.mul(oldSys._mtx).add(oldSys._vec)
                    .sub(newSys._vec).mul(newSys._mtx.inverse());
   }
   public static ImuV3D vec_newCord(
      ImuAr3D oldSys, ImuAr3D newSys, ImuV3D cordOld
   ) {
//    return newSys.vec_newCord(oldSys.vec_oldCord(cordOld));
      return cordOld.mul(oldSys._mtx).mul(newSys._mtx.inverse());
   }
*/

   //]==========   static part ===============================


}