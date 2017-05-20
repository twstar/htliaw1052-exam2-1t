package tw.fc ;
import java.io.IOException;
//import java.io.InputStream;
import java.io.EOFException;
import java.io.Reader;
import java.util.ArrayList;
import tw.fc.TxInputException;
//import tw.fc.re.Implement;


//[
//  Encapsulate InputStream
//  facilitate cascading inputs as the C++ istream
//  support pushback many chars and nested marking for backtracking.  
//
// **************************************
// public abstract class tk.ScanStream {
public abstract class TxIStream {

   public static final int   EOF= -1;
   public static final byte  CR=0x0D, LF=0x0A;

   // System.in is an InputStream, not parallel to output.
   // protected InputStream iii=null; //: �ª��d��
   protected Reader iii=null;

// static class UnReadData  //: b4 A5.037L, extracted to be a top-level class 

   // //protected int store;   protected boolean pushBack=false;
   // private int store;   private boolean pushBack=false;
   // private  //: used by BacktrackData
   UnReadData unreadData= new UnReadData(this);
   public void clearUnreadData() {  unreadData.clear();   }
   public boolean querySingleStore() {  return unreadData.singleStore;  }
   public boolean setSingleStore(boolean b) {  //: helpful in debugging
      boolean old=unreadData.singleStore;
      unreadData.singleStore=b;
      return old;
   }

   //---------------------------

// static class BacktrackData     
           //: b4 A5.037L, extracted to be a top-level class 

   //private   //: used in testMark1(), ... 
   BacktrackData backtrackData= new BacktrackData(this);
   
   public void isBtMarking() {  backtrackData.isMarking();  } //: A5.034L add
   public void numOfBtMarks() {  backtrackData.numOfMarks();  } //: A5.034L add
   public int pushBtMark() {  return backtrackData.pushMark();  }
   //[ pop a BtMark and its corresponding backtraking data.
   public void popBtMark(int mId) {  backtrackData.popMark(mId);  }
   //public void popBtMark() {  backtrackData.popMark();  }
   public void clearBacktrakingData() {  backtrackData.clear();  } //: A5.034L add
   //public void dumpBtMark() {  backtrackData.dump();  }
   public void dumpBacktrakingData() {  backtrackData.dump();  }
 //] A5.025L add

   //---------------------------

   ArrayList<CommentManager> _allCmntManagers= 
      new ArrayList<CommentManager>();

   void passiveAddCommentManager(CommentManager cM) {
      _allCmntManagers.add(cM);
   }

   //public CommentManager logCommentPattern(String begin, String end) { //: b4 A5.038L
   public CommentManager logCommentFormat(String begin, String end) {
      return new CommentManager(this, begin, end);
   }
   public void dislogCommentFormats() {
      _allCmntManagers.clear();
   }

  //] ========================================================

   private int _lineNum=1;    //: A5.037L.H add
   public boolean autoSkipWS=true;    //: encourage direct access

   protected int radix=10;
   private String[] _boolPattern={ "false", "true" };

   //---------------------------------------------
   protected  TxIStream() { }  //: only for subclasses

   //------------------------------------------------------------
   public int lineNum() {  return this._lineNum;  }  //: A5.037L.H add

   public final boolean queryStopFlag() {
      Thread thd= Thread.currentThread();
      if(thd instanceof StopFlagThread) {
         return ((StopFlagThread)thd).getStopFlag();
      }
      else {  return false;  }
   }


   //[ KNR said that -1 cannot be pushed back.
   //[ java.io.PushbackInputStream: unread(int) 
   // // //public void unread(int ch) throws IOException {  //: b4 A5.024L
   // // public void unread(char ch) throws IOException {  //: b4 A5.033L
   // public void unread(char ch) {
   //    // if(!pushBack) {
   //    // // if(ch == EOF) { return;  }
   //    //    // pushBack=true;  store=(byte)ch;  
   //    //        //: �������e���Ҽ{unicode�ɪ�����, �y��Ūstring��unread���~
   //    //    pushBack=true;  store=ch;
   //    // }
   //    // else {
   //    //    throw new IOException(
   //    //                 "TxIStream.unread(int) : store full"
   //    //              );
   //    // }
   //    this.unreadData.store(ch);
   //    int len=this.markedData.size();
   //    if(len>0) this.markedData.remove(len-1);
   // } //: b4 A5.034L
   public void unread(char ch) {
      if(backtrackData.isMarking()) {
         int i= backtrackData.pop();
         if(i!=ch) { 
             throw new IllegalArgumentException("cheating unread: "+ch); 
         }
      } 
      if(ch=='\n') {  this._lineNum--;  }
      this.unreadData.store(ch);
   }
   //[ a synonym in C++
   //public void putback(int ch) throws IOException {   //: b4 A5.024L
   public final void putback(char ch) throws IOException { 
      unread(ch);
   }

   //public void unread(String s) throws IOException { //: b4 A5.033L
   public void unread(String s) {
      for(int i=s.length()-1; i>=0; i--) unread(s.charAt(i)); 
   }
   //public void unread(char[] A, int from, int to) throws IOException { //: b4 A5.033L
   public void unread(char[] A, int from, int to) {
      if(to>A.length) to=A.length;
      for(int i=to-1; i>=from; i--) unread(A[i]); 
   }
   //public void unread(char[] A, int from) throws IOException { //: b4 A5.033L
   public void unread(char[] A, int from) {
      unread(A, from, A.length);
   }
   //public void unread(char[] A) throws IOException { //: b4 A5.033L
   public void unread(char[] A) {
      unread(A, 0, A.length);
   }

  //-----------

   // Not @Override 
   //[ read a char without skipWS
   //[ a normal function value is in 0~65535, EOF is -1.
   public int read() throws IOException {
   //D System.err.println("TxIStream::read()");
      // if(pushBack) {  pushBack=false;  return store;  }
      int ans;
      if(unreadData.notEmpty()) {  ans=unreadData.retrive();  }
      else {
       //[ cause DOS window not repaint while keyin-ing.  
       // while(!iii.ready()) {
       //  //[ so that not block after stopFlag==true
       //    if(this.queryStopFlag()) {  return TxIStream.EOF; }
       //  //]
       //    try{
       //       Thread.sleep(10);  //: ms
       //    }
       //    catch(InterruptedException xpt) {  return TxIStream.EOF;  }
       // }
       // // If ready(), the next read() is guaranteed not to block.
       //]
          ans= iii.read();  //: read a char from the embedded Reader
      }
      if(ans=='\n') {  this._lineNum++;  }
      // if(marking && ans!=TxIStream.EOF) this.markedData.add((char)ans); //: b4 A5.034L
      if(backtrackData.isMarking() && ans!=TxIStream.EOF) {
         backtrackData.push((char)ans);
      }
      return ans;
   }
   //[ a synonym in C++
   public int get() throws IOException {  return read();   }

//[ read(s) not skipWS.  g(s) may be autoskipWS 
   public TxIStream read(charRef s) throws IOException {
      s.v= ((char)this.read());
      return this;
   }

   //[ Reads characters into a portion of an array. 
   //[ This method will block until some input is available, 
   //[ an I/O error occurs, or the end of the stream is reached.
   //[ Parameters:
   //[     cbuf - Destination buffer
   //[     startIdx - Offset at which to start storing characters
   //[     len - Maximum number of characters to read 
   //[ Returns:
   //[     The number of characters read, or 
   //[     -1 if the end of the stream has been reached 
   public int read(char[] cBuf, int startIdx, int len) throws IOException
   {
      if(this.probeEOF()) return TxIStream.EOF;
      int i;
      for(i=0; i<len; i++) {
         int ch= this.read();
         if(ch==TxIStream.EOF) break;
         cBuf[startIdx+i]=(char)ch; 
      } 
      return i;
   }
   public int read(char[] dest) throws IOException {
      return read(dest, 0, dest.length);
   }

//>>> Not valid when processing comment
   //[ Skips characters. 
   //[ This method will block until some characters are available, 
   //[ an I/O error occurs, or the end of the stream is reached.
   //[ Parameters:
   //[     n - The number of characters to skip 
   //[ Returns:
   //[     The number of characters actually skipped 
   //[ Throws:
   //[     IllegalArgumentException - If n is negative. 
   //[     IOException - If an I/O error occurs
   public long skip(long n) throws IOException {
      if(n<0) throw new IllegalArgumentException("negative: "+n);
      int count;
      for(count=0; count<n; count++) {
         if(this.probeEOF()) break;
         this.read();
      }
      return count;
   }
   //[ a synonym for C++
   public long ignore(long count) throws IOException {
      return skip(count);
   }
   public boolean ready() throws IOException {
      if(unreadData.notEmpty()) return true;
      else return  iii.ready();
   }

// public long available() throws IOException {
//    if(!pushBack) {  return iii.available();  }
//    else {
//       return 1+iii.available();
//    }
// }

   public void close() throws IOException {
      iii.close();
      //pushBack=false;
      unreadData.clear();
   }
   //--------------------------------------------

   private String checkComment(char _ch) throws IOException {
         String cmnt=null;
         NEXT_CMNT:            
         for(CommentManager cM: _allCmntManagers) {
            if(_ch==cM.firstChar()) {
               cmnt= cM.getComment();
               if(cmnt!=null) { 
                  break NEXT_CMNT;  //: found a comment
               }
            }
         }
         return cmnt;
   }

//[ A5.037L.L merge into skip(String wsRange, StringBuilder skipped)
// //public   //: b4 A5.036
// private TxIStream skipSpHt(StringBuilder skipped) throws IOException
// { //[ skip succedding Spaces and Horizontal Tabs

   // // // //private TxIStream skipSpHtCrLf() 
   // // // private TxIStream skipSpHtCrLf()   //: b4 A5.037L.F
   // // private TxIStream skipSpHtCrLfCmnt()  //: b4 A5.037L.I
   // private TxIStream skipSpHtCrLfCmnt(StringBuilder skipped) 
   private TxIStream skipWithComment(String wsRange, StringBuilder skipped) 
   throws IOException
   {  //[ skip succedding Sp, Ht, Cr, Lf, comment
      int _ch;
      NEXT_CH:
      while( (_ch=this.read()) !=TxIStream.EOF) {
         String cmnt= checkComment((char)_ch);
         if(cmnt!=null) {  
            if(skipped!=null) skipped.append(cmnt); 
            continue NEXT_CH; 
         }
         if(wsRange.indexOf(_ch)>=0) { 
            if(skipped!=null) skipped.append((char)_ch); 
            continue NEXT_CH; 
         }
         break NEXT_CH;  
      }
      //this.unread(_ch);    //: b4 A5.024L
      if(_ch!=TxIStream.EOF) this.unread((char)_ch);
      return this;
   }

   public TxIStream skipWS(StringBuilder skipped) throws IOException {
      return skipWithComment(" \t\r\n", skipped);
   }
   public TxIStream skipWS() throws IOException {
      return skipWithComment(" \t\r\n", null);
   }
   public TxIStream skipWS_inLine(StringBuilder skipped) throws IOException {
      return skipWithComment(" \t", skipped);
   }
   public TxIStream skipWS_inLine() throws IOException {
      return skipWithComment(" \t", null);
   }

   //[ skip including the '\n' of current line
   //public TxIStream skipToEOL(boolean show) throws IOException//: b4 A5.012.G
   public TxIStream skipCurrentLine(TxOStream oS) throws IOException
   {
      int _ch;
      final StringBuilder buf=new StringBuilder();
      while( (_ch=this.read()) != TxIStream.EOF) {
         if( _ch ==(int)'\n') {  break;  }
         //if(show) {
         if(oS!=null) {
       //D  oS.p("\\x"+Integer.toHexString(_ch));
            //buf.append((char)_ch);   //: b4 A5.036L.G
            buf.append('/').append((char)_ch);
         }
      }
      if(oS!=null) {
         oS.p("\nskipToEOL: ").pn(buf.toString());
      }
      return this;
   }
   // public TxIStream skipToEOL() throws IOException {
   //    return skipToEOL(true);     //>>>> �i��אּfalse
   // } //: b4 A5.012.G

   @Deprecated
   public TxIStream skipToEOL(TxOStream oS) throws IOException {
      return skipCurrentLine(oS);
   }


   public int  //: ��ȫ�������char, ��EOF��-1�]����peek, �G��int
   peek() throws IOException {
      final int ch=this.read();  
      //this.unread(ch);    //: b4 A5.024L
      if(ch!=TxIStream.EOF) this.unread((char)ch);
      return ( ch );
   }

   public boolean probe(int expected) throws IOException {
      //[ int for EOF
      final int ch=this.read();  
      //this.unread(ch);    //: b4 A5.024L
      if(ch!=TxIStream.EOF) this.unread((char)ch);
      return ( ch==expected );
   }
   public boolean probeEOF() throws IOException {
      return this.probe(TxIStream.EOF);
   }

//[ A5.017L add, return the match count, 
//[ temporarily marked in A5.028.S 
// public int //: return matching_count, success iff matching_count==token.length
// // 0 1 2 3
// // * * *       matching count: 3
// probe(String token, intRef nextCh) throws IOException {
//    final int len= token.length();
//    int ch;  int i;
//    for(i=0; i<len; i++) {
//       ch=this.read(); 
//       if(token.charAt(i)!=ch) {
//          //this.unread(ch);
//          if(ch!=TxIStream.EOF) this.unread((char)ch);
//          if(nextCh!=null) nextCh.v=ch;
//          break;
//       }  
//    }
//    int ans= i;
//    if(ans==len) {
//       if(nextCh!=null) nextCh.v=this.peek(); //: for caller
//    }
//    for(i=ans-1; i>=0; i--) {
//       this.unread(token.charAt(i));
//    }     
//    return ans;
// }

 //[ A5.044L re-open so that oai can parse and get better performance
 //[ A5.034L.H mark since the method can be replaced by 
 //[ probef(str+"%c", intRef) 
   //[ A5.017L add, A5.028.S modify to only return true of false
   public boolean //: return true iff matched
   probe(String token, intRef reportNextCh) throws IOException {
   // 0 1 2 3
   // * * *       matching count: 3
      final int len= token.length();
      int ch;  int i;
      for(i=0; i<len; i++) {
         ch=this.read(); 
         if(token.charAt(i)!=ch) {
            //this.unread(ch);
            if(ch!=TxIStream.EOF) this.unread((char)ch);
            break;
         }  
      }
      if(reportNextCh!=null) reportNextCh.v=this.peek(); 
      int matchCount= i;
      for(i=matchCount-1; i>=0; i--) {
         this.unread(token.charAt(i));
      }     
      return matchCount==len;
      //return this.probef(token+"%c", reportNextCh);  // worse performance
   }

   //[ A5.028L.W add as a convenience method
   public boolean //: return true iff matched
   probe(String token) throws IOException {
      return this.probe(token, null);  
      //return this.probef(token);  // worse performance
   }

   //[ the sacnf actions should throw TxInputException when input error
   //[ so that it can be catched
   public boolean probef(String fStr, Object... args) throws IOException {
      boolean res=false;  
      int markID=pushBtMark();
      try{
         scanf(fStr, args);
         res=true;
      }
      catch(TxInputException xpt) {
     //D System.out.println(xpt);
         res=false;
      }
      finally {     
         popBtMark(markID);
      }
      return res;
   }
 //[ A5.038L.C delete, use the precisely probef
 //public boolean probe(String fStr, Object... args) throws IOException {
 //   return probef(fStr, args); 
 //}

   private static void testProbe() {
      TxIStrStream iS= new TxIStrStream(" ABCDEFG"); 
      intRef nxt=new intRef('?');
      boolean b;
      // //b=iS.probe("ABC", nxt);
      // b=iS.probe("%wABC%c", nxt); //: b4 A5.038L.C
      b=iS.probef("%wABC%c", nxt);
      Std.cout.printf("probe(\"ABC\"): %s, %c\n", 
                      Boolean.toString(b), (char)nxt.v); 
      // //b=iS.probe("ABx", nxt);
      // b=iS.probe("%wABx%c", nxt); //: b4 A5.038L.C
      nxt.v='?';
      b=iS.probef("%wABx%c", nxt);
      Std.cout.printf("probe(\"ABx\"): %s, %c\n", 
                      Boolean.toString(b), (char)nxt.v); 
      // //b=iS.probe("Ax", nxt);
      // b=iS.probe("%wAx%c", nxt); //: b4 A5.038L.C
      nxt.v='?';
      b=iS.probef("%wAx%c", nxt);
      Std.cout.printf("probe(\"Ax\"): %s, %c\n", 
                      Boolean.toString(b), (char)nxt.v); 
      // //b=iS.probe("x", nxt);
      // b=iS.probe("%wx%c", nxt);  //: b4 A5.038L.C
      nxt.v='?';
      b=iS.probef("%wx%c", nxt);
      Std.cout.printf("probe(\"x\"): %s, %c\n", 
                      Boolean.toString(b), (char)nxt.v); 
   }
/*
probe("ABC"): true, D
probe("ABx"): false, ?
probe("Ax"): false, ?
probe("x"): false, ?
*/
//[ previous version
// probe("ABC"): true, D
// probe("ABx"): false, C
// probe("Ax"): false, B
// probe("x"): false, A
//]


   public TxIStream expect(int expected) throws IOException {
      //: expected������char, ��EOF��-1�]����expect, �G��int
      // NEVER   this.skipSpHt(); //: should not automatically call

    // // ��C������, scanf format string����' ', '\t', '\n', '\r', 
    // // keyin�ɳ��O��skipWS
    //   if(expected==' ' || expected=='\t' || expected=='\n' || expected=='\r') {
    //      this.skipWS();  return this;
    //   }  
    // //] A5.016.J add the special process 
    //: A5.028.K ����������, �t�H "%w"�B�z.       

      int ch=this.read();
      if( ch != expected ) { //[ error report
         //this.unread(ch);  //: A5.012L add
         if(ch!=TxIStream.EOF) this.unread((char)ch);
         final StringBuilder msg= new StringBuilder("get ");
         if(ch==TxIStream.EOF) msg.append("EOF");
         else {
            msg.append("\'").append((char)ch)
               .append("\'(0x").append(Integer.toHexString(ch))
               .append(")");
         }
         msg.append(" while expecting \'").append((char)expected)
            .append("\'(0x").append(Integer.toHexString(expected))
            .append(")")
            .append(" in line ").append(_lineNum)
            .append(" of ").append(this.toString()).append("\n");
         throw new tw.fc.TxInputException(msg.toString());
      }
      return this;
   }
//[  int y='A';   // <--- it is OK
//   public TxIStream expect(char expected) throws IOException {
//      return expect((int)expected);
//   }

   public TxIStream expectEOF() throws IOException {
      int ch=this.read();
      if( ch != -1 ) { //[ error report
         final StringBuilder msg= new StringBuilder("get ");
         msg.append("\'").append((char)ch)
               .append("\'(0x").append(Integer.toHexString(ch))
               .append(")")
               .append(" in line ").append(_lineNum)
               .append(" of ").append(this.toString()).append("\n");
         msg.append(" while expecting EOF");
         throw new tw.fc.TxInputException(msg.toString());
      }
      return this;
   }

   //[ expect a single WS
   public TxIStream expectWS() throws IOException {
      int ch=this.read();
      if(ch==' ' || ch=='\t' || ch=='\n' || ch=='\r') {
         return this; 
      } 
      else { //[ error report
         if(ch!=TxIStream.EOF) this.unread((char)ch);
         final StringBuilder msg= new StringBuilder("get ");
         msg.append("\'").append((char)ch)
               .append("\'(0x").append(Integer.toHexString(ch))
               .append(")")
               .append(" in line ").append(_lineNum)
               .append(" of ").append(this.toString()).append("\n");
         msg.append(" while expecting a white space");
         throw new tw.fc.TxInputException(msg.toString());
      }
   }

   public TxIStream expectWS_inLine() throws IOException {
      int ch=this.read();
      if(ch==' ' || ch=='\t') {
         return this; 
      } 
      else { //[ error report
         if(ch!=TxIStream.EOF) this.unread((char)ch);
         final StringBuilder msg= new StringBuilder("get ");
         msg.append("\'").append((char)ch)
               .append("\'(0x").append(Integer.toHexString(ch))
               .append(")")
             ;
         msg.append(" while expecting a white space");
         throw new tw.fc.TxInputException(msg.toString());
      }
   }

//[ re-open since the performance is better
// @Deprecated // after A5.034L.B
   public TxIStream expect(String expectedToken) throws IOException {
      int i=0;
      try{
         for(i=0; i<expectedToken.length(); i++) {
            expect(expectedToken.charAt(i));
         }
      }
      catch(tw.fc.TxInputException xpt) {
         final StringBuffer msg=new StringBuffer("while expecting \"");
         msg.append(expectedToken).append("\" in index ").append(i).append("\n");
         msg.append(xpt.toString());
         throw new TxInputException(msg.toString());
      }
      return this;    //] b4 A5.034L.B
      //scanf(expectedToken);   //: worse performance   
      //return this;
   }

   //--------------------------------------------
   public TxIStream hex() {  radix=16;  return this; }
   public TxIStream dec() {  radix=10;  return this; }
   public TxIStream oct() {  radix=8;   return this; }
   public TxIStream bin() {  radix=2;   return this; }
   public TxIStream setRadix(int r) {
      if(2<=r && r<=36) {  radix=r;   return this;  }
      throw new IllegalArgumentException("radix error: "+r);
   }
   public final int getRadix() {  return radix; }

//[ ���� iNumRef ��static function
// //[ �B�z��ƶ}�Y�����t��
// private int _optSign() throws IOException {
//
//[ ���� doubleRef ��static function
// private final long _parseDigits(
//    int radix, intRef lastByte, booleanRef got
// ) throws IOException  {
//
//[ ���� longRef ��static function
// private final double _parseFraction(intRef lastByte, booleanRef got)
//    throws IOException
// {
//
//[ ���� longRef ��static function
// public long get_long(int radix) throws IOException {
//] b4 A5.022L 

   public long get_long(int radix) throws IOException {
      return longRef.get_long_from(this, radix);
   }
   public long get_long() throws IOException {
      return get_long(this.radix);  //: �Pg()����radix�v�T
   }
   public int get_int(int radix) throws IOException {
      //return (int)get_long(radix);
      return intRef.get_int_from(this, radix);
   }
   public int get_int() throws IOException {
      //return (int)get_long(this.radix);
      return get_int(this.radix);
   }
   public short get_short(int radix) throws IOException {
      //return (short)get_long(radix);
      return shortRef.get_short_from(this, radix);
   }
   public short get_short() throws IOException {
      //return (short)get_long(this.radix);
      return get_short(this.radix);
   }
   public byte get_byte(int radix) throws IOException {
      //return (byte)get_long(radix);
      return byteRef.get_byte_from(this, radix);
   }
   public byte get_byte() throws IOException {
      //return (byte)get_long(this.radix);
      return get_byte(this.radix);
   }

//[ ���� longRef ��static function
// public double get_double() throws IOException {
//] b4 A5.022L 
   public double get_double() throws IOException {
      return doubleRef.get_double_from(this);
   }
   public float get_float() throws IOException {
      //return (float)get_double();
      return floatRef.get_float_from(this);
   }

   public char get_char() throws IOException {
      if(autoSkipWS) {  skipWS(); }
         //: �����w�]�� skipWS,
         //: �Y�Ȥ�{�����QskipWS, �i�H����flag �Χ�� this.read()
      final int ch=this.read();
      if( ch==TxIStream.EOF ) {
         throw new java.io.EOFException("EOF when expect a char");
      }
      return ((char)ch);
   }

   public String getString() throws IOException {
  //D System.err.println("TxIStream::getString()");
      if(autoSkipWS) {  skipWS(); }
         //: �����w�]�� skipWS, �Y�Ȥ�{�����QskipWS,
         //: �i�H����flag �Χ�� this.getToken
      return (this.getToken(' '));
   }

   //[ ���}��public, �Ȥ�� gn(StringBuilder) �� getLine()
   //[ �������U�@�ӿ�J, ����w�M��.
   void _getLine(StringBuilder sb) throws IOException  {
      //! should not automatically skipWS()
      sb.delete(0,sb.length());
      if(this.probeEOF()) {
         //throw new java.io.EOFException("EOF when expect a string"); //: b4 A5.013.J
         return;
      }
      do {
         final int ch=this.read();
         if(ch==TxIStream.EOF) {   
            //this.unread(ch);  
            break;    
         }
         if(ch=='\n') {   break;    }   //: ����w�M��.
         if(ch!='\r') {  //: �o�� CR
            sb.append((char)ch);
         }
      } while(true);
   }

   //[ �������U�@�ӿ�J, ����w�M��.
   public String getLine()  throws IOException  {
      final StringBuilder buf=new StringBuilder(1024);
      //! should not automatically skipWS()
      this._getLine(buf);
      return buf.toString();
   }

   //[ Ū��CR, EOF, �� delimeter ����, �h�^�o�ǸӰ���char
   public void getToken(StringBuilder sb, char delimiter) throws IOException {
  //D System.err.println("getToken(StringBuilder sb, char delimiter)");
      //!   should not automatically skipWS()
      if(probeEOF()) {
         throw new java.io.EOFException("EOF when expect a string");
      }
      sb.delete(0,sb.length());
      do {
         final int ch=this.read();
     //D System.err.println("read()=="+ch);
         if(ch==delimiter || ch=='\n' || ch==TxIStream.EOF) {
            //this.unread(ch);  
            if(ch!=TxIStream.EOF) this.unread((char)ch);
            break;
         } //:  !!  by definition, token can't across line
         if(ch!='\r') { //: �o�� CR, �u�d LF
            sb.append((char)ch);
                //:  Without casting, it WOULD append the
                //:     value representation of ch
        //D System.err.println("sb.append((char)ch): ch=="+ch);
         }
     //D System.out.println("sb.append((char)ch): ch=="+ch);
      } while(true);

   }
   //[ Ū��CR, EOF, �� delimeter ����, �h�^�o�ǸӰ���char
   public String getToken(char delimiter) throws IOException {
   //[  should not automatically skipWS()
   //D System.err.println("getToken(char delimiter)");
      final StringBuilder temp=new StringBuilder(1024);
  //D System.err.println("new StringBuilder(1024)");
      this.getToken(temp, delimiter);
      return temp.toString();
   }
   //[ Ū��CR, EOF, �� delimeterS ����, �h�^�o�ǸӰ���char
   public void getToken(StringBuilder sb, String delimiterS) throws IOException {
    //[  should not automatically skipWS() !
  //D System.err.println("getToken(StringBuilder sb, String delimiterS)");
      if(this.probeEOF()) {
         throw new java.io.EOFException("EOF when expect a string");
      }
      sb.delete(0,sb.length());
      do {
         final int ch=this.read();
         if(delimiterS.indexOf(ch)>=0 || ch=='\n' || ch==TxIStream.EOF) {
            //this.unread(ch);  
            if(ch!=TxIStream.EOF) this.unread((char)ch);
            break;
         } //:  !!  by definition, token can't across line
         if(ch!='\r') { //: �o�� CR, �u�d LF
            sb.append((char)ch);
                //:  Without casting, it WOULD append the
                //:     value representation of ch
         }
      } while(true);
   }

   //[ Ū��CR, EOF, �� delimeterS ����, �h�^�o�ǸӰ���char
   public String getToken(String delimiterS) throws IOException {
      //:  should not automatically skipWS()
      final StringBuilder temp=new StringBuilder(1024);
      this.getToken(temp, delimiterS);
      return temp.toString();
   }

   //[ A5.019 Add
   public String getTokenInRange(String range) throws IOException {
throw new Error("NY");
   }

   public boolean get_boolean() throws IOException {
      // ���\�U��pattern, ��setBooleanPattern ���w��TxIStream��
      // �w�]�� "false", "true"
      if(autoSkipWS) {  skipWS(); }
      boolean ans;    String selected;
      int ch=this.read();
      if(ch== -1) {
         throw new java.io.EOFException("EOF when expect a boolean");
      }
      else if(ch==_boolPattern[1].charAt(0)) {
         ans=true;  selected=_boolPattern[1];
      }
      else if(ch==_boolPattern[0].charAt(0)) {
         ans=false; selected=_boolPattern[0];
      }
      else {
         throw new TxInputException("error on boolean value: "
            +"\'"+(char)ch+"\'");
      }
      for(int i=1; i<selected.length(); i++) {
         this.expect(selected.charAt(i));
      }
      return ans;
   }

   //[ $A5.016L add
   //[ support the virtual-get for an abstract cls
   public Object getInstanceOf(Class<?> cls) {
      // in cls, a static method getInstance(TxIStream ) 
      try {
         java.lang.reflect.Method mtd= 
            cls.getMethod(
               "getInstanceFrom", new Class[] {  TxIStream.class }  
            );
         Object result= mtd.invoke(null, new Object[]{ this } );
         return result;
      }
      catch(java.lang.NoSuchMethodException xpt) {  //: for getMethod(...)
         throw new IllegalArgumentException(cls+".getInstance() not found");
      }
      catch(java.lang.IllegalAccessException xpt) { //: for invoke(...)
         throw new IllegalArgumentException(xpt);
      }
      catch(java.lang.reflect.InvocationTargetException xpt) { //: for invoke(...)
         throw new IllegalArgumentException(xpt);
      }

   }

   //--------------------------------------------
   //[
   //[  most important function of this class
   //[  --> see Std.cin
   //[

   //[  g means 'get', a popular name of c++'s '>>',
   //[  �HWS�@delimeter
   public TxIStream g(ScannableI x) throws IOException {
  //D System.err.println("TxIStream::g(ScannableI x)");
      x.scanFrom(this);  //: virtual on x
      return this;
   }
   public TxIStream g(StringBuilder sb) throws IOException {
      if(autoSkipWS) {  skipWS(); }
      // this.getToken(sb, ' ');  //: �o�˷|�Y��'\t'
      this.getToken(sb, " \t");
      return this;
   }
   //[ Not recommadated to use
   public TxIStream g(StringBuffer sb) throws IOException {
      final StringBuilder s=new StringBuilder();
      this.g(s);
      sb.delete(0, sb.length());  sb.append(s.toString());
      return this;
   }

   //[ �M���Ψ�Ū�@��檺string
   //[ �u��r��~�����n�Ϥ�gn�Pg. ����gn�L�N�q(���ӴNskipWS)
   public TxIStream gn(StringBuilder sb) throws IOException {
      this._getLine(sb);
      return this;
   }
   //[ ����ĳ�ϥ�
   public TxIStream gn(StringBuffer sb) throws IOException {
      //: �M���Ψ�Ū�@��檺string  (�u��string�~�����n�Ϥ�gn�Pg)
      final StringBuilder s=new StringBuilder();
      this._getLine(s);
      sb.delete(0, sb.length());  sb.append(s.toString());
      return this;
   }
   public TxIStream gn(StringRef s) throws IOException {
      //: �M���Ψ�Ū�@��檺string  (�u��string�~�����n�Ϥ�gn�Pg)
      s.v= this.getLine();
      return this;
   }

   //[ �M���Ψ�Ū�@��檺string, ����� StringRef
   public TxIStream gn(MuStr s) throws IOException {
      //! �u��r��~�����n�Ϥ�gn�Pg
      //! ����gn�L�N�q(���ӴNskipWS)
      s.setBy(this.getLine());
      return this;
   }

   public TxIStream gc(ScannableI x) throws IOException {
      //: ��Jstring���t�g, �䥦���HWS��delimeter
      this.g(x).skipWS().expect(',');   return this;
   }
   public TxIStream gc(StringBuilder sb) throws IOException {
      // string�M�׳B�z
      this.getToken(sb, ',');  this.expect(',');   return this;
   }
   //[ ����ĳ�ϥ�
   public TxIStream gc(StringBuffer sb) throws IOException {
      sb.delete(0,sb.length());
      sb.append(this.getToken(','));  this.expect(',');
      return this;
   }
   public TxIStream gc(StringRef x) throws IOException {
      // string�M�׳B�z
      x.v =this.getToken(',');  this.expect(',');   return this;
   }
   public TxIStream gc(MuStr x) throws IOException {
      // string�M�׳B�z
      x.setBy(this.getToken(','));  this.expect(',');   return this;
   }

   public final TxIStream wg(int w, StringBuilder s)
   throws IOException {    //�̦hŪw��char
      this.skipWS();
      for(int i=0; i<w; i++) {
         int ch=this.read();
         if(ch==' ' || ch=='\t' || ch=='\r' || ch=='\n' || ch==TxIStream.EOF) {
            //this.unread(ch);  
            if(ch!=TxIStream.EOF) this.unread((char)ch);
            break;
         }
         s.append((char)ch);
      }
      return this;
   }
   public final TxIStream wg(int w, StringBuffer s)
   throws IOException {    //�̦hŪw��char
      this.skipWS();
      final StringBuilder buf=new StringBuilder();
      this.wg(w,buf);
      s.delete(0,s.length());  s.append(buf.toString());
      return this;
   }
   public final TxIStream wg(int w, StringRef s) throws IOException {
      //�̦hŪw��char
      this.skipWS();
      final StringBuilder buf=new StringBuilder();
      this.wg(w,buf);
      s.v= buf.toString();
      return this;
   }
   public final TxIStream wg(int w, MuStr s) throws IOException {
      //�̦hŪw��char
      this.skipWS();
      final StringBuilder buf=new StringBuilder();
      this.wg(w,buf);
      s.setBy(buf.toString());
      return this;
   }

   //-------------
   // // //public void scanf(String fStr, Object... args) throws IOException { //: b4 A5.012E
   // // public void scanf(String fStr, ScannableI... args) throws IOException { //: b4 A5.028L 
   // public void scanf(String fStr, Object... args) throws IOException {
   //    //final int argSize= args.length;
   // //D System.out.println("\""+fStr+"\"");
   //    final TxIStrStream fStrS= new TxIStrStream(fStr);
   //    fStrS.autoSkipWS=false;
   //    int argIdx=0;
   //    while(true) {
   //       String preStr= PrintfSpec.getPreString(fStrS);
   //   //D System.out.println("expect "+preStr);
   //       this.expect(preStr);
   //       if(fStrS.probeEOF()) break;  
   //       ScanfSpec fSpec= ScanfSpec.getInstanceFrom(fStrS);
   //       if(fSpec.noArg()) fSpec.doScan(this, null);
   //       else {
   //          Object ob=args[argIdx++];
   //          if(ob==null) {
   //             System.out.println("\n scanf(\""+fStr+"\", ... ) "); 
   //             throw new IllegalArgumentException("null on index: "+(argIdx-1));
   //          }
   //          fSpec.doScan(this, ob);
   //       }
   //    }
   // } //: b4 A5.034L.A
   public void scanf(String fStr, Object... args) throws IOException {
  //D System.out.println("\""+fStr+"\"");
      final TxIStrStream fStrS= new TxIStrStream(fStr);
      fStrS.autoSkipWS=false;
      int argIdx=0;
      while(! fStrS.probeEOF() ) {
         final char ch= (char)fStrS.read();
         if(ch!='%') {  this.expect(ch);  }
         else {
            fStrS.unread(ch);
            ScanfSpec fSpec= ScanfSpec.getInstanceFrom(fStrS);
            if(fSpec.noArg()) fSpec.doScan(this, null);
            else {
               Object ob=args[argIdx++];
               if(ob==null) {
                  System.out.println("\n scanf(\""+fStr+"\", ... ) "); 
                  throw new IllegalArgumentException("null on index: "+(argIdx-1));
               }
               fSpec.doScan(this, ob);
            }
         }
      }
   }

   //[ ���T�w probef, probf �����k���S�����D


   //-------------
//>>>>> �˰Q: ���w�g�� ArrayList<T>�F, �o���٦��ݭn��?
   public TxIStream g(ScannableI[] arr) throws IOException {
      for(int i=0; i<arr.length; i++) {
         g(arr[i]);   //: Note: virtual call, �bTxICStream�|auto catch
      }
      return this;
   }

   public TxIStream g(int[] arr) throws IOException {
      final intRef x=new intRef(); /////  final MuInt x=new MuInt();
      for(int i=0; i<arr.length; i++) {
         g(x);  //: Note: virtual call, �bTxICStream�|auto catch
         arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(boolean[] arr) throws IOException {
      final booleanRef x=new booleanRef(); ///// final MuBln x=new MuBln();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(byte[] arr) throws IOException {
      final byteRef x=new byteRef(); ///// final MuByt x=new MuByt();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(short[] arr) throws IOException {
      final shortRef x=new shortRef(); ///// final MuSht x=new MuSht();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(char[] arr) throws IOException {
/////      final MuChr x=new MuChr();
      final charRef x=new charRef();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(long[] arr) throws IOException {
/////      final MuLng x=new MuLng();
      final longRef x=new longRef();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(float[] arr) throws IOException {
/////      final MuFlt x=new MuFlt();
      final floatRef x=new floatRef();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(double[] arr) throws IOException {
/////      final MuDbl x=new MuDbl();
      final doubleRef x=new doubleRef();
      for(int i=0; i<arr.length; i++) {
         g(x);  arr[i]=x.v;
      }
      return this;
   }

   public TxIStream g(String[] arr) throws IOException {
      for(int i=0; i<arr.length; i++) {
         arr[i]= this.getString();
      }
      return this;
   }

   //----
   public TxIStream setBooleanPattern(String f, String t) { //: ����
      if(f==null || t==null) {
         throw new NullPointerException("null boolean pattern");
      }
      if(f.length()<1 || t.length()<1) {
         throw new IllegalArgumentException("empty boolean pattern");
      }
      if(f.charAt(0)==t.charAt(0)) {
         throw new IllegalArgumentException("conflict at leading chars");
      }
      _boolPattern[0]=f;  _boolPattern[1]=t;
      return this;
   }
   public TxIStream setBooleanPattern() { //: ����
      return setBooleanPattern("false", "true");
   }
   public final void getBooleanPattern(String[] dst) {
      if(dst.length!=2) {
         throw new IllegalArgumentException("length should be 2");
      }
      dst[0]=_boolPattern[0];  dst[1]=_boolPattern[1];
   }

 //]------------------------------------------------


//[ ���� iNumRef ��static function
// public static int toDigit(int _ch, int radix) { //:   b4 A5.022L 

   public static void main(String[] dummy) {
      testProbe();
   }

}

