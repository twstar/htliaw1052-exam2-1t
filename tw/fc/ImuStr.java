package tw.fc;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

// 比 class String 加了 printTo

public class ImuStr  
       implements DuplicableI<MuStr>, PrintableI, CharSequence
{
   final StringBuilder that;

   //===============================================

   //[ -----  依 class String 之排序 ------------
   // ImuStr 添加的都放在檔末 

   public ImuStr() {  
      that=new StringBuilder();  
   }
   public ImuStr(byte[] bytes) {
   // that=new StringBuilder(bytes);  //: 無此ctor
      this(new String(bytes));
   }
   public ImuStr(byte[] bytes, int offset, int length) {
   // that=new StringBuilder(bytes, offset, length); //: 無此ctor
      this(new String(bytes, offset, length));
   }
   public ImuStr(
      byte[] bytes, int offset, int length, String charsetName
   ) throws UnsupportedEncodingException
   {
//    that=new StringBuilder(bytes, offset, length, charsetName); //: 無此ctor
      this(new String(bytes, offset, length, charsetName));
   }
   public ImuStr(byte[] bytes, String charsetName)
       throws UnsupportedEncodingException
   {
//    that=new StringBuilder(bytes, charsetName);  //: 無此ctor
      this(new String(bytes, charsetName)); 
   }

   public ImuStr(char[] arr) {   
//    that=new StringBuilder(arr);   //: 無此ctor
      this(new String(arr));   
   }
   public ImuStr(char[] arr, int srcBegin, int len) {
//    that=new StringBuilder(arr, srcBegin, len); //: 無此ctor
      this(new String(arr, srcBegin, len));
   }
   public ImuStr(int[] codePoints, int offset, int count) {
//    that.new StringBuilder(codePoints, offset, count); //: 無此ctor
      this(new String(codePoints, offset, count));
   }
   public ImuStr(String str) {  
      that=new StringBuilder(str);
   }

   public ImuStr(StringBuffer b) {  
      that=new StringBuilder(b.toString());  
   }
   public ImuStr(StringBuilder b) {  
      that=new StringBuilder(b.toString());  
   }

   //----------------------------------------
   public char charAt(int index) {
	    return that.toString().charAt(index);
   }
   public int codePointAt(int index) {
      return that.toString().codePointAt(index);
   }
   public int codePointBefore(int index) {
      return that.toString().codePointBefore(index);
   }
   public int codePointCount(int beginIndex, int endIndex) {
      return that.toString().codePointCount(beginIndex, endIndex);
   }
   public final int compareTo(String s2) {
      return that.toString().compareTo(s2);
   }
   public final int compareToIgnoreCase(String str) {
      return that.toString().compareToIgnoreCase(str); 
   } 
   public String concat(String str) {
      return that.toString().concat(str);
   }
   public boolean contains(java.lang.CharSequence s) {
      return that.toString().contains(s);
   }
   public boolean contentEquals(CharSequence cs) {
      return that.toString().contentEquals(cs);
   }
   public boolean contentEquals(StringBuffer sb) {
      return that.toString().contentEquals(sb);
   }
   public static String copyValueOf(char[] data) {
      return String.copyValueOf(data);
   }
   public static String copyValueOf(
      char[] data, int offset, int count) 
   {
      return String.copyValueOf(data,offset,count);
   }
   public final boolean	endsWith(String suffix) {
      return that.toString().endsWith(suffix); 
   } 
   public boolean equals(Object o) {  
      return that.toString().equals(o);
   } 
   public boolean equalsIgnoreCase(String s2) {
      return that.toString().equalsIgnoreCase(s2); 
   }  
   public static String format(
      Locale l, String fm, Object... args) {
      return String.format(l,fm,args);
   }
   public static String format(
      String fm, Object... args) {
      return String.format(fm, args);
   }
   public final byte[] getBytes() {  
      return that.toString().getBytes();  
   } 
   public byte[] getBytes(String charsetName)
          throws UnsupportedEncodingException
   {
       return that.toString().getBytes(charsetName);
   }
   public void getChars(
      int srcBegin,int srcEnd,  char dst[],int dstBegin
   ) {
      that.toString().getChars(srcBegin, srcEnd, dst, dstBegin);
   }
   public final int hashCode() {  
      return that.toString().hashCode();  
   }
   public int indexOf(int ch) {
      return that.toString().indexOf(ch);
   }
   public int indexOf(int ch, int fromIndex) {
      return that.toString().indexOf(ch, fromIndex);
   }

   public int indexOf(String str) {  
      return that.indexOf(str, 0);   
   }
   public int indexOf(String str, int fromIndex) {
      return that.indexOf(str, fromIndex);
   }
   public String intern() {
      return that.toString().intern();
   }
   public int lastIndexOf(int ch) {
      return that.toString().lastIndexOf(ch);
   }
   public int lastIndexOf(int ch, int fromIndex) {
      return that.toString().lastIndexOf(ch, fromIndex);
   }
   public int lastIndexOf(String str) {
      return that.toString().lastIndexOf(str);
   }
   public int lastIndexOf(String str, int fromIndex) {
      return that.toString().lastIndexOf(str, fromIndex);
   }
   public int length() {  
      return that.toString().length();  
   }
   public boolean matches(String regex) {
      return that.toString().matches(regex);
   }
   public int offsetByCodePoints(
      int index, int codePointOffset
   ) {
      return that.toString()
             .offsetByCodePoints(index,codePointOffset);
   }
   public boolean regionMatches(
      boolean ignoreCase,
      int toffset, String other, int ooffset, int len
   ){
      return that.toString().   
         regionMatches(ignoreCase,toffset, other,ooffset, len);
   } 
   public String replace(char oldChar, char newChar) {
      return that.toString().replace(oldChar, newChar);
   }
   public String replace(
      CharSequence target, CharSequence replacement
   ) {
      return that.toString().replace(target, replacement);
   }
   public String replaceAll(String regex, String replacement) {
      return that.toString()
                 .replaceAll(regex, replacement);
   }
   public final ImuStr replaceFirst(String regex, String replacement) {
     //: 平行於 String.replaceFirst(String, String) {
      return new ImuStr(
         that.toString().replaceFirst(regex, replacement)
      );
   } 
   public final String[] split(String regex) {
      return that.toString().split(regex);
   } 
   public String[] split(String regex, int limit) {
      return that.toString().split(regex, limit);
   }
   public final boolean startsWith(String prefix) {
      return that.toString().startsWith(prefix);
   }
   public boolean startsWith(String prefix, int toffset) {
      return that.toString().startsWith(prefix, toffset);
   }
   public CharSequence subSequence(int start, int end) {
       return that.toString().subSequence(start, end);
   }
   public String substring(int start) {
      return that.toString().substring(start);
   }
   public String substring(int start, int end) {
      return that.toString().substring(start, end);
   }
   public final char[] toCharArray() {
      return that.toString().toCharArray();
   }
   public final ImuStr toLowerCase() {
      return new ImuStr(that.toString().toLowerCase());
   } 
   public final ImuStr toLowerCase(java.util.Locale locale) {
      return new ImuStr(that.toString().toLowerCase(locale));
   } 
   public String toString() {  
      return that.toString();   
   }
   public final ImuStr toUpperCase() {
      return new ImuStr(toString().toUpperCase());
   } 
   public final ImuStr toUpperCase(java.util.Locale locale) {
      return new ImuStr(toString().toUpperCase(locale));
   } 
   public final ImuStr trim() {
      return new ImuStr(that.toString().trim());
   } 
   public static String valueOf(boolean b) {
      return String.valueOf(b);
   }
   public static String valueOf(char c) {
      return String.valueOf(c);
   }
   public static String valueOf(char[] c) {
      return String.valueOf(c);
   }
   public static String valueOf(
      char[] data, int offset, int count
   ) {
      return String.valueOf(data, offset, count);
   }
   public static String valueOf(double d) {
      return String.valueOf(d);
   }
   public static String valueOf(float f) {
      return String.valueOf(f);
   }
   public static String valueOf(int i) {
      return String.valueOf(i);
   }
   public static String valueOf(long l) {
      return String.valueOf(l);
   }
   public static String valueOf(Object obj) {
      return String.valueOf(obj);
   }
   //] -----  依 class String 之排序 ------------

   //===============================================

   //[ -------  ImuStr之增添  ----------------
   public ImuStr(ImuStr sb) {
      if(sb==null) {  that= new StringBuilder("null");   }
      else {  that=new StringBuilder(sb.that.toString()); }
   }
   public ImuStr(char d) {  
      this(new char[]{ d });  
   }
   //---
   protected ImuStr(CharSequence seq) {
      //: 依StringBuilder之ctor, 給子類別用
      that= new StringBuilder(seq);
   }
   protected ImuStr(int capacity) { //: 依StringBuilder之ctor, 給子類別用
      that= new StringBuilder(capacity);
   }
   //------
   public boolean equals(String d2) {  
      return that.equals(d2);  
   }
   public boolean equals(ImuStr d2) {  
      return that.equals(d2.that);
   }
   public boolean equalsIgnoreCase(ImuStr s2) {
      return that.toString().equalsIgnoreCase(s2.that.toString()); 
   } 
   public final int compareTo(ImuStr s2) {
      return that.toString().compareTo(s2.toString());
   }
   public final int compareToIgnoreCase(ImuStr str) {
      return that.toString()
                 .compareToIgnoreCase(str.that.toString()); 
   } 
   public final ImuStr concat(ImuStr str) {
   //  return new ImuStr(
   //     that.toString().concat(str.that.toString())
   //  );
      //[ 改良
      final ImuStr ans=new ImuStr(that.toString());
      ans.that.append(str.that.toString());
      return ans;
   } 
   public final boolean	endsWith(ImuStr suffix) {
      return that.toString().endsWith(suffix.that.toString()); 
   } 
   public final boolean regionMatches(
      boolean ignoreCase, int toffset, ImuStr other, int ooffset, int len
   ) {
      return that.toString().regionMatches(
               ignoreCase, toffset, other.toString(), ooffset, len
             );
   }
   public final boolean startsWith(ImuStr prefix) {
      return toString().startsWith(prefix.toString());
   }
   public final boolean startsWith(ImuStr prefix, int toffset) {
      return toString().startsWith(prefix.toString(), toffset);
   }

   //[-------- implements DuplicableI 
   public MuStr duplicate() {  
      return new MuStr(this); 
   }
   //]-------- implements DuplicableI 

   //[-------- implements PrintableI  
    public final void printTo(TxOStream ooo) throws java.io.IOException {  ooo.p(toString());  }
    public final void widthPrintTo(int w, TxOStream ooo) throws java.io.IOException {  
       ooo.wp(w,that.toString()); 
    }
   //]-------- implements PrintableI  


   //] -------  ImuStr之增添  ----------------

}