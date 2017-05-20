package tw.fc.gui;

//===================================
//
//  ���ѰʤO, �A�Ω���������thread
//
public interface GameEngineI {
   public void gameCycle(int clockCount);   
   public void setClock(int sleepMs, boolean showClock) ;
   public void startPulse(int sleepMs) ; 
   public void stopPulse(int sleepMs) ; 
}
