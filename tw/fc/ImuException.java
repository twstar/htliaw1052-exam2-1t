package tw.fc ;

//*************   ImuException   ************************//

public class ImuException extends RuntimeException {

   private static final long serialVersionUID= 2006021220L;

   public        ImuException() {
      super("Try to alter immutable object");
   }
}
