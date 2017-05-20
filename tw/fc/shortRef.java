package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//*****************************************
public class shortRef 
   extends iNumRef // PrintableI, ScannableI,
   implements DuplicableI<shortRef>, SetableI<shortRef>, 
              java.lang.Comparable<shortRef>
{
   //public short _;  //:  before A30731
   public short v;  //:  encorage direct access

   //-----------------------------------
   public  shortRef() {  v=0; }
   public  shortRef(short i) {  v=i; } 
   public  shortRef(shortRef src) {  v=src.v; }          
///   public  shortRef(Integer I) {  v=I.intValue(); }    //: 不太有用
///   public  shortRef(String s) { v=Integer.parseInt(s);  } //: 不太有用

   //-----------------------------------
///   public final short   value() {  return v; }
///   public final boolean to_boolean() {  return (v!=0);  }
///   public final byte to_byte() {  return ((byte)v);  }
///   public final short to_short() {  return ((short)v);  }
///   public final char to_char() {  return ((char)v);  }
   @Override public final String  toString() {  return String.valueOf(v); }
   @Override public final String  toHexString(){  return Integer.toHexString(v); }
   @Override public final String  toOctalString(){ return Integer.toOctalString(v); }

   public final boolean equals(short d2) {  return (v==d2); }
   public final boolean equals(shortRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((shortRef)d2); }
   public final int hashCode() {  return v;  }

   public final shortRef setBy(short i) { v=i;   return this;  }
   public final shortRef setBy(shortRef i) { v=i.v;   return this;  }
///   public final shortRef setBy(Integer I) {  v=I.intValue(); return this; }
///   public final shortRef setBy(String s) {  
///      v=java.lang.Integer.parseInt(s); return this;   
///   }

   //[-------- implements DuplicableI  
   public final shortRef duplicate() {  return new shortRef(this);   }
   //]-------- implements DuplicableI  
   //[-------- implements PrintableI  
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }
   //]-------- implements PrintableI  

   public static short get_short_from(TxIStream iS, int radix) throws IOException {
      return (short)longRef.get_long_from(iS, radix);
   }
   public static int get_short_from(TxIStream iS) throws IOException {
      return (short)longRef.get_long_from(iS, 10);
   }

   //[-------- implements DuplicableI  
//I   public final DuplicableI duplicate() {  return new shortRef(this);   }
//   public final SetableI setBy(DuplicableI s) {
//      final shortRef src=(shortRef)s;  //: dynamic cast
//      v=src.v;  return this;
//   }
   public final void scanFrom(TxIStream iS) throws IOException {
      //  Note:  The standard method Integer.parseInt  
      //         can't work on a input stream with hybrid data.
      // iS.skipWS();   //: 由getInt負責
      //v=iS.get_short(iii.getRadix());  
      this.v= shortRef.get_short_from(iS, iS.getRadix());
   }
   //]-------- implements DuplicableI  

//----------------------------- 


   public final short add(short d2) { return (short)(v+d2); }
   public final short sub(short d2) { return (short)(v-d2); }
   public final short mul(short d2) { return (short)(v*d2); }
   public final short div(short d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (short)(v/d2); 
   }
   public final short mod(short d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (short)(v%d2); 
   }

   public final short add(shortRef d2) { return (short)(v+d2.v); }
   public final short sub(shortRef d2) { return (short)(v-d2.v); }
   public final short mul(shortRef d2) { return (short)(v*d2.v); }
   public final short div(shortRef d2) { 
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (short)(v/d2.v); 
   }
   public final short mod(shortRef d2) { 
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (short)(v%d2.v); 
   }

   public final shortRef addBy(short d2) { v+=d2; return this; }
   public final shortRef subBy(short d2) { v-=d2; return this; }
   public final shortRef mulBy(short d2) { v*=d2; return this; }
   public final shortRef divBy(short d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero");
      v/=d2; return this; 
   }
   public final shortRef modBy(short d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero");
      v%=d2; return this; 
   }
   public final shortRef addBy(shortRef d2) { v+=d2.v; return this; }
   public final shortRef subBy(shortRef d2) { v-=d2.v; return this; }
   public final shortRef mulBy(shortRef d2) { v*=d2.v; return this; }
   public final shortRef divBy(shortRef d2) { 
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v/=d2.v; return this; 
   }
   public final shortRef modBy(shortRef d2) { 
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v %=d2.v; return this; 
   }

// public final shortRef setByAdd(short x1, short x2) { 
//    v= x1+x2;  return this; 
// }
//]  用 setBy(x1-x2) 即可

//   public final shortRef setByAdd(shortRef x1, shortRef x2) { 
//      v= x1.v+x2.v;  return this; 
//   }
//]  用 setBy(x1.v-x2.v) 即可


   public final shortRef inc() {  ++v;  return this; } 
   public final shortRef dec() {  --v;  return this; } 

   public final short neg() { return (short)-v; }
   public final shortRef negate() { v=(short)-v; return this; }

   public final short abs() { return (short)Math.abs(v); }
   public final shortRef setByAbs(short x) {
      v= (short)Math.abs(x);  return this;
   }
   public final shortRef setByAbs(shortRef x) {
      v= (short)Math.abs(x.v);  return this;
   }

   //[ Math.pow不合適
   //[  static double pow(double a, double b) 
   public final short pow(int n) {    
      if(n<0) {  throw new ArithmeticException("negative power"); }
//    if(n==0 && v==0) throw new ArithmeticException("0 power of 0");
      short ans=1;
      for(int i=0; i<n; i++) {  ans*=v;  }
      return ans;
   }
   public final shortRef powBy(int n) {
      v=this.pow(n);  return this;
   }
   public final shortRef setByPow(short x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x;  }
      return this;
   }
   public final shortRef setByPow(shortRef x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x.v;  }
      return this;
   }

   //---------------------------
   public final int compareTo(short v2) {
   //[ 沒有Long.compare(short,short)
      final int d=v-v2;
      if(d>0) return 1;
      else if(d<0) return -1;
      else return 0;
   }
   public final int compareTo(shortRef v2) {  return compareTo(v2.v); }
///   public final int compareTo(Object o) { //: implements java.lang.Comparable
///      return compareTo((shortRef)o);      
///   }
   public final boolean eq(short v2) {  return compareTo(v2)==0; }
   public final boolean ne(short v2) {  return compareTo(v2)!=0; }
   public final boolean gt(short v2) {  return compareTo(v2)>0; }
   public final boolean ge(short v2) {  return compareTo(v2)>=0; }
   public final boolean lt(short v2) {  return compareTo(v2)<0; }
   public final boolean le(short v2) {  return compareTo(v2)<=0; }
   public final boolean eq(shortRef v2) {  return compareTo(v2)==0; }
   public final boolean ne(shortRef v2) {  return compareTo(v2)!=0; }
   public final boolean gt(shortRef v2) {  return compareTo(v2)>0; }
   public final boolean ge(shortRef v2) {  return compareTo(v2)>=0; }
   public final boolean lt(shortRef v2) {  return compareTo(v2)<0; }
   public final boolean le(shortRef v2) {  return compareTo(v2)<=0; }

   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}

