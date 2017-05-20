package tw.fc;

import java.io.IOException;

public class ImuIntArr implements PrintableI, DuplicableI<MuIntArr>  {

   protected int[] _array;
   protected int size;  //: 用在MuIntArr

   //---------------------------------
   public ImuIntArr(int size) { 
      _array=new int[size];   this.size=size;
   }

   public ImuIntArr(int[] ia) { 
      this(ia.length);
      for(int i=0; i<ia.length; i++) {  _array[i]=ia[i];  }
   }

   //----------------
   public boolean equals(ImuIntArr a2) {  return compare(a2)==0;  }
   public boolean equals(Object a2) {  
      return compare((ImuIntArr)a2)==0;  
   }
   public final int hashCode() {  
      long tL; int tI=17;
      for(int i=0; i<size; i++) {
         tL=_array[i];  tI=tI*37+(int)(tL^(tL>>32));
      }
      return tI;
   }
   //-----------------

   public int at(int idx) {
      return _array[idx];  //:  index 超界就讓它throw.
   }

   public int length() {   return size;   }

   public MuIntArr duplicate() {  return new MuIntArr(this); }


   //[-------- implements PrintableI  
   public final void printTo(TxOStream ooo) throws IOException {  
      print(ooo, _array);
   }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
//>>>> 應各entry皆wp
      printTo(ooo); 
   }
   //]-------- implements PrintableI  

   public int compare(ImuIntArr a2) {
      return ImuIntArr.compare(this._array, this.size, a2._array, a2.size);
   }


   //[ ======     static part    ===============================

   public static void print(TxOStream ooo, int[] arr, int bound) 
      throws IOException 
   {
      if(arr==null) {  ooo.p("null");  }
      ooo.p("["); 
      if(arr.length>0 && bound>0) {  ooo.p(arr[0]);  }
      for(int i=1; i<bound; i++) {  ooo.p(",").p(arr[i]);   }
      ooo.p("]");  
   }
   public static void print(TxOCStream ooo, int[] arr, int bound) {
      try {
         print((TxOStream)ooo, arr, bound); 
      }
      catch(IOException xpt) {
         throw new RuntimeException("\n"+xpt);
      }
   }

   public static void print(TxOStream ooo, int[] arr) 
      throws IOException
   {
      print(ooo, arr, arr.length); 
   }
   public static void print(TxOCStream ooo, int[] arr) {
      print(ooo, arr, arr.length); 
   }

   public static int[] duplicate(int[] ia) {
      final int[] t=new int[ia.length];
      for(int i=0; i<ia.length; i++) {  t[i]=ia[i];  } 
      return t;
   }

   public static void incInsert(int[] ia, int p, int K) {
      //[ 將K插入已increasing 的 ia[0],...,ia[p-1] 之間
      int i=p-1;
      while(i>=0 && K<ia[i]) {
         ia[i+1]=ia[i];  //: 向後挪
         i--; 
      }
      ia[i+1]=K;         //: 填入
   }

   static int[] insertionSort(int[] ia, int bound, boolean demo) {  
      //: 對[0...bound-1] 做 insertion insertionSort 使成 increasing
//    try {
         if(demo) {  
            Std.cout.p("origin:\t\t").p(ia).pn();
         }
         for(int p=1; p<bound; p++) {      
            //[ 將ia[p]取出, 插入已increasing的 ia[0],...,ia[p-1]之間
            final int K=ia[p];
            if(demo) {  Std.cout.p("p=").pt(p).p("K=").pt(K);  }
            incInsert(ia, p, K);
            if(demo) {
               print(Std.cout, ia, bound);
               Std.cout.pn();
            }
         }
//    }
//    catch (IOException xpt) {
//       throw new RuntimeException("\n"+xpt); 
//    }
      return ia;  //: 使能當expression用
   }
   public static int[] insertionSort(int[] ia, boolean demo) {  
      return insertionSort(ia, ia.length, demo);
   }
   public static int[] insertionSort(int[] ia) {  
      return insertionSort(ia,false); 
   }  

   static int compare(int[] ia1, int len1, int[] ia2, int len2) { 
      //: 字典排序
      //: -1 for less than, 0 for equals to, +1 for greater than
      for(int i=0;     ; i++) {
         if(i>=len1) {  //: ia1已完畢
            if(i<len2) return -1;  //: ia2未完
            else break;            //: ia2也完畢
         }
         else if(i>=len2) { //: ia1未完, ia2已完畢
            return +1;
         }
         else if(ia1[i]<ia2[i])  return -1;
         else if(ia1[i]>ia2[i])  return 1 ;
         else  ;
      }
      return 0;
   }

   public static int compare(int[] ia1, int[] ia2) { 
      //: 字典排序
      //: -1 for less than, 0 for equals to, +1 for greater than
      return compare(ia1, ia1.length, ia2, ia2.length);
   }

   //==========================================
   static void testSort() {
      insertionSort(new int[]{ 5,4,3,2,1 },true);
      Std.cout.pn();
      insertionSort(new int[]{ 2,3,4,5,1 },true);
      Std.cout.pn();
      insertionSort(new int[]{ 2,4,1,2,3 },true);
      Std.cout.pn();
      insertionSort(new int[]{ 4,2,9,3,5,3,2,6,8,4,7,1,8 },true);
      Std.cout.pn();
      // origin:         [4,2,9,3,5,3,2,6,8,4,7,1,8]
      // p=1     K=2     [2,4,9,3,5,3,2,6,8,4,7,1,8]
      // p=2     K=9     [2,4,9,3,5,3,2,6,8,4,7,1,8]
      // p=3     K=3     [2,3,4,9,5,3,2,6,8,4,7,1,8]
      // p=4     K=5     [2,3,4,5,9,3,2,6,8,4,7,1,8]
      // p=5     K=3     [2,3,3,4,5,9,2,6,8,4,7,1,8]
      // p=6     K=2     [2,2,3,3,4,5,9,6,8,4,7,1,8]
      // p=7     K=6     [2,2,3,3,4,5,6,9,8,4,7,1,8]
      // p=8     K=8     [2,2,3,3,4,5,6,8,9,4,7,1,8]
      // p=9     K=4     [2,2,3,3,4,4,5,6,8,9,7,1,8]
      // p=10    K=7     [2,2,3,3,4,4,5,6,7,8,9,1,8]
      // p=11    K=1     [1,2,2,3,3,4,4,5,6,7,8,9,8]
      // p=12    K=8     [1,2,2,3,3,4,4,5,6,7,8,8,9]
   }

   public static void main(String[] aaa) {  
      testSort();
   }

}