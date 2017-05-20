package tw.fc.algb ;
import tw.fc.*;

import java.io.IOException;

//**********    MuZp.java    ****************
//
//  interger modulo n
//

public class MuZp 
   implements ImuZp, SetableI<ImuZp>, ScannableI
{

   //[-----implement MutingGuardI   -------------------
   private boolean mutableFlag=true;
   public void immutalize() {  mutableFlag=false;  }
   public void assertMutable() {
      if(!mutableFlag) throw new ImuException();
   }
   //]-------------------------------------------------


   final int MODULO;
   int value;   

   //------------------------------------------------------  

// private MuZp() { }  //: 此generic class須設mod, 故禁用.

   public MuZp(int m, int x) {  
      if(m<2) throw new RuntimeException("illegal modulo: "+m);    
      MuZp.assertPrime(m);
      MODULO=m;   value=x; 
      reduce();  
   }

// private MuZp(int m) {  //:  防m誤為x, 故禁用
//    throw new RuntimeException("illegal call");
// }

   public MuZp(ImuZp src) { 
      MODULO=src.getModulo();   
      value=src.to_int();  
   }

   //------------------------------------------------------   

   public int getModulo() {  return MODULO; }
   public int to_int() {  reduce();  return value;  }

   public final String toString() { 
      return  String.valueOf(value) ;  
   }
   public boolean equals(ImuZp v2) { return (value==v2.to_int());  }
   public final boolean equals(Object d2) {  return equals((ImuZp)d2); }
   public final int hashCode() {  return value ;  }
   public MuZp duplicate() {  return new MuZp(this);   }

   //[-------- implements PrintableI  -----------------
   public final void printTo(TxOStream ooo) throws IOException {  
      ooo.p(value);  
   }
   public final void widthPrintTo(int w, TxOStream ooo) 
      throws IOException 
   {  
      ooo.wp(w,value); 
   }
   //]-------- implements PrintableI  -----------------   

   //[--------  implement ScannableI   -----------------   
   public final void scanFrom(TxIStream iii) throws IOException {
      this.assertMutable();
      if(iii.autoSkipWS) {  iii.skipWS();  } 
      this.value=iii.get_int(10);  //: 如同有理數不受radix影響
      reduce();
   }
//[ 無法先給MODULO
//   public static MuZp parseRn(String s) 
//      throws java.lang.NumberFormatException
//   {   } 
   //]--------  implement ScannableI   -----------------   


   //-----------------------------------------

   public final MuZp setBy(ImuZp src) {  
      this.assertMutable();
      this.checkModulo(src);
      this.value=src.to_int(); return this; 
   }
   //[ 要讓subclass override return type
   public MuZp setBy(int a) {  
      this.assertMutable();
      value=a; reduce(); return this; 
   }

/////   public final MuZp setBy(DuplicableI s) {
/////      this.assertMutable();
/////      final ImuZp src=(ImuZp)s;
/////      checkModulo(src);
/////      value=src.to_int();  
/////      return this; 
/////   }


   //------------------------------------------------------   

   protected void reduce() {   
      final int N=getModulo();
      if(value>=0) {  value %= N ; }
      else {  
         final int neg=(-value)%N;
         value= (neg==0)? 0: N-neg;  
//         value=N-((-value)%N);
//         if(value==N) {  value=0; } 
      }
   }

   //--------

   public final ImuZp add(ImuZp v2) {
      this.checkModulo(v2);  
      return new MuZp(MODULO, value+v2.to_int());    
   }
   public final ImuZp sub(ImuZp v2) {  
      this.checkModulo(v2);
      return new MuZp(MODULO, value-v2.to_int());    
   }
   public final ImuZp mul(ImuZp v2) {  
      this.checkModulo(v2);
      return  new MuZp(MODULO, value*v2.to_int());   
   }
   public final ImuZp div(ImuZp v2) {  
      return  this.mul(v2.inv());   
   }
      
   public final MuZp addBy(ImuZp v2) {  
      this.assertMutable();
      this.checkModulo(v2);
      value+= v2.to_int();   reduce();    return this; 
   }
   public final MuZp subBy(ImuZp v2) {  
      this.assertMutable();
      this.checkModulo(v2);
      value-= v2.to_int();   reduce();    return this;   }
   public final MuZp mulBy(ImuZp v2) {  
      this.assertMutable();
      this.checkModulo(v2);
      value*= v2.to_int();   reduce();    return this; 
   }
   public final MuZp divBy(ImuZp v2) {  
      this.assertMutable();
      this.checkModulo(v2);
      if(v2.to_int()==0) {  
         throw new ArithmeticException("zero divider");  
      }   
      final longRef M=new longRef(), N=new longRef();
      ImuRtn.gcd_coef(v2.to_int(), getModulo(), M, N);
         //: so that  1 = M * v2.value + N * mod
      value *= M._;  reduce();  return this;
   }
   public final MuZp addBy(int v2) {  
      this.assertMutable();
      value += v2;   reduce();    return this; 
   }
   public final MuZp subBy(int v2) {  
      this.assertMutable();
      value -= v2;   reduce();    return this; 
   }
   public final MuZp mulBy(int v2) {  
      this.assertMutable();
      value *= v2;   reduce();  return this; 
   }
   public final MuZp divBy(int v2) {  
      this.assertMutable();
      return this.divBy(new MuZp(MODULO, v2)); 
   }
   public final MuZp inc() {  
      this.assertMutable();
      value++;   reduce();    return this; 
   }
   public final MuZp dec() {  
      this.assertMutable();
      value--;   reduce();    return this; 
   }

   public final MuZp setByAdd(ImuZp x1, ImuZp x2) {
      this.assertMutable();
      this.checkModulo(x1);  this.checkModulo(x2);
      value= x1.to_int() + x2.to_int() ;   
      reduce();  return this;
   }
   public final MuZp setBySub(ImuZp x1, ImuZp x2) {
      this.assertMutable();
      value= x1.to_int() - x2.to_int() ;   reduce();  return this;
   }
   public final MuZp setByMul(ImuZp x1, ImuZp x2) {
      this.assertMutable();
      value= x1.to_int() * x2.to_int();   
      reduce();  return this;
   }
   public final MuZp setByDiv(ImuZp x1, ImuZp x2) {
      this.assertMutable();
      return setByMul(x1,x2.inv());  
   }

   //[ 要讓subclass override return type, 不可final
   public ImuZp neg() {  return new MuZp(MODULO, -value);   }
   public MuZp negate() {  
      this.assertMutable();
      value=-value;  reduce();  return this;   
   }
   public final MuZp setByNeg(ImuZp x) {
      this.assertMutable();
      setBy(x);  negate();
      return this;
   }

   public final ImuZp inv() {   
      if(this.value==0) {  
         throw new ArithmeticException("zero divider");  
      }   
      final longRef M=new longRef(), N=new longRef();
      ImuRtn.gcd_coef(this.value, getModulo(), M, N);
         //: so that  1 = M * this.value + N * mod
      return new MuZp(MODULO, (int)M._);  
   }

   public final MuZp invert() {   
   // return setBy(this.inv());   
      this.assertMutable();
      if(this.value==0) {  
         throw new ArithmeticException("zero divider");  
      }   
      final longRef M=new longRef(), N=new longRef();
      ImuRtn.gcd_coef(this.value, getModulo(), M, N);
         //: so that  1 = M * this.value + N * mod
      value=(int)M._;  reduce();  return this;
   }
   public final MuZp setByInv(ImuZp x) {
      this.assertMutable();
      return setBy(x.inv());
   }

   public final ImuZp pow(int n) {    
      final MuZp t=new MuZp(MODULO, 1) ;
      if(n>=0) {
         for(int i=1; i<=n; ++i) t.mulBy(this) ;
      }
      else {
         final MuZp b=new MuZp(this.inv()) ;
         for(int i=1; i<=(-n); ++i) t.mulBy(b) ;
      }
      return t ;
   }
   public final MuZp powBy(int n) {
      this.assertMutable();
      setBy(this.pow(n));  return this;
   }
   public final MuZp setByPow(ImuZp x, int n) {
      this.assertMutable();
      setBy(x.pow(n));  return this;
   }
   //--------------------------------



   public final void checkModulo(ImuZp v2) {
      if(this.MODULO!=v2.getModulo()) {
         throw new RuntimeException("inconsistent modulo");
      }
   }

   public final boolean eq(ImuZp v2) {  
      checkModulo(v2);
      return value==v2.to_int(); 
   }
   public final boolean ne(ImuZp v2) {  return value!=v2.to_int(); }

   public final boolean isZero() {  return (value==0);  }
   public final boolean notZero() {  return (value!=0);  }
   public final boolean eq0() {  return (value==0);  }
   public final boolean ne0() {  return (value!=0);  }

   //[==============  static part  ===================

   public static boolean isPrime(long v) {  
      if(v<=1) {  return false;    }
      //[ 設 V=M*N, M<=N, 則 V>=M*M (, sqrt(V)>=M )
      long f=2;  //: f是可能之因數, 除2外皆是奇數  
//    Std.cout.pc(v).pc(f).pn(v%f);
      if(f*f<=v && v%f==0) return false; 
      for(f=3; f*f<=v ; f+=2) { //: 只試奇數
         if(v%f==0) return false;
      } 
      return true;
   }

   public static void assertPrime(int m) {
      if(isPrime(m)) return;
      throw new RuntimeException("illegal modulo: "+m);    
   }



   public static void pAddTable(int mod) {
      for(int i=0; i<mod; i++) {
         for(int j=0; j<mod; j++) {
            Std.cout.pt((i+j)%mod); 
         } 
         Std.cout.pn();
      }
   }
   public static void pMulTable(int mod) {
      for(int i=0; i<mod; i++) {
         for(int j=0; j<mod; j++) {
            Std.cout.pt((i*j)%mod); 
         } 
         Std.cout.pn();
      }
   }
   public static void pNegTable(int mod) {
      Std.cout.pt(0); 
      for(int i=1; i<mod; i++) {
         Std.cout.pt(mod-i); 
      } 
   }
   public static void pInvTable(int mod) {

//>>>>>>

   }

   //]==============  static part  ===================

}
