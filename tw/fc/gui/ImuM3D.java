package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.TxOStream;
   import static java.lang.Math.sqrt;

public class ImuM3D implements DuplicableI<MuM3D>, PrintableI
{
   final MuV3D _r1, _r2, _r3;
//   double _11, _12, _13, _21, _22, _23, _31, _32, _33;

 //[ 3D mtx 的 det與inv 計算代價較高, 避免重覆算.
   double detOK=Double.NaN;  
   ImuM3D invOK=null;
 //]

   //-------------------------------------------------

   //[ default ctor, create a zero matrix
   public     ImuM3D() {
      _r1=new MuV3D(); _r2=new MuV3D(); _r3=new MuV3D();
//      _11=0.0; _12=0.0; _13=0.0;
//      _21=0.0; _22=0.0; _23=0.0;
//      _31=0.0; _32=0.0; _33=0.0;
   }

   //[ constructed by entries in row-major order
   public     ImuM3D(
      double d11, double d12, double d13,
      double d21, double d22, double d23,
      double d31, double d32, double d33
   ) {
      _r1=new MuV3D(d11,d12,d13); 
      _r2=new MuV3D(d21,d22,d23); 
      _r3=new MuV3D(d31,d32,d33);
//      _11=d11; _12=d12; _13=d13;
//      _21=d21; _22=d22; _23=d23;
//      _31=d31; _32=d32; _33=d33;
   }

   //[ constructed by row vector
   public     ImuM3D(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      _r1=new MuV3D(v1);  _r2=new MuV3D(v2); _r3=new MuV3D(v3);
//      _11=v1.x(); _12=v1.y(); _13=v1.z();
//      _21=v2.x(); _22=v2.y(); _23=v2.z();
//      _31=v3.x(); _32=v3.y(); _33=v3.z();
   }

   //[ construct a diagonal matrix 
   public     ImuM3D(double d1, double d2, double d3) {
      _r1=new MuV3D(d1,0.0,0.0); 
      _r2=new MuV3D(0.0,d2,0.0); 
      _r3=new MuV3D(0.0,0.0,d3);
//      _11=d1;  _12=0.0; _13=0.0;
//      _21=0.0; _22=d2;  _23=0.0;
//      _31=0.0; _32=0.0; _33=d3;
   }

   //[ copy-ctor
   public     ImuM3D(ImuM3D src) {
      _r1=new MuV3D(src._r1); 
      _r2=new MuV3D(src._r2); 
      _r3=new MuV3D(src._r3);
//      _11=src._11; _12=src._12; _13=src._13;
//      _21=src._21; _22=src._22; _23=src._23;
//      _31=src._31; _32=src._32; _33=src._33;
   }

   //--------------------------------------------------
   public final double e11() { return _r1.x; }
   public final double e12() { return _r1.y; }
   public final double e13() { return _r1.z; }
   public final double e21() { return _r2.x; }
   public final double e22() { return _r2.y; }
   public final double e23() { return _r2.z; }
   public final double e31() { return _r3.x; }
   public final double e32() { return _r3.y; }
   public final double e33() { return _r3.z; }
//   public final double e11() { return _11; }
//   public final double e12() { return _12; }
//   public final double e13() { return _13; }
//   public final double e21() { return _21; }
//   public final double e22() { return _22; }
//   public final double e23() { return _23; }
//   public final double e31() { return _31; }
//   public final double e32() { return _32; }
//   public final double e33() { return _33; }

   //[ 重new, 以免矩陣值在subclass變動. 
   public ImuV3D row1() {  return new ImuV3D(_r1);  }
   public ImuV3D row2() {  return new ImuV3D(_r2);  }
   public ImuV3D row3() {  return new ImuV3D(_r3);  }
   public final ImuV3D negRow1() {  return _r1.neg();  }
   public final ImuV3D negRow2() {  return _r2.neg();  }
   public final ImuV3D negRow3() {  return _r3.neg();  }
//   public final ImuV3D row1() {  return new ImuV3D(_11, _12, _13);  }
//   public final ImuV3D row2() {  return new ImuV3D(_21, _22, _23);  }
//   public final ImuV3D row3() {  return new ImuV3D(_31, _32, _33);  }
//   public final ImuV3D negRow1() {  return new ImuV3D(-_11, -_12, -_13);  }
//   public final ImuV3D negRow2() {  return new ImuV3D(-_21, -_22, -_23);  }
//   public final ImuV3D negRow3() {  return new ImuV3D(-_31, -_32, -_33);  }

   public final ImuV3D col1() {  return new ImuV3D(_r1.x, _r2.x, _r3.x);  }
   public final ImuV3D col2() {  return new ImuV3D(_r1.y, _r2.y, _r3.y);  }
   public final ImuV3D col3() {  return new ImuV3D(_r1.z, _r2.z, _r3.z);  }
   public final ImuV3D negCol1() {  return new ImuV3D(-_r1.x,-_r2.x,-_r3.x); }
   public final ImuV3D negCol2() {  return new ImuV3D(-_r1.y,-_r2.y,-_r3.y); }
   public final ImuV3D negCol3() {  return new ImuV3D(-_r1.z,-_r2.z,-_r3.z); }
//   public final ImuV3D col1() {  return new ImuV3D(_11, _21, _31);  }
//   public final ImuV3D col2() {  return new ImuV3D(_12, _22, _32);  }
//   public final ImuV3D col3() {  return new ImuV3D(_13, _23, _33);  }
//   public final ImuV3D negCol1() {  return new ImuV3D(-_11, -_21, -_31);  }
//   public final ImuV3D negCol2() {  return new ImuV3D(-_12, -_22, -_32);  }
//   public final ImuV3D negCol3() {  return new ImuV3D(-_13, -_23, -_33);  }

//   public final double row1NormSq() {  return _11*_11+_12*_12+_13*_13;  }
//   public final double row2NormSq() {  return _21*_21+_22*_22+_23*_23;  }
//   public final double row3NormSq() {  return _31*_31+_32*_32+_33*_33;  }
//   public final double row1Norm() {  return Math.sqrt(_11*_11+_12*_12+_13*_13);  }
//   public final double row2Norm() {  return Math.sqrt(_21*_21+_22*_22+_23*_23);  }
//   public final double row3Norm() {  return Math.sqrt(_31*_31+_32*_32+_33*_33);  }
   public final double rowMeanSqNorm() { //: 平均列長
   	  return Math.sqrt((_r1.normSq()+_r2.normSq()+_r3.normSq())/3);
//   	  return Math.sqrt((row1NormSq()+row2NormSq()+row3NormSq())/3);  
   }	

   public final ImuV3D getDiagonal() {  
      return new ImuV3D(_r1.x, _r2.y, _r3.z);  
   }
//   public final ImuV3D getDiagonal() {  return new ImuV3D(_11, _22, _33);  }

   public String toString() {
      return "[" + _r1.x + ", " + _r1.y + ", " + _r1.z + "; "
                 + _r2.x + ", " + _r2.y + ", " + _r2.z + "; "
                 + _r3.x + ", " + _r3.y + ", " + _r3.z + "] " ;
//      return "[" + _11 + ", " + _12 + ", " + _13 + "; "
//                 + _21 + ", " + _22 + ", " + _23 + "; "
//                 + _31 + ", " + _32 + ", " + _33 + "] " ;
   }

   public final boolean equals(ImuM3D m2) {
      return (_r1.equals(m2._r1) && _r2.equals(m2._r2) && _r3.equals(m2._r3) );
//      return (_11==m2._11 && _12==m2._12 && _13==m2._13
//           && _21==m2._21 && _22==m2._22 && _23==m2._23
//           && _31==m2._31 && _32==m2._32 && _33==m2._33 );
   }

   public final boolean equals(ImuM3D m2, double errSq) {
      return (this.distanceSq(m2)<errSq);
   }

   public final boolean notEquals(ImuM3D m2) {
      return !equals(m2);
   }

   public final boolean notEquals(ImuM3D m2, double errSq) {
      return !equals(m2, errSq);
   }

   public final boolean equals(Object v2) {
      return equals((ImuM3D)v2);
   }

   public final int hashCode() {
      long tL;  int tI=17;
      tL=Double.doubleToLongBits(_r1.x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r1.y); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r1.z); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r2.x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r2.y); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r2.z); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r3.x); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r3.y); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_r3.z); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_11); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_12); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_13); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_21); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_22); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_23); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_31); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_32); tI=tI*37+(int)(tL^(tL>>32));
//      tL=Double.doubleToLongBits(_33); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero() {
      return (  _r1.x==0 && _r1.y==0 && _r1.z==0 &&
                _r2.x==0 && _r2.y==0 && _r2.z==0 &&
                _r3.x==0 && _r3.y==0 && _r3.z==0
             );
//      return (  _11==0 && _12==0 && _13==0 &&
//                _21==0 && _22==0 && _23==0 &&
//                _31==0 && _32==0 && _33==0
//             );
   }

   public final boolean notZero() {
      return (!isZero());
   }

   //[-------- implements DuplicableI ------------------
   public final MuM3D duplicate() {  return new MuM3D(this); }
   //]-------- implements DuplicableI ------------------

   public final MuM3Df toM3Df() {
      return new MuM3Df(
         (float)_r1.x,(float)_r1.y,(float)_r1.z,
         (float)_r2.x,(float)_r2.y,(float)_r2.z,
         (float)_r3.x,(float)_r3.y,(float)_r3.z
//         (float)_11,(float)_12,(float)_13,
//         (float)_21,(float)_22,(float)_23,
//         (float)_31,(float)_32,(float)_33
      );
   }

   //[-------- implements PrintableI -------------------
   public final void printTo(TxOStream ooo) throws java.io.IOException {
      ooo.p("[").pc(_r1.x).pc(_r1.y).p(_r1.z).p("; ")
                .pc(_r2.x).pc(_r2.y).p(_r2.z).p("; ")
                .pc(_r3.x).pc(_r3.y).p(_r3.z).p("] ");
//      ooo.p("[").pc(_11).pc(_12).p(_13).p("; ")
//                .pc(_21).pc(_22).p(_23).p("; ")
//                .pc(_31).pc(_32).p(_33).p("] ");
   }

   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException 
   {
      ooo.p("[").wpc(w,_r1.x).wpc(w,_r1.y).wp(w,_r1.z).p("; ")
                .wpc(w,_r2.x).wpc(w,_r2.y).wp(w,_r2.z).p("; ")
                .wpc(w,_r3.x).wpc(w,_r3.y).wp(w,_r3.z).p("] ");
//      ooo.p("[").wpc(w,_11).wpc(w,_12).wp(w,_13).p("; ")
//                .wpc(w,_21).wpc(w,_22).wp(w,_23).p("; ")
//                .wpc(w,_31).wpc(w,_32).wp(w,_33).p("] ");
   }
   //]-------- implements PrintableI -------------------

   //---------------------------------------------------
   public final ImuM3D add(ImuM3D m2) {
      return new MuM3D(this).addBy(m2);
   }

   public final ImuM3D sub(ImuM3D m2) {
      return new MuM3D(this).subBy(m2);
   }

   public final ImuM3D neg() {
      return new ImuM3D(
         -_r1.x,-_r1.y,-_r1.z,
         -_r2.x,-_r2.y,-_r2.z,
         -_r3.x,-_r3.y,-_r3.z
      );
//      return new ImuM3D(
//         -_11,-_12,-_13,
//         -_21,-_22,-_23,
//         -_31,-_32,-_33
//      );
   }

   public final ImuM3D mul(double k) {  //:  scalar product
      return new MuM3D(this).mulBy(k);
   }

   public final ImuM3D div(double k) {  
      if(k==0) throw new IllegalArgumentException("zero divider"); 
      return this.mul(1.0/k);
   }

@Deprecated
   public final ImuM3D sMul(double k) {  //:  scalar product
      return new MuM3D(this).smulBy(k);
   }
@Deprecated
public final ImuM3D smul(double k) {  //:  scalar product
      return new MuM3D(this).smulBy(k);
}

   public final ImuM3D mul(ImuM3D m2) { //:  this*m2
      return new MuM3D(
         _r1.x*m2._r1.x + _r1.y*m2._r2.x + _r1.z*m2._r3.x,
         _r1.x*m2._r1.y + _r1.y*m2._r2.y + _r1.z*m2._r3.y,
         _r1.x*m2._r1.z + _r1.y*m2._r2.z + _r1.z*m2._r3.z,
         _r2.x*m2._r1.x + _r2.y*m2._r2.x + _r2.z*m2._r3.x,
         _r2.x*m2._r1.y + _r2.y*m2._r2.y + _r2.z*m2._r3.y,
         _r2.x*m2._r1.z + _r2.y*m2._r2.z + _r2.z*m2._r3.z,
         _r3.x*m2._r1.x + _r3.y*m2._r2.x + _r3.z*m2._r3.x,
         _r3.x*m2._r1.y + _r3.y*m2._r2.y + _r3.z*m2._r3.y,
         _r3.x*m2._r1.z + _r3.y*m2._r2.z + _r3.z*m2._r3.z
//         _11*m2._11 + _12*m2._21 + _13*m2._31,
//         _11*m2._12 + _12*m2._22 + _13*m2._32,
//         _11*m2._13 + _12*m2._23 + _13*m2._33,
//         _21*m2._11 + _22*m2._21 + _23*m2._31,
//         _21*m2._12 + _22*m2._22 + _23*m2._32,
//         _21*m2._13 + _22*m2._23 + _23*m2._33,
//         _31*m2._11 + _32*m2._21 + _33*m2._31,
//         _31*m2._12 + _32*m2._22 + _33*m2._32,
//         _31*m2._13 + _32*m2._23 + _33*m2._33
      );
   }
   public final ImuM3D swapMul(ImuM3D m1) { //: m1*this
      return m1.mul(this);
   }

   public final ImuV3D mul(ImuV3D v2) {
      //  return this*v2;       v2 視為 3 by 1 矩陣,
      return new MuV3D(
         _r1.x*v2.x + _r1.y*v2.y + _r1.z*v2.z,
         _r2.x*v2.x + _r2.y*v2.y + _r2.z*v2.z,
         _r3.x*v2.x + _r3.y*v2.y + _r3.z*v2.z
//         _11*v2.x + _12*v2.y + _13*v2.z,
//         _21*v2.x + _22*v2.y + _23*v2.z,
//         _31*v2.x + _32*v2.y + _33*v2.z
      );
   }
   public final ImuV3D swapMul(ImuV3D v1) {
      //: return  v1 * this ;     v1 視為 1 by 3 矩陣
      return new MuV3D(
          v1.x*_r1.x + v1.y*_r2.x + v1.z*_r3.x,
          v1.x*_r1.y + v1.y*_r2.y + v1.z*_r3.y,
          v1.x*_r1.z + v1.y*_r2.z + v1.z*_r3.z
//          v1.x*_11 + v1.y*_21 + v1.z*_31,
//          v1.x*_12 + v1.y*_22 + v1.z*_32,
//          v1.x*_13 + v1.y*_23 + v1.z*_33
      );
   }
@Deprecated
   public final ImuV3D rMul(ImuV3D v) {  return this.mul(v);  }
@Deprecated
   public final ImuV3D lMul(ImuV3D v) {  return swapMul(v);   }

// old version: only for editing search 
//   public final ImuV3D transform(ImuV3D v) {
//      //: return  v * this ;
//   }

   public final double dot(ImuM3D m2) {
      return
         _r1.x*m2._r1.x + _r1.y*m2._r1.y + _r1.z*m2._r1.z +
         _r2.x*m2._r2.x + _r2.y*m2._r2.y + _r2.z*m2._r2.z +
         _r3.x*m2._r3.x + _r3.y*m2._r3.y + _r3.z*m2._r3.z ;
//         _11*m2._11 + _12*m2._12 + _13*m2._13 +
//         _21*m2._21 + _22*m2._22 + _23*m2._23 +
//         _31*m2._31 + _32*m2._32 + _33*m2._33 ;
   }

@Deprecated
public final double dotMul(ImuM3D m2) {
      return dot(m2);
}

   public final ImuM3D transposition() {
      return new MuM3D(this).transpose();
   }

   public final double det() {
   	  if( Double.isNaN(detOK) ) {
   	  	 detOK = _r1.x*_r2.y*_r3.z + _r2.x*_r3.y*_r1.z + _r3.x*_r2.z*_r1.y
               - _r1.z*_r2.y*_r3.x - _r2.z*_r3.y*_r1.x - _r3.z*_r2.x*_r1.y ;
//   	  	 detOK = _11*_22*_33 + _21*_32*_13 + _31*_23*_12
//               - _13*_22*_31 - _23*_32*_11 - _33*_21*_12 ;
      }
      return detOK;
   }

   public final ImuM3D adjoint() {
      return new ImuM3D(
         +(_r2.y*_r3.z-_r3.y*_r2.z),-(_r1.y*_r3.z-_r3.y*_r1.z),+(_r1.y*_r2.z-_r2.y*_r1.z),
         -(_r2.x*_r3.z-_r3.x*_r2.z),+(_r1.x*_r3.z-_r3.x*_r1.z),-(_r1.x*_r2.z-_r2.x*_r1.z),
         +(_r2.x*_r3.y-_r3.x*_r2.y),-(_r1.x*_r3.y-_r3.x*_r1.y),+(_r1.x*_r2.y-_r2.x*_r1.y)
//         +(_22*_33-_32*_23),-(_12*_33-_32*_13),+(_12*_23-_22*_13),
//         -(_21*_33-_31*_23),+(_11*_33-_31*_13),-(_11*_23-_21*_13),
//         +(_21*_32-_31*_22),-(_11*_32-_31*_12),+(_11*_22-_21*_12)
      );
   }

   public final ImuM3D inverse() {
   	  if(invOK==null) {
         final double d=det();
         if(d==0.0) throw new RuntimeException("Not invertible");
         invOK=adjoint().mul(1/d);  //: 不用div以免重覆check!=0
      }
      return invOK;
   }

   public final double normSq() {
      return (
           _r1.x*_r1.x + _r1.y*_r1.y + _r1.z*_r1.z
         + _r2.x*_r2.x + _r2.y*_r2.y + _r2.z*_r2.z
         + _r3.x*_r3.x + _r3.y*_r3.y + _r3.z*_r3.z
//           _11*_11 + _12*_12 + _13*_13
//         + _21*_21 + _22*_22 + _23*_23
//         + _31*_31 + _32*_32 + _33*_33
      );
   }

   public final double norm() {  return sqrt(normSq()); }

   public final double distanceSq(ImuM3D x) {
      return this.sub(x).normSq();
   }

   public final double distance(ImuM3D x) {
      return this.sub(x).norm();
   }

   public static ImuM3D R_x(double RotAngle) {
       final double S = Math.sin (RotAngle),
                    C = Math.cos (RotAngle);

       final MuM3D U=new MuM3D();
       U._r1.x = 1.0;  U._r1.y = 0.0;  U._r1.z = 0.0;
       U._r2.x = 0.0;  U._r2.y =  +C;  U._r2.z =  +S;
       U._r3.x = 0.0;  U._r3.y =  -S;  U._r3.z =  +C;
//       U._11 = 1.0;  U._12 = 0.0;  U._13 = 0.0;
//       U._21 = 0.0;  U._22 =  +C;  U._23 =  +S;
//       U._31 = 0.0;  U._32 =  -S;  U._33 =  +C;
       return U;
   }

   public static ImuM3D R_y(double RotAngle) {
      final double S = Math.sin (RotAngle),
                    C = Math.cos (RotAngle);
      final MuM3D U=new MuM3D();
      U._r1.x =  +C;  U._r1.y = 0.0;  U._r1.z =  -S;
      U._r2.x = 0.0;  U._r2.y = 1.0;  U._r2.z = 0.0;
      U._r3.x =  +S;  U._r3.y = 0.0;  U._r3.z =  +C;
//      U._11 =  +C;  U._12 = 0.0;  U._13 =  -S;
//      U._21 = 0.0;  U._22 = 1.0;  U._23 = 0.0;
//      U._31 =  +S;  U._32 = 0.0;  U._33 =  +C;
      return U;
   }

   public static ImuM3D R_z(double RotAngle) {
      final double S = Math.sin (RotAngle),
                   C = Math.cos (RotAngle);
      final MuM3D U=new MuM3D();
      U._r1.x  =  +C;  U._r1.y =  +S;  U._r1.z = 0.0;
      U._r2.x  =  -S;  U._r2.y =  +C;  U._r2.z = 0.0;
      U._r3.x  = 0.0;  U._r3.y = 0.0;  U._r3.z = 1.0;
//      U._11  =  +C;  U._12 =  +S;  U._13 = 0.0;
//      U._21  =  -S;  U._22 =  +C;  U._23 = 0.0;
//      U._31  = 0.0;  U._32 = 0.0;  U._33 = 1.0;
      return U;
   }

   //[ 以 this 的 rows 為新座標系, 
   //[ cordNew, cordOld 皆為 row vector
   public final ImuV3D oldCord(ImuV3D cordNew) {
      return cordNew.mul(this);
   }
   public final ImuV3D newCord(ImuV3D cordOld) {
      return cordOld.mul(this.inverse());
   }
   //] 


   //[==========   static part ===============================
   public static final ImuM3D ZERO = new ImuM3D();
   public static final ImuM3D ONE=new ImuM3D(1.0, 1.0, 1.0);
   public static final ImuM3D IDENTITY=ONE;  // an alias

   //[ basic vectors
   public static final ImuM3D E11=
      new ImuM3D(1.0, 0.0, 0.0,  0.0, 0.0, 0.0,  0.0, 0.0, 0.0);

   public static final ImuM3D E12=
      new ImuM3D(0.0, 1.0, 0.0,  0.0, 0.0, 0.0,  0.0, 0.0, 0.0);

   public static final ImuM3D E13=
      new ImuM3D(0.0, 0.0, 1.0,  0.0, 0.0, 0.0,  0.0, 0.0, 0.0);

   public static final ImuM3D E21=
      new ImuM3D(0.0, 0.0, 0.0,  1.0, 0.0, 0.0,  0.0, 0.0, 0.0);

   public static final ImuM3D E22=
      new ImuM3D(0.0, 0.0, 0.0,  0.0, 1.0, 0.0,  0.0, 0.0, 0.0);

   public static final ImuM3D E23=
      new ImuM3D(0.0, 0.0, 0.0,  0.0, 0.0, 1.0,  0.0, 0.0, 0.0);

   public static final ImuM3D E31=
      new ImuM3D(0.0, 0.0, 0.0,  0.0, 0.0, 0.0,  1.0, 0.0, 0.0);

   public static final ImuM3D E32=
      new ImuM3D(0.0, 0.0, 0.0,  0.0, 0.0, 0.0,  0.0, 1.0, 0.0);

   public static final ImuM3D E33=
      new ImuM3D(0.0, 0.0, 0.0,  0.0, 0.0, 0.0,  0.0, 0.0, 1.0);

   public static ImuM3D scalar(double c) {
      return new ImuM3D(c, c, c);
   }

   public static ImuM3D createDiagonal(
      double c1, double c2, double c3
   ) {
      return new ImuM3D(c1, c2, c3);
   }

   public static final double det(ImuM3D M) { 
      return M.det();
   }
   public static final double det(ImuV3D v1, ImuV3D v2, ImuV3D v3) { 
      return ImuV3D.triple(v1,v2,v3);
   }
   public static final double det(
      double d11, double d12, double d13,
      double d21, double d22, double d23,
      double d31, double d32, double d33
   ) {
      return  d11*d22*d33 + d21*d32*d13 + d31*d23*d12
            - d13*d22*d31 - d23*d32*d11 - d33*d21*d12 ;
   }

   //----------------
   public static final ImuM3D findMap(
      ImuV3D v1, ImuV3D v2, ImuV3D v3,
      ImuV3D w1, ImuV3D w2, ImuV3D w3
   ) {
      //[ 求M,使 v1*M=w1, v2*M=w2, v3*M=w3;
      //: v1,v2,v3 必須線性獨立, 否則會exception於inverse
      final ImuM3D A= new ImuM3D(v1,v2,v3);
      final ImuM3D B= new ImuM3D(w1,w2,w3);
      // 解 A*X=B
      return A.inverse().mul(B);
   }

   //]==========   static part ===============================

   //================================================

//////   public static void main(String[] aaa) { //: self test
////////>>>>>
//////      ImuM3D m1=new ImuM3D(1,2,3,4,5,6,7,8,9);
//////      System.out.println(m1 + ", det="+ m1.det());
//////      ImuM3D m2=new ImuM3D(1,2,3,4,5,6,7,8,10);
//////      System.out.println(m2 + ", det="+ m2.det());
//////      ImuM3D m3=m2.inverse();
//////      ImuM3D m4=m2.mul(m3);
//////      System.out.println(m4);
//////   }
}
