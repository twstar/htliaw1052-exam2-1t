package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//**********************************************
public class byteRef 
   extends iNumRef // PrintableI, ScannableI,
   implements DuplicableI<byteRef>, SetableI<byteRef>,  
              java.lang.Comparable<byteRef>
{
   //public byte _;  //:  before A30731
   public byte v;  //:  encorage direct access

   //-----------------------------------
   public  byteRef() {  v=0; }
   public  byteRef(byte i) {  v=i; } 
   public  byteRef(byteRef src) {  v=src.v; }          
///   public  byteRef(Integer I) {  v=I.intValue(); }    //: 不太有用
///   public  byteRef(String s) { v=Integer.parseInt(s);  } //: 不太有用

   //-----------------------------------
///   public final boolean to_boolean() {  return (v!=0);  }
///   public final byte to_byte() {  return ((byte)v);  }
///   public final byte to_short() {  return ((byte)v);  }
///   public final char to_char() {  return ((char)v);  }
   @Override public final String  toString() {  return String.valueOf(v); }
   @Override public final String  toHexString() {  return Integer.toHexString(v); }
   @Override public final String  toOctalString(){ return Integer.toOctalString(v); }

   public final boolean equals(byte d2) {  return (v==d2); }
   public final boolean equals(byteRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((byteRef)d2); }
   public final int hashCode() {  return v;  }

   public final byteRef setBy(byte i) { v=i;   return this;  }
///   public final byteRef setBy(Integer I) {  v=I.intValue(); return this; }
///   public final byteRef setBy(String s) {  
///      v=java.lang.Integer.parseInt(s); return this;   
///   }

   //[-------- implements DuplicableI  
   public final byteRef duplicate() {  return new byteRef(this);   }
   //]-------- implements DuplicableI  
   //[-------- implements PrintableI  
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }
   //]-------- implements PrintableI  

   public static byte get_byte_from(TxIStream iS, int radix) throws IOException {
      return (byte)longRef.get_long_from(iS, radix);
   }
   public static int get_byte_from(TxIStream iS) throws IOException {
      return (byte)longRef.get_long_from(iS, 10);
   }

   //[-------- implements DuplicableI  
//I   public final DuplicableI duplicate() {  return new byteRef(this);   }
/////   public final SetableI setBy(DuplicableI s) {
/////      final byteRef src=(byteRef)s;  //: dynamic cast
/////      v=src.v;  return this;
/////   }
   public final byteRef setBy(byteRef src) {
      v=src.v;  return this;
   }
   public final void scanFrom(TxIStream iS) throws IOException {
      //  Note:  The standard method Integer.parseInt  
      //         can't work on a input stream with hybrid data.
      // iS.skipWS();   //: 由getInt負責
      //v=iS.get_byte(iii.getRadix());  
      this.v= byteRef.get_byte_from(iS, iS.getRadix());
   }
   //]-------- implements DuplicableI  

//----------------------------- 


   public final byte add(byte d2) { return (byte)(v+d2); }
   public final byte sub(byte d2) { return (byte)(v-d2); }
   public final byte mul(byte d2) { return (byte)(v*d2); }
   public final byte div(byte d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (byte)(v/d2); 
   }
   public final byte mod(byte d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (byte)(v%d2); 
   }
   public final byte add(byteRef d2) { return (byte)(v+d2.v); }
   public final byte sub(byteRef d2) { return (byte)(v-d2.v); }
   public final byte mul(byteRef d2) { return (byte)(v*d2.v); }
   public final byte div(byteRef d2) { 
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (byte)(v/d2.v); 
   }
   public final byte mod(byteRef d2) { 
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (byte)(v%d2.v); 
   }

   public final byteRef addBy(byte d2) { v+=d2; return this; }
   public final byteRef subBy(byte d2) { v-=d2; return this; }
   public final byteRef mulBy(byte d2) { v*=d2; return this; }
   public final byteRef divBy(byte d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero");
      v/=d2; return this; 
   }
   public final byteRef modBy(byte d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero");
      v%=d2; return this; 
   }
   public final byteRef addBy(byteRef d2) { v+=d2.v; return this; }
   public final byteRef subBy(byteRef d2) { v-=d2.v; return this; }
   public final byteRef mulBy(byteRef d2) { v*=d2.v; return this; }
   public final byteRef divBy(byteRef d2) { 
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v/=d2.v; return this; 
   }
   public final byteRef modBy(byteRef d2) { 
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v %=d2.v; return this; 
   }

// public final byteRef setByAdd(byte x1, byte x2) { 
//    v= x1+x2;  return this; 
// }
//]  用 setBy(x1-x2) 即可

//   public final byteRef setByAdd(byteRef x1, byteRef x2) { 
//      v= x1.v+x2.v;  return this; 
//   }
//]  用 setBy(x1.v-x2.v) 即可


   public final byteRef inc() {  ++v;  return this; } 
   public final byteRef dec() {  --v;  return this; } 

   public final byte neg() { return (byte)-v; }
   public final byteRef negate() { v=(byte)-v; return this; }

   public final byte abs() { return (byte)Math.abs(v); }
   public final byteRef setByAbs(byte x) {
      v= (byte)Math.abs(x);  return this;
   }
   public final byteRef setByAbs(byteRef x) {
      v= (byte)Math.abs(x.v);  return this;
   }

   //[ Math.pow不合適
   //[  static double pow(double a, double b) 
   public final byte pow(int n) {    
      if(n<0) {  throw new ArithmeticException("negative power"); }
//    if(n==0 && v==0) throw new ArithmeticException("0 power of 0");
      byte ans=1;
      for(int i=0; i<n; i++) {  ans*=v;  }
      return ans;
   }
   public final byteRef powBy(int n) {
      v=this.pow(n);  return this;
   }
   public final byteRef setByPow(byte x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x;  }
      return this;
   }
   public final byteRef setByPow(byteRef x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x.v;  }
      return this;
   }

   //---------------------------
   public final int compareTo(byte v2) {
   //[ 沒有Long.compare(byte,byte)
      final int d=v-v2;
      if(d>0) return 1;
      else if(d<0) return -1;
      else return 0;
   }
   public final int compareTo(byteRef v2) {  return compareTo(v2.v); }
///   public final int compareTo(Object o) { //: implements java.lang.Comparable
///      return compareTo((byteRef)o);      
///   }
   public final boolean eq(byte v2) {  return compareTo(v2)==0; }
   public final boolean ne(byte v2) {  return compareTo(v2)!=0; }
   public final boolean gt(byte v2) {  return compareTo(v2)>0; }
   public final boolean ge(byte v2) {  return compareTo(v2)>=0; }
   public final boolean lt(byte v2) {  return compareTo(v2)<0; }
   public final boolean le(byte v2) {  return compareTo(v2)<=0; }
   public final boolean eq(byteRef v2) {  return compareTo(v2)==0; }
   public final boolean ne(byteRef v2) {  return compareTo(v2)!=0; }
   public final boolean gt(byteRef v2) {  return compareTo(v2)>0; }
   public final boolean ge(byteRef v2) {  return compareTo(v2)>=0; }
   public final boolean lt(byteRef v2) {  return compareTo(v2)<0; }
   public final boolean le(byteRef v2) {  return compareTo(v2)<=0; }

   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}

