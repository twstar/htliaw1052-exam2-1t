import tw.fc.*;
import static tw.fc.Std.cout;
import static tw.fc.Std.cin;
import java.io.IOException;

public class TestDW {
    static void test1(){
        DW d1 = new DW(2);
        DW d2 = new DW(4);
        DW d3 = new DW(0);
        cout.p(d1).p(d2).pn(d3);
    }
    
    static void test2(){
        DW d = new DW();
        cin.g("Type an id: ", d);
        cout.p("The id you typed is: ").pn(d);
        
        cin.g("Type an id: ", d);
        cout.p("The id you typed is: ").pn(d);
        
        cin.g("Type an id: ", d);
        cout.p("The id you typed is: ").pn(d);
    }
    
    static void test3() throws IOException{
        TxOFStream fout = new TxOFStream("data1.txt");
        int max = 31;
        for( int i=1 ; i <= max ; i++ ){
            int j = i % 7; //to get the remainer. i.g. 2/7=2...2, 7/7=1...0.
            DW d = new DW(j);
            fout.p(d);
        }
        fout.close();
    }
    
    static void test4() throws IOException{
        TxIFStream fin = new TxIFStream("data1.txt");
        DW d = new DW();
        while(!fin.skipWS().probeEOF()){
            fin.skipWS().g(d);
            cout.p(d).p(", ");
        }
        cout.p("\b\b.");
    }
    
    public static void main(String[] Facebook) throws IOException{
//        test1();
//        test2();
        test3();
        test4();
    } 
}
