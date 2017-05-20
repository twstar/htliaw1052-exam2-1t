package tw.fc ;
import java.io.IOException;
import java.util.ArrayList;

// //[ A5.025L add
//   ArrayList<Character> markedData=new ArrayList<Character>();  
//   private boolean marking=false;
//   public void dumpMarkedData() {  //: for debug
//   public void resetMarkedData() {
//   //[ java.io.PushbackInputStream.mark(int readlimit), but it do nothing
//   public void mark() {
//   public void unmark() {
// //] A5.025L add
//] b4 A5.034L

   //[ A5.034L add
   //[ push marks so that suceeding read data can be backtraced.
class BacktrackData {
      private TxIStream _iS;
      private int _numOfMark=0;
      private ArrayList<Integer> _store;  //: store EOF for marks
      //     0 1 2 ............. s-1  s 
      //   [ M C C ... M C C ...  C  )top        
  
     //--------------
      BacktrackData(TxIStream iS) {  
         this._iS= iS;
         this._store=new ArrayList<Integer>();  
      }
    //--------------

      boolean isMarking() {  return _numOfMark>0; } 
      int numOfMarks() {  return _numOfMark; } 

    //[ return the ID of the current mark, which can be used in popMark(int) 
    //[ after push a BtMark, if the trying is success, 
    //[ not necessaryly to extract the mark, it is sufficient to ignore its ID.
      int //: return the ID of the current mark, which can be used in popMark(int) 
      pushMark() {
         ++_numOfMark;
         _store.add(-_numOfMark); //: use -1, -2, ... as marks
         return _numOfMark; 
      }
      void popMark(int mId) {
         if(mId<1 || mId>_numOfMark ) {
            throw new IllegalArgumentException("mID: "+mId);  
         }  
         int ch;
         do {
            while((ch=pop())>=0) {  
               // _iS.unread((char)ch);  //: ERR: will triggle this.pop() again
               _iS.unreadData.store((char)ch);
            } 
            _numOfMark--;
         } while( _numOfMark>=mId );
      }
      void popMark() {  this.popMark(_numOfMark);  }
      void push(char ch) {  
         if(! this.isMarking() ) {
            throw new Error("not mark yet");
         }
      //D System.out.println("["+ch+"]");
         _store.add((int)ch); 
      }
      int pop()  {  //: pop a char or a mark
         if(! this.isMarking()) {
            throw new Error("unreadData: not mark");
         }
         return _store.remove(_store.size()-1); 
      }
      //void clear() {  _store.clear();   }  //: error b4 A5.036L.I
      void clear() {  _store.clear();  _numOfMark=0;  }
      void dump() {
         System.out.print(" ["); 
         for(int i=0; i<_store.size(); i++) {
            int ch=_store.get(i);
            if(ch>=0) System.out.print((char)ch); 
            else System.out.print("{"+(-ch)+"}"); 
         }
         System.out.println(") "); 
      }

  //---------------------------------------------

   private static void readPrint(TxIStrStream iS, int n) {  
      while(n-- >0) System.out.print((char)iS.read());  
   }
   private static void testMark1() {
      System.out.println("\n --- testMark1 ---"); 
      TxIStrStream iS=new TxIStrStream("abcdefghijklmnopqrstuvwxyz");; 
      BacktrackData btd= iS.backtrackData;
      readPrint(iS, 2);                    btd.dump(); 
      btd.pushMark();  readPrint(iS, 2);   btd.dump(); 
      btd.popMark();  readPrint(iS, 3);    btd.dump(); 
      System.out.println();
   }
// ab [)
// cd [{1}cd)
// cde [)

   private static void testMark2() {
      System.out.println("\n --- testMark2 ---"); 
      TxIStrStream iS=new TxIStrStream("abcdefghijklmnopqrstuvwxyz");; 
      BacktrackData btd= iS.backtrackData;  
      readPrint(iS, 2);  btd.dump(); 
      btd.pushMark();  readPrint(iS, 2);   btd.dump(); 
      btd.pushMark();  readPrint(iS, 2);   btd.dump();
      btd.popMark();  readPrint(iS, 3);    btd.dump();
      btd.popMark();  readPrint(iS, 3);    btd.dump();
      System.out.println();
   }
// ab [)
// cd [{1}cd)
// ef [{1}cd{2}ef)
// efg [{1}cdefg)
// cde [)

   private static void testMark3() {
      System.out.println("\n --- testMark3 ---"); 
      TxIStrStream iS=new TxIStrStream("abcdefghijklmnopqrstuvwxyz");; 
      BacktrackData btd= iS.backtrackData;         
      readPrint(iS, 2);  btd.dump();
      int mID=btd.pushMark();  readPrint(iS, 2);  btd.dump();
      btd.pushMark();   readPrint(iS, 2);         btd.dump();
      btd.popMark(mID);  readPrint(iS, 3);        btd.dump();
      System.out.println();
   }
// ab [)
// cd [{1}cd)
// ef [{1}cd{2}ef)
// cde [)


   public static void main(String[] dummy) {
      testMark1();
      testMark2();
      testMark3();
   }


}

