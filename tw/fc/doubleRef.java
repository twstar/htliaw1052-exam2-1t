package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//****************************************
public class doubleRef extends fNumRef // PrintableI, ScannableI,  
   implements DuplicableI<doubleRef>, SetableI<doubleRef>,  
              java.lang.Comparable<doubleRef>
{
   //public double _;    //:  before A30731
   public double v;    //:  encorage direct access

   //---------------------------------------- 
   public  doubleRef() {  v=0.0; }
   public  doubleRef(double x) {  v=x; }
   public  doubleRef(doubleRef D) {  v=D.v; }
///   public  doubleRef(Double X)    {  v=X.doubleValue(); }
///   public  doubleRef(String s) {  v=Double.valueOf(s).doubleValue();  }

   //---------------
   public boolean isInfinite() {  return Double.isInfinite(v); } //: 正負無限大
   public boolean isNaN() {  return Double.isNaN(v); }
   public final String toString() {  return (""+v); }
   public final boolean equals(doubleRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((doubleRef)d2); }
   public final boolean equals(double d2) {  return (v==d2); }
   public final int hashCode() {  
      final long t=Double.doubleToLongBits(v);
      return (int)(t^(t>>32));   //: as is recommended by Effective Jave 
   }

   public final doubleRef setBy(double d) { v=d;   return this;  }
///   public final doubleRef setBy(Double D) {  v=D.doubleValue(); return this; }
///   public final doubleRef setBy(String s) {  
///   //:  v=Double.parseDouble(s); // VJ not support
///        v=Double.valueOf(s).doubleValue();  return this;
///   }
  
//[-------- implements OperatableI  
   public final doubleRef duplicate() {  return new doubleRef(this);   }
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }

   public final doubleRef setBy(doubleRef src) {
      v=src.v;  return this;
   }

   //[ A50217 由 TxIStream 移至 doubleRef
   protected static double _parseFraction(
      TxIStream iS, intRef lastChar, booleanRef got
   ) throws IOException 
   {
      double result=0.0;    double weight=1.0;
      int _byte;   boolean gotDigit=got.v;
      while(true) {
         _byte=iS.read();
         if(_byte==TxIStream.EOF) break; //: 可能還是正常狀態
         final int value= iNumRef.toDigit(_byte, 10);
         if( value<0 ) {  
            //iS.unread(_byte);  //: A5.012.F
            if(_byte!=TxIStream.EOF) iS.unread((char)_byte);  //: A5.012.F
            break;  
         }
         gotDigit=true;
         got.v=true;
         result += ((weight/=10)*value) ;
      }
      got.v=(gotDigit);  lastChar.v=(_byte);   return result;
   }

   //[ A50217 由 TxIStream 移至 doubleRef
   public static double get_double_from(TxIStream iS) throws IOException {
      //: 原為MuDbl._scan(TxIStream iS)

      // 實數不受radix影響

      // 由Java2 spec 3.10.2 做文法整理:

      // FloatingPointLiteral:
      // 	Digits '.' Digits? ExponentPart? FloatTypeSuffix?
      // 	       '.' Digits  ExponentPart? FloatTypeSuffix?
      // 	Digits             ExponentPart  FloatTypeSuffix?
      // 	Digits             ExponentPart? FloatTypeSuffix
      // ExponentPart:         ('e'|'E') ('+'|'-')? Digits
      // FloatTypeSuffix:      ('f'|'F'|'d'|'D')

      // 不管suffix則簡化為
      // FloatingPointLiteral:
      // 	Digits '.' Digits? ExponentPart?
      // 	       '.' Digits  ExponentPart?
      // 	Digits             ExponentPart?

      if(iS.autoSkipWS) {  iS.skipWS(); }
      final int sign= iNumRef._optSign(iS);

      final intRef lastChar=new intRef(); ///// final MuInt lastChar=new MuInt();
      final booleanRef gotDigit=new booleanRef(false);///// final MuBln gotDigit=new MuBln(false);
      double result= iNumRef._parseDigits(iS, 10, lastChar, gotDigit);

      if( lastChar.v == ((int)'.') ) {   //[ process fractional part
         iS.expect('.');  lastChar.v='\0'; 
         result += _parseFraction(iS, lastChar, gotDigit);
      }
      if (!gotDigit.v) {
         throw new TxInputException("get "+lastChar.v+" when expect digit");
      }

      if(lastChar.v==( (int)'E' ) || lastChar.v==( (int)'e' ) ) {
         //[ process exponent part
         int expSign= iNumRef._optSign(iS);
         iS.expect(lastChar.v);
         gotDigit.setBy(false);  //:  reset
         int exp=(int)iNumRef._parseDigits(iS, 10, lastChar, gotDigit);
         if(!gotDigit.v) {
            throw new TxInputException("expect exponent digit");
         }
         result *= Math.pow(10. , expSign*exp);
      }
      //this.unread(lastChar.v);   //: b4 A5.012.F
      return sign*result;
   }

   public final void scanFrom(TxIStream iii) throws IOException {
    // 由getDouble做 iii.skipWS();
      // v=iii.get_double();  //: b4 A5.033.G    
      this.v= get_double_from(iii);
   }
//]-------- implements OperatableI  

///   public static final double parseDouble(String s) {
///      return Double.parseDouble(s.trim()); 
///   } 

   public final double add(double d2) { return v+d2; }
   public final double sub(double d2) { return v-d2; }
   public final double mul(double d2) { return v*d2; }
   public final double div(double d2) { 
   // if(d2==0) throw new ArithmeticException("in double.Div ");
                            //: 依JAVA之風俗當NaN處理
      return v/d2; 
   }
   public final double add(doubleRef d2) { return v+d2.v; }
   public final double sub(doubleRef d2) { return v-d2.v; }
   public final double mul(doubleRef d2) { return v*d2.v; }
   public final double div(doubleRef d2) { 
   // if(d2==0) throw new ArithmeticException("in double.Div ");
                            //: 依JAVA之風俗當NaN處理
      return v/d2.v; 
   }

   public final doubleRef addBy(double d2) { v+=d2; return this; }
   public final doubleRef subBy(double d2) { v-=d2; return this; }
   public final doubleRef mulBy(double d2) { v*=d2; return this; }
   public final doubleRef divBy(double d2) { 
   // if(d2==0) throw new ArithmeticException("in doubleRef.DivBy ");
                            //: 依JAVA之風俗當NaN處理
      v/=d2; return this;  
   }
   public final doubleRef addBy(doubleRef d2) { v+=d2.v; return this; }
   public final doubleRef subBy(doubleRef d2) { v-=d2.v; return this; }
   public final doubleRef mulBy(doubleRef d2) { v*=d2.v; return this; }
   public final doubleRef divBy(doubleRef d2) { v/=d2.v; return this; }

// public final doubleRef setByAdd(double x1, double x2) {
//    v=x1+x2;      return this;
// }
//]  用 setBy(x1-x2) 即可

// public final doubleRef setByAdd(doubleRef x1, doubleRef x2) {
//    v=x1+x2;      return this;
// }
//]  用 setBy(x1-x2) 即可

   public final doubleRef inc() {  ++v;  return this; } 
   public final doubleRef dec() {  --v;  return this; } 

   public final double neg() {   return -v;   }

   public final doubleRef negate() { 
      v=-v;    return this;
   }

   public final double inv() { 
   // if(v==0) throw new ArithmeticException("in double.Inv ");
                            //: 依JAVA之風俗當NaN處理
      return 1/v; 
   }

   public final doubleRef invert() { 
   // if(v==0) throw new ArithmeticException("in doubleRef.Invert");
                            //: 依JAVA之風俗當NaN處理
      v=1/v; 
      return this;
   }

   public final double abs() { return Math.abs(v); }
   public final doubleRef setByAbs(double x) {
      v= Math.abs(x);  return this;
   }

   public final double pow(int n) {  
      return Math.pow(v, n);  
   }

   public final doubleRef powBy(int n) {
      v=Math.pow(v,n);  return this;
   }
   public final doubleRef setByPow(double x, int n) {
      v=Math.pow(x,n);  return this;
   }

   public final int compareTo(double v2) {
      return Double.compare(v, v2);
   }
   public final int compareTo(doubleRef v2) { 
      return Double.compare(v, v2.v);
   }
//   public final int compareTo(Object o) { //: implements java.lang.Comparable
//      return compareTo((doubleRef)o);      
//   }

   public final boolean eq(double v2) {  return compareTo(v2)==0; }
   public final boolean ne(double v2) {  return compareTo(v2)!=0; }
   public final boolean gt(double v2) {  return compareTo(v2)>0; }
   public final boolean ge(double v2) {  return compareTo(v2)>=0; }
   public final boolean lt(double v2) {  return compareTo(v2)<0; }
   public final boolean le(double v2) {  return compareTo(v2)<=0; }
   public final boolean eq(doubleRef v2) {  return compareTo(v2.v)==0; }
   public final boolean ne(doubleRef v2) {  return compareTo(v2.v)!=0; }
   public final boolean gt(doubleRef v2) {  return compareTo(v2.v)>0; }
   public final boolean ge(doubleRef v2) {  return compareTo(v2.v)>=0; }
   public final boolean lt(doubleRef v2) {  return compareTo(v2.v)<0; }
   public final boolean le(doubleRef v2) {  return compareTo(v2.v)<=0; }
   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}