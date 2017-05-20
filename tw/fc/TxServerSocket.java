package tw.fc ;
import java.io.IOException;
import java.net.UnknownHostException; //: extends java.io.IOException
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketAddress;


public class TxServerSocket {

   ServerSocket _svsk;

   public TxServerSocket(int port) throws IOException {
      this._svsk= new ServerSocket(port);
   }
   
   public TxSocket accept() throws IOException {
      Socket sk= this._svsk.accept();
      return new TxSocket(sk);
   }

   public void close() throws IOException {
      this._svsk.close();
   }

   public boolean isClosed() {
      return this._svsk.isClosed();
   }

}