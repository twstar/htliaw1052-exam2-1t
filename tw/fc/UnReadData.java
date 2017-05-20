package tw.fc ;
import java.io.IOException;
import java.util.ArrayList;

 //  Reader無unread的機制, 之前不知有 PushbackReader, 所以自製store. 
 //  java.io.PushbackInputStream 有 
 //     void unread(int b)
 //     void unread(byte[] buf)
 //     void unread(byte[] buf, int off, int len)
 //  java.io.PushbackReader 有
 //     void unread(int c)
 //     void unread(char[] cbuf)
 //     void unread(char[] cbuf, int off, int len)
 //  ctor 包一層: PushbackReader(Reader in)



//[ A5.037L extracted from TxIStream
//[    A5.017.G add as a nested static class in TxIStream
//[    so that we can pushback many char's (in Unicode).
class UnReadData {
//[  indent 6 since it is extracted form nested class
      final TxIStream _iS;
      boolean singleStore=false;  
      //private ArrayList<Integer> _store;  //: nonsence for unread the EOF
      private ArrayList<Character> _store;  //: working as a stack
      //      s s-1        1  0   
      //   top(  C  C  ... C  C ] original-stream 

    //--------------
      //UnReadData() {  _store=new ArrayList<Integer>();  }
      UnReadData(TxIStream iStream) {  
         this._iS=iStream;
         this._store=new ArrayList<Character>();  
      }


    //--------------
      boolean isEmpty() {  return _store.size()==0; } 
      boolean notEmpty() {  return _store.size()>0; } 
      void clear() {  _store.clear();  }
      //void _store(int ch) throws IOException {  //: b4 A%.033L 
      void store(char ch) {  //:  push
         if(singleStore && this.notEmpty() ) {
            throw new Error("unreadData: store full");
         }
         _store.add(ch); 
      }
      //int retrive() throws IOException {  //: b4 A%.033L
      char retrive()  {  //: pop
         if(_store.isEmpty()) {
            throw new Error("unreadData: store empty");
         }
         return _store.remove(_store.size()-1); 
      }
}
