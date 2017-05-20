package tw.fc;
import java.io.IOException;
import tw.fc.re.*;

public class ObjRef<T> {
   // public T _;  //: before A30731
   public T v;   //: encourage directly access
   public ObjRef(T o) {  v=o;   }
   public ObjRef() {  v=null;   }
   public ObjRef(ObjRef<T> src) {  v=src.v;   }

   //-----------------------------------
   public final String  toString() {  
      return (v==null)? "null": v.toString(); 
   }
  //ERROR public final boolean equals(T d2) {  return (v.equals(d2)); }
   public final boolean equals(ObjRef<T> d2) {  return (v==d2.v); }
  //ERROR public final boolean equals(Object d2) {  return equals((ObjRef)d2); }
   public final int hashCode() {  return v.hashCode();  }

   public final ObjRef<T> setBy(T o) { v=o;   return this;  }
   public final ObjRef<T> setBy(ObjRef<T> src) {
      v=src.v;  return this;
   }

   //[-------- implements DuplicableI  
   @Implement( DuplicableI.class )
   public final ObjRef<T> duplicate() {  return new ObjRef<T>(this);   }
   //]-------- implements DuplicableI  

   //[-------- implements PrintableI  
   @Implement( PrintableI.class )
   public final void printTo(TxOStream ooo) throws IOException {  
      if(v instanceof PrintableI) ooo.p((PrintableI)v); 
      else ooo.p(v.toString()); 
   }
   @Implement( WidthPrintableI.class )
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      if(v instanceof PrintableI) ooo.p((PrintableI)v); 
      else ooo.p(v.toString()); 
   }
   //]-------- implements PrintableI  

//[ Not very meaningful
   //[-------- implements ScannableI  
//   public final void scanFrom(TxIStream iii) throws IOException {
   //]-------- implements ScannableI  

//[ Troublesome: T may not be comparable
//   public final int compareTo(ObjRef<T> v2) {

//[ Troublesome: confusable meaning
//   public final boolean eq(T v2) {   
//   public final boolean ne(int v2) {  


}

//===============================
class TestObjRef {

   static void dup(ObjRef<String> sR) {
      String s=sR.v;
      sR.v= s+s;
   }

   public static void main(String[] noUse) {
      String s1="abc";
      ObjRef<String> sR=new ObjRef<String>(s1);
      dup(sR);
      s1=sR.v;
      System.out.println(s1);
   }
}