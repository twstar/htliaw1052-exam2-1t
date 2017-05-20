package tw.fc;
import java.io.IOException;
//import tw.fc.TxIStream;

//**********   WidthPrintableI.java     ********************//
public  interface  WidthPrintableI extends PrintableI {

   public void widthPrintTo(int w, TxOStream oS) throws IOException ;
      /////// 若不支援, 則轉呼叫printTo(oS)

}
