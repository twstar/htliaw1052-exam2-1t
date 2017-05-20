package tw.fc.gui;

import java.io.IOException;

import tw.fc.ScannableI;
import tw.fc.SetableI;
import tw.fc.TxIStrStream;
import tw.fc.TxIStream;

// A21112 xie add
public class MuP2D extends ImuP2D
implements SetableI<ImuP2D>, ScannableI {

   public MuP2D(){ super(1.0, 0.0); }
   public MuP2D(double r, double a){ super(r, a); }
   public MuP2D(double a){ super(1.0, a); }
   public MuP2D(ImuP2D src) { super(src); }
   public MuP2D(ImuV2D src) { super(src); }

   //---------------------------------------------------
   public final MuP2D radSetBy(double r) { radius=r;  return this; }
   public final MuP2D lonSetBy(double a) { angle=a; return this; }

   public final MuP2D setBy(double r, double a) {
      radius=r;  angle=a;  return this; 
   }
   public final MuP2D setBy(double a) { 
      return setBy(1.0, a); 
   }

   public final MuP2D setBy(ImuP2D v2) {
      radius=v2.radius; angle=v2.angle;  
      return this;
   }
   
   @Override public MuP2D setBy(ImuV2D src) { //: superªºsetBy ¤£ public
      super.setBy(src);  return this; 
   }
   
// @Override
// public SetableI<ImuP2D> setBy(ImuP2D v) {
//    this.setBy(v.radius, v.angle+Math.PI); // 20100715 modify by Thomas
//    return this;
// }

   @Override
   public void scanFrom(TxIStream iii) throws IOException {
      //[ key in as the form:  (r;f)
      iii.skipWS().expect('(').skipWS();
      radius=iii.get_double();
      iii.skipWS().expect(';').skipWS();
      angle=iii.get_double();
      iii.skipWS().expect(')');      
   }

   public static MuP2D parseP2D(String s) {
//    try {
    final TxIStrStream inputS=new TxIStrStream(s);
    final MuP2D x=new MuP2D();
    inputS.g(x);
    return x;
//    }catch (IOException xpt) {
//       throw new TxInputException(xpt.toString());
//    }
 }
   
   public final MuP2D radAddBy(double dx) {  radius+=dx;  return this; }
   public final MuP2D lonAddBy(double dx) {  angle+=dx;  return this; }
  
   public final MuP2D radSubBy(double dx) {  radius-=dx;  return this; }
   public final MuP2D lonSubBy(double dx) {  angle-=dx;  return this; }

//   public final MuP3D negate() {
//      longitude+=Math.PI;  latitude=-latitude;  return this;
//   }
//
//   public final MuP3D setByNeg(ImuP3D v) {
//      //////this.setBy(v.radius, v.longitude+Math.PI, v.latitude);
//      this.setBy(v.radius, v.longitude+Math.PI, -v.latitude); // 20100715 modify by Thomas
//      return this;
//   }
   

   public final MuP2D sMulBy(double k) {  radius*=k;  return this; }

   public final MuP2D setBySMul(double k, ImuP2D v) {
      this.setBy(k*v.radius, v.angle);  return this;
   }

   public final MuP2D setBySMul(ImuP2D v, double k) {
      this.setBy(k*v.radius, v.angle);  return this;
   }

   public final MuP2D normalize() {  radius=1.0; return this; }

   public final MuP2D setByLonAdd(ImuP2D v, double angle) {
      this.setBy( v.radius, v.angle+angle);
      return this;
   }

   public final MuP2D setByLonSub(ImuP2D v, double angle) {
      this.setBy( v.radius, v.angle-angle);
      return this;
   }

   public final MuP2D setByLatSub(ImuP2D v, double angle) {
      this.setBy( v.radius, v.angle);
      return this;
   }   

}
