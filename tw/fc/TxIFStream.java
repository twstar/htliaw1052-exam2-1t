package tw.fc;
//import java.io.InputStream;
import java.io.FileNotFoundException;  //: ���~��java.io.IOException 
import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;


//[ ��class�S���s���欰, �ҥH����override method, 
//[ �]�����Y��ƭȫ��A
public class TxIFStream 
   extends TxIStream
{

   //------------------------------------------
//   public TxIFStream(InputStream is) {
//      super();
//      super.iii=new BufferedReader(
//             new InputStreamReader(is)
//          );
//          //: �o��file��, ��buffered.  cf. TxIStream, TxIStrStream
//   } 

   //final String name; 
   private final File _absPathName;
   //----------------------------------------
   public TxIFStream(String filename) throws IOException
   //throws FileNotFoundException  //: filename�Odir, �Τ��s�b, �Ψ䥦���D
   {
      //super();  
      //name=filename;
      //super.iii=new BufferedReader(new FileReader(filename));
      this(new File(filename));
   }

   public TxIFStream(File file) throws IOException 
   {
      // super();  
      // name=file.toString();
      // super.iii=new BufferedReader(new FileReader(file));
      super();  
      this._absPathName=file;
      super.iii=new BufferedReader(new FileReader(file));
   }

/*
   public TxIFStream(java.io.FileDescriptor fd) throws IOException 
   {
      super(); 
      name=fd.toString(); 
      super.iii=new BufferedReader(new FileReader(fd));
   }
*/

   //------------------------------------------

   public void close() throws IOException {
      super.iii.close();
   }

//   public void reset() {
//
//   }
// ���Ůɥi��, ���s mark �@�_�䴩. 
//!   �n�ѦҤ�� TxOStrStream
//!   BufferedReader �P CharArrayReader �b�q��mark�ɰ�reset���欰�|���P

   //-----------------------------------------------

   @Override
   public String toString() {
      //return "TxIFStream("+name+")";    
      return "TxIFStream("+_absPathName+")";    
   }

   public File getAbsPathName() {   return this._absPathName;  }
    

}

