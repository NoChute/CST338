
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Assig5Part3 {
   
   static final int NUM_PLAYERS = 2;
   static final int NUM_CARDS_PER_HAND = 5;
   static final int COMPUTER = 0;
   static final int PLAYER = 0;
   
   private JLabel[] computerLabels   = new JLabel[NUM_CARDS_PER_HAND];
   private JButton[] humanLabels     = new JButton[NUM_CARDS_PER_HAND];  
   private JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS]; 
   private JLabel[] playLabelText    = new JLabel[NUM_PLAYERS]; 
   private GUICard  guicard          = new GUICard();
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
      
      Assig5Part3 newgame        = new Assig5Part3();
      newgame.myCardTable.setVisible(true);
      
   }
   
   public Assig5Part3() {
  
      this.highCardGame.deal();
      this.highCardGame.getHand(PLAYER).sort();
      
      this.myCardTable.setLocationRelativeTo(null);
      this.myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      this.playedCardLabels[0] = new JLabel(guicard.getIcon(null));
      this.playedCardLabels[1] = new JLabel(guicard.getIcon(null));
      this.playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      this.playLabelText[1] = new JLabel("You", JLabel.CENTER);
      
      updateGameTable();
      
      this.myCardTable.pack();
      
   }

   private void updateGameTable() {
      
      Hand computerHand = this.highCardGame.getHand(COMPUTER);
      Hand playerHand = this.highCardGame.getHand(PLAYER);
      
      // Update JLabels for Computer.
      myCardTable.pnlComputerHand.removeAll();
      for (int x = 0; x < computerHand.getNumCards(); x++) {
         myCardTable.pnlComputerHand.add(new JLabel(guicard.getBackCardIcon()));
      }
      
      //Draw Play Area
      myCardTable.cmpPlayArea.removeAll();
      myCardTable.cmpPlayArea.add(playedCardLabels[0]);
      myCardTable.hmnPlayArea.removeAll();
      myCardTable.hmnPlayArea.add(playedCardLabels[1]);
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);
      
      
      //Update JButtons for Player
      myCardTable.pnlHumanHand.removeAll();
      
      for (int x = 0; x < playerHand.getNumCards(); x++) {
         humanLabels[x] = new JButton(guicard.getIcon(playerHand.inspectCard(x)));
         humanLabels[x].setActionCommand(String.valueOf(x));
         humanLabels[x].addActionListener( new CardListener() );
         myCardTable.pnlHumanHand.add(humanLabels[x]);
      }
      
      myCardTable.pnlPlayArea.validate();
      myCardTable.pnlPlayArea.repaint();
      myCardTable.pnlHumanHand.validate();
      myCardTable.pnlHumanHand.repaint();
     
   }
   
   private class CardListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         Card cardPlayed = highCardGame.getHand(PLAYER).playCard(
               Integer.valueOf(e.getActionCommand()));
         playerPlays(cardPlayed);         
      }
   }
   
   private void buildPlayArea () {
      
    //  this.gametable.pnlPlayArea
      
      
   }
   
   private void playerPlays(Card cardPlayed) {
      playedCardLabels[1] = new JLabel(guicard.getIcon(cardPlayed));
      updateGameTable();
   }
   
   private void decidePlayWinner(Card playerCard, Card computerCard) {
      
   }
   
   private void decideGameWinner() {
      
   }
   
   private void computerPlays(Card cardPlayerPlayed) {
      
   }
   
}