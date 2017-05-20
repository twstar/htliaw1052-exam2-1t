package tw.fc;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
//============================================================

public class TxOFStream extends TxOStream {

//舊版: 
//  PrintStream  _ooo ;   

   private BufferedWriter bWriter ; 
	 private final String lineSeparator= System.getProperty("line.separator");
  
//I  boolean _startOfLine=true;
   private final File _absPathName;

   //---------------------------------------
   public TxOFStream(File file, boolean append) throws IOException {
      super();  
      this._absPathName=file;
      bWriter=new BufferedWriter(new FileWriter(file, append));
   }
   public TxOFStream(File file) throws IOException {
      this(file, false);
   }

   public TxOFStream(String filename, boolean append) throws IOException {
      //super();  //: 不能在try-block內做this-ctor call
      //bWriter=new BufferedWriter(new FileWriter(filename, append));
      this(new File(filename), append);
   }
   public TxOFStream(String filename) throws IOException {
      this(filename, false);
   }

/*
   public TxOFStream(java.io.FileDescriptor fdObj) throws IOException {
      super();
      bWriter=new BufferedWriter(new FileWriter(fdObj));
   }
*/

   //------------------------------------

   public File getAbsPathName() {   return this._absPathName;  }


   @Override
   public TxOFStream flush() throws IOException  {   
      bWriter.flush();  return this;
   }
   @Override
   public void close() throws IOException  {   bWriter.close();   }

   //---------------

//I @Override public boolean startOfLine() 

   //@Override public final TxOFStream p(String s) throws IOException { //: b4 A5.041
   @Override public TxOFStream p(String s) throws IOException { 
      bWriter.write(s,0,s.length());    
      if(s.length()>0) this._startOfLine=(s.charAt(s.length()-1)=='\n');   //: A5.023L
      return this;
   }

   //@Override public final TxOFStream pn() throws IOException {  //: b4 A5.041
   @Override public TxOFStream pn() throws IOException {  
      this.p(lineSeparator);  this._startOfLine=true;  return this.flush(); 
   }

/*
   @Override //[ 縮值型
   public TxOFStream setDecimalPattern(String p) { //: 長效
      super.setDecimalPattern(p);  return this;
   } 
   @Override //[ 縮值型
   public TxOFStream sDP(String p) { //: 長效
      super.setDecimalPattern(p);  return this;
   } 
   @Override //[ 縮值型
   public TxOFStream setDecimalPattern() { //: 長效
      super.setDecimalPattern();  return this;
   }
   @Override //[ 縮值型
   public TxOFStream sDP() { //: 長效
      super.setDecimalPattern();  return this;
   }
*/

//I  public TxOStream printf(String fStr, Object... args) throws IOException { 

}

//============================================================

