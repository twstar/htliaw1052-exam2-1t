package tw.fc.gui;

import java.io.Serializable;

import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;
import static java.lang.Math.sqrt;


public class ImuM2D implements DuplicableI<MuM2D>, PrintableI, Serializable
{
   private static final long serialVersionUID = 2014120808L;
   
   final MuV2D _r1, _r2;
//   double _11, _12, _21, _22;  

 //[ 2D mtx 計算 det與inv很快, 不必使用如同3D mtx的記錄機制
   //double detOK=Double.NaN;  
   //ImuM2D invOK=null;
 //]

   //-------------------------------------------------   

   //[ default ctor, create a zero matrix
   public ImuM2D() {  
      _r1=new MuV2D();  _r2=new MuV2D();
   // _11=0.0; _12=0.0;  
   // _21=0.0; _22=0.0;  
   }

   //[ constructed by entries in row-major order
   public ImuM2D(
      double d11, double d12, 
      double d21, double d22 
   ) {  
      _r1=new MuV2D(d11, d12);  _r2=new MuV2D(d21, d22);
   // _11=d11; _12=d12;  
   // _21=d21; _22=d22;  
   }

   //[ constructed by row vectors
   public     ImuM2D(ImuV2D v1, ImuV2D v2) {
      _r1=new MuV2D(v1);  _r2=new MuV2D(v2);
   // _11=v1.x(); _12=v1.y();  
   // _21=v2.x(); _22=v2.y();  
   }

   //[ construct a diagonal matrix 
   public     ImuM2D(double d1, double d2) {  
      _r1=new MuV2D(d1, 0.0);  _r2=new MuV2D(0.0, d2);
   // _11=d1;  _12=0.0;  
   // _21=0.0; _22=d2;   
   }

   //[ copy-ctor
   public     ImuM2D(ImuM2D src) {  
      _r1=new MuV2D(src._r1);  _r2=new MuV2D(src._r2);
   // _11=src._11; _12=src._12;
   // _21=src._21; _22=src._22;
   }

   //--------------------------------------------------
   public final double e11() {  return _r1.x; }
   public final double e12() {  return _r1.y; }
   public final double e21() {  return _r2.x; }
   public final double e22() {  return _r2.y; }
// public final double e11() {  return _11; }
// public final double e12() {  return _12; }
// public final double e21() {  return _21; }
// public final double e22() {  return _22; }

   public ImuV2D row1() {  return new ImuV2D(_r1); }
   public ImuV2D row2() {  return new ImuV2D(_r2); }
   public final ImuV2D col1() {  return new ImuV2D(_r1.x, _r2.x); }
   public final ImuV2D col2() {  return new ImuV2D(_r1.y, _r2.y); }
// public final ImuV2D row1() {  return new ImuV2D(_11, _12);  }
// public final ImuV2D row2() {  return new ImuV2D(_21, _22);  }
// public final ImuV2D col1() {  return new ImuV2D(_11, _21);  }
// public final ImuV2D col2() {  return new ImuV2D(_12, _22);  }

   public final ImuV2D getDiagonal() {  
      return new ImuV2D(_r1.x, _r2.y);  
   }

   public String toString() { 
      return "[" + _r1.x + ", " + _r1.y +  "; "
                 + _r2.x + ", " + _r2.y +  "] " ;  
   //   return "[" + _11 + ", " + _12 +  "; "
   //              + _21 + ", " + _22 +  "] " ;  
   }
   public final boolean equals(ImuM2D m2) { 
      return (_r1.equals(m2._r1) && _r2.equals(m2._r2));

//      return (_11==m2._11 && _12==m2._12 
//           && _21==m2._21 && _22==m2._22 );  
   }
   public final boolean equals(ImuM2D m2, double errSq) {
      return (this.distanceSq(m2)<errSq);
   }
   public final boolean notEquals(ImuM2D m2) { 
      return !equals(m2);
   }
   public final boolean notEquals(ImuM2D m2, double errSq) {
      return !equals(m2, errSq);
   }

   public final boolean equals(Object v2) {
      return equals((ImuM2D)v2);
   } 

   public final int hashCode() {  
      long tL;  int tI=17;
      tL=Double.doubleToLongBits(_r1.x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r1.y); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r2.x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r2.y); tI=tI*37+(int)(tL^(tL>>32));
   // tL=Double.doubleToLongBits(_11); tI=tI*37+(int)(tL^(tL>>32));
   // tL=Double.doubleToLongBits(_12); tI=tI*37+(int)(tL^(tL>>32));
   // tL=Double.doubleToLongBits(_21); tI=tI*37+(int)(tL^(tL>>32));
   // tL=Double.doubleToLongBits(_22); tI=tI*37+(int)(tL^(tL>>32));
      return tI;  
   }

   public final boolean isZero() {  
      return (  _r1.x==0 && _r1.y==0 && _r2.x==0 && _r2.y==0 );  
   // return (  _11==0 && _12==0 && _21==0 && _22==0 );  
   }
   public final boolean notZero() {  return (  !isZero() );  }

   //[-------- implements DuplicableI ------------------
   public final MuM2D duplicate() {  return new MuM2D(this); }
   //]-------- implements DuplicableI ------------------

/*
   public final MuM3Df toM2Df() {
      return new MuM2Df(
         (float)_11,(float)_12,
         (float)_21,(float)_22
      );  
   }
*/
   //[-------- implements PrintableI ------------------- 
   public final void printTo(TxOStream ooo) throws java.io.IOException {  
      ooo.p("[").pc(_r1.x).pc(_r1.y).p("; ")
                .pc(_r2.x).pc(_r2.y).p("] ");
   // ooo.p("[").pc(_11).pc(_12).p("; ")
   //           .pc(_21).pc(_22).p("] ");
   }
   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {  
      ooo.p("[").wpc(w,_r1.x).wpc(w,_r1.y).p("; ")
                .wpc(w,_r2.x).wpc(w,_r2.y).p("] ");
   // ooo.p("[").wpc(w,_11).wpc(w,_12).p("; ")
   //           .wpc(w,_21).wpc(w,_22).p("] ");
   }

   //]-------- implements PrintableI -------------------    

   //--------   
   public final ImuM2D add(ImuM2D m2) {  
      return new MuM2D(this).addBy(m2);
   }
   public final ImuM2D sub(ImuM2D m2) {  
      return new MuM2D(this).subBy(m2);
   }

   public final ImuM2D neg() {  
      return new ImuM2D(
         -_r1.x,-_r1.y,  -_r2.x,-_r2.y 
      ); 
   //   return new ImuM2D(
   //      -_11,-_12,  -_21,-_22 
   //   ); 
   }

   public final ImuM2D mul(double k) {  //:  scalar product
      return new MuM2D(this).mulBy(k);
   }

   public final ImuM2D div(double k) {  
      if(k==0) throw new IllegalArgumentException("zero divider"); 
      return this.mul(1.0/k);
   }

   public final ImuM2D mul(ImuM2D m2) {  
      return new MuM2D(
         _r1.x*m2._r1.x + _r1.y*m2._r2.x,   _r1.x*m2._r1.y + _r1.y*m2._r2.y,
         _r2.x*m2._r1.x + _r2.y*m2._r2.x,   _r2.x*m2._r1.y + _r2.y*m2._r2.y 
      );    
   // return new MuM2D(
   //    _11*m2._11 + _12*m2._21,   _11*m2._12 + _12*m2._22,
   //    _21*m2._11 + _22*m2._21,   _21*m2._12 + _22*m2._22 
   // );    
   }
   public final ImuM2D swapMul(ImuM2D m1) {  
      return m1.mul(this);
   }

   public final ImuV2D mul(ImuV2D v2) {  
      // v2 視為 3 by 1 矩陣, 做右乘.   
      return new MuV2D(
         _r1.x*v2.x + _r1.y*v2.y,
         _r2.x*v2.x + _r2.y*v2.y
      );    
   //   return new MuV2D(
   //      _11*v2.x + _12*v2.y,
   //      _21*v2.x + _22*v2.y
   //   );    
   }
   public final ImuV2D swapMul(ImuV2D v1) {
      //: return  v1 * this ;
      return new MuV2D(
          v1.x*_r1.x + v1.y*_r2.x,
          v1.x*_r1.y + v1.y*_r2.y
      );
   //   return new MuV2D(
   //       v1.x*_11 + v1.y*_21,
   //       v1.x*_12 + v1.y*_22
   //   );
   }

@Deprecated
   public final ImuV2D rMul(ImuV2D v) {  return this.mul(v);  }
@Deprecated
   public final ImuV2D lMul(ImuV2D v) {  return swapMul(v);   }

   public final double dot(ImuM2D m2) {  
      return 
         _r1.x*m2._r1.x + _r1.y*m2._r1.y +
         _r2.x*m2._r2.x + _r2.y*m2._r2.y  ;
      //   _11*m2._11 + _12*m2._12 +
      //   _21*m2._21 + _22*m2._22  ;
   }

   public final ImuM2D transposition() {  
      return new MuM2D(this).transpose(); 
   }

   public final double det() {  
      return  _r1.x*_r2.y - _r2.x*_r1.y ;
   // return  _11*_22 - _21*_12 ;
   }

   public final ImuM2D adjoint() {
      return new MuM2D(
          _r2.y, -_r2.x,
         -_r1.y,  _r1.x
      );
   //   return new MuM2D(
   //       _22, -_21,               
   //      -_12,  _11       //ht: bug fixed in A10104
   //   );
   }

   public final ImuM2D inverse() {  
      final double d=det();
      if(d==0.0) throw new RuntimeException("Not invertible");
      return adjoint().mul(1/d); 
   }
      
   public final double normSq() {  
      return (
           _r1.x*_r1.x + _r1.y*_r1.y 
         + _r2.x*_r2.x + _r2.y*_r2.y 
      //   _11*_11 + _12*_12 
      // + _21*_21 + _22*_22 
      ); 
   }
   public final double norm() {  return sqrt(normSq()); }
   public final double distanceSq(ImuM2D x) {  
      return this.sub(x).normSq(); 
   }
   public final double distance(ImuM2D x) {  
      return this.sub(x).norm(); 
   }

   //------------------------------

   //[==========   static part ===============================

   public static final ImuM2D ZERO=new ImuM2D();
   public static final ImuM2D ONE=new ImuM2D(1.0, 1.0);
   public static final ImuM2D E11=
      new ImuM2D(1.0, 0.0, 0.0, 0.0);
   public static final ImuM2D E12=
      new ImuM2D(0.0, 1.0, 0.0, 0.0);
   public static final ImuM2D E21=
      new ImuM2D(0.0, 0.0, 1.0, 0.0);
   public static final ImuM2D E22=
      new ImuM2D(0.0, 0.0, 0.0, 1.0);

   // public static final ImuM2D scalar(double c) {                              
   public static final MuM2D scalar(double c) {                               //: ???u c???x?}   
      return new MuM2D(c, c);
   }

   public static final double det(ImuM2D M) { 
      return M.det();
   }
   public static final double det(ImuV2D v1, ImuV2D v2) { 
      return v1.wedge(v2);  //: A10926 更正
   }
   public static final double det(
      double d11, double d12,
      double d21, double d22
   ) {
      return  d11*d22 - d12*d21;
   }


   //----------------
   public static final ImuM2D findMap(
      ImuV2D v1, ImuV2D v2,
      ImuV2D w1, ImuV2D w2
   ) {
      //[ 求M,使 v1*M=w1, v2*M=w2;
      //: 假設v1,v2 線性獨立, 否則會exception於inverse
      final ImuM2D A= new ImuM2D(v1,v2); 
      final ImuM2D B= new ImuM2D(w1,w2); 
      // 解 A*X=B
      return A.inverse().mul(B); 
   }

   //]==========   static part ===============================

}