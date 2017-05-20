//  ���礤
//
//  mathematical structure field
//  not the field of a class
//
public interface FieldI {
   // static FieldI getZero(); ����static
   FieldI neg();
   void negate();
   // static FieldI getOne(); ����static
   boolean isZero();
   boolean notZero();
   boolean invertible();
   FieldI inv(); 
   void invert();
}