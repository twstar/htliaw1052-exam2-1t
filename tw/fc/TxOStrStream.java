package tw.fc ;
import java.io.IOException;
//import java.io.PrintStream;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
import tw.fc.re.RootMethod;

public class TxOStrStream extends TxOStream {

   private final java.io.CharArrayWriter caWriter;
     //: automatically grows when data is written to the stream. 
     //: The data can be retrieved using toCharArray() and toString(). 

   private final String lineSeparator= 
        System.getProperty("line.separator");
//      (String) java.security.AccessController.doPrivileged(
//          new sun.security.action.GetPropertyAction("line.separator")
//       );  //: ³o¦æ§Û¦Û PrintWriter.java

//I  boolean _startOfLine= true;

   //------------------------------------------
   public TxOStrStream() {
      super();  
      caWriter=new java.io.CharArrayWriter();
   }
   public TxOStrStream(int initSize) {
      super();  
      caWriter=new java.io.CharArrayWriter(initSize);
   }

   //------------------------------------------
   @Override
   public TxOStrStream flush() { caWriter.flush();  return this;  }
   @Override
   public void close() {  
   //   caWriter.close(); //: JDKDoc: has no effect
   }
   @RootMethod
   //public void reset() {  
   public void resetBuffer() {  
      caWriter.reset();
  //: Resets the buffer so that you can use it again 
  //: without throwing away the already allocated buffer.
   } 

   //-----------------------


   @Override
   public String toString() {  return caWriter.toString();   } 

   @RootMethod
   public char[] toCharArray() {  return caWriter.toCharArray();   } 

   @RootMethod
   public int	size()  {  
      return caWriter.size();
            //: Returns the current size of the buffer. 
   } 


   //------------------------------------------

   @Override //[ ÁY­È«¬
   public final TxOStrStream setRadix(int r) {
      super.setRadix(r);  return this;  
   }
   @Override //[ ÁY­È«¬
   public final TxOStrStream dec() { //: ªø®Ä
      super.dec(); return this; 
   }
   @Override //[ ÁY­È«¬
   public final TxOStrStream hex() {  //: ªø®Ä
      super.hex(); return this; 
   } 
   @Override //[ ÁY­È«¬
   public final TxOStrStream oct() {  //: ªø®Ä 
      super.oct(); return this; 
   }  
   @Override //[ ÁY­È«¬
   public final TxOStrStream bin() {  //: ªø®Ä
      super.bin(); return this; 
   }


   @Override //[ ÁY­È«¬
   public final TxOStrStream setDecimalPattern(String ptn) { //: ªø®Ä
      super.setDecimalPattern(ptn);  return this;
   } 
   @Override //[ ÁY­È«¬
   public final TxOStrStream sDP(String ptn) { //: ªø®Ä
      super.setDecimalPattern(ptn);  return this;
   } 
   @Override //[ ÁY­È«¬
   public final TxOStrStream setDecimalPattern() { //: ªø®Ä
      super.setDecimalPattern();  return this;
   }
   @Override //[ ÁY­È«¬
   public final TxOStrStream sDP() { //: ªø®Ä
      super.setDecimalPattern();  return this;
   }

   @Override //[ ÁY­È«¬
   public final TxOStrStream setBooleanPattern(String f, String t) { //: ªø®Ä
      super.setBooleanPattern(f, t);  return this;
   } 
   @Override //[ ÁY­È«¬
   public final TxOStrStream setBooleanPattern() { //: ªø®Ä
      super.setBooleanPattern();  return this;
   }

   @Override //[ ÁY­È«¬
   public final TxOStrStream showPositive(boolean show) { //: ªø®Ä
      super.showPositive(show);  return this;
   }

//I @Override public boolean startOfLine() 

//!!!
   @Override
   public final TxOStrStream p(String s) { 
      caWriter.write(s, 0, s.length());    
      if(s.length()>0) this._startOfLine=(s.charAt(s.length()-1)=='\n');   //: A5.023L
      return this;
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(StringBuilder s) { 
      try{   super.p(s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(StringBuffer s) { 
      try{   super.p(s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(boolean b) { 
      try{   super.p(b);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(char i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(long i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(int i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(short i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(byte i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(double i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(float i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(PrintableI x) { 
      try{   super.p(x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   //public final TxOStrStream p(String indent, PrintableI x) { 
   public final TxOStrStream dp(String indent, PrintableI x) { 
      try{   super.dp(indent, x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }



   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(int[] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(long[] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream p(int[][] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

//!!!S
   @Override
   public final TxOStrStream pn() {  
      this.p(lineSeparator);   this._startOfLine=true;  return this.flush(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(String x) { 
      this.p(x); return pn(); 
//      try{   super.pn(x);  return this;   }
//      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(StringBuilder x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(StringBuffer x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(boolean i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(byte i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(short i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(int i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(long i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(float i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(double i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(char i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(PrintableI x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(int[] x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(long[] x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pn(int[][] x) { 
      this.p(x); return pn(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps()  { 
      this.p(" ");  return this;  
//      try{   super.ps();  return this;   }
//      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(String x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(StringBuilder x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(StringBuffer x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(boolean i) { 
      this.p(i); return ps();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(byte i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(short i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(int i)  { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(long i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(float i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(double i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(char i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(PrintableI x) { 
      this.p(x);  return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(int[] x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(long[] x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream ps(int[][] x) { 
      this.p(x); return ps(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc()  { 
      this.p(","); return this;  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(String x) { 
      this.p(x); return pc();    
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(StringBuilder x) { 
      this.p(x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(StringBuffer x) { 
      this.p(x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(boolean i) { 
      this.p(i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(byte i) { 
      this.p(i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(short i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(int i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(long i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(float i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(double i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(char i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(PrintableI x) { 
      this.p(x);  return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(int[] x) { 
      this.p(x); return pc();    
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(long[] x) { 
      this.p(x); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pc(int[][] x) { 
      this.p(x); return pc(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs()  { 
      this.p(", ");  return this;
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(String x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(StringBuilder x) { 
      this.p(x);  return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(StringBuffer x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(boolean i) { 
      this.p(i); return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(byte i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(short i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(int i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(long i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(float i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(double i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(char i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(PrintableI x) { 
      this.p(x); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(int[] x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(long[] x) { 
      this.p(x);  return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pcs(int[][] x) { 
      this.p(x);  return pcs();
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt()  { 
      this.p("\t");  return this;  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(String x) { 
      this.p(x); return pt();   
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(StringBuilder x) { 
      this.p(x); return pt(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(StringBuffer x) { 
      this.p(x); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(boolean i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(byte i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(short i) { 
      try{   super.pt(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(int i) { 
      this.p(i); return pt();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(long i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(float i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(double i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(char i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(PrintableI x) { 
      this.p(x); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(int[] x) { 
      this.p(x); return pt(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(long[] x) { 
      this.p(x); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pt(int[][] x) { 
      this.p(x); return pt(); 
   }

   @Override //[ Âo±¼ IOException
   public TxOStrStream printf(String fStr, Object... args) { 
      try{   super.printf(fStr, args);  return this;  }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

 //[ §ï¥Î printf(...) --------------------------------
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOStrStream setFormat(
      String pre, String post, String last) 
   { //: ªø®Ä
      super.setFormat(pre, post, last);  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOStrStream setFormat(String pre, String post) { //: ªø®Ä
      super.setFormat(pre, post);  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOStrStream setFormat() { //: ªø®Ä
      super.setFormat();  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOStrStream sF(String pre, String post, String last) { 
    //: alias, ªø®Ä
      super.sF(pre, post, last);  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOStrStream sF(String pre, String post) { //: alias, ªø®Ä
      super.sF(pre, post, "");  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOStrStream sF() { //: alias, ªø®Ä
      super.sF();  return this;
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(String x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(StringBuilder x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(StringBuffer x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(boolean i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(byte i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(short i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(int i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(long i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(float i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(double i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(char i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(PrintableI x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(int[] x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(long[] x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pf(int[][] x) { 
      return p(_preF).p(x).p(_postF); 
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(String x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(StringBuilder x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(StringBuffer x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(boolean i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(byte i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(short i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(int i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(long i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(float i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(double i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(char i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(PrintableI x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(int[] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(long[] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream pfe(int[][] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
 //] --------------------------------

   @Override //: ÁY­È«¬
   public final TxOStrStream setLeftAdjustment() { //: ¾a¥ª¹ï»ô, ªø®Ä
      super.setLeftAdjustment();  return this;   
   }
   @Override //: ÁY­È«¬
   public final TxOStrStream setRightAdjustment() { //: ¾a¥k¹ï»ô, ªø®Ä
      super.setRightAdjustment();  return this;   
   }
   @Override //: ÁY­È«¬
   public final TxOStrStream setInternalAdjustment() { //: ¾a¤¤¹ï»ô, ªø®Ä
      super.setInternalAdjustment();  return this;   
   }
   @Override //: ÁY­È«¬
   public final TxOStrStream setFill(char f) { //: ªø®Ä
      super.setFill(f);  return this;   
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, String s) { 
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, StringBuilder s) { //: ­«­n
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, StringBuffer s) { //: ­«­n
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, char i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w) {  
      try{   super.wp(w);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, boolean b) {
      try{   super.wp(w,b);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, int i)  { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, byte i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, short i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, long i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, float i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, double i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wp(int w, WidthPrintableI x) { 
      try{   super.wp(w,x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w) { 
      this.wp(w);  return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, boolean i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, byte i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, short i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, int i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, long i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, float i) { 
      this.wp(w,i); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, double i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, char i) { 
      this.wp(w,i); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, String x) { 
      this.wp(w,x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, StringBuilder x) { 
      this.wp(w,x); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, StringBuffer x) { 
      this.wp(w,x); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpn(int w, WidthPrintableI x) { 
      this.wp(w,x);  return pn(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w) { 
      wp(w);  return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, String x) { 
      wp(w,x); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, StringBuilder x) { 
      wp(w,x); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, StringBuffer x) { 
      wp(w,x); return ps();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, boolean i) { 
      wp(w,i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, byte i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, short i) { 
      wp(w,i); return ps();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, int i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, long i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, float i) { 
      wp(w,i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, double i) { 
      wp(w,i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, char i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wps(int w, WidthPrintableI x) { 
      wp(w,x);  return ps();
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w) { 
      wp(w);  return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, String x) { 
      wp(w,x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, StringBuilder x) { 
      wp(w,x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, StringBuffer x) { 
      wp(w,x); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, boolean i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, byte i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, short i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, int i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, long i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, float i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, double i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, char i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpc(int w, WidthPrintableI x) { 
      wp(w,x);  return pc();
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, String x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, StringBuilder x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, StringBuffer x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, boolean x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, byte x)  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, short x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, int x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, long x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, float x)  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, double x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, char x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpf(int w, WidthPrintableI x) { 
      return p(_preF).wp(w,x).p(_postF);
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, String x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, StringBuilder x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, StringBuffer x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, boolean x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, byte x)  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, short x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, int x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, long x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, float x)  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, double x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, char x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOStrStream wpfe(int w, WidthPrintableI x) { 
      return p(_preF).wp(w,x).p(_lastF);
   }


}

//============================================================
