//package oai.util;  //: b4 A5.007.G
package tw.fc;

//[-----------------------------------
// Liaw's scheme:
//   0 <= front < MAXSIZE
//   front <= rear <= front+MAXSIZE 
//   logically effective data a[front], a[front+1], ..., a[rear-1]
//   push: a[(rear++)%MAXSIZE]=x;    
//   pop: return a[front++];  must adjust front after pop
//]

public class ArrayQueue<T> {  

   private int MAXSIZE;
   private Object[] a;  
   private int front, rear;

   //-----------------------------
   public ArrayQueue(int initSize) { 
      MAXSIZE=initSize;
      // a=new T[MAXSIZE];  //ERROR: generic array creation
      a=new Object[MAXSIZE];  
      front=0; rear=0;
   } 

   public ArrayQueue() { this(512); }  // default ctor

   //-----------------------------
   @Override public String toString() {
      final StringBuilder sb=new StringBuilder(" [ ");
      sb.append(front).append(",").append(rear).append(": ");
      for(int i=front; i<rear; i++) {
         sb.append(a[i%MAXSIZE]).append(", ");
      }
      sb.append(" ] ");
      return sb.toString();
   }

   //-----------------------------

   public boolean isEmpty() { return (front==rear); }
   public boolean notEmpty() { return (front!=rear); }
   public int size() { return (rear-front); }

   public void push(T x) {  
      if(rear-front>=MAXSIZE) { overflowExtension(); } 
//    if(rear<MAAXSIZE) {  a[rear++]=x;  }
//    else {  a[(rear++)-MAXSIZE]=x;  }
      a[(rear++)%MAXSIZE]=x;
//i.e.  a[rear%MAXSISE]=x;  rear+=1;      
   } 

   public T pop() {  
      if(front==rear) throw new IllegalArgumentException("\n\nqueue underflow"); 
      @SuppressWarnings("unchecked") T ans=(T)a[front++]; 
      if(front>=MAXSIZE) {  
         front-=MAXSIZE;   rear-=MAXSIZE;  
      }      
      return ans;
   }

   public T peek() {  
      if(front==rear) throw new IllegalArgumentException("\n\nqueue underflow");  
      @SuppressWarnings("unchecked") T ans= (T)a[front]; 
      return ans; 
   }

   private void overflowExtension() {
//D	  System.out.println("overflow at: front="+front+", rear="+rear);
      Object[] old=a;
      final int newSize=MAXSIZE*2;
      // a=new T[newSize];    //ERROR: generic array creation
      a=new Object[newSize];
      for(int i=front, j=0; i<rear; i++, j++) {
      	 a[j]=old[i%MAXSIZE]; 
      }	
      MAXSIZE=newSize;
      rear-= front;  front=0;
//D 
      System.out.println("after expanded, front="+front+", rear="+rear);
   }	

   public final void clear() {  
      front=0; rear=0;
   }

   //---------------------------------------------------
   // demo and test

   static void test1() {
      System.out.println("\n ---- test1 ----");
      ArrayQueue<Integer> s1=new ArrayQueue<Integer>();
      s1.push(10);   s1.push(20);  s1.push(30);
      System.out.println(s1);
      int x=(Integer)s1.pop();
      System.out.println(x);
      System.out.println(s1);
      s1.clear();
      System.out.println(s1);
/*
 ---- test1 ----
 [ 0,3: 10, 20, 30,  ]
10
 [ 1,3: 20, 30,  ]
 [ 0,0:  ]

*/
   }   		
   static void test2() {
      System.out.println("\n ---- test2 ----");
      ArrayQueue<Integer> s1=new ArrayQueue<Integer>();
      s1.push(10);   s1.push(20);  
      System.out.println(s1);
      s1.pop();  System.out.println(s1);
      s1.pop();  System.out.println(s1);
      s1.pop();   //: client program error: underflow
/*
 ---- test2 ----
 [ 0,2: 10, 20,  ]
 [ 1,2: 20,  ]
 [ 2,2:  ]
Exception in thread "main" java.lang.IllegalArgumentException:

queue underflow
        at oai.util.ArrayQueue.pop(ArrayQueue.java:52)
        at oai.util.ArrayQueue.test2(ArrayQueue.java:113)
        at oai.util.ArrayQueue.main(ArrayQueue.java:174)
*/
   }   		
   static void test3() {
      System.out.println("\n ---- test3 ----");
      ArrayQueue<Integer> s1=new ArrayQueue<Integer>();
      s1.push(10);  s1.push(20);   s1.push(30);   
      s1.pop();  System.out.println(s1);
      while( ! s1.isEmpty() ) {
         s1.pop();  System.out.println(s1);
      }
/*
 ---- test3 ----
 [ 1,3: 20, 30,  ]
 [ 2,3: 30,  ]
 [ 3,3:  ]
*/
   }   		
   static void test4() {
      System.out.println("\n ---- test4 ----");
      ArrayQueue<Integer> s1=new ArrayQueue<Integer>(5);
      for(int i=1; i<=30; i++) {
         s1.push(i);  System.out.println(s1);
      }
   }   		
/*
// set MAXSIZE=5;
 ---- test4 ----
 [ 0,1: 1,  ]
 [ 0,2: 1, 2,  ]
 [ 0,3: 1, 2, 3,  ]
 [ 0,4: 1, 2, 3, 4,  ]
 [ 0,5: 1, 2, 3, 4, 5,  ]
after expanded, front=0, rear=5
 [ 0,6: 1, 2, 3, 4, 5, 6,  ]
... 
after expanded, front=0, rear=10
 [ 0,11: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,  ]
...
after expanded, front=0, rear=20
 [ 0,21: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
21,  ]
...
 [ 0,30: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
21, 22, 23, 24, 25, 26, 27, 28, 29, 30,  ]
*/

   public static void main(String[] VOID) {
      test1();
    //test2();
      test3();
      test4();
   }

}

