package tw.fc;
import static tw.fc.Std.*;

//[ Jason string, enclosed in ".
public class JsonNumber 
   extends JsonValue
   //implements IndentPrintableI, ScannableI 
   implements PrintableI, ScannableI 
{
   public double v;
  //-------------------------
   public JsonNumber() {  }
   public JsonNumber(double d) {  this.v=d; }
  //-------------------------
   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {
      oS.printf("%f", v);
   }
   //@Override public void printTo(TxOStream oS, String indent) 
   //throws java.io.IOException {
   //   oS.printf("%s%d", indent, v);
   //}
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
      double d=iS.get_double();
      this.v=d; 
   }

  //---------------------------------------
   public java.math.BigDecimal bigDecimalValue() {
      throw new Error("Not supported");
   }
   public java.math.BigInteger bigIntegerValue() {
      throw new Error("Not supported");
   }
   public java.math.BigInteger bigIntegerValueExact() {
      throw new Error("Not supported");
   }
   public boolean equals(Object obj) {
      throw new Error("Not supported");
   }
   public int hashCode() {
      long v= Double.doubleToLongBits(this.v);
      return (int)(v^(v>>>32));
   }

   public byte byteValue() {   return (byte)this.v;   }
   public double doubleValue() {   return this.v;   }

   public int intValue() {   return (int)this.v;   }
   public int intValueExact() {
      int i=(int)this.v;
      if(i==this.v) return i;
      throw new ArithmeticException();   
   }
   public boolean isIntegral() {
      int i=(int)this.v;
      return (i==this.v);
   }
   public long longValue() {   return (long)this.v;   }
   public long longValueExact() {
      long i=(long)this.v;
      if(i==this.v) return i;
      throw new ArithmeticException();   
   }
 	 public String toString() {
      return Double.toString(this.v);
   }

  //======================================
   public static void main(String[] dummy) {
      JsonNumber s1=new JsonNumber(3.5E2);
      cout.printf("s1: %z\n", s1);      
      cout.p("keyin JsonNumber: ");
      cin.scanf("%z", s1);
      cout.printf("s1: %z\n", s1);      
   } 
}
/*
C:\Users\ht\Desktop\TwFC\TwFC_working>java tw.fc.JsonNumber
s1: 350.0
keyin JsonNumber: 67
s1: 67.0
*/
