package tw.fc.gui;
   import tw.fc.DuplicableI;
   import tw.fc.PrintableI;
   import tw.fc.TxOStream;
   import tw.fc.Std;

//**********    ImuP3D.java    *********************
//
//  floating-point 3D vector in polar form (spherical coordinate)
//

public class ImuP3Df
   implements DuplicableI<MuP3Df>, PrintableI
{

   float radius;    //: 半徑
   float longitude; //: 經度, in radian
   float latitude;  //: 緯度, in radian

   private void adjustLatRad() {
      latitude=(float)Std.bias2PI(latitude);  //: 先調整緯度在正負PI之間
      if(latitude>Std.HALF_PI) {       //: 緯度高過北極
         latitude=(float)(Math.PI-latitude);  longitude+=(float)Math.PI;
      }
      if(latitude<-Std.HALF_PI) {      //: 緯度低過南極
         latitude=(float)(-Math.PI-latitude);  longitude+=(float)Math.PI;
      }
      if(radius<0) {
         radius=-radius;  latitude=-latitude;  longitude+=Math.PI;
      }
   }

   public void adjustModBias() {  //:  天球適用
      adjustLatRad();
      longitude=(float)Std.mod2PI(longitude); //:  0 <= lon < 2PI
   }

   public void adjustBiasBias() { //: 地球適用
      adjustLatRad();
      longitude=(float)Std.bias2PI(longitude); //:  -PI <= lon < +PI
   }

   //-----------------------------------------------
   public ImuP3Df(float r, float lon, float lat) {
      radius=r;  longitude=lon;  latitude=lat;
   }

   public ImuP3Df(float lon, float lat) { //: 預設在單位球面
      this(1f, lon, lat);
   }
   public ImuP3Df() {  this(1f, 0f, 0f);  }

   public ImuP3Df(ImuP3Df src) {
      this(src.radius, src.longitude, src.latitude);
      //this(src.longitude, src.latitude, src.radius);
   }

   //[ 不public, 有被MuP3Df用
   void setBy(ImuV3Df src) {
      radius=(float)Math.sqrt(src.x*src.x+src.y*src.y+src.z*src.z);
      if(radius==0f) {
         longitude=0f;  latitude=0f;
      }
      else {
         if(src.x==0f && src.y==0f) {
            longitude=0f;
         }
         else {
            longitude=(float)Math.atan2(src.y,src.x);   // 經度 -PI ~ +PI
            if(zeroTo2PI && longitude<0f) {
               longitude+=Std.TWO_PI;   // 經度 0 ~ 2PI
            }
         }

         final float t=src.z/radius;
         if(t>1.0f) latitude=(float)Math.PI;           // error adjust
         else if(t<-1.0f)  latitude=(float)-Math.PI;   // error adjust
         else latitude=(float)tw.fc.Std.a_sin(t);
      }
   }

   public ImuP3Df(ImuV3Df src) {  setBy(src); }

   public MuV3Df toV3Df() {  return new MuV3Df(this);   }

   //-----------------------------------------------
   public final float rad() { return radius;    }
   public final float lon() { return longitude; }
   public final float lat() { return latitude;  }

   public final boolean equals(ImuP3Df v2) {
      return (radius==v2.radius &&
              longitude==v2.longitude && latitude==v2.latitude);
   }
   public final boolean notEquals(ImuP3Df v2) { return !equals(v2); }

   public final boolean equals(ImuP3Df v2, float errSq) {
      return new ImuV3Df(this).equals(new ImuV3Df(v2),errSq) ;
   }
   public final boolean notEquals(ImuP3Df v2, float epsilon) {
      return !equals(v2, epsilon);
   }
   public final boolean equals(Object v2) {
      return equals((ImuP3Df)v2);
   }
   public final int hashCode() {
      long tL; int tI=17;
      tL=Double.doubleToLongBits(radius); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(longitude); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(latitude); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }

   public final boolean isZero()  { return radius==0f; }
   public final boolean notZero() { return radius!=0f; }

   //[ -----------  implement DuplicableI
   public final MuP3Df duplicate() {  return new MuP3Df(this); }
   //] -----------  implement DuplicableI

   public MuP3D toP3D() {
      return new MuP3D(radius, longitude, latitude);
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

   public final ImuP3Df neg() {
      return new ImuP3Df(radius,(float)(longitude+Math.PI),-latitude);
   }

   //////public final ImuP3Df sMul(float k) {  //:  scalar product
   public final ImuP3Df mul(float k) {  //:  scalar product
      return new ImuP3Df(k*radius, longitude, latitude);
   }


 //[ =========  static part  ===========================
   public static final ImuP3Df ZERO=new ImuP3Df(0f, 0f, 0f);
   public static final ImuP3Df E1  =new ImuP3Df(1f, 0f, 0f);
   public static final ImuP3Df E2  =new ImuP3Df(1f, Std.HALF_PIf, 0f);
   public static final ImuP3Df NORTH =new ImuP3Df(1f, 0f, Std.HALF_PIf);
   public static final ImuP3Df SOUTH =new ImuP3Df(1f, 0f, (float)-Math.PI/2);;
   public static final ImuP3Df E3  =NORTH;

   public static boolean zeroTo2PI=true;    //: 經度 0 ~ 2PI
                             //   =false;   //: 經度 -PI ~ +PI


 //] =========  static part  ===========================

}