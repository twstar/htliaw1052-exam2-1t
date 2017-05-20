package tw.fc ;
import java.io.IOException;
import java.io.InputStream;
//import java.io.EOFException;
//import java.io.Reader;
import java.io.InputStreamReader;
import tw.fc.re.RootMethod;

//**************    TxICStream.java   ****************

public class TxICStream 
   extends TxIStream   
   implements AutoReinputableI
{

// public static final TxICStream CIN
//                        =new TxICStream(System.in);  
//                        //: 已改為 Std.cin

   //=====================================================
   private boolean autoCatch=true; 
     //: auto catch 在cascated input時只對錯的部份redo,
     //: 這在多個同型輸入時可能會使user陷入慌張, 
     //: 對此,客戶程式應setAutoCatch(false)並自行catch.
   @Override
   //public final void setAutoCatch(boolean b) {  this.autoCatch=b;  }
   public final void setAutoReInput(boolean b) {  this.autoCatch=b;  }
   @Override
   //public final boolean getAutoCatch() {  return this.autoCatch;  }
   public final boolean getAutoReInput() {  return this.autoCatch;  }

//I  protected Reader iii=null;

   //-------------------------------------
   //public 
   TxICStream(InputStream i) { //:  System.in 是 InputStream
      super();
      super.iii=new InputStreamReader(i);   
          //: 這給console或string用, 不必buffered.  cf. => TxIFStream
   } 

   //---------------------------------------
   @Override
   public String toString() {
      if(this==Std.cin) return "Std.cin";
      return "TxICStream("+super.iii+")";    
   }
   @Override //: 以縮小函數值型態
   public TxICStream hex() {  radix=16;  return this; }
   @Override //: 以縮小函數值型態
   public TxICStream dec() {  radix=10;  return this; } 
   @Override //: 以縮小函數值型態
   public TxICStream oct() {  radix=8;   return this; } 
   @Override //: 以縮小函數值型態
   public TxICStream bin() {  radix=2;   return this; } 
   @Override //: 以縮小函數值型態
   public TxICStream setRadix(int r) {
      if(2<=r && r<=36) {  radix=r;   return this;  }
      throw new IllegalArgumentException("radix error: "+r);
   }
   @Override //: 以縮小函數值型態
   public TxICStream setBooleanPattern(String f, String t) { //: 長效
      super.setBooleanPattern(f,t);  return this;
   }
   @Override //: 以縮小函數值型態
   public TxICStream setBooleanPattern() { //: 長效
      super.setBooleanPattern("false", "true");  return this;
   }


   //---------------------------------------

   // @Override  //: 以濾掉 IOException 並補報EOSException
   // //public void unread(int ch) {
   // public void unread(char ch) {
   //    //try {  
   //       super.unread(ch);  
   //    //}
   //    //catch(IOException xpt) { throw new StateError("\n"+xpt);  } //: b4 A%.033L
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


   @Override //: 以濾掉 IOException
   public int read() {  
   //D System.err.println("TxICStream::read()");
      try{  return super.read();  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public int get() {  
      try {  return super.get();  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   //[ 立即式讀一個char. 不會syntax error, 沒有undo的問題
   //[ 用 g(s) 會autoskipWS, read(s)不會
   @Override //: 濾掉 IOException 
   public final TxICStream read(charRef s) { // throws IOException {
      try {  super.read(s);   return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public   //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream read(String prompt, charRef s) { 
      if(prompt!=null) {  System.err.print(prompt);  }
      this.read(s);   return this;
   }

   @Override //: 以濾掉 IOException
   public int read(char[] dest, int offset, int count) {
      try {  return super.read(dest, offset, count);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public int read(char[] dest) { 
      try {  return super.read(dest);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public long skip(long count) { 
      try {  return super.skip(count);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public long ignore(long count) { 
      try {  return super.ignore(count);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   //@Override //: 以濾掉 IOException
   //public TxICStream skipSpHt() { 
   //   try {  super.skipSpHt();  return this;  }
   //   catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   //} //: b4 A5.036
   @Override //: 以濾掉 IOException
   public TxICStream skipWS() { 
      try {  super.skipWS();  return this; }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public TxICStream skipWS_inLine() { 
      try {  super.skipWS_inLine();  return this; }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Deprecated 
   @Override //: 以濾掉 IOException
   //public TxICStream skipToEOL(boolean show) { //: b4 A5.012.G
   public TxICStream skipToEOL(TxOStream oS) { 
      //try {  super.skipToEOL(show);  return this;  }
      try {  super.skipToEOL(oS);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   // //public TxICStream skipToEOL(boolean show) { //: b4 A5.012.G
   // public TxICStream skipToEOL(TxOStream oS) { 
   public TxICStream skipCurrentLine(TxOStream oS) { 
      //try {  super.skipToEOL(show);  return this;  }
      try {  super.skipCurrentLine(oS);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   public TxICStream skipCurrentLine() { 
      return this.skipCurrentLine(Std.cerr);
   }

   @Override //: 以濾掉 IOException
   public int peek() {
      try {  return super.peek();  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException, 並暫關 autoReinput
   public boolean probe(int expected) { 
      try {
         boolean ans= super.probe(expected);  
         return ans;
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException 
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

   @Override //: 以濾掉 IOException, 並暫關 autoReinput
   //public boolean probe(String fStr, Object... args) {  //: b4 A5.038.C
   public boolean probef(String fStr, Object... args) {
      try{
         boolean oldAuto=this.getAutoReInput();
         this.setAutoReInput(false);
         boolean ans=  super.probef(fStr, args);  
         this.setAutoReInput(oldAuto);
         return ans;
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }


   @Override //: 以濾掉 IOException, 並做 autoReinput
   public final TxICStream expect(int expected) { 
      try {  
         while(true) {  //: redo until format correct
            try {
               super.expect(expected);  return this;  
            }
            catch(tw.fc.TxInputException xpt) {
               if(this.getAutoReInput()) {
                  skipCurrentLine(); 
                  Std.cerr.p("\nError on expect \'"+(char)expected)
                          .pn("\'. Please Redo:");
               }
               else {   throw xpt;   }
            }
         }
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException, 並做 autoReinput
   public final TxICStream expectWS() { 
      try {  
         while(true) {  //: redo until format correct
            try {
               super.expectWS();  return this;  
            }
            catch(tw.fc.TxInputException xpt) {
               if(this.getAutoReInput()) {
                  skipCurrentLine(); 
                  Std.cerr.p("\nError on expect WS")
                          .pn("\'. Please Redo:");
               }
               else {   throw xpt;   }
            }
         }
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException, 並做 autoReinput
   public final TxICStream expectWS_inLine() { 
      try {  
         while(true) {  //: redo until format correct
            try {
               super.expectWS_inLine();  return this;  
            }
            catch(tw.fc.TxInputException xpt) {
               if(this.getAutoReInput()) {
                  skipCurrentLine(); 
                  Std.cerr.p("\nError on expect WS")
                          .pn("\'. Please Redo:");
               }
               else {   throw xpt;   }
            }
         }
      }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException
   public String getLine() { 
      try {  return super.getLine();  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public String getLine(String prompt) { 
      if(prompt!=null) System.out.print(prompt);
      return this.getLine();
   }

   @Override //: 以濾掉 IOException
   public String getToken(char delimiter) { 
      try {  return super.getToken(delimiter);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   //-----------------------------------------------------------
   @Override //: 以濾掉 IOException
   public String getString() { 
      try{    return super.getString();  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   //[ 被 get_long(String prompt),  get_long(int radix), get_long() 呼叫
   @RootMethod  //: TxICStream才有prompt, prompt null時不印
   public long get_long(String prompt, int radix) {
      //: 因為要autoCatch, 所以
      //: 不是 get_long(String, int)呼叫get_long(int)
      while(true) { //: redo until format correct
         try{
            if(prompt!=null) {  Std.cerr.p(prompt);  }
            return super.get_long(radix); 
         }
         catch(tw.fc.TxInputException xpt) {
            if(this.getAutoReInput()) {
               skipCurrentLine(); 
               Std.cerr.p("\nFormat error on integer")
                       .pn(". Please Redo:");
            }
            else {   throw xpt;   }
         }
         catch(IOException xpt) {
            throw new StateError("\n"+xpt);
         }
      }
   }
   @RootMethod
   public long get_long(String prompt) {   
      return this.get_long(prompt,this.radix);  
   }
   @Override //: 濾掉 IOException並支援autoCatch
   public final long get_long(int radix) { 
      return this.get_long(null, radix);
   }
   @Override //: 濾掉 IOException 並支援autoCatch
   public final long get_long() {  
      return this.get_long(null, this.radix) ;    
   }

   @RootMethod
   public int get_int(String prompt, int radix) {  
      return (int)this.get_long(prompt,radix);  
   }
   @RootMethod
   public int get_int(String prompt) {   
      return (int)this.get_long(prompt, this.radix);     
   }

   @Override //: 濾掉 IOException 並支援autoCatch
   public final int get_int(int radix) {  
      return (int)get_long(null,radix);  
   }
   @Override //: 濾掉 IOException 並支援autoCatch
   public final int get_int() {    
      return (int)get_long(null,this.radix);  
   }

   @RootMethod
   public short get_short(String prompt, int radix) {  
      return (short)this.get_long(prompt,radix);  
   }
   @RootMethod
   public short get_short(String prompt) {    
      return (short)this.get_long(prompt,this.radix);   
   }

   @Override //: 濾掉 IOException 並支援autoCatch
   public final short get_short(int radix) { 
      return (short)this.get_long(null,radix);  
   }
   @Override //: 濾掉 IOException 並支援autoCatch
   public final short get_short() {  
      return (short)this.get_long(null,this.radix);  
   }

   @Override //: 濾掉 IOException 並支援autoCatch
   public final byte get_byte(int radix) { 
      return (byte)this.get_long(null,radix);  
   }
   @Override //: 濾掉 IOException 並支援autoCatch
   public final byte get_byte() { 
      return (byte)this.get_long(null,this.radix);  
   }

   //[ floating 不管 radix
   @RootMethod
   public double get_double(String prompt) { 
      //: 因為要autoCatch, 所以
      //: 不是getDouble(String)呼叫getDouble()
      while(true) { //: redo until format correct
         try{
            if(prompt!=null) {  System.err.print(prompt);  }
            return super.get_double(); 
         }
         catch(tw.fc.TxInputException xpt) {
            if(this.getAutoReInput()) {
               skipCurrentLine(); 
               Std.cerr.p("\nFormat error on floating number")
                       .pn(". Please Redo:");
            }
            else {   throw xpt;   }
         }
         catch(IOException xpt) {
            throw new StateError("\n"+xpt);
         }
      }
   }
   @Override //: 濾掉 IOException並支援autoCatch
   public final double get_double() {  
      return this.get_double(null); 
   }

   @RootMethod
   public double get_float(String prompt) { 
      return (float)this.get_double(prompt);  
   }
   @Override //: 以濾掉 IOException
   public final float get_float() {  
      return (float)this.get_double(null);  
   }

   @RootMethod
   public boolean get_boolean(String prompt) {  
      //: 因為要autoCatch, 所以
      //: 不是getBoolean(String)呼叫getBoolean()
      while(true) { //: redo until format correct
         try{
            if(prompt!=null) {  System.err.print(prompt);  }
            return super.get_boolean(); 
         }
         catch(tw.fc.TxInputException xpt) {
            if(this.getAutoReInput()) {
               skipCurrentLine(); 
               Std.cerr.p("\nFormat error on ")
                       .p("boolean").pn(". Please Redo:");
            }
            else {   throw xpt;   }
         }
         catch(IOException xpt) {
            throw new StateError("\n"+xpt);
         }
      }
   }

   @Override //: 濾掉 IOException並支援autoCatch
   public boolean get_boolean() {  
      return this.get_boolean(null); 
   }

   @RootMethod
   public char get_char(String prompt) {  
   //: getChar不會format error, 所以沒有autoCatch的問題 
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.get_char();  
   }
   @Override //: 以濾掉 IOException
   public char get_char() { 
      try{    return ( super.get_char() ); }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public void getToken(StringBuilder sb, char delimiter) {
      try{ //: 不支援autoCatch
         super.getToken(sb, delimiter);
      }
      catch(IOException ioxpt) {
         throw new StateError("\n"+ioxpt);
      }
   }  

   //-----------------------------------------------------------

   //[ called by this.g(ScannableI x)
   //[ ScannableI 已含 intRef, doubleRef 等
   @RootMethod  //: TxICStream才有prompt
   public final TxICStream g(String prompt, ScannableI x) {  
   //D System.err.println("TxICStream::g(String prompt, ScannableI x)");
      // redo時要重印prompt, 所以
      // 不是由 g(String,Scanable)呼叫g(Scanable)
      try{
         while(true) {  //: 支援autoCatch
            try{
               if(prompt!=null) {  System.err.print(prompt);  }
               super.g(x);  return this;
            }
            catch(tw.fc.TxInputException xpt) {
               if(this.getAutoReInput()) {
                  skipCurrentLine(); 
                  Std.cerr.p("\nFormat error on ")
                          .p(x.getClass().toString())
                          .pn(". Please Redo:");
               }
               else {   throw xpt;   }
            }
         }
      }
      catch(IOException ioxpt) {
         throw new StateError("\n"+ioxpt);
      }
   } 
   @Override //: 以濾掉 IOException並支援autoCatch
   public final TxICStream g(ScannableI x) { //: 已含 intRef, doubleRef 等
      return this.g(null, x); 
   } 

// public final TxICStream g(StringRef s) //: StringRef歸由ScanableI處理

   @RootMethod 
   public final TxICStream g(String prompt, StringBuilder x) { 
   //: 字串不會syntax error, 沒有undo的問題
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.g(x);
//[ 想錯了
//      //: StringBuilder不是ScanableI, 又不想抄一遍, 所以包一層起來用.
//      final StringBuilderRef wrap=new StringBuilderRef(x);
//      this.g(prompt, wrap);
//      return this;
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(StringBuilder x) { 
   //: 字串不會syntax error, 沒有undo的問題
      try{   super.g(x);  return this;   }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   //[ 不建議使用StringBuffer
   @RootMethod
   public final TxICStream g(String prompt, StringBuffer x) { 
   //: 字串不會syntax error, 沒有undo的問題
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.g(x);
   }
   //[ 不建議使用StringBuffer
   @Override //: 以濾掉 IOException並支援autoCatch
   public final TxICStream g(StringBuffer x) { 
   //: 字串不會syntax error, 沒有undo的問題
      try{   super.g(x);  return this;   }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @Override //: 以濾掉 IOException
   public final TxICStream gn(StringRef s) { 
      //: 不會syntax error, 沒有undo的問題
      try{   super.gn(s);  return this;   }
      catch(IOException no_check) {  
         throw new StateError("\n"+no_check);  
      }
   }
   @Override //: 以濾掉 IOException
   public final TxICStream gn(StringBuilder s) { 
      //: 不會syntax error, 沒有undo的問題
      try{   super.gn(s);  return this;   }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public final TxICStream gn(StringBuffer s) { 
      try{   super.gn(s);  return this;   }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public final TxICStream gn(MuStr s) { 
      try{   super.gn(s);  return this;   }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

// public final TxICStream g(String prompt, ScannableI x)  
              //:  錯! 字串才會有 gn

  //>>> confusing-prone when the prompt contains '%'  
   @RootMethod
   public final TxICStream gn(String prompt, StringRef s) {  
      //: 專門用來讀一整行的string  (只對string才有gn)
      //: 不會syntax error, 沒有undo的問題
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.gn(s);
   }
   @RootMethod
   public final TxICStream gn(String prompt, StringBuilder s) {  
      //: 專門用來讀一整行的string  (只對string才有gn)
      //: 不會syntax error, 沒有undo的問題
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.gn(s);
   }
   @RootMethod
   public final TxICStream gn(String prompt, StringBuffer s) {  
      //: 專門用來讀一整行的string  (只對string才有gn)
      //: 不會syntax error, 沒有undo的問題
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.gn(s);
   }
   @RootMethod
   public final TxICStream gn(String prompt, MuStr s) {  
      //: 專門用來讀一整行的string  (只對string才有gn)
      //: 不會syntax error, 沒有undo的問題
      if(prompt!=null) {  System.err.print(prompt);  }
      return this.gn(s);
   }

   @Override //: 以濾掉 IOException
   public TxICStream g(ScannableI[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @Override //: 以濾掉 IOException
   public final TxICStream g(int[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public final TxICStream gc(String prompt, ScannableI x) {
      //! 物件後的逗點沒必要redo. 
      //! 物件內的逗點在expect失敗時會丟tw.fc.TxInputException  
      this.g(prompt, x);  this.expect(',');  return this;
   }
   @Override //: 以濾掉 IOException
   public final TxICStream gc(ScannableI x) { 
      return this.gc(null, x);  // super.gc(x) 不auto catch
   }

// public final TxICStream gc(String prompt, MuStr x) //: MuStr 已Scanable 
// public final TxICStream gc(MuStr x) //: MuStr 已Scanable 
// public final TxICStream gc(String prompt, StringRef x) //: StringRef 已Scanable 

   @RootMethod
   public final TxICStream gc(String prompt, StringBuilder x) { 
      this.g(prompt, x);  this.expect(',');  return this;
   }
// public final TxICStream gc(StringRef x)  //: StringRef 已Scanable 
   @Override //: 以濾掉 IOException並支援autoCatch
   public final TxICStream gc(StringBuilder x) { 
      return this.gc(null,x);  
   }
   @RootMethod
   public final TxICStream gc(String prompt, StringBuffer x) { 
      this.g(prompt, x);  this.expect(',');  return this;
   }
   @Override //: 以濾掉 IOException並支援autoCatch
   public final TxICStream gc(StringBuffer x) { 
      return this.gc(null,x);  
   }

   //------------
   @Override //: 以濾掉 IOException
   // //public void scanf(String fStr, Object... args) { //: b4 A5.012E 
   // public void scanf(String fStr, ScannableI... args) { //: b4 A5.028L  
   public void scanf(String fStr, Object... args) { // 
      try{  super.scanf(fStr, args);  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }

   @RootMethod
   public final TxICStream g(String prompt, ScannableI[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         this.g(indexPrompt, arr[i]);
      }
      return this;
   }
   @RootMethod
   public final TxICStream g(String prompt, int[] arr) { 
   //:  get an array of integer
      if(prompt!=null) { System.err.print(prompt);  }
      final intRef x=new intRef();  /// final MuInt x=new MuInt();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(boolean[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public   //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, boolean[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      final booleanRef x=new booleanRef();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(byte[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public   //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, byte[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      final byteRef x=new byteRef(); 
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(short[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public  //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, short[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      final shortRef x=new shortRef(); ///// final MuSht x=new MuSht();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(char[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public   //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, char[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      final charRef x=new charRef();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(long[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public  //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, long[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      final longRef x=new longRef();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(float[] arr) { 
      try {  super.g(arr);  return this;  }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public  //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, float[] arr) { 
      if(prompt!=null) { System.err.print(prompt);  }
      final floatRef x=new floatRef();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 
   @Override //: 以濾掉 IOException
   public final TxICStream g(double[] arr) { 
      try {  super.g(arr);  return this;   }
      catch(IOException xpt) { throw new StateError("\n"+xpt);  }
   }
   @RootMethod
   public   //: prompt 只該由cin設, 但為cascated還是要public
   final TxICStream g(String prompt, double[] arr) {
      if(prompt!=null) { System.err.print(prompt);  }
      final doubleRef x=new doubleRef();
      for(int i=0; i<arr.length; i++) {
         final String indexPrompt=((prompt!=null)? ("["+i+"]:") : null);
         g(indexPrompt, x);  //: 會auto catch  
         arr[i]=x.v;   
      }
      return this; 
   } 


   //--------------------------------------------


}