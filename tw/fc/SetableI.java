package tw.fc ;

//**********   SetableI.java     ********************//
//
//   fundamental interface for generic objects
//
public  interface  SetableI<T> {

/////   public SetableI setBy(SetableI src); //: ���~! �󥿩�930612 2330
/////   public SetableI setBy(DuplicableI src); //: ��}��950212
   public SetableI<T> setBy(T src);
   //:  To simulate the assignment of object-contents

}

