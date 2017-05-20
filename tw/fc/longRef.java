package tw.fc ;
import java.io.IOException;
import java.io.EOFException;

//*******************************************
public class longRef 
   extends iNumRef // PrintableI, ScannableI,
   implements DuplicableI<longRef>, SetableI<longRef>, 
              java.lang.Comparable<longRef>
{
   //public long _;  //: before A30731
   public long v;  //:  encorage direct access

   //-----------------------------------
   public  longRef() {  v=0; }
   public  longRef(long i) {  v=i; }
   public  longRef(longRef src) {  v=src.v; }

   //-----------------------------------
   @Override public final String  toString(){ return String.valueOf(v); }
   @Override public final String  toHexString(){ return Long.toHexString(v); }
   @Override public final String  toOctalString(){ return Long.toOctalString(v); }

   public final boolean equals(long d2) {  return (v==d2); }
   public final boolean equals(longRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((longRef)d2); }
   public final int hashCode() {  return (int)(v^(v>>32));  }

   public final longRef setBy(long i) { v=i;   return this;  }

   //[-------- implements DuplicableI
   public final longRef duplicate() {  return new longRef(this);   }
   //]-------- implements DuplicableI
   //[-------- implements PrintableI
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }
   //]-------- implements PrintableI

//I protected static final long _parseDigits(
//I    TxIStream iS, int radix, intRef lastByte, booleanRef got
//I ) throws IOException  {

//I protected static int _optSign(TxIStream iS) throws IOException {

   //[ 自寫緣由:
   //[  The jdk-method Integer.parseInt 不能在 hybrid data 運作
   //[
   //[ 920708重整EOF之邏輯: 不catch IOException
   //[ 930130 加入radix能力, 並再整理邏輯
   //[ 930709 整理並抽出副程式.
   //[ A50217 由 TxIStream 移至 longRef
   public static long get_long_from(TxIStream iS, int radix) throws IOException {
  //D System.out.println("get_long(int)...");

      if(iS.autoSkipWS) {  iS.skipWS(); }
      int sign=_optSign(iS);

      final intRef lastChar=new intRef(); 
      final booleanRef gotDigit=new booleanRef(false);
      long result= _parseDigits(iS, radix, lastChar, gotDigit);
      // this.unread(lastChar.v);  //: b4 A5.012.F
      if (!gotDigit.v) {
         //: 開始要求數字後, 若還沒讀到數字就遇到EOF或其它, 就是格式錯誤
         throw new tw.fc.TxInputException(
            (lastChar.v==TxIStream.EOF)?
             "EOF while expecting digit" :
             ("get \'"+ (char)lastChar.v +"\' while expecting integer")
         );
      }
  //D System.out.println("get_long(int) OK");
      return ( sign * result );
   }

   public static long get_long_from(TxIStream iS) throws IOException {
      return get_long_from(iS, 10); 
   }

   //[-------- implements DuplicableI
//I   public final DuplicableI duplicate() {  return new longRef(this);   }
   public final longRef setBy(longRef s) {
      //////final longRef src=(longRef)s;  //: dynamic cast
      //////v=src.v;  return this;
      v = s.v;  return this;
   }
   public final void scanFrom(TxIStream iS) throws IOException {
      //  Note:  The standard method Integer.parseInt
      //         can't work on a input stream with hybrid data.
      // iS.skipWS();   //: 由getInt負責
      //v=iS.get_long(iS.getRadix());  //: b4 A50217
      this.v= longRef.get_long_from(iS, iS.getRadix());
   }
   //]-------- implements DuplicableI

   //-----------------------------

   public final long add(long d2) { return (v+d2); }
   public final long sub(long d2) { return (v-d2); }
   public final long mul(long d2) { return (v*d2); }
   public final long div(long d2) {
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (v/d2);
   }
   public final long mod(long d2) {
      if(d2==0) throw new ArithmeticException("divided by zero ");
      return (v%d2);
   }

   public final long add(longRef d2) { return (v+d2.v); }
   public final long sub(longRef d2) { return (v-d2.v); }
   public final long mul(longRef d2) { return (v*d2.v); }
   public final long div(longRef d2) {
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (v/d2.v);
   }
   public final long mod(longRef d2) {
      if(d2.v==0) throw new ArithmeticException("divided by zero ");
      return (v%d2.v);
   }

   public final longRef addBy(long d2) { v+=d2; return this; }
   public final longRef subBy(long d2) { v-=d2; return this; }
   public final longRef mulBy(long d2) { v*=d2; return this; }
   public final longRef divBy(long d2) {
      if(d2==0) throw new ArithmeticException("divided by zero");
      v/=d2; return this;
   }
   public final longRef modBy(long d2) {
      if(d2==0) throw new ArithmeticException("divided by zero");
      v%=d2; return this;
   }
   public final longRef addBy(longRef d2) { v+=d2.v; return this; }
   public final longRef subBy(longRef d2) { v-=d2.v; return this; }
   public final longRef mulBy(longRef d2) { v*=d2.v; return this; }
   public final longRef divBy(longRef d2) {
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v/=d2.v; return this;
   }
   public final longRef modBy(longRef d2) {
      if(d2.v==0) throw  new ArithmeticException("divided by zero ");
      v %=d2.v; return this;
   }

// public final longRef setByAdd(long x1, long x2) {
//    v= x1+x2;  return this;
// } //:  用 setBy(x1-x2) 即可

//   public final longRef setByAdd(longRef x1, longRef x2) {
//      v= x1.v+x2.v;  return this;
//   } //:  用 setBy(x1.v-x2.v) 即可

   public final longRef inc() {  ++v;  return this; }
   public final longRef dec() {  --v;  return this; }

   public final long neg() { return -v; }
   public final longRef negate() { v=-v; return this; }

   public final long abs() { return Math.abs(v); }
   public final longRef setByAbs(long x) {
      v= Math.abs(x);  return this;
   }
   public final longRef setByAbs(longRef x) {
      v= Math.abs(x.v);  return this;
   }

   //[ Math.pow不合適:  static double pow(double a, double b)
   public final long pow(int n) {
      if(n<0) {  throw new ArithmeticException("negative power"); }
//    if(n==0 && v==0) throw new ArithmeticException("0 power of 0");
      long ans=1;
      for(int i=0; i<n; i++) {  ans*=v;  }
      return ans;
   }
   public final longRef powBy(int n) {
      v=this.pow(n);  return this;
   }
   public final longRef setByPow(long x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x;  }
      return this;
   }
   public final longRef setByPow(longRef x, int n) {
      if(n<0) throw new  ArithmeticException("negative power for integer");
      v=1;
      for(int i=0; i<n; i++) {  v*=x.v;  }
      return this;
   }

   //---------------------------
   public final int compareTo(long v2) {
   //[ 沒有Long.compare(long,long)
      final long d=v-v2;
      if(d>0) return 1;
      else if(d<0) return -1;
      else return 0;
   }
   public final int compareTo(longRef v2) {  return compareTo(v2.v); }
   public final boolean eq(long v2) {  return compareTo(v2)==0; }
   public final boolean ne(long v2) {  return compareTo(v2)!=0; }
   public final boolean gt(long v2) {  return compareTo(v2)>0; }
   public final boolean ge(long v2) {  return compareTo(v2)>=0; }
   public final boolean lt(long v2) {  return compareTo(v2)<0; }
   public final boolean le(long v2) {  return compareTo(v2)<=0; }
   public final boolean eq(longRef v2) {  return compareTo(v2)==0; }
   public final boolean ne(longRef v2) {  return compareTo(v2)!=0; }
   public final boolean gt(longRef v2) {  return compareTo(v2)>0; }
   public final boolean ge(longRef v2) {  return compareTo(v2)>=0; }
   public final boolean lt(longRef v2) {  return compareTo(v2)<0; }
   public final boolean le(longRef v2) {  return compareTo(v2)<=0; }

   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}

