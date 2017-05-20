package tw.fc.gui;

import java.io.IOException;
import tw.fc.SetableI;
import tw.fc.ScannableI;
import tw.fc.TxIStream;
import tw.fc.TxIStrStream;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

//**************    MuV2Df.java    ********************
//
//  floating-point 2D vector
//
public class MuV2Df extends ImuV2Df
             implements SetableI<ImuV2Df>, ScannableI
{
   private static final long serialVersionUID = 2014120813L;
//I   float x, y;

   //------------------------------------------------
   public   MuV2Df() {  super(); }
   public   MuV2Df(float xx, float yy) {  super(xx,yy);  }
   public   MuV2Df(ImuV2Df src) {  super(src);  }
//   public   MuV2Df(ImuV2Df src) {  super(src);  }
//   public   MuV2Df(ImuV2Di src) {  super(src);  }
   public   MuV2Df(ImuXY src) {  super(src);  }
//   public     MuV2Df(ImuP2D src) {  super(src); } //: ???y??????y??
   
 //[ convert-ctor
   public MuV2Df(ImuP2Df src) {  super(src); } //: ∑•Æyº–Ôı-?o?Æy¶Ï

   public   MuV2Df(java.awt.event.MouseEvent e) {  super(e); }

   //-----------------------------------------------
//I   public MuV2Df toV2Df()
//I   public MuV2Di toV2Di()
//I   public MuXY toXY()

//I   public String toString() {  ...  }
//I   public final boolean equals(MuV2Df v2) {  ...  }
//I   public final boolean equals(MuV2Df v2, float epsilon) {  ...  }
//I   public final DuplicableI duplicate() {  ...  }

   
   public final MuV2Df setBy(ImuV2Df src) { x=src.x;  y=src.y;  return this; }
   public final MuV2Df setBy(ImuXY src) {  x=src.x;  y=src.y;  return this; }
//   public final MuV2Df setBy(ImuP2D src) {  ....  return this; }
   public final MuV2Df setBy(float xx, float yy) {
      x=xx;  y=yy;  return this;
   }

// §£?|ºg setBy(float, float) ??setBy(int,int)

   public final MuV2Df xSetBy(float val) {  x=val; return this;  }
   public final MuV2Df ySetBy(float val) {  y=val; return this;  }
   //[ A5.039L.K add for iterating
   public final void compSetBy(int axis, float val) { 
      switch(axis) {
      case 0:  x=val;   break;
      case 1:  y=val;   break;
      default:  throw new IllegalArgumentException("illegal axis: "+axis);
      }
   }

   public final MuV2Df setByPolar(float radius, float angle) {                  //: ???y??????y??
      x=(float) (radius*cos(angle));  y=(float) (radius*sin(angle));  
      return this;
   }

//   public final SetableI setBy(DuplicableI s) {
//      return setBy((ImuV2Df)s);
//   }
   public final MuV2Df setBy(java.awt.event.MouseEvent e) {
      x=e.getX();  y=e.getY();  return this;
   }

   public void scanFrom(TxIStream iii) throws IOException {                       //: ??TwFC????J?\??
      iii.skipWS().expect('(').skipWS();
      this.x=iii.get_float();
      iii.skipWS().expect(',').skipWS();
      this.y=iii.get_float();
      iii.skipWS().expect(')');
   }

   public static MuV2Df parseV2D(String s) {                                       //: ???s?O?@?V?q ?‚Z:"(5,5)"
//      try {
      final TxIStrStream inputS=new TxIStrStream(s);
      final MuV2Df x=new MuV2Df();
      inputS.g(x);
      return x;
//      }catch (IOException xpt) {
//         throw new TxInputException(xpt.toString());
//      }
   }

   //-------------------------------------

//I   public final ImuV2Df add(ImuV2Df v2) {  ...  }
//I   public final ImuV2Df sub(ImuV2Df v2) {  ...  }
   public final MuV2Df addBy(ImuV2Df v2) { 
      x+=v2.x;  y+=v2.y;  
      return this; 
   }
   public final MuV2Df addBy(ImuXY v2)  { x+=v2.x;  y+=v2.y;  return this; }
   public final MuV2Df addBy(float dx, float dy) {
      x+=dx;  y+=dy;  return this;
   }
   
   //A20829 xie
   public final MuV2Df addBy(int dx, int dy) {
      x+=dx;  y+=dy;  return this;
   }
// ?????t?g addBy(float, float) ?? addBy(int, int)
   public final MuV2Df subBy(ImuV2Df v2) {  x-=v2.x;  y-=v2.y;  return this; }
   public final MuV2Df subBy(ImuXY v2)  {  x-=v2.x;  y-=v2.y;  return this; }
   public final MuV2Df subBy(float dx, float dy){
      x-=dx;  y-=dy;  return this;
   }
   public final MuV2Df setByAdd(ImuV2Df v1, ImuV2Df v2) {
   // this.setBy(v1).addBy(v2);  return this;
         //: Terrable Bug!! ?Y(this==v2)?N?|??        //: ?]???b this.setBy(v1)?B?J?? this???b?O???? this
      this.setBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuV2Df addByAdd(ImuV2Df v1, ImuV2Df v2) {
      this.addBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuV2Df subByAdd(ImuV2Df v1, ImuV2Df v2) {
      this.subBy(v1.x+v2.x, v1.y+v2.y);  return this;
   }
   public final MuV2Df setBySub(ImuV2Df v1, ImuV2Df v2) {
      this.setBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuV2Df addBySub(ImuV2Df v1, ImuV2Df v2) {
      this.addBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuV2Df subBySub(ImuV2Df v1, ImuV2Df v2) {
      this.subBy(v1.x-v2.x, v1.y-v2.y);  return this;
   }
   public final MuV2Df xAddBy(float dx) {  x+=dx;  return this; }
   public final MuV2Df xSubBy(float dx) {  x-=dx;  return this; }
   public final MuV2Df yAddBy(float dy) {  y+=dy;  return this; }
   public final MuV2Df ySubBy(float dy) {  y-=dy;  return this; }

//I   public final ImuV2Df neg() {  ...  }
   public final MuV2Df negate() {  x=-x; y=-y;  return this;  }
   public final MuV2Df xNegate() {  x=-x;  return this;  }
   public final MuV2Df yNegate() {  y=-y;  return this;  }
   public final MuV2Df setByNeg(ImuV2Df v) { return this.setBy(-v.x, -v.y); }
// addByNeg?? subBy?Y?i, subByNeg??addBy?Y?i     //: ?u?O?B????2
   public final MuV2Df setByXNeg(ImuV2Df v) { return this.setBy(-v.x, v.y); }
   public final MuV2Df setByYNeg(ImuV2Df v) { return this.setBy(v.x, -v.y); }

//I   public final ImuV2Df mul(float k) {  ...  }
   public final MuV2Df mulBy(float k) {  x*=k;  y*=k;  return this; }
@Deprecated
public final MuV2Df smulBy(float k) {  x*=k;  y*=k;  return this; }
@Deprecated
public final MuV2Df sMulBy(float k) {  x*=k;  y*=k;  return this; }
   public final MuV2Df divBy(float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      x/=k;  y/=k;  return this;
   }

   public final MuV2Df setByMul(float k, ImuV2Df v) {
      return this.setBy(k*v.x, k*v.y);
   }
   public final MuV2Df setByMul(ImuV2Df v, float k) {
      return this.setBy(k*v.x, k*v.y);
   }
   public final MuV2Df setByDiv(ImuV2Df v, float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      return this.setBy(v.x/k, v.y/k);
   }
@Deprecated
public final MuV2Df setBySMul(float k, ImuV2Df v) {
      return this.setBy(k*v.x, k*v.y);
}
@Deprecated
public final MuV2Df setBySMul(ImuV2Df v, float k) {
      return this.setBy(k*v.x, k*v.y);
}

   public final MuV2Df addByMul(ImuV2Df v, float k) {
      return this.addBy(k*v.x, k*v.y);
   }
   public final MuV2Df addByMul(float k, ImuV2Df v) {
      return this.addBy(k*v.x, k*v.y);
   }
   public final MuV2Df addByDiv(ImuV2Df v, float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      return this.addBy(v.x/k, v.y/k);
   }
@Deprecated
public final MuV2Df addBySMul(ImuV2Df v, float k) {
      this.addBy(k*v.x, k*v.y); return this;
}
@Deprecated
public final MuV2Df addBySMul(float k, ImuV2Df v) {
      this.addBy(k*v.x, k*v.y); return this;
}

   public final MuV2Df subByMul(ImuV2Df v, float k) {
      return this.subBy(k*v.x, k*v.y);
   }
   public final MuV2Df subByMul(float k, ImuV2Df v) {
      return this.subBy(k*v.x, k*v.y);
   }
   public final MuV2Df subByDiv(ImuV2Df v, float k) {
      if(k==0) throw new IllegalArgumentException("divided by 0");
      return this.subBy(v.x/k, v.y/k);
   }
@Deprecated
public final MuV2Df subBySMul(ImuV2Df v, float k) {
      return this.subBy(k*v.x, k*v.y);
}
@Deprecated
public final MuV2Df subBySMul(float k, ImuV2Df v) {
      return this.subBy(k*v.x, k*v.y);
}

//I   public final float dot(ImuV2Df v2) {  ...  }
//I   public final float wedgemul(ImuV2Df v2) {  ...  }

//I   public final float norm() {  ...  }
//I   public final float normSquare() {  ...  }
//I   public final float distance(ImuV2Df x) {  ...  }
   public final MuV2Df normalize() {
      final float n=this.norm();
      if(n==0.0) {  throw
           new RuntimeException("attempt to normalize the zero vecter");
      }
      if(n==1.0) {   return this;  }
      else {  this.divBy(n);   return this;   }
   }
   public final MuV2Df setByDirection(ImuV2Df v) {
   // setBy(v.direction());
      return this.setBy(v).normalize();
   }

   public final MuV2Df setByLinearCombination(                                  //: ?u???X
      float a1,ImuV2Df v1, float a2,ImuV2Df v2
   ){ //:  this:=(a1*v1+a2*v2)
   //   return this.setBySMul(a1, v1).addBySMul(a2,v2);
              //: bug!! ?Y(v2==this)?N?|??
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Df setByLinearCombination(                                  //: ?u???X
      float a1,float a2, ImuV2Df v1,ImuV2Df v2
   ){ //:  this:=(a1*v1+a2*v2)
      return this.setBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Df addByLinearCombination(
      float a1,ImuV2Df v1, float a2,ImuV2Df v2
   ){ //:  this+=(a1*v1+a2*v2)
      return this.addBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Df addByLinearCombination(
      float a1,float a2, ImuV2Df v1,ImuV2Df v2
   ){ //:  this+=(a1*v1+a2*v2)
      return this.addBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Df subByLinearCombination(
      float a1,ImuV2Df v1, float a2,ImuV2Df v2
   ){ //:  this-=(a1*v1+a2*v2)
      return this.subBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Df subByLinearCombination(
      float a1,float a2, ImuV2Df v1,ImuV2Df v2
   ){ //:  this-=(a1*v1+a2*v2)
      return this.subBy(a1*v1.x+a2*v2.x, a1*v1.y+a2*v2.y);
   }
   public final MuV2Df setByLinearCombination(                                  //: ?u???X
      float a1,ImuV2Df v1, float a2,ImuV2Df v2, float a3,ImuV2Df v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! ?Y(v3==this)?N?|??
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }
   public final MuV2Df setByLinearCombination(                                  //: ?u???X
      float a1, float a2, float a3,
      ImuV2Df v1, ImuV2Df v2, ImuV2Df v3
   ){ //:  this:=(a1*v1+a2*v2+a3*v3)
   // this.setByAdd(v1.mul(a1), v2.mul(a2)).addBy(v3.mul(a3);  return this;
              //:  bug!! ?Y(v3==this)?N?|??
      return this.setBy(
         a1*v1.x+a2*v2.x+a3*v3.x,
         a1*v1.y+a2*v2.y+a3*v3.y
      );
   }
   public final MuV2Df setByLinearCombination(                                  //: ?u???X
      float[] a, ImuV2Df[] v
   ){
      return this.setBy(linearCombination(a,v));
                     //: ???????[, ??this?|????Y??v[i]
   }

   public final MuV2Df setByLinearCoef(ImuV2Df v, ImuV2Df v1, ImuV2Df v2) {        //: ?D v?? v1?M v2???u???X?Y??n, ?o]?^ this
      //  let  v=h*v1+k*v2, then: h=v^v2/v1^v2, k=v1^v/v1^v2
      final float D=v1.wedge(v2);
      if(D==0) throw new IllegalArgumentException("the wedge is zero");
      return this.setBy(v.wedge(v2)/D, v1.wedge(v)/D);
   }

   public final MuV2Df addByLinearCombination(
      float a1,ImuV2Df v1, float a2,ImuV2Df v2, float a3,ImuV2Df v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      return this.addBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2Df addByLinearCombination(
      float a1, float a2, float a3,
      ImuV2Df v1, ImuV2Df v2, ImuV2Df v3
   ){ //:  this+=(a1*v1+a2*v2+a3*v3)
      return this.addBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2Df addByLinearCombination(
      float[] a, ImuV2Df[] v
   ){
      return this.addBy(linearCombination(a,v));
                     //: ???????[, ??this?|????Y??v[i]
   }
   public final MuV2Df subByLinearCombination(
      float a1,ImuV2Df v1, float a2,ImuV2Df v2, float a3,ImuV2Df v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      return this.subBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2Df subByLinearCombination(
      float a1, float a2, float a3,
      ImuV2Df v1, ImuV2Df v2, ImuV2Df v3
   ){ //:  this-=(a1*v1+a2*v2+a3*v3)
      return this.subBy(a1*v1.x+a2*v2.x+a3*v3.x, a1*v1.y+a2*v2.y+a3*v3.y);
   }
   public final MuV2Df subByLinearCombination(
      float[] a, ImuV2Df[] v
   ){
      return this.subBy(linearCombination(a,v));
   }

   //------
//I   public static final ImuV2Df linePoint(ImuV2Df p1, ImuV2Df p2, float t) { ... }
   public final MuV2Df setByLinePoint(ImuV2Df p1, ImuV2Df p2, float t) {
//      this.setByAdd(p1.mul(1-t),p2.mul(t)) ;  return this;
      return this.setByLinearCombination(1-t, p1, t, p2);
   }
   public final MuV2Df addByLinePoint(ImuV2Df p1, ImuV2Df p2, float t) {
//      this.setByAdd(p1.mul(1-t),p2.mul(t)) ;  return this;
      return this.addByLinearCombination(1-t, p1, t, p2);
   }
   public final MuV2Df subByLinePoint(ImuV2Df p1, ImuV2Df p2, float t) {
//      this.setByAdd(p1.mul(1-t),p2.mul(t)) ;  return this;
      return this.subByLinearCombination(1-t, p1, t, p2);
   }

//I   public static final ImuV2Df midPoint(ImuV2Df p1, ImuV2Df p2) { ... }
   public final MuV2Df setByMidPoint(ImuV2Df p1, ImuV2Df p2) {
      return this.setBy((p1.x+p2.x)*0.5f,(p1.y+p2.y)*0.5f);
   }
   public final MuV2Df addByMidPoint(ImuV2Df p1, ImuV2Df p2) {
      return this.addBy((p1.x+p2.x)*0.5f,(p1.y+p2.y)*0.5f);
   }
   public final MuV2Df subByMidPoint(ImuV2Df p1, ImuV2Df p2) {
      return this.subBy((p1.x+p2.x)*0.5f,(p1.y+p2.y)*0.5f);
   }

//I   public final ImuV2Df proj(ImuV2Df d) {  ...  }
//I   public final ImuV2Df co_proj(ImuV2Df d) {  ...  }
//I   public final ImuV2Df refl(ImuV2Df d) {  ...  }
//I   public final ImuV2Df co_refl(ImuV2Df d) {  ...  }
   public final MuV2Df setByProj(ImuV2Df v, ImuV2Df f) {            //: ?V?q v?b d??V????v?V?q
      //:  this:=v.proj(d), v project into direction d
      if(f.normSq()==0f) {  this.setBy(0f,0f); }
      else {  this.setByMul(v.dot(f)/f.dot(f), f);  }
      return this;
   }
   public final MuV2Df projBy(ImuV2Df f) { //: this:=this.proj(d)  //: ?V?q this?b d??V????v?V?q
      return this.setByProj(this,f);
   }
   public final MuV2Df addByProj(ImuV2Df v, ImuV2Df f) {            //: this += v.proj(d)
      //:  project into direction d
      if(f.normSq()==0f) {  ;  }
      else {  this.addByMul(v.dot(f)/f.dot(f), f);  }
      return this;
   }
   public final MuV2Df subByProj(ImuV2Df v, ImuV2Df f) {            //: this -= v.proj(d)
      //:  project into direction d
      if(f.normSq()==0f) {  ;  }
      else {  this.subByMul(v.dot(f)/f.dot(f), f);  }
      return this;
   }
   public final MuV2Df setByCoProj(ImuV2Df v, ImuV2Df f) {          //: ?V?q v?b d??V???l??v?V?q
      if(f.normSq()==0.0) this.setBy(v);
      else this.setByLinearCombination(
         1f,v, -v.dot(f)/f.dot(f),f
      );
      return this;
   }
   public final MuV2Df coProjBy(ImuV2Df f) {                       //: ?V?q this?b d??V???l??v?V?q
      return this.setByCoProj(this,f);
   }
   public final MuV2Df addByCoProj(ImuV2Df v, ImuV2Df f) {          //: this += v.CoProj(d)
      if(f.normSq()==0.0) this.addBy(v);
      else this.addByLinearCombination(
         1f,v, -v.dot(f)/f.dot(f),f
      );
      return this;
   }
   public final MuV2Df subByCoProj(ImuV2Df v, ImuV2Df f) {          //: this -= v.CoProj(d)
      if(f.normSq()==0f) this.subBy(v);
      else this.subByLinearCombination(
         1f,v, -v.dot(f)/f.dot(f),f
      );
      return this;
   }
   public final MuV2Df setByRefl(ImuV2Df v, ImuV2Df f) {            //: ?V?q v?b d??V????g?V?q
      if(f.normSq()==0f) this.setByNeg(v);
      else this.setByLinearCombination(
         2f*v.dot(f)/f.dot(f),f, -1f,v
      );
      return this;
   }
   public final MuV2Df reflBy(ImuV2Df f) {                         //: ?V?q this?b d??V????g?V?q
      return this.setByRefl(this, f);
   }
   public final MuV2Df addByRefl(ImuV2Df v, ImuV2Df f) {            //: this += v.Ref(d)
      if(f.normSq()==0.0) this.subBy(v);
      else this.addByLinearCombination(
         2f*v.dot(f)/f.dot(f),f, -1f,v
      );
      return this;
   }
   public final MuV2Df subByRefl(ImuV2Df v, ImuV2Df f) {            //: this -= v.Ref(d)
      if(f.normSq()==0f) this.addBy(v);
      else this.subByLinearCombination(
         2f*v.dot(f)/f.dot(f),f, -1f,v
      );
      return this;
   }
   public final MuV2Df setByCoRefl(ImuV2Df v, ImuV2Df f) {          //: ?V?q v?b d??V???l??g?V?q
      if(f.normSq()==0f) this.setBy(v);
      else this.setByLinearCombination(
         1f,v, -2f*v.dot(f)/f.dot(f),f
      );
      return this;
   }
   public final MuV2Df coReflBy(ImuV2Df d) {                       //: ?V?q this?b d??V???l??g?V?q
      return this.setByCoRefl(this, d);
   }
   public final MuV2Df addByCoRefl(ImuV2Df v, ImuV2Df f) {          //: this += v.CoRef(d)
      if(f.normSq()==0f) this.addBy(v);
      else this.addByLinearCombination(
         1f,v, -2f*v.dot(f)/f.dot(f),f
      );
      return this;
   }
   public final MuV2Df subByCoRefl(ImuV2Df v, ImuV2Df f) {          //: this -= v.CoRef(d)
      if(f.normSq()==0f) this.subBy(v);
      else this.subByLinearCombination(
         1f,v, -2f*v.dot(f)/f.dot(f),f
      );
      return this;
   }


   //----------------
//I   public final ImuV2Df rot90() {  ...  }
   public final MuV2Df rotBy90() {
   //B  x=-y;  y=x;   return this;  //: terrible bug! x?w??R??
      return this.setBy(-y,x);
   }
   public final MuV2Df setByRot90(ImuV2Df v) {
   //B  x=-v.y; y=v.x; return this; //: bug: ?Y(v==this)?N?|?]??????
      return this.setBy(-v.y,v.x);
   }
   public final MuV2Df addByRot90(ImuV2Df v) {
      return this.addBy(-v.y,v.x);
   }
   public final MuV2Df subByRot90(ImuV2Df v) {
      return this.subBy(-v.y,v.x);
   }

   public final MuV2Df rotByNeg90() {
      return this.setBy(y,-x);
   }
   public final MuV2Df setByRotNeg90(ImuV2Df v) {
      return this.setBy(v.y,-v.x);
   }
   public final MuV2Df addByRotNeg90(ImuV2Df v) {
      return this.addBy(v.y,-v.x);
   }
   public final MuV2Df subByRotNeg90(ImuV2Df v) {
      return this.subBy(v.y,-v.x);
   }

//I   public final ImuV2Df rot(float Angle) {  ...  }
//I   public final ImuV2Df rot(float Angle, ImuV2Df center) {  ...  }

   //[ this ?]?? src??A?????G,?{???????Ocos(A),sin(A)??DA
   final MuV2Df setByCSRot(ImuV2Df src, float cosA, float sinA) {
   //: cosA*(src.x, src.y)+sinA*(-src.y, src.x)
      return this.setBy(
                    src.x*cosA-src.y*sinA,
                    src.y*cosA+src.x*sinA
                  );
   }
   //[ this ??A, ?{???????Ocos(A),sin(A)??DA
   final MuV2Df csRotBy(float cosA, float sinA) {
      return this.setByCSRot(this,cosA,sinA);
   }
   final MuV2Df addByCSRot(ImuV2Df v, float cosA, float sinA) {
      return this.addBy(v.x*cosA-v.y*sinA, v.x*sinA+v.y*cosA);
   }
   final MuV2Df subByCSRot(ImuV2Df v, float cosA, float sinA) {
      return this.subBy(v.x*cosA-v.y*sinA, v.x*sinA+v.y*cosA);
   }
   final MuV2Df setByCSRot(
      ImuV2Df v, float cosA, float sinA, ImuV2Df center
   ) {
      return this.setBy(
         center.x+(v.x-center.x)*cosA-(v.y-center.y)*sinA,
         center.y+(v.x-center.x)*sinA+(v.y-center.y)*cosA
      );
   }
   final MuV2Df csRotBy(
      float cosA, float sinA, ImuV2Df center
   ) {
      return this.setByCSRot(this, cosA, sinA, center);
   }
   final MuV2Df addByCSRot(
      ImuV2Df v, float cosA, float sinA, ImuV2Df center
   ) {
      return this.addBy(
         center.x+(v.x-center.x)*cosA-(v.y-center.y)*sinA,
         center.y+(v.x-center.x)*sinA+(v.y-center.y)*cosA
      );
   }
   final MuV2Df subByCSRot(
      ImuV2Df v, float cosA, float sinA, ImuV2Df center
   ) {
      return this.subBy(
         center.x+(v.x-center.x)*cosA-(v.y-center.y)*sinA,
         center.y+(v.x-center.x)*sinA+(v.y-center.y)*cosA
      );
   }
   //----------
   //[ this ?]?? src ??A?????G
   public final MuV2Df setByRot(ImuV2Df src, float A) {
      return this.setByCSRot(src, (float)cos(A),(float)sin(A));
   }
   //[ this ?]?? src ??A?????G
   public final MuV2Df rotBy(float A) {
      return this.setByCSRot(this, (float)cos(A), (float)sin(A));
   }
   //[ A21111 xie
   public final MuV2Df xRotBy(float angle) { //: this?x∂b≠–??A?   
      return this.setByCSRot(this, (float)cos(angle), (float)sin(angle));
   }
   public final MuV2Df yRotBy(float angle) { //: this?y∂b≠–??A?
      return this.setByCSRot(this, (float)sin(angle), (float)cos(angle));
   }
   
   //]
   public final MuV2Df addByRot(ImuV2Df v, float A) {
      return this.addByCSRot(v, (float)cos(A), (float)sin(A));
   }
   public final MuV2Df subByRot(ImuV2Df v, float A) {
      return this.subByCSRot(v, (float)cos(A), (float)sin(A));
   }
   //---
   public final MuV2Df setByRot(ImuV2Df v, float A, ImuV2Df center) {
      return setByCSRot(v, (float)cos(A), (float)sin(A), center);
   }
   public final MuV2Df rotBy(float A, ImuV2Df center) {
      return this.setByCSRot(this, (float)cos(A), (float)sin(A), center);
   }
   public final MuV2Df addByRot(ImuV2Df v, float A, ImuV2Df center) {
      return addByCSRot(v, (float)cos(A), (float)sin(A), center);
   }
   public final MuV2Df subByRot(ImuV2Df v, float A, ImuV2Df center) {
      return subByCSRot(v, (float)cos(A), (float)sin(A), center);
   }

   public final MuV2Df setBySinRot(ImuV2Df v, float sinA) {
      //: ?A?£^??X: ???{???????Osin(A)??DA
      //: -PI/2 <= A <= +PI/2, -1 <= sin(A) <= +1
      final float cosA=(float)sqrt(1-sinA*sinA);
      return setByCSRot(v, cosA, sinA);
   }
   public final MuV2Df sinRotBy(float sinA) {
      //: ?A?£^??X: ???t?U??, ?B?{???????Osin(A)??DA
      //: -PI/2 <= A <= +PI/2, -1 <= sin(A) <= +1
      final float cosA=(float)sqrt(1-sinA*sinA);
      return setByCSRot(this, cosA, sinA);
   }
   public final MuV2Df addBySinRot(ImuV2Df v, float sinA) {
      //: ?A?£^??X: ???{???????Osin(angle)??Dangle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1
      final float cosA=(float)sqrt(1-sinA*sinA);
      return this.addByCSRot(v, cosA, sinA);
   }
   public final MuV2Df subBySinRot(ImuV2Df v, float sinA) {
      //: ?A?£^??X: ???{???????Osin(angle)??Dangle
      //: -PI/2 <= angle <= +PI/2, -1 <= sin(angle) <= +1
      final float cosA=(float)sqrt(1-sinA*sinA);
      return this.subByCSRot(v, cosA, sinA);
   }
   public final MuV2Df setBySinRot(ImuV2Df v, float sinA, ImuV2Df center) {
      final float cosA=(float)sqrt(1-sinA*sinA);
      return setByCSRot(v, cosA, sinA, center);
   }
   public final MuV2Df sinRotBy(float sinA, ImuV2Df center) {
      final float cosA=(float)sqrt(1-sinA*sinA);
      return setByCSRot(this, cosA, sinA, center);
   }
   public final MuV2Df addBySinRot(ImuV2Df v, float sinA, ImuV2Df center) {
      final float cosA=(float)sqrt(1-sinA*sinA);
      return addByCSRot(v, cosA, sinA, center);
   }
   public final MuV2Df subBySinRot(ImuV2Df v, float sinA, ImuV2Df center) {
      final float cosA=(float)sqrt(1-sinA*sinA);
      return subByCSRot(v, cosA, sinA, center);
   }

   //-------------------
   public final MuV2Df solveLinearCoef(ImuV2Df v, ImuV2Df v1, ImuV2Df v2) {          //: ?? wedge product?D?u???X???Y?? h,k
      //  let  v=h*v1+k*v2,  then: h=v^v2/v1^v2, k=v1^v/v1^v2
      //  this.setBy(h,k)
      final float D=v1.wedge(v2);
      if(D==0) throw new IllegalArgumentException("v1 adn v2 are colinear");
      return this.setBy(v.wedge(v2)/D, v1.wedge(v)/D);
   }

   //[  Suppose lines AB, and CD not parallel.
   //[  LET  E is the intersection of line AB and line CD
   //[  Let  <AE>=h<AB>, <CE>=k<CD>,
   //[  THEN  h=<AC>^<CD>/<AB>^<CD>, k=<AC>^<AB>/<AB>^<CD>
   //[  solve and set (h,k) to this
   //[  Note: ??dh,k?i?P?w???I?b???@?q.
   public final MuV2Df solveIntersectPara(                                      //: ?D???u(AB, CD)???I?? h,k?Y??
      ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D
   ){
      final float ABx=B.x-A.x, ABy=B.y-A.y,
                   CDx=D.x-C.x, CDy=D.y-C.y;
      final float w=ABx*CDy-ABy*CDx;
      if(w==0) throw new IllegalArgumentException("AB and CD are parallel");
      final float ACx=C.x-A.x, ACy=C.y-A.y;
      return this.setBy((ACx*CDy-ACy*CDx)/w, (ACx*ABy-ACy*ABx)/w);
     //[ ?V?q??????? free store
     // final ImuV2Df AB=B.sub(A), CD=D.sub(C);
     // final float w=AB.wedge(CD);
     // if(w==0) throw new IllegalArgumentException("AB AND CD are parallel");
     // final ImuV2Df CD=C.sub(A);
     // return this.setBy(this.wedge(CD)/w, CD.wedge(AB)/w);
   }

   //[  Suppose lines AB, and CD not parallel.
   //[  Solve and set the intersection point E to this
   //[  E=A+<AE>=A+h<AB>, h=<AC>^<CD>/<AB>^<CD>
   public final MuV2Df setByIntersect(                                           //: ?D AB, CD???u???I
      ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D
   ){
      final float ABx=B.x-A.x, ABy=B.y-A.y,
                   CDx=D.x-C.x, CDy=D.y-C.y;
      final float w=ABx*CDy-ABy*CDx; // w=AB.wedge(CD);
      if(w==0) throw new IllegalArgumentException("AB and CD are parallel");
      final float ACx=C.x-A.x, ACy=C.y-A.y;  //: AC:=C-A
      final float h=(ACx*CDy-ACy*CDx)/w; //: h=AC.wedge(CD)/w
      this.setBy(A.x+h*ABx, A.y+h*ABy);                            //: P = A +tAB
      return this;
//[ ?V?q???????free store
//    final ImuV2Df AB=B.sub(A), CD=D.sub(C);
//    final float w=AB.wedge(CD);
//    if(w==0) throw new IllegalArgumentException("the wedge is zero");
//    final ImuV2Df AC=C.sub(A);
//    final float h=AC.wedge(CD)/w; //: h=AC.wedge(CD)/w
//    this.setBy(A).addByMul(h,AB);
//    return this;
   }

   // addByIntersect???X?z, ?]intersect?O?I??D?V?q

//I   public final float angle(ImuV2Df till) {  ...  }
//I   public int winding(ImuV2Df[] vertices) {  ...  }
//I   public ImuV2Df linear_coef(ImuV2Df v1, ImuV2Df v2) {  ...  }
//I   public static ImuV2Df intersect_para(ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D) {  ...  }
//I   public static ImuV2Df intersect(ImuV2Df A, ImuV2Df B, ImuV2Df C, ImuV2Df D){  ...  }


   //[ ---- matrix ----------------------------
   // this ?? 1 by 2 ?x?}, ???k??
   public final MuV2Df mulBy(ImuM2D m) { //  this*=m;
      return this.setBy(
         (float)(this.x*m._r1.x+this.y*m._r2.x),
         (float)(this.x*m._r1.y+this.y*m._r2.y)
      //   this.x*m._11+this.y*m._21,
      //   this.x*m._12+this.y*m._22
      );
   }
   //  m*=v ?L?N?q: m*v?O?V?q
   // this ?? 1 by 2 ?x?}, ???k??
   public final MuV2Df rMulBy(ImuM2D m) {  // this*=m
      return mulBy(m);
   }

   // this ?? 2 by 1 ?x?}, ??????
   public final MuV2Df swapMulBy(ImuM2D m) { //  this:= m*this
      return this.setBy(
        (float)(m._r1.x*this.x+m._r1.y*this.y),
        (float)(m._r2.x*this.x+m._r2.y*this.y)
      //   m._11*this.x+m._12*this.y,
      //   m._21*this.x+m._22*this.y
      );
   }

   // this ?? 2 by 1 ?x?}, ??????
   public final MuV2Df lMulBy(ImuM2D m) {  //  this:= m*this
      return this.swapMulBy(m);
   }

   // v ?? 1 by 2 ?x?}, ???k??
   public final MuV2Df setByMul(ImuV2Df v, ImuM2D m) {
      return this.setBy(
        (float)(v.x*m._r1.x + v.y*m._r2.x),
        (float)(v.x*m._r1.y + v.y*m._r2.y)
      //   v.x*m._11 + v.y*m._21,
      //   v.x*m._12 + v.y*m._22
      );
   }

   // v ?? 2 by 1 ?x?}, ??????
   public final MuV2Df setByMul(ImuM2D m, ImuV2Df v) {
      return this.setBy(
         (float)(m._r1.x*v.x+m._r1.y*v.y),
         (float)(m._r2.x*v.x+m._r2.y*v.y)
      //   m._11*v.x+m._12*v.y,
      //   m._21*v.x+m._22*v.y
      );
   }

   public final MuV2Df addByMul(ImuV2Df v, ImuM2D m) {
      return this.addBy(
         (float)(v.x*m._r1.x + v.y*m._r2.x),
         (float)(v.x*m._r1.y + v.y*m._r2.y)
      //   v.x*m._11 + v.y*m._21 ,
      //   v.x*m._12 + v.y*m._22
      );
   }
   public final MuV2Df addByMul(ImuM2D m, ImuV2Df v) {
      return this.addBy(
        (float)(m._r1.x*v.x+m._r1.y*v.y),
        (float)(m._r2.x*v.x+m._r2.y*v.y)
      //   m._11*v.x+m._12*v.y,
      //   m._21*v.x+m._22*v.y
      );
   }

   public final MuV2Df subByMul(ImuV2Df v, ImuM2D m) {
      return this.subBy(
         (float)(v.x*m._r1.x + v.y*m._r2.x),
         (float)(v.x*m._r1.y + v.y*m._r2.y)
      //   v.x*m._11 + v.y*m._21 ,
      //   v.x*m._12 + v.y*m._22
      );
   }
   public final MuV2Df subByMul(ImuM2D m, ImuV2Df v) {
      return this.subBy(
         (float)(m._r1.x*v.x+m._r1.y*v.y),
         (float)(m._r2.x*v.x+m._r2.y*v.y)
      //   m._11*v.x+m._12*v.y,
      //   m._21*v.x+m._22*v.y
      );
   }
   //] ---- ?x?}

}
