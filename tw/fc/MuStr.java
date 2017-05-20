//
// 功能平行於 StringBuilder, 可以setBy, scanFrom. 加了幾個ctor
// 可改用 StringRef
//
package tw.fc;

import java.io.IOException;

public class MuStr extends ImuStr 
       implements SetableI<ImuStr>, ScannableI
{

    //[ --------- 依StringBuilder 排序  -------------
    public MuStr() {  super();   }
    public MuStr(CharSequence seq) {  super(seq);   }
    public MuStr(int capacity) {  super(capacity);   }
    public MuStr(String str) {  super(str);    }
    // MuStr 添加的都放在檔末 

    //--------------------------------------------
    public MuStr append(boolean b) {
       that.append(b);    return this;
    }
    public MuStr append(char c) {
       that.append(c);  return this;
    }
    public MuStr append(char[] arr) { 
       that.append(arr);  return this;
    }
    public MuStr append(
       char[] arr, int srcBegin, int len
    ) {
       that.append(arr, srcBegin, len);  return this;
    }
    public MuStr append(CharSequence c) { 
       that.append(c);  return this;
    }
    public MuStr append(
       CharSequence arr, int start, int end
    ) {
       that.append(arr, start, end);  return this;
    }
    public MuStr append(double d) {
       that.append(d);   return this;
    }
    public MuStr append(float f) {
       that.append(f);   return this;
    }
    public MuStr append(int i) {
       that.append(i);   return this;
    }
    public MuStr append(long l) {
       that.append(l);   return this;
    }
    public MuStr append(Object obj) {
       that.append(obj);   return this;
    }
    public MuStr append(String str) {
       that.append(str);   return this;
    }
    public MuStr append(StringBuffer str) {
       that.append(str);   return this;
    }
    public MuStr appendCodePoint(int codePoint) {
       that.appendCodePoint(codePoint);   return this;
    }
    public int capacity() {  return that.capacity();  }
    public char charAt(int index) {
       return that.charAt(index); 
    }
    public int codePointAt(int index) {
       return that.codePointAt(index);
    }
    public int codePointBefore(int index) {
       return that.codePointBefore(index);  
    }
    public int codePointCount(int beginIndex, int endIndex) {
       return that.codePointCount(beginIndex, endIndex);
    }
    public MuStr delete(int start, int end) {
       that.delete(start, end);  return this;
    }
    public MuStr deleteCharAt(int index) {
       that.deleteCharAt(index);  return this;
    }
    public void ensureCapacity(int minimumCapacity) {
       that.ensureCapacity(minimumCapacity);
    }
    public void getChars(
       int srcBegin, int srcEnd,
       char[] dst, int dstBegin
    ) {
       that.getChars(srcBegin, srcEnd, dst, dstBegin);
    }
    public int indexOf(String str) {
       return that.indexOf(str);
    }
    public int indexOf(String str, int fromIndex){
       return that.indexOf(str, fromIndex);
    }
    public MuStr insert(int index, boolean b) {
       that.insert(index, b);  return this;
    }
    public MuStr insert(int index, char c) {
       that.insert(index, c);  return this;
    }
    public MuStr insert(int index, char[] arr) {
       that.insert(index, arr);  return this;
    }
    public MuStr insert(
       int index,  char[] arr,int offset,int len) 
    {
       that.insert(index, arr, offset, len);  return this;
    }
    public MuStr insert(int dstOffset, CharSequence s) {
       that.insert(dstOffset, s);  return this;
    }
    public MuStr insert(
       int dstOffset, CharSequence s, int start, int end
    ) {
       that.insert(dstOffset, s, start, end);  return this;
    }
    public MuStr insert(int index, double d) {
       that.insert(index, d);  return this;
    }
    public MuStr insert(int index, float f) {
       that.insert(index, f);  return this;
    }
    public MuStr insert(int index, int i) {
       that.insert(index, i);  return this;
    }
    public MuStr insert(int index, long l) {
       that.insert(index, l);  return this;
    }
    public MuStr insert(int index, Object obj) {
       that.insert(index, obj);  return this;
    }
    public MuStr insert(int index, String str) {
       that.insert(index, str);  return this;
    }

    public int lastIndexOf(String str) {
       return that.lastIndexOf(str);
    }
    public int lastIndexOf(String str, int fromIndex) {
       return that.lastIndexOf(str, fromIndex);
    }
    public int length() {
       return that.length();
    }
    public int offsetByCodePoints(
       int index, int codePointOffset
    ) {
       return that.offsetByCodePoints(index, codePointOffset);
    }
    public MuStr replace(
       int start, int end, String str
    ) {
       that.replace(start, end, str);  return this;
    }
    public MuStr reverse() {
       that.reverse();  return this;
    }
    public void setCharAt(int index, char ch) {
       that.setCharAt(index, ch); 
    }
    public void setLength(int newLength) {
       that.setLength(newLength); 
    }
    public CharSequence subSequence(int start, int end) {
       return that.subSequence(start, end);
    }
    public String substring(int start) {
       return that.substring(start);
    }
    public String substring(int start, int end) {
       return that.substring(start, end);
    }
    public String toString() {
       return that.toString(); 
    }
    public void trimToSize() {
       that.trimToSize();
    }
    //] --------- 依StringBuilder 排序  -------------

    //[ -----------  MuStr 添加   ---------------
    public MuStr(ImuStr sb) {  //:加  
       this(sb.toString()); 
    } 
    public MuStr(char[] arr, int srcBegin, int len) { //: 加
       super();
       that.append(arr, srcBegin, len);
    }
    //----
    public MuStr append(ImuStr sb) {
       that.append(sb.that.toString()); 
       return this;
    }
    //] -----------  MuStr 添加   --------------- 


    //[ ----- implements SetableI --------------
    public final MuStr setBy(ImuStr s) { 
       that.delete(0, that.length());
       that.append(s.that.toString());
       return this;
    }
    //] -----------------------------------------
    public final MuStr setBy(String s) { 
       that.delete(0, that.length());
       that.append(s);
       return this;
    }
    public final MuStr setBy(char[] chars) {  
       that.delete(0, that.length());
       that.append(chars);
       return this;
    }

    //[ -----  implements ScannableI  ------------
    public final void scanFrom(TxIStream iii) throws IOException {
       final String s= iii.getString();
       that.delete(0, that.length());
       that.append(s);
    }
    //] -----------------------------------------


}
