package tw.fc;
import static tw.fc.Std.*;

//[ Jason string, enclosed in ".
public class JsonNull 
   extends JsonValue
   //implements IndentPrintableI, ScannableI 
   implements PrintableI, ScannableI 
{
  //-------------------------
   public JsonNull() {  }
  //-------------------------
   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {
      oS.p("null");
   }
   //@Override public void printTo(TxOStream oS, String indent) 
   //throws java.io.IOException {
   //   oS.printf("%snull", indent);
   //}
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
      if(iS.autoSkipWS) {  iS.skipWS();  }
      //iS.expect("null");  
      iS.scanf("null");  
   }

  //======================================
   public static void main(String[] dummy) {
   } 
}
