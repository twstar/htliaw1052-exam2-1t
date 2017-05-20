/*

package tw.fc.gui;
   import tw.fc.*;

//************   TwXYQueue    *****************
//
// 專門讓TwXY進出的queue 
//
public class TwXYQueue extends TwQueue {

// public static int report() //: 沿用super的

   //----------------------------------------------
   public                TwXYQueue() {   }
   //---------------
 
// final public boolean  isEmpty() //: 沿用super的
// final public boolean  notEmpty()//: 沿用super的

   public void  push(TwXY p) {  pushObj(p);   }
   public TwXY  pop() {  return (TwXY)popObj();  }
   public TwXY  peek() {  return (TwXY)peekObj();  }

// public final void clear()  //: 沿用super的
// public final String toString() //: 沿用super的
// public final int  length()  //: 沿用super的
// public Object[] toObjArr() //: 沿用super的
   public Object[] toObjArr() { //: 禁用
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