package tw.fc;
import javax.swing.JComponent;

import java.awt.event.KeyListener;
import java.util.Vector;

import static java.awt.event.KeyEvent.*;                                      //**W0306加入

import static tw.fc.Std.*;

// ****************************************************
//[ window input/output console: 
//[ a transparent console pasted over a window supporting I/O
public class WIOConsole 
   extends WOConsole 
   implements KeyListener
{

   private final StringBuilder keyinLine= new StringBuilder();
   // final Vector<String> keyinBuffer= new Vector<String>();  //: b4 A5.016L
   final Vector<String> keyinLines= new Vector<String>();  //: is synchronized.

   final WIOConsoleReader reader=new WIOConsoleReader(this);

//I  public final TxOWinStream out= new TxOWinStream(this);
//I  public final TxOWinStream err= new TxOWinStream(this);
   public final TxIWinStream in= new TxIWinStream(this);
   

 //----------------------------------------------------------
   public WIOConsole(
      int vwRowSize, int vwColSize, JComponent jc, int X, int Y
   ) {
      super(vwRowSize, vwColSize, jc, X, Y);
      super.hostWindow.addKeyListener(this);

      super.paintCursorFlag=true;

   }

   //[ form keyboard to keyinLine, 
   //[ executed by event-dispatching thread in WIOConsole 
   // public void keyin(char ch) {  //: b4 A5.030L
   protected void keyin(char ch) {   //**W0306修改                                                //**W0306修改
      if(ch!='\n') {
         int inPos= this.cursorJ-this.lineLength[curI]+this.keyinLine.length();  //**W0307修改
         this.keyinLine.insert(inPos, ch);                                       
      }
      else {
         this.keyinLine.append(ch);  //: append too!
         final String s= this.keyinLine.toString();
         this.keyinLines.add(s);
     //D 
System.out.println("\nget a line: ["+s+"]");
         this.keyinLine.delete(0, this.keyinLine.length());
      }  
   }


//I   public void paintComponent(Graphics gr) {


   @Override
   public void keyTyped(java.awt.event.KeyEvent e) {                             //**W0306加入
      final char ch=e.getKeyChar(); //: 已解析出charactor
      if(ch==VK_ESCAPE){ //java.awt.event.KeyEvent.VK_ESCAPE;
         this.cursorJ = curJ;                                                    //**W0307修正bug
         int len= this.keyinLine.length();
         for(int i=0; i<len; i++){
            super.print('\b');
         }
         this.keyinLine.delete(0, this.keyinLine.length());
      }else if(ch==VK_BACK_SPACE){  //java.awt.event.KeyEvent.VK_BACK_SPACE;
         int len= this.keyinLine.length();
         if(len>0){
            int delPos=this.cursorJ-this.lineLength[curI]+len-1;
            if(delPos>=0){                                                       //**W0307修正bug
               this.keyinLine.deleteCharAt(delPos);
               super.print('\b');
            }
         }
      }else{
      //D   cout.hex().p("[").pc((int)ch).p(ch).p("]"); //: 典型用法
      // //this.in.keyin(ch);  //: b4 A5.007.K
      //this.in.getReader().keyin(ch);  //: b4 A5.007.N
         this.keyin(ch);
         super.print(ch);
         super.hostWindow.repaint();
      }
   }

//竟拿不到 tab
   static final int keyBS=8;     //0x08
   static final int keyESC=27;    //0x1B
   static final int keyLEFT=37;   //0x25
   static final int keyRIGHT=39;  //0x27
//   static final int keyUP=0x26, keyDown=0x28;
//   static final int keyF1=0x70, keyF2=0x71;
//   static final int keyF9=0x78;

   @Override
   public void keyPressed(java.awt.event.KeyEvent e) {                                 //**W0306修改
      final int keyCode=e.getKeyCode(); 
      if(keyCode==VK_LEFT){  //java.awt.event.KeyEvent.VK_LEFT keyTyped不能接收左右的事件
         if(this.lineLength[curI]-this.keyinLine.length()<cursorJ){
            super.cursorToLeft();
         }
      }else if(keyCode==VK_RIGHT){  //java.awt.event.KeyEvent.VK_LEFT
         super.cursorToRight();
      }
         //: 純按鍵, 不分大小寫. 含shift, alt, F1-F10, edit-key
//D   cout.dec().p("(.").p(keyCode).p(")");  //: 典型用法
   }

   @Override
   public void keyReleased(java.awt.event.KeyEvent e) {  }


}