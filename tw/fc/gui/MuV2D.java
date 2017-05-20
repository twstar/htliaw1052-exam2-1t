package tw.fc.gui;
import java.io.IOException;
import tw.fc.SetableI;
import tw.fc.ScannableI;
//import tw.fc.DuplicableI;
//import tw.fc.PrintableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
//import tw.fc.TxInputException;
import tw.fc.Std;
import tw.fc.EpsilonException;
//import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
//import static java.lang.Math.round;

//
//  double precision floating-point 2D vector 
//
//**********************************
public class MuV2D extends ImuV2D 
             implements SetableI<ImuV2D>, ScannableI
{
   private static final long serialVersionUID = 2014120812L;
//I   double x, y;  

   //------------------------------------------------   
   public   MuV2D() {  super(); }
   public   MuV2D(double xx, double yy) {  super(xx,yy);  }
   public   MuV2D(ImuV2D src) {  super(src);  }
   public   MuV2D(ImuV2Df src) {  super(src);  }
   public   MuV2D(ImuV2Di src) {  super(src);  }
   public   MuV2D(ImuXY src) {  super(src);  }
//   public     MuV2D(ImuP2D src) {  super(src); } //: 極座標轉正交座標

   public   MuV2D(java.awt.event.MouseEvent e) {  super(e); } 

   //-----------------------------------------------
//I   public MuV2Df toV2Df()   
//I   public MuV2Di toV2Di()   
//I   public MuXY toXY()   

//I   public String toString() {  ...  }
//I   public final boolean equals(MuV2D v2) {  ...  }
//I   public final boolean equals(MuV2D v2, double epsilon) {  ...  }
//I   public final DuplicableI duplicate() {  ...  }

   public final MuV2D setBy(ImuV2D src) { x=src.x;  y=src.y;  return this; }
   public final MuV2D setBy(ImuXY src) {  x=src.x;  y=src.y;  return this; }
//   public final MuV2D setBy(ImuP2D src) {  ....  return this; }
   public final MuV2D setBy(double xx, double yy) {  
      x=xx;  y=yy;  return this; 
   }
// 不必另寫 setBy(float, float) 及 setBy(int,int)

   //: 原setX, setY
   public final MuV2D xSetBy(double val) {  x=val; return this;  }  
   public final MuV2D ySetBy(double val) {  y=val; return this;  }  
   //[ A5.039L.K add for iterating
   public final void compSetBy(int axis, double val) { 
      switch(axis) {
      case 0:  x=val;   break;
      case 1:  y=val;   break;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }


   public final MuV2D setByPolar(double radius, double angle) {
      x=radius*cos(angle);  y=radius*sin(angle);  return this;
   }

//   public final SetableI setBy(DuplicableI s) {
//      return setBy((ImuV2D)s);
//   }
   public final MuV2D setBy(java.awt.event.MouseEvent e) { 
      x=e.getX();  y=e.getY();  return this; 
   }

   public void scanFrom(TxIStream iii) throws IOException {
      iii.skipWS().expect('(').skipWS();
      this.x=iii.get_double();  
      iii.skipWS().expect(',').skipWS();
      this.y=iii.get_double();  
      iii.skipWS().expect(')');
   }

   public static MuV2D parseV2D(String s) {
//      try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuV2D x=new MuV2D();  
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   } 

   //-------------------------------------   
   
//I   public final ImuV2D add(ImuV2D v2) {  ...  }
//I   public final ImuV2D sub(ImuV2D v2) {  ...  }
   public final MuV2D addBy(ImuV2D v2) {  x+=v2.x;  y+=v2.y;  return this; }
   public final MuV2D addBy(ImuXY v2)  {  x+=v2.x;  y+=v2.y;  return this; }
   public final MuV2D addBy(double dx, double dy) {  
      x+=dx;  y+=dy;  return this; 
   }
// 不必另寫 addBy(float, float) 及 addBy(int, int)
   public final MuV2D subBy(ImuV2D v2) {  x-=v2.x;  y-=v2.y;  return this; }
   public final MuV2D subBy(ImuXY v2)  {  x-=v2.x;  y-=v2.y;  return this; }
   public final MuV2D subBy(double dx, double dy){
      x-=dx;  y-=dy;  return this; 
   }
   public final MuV2D setByAdd(ImuV2D v1, ImuV2D v2) {
   // this.setBy(v1).addBy(v2);  return this;  
         //: Terrable Bug!! 若(this==v2)就會錯掉
      this.setBy(v1.x+v2.x, v1.y+v2.y);  return this;  
   }
   public final MuV2D addByAdd(ImuV2D v1, ImuV2D v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y);  return this;  
   }
   public final MuV2D subByAdd(ImuV2D v1, ImuV2D v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y);  return this;  
   }
   public final MuV2D setBySub(ImuV2D v1, ImuV2D v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y);  return this;  
   }
   public final MuV2D addBySub(ImuV2D v1, ImuV2D v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y);  return this;  
   }
   public final MuV2D subBySub(ImuV2D v1, ImuV2D v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y);  return this;  
   }
   public final MuV2D xAddBy(double dx) {  x+=dx;  return this; }
   public final MuV2D xSubBy(double dx) {  x-=dx;  return this; }
   public final MuV2D yAddBy(double dy) {  y+=dy;  return this; }
   public final MuV2D ySubBy(double dy) {  y-=dy;  return this; }

//I   public final ImuV2D neg() {  ...  }
   public final MuV2D negate() {  x=-x; y=-y;  return this;  }
   public final MuV2D xNegate() {  x=-x;  return this;  }
   public final MuV2D yNegate() {  y=-y;  return this;  }
   public final MuV2D setByNeg(ImuV2D v) { return this.setBy(-v.x, -v.y); }
// addByNeg用 subBy即可, subByNeg用addBy即可
   public final MuV2D setByXNeg(ImuV2D v) { return this.setBy(-v.x, v.y); }
   public final MuV2D setByYNeg(ImuV2D v) { return this.setBy(v.x, -v.y); }

//I   public final ImuV2D mul(double k) {  ...  }
   public final MuV2D mulBy(double k) {  x*=k;  y*=k;  return this; }
@Deprecated
public final MuV2D smulBy(double k) {  x*=k;  y*=k;  return this; }
@Deprecated
public final MuV2D sMulBy(double k) {  x*=k;  y*=k;  return this; }
   public final MuV2D divBy(double k) throws EpsilonException {   
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      x/=k;  y/=k;  return this; 
   }

   public final MuV2D setByMul(double k, ImuV2D v) {  
      return this.setBy(k*v.x, k*v.y); 
   }
   public final MuV2D setByMul(ImuV2D v, double k) {  
      return this.setBy(k*v.x, k*v.y); 
   }
   public final MuV2D setByDiv(ImuV2D v, double k) throws EpsilonException {     
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      return this.setBy(v.x/k, v.y/k);  
   }
@Deprecated
public final MuV2D setBySMul(double k, ImuV2D v) {  
      return this.setBy(k*v.x, k*v.y); 
}
@Deprecated
public final MuV2D setBySMul(ImuV2D v, double k) {  
      return this.setBy(k*v.x, k*v.y); 
}

   public final MuV2D addByMul(ImuV2D v, double k) {  
      return this.addBy(k*v.x, k*v.y); 
   }
   public final MuV2D addByMul(double k, ImuV2D v) {  
      return this.addBy(k*v.x, k*v.y); 
   }
   public final MuV2D addByDiv(ImuV2D v, double k) throws EpsilonException {    
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      return this.addBy(v.x/k, v.y/k); 
   }
@Deprecated
public final MuV2D addBySMul(ImuV2D v, double k) {  
      this.addBy(k*v.x, k*v.y); return this; 
}
@Deprecated
public final MuV2D addBySMul(double k, ImuV2D v) {  
      this.addBy(k*v.x, k*v.y); return this; 
}

   public final MuV2D subByMul(ImuV2D v, double k) {  
      return this.subBy(k*v.x, k*v.y); 
   }
   public final MuV2D subByMul(double k, ImuV2D v) {  
      return this.subBy(k*v.x, k*v.y); 
   }
   public final MuV2D subByDiv(ImuV2D v, double k) throws EpsilonException {    
      if(Math.abs(k)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("divided by epsilonSq");
         new EpsilonException(k, "divided by epsilonSq");
      return this.subBy(v.x/k, v.y/k); 
   }
@Deprecated
public final MuV2D subBySMul(ImuV2D v, double k) {  
      return this.subBy(k*v.x, k*v.y);  
}
@Deprecated
public final MuV2D subBySMul(double k, ImuV2D v) {  
      return this.subBy(k*v.x, k*v.y); 
}

//I   public final double dot(ImuV2D v2) {  ...  }
//I   public final double wedgemul(ImuV2D v2) {  ...  }

//I   public final double norm() {  ...  }
//I   public final double normSquare() {  ...  }
//I   public final double distance(ImuV2D x) {  ...  }
   public final MuV2D normalize() throws EpsilonException {  
      final double n=this.norm();
      if(n<Std.epsilon()) {  throw 
          // new RuntimeException("attempt to normalize a very short vecter");
          new EpsilonException(n, "attempt to normalize a very short vecter");
      }
      if(n==1.0) {   return this;  }
      else {  this.divBy(n);   return this;   }
   }
   public final MuV2D setByDirection(ImuV2D v){  
   // setBy(v.direction()); 
      return this.setBy(v).normalize(); 
   }

   public final MuV2D setByLinearCombination(
      double a1,ImuV2D v1, double a2,ImuV2D v2
   ){ //:  this:=(a1*v1+a2*v2)
   //   return this.setBySMul(a1, v1).addBySMul(a2,v2);
              //: bug!! 若(v2==this)就會錯掉
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2D setByLinearCombination(
      double a1,double a2, ImuV2D v1,ImuV2D v2
   ){ //:  this:=(a1*v1+a2*v2)
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2D addByLinearCombination(
      double a1,ImuV2D v1, double a2,ImuV2D v2
   ){ //:  this+=(a1*v1+a2*v2)
      return this.addBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2D addByLinearCombination(
      double a1,double a2, ImuV2D v1,ImuV2D v2
   ){ //:  this+=(a1*v1+a2*v2)
      return this.addBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2D subByLinearCombination(
      double a1,ImuV2D v1, double a2,ImuV2D v2
   ){ //:  this-=(a1*v1+a2*v2)
      return this.subBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2D subByLinearCombination(
      double a1,double a2, ImuV2D v1,ImuV2D v2
   ){ //:  this-=(a1*v1+a2*v2)
      return this.subBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2D setByLinearCombination(
      double a1,ImuV2D v1, double a2,ImuV2D v2, double a3,ImuV2D v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! 若(v3==this)就會錯掉
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }
   public final MuV2D setByLinearCombination(
      double a1, double a2, double a3,
      ImuV2D v1, ImuV2D v2, ImuV2D v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! 若(v3==this)就會錯掉
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }
   public final MuV2D setByLinearCombination(
      double[] a, ImuV2D[] v
   ){  
      return this.setBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }

   public final MuV2D setByLinearCoef(ImuV2D v, ImuV2D v1, ImuV2D v2) 
   throws EpsilonException {  
      //  let  v=h*v1+k*v2, then: h=v^v2/v1^v2, k=v1^v/v1^v2
      final double D=v1.wedge(v2);
      if(Math.abs(D)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("the wedge is epsilonSq");
         new EpsilonException(D, "the wedge is epsilonSq");
         
      return this.setBy(v.wedge(v2)/D, v1.wedge(v)/D);
   }

   public final MuV2D addByLinearCombination(
      double a1,ImuV2D v1, double a2,ImuV2D v2, double a3,ImuV2D v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      return this.addBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2D addByLinearCombination(
      double a1, double a2, double a3,
      ImuV2D v1, ImuV2D v2, ImuV2D v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      return this.addBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2D addByLinearCombination(
      double[] a, ImuV2D[] v
   ){  
      return this.addBy(linearCombination(a,v));
                     //: 不能直接加, 怕this會等於某個v[i]
   }
   public final MuV2D subByLinearCombination(
      double a1,ImuV2D v1, double a2,ImuV2D v2, double a3,ImuV2D v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      return this.subBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2D subByLinearCombination(
      double a1, double a2, double a3,
      ImuV2D v1, ImuV2D v2, ImuV2D v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      return this.subBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2D subByLinearCombination(
      double[] a, ImuV2D[] v
   ){  
      return this.subBy(linearCombination(a,v));
   }

   //------
//I   public static final ImuV2D linePoint(ImuV2D p1, ImuV2D p2, double t) { ... }
   public final MuV2D setByLinePoint(ImuV2D p1, ImuV2D p2, double t) {
//      this.setByAdd(p1.mul(1-t),p2.mul(t)) ;  return this; 
      return this.setByLinearCombination(1-t, p1, t, p2);
   }
   public final MuV2D addByLinePoint(ImuV2D p1, ImuV2D p2, double t) {
//      this.setByAdd(p1.mul(1-t),p2.mul(t)) ;  return this; 
      return this.addByLinearCombination(1-t, p1, t, p2);
   }
   public final MuV2D subByLinePoint(ImuV2D p1, ImuV2D p2, double t) {
//      this.setByAdd(p1.mul(1-t),p2.mul(t)) ;  return this; 
      return this.subByLinearCombination(1-t, p1, t, p2);
   }

//I   public static final ImuV2D midPoint(ImuV2D p1, ImuV2D p2) { ... }
   public final MuV2D setByMidPoint(ImuV2D p1, ImuV2D p2) {  
      return this.setBy((p1.x+p2.x)*0.5,(p1.y+p2.y)*0.5);  
   } 
   public final MuV2D addByMidPoint(ImuV2D p1, ImuV2D p2) {  
      return this.addBy((p1.x+p2.x)*0.5,(p1.y+p2.y)*0.5);  
   } 
   public final MuV2D subByMidPoint(ImuV2D p1, ImuV2D p2) {  
      return this.subBy((p1.x+p2.x)*0.5,(p1.y+p2.y)*0.5);  
   } 

//I   public final ImuV2D proj(ImuV2D d) {  ...  }
//I   public final ImuV2D co_proj(ImuV2D d) {  ...  }
//I   public final ImuV2D refl(ImuV2D d) {  ...  }
//I   public final ImuV2D co_refl(ImuV2D d) {  ...  }
   public final MuV2D setByProj(ImuV2D v, ImuV2D d) {  
      //:  this:=v.proj(d), v project into direction d
      if(d.normSq()<Std.epsilonSq()) {  this.setBy(0d,0d); }
      else {  this.setByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }
   public final MuV2D projBy(ImuV2D d) { //: this:=this.proj(d)  
      return this.setByProj(this,d);
   }
   public final MuV2D addByProj(ImuV2D v, ImuV2D d) {  
      //:  project into direction d
      if(d.normSq()<Std.epsilonSq()) {  ;  }
      else {  this.addByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }
   public final MuV2D subByProj(ImuV2D v, ImuV2D d) {  
      //:  project into direction d
      if(d.normSq()<Std.epsilonSq()) {  ;  }
      else {  this.subByMul(v.dot(d)/d.dot(d), d);  }
      return this;
   }
   public final MuV2D setByCoProj(ImuV2D v, ImuV2D d) {  
      if(d.normSq()<Std.epsilonSq()) this.setBy(v);
      else this.setByLinearCombination(
         1.0,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV2D coProjBy(ImuV2D d) {
      return this.setByCoProj(this,d); 
   }  
   public final MuV2D addByCoProj(ImuV2D v, ImuV2D d) {  
      if(d.normSq()<Std.epsilonSq()) this.addBy(v);
      else this.addByLinearCombination(
         1.0,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV2D subByCoProj(ImuV2D v, ImuV2D d) {  
      if(d.normSq()<Std.epsilonSq()) this.subBy(v);
      else this.subByLinearCombination(
         1.0,v, -v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV2D setByRefl(ImuV2D v, ImuV2D d) { 
      if(d.normSq()<Std.epsilonSq()) this.setByNeg(v);
      else this.setByLinearCombination(
         2.0*v.dot(d)/d.dot(d),d, -1.0,v
      );
      return this;
   }
   public final MuV2D reflBy(ImuV2D d) { 
      return this.setByRefl(this, d);
   }
   public final MuV2D addByRefl(ImuV2D v, ImuV2D d) { 
      if(d.normSq()<Std.epsilonSq()) this.subBy(v);
      else this.addByLinearCombination(
         2.0*v.dot(d)/d.dot(d),d, -1.0,v
      );
      return this;
   }
   public final MuV2D subByRefl(ImuV2D v, ImuV2D d) { 
      if(d.normSq()<Std.epsilonSq()) this.addBy(v);
      else this.subByLinearCombination(
         2.0*v.dot(d)/d.dot(d),d, -1.0,v
      );
      return this;
   }
   public final MuV2D setByCoRefl(ImuV2D v, ImuV2D d) { 
      if(d.normSq()<Std.epsilonSq()) this.setBy(v);
      else this.setByLinearCombination(
         1.0,v, -2.0*v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV2D coReflBy(ImuV2D d) { 
      return this.setByCoRefl(this, d);
   }
   public final MuV2D addByCoRefl(ImuV2D v, ImuV2D d) { 
      if(d.normSq()<Std.epsilonSq()) this.addBy(v);
      else this.addByLinearCombination(
         1.0,v, -2.0*v.dot(d)/d.dot(d),d
      );
      return this;
   }
   public final MuV2D subByCoRefl(ImuV2D v, ImuV2D d) { 
      if(d.normSq()<Std.epsilonSq()) this.subBy(v);
      else this.subByLinearCombination(
         1.0,v, -2.0*v.dot(d)/d.dot(d),d
      );
      return this;
   }


   //----------------
//I   public final ImuV2D rot90() {  ...  }
   public final MuV2D rotBy90() {  
   //B  x=-y;  y=x;   return this;  //: terrible bug! x已先摧毀
      return this.setBy(-y,x);   
   }
   public final MuV2D setByRot90(ImuV2D v) {  
   //B  x=-v.y; y=v.x; return this; //: bug: 若(v==this)就會因順序而錯掉 
      return this.setBy(-v.y,v.x);    
   }
   public final MuV2D addByRot90(ImuV2D v) {  
      return this.addBy(-v.y,v.x); 
   }
   public final MuV2D subByRot90(ImuV2D v) {  
      return this.subBy(-v.y,v.x);     
   }

   public final MuV2D rotByNeg90() {  
      return this.setBy(y,-x);   
   }
   public final MuV2D setByRotNeg90(ImuV2D v) {  
      return this.setBy(v.y,-v.x);    
   }
   public final MuV2D addByRotNeg90(ImuV2D v) {  
      return this.addBy(v.y,-v.x);  
   }
   public final MuV2D subByRotNeg90(ImuV2D v) {  
      return this.subBy(v.y,-v.x);  
   }

//I   public final ImuV2D rot(double Angle) {  ...  }
//I   public final ImuV2D rot(double Angle, ImuV2D center) {  ...  }

   //[ this 設為 src轉角A的結果,現成的資料是cos(A),sin(A)而非A
   final MuV2D setByCSRot(ImuV2D src, double cosA, double sinA) {
   //: cosA*(src.x, src.y)+sinA*(-src.y, src.x)
      return this.setBy(
                    src.x*cosA-src.y*sinA, 
                    src.y*cosA+src.x*sinA
                  );
   }
   //[ this 轉角A, 現成的資料是cos(A),sin(A)而非A
   final MuV2D csRotBy(double cosA, double sinA) {
      return this.setByCSRot(this,cosA,sinA);
   }
   final MuV2D addByCSRot(ImuV2D v, double cosA, double sinA) {
      return this.addBy(v.x*cosA-v.y*sinA, v.x*sinA+v.y*cosA);
   }
   final MuV2D subByCSRot(ImuV2D v, double cosA, double sinA) {
      return this.subBy(v.x*cosA-v.y*sinA, v.x*sinA+v.y*cosA);
   }
   final MuV2D setByCSRot(
      ImuV2D v, double cosA, double sinA, ImuV2D center
   ) {
      return this.setBy(
         center.x+(v.x-center.x)*cosA-(v.y-center.y)*sinA,
         center.y+(v.x-center.x)*sinA+(v.y-center.y)*cosA
      );
   }
   final MuV2D csRotBy(
      double cosA, double sinA, ImuV2D center
   ) {
      return this.setByCSRot(this, cosA, sinA, center);
   }
   final MuV2D addByCSRot(
      ImuV2D v, double cosA, double sinA, ImuV2D center
   ) {
      return this.addBy(
         center.x+(v.x-center.x)*cosA-(v.y-center.y)*sinA,
         center.y+(v.x-center.x)*sinA+(v.y-center.y)*cosA
      );
   }
   final MuV2D subByCSRot(
      ImuV2D v, double cosA, double sinA, ImuV2D center
   ) {
      return this.subBy(
         center.x+(v.x-center.x)*cosA-(v.y-center.y)*sinA,
         center.y+(v.x-center.x)*sinA+(v.y-center.y)*cosA
      );
   }
   //----------
   //[ this 設為 src 轉角A的結果
   public final MuV2D setByRot(ImuV2D src, double A) {
      return this.setByCSRot(src,cos(A),sin(A));
   }
   //[ this 設為 src 轉角A的結果
   public final MuV2D rotBy(double A) {
      return this.setByCSRot(this,cos(A),sin(A));
   }
   //[ A21111 xie
   public final MuV2D xRotBy(double angle) { 
      return this.setByCSRot(this,cos(angle),sin(angle));
   }
   public final MuV2D yRotBy(double angle) { 
      return this.setByCSRot(this,sin(angle),cos(angle));
   }   
   //]
   
   public final MuV2D addByRot(ImuV2D v, double A) {
      return this.addByCSRot(v, cos(A), sin(A));
   }
   public final MuV2D subByRot(ImuV2D v, double A) {
      return this.subByCSRot(v, cos(A), sin(A));
   }
   //---
   public final MuV2D setByRot(ImuV2D v, double A, ImuV2D center) {
      return setByCSRot(v, cos(A), sin(A), center);
   }
   public final MuV2D rotBy(double A, ImuV2D center) {
      return this.setByCSRot(this, cos(A), sin(A), center);
   }
   public final MuV2D addByRot(ImuV2D v, double A, ImuV2D center) {
      return addByCSRot(v, cos(A), sin(A), center);
   }
   public final MuV2D subByRot(ImuV2D v, double A, ImuV2D center) {
      return subByCSRot(v, cos(A), sin(A), center);
   }

   public final MuV2D setBySinRot(ImuV2D v, double sinA) {
      //: 適用場合: 客戶現成的資料是sin(A)而非A
      //: -PI/2 <= A <= +PI/2, -1 <= sin(A) <= +1  
      final double cosA=sqrt(1-sinA*sinA);
      return setByCSRot(v, cosA, sinA);
   }
   public final MuV2D sinRotBy(double sinA) { 
      //: 適用場合: 正負銳角, 且現成的資料是sin(A)而非A
      //: -PI/2 <= A <= +PI/2, -1 <= sin(A) <= +1  
      final double cosA=sqrt(1-sinA*sinA);
      return setByCSRot(this, cosA, sinA);
   }
   public final MuV2D addBySinRot(ImuV2D v, double sinA) {
      //: 適用場合: 客戶現成的資料是sin(angle)而非angle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1  
      final double cosA=sqrt(1-sinA*sinA);
      return this.addByCSRot(v, cosA, sinA);
   }
   public final MuV2D subBySinRot(ImuV2D v, double sinA) {
      //: 適用場合: 客戶現成的資料是sin(angle)而非angle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1  
      final double cosA=sqrt(1-sinA*sinA);
      return this.subByCSRot(v, cosA, sinA);
   }
   public final MuV2D setBySinRot(ImuV2D v, double sinA, ImuV2D center) {
      final double cosA=sqrt(1-sinA*sinA);
      return setByCSRot(v, cosA, sinA, center);
   }
   public final MuV2D sinRotBy(double sinA, ImuV2D center) {
      final double cosA=sqrt(1-sinA*sinA);
      return setByCSRot(this, cosA, sinA, center);
   }
   public final MuV2D addBySinRot(ImuV2D v, double sinA, ImuV2D center) {
      final double cosA=sqrt(1-sinA*sinA);
      return addByCSRot(v, cosA, sinA, center);
   }
   public final MuV2D subBySinRot(ImuV2D v, double sinA, ImuV2D center) {
      final double cosA=sqrt(1-sinA*sinA);
      return subByCSRot(v, cosA, sinA, center);
   }

   //-------------------
   public final MuV2D solveLinearCoef(ImuV2D v, ImuV2D v1, ImuV2D v2) 
   throws EpsilonException {
      //  let  v=h*v1+k*v2,  then: h=v^v2/v1^v2, k=v1^v/v1^v2
      //  this.setBy(h,k)
      final double D=v1.wedge(v2);
      if(Math.abs(D)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("v1 adn v2 are near colinear");
         new EpsilonException(D, "v1 adn v2 are near colinear");
      return this.setBy(v.wedge(v2)/D, v1.wedge(v)/D);
   }

   //[  Suppose lines AB, and CD not parallel.
   //[  LET  E is the intersection of line AB and line CD
   //[  Let  <AE>=h<AB>, <CE>=k<CD>, 
   //[  THEN  h=<AC>^<CD>/<AB>^<CD>, k=<AC>^<AB>/<AB>^<CD>
   //[  solve and set (h,k) to this
   //[  Note: 檢查h,k可判定交點在哪一段.
   public final MuV2D solveIntersectPara(
      ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D
   )throws EpsilonException {   
      final double ABx=B.x-A.x, ABy=B.y-A.y,
                   CDx=D.x-C.x, CDy=D.y-C.y;
      final double w=ABx*CDy-ABy*CDx;
      if(Math.abs(w)<Std.epsilonSq()) throw 
        // new IllegalArgumentException("AB and CD are near parallel");
         new EpsilonException(w, "AB and CD are near parallel");
      final double ACx=C.x-A.x, ACy=C.y-A.y;
      return this.setBy((ACx*CDy-ACy*CDx)/w, (ACx*ABy-ACy*ABx)/w);
     //[ 向量版須動用 free store
     // final ImuV2D AB=B.sub(A), CD=D.sub(C);
     // final double w=AB.wedge(CD);
     // if(w<Std.epsilonSq()) throw new IllegalArgumentException("AB AND CD are parallel");
     // final ImuV2D CD=C.sub(A);  
     // return this.setBy(this.wedge(CD)/w, CD.wedge(AB)/w);
   }

   //[  Suppose lines AB, and CD not parallel.
   //[  Solve and set the intersection point E to this
   //[  E=A+<AE>=A+h<AB>, h=<AC>^<CD>/<AB>^<CD>
   public final MuV2D setByIntersect(
      ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D
   ) throws EpsilonException {   
      final double ABx=B.x-A.x, ABy=B.y-A.y, 
                   CDx=D.x-C.x, CDy=D.y-C.y;
      final double w=ABx*CDy-ABy*CDx; // w=AB.wedge(CD);
      if(Math.abs(w)<Std.epsilonSq()) throw 
         //new IllegalArgumentException("AB and CD are near parallel");
         new EpsilonException(w, "AB and CD are near parallel");
      final double ACx=C.x-A.x, ACy=C.y-A.y;  //: AC:=C-A
      final double h=(ACx*CDy-ACy*CDx)/w; //: h=AC.wedge(CD)/w
      this.setBy(A.x+h*ABx, A.y+h*ABy);
      return this;
//[ 向量版須動用free store
//    final ImuV2D AB=B.sub(A), CD=D.sub(C);
//    final double w=AB.wedge(CD);
//    if(w<Std.epsilonSq()) throw new IllegalArgumentException("the wedge is zero");
//    final ImuV2D AC=C.sub(A);
//    final double h=AC.wedge(CD)/w; //: h=AC.wedge(CD)/w
//    this.setBy(A).addByMul(h,AB);
//    return this;
   }

   // addByIntersect不合理, 因intersect是點而非向量

//I   public final double angle(ImuV2D till) {  ...  }
//I   public int winding(ImuV2D[] vertices) {  ...  }
//I   public ImuV2D linear_coef(ImuV2D v1, ImuV2D v2) {  ...  }
//I   public static ImuV2D intersect_para(ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D) {  ...  }
//I   public static ImuV2D intersect(ImuV2D A, ImuV2D B, ImuV2D C, ImuV2D D){  ...  }


   //[ ---- matrix ----------------------------
   public final MuV2D mulBy(ImuM2D m) { //  this*=m; 
      return this.setBy(
         this.x*m._r1.x+this.y*m._r2.x,
         this.x*m._r1.y+this.y*m._r2.y
      //   this.x*m._11+this.y*m._21,
      //   this.x*m._12+this.y*m._22
      );
   }
   //  m*=v 無意義: m*v是向量
   public final MuV2D rMulBy(ImuM2D m) {  // this*=m
      return mulBy(m); 
   }
   public final MuV2D swapMulBy(ImuM2D m) { //  this:= m*this 
      return this.setBy(
         m._r1.x*this.x+m._r1.y*this.y,
         m._r2.x*this.x+m._r2.y*this.y
      //   m._11*this.x+m._12*this.y,
      //   m._21*this.x+m._22*this.y
      );
   }
   public final MuV2D lMulBy(ImuM2D m) {  //  this:= m*this 
      return this.swapMulBy(m);
   }

   public final MuV2D setByMul(ImuV2D v, ImuM2D m) {  
      return this.setBy(
         v.x*m._r1.x + v.y*m._r2.x,
         v.x*m._r1.y + v.y*m._r2.y
      //   v.x*m._11 + v.y*m._21,
      //   v.x*m._12 + v.y*m._22
      );    
   }
   public final MuV2D setByMul(ImuM2D m, ImuV2D v) {  
      return this.setBy(
         m._r1.x*v.x+m._r1.y*v.y,
         m._r2.x*v.x+m._r2.y*v.y
      //   m._11*v.x+m._12*v.y,
      //   m._21*v.x+m._22*v.y
      );
   }

   public final MuV2D addByMul(ImuV2D v, ImuM2D m) {  
      return this.addBy(
         v.x*m._r1.x + v.y*m._r2.x ,
         v.x*m._r1.y + v.y*m._r2.y 
      //   v.x*m._11 + v.y*m._21 ,
      //   v.x*m._12 + v.y*m._22 
      );    
   }
   public final MuV2D addByMul(ImuM2D m, ImuV2D v) {  
      return this.addBy(
         m._r1.x*v.x+m._r1.y*v.y,
         m._r2.x*v.x+m._r2.y*v.y
      //   m._11*v.x+m._12*v.y,
      //   m._21*v.x+m._22*v.y
      );
   }

   public final MuV2D subByMul(ImuV2D v, ImuM2D m) {  
      return this.subBy(
         v.x*m._r1.x + v.y*m._r2.x ,
         v.x*m._r1.y + v.y*m._r2.y 
      //   v.x*m._11 + v.y*m._21 ,
      //   v.x*m._12 + v.y*m._22 
      );    
   }
   public final MuV2D subByMul(ImuM2D m, ImuV2D v) {  
      return this.subBy(
         m._r1.x*v.x+m._r1.y*v.y,
         m._r2.x*v.x+m._r2.y*v.y
      //   m._11*v.x+m._12*v.y,
      //   m._21*v.x+m._22*v.y
      );
   }
   //] ---- 矩陣

}
