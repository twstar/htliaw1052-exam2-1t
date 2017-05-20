package tw.fc.gui;
import java.io.IOException;
//import tw.fc.gui.MuV3D;
//import tw.fc.gui.ImuV3D;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;

public class MuM3Df extends ImuM3Df
       implements SetableI<ImuM3Df>, ScannableI // SetableI
{
//I   float _11, _12, _13, _21, _22, _23, _31, _32, _33;

   public void setE11(float v) {  _11=v;  }
   public void setE12(float v) {  _12=v;  }
   public void setE13(float v) {  _13=v;  }
   public void setE21(float v) {  _21=v;  }
   public void setE22(float v) {  _22=v;  }
   public void setE23(float v) {  _23=v;  }
   public void setE31(float v) {  _31=v;  }
   public void setE32(float v) {  _32=v;  }
   public void setE33(float v) {  _33=v;  }

//-------------------------------------------------
//I   public String toString() {  ... }
//I   public final boolean equals(ImuM3Df m2) { ... }
//I   public final boolean equals(ImuM3Df m2, float epsilon) { ... }

//-------------------------------------------------
   public     MuM3Df() {  super();   }

   public     MuM3Df(
      float d11, float d12, float d13,
      float d21, float d22, float d23,
      float d31, float d32, float d33
   ) {
      super(d11,d12,d13, d21,d22,d23, d31,d32,d33);
   }

   public     MuM3Df(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      super(v1,v2,v3);
   }

   public     MuM3Df(float d1, float d2, float d3) {
      super(d1,d2,d3);
   }

   public     MuM3Df(ImuM3Df src) {
      super(src);
   }

//--------------------------------------------------

   public final MuM3Df setBy(
      float d11, float d12, float d13,
      float d21, float d22, float d23,
      float d31, float d32, float d33
   ) {
      _11=d11; _12=d12; _13=d13;
      _21=d21; _22=d22; _23=d23;
      _31=d31; _32=d32; _33=d33;
      return this;
   }
/////   public final SetableI setBy(DuplicableI s) {
/////      return setBy((ImuM3Df)s);
/////   }
   public final MuM3Df setBy(ImuM3Df src) {
      _11=src._11; _12=src._12; _13=src._13;
      _21=src._21; _22=src._22; _23=src._23;
      _31=src._31; _32=src._32; _33=src._33;
      return this;
   }
   public final MuM3Df setByRow(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      _11=v1.x(); _12=v1.y(); _13=v1.z();
      _21=v2.x(); _22=v2.y(); _23=v2.z();
      _31=v3.x(); _32=v3.y(); _33=v3.z();
      return this;
   }
   public final MuM3Df setBy(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      return setByRow(v1, v2, v3);
   }
   public final MuM3Df setByCol(ImuV3Df v1, ImuV3Df v2, ImuV3Df v3) {
      _11=v1.x(); _12=v2.x(); _13=v3.x();
      _21=v1.y(); _22=v2.y(); _23=v3.y();
      _31=v1.z(); _32=v2.z(); _33=v3.z();
      return this;
   }

   public final MuM3Df row1SetBy(ImuV3Df v1) {
      _11=v1.x(); _12=v1.y(); _13=v1.z();
      return this;
   }
   public final MuM3Df row2SetBy(ImuV3Df v2) {
      _21=v2.x(); _22=v2.y(); _23=v2.z();
      return this;
   }
   public final MuM3Df row3SetBy(ImuV3Df v3) {
      _31=v3.x(); _32=v3.y(); _33=v3.z();
      return this;
   }

   public final MuM3Df col1SetBy(ImuV3Df v1) {
      _11=v1.x(); _21=v1.y(); _31=v1.z();
      return this;
   }
   public final MuM3Df col2SetBy(ImuV3Df v2) {
      _12=v2.x(); _22=v2.y(); _32=v2.z();
      return this;
   }
   public final MuM3Df col3SetBy(ImuV3Df v3) {
      _13=v3.x(); _23=v3.y(); _33=v3.z();
      return this;
   }


   public void scanFrom(TxIStream iii) throws IOException {
         iii.skipWS().expect('['); // .skipWS();  //: 由getDouble負責
         _11=iii.get_float();
         iii.skipWS().expect(','); // .skipWS();
         _12=iii.get_float();
         iii.skipWS().expect(','); // .skipWS();
         _13=iii.get_float();
         iii.skipWS().expect(';');
         _21=iii.get_float();
         iii.skipWS().expect(','); // .skipWS();
         _22=iii.get_float();
         iii.skipWS().expect(','); // .skipWS();
         _23=iii.get_float();
         iii.skipWS().expect(';');
         _31=iii.get_float();
         iii.skipWS().expect(','); // .skipWS();
         _32=iii.get_float();
         iii.skipWS().expect(','); // .skipWS();
         _33=iii.get_float();
         iii.skipWS().expect(']');
   }

//I   public final DuplicableI duplicate() {  ... }

//--------

   // 原Long.parseLong不允許有前後之space
   public static MuM3Df parseM3D(String s) {
//      try {
      TxIStrStream inputS=new TxIStrStream(s);
      MuM3Df x=new MuM3Df();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }


//--------

//I   public final ImuM3Df add(ImuM3Df m2) {  ... }
//I   public final ImuM3Df sub(ImuM3Df m2) {  ... }

   public final MuM3Df addBy(ImuM3Df m2) {
      _11+= m2._11;  _12+= m2._12;  _13+= m2._13;
      _21+= m2._21;  _22+= m2._22;  _23+= m2._23;
      _31+= m2._31;  _32+= m2._32;  _33+= m2._33;
      return this;
   }
   public final MuM3Df subBy(ImuM3Df m2) {
      _11-= m2._11;  _12-= m2._12;  _13-= m2._13;
      _21-= m2._21;  _22-= m2._22;  _23-= m2._23;
      _31-= m2._31;  _32-= m2._32;  _33-= m2._33;
      return this;
   }

   public final MuM3Df addBy(
      float d11, float d12, float d13,
      float d21, float d22, float d23,
      float d31, float d32, float d33
   ) {
      _11+=d11; _12+=d12; _13+=d13;
      _21+=d21; _22+=d22; _23+=d23;
      _31+=d31; _32+=d32; _33+=d33;
      return this;
   }

   public final MuM3Df subBy(
      float d11, float d12, float d13,
      float d21, float d22, float d23,
      float d31, float d32, float d33
   ) {
      _11-=d11; _12-=d12; _13-=d13;
      _21-=d21; _22-=d22; _23-=d23;
      _31-=d31; _32-=d32; _33-=d33;
      return this;
   }

//I   public final ImuM3Df neg() {  ...  }
   public final MuM3Df negate() {
      _11= -_11; _12= -_12;  _13= -_13;
      _21= -_21; _22= -_22;  _23= -_23;
      _31= -_31; _32= -_32;  _33= -_33;
      return this;
   }

//I   public final ImuM3Df mul(float k) {  ...  }
   public final MuM3Df mulBy(float k) {
      _11*=k;  _12*=k;  _13*=k;
      _21*=k;  _22*=k;  _23*=k;
      _31*=k;  _32*=k;  _33*=k;
      return this;
   }


//I   public final ImuM3Df smul(float k) {  ...  }
@Deprecated
public final MuM3Df smulBy(float k) {
   _11*=k;  _12*=k;  _13*=k;
   _21*=k;  _22*=k;  _23*=k;
   _31*=k;  _32*=k;  _33*=k;
   return this;
}

   public final MuM3Df divBy(float k) {
      if(k==0.0) throw new IllegalArgumentException("divided by zero");
      _11/=k;  _12/=k;  _13/=k;
      _21/=k;  _22/=k;  _23/=k;
      _31/=k;  _32/=k;  _33/=k;
      return this;
   }



//I   public final ImuM3Df mul(ImuM3Df m2) {  ...  }
   public final MuM3Df mulBy(ImuM3Df m2) {
      //////setBy(mul(m2));  return this;
      return this.setBy(
         this._11*m2._11 + this._12*m2._21 + this._13*m2._31,
          this._11*m2._12 + this._12*m2._22 + this._13*m2._32,
           this._11*m2._13 + this._12*m2._23 + this._13*m2._33,
         this._21*m2._11 + this._22*m2._21 + this._23*m2._31,
          this._21*m2._12 + this._22*m2._22 + this._23*m2._32,
           this._21*m2._13 + this._22*m2._23 + this._23*m2._33,
         this._31*m2._11 + this._32*m2._21 + this._33*m2._31,
          this._31*m2._12 + this._32*m2._22 + this._33*m2._32,
           this._31*m2._13 + this._32*m2._23 + this._33*m2._33
      );
   }


//I   public final ImuM3Df transposition() {  ...  }
   public final MuM3Df transpose() {
      float t;
      t=_12; _12=_21; _21=t;
      t=_13; _13=_31; _31=t;
      t=_23; _23=_32; _32=t;
      return this;
   }

//I   public final float det() {  ...  }

//I   public final ImuM3Df adjoint() {

//I   public final ImuM3Df inverse() {  ...  }
   public final MuM3Df invert() {
      //////setBy( inverse() );
      //////return this;
      return this.setByInverse(this);
   }

   //[ this==src 仍可
   public final MuM3Df setByAdjoint(ImuM3Df src) {
      return this.setBy(
         +(src._22*src._33-src._32*src._23),
             -(src._12*src._33-src._32*src._13),
                +(src._12*src._23-src._22*src._13),
         -(src._21*src._33-src._31*src._23),
             +(src._11*src._33-src._31*src._13),
                -(src._11*src._23-src._21*src._13),
         +(src._21*src._32-src._31*src._22),
             -(src._11*src._32-src._31*src._12),
                +(src._11*src._22-src._21*src._12)
      );
   }

   //[ this==src 仍可
   public final MuM3Df setByInverse(ImuM3Df src) {
      final float d=src.det(); //: 可能 this==src, 必須先算好
      if(d==0.0) throw new IllegalArgumentException(
          "source matrix not invertible"
      );
      return this.setByAdjoint( src ).mulBy((float)(1.0/d));
                     //: 不用divBy 以免重覆check d!=0
   }

//I   public final float normSquare() {  ...  }
//I   public final float norm() {  ...  }
//I   public final float distance(MuM3Df x) {  ...  }

   //------------------------------


//I   public static final ImuM3Df findMap(
//I      ImuV3Df v1, ImuV3Df v2, ImuV3Df v3,
//I      ImuV3Df w1, ImuV3Df w2, ImuV3Df w3
//I   ) {  ...  }


   public final MuM3Df setByAdd(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this:=(m2+m3)
      return this.setBy(
         m2._11+m3._11, m2._12+m3._12, m2._13+m3._13,
         m2._21+m3._21, m2._22+m3._22, m2._23+m3._23,
         m2._31+m3._31, m2._32+m3._32, m2._33+m3._33
      );
   }

   public final MuM3Df addByAdd(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this+=(m2+m3)
      return this.addBy(
         m2._11+m3._11, m2._12+m3._12, m2._13+m3._13,
         m2._21+m3._21, m2._22+m3._22, m2._23+m3._23,
         m2._31+m3._31, m2._32+m3._32, m2._33+m3._33
      );
   }

   public final MuM3Df subByAdd(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this-=(m2+m3)
      return this.subBy(
         m2._11+m3._11, m2._12+m3._12, m2._13+m3._13,
         m2._21+m3._21, m2._22+m3._22, m2._23+m3._23,
         m2._31+m3._31, m2._32+m3._32, m2._33+m3._33
      );
   }

   public final MuM3Df setBySub(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this:=(m2-m3)
      return this.setBy(
         m2._11-m3._11, m2._12-m3._12, m2._13-m3._13,
         m2._21-m3._21, m2._22-m3._22, m2._23-m3._23,
         m2._31-m3._31, m2._32-m3._32, m2._33-m3._33
      );
   }

   public final MuM3Df addBySub(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this+=(m2-m3)
      return this.addBy(
         m2._11-m3._11, m2._12-m3._12, m2._13-m3._13,
         m2._21-m3._21, m2._22-m3._22, m2._23-m3._23,
         m2._31-m3._31, m2._32-m3._32, m2._33-m3._33
      );
   }

   public final MuM3Df subBySub(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this-=(m2-m3)
      return this.subBy(
         m2._11-m3._11, m2._12-m3._12, m2._13-m3._13,
         m2._21-m3._21, m2._22-m3._22, m2._23-m3._23,
         m2._31-m3._31, m2._32-m3._32, m2._33-m3._33
      );
   }

   public final MuM3Df setByMul(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this:=(m2*m3)
      return this.setBy(
         m2._11*m3._11 + m2._12*m3._21 + m2._13*m3._31,
          m2._11*m3._12 + m2._12*m3._22 + m2._13*m3._32,
           m2._11*m3._13 + m2._12*m3._23 + m2._13*m3._33,
         m2._21*m3._11 + m2._22*m3._21 + m2._23*m3._31,
          m2._21*m3._12 + m2._22*m3._22 + m2._23*m3._32,
           m2._21*m3._13 + m2._22*m3._23 + m2._23*m3._33,
         m2._31*m3._11 + m2._32*m3._21 + m2._33*m3._31,
          m2._31*m3._12 + m2._32*m3._22 + m2._33*m3._32,
           m2._31*m3._13 + m2._32*m3._23 + m2._33*m3._33
      );
   }

   public final MuM3Df addByMul(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this+=(m2*m3)
      return this.addBy(
         m2._11*m3._11 + m2._12*m3._21 + m2._13*m3._31,
          m2._11*m3._12 + m2._12*m3._22 + m2._13*m3._32,
           m2._11*m3._13 + m2._12*m3._23 + m2._13*m3._33,
         m2._21*m3._11 + m2._22*m3._21 + m2._23*m3._31,
          m2._21*m3._12 + m2._22*m3._22 + m2._23*m3._32,
           m2._21*m3._13 + m2._22*m3._23 + m2._23*m3._33,
         m2._31*m3._11 + m2._32*m3._21 + m2._33*m3._31,
          m2._31*m3._12 + m2._32*m3._22 + m2._33*m3._32,
           m2._31*m3._13 + m2._32*m3._23 + m2._33*m3._33
      );
   }

   public final MuM3Df subByMul(ImuM3Df m2, ImuM3Df m3) {
     //[ simulate this-=(m2*m3)
      return this.subBy(
         m2._11*m3._11 + m2._12*m3._21 + m2._13*m3._31,
          m2._11*m3._12 + m2._12*m3._22 + m2._13*m3._32,
           m2._11*m3._13 + m2._12*m3._23 + m2._13*m3._33,
         m2._21*m3._11 + m2._22*m3._21 + m2._23*m3._31,
          m2._21*m3._12 + m2._22*m3._22 + m2._23*m3._32,
           m2._21*m3._13 + m2._22*m3._23 + m2._23*m3._33,
         m2._31*m3._11 + m2._32*m3._21 + m2._33*m3._31,
          m2._31*m3._12 + m2._32*m3._22 + m2._33*m3._32,
           m2._31*m3._13 + m2._32*m3._23 + m2._33*m3._33
      );
   }

   public final MuM3Df setByLinearCombination(
      float a1, ImuM3Df m1, float a2, ImuM3Df m2, float a3, ImuM3Df m3
   ) {
     //[ simulate this:=(a1*m2+a2*m2+a3*m3)
      return this.setBy(
         a1*m1._11+a2*m2._11+a3*m3._11,
          a1*m1._12+a2*m2._12+a3*m3._12,
           a1*m1._13+a2*m2._13+a3*m3._13,
         a1*m1._21+a2*m2._21+a3*m3._21,
          a1*m1._22+a2*m2._22+a3*m3._22,
           a1*m1._23+a2*m2._23+a3*m3._23,
         a1*m1._31+a2*m2._31+a3*m3._31,
          a1*m1._32+a2*m2._32+a3*m3._32,
           a1*m1._33+a2*m2._33+a3*m3._33
      );
   }

   public final MuM3Df setByLinearCombination(
      float a1, ImuM3Df m1, float a2, ImuM3Df m2
   ) {
     //[ simulate this:=(a1*m2+a2*m2)
      return this.setBy(
         a1*m1._11+a2*m2._11,
          a1*m1._12+a2*m2._12,
           a1*m1._13+a2*m2._13,
         a1*m1._21+a2*m2._21,
          a1*m1._22+a2*m2._22,
           a1*m1._23+a2*m2._23,
         a1*m1._31+a2*m2._31,
          a1*m1._32+a2*m2._32,
           a1*m1._33+a2*m2._33
      );
   }

   //[ this 設為各列以drx為軸轉角A的結果
   public final MuM3Df rotBy(ImuV3Df drx, float A) {
      final MuV3Df b1=new MuV3Df(this.row1());
      final MuV3Df b2=new MuV3Df(this.row2());
      final MuV3Df b3=new MuV3Df(this.row3());
      b1.rotBy(drx, A);
      b2.rotBy(drx, A);
      b3.rotBy(drx, A);
      this.setBy(b1,b2,b3);
      return this;
   }
   
   //[ this 設為各列以drx為軸轉角A的結果, 參數是sinA
   public final MuM3Df sinRotBy(ImuV3Df drx, float sinA) {
      final MuV3Df b1=new MuV3Df(this.row1());
      final MuV3Df b2=new MuV3Df(this.row2());
      final MuV3Df b3=new MuV3Df(this.row3());
      b1.sinRotBy(drx, sinA);
      b2.sinRotBy(drx, sinA);
      b3.sinRotBy(drx, sinA);
      this.setBy(b1,b2,b3);
      return this;
   }

   //[==========   static part ===============================
   public static MuM3Df scalar(float c) {
      return new MuM3Df(c, c, c);
   }

   //[ 不稱為 diagonal 是怕誤為取出對角線向量
   public static final MuM3Df createDiagonal(
      float c1, float c2, float c3
   ) {
      return new MuM3Df(c1, c2, c3);
   }


   //===========================================
//////   public static void main(String[] aaa) { //: self test
////////>>>>>
//////      MuM3Df m1=new MuM3Df(1,2,3,4,5,6,7,8,9);
//////      System.out.println(m1 + ", det="+ m1.det());
//////      MuM3Df m2=new MuM3Df(1,2,3,4,5,6,7,8,10);
//////      System.out.println(m2 + ", det="+ m2.det());
//////      MuM3Df m3=new MuM3Df(m2.inverse());
//////      MuM3Df m4=new MuM3Df(m2.mul(m3));
//////      System.out.println(m4);
//////   }
}