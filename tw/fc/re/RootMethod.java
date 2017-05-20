package tw.fc.re;
import java.lang.annotation.Target; 
import java.lang.annotation.ElementType; 

@Target(ElementType.METHOD)
public @interface RootMethod {
//: 標示一個instance method不可override superclass的方法.
//: RootMethod 與 Override 互逆

}