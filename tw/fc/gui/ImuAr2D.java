package tw.fc.gui;

import java.io.IOException;
import java.io.Serializable;

import tw.fc.DuplicableI;
import tw.fc.PrintableI;
import tw.fc.TxOStream;

//: A20501 wu

public class ImuAr2D implements DuplicableI<MuAr2D>, PrintableI, Serializable{

   private static final long serialVersionUID = 2014120807L;

	public static final ImuAr2D STANDARD=new ImuAr2D();

	MuM2D _mtx;
	final MuV2D _vec;

	//[ -------------------- constructors --------------------   
	
	//: default ctor, create an identity system
	public ImuAr2D(){
		this(ImuM2D.ONE, ImuV2D.ZERO);	   
	}
		
	public ImuAr2D(ImuM2D baseMtx, ImuV2D centerVec){
		this._mtx=new MuM2D(baseMtx);
		this._vec=new MuV2D(centerVec);   	
	}
	
	public ImuAr2D(ImuV2D b1, ImuV2D b2, ImuV2D c){
		this._mtx=new MuM2D(b1, b2);
		this._vec=new MuV2D(c);
	}
	
	public ImuAr2D(double b11, double b12, 
			         double b21, double b22, 
			         double c1,  double c2){
		this._mtx=new MuM2D(b11, b12, b21, b22);
		this._vec=new MuV2D(c1, c2);
	}
	
	//: create a pure translation
	public ImuAr2D(ImuV2D c){		
//		this._mtx=MuM2D.scalar(1.0);
		this._mtx=ImuM2D.scalar(1.0);
		this._vec=new MuV2D(c);
	}
	
	public ImuAr2D(double c1, double c2){
		this._mtx=MuM2D.scalar(1.0);
		this._vec=new MuV2D(c1, c2);
	}
	
	//: create a center-fixed system
	public ImuAr2D(ImuM2D baseMtx){
		this._mtx=new MuM2D(baseMtx);
		this._vec=new MuV2D();		
	}
	
	public ImuAr2D(ImuV2D b1, ImuV2D b2){
		this._mtx=new MuM2D(b1, b2);
		this._vec=new MuV2D();		
	}
	
	public ImuAr2D(double b11, double b12, double b21, double b22){
		this._mtx=new MuM2D(b11, b12, b21, b22);
		this._vec=new MuV2D();
	}

	//: copy ctor
	public ImuAr2D(ImuAr2D src){
		this(src._mtx, src._vec);
	}

   //] -------------------- constructors --------------------


	@Override
	public void printTo(TxOStream ooo) throws IOException {
		ooo.p("[").ps(this._mtx).p(this._vec).p("]");		
	}
	
	public final void widthPrintTo(int w, TxOStream ooo) throws IOException{
		printTo(ooo);
	}

	@Override
	public MuAr2D duplicate() {
		return new MuAr2D(this);
	}
	
	@Override 
	public String toString(){
		return "{"+ _mtx + _vec + "}";
	}
	
	public final boolean equals(ImuAr2D m2){
      return (this._mtx.equals(m2._mtx) &&
      		  this._vec.equals(m2._vec));
	}	
	
	public final boolean nonEquals(ImuAr2D m2){
		return !this.equals(m2);
	}
	
	@Override
	public final boolean equals(Object v2){
		return this.equals((ImuAr2D)v2);
	}
	
	@Override
   public final int hashCode() {
      long tL;  int tI=17;
      tL=_mtx.hashCode(); tI=tI*37+(int)(tL^(tL>>32));
      tL=_vec.hashCode(); tI=tI*37+(int)(tL^(tL>>32));
      return tI;
   }
	
	public final boolean isStandard(){
		return this.equals(ImuAr2D.STANDARD);
	}
	
	public final boolean notStandard(){
		return !this.isStandard();
	}
	
	public ImuV2D org(){ return new ImuV2D(_vec); }
	public ImuM2D mtx(){ return new ImuM2D(_mtx); }
	public ImuV2D axis1(){ return new ImuV2D(_mtx._r1); }
	public ImuV2D axis2(){ return new ImuV2D(_mtx._r2); }
	public ImuV2D negAxis1(){ return _mtx._r1.neg(); }
	public ImuV2D negAxis2(){ return _mtx._r2.neg(); }
	
	public final double axis1Norm(){ return _mtx._r1.norm(); }
	public final double axis2Norm(){ return _mtx._r2.norm(); }
	public final double axis1NormSq(){ return _mtx._r1.normSq(); }
	public final double axis2NormSq(){ return _mtx._r2.normSq(); }
	public final double axesMeanSqNorm(){ 
		return Math.sqrt((_mtx._r1.normSq()+_mtx._r2.normSq())/2); 
	}
	
	//[ If Sys0 describe Sys1 as Ar2D(B1,c1)
   //[ and Sys1 describe Sys2 as Ar2D(B2,c2)
   //[ then Sys0 describe Sys2 as Ar2D(B2,c2)*Ar2D(B1,c1)
   public final ImuAr2D mul(ImuAr2D A2) {      
   //  [ M1  0 ][ M2  0 ]=[ M1*M2     0 ]
   //  [ v1  1 ][ v2  1 ] [ v1*M2+v2  1 ]   
      return new ImuAr2D(this._mtx.mul(A2._mtx), 
                         this._vec.mul(A2._mtx).add(A2._vec));
   } 
   
   public final ImuAr2D swapMul(ImuAr2D a1){
   	return a1.mul(this);
   }
   
   public final ImuV2D swapMul(ImuV2D v1){
   //           [ M2  0 ]
   //  [ v1  1 ][ v2  1 ]=[ v1*M2+v2  1 ]
      return new MuV2D(this._vec).addByMul(v1, this._mtx);
   }
	
   public final ImuAr2D inverse(){
   	return new MuAr2D(this).inverse();
   }
   
   public final double det() {  return this._mtx.det();  }
	
   //[ this:     the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newCord:  the new coordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final void pos_updateOldCord(MuV2D oldCord, ImuV2D newCord) {
      // The throry:
      //   this == new ImuAr2D(b1',b2',c')
      //   old system described by itself == ImuAr2D(E1,E2,ZERO)
      //   Let oldCord==(x,y), newCord=(x',y'), 
      //   Then  obj == x*E1+y*E2+ZERO , 
      //         obj == x'*b1'+y'*b2'+c'            
      //             == (x',y') * Mtx(b1',b2') + c';       //: vector form
      //   oldCord = newCord*B'+c'                         //: matrix form  
      oldCord.setByMul( newCord, this._mtx ).addBy(this._vec);
      //                                 [ B'  o ]
      //   [ oldCord 1 ] = [ newCord  1 ][ c'  1 ]         //: affine form:   	
   }
   
   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final void vec_updateOldCord(MuV2D oldCord, ImuV2D newCord) {
      oldCord.setByMul( newCord, this._mtx );
   }
   
   //[ this:     the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newCord:  the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final ImuV2D pos_oldCord(ImuV2D cordNew) {
      return cordNew.mul(this._mtx).add(this._vec);
   }
    
   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final ImuV2D vec_oldCord(ImuV2D cordNew) {
      return cordNew.mul(this._mtx);
   }
   
   //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final void pos_updateNewCord(ImuV2D oldCord, MuV2D newCord) {
      //   oldCord = newCord*B'+c'                         //: matrix form  
      //   newCord = (oldCord-c')*B'.inv                   //: matrix form  
      newCord.setBySub(oldCord,_vec).mulBy(_mtx.inverse());
      //                                 [ B'    o ]
      //  [ oldCord  1 ] = [ newCord  1 ][ c'    1 ]       //: affine form
      //
      //                                 [    B'.inv  o ]
      //  [ newCord  1 ] = [ oldCord  1 ][ -c*B'.inv  1 ]  //: affine form
   }
   
 //[ this:     the new system described by old system, 
   //[ newCord:  the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final void vec_updateNewCord(ImuV2D oldCord, MuV2D newCord) {
      newCord.setByMul(oldCord,_mtx.inverse());      
   }
   
   //[ this:     the new system described by old system, 
   //[ ans:      the new cordinate of a position in new system 
   //[ oldCord:  the old coordinate of a position in old System
   public final ImuV2D pos_newCord(ImuV2D oldCord) {
      return oldCord.sub(this._vec).mul(this._mtx.inverse());
                   //: exception when this.mtx() is not invertible
   }     
   
   //[ this:     the new system described by old system, 
   //[ ans:      the new cordinate of a vector in new system 
   //[ oldCord:  the old coordinate of a vector in old System
   public final ImuV2D vec_newCord(ImuV2D oldCord) {
      return oldCord.mul(this._mtx.inverse());
   }
   
   //[ this:    the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newDsc:  the new descreption of a system in new system 
   //[ oldCord: the old descreption of a system in old System
   public final void updateOldDescription(MuAr2D oldDesc, ImuAr2D newDesc) {
      if(this==oldDesc) throw new IllegalArgumentException("Not support");
   //  this.vec_updateOldCord(oldDesc.axis1(), newCord.axis1()); 
   //  this.vec_updateOldCord(oldDesc.axis2(), newCord.axis2()); 
   //  this.vec_updateOldCord(oldDesc.axis3(), newCord.axis3()); 
   //  this.pos_updateOldCord(oldDesc.org(), newCord.org()); 
      //
      //  LET   this==ImuAr3D(b1',b2',b3',c')==ImuAr3D(B',c') 
      //  THEN  oldAxis1 = newAxis1*B',
      //        oldAxis2 = newAxis2*B', 
      //        oldAxis3 = newAxis3*B', 
      //        oldOrg   = newOrg*B'+c'.      //: vector form
      //  I.E.  oldMtx = newMtx*B',  
      //        oldOrg = newOrg*B'+c'.        //: matrix form
      //  I.E.
      //        [ oldMtx  o ]   [ newMtx  o ] [  B'  o ]
      //        [ oldOrg  1 ] = [ newOrg  1 ] [  c'  1 ]  //: affine form
      oldDesc._vec.setBy(this._vec).addByMul(newDesc._vec,this._mtx); 
      oldDesc._mtx.setByMul( newDesc._mtx, this._mtx );               
                                //: ?i?? oldDesc==newDesc, ??Hmtx?n??? 
      // oldDesc.setByMul( newDesc, this );   //: affine form
   }
   //[ this:    the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newDsc:  the new descreption of a system in new system 
   //[ ans:     the old descreption of a system in old System
   public final ImuAr2D oldDescription(ImuAr2D newDesc) {
      return new ImuAr2D( 
         newDesc._mtx.mul(this._mtx), 
         newDesc._vec.mul(this._mtx).add(this._vec));  //: matrix form                               
   }
   
   //[ this:    the new system described by old system,
   //[           ( usually the old system is the standard system.)    
   //[ newDsc:  the new descreption of a system in new system 
   //[ oldCord: the old descreption of a system in old System
   public final void updateNewDescription(ImuAr2D oldDesc, MuAr2D newDesc) {
      if(this==newDesc) throw new IllegalArgumentException("Not support");
      //
      //  LET   this==ImuAr3D(b1',b2',b3',c')==ImuAr3D(B',c') 
      //  THEN  oldAxis1 = newAxis1*B',
      //        oldAxis2 = newAxis2*B', 
      //        oldAxis3 = newAxis3*B', 
      //        oldOrg   = newOrg*B'+c'. 
      //  THEN  newAxis1 = oldAxis1*B'.inv ,
      //        newAxis2 = oldAxis2*B'.inv , 
      //        newAxis3 = oldAxis3*B'.inv , 
      //        newOrg   = (oldOrg-c')*B'.inv . 
      //  I.E.  newMtx = oldMtx*B'.inv,  
      //        newOrg = (oldOrg-c')*B'.inv .
      newDesc._vec.setBySub(oldDesc._vec,this._vec).mulBy(this._mtx.inverse()); 
      newDesc._mtx.setByMul( oldDesc._mtx, this._mtx.inverse());               
   }
   
   public final ImuAr2D newDescription(ImuAr2D oldDesc) {
      return new ImuAr2D(
         oldDesc._mtx.mul(this._mtx.inverse()),
         oldDesc._vec.sub(this._vec).mul(this._mtx.inverse())
      );
   }
}
