package tw.fc ;
import java.io.IOException;
import java.io.EOFException;

abstract public class iNumRef 
   implements PrintableI, ScannableI 
// extendedBy byteRef
// extendedBy shortRef
// extendedBy intRef
// extendedBy longRef
{
   public abstract String toHexString();
   public abstract String toOctalString();

   @Override public abstract void scanFrom(TxIStream iS) throws IOException ;

   //[ virtual-get
   public static iNumRef getInstanceFrom(TxIStream iS) throws IOException {
      final longRef lRef= new longRef();
      lRef.scanFrom(iS);
      if(Byte.MIN_VALUE <= lRef.v && lRef.v<=Byte.MAX_VALUE) {
         return new byteRef((byte)lRef.v);
      }
      else if(Short.MIN_VALUE <= lRef.v && lRef.v<=Short.MAX_VALUE) {
         return new shortRef((short)lRef.v);
      }
      else if(Integer.MIN_VALUE <= lRef.v && lRef.v<=Integer.MAX_VALUE) {
         return new intRef((int)lRef.v);
      }
      else {
         return lRef;
      }
   }
   public static iNumRef getInstanceFrom(TxICStream iS) {
      try { return getInstanceFrom((TxIStream)iS);  }
      catch(IOException xpt) {  throw new Error(xpt); }
   }


   //---------------
   public static int toDigit(int _byte, int radix) {
      //: return -1 if fail , 頂多到36進位
       int value=(_byte-(byte)'0');
       if( value<0 ) return -1;
       if( value<10 ) { //: 0,1,2,...,9
          if(value<radix) return value;
          else return -1;
       }
       value=(_byte-(byte)'A'+10);
       if(value<10) return -1;
       if(value<36) { //: A,B,...,Z means 10,11,...,35
          if(value<radix) return value;
          else return -1;
       }
       value=(_byte-(byte)'a'+10);
       if(value<10) return -1;
       if(value<36) { //: a,b,...,z means 10,11,...,35
          if(value<radix) return value;
          else return -1;
       }
       return -1;
   }


   //[ 處理整數開頭的正負號
   //[ A50217 由 TxIStream 移至 iNumRef, 稍加調整
   protected static int _optSign(TxIStream iS) throws IOException {
      int _byte=iS.read();
      if(_byte==TxIStream.EOF) {
         throw new EOFException("EOF while expecting number");
      }
      int sign=1;
      if( _byte==(byte)'-') {  sign= -1; }
      else if( _byte==(byte)'+') {  ;  }
      else {
         //iS.unread(_byte);
         if(_byte!=TxIStream.EOF) iS.unread((char)_byte);
      }
      return sign;
   }

 //------------------------------------------------

   //[ A50217 由 TxIStream 移至 iNumRef, 需稍加調整
   protected static final long _parseDigits(
      TxIStream iS, int radix, 
      intRef lastByte, booleanRef got
   ) 
   throws IOException  {
      long result=0;   int _byte;   boolean gotDigit=false;
      while(true) {
         _byte= iS.read();
         if(_byte==TxIStream.EOF) {  break;  }  //: 可能還是正常狀態
         final int value= iNumRef.toDigit(_byte, radix);
         if( value<0 ) {  
            //iS.unread(_byte);   //: b4 A5.024L
            if(_byte!=TxIStream.EOF) iS.unread((char)_byte); //: A5.012.F add
            break;  
         }
         gotDigit=true;
         result = result * radix + value ;
      } //] 遇到EOF或非數字而跳出迴圈
      got.v =(gotDigit);  lastByte.v =(_byte);   return result;
   }


}