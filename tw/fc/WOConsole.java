package tw.fc;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JComponent;
import static tw.fc.Std.*;
import tw.fc.gui.MuXY;
import tw.fc.gui.ImuXY;


// ****************************************************
//[ window output console: 
//[ a transparent console pasted over a window supporting output
public class WOConsole {

   public final TxOWinStream out= new TxOWinStream(this);
         //: implement redirecting ?
   public final TxOWinStream err= new TxOWinStream(this);
      //: the most important utility
 
   protected JComponent hostWindow;
   protected int consoleX, consoleY, consoleW=0, consoleH=0; 
   //private int curX, curY;  //: b4 A5.007.E

   //private int rendX, rendY;  //: current output position, in pixels 
   //private MuXY rend=new MuXY();


   private final int INITTOTALLINES=3;
   private static int FREESPACE=20;
   protected int[] lineLength= new int[INITTOTALLINES]; //: logically               //**W0306修改為protected
   private char[][] txBuf= new char[INITTOTALLINES][];
// { 
//    this.txBuf[0]= new char[this.FREESPACE]; 
// }  //: b4 A5.007.E
   protected int curI=0,   //: current row_index                                    //**W0306修改為protected
               curJ=0;   //: current colume_index
   private int totalLines=0;                        //: logically

 //----
   public boolean wordWrap;
   public boolean scrollable;
   private int viewRowSize; 
   private int viewColSize;  //: only used when wordWrap==true
   private int scrollValue=0; //: only used when (scrollable==true)
   //// private int viewTopLineIdx= 0;   //: b4 A5.004L

   public boolean monoSpacedAdjust=true;
   public boolean paintBorderFlag= true;
   public Color borderColor= Color.BLUE;
   public Color fontColor= Color.BLACK;

   public boolean paintCursorFlag= false;  

// public int cursorI; //跟curI同值
   protected int cursorJ=0;     //**W0306加入
 //----
   private int TABLENGTH= 8;

 //----------------------------------------------------------
   //public WOConsole(JComponent jc, int X, int Y, int W, int H) { //: b4 A5.004.L
   public WOConsole(int vwRowSize, int vwColSize, JComponent jc, int X, int Y) {
      this.viewRowSize=vwRowSize;  //: view row size
      this.scrollable=true;
      this.viewColSize=vwColSize;  //: view column size
      this.wordWrap=true;
      this.hostWindow=jc;
      this.consoleX=X;   this.consoleY=Y;
   }
//   public WOConsole(int viewRowSize, JComponent jc, int X, int Y) {
//      this(viewRowSize, 80, jc, X, Y);
//      this.scrollable=true;
//      this.wordWrap=false;
//   } //: b4 A5.007F
   public WOConsole(JComponent jc, int X, int Y) {
      this(25, 80, jc, X, Y);
      this.wordWrap=false;
      this.scrollable=false;
   }

 //----------------------------------------------------------

   public int getI() {   return this.curI; }
   public int getJ() {   return this.curJ; }
//[ TODO; open and test
//   public void setI(int i) {  this.curI= i;  }
//   public void setJ(int j) {  this.curJ= j;  }
//   public void setIJ(int i, int j) { 
//      this.curI= i;   this.curJ= j;
//   }

   void printDoc(TxOStream oS) throws IOException {
      for(int i=0; i<this.totalLines; i++) {
         for(int j=0; j<lineLength[i]; j++) {
            oS.p(txBuf[i][j]);
         }
         oS.pn();
      }
   }

  //---------------
   private void pCRLF() {
     //D System.out.println("pCRLF(), curIJ:"+curI+","+curJ);
      if(this.curJ==0) { //: CRLF in a empty line 
     //D System.out.println("totalLines:"+totalLines);
     //D System.out.println("curIJ:"+curI+","+curJ);
         this.totalLines++;
      }
      this.checkWritingPos();  //: add in A5.010W
      this.lineLength[this.curI]= curJ;   //**W0306修改
      this.curJ=0;  //: CR
      this.curI++;  //: LF
      cursorJ=0;
      hostWindow.repaint();
   }

   private void pBELL() {
      System.out.print('\7');  
       //: Not fill '=7' into txBuf, therefore not beep in paintComponent($)
   }

   private void pBS() {                              //**W0306修改
      if(this.curJ > 0){
         int len = this.lineLength[curI];
         for(int i=cursorJ-1; i<len-1; i++){
            this.txBuf[curI][i] = this.txBuf[curI][i+1];
         }
         this.curJ--;
         this.lineLength[this.curI]--;
         cursorJ--;
         hostWindow.repaint();
      }
   }

   //[ behaviors are not consistant between Window and UNIX 
   private void pTAB() { 
      int tabSpace= (this.curJ/TABLENGTH)+1;
      for(int i=this.curJ; i<tabSpace*TABLENGTH; i++){
         this.pChar(' ');  //: only store to buffer
         cursorJ++;                                       //**W0306修改
      }
   }


 
   //[ should be called just before writing into txBuf[curI][curJ] 
   //[ i.e. L==5:
   //[  0 1 2 3 4 L
   //[  * * * * *
   private void checkWritingPos() {
  //D System.out.println("check at "+curI+", "+curJ);
      final int cI=this.curI; 
      //if(cI>=this.totalLines) {  //: b4 A5.010W
//      if(cI>=this.totalLines || cI>=this.txBuf.length) { 
      if(cI>=this.totalLines || cI>=this.txBuf.length-1) {                    //**W0307修改
         cout.p("cI=").pn(cI);
         cout.p("this.totalLines=").pn(this.totalLines);
         cout.p((cI>=this.totalLines || cI>=this.txBuf.length-1));
                               //: consider a lot of '\n'  
         this.totalLines=this.curI+1;  //: legalize the line index curI
         final int oldPhyLineNum= this.txBuf.length;  //: physical length
         if(this.totalLines+INITTOTALLINES>oldPhyLineNum) { 
         //[ Due to empty lines, maybe (totalLines>oldPhyLineNum). 
            int newPhyLineNum= oldPhyLineNum*2;
            if(newPhyLineNum<totalLines) newPhyLineNum+=totalLines;
        //D System.out.println("oldPhyLineNum:"+oldPhyLineNum);
        //D System.out.println("totalLines:"+totalLines);
        //D System.out.println("INITTOTALLINES:"+INITTOTALLINES);
            char[][] newArray= new char[newPhyLineNum][];
            int[] newLenA= new int[newPhyLineNum];
            for(int i=0; i<this.totalLines; i++) {  
               newArray[i]=(i<oldPhyLineNum)? txBuf[i]: null;  
               newLenA[i]=(i<oldPhyLineNum)? lineLength[i]: 0;  
            }
            txBuf=newArray; 
            lineLength=newLenA; 
         }
      }
      if(this.txBuf[cI]==null) { // lazy initialization
         this.txBuf[cI]= new char[this.viewColSize]; 
      }
      final int len=lineLength[cI];  //: logically line length
      if(this.curJ>=len) {  //: consider \t, \b, and setIJ($$)
         lineLength[cI]=this.curJ+1;  //: legalize the position cutJ
         final int oldPhyLen= this.txBuf[cI].length;  //: physical length
         if(len+FREESPACE>this.txBuf[cI].length) { 
            char[] newArray= new char[oldPhyLen*2];
            for(int j=0; j<len; j++) {  newArray[j]=txBuf[cI][j];  }
            txBuf[cI]=newArray; 
         }
      }
   }

   private void pChar(char ch) {
      if(ch=='\t'||ch=='\n'||ch=='\b'||ch=='\7') 
         throw new Error( "Illegal ASCII code: "+(int)ch );
      //if(this.wordWrap && this.curJ>=this.viewColSize) {
      //   this.pCRLF();
      //}  //: b4 A5.004.A
  //D System.out.println("("+curI+","+curJ+")");
/*
      this.checkWritingPos();
      this.txBuf[this.curI][this.curJ++]=ch;
      cursorJ = this.curJ;
      hostWindow.repaint();
*/

      this.checkWritingPos();                                           //**W0306修改
      for(int i=this.curJ; i>=this.cursorJ+1; i--){
         this.txBuf[curI][i] = this.txBuf[curI][i-1];
      }
      this.txBuf[curI][this.cursorJ++]=ch;
      this.curJ++;
      hostWindow.repaint();
   }

   public void clearScreen() {                                          //**W0306修改
      for(int i=0; i<this.totalLines; i++) {
         lineLength[i]=0; 
      }
      this.totalLines=0;
      this.curI=0; this.curJ=0;
      this.cursorJ=0;
   }

 //------------------------
 //[ the method name is parallel to that of java.io.PrintStream
   void print(char ch) {
      this.scrollValue=0; 
      if(ch=='\n'){  this.pCRLF();  }
      else if(ch=='\b'){  this.pBS();  }
      else if(ch=='\t'){  this.pTAB(); }
      else if(ch=='\7'){  this.pBELL();  }
      else{    this.pChar(ch);    }
   }

   void print(String s) {
      for(int i=0; i<s.length(); i++) {
         this.print(s.charAt(i));
      }
   }
   void println(String s) {
      this.print(s);  this.pCRLF();
   }
   void println() {   this.pCRLF();   }
 //]

 //------------------------------------------------
 // rendering 

   void paintBorder(Graphics gr) {
      if(this.consoleW==0) {  //: not init.  null ptr xpt if init in ctor 
         final FontMetrics mtx= gr.getFontMetrics(gr.getFont());
         this.consoleW=viewColSize*getMaxChWidth(gr);   
         this.consoleH=viewRowSize*mtx.getHeight();
      }
      gr.setColor(this.borderColor);
      if(this.scrollable) {
         if(this.wordWrap) {
            gr.drawRect(consoleX,consoleY,consoleW,consoleH);
         }
         else {
            gr.drawLine(consoleX,             consoleY,
                        hostWindow.getWidth(),consoleY); //: upper horizontal
            gr.drawLine(consoleX,             consoleY+consoleH,
                        hostWindow.getWidth(),consoleY+consoleH);
                                                         //: lower horizontal  
            gr.drawLine(consoleX,consoleY,
                        consoleX,consoleY+consoleH);     //: left vertical
         }
      }
      else {
         if(this.wordWrap) {
            gr.drawLine(consoleX,          consoleY,
                        consoleX, hostWindow.getHeight()); //: left vertical
            gr.drawLine(consoleX+consoleW, consoleY,
                        consoleX+consoleW, hostWindow.getHeight()); 
                                                           //: right vertical
            gr.drawLine(consoleX,         consoleY,
                        consoleX+consoleW,consoleY);       //: upper horizontal
         }
         else {
            gr.drawLine(consoleX,consoleY,hostWindow.getWidth(),consoleY);
            gr.drawLine(consoleX,consoleY,consoleX,hostWindow.getHeight());
         }
      }
   }

   public final void flush() {  hostWindow.repaint();  }

   public static int getFontHeight(Graphics gr) {
      FontMetrics mtx= gr.getFontMetrics(gr.getFont());
      return mtx.getHeight();
   }

   public static int getMaxChWidth(Graphics gr) {
      return gr.getFont().getSize(); 
          //: ch_height as a width of a Chinese Char 
   // count by loop?
   }

   private void renderChar(Graphics gr, char ch, MuXY rendPos) {
      if(ch=='\t'||ch=='\n'||ch=='\b'||ch=='\7') 
         throw new Error( "Illegal ASCII code: "+(int)ch );
      final FontMetrics mtx= gr.getFontMetrics(gr.getFont());
      final String chString= String.valueOf(ch);
      final int chW= mtx.stringWidth(chString);
      final int maxChW= WOConsole.getMaxChWidth(gr);  
      final int shiftX= (monoSpacedAdjust)? (maxChW-chW)/2: 0;
      gr.setColor(this.fontColor);
      gr.drawString(chString, rendPos.x()+shiftX, rendPos.y());
      rendPos.xAddBy( (monoSpacedAdjust)? maxChW: chW );
   }

   private void renderCursor(Graphics gr, ImuXY rendPos) {                          //**W0306修改
      final FontMetrics mtx= gr.getFontMetrics(gr.getFont());
      final int mtxHeight= mtx.getHeight();
      final int maxChW= WOConsole.getMaxChWidth(gr);  
      gr.setXORMode(Color.WHITE);
      gr.setColor(Color.BLACK);
      int x, y;
      if(this.wordWrap){
         int row = (this.lineLength[curI]-1)/this.viewColSize;
         int cursorI = cursorJ/this.viewColSize;
         x=consoleX + (cursorJ % this.viewColSize) * maxChW;
         y=row-cursorI + 1;
      }else{
         x=consoleX + (cursorJ) * maxChW;
         y=1;
      }
      gr.fillRect(x, rendPos.y()-y*mtxHeight+2, 1+maxChW/4, mtxHeight);
//      gr.fillRect(rendPos.x(), rendPos.y()-mtxHeight+2, 1+maxChW/4, mtxHeight);
   }
   
   protected void cursorToLeft(){                                                         //**W0306增加
      if(cursorJ>0){
         cursorJ--;
      }
      hostWindow.repaint();
   }
   
   protected void cursorToRight(){                                                        //**W0306增加
      if(cursorJ<this.lineLength[curI]){
         cursorJ++;
      }
      hostWindow.repaint();
   }

  //----------------------------------------------
   private class ViewLeft implements PrintableI {
      final int viewWidth;
      int viewI;  static final int viewJ=0;
      int docI, docJ;
    //----------------------
      ViewLeft(int viewBottom, int curI, int curJ) {
         this.viewWidth= WOConsole.this.viewColSize;
         this.viewI= viewBottom;
         this.docI= curI; 
         this.docJ= (curJ/this.viewWidth)*this.viewWidth;
      }
      boolean docIJCanUp() {
         if(this.docI>0) {  return true; }
         else {
            if(!WOConsole.this.wordWrap) return false;
            else return (this.docJ>=this.viewWidth);
         }
      }
      boolean docIJUp() {
         if(!this.docIJCanUp()) {  return false;  }
         if(WOConsole.this.wordWrap && this.docJ>=this.viewWidth) {
          //[ 若可折且超寬則左移
            this.docJ-= this.viewWidth;
         }
         else {
          //[ 否則上移
            this.docI--;  
            int len=WOConsole.this.lineLength[this.docI];
            //this.docJ=(len/this.viewWidth)*this.viewWidth;  //: b4 A5.010W
            this.docJ= (WOConsole.this.wordWrap)?
                       (len/this.viewWidth)*this.viewWidth: 0 ;
         }
         return true;  //: valid area
      }
      boolean bothIJCanUp() {
         return (this.viewI>0 && docIJCanUp()); 
      }
      boolean bothIJUp() {
         if(!this.bothIJCanUp()) {  return false;  }
         this.docIJUp();
         this.viewI--;
         return true;  //: valid area
      }
      boolean docIJCanDown() {
         if(this.docI<WOConsole.this.totalLines-1) {  return true; }
         else {
            if(!WOConsole.this.wordWrap) return false;
            else {
               final int len= WOConsole.this.lineLength[this.docI];
               return (len-this.docJ>=this.viewWidth);
            }  
         }
      }
      boolean docIJDown() {
         if(!this.docIJCanDown()) {  return false;  }
         if(WOConsole.this.wordWrap && 
            this.docJ+this.viewWidth<WOConsole.this.lineLength[this.viewI]
         ) {
            this.docJ+= this.viewWidth;
         }
         else{  this.docI++;  this.docJ=0;  } 
         return (this.viewI<=WOConsole.this.viewRowSize);  //: valid area
      }
      boolean bothIJCanDown() {
         return (this.viewI<WOConsole.this.totalLines-1 && docIJCanDown()); 
      }
      boolean bothIJDown() {
         if(!this.bothIJCanDown()) {  return false;  }
         this.docIJDown();
         this.viewI++;
         return (this.viewI<=WOConsole.this.viewRowSize);  //: valid area
      }
      @Override public void printTo(TxOStream oS) throws IOException {
         oS.p("(").pcs(this.viewI).pc(this.docI).p(this.docJ).pcs(")");        
      }
   }
  //----------------------------------------------

   //[ so that VIEW@(0,0) corresponding to DOC@(docIJ[0], docIJ[1])
   private void computeViewTopDocPos(ViewLeft vL) {
      if(!this.scrollable) {
         vL.viewI=0;  vL.docI=0;  vL.docJ=0;  return;
      }
      while(vL.bothIJCanUp()) {  vL.bothIJUp();   }
      if(vL.viewI>0) { //: too few lines 
         vL.viewI=0; 
      }  
      //: now VIEW@(viewI, 0) corresponding to DOC@(docI, docJ)
      if(this.scrollValue==0) {  ;  }
      else if(this.scrollValue<0) {
         for(int s=-1; s>=this.scrollValue; s--) {
            boolean sucess=vL.docIJUp();
            if(!sucess) {  this.scrollValue=s+1;  break;  }
         }
      }
      else {  // (this.scrollValue>0)
         throw new Error("forbidden state");
      }    
   }

   public void paintComponent(Graphics gr) {
      final ViewLeft vL= 
         new ViewLeft(this.viewRowSize-1, this.curI, this.curJ);
      computeViewTopDocPos(vL); //: docPos corresponding to top-left of view
      int docI=vL.docI, docJ=vL.docJ; 
   //------ 
      if(this.paintBorderFlag) this.paintBorder(gr);
      final Font font= gr.getFont();
      final FontMetrics mtx= gr.getFontMetrics(font);
      final int mtxHeight= mtx.getHeight();
      final MuXY rend= new MuXY(consoleX, consoleY+mtxHeight);
                            //: from Top-left, adjust to baseline
P_DOC:    
      // for(int viewI=0; viewI<viewRowSize ; viewI++) {  //: b4 A5.010W
      for(int viewI=0; !this.scrollable || viewI<viewRowSize; viewI++) {
     //D cout.dec().p("\n>rendX=").pc(rendX).p("rendY=").pn(rendY);
     //D cout.dec().p(">docI=").pc(docI).p("docJ=").pn(docJ);
         if(docI>=this.totalLines) {
            //continue;
            break P_DOC;
         }
     //D cout.dec().p(docI).p(": ");
         while(docJ<lineLength[docI]) {
            if(this.wordWrap && docJ%this.viewColSize==0 && rend.x()>consoleX){ 
               rend.xSetBy(consoleX);  rend.yAddBy(mtxHeight);  
               viewI++;
               //if(viewI>=viewRowSize) break P_DOC;  //: b4 A5.010W
               if(this.scrollable && viewI>=viewRowSize) break P_DOC;
            }
        //D cout.p(txBuf[docI][docJ]);
        //D cout.dec().p((int)txBuf[docI][docJ]);
            this.renderChar(gr, txBuf[docI][docJ++], rend);
        //D cout.dec().p(".rendX=").pc(rendX).p("rendY=").pn(rendY);
         }
     //D cout.pn(); 
         if(docI>=curI) break P_DOC;  //: 下方不印(可能有scrollValue)
         docI++; docJ=0;
         rend.xSetBy(consoleX);  rend.yAddBy(mtxHeight);  
     //D cout.dec().p(".rendX=").pc(rendX).p("rendY=").pn(rendY);
      }  //] P_DOC:
  //D cout.p("totalLines:").pn(this.totalLines);
  //D try {  this.printDoc(cout);  } catch(IOException xpt) { }

  //D cout.dec().p("curI=").pc(curI).p("curJ=").pn(curJ);
      if(this.paintCursorFlag) {
         renderCursor(gr, rend);
      }
   }

   public void scroll(int s) {  // s<0 for view up and doc down
  //D System.out.println("scroll("+s+")");
      if(!this.scrollable) return;  //: ignore
      this.scrollValue+=s;
    //[ 行數夠多時, view底不該低於doc底.  所以禁止 scrollValue為正
      if(this.scrollValue>0) this.scrollValue=0; 
   }

}

