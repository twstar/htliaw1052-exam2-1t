package tw.fc.gui;

import static tw.fc.Std.iRound ;

public abstract class GrObj {

   //[ =========  static part   =======================

   public static double PI=Math.PI;
   public static double PI2=Math.PI*2;
   public static double sqrt2=Math.sqrt(2);

   public static final MuV2D lastMousePos=new MuV2D();
           //: ����, �]�����q���u���@��mouse

   //---------------------------------

//   public static final ImuV2D lastMousePos() {  return lastMousePos; }

/////   public static int round(double d) {  return (int)Math.round(d);  }
/////   public static double cos(double d) {  return Math.cos(d);  }
/////   public static double sin(double d) {  return Math.sin(d);  }
/////   public static double tan(double d) {  return Math.tan(d);  }

   static double rangeCorrection(double sine) {
   //: �쬰 class Space3D��static��kdouble correctingSine(double)
   //:
   //: �ϥ�Math.asin(s), �bs��1.0�ɦ]�~�t�ӨϨ�ƭȬ�NaN. 
   //: ��bug�����F�Ƥp��.     20040509
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
//[ ���Jclass Std, ���q���R�Ʒj
//   public static double a_sin(double d) {  
//      return Math.asin(rangeCorrection(d));  
//   }
//   public static double a_cos(double d) {  
//      return Math.acos(rangeCorrection(d));  
//   }

   //[  deprecated, ���ϥ� class XYW �� seyBy(c, r)
   public static void setXYW(ImuV2D c, double r, int[] dst) {
   // �Ѷ�ߤΥb�|�D�~������Ϊ�x,y,w
      dst[0]=iRound(c.x()-r);
      dst[1]=iRound(c.y()-r);
      dst[2]=iRound(r*2);
   } 

   //] =========  static part   =======================


   //[ =========  instance part  ====================

   public boolean visible=true;  //: ���y��������

   protected final MuV2D tempVec=new MuV2D(); 
        //: �i�קK�`new , ���h���ɭn�p��

   //---------------------------------

//   public ImuV2D getPosition() { 
//      //: �i�ण��. �Y�ϥδN��override. �Ҧp Eye.paintTo �|�Ψ�
//      throw new RuntimeException("\n\nnot overrided in subclass of GrObj\n");
//   }
   public boolean contains(tw.fc.gui.ImuV2D at) {
      return false;  //: �w�]�������Ŷ�. �q�`�noverride.
   }



   public void setPanelForChildren() {  
      ;               //: may be overriden in subclass 
   } 

   //abstract public void toTop();

   //abstract public void toBottom();

   //abstract public void indexOf();


   //[ ----------
   //[  MouseEvent�����W�O�K��Ȥ�{���ۻs
   // ����listener����W�H�קKGraphicObj�Ppanel�P�ɮɧ���a��.
   // �����Ϥ�mousePressed, keyPressed, �]�ioverload
   // for �� at �n, ������

   public void forEntered(java.awt.event.MouseEvent e) { }
   public void forExited(java.awt.event.MouseEvent e) { }
   public void forPressed(java.awt.event.MouseEvent e) { }
   public void forReleased(java.awt.event.MouseEvent e) { }
   public void forClicked(java.awt.event.MouseEvent e) { }
   public void forMoved(java.awt.event.MouseEvent e) { }
   public void forDragged(java.awt.event.MouseEvent e) { 
       // �Y�i��ʫh�q�`�p�U
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