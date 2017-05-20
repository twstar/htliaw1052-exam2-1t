package tw.fc ;

/*
  JDKAPI SE7

 	@Deprecated stop()
  This method is inherently unsafe. 
  Stopping a thread with Thread.stop causes it to
  unlock all of the monitors that it has locked 
  (as a natural consequence of the unchecked ThreadDeath exception 
   propagating up the stack). 
  If any of the objects previously protected by these monitors 
  were in an inconsistent state, the damaged objects 
  become visible to other threads, 
  potentially resulting in arbitrary behavior. 
  Many uses of stop should be replaced by code that 
  simply modifies some variable to indicate that 
  the target thread should stop running. 
  The target thread should check this variable regularly, 
  and return from its run method in an orderly fashion 
  if the variable indicates that it is to stop running. 
  If the target thread waits for long periods 
  (on a condition variable, for example), 
  the interrupt method should be used to interrupt the wait. 

  For more information, see Why are Thread.stop, 
  Thread.suspend and Thread.resume Deprecated?.
*/

/*
  H.T.Liaw's Note A50212
  1. It is an issue on Thread, Not on Runnable.
     A piece of code can be called by main() as well as by a Runnable.
  2. Not an issue on Stream
*/

//[ A5.015 
// ********************************************
public class StopFlagThread extends Thread {
   private boolean _stopFlag=false; 

   public boolean getStopFlag() {  return this._stopFlag; }

   public boolean setStopFlag(boolean f) {  
      boolean oldF= this._stopFlag;
      this._stopFlag= f; 
      return oldF; 
   }

 //---------------------------------------------------

   public StopFlagThread() {  super(); }
   public StopFlagThread(Runnable target) {  super(target);  } 
   public StopFlagThread(Runnable target, String name) {
      super(target, name);
   }
   public StopFlagThread(String name) {  super(name); }
   public StopFlagThread(ThreadGroup group, Runnable target) {  
      super(group, target); 
   }
   public StopFlagThread(ThreadGroup group, Runnable target, String name) {
      super(group, target, name);
   }
   public StopFlagThread(
      ThreadGroup group, Runnable target, String name, long stackSize
   ) {
      super(group, target, name, stackSize);
   }
   public StopFlagThread(ThreadGroup group, String name) {
      super(group, name);
   }

}