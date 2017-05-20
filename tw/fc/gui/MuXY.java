package tw.fc.gui;
import java.io.IOException;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;

//**********    MuXY.java    ****************
//
//  Integer pair to represent a screen position
//
public class MuXY extends ImuXY  
       implements SetableI<ImuXY>, ScannableI
{
//I   int x, y;   

   //------------------------------------------------------   
   public  MuXY() {  super(); }
   public  MuXY(int xx, int yy) {  super(xx,yy); }
   public  MuXY(double xx, double yy) {  super(xx,yy);   }
   public  MuXY(ImuXY src) {  super(src);  }
   // public  MuXY(ImuV2D v)  //: 用v.toXY()
   public  MuXY(java.awt.event.MouseEvent e) {  super(e);  }
   //------------------------------------------------------   

//I   public String toString() { return " ( ... }
//I   public final boolean equals(ImuXY v2) { ...  }

   public final MuXY setBy(ImuXY src) {  x=src.x;  y=src.y; return this; }
   public final MuXY setBy(int xx, int yy) {  x=xx;  y=yy; return this; }
   public final MuXY setBy(double xx, double yy) {  
      x=(int)(Math.round(xx));  y=(int)(Math.round(yy));  
      return this; 
   }
   public final MuXY setBy(ImuV2D src) {  
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

   public final MuXY setBy(java.awt.event.MouseEvent e) { 
      x=e.getX();  y=e.getY();  return this; 
   }
   
//I   public final DuplicableI duplicate() {  return new MuXY(this);   }
 
/////   public final SetableI setBy(DuplicableI s) {
/////      return setBy((ImuXY)s);
/////   }

   public final void scanFrom(TxIStream iii) 
   throws IOException {
      iii.skipWS().expect('(').skipWS();
      x=iii.get_int(iii.getRadix());
      iii.skipWS().expect(',').skipWS();
      y=iii.get_int(iii.getRadix());
      iii.skipWS().expect(')');
   }

   public static MuXY parseXY(String s) {
//      try {
      TxIStrStream inputS=new TxIStrStream(s);
      final MuXY x=new MuXY();  
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   } 

//I   public final ImuXY add(ImuXY v2) {  ...  }
//I   public final ImuXY add(int x2, int y2) {  ...  }
//I   public final ImuXY sub(ImuXY v2) {  ...   }
//I   public final ImuXY sub(int x2, int y2) {  ...  }
   
   public final MuXY addBy(ImuXY v2) {  x+=v2.x;  y+=v2.y;  return this; }
   public final MuXY subBy(ImuXY v2) {  x-=v2.x;  y-=v2.y;  return this; }
   //[ 此型常可少new一個垃圾
   public final MuXY addBy(int dx, int dy){  x+=dx;  y+=dy;  return this; }
   public final MuXY subBy(int dx, int dy){  x-=dx;  y-=dy;  return this; }
   //[ 由數學平面映到content pane時會用到.
   public final MuXY addBy(double dx, double dy) {
      x+=(int)(Math.round(dx)); y+=(int)(Math.round(dy)); 
      return this;
   }
   public final MuXY subBy(double dx, double dy) {
      x-=(int)(Math.round(dx)); y-=(int)(Math.round(dy)); 
      return this;
   }
   public final MuXY addBy(ImuV2D d) {  return addBy(d.x, d.y);  }
   public final MuXY subBy(ImuV2D d) {  return subBy(d.x, d.y);  }
   public final MuXY setByAdd(ImuXY v1, ImuXY v2) {
      this.setBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuXY setBySub(ImuXY v1, ImuXY v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuXY addByAdd(ImuXY v1, ImuXY v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuXY addBySub(ImuXY v1, ImuXY v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuXY subByAdd(ImuXY v1, ImuXY v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuXY subBySub(ImuXY v1, ImuXY v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }

   public final MuXY xAddBy(int dx) {  x+=dx;  return this; }
   public final MuXY yAddBy(int dy) {  y+=dy;  return this; }
   public final MuXY xSubBy(int dx) {  x-=dx;  return this; }
   public final MuXY ySubBy(int dy) {  y-=dy;  return this; }

//I   public final ImuXY neg() {  ...  }
   public final MuXY negate() {  x=-x; y=-y;  return this;  }
   public final MuXY xNegate() {  x=-x;  return this;  }
   public final MuXY yNegate() {  y=-y;  return this;  }
   public final MuXY setByNeg(ImuXY v) {  setBy(-v.x,-v.y);  return this;  }
   public final MuXY setByXNeg(ImuXY v) {  setBy(-v.x,v.y);  return this;  }
   public final MuXY setByYNeg(ImuXY v) {  setBy(v.x,-v.y);  return this;  }

//I  public final ImuXY smul(int k) {  ...  }
   public final MuXY mulBy(int k) {  x*=k;  y*=k;  return this; }
@Deprecated
public final MuXY smulBy(int k) {  x*=k;  y*=k;  return this; }
   public final MuXY mulBy(double k) {  
      return this.setBy(x*k, y*k); 
   }

   public final MuXY setByMul(int k, ImuXY v) {  
      setBy(k*v.x,k*v.y);  return this;  
   }
   public final MuXY setByMul(ImuXY v, int k) {  
      setBy(k*v.x,k*v.y);  return this;  
   }
@Deprecated
public final MuXY setBySMul(int k, ImuXY v) {  
      setBy(k*v.x,k*v.y);  return this;  
}
@Deprecated
public final MuXY setBySMul(ImuXY v, int k) {  
      setBy(k*v.x,k*v.y);  return this;  
}
   public final MuXY addByMul(int k, ImuXY v) {  
      addBy(k*v.x,k*v.y);  return this;  
   }
   public final MuXY addByMul(ImuXY v, int k) {  
      addBy(k*v.x,k*v.y);  return this;  
   }
   public final MuXY subByMul(int k, ImuXY v) {  
      subBy(k*v.x,k*v.y);  return this;  
   }
   public final MuXY subByMul(ImuXY v, int k) {  
      subBy(k*v.x,k*v.y);  return this;  
   }

//I   public final int dotmul(ImuXY v2) {  ...  }
//I   public final int wedgemul(ImuXY v2) {  ...  }

//I   public int normSquare() {  ...   }
//I   public final double norm() {  ...  }


   public final MuXY setByLinearCombination(
      int a1, ImuXY v1, int a2, ImuXY v2
   ){ //:  this:=(a1*v1+a2*v2)
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuXY setByLinearCombination(
      int a1, ImuXY v1, int a2, ImuXY v2, int a3, ImuXY v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }

//   public final MuXY setByLinePoint(ImuXY p1, ImuXY p2, double t) {
//      return this.setByLinearCombination(1-t, p1, t, p2);
//   }
//   public final MuXY setByMidPoint(ImuXY p1, ImuXY p2) {  
//      return this.setByAdd(p1,p2).mulBy(0.5);  
//   } 

}
