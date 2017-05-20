package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//***********************************************
public class floatRef extends fNumRef // PrintableI, ScannableI, 
   implements DuplicableI<floatRef>, SetableI<floatRef>,  
              java.lang.Comparable<floatRef>
{
   //public float _;    //:  before A30731
   public float v;    //:  encorage direct access

   //---------------------------------------- 
   public  floatRef() {  v=0.0F; }
   public  floatRef(float x) {  v=x; }
   public  floatRef(floatRef D) {  v=D.v; }
///   public  floatRef(Float X)    {  v=X.doubleValue(); }
///   public  floatRef(String s) {  v=Float.valueOf(s).doubleValue();  }

   //---------------
   public boolean isInfinite() {  return Float.isInfinite(v); } //: 正負無限大
   public boolean isNaN() {  return Float.isNaN(v); }
   public final String toString() {  return (""+v); }
   public final boolean equals(floatRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((floatRef)d2); }
   public final boolean equals(float d2) {  return (v==d2); }
   public final int hashCode() {  
      final long t=Double.doubleToLongBits(v);
      return (int)(t^(t>>32));   //: as is recommended by Effective Jave 
   }

   public final floatRef setBy(float d) { v=d;   return this;  }
///   public final floatRef setBy(Float D) {  v=D.doubleValue(); return this; }
///   public final floatRef setBy(String s) {  
///   //:  v=Float.parseDouble(s); // VJ not support
///        v=Float.valueOf(s).doubleValue();  return this;
///   }
  
   public static float get_float_from(TxIStream iS) throws IOException {
      return (float)doubleRef.get_double_from(iS);
   }


//[-------- implements OperatableI  
   public final floatRef duplicate() {  return new floatRef(this);   }
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }

   public final floatRef setBy(floatRef src) {
      v=src.v;  return this;
   }
/////   public final SetableI setBy(DuplicableI s) {
/////      final floatRef src=(floatRef)s;
/////      v=src.v;  return this;
/////   }
   public final void scanFrom(TxIStream iS) throws IOException {
    // 由getDouble做 iS.skipWS();
      //v=iS.get_float();  
      this.v= floatRef.get_float_from(iS);
   }
//]-------- implements OperatableI  

///   public static final float parseDouble(String s) {
///      return Float.parseDouble(s.trim()); 
///   } 

   public final float add(float d2) { return v+d2; }
   public final float sub(float d2) { return v-d2; }
   public final float mul(float d2) { return v*d2; }
   public final float div(float d2) { 
   // if(d2==0) throw new ArithmeticException("in float.Div ");
                            //: 依JAVA之風俗當NaN處理
      return v/d2; 
   }
   public final float add(floatRef d2) { return v+d2.v; }
   public final float sub(floatRef d2) { return v-d2.v; }
   public final float mul(floatRef d2) { return v*d2.v; }
   public final float div(floatRef d2) { 
   // if(d2==0) throw new ArithmeticException("in float.Div ");
                            //: 依JAVA之風俗當NaN處理
      return v/d2.v; 
   }

   public final floatRef addBy(float d2) { v+=d2; return this; }
   public final floatRef subBy(float d2) { v-=d2; return this; }
   public final floatRef mulBy(float d2) { v*=d2; return this; }
   public final floatRef divBy(float d2) { 
   // if(d2==0) throw new ArithmeticException("in floatRef.DivBy ");
                            //: 依JAVA之風俗當NaN處理
      v/=d2; return this;  
   }
   public final floatRef addBy(floatRef d2) { v+=d2.v; return this; }
   public final floatRef subBy(floatRef d2) { v-=d2.v; return this; }
   public final floatRef mulBy(floatRef d2) { v*=d2.v; return this; }
   public final floatRef divBy(floatRef d2) { v/=d2.v; return this; }

// public final floatRef setByAdd(float x1, float x2) {
//    v=x1+x2;      return this;
// }
//]  用 setBy(x1-x2) 即可

// public final floatRef setByAdd(floatRef x1, floatRef x2) {
//    v=x1+x2;      return this;
// }
//]  用 setBy(x1-x2) 即可

   public final floatRef inc() {  ++v;  return this; } 
   public final floatRef dec() {  --v;  return this; } 

   public final float neg() {   return -v;   }

   public final floatRef negate() { 
      v=-v;    return this;
   }

   public final float inv() { 
   // if(v==0) throw new ArithmeticException("in float.Inv ");
                            //: 依JAVA之風俗當NaN處理
      return 1/v; 
   }

   public final floatRef invert() { 
   // if(v==0) throw new ArithmeticException("in floatRef.Invert");
                            //: 依JAVA之風俗當NaN處理
      v=1/v; 
      return this;
   }

   public final float abs() { return Math.abs(v); }
   public final floatRef setByAbs(float x) {
      v= Math.abs(x);  return this;
   }

   public final float pow(int n) {  
      return (float)Math.pow(v, n);  
   }

   public final floatRef powBy(int n) {
      v=(float)Math.pow(v,n);  return this;
   }
   public final floatRef setByPow(float x, int n) {
      v=(float)Math.pow(x,n);  return this;
   }

   public final int compareTo(float v2) {
      return Float.compare(v, v2);
   }
   public final int compareTo(floatRef v2) { 
      return Float.compare(v, v2.v);
   }
//   public final int compareTo(Object o) { //: implements java.lang.Comparable
//      return compareTo((floatRef)o);      
//   }

   public final boolean eq(float v2) {  return compareTo(v2)==0; }
   public final boolean ne(float v2) {  return compareTo(v2)!=0; }
   public final boolean gt(float v2) {  return compareTo(v2)>0; }
   public final boolean ge(float v2) {  return compareTo(v2)>=0; }
   public final boolean lt(float v2) {  return compareTo(v2)<0; }
   public final boolean le(float v2) {  return compareTo(v2)<=0; }
   public final boolean eq(floatRef v2) {  return compareTo(v2.v)==0; }
   public final boolean ne(floatRef v2) {  return compareTo(v2.v)!=0; }
   public final boolean gt(floatRef v2) {  return compareTo(v2.v)>0; }
   public final boolean ge(floatRef v2) {  return compareTo(v2.v)>=0; }
   public final boolean lt(floatRef v2) {  return compareTo(v2.v)<0; }
   public final boolean le(floatRef v2) {  return compareTo(v2.v)<=0; }
   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}