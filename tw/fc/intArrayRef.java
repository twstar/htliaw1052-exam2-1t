package tw.fc ;
import java.io.IOException;
//import java.io.EOFException;
import tw.fc.re.*;

//************   intArrayRef.java   ****************

public class intArrayRef 
   implements DuplicableI<intArrayRef>, PrintableI, SetableI<intArrayRef>
{
   //public int[] _;  //:  before A30731
   public int[] v;  //:  encorage direct access

   //-----------------------------------
   public  intArrayRef() {  v=null; }
   public  intArrayRef(int[] a) {  v=a; }
   public  intArrayRef(intArrayRef src) {  v= src.v; }          

   //-----------------------------------
   public final String  toString() {  return v.toString(); }
   public final boolean equals(int[] d2) {  return (v==d2); }
   public final boolean equals(intArrayRef d2) {  return (v==d2.v); }
   public final boolean equals(Object d2) {  return equals((intArrayRef)d2); }
   public final int hashCode() {  return v.hashCode();  }

   public final intArrayRef setBy(int[] a) { v=a;   return this;  }

   //[-------- implements DuplicableI  
   @Implement( DuplicableI.class )
   public final intArrayRef duplicate() {  return new intArrayRef(this);   }
   //]-------- implements DuplicableI  
   //[-------- implements PrintableI  
   @Implement( PrintableI.class )
   public final void printTo(TxOStream ooo) throws IOException {  ooo.p(v);  }
   @Implement( WidthPrintableI.class )
   public final void widthPrintTo(int w, TxOStream ooo) throws IOException {  
      ooo.p(v); 
   }
   //]-------- implements PrintableI  

   //[-------- implements DuplicableI  
   public final intArrayRef setBy(intArrayRef src) {
      v=src.v;  return this;
   }
   //]-------- implements DuplicableI  

}

