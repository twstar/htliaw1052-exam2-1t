package tw.fc;
import java.util.ArrayList;
import static tw.fc.Std.*;

public class JsonObject extends JsonValue 
   implements IndentPrintableI, ScannableI 
{
   ArrayList<JsonPair> list= new ArrayList<JsonPair>();

   public JsonObject() {  }

   JsonObject add(JsonPair val) {  this.list.add(val);  return this;  }


   //@Override public void printTo(TxOStream oS, String indent) 
   @Override public void indentPrintTo(TxOStream oS, String indent) 
   throws java.io.IOException {
      //oS.printf("%s{\n", indent);  //: b4 A5.023L
      oS.printf("%|s\n", indent, "{");
      //D final String indent1=indent+"~~~";
      final String indent1=indent+"   ";
      final int len= list.size();
      for(int i=0; i<len; i++) {
         String optComma= (i<len-1)? ",": "";
         //list.get(i).printTo(oS, indent1); 
         //oS.pn(optComma);
         oS.printf("%|z%s\n", indent1, list.get(i), optComma);
      }
      //oS.printf("%s}", indent);
      oS.printf("%|s", indent, "}");
   } 

   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {  
      //this.indentPrintTo(oS, "");  
      oS.printf("%s ", "{");
      final int len= list.size();
      for(int i=0; i<len; i++) {
         String optComma= (i<len-1)? ",": "";
         oS.printf("%z%s ", list.get(i), optComma);
      }
      oS.printf("%s", "}");
   }

 //[ JsnObject ::=  '{' Members? '}'  ;         
 //[ Members   ::=  JsnPair ( ',' JsnPair )*  ; 
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
      // //iS.scanf(" {");
      // iS.scanf("% {");
      iS.scanf("%w{");
      // //if( iS.skipWS().probe('}') ) {  }
      // if( iS.probe("%w}") ) {  }  //: b4 A5.038L.C
      if( iS.probef("%w}") ) {  }
      else {
         while(true) {
            JsonPair v= new JsonPair();
            //iS.scanf(" %z", v);   this.add(v);    //: b4 A5.028L
            iS.scanf("%w%z", v);   this.add(v);  
            // //if(! iS.skipWS().probe(',') ) break;
            // if(! iS.probe("%w,") ) break;  //: b4 A5.038L.C
            if(! iS.probef("%w,") ) break;
            //iS.expect(',');
            iS.scanf("%w,");
         }
      }  
      // //iS.scanf(" }");  //: b4 A5.028L.K
      // iS.scanf("% }");
      iS.scanf("%w}");
   }


  //--------------------------------------------

   protected JsonValue get(String name) {
      for(JsonPair jP: list) {
         //if(jP.key.equals(name)) {  //: bug b4 A5.028L.N
         if(jP.key.v.equals(name)) {
            return jP.valueR.v;
         }
      }
      return null;
   } 

  //[ alphbetic order as in JavaEE API

   public boolean getBoolean(String name) {
      JsonValue jV= get(name);
      if(jV!=null) return ((JsonBool)jV).v; 
      else throw new NullPointerException("No mapping name: "+name);
   }

   public boolean getBoolean(String name, boolean defaultValue) {
      JsonValue jV= get(name);
      if(jV!=null) return ((JsonBool)jV).v; 
      else return defaultValue;
   }

   public int getInt(String name) {
      return getJsonNumber(name).intValue();
   }
   public int getInt(String name, int defaultValue) {
      JsonNumber jN= getJsonNumber(name);
      if(jN!=null) return jN.intValue(); 
      else return defaultValue; 
   }

   public JsonArray getJsonArray(String name) {
      JsonValue jV= get(name);
      if(jV!=null) return (JsonArray)jV; 
      else return null;
   }

   public JsonNumber getJsonNumber(String name) {
      JsonValue jV= get(name);
      if(jV!=null) return (JsonNumber)jV; 
      else return null;
   }
   public JsonObject getJsonObject(String name) {
//D tw.fc.Std.cout.pn(this);
//D System.out.println("getJsonObject(\""+name+"\")");
      JsonValue jV= get(name);
      if(jV!=null) return (JsonObject)jV; 
      else return null;
   }

   public JsonString getJsonString(String name) {
      JsonValue jV= get(name);
      if(jV!=null) return (JsonString)jV; 
      else return null;
   }

   public String getString(String name) {
      JsonString jS= getJsonString(name);
      if(jS!=null) return jS.getString();
      else throw new NullPointerException();
   }

   public String getString(String name, String defaultValue) {
      JsonString jS= getJsonString(name);
      if(jS!=null) return jS.getString();
      else return defaultValue;
   }
   
   public boolean isNull(String name) {
      JsonValue jV= get(name);
      if(jV!=null) return (jV instanceof JsonNull);
      else throw new NullPointerException();
   }

  //--------------------------------------
  // methods modified from JsonObjectBuilder

   public JsonObject add(String name, JsonValue val) {
      this.list.add(new JsonPair(name, val));
      return this;
   }
   public JsonObject add(String name, String val) {
      return this.add(name, new JsonString(val));
   }

  


  //============================================
   static void test1() {
      cout.pn(" --- test1 ---");
      JsonObject o1= new JsonObject();
      o1.add(new JsonPair("key1", "value1"));
      o1.add(new JsonPair("key2", "value2"));
      o1.add(new JsonPair("key3", "value3"));
      cout.pn(o1);
   }
   static void test2() {
      cout.pn(" --- test2 ---");
      JsonObject a1=new JsonObject();
      a1.add(new JsonPair("key1", "value1"))
        .add(new JsonPair("key2", "value2"))
        .add(new JsonPair("key3", "value3"));
      cout.printf("a1=\n%z\n", a1);
      TxOStrStream oS= new TxOStrStream();  oS.p(a1);
      TxIStrStream iS= new TxIStrStream(oS.toString());
      JsonObject a2=new JsonObject();
      iS.scanf("%z", a2);
      cout.printf("a2=%z\n", a2);
   }
   static void test3() {
      cout.pn(" --- test3 ---");
      JsonObject a2=new JsonObject();
      cin.scanf("%z", a2);
      cout.printf("a2=\n%z\n", a2);
   }
   public static void main(String[] dummy) {
      //test1();
      //test2();
      test3();
      System.out.println("OK");
   } 


}
/*  
C:\Users\ht\Desktop\TwFC\TwFC_working>java tw.fc.JsonObject
 --- test3 ---
{
   "menu":
   {
      "id": "file",
      "value": "File",
      "popup":
      {
         "menuitem":
         [
           {"value": "New", "onclick": "CreateNewDoc()"},
           {"value": "Open", "onclick": "OpenDoc()"},
           {"value": "Close", "onclick": "CloseDoc()"}
         ]
      }
   }
}
a2=
{
   "menu": {
      "id": "file",
      "value": "File",
      "popup": {
         "menuitem": [
            {
               "value": "New",
               "onclick": "CreateNewDoc()"
            },
            {
               "value": "Open",
               "onclick": "OpenDoc()"
            },
            {
               "value": "Close",
               "onclick": "CloseDoc()"
            }
         ]
      }
   }
}
*/
/*  Example in https://tools.ietf.org/html/rfc7159
C:\Users\ht\Desktop\TwFC\TwFC_working>java tw.fc.JsonObject
 --- test3 ---
{
   "Image": {
      "Width":  800,
      "Height": 600,
      "Title":  "View from 15th Floor",
      "Thumbnail": {
         "Url":    "http://www.example.com/image/481989943",
         "Height": 125,
         "Width":  100
      },
      "Animated" : false,
      "IDs": [116, 943, 234, 38793]
   }
}
a2=
{
   "Image": {
      "Width": 800.0,
      "Height": 600.0,
      "Title": "View from 15th Floor",
      "Thumbnail": {
         "Url": "http://www.example.com/image/481989943",
         "Height": 125.0,
         "Width": 100.0
      },
      "Animated": false,
      "IDs": [
         116.0,
         943.0,
         234.0,
         38793.0
      ]
   }
}
*/