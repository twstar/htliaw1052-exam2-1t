package tw.fc.gui;
import java.io.IOException;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;

//**********    MuV2Di.java    ****************
//
//  Integer pair to represent a screen position
//
public class MuV2Di extends ImuV2Di  
       implements SetableI<ImuV2Di>, ScannableI
{
//I   int x, y;   

   //------------------------------------------------------   
   public  MuV2Di() {  super(); }
   public  MuV2Di(int xx, int yy) {  super(xx,yy); }
   public  MuV2Di(double xx, double yy) {  super(xx,yy);   }
   public  MuV2Di(ImuV2Di src) {  super(src);  }
   // public  MuV2Di(ImuV2D v)  //: 用v.toXY()
   public  MuV2Di(java.awt.event.MouseEvent e) {  super(e);  }
   //------------------------------------------------------   

//I   public String toString() { return " ( ... }
//I   public final boolean equals(ImuV2Di v2) { ...  }

   public final MuV2Di setBy(ImuV2Di src) {  x=src.x;  y=src.y; return this; }
   public final MuV2Di setBy(int xx, int yy) {  x=xx;  y=yy; return this; }
   public final MuV2Di setBy(double xx, double yy) {  
      x=(int)(Math.round(xx));  y=(int)(Math.round(yy));  
      return this; 
   }
   public final MuV2Di setBy(ImuV2D src) {  
      return this.setBy(src.x, src.y);
   }
   public final void xSetBy(int v) {  x=v;  }  //: 原setX
   public final void ySetBy(int v) {  y=v;  }  //: 原setY
   //[ A5.039L.K add for iterating
   public final void compSetBy(int axis, int val) { 
      switch(axis) {
      case 0:  x=val;   break;
      case 1:  y=val;   break;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

   public final MuV2Di setBy(java.awt.event.MouseEvent e) { 
      x=e.getX();  y=e.getY();  return this; 
   }
   
//I   public final DuplicableI duplicate() {  return new MuV2Di(this);   }
 
/////   public final SetableI setBy(DuplicableI s) {
/////      return setBy((ImuV2Di)s);
/////   }

   public final void scanFrom(TxIStream iii) 
   throws IOException {
      iii.skipWS().expect('(').skipWS();
      x=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      y=iii.get_int(iii.getRadix());
      iii.skipWS().expect(')');
   }

   public static MuV2Di parseXY(String s) {
//      try {
      TxIStrStream inputS=new TxIStrStream(s);
      final MuV2Di x=new MuV2Di();  
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   } 

//I   public final ImuV2Di add(ImuV2Di v2) {  ...  }
//I   public final ImuV2Di add(int x2, int y2) {  ...  }
//I   public final ImuV2Di sub(ImuV2Di v2) {  ...   }
//I   public final ImuV2Di sub(int x2, int y2) {  ...  }
   
   public final MuV2Di addBy(ImuV2Di v2) {  x+=v2.x;  y+=v2.y;  return this; }
   public final MuV2Di subBy(ImuV2Di v2) {  x-=v2.x;  y-=v2.y;  return this; }
   //[ 此型常可少new一個垃圾
   public final MuV2Di addBy(int dx, int dy){  x+=dx;  y+=dy;  return this; }
   public final MuV2Di subBy(int dx, int dy){  x-=dx;  y-=dy;  return this; }
   //[ 由數學平面映到content pane時會用到.
   public final MuV2Di addBy(double dx, double dy) {
      x+=(int)(Math.round(dx)); y+=(int)(Math.round(dy)); 
      return this;
   }
   public final MuV2Di subBy(double dx, double dy) {
      x-=(int)(Math.round(dx)); y-=(int)(Math.round(dy)); 
      return this;
   }
   public final MuV2Di addBy(ImuV2D d) {  return addBy(d.x, d.y);  }
   public final MuV2Di subBy(ImuV2D d) {  return subBy(d.x, d.y);  }
   public final MuV2Di setByAdd(ImuV2Di v1, ImuV2Di v2) {
      this.setBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuV2Di setBySub(ImuV2Di v1, ImuV2Di v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuV2Di addByAdd(ImuV2Di v1, ImuV2Di v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuV2Di addBySub(ImuV2Di v1, ImuV2Di v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuV2Di subByAdd(ImuV2Di v1, ImuV2Di v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuV2Di subBySub(ImuV2Di v1, ImuV2Di v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }

   public final MuV2Di xAddBy(int dx) {  x+=dx;  return this; }
   public final MuV2Di yAddBy(int dy) {  y+=dy;  return this; }
   public final MuV2Di xSubBy(int dx) {  x-=dx;  return this; }
   public final MuV2Di ySubBy(int dy) {  y-=dy;  return this; }

//I   public final ImuV2Di neg() {  ...  }
   public final MuV2Di negate() {  x=-x; y=-y;  return this;  }
   public final MuV2Di xNegate() {  x=-x;  return this;  }
   public final MuV2Di yNegate() {  y=-y;  return this;  }
   public final MuV2Di setByNeg(ImuV2Di v) {  setBy(-v.x,-v.y);  return this;  }
   public final MuV2Di setByXNeg(ImuV2Di v) {  setBy(-v.x,v.y);  return this;  }
   public final MuV2Di setByYNeg(ImuV2Di v) {  setBy(v.x,-v.y);  return this;  }

//I  public final ImuV2Di smul(int k) {  ...  }
   public final MuV2Di mulBy(int k) {  x*=k;  y*=k;  return this; }
@Deprecated
public final MuV2Di smulBy(int k) {  x*=k;  y*=k;  return this; }
   public final MuV2Di mulBy(double k) {  
      return this.setBy(x*k, y*k); 
   }

   public final MuV2Di setByMul(int k, ImuV2Di v) {  
      setBy(k*v.x,k*v.y);  return this;  
   }
   public final MuV2Di setByMul(ImuV2Di v, int k) {  
      setBy(k*v.x,k*v.y);  return this;  
   }
@Deprecated
public final MuV2Di setBySMul(int k, ImuV2Di v) {  
      setBy(k*v.x,k*v.y);  return this;  
}
@Deprecated
public final MuV2Di setBySMul(ImuV2Di v, int k) {  
      setBy(k*v.x,k*v.y);  return this;  
}
   public final MuV2Di addByMul(int k, ImuV2Di v) {  
      addBy(k*v.x,k*v.y);  return this;  
   }
   public final MuV2Di addByMul(ImuV2Di v, int k) {  
      addBy(k*v.x,k*v.y);  return this;  
   }
   public final MuV2Di subByMul(int k, ImuV2Di v) {  
      subBy(k*v.x,k*v.y);  return this;  
   }
   public final MuV2Di subByMul(ImuV2Di v, int k) {  
      subBy(k*v.x,k*v.y);  return this;  
   }

//I   public final int dotmul(ImuV2Di v2) {  ...  }
//I   public final int wedgemul(ImuV2Di v2) {  ...  }

//I   public int normSquare() {  ...   }
//I   public final double norm() {  ...  }


   public final MuV2Di setByLinearCombination(
      int a1, ImuV2Di v1, int a2, ImuV2Di v2
   ){ //:  this:=(a1*v1+a2*v2)
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Di setByLinearCombination(
      int a1, ImuV2Di v1, int a2, ImuV2Di v2, int a3, ImuV2Di v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }

//   public final MuV2Di setByLinePoint(ImuV2Di p1, ImuV2Di p2, double t) {
//      return this.setByLinearCombination(1-t, p1, t, p2);
//   }
//   public final MuV2Di setByMidPoint(ImuV2Di p1, ImuV2Di p2) {  
//      return this.setByAdd(p1,p2).mulBy(0.5);  
//   } 

}
