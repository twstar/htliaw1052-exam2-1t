package tw.fc ;
//import java.io.IOException;

//**********   OperatableI.java     ********************//
//
//   fundamental interface for generic objects
//

@Deprecated      // «ÝÀË°Q
public interface OperatableI<ImT, T> 
         extends DuplicableI<T>, PrintableI, SetableI<ImT>, ScannableI {

//[ inherit from super interfaces

// public void printTo(TxOStream ooo) ;
// public DuplicableI duplicate() ; // virtual clone
// public void scanFrom(TxStream iii) throws IOException ;
// public SetableI setBy(DuplicableI src);


}


