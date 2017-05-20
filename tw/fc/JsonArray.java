package tw.fc;
import java.util.ArrayList;
import static tw.fc.Std.*;

public class JsonArray extends JsonValue 
   implements IndentPrintableI, ScannableI 
{
   ArrayList<JsonValue> list= new ArrayList<JsonValue>();

   public JsonArray() {  }
   
   JsonArray add(JsonValue val) {  this.list.add(val);  return this; }

   //@Override public void printTo(TxOStream oS, String indent) 
   @Override public void indentPrintTo(TxOStream oS, String indent) 
   throws java.io.IOException {
      //oS.printf("%s[\n", indent);  //: b4 A5.023L
      oS.printf("%|s\n", indent, "[");
      //D final String indent1=indent+"...";
      final String indent1=indent+"   ";
      final int len= list.size();
      for(int i=0; i<len; i++) {
         String optComma= (i<len-1)? ",": "";
         // PrintableI elm= list.get(i);
         // //if(elm instanceof IndentPrintableI) {
         // //   ((IndentPrintableI)elm).printTo(oS, indent1); 
         // //}
         // //else {
         // //   oS.p(indent1); elm.printTo(oS); 
         // //}
         // oS.p(indent1, elm); 
         // oS.pn(optComma);
         oS.printf("%|z%s\n", indent1, list.get(i), optComma);
      }
      oS.printf("%|s", indent, "]");
   } 
   @Override public void printTo(TxOStream oS) 
   throws java.io.IOException {  
      // this.indentPrintTo(oS, "");  
      oS.p("[ ");
      final int len= list.size();
      for(int i=0; i<len; i++) {
         String optComma= (i<len-1)? ",": "";
         oS.printf("%z%s ", list.get(i), optComma);
      }
      oS.p("]");
   }

 //[ JsnArray  ::=  '[' elements? ']'  ;            
 //[ elements  ::=  JsnValue ( ',' JsnValue )*  ;   
   @Override public void scanFrom(TxIStream iS) 
   throws java.io.IOException {  
      // //iS.scanf(" [");  //: b4 A5.028L.K
      // iS.scanf("% [");
      iS.scanf("%w[");
      // //if( iS.skipWS().probe(']') ) {  }
      // if( iS.probe("%w]") ) {  }   //: b4 A5.038L.C
      if( iS.probef("%w]") ) {  }
      else {
         final JsonValueRef valueR= new JsonValueRef();
         while(true) {
            // iS.skipWS();  this.add(JsonValue.getInstanceFrom(iS));
            //iS.scanf(" %z", valueR);
            iS.scanf("%z", valueR);
            this.add(valueR.v); 
            // //if(! iS.skipWS().probe(',') ) break;
            // if(! iS.probe("%w,") ) break;  //: b4 A5.038L.C
            if(! iS.probef("%w,") ) break;
            //iS.expect(',');
            iS.scanf("%w,");
         }
      } 
      // //iS.scanf(" ]");  //: b4 A5.028L.K
      // iS.scanf("% ]");
      iS.scanf("%w]");
   }

  //----------------------------------------

   protected JsonValue get(int index) {
      return list.get(index);
   } 

  //[ alphbetic order as in JavaEE API

   public boolean getBoolean(int index) {
      JsonValue jV= get(index);
      return ((JsonBool)jV).v; 
   }

   public boolean getBoolean(int index, boolean defaultValue) {
      JsonValue jV= get(index);
      if(jV instanceof JsonBool) return ((JsonBool)jV).v; 
      else return defaultValue;
   }

   public int getInt(int index) {
      return getJsonNumber(index).intValue();
   }
   public int getInt(int index, int defaultValue) {
      JsonValue jV= get(index);
      if(jV instanceof JsonNumber) return ((JsonNumber)jV).intValue(); 
      else return defaultValue; 
   }

   public JsonArray getJsonArray(int index) {
      JsonValue jV= get(index);
      return (JsonArray)jV; 
   }

   public JsonNumber getJsonNumber(int index) {
      JsonValue jV= get(index);
      return (JsonNumber)jV; 
   }

   public JsonObject getJsonObject(int index) {
      JsonValue jV= get(index);
      return (JsonObject)jV; 
   }

   public JsonString getJsonString(int index) {
      JsonValue jV= get(index);
      return (JsonString)jV; 
   }

   public String getString(int index) {
      JsonString jV= getJsonString(index);
      return jV.v; 
   }

   public String getString(int index, String defaultValue) {
      JsonValue jV= get(index);
      if(jV instanceof JsonString) return ((JsonString)jV).getString();
      else return defaultValue;
   }

   public int size() { 
      return this.list.size();
   }

// ¤£©ú
// <T extends JsonValue> List<T> getValuesAs(Class<T> clazz)

   public boolean isNull(int index) {
      JsonValue jV= get(index);
      return (jV instanceof JsonNull);
   }

  //----------------------------
  // methods modified from JsonArrayBuilder

   public JsonArray add(JsonObject ob) {
      this.list.add(ob);
      return this;
   }


  //============================================
   static void test1() {
      cout.pn(" --- test1 ---");
      JsonArray a1=new JsonArray();
      cout.pn(a1);
      a1.add(new JsonString("aaa")); 
      cout.pn(a1);
      a1.add(new JsonString("bbb")); 
      cout.pn(a1);
      a1.add(new JsonString("ccc")); 
      cout.pn(a1);
   }
   static void test2() {
      cout.pn(" --- test2 ---");
      JsonArray a1=new JsonArray();
      a1.add(new JsonString("aaa")).add(new JsonString("bbb")).add(new JsonString("ccc"));
      cout.printf("a1=\n%z\n", a1);
      TxOStrStream oS= new TxOStrStream();  oS.p(a1);
      TxIStrStream iS= new TxIStrStream(oS.toString());
      JsonArray a2=new JsonArray();
      iS.scanf("%z", a2);
      cout.printf("a2=%z\n", a2);
   }
   static void test3() {
      cout.pn(" --- test3 ---");
      JsonArray a2=new JsonArray();
      cin.scanf("%z", a2);
      cout.printf("a2=\n%z\n", a2);
   }
   public static void main(String[] dummy) {
      //test1();
      //test2();
      test3();
   } 
}
/*
C:\Users\ht\Desktop\TwFC\TwFC_working>java tw.fc.JsonArray
 --- test3 ---
[
           {"value": "New", "onclick": "CreateNewDoc()"},
           {"value": "Open", "onclick": "OpenDoc()"},
           {"value": "Close", "onclick": "CloseDoc()"}
]
a2=
[
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
*/
/*  Example in https://tools.ietf.org/html/rfc7159
C:\Users\ht\Desktop\TwFC\TwFC_working>java tw.fc.JsonArray
 --- test3 ---
[
        {
           "precision": "zip",
           "Latitude":  37.7668,
           "Longitude": -122.3959,
           "Address":   "",
           "City":      "SAN FRANCISCO",
           "State":     "CA",
           "Zip":       "94107",
           "Country":   "US"
        },
        {
           "precision": "zip",
           "Latitude":  37.371991,
           "Longitude": -122.026020,
           "Address":   "",
           "City":      "SUNNYVALE",
           "State":     "CA",
           "Zip":       "94085",
           "Country":   "US"
        }
 ]
a2=
[
   {
      "precision": "zip",
      "Latitude": 37.7668,
      "Longitude": -122.3959,
      "Address": "",
      "City": "SAN FRANCISCO",
      "State": "CA",
      "Zip": "94107",
      "Country": "US"
   },
   {
      "precision": "zip",
      "Latitude": 37.371991,
      "Longitude": -122.02602,
      "Address": "",
      "City": "SUNNYVALE",
      "State": "CA",
      "Zip": "94085",
      "Country": "US"
   }
]
*/
