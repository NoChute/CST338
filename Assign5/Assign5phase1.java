import javax.swing.*;
import java.awt.*;

public class Assign5phase1 {
   // static for the 57 icons and their corresponding labels
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back of card img
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   static void loadCardIcons() {
      String filename; 
      
      int cardIndex = 0;
      
      for (int suit = 0; suit < 4; suit++) {
         for (int value = 0; value < 14; value++) {
            filename = "images\\" + turnIntIntoCardValue(value)
               + turnIntIntoCardSuit(suit) + ".gif";
            icon[cardIndex++] = new ImageIcon(filename);
         }
      }      
      // Finally add the back of card icon
      icon[cardIndex] = new ImageIcon("images\\BK.gif");
   }
   
   static String turnIntIntoCardValue(int k) {
      String output = "";
      switch (k){
         case(0):
            output = "X";
            break;
         case(1):
            output = "A";
            break;
         case(2):
            output = "2";
            break;
         case(3):
            output = "3";
            break;
         case(4):
            output = "4";
            break;
         case(5):
            output = "5";
            break;
         case(6):
            output = "6";
            break;
         case(7):
            output = "7";
            break;
         case(8):
            output = "8";
            break;
         case(9):
            output = "9";
            break;
         case(10):
            output = "T";
            break;
         case(11):
            output = "J";
            break;
         case(12):
            output = "Q";
            break;
         case(13):
            output = "K";
            break;
         default:
            // Do nothing                      
      }
      return output;
   }
   
   static String turnIntIntoCardSuit(int j) {
      String output = "";
      switch (j){
         case (0):  
            output = "C";
            break;
         case (1):  
            output = "H";
            break;
         case (2):
            output = "D";
            break;
         case (3):
            output = "S";
            break;
         default:
            //do nothing
      }
      return output;   
   }
   
   public static void main(String[] args) {
      
      int k;
      
      loadCardIcons();
      
      JFrame cardRoom = new JFrame("Card Room");
      cardRoom.setSize(1150, 650);
      cardRoom.setLocationRelativeTo(null);
      cardRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      cardRoom.setLayout(layout);
      
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++) {
         labels[k] = new JLabel(icon[k]);
      }
      
      for (k = 0; k < NUM_CARD_IMAGES; k++) {
         cardRoom.add(labels[k]);
      }
      
      cardRoom.setVisible(true);
            
   } 
   
}