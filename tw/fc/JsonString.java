package tw.fc;
import static tw.fc.Std.*;

//[ Jason string, enclosed in ".
public class JsonString 
   extends JsonValue
   //implements IndentPrintableI, ScannableI 
   implements PrintableI, ScannableI 
{
   public String v="";
  //-------------------------
   public JsonString() {  }
   public JsonString(String s) {  this.v=s; }
  //-------------------------
   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {
      oS.printf("\"%s\"", v);
   }
   //@Override public void printTo(TxOStream oS, String indent) 
   //throws java.io.IOException {
   //   oS.printf("%s\"%s\"", indent, v);
   //}
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
      StringRef sR=new StringRef();
      //iS.scanf("% \"%[^\"\n]\"", sR);
      iS.scanf("%w\"%[^\"\n]\"", sR);
      this.v=sR.v; 
   }

  //--------------------------------------
   @Override public boolean equals(Object obj) {
      return toString().equals(obj);  
   }

   public String getString() {
      return this.v; 
   }

   public CharSequence getChars() {
      return this.v; 
   }

   public int hashCode() {
      return this.v.hashCode(); 
   }
  //======================================
   public static void main(String[] dummy) {
      JsonString s1=new JsonString("ABC");
      cout.printf("s1: %z\n", s1);      
      cout.p("keyin JsonString: ");
      cin.scanf("%z", s1);
      cout.printf("s1: %z\n", s1);      
   } 
}
