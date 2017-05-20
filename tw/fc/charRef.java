package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//************   charRef.java   ****************

public class charRef 
   implements DuplicableI<charRef>, PrintableI, SetableI<charRef>, ScannableI, 
              WidthPrintableI,
              java.lang.Comparable<charRef>
{
   //public char _;  //:  before A30731
   public char v;  //:  encorage direct access

   //-----------------------------------
   public  charRef() {  v=0; }
   public  charRef(char i) {  v=i; } 
   public  charRef(charRef src) {  v=src.v; }          
///   public  charRef(Integer I) {  v=I.intValue(); }    //: 不太有用
///   public  charRef(String s) { v=Integer.parseInt(s);  } //: 不太有用

   //-----------------------------------
///   public final char   value() {  return v; }
///   public final boolean to_boolean() {  return (v!=0);  }
///   public final byte to_byte() {  return ((byte)v);  }
///   public final char to_short() {  return ((char)v);  }
///   public final char to_char() {  return ((char)v);  }
   public final String  toString() {  return String.valueOf(v); }
   public final boolean equals(char d2) {  return (v==d2); }
   public final boolean equals(charRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((charRef)d2); }
   public final int hashCode() {  return v;  }

   public final charRef setBy(char i) { v=i;   return this;  }
///   public final charRef setBy(Integer I) {  v=I.intValue(); return this; }
///   public final charRef setBy(String s) {  
///      v=java.lang.Integer.parseInt(s); return this;   
///   }

   //[-------- implements DuplicableI  
   public final charRef duplicate() {  return new charRef(this);   }
   //]-------- implements DuplicableI  
   //[-------- implements PrintableI  
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }
   //]-------- implements PrintableI  

   //[-------- implements DuplicableI  
//I   public final DuplicableI duplicate() {  return new charRef(this);   }
/////   public final SetableI setBy(DuplicableI s) {
/////      final charRef src=(charRef)s;  //: dynamic cast
/////      v=src.v;  return this;
/////   }
   public final charRef setBy(charRef src) {
      v=src.v;  return this;
   }
   public final void scanFrom(TxIStream iii) throws IOException {
      //  Note:  The standard method Integer.parseInt  
      //         can't work on a input stream with hybrid data.
      // iii.skipWS();   //: 由getInt負責
      v=iii.get_char();  
   }
   //]-------- implements DuplicableI  


   //---------------------------
   public final int compareTo(char v2) {
   //[ 沒有Long.compare(char,char)
      final int d=v-v2;
      if(d>0) return 1;
      else if(d<0) return -1;
      else return 0;
   }
   public final int compareTo(charRef v2) {  return compareTo(v2.v); }
///   public final int compareTo(Object o) { //: implements java.lang.Comparable
///      return compareTo((charRef)o);      
///   }
   public final boolean eq(char v2) {  return compareTo(v2)==0; }
   public final boolean ne(char v2) {  return compareTo(v2)!=0; }
   public final boolean gt(char v2) {  return compareTo(v2)>0; }
   public final boolean ge(char v2) {  return compareTo(v2)>=0; }
   public final boolean lt(char v2) {  return compareTo(v2)<0; }
   public final boolean le(char v2) {  return compareTo(v2)<=0; }
   public final boolean eq(charRef v2) {  return compareTo(v2)==0; }
   public final boolean ne(charRef v2) {  return compareTo(v2)!=0; }
   public final boolean gt(charRef v2) {  return compareTo(v2)>0; }
   public final boolean ge(charRef v2) {  return compareTo(v2)>=0; }
   public final boolean lt(charRef v2) {  return compareTo(v2)<0; }
   public final boolean le(charRef v2) {  return compareTo(v2)<=0; }

   public final boolean isZero() {  return (v==0);  }
   public final boolean eq0() {  return (v==0);  }
   public final boolean ne0() {  return (v!=0);  }
   public final boolean gt0() {  return (v>0);  }
   public final boolean ge0() {  return (v>=0);  }
   public final boolean lt0() {  return (v<0);  }
   public final boolean le0() {  return (v<=0);  }

}

