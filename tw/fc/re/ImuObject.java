package tw.fc.re;
import java.lang.annotation.Target; 
import java.lang.annotation.ElementType; 

@Target(ElementType.TYPE)
public @interface ImuObject {
//: 標示這個class或interface中
//: 所有instance method都應該標示 @ImuMethod  

}
