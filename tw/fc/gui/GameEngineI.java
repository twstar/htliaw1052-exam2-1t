package tw.fc.gui;

//===================================
//
//  提供動力, 適用於離散型的thread
//
public interface GameEngineI {
   public void gameCycle(int clockCount);   
   public void setClock(int sleepMs, boolean showClock) ;
   public void startPulse(int sleepMs) ; 
   public void stopPulse(int sleepMs) ; 
}
