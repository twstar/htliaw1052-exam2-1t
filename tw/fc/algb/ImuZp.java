package tw.fc.algb ;
import tw.fc.*;

//**********    ImuZp.java    ****************
//
//  integer modulo p
//

public interface ImuZp 
   extends MutingGuardI, DuplicableI<MuZp>, PrintableI, WidthPrintableI
{

   //------------------------------------------------------   
   public abstract int to_int() ;
   public abstract int getModulo() ;

   public abstract boolean equals(ImuZp v2) ;
  
   public abstract MuZp duplicate() ;

   public abstract ImuZp add(ImuZp v2) ;  
   public abstract ImuZp sub(ImuZp v2) ;  
   public abstract ImuZp mul(ImuZp v2) ;  
   public abstract ImuZp div(ImuZp v2) ;  
   public abstract ImuZp neg() ;
   public abstract ImuZp inv() ; 
   public abstract ImuZp pow(int n) ;   
 
}
