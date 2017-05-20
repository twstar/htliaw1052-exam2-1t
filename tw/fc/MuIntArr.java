package tw.fc;

public class MuIntArr extends ImuIntArr 
             implements SetableI<ImuIntArr>   // , ScannableI,  
{

   final static int INITSIZE=32;
   final static int INCSIZE=256;

   //---------------------------------------------
   public MuIntArr() { 
      super(INITSIZE);
   }
  
   public MuIntArr(int[] ia) { super(ia);  }

   public MuIntArr(ImuIntArr src) {
      super(src.length());
      for(int i=0; i<src.size; i++) {  
         _array[i]=src._array[i];
      }
   }
   
   //-------------------------------------

   private void _expand(int len) {
      if(_array.length>=len) return;
      final int[]  c=new int[len]; 
      for(int i=0; i<_array.length; i++) {  c[i]=_array[i];    }       
      _array=c;  
   }

   public MuIntArr setBy(ImuIntArr src) {
      if(this._array.length < src.length()) {  //: 兩者取不同的length
         _array=new int[src.length()];
      }      
      for(int i=0; i<src.length(); i++) {  
         _array[i]=src._array[i];    
      }       
      size=src.length();
      return this;
   }



   public int at(int idx) {
      if(idx>=size) throw 
         new RuntimeException("index at empty range: "+idx);
      return _array[idx];  //:  index <0 就讓它throw.
   }

   public void setAt(int idx, int value) {
      if(idx>=_array.length) {   _expand(idx+INCSIZE);  }
      _array[idx]=value;    //: idx<0 就讓它throw
      if(idx>=size) {   size=idx+1;  }
   }

  
   public void incInsert(int K) {
      // client code 自需保證原為increasing      
      if(!(size<_array.length)) {   _expand(_array.length+INCSIZE); }
      ImuIntArr.incInsert(_array, size, K);
   }

   public void insertionSort(boolean demo) {
      ImuIntArr.insertionSort(_array, size, demo);
   }
   public void insertionSort() {
      ImuIntArr.insertionSort(_array, size, false);
   }


}

//===========================================================

class TestIntArr {

   static void testSort1() {
      new MuIntArr(new int[]{ 5,4,3,2,1 }).insertionSort(true);
      Std.cout.pn();
      new MuIntArr(new int[]{ 2,3,4,5,1 }).insertionSort(true);
      Std.cout.pn();
      new MuIntArr(new int[]{ 2,4,1,2,3 }).insertionSort(true);
      Std.cout.pn();
      new MuIntArr(new int[]{ 4,2,9,3,5,3,2,6,8,4,7,1,8 }).insertionSort(true);
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
      testSort1();
   }
}
