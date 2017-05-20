package tw.fc.gui;

import java.io.IOException;

import tw.fc.ScannableI;
import tw.fc.SetableI;
import tw.fc.TxIStrStream;
import tw.fc.TxIStream;

//A21112 xie add
public class MuP2Df extends ImuP2Df
implements SetableI<ImuP2Df>, ScannableI {

   public MuP2Df(){ super(1f, 0f); }
   public MuP2Df(float r, float a){ super(r, a); }
   public MuP2Df(float a){ super(1f, a); }
   public MuP2Df(ImuP2Df src) { super(src); }
   public MuP2Df(ImuV2D src) { super(src); }

   //---------------------------------------------------
   public final MuP2Df radSetBy(float r) { radius=r;  return this; }
   public final MuP2Df lonSetBy(float a) { angle=a; return this; }

   public final MuP2Df setBy(float r, float a) {
      radius=r;  angle=a;  return this; 
   }
   public final MuP2Df setBy(float a) { 
      return setBy(1f, a); 
   }

   public final MuP2Df setBy(ImuP2Df v2) {
      radius=v2.radius; angle=v2.angle;  
      return this;
   }

   @Override public MuP2Df setBy(ImuV2D src) { //: superªºsetBy ¤£ public
      super.setBy(src);  return this; 
   }

   // @Override
   // public SetableI<ImuP2Df> setBy(ImuP2Df v) {
   //       this.setBy(v.radius, v.angle+Math.PI); // 20100715 modify by Thomas
   //       return this;
   // }

   @Override
   public void scanFrom(TxIStream iii) throws IOException {
      //[ key in as the form:  (r;f)
      iii.skipWS().expect('(').skipWS();
      radius=iii.get_float();
      iii.skipWS().expect(';').skipWS();
      angle=iii.get_float();
      iii.skipWS().expect(')');      
   }

   public static MuP2Df parseP2D(String s) {
      //       try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuP2Df x=new MuP2Df();
      inputS.g(x);
      return x;
      //       }catch (IOException xpt) {
      //          throw new TxInputException(xpt.toString());
      //       }
   }

   public final MuP2Df radAddBy(float dx) {  radius+=dx;  return this; }
   public final MuP2Df lonAddBy(float dx) {  angle+=dx;  return this; }

   public final MuP2Df radSubBy(float dx) {  radius-=dx;  return this; }
   public final MuP2Df lonSubBy(float dx) {  angle-=dx;  return this; }

   //      public final MuP3D negate() {
   //         longitude+=Math.PI;  latitude=-latitude;  return this;
   //      }
   //
   //      public final MuP3D setByNeg(ImuP3D v) {
   //         //////this.setBy(v.radius, v.longitude+Math.PI, v.latitude);
   //         this.setBy(v.radius, v.longitude+Math.PI, -v.latitude); // 20100715 modify by Thomas
   //         return this;
   //      }


   public final MuP2Df sMulBy(float k) {  radius*=k;  return this; }

   public final MuP2Df setBySMul(float k, ImuP2Df v) {
      this.setBy(k*v.radius, v.angle);  return this;
   }

   public final MuP2Df setBySMul(ImuP2Df v, float k) {
      this.setBy(k*v.radius, v.angle);  return this;
   }

   public final MuP2Df normalize() {  radius=1f; return this; }

   public final MuP2Df setByLonAdd(ImuP2Df v, float angle) {
      this.setBy( v.radius, v.angle+angle);
      return this;
   }

   public final MuP2Df setByLonSub(ImuP2Df v, float angle) {
      this.setBy( v.radius, v.angle-angle);
      return this;
   }

   public final MuP2Df setByLatSub(ImuP2Df v, float angle) {
      this.setBy( v.radius, v.angle);
      return this;
   }   

}
