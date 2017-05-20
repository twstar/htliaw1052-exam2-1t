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

// *************************************************************** 
public class SendFactory {

   static final int DislogLimit=100;  // >>>


  //[=============================================================
   //public static class SenderJob implements tw.fc.StoppableI { //: b4 A5.026L
   public static class SenderJob implements Runnable {

      public boolean shouldStop= false;  
          //: advised by by JDKAPI, in stead of deprecated Thread::stop()
       
      //private Object _server;   // >> to modify _server's type
      private TxSocket socketToReceiver;
      
      // //public SenderJob(Object svr, Socket sktToReceiver) {
      // public SenderJob(Socket sktToReceiver) {
      //    //this._server=svr;   
      //    this.socketToReceiver= sktToReceiver;
      // }

      //public  //: should called exclusively by createSender($)
      SenderJob(TxSocket sktToReceiver) {
         //this._server=svr;   
         this.socketToReceiver= sktToReceiver;
         //sktToReceiver.logRunnable(this);
      }

      //public void setStopFlag() {
      //   this.shouldStop= true;
      //   //[ ¨S¥Î
      //   //try {  cin.close();   System.in.close();   }
      //   //catch(IOException xpt) {  throw new Error("Impossible"); }
      //   //]
      //} //: b4 A5.026L
      
      private void send() throws IOException {
         try {
            //n_out= new TxONetStream(this.socketToReceiver);
            TxONetStream n_out= this.socketToReceiver.out();
            //----------------------------
            // cerr.p("wait for keyboard...\n");
            final StringRef frKbd= new StringRef();
            for(int i=0; i<DislogLimit; i++) {
               if(this.shouldStop) return; 
               cout.pn().p(i);
               cin.gn("% ", frKbd);   // >>> How to interrupt when shouldStop
                    //: this is TxICStream::gn(String prompt, StringRef s)
               cerr.printf("[%s]\n", frKbd);
               n_out.pn(frKbd);
            }
            // while(true) {  } 
         } 
         catch(java.net.SocketException e) {
            System.err.println("\n catch SocketException in SenderJob");
            //System.err.println("{");
            //e.printStackTrace();
            //System.err.println("\n}");
         } 
      }
      
      @Override public void run() {  
         try {
            this.send();   
         }
         catch (IOException e) {
            e.printStackTrace();
            System.err.println("\n //: reported by SenderJob::run()"); 
         }
         finally {
            try {
               if(this.socketToReceiver!= null 
                && !this.socketToReceiver.isClosed()
               ) {
                  this.socketToReceiver.close();
                    //: auto call this.socketToReceiver.StoppingRelatedRunnable();
               }
               //   if(n_in != null)  n_in.close();
               //   if(n_out != null) {   n_out.close();  }
               System.err.println("\n END OF SenderJob::run()\n");
            } 
            catch (IOException e) {
               e.printStackTrace();
               System.err.println("\n //: reported by SenderJob::run() in finally"); 
            }
         }
      }
      
   }
  //]=============================================================

   // //public static Runnable createSender(Object svr, Socket socketToReceiver) {
   // //   return new SenderJob(svr, socketToReceiver);
   // //}
   // public static Runnable createSender(Socket socketToReceiver) {
   //    return new SenderJob(socketToReceiver);
   // }
   public static SenderJob createSender(TxSocket socketToReceiver) {
      return new SenderJob(socketToReceiver);
   }


  //[=============================================================
   //public static class ReceiverJob implements tw.fc.StoppableI { //: b4 A5.026L
   public static class ReceiverJob implements Runnable {

      public boolean shouldStop= false;  
          //: advised by by JDKAPI, in stead of deprecated Thread::stop()

      //private Object _client;   // >> to modify _client's type
      private TxSocket socketToSender;

      // //public ReceiverJob(Object clnt, Socket sktToSendef) {
      // public ReceiverJob(Socket sktToSendef) {
      //    //this._client=clnt;   
      //    this.socketToSender= sktToSendef;
      // }
      //public  //: should called exclusively by createReceiver($)
      ReceiverJob(TxSocket sktToSendef) {
         //this._client=clnt;   
         this.socketToSender= sktToSendef;
         //sktToSendef.logRunnable(this);
      }

   // public void setStopFlag() {   this.shouldStop= true;   }  //: b4 A5.026L

      private void receive() throws IOException {
         try {
            //n_in= new TxINetStream(this.socketToSender);
            TxINetStream n_in= this.socketToSender.in();
            for(int i=0; i<DislogLimit; i++) {
               if(this.shouldStop) return;
               String line= n_in.getLine();   // <<< How to interrupt if shouldStop?
               cout.printf("Receive[%s]\n", line);  
            } 
         } 
         catch(java.net.SocketException e) {
            System.err.println("\ncatch SocketException in ReceiverJob");
            System.err.println("isInputShutdown: "+this.socketToSender.isInputShutdown());
            System.err.println("isOutputShutdown: "+this.socketToSender.isOutputShutdown());          
            System.err.println("isConnected: "+this.socketToSender.isConnected()); 
            System.err.println("isClosed: "+this.socketToSender.isClosed()); 
                 // isInputShutdown: false
                 // isOutputShutdown: false
                 // isConnected: true
                 // isClosed: false
            if(!this.socketToSender.isClosed()) {
               this.socketToSender.close();
               //: auto call this.socketToSender.StoppingRelatedRunnable(); 
            }
            System.err.println("after closed... ");
            System.err.println("isInputShutdown: "+this.socketToSender.isInputShutdown());
            System.err.println("isOutputShutdown: "+this.socketToSender.isOutputShutdown());          
            System.err.println("isConnected: "+this.socketToSender.isConnected()); 
            System.err.println("isClosed: "+this.socketToSender.isClosed()); 
                 // isInputShutdown: false
                 // isOutputShutdown: false
                 // isConnected: true
                 // isClosed: true

            //System.err.println("{");
            //e.printStackTrace();
            //System.err.println("\n}");
         } 
      }

      // @Override public void run() throws IOException {   this.receive();   }
                //: Error: overridden method does not throw IOException 
      @Override public void run() {   
         try {
            this.receive();   
         }
         catch(IOException e) {  
            e.printStackTrace();  
            System.err.println("\n //: reported by ReceiverJob::run()"); 
         } 
         finally {
            try {
               if(this.socketToSender!= null 
                && !this.socketToSender.isClosed()
               ) {
                  this.socketToSender.close();
               }
            } 
            catch(IOException e) {  
               e.printStackTrace();  
               System.err.println("\n //: reported by ReceiverJob::run() in finally"); 
            } 
            System.err.println("\n ... END OF ReceiverJob::run()"); 
         }
      }

   }

  //]=============================================================


   // //public static Runnable createReceiver(Object clnt, Socket socketToSender) {
   // //   return new ReceiverJob(clnt, socketToSender);
   // //}
   // public static Runnable createReceiver(Socket socketToSender) {
   //    return new ReceiverJob(socketToSender);
   // }
   public static ReceiverJob createReceiver(TxSocket socketToSender) {
      return new ReceiverJob(socketToSender);
   }


}