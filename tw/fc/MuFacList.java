package tw.fc;

import java.io.IOException; 

public class MuFacList implements ImuFacList,
                                  SetableI<ImuFacList>
{
   //[ �����z�ƪ���]�Ƥ���,
   //[ ���]�ѽ�ƻ��W�ƦC. �ѫ��ƥi���i�t���i�s.
   long[]  primes;      //: ��ư}�C, �i�۰�expand.
   int[]   exps;        //: ��ƫ��ư}�C, �i�۰�expand.
   int     size;        //: ���Ķ���

   //[ ���ȮɦA��, �Y�wvalid�N������
   long    product_A=0; //: ���_�Ӫ����l, �i��W��. 0�N��invalid
   long    product_B=0; //: ���_�Ӫ�����, �i��W��. 0�N��invalid
 //boolean _autoProductFlag=false;

   //[  value=p1*p2*...*pr,  2^r<=value,  r<=lg(value)
   //[  ���b�e16�ӽ�Ƭۭ��ɶW�L +long �d��
   static final int INITSIZE=16; //: �w�ҩ��blong�����ѵ������
   static final int INCSIZE=32;  //: ���extra-long�ɤ~�i��Ψ�
   //-----------------------------------

   public MuFacList(long value) {
      exps=new int[INITSIZE];   primes=new long[INITSIZE];
      size=0;
      setBy(value);
   }

   public MuFacList(long a, long b) {
      this(a);
      divBy(b);
   }

   public MuFacList(ImuFacList s) {
      final MuFacList src=(MuFacList)s;
      this.exps=new int[src.exps.length];
      this.primes=new long[src.exps.length];
      //////this._copyFrom((MuFacList)src);  //: �� this._copyTo(res);
      this._copyFrom(src);  //: �� this._copyTo(res);
   }

   //-----------------------------------

   public boolean equals(MuFacList L2) { //: implements ImuFacList
      if(this.size!=L2.size)  return false;
      for(int i=0; i<this.size; i++) {
         if(primes[i]!=L2.primes[i]) return false;
         if(exps[i]!=L2.exps[i]) return false;
      }
      return true;
   }

   public boolean equals(ImuFacList a2) {
      return equals((MuFacList)a2);
   }

   public boolean equals(Object a2) {  return equals((MuFacList)a2);  }

   public final int hashCode() {
      long tL; int tI=17;
      for(int i=0; i<size; i++) {
         tL=primes[i];  tI=tI*37+(int)(tL^(tL>>32))+exps[i];
      }
      return tI;
   }

   private void _expand() {
      final int[]  c=new int[exps.length+INCSIZE];
      final long[]  P=new long[exps.length+INCSIZE];
      for(int i=0; i<exps.length; i++) {
         c[i]=exps[i];  P[i]=primes[i];
      }
      exps=c;  primes=P;
   }

//   private void _copyTo(MuFacList dst) {
//      for(int i=0; i<this.size; i++) {
//         dst.exps[i]=this.exps[i];
//         dst.primes[i]=this.primes[i];
//      }
//      dst.size=this.size;
//      dst.product=this.product;
//   }
   private void _copyFrom(MuFacList src) {
      for(int i=0; i<src.size; i++) {
         this.exps[i]=src.exps[i];
         this.primes[i]=src.primes[i];
      }
      this.size=src.size;
      this.product_A=src.product_A;
      this.product_B=src.product_B;
   }

   public MuFacList duplicate() {  return new MuFacList(this);  }
                                      //: implements ImuFacList

   void setBy(final long toFactoring) {
      if(toFactoring<=0) {
         throw new RuntimeException("not positive");
      }
      if(toFactoring==1) {
         this.size=0;  product_A=1;  product_B=1;  return;
      }

      //[ �] V=M*N, M<=N, �h V>=M*M (, sqrt(V)>=M )
      //[ �] V=L1L2..Lr*M*N, �h V/L1L2...Lr=M*N >= M*M
      this.size=0;
      long v=toFactoring;   //:  v=V/L1L2...Lr
      long f=2;  //: f�O�i�ध�]��, ��2�~�ҬO�_��
      {
         while(v%f==0) {  v /= f;   _appendPrime(f);   }
      }
      for(f=3; f*f<=v ; f+=2) { //: �u�թ_��
         while(v%f==0) {  v /= f;   _appendPrime(f);   }
      }
      // ����4�h�ܦ�v==1, ����6�h�ܦ�v==3, ����7�h�ܦ�v==7.
      if(v>1) {     _appendPrime(v);      }
//D   Std.cout.p("(").p(this.size).p(")");

    //if(_autoProductFlag) {
         product_A=0;  product_B=0;
    //}
    //else {
    //   computeProduct();
    //   if(toFactoring!=this.product_A) {
    //      throw new RuntimeException(
    //            "Panic error! parameter="+toFactoring
    //            +", product="+this.product_A
    //      );
    //   }
    //}
   }

   public MuFacList setBy(ImuFacList s) {
      final MuFacList src= (MuFacList)s;
      if(this.exps.length<src.exps.length) {
         this.exps=new int[src.exps.length];
         this.primes=new long[src.exps.length];
      }
      this._copyFrom(src);   //: �� src._copyTo(this);
      return this;
   }

/////   public SetableI setBy(DuplicableI src) {
/////      final ImuFacList s=(ImuFacList)src;
/////      this.setBy(s);
/////      return this;
/////   }

   private final void _appendPrime(long f) {
      // assume(f�O��� && f>=�w�J�}�C�����)
      if(this.size==0 || primes[this.size-1]!=f) {
         exps[this.size]=1;  primes[this.size++]=f;
      }
      else {  exps[this.size-1]++; }
      product_A=0;  product_B=0;   //: ��invalid
   }

   //--------

   private void insertPrime(int exp, long K) {
      // assume  MuFacList.isPrime(K);
      //[ �w�qprimes[i]��exps[i]����R[i], ��i�쪺record
      //[ �Nexp�ӽ�ƴ��J(��merge)
      //[ �wincreasing �� R[0],...,R[size-1] ����

      int i=size-1;
      while(i>=0 && primes[i]>K) {
         i--;
      }
      //: �X�j��� i<0 �� (i>=0 && primes[i]<=K)
      //:  case1: i<0 �����JR[0], �YR[i+1]
      //:  case2: i>=0 && primes[i]==K ��merge�JPCs[i]
      //:  case3: i>=0 && primes[i]<K �����JR[i+1]

      if(i>=0 && primes[i]==K) {
//D      Std.cout.p("merge, i=").pn(i);
         exps[i]+=exp;
         if(exps[i]==0) {  // �V�e����
            for(int j=i+1; j<size; j++) {
               exps[j-1]=exps[j];  primes[j-1]=primes[j];
            }
            size--;
         }
      }
      else {
//D      Std.cout.p("new insert, i=").pn(i);
         // �srecord���J����R[i+1]
         if(!(size<exps.length)) {   _expand();  }
         for(int j=size-1; j>i; j--) {  // �V�ᮿ��
            exps[j+1]=exps[j];  primes[j+1]=primes[j];
         }
         exps[i+1]=exp; primes[i+1]=K;
         size++;
      }

      product_A=0;  product_B=0;   //: ��invalid

   }

   public MuFacList mulBy(ImuFacList L2) {
      final MuFacList L=(MuFacList)L2;
      for(int i=0; i<L.size; i++) {
         insertPrime(L.exps[i], L.primes[i]);
      }
    //if(_autoProductFlag) {   computeProduct();  }
    //else {
         product_A=0;  product_B=0;    //: ��invalid
    //}
      return this;
   }
   public MuFacList mulBy(long L) {
      return mulBy(new MuFacList(L));
   }
   public ImuFacList mul(ImuFacList L2) {  //: implements ImuFacList
      final MuFacList res=new MuFacList(this);
      return res.mulBy(L2);
   }
   public ImuFacList mul(long L) {  //: implements ImuFacList
      return this.mul(new MuFacList(L));
   }

   public MuFacList divBy(ImuFacList L2) {
      final MuFacList L=(MuFacList)L2;
      for(int i=0; i<L.size; i++) {
         insertPrime(-L.exps[i], L.primes[i]);
      }
    //if(_autoProductFlag) {   computeProduct();  }
    //else {
         product_A=0;  product_B=0;   //: ��invalid
    //}
      return this;
   }
   public MuFacList divBy(long L) {
      if(L==0) throw new RuntimeException("zero demoninator");
      return divBy(new MuFacList(L));
   }
   public ImuFacList div(ImuFacList L2) { //: implements ImuFacList
      final MuFacList res=new MuFacList(this);
      return res.divBy(L2);
   }
   public ImuFacList div(long L) { //: implements ImuFacList
      return this.div(new MuFacList(L));
   }

   public MuFacList invert() {
      for(int i=0; i<size; i++) {   exps[i]=-exps[i];   }
      // computeProduct();
      final long t=product_A; product_A=product_B; product_B=t;
      return this;
   }
   public ImuFacList inverse() { //: implements ImuFacList
      final MuFacList res=new MuFacList(this);
      return res.invert();
   }

   public MuFacList powerBy(int e) {  //: ����
      if(e==0) {  size=0; }
      else {
         for(int i=0; i<size; i++) {   exps[i]*=e;   }
      }
      product_A=0;  product_B=0;   //: ��invalid
      return this;
   }
   public ImuFacList power(int e) {  //: ���� //: implements ImuFacList
      final MuFacList res=new MuFacList(this);
      return res.powerBy(e);
   }

   public ImuFacList gcd(ImuFacList I2) { //: implements ImuFacList
      //: �U������ƨ�min power
      //: ���]�ѽ�ƨ�inc order�ƦC
      final MuFacList L2=(MuFacList)I2;
      final MuFacList res=new MuFacList(1L);
      int i=0;  int j=0;
      while(i<this.size && j<L2.size) {
//D      Std.cout.p("i=").pc(i).p("j=").pn(j);
         if(this.primes[i] < L2.primes[j]) {
//D         Std.cout.pn("<");
            //[ this�M������
            if(this.exps[i]<0) {
               res.insertPrime(this.exps[i], this.primes[i]);
            }
            i++;
         }
         else if(this.primes[i] > L2.primes[j]) {
//D         Std.cout.pn(">");
            //[ L2�M������
            if(L2.exps[j]<0) {
               res.insertPrime(L2.exps[j], L2.primes[j]);
            }
            j++;
         }
         else { // ( this.primes[i] == L2.primes[j] )
//D         Std.cout.pn("==");
            int min=this.exps[i];  if(L2.exps[j]<min) min=L2.exps[j];
            if(min==0) {  throw new RuntimeException("Panic Error"); }
            else {  res.insertPrime(min, this.primes[i]);  }
            i++;  j++;
         }
      }
//D   cout.pn("T");
      while(i<this.size) {  //[ this�ѤU����
         if(this.exps[i]<0) {
            res.insertPrime(this.exps[i], this.primes[i]);
         }
         i++;
      }
      while(j<L2.size) {  //[ L2�ѤU����
         if(L2.exps[j]<0) {
            res.insertPrime(L2.exps[j], L2.primes[j]);
         }
         j++;
      }
      return res;
   }

   public ImuFacList lcm(ImuFacList I2) {  //: implements ImuFacList
      //: �U������ƨ�max power
      //: ���]�ѽ�ƨ�inc order�ƦC
      final MuFacList L2=(MuFacList)I2;
      final MuFacList res=new MuFacList(1L);
      int i=0;  int j=0;
      while(i<this.size && j<L2.size) {
         if(this.primes[i] < L2.primes[j]) {
            //[ this�M������
            if(this.exps[i]>0) {
               res.insertPrime(this.exps[i], this.primes[i]);
            }
            i++;
         }
         else if(this.primes[i] > L2.primes[j]) {
            //[ L2�M������
            if(L2.exps[j]>0) {
               res.insertPrime(L2.exps[j], L2.primes[j]);
            }
            j++;
         }
         else { // ( this.primes[i] == L2.primes[j] )
            int max=this.exps[i];  if(L2.exps[j]>max) max=L2.exps[j];
            if(max==0) {  throw new RuntimeException("Panic Error"); }
            else {  res.insertPrime(max, this.primes[i]);  }
            i++;  j++;
         }
      }
      while(i<this.size) {  //[ this�ѤU����
         if(this.exps[i]>0) {
            res.insertPrime(this.exps[i], this.primes[i]);
         }
         i++;
      }
      while(j<L2.size) {  //[ L2�ѤU����
         if(L2.exps[j]>0) {
            res.insertPrime(L2.exps[j], L2.primes[j]);
         }
         j++;
      }
      return res;
   }

   public void printTo(TxOStream ooo) throws IOException {  //: implements
    //ooo.p("(");
      if(size==0) ooo.p("1");
      else {
         for(int i=0; i<size; i++) {
            if(exps[i]==0) continue;
            ooo.p("(").p(primes[i]);
            if(exps[i]!=1) ooo.p("^").p(exps[i]);
            ooo.p(")");
         }
      }
    //ooo.p(")");
   }
   public void widthPrintTo(int w, TxOStream ooo) throws IOException { //: implements
      printTo(ooo);
   }

   public int size() {  return size;  }  //: implements ImuFacList

   public boolean isOne() {  return size==0;  }
                                         //: implements ImuFacList
   public boolean isInteger() {          //: implements ImuFacList
      for(int i=0; i<size; i++) {
         if(exps[i]<0) return false;
      }
      return true;
   }
   public boolean isPrime() {  return size==1;  }
                                          //: implements ImuFacList

   private void computeProduct() {
      if(product_A!=0L && product_B!=0L) return;
      this.product_A=1L;  this.product_B=1L;
      for(int i=0; i<size; i++) {
         if(exps[i]>=0) {  // �����Ʈɭ��i���l
            for(int j=1; j<=exps[i]; j++) {
               this.product_A *= primes[i];
            }
            if(product_A<0) throw
               new RuntimeException(" +long overflow: ");
         }
         else {  // �t���Ʈɭ��i����
            for(int j=1; j<= -exps[i]; j++) {
               this.product_B *= primes[i];
            }
            if(product_B<0) throw
               new RuntimeException(" +long overflow: ");
         }
      }
   }
// public void autoProduct(boolean auto) {
//    _autoProductFlag=auto;
// }

   public long longValue() { //: implements ImuFacList
      if(!isInteger()) throw new RuntimeException("not integer");
      computeProduct();
      return this.product_A;
   }
   public MuRtn value() { //: implements ImuFacList
      computeProduct();
      return new MuRtn(this.product_A, this.product_B);
   }

   //===================================================

   public static boolean isPrime(long v) {
   // return new MuFacList(toFactoring).isPrime();
                          //: �o�ˤӺC, �D��������X��.
      if(v<=1) {  return false;    }
      //[ �] V=M*N, M<=N, �h V>=M*M (, sqrt(V)>=M )
      long f=2;  //: f�O�i�ध�]��, ��2�~�ҬO�_��
//Std.cout.pc(v).pc(f).pn(v%f);
      if(f*f<=v && v%f==0) return false;
      for(f=3; f*f<=v ; f+=2) { //: �u�թ_��
         if(v%f==0) return false;
      }
      return true;
   }

   public static void detectPrimePruduct() {
      // �Ѧ�����o�� +long �������Ѧ]�����h��15�Ӭ۲���]��
      long count=0;     long product=1;
      for(long i=2; i<100; i++) {
         if(MuFacList.isPrime(i)) {
            count++;  product*=i;
            Std.cout.p(i).p("#").pcs(count).pn(product);
            if(product<0)  break;
         }
      }
      // 2#1, 2   3#2, 6   5#3, 30   7#4, 210   11#5, 2310   13#6, 30030
      // .....
      // 43#14, 13082761331670030
      // 47#15, 614889782588491410
      // 53#16, -4304329670229058502
   }

   public static long primeCount(long from, long bound, boolean demo) {
      long count=0;
      for(long i=from; i<bound; i++) {
         if(isPrime(i)) {
            count++;
            if(demo) Std.cout.p(i).p("#").ps(count);
         }
//if(i%1000000==0) Std.cout.p(i).p("-->").pn(count);
      }
      return count;
   }

}
//=====================================

