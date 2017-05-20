package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;

//************   StringRef.java   ****************

public class StringRef  
   implements DuplicableI<StringRef>, PrintableI, SetableI<StringRef>, ScannableI, 
              java.lang.Comparable<StringRef>
{
   //public String _;    //:  before A30731
   public String v;    //:  encorage direct access

   //---------------------------------------- 
   public  StringRef() {  v=""; }
   public  StringRef(String x) {  v=x; }
   public  StringRef(StringRef D) {  v=D.v; }

   //---------------
   public final String toString() {  return (""+v); }
   public final boolean equals(StringRef d2) {  return (v.equals(d2.v)); }
   public final boolean equals(Object d2) {  return equals((StringRef)d2); }
   public final boolean equals(String d2) {  return (v.equals(d2)); }
   public final int hashCode() {  
      return v.hashCode();  
   }

   public final StringRef setBy(String d) { v=d;   return this;  }
 
//[-------- implements OperatableI  
   public final StringRef duplicate() {  return new StringRef(this);   }
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.wp(w,v); 
   }

/////   public final SetableI setBy(DuplicableI s) {
/////      final StringRef src=(StringRef)s;
/////      v=src.v;  return this;
/////   }
   public final StringRef setBy(StringRef src) {
      v=src.v;  return this;
   }
   public final void scanFrom(TxIStream iii) throws IOException {
   //D System.err.println("StringRef::scanFrom(TxIStream iii)");
    // ¥ÑgetString°µ iii.skipWS();
      v=iii.getString();  
   }
//]-------- implements OperatableI  

   public final String concate(String d2) { return v+d2; }
   public final String concate(StringRef d2) { return v+d2.v; }
   public final StringRef concateBy(String d2) { v+=d2; return this; }
   public final StringRef concateBy(StringRef d2) { v+=d2.v; return this; }

    public StringRef concateBy(Object obj) {
       return concateBy(obj==null? "null": obj.toString());
    }
    public StringRef concateBy(boolean b) {
       v+=b; return this;
    }
    public StringRef concateBy(char c) {
       v+= c;   return this;
    }
    public StringRef concateBy(int i) {
       v+= Integer.toString(i);   return this;
    }
    public StringRef concateBy(long l) {
       v+= Long.toString(l);   return this;
    }
    public StringRef concateBy(float f) {
       v+= Float.toString(f);   return this;
    }
    public StringRef concateBy(double d) {
       v +=Double.toString(d);   return this;
    }




   public final int compareTo(String v2) {
      return v.compareTo(v2);
   }
   public final int compareTo(StringRef v2) { 
      return v.compareTo(v2.v);
   }
//   public final int compareTo(Object o) { //: implements java.lang.Comparable
//      return v.compareTo((StringRef)o);      
//   }

   public final boolean eq(String v2) {  return compareTo(v2)==0; }
   public final boolean ne(String v2) {  return compareTo(v2)!=0; }
   public final boolean eq(StringRef v2) {  return compareTo(v2.v)==0; }
   public final boolean ne(StringRef v2) {  return compareTo(v2.v)!=0; }

}