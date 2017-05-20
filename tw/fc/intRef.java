package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;
import tw.fc.re.*;

//*******************************************
public class intRef 
   extends iNumRef  // PrintableI, ScannableI,
   implements DuplicableI<intRef>,  SetableI<intRef>,  
              java.lang.Comparable<intRef>
{
   //public int _;  //:  before A30731
   public int v;  //:  encorage direct access

   //-----------------------------------
   public  intRef() {  v=0; }
   public  intRef(int i) {  v=i; } 
   public  intRef(intRef src) {  v=src.v; }          

   //-----------------------------------
   @Override public final String  toString() {  return String.valueOf(v); }
   @Override public final String  toHexString(){ return Integer.toHexString(v); }
   @Override public final String  toOctalString(){ return Integer.toOctalString(v); }

   public final boolean equals(int d2) {  return (v==d2); }
   public final boolean equals(intRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((intRef)d2); }
   public final int hashCode() {  return v;  }

   public final intRef setBy(int i) { v=i;   return this;  }
   public final intRef setBy(intRef src) {
      v=src.v;  return this;
   }

   //[-------- implements DuplicableI  
   @Implement( DuplicableI.class )
   public final intRef duplicate() {  return new intRef(this);   }
   //]-------- implements DuplicableI  

   //[-------- implements PrintableI  
   @Implement( PrintableI.class )
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   @Implement( WidthPrintableI.class )
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }
   //]-------- implements PrintableI  

   public static int get_int_from(TxIStream iS, int radix) throws IOException {
      return (int)longRef.get_long_from(iS, radix);
   }
   public static int get_int_from(TxIStream iS) throws IOException {
      return (int)longRef.get_long_from(iS, 10);
   }

   //[-------- implements ScannableI  
   public final void scanFrom(TxIStream iS) throws IOException {
      //  Note:  The standard method Integer.parseInt  
      //         can't work on a input stream with hybrid data.
      // iS.skipWS();   //: 由getInt負責
      //v=iS.get_int(iS.getRadix());  
      this.v= intRef.get_int_from(iS, iS.getRadix());
   }
   //]-------- implements ScannableI  

//----------------------------- 

   public final int add(int d2) { return (v+d2); }
   public final int sub(int d2) { return (v-d2); }
   public final int mul(int d2) { return (v*d2); }
   public final int div(int d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (v/d2); 
   }
   public final int mod(int d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (v%d2); 
   }

   public final int add(intRef d2) { return (v+d2.v); }
   public final int sub(intRef d2) { return (v-d2.v); }
   public final int mul(intRef d2) { return (v*d2.v); }
   public final int div(intRef d2) { 
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (v/d2.v); 
   }
   public final int mod(intRef d2) { 
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (v%d2.v); 
   }

   public final intRef addBy(int d2) { v+=d2; return this; }
   public final intRef subBy(int d2) { v-=d2; return this; }
   public final intRef mulBy(int d2) { v*=d2; return this; }
   public final intRef divBy(int d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero");
      v/=d2; return this; 
   }
   public final intRef modBy(int d2) { 
      if(d2==0) throw new ArithmeticException("divided by zero");
      v%=d2; return this; 
   }
   public final intRef addBy(intRef d2) { v+=d2.v; return this; }
   public final intRef subBy(intRef d2) { v-=d2.v; return this; }
   public final intRef mulBy(intRef d2) { v*=d2.v; return this; }
   public final intRef divBy(intRef d2) { 
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v/=d2.v; return this; 
   }
   public final intRef modBy(intRef d2) { 
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v %=d2.v; return this; 
   }

// public final intRef setByAdd(int x1, int x2) { 
//    v= x1+x2;  return this; 
// }
//]  用 setBy(x1-x2) 即可

//   public final intRef setByAdd(intRef x1, intRef x2) { 
//      v= x1.v+x2.v;  return this; 
//   }
//]  用 setBy(x1.v-x2.v) 即可


   public final intRef inc() {  ++v;  return this; } 
   public final intRef dec() {  --v;  return this; } 

   public final int neg() { return -v; }
   public final intRef negate() { v=-v; return this; }

   public final int abs() { return Math.abs(v); }
   public final intRef setByAbs(int x) {
      v= Math.abs(x);  return this;
   }
   public final intRef setByAbs(intRef x) {
      v= Math.abs(x.v);  return this;
   }

   //[ Math.pow不合適
   //[  static double pow(double a, double b) 
   public final int pow(int n) {    
      if(n<0) {  throw new ArithmeticException("negative power"); }
//    if(n==0 && v==0) throw new ArithmeticException("0 power of 0");
      int ans=1;
      for(int i=0; i<n; i++) {  ans*=v;  }
      return ans;
   }
   public final intRef powBy(int n) {
      v=this.pow(n);  return this;
   }
   public final intRef setByPow(int x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x;  }
      return this;
   }
   public final intRef setByPow(intRef x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x.v;  }
      return this;
   }

   //---------------------------
   public final int compareTo(int v2) {
   //[ 沒有Integer.compare(int,int)
      final int d=v-v2;
      if(d>0) return 1;
      else if(d<0) return -1;
      else return 0;
   }
   public final int compareTo(intRef v2) {  return compareTo(v2.v); }
///   public final int compareTo(Object o) { //: implements java.lang.Comparable
   public final boolean eq(int v2) {  return compareTo(v2)==0; }
   public final boolean ne(int v2) {  return compareTo(v2)!=0; }
   public final boolean gt(int v2) {  return compareTo(v2)>0; }
   public final boolean ge(int v2) {  return compareTo(v2)>=0; }
   public final boolean lt(int v2) {  return compareTo(v2)<0; }
   public final boolean le(int v2) {  return compareTo(v2)<=0; }
   public final boolean eq(intRef v2) {  return compareTo(v2)==0; }
   public final boolean ne(intRef v2) {  return compareTo(v2)!=0; }
   public final boolean gt(intRef v2) {  return compareTo(v2)>0; }
   public final boolean ge(intRef v2) {  return compareTo(v2)>=0; }
   public final boolean lt(intRef v2) {  return compareTo(v2)<0; }
   public final boolean le(intRef v2) {  return compareTo(v2)<=0; }

   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}

