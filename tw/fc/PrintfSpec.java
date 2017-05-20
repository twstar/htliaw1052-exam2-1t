package tw.fc;
import java.io.IOException;
import java.math.BigInteger;

// java.util.IllegalFormatException
//    extended by java.lang.RuntimeException
//      extended by java.lang.IllegalArgumentException
//        extended by java.util.IllegalFormatException

//[ java.util.IllegalFormatException 的ctor未開放, 只能用它的subclass 
 import java.util.UnknownFormatConversionException; 
 import java.util.IllegalFormatConversionException;        //: is of an incompatible type   
 import java.util.UnknownFormatFlagsException;
 import java.util.DuplicateFormatFlagsException; 
 import java.util.IllegalFormatFlagsException;             //: illegal combination flags 
 import java.util.FormatFlagsConversionMismatchException;  //: a conversion and flag are incompatible
 import java.util.IllegalFormatWidthException; 
 import java.util.MissingFormatWidthException; 
 import java.util.IllegalFormatPrecisionException; 
 import java.util.MissingFormatArgumentException; 
//  IllegalFormatCodePointException;          // 不
//]

////import tw.fc.IllegalFormatException;


/* KNR C
 The 'format string' contains two types of objects: 
 ordinary characters, which are copied to the output stream, and 
 'conversion specifications', each of which causes conversion and 
 printing of the next successive argument to fprintf. 
 Each conversion specification begins with the character % and ends with 
 a 'conversion character'.
 Between the % and the conversion character there may be, in order:
   Flags (in any order), which modify the specification:
     '-': which specifies left adjustment of the converted argument in its field.
     '+': which specifies that the number will always be printed with a sign.
     ' ': if the first character is not a sign, a space will be prefixed.
     '0': for numeric conversions, specifies padding to the field width 
          with leading zeros.
     '#': which specifies an alternate output form. 
          For o, the first digit will become zero. 
          For x or X, 0x or 0X will be prefixed to a non-zero result. 
          For e, E, f, g, and G, the output will always have a decimal point; 
          for g and G, trailing zeros will not be removed.
  A number specifying a minimum field width. 
     The converted argument will be printed in a field at least this wide, 
     and wider if necessary. 
     If the converted argument has fewer characters than the field width 
     it will be padded on the left 
     (or right, if left adjustment has been requested) 
     to make up the field width. 
     The padding character is normally space, 
     but is 0 if the zero padding flag is present.
  A period, which separates the field width from the precision.
  A number, the precision, 
     that specifies the maximum number of characters to be printed 
     from a string, 
     or the number of digits to be printed after the decimal point 
     for e, E, or f conversions, 
     or the number of significant digits for g or G conversion, 
     or the number of digits to be printed for an integer 
     (leading 0s will be added to make up the necessary width).
  A length modifier h, l, or L. 
     "h" indicates that the corresponding argument is to be printed 
         as a short or unsigned short; 
     "l" indicates that the argument is a long or unsigned long, 
     "L" indicates that the argument is a long double.
*/

/*
  JDK API: class java.util.Formatter
 
  The 'format string' is a String which may contain fixed text and 
  one or more embedded format specifiers
  
  The format specifiers for general, character, and numeric types 
  have the following syntax:
         %[argument_index$][flags][width][.precision]conversion
  argument_index: The 1st argument is referenced by "1$", ... , etc.      
  flags: a set of characters that modify the output format. 
  width: indicating the minimum number of characters to be written.
  precision: used to restrict the number of characters. 
  conversion: indicating how the argument should be formatted.
  Conversions are divided into the following categories:
   General - may be applied to any argument type
   Character - may be applied to basic types which represent Unicode characters: char, Character, byte, Byte, short, and Short. This conversion may also be applied to the types int and Integer when Character.isValidCodePoint(int) returns true
   Numeric
      Integral - may be applied to Java integral types: byte, Byte, short, Short, int and Integer, long, Long, and BigInteger
      Floating Point - may be applied to Java floating-point types: float, Float, double, Double, and BigDecimal 
   Date/Time - may be applied to Java types which are capable of encoding a date or time: long, Long, Calendar, and Date.
   Percent - produces a literal '%' ('\u0025')
   Line Separator - produces the platform-specific line separator 
 
  The format specifiers for types which are used to represents dates ...
 
   Conversions are divided into the following categories:
  
      General - may be applied to any argument type
      Character - may be applied to basic types which represent Unicode characters: char, Character, byte, Byte, short, and Short. This conversion may also be applied to the types int and Integer when Character.isValidCodePoint(int) returns true
      Numeric
          Integral - may be applied to Java integral types: byte, Byte, short, Short, int and Integer, long, Long, and BigInteger
          Floating Point - may be applied to Java floating-point types: float, Float, double, Double, and BigDecimal 
      Date/Time - may be applied to Java types which are capable of encoding a date or time: long, Long, Calendar, and Date.
      Percent - produces a literal '%' ('\u0025')
      Line Separator - produces the platform-specific line separator 
  
  Conversions denoted by an upper-case character are the same as those 
  for the corresponding lower-case conversion + invocation of String.toUpperCase()
  
  // Oracle 的 tutorial 說是 converter
  //  https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
  
  
  Conversion 	Argument Category 	Description
  'b', 'B' 	general 	If the argument arg is null, then the result is "false". If arg is a boolean or Boolean, then the result is the string returned by String.valueOf(). Otherwise, the result is "true".
  'h', 'H' 	general 	If the argument arg is null, then the result is "null". Otherwise, the result is obtained by invoking Integer.toHexString(arg.hashCode()).
  's', 'S' 	general 	If the argument arg is null, then the result is "null". If arg implements Formattable, then arg.formatTo is invoked. Otherwise, the result is obtained by invoking arg.toString().
  'c', 'C' 	character 	The result is a Unicode character
  'd' 	integral 	The result is formatted as a decimal integer
  'o' 	integral 	The result is formatted as an octal integer
  'x', 'X' 	integral 	The result is formatted as a hexadecimal integer
  'e', 'E' 	floating point 	The result is formatted as a decimal number 
            in computerized scientific notation
  'f' 	floating point 	The result is formatted as a decimal number
  'g', 'G' 	floating point 	
        The result is formatted using computerized scientific notation or decimal format, depending on the precision and the value after rounding.
  'a', 'A' 	floating point 	
        The result is formatted as a hexadecimal floating-point number with a significand and an exponent
  't', 'T' 	
        date/time 	Prefix for date and time conversion characters. See Date/Time Conversions.
  '%' 	percent 	The result is a literal '%' ('\u0025')
  'n' 	line separator 	The result is the platform-specific line separator
  
  Any characters not explicitly defined as conversions are illegal and are reserved for future extensions. 
   Flags
  
  The following table summarizes the supported flags. 
  y means the flag is supported for the indicated argument types.
  Flag  General 	
            Character 	
                Integral 	
                    Floating Point 	
                       Date/Time 	
                            Description 
  '-' 	y 	y 	y 	y 	y 	The result will be left-justified.
  '#' 	y1 	- 	y3 	y 	- 	The result should use a conversion-dependent alternate form
  '+' 	- 	- 	y4 	y 	- 	The result will always include a sign
  ' ' 	- 	- 	y4 	y 	- 	The result will include a leading space for positive values
  '0' 	- 	- 	y 	y 	- 	The result will be zero-padded
  ',' 	- 	- 	y2 	y5 	- 	The result will include locale-specific grouping separators
  '(' 	- 	- 	y4 	y5 	- 	The result will enclose negative numbers in parentheses
  
  1 Depends on the definition of Formattable.
  2 For 'd' conversion only.
  3 For 'o', 'x', and 'X' conversions only.
  4 For 'd', 'o', 'x', and 'X' conversions applied to BigInteger or 'd' applied to byte, Byte, short, Short, int and Integer, long, and Long.
  5 For 'e', 'E', 'f', 'g', and 'G' conversions only.
  
  Any characters not explicitly defined as flags are illegal and are reserved for future extensions. 

*/


// ******************************************************
//[  format specifiers
//abstract class PrintFormatSpec extends FormatSpec  //: b4 A5.032L.I
public abstract class PrintfSpec extends IOFormatSpec 
{

   // char conversion='\0';   //:  b4 A5.020

//I  SpecAux SpecAttrib=null;

   PrintfSpec() {   }  //: for subclass without attribute 
   PrintfSpec(SpecAttrib atb) {  super(atb);  }


//I  static String getPreString(TxIStream iS) throws IOException {

   // //boolean isPercent() {  return this.conversion=='%' }
   // boolean noArg() {  return this.conversion=='%' || this.conversion=='n';  }
   boolean noArg() {  return false;  }

   boolean hasIndent() {
      // return (this.specAux!=null && this.specAux.hasFlag('|') ); //: b4 A5.032L
      return (this.hasFlag('|') );
   }
 
   static PrintfSpec getInstanceFrom(TxIStream iS) throws IOException {
      iS.expect('%');
      char nextCh= (char)iS.read();  //: no skipWS
  //D System.out.printf("["+nextCh+"]");

      if( nextCh=='%' ) {
         return new PrintfSpec_percent();  //: "%%" 
      }
      else if( nextCh=='n' ) {
         return new PrintfSpec_n();   //: "%n" 
      }
      else iS.unread(nextCh);    

      final SpecAttrib aux= new SpecAttrib("-+ 0#|");
      aux.scanFrom(iS);

      nextCh= (char)iS.read();  //: no skipWS
    //[   conversion  
      //D System.out.printf("beginConversion["+nextCh+"]");
      switch(nextCh) {
      case 'c':  case 'C':  
                 return new PrintfSpec_c(aux);   //: a Unicode character 
      case 's':  return new PrintfSpec_s(aux);  
      case 'd':  return new PrintfSpec_d(aux);  
      //case 'h':  case 'H':  //: error b4 A%.039L   
      case 'x':  case 'X':   
                 return new PrintfSpec_x(aux, nextCh);
      case 'o':  return new PrintfSpec_o(aux);   
      case 'e':  case 'E':  case 'f': case 'g':  case 'G':
                 return new PrintfSpec_f(aux, nextCh);  
      case 'z':  return new PrintfSpec_z(aux);    //:  PrintableI 
      default:  throw new UnknownFormatConversionException("%"+nextCh);
      }
   }
   //--------------------------


   // //void output(TxOStream oS, Object ob) throws IOException {
   // abstract void print(TxOStream oS, Object ob) throws IOException ;  //: b4 A5.022L
   abstract void doPrint(TxOStream oS, Object ob) throws IOException ;

   //void doPrint(TxOStream oS, String indent, Object ob) throws IOException {
   //   throw new IllegalArgumentException("Not supported on "+this.getClass());
   //}
   abstract void doPrint(TxOStream oS, String indent, Object ob) throws IOException ;

}

// *********************************************
//    %%
// class PrintPercentSpec extends PrintfSpec {  //: b5 A5.032L.I
class PrintfSpec_percent extends PrintfSpec {
   PrintfSpec_percent() {  super();   }
   boolean noArg() {  return true;  }
   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
      oS.p('%');   
   }
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      throw new Error("Not supported on "+this.getClass());
   }
}

// *********************************************
//    %n
//class PrintLineSepSpec extends PrintfSpec {  //: b4 A5.032L.I
class PrintfSpec_n extends PrintfSpec {
   PrintfSpec_n() {  super();   }
   boolean noArg() {  return true;  }
   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
      String lineSep=System.getProperty("line.separator");  
      oS.p(lineSep);   
   }
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      throw new Error("Not supported on "+this.getClass());
   }
}
// *********************************************
//    %z   since z is the last Latin alphabet
//class PrintPrintableSpec extends PrintfSpec {  //: b4 A%.032L.I
class PrintfSpec_z extends PrintfSpec {
   //PrintfSpec_z(SpecAttrib atb) {  super();  this.setAttrib(atb);  } //: b4 A5.032L.G 
   PrintfSpec_z(SpecAttrib atb) {  super(atb);  } 
   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
      final PrintableI P= (PrintableI)ob;   P.printTo(oS);
   }
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      final PrintableI P= (PrintableI)ob;   
      // //if(P instanceof IndentPrintableI) {
      // //   ((IndentPrintableI)P).printTo(oS, indent);  //: for complex object
      // //}
      // //else {
      // //   oS.p(indent);  P.printTo(oS); 
      // //}  //: management in oS.p(indent, P) 
      // oS.p(indent, P);
      oS.dp(indent, P);
   }
}
// *********************************************
//    %c
// class PrintCharSpec extends PrintfSpec {  //: b4 A5.032L.I
class PrintfSpec_c extends PrintfSpec {
   //PrintfSpec_c(SpecAttrib atb) {  super();   setAttrib(atb);  } //: b4 A5.032L.G 
   PrintfSpec_c(SpecAttrib atb) {  super(atb);  } 
   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
      if(ob == null ) {
         throw new MissingFormatArgumentException(" null "); 
      }
      else if(ob instanceof Character) { 
         oS.p(ob.toString());
      }
      else if(ob instanceof Byte || ob instanceof Short) {
         final Number S= (Number)ob;
         oS.p((char)S.shortValue());
      }
      else if(ob instanceof charRef) {
         final charRef chR= (charRef)ob;
         oS.p(chR.v); 
      }  
      else {
         throw new IllegalFormatConversionException('c', ob.getClass());
      }
   }
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      if(oS.startOfLine()) oS.p(indent);  
      this.doPrint(oS, ob); 
   }
}

// *********************************************
//    %s
// class PrintStringSpec extends PrintfSpec {  //: b4 A5.032L.I
class PrintfSpec_s extends PrintfSpec {
   //PrintfSpec_s(SpecAttrib atb) {  super();   this.setAttrib(atb);  } //: b4 A5.032L.G 
   PrintfSpec_s(SpecAttrib atb) {  super(atb);  } 

   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
   // >>> TODO flag -
      if(ob==null) {  oS.p(" null ");  }
      else if(ob instanceof String) {  oS.p((String)ob);  }
      else if(ob instanceof StringBuffer) {  oS.p( ob.toString() );  }
      else if(ob instanceof StringBuilder) {  oS.p( ob.toString() );  }
      else if(ob instanceof StringRef) {  oS.p(((StringRef)ob).v);  }
      else {
         oS.p( ob.toString() );   //: in the philosophy of java
         //throw new IllegalArgumentException(
         //   "arg is a "+ob.getClass()+" while need a string"
         //);
      } 
   }
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      if(oS.startOfLine()) oS.p(indent);  
      this.doPrint(oS, ob); 
   }

}

// *********************************************
//   %d || %h || %H || %o
//abstract class PrintInteralSpec extends PrintfSpec { //: A5.032L.I
abstract class PrintfSpec_i extends PrintfSpec {
   //PrintfSpec_i(SpecAttrib atb) {  super();  this.setAttrib(atb);  } //: b4 A5.032L.G    
   PrintfSpec_i(SpecAttrib atb) {  super(atb);  }    
   @Override abstract void doPrint(TxOStream oS, Object ob) throws IOException ;
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      if(oS.startOfLine()) oS.p(indent);  
      if(ob==null) throw new MissingFormatArgumentException(" null ");
      this.doPrint(oS, ob); 
   }
}

// *********************************************
//    %d
// class PrintDecSpec extends PrintfSpec_i {  //: b4 A5.032L.I
class PrintfSpec_d extends PrintfSpec_i {
   PrintfSpec_d(SpecAttrib a) {  super(a);  }
   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
   // >>> TODO flags
      if(ob==null) throw new MissingFormatArgumentException(" null ");
      // int's has been boxed to Integers
      if( ob instanceof iNumRef ||
          ob instanceof Byte    || ob instanceof Short || 
          ob instanceof Integer || ob instanceof Long || 
          ob instanceof java.math.BigInteger 
      ) {
         String s= ob.toString();
         oS.p(s);
      }
      else {
         throw new IllegalFormatConversionException('d', ob.getClass());
      } 
   }
//I void doPrint(TxOStream oS, String indent, Object ob)  throws IOException
}

// *********************************************
//    %x  %X
// class PrintHexSpec extends PrintfSpec_i {  //: b4 A5.032L.I
class PrintfSpec_x extends PrintfSpec_i {
   char conversion='\0';   //: Not initialized
   PrintfSpec_x(SpecAttrib a, char cv) {  
      super(a);   this.conversion=cv ; 
   }

   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
   // >>> TODO flags
      if(ob==null) throw new MissingFormatArgumentException(" null ");
      else if(ob instanceof iNumRef) {
         final iNumRef i= (iNumRef)ob;
         oS.p(i.toHexString());
      }
      else if(ob instanceof Byte || ob instanceof Short || 
              ob instanceof Integer ||  ob instanceof Long  
      ) {
         final Number i= (Number)ob;
         oS.p(Long.toHexString(i.longValue()));
      }
      else if(ob instanceof BigInteger) {
         final BigInteger bi= (BigInteger)ob;
         oS.p(bi.toString(16));
      } 
      else {
         throw new IllegalFormatConversionException('x', ob.getClass());
      } 
   }
//I void doPrint(TxOStream oS, String indent, Object ob)  throws IOException
}

// *********************************************
//    %o 
// class PrintOctSpec extends PrintfSpec_i {  //: b4 A5.032L.I
class PrintfSpec_o extends PrintfSpec_i {
   PrintfSpec_o(SpecAttrib a) {  super(a);   }

   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
      if(ob==null) throw new MissingFormatArgumentException(" null ");
      else if(ob instanceof iNumRef) {
         final iNumRef i= (iNumRef)ob;   oS.p(i.toOctalString());
      }
      else if(ob instanceof Byte || ob instanceof Short || 
         ob instanceof Integer ||  ob instanceof Long  
      ) {
         final Number i=(Number)ob;  
         oS.p(Long.toOctalString(i.longValue()));
      }
      else if(ob instanceof BigInteger) {
         final BigInteger bi=(BigInteger)ob;
         oS.p(bi.toString(8));
      } 
      else {
         throw new IllegalFormatConversionException('o', ob.getClass());
      } 
   }
//I void doPrint(TxOStream oS, String indent, Object ob)  throws IOException
}

// *********************************************
//    %e  %E  %f  %g  %G
// class PrintFloatingSpec extends PrintfSpec {  //: b4 A5.032L.I
class PrintfSpec_f extends PrintfSpec {
   char conversion='\0';   //: Not initialized
   PrintfSpec_f(SpecAttrib atb, char cv) {  
      //super();   this.conversion=cv;   this.setAttrib(atb);  //: b4 A5.032L.G    
      super(atb);   this.conversion=cv;   
   }
   @Override void doPrint(TxOStream oS, Object ob) throws IOException {
      // >>> TODO e, f, BigDecimal
      if(ob==null) throw new MissingFormatArgumentException(" null ");
      else if(ob instanceof fNumRef) {
         final fNumRef f= (fNumRef)ob;   oS.p(f.toString());
      }
      else if(ob instanceof Float || ob instanceof Double) {
         oS.p(ob.toString());
      } 
      else {
         throw new IllegalFormatConversionException(this.conversion, ob.getClass());
      } 
   }
   @Override void doPrint(TxOStream oS, String indent, Object ob) 
   throws IOException {
      if(oS.startOfLine()) oS.p(indent);  
      this.doPrint(oS, ob); 
   }

}


