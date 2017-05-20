package tw.fc ;
   import java.io.PrintStream;
   import java.io.IOException;

public 
class TxOCStream extends TxOStream {

// ��b Std.java ���K�[ Std.cout �� Std.cerr

   //-----------------------------------------------------------------

   private PrintStream  _ooo ;   
        //: System.out�OPrintStream, �ҥH�o�̤����Writer
        //: PrintStream���˳]��OutputStreamWriter��BufferedWriter 

//I  boolean _startOfLine=true;  

   //------------------------------------------
   //public  TxOCStream(PrintStream o) {  super();  _ooo=o; } 
   protected  TxOCStream(PrintStream o) {  super();  _ooo=o; } 

   //------------------------------------------
   @Override
   public final TxOCStream flush() {  _ooo.flush();  return this;  }

   @Override
   public void close() {   ;   }

   //------------------------------------------
   @Override
   public String toString() {
      if(this==Std.cout) return "Std.cout";
      if(this==Std.cerr) return "Std.cerr";
      return "TxOCStream("+_ooo+")";    
   }

   //------------------------------------------

   @Override //[ �Y�ȫ�
   public final TxOCStream setRadix(int r) {
      super.setRadix(r);  return this;  
   }
   @Override //[ �Y�ȫ�
   public final TxOCStream dec() { //: ����
      super.dec(); return this; 
   }
   @Override //[ �Y�ȫ�
   public final TxOCStream hex() {  //: ����
      super.hex(); return this; 
   } 
   @Override //[ �Y�ȫ�
   public final TxOCStream oct() {  //: ���� 
      super.oct(); return this; 
   }  
   @Override //[ �Y�ȫ�
   public final TxOCStream bin() {  //: ����
      super.bin(); return this; 
   }

   @Override //[ �o�� IOException
   //public void printf(String fStr, Object... args) { 
   public TxOCStream printf(String fStr, Object... args) { 
      try{   super.printf(fStr, args);  return this;  }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }


   @Override //[ �Y�ȫ�
   public final TxOCStream setDecimalPattern(String ptn) { //: ����
      super.setDecimalPattern(ptn);  return this;
   } 
   @Override //[ �Y�ȫ�
   public final TxOCStream sDP(String ptn) { //: ����
      super.setDecimalPattern(ptn);  return this;
   } 
   @Override //[ �Y�ȫ�
   public final TxOCStream setDecimalPattern() { //: ����
      super.setDecimalPattern();  return this;
   }
   @Override //[ �Y�ȫ�
   public final TxOCStream sDP() { //: ����
      super.setDecimalPattern();  return this;
   }

   @Override //[ �Y�ȫ�
   public final TxOCStream setBooleanPattern(String f, String t) { //: ����
      super.setBooleanPattern(f, t);  return this;
   } 
   @Override //[ �Y�ȫ�
   public final TxOCStream setBooleanPattern() { //: ����
      super.setBooleanPattern();  return this;
   }

   @Override //[ �Y�ȫ�
   public final TxOCStream showPositive(boolean show) { //: ����
      super.showPositive(show);  return this;
   }


//I @Override public boolean startOfLine() 

//!!!
   @Override //: ��@���o�� IOException, �Y�ȫ�
   public final TxOCStream p(String s) { 
      _ooo.print(s);    
      if(s.length()>0) this._startOfLine=(s.charAt(s.length()-1)=='\n');   //: A5.023L
      return this;
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(StringBuilder s) { 
      try{   super.p(s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(StringBuffer s) { 
      try{   super.p(s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(boolean b) { 
      try{   super.p(b);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(char i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(long i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(int i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(short i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(byte i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(double i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(float i) { 
      try{   super.p(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(PrintableI x) { 
      try{   super.p(x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   //public final TxOCStream p(String indent, PrintableI x) { 
   public final TxOCStream dp(String indent, PrintableI x) { 
      try{   super.dp(indent, x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(int[] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(long[] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream p(int[][] arr) {
      try{   super.p(arr);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

//!!!
   @Override //: ��@���o�� IOException, �Y�ȫ�
   public final TxOCStream pn() { 
      _ooo.println();  //: �bPrintStream�O�I�s��private method newLine()
      this._startOfLine=true;
      return this.flush();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(String x) { 
      this.p(x); return pn(); 
//      try{   super.pn(x);  return this;   }
//      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(StringBuilder x) { 
      this.p(x); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(StringBuffer x) { 
      this.p(x); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(boolean i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(byte i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(short i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(int i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(long i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(float i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(double i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(char i) { 
      this.p(i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(PrintableI x) { 
      this.p(x); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(int[] x) { 
      this.p(x); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(long[] x) { 
      this.p(x); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pn(int[][] x) { 
      this.p(x); return pn(); 
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps()  { 
      this.p(" ");  return this;  
//      try{   super.ps();  return this;   }
//      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(String x) { 
      this.p(x); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(StringBuilder x) { 
      this.p(x); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(StringBuffer x) { 
      this.p(x); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(boolean i) { 
      this.p(i); return ps();  
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(byte i) { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(short i) { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(int i)  { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(long i) { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(float i) { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(double i) { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(char i) { 
      this.p(i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(PrintableI x) { 
      this.p(x);  return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(int[] x) { 
      this.p(x); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(long[] x) { 
      this.p(x); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream ps(int[][] x) { 
      this.p(x); return ps(); 
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc()  { 
      this.p(","); return this;  
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(String x) { 
      this.p(x); return pc();    
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(StringBuilder x) { 
      this.p(x); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(StringBuffer x) { 
      this.p(x); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(boolean i) { 
      this.p(i); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(byte i) { 
      this.p(i); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(short i) { 
      this.p(i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(int i) { 
      this.p(i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(long i) { 
      this.p(i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(float i) { 
      this.p(i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(double i) { 
      this.p(i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(char i) { 
      this.p(i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(PrintableI x) { 
      this.p(x);  return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(int[] x) { 
      this.p(x); return pc();    
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(long[] x) { 
      this.p(x); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pc(int[][] x) { 
      this.p(x); return pc(); 
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs()  { 
      this.p(", ");  return this;
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(String x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(StringBuilder x) { 
      this.p(x);  return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(StringBuffer x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(boolean i) { 
      this.p(i); return pcs(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(byte i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(short i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(int i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(long i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(float i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(double i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(char i) { 
      this.p(i); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(PrintableI x) { 
      this.p(x); return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(int[] x) { 
      this.p(x);  return pcs(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(long[] x) { 
      this.p(x);  return pcs();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pcs(int[][] x) { 
      this.p(x);  return pcs();
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt()  { 
      this.p("\t");  return this;  
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(String x) { 
      this.p(x); return pt();   
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(StringBuilder x) { 
      this.p(x); return pt(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(StringBuffer x) { 
      this.p(x); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(boolean i) { 
      this.p(i); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(byte i) { 
      this.p(i); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(short i) { 
      try{   super.pt(i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(int i) { 
      this.p(i); return pt();  
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(long i) { 
      this.p(i); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(float i) { 
      this.p(i); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(double i) { 
      this.p(i); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(char i) { 
      this.p(i); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(PrintableI x) { 
      this.p(x); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(int[] x) { 
      this.p(x); return pt(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(long[] x) { 
      this.p(x); return pt();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pt(int[][] x) { 
      this.p(x); return pt(); 
   }

 //[ ��� printf(...) --------------------------
   @Deprecated @Override //[ �Y�ȫ�
   public final TxOCStream setFormat(
      String pre, String post, String last) 
   { //: ����
      super.setFormat(pre, post, last);  return this;
   }
   @Deprecated @Override //[ �Y�ȫ�
   public final TxOCStream setFormat(String pre, String post) { //: ����
      super.setFormat(pre, post);  return this;
   }
   @Deprecated @Override //[ �Y�ȫ�
   public final TxOCStream setFormat() { //: ����
      super.setFormat();  return this;
   }
   @Deprecated @Override //[ �Y�ȫ�
   public final TxOCStream sF(String pre, String post, String last) { 
    //: alias, ����
      super.sF(pre, post, last);  return this;
   }
   @Deprecated @Override //[ �Y�ȫ�
   public final TxOCStream sF(String pre, String post) { //: alias, ����
      super.sF(pre, post, "");  return this;
   }
   @Deprecated @Override //[ �Y�ȫ�
   public final TxOCStream sF() { //: alias, ����
      super.sF();  return this;
   }

   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(String x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(StringBuilder x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(StringBuffer x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(boolean i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(byte i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(short i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(int i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(long i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(float i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(double i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(char i) { 
      return p(_preF).p(i).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(PrintableI x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(int[] x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(long[] x) { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pf(int[][] x) { 
      return p(_preF).p(x).p(_postF); 
   }

   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(String x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(StringBuilder x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(StringBuffer x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(boolean i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(byte i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(short i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(int i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(long i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(float i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(double i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(char i) { 
      return p(_preF).p(i).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(PrintableI x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(int[] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(long[] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream pfe(int[][] x) { 
      return p(_preF).p(x).p(_lastF); 
   }
 //] ��� printf(...) --------------------------


   @Override //: �Y�ȫ�
   public final TxOCStream setLeftAdjustment() { //: �a�����, ����
      super.setLeftAdjustment();  return this;   
   }
   @Override //: �Y�ȫ�
   public final TxOCStream setRightAdjustment() { //: �a�k���, ����
      super.setRightAdjustment();  return this;   
   }
   @Override //: �Y�ȫ�
   public final TxOCStream setInternalAdjustment() { //: �a�����, ����
      super.setInternalAdjustment();  return this;   
   }
   @Override //: �Y�ȫ�
   public final TxOCStream setFill(char f) { //: ����
      super.setFill(f);  return this;   
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, String s) { 
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, StringBuilder s) { //: ���n
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, StringBuffer s) { //: ���n
      try{   super.wp(w,s);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, char i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w) {  
      try{   super.wp(w);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, boolean b) {
      try{   super.wp(w,b);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, int i)  { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, byte i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, short i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, long i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, float i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, double i) { 
      try{   super.wp(w,i);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wp(int w, WidthPrintableI x) { 
      try{   super.wp(w,x);  return this;   }
      catch(IOException xpt) { throw new tw.fc.StateError("\n"+xpt);  }
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w) { 
      this.wp(w);  return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, boolean i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, byte i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, short i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, int i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, long i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, float i) { 
      this.wp(w,i); return pn();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, double i) { 
      this.wp(w,i); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, char i) { 
      this.wp(w,i); return pn();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, String x) { 
      this.wp(w,x); return pn(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, StringBuilder x) { 
      this.wp(w,x); return pn();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, StringBuffer x) { 
      this.wp(w,x); return pn();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpn(int w, WidthPrintableI x) { 
      this.wp(w,x);  return pn(); 
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w) { 
      wp(w);  return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, String x) { 
      wp(w,x); return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, StringBuilder x) { 
      wp(w,x); return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, StringBuffer x) { 
      wp(w,x); return ps();  
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, boolean i) { 
      wp(w,i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, byte i) { 
      wp(w,i); return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, short i) { 
      wp(w,i); return ps();  
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, int i) { 
      wp(w,i); return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, long i) { 
      wp(w,i); return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, float i) { 
      wp(w,i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, double i) { 
      wp(w,i); return ps(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, char i) { 
      wp(w,i); return ps();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wps(int w, WidthPrintableI x) { 
      wp(w,x);  return ps();
   }

   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w) { 
      wp(w);  return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, String x) { 
      wp(w,x); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, StringBuilder x) { 
      wp(w,x); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, StringBuffer x) { 
      wp(w,x); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, boolean i) { 
      wp(w,i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, byte i) { 
      wp(w,i); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, short i) { 
      wp(w,i); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, int i) { 
      wp(w,i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, long i) { 
      wp(w,i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, float i) { 
      wp(w,i); return pc(); 
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, double i) { 
      wp(w,i); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, char i) { 
      wp(w,i); return pc();
   }
   @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpc(int w, WidthPrintableI x) { 
      wp(w,x);  return pc();
   }

   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, String x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, StringBuilder x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, StringBuffer x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, boolean x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, byte x)  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, short x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, int x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, long x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, float x)  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, double x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, char x) { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpf(int w, WidthPrintableI x) { 
      return p(_preF).wp(w,x).p(_postF);
   }

   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, String x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, StringBuilder x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, StringBuffer x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, boolean x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, byte x)  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, short x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, int x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, long x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, float x)  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, double x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, char x) { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated @Override //: �o IOException, �Y�ȫ�
   public final TxOCStream wpfe(int w, WidthPrintableI x) { 
      return p(_preF).wp(w,x).p(_lastF);
   }

}

//============================================================
