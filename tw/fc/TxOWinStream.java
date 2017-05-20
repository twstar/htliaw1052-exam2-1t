package tw.fc ;
   import java.io.PrintStream;
   import java.io.IOException;

//[ modified from TxOCStream
public class TxOWinStream extends TxOStream {

      //-----------------------------------------------------------------

   private WOConsole _ooo ;   

//I  boolean _startOfLine= true;

   //------------------------------------------
   protected  TxOWinStream(WOConsole o) {  super();  _ooo=o; } 

   //------------------------------------------
   @Override
   public final TxOWinStream flush() {  _ooo.flush();  return this;  }

   @Override
   public void close() {   ;   }

   //------------------------------------------
   @Override
   public String toString() {
//      if(this==Std.cout) return "Std.cout";
//      if(this==Std.cerr) return "Std.cerr";
      return "TxOWinStream("+_ooo+")";    
   }

   //------------------------------------------

   @Override //[ ÁY­È«¬
   public final TxOWinStream setRadix(int r) {
      super.setRadix(r);  return this;  
   }
   @Override //[ ÁY­È«¬
   public final TxOWinStream dec() { //: ªø®Ä
      super.dec(); return this; 
   }
   @Override //[ ÁY­È«¬
   public final TxOWinStream hex() {  //: ªø®Ä
      super.hex(); return this; 
   } 
   @Override //[ ÁY­È«¬
   public final TxOWinStream oct() {  //: ªø®Ä 
      super.oct(); return this; 
   }  
   @Override //[ ÁY­È«¬
   public final TxOWinStream bin() {  //: ªø®Ä
      super.bin(); return this; 
   }

   @Override //[ Âo±¼ IOException
   //public void printf(String fStr, Object... args) { 
   public TxOWinStream printf(String fStr, Object... args) { 
      try{   super.printf(fStr, args);   return this;  }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //[ ÁY­È«¬
   public final TxOWinStream setDecimalPattern(String ptn) { //: ªø®Ä
      super.setDecimalPattern(ptn);  return this;
   } 
   @Override //[ ÁY­È«¬
   public final TxOWinStream sDP(String ptn) { //: ªø®Ä
      super.setDecimalPattern(ptn);  return this;
   } 
   @Override //[ ÁY­È«¬
   public final TxOWinStream setDecimalPattern() { //: ªø®Ä
      super.setDecimalPattern();  return this;
   }
   @Override //[ ÁY­È«¬
   public final TxOWinStream sDP() { //: ªø®Ä
      super.setDecimalPattern();  return this;
   }

   @Override //[ ÁY­È«¬
   public final TxOWinStream setBooleanPattern(String f, String t) { //: ªø®Ä
      super.setBooleanPattern(f, t);  return this;
   } 
   @Override //[ ÁY­È«¬
   public final TxOWinStream setBooleanPattern() { //: ªø®Ä
      super.setBooleanPattern();  return this;
   }

   @Override //[ ÁY­È«¬
   public final TxOWinStream showPositive(boolean show) { //: ªø®Ä
      super.showPositive(show);  return this;
   }

//I @Override public boolean startOfLine() 

//!!!
   @Override //: ¹ê§@¨ÃÂo±¼ IOException, ÁY­È«¬
   public final TxOWinStream p(String s) { 
      _ooo.print(s);   
      if(s.length()>0) this._startOfLine=(s.charAt(s.length()-1)=='\n');   //: A5.023L
      return this;
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(StringBuilder s) { 
      try{   super.p(s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(StringBuffer s) { 
      try{   super.p(s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(boolean b) { 
      try{   super.p(b);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(char i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(long i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(int i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(short i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(byte i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(double i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(float i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(PrintableI x) { 
      try{   super.p(x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   //public final TxOWinStream p(String indent, PrintableI x) { 
   public final TxOWinStream dp(String indent, PrintableI x) { 
      try{   super.dp(indent, x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(int[] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(long[] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream p(int[][] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

//!!!
   @Override //: ¹ê§@¨ÃÂo±¼ IOException, ÁY­È«¬
   public final TxOWinStream pn() { 
      _ooo.println();  //: ¦bPrintStream¬O©I¥s¨äprivate method newLine()
      this._startOfLine=true;
      return this.flush();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(String x) { 
      this.p(x); return pn(); 
//      try{   super.pn(x);  return this;   }
//      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(StringBuilder x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(StringBuffer x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(boolean i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(byte i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(short i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(int i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(long i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(float i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(double i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(char i) { 
      this.p(i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(PrintableI x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(int[] x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(long[] x) { 
      this.p(x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pn(int[][] x) { 
      this.p(x); return pn(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps()  { 
      this.p(" ");  return this;  
//      try{   super.ps();  return this;   }
//      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(String x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(StringBuilder x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(StringBuffer x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(boolean i) { 
      this.p(i); return ps();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(byte i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(short i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(int i)  { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(long i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(float i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(double i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(char i) { 
      this.p(i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(PrintableI x) { 
      this.p(x);  return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(int[] x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(long[] x) { 
      this.p(x); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream ps(int[][] x) { 
      this.p(x); return ps(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc()  { 
      this.p(","); return this;  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(String x) { 
      this.p(x); return pc();    
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(StringBuilder x) { 
      this.p(x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(StringBuffer x) { 
      this.p(x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(boolean i) { 
      this.p(i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(byte i) { 
      this.p(i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(short i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(int i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(long i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(float i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(double i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(char i) { 
      this.p(i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(PrintableI x) { 
      this.p(x);  return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(int[] x) { 
      this.p(x); return pc();    
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(long[] x) { 
      this.p(x); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pc(int[][] x) { 
      this.p(x); return pc(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs()  { 
      this.p(", ");  return this;
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(String x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(StringBuilder x) { 
      this.p(x);  return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(StringBuffer x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(boolean i) { 
      this.p(i); return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(byte i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(short i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(int i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(long i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(float i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(double i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(char i) { 
      this.p(i); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(PrintableI x) { 
      this.p(x); return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(int[] x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(long[] x) { 
      this.p(x);  return pcs();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pcs(int[][] x) { 
      this.p(x);  return pcs();
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt()  { 
      this.p("\t");  return this;  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(String x) { 
      this.p(x); return pt();   
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(StringBuilder x) { 
      this.p(x); return pt(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(StringBuffer x) { 
      this.p(x); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(boolean i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(byte i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(short i) { 
      try{   super.pt(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(int i) { 
      this.p(i); return pt();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(long i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(float i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(double i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(char i) { 
      this.p(i); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(PrintableI x) { 
      this.p(x); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(int[] x) { 
      this.p(x); return pt(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(long[] x) { 
      this.p(x); return pt();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pt(int[][] x) { 
      this.p(x); return pt(); 
   }

 //[ §ï¥Î printf(...)  -----------------------------
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOWinStream setFormat(
      String pre, String post, String last) 
   { //: ªø®Ä
      super.setFormat(pre, post, last);  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOWinStream setFormat(String pre, String post) { //: ªø®Ä
      super.setFormat(pre, post);  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOWinStream setFormat() { //: ªø®Ä
      super.setFormat();  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOWinStream sF(String pre, String post, String last) { 
    //: alias, ªø®Ä
      super.sF(pre, post, last);  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOWinStream sF(String pre, String post) { //: alias, ªø®Ä
      super.sF(pre, post, "");  return this;
   }
   @Deprecated @Override //[ ÁY­È«¬
   public final TxOWinStream sF() { //: alias, ªø®Ä
      super.sF();  return this;
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(String x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(StringBuilder x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(StringBuffer x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(boolean i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(byte i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(short i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(int i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(long i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(float i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(double i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(char i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(PrintableI x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(int[] x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(long[] x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pf(int[][] x) { 
      return p(_preF).p(x).p(_postF); 
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(String x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(StringBuilder x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(StringBuffer x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(boolean i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(byte i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(short i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(int i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(long i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(float i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(double i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(char i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(PrintableI x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(int[] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(long[] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream pfe(int[][] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
 //] §ï¥Î printf(...)  -----------------------------

   @Override //: ÁY­È«¬
   public final TxOWinStream setLeftAdjustment() { //: ¾a¥ª¹ï»ô, ªø®Ä
      super.setLeftAdjustment();  return this;   
   }
   @Override //: ÁY­È«¬
   public final TxOWinStream setRightAdjustment() { //: ¾a¥k¹ï»ô, ªø®Ä
      super.setRightAdjustment();  return this;   
   }
   @Override //: ÁY­È«¬
   public final TxOWinStream setInternalAdjustment() { //: ¾a¤¤¹ï»ô, ªø®Ä
      super.setInternalAdjustment();  return this;   
   }
   @Override //: ÁY­È«¬
   public final TxOWinStream setFill(char f) { //: ªø®Ä
      super.setFill(f);  return this;   
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, String s) { 
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, StringBuilder s) { //: ­«­n
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, StringBuffer s) { //: ­«­n
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, char i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w) {  
      try{   super.wp(w);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, boolean b) {
      try{   super.wp(w,b);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, int i)  { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, byte i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, short i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, long i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, float i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, double i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wp(int w, WidthPrintableI x) { 
      try{   super.wp(w,x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w) { 
      this.wp(w);  return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, boolean i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, byte i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, short i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, int i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, long i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, float i) { 
      this.wp(w,i); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, double i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, char i) { 
      this.wp(w,i); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, String x) { 
      this.wp(w,x); return pn(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, StringBuilder x) { 
      this.wp(w,x); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, StringBuffer x) { 
      this.wp(w,x); return pn();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpn(int w, WidthPrintableI x) { 
      this.wp(w,x);  return pn(); 
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w) { 
      wp(w);  return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, String x) { 
      wp(w,x); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, StringBuilder x) { 
      wp(w,x); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, StringBuffer x) { 
      wp(w,x); return ps();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, boolean i) { 
      wp(w,i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, byte i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, short i) { 
      wp(w,i); return ps();  
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, int i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, long i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, float i) { 
      wp(w,i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, double i) { 
      wp(w,i); return ps(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, char i) { 
      wp(w,i); return ps();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wps(int w, WidthPrintableI x) { 
      wp(w,x);  return ps();
   }

   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w) { 
      wp(w);  return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, String x) { 
      wp(w,x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, StringBuilder x) { 
      wp(w,x); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, StringBuffer x) { 
      wp(w,x); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, boolean i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, byte i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, short i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, int i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, long i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, float i) { 
      wp(w,i); return pc(); 
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, double i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, char i) { 
      wp(w,i); return pc();
   }
   @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpc(int w, WidthPrintableI x) { 
      wp(w,x);  return pc();
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, String x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, StringBuilder x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, StringBuffer x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, boolean x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, byte x)  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, short x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, int x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, long x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, float x)  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, double x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, char x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpf(int w, WidthPrintableI x) { 
      return p(_preF).wp(w,x).p(_postF);
   }

   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, String x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, StringBuilder x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, StringBuffer x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, boolean x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, byte x)  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, short x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, int x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, long x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, float x)  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, double x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, char x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: Âo IOException, ÁY­È«¬
   public final TxOWinStream wpfe(int w, WidthPrintableI x) { 
      return p(_preF).wp(w,x).p(_lastF);
   }



}

//============================================================
