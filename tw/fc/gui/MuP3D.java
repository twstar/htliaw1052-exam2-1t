package tw.fc.gui;
import java.io.IOException;
//import tw.fc.DuplicableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
import tw.fc.SetableI;
//import tw.fc.PrintableI;
import tw.fc.ScannableI;
//import tw.fc.TxInputException;

//**********    MuV3D.java    *********************
//
//  floating-point 3D vector in Polar form (Spherical cordinate)
//
public class MuP3D extends ImuP3D
   implements SetableI<ImuP3D>, ScannableI
{

   public MuP3D(double r, double lon, double lat) { super(r, lon, lat); }

   public MuP3D(double lon, double lat) { super(1.0, lon, lat); }

   public MuP3D() { super(1.0, 0.0, 0.0); }

   public MuP3D(ImuP3D src) { super(src); }

   public MuP3D(ImuV3D src) { super(src); }
// public MuP3D(ImuV3D src, ImuAr3D localSys) { super(src, localSys); }
  
   //---------------------------------------------------
   public final MuP3D radSetBy(double r) { radius=r;  return this; }
   public final MuP3D lonSetBy(double lon) { longitude=lon; return this; }
   public final MuP3D latSetBy(double lat) { latitude=lat; return this;  }

   public final MuP3D setBy(double r, double lon, double lat) {
      radius=r;  longitude=lon;  latitude=lat;  return this; 
   }
   public final MuP3D setBy(double lon, double lat) { 
   	  return setBy(1.0,lon,lat); 
   }

   public final MuP3D setBy(ImuP3D v2) {
      radius=v2.radius; longitude=v2.longitude;  latitude=v2.latitude;
      return this;
   }

/////   public final SetableI setBy(DuplicableI src) {
/////      return setBy((ImuP3D)src);
/////   }

   @Override public MuP3D setBy(ImuV3D src) { //: super的setBy 不 public
      super.setBy(src);  return this; 
   }
// public void setBy(ImuV3D src, ImuAr3D localSys) { //: super的setBy 不 public
//    super.setBy(src, localSys);
// }

   public void scanFrom(TxIStream iii) throws IOException {
      //[ key in as the form:  (r;t,f)
      iii.skipWS().expect('(').skipWS();
      radius=iii.get_double();
      iii.skipWS().expect(';').skipWS();
      longitude=iii.get_double();
      iii.skipWS().expect(',').skipWS();
      latitude=iii.get_double();
      iii.skipWS().expect(')');
   }

   public static MuP3D parseP3D(String s) {
//      try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuP3D x=new MuP3D();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }

//>>>>>> range adjustment?

   public final MuP3D radAddBy(double dx) {  radius+=dx;  return this; }
   public final MuP3D lonAddBy(double dx) {  longitude+=dx;  return this; }
   public final MuP3D latAddBy(double dx) {  latitude+=dx;  return this; }

   public final MuP3D radSubBy(double dx) {  radius-=dx;  return this; }
   public final MuP3D lonSubBy(double dx) {  longitude-=dx;  return this; }
   public final MuP3D latSubBy(double dx) {  latitude-=dx;  return this; }

   public final MuP3D negate() {
      longitude+=Math.PI;  latitude=-latitude;  return this;
   }

   public final MuP3D setByNeg(ImuP3D v) {
      //////this.setBy(v.radius, v.longitude+Math.PI, v.latitude);
      this.setBy(v.radius, v.longitude+Math.PI, -v.latitude); // 20100715 modify by Thomas
      return this;
   }

   public final MuP3D sMulBy(double k) {  radius*=k;  return this; }

   public final MuP3D setBySMul(double k, ImuP3D v) {
      this.setBy(k*v.radius, v.longitude, v.latitude);  return this;
   }

   public final MuP3D setBySMul(ImuP3D v, double k) {
      this.setBy(k*v.radius, v.longitude, v.latitude);  return this;
   }

   public final MuP3D normalize() {  radius=1.0; return this; }

   public final MuP3D setByLonAdd(ImuP3D v, double angle) {
      this.setBy( v.radius, v.longitude+angle, v.latitude );
      return this;
   }

   public final MuP3D setByLonSub(ImuP3D v, double angle) {
      this.setBy( v.radius, v.longitude-angle, v.latitude );
      return this;
   }

   public final MuP3D setByLatSub(ImuP3D v, double angle) {
      this.setBy( v.radius, v.longitude, v.latitude-angle );
      return this;
   }
}