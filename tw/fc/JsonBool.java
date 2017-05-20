package tw.fc;
import static tw.fc.Std.*;

//[ Jason string, enclosed in ".
public class JsonBool 
   extends JsonValue
   //implements IndentPrintableI, ScannableI 
   implements PrintableI, ScannableI 
{
   public boolean v;
  //-------------------------
   public JsonBool() {  }
   public JsonBool(boolean b) {  this.v=b; }
  //-------------------------
   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {
      oS.printf("%s", (v?"true":"false"));
   }
   //@Override public void printTo(TxOStream oS, String indent) 
   //throws java.io.IOException {
   //   oS.printf("%s%s", indent, (v?"true":"false"));
   //}
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
      if(iS.autoSkipWS) {  iS.skipWS();  }
      if(iS.probe('t')) {
         //iS.expect("true");  this.v=true;
         iS.scanf("true");  this.v=true;
      }  
      else {
         //iS.expect("false");  this.v=false;
         iS.scanf("false");  this.v=false;
      }
   }

  //======================================
   public static void main(String[] dummy) {
      JsonBool s1=new JsonBool(false);
      JsonBool s2=new JsonBool(true);
      cout.printf("%z, %z\n", s1, s2);      
      cout.p("keyin JsonBool: ");
      cin.scanf("%z", s1);
      cout.printf("s1: %z\n", s1);      
   } 
}
/*
C:\Users\ht\Desktop\TwFC\TwTx_working>java tw.fc.JsonBool
false, true
keyin JsonBool: true
s1: true
*/
