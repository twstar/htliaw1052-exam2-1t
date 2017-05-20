package tw.fc;
import java.io.IOException;

public abstract class JsonValue 
   //implements IndentPrintableI 
   implements PrintableI 
{

   public JsonValue() {   }

 //[JsnValue  ::= 
 //[   JsnString | JsnObject  | JsnArray  ;
 //[   JsnNumber | JsnBool | JsnNull   
   public static JsonValue getInstanceFrom(TxIStream iS) throws IOException {
      if(iS.autoSkipWS) {  iS.skipWS();  }
      char nextCh= (char)iS.peek();
      if(nextCh=='\"') {
         JsonString ans= new JsonString();
         iS.scanf("%z", ans);   return  ans; 
      } 
      else if(nextCh=='[') {
         JsonArray ans= new JsonArray();
         iS.scanf("%z", ans);   return  ans; 
      }
      else if(nextCh=='{') {
         JsonObject ans= new JsonObject();
         iS.scanf("%z", ans);   return  ans; 
      }
      else if(nextCh=='-' || ('0'<=nextCh && nextCh<='9') ) {
         JsonNumber ans= new JsonNumber();
         iS.scanf("%z", ans);   return  ans; 
      }
      else if(nextCh=='t' || nextCh=='f' ) {
         JsonBool ans= new JsonBool();
         iS.scanf("%z", ans);   return  ans; 
      }
      else if(nextCh=='n' ) {
         JsonNull ans= new JsonNull();
         //iS.expect("null");   return  ans; 
         iS.scanf("null");   return  ans; 
      }
      else throw new tw.fc.TxInputException("\n["+nextCh+"]");
   }

}
