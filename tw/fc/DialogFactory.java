package tw.fc ;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import tw.fc.Std;
import static tw.fc.Std.cin;
import static tw.fc.Std.cout;
import static tw.fc.Std.cerr;
import tw.fc.StringRef;

import tw.fc.TxINetStream;
import tw.fc.TxONetStream;

//[ 此為實驗產品, 只供 demoTx.N_Server0 及 demoTx.N_Client0 使用
//[ console keyin 完成前會擋住 socket input
// *************************************************************** 
public class DialogFactory {

  //[=============================================================
   static class ServerJob implements Runnable {
       
      //private Object _server;   // >>> TODO _server's type
      private Socket socketToClient;
      
      //public ServerJob(Object svr, Socket socketToClient) {
      public ServerJob(Socket socketToClient) {
         //this._server=svr;   
         this.socketToClient= socketToClient;
      }
      
      void dialog() {
         TxINetStream n_in= null;
         TxONetStream n_out= null;
         try {
            n_out= new TxONetStream(this.socketToClient);
            n_in= new TxINetStream(this.socketToClient);
            final SocketAddress ip= this.socketToClient.getRemoteSocketAddress();
            //n_out.p('(').p("Hello!").pn(')');  // pn() contains flush();  
            n_out.printf("(%s) %s\n", "Hello!", ip.toString()).flush();  //: skipWS on next input
            //----------------------------
            // cerr.p("wait for keyboard...\n");
            final StringRef frKbd= new StringRef();
            for(int i=1; i<100; i++) {
               while(n_in.ready()) {
                  String line= n_in.getLine();
                  cout.printf("Receive[%s]\n", line);  
               }
               cout.pn().p(i);
               cin.gn("> ", frKbd); 
               cerr.printf("[%s]\n", frKbd);
               n_out.pn(frKbd);
            }
            // while(true) {  } 
            System.err.println("\n END OF DIALOG\n");
         } 
         catch(IOException e) {  e.printStackTrace();  } 
         finally {
            try {
               if(this.socketToClient!= null 
                && !this.socketToClient.isClosed()
               ) {
                  this.socketToClient.close();
               }
               //   if(n_in != null)  n_in.close();
               //   if(n_out != null) {   n_out.close();  }
            } 
            catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
      
      
      @Override public void run() {   this.dialog();   }
      
      
   }
  //]=============================================================




  //[=============================================================
   public static class ClientJob implements Runnable {

      //private Object _client;   // nullable,  >>> TODO _client's type
      private Socket socketToServer;

      //public ClientJob(Object clnt, Socket sktToServer) {
      public ClientJob(Socket sktToServer) {
         //this._client=clnt;   
         this.socketToServer= sktToServer;
      }

      void dialog() {
         TxINetStream n_in= null;
         TxONetStream n_out= null;
         try {
            n_out= new TxONetStream(this.socketToServer);
            n_in= new TxINetStream(this.socketToServer);
            final StringRef frKbd= new StringRef();
            for(int i=0; i<100; i++) {
               while(n_in.ready()) {
                  String line= n_in.getLine();
                  cout.printf("Receive[%s]\n", line);  
               }
//               String line= n_in.getLine();   
//               cout.printf("%d: Receive [%s]\n", i, line);  
               cout.pn().p(i);   cin.gn("> ", frKbd); 
               cerr.printf("[%s]\n", frKbd);
               n_out.pn(frKbd);
            } 
            System.err.println("\n END OF DIALOG\n");
         } 
         catch(java.net.SocketException e) {  
            System.err.println("\n catch SocketException\n");
            e.printStackTrace();  
         } 
         catch(IOException e) {  
            System.err.println("\n catch IOException\n");
            e.printStackTrace();  
         } 
         finally {
            try {
               if(this.socketToServer!= null 
                && !this.socketToServer.isClosed()
               ) {
                  this.socketToServer.close();
               }
            } 
            catch (IOException e) {   
               System.err.println("\n catch IOException in finally\n");
               e.printStackTrace();   
            }
         }

      }

      @Override public void run() {   this.dialog();   }
   }

  //]=============================================================

   //public static Runnable createServer(Object svr, Socket socketToClient) {
   //   return new ServerJob(svr, socketToClient);
   //}
   public static Runnable createServer(Socket socketToClient) {
      return new ServerJob(socketToClient);
   }

   //public static Runnable createClient(Object clnt, Socket socketToServer) {
   //   return new ClientJob(clnt, socketToServer);
   //}
   public static Runnable createClient(Socket socketToServer) {
      return new ClientJob(socketToServer);
   }


}