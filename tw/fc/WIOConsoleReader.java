package tw.fc ;

import java.io.Reader;
//import java.io.CharArrayReader;
import java.io.StringReader;
import java.nio.CharBuffer;
//import java.util.ArrayList;
import java.util.Vector;
import java.io.IOException;
import static tw.fc.Std.*;

//[ A5.007.J add.
//[ 動機: 一致性地由 TxIStream 處理 unread 的問題.
class WIOConsoleReader 
   extends Reader   //:  Reader無unread的機制
{

   private final WIOConsole _hostWin;   

 //[ has moved to WIOConsole
   //final StringBuilder keyinLine= new StringBuilder();    //: b4 A5.007.N
   //final Vector<String> keyinBuffer= new Vector<String>();  //: b4 A5.007.N
 //]

   // StringReader fromWin= new StringReader("");  //: b4 A5.007.N
   StringReader readingLine= new StringReader("");  
          //: 此時為EOF, 每次遇到 input 時都要讀keyboard並重新設定


 //-----------------------------------------------------
   public WIOConsoleReader(WIOConsole w) {  
      super();  
      this._hostWin=w;
   }

 //-----------------------------------------------------
   public WIOConsole getHostWindow() {  return this._hostWin;  }

//[ has moved to WIOConsole
// //[ form keyboard to keyinLine, 
// //[ executed by event-dispatching thread in WIOConsole 
// public void keyin(char ch) {
//    if(ch!='\n') {
//       keyinLine.append(ch);
//    }
//    else {
//       keyinLine.append(ch);  //: append too!
//       final String s=keyinLine.toString();
//       keyinLines.add(s);
//   //D 
//       System.out.println("\nget a line: ["+s+"]");
//       keyinLine.delete(0, keyinLine.length());
//    }  
// }

 //-----------------------------------------------------
   @Override public int read() {
      int ch;   //: int for -1, the EOF
      try{  
     //D cout.pn("\nread()... ");
         while(true) {
            ch= this.readingLine.read();
            if(ch!=TxIStream.EOF) {  break;  }
            //[ EOF, wait for new keyin 
        //D cout.pn("\nwait... ");
          //[ class Vector is synchronized
            while(this._hostWin.keyinLines.size()==0) { //: has extra lines
               final int ms=500;  
               //Thread.currentThread().sleep(ms);  // b4 A5.043L
               Thread.sleep(ms);  // unless too busy 
            }
            final String nxtLine= this._hostWin.keyinLines.remove(0); 
            this.readingLine=new StringReader(nxtLine);
         } 
cout.p("Rd[").hex().p(ch).p("]");
         return ch;  
      }
      catch(InterruptedException xpt) { 
         throw new StateError("\n"+xpt);
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override public int read(char[] cbuf) {
      throw new Error("Not Supported");
   }
   @Override public int read(char[] cbuf, int off, int len) {
      throw new Error("Not Supported");
   }
   @Override public int read(CharBuffer target) {
      throw new Error("Not implement yet");
   }
   @Override public long skip(long n) {
      long skipCount;
      for(skipCount=0; skipCount<n; skipCount++) {
         int ch=this.read();
         if(ch==TxIStream.EOF) break; 
      }
      return skipCount;
   }
   @Override public void mark(int readAheadLimit) {
      throw new Error("Not Supported");
   }
   @Override public boolean markSupported() {
      return false;
   }
   @Override public boolean ready() {
      try {
         return ( this.readingLine.ready() || this._hostWin.keyinLines.size()>0 );
      } 
      catch(IOException x) {  throw new Error(x);  }
   }
   @Override public void reset() {
      throw new Error("Not support");
   }
   @Override public void close() {
      // throw new Error("Not supported");
      ;  //: just ignore
   }

}