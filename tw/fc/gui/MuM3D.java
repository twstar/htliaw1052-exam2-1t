package tw.fc.gui;
import java.io.IOException;
import tw.fc.gui.MuV3D;
import tw.fc.gui.ImuV3D;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;

public class MuM3D extends ImuM3D
       implements SetableI<ImuM3D>, ScannableI //  SetableI
{
//I   double _11, _12, _13, _21, _22, _23, _31, _32, _33;

   public final void setE11(double v) {  
   	  _r1.x=v;  detOK=Double.NaN; invOK=null; 
//   	  _11=v;  detOK=Double.NaN; invOK=null; 
   }
   public final void setE12(double v) {  
   	  _r1.y=v;  detOK=Double.NaN; invOK=null;  
//   	  _12=v;  detOK=Double.NaN; invOK=null;  
   }
   public final void setE13(double v) {  
   	  _r1.z=v;  detOK=Double.NaN; invOK=null;
//   	  _13=v;  detOK=Double.NaN; invOK=null;
   }
   public final void setE21(double v) {  
   	  _r2.x=v;  detOK=Double.NaN; invOK=null;
//   	  _21=v;  detOK=Double.NaN; invOK=null;
   }
   public final void setE22(double v) {  
   	  _r2.y=v;  detOK=Double.NaN; invOK=null;
//   	  _22=v;  detOK=Double.NaN; invOK=null;
   }
   public final void setE23(double v) {  
   	  _r2.z=v;  detOK=Double.NaN; invOK=null;
//   	  _23=v;  detOK=Double.NaN; invOK=null;
   }
   public final void setE31(double v) {  
   	  _r3.x=v;  detOK=Double.NaN; invOK=null;
//   	  _31=v;  detOK=Double.NaN; invOK=null;
   }
   public final void setE32(double v) {  
   	  _r3.y=v;  detOK=Double.NaN; invOK=null;
//   	  _32=v;  detOK=Double.NaN; invOK=null;
   }
   public final void setE33(double v) {  
   	  _r3.z=v;  detOK=Double.NaN; invOK=null;
//   	  _33=v;  detOK=Double.NaN; invOK=null;
   }

//-------------------------------------------------
//I   public String toString() {  ... }
//I   public final boolean equals(ImuM3D m2) { ... }
//I   public final boolean equals(ImuM3D m2, double epsilon) { ... }

//-------------------------------------------------

   //[ default ctor, create a zero matrix
   public     MuM3D() {  super();   }

   //[ constructed by entries in row-major order
   public     MuM3D(
      double d11, double d12, double d13,
      double d21, double d22, double d23,
      double d31, double d32, double d33
   ) {
      super(d11,d12,d13,  d21,d22,d23,  d31,d32,d33);
   }

   //[ constructed by row vectors
   public     MuM3D(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      super(v1,v2,v3);
   }

   //[ construct a diagonal matrix 
   public     MuM3D(double d1, double d2, double d3) {
      super(d1,d2,d3);
   }

   //[ copy-ctor
   public     MuM3D(ImuM3D src) {
      super(src);
   }

//--------------------------------------------------

   @Override public final MuV3D row1() {  
      detOK=Double.NaN; invOK=null;   return _r1;  
   }
   @Override public final MuV3D row2() {  
      detOK=Double.NaN; invOK=null;   return _r2;  
   }
   @Override public final MuV3D row3() {  
      detOK=Double.NaN; invOK=null;   return _r3;  
   }

   public final MuM3D setBy(
      double d11, double d12, double d13,
      double d21, double d22, double d23,
      double d31, double d32, double d33
   ) {
      detOK=Double.NaN; invOK=null;
      _r1.x=d11; _r1.y=d12; _r1.z=d13;
      _r2.x=d21; _r2.y=d22; _r2.z=d23;
      _r3.x=d31; _r3.y=d32; _r3.z=d33;
//      _11=d11; _12=d12; _13=d13;
//      _21=d21; _22=d22; _23=d23;
//      _31=d31; _32=d32; _33=d33;
      return this;
   }

//   public final SetableI setBy(DuplicableI s) {
//      return setBy((ImuM3D)s);
//   }

   public final MuM3D setBy(ImuM3D src) {
      detOK=Double.NaN; invOK=null;
      _r1.setBy(src._r1);  _r2.setBy(src._r2); _r3.setBy(src._r3);
//      _11=src._11; _12=src._12; _13=src._13;
//      _21=src._21; _22=src._22; _23=src._23;
//      _31=src._31; _32=src._32; _33=src._33;
      return this;
   }

   public final MuM3D setByRow(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      detOK=Double.NaN; invOK=null;
      _r1.setBy(v1);  _r2.setBy(v2);  _r3.setBy(v3);
//      _11=v1.x(); _12=v1.y(); _13=v1.z();
//      _21=v2.x(); _22=v2.y(); _23=v2.z();
//      _31=v3.x(); _32=v3.y(); _33=v3.z();
      return this;
   }

   public final MuM3D setBy(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      return setByRow(v1, v2, v3);
   }

   public final MuM3D setByCol(ImuV3D v1, ImuV3D v2, ImuV3D v3) {
      detOK=Double.NaN; invOK=null;
      _r1.x=v1.x; _r1.y=v2.x; _r1.z=v3.x;
      _r2.x=v1.y; _r2.y=v2.y; _r2.z=v3.y;
      _r3.x=v1.z; _r3.y=v2.z; _r3.z=v3.z;
      return this;
   }

   public final MuM3D row1SetBy(ImuV3D v1) {
      detOK=Double.NaN; invOK=null;
      _r1.setBy(v1);
//      _11=v1.x(); _12=v1.y(); _13=v1.z();
      return this;
   }
   public final MuM3D row2SetBy(ImuV3D v2) {
      detOK=Double.NaN; invOK=null;
      _r2.setBy(v2);
//      _21=v2.x(); _22=v2.y(); _23=v2.z();
      return this;
   }
   public final MuM3D row3SetBy(ImuV3D v3) {
      detOK=Double.NaN; invOK=null;
      _r3.setBy(v3);
//      _31=v3.x(); _32=v3.y(); _33=v3.z();
      return this;
   }

   public final MuM3D row1MulBy(double k) {
      _r1.x*=k; _r1.y*=k; _r1.z*=k;
      detOK*=k; invOK=null;
      return this;
   }
   public final MuM3D row2MulBy(double k) {
      _r2.x*=k; _r2.y*=k; _r2.z*=k;
      detOK*=k; invOK=null;
      return this;
   }
   public final MuM3D row3MulBy(double k) {
      _r3.x*=k; _r3.y*=k; _r3.z*=k;
      detOK*=k; invOK=null;
      return this;
   }


   public final MuM3D col1SetBy(ImuV3D v1) {
      _r1.x=v1.x(); _r2.x=v1.y(); _r3.x=v1.z();
      detOK=Double.NaN; invOK=null;
      return this;
   }
   public final MuM3D col2SetBy(ImuV3D v2) {
      _r1.y=v2.x(); _r2.y=v2.y(); _r3.y=v2.z();
      detOK=Double.NaN; invOK=null;
      return this;
   }
   public final MuM3D col3SetBy(ImuV3D v3) {
      _r1.z=v3.x(); _r2.z=v3.y(); _r3.z=v3.z();
      detOK=Double.NaN; invOK=null;
      return this;
   }

   public void scanFrom(TxIStream iii) throws IOException {
         iii.skipWS().expect('['); // .skipWS();  //: 由getDouble負責
         _r1.x=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r1.y=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r1.z=iii.get_double();
         iii.skipWS().expect(';');
         _r2.x=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r2.y=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r2.z=iii.get_double();
         iii.skipWS().expect(';');
         _r3.x=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r3.y=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r3.z=iii.get_double();
         iii.skipWS().expect(']');
         detOK=Double.NaN; invOK=null;
   }

//I   public final DuplicableI duplicate() {  ... }

//--------
   // 原Long.parseLong不允許有前後之space
   public static MuM3D parseM3D(String s) {
//      try {
      TxIStrStream inputS=new TxIStrStream(s);
      MuM3D x=new MuM3D();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }


//--------

//I   public final ImuM3D add(ImuM3D m2) {  ... }
//I   public final ImuM3D sub(ImuM3D m2) {  ... }

   public final MuM3D addBy(ImuM3D m2) {
      _r1.x+= m2._r1.x;  _r1.y+= m2._r1.y;  _r1.z+= m2._r1.z;
      _r2.x+= m2._r2.x;  _r2.y+= m2._r2.y;  _r2.z+= m2._r2.z;
      _r3.x+= m2._r3.x;  _r3.y+= m2._r3.y;  _r3.z+= m2._r3.z;
      detOK=Double.NaN; invOK=null;
      return this;
   }

   public final MuM3D subBy(ImuM3D m2) {
      _r1.x-= m2._r1.x;  _r1.y-= m2._r1.y;  _r1.z-= m2._r1.z;
      _r2.x-= m2._r2.x;  _r2.y-= m2._r2.y;  _r2.z-= m2._r2.z;
      _r3.x-= m2._r3.x;  _r3.y-= m2._r3.y;  _r3.z-= m2._r3.z;
      detOK=Double.NaN; invOK=null;
      return this;
   }

   public final MuM3D addBy(
      double d11, double d12, double d13,
      double d21, double d22, double d23,
      double d31, double d32, double d33
   ) {
      _r1.x+=d11; _r1.y+=d12; _r1.z+=d13;
      _r2.x+=d21; _r2.y+=d22; _r2.z+=d23;
      _r3.x+=d31; _r3.y+=d32; _r3.z+=d33;
      detOK=Double.NaN; invOK=null;
      return this;
   }

   public final MuM3D subBy(
      double d11, double d12, double d13,
      double d21, double d22, double d23,
      double d31, double d32, double d33
   ) {
      _r1.x-=d11; _r1.y-=d12; _r1.z-=d13;
      _r2.x-=d21; _r2.y-=d22; _r2.z-=d23;
      _r3.x-=d31; _r3.y-=d32; _r3.z-=d33;
      detOK=Double.NaN; invOK=null;
      return this;
   }

//I   public final ImuM3D neg() {  ...  }
   public final MuM3D negate() {
      _r1.x= -_r1.x; _r1.y= -_r1.y;  _r1.z= -_r1.z;
      _r2.x= -_r2.x; _r2.y= -_r2.y;  _r2.z= -_r2.z;
      _r3.x= -_r3.x; _r3.y= -_r3.y;  _r3.z= -_r3.z;
      detOK=Double.NaN; invOK=null;
      return this;
   }

//I   public final ImuM3D mul(double k) {  ...  }
   public final MuM3D mulBy(double k) {
      _r1.x*=k;  _r1.y*=k;  _r1.z*=k;
      _r2.x*=k;  _r2.y*=k;  _r2.z*=k;
      _r3.x*=k;  _r3.y*=k;  _r3.z*=k;
      detOK=Double.NaN; invOK=null;
      return this;
   }

@Deprecated
public final MuM3D smulBy(double k) {
      _r1.x*=k;  _r1.y*=k;  _r1.z*=k;
      _r2.x*=k;  _r2.y*=k;  _r2.z*=k;
      _r3.x*=k;  _r3.y*=k;  _r3.z*=k;
      detOK=Double.NaN; invOK=null;
      return this;
}

   public final MuM3D divBy(double k) {
      if(k==0.0) throw new IllegalArgumentException("divided by zero");
      _r1.x/=k;  _r1.y/=k;  _r1.z/=k;
      _r2.x/=k;  _r2.y/=k;  _r2.z/=k;
      _r3.x/=k;  _r3.y/=k;  _r3.z/=k;
      detOK=Double.NaN; invOK=null;
      return this;
   }

//I   public final ImuM3D mul(ImuM3D m2) {  ...  }
   public final MuM3D mulBy(ImuM3D m2) {  //: this = this*m2
      return this.setBy(
         this._r1.x*m2._r1.x + this._r1.y*m2._r2.x + this._r1.z*m2._r3.x,
          this._r1.x*m2._r1.y + this._r1.y*m2._r2.y + this._r1.z*m2._r3.y,
           this._r1.x*m2._r1.z + this._r1.y*m2._r2.z + this._r1.z*m2._r3.z,
         this._r2.x*m2._r1.x + this._r2.y*m2._r2.x + this._r2.z*m2._r3.x,
          this._r2.x*m2._r1.y + this._r2.y*m2._r2.y + this._r2.z*m2._r3.y,
           this._r2.x*m2._r1.z + this._r2.y*m2._r2.z + this._r2.z*m2._r3.z,
         this._r3.x*m2._r1.x + this._r3.y*m2._r2.x + this._r3.z*m2._r3.x,
          this._r3.x*m2._r1.y + this._r3.y*m2._r2.y + this._r3.z*m2._r3.y,
           this._r3.x*m2._r1.z + this._r3.y*m2._r2.z + this._r3.z*m2._r3.z
      );
   }
   public final MuM3D swapMulBy(ImuM3D m1) { //: this = m1*this
      return this.setByMul(m1,this);
   }


//[ 無意義
//   public final MuM3D mulBy(ImuV3D v) {
     //[ simulate this*=v, v視為column matrix
//   public final MuM3D lMulBy(ImuV3D v) {
     //[ simulate this:=(v*this), v視為row matrix
//]

//I   public final ImuM3D transposition() {  ...  }
   public final MuM3D transpose() {
      invOK=null;  // detOK=Double.NaN; 
      double t;
      t=_r1.y; _r1.y=_r2.x; _r2.x=t;
      t=_r1.z; _r1.z=_r3.x; _r3.x=t;
      t=_r2.z; _r2.z=_r3.y; _r3.y=t;
      return this;
   }

   public final MuM3D setByTr(ImuM3D src) {
      detOK=Double.NaN; invOK=null;
      _r1.x=src._r1.x; _r1.y=src._r2.x; _r1.z=src._r3.x;
      _r2.x=src._r1.y; _r2.y=src._r2.y; _r2.z=src._r3.y;
      _r3.x=src._r1.z; _r3.y=src._r2.z; _r3.z=src._r3.z;
      return this;
   }

//I   public final double det() {  ...  }

//I   public final ImuM3D adjoint() {

//I   public final ImuM3D inverse() {  ...  }
   public final MuM3D invert() {
   // this.setBy( inverse() );   return this;
      return this.setByInverse(this);
   }

   //[ this==src 仍可
   public final MuM3D setByAdjoint(ImuM3D src) {
      return this.setBy(
         +(src._r2.y*src._r3.z-src._r3.y*src._r2.z),
             -(src._r1.y*src._r3.z-src._r3.y*src._r1.z),
                +(src._r1.y*src._r2.z-src._r2.y*src._r1.z),
         -(src._r2.x*src._r3.z-src._r3.x*src._r2.z),
             +(src._r1.x*src._r3.z-src._r3.x*src._r1.z),
                -(src._r1.x*src._r2.z-src._r2.x*src._r1.z),
         +(src._r2.x*src._r3.y-src._r3.x*src._r2.y),
             -(src._r1.x*src._r3.y-src._r3.x*src._r1.y),
                +(src._r1.x*src._r2.y-src._r2.x*src._r1.y)
      );
      // detOK=Double.NaN; invOK=null;
   }

   //[ this==src 仍可
   public final MuM3D setByInverse(ImuM3D src) {
      final double d=src.det(); //: 可能 this==src, 必須先算好
      if(d==0.0) throw new IllegalArgumentException(
          "source matrix not invertible"
      );
      return this.setByAdjoint( src ).mulBy(1.0/d); 
                     //: 不用divBy 以免重覆check d!=0
      // detOK=Double.NaN; invOK=null;
   }
//I   public final double normSquare() {  ...  }
//I   public final double norm() {  ...  }
//I   public final double distance(MuM3D x) {  ...  }

   //------------------------------


//I   public static final ImuM3D findMap(
//I      ImuV3D v1, ImuV3D v2, ImuV3D v3,
//I      ImuV3D w1, ImuV3D w2, ImuV3D w3
//I   ) {  ...  }

   public final MuM3D setByAdd(ImuM3D m2, ImuM3D m3) {
     //[ simulate this:=(m2+m3)
      return this.setBy(
         m2._r1.x+m3._r1.x, m2._r1.y+m3._r1.y, m2._r1.z+m3._r1.z,
         m2._r2.x+m3._r2.x, m2._r2.y+m3._r2.y, m2._r2.z+m3._r2.z,
         m2._r3.x+m3._r3.x, m2._r3.y+m3._r3.y, m2._r3.z+m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D addByAdd(ImuM3D m2, ImuM3D m3) {
     //[ simulate this+=(m2+m3)
      return this.addBy(
         m2._r1.x+m3._r1.x, m2._r1.y+m3._r1.y, m2._r1.z+m3._r1.z,
         m2._r2.x+m3._r2.x, m2._r2.y+m3._r2.y, m2._r2.z+m3._r2.z,
         m2._r3.x+m3._r3.x, m2._r3.y+m3._r3.y, m2._r3.z+m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D subByAdd(ImuM3D m2, ImuM3D m3) {
     //[ simulate this-=(m2+m3)
      return this.subBy(
         m2._r1.x+m3._r1.x, m2._r1.y+m3._r1.y, m2._r1.z+m3._r1.z,
         m2._r2.x+m3._r2.x, m2._r2.y+m3._r2.y, m2._r2.z+m3._r2.z,
         m2._r3.x+m3._r3.x, m2._r3.y+m3._r3.y, m2._r3.z+m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D setBySub(ImuM3D m2, ImuM3D m3) {
     //[ simulate this:=(m2-m3)
      return this.setBy(
         m2._r1.x-m3._r1.x, m2._r1.y-m3._r1.y, m2._r1.z-m3._r1.z,
         m2._r2.x-m3._r2.x, m2._r2.y-m3._r2.y, m2._r2.z-m3._r2.z,
         m2._r3.x-m3._r3.x, m2._r3.y-m3._r3.y, m2._r3.z-m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D addBySub(ImuM3D m2, ImuM3D m3) {
     //[ simulate this+=(m2-m3)
      return this.addBy(
         m2._r1.x-m3._r1.x, m2._r1.y-m3._r1.y, m2._r1.z-m3._r1.z,
         m2._r2.x-m3._r2.x, m2._r2.y-m3._r2.y, m2._r2.z-m3._r2.z,
         m2._r3.x-m3._r3.x, m2._r3.y-m3._r3.y, m2._r3.z-m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D subBySub(ImuM3D m2, ImuM3D m3) {
     //[ simulate this-=(m2-m3)
      return this.subBy(
         m2._r1.x-m3._r1.x, m2._r1.y-m3._r1.y, m2._r1.z-m3._r1.z,
         m2._r2.x-m3._r2.x, m2._r2.y-m3._r2.y, m2._r2.z-m3._r2.z,
         m2._r3.x-m3._r3.x, m2._r3.y-m3._r3.y, m2._r3.z-m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D setByMul(ImuM3D m2, ImuM3D m3) {
     //[ simulate this:=(m2*m3)
      return this.setBy(
         m2._r1.x*m3._r1.x + m2._r1.y*m3._r2.x + m2._r1.z*m3._r3.x,
          m2._r1.x*m3._r1.y + m2._r1.y*m3._r2.y + m2._r1.z*m3._r3.y,
           m2._r1.x*m3._r1.z + m2._r1.y*m3._r2.z + m2._r1.z*m3._r3.z,
         m2._r2.x*m3._r1.x + m2._r2.y*m3._r2.x + m2._r2.z*m3._r3.x,
          m2._r2.x*m3._r1.y + m2._r2.y*m3._r2.y + m2._r2.z*m3._r3.y,
           m2._r2.x*m3._r1.z + m2._r2.y*m3._r2.z + m2._r2.z*m3._r3.z,
         m2._r3.x*m3._r1.x + m2._r3.y*m3._r2.x + m2._r3.z*m3._r3.x,
          m2._r3.x*m3._r1.y + m2._r3.y*m3._r2.y + m2._r3.z*m3._r3.y,
           m2._r3.x*m3._r1.z + m2._r3.y*m3._r2.z + m2._r3.z*m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D addByMul(ImuM3D m2, ImuM3D m3) {
     //[ simulate this+=(m2*m3)
      return this.addBy(
         m2._r1.x*m3._r1.x + m2._r1.y*m3._r2.x + m2._r1.z*m3._r3.x,
          m2._r1.x*m3._r1.y + m2._r1.y*m3._r2.y + m2._r1.z*m3._r3.y,
           m2._r1.x*m3._r1.z + m2._r1.y*m3._r2.z + m2._r1.z*m3._r3.z,
         m2._r2.x*m3._r1.x + m2._r2.y*m3._r2.x + m2._r2.z*m3._r3.x,
          m2._r2.x*m3._r1.y + m2._r2.y*m3._r2.y + m2._r2.z*m3._r3.y,
           m2._r2.x*m3._r1.z + m2._r2.y*m3._r2.z + m2._r2.z*m3._r3.z,
         m2._r3.x*m3._r1.x + m2._r3.y*m3._r2.x + m2._r3.z*m3._r3.x,
          m2._r3.x*m3._r1.y + m2._r3.y*m3._r2.y + m2._r3.z*m3._r3.y,
           m2._r3.x*m3._r1.z + m2._r3.y*m3._r2.z + m2._r3.z*m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D subByMul(ImuM3D m2, ImuM3D m3) {
     //[ simulate this-=(m2*m3)
      return this.subBy(
         m2._r1.x*m3._r1.x + m2._r1.y*m3._r2.x + m2._r1.z*m3._r3.x,
          m2._r1.x*m3._r1.y + m2._r1.y*m3._r2.y + m2._r1.z*m3._r3.y,
           m2._r1.x*m3._r1.z + m2._r1.y*m3._r2.z + m2._r1.z*m3._r3.z,
         m2._r2.x*m3._r1.x + m2._r2.y*m3._r2.x + m2._r2.z*m3._r3.x,
          m2._r2.x*m3._r1.y + m2._r2.y*m3._r2.y + m2._r2.z*m3._r3.y,
           m2._r2.x*m3._r1.z + m2._r2.y*m3._r2.z + m2._r2.z*m3._r3.z,
         m2._r3.x*m3._r1.x + m2._r3.y*m3._r2.x + m2._r3.z*m3._r3.x,
          m2._r3.x*m3._r1.y + m2._r3.y*m3._r2.y + m2._r3.z*m3._r3.y,
           m2._r3.x*m3._r1.z + m2._r3.y*m3._r2.z + m2._r3.z*m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D setByLinearCombination(
      double a1, ImuM3D m1, double a2, ImuM3D m2, double a3, ImuM3D m3
   ) {
     //[ simulate this:=(a1*m2+a2*m2+a3*m3)
      return this.setBy(
         a1*m1._r1.x+a2*m2._r1.x+a3*m3._r1.x,
          a1*m1._r1.y+a2*m2._r1.y+a3*m3._r1.y,
           a1*m1._r1.z+a2*m2._r1.z+a3*m3._r1.z,
         a1*m1._r2.x+a2*m2._r2.x+a3*m3._r2.x,
          a1*m1._r2.y+a2*m2._r2.y+a3*m3._r2.y,
           a1*m1._r2.z+a2*m2._r2.z+a3*m3._r2.z,
         a1*m1._r3.x+a2*m2._r3.x+a3*m3._r3.x,
          a1*m1._r3.y+a2*m2._r3.y+a3*m3._r3.y,
           a1*m1._r3.z+a2*m2._r3.z+a3*m3._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM3D setByLinearCombination(
      double a1, ImuM3D m1, double a2, ImuM3D m2
   ) {
     //[ simulate this:=(a1*m2+a2*m2)
      return this.setBy(
         a1*m1._r1.x+a2*m2._r1.x,
          a1*m1._r1.y+a2*m2._r1.y,
           a1*m1._r1.z+a2*m2._r1.z,
         a1*m1._r2.x+a2*m2._r2.x,
          a1*m1._r2.y+a2*m2._r2.y,
           a1*m1._r2.z+a2*m2._r2.z,
         a1*m1._r3.x+a2*m2._r3.x,
          a1*m1._r3.y+a2*m2._r3.y,
           a1*m1._r3.z+a2*m2._r3.z
      );
      // detOK=Double.NaN; invOK=null;
   }

   //[ this 設為各列以drx為軸轉角A的結果
   public final MuM3D rotBy(ImuV3D drx, double A) {
      final MuV3D b1=new MuV3D(this.row1());
      final MuV3D b2=new MuV3D(this.row2());
      final MuV3D b3=new MuV3D(this.row3());
      b1.rotBy(drx, A);
      b2.rotBy(drx, A);
      b3.rotBy(drx, A);
      this.setBy(b1,b2,b3); // detOK=Double.NaN; invOK=null;
      return this;
   }
   public final MuM3D xRotBy(double angle) {
      final MuV3D b1=new MuV3D(this.row1());
      final MuV3D b2=new MuV3D(this.row2());
      final MuV3D b3=new MuV3D(this.row3());
      b1.xRotBy(angle);
      b2.xRotBy(angle);
      b3.xRotBy(angle);
      this.setBy(b1,b2,b3);
      // detOK=Double.NaN; invOK=null;
      return this;
   }   
   public final MuM3D yRotBy(double angle) {
      final MuV3D b1=new MuV3D(this.row1());
      final MuV3D b2=new MuV3D(this.row2());
      final MuV3D b3=new MuV3D(this.row3());
      b1.yRotBy(angle);
      b2.yRotBy(angle);
      b3.yRotBy(angle);
      this.setBy(b1,b2,b3);
      // detOK=Double.NaN; invOK=null;
      return this;
   }   
   public final MuM3D zRotBy(double angle) {
      final MuV3D b1=new MuV3D(this.row1());
      final MuV3D b2=new MuV3D(this.row2());
      final MuV3D b3=new MuV3D(this.row3());
      b1.zRotBy(angle);
      b2.zRotBy(angle);
      b3.zRotBy(angle);
      this.setBy(b1,b2,b3);
      // detOK=Double.NaN; invOK=null;
      return this;
   }   
   //[ this 設為各列以drx為軸轉角A的結果, 參數是sinA
   public final MuM3D sinRotBy(ImuV3D drx, double sinA) {
      final MuV3D b1=new MuV3D(this.row1());
      final MuV3D b2=new MuV3D(this.row2());
      final MuV3D b3=new MuV3D(this.row3());
      b1.sinRotBy(drx, sinA);
      b2.sinRotBy(drx, sinA);
      b3.sinRotBy(drx, sinA);
      this.setBy(b1,b2,b3);
      // detOK=Double.NaN; invOK=null;
      return this;
   }

   

   //[==========   static part ===============================
   public static MuM3D scalar(double c) {
      return new MuM3D(c, c, c);
   }

   //[ 不稱為 diagonal 是怕誤為 取出對角線向量
   public static final MuM3D createDiagonal(
      double c1, double c2, double c3
   ) {
      return new MuM3D(c1, c2, c3);
   }
   //]==========   static part ===============================

   //===========================================
//   public static void main(String[] aaa) { //: self test
//      MuM3D m1=new MuM3D(1,2,3,4,5,6,7,8,9);
//      System.out.println(m1 + ", det="+ m1.det());
//      MuM3D m2=new MuM3D(1,2,3,4,5,6,7,8,10);
//      System.out.println(m2 + ", det="+ m2.det());
//      MuM3D m3=new MuM3D(m2.inverse());
//      MuM3D m4=new MuM3D(m2.mul(m3));
//      System.out.println(m4);
//   }
}