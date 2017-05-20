package tw.fc ;

public class Std {

   //[ ready for use with 'import static Std.*;'
   public static final TxOCStream cout=new TxOCStream(System.out);
   public static final TxOCStream cerr=new TxOCStream(System.err);
   public static final TxICStream cin=new TxICStream(System.in);

   //--------------------------
   private static double _epsilon=0.000001; //: 1 millionth
   private static double _epsilonSq=_epsilon*_epsilon;
   private static double _epsilonCb=_epsilon*_epsilon*_epsilon;
   private static double _bound=1000000;
   private static double _boundSq=_bound*_bound;
   private static double _boundCb=_bound*_bound*_bound;

   public static final double epsilon() {  return _epsilon; }
   public static final double epsilonSq() {  return _epsilonSq; }
   public static final double epsilonCb() {  return _epsilonCb; }
   public static final void setEpsilon(double e) {
      if(e<=0 || e>0.01) throw new IllegalArgumentException("... :"+e);
      _epsilon=e;
      _epsilonSq=e*e;
      _epsilonCb=_epsilonSq*e;
   }
   public static final double bound() {  return _bound; }
   public static final double boundSq() {  return _boundSq; }
   public static final double boundCb() {  return _boundCb; }
   public static final void setBound(double b) {
      if(b<=100|| b>Integer.MAX_VALUE) throw new IllegalArgumentException("... :"+b);
      _bound=b;
      _boundSq=_bound*_bound;
      _boundCb=_boundSq*_bound;
   }

   //-----------------
   public static final double
      DEG2RAD=Math.PI/180.0, RAD2DEG=180.0/Math.PI;

   public static final double
      PI=Math.PI, TWO_PI=2.0*Math.PI, HALF_PI=Math.PI/2;

   public static final double DEG1=Math.PI/180.0;     //: 角度
   public static final double MIN1=Math.PI/10800.0;   //: 角分
   public static final double SEC1=Math.PI/648000.0;  //: 角秒

   public static final double
      DEG0=0.0, DEG5=Math.PI/36.0, DEG10=Math.PI/18.0,
      DEG15=Math.PI/12.0, DEG20=Math.PI/9.0, DEG30=Math.PI/6.0,
      DEG40=Math.PI/4.5,  DEG50=Math.PI/3.6, DEG45=Math.PI/4.0,
      DEG60=Math.PI/3.0,  DEG70=Math.PI*7.0/18.0, DEG75=Math.PI/2.4,
      DEG80=Math.PI/2.25, DEG90=Math.PI/2.0,
      DEG100=Math.PI/1.8, DEG110=Math.PI*11/18.0, DEG120=Math.PI/1.5,
      DEG130=Math.PI*13.0/18.0, DEG140=Math.PI*7.0/9.0, DEG150=Math.PI/1.2,
      DEG160=Math.PI/1.125,     DEG170=Math.PI*17.0/18.0, DEG180=Math.PI,
      DEG270=Math.PI*1.5,       DEG360=Math.PI*2.0;


   //-----------------
   public static final float
      DEG2RADf=(float)(Math.PI/180.0), RAD2DEGf=(float)(180.0/Math.PI);

   public static final float
      PIf=(float)Math.PI,
      TWO_PIf=(float)(2.0*Math.PI), HALF_PIf=(float)(Math.PI/2);

   public static final float DEG1f=(float)(Math.PI/180.00);     //: 角度
   public static final float MIN1f=(float)(Math.PI/10800.0);   //: 角分
   public static final float SEC1f=(float)(Math.PI/648000.0);  //: 角秒

   public static final float
      DEG0f=0.0f,          DEG10f=(float)DEG10,   DEG15f=(float)DEG15,
      DEG20f=(float)DEG20, DEG30f=(float)DEG30,   DEG40f=(float)DEG40,
      DEG50f=(float)DEG50, DEG45f=(float)DEG45,   DEG60f=(float)DEG60,
      DEG70f=(float)DEG70, DEG75f=(float)DEG75,   DEG80f=(float)DEG80,
      DEG90f=(float)DEG90, DEG100f=(float)DEG100, DEG110f=(float)DEG110,
      DEG120f=(float)DEG120,DEG130f=(float)DEG130,DEG140f=(float)DEG140,
      DEG150f=(float)DEG150,DEG160f=(float)DEG160,DEG170f=(float)DEG170,
      DEG180f=(float)DEG180,
      DEG270f=(float)DEG270, DEG360f=(float)DEG360;

   //------------
   public static final int HourPerDay=24,
                           MinPerDay=1440,
                           SecPerDay=86400;

   public static final double SID_HOUR=Math.PI/12.0,    //: 恆星時
                              SID_MIN=Math.PI/720.0,    //: 恆星分
                              SID_SEC=Math.PI/43200.0;  //: 恆星秒

   public static final float SID_HOURf=(float)(Math.PI/12.0),   //: 恆星時
                             SID_MINf=(float)(Math.PI/720.0),   //: 恆星分
                             SID_SECf=(float)(Math.PI/43200.0); //: 恆星秒

   //--------------------------
   public static int iRound(double d) {
      return (int)Math.round(d);
   }

   static double rangeCorrection(double sine) {
   //: 原為 class Space3D的static方法 double correctingSine(double)
   //: 使用Math.asin(s), 在s為1.0時因誤差而使函數值為NaN.
   //: 此bug除錯達數小時.     20040509
   //:
      if(sine>1.0) {
         if(sine>1.001) {
            throw new IllegalArgumentException("range error: "+sine);
         }
         return 1.0;  //: floating error correction
      }
      if(sine<-1.0) {
         if(sine<-1.001) {
            throw new IllegalArgumentException("range error: "+sine);
         }
         return -1.0;  //: floating error correction
      }
      return sine;
   }
   public static double a_sin(double d) {  //: 避免直接用 Math.asin
      return Math.asin(rangeCorrection(d));  //: 避免直接用Math.asin
   }
   public static double a_cos(double d) { //: 避免直接用 Math.acos
      return Math.acos(rangeCorrection(d));  //: 避免直接用Math.acos
   }

   //-----------------
   //[  the fractional part of a number
   //[  e.x.  frac(5.2)==0.2,  frac(-5.2)==0.8
   public static double frac(double x){  // 就是mod(x,1)
      return x-Math.floor(x);
   }
   //[  mod(x, M) 就是以M為一圈時, 依M的轉向所得的餘量.
   //[  M正時 0 <= mod(x,M) < M,  M負時  M < mod(x,M) <=0
   //[  e.x.  mod(363,360)==3,  mod(-363,360)==357
   //[        mod(-363,-360)==-3,  mod(363,-360)==-357
   public static double mod(double x, double M) {
      return frac(x/M)*M;   //  x%M 不太保險
   // return ((x/M)-Math.floor(x/M))*M;
   }
   //[  mod(x,2*PI)
   public static double mod2PI(double x) {
      return frac(x/TWO_PI)*TWO_PI;
   }
   //[  mod(x,PI)
   public static double modPI(double x) {
      return frac(x/PI)*PI;
   }
   //[  mod(x,360)
   public static double mod360(double x) {
      return frac(x/360.0)*360.0;
   }
   //[  mod(x,180)
   public static double mod180(double x) {
      return frac(x/180.0)*180.0;
   }

   //[  the integral bias part of a number
   //[  e.x.  bias(5.2)==0.2,  bias(5.6)==-0.4
   public static double bias(double x){
      return x-Math.round(x);
   }
   //[  bias(x,M) 就是以M為一圈時, 依M的轉向所得的半圈偏量.
   //[  e.x.  bias(363,360)==3,  bias(-363,360)==-3
   //[        bias(-363,-360)==-3,  bias(363,-360)==3
   public static double bias(double x, double M) {
      return bias(x/M)*M;
   }
   public static double bias2PI(double x) {
      return bias(x/TWO_PI)*TWO_PI;
   }
   public static double biasPI(double x) {
      return bias(x/PI)*PI;
   }
   public static double bias360(double x) {
      return bias(x/360.0)*360.0;
   }
   public static double bias180(double x) {
      return bias(x/180.0)*180.0;
   }

   //--------------------------

   public static void testArc() {
      cout.p("mod360(363)=").pn(mod360(363));
      cout.p("mod360(-363)=").pn(mod360(-363));
      cout.p("mod(363,360)=").pn(mod(363,360));
      cout.p("mod(-363,360)=").pn(mod(-363,360));
      cout.p("mod(-363,-360)=").pn(mod(-363,-360));
      cout.p("mod(363,-360)=").pn(mod(363,-360));
      cout.p("bias(360+183,360)=").pn(bias(360+183,360));
      cout.p("bias(-360-183,360)=").pn(bias(-543,360));
      cout.p("bias(360+183,-360)=").pn(bias(543,-360));
      cout.p("bias(-360-183,-360)=").pn(bias(-543,-360));
/*
mod360(363)=2.9999999999999893
mod360(-363)=357.0
mod(363,360)=2.9999999999999893
mod(-363,360)=357.0
mod(-363,-360)=-2.9999999999999893
mod(363,-360)=-357.0
bias(360+183,360)=-177.0
bias(-360-183,360)=177.0
bias(360+183,-360)=-177.0
bias(-360-183,-360)=177.0
*/
   }

   public static void main(String[] a) {
      testArc();
   }


}