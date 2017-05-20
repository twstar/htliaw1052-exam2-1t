package tw.fc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TxONetStream extends TxOStream {
   private final String lineSeparator= System.getProperty("line.separator");

   private BufferedWriter bWrite;

//I  boolean _startOfLine= true;

  //---------------------------------------------------------

   //[ should exclusively called in TxSocket.out() 
   //[ temporarily called in tw.fc.DialogFactory
   //public  //: b4 A5.014.G
   TxONetStream(Socket socket) throws IOException {
      super();
      bWrite= new BufferedWriter(
         new OutputStreamWriter(socket.getOutputStream())
      );
   }

 // public TxONetStream(String host, int port) 
 // throws UnknownHostException, IOException
 // {
 //    this(new Socket(host, port));
 // } //: b4 A5.014.G

  //---------------------------------------------------------
   @Override
   public TxONetStream flush() throws IOException {
      bWrite.flush();   return this;
   }

   @Override
   public void close() throws IOException {
      bWrite.close();
   }

//I  @Override public boolean startOfLine()

   @Override
   public TxONetStream p(String s) throws IOException {
      bWrite.write(s);  
      if(s.length()>0) this._startOfLine=(s.charAt(s.length()-1)=='\n');   //: A5.023L
      return this;
   }

   @Override
   public TxONetStream pn() throws IOException {
      this.p(lineSeparator);  
         //: NOT send on TxONetStream until flush()  
      this._startOfLine=true;
      return this.flush();  
   }

//I public TxOStream printf(String fStr, Object... args) throws IOException { 


}
