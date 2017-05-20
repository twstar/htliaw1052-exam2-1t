package tw.fc;

public interface ImuFacList extends DuplicableI<MuFacList>, PrintableI {

   public MuFacList duplicate() ;
//             public boolean equals(ImuFacList) ;
   public long longValue() ;
   public MuRtn value() ;
   public int size() ;
   public boolean isOne() ;
   public boolean isInteger() ;
   public boolean isPrime() ;
   public ImuFacList mul(ImuFacList L2) ;
   public ImuFacList mul(long L) ;
   public ImuFacList div(ImuFacList L2) ;
   public ImuFacList div(long L) ;
   public ImuFacList inverse() ;
   public ImuFacList power(int e) ;
   public ImuFacList gcd(ImuFacList I2) ;  //: ���z�Ƥ��A��
   public ImuFacList lcm(ImuFacList I2) ;  //: ���z�Ƥ��A��

   public boolean equals(ImuFacList L2) ;
   public boolean equals(Object L2) ;

} 