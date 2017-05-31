
import javax.swing.*;
import java.awt.*;

public class Assign5phase1 {
   // static for the 57 icons and their corresponding labels
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back of card img
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   static final int SUITS = 4; 
   static final int VALUES = 14;
   
   static void loadCardIcons() {
      String filename; 
      
      int cardIndex = 0; // Index for icon[]. Start at the first entry
      
      for (int suit = 0; suit < SUITS; suit++) {
         for (int value = 0; value < VALUES; value++) {
            filename = "images\\" + turnIntIntoCardValue(value)
               + turnIntIntoCardSuit(suit) + ".gif";
            // Add the image to icon[], then increment cardIndex
            icon[cardIndex++] = new ImageIcon(filename);
         }
      }      
      // Finally add the back of card icon
      icon[cardIndex] = new ImageIcon("images\\BK.gif");
   }
   
   static String turnIntIntoCardValue(int k) {
   // Returns a one character string based upon the int value passed
      String output = "";
      String[] values = { "X", "2", "3", "4", "5", "6", "7", 
                          "8", "9", "T", "J", "Q", "K", "A"};
      if (0 <= k && k < VALUES) {
         output = values[k];
      }                    
      return output;
   }
   
   static String turnIntIntoCardSuit(int j) {
   // Returns a one character String based upon the int passed
      String output = "";
      String[] suits = { "C", "H", "D", "S" };
      if (0 <= j && j < SUITS) {
         output = suits[j];
      }
      return output;   
   }
   
   public static void main(String[] args) {
      
      loadCardIcons();
      
      // Create the Card Room
      JFrame cardRoom = new JFrame("Card Room");
      cardRoom.setSize(1150, 650);
      cardRoom.setLocationRelativeTo(null);
      cardRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // Define a layout, and add it to cardRoom
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      cardRoom.setLayout(layout);
      
      // Create a JLabel for each card to be displayed
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      
      // Create and add NUM_CARD_IMAGES JLabels to our JFrame
      for (int k = 0; k < NUM_CARD_IMAGES; k++) {
         cardRoom.add(new JLabel(icon[k]));
      }
      
      // Show our work
      cardRoom.setVisible(true);
            
   } 
   
}