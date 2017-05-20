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
//  �����O���\��:
//  1. ��frame��content pane��, �w�]����(�i��java.awt.Component��setBackground��)
//     �åi��setWatching(true)�ʵ��ƹ��i�X
//  2. �w�]�U��listener, �ϵ{���v�u���мg�n�Ψ쪺�������
//     �ô��ѷƹ��P�O isLeft, isRight, withControl, withShift, withAlt
//     �ô��ѳ\�h���䪺symbolic name
//  3. �޲zGrObj
//  4. ����²�K���ʤO����.

//
//  �o�i�v
//  1. ��class�׹���TeachGUI�ª���PassivePanel,
//     ��� tw.fc.gui.TwPanel.
//     ����O�b��class��double buffering, ���]JComponent�w��, �ҥH�N�ٱ�.
//     ���e�����٥�PaneListen�T�w��passive object���榲, �������.
//     ��ӤS�Nactive panel������]�֤J��class,
//     �åѤ��P��ctor�Ϥ�.
//
//  2. �g�s�ӽҵ{GraphicMath�ɧ��GrPanel.
//     �[�WGraphicObjManager, �ΨӺ޲zGraphicObj
//     ��GraphicPanel, Space3D, Space2D��superclass
//
//  3. 940822��XGrObj, GrObjManager, GrPanel
//
//  11. 990806�Nmanager�U����GraphicPanel. �o�O�]��3D��manager���欰�j���ۦP
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
   public static void delay(int ms) { //:  �U��game�`�|�ݭn delay
      try {
         //Thread.currentThread().sleep(ms);  //: error b4 A5.043L
         Thread.sleep(ms);
      }
      catch(InterruptedException e) { }
   }

   //[ �]�w�h�����ƥ\��
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
           RenderingHints.KEY_ANTIALIASING,///�}�ҥh�����ƥ\��
 		       RenderingHints.VALUE_ANTIALIAS_OFF
        );
   	  }
   }

//[ �令�bGrPanel.windowClosing���@����
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
/////   public static int round(double d) {  //: �ӱ`�ΤF
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
   // p1, p2�����N�﨤�I
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
   // p1, p2�����N�﨤�I
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
      int dx, int dy   // �L���q
   ) {
      g.drawString(name, p.ix()+dx, p.iy()+dy);
   }

   //]==========  static part  ================================

   //[ =========  instance part  ==============================

   //-----------------------------------------------
   protected Frame theFrame;
///////   protected GrObjManager GrObjM=null;        //: ø�Ϫ���޲z
   protected TwGameClock gameClock=null;      //: �ʤO

   private boolean flag_stopPulse=true;  //:  encourage direct access
                   //: ��flag�g�ѥt�@thread�I�sstopPulse()
                   //: �ӵ���startPulse()


   //------------------------------------------------
   public GrPanel() {
      //: JApplet ���~��Window, �ҥH��������o��ctor
      setBackground(new Color(0x99,0xFF,0x99));
              //: of java.awt.JComponent,
              //: it overrides Compone nts.setBackground

      //  this.graphicObjM=new GrObjManager(this);  // ��subclass�]��

      //[ �����jdk1.4�o����add��listener������
      this.addMouseListener(this);
      // this.addMouseListener(graphicObjM);
      this.addMouseMotionListener(this);
      // this.addMouseMotionListener(graphicObjM);
      //] �b���[GrObjM��listener���u�I�O����subclass�мg��GrObjM������
      //] ���I�O���C�iŪ��, �ӥB�����T������������������
      //] �ҥH�٬O�M�w�� GrPanel �t�d�I�s graphicObjM ���������
      addMouseWheelListener(this);
      this.addKeyListener(this);
      this.addComponentListener(this);
   // this.addWindowListener(this);
   //          //: ����! �u��[�bclass Window�W
   //          //: �n�actor GrPanel(Frame)��setFrame(Frame)��
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
         public GrPanel(int sleepMs) {  //: release �ɨϥ�
            this();    this.setClock(sleepMs, false);
         }
         @Deprecated
         public GrPanel(Frame mf, int sleepMs) {  //: release �ɨϥ�
            this(mf);  this.setClock(sleepMs, false);
         }
   //] ----

   //------------------------------------------------

   public void setFrame(Frame mf) {
      //: �˶�main frame�O���F�n��windowlistener�ӽ�keyboard focus
      theFrame=mf;
      mf.addWindowListener(this); //: �Oclass Window����k
   }

   //---------------------------------------------------------
   //
   abstract public int getObjCount() ;

//[ �bsubclass, �íק�IO���A
// public void addObj(GrObj ob) {
// public void removeObj(GrObj ob) {
// public GrObj getObj(int idx) {
// public final int indexOf(GrObj ob) {  //: ���ݩ�h-1
// public GrObj findObjAt(ImuV2D at) {
// public void toTop(GrObj ob) {
//

// �bGraphicPanel�O
//   public void paintAllObj(Graphics g) {
// ���bView3D�O
//   public void paintAllObj() { // ��wG�ۨ�Graphics


   //---------------------------------------------------------

   protected void paintComponent(Graphics g) {
        // -- Note in jdkdoc --
        //  Beginning with Java 1.1, the background color
        //  of offscreen images may be system dependent.
        //  Applications should use setColor followed by fillRect
        //  to ensure that an offscreen image is cleared
        //  to a specific color.

      final Color oldColor=g.getColor();
      //[ jdkdoc���Y�~��JPanel�N�i�H�asuper�e�I��
      //[ ��class�u�~��JComponent, �ҥH�ۤv�e�I��
      final Color bc=getBackground(); //: of java.awt.Component
      g.setColor(bc);  g.fillRect(0,0,getWidth(), getHeight());
      g.setColor(oldColor); //: �_�h�i��|�`���U�ӵe���ݤ���
      //] �e�I��

      //---------
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc�����@�w�|���\.
        //: �n������ FocusEvent.FOCUS_GAINED �~�T�w���\.

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
   //// return (e.getButton()==MouseEvent.BUTTON1);  //: jdk1.4�_
   }
   @Deprecated public static final boolean isLeft(MouseEvent e) {
      return isLeftDown(e);
   }
   public static final boolean isMiddleDown(MouseEvent e) {
      return ((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK )!=0) ;
   }
   public static final boolean isRightDown(MouseEvent e) {
      // // // return (e.getButton()==MouseEvent.BUTTON3) ; //: jdk1.4�_
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

//[ �ƹ��ާ@�b2D�P�b3D�j���ۦP
//   //[ ---------  implements  MouseInputListener  -------------
//   //[  should be overrided to proper actions
//   //[  MouseEvent�����W�O���gsubclass�ɫK��ۻs
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
   //[ �a requestFocusInWindow(),
   //[ �{�O�bpaintComponent��mouseEntered���n�D

   public static final int KEY_SHT=0x10, KEY_CTR=0x11;
   public static final int KEY_ALT=0x12;   //: �|�ް_��������!
   public static final int
      KEY_ESC=0x1B,  KEY_ENTER=0x0A,  KEY_BKSP=0x08, KEY_CAPLOCK=0x14,
      KEY_PAUSE=0x13, KEY_NumLk=0x91;
             //: Tab�L����, SysRq�u�brelease��0x9A,
   public static final int
      KEY_UP=0x26,   KEY_DOWN=0x28,   KEY_LEFT=0x25, KEY_RIGHT=0x27,
      KEY_PGUP=0x21, KEY_PGDOWN=0x22, KEY_HOME=0x24, KEY_END=0x23,
      KEY_INS=0x9b,  KEY_DEL=0x7f;
   public static final int
      KEY_F1=0x70, KEY_F2=0x71, KEY_F3=0x72, KEY_F4=0x73, KEY_F5=0x74,
      KEY_F6=0x75, KEY_F7=0x76, KEY_F8=0x77, KEY_F9=0x78, KEY_F10=0x79;
        //: F11, F12�L����

   public void keyPressed(java.awt.event.KeyEvent e) {
      final int keyCode=e.getKeyCode();  //: �«���, �����j�p�g.
      tw.fc.Std.cout.hex().p("(.").p(keyCode).p(")");  //: �嫬�Ϊk
   }
   public void keyReleased(java.awt.event.KeyEvent e) {
   // final int keyCode=e.getKeyCode();  //: �«���, �����j�p�g.
   // tw.fc.Std.cout.hex().p("(^").p(keyCode).p(")");  //: �嫬�Ϊk
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
//                             //: ���ϥ�character literal�Y�i
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
       // CH_CTR_SP�|�������^��
       // CH_CTR_0 ... CH_CTR_9, CH_CTR_- ���S��char
       // CH_CTR_F1 ... CH_CTR_F12 ���S��char

   public void keyTyped(java.awt.event.KeyEvent e) {
   // final char c=e.getKeyChar();    //: �w�ѪR�Xcharactor
   // tw.fc.Std.cout.p("[").pc((int)c).p(c).p("]");  //: �嫬�Ϊk
   }


   //[ implements ComponentListener
   public void componentShown(ComponentEvent e) { }
   public void componentHidden(ComponentEvent e) { }
   public void componentMoved(ComponentEvent e) { }
   public void componentResized(ComponentEvent e) { }

   //[ implements WindowListener
   public void windowActivated(WindowEvent e) {
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc�����@�w�|���\.
        //: �n������ FocusEvent.FOCUS_GAINED �~�T�w���\.
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
        //: jdkdoc�����@�w�|���\.
        //: �n������ FocusEvent.FOCUS_GAINED �~�T�w���\.
   //D  System.out.print("!F");
   }
   public void windowIconified(WindowEvent e) { }
   public void windowOpened(WindowEvent e) {
      requestFocusInWindow();  //: in JComponent
        //: jdkdoc�����@�w�|���\.
        //: �n������ FocusEvent.FOCUS_GAINED �~�T�w���\.
   //D  System.out.print("!O");
   }

   //--------------------------------------------------------

   //[  ------- implements GameEngineI  ----------------
   public void gameCycle(int logNum) {   }   //: �w�ƳQoverride

   public final void setClock(int sleepMs, boolean showClock) {
      gameClock=new TwGameClock(sleepMs, this);
      if(showClock) {
         gameClock.setVisible(true);   //: developing �ɨϥ�
      }
      else {
         gameClock.start();  //: release �ɨϥ�, �Χ��main�I�sdrive
      }
   }

   public final void stopPulse(int sleepMs) {
      flag_stopPulse=true;
   }
   public final void startPulse(int sleepMs) {
   //: �ѬY��thread�����I�s, ����t�@thread�Nflag_stopPulse�]true�~����.
   //: �]System.exit�i��Q�ʦ�, �ҥHFrame�W���[WindowListener,
   //:                         ��close�ɥi�Nflag_stopPulse�]��false.
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
