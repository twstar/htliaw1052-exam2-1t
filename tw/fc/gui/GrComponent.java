package tw.fc.gui;

//[ 這個 class 是為了讓同package的GrPanel
//[ 在 paintAddedJComponents(Graphics g) 中有權力呼叫paintComponent
public abstract class GrComponent 
              extends javax.swing.JComponent
{
   private static final long serialVersionUID = 2014120806L;

   @Override protected abstract 
   void paintComponent(java.awt.Graphics g);
   //: 重宣告為abstract以去除JComponet的實作
}