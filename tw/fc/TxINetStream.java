package tw.fc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class TxINetStream extends TxIStream {

//I protected Reader iii=null;

  //---------------------------------------------------------

   //[ should exclusively called in TxSocket.in() 
   // public  //: b4 A5.014.G
   TxINetStream(Socket socket) throws IOException {
      super();
      super.iii= 
        new BufferedReader(
           new InputStreamReader(socket.getInputStream())
        );  
   }

// public TxINetStream(String host, int port) 
// throws UnknownHostException, IOException {
//    this(new Socket(host, port));
// } //: b4 A5.014.G

// public TxINetStream(URL url) throws IOException {
//    super();
//    super.iii= 
//       new BufferedReader(
//          new InputStreamReader(url.openConnection().getInputStream())
//       );
// } //: b4 A5.014.G


  //---------------------------------------------------------
   public void close() throws IOException {
      super.iii.close();
   }
}
