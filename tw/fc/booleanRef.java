package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//****************************
public class booleanRef 
   implements DuplicableI<booleanRef>, PrintableI, SetableI<booleanRef>, ScannableI, 
              java.lang.Comparable<booleanRef>
{
   //public boolean _;  //:  before A30731
   public boolean v;  //:  encorage direct access

   //-----------------------------------
   public  booleanRef() {  v=false; }
   public  booleanRef(boolean i) {  v=i; } 
   public  booleanRef(booleanRef src) {  v=src.v; }          
///   public  booleanRef(Integer I) {  v=I.intValue(); }    //: 不太有用
///   public  booleanRef(String s) { v=Integer.parseInt(s);  } //: 不太有用

   //-----------------------------------
///   public final boolean   value() {  return v; }
///   public final boolean to_boolean() {  return (v!=0);  }
///   public final byte to_byte() {  return ((byte)v);  }
///   public final boolean to_short() {  return ((boolean)v);  }
///   public final char to_char() {  return ((char)v);  }

   public final int to_int() {  return (v?1:0); }
   public static int to_int(boolean b) {  return (b?1:0); }
   public final String toString() {  return String.valueOf(v); }
   public static String toString(boolean b) {  return String.valueOf(b); }
   public final boolean equals(boolean d2) {  return (v==d2); }
   public final boolean equals(booleanRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((booleanRef)d2); }
   public final int hashCode() {  return v?1:0;  }

   public final booleanRef setBy(boolean i) { v=i;   return this;  }
///   public final booleanRef setBy(Integer I) {  v=I.intValue(); return this; }
///   public final booleanRef setBy(String s) {  
///      v=java.lang.Integer.parseInt(s); return this;   
///   }

   //[-------- implements DuplicableI  
   public final booleanRef duplicate() {  return new booleanRef(this);   }
   //]-------- implements DuplicableI  
   //[-------- implements PrintableI  
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }
   //]-------- implements PrintableI  

   //[-------- implements DuplicableI  
//I   public final DuplicableI duplicate() {  return new booleanRef(this);   }
   public final booleanRef setBy(booleanRef src) {
      v=src.v;  return this;
   }
/////   public final SetableI setBy(DuplicableI s) {
/////      final booleanRef src=(booleanRef)s;  //: dynamic cast
/////      v=src.v;  return this;
/////   }
   public final void scanFrom(TxIStream iii) throws IOException {
      // iii.skipWS();   //: 由getBoolean負責
      v=iii.get_boolean();  
   }
   //]-------- implements DuplicableI  

//----------------------------- 

   public final boolean and(boolean d2) { return v&&d2 ; }
   public final boolean or(boolean d2) { return v||d2 ; }
   public final boolean xor(boolean d2) { return v^d2; }
   public final boolean and(booleanRef d2) { return v&&d2.v ; }
   public final boolean or(booleanRef d2) { return v||d2.v ; }
   public final boolean xor(booleanRef d2) { return v^d2.v; }

   public final boolean not() { return !v ; }
//   public final booleanRef not() { v=!v; return this; }
          // 用 x.setBy(!x.v)

   public final booleanRef andBy(boolean d2) { v &= d2; return this; }
   public final booleanRef orBy(boolean d2) { v |= d2; return this; }
   public final booleanRef xorBy(boolean d2) { v ^= d2; return this; }
   public final booleanRef andBy(booleanRef d2) { v &= d2.v; return this; }
   public final booleanRef orBy(booleanRef d2) { v |= d2.v; return this; }
   public final booleanRef xorBy(booleanRef d2) { v ^= d2.v; return this; }


   //---------------------------
   public final int compareTo(boolean v2) {
      if(v) {
         if(v2) return 1;
         else return 0;
      }
      else {
         if(v2) return -1;
         else return 1;
      }
   }
   public final int compareTo(booleanRef v2) {  return compareTo(v2.v); }
///   public final int compareTo(Object o) { //: implements java.lang.Comparable
///      return compareTo((booleanRef)o);      
///   }
   public final boolean eq(boolean v2) {  return compareTo(v2)==0; }
   public final boolean ne(boolean v2) {  return compareTo(v2)!=0; }
   public final boolean gt(boolean v2) {  return compareTo(v2)>0; }
   public final boolean ge(boolean v2) {  return compareTo(v2)>=0; }
   public final boolean lt(boolean v2) {  return compareTo(v2)<0; }
   public final boolean le(boolean v2) {  return compareTo(v2)<=0; }
   public final boolean eq(booleanRef v2) {  return compareTo(v2)==0; }
   public final boolean ne(booleanRef v2) {  return compareTo(v2)!=0; }
   public final boolean gt(booleanRef v2) {  return compareTo(v2)>0; }
   public final boolean ge(booleanRef v2) {  return compareTo(v2)>=0; }
   public final boolean lt(booleanRef v2) {  return compareTo(v2)<0; }
   public final boolean le(booleanRef v2) {  return compareTo(v2)<=0; }

}

