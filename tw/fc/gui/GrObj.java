package tw.fc.gui;

import static tw.fc.Std.iRound ;

public abstract class GrObj {

   //[ =========  static part   =======================

   public static double PI=Math.PI;
   public static double PI2=Math.PI*2;
   public static double sqrt2=Math.sqrt(2);

   public static final MuV2D lastMousePos=new MuV2D();
           //: 公用, 因為全電腦只有一隻mouse

   //---------------------------------

//   public static final ImuV2D lastMousePos() {  return lastMousePos; }

/////   public static int round(double d) {  return (int)Math.round(d);  }
/////   public static double cos(double d) {  return Math.cos(d);  }
/////   public static double sin(double d) {  return Math.sin(d);  }
/////   public static double tan(double d) {  return Math.tan(d);  }

   static double rangeCorrection(double sine) {
   //: 原為 class Space3D的static方法double correctingSine(double)
   //:
   //: 使用Math.asin(s), 在s為1.0時因誤差而使函數值為NaN. 
   //: 此bug除錯達數小時.     20040509
   //:                                              
      if(sine>1.0) {
         if(sine>1.001) {
            throw new IllegalArgumentException("range error: "+sine);
         }
         return 1.0;  //: floating error correction
      }
      if(sine<-1.0) {
         if(sine<-1.001) {
            throw new IllegalArgumentException("range error: "+sine);
         }
         return -1.0;  //: floating error correction
      } 
      return sine;
   }
//[ 移入class Std, 此段不刪備搜
//   public static double a_sin(double d) {  
//      return Math.asin(rangeCorrection(d));  
//   }
//   public static double a_cos(double d) {  
//      return Math.acos(rangeCorrection(d));  
//   }

   //[  deprecated, 應使用 class XYW 的 seyBy(c, r)
   public static void setXYW(ImuV2D c, double r, int[] dst) {
   // 由圓心及半徑求外接正方形的x,y,w
      dst[0]=iRound(c.x()-r);
      dst[1]=iRound(c.y()-r);
      dst[2]=iRound(r*2);
   } 

   //] =========  static part   =======================


   //[ =========  instance part  ====================

   public boolean visible=true;  //: 鼓勵直接取用

   protected final MuV2D tempVec=new MuV2D(); 
        //: 可避免常new , 但多緒時要小心

   //---------------------------------

//   public ImuV2D getPosition() { 
//      //: 可能不用. 若使用就須override. 例如 Eye.paintTo 會用到
//      throw new RuntimeException("\n\nnot overrided in subclass of GrObj\n");
//   }
   public boolean contains(tw.fc.gui.ImuV2D at) {
      return false;  //: 預設成不佔空間. 通常要override.
   }



   public void setPanelForChildren() {  
      ;               //: may be overriden in subclass 
   } 

   //abstract public void toTop();

   //abstract public void toBottom();

   //abstract public void indexOf();


   //[ ----------
   //[  MouseEvent給全名是便於客戶程式抄製
   // 不用listener的原名以避免GraphicObj與panel同檔時改錯地方.
   // 不必區分mousePressed, keyPressed, 因可overload
   // for 比 at 好, 反應嘛

   public void forEntered(java.awt.event.MouseEvent e) { }
   public void forExited(java.awt.event.MouseEvent e) { }
   public void forPressed(java.awt.event.MouseEvent e) { }
   public void forReleased(java.awt.event.MouseEvent e) { }
   public void forClicked(java.awt.event.MouseEvent e) { }
   public void forMoved(java.awt.event.MouseEvent e) { }
   public void forDragged(java.awt.event.MouseEvent e) { 
       // 若可拖動則通常如下
   //  tempVec.setBy(e).subBy(lastMousePos());
   //  this.translate(tempVec);
   }
   public void forWheeled(java.awt.event.MouseWheelEvent e) { }

   public void forPressed(java.awt.event.KeyEvent e) { }
   public void forReleased(java.awt.event.KeyEvent e) { }
   public void forTyped(java.awt.event.KeyEvent e) { }

   //] -----------


   //[ =========  instance part  ====================

}