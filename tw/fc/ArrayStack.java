// package oai.util;   //: b4 A5.007.G
package tw.fc;

public class ArrayStack<T> {  //: A11112L 

   protected int MAXSIZE;
   protected Object[] a;
   protected int sp;

   //-----------------------------
   public ArrayStack(int initSize) { //: A20823L
  //D System.out.println("size:"+initSize);
      MAXSIZE=initSize;
      // a=new T[MAXSIZE];  //ERROR: generic array creation
      a=new Object[MAXSIZE];  
      sp=0;
   } 
   public ArrayStack() { this(512); }  // default ctor

   //-----------------------------
   @Override public String toString() {
      StringBuilder sb=new StringBuilder(" [ ");
      for(int i=0; i<sp; i++) {
         sb.append(a[i]);  sb.append(", ");
      }
      sb.append(" ]TOP ");
      return sb.toString();
   }
   //-----------------------------
   public boolean isEmpty() { return (sp<=0); }
   public boolean notEmpty() { return (sp>0); }
   public final int size() {   return sp;   }

   public void push(T x) {  
      if(sp>=MAXSIZE) { overflowExtension(); } 
      a[sp++]=x; // a[sp]=x; sp+=1;  
   } 

   //public Object pop() {  //: b4 A40320  
   public T pop() {  
      //if(sp<=0) throw new RuntimeException("\n\nstack underflow"); //: b4 A30731 
      if(sp<=0) throw new IllegalArgumentException("\n\nstack underflow");  
      @SuppressWarnings("unchecked") T ans= (T) a[--sp];
      a[sp]=null;    //: help garbage collection
      return ans;  
   }

   //public Object peek() {   //: b4 A40320
   public T peek() {  
      //if(sp<=0) throw new RuntimeException("\n\nstack underflow"); //: b4 A30731 
      if(sp<=0) throw new IllegalArgumentException("\n\nstack underflow");  
      @SuppressWarnings("unchecked") T ans= (T)a[sp-1]; 
      return ans;
   }

   private void overflowExtension() {
  //D System.out.println("overflow extension");
      Object[] old=a;
      final int newSize=MAXSIZE*2;
      a=new Object[newSize];
      for(int i=0; i<MAXSIZE; i++) {
         a[i]=old[i]; 
      }	
      MAXSIZE=newSize;
   }	

   public void clear() {  
      for(int i=0; i<sp; i++) {
         a[i]=null;  //: help garbage collection
      } 
      sp=0;  
   }

   //---------------------------------------------------
   // demo and test

   static void test1() {
      System.out.println("\n ---- test1 ----");
      ArrayStack<Integer> s1=new ArrayStack<Integer>();
      s1.push(10);   s1.push(20);  s1.push(30);
      System.out.println(s1);
      int x=(Integer)s1.pop();
      System.out.println(x);
      System.out.println(s1);
      s1.clear();
      System.out.println(s1);
/*
 ---- test1 ----
 [ 10, 20, 30,  ]
30
 [ 10, 20,  ]
 [  ]

*/
   }   		
   static void test2() {
      System.out.println("\n ---- test2 ----");
      ArrayStack<Integer> s1=new ArrayStack<Integer>();
      s1.push(10);   s1.push(20);  
      System.out.println(s1);
      s1.pop();  System.out.println(s1);
      s1.pop();  System.out.println(s1);
      s1.pop();   //: client program error: underflow
/*
 ---- test2 ----
 [ 10, 20,  ]
 [ 10,  ]
 [  ]
Exception in thread "main" java.lang.RuntimeException:

stack underflow
        at oai.util.ArrayStack.pop(ArrayStack.java:30)
        at oai.util.ArrayStack.test2(ArrayStack.java:83)
        at oai.util.ArrayStack.main(ArrayStack.java:121)
*/
   }   		
   static void test3() {
      System.out.println("\n ---- test3 ----");
      ArrayStack<Integer> s1=new ArrayStack<Integer>();
      s1.push(10);  s1.push(20);   s1.push(30);   
      s1.pop();  System.out.println(s1);
      while( ! s1.isEmpty() ) {
         s1.pop();  System.out.println(s1);
      }
/*
 ---- test3 ----
 [ 10, 20,  ]
 [ 10,  ]
 [  ]
*/
   }   		
   static void test4() {
      System.out.println("\n ---- test4 ----");
      ArrayStack<Integer> s1=new ArrayStack<Integer>(5);
      for(int i=1; i<=30; i++) {
         s1.push(i);  System.out.println(s1);
      }
   }   
/*
 ---- test4 ----
size:5
 [ 1,  ]
 [ 1, 2,  ]
 [ 1, 2, 3,  ]
 [ 1, 2, 3, 4,  ]
 [ 1, 2, 3, 4, 5,  ]
overflow extension
 [ 1, 2, 3, 4, 5, 6,  ]
...
overflow extension
 [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,  ]
...
overflow extension
 [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,  ]
...
 [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22
, 23, 24, 25, 26, 27, 28, 29, 30,  ]
*/

		
   public static void main(String[] VOID) {
      test1();
    //  test2();
      test3();
      test4();
   }

}

