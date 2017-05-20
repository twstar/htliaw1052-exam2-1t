//  實驗中
//
//  mathematical structure field
//  not the field of a class
//
public interface FieldI {
   // static FieldI getZero(); 不能static
   FieldI neg();
   void negate();
   // static FieldI getOne(); 不能static
   boolean isZero();
   boolean notZero();
   boolean invertible();
   FieldI inv(); 
   void invert();
}