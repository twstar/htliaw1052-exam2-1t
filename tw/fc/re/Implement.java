package tw.fc.re;
import java.lang.annotation.Target; 
import java.lang.annotation.ElementType; 

@Target(ElementType.METHOD)
public @interface Implement {
//: �Хܤ@��instance method ��@�Y��interface
   //Class value(); //: A31208X add type args
   Class<?> value();
   
}