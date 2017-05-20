// 實驗中

public interface IntDomainI {
 //static IntDomainI getZero(); 不能static
   IntDomainI neg();
   void negate();
 //static IntDomainI getOne();  不能static
   boolean isZero();
   boolean notZero();
   boolean invertible();
   IntDomainI inv(); 
   void invert();
}