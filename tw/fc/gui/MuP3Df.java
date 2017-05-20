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
public class MuP3Df extends ImuP3Df
     implements SetableI<ImuP3Df>, ScannableI
{
   public MuP3Df(float r, float lon, float lat) { super(r, lon, lat);}

   public MuP3Df(float lon, float lat) { super(1f, lon, lat); }

   public MuP3Df() { super(1f, 0f, 0f); }

   public MuP3Df(ImuP3Df src) { super(src); }

   public MuP3Df(ImuV3Df src) { super(src);  }

   //---------------------------------------------------
   public final void radSetBy(float r) {  radius=r;  }
   public final void lonSetBy(float lon) {  longitude=lon;  }
   public final void latSetBy(float lat) {  latitude=lat;   }

   public final void setBy(float r, float lon, float lat) {
      radius=r;  longitude=lon;  latitude=lat;
   }

   public final void setBy(float lon, float lat) { setBy(1f,lon,lat); }

   public final MuP3Df setBy(ImuP3Df v2) {
      radius=v2.radius; longitude=v2.longitude;  latitude=v2.latitude;
      return this;
   }
/////   public final SetableI setBy(DuplicableI src) {
/////      return setBy((ImuP3Df)src);
/////   }

   public void setBy(ImuV3Df src) { //: superªºsetBy ¤£ public
      super.setBy(src);
   }

   public void scanFrom(TxIStream iii) throws IOException {
       //[ key in as the form:  (r;t,f)
         iii.skipWS().expect('(').skipWS();
         radius=iii.get_float();
         iii.skipWS().expect(';').skipWS();
         longitude=iii.get_float();
         iii.skipWS().expect(',').skipWS();
         latitude=iii.get_float();
         iii.skipWS().expect(')');
   }
   public static MuP3Df parseP3D(String s) {
//      try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuP3Df x=new MuP3Df();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }

//>>>>>> range adjustment?
   public final MuP3Df radAddBy(float dx) {  radius+=dx;  return this; }
   public final MuP3Df lonAddBy(float dx) {  longitude+=dx;  return this; }
   public final MuP3Df latAddBy(float dx) {  latitude+=dx;  return this; }

   public final MuP3Df radSubBy(float dx) {  radius-=dx;  return this; }
   public final MuP3Df lonSubBy(float dx) {  longitude-=dx;  return this; }
   public final MuP3Df latSubBy(float dx) {  latitude-=dx;  return this; }

   public final MuP3Df negate() {
      longitude+=Math.PI;  latitude=-latitude;  return this;
   }
   public final MuP3Df setByNeg(ImuP3Df v) {
      this.setBy(v.radius, v.longitude+(float)Math.PI, -v.latitude);
      return this;
   }

   public final MuP3Df sMulBy(float k) {  radius*=k;  return this; }
   public final MuP3Df setBySMul(float k, ImuP3Df v) {
      this.setBy(k*v.radius, v.longitude, v.latitude);  return this;
   }
   public final MuP3Df setBySMul(ImuP3Df v, float k) {
      this.setBy(k*v.radius, v.longitude, v.latitude);  return this;
   }

   public final MuP3Df normalize() {  radius=1f; return this; }

   public final MuP3Df setByLonAdd(ImuP3Df v, float angle) {
      this.setBy( v.radius, v.longitude+angle, v.latitude );
      return this;
   }
   public final MuP3Df setByLonSub(ImuP3Df v, float angle) {
      this.setBy( v.radius, v.longitude-angle, v.latitude );
      return this;
   }
   public final MuP3Df setByLatSub(ImuP3Df v, float angle) {
      this.setBy( v.radius, v.longitude, v.latitude-angle );
      return this;
   }
}