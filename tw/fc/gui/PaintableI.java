package tw.fc.gui;
   import java.awt.Graphics;

//===================================
//
// 實作這個interface的class, 可以將它自己畫到Graphics去
//
public interface PaintableI {
   public void paintTo(Graphics g) ;
}