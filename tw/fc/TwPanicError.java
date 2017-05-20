package tw.fc ;

// ***********  TwPanicError.java   ***************
//
//  exception for implementation error in tw.fc package
//

public class TwPanicError extends Error {
   static final String sorry=
      "\n\n**** Very sorry! **** \n" +
      "Please report the related information" +
      " to the designner of the library tw.fc.\n\n";

   public TwPanicError() {
      super(sorry);
   }

   public TwPanicError(String msg) {
      super(msg + sorry);
   }

   private static final long serialVersionUID= 2005111615L;

}