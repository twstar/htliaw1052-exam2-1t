package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.TxOStream;

public class ImuM3Df implements DuplicableI<MuM3Df>, PrintableI
{
   float _11, _12, _13, _21, _22, _23, _31, _32, _33;

   //-------------------------------------------------

   public     ImuM3Df() {
      _11=0f; _12=0f; _13=0f;
      _21=0f; _22=0f; _23=0f;
      _31=0f; _32=0f; _33=0f;
   }
   public     ImuM3Df(
      float d11, float d12, float d13,
      float d21, float d22, float d23,
      float d31, float d32, float d33
   ) {
      _11=d11; _12=d12; _13=d13;
      _21=d21; _22=d22; _23=d23;
      _31=d31; _32=d32; _33=d33;
   }
   public     ImuM3Df(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      _11=v1.x(); _12=v1.y(); _13=v1.z();
      _21=v2.x(); _22=v2.y(); _23=v2.z();
      _31=v3.x(); _32=v3.y(); _33=v3.z();
   }
   public     ImuM3Df(float d1, float d2, float d3) {
      _11=d1;  _12=0f; _13=0f;
      _21=0f; _22=d2;  _23=0f;
      _31=0f; _32=0f; _33=d3;
   }
   public     ImuM3Df(ImuM3Df src) {
      _11=src._11; _12=src._12; _13=src._13;
      _21=src._21; _22=src._22; _23=src._23;
      _31=src._31; _32=src._32; _33=src._33;
   }

   //--------------------------------------------------
   public float e11() {  return _11; }
   public float e12() {  return _12; }
   public float e13() {  return _13; }
   public float e21() {  return _21; }
   public float e22() {  return _22; }
   public float e23() {  return _23; }
   public float e31() {  return _31; }
   public float e32() {  return _32; }
   public float e33() {  return _33; }

   public ImuV3Df row1() {  return new ImuV3Df(_11, _12, _13);  }
   public ImuV3Df row2() {  return new ImuV3Df(_21, _22, _23);  }
   public ImuV3Df row3() {  return new ImuV3Df(_31, _32, _33);  }

   public ImuV3Df col1() {  return new ImuV3Df(_11, _21, _31);  }
   public ImuV3Df col2() {  return new ImuV3Df(_12, _22, _32);  }
   public ImuV3Df col3() {  return new ImuV3Df(_13, _23, _33);  }

   public final ImuV3Df getDiagonal() {  return new ImuV3Df(_11, _22, _33);  }

   public String toString() {
      return "[" + _11 + ", " + _12 + ", " + _13 + "; "
                 + _21 + ", " + _22 + ", " + _23 + "; "
                 + _31 + ", " + _32 + ", " + _33 + "] " ;
   }
   public final boolean equals(ImuM3Df m2) {
      return (_11==m2._11 && _12==m2._12 && _13==m2._13
           && _21==m2._21 && _22==m2._22 && _23==m2._23
           && _31==m2._31 && _32==m2._32 && _33==m2._33 );
   }
   public final boolean notEquals(ImuM3Df m2) {
      return !equals(m2);
   }
   public final boolean equals(ImuM3Df m2, float errSq) {
      return (this.distanceSq(m2)<errSq);
   }
   public final boolean notEquals(ImuM3Df m2, float errSq) {
      return !equals(m2, errSq);
   }

   public final boolean equals(Object v2) {
      return equals((ImuM3Df)v2);
   }

   public final int hashCode() {
      long tL;  int tI=17;
      tL=Double.doubleToLongBits(_11); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_12); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_13); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_21); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_22); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_23); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_31); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_32); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(_33); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero() {
      return (  _11==0 && _12==0 && _13==0
             && _21==0 && _22==0 && _23==0
             && _31==0 && _32==0 && _33==0
              );
   }
   public final boolean notZero() {  return (  !isZero() );  }


   //[-------- implements DuplicableI ------------------
   public final MuM3Df duplicate() {  return new MuM3Df(this); }
   //]-------- implements DuplicableI ------------------

   public final MuM3D toM3D() {
      return new MuM3D(_11,_12,_13,_21,_22,_23,_31,_32,_33);
   }

   //[-------- implements PrintableI -------------------
   public final void printTo(TxOStream ooo) throws java.io.IOException {
      ooo.p("[").pc(_11).pc(_12).p(_13).p("; ")
                .pc(_21).pc(_22).p(_23).p("; ")
                .pc(_31).pc(_32).p(_33).p("] ");
   }
   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {
      ooo.p("[").wpc(w,_11).wpc(w,_12).wp(w,_13).p("; ")
                .wpc(w,_21).wpc(w,_22).wp(w,_23).p("; ")
                .wpc(w,_31).wpc(w,_32).wp(w,_33).p("] ");
   }

   //]-------- implements PrintableI -------------------

   //--------
   public final ImuM3Df add(ImuM3Df m2) {
      return new MuM3Df(this).addBy(m2);
   }

   public final ImuM3Df sub(ImuM3Df m2) {
      return new MuM3Df(this).subBy(m2);
   }

   public final ImuM3Df neg() {
      return new ImuM3Df(
         -_11,-_12,-_13,
         -_21,-_22,-_23,
         -_31,-_32,-_33
      );
   }

   public final ImuM3Df mul(float k) {  //:  scalar product
      return new MuM3Df(this).mulBy(k);
   }

   public final ImuM3Df div(float k) {
      if(k==0) throw new IllegalArgumentException("zero divider");
      return this.mul((float)(1.0/k));
   }

@Deprecated
public final ImuM3Df sMul(float k) {  //:  scalar product
   return new MuM3Df(this).smulBy(k);
}

//[ deprecated
@Deprecated
public final ImuM3Df smul(float k) {  //:  scalar product
      return new MuM3Df(this).smulBy(k);
}
//

   public final ImuM3Df mul(ImuM3Df m2) {
      return new ImuM3Df(
         _11*m2._11 + _12*m2._21 + _13*m2._31,
         _11*m2._12 + _12*m2._22 + _13*m2._32,
         _11*m2._13 + _12*m2._23 + _13*m2._33,
         _21*m2._11 + _22*m2._21 + _23*m2._31,
         _21*m2._12 + _22*m2._22 + _23*m2._32,
         _21*m2._13 + _22*m2._23 + _23*m2._33,
         _31*m2._11 + _32*m2._21 + _33*m2._31,
         _31*m2._12 + _32*m2._22 + _33*m2._32,
         _31*m2._13 + _32*m2._23 + _33*m2._33
      );
   }


   public final ImuV3Df mul(ImuV3Df v2) {
      // v2 視為 3 by 1 矩陣, 做右乘
      return new ImuV3Df(
         _11*v2.x + _12*v2.y + _13*v2.z,
         _21*v2.x + _22*v2.y + _23*v2.z,
         _31*v2.x + _32*v2.y + _33*v2.z
      );
   }

   public final ImuV3Df rMul(ImuV3Df v) {
      return this.mul(v);
   }

   public final ImuV3Df lMul(ImuV3Df v) {
      //: return  v * this ;
      return new MuV3Df(
          v.x*_11 + v.y*_21 + v.z*_31,
          v.x*_12 + v.y*_22 + v.z*_32,
          v.x*_13 + v.y*_23 + v.z*_33
      );
   }

/*
   public final ImuV3Df transform(ImuV3Df v) {
      //: return  v * this ;
      return new ImuV3Df(
          v.x()*_11 + v.y()*_21 + v.z()*_31,
          v.x()*_12 + v.y()*_22 + v.z()*_32,
          v.x()*_13 + v.y()*_23 + v.z()*_33
      );
   }
*/

   public final float dot(ImuM3Df m2) {
      return
         _11*m2._11 + _12*m2._12 + _13*m2._13 +
         _21*m2._21 + _22*m2._22 + _23*m2._23 +
         _31*m2._31 + _32*m2._32 + _33*m2._33 ;
   }

@Deprecated
public final float dotMul(ImuM3Df m2) {
   return dot(m2);
}

   public final ImuM3Df transposition() {
      return new MuM3Df(this).transpose();
   }

   public final float det() {
      return  _11*_22*_33 + _21*_32*_13 + _31*_23*_12
            - _13*_22*_31 - _23*_32*_11 - _33*_21*_12 ;
   }

   public final ImuM3Df adjoint() {
      return new ImuM3Df(
         +(_22*_33-_32*_23),-(_12*_33-_32*_13),+(_12*_23-_22*_13),
         -(_21*_33-_31*_23),+(_11*_33-_31*_13),-(_11*_23-_21*_13),
         +(_21*_32-_31*_22),-(_11*_32-_31*_12),+(_11*_22-_21*_12)
      );
   }

   public final ImuM3Df inverse() {
      float d=det();
      if(d==0f) throw new RuntimeException("Not invertible");
      return adjoint().smul(1/det());  //: 不用div以免重覆check!=0
   }

   public final float normSq() {
      return (
           _11*_11 + _12*_12 + _13*_13
         + _21*_21 + _22*_22 + _23*_23
         + _31*_31 + _32*_32 + _33*_33
      );
   }
   public final float norm() {  return (float)Math.sqrt(normSq()); }

   public final float distanceSq(ImuM3Df x) {
      return this.sub(x).normSq();
   }
   public final float distance(ImuM3Df x) {
      return this.sub(x).norm();
   }

   //------------------------------
   public static ImuM3Df R_x(double RotAngle) {
       final float S = (float)Math.sin (RotAngle),
                   C = (float)Math.cos (RotAngle);

       final MuM3Df U=new MuM3Df();
       U._11 = 1.0f;  U._12 = 0.0f;  U._13 = 0.0f;
       U._21 = 0.0f;  U._22 =  +C;  U._23 =  +S;
       U._31 = 0.0f;  U._32 =  -S;  U._33 =  +C;
       return U;
    }

    public static ImuM3Df R_y(double RotAngle) {
       final float S = (float)Math.sin (RotAngle),
                   C = (float)Math.cos (RotAngle);
       final MuM3Df U=new MuM3Df();
       U._11 =  +C;  U._12 = 0.0f;  U._13 =  -S;
       U._21 = 0.0f; U._22 = 1.0f;  U._23 = 0.0f;
       U._31 =  +S;  U._32 = 0.0f;  U._33 =  +C;
       return U;
    }

    public static ImuM3Df R_z(double RotAngle) {
       final float S = (float)Math.sin (RotAngle),
                   C = (float)Math.cos (RotAngle);
       final MuM3Df U=new MuM3Df();
       U._11  =  +C;  U._12 =  +S;  U._13 = 0.0f;
       U._21  =  -S;  U._22 =  +C;  U._23 = 0.0f;
       U._31  = 0.0f; U._32 = 0.0f; U._33 = 1.0f;
       return U;
    }


   //[==========   static part ===============================
   public static final ImuM3Df ZERO=new ImuM3Df();
   public static final ImuM3Df ONE=new ImuM3Df(1f, 1f, 1f);
   public static final ImuM3Df IDENTITY=ONE;  // an alias

   public static final ImuM3Df E11=
      new ImuM3Df(1f, 0f, 0f,  0f, 0f, 0f,  0f, 0f, 0f);
   public static final ImuM3Df E12=
      new ImuM3Df(0f, 1f, 0f,  0f, 0f, 0f,  0f, 0f, 0f);
   public static final ImuM3Df E13=
      new ImuM3Df(0f, 0f, 1f,  0f, 0f, 0f,  0f, 0f, 0f);
   public static final ImuM3Df E21=
      new ImuM3Df(0f, 0f, 0f,  1f, 0f, 0f,  0f, 0f, 0f);
   public static final ImuM3Df E22=
      new ImuM3Df(0f, 0f, 0f,  0f, 1f, 0f,  0f, 0f, 0f);
   public static final ImuM3Df E23=
      new ImuM3Df(0f, 0f, 0f,  0f, 0f, 1f,  0f, 0f, 0f);
   public static final ImuM3Df E31=
      new ImuM3Df(0f, 0f, 0f,  0f, 0f, 0f,  1f, 0f, 0f);
   public static final ImuM3Df E32=
      new ImuM3Df(0f, 0f, 0f,  0f, 0f, 0f,  0f, 1f, 0f);
   public static final ImuM3Df E33=
      new ImuM3Df(0f, 0f, 0f,  0f, 0f, 0f,  0f, 0f, 1f);

   public static ImuM3Df scalar(float c) {
      return new ImuM3Df(c, c, c);
   }

   public static ImuM3Df createDiagonal(
      float c1, float c2, float c3
   ) {
      return new ImuM3Df(c1, c2, c3);
   }

   //----------------
   public static final ImuM3Df findMap(
      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3,
      ImuV3Df w1, ImuV3Df w2, ImuV3Df w3
   ) {
      //[ 求M,使 v1*M=w1, v2*M=w2, v3*M=w3;
      //: 假設v1,v2,v3 線性獨立, 否則會exception於inverse
      final ImuM3Df A= new ImuM3Df(v1,v2,v3);
      final ImuM3Df B= new ImuM3Df(w1,w2,w3);
      // 解 A*X=B
      return A.inverse().mul(B);
   }

   //]==========   static part ===============================

   //================================================

//////   public static void main(String[] aaa) { //: self test
////////>>>>>
//////      ImuM3Df m1=new ImuM3Df(1,2,3,4,5,6,7,8,9);
//////      System.out.println(m1 + ", det="+ m1.det());
//////      ImuM3Df m2=new ImuM3Df(1,2,3,4,5,6,7,8,10);
//////      System.out.println(m2 + ", det="+ m2.det());
//////      ImuM3Df m3=m2.inverse();
//////      ImuM3Df m4=m2.mul(m3);
//////      System.out.println(m4);
//////   }

}