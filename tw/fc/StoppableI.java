package tw.fc;

// A5.014.J
@Deprecated //: on A5.026L
public interface StoppableI extends Runnable {
//I void run()
   public void setStopFlag();
}