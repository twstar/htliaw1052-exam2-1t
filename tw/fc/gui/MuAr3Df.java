package tw.fc.gui;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
//import tw.fc.TxOStream;
//import static java.lang.Math.sqrt;

//[ mutable row-type affine matrix for 3D transformation
//[
public class MuAr3Df extends ImuAr3Df
{
   public     MuAr3Df(ImuM3Df baseMtx, ImuV3Df centerVec) {
      super(baseMtx,centerVec);
   }

   public     MuAr3Df(ImuV3Df b1, ImuV3Df b2, ImuV3Df b3, ImuV3Df c) {
      super(b1, b2, b3, c);
   }

   public     MuAr3Df(
      float b11, float b12, float b13,
      float b21, float b22, float b23,
      float b31, float b32, float b33,
      float c1,  float c2,  float c3
   ) {
      super(
         b11, b12, b13,
         b21, b22, b23,
         b31, b32, b33,
          c1,  c2,  c3
      );
   }

   public     MuAr3Df(ImuV3Df c) {
      super(c);
   }

   public     MuAr3Df(float c1, float c2, float c3) {
      super(c1, c2, c3);
   }

   public     MuAr3Df(ImuM3Df baseMtx) {
      super(baseMtx);
   }

   public     MuAr3Df(ImuV3Df b1, ImuV3Df b2, ImuV3Df b3) {
      super(b1, b2, b3);
   }

   public     MuAr3Df(
      float b11, float b12, float b13,
      float b21, float b22, float b23,
      float b31, float b32, float b33
   ) {
      super(
         b11, b12, b13,
         b21, b22, b23,
         b31, b32, b33
      );
   }

   public     MuAr3Df() {
      super();
   }

   public MuAr3Df(ImuAr3Df src) {  super(src);  }

   public MuV3Df vec() {  return super._vec;  }
   public MuM3Df mtx() {  return super._mtx;  }

//>>>>>>




}


