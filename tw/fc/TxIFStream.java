package tw.fc;
//import java.io.InputStream;
import java.io.FileNotFoundException;  //: 此繼承java.io.IOException 
import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;


//[ 本class沒有新的行為, 所以不必override method, 
//[ 也不必縮函數值型態
public class TxIFStream 
   extends TxIStream
{

   //------------------------------------------
//   public TxIFStream(InputStream is) {
//      super();
//      super.iii=new BufferedReader(
//             new InputStreamReader(is)
//          );
//          //: 這給file用, 需buffered.  cf. TxIStream, TxIStrStream
//   } 

   //final String name; 
   private final File _absPathName;
   //----------------------------------------
   public TxIFStream(String filename) throws IOException
   //throws FileNotFoundException  //: filename是dir, 或不存在, 或其它問題
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
// 有空時可做, 應連 mark 一起支援. 
//!   要參考比較 TxOStrStream
//!   BufferedReader 與 CharArrayReader 在從未mark時做reset的行為會不同

   //-----------------------------------------------

   @Override
   public String toString() {
      //return "TxIFStream("+name+")";    
      return "TxIFStream("+_absPathName+")";    
   }

   public File getAbsPathName() {   return this._absPathName;  }
    

}

