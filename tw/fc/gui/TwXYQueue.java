/*

package tw.fc.gui;
   import tw.fc.*;

//************   TwXYQueue    *****************
//
// �M����TwXY�i�X��queue 
//
public class TwXYQueue extends TwQueue {

// public static int report() //: �u��super��

   //----------------------------------------------
   public                TwXYQueue() {   }
   //---------------
 
// final public boolean  isEmpty() //: �u��super��
// final public boolean  notEmpty()//: �u��super��

   public void  push(TwXY p) {  pushObj(p);   }
   public TwXY  pop() {  return (TwXY)popObj();  }
   public TwXY  peek() {  return (TwXY)peekObj();  }

// public final void clear()  //: �u��super��
// public final String toString() //: �u��super��
// public final int  length()  //: �u��super��
// public Object[] toObjArr() //: �u��super��
   public Object[] toObjArr() { //: �T��
      throw new RuntimeException("Please use toArray");
   }
   public TwXY[] toArray() { 
      Object[] t=super.toObjArr();
      TwXY[] dst=new TwXY[_length];
      for(int i=0; i<_length; i++){
         dst[i]=(TwXY)t[i];
      } 
      return dst;
   }

}


*/