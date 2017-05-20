package tw.fc.gui;

import tw.fc.DuplicableI;
import tw.fc.Std;
import tw.fc.TxOStream;

//A21112 xie add
public class ImuP2D 
   implements DuplicableI<MuP2D>{
   double radius; // �b�|
   double angle; // ����(theta)

   public static boolean zeroTo2PI=true;
   public static final ImuP2D ZERO=new ImuP2D(0.0, 0.0);
   
   public ImuP2D(){ this(1.0, 0.0); }
   public ImuP2D(double r, double a){
      this.radius=r; this.angle=a;
   }
   public ImuP2D(ImuP2D src) {   //: �� ImuP2D���� src�y�y�y��
      this(src.radius, src.angle);
   }
   public ImuP2D(ImuV2D src) { setBy(src); }

   
   //[ ��public, ���QMuP2D��
   ImuP2D setBy(ImuV2D src){
      this.radius = Math.sqrt(src.x*src.x + src.y*src.y);
      if(radius == 0.0){ // src�����I
         angle=0.0;
      }
      else{
         angle = Math.atan2(src.y, src.x);  // �g�� -PI ~ +PI
         if(zeroTo2PI && angle < 0) {
            angle += Std.TWO_PI;            // �g�� 0 ~ 2PI
         }
      }
      return this;
   }
   
   //-----------------------------------------------
   
   public final double rad(){ return this.radius; }
   public final double theta(){ return this.theta(); }
   
   public final boolean equals(ImuP2D v2){
      return (this.radius==v2.radius && this.angle==v2.angle);
   }
   
   public final boolean notEquals(ImuP2D v2){ return !equals(v2);}
   
   public final boolean equals(ImuP2D v2, double errSq) {
      return new ImuV2D(this).equals(new ImuV2D(v2),errSq) ;
   }
   
   public final boolean notEquals(ImuP2D v2, double epsilon) {
      return !equals(v2, epsilon);
   }

   public final boolean equals(Object v2) {
      return equals((ImuP2D)v2);
   }
   
   public final int hashCode() {
      long tL; int tI=17;
      tL=Double.doubleToLongBits(radius); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(angle); tI=tI*37+(int)(tL^(tL>>32));     
      return tI;
   }
   
   public final boolean isZero()  { return radius==0; }    //: �P�_ this�� radius�O�_�� 0
   public final boolean notZero() { return radius!=0; }  
   
   //[ -----------  implement DuplicableI
   public final MuP2D duplicate() { return new MuP2D(this); }
   //] -----------  implement DuplicableI

   public final MuP2Df toP2Df() {                         //: �N this�ন ImuP2Df
      return new MuP2Df((float)radius,(float)angle);
   }

   public String toString() {
      return "("+radius+"�F"+angle+")";
   }
   
   //[-------- implements PrintableI -------------------
   public final void printTo(TxOStream ooo) throws java.io.IOException {
      ooo.p("(").p(radius).p(';').pc(angle).p(")");
   }

   public final void widthPrintTo(int w, TxOStream ooo)
      throws java.io.IOException
   {
      ooo.p("P(").wp(w,radius).p(';').wpc((int) angle).p(")");
   }
   //]-------- implements PrintableI -------------------
   
//   public final ImuP2D neg() {                                                            //: this���﨤�y��
//      return new ImuP2D(radius,angle+Math.PI,-latitude);
//   }
//
   //////public final ImuP2D sMul(double k) {  //:  scalar product
   public final ImuP2D mul(double k) {  //:  scalar product                               //: radius���H�Y�� k
      return new ImuP2D(k*radius, angle);
   }  

}
