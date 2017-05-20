import java.io.IOException;
import tw.fc.*;

public class DW implements PrintableI, ScannableI{
    
    int d;
    
    DW(){}
    
    DW(int day){
        this.d = day;
    }
    
    @Override public void printTo(TxOStream oS) throws IOException{
        oS.p("[").p(this.d).p("]");
    }
    
    @Override public void scanFrom(TxIStream iS) throws IOException{
        iS.skipWS().expect("[").skipWS();
        this.d = iS.get_int();
        iS.skipWS().expect("]");
    }
}
