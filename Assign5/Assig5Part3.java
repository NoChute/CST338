package assignment5;
import java.util.Random;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//import assignment2and1.Card;
//import assignment2and1.Deck;


public class Assig5Part3 {
   
   static int NUM_PLAYERS = 2;
   static int NUM_CARDS_PER_HAND = 5;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
   
   

   public static void main(String[] args) {
      
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;
      
      int x;

      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      highCardGame.deal();
      
      GUICard guicard = new GUICard();
      //establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // Computer and Human Labels
      for (x = 0; x < NUM_CARDS_PER_HAND; x++) {
         computerLabels[x] = new JLabel(guicard.getBackCardIcon());
      }
      
      for (x = 0; x < NUM_CARDS_PER_HAND; x++) {
         humanLabels[x] = new JLabel(guicard.getIcon(highCardGame.getHand(1).inspectCard(x)));
         //humanLabels[x].setBorderPainted(false);
      }
      
      // ADD LABELS TO PANELS -----------------------------------------
      for (x = 0; x < NUM_CARDS_PER_HAND; x++) {
         myCardTable.pnlHumanHand.add(humanLabels[x]);
      }
      
      for (x = 0; x < NUM_CARDS_PER_HAND; x++) {
         myCardTable.pnlComputerHand.add(computerLabels[x]);
      }
      
      // and two random cards in the play region (simulating a computer/hum ply)
      //for (JLabel humanLabel : humanLabels) {
      //   myCardTable.pnlHumanHand.add(humanLabel);
      //}
      for(x = 0; x < NUM_PLAYERS; x++) {
         playedCardLabels[x] = new JLabel(GUICard.getIcon(generateRandomCard()));
      }
      
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);
      
      for (x = 0; x < NUM_PLAYERS; x++) {
         myCardTable.pnlPlayArea.add(playedCardLabels[x]);
      }
      
      
      //myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      //myCardTable.pnlPlayArea.add(playedCardLabels[1]);
      
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);
      
      // show everything to the user
      //myCardTable.pack();
      myCardTable.setVisible(true);
   }

   static Card inspectCard() {
      return new Deck().inspectCard(new Random().nextInt(56));
   }
   static Card generateRandomCard() {
      return new Deck().inspectCard(new Random().nextInt(56));
   }

}
