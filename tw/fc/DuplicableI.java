package tw.fc ;

//**********   DuplicableI.java     ********************//
//
//   fundamental interface for generic objects
//

public interface DuplicableI<T> {

   public T duplicate() ; // virtual clone
//>>>>> «Ý­×
   //:  To enable virtual clone so that generic algorithms can work.
   //:
   //:  Ex.   
   //:       static void genericSwap(OperatableI o1, OperatableI o2) { 
   //:          OperatableI temp=o1.duplicate();
   //:          o1.setBy(o2);  o2.setBy(temp);
   //:       }
   //:       static void swapExample(
   //:          TwInt i1, TwInt i2,  TwV2D v1, TwV2D v2
   //:       ) {
   //:          genericSwap(i1,i2);   genericSwap(v1,v2);
   //:       }
   //:

}

//  ÂÂª©¯d°Ñ
//public interface DuplicableI {
//
//   public DuplicableI duplicate() ; // virtual clone
//   //:  To enable virtual clone so that generic algorithms can work.
//   //:
//   //:  Ex.   
//   //:       static void genericSwap(OperatableI o1, OperatableI o2) { 
//   //:          OperatableI temp=o1.duplicate();
//   //:          o1.setBy(o2);  o2.setBy(temp);
//   //:       }
//   //:       static void swapExample(
//   //:          TwInt i1, TwInt i2,  TwV2D v1, TwV2D v2
//   //:       ) {
//   //:          genericSwap(i1,i2);   genericSwap(v1,v2);
//   //:       }
//   //:
//
//}


