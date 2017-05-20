package tw.fc;
import static tw.fc.Std.*;

public class JsonPair 
   implements IndentPrintableI, ScannableI 
{
   JsonString key;
   JsonValueRef valueR= new JsonValueRef();

   public JsonPair(JsonString k, JsonValue val) {
      this.key=k;   this.valueR.v=val;     //: grab argument
   }
   public JsonPair(String k, JsonValue val) {  //: A5.028L add
      this.key=new JsonString(k);   this.valueR.v=val;     //: grab argument
   }
   public JsonPair(String k, String val) {
      this.key= new JsonString(k);   this.valueR.v= new JsonString(val); 
   }
   public JsonPair() { 
      this("", "");
   }

   //@Override public void printTo(TxOStream oS, String indent) 
   @Override public void indentPrintTo(TxOStream oS, String indent) 
   throws java.io.IOException 
   {
      //oS.printf("%s%z: ", indent, key);
      oS.printf("%|z: ", indent, key);
      // boolean complex= 
      //    valueR.v instanceof JsonArray || valueR.v instanceof JsonObject;
      // if(!complex) {
      //    oS.printf("%z", valueR.v);  //: following the line of key
      // }
      // else {
      //    //oS.pn();
      //    //((IndentPrintableI)valueR.v).printTo(oS, indent);
      //    oS.printf("\n%|z", indent, valueR.v);  //: 直接用 valueR 會扁掉.
      // }  //: b4 A5.023 startOfLine()
      oS.printf("%|z", indent, valueR.v);  //: 直接用 valueR 會扁掉.
   } 
   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {  
      //this.indentPrintTo(oS, "");  
      oS.printf("%z: %z", key, valueR.v); 
   }

 
 //[  JsnObject ::=  '{' Members? '}'  ;           
 //[  Members   ::=  JsnPair ( ',' JsnPair )*  ; 
 //[  JsnPair  ::=  JsnString ':' JsnValue  ;
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
   //   iS.scanf("%z : ", key);
   // //D cout.printf("...//%z\n", this.key);
   //   this.valueR.v= JsonValue.getInstanceFrom(iS); 
   // //D cout.printf("...//[%z]\n", this.value);
      // //iS.scanf("%z : %z", key, valueR);  //: b4 A5.028L
      // iS.scanf("%z% :%z", key, valueR);
      iS.scanf("%z%w:%z", key, valueR);
   }

  //============================================
   static void test1() {
      JsonString s1=new JsonString("key1");
      JsonString s2=new JsonString("value1");
      JsonPair p1=new JsonPair(s1, s2);
      cout.pn(p1);
      JsonPair p2=new JsonPair("key2", "value2");
      cout.pn(p2);
   }
   static void test2() {
      JsonPair p1=new JsonPair("key1", "value1");
      cout.printf("p1=%z\n", p1);
      TxOStrStream oS= new TxOStrStream();
      oS.p(p1);
      TxIStrStream iS= new TxIStrStream(oS.toString());
      JsonPair p2=new JsonPair("aaa", "bbb");
      iS.scanf("%z", p2);
      cout.printf("p2=%z", p2);
   }
   public static void main(String[] dummy) {
      //test1();
      test2();
   } 
   

}
