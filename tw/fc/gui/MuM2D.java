package tw.fc.gui;
import java.io.IOException;
//import tw.fc.gui.MuV3D;
import tw.fc.gui.ImuV2D;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;


public class MuM2D extends ImuM2D
       implements SetableI<ImuM2D>, ScannableI // SetableI  
{
   private static final long serialVersionUID = 2014120810L;
//I   final MuV2D _r1, _r2;

   public final void setE11(double v) {  _r1.x=v;  }       
   public final void setE12(double v) {  _r1.y=v;  }       
   public final void setE21(double v) {  _r2.x=v;  }       
   public final void setE22(double v) {  _r2.y=v;  }       
//   public final void setE11(double v) {  _11=v;  }       
//   public final void setE12(double v) {  _12=v;  }       
//   public final void setE21(double v) {  _21=v;  }       
//   public final void setE22(double v) {  _22=v;  }       

//-------------------------------------------------   
//I   public String toString() {  ... }
//I   public final boolean equals(ImuM2D m2) { ... }
//I   public final boolean equals(ImuM2D m2, double epsilon) { ... }

//-------------------------------------------------   

   //[ default ctor, create a zero matrix
   public MuM2D() {  super();   }

   //[ constructed by entries in row-major order
   public MuM2D(
      double d11, double d12,
      double d21, double d22
   ) {  
      super(d11,d12, d21,d22); 
   }

   //[ constructed by row vectors
   public MuM2D(ImuV2D v1, ImuV2D v2) {
      super(v1,v2); 
   }

   //[ construct a diagonal matrix 
   public MuM2D(double d1, double d2) {  
      super(d1,d2);
   }

   //[ copy-ctor
   public MuM2D(ImuM2D src) {  
      super(src);
   }

//--------------------------------------------------

   @Override public final MuV2D row1() {  
      return _r1;  
   }
   @Override public final MuV2D row2() {  
      return _r2;  
   }

   public final MuM2D setBy(
      double d11, double d12,
      double d21, double d22
   ) {  
      _r1.x=d11; _r1.y=d12; 
      _r2.x=d21; _r2.y=d22; 
   // _11=d11; _12=d12; 
   // _21=d21; _22=d22; 
      return this; 
   }
//   public final SetableI setBy(DuplicableI s) {
//      return setBy((ImuM2D)s);
//   }
   public final MuM2D setBy(ImuM2D src) {  
      _r1.setBy(src._r1); _r2.setBy(src._r2); 
   // _11=src._11; _12=src._12; 
   // _21=src._21; _22=src._22; 
      return this; 
   }
   public final MuM2D setByRow(ImuV2D v1, ImuV2D v2) {
      _r1.setBy(v1);  _r2.setBy(v2);
   // _11=v1.x; _12=v1.y; 
   // _21=v2.x; _22=v2.y; 
      return this; 
   }
   public final MuM2D setBy(ImuV2D v1, ImuV2D v2) {
      return setByRow(v1, v2);
   }
   public final MuM2D setByCol(ImuV2D v1, ImuV2D v2) {
      _r1.setBy(v1.x, v2.x);
      _r2.setBy(v1.y, v2.y);
   // _11=v1.x; _12=v2.x; 
   // _21=v1.y; _22=v2.y; 
      return this; 
   }

   public final MuM2D row1SetBy(ImuV2D v1) {
      _r1.setBy(v1);
   // _11=v1.x; _12=v1.y; 
      return this; 
   }
   public final MuM2D row2SetBy(ImuV2D v2) {
      _r2.setBy(v2);
   // _21=v2.x; _22=v2.y; 
      return this; 
   }

   public final MuM2D col1SetBy(ImuV2D v1) {
      _r1.x=v1.x; _r2.x=v1.y; 
   // _11=v1.x; _21=v1.y; 
      return this; 
   }
   public final MuM2D col2SetBy(ImuV2D v2) {
      _r1.y=v2.x; _r2.y=v2.y; 
   // _12=v2.x; _22=v2.y;  
      return this; 
   }
 
   public void scanFrom(TxIStream iii) throws IOException {
         iii.skipWS().expect('['); // .skipWS();  //: 由getDouble負責
         _r1.x=iii.get_double();
   //    _11=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r1.y=iii.get_double();
   //    _12=iii.get_double();
         iii.skipWS().expect(';');
         _r2.x=iii.get_double();
   //    _21=iii.get_double();
         iii.skipWS().expect(','); // .skipWS();
         _r2.y=iii.get_double();
   //    _22=iii.get_double();
         iii.skipWS().expect(']');
   }

//I   public final DuplicableI duplicate() {  ... }
   
//--------   

   // 原Long.parseLong不允許有前後之space
   public static MuM2D parseM3D(String s) {
//      try {
      TxIStrStream inputS=new TxIStrStream(s);
      MuM2D x=new MuM2D();  
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   } 
//--------   

//I   public final ImuM2D add(ImuM2D m2) {  ... }
//I   public final ImuM2D sub(ImuM2D m2) {  ... }
   
   public final MuM2D addBy(ImuM2D m2) {  
      _r1.addBy(m2._r1);
      _r2.addBy(m2._r2);
   // _11+= m2._11;  _12+= m2._12; 
   // _21+= m2._21;  _22+= m2._22; 
      return this; 
   }
   public final MuM2D subBy(ImuM2D m2) {  
      _r1.subBy(m2._r1);
      _r2.subBy(m2._r2);
   // _11-= m2._11;  _12-= m2._12; 
   // _21-= m2._21;  _22-= m2._22; 
      return this; 
   }

   public final MuM2D addBy(
      double d11, double d12, 
      double d21, double d22
   ) {  
      _r1.x+=d11; _r1.y+=d12; 
      _r2.x+=d21; _r2.y+=d22; 
   // _11+=d11; _12+=d12; 
   // _21+=d21; _22+=d22; 
      return this; 
   }
   public final MuM2D subBy(
      double d11, double d12, 
      double d21, double d22
   ) {  
      _r1.x-=d11; _r1.y-=d12; 
      _r2.x-=d21; _r2.y-=d22; 
   // _11-=d11; _12-=d12; 
   // _21-=d21; _22-=d22; 
      return this; 
   }

//I   public final ImuM2D neg() {  ...  }
   public final MuM2D negate() {  
      _r1.x= -_r1.x; _r1.y= -_r1.y; 
      _r2.x= -_r2.x; _r2.y= -_r2.y; 
   // _11= -_11; _12= -_12; 
   // _21= -_21; _22= -_22; 
      return this;  
   }

//I   public final ImuM2D mul(double k) {  ...  }
   public final MuM2D mulBy(double k) {  
      _r1.x*=k;  _r1.y*=k;  
      _r2.x*=k;  _r2.y*=k;  
   // _11*=k;  _12*=k;  
   // _21*=k;  _22*=k;  
      return this; 
   }
   public final MuM2D divBy(double k) {  
      if(k==0.0) throw new IllegalArgumentException("divided by zero");
      _r1.x/=k;  _r1.y/=k;  
      _r2.x/=k;  _r2.y/=k;  
   // _11/=k;  _12/=k;  
   // _21/=k;  _22/=k;  
      return this; 
   }

//I   public final ImuM2D mul(ImuM2D m2) {  ...  }
   public final MuM2D mulBy(ImuM2D m2) {  
      return this.setBy(
         this._r1.x*m2._r1.x + this._r1.y*m2._r2.x,
          this._r1.x*m2._r1.y + this._r1.y*m2._r2.y,
         this._r2.x*m2._r1.x + this._r2.y*m2._r2.x,
          this._r2.x*m2._r1.y + this._r2.y*m2._r2.y
      //   this._11*m2._11 + this._12*m2._21,
      //    this._11*m2._12 + this._12*m2._22,
      //   this._21*m2._11 + this._22*m2._21,
      //    this._21*m2._12 + this._22*m2._22
      );
   }
   public final MuM2D swapMulBy(ImuM2D m1) {  
      return this.setByMul(m1,this);
   }

//[ 無意義
//   public final MuM2D mulBy(ImuV2D v) {  
     //[ simulate this*=v, v視為column matrix 
//   public final MuM2D lMulBy(ImuV2D v) {  
     //[ simulate this:=(v*this), v視為row matrix 
//]

//I   public final ImuM2D transposition() {  ...  }
   public final MuM2D transpose() {  
      double t=_r1.y; _r1.y=_r2.x; _r2.x=t;
   // double t=_12; _12=_21; _21=t;
      return this; 
   }

//I   public final double det() {  ...  }

//I   public final ImuM2D adjoint() {

//I   public final ImuM2D inverse() {  ...  }
   public final MuM2D invert() {  
      this.setBy( inverse() );
      return this; 
   }
      
//I   public final double normSquare() {  ...  }
//I   public final double norm() {  ...  }
//I   public final double distance(MuM2D x) {  ...  }

   //------------------------------
   
//I   public static final ImuM2D findMap

   public final MuM2D setByAdd(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this:=(m2+m3) 
      return this.setBy(
         m2._r1.x+m3._r1.x, m2._r1.y+m3._r1.y,
         m2._r2.x+m3._r2.x, m2._r2.y+m3._r2.y
      // m2._11+m3._11, m2._12+m3._12,
      // m2._21+m3._21, m2._22+m3._22
      );
   }
   public final MuM2D addByAdd(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this+=(m2+m3) 
      return this.addBy(
         m2._r1.x+m3._r1.x, m2._r1.y+m3._r1.y,
         m2._r2.x+m3._r2.x, m2._r2.y+m3._r2.y
      //   m2._11+m3._11, m2._12+m3._12,
      //  m2._21+m3._21, m2._22+m3._22
      );
   }
   public final MuM2D subByAdd(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this-=(m2+m3) 
      return this.subBy(
         m2._r1.x+m3._r1.x, m2._r1.y+m3._r1.y,
         m2._r2.x+m3._r2.x, m2._r2.y+m3._r2.y
      // m2._11+m3._11, m2._12+m3._12,
      // m2._21+m3._21, m2._22+m3._22
      );
   }

   public final MuM2D setBySub(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this:=(m2-m3) 
      return this.setBy(
         m2._r1.x-m3._r1.x, m2._r1.y-m3._r1.y,
         m2._r2.x-m3._r2.x, m2._r2.y-m3._r2.y
      // m2._11-m3._11, m2._12-m3._12,
      // m2._21-m3._21, m2._22-m3._22
      );
   }
   public final MuM2D addBySub(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this+=(m2-m3) 
      return this.addBy(
         m2._r1.x-m3._r1.x, m2._r1.y-m3._r1.y,
         m2._r2.x-m3._r2.x, m2._r2.y-m3._r2.y
      //   m2._11-m3._11, m2._12-m3._12,
      //   m2._21-m3._21, m2._22-m3._22
      );
   }
   public final MuM2D subBySub(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this-=(m2-m3) 
      return this.subBy(
         m2._r1.x-m3._r1.x, m2._r1.y-m3._r1.y,
         m2._r2.x-m3._r2.x, m2._r2.y-m3._r2.y
      //   m2._11-m3._11, m2._12-m3._12,
      //   m2._21-m3._21, m2._22-m3._22
      );
   }

   public final MuM2D setByMul(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this:=(m2*m3) 
      return this.setBy(
         m2._r1.x*m3._r1.x + m2._r1.y*m3._r2.x ,
          m2._r1.x*m3._r1.y + m2._r1.y*m3._r2.y ,
         m2._r2.x*m3._r1.x + m2._r2.y*m3._r2.x ,
          m2._r2.x*m3._r1.y + m2._r2.y*m3._r2.y 
      // m2._11*m3._11 + m2._12*m3._21 ,
      //  m2._11*m3._12 + m2._12*m3._22 ,
      // m2._21*m3._11 + m2._22*m3._21 ,
      //  m2._21*m3._12 + m2._22*m3._22 
      );
   }
   public final MuM2D addByMul(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this+=(m2*m3) 
      return this.addBy(
         m2._r1.x*m3._r1.x + m2._r1.y*m3._r2.x ,
          m2._r1.x*m3._r1.y + m2._r1.y*m3._r2.y ,
         m2._r2.x*m3._r1.x + m2._r2.y*m3._r2.x ,
          m2._r2.x*m3._r1.y + m2._r2.y*m3._r2.y 
      );
   }
   public final MuM2D subByMul(ImuM2D m2, ImuM2D m3) {  
     //[ simulate this-=(m2*m3) 
      return this.subBy(
         m2._r1.x*m3._r1.x + m2._r1.y*m3._r2.x ,
          m2._r1.x*m3._r1.y + m2._r1.y*m3._r2.y ,
         m2._r2.x*m3._r1.x + m2._r2.y*m3._r2.x ,
          m2._r2.x*m3._r1.y + m2._r2.y*m3._r2.y 
      );
   }

   public final MuM2D setByLinearCombination(
      double a1, ImuM2D m1, double a2, ImuM2D m2, double a3, ImuM2D m3
   ) {  
     //[ simulate this:=(a1*m2+a2*m2+a3*m3) 
      return this.setBy(
         a1*m1._r1.x+a2*m2._r1.x+a3*m3._r1.x,
          a1*m1._r1.y+a2*m2._r1.y+a3*m3._r1.y,
         a1*m1._r2.x+a2*m2._r2.x+a3*m3._r2.x,
          a1*m1._r2.y+a2*m2._r2.y+a3*m3._r2.y   
      // a1*m1._11+a2*m2._11+a3*m3._11,
      //  a1*m1._12+a2*m2._12+a3*m3._12,
      // a1*m1._21+a2*m2._21+a3*m3._21,
      //  a1*m1._22+a2*m2._22+a3*m3._22     //ht: bug fixed in A10104
      );
   }

   public final MuM2D setByLinearCombination(
      double a1, ImuM2D m1, double a2, ImuM2D m2
   ) {  
     //[ simulate this:=(a1*m2+a2*m2) 
      return this.setBy(
         a1*m1._r1.x+a2*m2._r1.x,
          a1*m1._r1.y+a2*m2._r1.y,
         a1*m1._r2.x+a2*m2._r2.x,
          a1*m1._r2.y+a2*m2._r2.y
      );
   }

   //===========================================

   //[ A20501 wu

   public MuM2D row1MulBy(double k) {
      this._r1.x*=k; this._r2.y*=k;
      return this;
   }

   public MuM2D row2MulBy(double k) {
      this._r2.x*=k; this._r2.y*=k;
      return this;
   }

   public final MuM2D setByAdjoint(ImuM2D src) {
      return this.setBy(src._r2.y, -src._r2.x,
            -src._r1.y,  src._r1.x);
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM2D setByInverse(ImuM2D src) {
      final double d=src.det(); 
      if(d==0.0) throw new IllegalArgumentException(
            "source matrix not invertible"
            );
      return this.setByAdjoint( src ).mulBy(1.0/d); 
      // detOK=Double.NaN; invOK=null;
   }

   public final MuM2D rotBy(ImuV2D drx, double A) {
      final MuV2D b1=new MuV2D(this.row1());
      final MuV2D b2=new MuV2D(this.row2());
      b1.rotBy(A, drx);
      b2.rotBy(A, drx);
      this.setBy(b1,b2); // detOK=Double.NaN; invOK=null;
      return this;
   }
   
   public final MuM2D sinRotBy(ImuV2D drx, double sinA) {
      final MuV2D b1=new MuV2D(this.row1());
      final MuV2D b2=new MuV2D(this.row2());
      b1.sinRotBy(sinA, drx);
      b2.sinRotBy(sinA, drx);
      this.setBy(b1,b2);
      // detOK=Double.NaN; invOK=null;
      return this;
   }
   
   //]
   
   //[ A20724 xie
   public final MuM2D xRotBy(double angle) {
      final MuV2D b1=new MuV2D(this.row1());
      final MuV2D b2=new MuV2D(this.row2());
      b1.xRotBy(angle);
      b2.xRotBy(angle);
      this.setBy(b1,b2);
      // detOK=Double.NaN; invOK=null;
      return this;
   }

   public final MuM2D yRotBy(double angle) {
      final MuV2D b1=new MuV2D(this.row1());
      final MuV2D b2=new MuV2D(this.row2());
      b1.yRotBy(angle);
      b2.yRotBy(angle);
      this.setBy(b1,b2);
      // detOK=Double.NaN; invOK=null;
      return this;
   }
   //]
}