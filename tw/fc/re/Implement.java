package tw.fc.re;
import java.lang.annotation.Target; 
import java.lang.annotation.ElementType; 

@Target(ElementType.METHOD)
public @interface Implement {
//: 標示一個instance method 實作某個interface
   //Class value(); //: A31208X add type args
   Class<?> value();
   
}