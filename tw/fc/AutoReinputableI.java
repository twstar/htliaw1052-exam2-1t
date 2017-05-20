package tw.fc ;

//[ A5.034L add
//[ so that client code can test a TxIStream 
public interface AutoReinputableI {
   public void setAutoReInput(boolean b);
   public boolean getAutoReInput();
}