package tw.fc ;
import java.io.IOException;
import java.lang.StringBuilder;

//[ Principle:
//[    In an input stream, 
//[    any place can find a commant must can find a white space.
//[    So the processing of comments should triggled by skipWS().
//[    So that the logic of a lexer can be simplified.
//[

// ******************************
public class CommentManager {
   TxIStream _iS;
   final String _begin, _end;
   StringBuilder _buf;

   public boolean reportFlag=false;
   // int _state=0;
   //   //: match count of comment head and tail

   CommentManager(TxIStream iStream, String b, String e) {
      this.addIStream(iStream);
      if(b==null || b.length()==0) {
         throw new IllegalArgumentException("Illegal begin string");
      }
      if(e==null || e.length()==0) {
         throw new IllegalArgumentException("Illegal end string");
      }
      this._begin=b;  this._end=e;
      this._buf= new StringBuilder();
   }
   
   //[ <n--1> relation
   void addIStream(TxIStream iStream) {
      this._iS=iStream;
      iStream.passiveAddCommentManager(this);
   }

   char firstChar() {  return _begin.charAt(0);  } 

 //[ after matching pattern's 1st char, 
 //[ try to match total remaining pattern
   private boolean tryMatch(String patn) throws IOException {
       int at=1;
       while(at<patn.length()) {
          int ch= this._iS.read();  
          this._buf.append((char)ch); 
          if(ch==patn.charAt(at)) {
             at++;
          }
          else {
           //[ mismatch: unreading and unbuffering until the head-char 
             if(ch!= TxIStream.EOF) this._iS.unread((char)ch);
             while(--at>0) {
                this._buf.deleteCharAt(this._buf.length()-1);      
                this._iS.unread(patn.charAt(at));
             } 
             // now (at==0), head-char not unread/unbuffer
             return false;
          }
       }
       //: match the pattern 
       return true; 
   }

   String  //: return the comment, null if not match the begin string 
   getComment() throws IOException {
      //char beginCh= this._begin.charAt(0);
      //this._iS.expect(beginCh); 
      this._buf.delete(0, this._buf.length());
      this._buf.append(this._begin.charAt(0)); 
           //: already read() b4 calling getComment()
      if(!tryMatch(this._begin)) return null;
      while(true) {
       //[ collecting comment content 
         int ch= this._iS.read();
         if(ch==TxIStream.EOF) throw 
            new tw.fc.TxInputException("EOF in comment");
         this._buf.append((char)ch); 
         if(ch==this._end.charAt(0)) {  
            if(tryMatch(this._end)) {
               String ans= this._buf.toString();
               if(reportFlag) System.out.println(ans); 
               return ans;
            }
         }
         else ;  //: try next char
      }       
   }

  //-------------------------------------

   static void testExtract(String s, String b, String e) 
   throws IOException {
      TxIStrStream iS= new TxIStrStream(s);
      CommentManager cmtM= new CommentManager(iS, b, e);
      int ch;
      while((ch=iS.read())!=TxIStream.EOF) {
         if(ch==cmtM.firstChar()) {
            //iS.unread((char)ch);
            String cmt=cmtM.getComment();
            if(cmt!=null) System.out.print("{"+cmt+"}"); 
            else {
               System.out.print((char)ch);
            } 
         }  
         else {
            System.out.print((char)ch);
         } 
      }
      System.out.println();
   }

   static void test1() throws IOException {
      String b="<!--", e="-->"; 
      testExtract("abcd<!-- efg -->hijk", b, e);
      testExtract("abcd<!-- efg --hi-->j-->k", b, e);
      try{
         testExtract("abcd<!-- efg --hi--jk", b, e);
      } 
      catch(tw.fc.TxInputException xpt) {
         System.out.println("\n"+xpt);
      }   
      testExtract("abcd<!-efg <!-- hi -->jk", b, e);
      testExtract("abcd<!-- e\nf\n\ng -->hijk", b, e);
      System.out.println();
   }
/*
abcd{<!-- efg -->}hijk
abcd{<!-- efg ---hhi-->}j-->k
abcd
tw.fc.TxInputException: EOF in comment
abcd<!-efg {<!-- hi -->}jk
abcd{<!-- e
f

g -->}hijk
*/

   static void test2() throws IOException {
      String b="//", e="\n"; 
      testExtract("abcd// efg \nhijk", b, e);
      testExtract("abcd// efg \nhi\nk", b, e);
      try{
         testExtract("abcd// efg ", b, e);
      } 
      catch(tw.fc.TxInputException xpt) {
         System.out.println("\n"+xpt);
      }   
      testExtract("abcd/efg // hi \njk", b, e);
      System.out.println();
   }
/*
abcd{// efg
}hijk
abcd{// efg
}hi
k
abcd
tw.fc.TxInputException: EOF in comment
abcd/efg {// hi
}jk
*/


   public static void main(String[] dummy) throws IOException  {
      test1();
      test2();
   }


}