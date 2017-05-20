package tw.fc;
import java.io.IOException;
//import tw.fc.TxIStream;

//
//  indent printable
//
//******************************//
public interface IndentPrintableI extends PrintableI {

   //public void printTo(TxOStream oS, String indent) throws IOException ;
   public void indentPrintTo(TxOStream oS, String indent) throws IOException ;


}
