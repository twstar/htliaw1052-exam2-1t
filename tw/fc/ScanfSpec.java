package tw.fc;
import java.io.IOException;
//  java.lang.RuntimeException
//     extended by java.lang.IllegalArgumentException
//        extended by java.util.IllegalFormatException
//[ java.util.IllegalFormatException 的ctor未開放, 只能用它的subclass 
 import java.util.UnknownFormatConversionException; 
 import java.util.IllegalFormatConversionException;        //: is of an incompatible type   
//] 

// import tw.fc.IllegalFormatException;

/*
in KNR 
B.1.3 Formatted Input

A conversion specification determines the conversion of the next input field. 
Normally the result is placed in the variable pointed to 
by the corresponding argument. 
If assignment suppression is indicated by *, as in %*s, however, 
the input field is simply skipped; no assignment is made. 

An input field is defined as a string of non-white space characters; 
it extends either to the next white space character or
until the field width, if specified, is exhausted. 

This implies that scanf will read across line boundaries
to find its input, since newlines are white space. 
(White space characters are blank, tab, newline, 
carriage return, vertical tab, and formfeed.)

The conversion character indicates the interpretation of the input field. 
The corresponding argument must be a pointer. 
The legal conversion characters are shown in Table B.2.

Character     
    Input Data;       Argument type
%   literal %;                  no assignment is made.
d   decimal integer;            int*
i   integer;                    int*. 
    The integer may be in octal (leading 0) or hexadecimal (leading 0x or 0X).
o   octal integer (with or without leading zero); int *.
u   unsigned decimal integer;   unsigned int *.
x   hexadecimal integer (with or without leading 0x or 0X); int*.
c   characters;                 char*. 
    The next input characters are placed in the indicated array, 
    up to the number given by the width field; the default is 1. 
    No '\0' is added. 
    The normal skip over white space characters is suppressed in this case; 
    to read the next non-white space character, use %1s.
s   string of non-white space characters (not quoted); char *, 
    pointing to an array of characters large enough to hold the string 
    and a terminating '\0' that will be added.
e,f,g
    floating-point number;       float *. 
    The input format for float's is an optional sign, a string of numbers 
    possibly containing a decimal point, and an optional exponent field
    containing an E or e followed by a possibly signed integer.
p   pointer value as printed by printf("%p");, void *.
n   writes into the argument the number of characters read so far by this call; 
    int *. No input is read. The converted item count is not incremented.
[...]
    matches the longest non-empty string of input characters from the set 
    between brackets;            char *. A '\0' is added. 
    []...] includes ] in the set.
[^...]
    matches the longest non-empty string of input characters not from the set 
    between brackets;            char *. A '\0' is added. 
    [^]...] includes ] in the set.

*/

// ******************************************************
//[  format specifiers, for the pattern $...c
// abstract class ScanFormatSpec extends IOFormatSpec  //: b4 A5.032L.I
public abstract class ScanfSpec extends IOFormatSpec 
{

//I  SpecAttrib specAux=null;

   ScanfSpec() {    }  //: for subclass without attribute 
   ScanfSpec(SpecAttrib atb) {  super(atb);  }


//I  static String getPreString(TxIStream iS) throws IOException {

//   @Override
//   public final void scanFrom(TxIStream iS) throws IOException {

   boolean noArg() {  return false;  }

   public static ScanfSpec getInstanceFrom(TxIStream iS) throws IOException {
      iS.expect('%');
      char nextCh= (char)iS.read();  //: no skipWS
  //D System.out.printf("beginConversion["+nextCh+"]");
      if(nextCh=='%') {  //:  %%
         return new ScanfSpec_percent(); 
      }
      else iS.unread(nextCh);

      // final SpecAttrib aux= new SpecAttrib("-+ 0#");  //: error b4 A5.028L
      final SpecAttrib atb= new SpecAttrib("*!");
         //: "*" for supress assignment as in C
         //: "!" for future work, to store the sprinkles (and line numbers?)
      atb.scanFrom(iS);

   //------------
      nextCh= (char)iS.read();  //: here no skipWS
      switch(nextCh) {     
      //case ' ':
      case 'w':  return new ScanfSpec_w(atb);
      case 'c':  return new ScanfSpec_c(atb); //:  "%c", a Unicode character  
      case 's':  return new ScanfSpec_s(atb); 
      case 'i':  return new ScanfSpec_i(atb);   
      case 'd':  return new ScanfSpec_d(atb);   
      //case 'h':  case 'H':    //: error b4 A5.039L
      case 'x':  case 'X':  
                 return new ScanfSpec_h(atb, nextCh); 
      case 'o':  return new ScanfSpec_o(atb); 
      case 'e': case 'f': case 'g':
                 return new ScanFloatingSpec(atb, nextCh); 
      case 'z':  return new ScanParsableSpec(atb); 
      case '[':  //:  "%[...]" or "%[^...]"
         //ScanfSpec_token ans= ScanfSpec_token.getInstanceFrom(iS);  ans.setAttrib(atb);
                 //: b4 A5.032L.H
         return ScanfSpec_token.getInstanceFrom(iS, atb);
      default: throw new UnknownFormatConversionException("%"+nextCh);
      }
   }

   //[ Not necessary, 
   //[ since getInstanceFrom(TxIStream iS) is only used in TxIStream
   //public static ScanfSpec getInstanceFrom(TxIStrStream iS) {
   //   try {  return getInstanceFrom((TxIStream)iS);   }
   //   catch(IOException xpt) {  throw new Error(xpt); }
   //}


  //-----------------------
   //abstract void scan(TxIStream iS, Object ob) throws IOException ;  //: b4 A5.022L
   abstract void doScan(TxIStream iS, Object ob) throws IOException ;

}                        

// *********************************************
// class ScanPercentSpec extends ScanfSpec {   //: b4 A5.032L.I
class ScanfSpec_percent extends ScanfSpec {   //: "%%"
   ScanfSpec_percent() {  super();  }
   boolean noArg() {  return true;  }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      if(ob!=null) throw new Error();
      iS.expect('%');
   }
}

// *********************************************
// class SkipWSSpec extends ScanfSpec {   //: b4 A5.032L.I
class ScanfSpec_w extends ScanfSpec {   //: "%w"
   //ScanfSpec_w(SpecAttrib atb) {  super();  this.setAttrib(atb);  }  //: b4 A5.032L.G
   ScanfSpec_w(SpecAttrib atb) {  super(atb);  }  
   boolean noArg() {  return true;  }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      if(ob!=null) throw new Error();
      int minW= super.getMinWidth();
      while(minW>0) {
      //D System.out.println(minW);
         if(!super.hasFlag('-')) {  iS.expectWS();  }  //: A5.036L.F add 
         else {       iS.expectWS_inLine();         } 
         minW--;
      } 
      if(!super.hasFlag('-')) {   iS.skipWS();   }
      else {   iS.expectWS_inLine();         } 
  //D System.out.println("ScanfSpec_w skipWS");
   }
}

// *********************************************
// class ScanCharSpec extends ScanfSpec {  //: b4 A5.032L.I
class ScanfSpec_c extends ScanfSpec {  //: "%c"
   //ScanfSpec_c(SpecAttrib atb) {  super();  this.setAttrib(atb);  } //: b4 A5.032L.G
   ScanfSpec_c(SpecAttrib atb) {  super(atb);  } 
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      if(ob instanceof charRef) {
         final charRef C= (charRef)ob;            
         C.scanFrom(iS);
      }
      else if(ob instanceof intRef) {  //: may be EOF           
         final intRef C= (intRef)ob;   
         if(iS.probeEOF()) {  C.v= TxIStream.EOF;  } 
         else {
            charRef cR= new charRef();
            cR.scanFrom(iS);
            C.v=cR.v;
         }
      }
      else throw new IllegalFormatConversionException('s', ob.getClass());
  
   }
}

// *********************************************
// class ScanStringSpec extends ScanfSpec {   //: b4 A5.032L.I 
class ScanfSpec_s extends ScanfSpec {   //:  "%s"
   //ScanfSpec_s(SpecAttrib atb) {  super();  this.setAttrib(atb);  } //: b4 A5.032L.G
   ScanfSpec_s(SpecAttrib atb) {  super(atb);  } 
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      if(ob instanceof StringRef) {
         ((StringRef)ob).scanFrom(iS);
      }
      else if(ob instanceof StringBuffer) {   // possible after A5.028L
         StringBuffer sb=(StringBuffer)ob;
         sb.delete(0, sb.length());
         String s=iS.getString();
         sb.append(s); 
      }
      else if(ob instanceof StringBuilder) {   // possible after A5.028L
         StringBuilder sb=(StringBuilder)ob;
         sb.delete(0, sb.length());
         String s=iS.getString();
         sb.append(s); 
      }
      else throw new IllegalFormatConversionException('s', ob.getClass());
   }
}

// *********************************************
// 目前用在 JsonString, 以後可能用 %r 取代
// abstract class ScanfSpec_token extends ScanfSpec {  //:  b4 A5.032L
abstract class ScanfSpec_token extends ScanfSpec {  //:  %[^ ...] 或  %[ ...]
   String charSet;

   // ScanfSpec_token(String elms) {  super();  this.charSet=elms;   } //: b4 A5.032L.H
   ScanfSpec_token(String elms, SpecAttrib atb) { super(atb);  this.charSet=elms; }

 //[ reading after "%[", to build ScanfSpec_tokenD or ScanfSpec_tokenR
   // public static ScanTokenSpec getInstanceFrom(TxIStream iS) //: b4 A5.032L.H
   public static ScanfSpec_token getInstanceFrom(TxIStream iS, SpecAttrib atb) 
   throws IOException {
      //[ bug b4 A5.028 --> see bugCase1() of the class
      // char nextCh= (char)iS.read();
      // final boolean isCap= (nextCh=='^');
      // if(isCap) {  nextCh= (char)iS.read();  }
      // final StringBuilder charsInBracket= new StringBuilder();
      // if(nextCh==']') {  //: the set contains ']'
      //    charsInBracket.append(nextCh);   nextCh= (char)iS.read();
      // }
      // while(nextCh!=']') {
      //    charsInBracket.append(nextCh);   nextCh= (char)iS.read();
      // } 
      // final String set= charsInBracket.toString();
      // return isCap? new ScanfSpec_tokenD(set): 
      //                 new ScanfSpec_tokenR(set);
      //]
      int nextCh= iS.read();  //: must use int to detect EOF
      final boolean isCap= (nextCh=='^');
      if(isCap) {  nextCh= iS.read();  }
      final StringBuilder charsInBracket= new StringBuilder();
      if(nextCh==']') {  //: the set contains ']'
         //charsInBracket.append(nextCh);   // <--- bug: see bugCase2()   
         charsInBracket.append((char)nextCh);   
         nextCh= iS.read();
      }
      while(nextCh!=']') {
         if(nextCh==TxIStream.EOF) throw 
            new UnknownFormatConversionException("EOF in parsing \"%[...");
         //charsInBracket.append(nextCh);   // <--- bug: see bugCase2()   
         charsInBracket.append((char)nextCh);   
         nextCh= iS.read();
      } 
      final String set= charsInBracket.toString();
      return isCap? new ScanfSpec_tokenD(set, atb): 
                    new ScanfSpec_tokenR(set, atb);
   }

   @Override abstract void doScan(TxIStream iS, Object ob) throws IOException ;

   //-----------------------------------
   private static void bugCase1() {
      char a=(char)(-1);
      System.out.println((int)a);  //: get 65535
   }
   private static void bugCase2() {
      final StringBuilder charsInBracket= new StringBuilder();
      int ch='A';
      charsInBracket.append(ch);
      System.out.println(charsInBracket.toString());  //: get 65  
   }

   //[ demo bugs
   public static void main(String[] dummy) {
      System.out.println(" --- demo bugs --- ");
      //bugCase1();
      bugCase2();
   }
}
// *********************************************
//class ScanDelimTokenSpec extends ScanfSpec_token {  //:  "%[^ ...]"
class ScanfSpec_tokenD extends ScanfSpec_token {  //:  "%[^ ...]"
   //ScanfSpec_tokenD(String s) {  super(s);  } //: b4 A5.032L.H
   ScanfSpec_tokenD(String s, SpecAttrib atb) {  
      super(s, atb);  
  //D System.out.println("ScanfSpec_tokenD("+s+")");    
   }

   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      final StringRef str= (StringRef)ob;
      str.v=iS.getToken(charSet);
   }
}

// *********************************************
//class ScanRangeTokenSpec extends ScanfSpec_token {  //:  b4 A%.032L.I
class ScanfSpec_tokenR extends ScanfSpec_token {  //:  "%[ ...]"
   //ScanfSpec_tokenR(String s) {  super(s);   } //: b4 A5.032L.H
   ScanfSpec_tokenR(String s, SpecAttrib atb) {  super(s, atb);   }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      final StringRef str= (StringRef)ob;
      str.v=iS.getTokenInRange(charSet);
   }
}

// *********************************************
//abstract   //: b4 A5.028L
//class ScanInteralSpec extends ScanfSpec {  //:  b4 A5.032L.I
class ScanfSpec_i extends ScanfSpec {  //:  "%i"
   ScanfSpec_i(SpecAttrib atb) {   
      //super();   // this.conversion=cv;  
      //this.setAttrib(atb);  
      //] b4 A5.032L.G
      super(atb);
   }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      // TODO BigInteger  
      final iNumRef num= (iNumRef)ob;   
      int leadDigit= iS.peek();
      final int storeRadix= iS.getRadix();
      if(leadDigit!='0') {
         iS.setRadix(10);
      }
      else {  //:  0  or 0x
         iS.skip(1);
         int nextDigit= iS.peek(); 
         if(nextDigit!='x' && nextDigit!='X') {
            iS.setRadix(8);
         }
         else {
            iS.setRadix(16);
         }
      }
      iS.g(num);
      iS.setRadix(storeRadix);
   }
}

// *********************************************
// class ScanDecSpec extends ScanfSpec_i {  //:  b4 A5.032L.I
class ScanfSpec_d extends ScanfSpec_i {  //:   "%d"
   ScanfSpec_d(SpecAttrib a) {   super(a);    }
   // @Override void doScan(TxIStream iS, Object ob) throws IOException {
   //    // TODO BigInteger  
   //    final iNumRef num= (iNumRef)ob;   
   //    // num.scanFrom(iS);
   //    iS.g(num);
   // } //: b4 A5.039L
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      // TODO BigInteger  
      final iNumRef num= (iNumRef)ob;   
      final int storeRadix= iS.getRadix();
      iS.setRadix(10);  
      iS.g(num);
      iS.setRadix(storeRadix);
   }
}

// *********************************************
// class ScanHexSpec extends ScanfSpec_i { //: b4 A5.032L.I
class ScanfSpec_h extends ScanfSpec_i { //:  "%x"  
   char conversion='\0';   //: Not initialized
   ScanfSpec_h(SpecAttrib a, char cv) {   
      super(a);   this.conversion=cv;  
   }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      // TODO BigInteger  
      final iNumRef num= (iNumRef)ob;
      final int storeRadix= iS.getRadix();
      iS.setRadix(16);  
      //int leadDigit= iS.peek();  //: bug b4 A5.039L.D
      int leadDigit= iS.skipWS().peek();
      if(leadDigit=='0') {  
         iS.skip(1);
         int nextDigit= iS.peek();
         if(nextDigit=='x' || nextDigit=='X') {
            iS.skip(1);
         }
         else {
            throw new TxInputException(
                     "read "+(char)nextDigit+" while expecting x or X"
            );
         }
      }
      //num.scanFrom(iS);  
      iS.g(num);
      iS.setRadix(storeRadix);
   }
}

// *********************************************
//class ScanOctSpec extends ScanfSpec_i {  //: b4 A5.032L.I
class ScanfSpec_o extends ScanfSpec_i {  //:  "%o"
   ScanfSpec_o(SpecAttrib s) {   super(s);  }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
    // TODO BigInteger  
      final iNumRef num= (iNumRef)ob;
      final int storeRadix= iS.getRadix();
      iS.setRadix(8);  
      //int leadDigit= iS.peek();  //: bug b4 A5.039L.D
      int leadDigit= iS.skipWS().peek();
      if(leadDigit=='0') iS.skip(1);
      //num.scanFrom(iS);  
      iS.g(num); 
      iS.setRadix(storeRadix);
   }
}

// *********************************************
class ScanFloatingSpec extends ScanfSpec {   //:  "%f"
   char conversion='\0';   //: Not initialized
   ScanFloatingSpec(SpecAttrib atb, char cv) {  
      //super();   this.conversion=cv;   this.setAttrib(atb);  //: b4 A5.032L.G  
      super(atb);   this.conversion=cv;   
   }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      // >>> TODO e, E, f, g 
      final fNumRef num= (fNumRef)ob;  
      //num.scanFrom(iS);
      iS.g(num);
   }
}

// *********************************************
class ScanParsableSpec extends ScanfSpec {
   // ScanParsableSpec() {   //: b4 A%.032L.G
   ScanParsableSpec(SpecAttrib atb) {  
      super(atb);   // this.conversion='z';   
   }
   @Override void doScan(TxIStream iS, Object ob) throws IOException {
      if(ob==null) throw new IllegalArgumentException("scanf argument is null");
      final ScannableI sc= (ScannableI)ob;   
      // sc.scanFrom(iS);
      iS.g(sc); 
   }
}

