package tw.fc.gui;
import java.awt.Frame;
//import java.awt.Window;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyListener;
import java.awt.event.ComponentListener;
//import java.awt.event.KeyEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.Component;

//**************   GrPanel.java    ********************//
//
//  本類別的功能:
//  1. 當frame的content pane用, 預設底色(可用java.awt.Component的setBackground改)
//     並可由setWatching(true)監視滑鼠進出
//  2. 預設各種listener, 使程式師只須覆寫要用到的反應函數
//     並提供滑鼠判別 isLeft, isRight, withControl, withShift, withAlt
//     並提供許多按鍵的symbolic name
//  3. 管理GrObj
//  4. 提供簡便的動力機制.

//
//  發展史
//  1. 本class修飾自TeachGUI舊版的PassivePanel,
//     改稱 tw.fc.gui.TwPanel.
//     原先是在此class做double buffering, 但因JComponent已做, 所以就省掉.
//     先前版本還用PaneListen固定做passive object的脫曳, 本版放棄.
//     後來又將active panel的機制也併入此class,
//     並由不同的ctor區分.
//
//  2. 寫製商課程GraphicMath時改稱GrPanel.
//     加上GraphicObjManager, 用來管理GraphicObj
//     當成GraphicPanel, Space3D, Space2D的superclass
//
//  3. 940822拆出GrObj, GrObjManager, GrPanel
//
//  11. 990806將manager下移到GraphicPanel. 這是因為3D時manager的行為大不相同
//
public abstract class GrPanel
       extends GrComponent 
               // extends JComponent
       implements MouseInputListener,
                  MouseWheelListener,
                  KeyListener,
                  ComponentListener,
                  WindowListener,
                  GameEngineI
{
   //[==========  static part  ================================

   private static final long serialVersionUID= 2005111612L;

   public static final Cursor handCursor= new Cursor(Cursor.HAND_CURSOR);
   public static final Cursor waitCursor= new Cursor(Cursor.WAIT_CURSOR);
   public static final Cursor normalCursor= new Cursor(Cursor.DEFAULT_CURSOR);
 //public void setWaitCursor() {   setCursor(waitCursor);   }
 //public void setHandCursor() {   setCursor(handCursor);   }
 //public void setNormalCursor() {  setCursor(normalCursor);   }

   @SuppressWarnings("static-access") //A31208X
   public static void delay(int ms) { //:  各種game常會需要 delay
      try {
         //Thread.currentThread().sleep(ms);  //: error b4 A5.043L
         Thread.sleep(ms);
      }
      catch(InterruptedException e) { }
   }

   //[ 設定去鋸齒化功能
   public static void antialias(Graphics g, boolean b) {
      if(g==null) throw new IllegalArgumentException("\n... null Graphics");
      final Graphics2D g2d=(Graphics2D)g;
      if(b) {
      	g2d.setRenderingHint(
           RenderingHints.KEY_ANTIALIASING,
 		       RenderingHints.VALUE_ANTIALIAS_ON
        );
      }
      else {
      	g2d.setRenderingHint(
           RenderingHints.KEY_ANTIALIASING,///開啟去鋸齒化功能
 		       RenderingHints.VALUE_ANTIALIAS_OFF
        );
   	  }
   }

//[ 改成在GrPanel.windowClosing中作反應
/////   public void setExitClose(JFrame jf) {
/////      this.flag_stopPulse=true;
/////      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/////   }
/////   public void setConfirmClose(
/////      final JFrame jf, final String msg, final String title
/////   ) {
/////      jf.addWindowListener(
/////         new java.awt.event.WindowAdapter() {
/////            public void windowClosing(WindowEvent e) {
/////               if(JOptionPane.YES_OPTION==
/////                  JOptionPane.showConfirmDialog(
/////                     jf, msg, title, JOptionPane.YES_NO_OPTION
/////                  )
/////               ) {
/////                  GrPanel.this.flag_stopPulse=true;
/////                  System.exit(0);
/////               } else {
/////                  ;  //:  ignore
/////               }
/////            }
/////
/////         }
/////      );
/////   }

   //-------------------------------------------------------
/////   public static int round(double d) {  //: 太常用了
/////      return (int)Math.round(d);
/////   }

   public void verticalChanged(double azi, GrObj o){}
   public void horizontalChanged(double alt, GrObj o){}
   public void mixedChanged(double dx, double dy, GrObj o){}
   public void stepChanged(int i, GrObj button){}

   public void verticalChanged(double azi, GrComponent c){}
   public void horizontalChanged(double alt, GrComponent c){}
   public void mixedChanged(double dx, double dy, GrComponent c){}
   public void stepChanged(int i, GrComponent button){}
   public void stepChanged(int i, GrButton button){}

   public void verticalChanged(double azi, Component c){}
   public void horizontalChanged(double alt, Component c){}
   public void mixedChanged(double dx, double dy, Component c){}
   public void stepChanged(int i, Component button){}

   public static void GrDrawLine(Graphics g, ImuV2D p1, ImuV2D p2) {
      g.drawLine(p1.ix(), p1.iy(), p2.ix(), p2.iy());
   }

   public static void GrDrawRect(Graphics g, ImuV2D p1, ImuV2D p2) {
   // p1, p2為任意對角點
      int minX,minY,W,H;
      if(p1.x<=p2.x) {
         minX=(int)Math.round(p1.x);
         W=(int)Math.round(p2.x-p1.x);
      }
      else {
         minX=(int)Math.round(p2.x);
         W=(int)Math.round(p1.x-p2.x);
      }
      if(p1.y<=p2.y) {
         minY=(int)Math.round(p1.y);
         H=(int)Math.round(p2.y-p1.y);
      }
      else {
         minY=(int)Math.round(p2.y);
         H=(int)Math.round(p1.y-p2.y);
      }
      g.drawRect(minX, minY, W, H);
   }
   public static void GrFillRect(Graphics g, ImuV2D p1, ImuV2D p2) {
   // p1, p2為任意對角點
      int minX,minY,W,H;
      if(p1.x<=p2.x) {
         minX=(int)Math.round(p1.x);
         W=(int)Math.round(p2.x-p1.x);
      }
      else {
         minX=(int)Math.round(p2.x);
         W=(int)Math.round(p1.x-p2.x);
      }
      if(p1.y<=p2.y) {
         minY=(int)Math.round(p1.y);
         H=(int)Math.round(p2.y-p1.y);
      }
      else {
         minY=(int)Math.round(p2.y);
         H=(int)Math.round(p1.y-p2.y);
      }
      g.fillRect(minX, minY, W, H);
   }

   public static void GrDrawCircle(Graphics g, ImuV2D center, double R) {
      final int iR=(int)Math.round(R);
      final int i2R=(int)Math.round(2*R);
      g.drawOval(center.ix()-iR, center.iy()-iR, i2R, i2R);
   }
   public static void GrFillCircle(Graphics g, ImuV2D center, double R) {
      final int iR=(int)Math.round(R);
      final int i2R=(int)Math.round(2*R);
      g.fillOval(center.ix()-iR, center.iy()-iR, i2R, i2R);
   }

   public static void GrDrawString(Graphics g, String s, ImuV2D p) {
      g.drawString(s, p.ix(), p.iy());
   }
   public static void GrDrawName(
      Graphics g, String name, ImuV2D p,
      int dx, int dy   // 微偏量
   ) {
      g.drawString(name, p.ix()+dx, p.iy()+dy);
   }

   //]==========  static part  ================================

   //[ =========  instance part  ==============================

   //-----------------------------------------------
   protected Frame theFrame;
///////   protected GrObjManager GrObjM=null;        //: 繪圖物件管理
   protected TwGameClock gameClock=null;      //: 動力

   private boolean flag_stopPulse=true;  //:  encourage direct access
                   //: 此flag經由另一thread呼叫stopPulse()
                   //: 而結束startPulse()


   //------------------------------------------------
   public GrPanel() {
      //: JApplet 不繼承Window, 所以不能取消這個ctor
      setBackground(new Color(0x99,0xFF,0x99));
              //: of java.awt.JComponent,
              //: it overrides Compone nts.setBackground

      //  this.graphicObjM=new GrObjManager(this);  // 由subclass設值

      //[ 實驗於jdk1.4得知先add的listener先反應
      this.addMouseListener(this);
      // this.addMouseListener(graphicObjM);
      this.addMouseMotionListener(this);
      // this.addMouseMotionListener(graphicObjM);
      //] 在此加GrObjM為listener的優點是不怕subclass覆寫掉GrObjM的反應
      //] 缺點是降低可讀性, 而且不能精確控制較複雜的整體反應
      //] 所以還是決定由 GrPanel 負責呼叫 graphicObjM 的反應函數
      addMouseWheelListener(this);
      this.addKeyListener(this);
      this.addComponentListener(this);
   // this.addWindowListener(this);
   //          //: 不行! 只能加在class Window上
   //          //: 要靠ctor GrPanel(Frame)或setFrame(Frame)做
   }
   public GrPanel(Frame mf) {
      this();    this.setFrame(mf);
   }

   //[ ---  deprecated ctrs
         @Deprecated
         public GrPanel(int sleepMs, boolean showClock) {
            this();    this.setClock(sleepMs, showClock);
         }
         @Deprecated
         public GrPanel(Frame mf, int sleepMs, boolean showClock) {
            this(mf);  this.setClock(sleepMs, showClock);
         }
         @Deprecated
         public GrPanel(int sleepMs) {  //: release 時使用
            this();    this.setClock(sleepMs, false);
         }
         @Deprecated
         public GrPanel(Frame mf, int sleepMs) {  //: release 時使用
            this(mf);  this.setClock(sleepMs, false);
         }
   //] ----

   //------------------------------------------------

   public void setFrame(Frame mf) {
      //: 裝填main frame是為了要用windowlistener申請keyboard focus
      theFrame=mf;
      mf.addWindowListener(this); //: 是class Window的方法
   }

   //---------------------------------------------------------
   //
   abstract public int getObjCount() ;

//[ 在subclass, 並修改IO型態
// public void addObj(GrObj ob) {
// public void removeObj(GrObj ob) {
// public GrObj getObj(int idx) {
// public final int indexOf(GrObj ob) {  //: 不屬於則-1
// public GrObj findObjAt(ImuV2D at) {
// public void toTop(GrObj ob) {
//

// 在GraphicPanel是
//   public void paintAllObj(Graphics g) {
// 但在View3D是
//   public void paintAllObj() { // 由wG自取Graphics


   //---------------------------------------------------------

   protected void paintComponent(Graphics g) {
        // -- Note in jdkdoc --
        //  Beginning with Java 1.1, the background color
        //  of offscreen images may be system dependent.
        //  Applications should use setColor followed by fillRect
        //  to ensure that an offscreen image is cleared
        //  to a specific color.

      final Color oldColor=g.getColor();
      //[ jdkdoc說若繼承JPanel就可以靠super畫背景
      //[ 本class只繼承JComponent, 所以自己畫背景
      final Color bc=getBackground(); //: of java.awt.Component
      g.setColor(bc);  g.fillRect(0,0,getWidth(), getHeight());
      g.setColor(oldColor); //: 否則可能會害接下來畫的看不到
      //] 畫背景

      //---------
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc說不一定會成功.
        //: 要等收到 FocusEvent.FOCUS_GAINED 才確定成功.

   }

   public void paintAddedJComponents(Graphics g){
      final int count = super.getComponentCount();
      for(int i=0; i<count; i++){
         final Component c0 = super.getComponent(i);
         if(c0 instanceof GrComponent) {
            final GrComponent c=(GrComponent)c0;
            c.paintComponent(g);
//D         System.out.print("~"+i);
         }
         else {
            c0.repaint();   // not clear what to happen, discorage to do so.
         }
      }
      for(int i=0; i<count; i++){
         final GrComponent c = (GrComponent)super.getComponent(i);
         if(c instanceof GrButton){
            GrButton d = (GrButton)c;
            d.drawTip(g);
         }
      }
   }

   //---------------------------------------------------

   public static final boolean isLeftDown(MouseEvent e) {
      return ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK )!=0) ;
   // return ((e.getModifiers() & InputEvent.BUTTON1_MASK )!=0); //: older
   //// return (e.getButton()==MouseEvent.BUTTON1);  //: jdk1.4起
   }
   @Deprecated public static final boolean isLeft(MouseEvent e) {
      return isLeftDown(e);
   }
   public static final boolean isMiddleDown(MouseEvent e) {
      return ((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK )!=0) ;
   }
   public static final boolean isRightDown(MouseEvent e) {
      // // // return (e.getButton()==MouseEvent.BUTTON3) ; //: jdk1.4起
      // // return ((e.getModifiers() & InputEvent.BUTTON3_MASK )!=0) ; //: old
      // return ((e.getModifiersEx() & InputEvent.BUTTON3_MASK )!=0) ; //: bug before A30523L
      return ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK )!=0) ;
   }
   @Deprecated public static final boolean isRight(MouseEvent e) {
      return isRight(e);
   }

   public static final boolean withControl(MouseEvent e) {
      return e.isControlDown();
   }
   public static final boolean withShift(MouseEvent e) {
      return e.isShiftDown();
   }
   public static final boolean withAlt(MouseEvent e) {
      return e.isAltDown();
   }
   //-------

//[ 滑鼠操作在2D與在3D大不相同
//   //[ ---------  implements  MouseInputListener  -------------
//   //[  should be overrided to proper actions
//   //[  MouseEvent給全名是為寫subclass時便於抄製
//   public void mouseEntered(java.awt.event.MouseEvent e) {
//   public void mouseExited(java.awt.event.MouseEvent e) {
//   public void mousePressed(java.awt.event.MouseEvent e) {
//   public void mouseReleased(java.awt.event.MouseEvent e) {
//   public void mouseClicked(java.awt.event.MouseEvent e) {
//   public void mouseMoved(java.awt.event.MouseEvent e) {
//   public void mouseDragged(java.awt.event.MouseEvent e) {
//   //] ---------  implements  MouseInputListener  -------------
//
//   //[ ---------  implements MouseWheelListener  -------------
//   public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
//   //] ---------  implements MouseWheelListener  -------------


   //[ ---------  KeyListener   ------------------------
   //[ should be overrided to proper actions
   //[ 靠 requestFocusInWindow(),
   //[ 現是在paintComponent及mouseEntered做要求

   public static final int KEY_SHT=0x10, KEY_CTR=0x11;
   public static final int KEY_ALT=0x12;   //: 會引起視窗反應!
   public static final int
      KEY_ESC=0x1B,  KEY_ENTER=0x0A,  KEY_BKSP=0x08, KEY_CAPLOCK=0x14,
      KEY_PAUSE=0x13, KEY_NumLk=0x91;
             //: Tab無反應, SysRq只在release為0x9A,
   public static final int
      KEY_UP=0x26,   KEY_DOWN=0x28,   KEY_LEFT=0x25, KEY_RIGHT=0x27,
      KEY_PGUP=0x21, KEY_PGDOWN=0x22, KEY_HOME=0x24, KEY_END=0x23,
      KEY_INS=0x9b,  KEY_DEL=0x7f;
   public static final int
      KEY_F1=0x70, KEY_F2=0x71, KEY_F3=0x72, KEY_F4=0x73, KEY_F5=0x74,
      KEY_F6=0x75, KEY_F7=0x76, KEY_F8=0x77, KEY_F9=0x78, KEY_F10=0x79;
        //: F11, F12無反應

   public void keyPressed(java.awt.event.KeyEvent e) {
      final int keyCode=e.getKeyCode();  //: 純按鍵, 不分大小寫.
      tw.fc.Std.cout.hex().p("(.").p(keyCode).p(")");  //: 典型用法
   }
   public void keyReleased(java.awt.event.KeyEvent e) {
   // final int keyCode=e.getKeyCode();  //: 純按鍵, 不分大小寫.
   // tw.fc.Std.cout.hex().p("(^").p(keyCode).p(")");  //: 典型用法
   }

   //--------------------
   public static final char
//    ' '=0x20, '!'=0x21, ...,  '/'=0x2F
//    '0'=0x30, '1'=0x31, ...,  '9'=0x39,
//    ':'=0x3A, ..., '?'=0x3F,
//    '@'=0x40, 'A'=0x41, 'B'=0x42, ... ,  'Z'=0x5A,
//    '['=0x5B, ..., '_'=0x5E,
//    '\''=0x60, 'a'=0x61, ... ,  'z'=0x7A,
//    '{'=0x7B, ..., '~'=0x7E,
//                             //: 此區用character literal即可
      CH_CTR_AT=0x00,
      CH_CTR_A=0x01, CH_CTR_B=0x02, CH_CTR_C=0x03, CH_CTR_D=0x04,
      CH_CTR_E=0x05, CH_CTR_F=0x06, CH_CTR_G=0x07, CH_CTR_H=0x08,
      CH_CTR_I=0x09, CH_CTR_J=0x0A, CH_CTR_K=0x0B, CH_CTR_L=0x0C,
      CH_CTR_M=0x0D, CH_CTR_N=0x0E, CH_CTR_O=0x0F, CH_CTR_P=0x10,
      CH_CTR_Q=0x11, CH_CTR_R=0x12, CH_CTR_S=0x13, CH_CTR_T=0x14,
      CH_CTR_U=0x15, CH_CTR_V=0x16, CH_CTR_W=0x17, CH_CTR_X=0x18,
      CH_CTR_Y=0x19, CH_CTR_Z=0x1A,
      CH_CTR_LBRAKET=0x1B,  CH_CTR_BKSLASH=0x1C, CH_CTR_RBRAKET=0x1D,
      CH_CTR_CAP=0x1E, CH_CTR_UNDERBAR=0x1F;
       // CH_CTR_SP會切換中英文
       // CH_CTR_0 ... CH_CTR_9, CH_CTR_- 都沒有char
       // CH_CTR_F1 ... CH_CTR_F12 都沒有char

   public void keyTyped(java.awt.event.KeyEvent e) {
   // final char c=e.getKeyChar();    //: 已解析出charactor
   // tw.fc.Std.cout.p("[").pc((int)c).p(c).p("]");  //: 典型用法
   }


   //[ implements ComponentListener
   public void componentShown(ComponentEvent e) { }
   public void componentHidden(ComponentEvent e) { }
   public void componentMoved(ComponentEvent e) { }
   public void componentResized(ComponentEvent e) { }

   //[ implements WindowListener
   public void windowActivated(WindowEvent e) {
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc說不一定會成功.
        //: 要等收到 FocusEvent.FOCUS_GAINED 才確定成功.
   //D  System.out.print("!A");
   }
   public void windowClosed(WindowEvent e) {
      // ?
   }
   public void windowClosing(WindowEvent e) {
      if(JOptionPane.YES_OPTION==
         JOptionPane.showConfirmDialog(
            theFrame, "Really to Exit?", "TwFC",
            JOptionPane.YES_NO_OPTION
         )
      ) {
         this.flag_stopPulse=true;
         System.exit(0);
      } else {
         ;  //:  ignore
      }
   }
   public void windowDeactivated(WindowEvent e) { }
   public void windowDeiconified(WindowEvent e) {
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc說不一定會成功.
        //: 要等收到 FocusEvent.FOCUS_GAINED 才確定成功.
   //D  System.out.print("!F");
   }
   public void windowIconified(WindowEvent e) { }
   public void windowOpened(WindowEvent e) {
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc說不一定會成功.
        //: 要等收到 FocusEvent.FOCUS_GAINED 才確定成功.
   //D  System.out.print("!O");
   }

   //--------------------------------------------------------

   //[  ------- implements GameEngineI  ----------------
   public void gameCycle(int logNum) {   }   //: 預備被override

   public final void setClock(int sleepMs, boolean showClock) {
      gameClock=new TwGameClock(sleepMs, this);
      if(showClock) {
         gameClock.setVisible(true);   //: developing 時使用
      }
      else {
         gameClock.start();  //: release 時使用, 或改用main呼叫drive
      }
   }

   public final void stopPulse(int sleepMs) {
      flag_stopPulse=true;
   }
   public final void startPulse(int sleepMs) {
   //: 由某個thread直接呼叫, 直到另一thread將flag_stopPulse設true才結束.
   //: 因System.exit可能被封住, 所以Frame上應加WindowListener,
   //:                         使close時可將flag_stopPulse設為false.
      flag_stopPulse=false;
      int count=0;
      while(!flag_stopPulse) {
         gameCycle(++count);
         GrPanel.delay(sleepMs);
      }
   }
   //]  ------- implements GameEngineI  ----------------

   //] =========  instance part  ==============================

} //]  class GrPanel   ===============================
