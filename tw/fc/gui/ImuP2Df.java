package tw.fc.gui;

import tw.fc.DuplicableI;
import tw.fc.Std;
import tw.fc.TxOStream;

//A21112 xie add
public class ImuP2Df 
implements DuplicableI<MuP2Df>{
   float radius; // �b�|
   float angle; // ����(theta)

   public static boolean zeroTo2PI=true;
   public static final ImuP2Df ZERO=new ImuP2Df(0f, 0f);

   public ImuP2Df(){ this(1f, 0f); }
   public ImuP2Df(float r, float a){
      this.radius=r; this.angle=a;
   }
   public ImuP2Df(ImuP2Df src) {  //: �� ImuP2D���� src�y�y�y��
      this(src.radius, src.angle);
   }
   public ImuP2Df(ImuV2D src) { setBy(src); }


    //[ ��public, ���QMuP2Df��
   ImuP2Df setBy(ImuV2D src){
      this.radius = (float)Math.sqrt(src.x*src.x + src.y*src.y);
      if(radius == 0.0){ // src�����I
         angle=0f;
      }
      else{
         angle = (float)Math.atan2(src.y, src.x);  // �g�� -PI ~ +PI
         if(zeroTo2PI && angle < 0) {
            angle += Std.TWO_PI;             // �g�� 0 ~ 2PI
         }
      }
      return this;
   }

   //-----------------------------------------------

   public final float rad(){ return this.radius; }
   public final float theta(){ return this.theta(); }

   public final boolean equals(ImuP2Df v2){
      return (this.radius==v2.radius && this.angle==v2.angle);
   }

   public final boolean notEquals(ImuP2Df v2){ return !equals(v2);}

   public final boolean equals(ImuP2Df v2, float errSq) {
      return new ImuV2Df(this).equals(new ImuV2Df(v2),errSq) ;
   }

   public final boolean notEquals(ImuP2Df v2, float epsilon) {
      return !equals(v2, epsilon);
   }

   public final boolean equals(Object v2) {
      return equals((ImuP2Df)v2);
   }

   public final int hashCode() {
      long tL; int tI=17;
      tL=Double.doubleToLongBits(radius); tI=tI*37+(int)(tL^(tL>>32));
      tL=Double.doubleToLongBits(angle); tI=tI*37+(int)(tL^(tL>>32));     
      return tI;
   }

   public final boolean isZero()  { return radius==0; }  //: �P�_ this�� radius�O�_�� 0
   public final boolean notZero() { return radius!=0; }  

   //[ -----------  implement DuplicableI
   public final MuP2Df duplicate() { return new MuP2Df(this); }
   //] -----------  implement DuplicableI

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

   //      public final ImuP2Df neg() {                                                            //: this?????y??
   //         return new ImuP2Df(radius,angle+Math.PI,-latitude);
   //      }
   //
   //////public final ImuP2Df sMul(float k) {  //:  scalar product
   public final ImuP2Df mul(float k) {  //:  scalar product                               //: radius???H?Y?? k
      return new ImuP2Df(k*radius, angle);
   }  

}
