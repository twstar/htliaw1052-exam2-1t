package tw.fc ;
//import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.StringReader;
import java.io.IOException;

// ***********************************************
public class TxIStrStream extends TxIStream {

//I  protected Reader iii=null;
   private String creatingStr; 
   //protected final char[] cArr;  //: b4 A5.011L
  
   //---------------------------------------------
   
   //public  TxIStrStream(String s) { 
   //   super();
   //   cArr=new char[s.length()];
   //   s.getChars(0, s.length(), cArr, 0);
   //   super.iii=new CharArrayReader(cArr, 0, cArr.length);
   //}  //: b4 A5.011L
   public TxIStrStream(String s) { 
      super();
      this.creatingStr=s;
      super.iii=new StringReader(s);
   } 
   public TxIStrStream(String s, int beginIdx, int endIdx) { //: A5.011L add
      this(s.substring(beginIdx, endIdx));
   }
   public TxIStrStream(String s, int beginIdx) { //: A5.011L add
      this(s.substring(beginIdx));
   }


   //---------------------------------------

//   @RootMethod
   //public void reset() {
   public void resetData() {
      try {
         super.iii.reset(); 
           //JDK:
           // Resets the stream to the most recent mark, 
           // or to the beginning if it has never been marked.
         //super.pushBack=false;  //: b4 A5.017.B
         super.clearUnreadData();  //!! 否則iii.reset會被store內的EOF擋住
      }
      catch(java.io.EOFException xpt) {
         throw new tw.fc.EOSException("=n"+xpt);
      }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   //---------------------------------------

   //@Override
   //public String toString() {
   //   return "TxOCStream("+cArr+")";    //: cause Eclipse warning
   //}
   @Override
   public String toString() {
      return "TxOCStream("+creatingStr+")";   
   }                       
                           
                           
   @Override //: 以縮小函數值型態
   public TxIStrStream hex() {  radix=16;  return this; }
   @Override //: 以縮小函數值型態
   public TxIStrStream dec() {  radix=10;  return this; } 
   @Override //: 以縮小函數值型態
   public TxIStrStream oct() {  radix=8;   return this; } 
   @Override //: 以縮小函數值型態
   public TxIStrStream bin() {  radix=2;   return this; } 
   @Override //: 以縮小函數值型態
   public TxIStrStream setRadix(int r) {
      if(2<=r && r<=36) {  radix=r;   return this;  }
      throw new IllegalArgumentException("radix error: "+r);
   }
   @Override //: 以縮小函數值型態
   public TxIStrStream setBooleanPattern(String f, String t) { //: 長效
      super.setBooleanPattern(f,t);  return this;
   }
   @Override //: 以縮小函數值型態
   public TxIStrStream setBooleanPattern() { //: 長效
      super.setBooleanPattern("false", "true");  return this;
   }

   // @Override  //: 以濾掉 IOException
   // //public void unread(int ch) {
   // public void unread(char ch) {
   //    //try {  
   //       super.unread(ch);  
   //    //}
   //    //catch(IOException xpt) { throw new StateError("\n"+xpt);  }  //: b4 A5.033L
   // }
   // @Override  //: 以濾掉 IOException 
   // public void unread(String s) {
   //    try {  super.unread(s);  }
   //    catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   // }
   // @Override  //: 以濾掉 IOException 
   // public void unread(char[] A, int from, int to) {
   //    try {  super.unread(A, from, to);  }
   //    catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   // }
   // @Override  //: 以濾掉 IOException 
   // public void unread(char[] A, int from) {
   //    try {  super.unread(A, from);  }
   //    catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   // }
   // @Override  //: 以濾掉 IOException 
   // public void unread(char[] A) {
   //    try {  super.unread(A);  }
   //    catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   // }

   @Override  //: 以濾掉 IOException 並補報EOSException
   public int read() {
      try {
//       return iii.read(); 
//         //: 960120誤製的的大bug!! 造成忽略super.store
         return super.read();
      }
      catch(java.io.EOFException xpt) {
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public int get() {  
      try {  return super.get();  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public TxIStrStream read(charRef s) {
      try {  super.read(s);  return this; }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public int read(char[] dest, int offset, int count) {
      try {  return super.read(dest, offset, count);  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public int read(char[] dest) { 
      try {  return super.read(dest);  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public long skip(long count) { 
      try {  return super.skip(count);  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public long ignore(long count) { 
      try {  return super.ignore(count);  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   //@Override //: 以濾掉 IOException 並補報EOSException
   //public TxIStrStream skipSpHt() { 
   //   try {  super.skipSpHt();  return this;  }
   //   catch(java.io.EOFException ioxpt) { 
   //      //: EOFException is_a IOException
   //      throw new tw.fc.EOSException();
   //   }
   //   catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   //} //: b4 A5.036
   @Override //: 以濾掉 IOException 並補報EOSException
   public TxIStrStream skipWS() { 
      try {  super.skipWS();  return this; }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public TxIStrStream skipWS_inLine() { 
      try {  super.skipWS_inLine();  return this; }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Deprecated
   @Override //: 以濾掉 IOException 並補報EOSException
   //public TxIStrStream skipToEOL(boolean show) { 
   public TxIStrStream skipToEOL(TxOStream oS) { 
      //try {  super.skipToEOL(show);  return this;  }
      try {  super.skipCurrentLine(oS);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   //public TxIStrStream skipToEOL() { 
   //   this.skipToEOL(false);
   //}

   @Override //: 以濾掉 IOException 並補報EOSException
   //public TxIStrStream skipToEOL(boolean show) { 
   public TxIStrStream skipCurrentLine(TxOStream oS) { 
      //try {  super.skipToEOL(show);  return this;  }
      try {  super.skipCurrentLine(oS);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }


   @Override //: 以濾掉 IOException 並補報EOSException
   public int peek() {
      try {  return super.peek();  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public boolean probe(int expected) { 
      try {  return super.probe(expected);  }
      catch(java.io.EOFException ioxpt) {  //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public boolean probeEOF() {  return this.probe(TxIStream.EOF);  }


// @Override //: 以濾掉 IOException
// //public int probe(String token, intRef nextCh) {
// public boolean probe(String token, intRef nextCh) {
//    try {  return super.probe(token, nextCh);  }
//    catch(IOException xpt) { throw new StateError("\n"+xpt);  }
// }
// @Override //: 以濾掉 IOException
// public boolean probe(String token) {
//    try {  return super.probe(token, null);  }
//    catch(IOException xpt) { throw new StateError("\n"+xpt);  }
// }
   @Override //: 以濾掉 IOException 
   //public boolean probe(String fStr, Object... args) {  //: b4 A5.038.C
   public boolean probef(String fStr, Object... args) {
      try {  return super.probef(fStr, args);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream expect(int expected) { 
      try {  super.expect(expected);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream expectWS() { 
      try {  super.expectWS();  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream expectWS_inLine() { 
      try {  super.expectWS_inLine();  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public String getLine() { 
      try {  return super.getLine();  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public String getToken(char delimiter) { 
      try {  return super.getToken(delimiter);  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   //-----------------------------------------------------------
   @Override //: 以濾掉 IOException 並補報EOSException
   public String getString() { 
      try{    return ( super.getString() ); }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public long get_long(int radix) { 
      try {
         return super.get_long(radix);  //: 兩種寫法都通
      }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException("\n"+ioxpt);
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public long get_long() { 
      return this.get_long(this.radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public int get_int(int radix) {
      return (int)this.get_long(radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public int get_int() {
      return (int)this.get_long(this.radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public short get_short(int radix) {
      return (short)this.get_long(radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public short get_short() {
      return (short)this.get_long(this.radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public byte get_byte(int radix) {
      return (byte)this.get_long(radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public byte get_byte() {
      return (byte)this.get_long(this.radix);
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public double get_double() {  
      try {   
         return super.get_double(); 
      }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public float get_float() {      
      return (float)this.get_double(); 
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public boolean get_boolean() {
      try{ 
         return super.get_boolean();
      } 
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public char get_char() {
      try {
         return super.get_char();
      }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   //-----------------------------------------------
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(ScannableI x) { 
      try{
         super.g(x);
         return this;
      }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   } 
// public final TxIStrStream g(StringRef s)  
      //: StringRef歸由ScanableI處理
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(StringBuilder s) { 
      try{   super.g(s);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(StringBuffer s) { 
      try{   super.g(s);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gn(StringRef s) { 
      try{   super.gn(s);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gn(StringBuilder s) { 
      try{   super.gn(s);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gn(StringBuffer s) { 
      try{   super.gn(s);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gn(MuStr s) { 
      try{   super.gn(s);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public TxIStrStream g(ScannableI[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(int[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gc(ScannableI x) { 
      try {  super.gc(x);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gc(StringRef x) { 
      try {  super.gc(x);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gc(StringBuilder x) { 
      try {  super.gc(x);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gc(StringBuffer x) { 
      try {  super.gc(x);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream gc(MuStr x) { 
      try {  super.gc(x);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(boolean[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(byte[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(short[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(char[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(long[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(float[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 並補報EOSException
   public final TxIStrStream g(double[] arr) { 
      try {  super.g(arr);  return this;   }
      catch(java.io.EOFException ioxpt) { 
         //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }


   @Override //: 以濾掉 IOException 並補報EOSException
   //public void scanf(String fStr, ScannableI... args) {  //: b4 A5.028L
   public void scanf(String fStr, Object... args) {  
      try{  super.scanf(fStr, args);  }
      catch(java.io.EOFException ioxpt) { //: EOFException is_a IOException
         throw new tw.fc.EOSException();
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

} 

