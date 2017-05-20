package tw.fc.re;
import java.lang.annotation.Target; 
import java.lang.annotation.ElementType; 

@Target(ElementType.METHOD)
public @interface ImuMethod {
//: 標示一個instance method不會變更this所指的物件

}