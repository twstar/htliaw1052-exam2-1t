package tw.fc ;

// ***********  EOSException.java   ***************
//
//  exception for end-of-string when reading TxStrStream
//
public class EOSException extends RuntimeException {
   public EOSException() {  super(); }
   public EOSException(String s) {  super(s); }
   private static final long serialVersionUID= 2006012021L;
}