package tw.fc.gui;
   import java.awt.Frame;
   import javax.swing.JDialog;
   import java.awt.event.InputEvent;
   import java.awt.event.MouseEvent;
   import javax.swing.event.MouseInputListener;
   import java.awt.Cursor;

//>>>> 待裝一個JSpinner, 以改善外觀. 並能調整 delay


//[ =========   class Updater      ======================

public class TwGameClock extends JDialog
      implements MouseInputListener
{

   private static final long serialVersionUID= 2005111610L;

   protected javax.swing.Timer timer;
   //: in jdkDoc of Timer ---
   //  In v 1.3, another Timer class was added to the Java platform:
   //  java.util.Timer. Both it and javax.swing.Timer provide the
   //  same basic functionality, but java.util.Timer is more general
   //  and has more features.
   //  The javax.swing.Timer has two features that can make it a
   //  little easier to use with GUIs.
   //  First, its event handling metaphor is familiar to GUI programmers
   //  and can make dealing with the event-dispatching thread a bit
   //  simpler.
   //  Second, its automatic thread sharing means that you don't have
   //  to take special steps to avoid spawning too many threads.
   //  Instead, your timer uses the same thread used to make cursors
   //  blink, tool tips appear, and so on.

   protected GameEngineI pane;
   protected int clockCount=0;
   protected boolean clockRunning=false;

   @Override public void paint(java.awt.Graphics g) {
      super.paint(g);
      g.setColor(java.awt.Color.BLACK);
      g.drawString("Current delay is "+getDelay()+"ms.",10,50);
      g.drawString("Click right button to stop/start the clock.",10,70);
      g.drawString("When stopped -- ",10,90);
      g.drawString("Click left button to triggle a tick.",10,110);
      g.drawString("Drag left button to triggle many ticks.",10,130);
   }

   //------------------------------------
   public TwGameClock(int ms, GameEngineI p) {
      super();
      timer=new javax.swing.Timer(
         ms,
         new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
               pane.gameCycle(++clockCount);
            }
         }
      );
      pane=p;
      setBounds(300,400,300,150);
      setTitle("Clock Controller");
      this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }
   public TwGameClock(Frame owner, int ms, GameEngineI p) {
      super(owner);
      timer=new javax.swing.Timer(
         ms,
         new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
               pane.gameCycle(++clockCount);
            }
         }
      );
      pane=p;
      setBounds(300,400,300,150);
      setTitle("Clock Controller");
      this.setCursor(new Cursor(Cursor.HAND_CURSOR));
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }

   //----------------------------

   //@refac.NotOverride 
   public void setDelay(int ms) {
      if(ms<=0) throw new RuntimeException("Illegal sleep time:"+ms);
      timer.setDelay(ms);
   }
   //@refac.NotOverride 
   public int getDelay() {   return timer.getDelay();   }

   //@refac.NotOverride 
   public void start() {
      clockRunning=true;
      timer.start();
   }
   //@refac.NotOverride 
   public void stop() {
      clockRunning=false;
      timer.stop();
   }
   //@refac.NotOverride 
   public void restart() {
      clockRunning=true;
      timer.restart();
   }

   //[--------------------------------------
   //@refac.NotOverride 
   protected static final boolean isLeft(MouseEvent e) {
      return ((e.getModifiers() & InputEvent.BUTTON1_MASK )!=0) ;
   }
   //@refac.NotOverride 
   protected static final boolean isRight(MouseEvent e) {
      return ((e.getModifiers() & InputEvent.BUTTON3_MASK )!=0) ;
   }

   @Override public void mousePressed(MouseEvent e) { }
   @Override public void mouseReleased(MouseEvent e) { }
   @Override public void mouseClicked(MouseEvent e) {
      if(isRight(e)) {
         if(!clockRunning) {  start();  }
         else {  stop();  }
   //D   System.out.print("R ");
      }
      if(isLeft(e) && !clockRunning) {
         pane.gameCycle(++clockCount);
   //D   System.out.print(">");
      }
   }
   @Override public void mouseDragged(MouseEvent e) {
      if(isLeft(e) && !clockRunning) {
         pane.gameCycle(++clockCount);
   //D   System.out.print(">");
      }
   }
   @Override public void mouseEntered(MouseEvent e) { }
   @Override public void mouseExited(MouseEvent e) { }
   @Override public void mouseMoved(MouseEvent e) { }
   //]--------------------------------------

}//] class TwGameClock ==============================
