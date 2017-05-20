package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.TxOStream;
   import tw.fc.Std;

//**********    ImuP3D.java    *********************
//
//  floating-point 3D vector in polar form (spherical coordinate)
//

public class ImuP3D
   implements DuplicableI<MuP3D>, PrintableI
{

   double radius;    //: 半徑
   double longitude; //: 經度, in radian  azimuth  phi
   double latitude;  //: 緯度, in radian  altitude theta

   private void adjustLatRad() {
      latitude=Std.bias2PI(latitude);  //: 先調整緯度在正負PI之間
      if(latitude>Std.HALF_PI) {       //: 緯度高過北極
         latitude=Math.PI-latitude;  longitude+=Math.PI;
      }
      if(latitude<-Std.HALF_PI) {      //: 緯度低過南極
         latitude=-Math.PI-latitude;  longitude+=Math.PI;
      }
      if(radius<0) {
         radius=-radius;  latitude=-latitude;  longitude+=Math.PI;
      }
   }

   public void adjustModBias() {  //:  天球適用
      adjustLatRad();
      longitude=Std.mod2PI(longitude); //:  0 <= lon < 2PI
   }

   public void adjustBiasBias() { //: 地球適用
      adjustLatRad();
      longitude=Std.bias2PI(longitude); //:  -PI <= lon < +PI
   }

   //-----------------------------------------------
   public ImuP3D(double r, double lon, double lat) {
      radius=r;  longitude=lon;  latitude=lat;
   }

   public ImuP3D(double lon, double lat) { //: 預設在單位球面
      this(1.0, lon, lat);
   }

   public ImuP3D() { this(1.0, 0.0, 0.0); }

   public ImuP3D(ImuP3D src) {
      this(src.radius, src.longitude, src.latitude);
      //this(src.longitude, src.latitude, src.radius);
   }

   //[ 不public, 有被MuP3D用
   ImuP3D setBy(ImuV3D src) {
      radius = Math.sqrt(src.x*src.x + src.y*src.y + src.z*src.z);
      if (radius == 0.0) { // src是原點
         longitude=0;  latitude=0;
      }
      else {
         if(src.x==0 && src.y==0) {  // src在z軸上
            longitude=0;
         }
         else { // src在z軸外
            longitude = Math.atan2(src.y, src.x);  // 經度 -PI ~ +PI
            if(zeroTo2PI && longitude < 0) {
               longitude += Std.TWO_PI;            // 經度 0 ~ 2PI
            }
         }
         final double t = src.z / radius;
         if (t > 1.0) latitude = Math.PI/2;        // 誤差 error adjust
         else if (t < -1.0) latitude = -Math.PI/2; // 誤差 error adjust
         else latitude = tw.fc.Std.a_sin(t);
      }
      return this;
   }

   public ImuP3D(ImuV3D src) { setBy(src); }

// 暫關閉, 太方便怕會降低可讀性
//   //[ 不public, 可被MuP3D用
//   //[ cordOld是標準正交座標,
//   //[ 求算cordOld相對於localSys的極座標並設入this
//   void setBy(ImuV3D cordOld, ImuAr3D localSys) {
//      this.setBy(localSys.findNewCord(cordOld));
//   }
//
//   //[ construct local polar coordinate of src w.r.t localSys
//   //[ localSys and src described in standars system
//   public ImuP3D(ImuV3D src, ImuAr3D localSys) {
//      this.setBy(src, localSys);
//   }
//


   public MuV3D toV3D() { return new MuV3D(this); }

   //-----------------------------------------------
   public final double rad() {  return radius; }
   public final double lon() { return longitude; }
   public final double lat() {  return latitude; }

   public final boolean equals(ImuP3D v2) {
      return (radius==v2.radius &&
              longitude==v2.longitude && latitude==v2.latitude);
   }

   public final boolean notEquals(ImuP3D v2) { return !equals(v2); }

   public final boolean equals(ImuP3D v2, double errSq) {
      return new ImuV3D(this).equals(new ImuV3D(v2),errSq) ;
   }

   public final boolean notEquals(ImuP3D v2, double epsilon) {
      return !equals(v2, epsilon);
   }

   public final boolean equals(Object v2) {
      return equals((ImuP3D)v2);
   }

   public final int hashCode() {
      long tL; int tI=17;
      tL=Double.doubleToLongBits(radius); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(longitude); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(latitude); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero()  { return radius==0; }
   public final boolean notZero() { return radius!=0; }

   //[ -----------  implement DuplicableI
   public final MuP3D duplicate() { return new MuP3D(this); }
   //] -----------  implement DuplicableI

   public final MuP3Df toP3Df() {
      return new MuP3Df((float)radius,(float)longitude,(float)latitude);
   }

   public String toString() {
      return "("+radius+";"+longitude+","+latitude+")";
   }

   //[-------- implements PrintableI -------------------
   public final void printTo(TxOStream ooo) throws java.io.IOException {
      ooo.p("(").p(radius).p(';').pc(longitude).p(latitude).p(")");
   }

   public final void widthPrintTo(int w, TxOStream ooo) 
      throws java.io.IOException
   {
      ooo.p("P(").wp(w,radius).p(';').wpc(w,longitude).wp(w,latitude).p(")");
   }
   //]-------- implements PrintableI -------------------

   public final ImuP3D neg() {
      return new ImuP3D(radius,longitude+Math.PI,-latitude);
   }

   //////public final ImuP3D sMul(double k) {  //:  scalar product
   public final ImuP3D mul(double k) {  //:  scalar product
      return new ImuP3D(k*radius, longitude, latitude);
   }

 //[ =========  static part  ===========================
   public static final ImuP3D ZERO=new ImuP3D(0.0, 0.0, 0.0);
   public static final ImuP3D E1  =new ImuP3D(1.0, 0.0, 0.0);
   public static final ImuP3D E2  =new ImuP3D(1.0, Std.HALF_PI, 0.0);
   public static final ImuP3D NORTH =new ImuP3D(1.0, 0.0, Std.HALF_PI);
   public static final ImuP3D SOUTH =new ImuP3D(1.0, 0.0, -Math.PI/2);;
   public static final ImuP3D E3  =NORTH;

   public static boolean zeroTo2PI=true;    //: 經度 0 ~ 2PI
                             //   =false;   //: 經度 -PI ~ +PI

 //] =========  static part  ===========================

}