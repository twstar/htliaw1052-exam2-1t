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

   double radius;    //: �b�|
   double longitude; //: �g��, in radian  azimuth  phi
   double latitude;  //: �n��, in radian  altitude theta

   private void adjustLatRad() {
      latitude=Std.bias2PI(latitude);  //: ���վ�n�צb���tPI����
      if(latitude>Std.HALF_PI) {       //: �n�װ��L�_��
         latitude=Math.PI-latitude;  longitude+=Math.PI;
      }
      if(latitude<-Std.HALF_PI) {      //: �n�קC�L�n��
         latitude=-Math.PI-latitude;  longitude+=Math.PI;
      }
      if(radius<0) {
         radius=-radius;  latitude=-latitude;  longitude+=Math.PI;
      }
   }

   public void adjustModBias() {  //:  �Ѳy�A��
      adjustLatRad();
      longitude=Std.mod2PI(longitude); //:  0 <= lon < 2PI
   }

   public void adjustBiasBias() { //: �a�y�A��
      adjustLatRad();
      longitude=Std.bias2PI(longitude); //:  -PI <= lon < +PI
   }

   //-----------------------------------------------
   public ImuP3D(double r, double lon, double lat) {
      radius=r;  longitude=lon;  latitude=lat;
   }

   public ImuP3D(double lon, double lat) { //: �w�]�b���y��
      this(1.0, lon, lat);
   }

   public ImuP3D() { this(1.0, 0.0, 0.0); }

   public ImuP3D(ImuP3D src) {
      this(src.radius, src.longitude, src.latitude);
      //this(src.longitude, src.latitude, src.radius);
   }

   //[ ��public, ���QMuP3D��
   ImuP3D setBy(ImuV3D src) {
      radius = Math.sqrt(src.x*src.x + src.y*src.y + src.z*src.z);
      if (radius == 0.0) { // src�O���I
         longitude=0;  latitude=0;
      }
      else {
         if(src.x==0 && src.y==0) {  // src�bz�b�W
            longitude=0;
         }
         else { // src�bz�b�~
            longitude = Math.atan2(src.y, src.x);  // �g�� -PI ~ +PI
            if(zeroTo2PI && longitude < 0) {
               longitude += Std.TWO_PI;            // �g�� 0 ~ 2PI
            }
         }
         final double t = src.z / radius;
         if (t > 1.0) latitude = Math.PI/2;        // �~�t error adjust
         else if (t < -1.0) latitude = -Math.PI/2; // �~�t error adjust
         else latitude = tw.fc.Std.a_sin(t);
      }
      return this;
   }

   public ImuP3D(ImuV3D src) { setBy(src); }

// ������, �Ӥ�K�ȷ|���C�iŪ��
//   //[ ��public, �i�QMuP3D��
//   //[ cordOld�O�зǥ���y��,
//   //[ �D��cordOld�۹��localSys�����y�Шó]�Jthis
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

   public static boolean zeroTo2PI=true;    //: �g�� 0 ~ 2PI
                             //   =false;   //: �g�� -PI ~ +PI

 //] =========  static part  ===========================

}