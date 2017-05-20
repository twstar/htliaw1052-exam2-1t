package tw.fc ;
import java.io.IOException;
import java.net.UnknownHostException; //: extends java.io.IOException
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketImplFactory;
import java.net.SocketImpl;
import java.net.Proxy;
import java.util.ArrayList;

// public class TxSocket extends Socket {  
      //: 不行: TxServerSocket::accept() 傳回 Socket 後不能包成 TxSocket
public class TxSocket {

   private Socket _socket;
   private TxONetStream _out=null; //: lazy initialization by out()
   private TxINetStream _in=null;  //: lazy initialization by in()

   ////private ArrayList<TxONetStream> allTxONetStream= new ArrayList<TxONetStream>();
   ////private ArrayList<TxINetStream> allTxINetStream= new ArrayList<TxINetStream>();
   //private ArrayList<StoppableI> allRunnable= new ArrayList<StoppableI>();

  //-----------------------------------------------------------

   //[ used by TxServerSocket::accept()
   TxSocket(Socket sk) throws IOException {
      this._socket= sk;
      //this._out= new TxONetStream(sk); //: lazy initialization
      //this._in= new TxINetStream(sk);  //: lazy initialization
   } 

   public TxSocket() {
      this._socket= new Socket(); 
   }

   public TxSocket(InetAddress address, int port) throws IOException {
      this._socket= new Socket(address, port);
   }

   public TxSocket(
      InetAddress address,  int port,
      InetAddress localAddr, int localPort
   ) throws IOException 
   {
      this._socket= new Socket(address, port, localAddr, localPort);
   }

   public TxSocket(Proxy proxy) {
      this._socket= new Socket(proxy);
   }

   //protected TxSocket(SocketImpl impl) throws SocketException {
   //   this._socket= new Socket(impl);  //:  no suitable constructor found
   //}

   public TxSocket(String ip, int port) throws UnknownHostException, IOException
   {
      this._socket= new Socket(ip, port);
      // this._out= new TxONetStream(this._socket);  //: lazy initialization
      // this._in= new TxINetStream(this._socket);   //: lazy initialization
   }
   
   public TxSocket(
      String host, int port,
      InetAddress localAddr, int localPort
   ) throws IOException
   {
      this._socket= new Socket(host, port, localAddr, localPort);
   }

 //-----------------------------------------------------------
   //public void logRunnable(StoppableI r) {
   //   allRunnable.add(r);
   //} 

   //public void StoppingRelatedRunnable() {
   //   for(StoppableI s: allRunnable) {
   //      s.setStopFlag(); 
   //   }
   //}

 //-----------------------------------------------------------
 //[ alphabetic order in JDK API
   public void bind(SocketAddress bindpoint) throws IOException {
      this._socket.bind(bindpoint);
   }
   public void close() throws IOException {
      //this.StoppingRelatedRunnable();   // !!!
      this._socket.close();
   }
   public void connect(SocketAddress endpoint) throws IOException {
      this._socket.connect(endpoint);
   }
   public void connect(SocketAddress endpoint, int timeout) throws IOException {
      this._socket.connect(endpoint, timeout);
   }
   public SocketChannel getChannel() {
      return this._socket.getChannel();
   }
   public InetAddress getInetAddress() {
      return this._socket.getInetAddress();
   }

 //public InputStream getInputStream() throws IOException
   public TxINetStream in() throws IOException {
      if(this._in==null) {
         this._in= new TxINetStream(this._socket);
      }
      return this._in;
   }

   public boolean getKeepAlive() throws SocketException {
      return this._socket.getKeepAlive();
   }
   public InetAddress getLocalAddress() {
      return this._socket.getLocalAddress();
   }
   public int getLocalPort() {
      return this._socket.getLocalPort();
   }
   public SocketAddress getLocalSocketAddress() {
      return this._socket.getLocalSocketAddress();
   }
   public boolean getOOBInline() throws SocketException {
      return this._socket.getOOBInline();
   }
// public OutputStream getOutputStream() throws IOException
   public TxONetStream out() throws IOException {
      if(this._out==null) {
         this._out= new TxONetStream(this._socket);
      }
      return this._out;
   }

   public int getPort() {
      return this._socket.getPort();
   }
   public int getReceiveBufferSize() throws SocketException {
      return this._socket.getReceiveBufferSize();
   }
   public SocketAddress getRemoteSocketAddress() {
      return this._socket.getRemoteSocketAddress();
   }
   public boolean getReuseAddress() throws SocketException {
      return this._socket.getReuseAddress();
   }
   public int getSendBufferSize() throws SocketException {
      return this._socket.getSendBufferSize();
   }
   public int getSoLinger() throws SocketException {
      return this._socket.getSoLinger();
   }
   public int getSoTimeout() throws SocketException {
      return this._socket.getSoTimeout();
   }
   public boolean getTcpNoDelay() throws SocketException {
      return this._socket.getTcpNoDelay();
   }
   public int getTrafficClass() throws SocketException {
      return this._socket.getTrafficClass();
   }
   public boolean isBound() {
      return this._socket.isBound();
   }
   public boolean isClosed() {
      return this._socket.isClosed();
   }
   public boolean isConnected() {
      return this._socket.isConnected();
   }
   public boolean isInputShutdown() {
      return this._socket.isInputShutdown();
   }
   public boolean isOutputShutdown() {
      return this._socket.isOutputShutdown();
   }
   public void sendUrgentData(int data) throws IOException {
      this._socket.sendUrgentData(data);
   }
   public void setKeepAlive(boolean on) throws SocketException {
      this._socket.setKeepAlive(on);
   }
   public void setOOBInline(boolean on) throws SocketException {
      this._socket.setOOBInline(on);
   }
   public void setPerformancePreferences(
      int connectionTime, int latency, int bandwidth
   ) {
      this._socket.setPerformancePreferences(connectionTime, latency, bandwidth);
   }
   public void setReceiveBufferSize(int size) throws SocketException {
      this._socket.setReceiveBufferSize(size);
   }
   public void setReuseAddress(boolean on) throws SocketException {
      this._socket.setReuseAddress(on);
   }
   public void setSendBufferSize(int size) throws SocketException {
      this._socket.setSendBufferSize(size);
   }
   public static void setSocketImplFactory(SocketImplFactory fac) throws IOException {
      Socket.setSocketImplFactory(fac);
   }
   public void setSoLinger(boolean on, int linger) throws SocketException {
      this._socket.setSoLinger(on, linger);
   }
   public void setSoTimeout(int timeout) throws SocketException {
      this._socket.setSoTimeout(timeout);
   }
   public void setTcpNoDelay(boolean on) throws SocketException {
      this._socket.setTcpNoDelay(on);
   }
   public void setTrafficClass(int tc) throws SocketException {
      this._socket.setTrafficClass(tc);
   }
   public void shutdownInput() throws IOException {
      this._socket.shutdownInput();
   }
   public void shutdownOutput() throws IOException {
      this._socket.shutdownOutput();
   }
   @Override public String toString() {
      return "TxSocket( "+this._socket.toString()+" )";
   }

  //--------------------------------------------

}
