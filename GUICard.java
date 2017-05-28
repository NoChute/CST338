package assignment5;

import javax.swing.*;
//import java.awt.*;
//import java.util.*;

public class GUICard {

   private static final int SUITS = 4;
   private static final int VALUES = 14;
   private static Icon[][] iconCards = new ImageIcon[VALUES][SUITS];
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   
   private static String[] values = { "X", "2", "3", "4", "5", "6", "7",
                                      "8", "9", "T", "J", "Q", "K", "A"};
   
   public GUICard() {
      if (!iconsLoaded) {        
         loadCardIcons();
      }
   }
   
   static private void loadCardIcons() {
      for(int suit = 0; suit < SUITS; suit++) {
         for (int value = 0; value < VALUES; value++) {
            String iconFile = "images\\" + turnIntIntoCardValue(value) +
               turnIntIntoCardSuit(suit) + ".gif";
            iconCards[value][suit] = new ImageIcon(iconFile);
         }
      }
      iconBack = new ImageIcon("images\\BK.gif");
      iconsLoaded = true;;
   }
   
   static private String turnIntIntoCardValue(int k) {
   // Returns a one character string based upon the int value passed
      String output = "";
      if (0 <= k && k < VALUES) {
         output = values[k];
      }                    
      return output;
   }

   static private String turnIntIntoCardSuit(int j) {
   // Returns a one character String based upon the int passed
      String output = "";
      String[] suits = { "D", "H", "S", "C" };
      if (0 <= j && j < SUITS) {
         output = suits[j];
      }
      return output;   
   }
   
   static private int valueAsInt(Card card) {
      char valueAsChar = card.getValue();
      int valueAsInt = -1;
      for (int i = 0; i < VALUES; i++) {
         if (values[i].charAt(0) == valueAsChar) {
            valueAsInt = i;
            break;
         }
      }
      
      return valueAsInt;
   }
   
   static int suitAsInt(Card card) {
      int suitAsInt = -1;
      switch (card.getSuit()) {
         case DIAMONDS:
            suitAsInt = 0;
            break;
         case HEARTS:
            suitAsInt = 1;
            break;
         case SPADES:
            suitAsInt = 2;
            break;
         case CLUBS:
            suitAsInt = 3;
            break;
         default:
          // Do nothing
      }
      return suitAsInt;
   }
   
   static public Icon getIcon(Card card) {
      if(iconsLoaded && !card.getErrorFlag() ) {
         return iconCards[valueAsInt(card)][suitAsInt(card)];
      } else {
         return null;
      }     
   }
   
   static public Icon getBackCardIcon() {
      if (iconsLoaded) {
         return iconBack;
      } else {
         return null;
      }
   }
   
}