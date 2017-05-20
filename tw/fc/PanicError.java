package tw.fc ;

//***********   PanicError.java    ****************//

public class PanicError extends RuntimeException {

   private static final long serialVersionUID= 2006021217L;

   public PanicError(String msg) {
      super(msg
            + "\nVery sorry! There is a system error in TwFC.\n"
       + "Please mail the current working state and the system version to designer.\n");
   }
   public PanicError() {
      this("\n");
   }

}