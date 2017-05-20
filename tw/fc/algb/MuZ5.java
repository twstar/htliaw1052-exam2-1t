package tw.fc.algb ;
import tw.fc.*;

public class MuZ5 extends MuZp 
                  implements ImuZ5
     //                      , SetableI<ImuZ5>
         //: 錯誤訊息待究
         //:  tw.fc.SetableI cannot be inherited with  
         //:  different arguments: <tw.fc.algb.ImuZ5> and <tw.fc.algb.ImuZp>                                  

{

   //-----------------------------------------
   public  MuZ5(int a) {  super(5, a); }
   public  MuZ5() {  super(5, 0); }
   public  MuZ5(ImuZ5 X) {  super(5, X.to_int());  }

   //-----------------------------------------
   public final MuZ5 duplicate() {  
      return new MuZ5(this);   
   }
   public boolean equals(ImuZ5 v2) { return (value==v2.to_int());  }

   public final MuZ5 setBy(ImuZ5 src) {  value=src.to_int();  return this; }

   public final MuZ5 setBy(int a) {  return (MuZ5)super.setBy(a);  }

   public int getModulo() {  return 5; }
   public int to_int() {  return value; }

   public final ImuZ5 add(ImuZ5 v2) {  
      return (MuZ5)super.add((MuZ5)v2);    
   }



   //[  1.4不行, 要用java5.0
   public final MuZ5 negate() {  
      return (MuZ5)super.negate(); 
   }

// >>>>>>>

}