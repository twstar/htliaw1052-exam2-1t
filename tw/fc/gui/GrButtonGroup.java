package tw.fc.gui;

import java.util.ArrayList;

public class GrButtonGroup{
   private ArrayList<GrButton> tButtomM = new ArrayList<GrButton>();

   public void add(GrButton tmp){
      tButtomM.add(tmp);
   }

   public void manage(GrButton tmp){
      for(int i=0; i<tButtomM.size(); i++){
         final GrButton button = tButtomM.get(i);
         if(button == tmp){
            button.setClick(false);
            button.setState(true);
         }
         else{
            button.setClick(true);
            button.setState(false);
         }
      }
   }
}