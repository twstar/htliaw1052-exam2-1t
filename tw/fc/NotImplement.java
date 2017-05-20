package tw.fc ;

//*************   NotImplement   ************************//

public class NotImplement extends RuntimeException {

   private static final long serialVersionUID= 2006021218L;

    public        NotImplement() {
       super("\n --- Sorry, Not Implement Yet ---");
    }
    public        NotImplement(String where) {
       super(
          "\n" + where + "\n : --- Sorry, Not Implement Yet ---"
       );
    }
}
