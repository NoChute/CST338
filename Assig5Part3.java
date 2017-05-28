package assignment5;
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Assig5Part3 {
   
   static final int NUM_PLAYERS = 2;
   static final int NUM_CARDS_PER_HAND = 5;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JButton[] humanLabels = new JButton[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 

   
   static final int COMPUTER = 0;
   static final int PLAYER = 0;
   
   private int numPacksPerDeck        = 1;
   private int numJokersPerPack       = 0;
   private int numUnusedCardsPerPack  = 0;
   private Card[] unusedCardsPerPack  = null;
   
   private CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
   
   private CardTable myCardTable = new CardTable("CardTable", 
            NUM_CARDS_PER_HAND, NUM_PLAYERS); 
   
   
   public static void main(String[] args) {
      
      Assig5Part3 program = new Assig5Part3();
      GUICard guicard = new GUICard(); 
      
      //Computer is player 0, Human is player 1
      program.highCardGame.deal();
      
      //establish main frame in which program will run
      
      program.myCardTable.setSize(1000, 600);
      program.myCardTable.setLocationRelativeTo(null);
      program.myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      program.updateGameTable(program.myCardTable, program.highCardGame);
        
     
      playedCardLabels[0] = new JLabel(guicard.getIcon(null));
      playedCardLabels[1] = new JLabel(guicard.getIcon(null));
      
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);
      
      program.myCardTable.pnlPlayArea.add(playLabelText[0]);
      program.myCardTable.pnlPlayArea.add(playLabelText[1]);
      
      program.myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      program.myCardTable.pnlPlayArea.add(playedCardLabels[1]);
      
      //program.myCardTable.pack();
      program.myCardTable.setVisible(true);
   }
   
   private Card generateRandomCard() {
      return new Deck().inspectCard(new Random().nextInt(56));
   }

   public void updateGameTable(CardTable gametable, CardGameFramework cardgame, Card cardPlayed) {
      
      Hand computerHand = cardgame.getHand(COMPUTER);
      Hand playerHand = cardgame.getHand(PLAYER);
      GUICard guicard = new GUICard();
      
      // Update JLabels for Computer.
      gametable.pnlComputerHand.removeAll();
      
      for (int x = 0; x < computerHand.getNumCards(); x++) {
         gametable.pnlComputerHand.add(new JLabel(guicard.getBackCardIcon()));
      }
      
      playerHand.sort();
      //Update JButton for Player
      gametable.pnlHumanHand.removeAll();
      for (int x = 0; x < playerHand.getNumCards(); x++) {
         humanLabels[x] = new JButton(guicard.getIcon(playerHand.inspectCard(x)));
         humanLabels[x].setActionCommand(String.valueOf(x));
         humanLabels[x].addActionListener( new CardListener() );
         gametable.pnlHumanHand.add(humanLabels[x]);
         
      }
      
      gametable.pnlPlayArea.remove(playedCardLabels[0]);
      gametable.pnlPlayArea.remove(playedCardLabels[1]);
      
      playedCardLabels[0] = new JLabel(guicard.getIcon(cardPlayed));
      playedCardLabels[1] = new JLabel(guicard.getIcon(cardPlayed));
      
      myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      myCardTable.pnlPlayArea.add(playedCardLabels[1]);
      
      myCardTable.pnlPlayArea.validate();
      myCardTable.pnlPlayArea.repaint();
      
      gametable.pnlHumanHand.validate();
      gametable.pnlHumanHand.repaint();
      
      
   }
   
public void updateGameTable(CardTable gametable, CardGameFramework cardgame) {
      
      Hand computerHand = cardgame.getHand(COMPUTER);
      Hand playerHand = cardgame.getHand(PLAYER);
      GUICard guicard = new GUICard();
      
      // Update JLabels for Computer.
      gametable.pnlComputerHand.removeAll();
      
      for (int x = 0; x < computerHand.getNumCards(); x++) {
         gametable.pnlComputerHand.add(new JLabel(guicard.getBackCardIcon()));
      }
      
      playerHand.sort();
      //Update JButton for Player
      gametable.pnlHumanHand.removeAll();
      for (int x = 0; x < playerHand.getNumCards(); x++) {
         humanLabels[x] = new JButton(guicard.getIcon(playerHand.inspectCard(x)));
         humanLabels[x].setActionCommand(String.valueOf(x));
         humanLabels[x].addActionListener( new CardListener() );
         gametable.pnlHumanHand.add(humanLabels[x]);
         
      }

      gametable.pnlHumanHand.validate();
      gametable.pnlHumanHand.repaint();
      
     
   }
   
   private class CardListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         Card cardPlayed = highCardGame.getHand(PLAYER).playCard(Integer.valueOf(e.getActionCommand()));
         playerPlays(cardPlayed);
         updateGameTable(myCardTable, highCardGame, cardPlayed);
      }
   }
   
   private void playerPlays(Card cardPlayed) {
      
   }
}
