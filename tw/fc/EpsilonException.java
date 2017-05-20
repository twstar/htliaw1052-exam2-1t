package tw.fc;

//[ A30108X
//public class EpsilonException extends Exception { //: force try­catch
public class EpsilonException extends RuntimeException { 
   private static final long serialVersionUID = 2014120805L;

   public double epsilon;
   public EpsilonException(String msg){
      super(msg);
   }   
   
   public EpsilonException(double ep, String msg){
      this(msg);
      this.epsilon=ep;
   }
}