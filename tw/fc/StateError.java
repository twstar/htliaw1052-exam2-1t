package tw.fc ;

// ***********  StateError.java   ***************
//
//  exception for runtime errors in tw.fc package
//
public class StateError extends RuntimeException {
   public StateError() {  super(); }
   public StateError(String s) {  super(s); }
   private static final long serialVersionUID= 2005111616L;
}