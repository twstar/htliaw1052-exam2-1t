// ���礤

public interface IntDomainI {
 //static IntDomainI getZero(); ����static
   IntDomainI neg();
   void negate();
 //static IntDomainI getOne();  ����static
   boolean isZero();
   boolean notZero();
   boolean invertible();
   IntDomainI inv(); 
   void invert();
}