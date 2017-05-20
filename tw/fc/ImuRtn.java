package tw.fc ;

import java.io.IOException;
//import java.io.EOFException;

//**********    ImuRtn.java    ****************
//
//  immutable rational number, 
//  a demostration class for TwTX
//

public class ImuRtn
   implements DuplicableI<MuRtn>, 
              WidthPrintableI, // PrintableI, 
              java.lang.Comparable<ImuRtn>
{
   //[ ---------  static part -------------------------

   //[ 測試實例:  gcd(4369L,5911L)=257
   public static final long gcd(long p, long q) {
      long r;
      if(p<0) p=(-p) ;  if(q<0) q=(-q) ;
      if(q==0L) return(p) ;
      while(true) {
          r= p%q ;
          if(r==0L) break ;
          p= q  ;  q= r ;
      }
      return q ;
   }

   public static final long gcd_trace(long p, long q) {
      //: 印出求gcd的過程, 教學用或驗算用.
      long r;
      if(p<0) p=(-p) ;  if(q<0) q=(-q) ;
      if(q==0L) return(p) ;
      while(true) {
          System.out.println(p+","+q+" (quotient="+p/q+")");
          r= p%q ;
          if(r==0L) break ;
          p= q  ;  q= r ;
      }
      return q ;
   }

   public static final long gcd_coef(long a, long b, longRef M, longRef N){
   // find M, N so that gcd(a,b)=M*a+N*b,
   // and return gcd(a,b)
      long pD,pM,pN;    long qD,qM,qN;    long rD,rM,rN;
      if(a>=0) { pD=a; pM=1L; pN=0L; }
      else { pD=-a; pM=-1L; pN=0L; }  //: pD= pM * a + pN * b
      if(b>=0) { qD=b ; qM=0L; qN=1L; }
      else {  qD=-b; qM=0L; qN=-1L; } //: qD= qM * a + qN * b
      if(qD==0L) {
          M.v=pM ; N.v=pN ; return(pD) ;
      }
      long quotient;
      while(true) {
          quotient= pD / qD ;
          rD = pD - quotient * qD ;
            //: 這行其實是 rD=pD%qD; 但特別寫成與rM,rN的運算平行.
          if(rD==0L) break ;
          rM = pM - quotient * qM ;    rN = pN - quotient * qN ;
          pD=qD; pM=qM; pN=qN;
          qD=rD; qM=rM; qN=rN;
      }
      M.v=qM; N.v=qN; return(qD) ;
   }


   //] ------------  static part  ---------------------


   long a, b;

   //------------------------------------------------------
   public  ImuRtn() {  a=0L; b=1L; }
   public  ImuRtn(int above) {  a=above; b=1L; }
   public  ImuRtn(int above, int below) {
      if(below>0) {  a=above; b=below;  reduce();  }
      else if(below<0) {  a=-above; b=-below;  reduce();  }
      else throw new ArithmeticException("ImuRtn.ctor : zero denominator");
   }
   public  ImuRtn(long above) {  a=above; b=1L; }
   public  ImuRtn(long above, long below) {
      if(below>0) {  a=above; b=below;  reduce();  }
      else if(below<0) {  a=-above; b=-below;  reduce();  }
      else throw new ArithmeticException("ImuRtn.ctor : zero denominator");
   }

   public  ImuRtn(ImuRtn src) {  a=src.a;  b=src.b;  }
   public  ImuRtn(Byte X)     {  a=X.byteValue(); b=1L;  }
   public  ImuRtn(Short X)    {  a=X.shortValue(); b=1L; }
   public  ImuRtn(Character X){  a=X.charValue();  b=1L; }
   public  ImuRtn(Integer X)  {  a=X.intValue();   b=1L; }
   public  ImuRtn(Long X)     {  a=X.longValue();  b=1L; }

   //------------------------------------------------------

   public final long above() { return a; }
   public final long below() { return b; }

   public int to_int() {
      return ((int)Math.round(a/(double)b));
   }
   public long to_long() {
      return (Math.round(a/(double)b));
   }

   public final double to_double() {  return (double)a/(double)b;  }

   @Override
   public final String toString() { //: 不受radix影響
      if(b==1L) return Long.toString(a);
      return  Long.toString(a) + "/" + Long.toString(b);
   }
   public boolean equals(ImuRtn v2) { return (a*v2.b == b*v2.a);  }
   public final boolean equals(Object d2) {  return equals((ImuRtn)d2); }

   @Override
   public final int hashCode() {
      return  37*( 37*17+(int)(a^(a>>32)) ) + (int)(b^(b>>32));
   }

   final ImuRtn reduce() {
      long g= gcd(a,b) ;
      if(b>0) { a/=g; b/=g; } else { a/=(-g); b/=(-g) ; }
      return this;
   }

   //[--------  implement DuplicableI   ---------
   public final MuRtn duplicate() {  return new MuRtn(this);   }
   //]--------  implement DuplicableI   ---------

   //[-------- implements WidthPrintableI
   @Override
   public final void printTo(TxOStream oS) throws IOException {
   // oS.p(this.toString());   //: 不受radix影響
      oS.p(Long.toString(a));  //: 不受radix影響
      if(b!=1L) {
         // oS.p("/").p(Long.toString(a)); //: bug b4 A31210
         oS.p("/").p(Long.toString(b)); //: 不受radix影響
      } 
   }
   @Override
   public final void widthPrintTo(int w, TxOStream oS) throws IOException {
      oS.wp(w,this.toString());
   }
   //]-------- implements WidthPrintableI

   public final ImuRtn add(ImuRtn v2) {
      return new ImuRtn(a*v2.b+b*v2.a, b*v2.b);
   }
   public final ImuRtn sub(ImuRtn v2) {
      return new ImuRtn(a*v2.b-b*v2.a, b*v2.b);
   }
   public final ImuRtn mul(ImuRtn v2) {
      return  new ImuRtn(a*v2.a, b*v2.b);
   }
   public final ImuRtn div(ImuRtn v2) {
      if(v2.a==0) {  throw new ArithmeticException("zero divider");  }
      return  new ImuRtn(a*v2.b, b*v2.a);
   }

   public final ImuRtn add(long v2) {
      return new ImuRtn(a+b*v2, b);
   }
   public final ImuRtn sub(long v2) {
      return new ImuRtn(a-b*v2, b);
   }
   public final ImuRtn mul(long v2) {
      return  new ImuRtn(a*v2, b);
   }
   public final ImuRtn div(long v2) {
      if(v2==0) {  throw new ArithmeticException("zero divider");  }
      return  new ImuRtn(a, b*v2);
   }

   public final ImuRtn add(int v2) {
      return new ImuRtn(a+b*v2, b);
   }
   public final ImuRtn sub(int v2) {
      return new ImuRtn(a-b*v2, b);
   }
   public final ImuRtn mul(int v2) {
      return  new ImuRtn(a*v2, b);
   }
   public final ImuRtn div(int v2) {
      if(v2==0) {
         throw new ArithmeticException("zero divider");
      }
      return  new ImuRtn(a, b*v2);
   }

   public final ImuRtn neg() {   return new ImuRtn(-a, b);   }

   public final ImuRtn inv() {
      final long t=this.a ;
      if(t==0L) {
          throw new ArithmeticException("ImuRtn.inv on zero") ;
      }
      if(t>0L) return( new ImuRtn(this.b, t) );
      else return(  new ImuRtn(-this.b,-t) );
   }

   public final ImuRtn abs() {
      final ImuRtn t=new ImuRtn(this);
      if(t.a<0) {  t.a=-t.a;  }
      return t;
   }

   public final ImuRtn pow(int n) {
      final MuRtn t=new MuRtn(1L) ;
      if(n>=0) {
         for(int i=1; i<=n; ++i) {  t.mulBy(this);  }
            //: 可改為對數時間, 但在long的承載度中無用
      }
      else {
         final ImuRtn b=this.inv() ;
         for(int i=1; i<=(-n); ++i) {  t.mulBy(b);  }
      }
      return t ;
   }

   //--------------------------------

   @Override 
   public final int compareTo(ImuRtn v2) {
      final long d=a*v2.b-b*v2.a;
      if(d>0) return 1;
      else if(d<0) return -1;
      else return 0;
   }
   //public final int compareTo(Object o) { //: implements java.lang.Comparable
   //   return compareTo((ImuRtn)o);
   //}
   public final boolean eq(ImuRtn v2) {  return compareTo(v2)==0; }
   public final boolean ne(ImuRtn v2) {  return compareTo(v2)!=0; }
   public final boolean gt(ImuRtn v2) {  return compareTo(v2)>0; }
   public final boolean ge(ImuRtn v2) {  return compareTo(v2)>=0; }
   public final boolean lt(ImuRtn v2) {  return compareTo(v2)<0; }
   public final boolean le(ImuRtn v2) {  return compareTo(v2)<=0; }

   public final boolean isZero() {  return (a==0);  }
   public final boolean notZero() {  return (a!=0);  }
   public final boolean eq0() {  return (a==0);  }
   public final boolean ne0() {  return (a!=0);  }
   public final boolean gt0() {  return (a>0);  }
   public final boolean ge0() {  return (a>=0);  }
   public final boolean lt0() {  return (a<0);  }
   public final boolean le0() {  return (a<=0);  }
   public final boolean eq1() {  return (a==1 && b==1);  }  //: A5.036.A add
   public final boolean ne1() {  return (a!=1 || b!=1);  }  //: A5.036.A add

   public static MuRtn getInstanceFrom(TxIStream iS) throws IOException {
      final MuRtn ans= new MuRtn();  ans.scanFrom(iS);  return ans;
   }
   public static MuRtn getInstanceFrom(TxICStream iS) {
      try {  return getInstanceFrom((TxIStream)iS);   }
      catch(IOException xpt) {  throw new Error(xpt); }
   }
   public static MuRtn getInstanceFrom(TxIStrStream iS) {
      try {  return getInstanceFrom((TxIStream)iS);   }
      catch(IOException xpt) {  throw new Error(xpt); }
   }
   public static MuRtn getInstanceFrom(TxIWinStream iS) {
      try {  return getInstanceFrom((TxIStream)iS);   }
      catch(IOException xpt) {  throw new Error(xpt); }
   }


}
