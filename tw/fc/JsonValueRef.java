package tw.fc;
import java.io.IOException;
import tw.fc.re.*;

public class JsonValueRef implements ScannableI//, PrintableI
{
   public JsonValue v;   //: encourage directly access

   //-----------------------------------
   public JsonValueRef(JsonValue o) {  v=o;   }
   public JsonValueRef() {  v=null;   }

   //-----------------------------------
   public final String  toString() {  
      return (v==null)? "null": v.toString(); 
   }


   //[-------- implements PrintableI  
//   @Override @Implement( PrintableI.class )
//   public final void printTo(TxOStream oS) throws IOException {  
//      if(v!=null) oS.p((PrintableI)v); 
//      else oS.p("null content"); 
//   }
   //]-------- implements PrintableI  

   //[-------- implements ScannableI  
   @Override public final void scanFrom(TxIStream iS) throws IOException {
      this.v= JsonValue.getInstanceFrom(iS); 
   }
   //]-------- implements ScannableI  


}

