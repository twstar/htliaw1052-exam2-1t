package tw.fc;
import java.io.IOException;
//import tw.fc.TxIStream;

//**********   PrintableI.java     ********************//
//
//   fundamental interface for client classes
//
//------------
public  interface  PrintableI {

   //:  To enable the simulation of operator<<() in C++.
   //:
   //:  Ex.    
   //:       byte b=(byte)127;
   //:       int x=20; 
   //:       MuRtn r=new MuRtn(1,7);
   //:       ImuXY=new ImuXY(2,3);
   //:       cout.pc(b).pc(x).pc(r).pn(v); 

   //:       ��jdk��final class, �i��toString��X, �Ҧp: 
   //:       cout.p("red=").pn(Color.RED.toString());
   //:     

   //:       TxOStream ooo=new TxOStream("filename");  
   //:       ooo.p(b).p(x).p(v); 
   //:
   //:  How to implement the method print?  --> see the class ImuXY
   //:

   public void printTo(TxOStream oS) throws IOException ;

// public void widthPrintTo(int w, TxOStream ooo);
     //: ��m��WidthPrintableI�� wp, wpc ���ϥ�.

}
