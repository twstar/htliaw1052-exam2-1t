package tw.fc;
import java.io.IOException;

//[ attributes used in ScanfSpec and PrintfSpec
// the aux data between % and conversion
// %[flags][minWidth][.precision]conversion
// ******************************************************
// class SpecAux  {   //: b4 A%.032L
class SpecAttrib implements ScannableI {
   final String legalFlags; // = "-+ 0#|"; 
       //:  '|' for indent in TwTx
       //:  "-+ 0#" in printf of C
       //:  "*" in scanf of C
       //:  "-+ 0#,(" in java

   private String flags;  
   //int width, precision;   //: b4 A5.032.A 
   int minWidth=0, precision=0;   

   //SpecAttrib() {   }
   SpecAttrib(String legalFs) {  this.legalFlags=legalFs;  }

   // void scanFrom(TxIStream iS) throws IOException {
   @Override public void scanFrom(TxIStream iS) throws IOException {
      char nextCh= (char)iS.peek();
   // not support [argument_index$] since it is meaningless on scanf 

    //[  parse optional flags
      StringBuilder readingFlags= new StringBuilder();  
      while(legalFlags.indexOf(nextCh)>=0) {
         readingFlags.append(nextCh);
         //iS.skip(1);   //: b4 A5.029
         iS.expect(nextCh);  //: safer than skip(1) 
         nextCh= (char)iS.peek();
      }
      flags= readingFlags.toString();

    //[  parse optional minWidth
      if('0'<=nextCh && nextCh<='9') {
         this.minWidth= iS.get_int();
     //D System.out.println("minWidth:"+this.minWidth);
         //nextCh= (char)iS.read();  //: bug b4 A5.
         nextCh= (char)iS.peek();  //: no skipWS
      }
    //[  parse optional  .precision 
      if(nextCh=='.') {
         //iS.skip(1); //: b4 A5.029
         iS.expect('.');  //: safer than skip(1)  
         this.precision= iS.get_int();
      }
  //D System.out.println("{"+flags+","+minWidth+","+precision+"}");
   }

   final boolean hasFlag(char fL) {
      return ( flags!=null && flags.indexOf(fL)>=0 );
   }   

   //[ positive iff legal
   final int getMinWidth() {   return ( this.minWidth );  }   

   //[ positive iff legal
   final int getPrecision() {   return ( this.precision );  }   


}
