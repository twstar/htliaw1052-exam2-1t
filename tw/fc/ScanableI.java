package tw.fc;
import java.io.IOException;
import tw.fc.TxIStream;

// *******************************************************
@Deprecated  //: use ScannableI instead
public  interface  ScanableI {

   public void scanFrom(TxIStream iS) throws IOException ;

}
