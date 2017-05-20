package tw.fc;
import java.io.IOException;


//[ temporarily use
//  java.lang.RuntimeException
//     extended by java.lang.IllegalArgumentException
//        extended by java.util.IllegalFormatException
// java.util.IllegalFormatException 的ctor未開放, 只能用它的subclass 

//class IllegalFormatException extends java.lang.IllegalArgumentException {
//   IllegalFormatException(String s) {  super(s);  }
//}

// ********************************
// abstract class FormatSpec   //: b4 A5.032L.I
public abstract class IOFormatSpec  
// extended_by PrintfSpec
// extended_by ScanfSpec
{

   private SpecAttrib _attribs=null;

   IOFormatSpec () {  }  //: for subclass without attribute 
   IOFormatSpec (SpecAttrib atb) {  this._attribs= atb;  }

//[ b4 A5.032L.H
// //[ for %[ ...] and %[^ ...], the following method must be called after new.  
//   // void setSpecAux(SpecAttrib a) {  this._attribs= a;  } 
//   void setAttrib(SpecAttrib atb) {  this._attribs= atb;  } 
//]

 //[ used b4 A5.034L.A 
 // //[ get the prefix string before %
 // public static String getPreString(TxIStream iS) throws IOException {
 //    StringBuilder sb=new StringBuilder();
 //    while(!iS.probeEOF()) {
 //       char ch=(char)iS.read();  //: no skipWS
 //       if(ch=='%') {
 //          iS.unread(ch);     break;
 //       }
 //       sb.append(ch);
 //    }      
 //    return sb.toString();
 // }

   public final boolean hasFlag(char flg) {
      return (this._attribs!=null && this._attribs.hasFlag(flg)) ;
   }

   //[ positive iff legal
   final int getMinWidth() {   
      if(this._attribs==null) return 0;
      else return this._attribs.getMinWidth();  
   }   

   //[ positive iff legal
   final int getPrecision() {   
      if(this._attribs==null) return 0;
      else return this._attribs.getPrecision();  
   }   

}