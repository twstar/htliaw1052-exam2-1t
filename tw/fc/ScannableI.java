package tw.fc;
import java.io.IOException;
import tw.fc.TxIStream;

//[
//[   fundamental input interface for client classes
//[
// ******************************************************
//public  interface  ParsableI {   //: b4 A5.035L.O
public  interface  ScannableI {

   //[  To enable the simulation of operator>>() in C++.
   //[
   //[  Ex.    
   //[       final intRef x=new intRef(); 
   //[       final MuV2D v=new MuV2D();
   //[       cin.g(x).g(v); 
   //[       cin.scanf("%d%z", x, v);
   //[
   //[       TxIFStream f_in=new TxIFStream("filename");  
   //[       f_in.g(x).g(v);             
   //[       f_in.scanf("%d%z", x, v);
   //[
   //[  How to implement the method sparseFrom?  --> see the class tw.fc.MuRtn
   //[

   //public void parseFrom(TxIStream iS) throws IOException ;  //: b4 A5.035L.O
   public void scanFrom(TxIStream iS) throws IOException ;
      //:  cascate是在g時傳回stream. 此函數供callback用, 回傳函數值無益處.

}
