package tw.fc ;

import java.io.IOException;
//import java.io.EOFException;

//**********    MuRtn.java    ****************
//
//  mutable rational number, 
//  a demostration class for TwTX
//

public class MuRtn extends ImuRtn
   implements SetableI<ImuRtn>, ScannableI 
   //I        java.lang.Comparable<ImuRtn>  
{
//I   private long a, b;   

   //------------------------------------------------------   
   public  MuRtn() {  super(); }
   public  MuRtn(int a) {  super(a); }
   public  MuRtn(int a, int b) {  super(a, b); }
   public  MuRtn(long a) {  super(a); }
   public  MuRtn(long a, long b) {  super(a, b);  }
   public  MuRtn(ImuRtn X) {  super(X);  }   //:  cp-ctor
   public  MuRtn(Byte X)     {  super(X);  }
   public  MuRtn(Short X)    {  super(X);  }
   public  MuRtn(Character X){  super(X);  }
   public  MuRtn(Integer X)  {  super(X);  }
   public  MuRtn(Long X)     {  super(X);  }

   //------------------------------------------------------   
   public final MuRtn setBy(ImuRtn src) {  a=src.a;  b=src.b; return this; }
   public final MuRtn setBy(int above) {  a=above; b=1L; return this; }
   public final MuRtn setBy(int above, int below) {  
      if(below>0) {  a=above; b=below; reduce(); return this; }
      else if(below<0) {  a=-above; b=-below; reduce(); return this; }
      else {
         throw new ArithmeticException("zero denominator");
      }
   }
   public final MuRtn setBy(long above) {  a=above; b=1L; return this; }
   public final MuRtn setBy(long aa, long bb) {  
      if(bb==0) {
         throw new ArithmeticException(
                   "MuRtn.setBy : zero denominator");
      }
      a=aa;  b=bb; return this; 
   }
   public MuRtn setBy(byte aa) {  a=aa; b=1L; return this; }
   public MuRtn setBy(short aa) {  a=aa; b=1L; return this; }

   public MuRtn setBy(Byte X) {  setBy(X.byteValue());  return this;}
   public MuRtn setBy(Short X) {  setBy(X.shortValue()); return this; }
   public MuRtn setBy(Integer X) {  setBy(X.intValue()); return this; }
   public MuRtn setBy(Long X) {  setBy(X.longValue()); return this;}
 
   //------------------------------------------------------   

//I   public final String toString() { ... }
//I   public boolean equals(MuRtn v2) { ... }
  
   //[--------  implement OperatableI   ---------   
//I   public final DuplicableI duplicate() {  return new MuRtn(this);   }
   
/////   public final SetableI setBy(DuplicableI s) {
/////      final ImuRtn src=(ImuRtn)s;
/////      a=src.a;  b=src.b; return this; 
/////   }

   @Override
   public final void scanFrom(TxIStream iii) throws IOException {
   // if(iii.autoSkipWS) {  iii.skipWS();  }  //: 由get_int處理 
      this.a=iii.get_int(10);  //: 有理數如同實數不受radix影響
      final int next=iii.read();
      if(next!='/') {    //: 除號前不准有WS
         //iii.unread(next);
         if(next!=TxIStream.EOF) iii.unread((char)next);
         this.b=1;
      }
      else {
         final boolean old=iii.autoSkipWS;
         iii.autoSkipWS=false;    //: 除號後不准有WS
         this.b=iii.get_int(10);  //: 有理數如同實數不受radix影響
         iii.autoSkipWS=old;    //: 還原
         if(this.b==0) {
            throw new NumberFormatException("zero denominator");
         }
      }
      reduce();  
   }

   //]--------  implement OperatableI   ---------   
   
   public static MuRtn parseRtn(String s) 
   {
      final TxIStrStream inputS=new TxIStrStream(s);
      MuRtn x=new MuRtn();  
      inputS.g(x);
      return x;
   } 

   //--------

//I   public final MuRtn add(ImuRtn v2) {  ...  }
//I   public final MuRtn sub(ImuRtn v2) {  ...  }
//I   public final MuRtn mul(ImuRtn v2) {  ...  }
//I   public final MuRtn div(ImuRtn v2) {  ...  }
//I   public final MuRtn add(ImuLng v2) {  ...  }
//I   public final MuRtn sub(ImuLng v2) {  ...  }
//I   public final MuRtn mul(ImuLng v2) {  ...  }
//I   public final MuRtn div(ImuLng v2) {  ...  }
//I   public final MuRtn add(ImuInt v2) {  ...  }
//I   public final MuRtn sub(ImuInt v2) {  ...  }
//I   public final MuRtn mul(ImuInt v2) {  ...  }
//I   public final MuRtn div(ImuInt v2) {  ...  }
      
   public final MuRtn addBy(ImuRtn v2) {  
       // simulate  (*this)+=(*v2);
       a = a*v2.b + b*v2.a ;  b *= v2.b ;
       reduce();    return this; 
   }
   public final MuRtn subBy(ImuRtn v2) {  
       a = a*v2.b - b*v2.a ;  b *= v2.b ;
       reduce();    return this; 
   }
   public final MuRtn mulBy(ImuRtn v2) {  
      a*=v2.a;  b*=v2.b;   reduce();  return this; 
   }
   public final MuRtn divBy(ImuRtn v2) {  
      if(v2.a==0) { 
         throw new ArithmeticException("MuRtn.divBy : zero divider");
      }   
      a*=v2.b;  b*=v2.a;  reduce();  return this; 
   }

   public final MuRtn addBy(long v2) {  
      a += b*v2;   reduce();    return this; 
   }
   public final MuRtn subBy(long v2) {  
       a -= b*v2;   reduce();    return this; 
   }
   public final MuRtn mulBy(long v2) {  
      a*=v2;  reduce();  return this; 
   }
   public final MuRtn divBy(long v2) {  
      if(v2==0) { 
         throw new ArithmeticException("MuRtn.divBy : zero divider");
      }   
      b*=v2;  reduce();  return this; 
   }
   public final MuRtn addBy(int v2) {  
       a += b*v2;   reduce();    return this; 
   }
   public final MuRtn subBy(int v2) {  
       a -= b*v2;   reduce();    return this; 
   }
   public final MuRtn mulBy(int v2) {  
      a*=v2;  reduce();  return this; 
   }
   public final MuRtn divBy(int v2) {  
      if(v2==0) { 
         throw new ArithmeticException("MuRtn.divBy : zero divider");
      }   
      b*=v2;  reduce();  return this; 
   }

   public final MuRtn setByAdd(ImuRtn x1, ImuRtn x2) {
      // simulate  (*this)= *x1 + *x2; 
      a= x1.a*x2.b + x1.b*x2.a ;  b= x1.b*x2.b ;
      reduce();  return this;
   }
   public final MuRtn setBySub(ImuRtn x1, ImuRtn x2) {
      a= x1.a*x2.b - x1.b*x2.a ;  b= x1.b*x2.b ;
      reduce();  return this;
   }
   public final MuRtn setByMul(ImuRtn x1, ImuRtn x2) {
      a= x1.a*x2.a;  b=x1.b*x2.b;
      reduce();  return this;
   }
   public final MuRtn setByDiv(ImuRtn x1, ImuRtn x2) {
      if(x2.a==0) { 
         throw new ArithmeticException("MuRtn.divBy : zero divider");
      }   
      a= x1.a*x2.b;  b=x1.b*x2.a;
      reduce();  return this;
   }
   public final MuRtn addByMul(ImuRtn x1, ImuRtn x2) {
      // simulate (*this) += ((*x1)*(*x2));
      final long ta= x1.a*x2.a, tb=x1.b*x2.b;
      a= a*tb+b*ta;  b *= tb; 
      reduce();  return this;
   }
   public final MuRtn addByDiv(ImuRtn x1, ImuRtn x2) {
      // simulate (*this) += ((*x1)/(*x2));
      if(x2.a==0) { 
         throw new ArithmeticException("MuRtn.divBy : zero divider");
      }   
      final long ta= x1.a*x2.b, tb=x1.b*x2.a;
      a= a*tb+b*ta;  b *= tb; 
      reduce();  return this;
   }

   public final MuRtn mulByAdd(ImuRtn x1, ImuRtn x2) {
      // simulate (*this) *= ((*x1)+(*x2));
      final long ta= x1.a*x2.b+x1.b*x2.a, tb=x1.b*x2.b;
      a*=ta;  b*=tb;
      reduce();  return this;
   }
   public final MuRtn mulBySub(ImuRtn x1, ImuRtn x2) {
      // simulate (*this) *= ((*x1)-(*x2));
      final long ta= x1.a*x2.b-x1.b*x2.a, tb=x1.b*x2.b;
      a*=ta;  b*=tb;
      reduce();  return this;
   }

   public final MuRtn inc() { addBy(1L); return this;  }
   public final MuRtn dec() { subBy(1L); return this;  }

//I   public final ImuRtn neg() {   ...   }
   public final MuRtn negate() {  a=-a; return this;   }
   public final MuRtn setByNeg(ImuRtn x) {
      a= -x.a;  b=x.b;  return this;
   }

//I   public final ImuRtn inv() {   ...   }
   public final MuRtn invert() {   return setBy(this.inv());   }
   public final MuRtn setByInv(ImuRtn x) {
      if(x.a>0) {  a=x.b;  b=x.a;  return this;  } 
      else if(x.a<0) {  a= -x.b;  b= -x.a;  return this;  } 
      else {
         throw new ArithmeticException("inv of zero");
      }
   }

//I   public final ImuRtn abs() {  ...  }

   public final MuRtn setByAbs(ImuRtn x) {
      this.setBy(x);
      if(this.a<0) {  this.a=-this.a;  }
      return this;
   }

//I   public final MuRtn pow(int n) {    ...   }
   public final MuRtn powBy(int n) {
      setBy(this.pow(n));  return this;
   }
   public final MuRtn setByPow(ImuRtn x, int n) {
      setBy(x.pow(n));  return this;
   }
   //--------------------------------

//I   public final int compareTo(ImuRtn v2) {  ...  }
//I   public final int compareTo(Object o) { ...  }
//I   public final boolean eq(ImuRtn v2) {  return compareTo(v2)==0; }
//I   public final boolean ne(ImuRtn v2) {  return compareTo(v2)!=0; }
//I   public final boolean gt(ImuRtn v2) {  return compareTo(v2)>0; }
//I   public final boolean ge(ImuRtn v2) {  return compareTo(v2)>=0; }
//I   public final boolean lt(ImuRtn v2) {  return compareTo(v2)<0; }
//I   public final boolean le(ImuRtn v2) {  return compareTo(v2)<=0; }
   
}
