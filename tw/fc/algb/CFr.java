package tw.fc.algb;
import tw.fc.ImuRtn;
import tw.fc.MuRtn;

//[ Continued Fractions
public class CFr {

   public static boolean simpleForm=true;
   public static final double precision=1.0/0x7fffffff;  //: 1/(2G-1)
   // static {  System.out.println(0x7fffffff);  }
   public static final int MAX_ORDER=30;                 //>>>> ?

   //====================================================
   long d0;
   int[] d;  //: d[0] 改用d0
   int _order;
   
   // 規定 d[1], d[2], ... , d[_order]>0. 且 d[_order]>=2 

   private final void normalize() {
      if(_order>0 && d[_order]==1) {
         _order--;  
         if(_order==0) {  d0++; }
         else {  d[_order]++;  }
      }
   }

   public   CFr(int[] src, int order) {
      if(order<0) throw new IllegalArgumentException("negative order");
      if(src.length<0) throw new IllegalArgumentException("empty array");
      if(src.length<order+1) throw new IllegalArgumentException("out of order");
      d=new int[order+1];
      d0=src[0];
      for(int i=1; i<=order; i++) {
         d[i]=src[i];   
         if(d[i]<1) throw new 
            IllegalArgumentException("\n i="+i+", data="+d[i]);
      }
      this._order=order;
      normalize();
   }
   public   CFr(int[] src) {
      this(src, src.length-1);
   }

   public   CFr(double v, int order) {
      if(order<0) throw new IllegalArgumentException("negative order");
      d=new int[order+1];
      final double t=Math.floor(v);
      d0=(long)t;    this._order=0;
      if((double)d0!=t) throw new 
         IllegalArgumentException("\n data overflow: "+v); 
      double a=v, r=a-d0;
      while(r>CFr.precision && this._order<order) {   
         a=1/r;
         d[++this._order]=(int)Math.floor(a);
         r=a-d[this._order];
      }
      normalize();
   }

   public   CFr(ImuRtn v) {
   // this(v.to_double(),MAX_ORDER);
   // long 做輾轉相除怕會超過int的範圍
      d=new int[1+MAX_ORDER];
      long a=v.above(), b=v.below();  
      if(b<0) {  b=-b;  a=-a; }
      long q,r;      
      if(a>=0) {
         q=a/b;  r=a-q*b;
      }
      else {
         a=-a;  //: 正化
         q=a/b; r=a-q*b;  //:  a=q*b+r
         if(r==0) {      //:  -a=-q*b-r=-q*b+0 
            q=-q;    
         }
         else {          //: -a=-q*b-r=-(q+1)*b+b-r  
            q=-(q+1); r=b-r;
         }
      }
      d0=q;  int ord=0;
      while(r>0) {
         if(ord>=MAX_ORDER) throw new   
            RuntimeException("\nlength overflow: ord="+ord);
         a=b;  b=r;
         q=a/b;  r=a-q*b;
         if(q>0x7fffffff) throw new 
            RuntimeException("\ndata overflow: ord="+ord+", q="+q);
         d[++ord]=(int)q;
      }
      this._order=ord;
      normalize();
   }

   public   CFr(CFr src) {
      this._order=src._order;
      d0=src.d0; 
      d=new int[src.d.length];
      for(int i=1; i<d.length; i++) {  d[i]=src.d[i]; }
   }

   public final int order() {  return this._order; }

   public String toString() {
      StringBuffer ans;
      if(simpleForm) {
         ans=new StringBuffer("CFr[");
         ans.append(d0);
         for(int i=1;i<=this._order;i++) {  
            ans.append(",").append(d[i]);
         }
         ans.append("]");
      }
      else {
         ans=new StringBuffer("(").append(d0);
         for(int i=1;i<this._order;i++) {  
            ans.append("+1/(").append(d[i]);
         }
         if(this._order>0) { 
            ans.append("+1/").append(d[this._order]);
                          //: 最深層不加括號
         }
         for(int i=1;i<this._order;i++) { ans.append(")"); }
         ans.append(")");
      }
      return ans.toString();
   }

   public boolean equals(Object src) {
      final CFr s=(CFr)src;
      if(_order!=s._order) return false;
      if(d0!=s.d0) return false;
      for(int i=0; i<=_order; i++) {
         if(d[i]!=s.d[i]) return false;
      }
      return true;
   }

   public final int hashCode() {  
      return 37*( 37*17+(int)(d0^(d0>>32)) ) + d.hashCode();
   }
   public ImuRtn _toRtn(int apxOrder) { //[ 由內而外的直覺算法
      if(apxOrder>this._order) apxOrder=this._order;
      if(apxOrder<0) throw new IllegalArgumentException("negtaive apxOrder");
      if(apxOrder==0) return new MuRtn(d0);
      final MuRtn ans=new MuRtn(d[apxOrder]);
      for(int i=apxOrder-1; i>0; i--) {
         ans.invert().addBy(d[i]);
      }
      ans.invert().addBy(d0);
      return ans;
   }

   public ImuRtn toRtn(int apxOrder) { //[ 向量遞迴關係法
      if(apxOrder>this._order) apxOrder=this._order;
      if(apxOrder<0) throw new IllegalArgumentException("negtaive apxOrder");
      if(apxOrder==0) {  return new ImuRtn(d0);  }
      long A_2=d0,B_2=1; 
      long A_1=d0*d[1]+1,B_1=d[1]; 
      long A=0,B=0;
      if(apxOrder==1) {  return new ImuRtn(A_1,B_1); }
      for(int i=2; i<=apxOrder; i++) {
         A=d[i]*A_1+A_2;  B=d[i]*B_1+B_2;
         A_2=A_1;         B_2=B_1;
         A_1=A;           B_1=B;   
      }
      return new ImuRtn(A,B);
   }

   public ImuRtn toRtn() {
      return toRtn(this._order);
   }
} 