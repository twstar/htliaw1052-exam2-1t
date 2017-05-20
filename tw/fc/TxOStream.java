package tw.fc ;
import java.io.IOException;
//import java.io.PrintStream;
import java.text.DecimalFormat;
//
// Note: toString()的機制並不支援radix, precision等ostream功能
// Note: Integer.toHexString(i), Integer.toBinaryString(i)在負數是用2'補數
//       連c++的ostream也是這樣   
//       小算盤也是這樣, 結果在-20轉hex再轉dec竟變不回-20
//
public abstract class TxOStream {
//
//  'p' means 'put', a popular name of c++ operator '<<'
//
//   --> see the class cout and cerr
//

   private int                _radix=10; 
   @Deprecated protected String  _preF="", _postF="", _lastF="";
   private DecimalFormat      _decFormatter=null;   //: 只用在floating number
   private String[]           _boolPattern={ "false", "true" }; 
   private char               _fillChar=' ';
   //private int                _minWidth=0; //A31208X not use
   private boolean            _showPositive=false;  //: 是否對正數印+

   private static final int   LEFT_ADJ=0, RIGHT_ADJ=1, INTERN_ADJ=2;
   private int                _adjustment=RIGHT_ADJ;     //: 預設為靠右對齊
   private final StringBuffer _obuf=new StringBuffer(1024); 
                               //: widthAdj專用, 以免產生額外的garbage

   //protected boolean _afterLF= true;  //: b4 A5.035.M
   protected boolean _startOfLine= true;  //: 一開始是視為剛換行

   //-------------------------------------
   protected TxOStream() {   }       

   //-------------------------------------
   public abstract TxOStream flush() throws IOException ;
   public abstract void close() throws IOException ;

   //-------

//[
// 子類TxOCStream及TxOStrStream要縮值型, 
// 否則在串接呼叫時, 下一個就必須catch IOException
//]

   public final int getRadix() {  return _radix; }
   // final   //: 子類要縮值型. 
   public TxOStream setRadix(int r) {
      if(2<=r && r<=36) {  _radix=r;   return this;  }
      throw new RuntimeException("radix error: "+r);
   }
   public TxOStream dec() { _radix=10; return this; } //: 長效
   public TxOStream hex() { _radix=16; return this; } //: 長效 
   public TxOStream oct() { _radix=8; return this; }  //: 長效
   public TxOStream bin() { _radix=2; return this; }  //: 長效
   // -----

   // -----
   public final String getDecimalPattern() {  
      if(_decFormatter==null) {  return null;  }    //: 990927
      return _decFormatter.toPattern();  
   } 
   // final   //: 子類要縮值型. 
   public TxOStream setDecimalPattern(String ptn) { //: 長效
      //[ use default format if pattern is null  //: 990927
      //[ IllegalArgumentException - if pattern is invalid.
      if(ptn==null) {
         _decFormatter=null;   return this;
      }
      if(_decFormatter==null) {   
         _decFormatter=new DecimalFormat(ptn);   
      }
      else {     
         //final String old=_decFormatter.toPattern(); //A31208X not use
         _decFormatter.applyPattern(ptn);     
      } 
      return this;
   } 
   // final   //: 子類要縮值型. 
   public TxOStream sDP(String ptn) { //: 長效
      return setDecimalPattern(ptn); 
   }
   // final   //: 子類要縮值型. 
   public TxOStream setDecimalPattern() { //: 長效
      _decFormatter=null;   return this;
   }
   // final   //: 子類要縮值型. 
   public TxOStream sDP() { //: 長效
      _decFormatter=null;   return this;
   }
   // -----

   public final void getBooleanPattern(String[] dst) {  
      dst[0]=_boolPattern[0];  dst[1]=_boolPattern[1];
   } 
   // final   //: 子類要縮值型. 
   public TxOStream setBooleanPattern(String f, String t) { //: 長效
      if(f==null || t==null) {
         throw new NullPointerException("null pattern");
      }
      if(f.length()<1 || t.length()<1) {
         throw new IllegalArgumentException("empty pattern");
      }
      if(f.charAt(0)==t.charAt(0)) {
         throw new IllegalArgumentException("conflict at leading chars");
      }
      _boolPattern[0]=f;  _boolPattern[1]=t;
      return this;
   } 
   public TxOStream setBooleanPattern() { //: 長效
      return setBooleanPattern("false", "true");
   }
   // ----
   public final boolean getPositive() { 
      return _showPositive;
   }
   // final   //: 子類要縮值型. 
   public TxOStream showPositive(boolean show) { //: 長效
      _showPositive=show;   return this;
   }


   //-------------------------------------------

   //public abstract boolean afterLF() ;
   public final boolean startOfLine() {  return this._startOfLine;  }


   public abstract TxOStream p(String s) throws IOException ;

   //[ 子類要濾掉IOException, 所以不能final
   public TxOStream p(StringBuilder s) throws IOException { 
      this.p(s.toString());  return this;  
   }
   public TxOStream p(StringBuffer s) throws IOException { 
      this.p(s.toString());  return this;  
   }
 
   //[ 要濾掉IOException, 所以不能final
   public TxOStream p(boolean b) throws IOException { 
      this.p( b? _boolPattern[1]: _boolPattern[0] );
      return this;  
   }
   public TxOStream p(char i) throws IOException { 
      this.p(String.valueOf(i));  //: 此為PrintStream.print之做法
      return this;
   }
   public TxOStream p(long i) throws IOException { 
      if(_showPositive && i>0) {  p("+"); }
      if(_radix==10) {  this.p(Long.toString(i)); }  
      else if(_radix==16) { this.p(Long.toHexString(i)); }
      else if(_radix==2) {  this.p(Long.toBinaryString(i));  }
      else if(_radix==8) {  this.p(Long.toOctalString(i));  }
      else {   this.p(Long.toString(i,_radix));   }
      return this; 
   }
   public TxOStream p(int i) throws IOException { 
      // p((long)i);  return this;  //: 不行! 負數會補得過長.
      if(_showPositive && i>0) {  p("+"); }
      if(_radix==10)     { this.p(Integer.toString(i));       }  
      else if(_radix==16){ this.p(Integer.toHexString(i));    }
      else if(_radix==2) { this.p(Integer.toBinaryString(i)); }
      else if(_radix==8) { this.p(Integer.toOctalString(i));  }
      else {        this.p(Integer.toString(i,_radix));       } 
      return this; 
   }
   public TxOStream p(short i) throws IOException { 
      this.p((int)i);  return this;  
   }    //: jdk沒有 Short.toHexString(short)
   public TxOStream p(byte i) throws IOException { 
      this.p((int)i);  return this;  
   }
   public TxOStream p(double i) throws IOException { 
      if(_showPositive && i>0) {  p("+"); }
      if(_decFormatter==null) {  this.p(String.valueOf(i));  }
      else {    this.p(_decFormatter.format(i));    }
      return this;  
   }
   public TxOStream p(float i) throws IOException { 
      if(_showPositive && i>0) {  p("+"); }
      if(_decFormatter==null) {  this.p(String.valueOf(i));  }
      else {  this.p(_decFormatter.format(i));   }
      return this;  
   }
   public TxOStream p(PrintableI x) throws IOException { 
      if(x==null) {  p(" null ");  }
      else {  x.printTo(this);   } 
      return this;  
   }
 //[ 不提供Object的方法, 以提醒自製的class實作PrintableI
 //[ jdk中的final class 如Color可用 cout.p(color.toString())
 //public final TxOStream p(Object x) { 
 //   this.p(" <[ "+x+" ]> ");  return this;   
 //}  

  //[ A5.019.C add
  //[ called by PrintfSpec_z::doPrint(TxOStream oS, String idnt, Object ob)
  //[ overridden in TxOCStrteam, TxOStrStream, TxOWinStream to filer IOException
   //public TxOStream p(String idnt, PrintableI x) throws IOException { //: b4 A5.035.J
   public TxOStream dp(String idnt, PrintableI x) throws IOException { 
      if(x==null) {
         if(this.startOfLine()) this.p(idnt);   //: A5.035.H add
         this.p(" null ");
      }
      else if(x instanceof IndentPrintableI) {
         //((IndentPrintableI)x).printTo(this, idnt);  //: b4 A5.035.K
         ((IndentPrintableI)x).indentPrintTo(this, idnt);
      }  
      else {
         if(this.startOfLine()) this.p(idnt);  
         x.printTo(this);
      }
      return this;   
   }

   public abstract TxOStream pn() throws IOException ;     
   
   public TxOStream pn(String x) throws IOException { 
      this.p(x); return this.pn();  
   }
   public TxOStream pn(StringBuilder x) throws IOException { 
      this.p(x); return this.pn();  
   }
   public TxOStream pn(StringBuffer x) throws IOException { 
      this.p(x); return this.pn();  
   }
   public TxOStream pn(boolean i) throws IOException { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(byte i) throws IOException  { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(short i) throws IOException { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(int i) throws IOException { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(long i) throws IOException { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(float i) throws IOException { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(double i) throws IOException { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(char i) throws IOException  { 
      this.p(i); return this.pn();  
   }
   public TxOStream pn(PrintableI x) throws IOException { 
      x.printTo(this);  return this.pn();
   }

//[ A5.035L.C add and deleted later,
//[ since ooo.pn("...", x); 
//[ can be replaced as  ooo.p("...", x).pn();
//  public TxOStream pn(String idnt, PrintableI x) throws IOException { 
//     this.p(idnt, x);  return this.pn();
//  }

   public TxOStream pn(int[] x) throws IOException { 
      this.p(x); return this.pn();  
   }
   public TxOStream pn(long[] x) throws IOException { 
      this.p(x); return this.pn();  
   }
   public TxOStream pn(int[][] x) throws IOException { 
      this.p(x); return this.pn();  
   }
   //public final TxOStream pn(Object i) { p(i); this.pn(); return this;  }

   public TxOStream ps()  throws IOException { 
      this.p(" ");  return this;  
   }
   public TxOStream ps(String x) throws IOException { 
      this.p(x); return ps(); 
   }
   public TxOStream ps(StringBuilder x) throws IOException { 
      this.p(x); return ps(); 
   }
   public TxOStream ps(StringBuffer x) throws IOException { 
      this.p(x); return ps(); 
   }
   public TxOStream ps(boolean i) throws IOException { 
      this.p(i); return ps();  
   }
   public TxOStream ps(byte i)  throws IOException  { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(short i) throws IOException  { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(int i)   throws IOException  { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(long i)  throws IOException  { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(float i) throws IOException  { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(double i) throws IOException { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(char i)  throws IOException  { 
      this.p(i); return ps(); 
   }
   public TxOStream ps(PrintableI x) throws IOException { 
   // this.p(x);  return ps();
      x.printTo(this);  return ps();
   }
   public TxOStream ps(int[] x) throws IOException { 
      this.p(x); return ps(); 
   }
   public TxOStream ps(long[] x) throws IOException { 
      this.p(x); return ps(); 
   }
   public TxOStream ps(int[][] x) throws IOException { 
      this.p(x); return ps(); 
   }
   //public final TxOStream ps(Object i) { p(i); ps(); return this;  }

   public TxOStream pc() throws IOException  { 
      this.p(","); return this;  
   }
   public TxOStream pc(String x) throws IOException { 
      this.p(x); return pc();    
   }
   public TxOStream pc(StringBuilder x) throws IOException { 
      this.p(x); return pc();
   }
   public TxOStream pc(StringBuffer x) throws IOException { 
      this.p(x); return pc();
   }
   public TxOStream pc(boolean i) throws IOException { 
      this.p(i); return pc();
   }
   public TxOStream pc(byte i) throws IOException  { 
      this.p(i); return pc();
   }
   public TxOStream pc(short i) throws IOException { 
      this.p(i); return pc(); 
   }
   public TxOStream pc(int i) throws IOException   { 
      this.p(i); return pc(); 
   }
   public TxOStream pc(long i) throws IOException  { 
      this.p(i); return pc(); 
   }
   public TxOStream pc(float i) throws IOException  { 
      this.p(i); return pc(); 
   }
   public TxOStream pc(double i) throws IOException { 
      this.p(i); return pc(); 
   }
   public TxOStream pc(char i) throws IOException  { 
      this.p(i); return pc(); 
   }
   public TxOStream pc(PrintableI x) throws IOException { 
   // this.p(x); return pc(); 
      x.printTo(this);  return pc();
   }
   public TxOStream pc(int[] x) throws IOException { 
      this.p(x); return pc();    
   }
   public TxOStream pc(long[] x) throws IOException { 
      this.p(x); return pc(); 
   }
   public TxOStream pc(int[][] x) throws IOException { 
      this.p(x); return pc(); 
   }
   //public final TxOStream pc(Object i) { p(i); pc(); return this;  }

   public TxOStream pcs()  throws IOException       { 
      this.p(", ");  return this;
   }
   public TxOStream pcs(String x) throws IOException { 
      this.p(x);  return pcs(); 
   }
   public TxOStream pcs(StringBuilder x) throws IOException { 
      this.p(x);  return pcs();
   }
   public TxOStream pcs(StringBuffer x) throws IOException { 
      this.p(x);  return pcs(); 
   }
   public TxOStream pcs(boolean i) throws IOException { 
      this.p(i); return pcs(); 
   }
   public TxOStream pcs(byte i) throws IOException  { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(short i) throws IOException { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(int i)  throws IOException  { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(long i)  throws IOException { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(float i) throws IOException { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(double i) throws IOException { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(char i) throws IOException  { 
      this.p(i); return pcs();
   }
   public TxOStream pcs(PrintableI x) throws IOException { 
   // this.p(x); return pcs();
      x.printTo(this);  return pcs();
   }
   public TxOStream pcs(int[] x) throws IOException { 
      this.p(x);  return pcs(); 
   }
   public TxOStream pcs(long[] x) throws IOException { 
      this.p(x);  return pcs();
   }
   public TxOStream pcs(int[][] x) throws IOException { 
      this.p(x);  return pcs();
   }
   //public final TxOStream pcs(Object i) { p(i); pcs(); return this;  }

   public TxOStream pt()  throws IOException  { 
      this.p("\t");  return this;  
   }
   public TxOStream pt(String x) throws IOException { 
      this.p(x); return pt();   
   }
   public TxOStream pt(StringBuilder x) throws IOException { 
      this.p(x); return pt(); 
   }
   public TxOStream pt(StringBuffer x) throws IOException { 
      this.p(x); return pt();
   }
   public TxOStream pt(boolean i) throws IOException { 
      this.p(i); return pt();
   }
   public TxOStream pt(byte i) throws IOException  { 
      this.p(i); return pt();
   }
   public TxOStream pt(short i) throws IOException { 
      this.p(i); return pt();  
   }
   public TxOStream pt(int i) throws IOException   { 
      this.p(i); return pt();
   }
   public TxOStream pt(long i) throws IOException  { 
      this.p(i); return pt();
   }
   public TxOStream pt(float i) throws IOException { 
      this.p(i); return pt();
   }
   public TxOStream pt(double i) throws IOException { 
      this.p(i); return pt();
   }
   public TxOStream pt(char i) throws IOException  { 
      this.p(i); return pt();
   }
   public TxOStream pt(PrintableI x) throws IOException { 
   // this.p(x); return pt();
      x.printTo(this);  return pt();
   }
   public TxOStream pt(int[] x) throws IOException { 
      this.p(x); return pt(); 
   }
   public TxOStream pt(long[] x) throws IOException { 
      this.p(x); return pt();
   }
   public TxOStream pt(int[][] x) throws IOException { 
      this.p(x); return pt(); 
   }
   //public final TxOStream pt(Object i) { p(i); pt(); return this;  }

   // //public void printf(String fStr, Object... args) throws IOException { 
   // public TxOStream printf(String fStr, Object... args) throws IOException { 
   // //D System.out.println("\""+fStr+"\"");
   //    final TxIStrStream fStrS= new TxIStrStream(fStr);
   //    fStrS.autoSkipWS=false;
   //    //final PrintfSpec fSpec= new PrintfSpec(); 
   //    String preStr; 
   //    int argIdx=0;
   //    while(true) {
   //       preStr= PrintfSpec.getPreString(fStrS);
   //       this.p(preStr);
   //       if(fStrS.probeEOF()) break;  
   //       //fStrS.g(fSpec);
   //       final PrintfSpec fSpec= PrintfSpec.getInstanceFrom(fStrS);
   //       if(fSpec.noArg()) {  //: "%%" || "%n"  
   //          fSpec.doPrint(this, null);
   //       }
   //       else if(fSpec.hasIndent()) {  // "%|z", ...
   //          final String idt= (String)args[argIdx++];  
   //          final Object ob= args[argIdx++];  
   //          fSpec.doPrint(this, idt, ob);
   //       }
   //       else {
   //          final Object ob= args[argIdx++];  //: this line used for Xpt report
   //          fSpec.doPrint(this, ob);
   //       }
   //    }
   //    return this;
   // } //: b4 A%.034L.A
   public TxOStream printf(String fStr, Object... args) throws IOException { 
  //D System.out.println("\""+fStr+"\"");
      final TxIStrStream fStrS= new TxIStrStream(fStr);
      fStrS.autoSkipWS=false;
      int argIdx=0;
      while(! fStrS.probeEOF() ) {
         final char ch= (char)fStrS.read();
         if(ch!='%') {  this.p(ch);  }
         else {
            fStrS.unread(ch);
            final PrintfSpec fSpec= PrintfSpec.getInstanceFrom(fStrS);
            if(fSpec.noArg()) {  //: "%%" || "%n"  
               fSpec.doPrint(this, null);
            }
            else if(fSpec.hasIndent()) {  // "%|z", ...
               final String idt= (String)args[argIdx++];  
               final Object ob= args[argIdx++];  
               fSpec.doPrint(this, idt, ob);
            }
            else {
               final Object ob= args[argIdx++];  //: this line used for Xpt report
               fSpec.doPrint(this, ob);
            }
         }
      }
      return this;
   }


   public TxOStream p(int[] arr) throws IOException {
      ImuIntArr.print(this,arr);   return this;
   }
   public TxOStream p(long[] arr) throws IOException {
      if(arr==null) {  p("null");  return this;  }
      p("["); 
      if(arr.length>0) {  p(arr[0]);  }
      for(int i=1; i<arr.length; i++) {  p(",").p(arr[i]);   }
      p("]");     return this;
   }
   public TxOStream p(int[][] arr) throws IOException {
      if(arr==null) {  p("null");  return this;  }
      p("["); 
      if(arr.length>0) {  p(arr[0]);  }
      for(int i=1; i<arr.length; i++) {  p(",").p(arr[i]);   }
      p("]");     return this;
   }


 //[ use printf(...) instead  -------
   @Deprecated public final void getFormat(String[] dst) { //: 長效
      dst[0]=_preF;   dst[1]=_postF; 
   }
   // final   //: 子類要縮值型. 
   @Deprecated public TxOStream setFormat(
      String pre, String post, String last) 
   { //: 長效
      if(pre==null || post==null || last==null) {
         throw new RuntimeException("null pattern");
      }
      _preF=pre;  _postF=post;  _lastF=last;  return this;
   }
   @Deprecated public TxOStream setFormat(String pre, String post) { //: 長效
      return setFormat(pre, post, "");
   }
//@Deprecated public TxOStream setFormat(String post) { //: 長效
//      return setFormat("", post, "") ;
//}

   @Deprecated public TxOStream setFormat() { //: 長效
      return setFormat("", "", "") ;
   }
   @Deprecated  public TxOStream sF(String pre, String post, String last) { 
    //: alias, 長效
      return setFormat(pre, post, last);   
   }
   @Deprecated 
   public TxOStream sF(String pre, String post) { //: alias, 長效
      return setFormat(pre, post, "");   
   }
   @Deprecated 
   public TxOStream sF(String post) { //: alias, 長效
      return setFormat("", post, "") ;
   }
   @Deprecated 
   public TxOStream sF() { //: alias, 長效
      return setFormat("", "", "") ;
   }

   @Deprecated public TxOStream pf(String x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(StringBuilder x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(StringBuffer x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(boolean x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(byte x) throws IOException  { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(short x) throws IOException  { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(int x) throws IOException   { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(long x) throws IOException  { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(float x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(double x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(char x) throws IOException  { 
      return p(_preF).p(x).p(_postF); 
   }
   @Deprecated public TxOStream pf(PrintableI x) throws IOException { 
      return p(_preF).p(x).p(_postF);
   }
   @Deprecated public TxOStream pf(int[] x) throws IOException { 
      return p(_preF).p(x).p(_postF);  
   }
   @Deprecated public TxOStream pf(long[] x) throws IOException { 
      return p(_preF).p(x).p(_postF);  
   }
   @Deprecated public TxOStream pf(int[][] x) throws IOException { 
      return p(_preF).p(x).p(_postF); 
   }
   //public final TxOStream pf(Object i) { return p(_preF).p(x).p(_postF); }

   @Deprecated public TxOStream pfe(String x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(StringBuilder x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(StringBuffer x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(boolean x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(byte x) throws IOException  { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(short x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(int x) throws IOException   { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(long x) throws IOException  { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(float x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(double x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(char x) throws IOException  { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(PrintableI x) throws IOException { 
      return p(_preF).p(x).pn(_lastF);
   }
   @Deprecated public TxOStream pfe(int[] x) throws IOException { 
      return p(_preF).p(x).p(_lastF);
   }
   @Deprecated public TxOStream pfe(long[] x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   @Deprecated public TxOStream pfe(int[][] x) throws IOException { 
      return p(_preF).p(x).p(_lastF); 
   }
   //public final TxOStream pfe(Object i) { return p(_preF).p(x).p(_lastF); }
 //] ----------------------------

   // -----------------
   //[ 原C++的規定太狹隘: minmal width 的機制對任一種type都該有作用. 
   public TxOStream setLeftAdjustment() { //: 靠左對齊, 長效
      _adjustment=LEFT_ADJ;  return this;
   }
   public TxOStream setRightAdjustment() { //: 靠右對齊, 長效
      _adjustment=RIGHT_ADJ;  return this; 
   }
   public TxOStream setInternalAdjustment() { //: 靠中對齊, 長效
      _adjustment=INTERN_ADJ;   return this;
   }
   public TxOStream setFill(char f) { //: 長效
      _fillChar=f;    return this;
   }

   private final String widthAdj(int w, String s) {
   synchronized(_obuf) { //: 因為_obuf是static field
      if(!(w>0))  return s;  
      _obuf.delete(0,_obuf.length());   
      _obuf.append(s);
      int diff= w-_obuf.length();  
      while(diff>0) {
         if(_adjustment==RIGHT_ADJ) {  //: 靠右對齊
            _obuf.insert(0,_fillChar);  
         }
         else if(_adjustment==LEFT_ADJ) {  //: 靠左對齊
            _obuf.append(_fillChar);  
         }
         else { // INTERN_ADJ
            if(diff%2==1)  _obuf.insert(0,_fillChar); 
            else  _obuf.append(_fillChar);
         } 
         diff--;      
      }
      return _obuf.toString();
   } //] synchronized
   }

   public TxOStream wp(int w, String s) throws IOException { //: 重要
      this.p(widthAdj(w,s));   return this;
   }
   public TxOStream wp(int w, StringBuilder s) throws IOException { //: 重要
      this.p(widthAdj(w,s.toString()));   return this;
   }
   public TxOStream wp(int w, StringBuffer s) throws IOException { //: 重要
      this.p(widthAdj(w,s.toString()));   return this;
   }

   public TxOStream wp(int w, char i) throws IOException { 
      this.wp(w,Character.toString(i));  return this;  
   }
   public TxOStream wp(int w) throws IOException {  
      this.wp(w,"");   return this;  
   }
   public TxOStream wp(int w, boolean b) throws IOException {
      this.wp(w, b?_boolPattern[1]: _boolPattern[0] );     
      return this;  
   }
   public TxOStream wp(int w, int i) throws IOException    { 
      String s;
      if(_radix==10)     { s=Integer.toString(i); }  
      else if(_radix==16){ s=Integer.toHexString(i); }
      else if(_radix==2) { s=Integer.toBinaryString(i);}
      else if(_radix==8) { s=Integer.toOctalString(i); }
      else {        s=Integer.toString(i,_radix);    } 
      if(_showPositive && i>0) {  s="+"+s; }
      this.wp(w,s);
      return this; 
   }
   public TxOStream wp(int w, byte i) throws IOException { 
      this.wp(w, (int)i);  return this;  //: 由轉接取得radix服務
   }
   public TxOStream wp(int w, short i) throws IOException { 
      this.wp(w, (int)i);  return this;  //: 由轉接取得radix服務
   }
   public TxOStream wp(int w, long i) throws IOException   { 
      String s;
      if(_radix==10) {  s=Long.toString(i); }  
      else if(_radix==16) {  s=Long.toHexString(i); }
      else if(_radix==2) {  s=Long.toBinaryString(i); }
      else if(_radix==8) {  s=Long.toOctalString(i); }
      else {   s=Long.toString(i,_radix);   }
      if(_showPositive && i>0) {  s="+"+s; }
      this.wp(w,s);
      return this; 
   }
   public TxOStream wp(int w, float i) throws IOException  { 
      String s;
      if(_decFormatter==null) {  s=Float.toString(i);  }
      else {  s=_decFormatter.format(i);   }
      if(_showPositive && i>0) {  s="+"+s; }
      this.wp(w,s);
      return this;  
   }
   public TxOStream wp(int w, double i) throws IOException { 
      String s; 
      if(_decFormatter==null) {  s=Double.toString(i);  }
      else { s=_decFormatter.format(i);    }
      if(_showPositive && i>0) {  s="+"+s; }
      this.wp(w,s);
      return this;  
   }
   public TxOStream wp(int w, WidthPrintableI x) throws IOException { 
      x.widthPrintTo(w,this);  return this;   
   }

   public TxOStream wpn(int w) throws IOException { 
      this.wp(w);  return pn(); 
   }
   public TxOStream wpn(int w, boolean i) throws IOException { 
      this.wp(w,i); return pn(); 
   }
   public TxOStream wpn(int w, byte i)   throws IOException { 
      this.wp(w,i); return pn(); 
   }
   public TxOStream wpn(int w, short i)  throws IOException { 
      this.wp(w,i); return pn(); 
   }
   public TxOStream wpn(int w, int i)    throws IOException { 
      this.wp(w,i); return pn(); 
   }
   public TxOStream wpn(int w, long i)   throws IOException { 
      this.wp(w,i); return pn(); 
   }
   public TxOStream wpn(int w, float i)  throws IOException { 
      this.wp(w,i); return pn();
   }
   public TxOStream wpn(int w, double i) throws IOException { 
      this.wp(w,i); return pn(); 
   }
   public TxOStream wpn(int w, char i)   throws IOException { 
      this.wp(w,i); return pn();
   }
   public TxOStream wpn(int w, String x) throws IOException { 
      this.wp(w,x); return pn(); 
   }
   public TxOStream wpn(int w, StringBuilder x) throws IOException { 
      this.wp(w,x); return pn();
   }
   public TxOStream wpn(int w, StringBuffer x) throws IOException { 
      this.wp(w,x); return pn();
   }
   public TxOStream wpn(int w, WidthPrintableI x) throws IOException { 
      this.wp(w,x);  return pn(); 
   }
   //public final TxOStream wpn(int w, Object i) { this.wp(w,i); return pn(); }

   // 沒有wpt是因為印tab(dep. on absolute position)和
   //  設width(dep. on relative position)的功能類似, 且不適合混用
//>>> 但若有fill char就不同.

   // 在靠左時 wps(k,x)與wps(k+1,x)相同, 
   // 在靠右時可併入下一筆. 但若有fill char就不同.
   public TxOStream wps(int w) throws IOException { 
      wp(w);  return ps();
   }
   public TxOStream wps(int w, String x) throws IOException { 
      wp(w,x); return ps();
   }
   public TxOStream wps(int w, StringBuilder x) throws IOException { 
      wp(w,x); return ps();
   }
   public TxOStream wps(int w, StringBuffer x) throws IOException { 
      wp(w,x); return ps();  
   }
   public TxOStream wps(int w, boolean i) throws IOException { 
      wp(w,i); return ps(); 
   }
   public TxOStream wps(int w, byte i)  throws IOException  { 
      wp(w,i); return ps();
   }
   public TxOStream wps(int w, short i) throws IOException  { 
      wp(w,i); return ps();  
   }
   public TxOStream wps(int w, int i)  throws IOException   { 
      wp(w,i); return ps();
   }
   public TxOStream wps(int w, long i) throws IOException   { 
      wp(w,i); return ps();
   }
   public TxOStream wps(int w, float i) throws IOException  { 
      wp(w,i); return ps(); 
   }
   public TxOStream wps(int w, double i) throws IOException  { 
      wp(w,i); return ps(); 
   }
   public TxOStream wps(int w, char i) throws IOException   { 
      wp(w,i); return ps();
   }
   public TxOStream wps(int w, WidthPrintableI x) throws IOException  { 
      wp(w,x);  return ps();
   }
   //public TxOStream wps(int w, Object i) { wp(w,i); return ps(); }

   public TxOStream wpc(int w) throws IOException { 
      wp(w);  return pc();
   }
   public TxOStream wpc(int w, String x) throws IOException { 
      wp(w,x); return pc();
   }
   public TxOStream wpc(int w, StringBuilder x) throws IOException { 
      wp(w,x); return pc();
   }
   public TxOStream wpc(int w, StringBuffer x) throws IOException { 
      wp(w,x); return pc(); 
   }
   public TxOStream wpc(int w, boolean i) throws IOException { 
      wp(w,i); return pc(); 
   }
   public TxOStream wpc(int w, byte i) throws IOException   { 
      wp(w,i); return pc();
   }
   public TxOStream wpc(int w, short i) throws IOException  { 
      wp(w,i); return pc();
   }
   public TxOStream wpc(int w, int i) throws IOException    { 
      wp(w,i); return pc(); 
   }
   public TxOStream wpc(int w, long i) throws IOException   { 
      wp(w,i); return pc(); 
   }
   public TxOStream wpc(int w, float i) throws IOException  { 
      wp(w,i); return pc(); 
   }
   public TxOStream wpc(int w, double i) throws IOException { 
      wp(w,i); return pc();
   }
   public TxOStream wpc(int w, char i) throws IOException   { 
      wp(w,i); return pc();
   }
   public TxOStream wpc(int w, WidthPrintableI x) throws IOException { 
      wp(w,x);  return pc();
   }
   //public TxOStream wpc(int w, Object i) { wp(w,i); return pn(); }


   @Deprecated public TxOStream wpf(int w, String x) throws IOException { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, StringBuilder x) throws IOException { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, StringBuffer x) throws IOException { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, boolean x) throws IOException { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, byte x) throws IOException   { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, short x) throws IOException  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, int x) throws IOException    { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, long x) throws IOException   { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, float x) throws IOException  { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, double x) throws IOException { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, char x) throws IOException   { 
      return p(_preF).wp(w,x).p(_postF); 
   }
   @Deprecated public TxOStream wpf(int w, WidthPrintableI x) throws IOException { 
      return p(_preF).wp(w,x).p(_postF);
   }

   @Deprecated public TxOStream wpfe(int w, String x) throws IOException { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, StringBuilder x) throws IOException { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, StringBuffer x) throws IOException { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, boolean x) throws IOException { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, byte x) throws IOException   { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, short x) throws IOException  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, int x) throws IOException    { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, long x) throws IOException   { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, float x) throws IOException  { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, double x) throws IOException { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, char x) throws IOException   { 
      return p(_preF).wp(w,x).p(_lastF); 
   }
   @Deprecated public TxOStream wpfe(int w, WidthPrintableI x) throws IOException { 
      return p(_preF).wp(w,x).p(_lastF);
   }

}
