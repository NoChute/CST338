
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Assig5Part3 {
   
   static final int NUM_PLAYERS = 2;
   static final int NUM_CARDS_PER_HAND = 5;
   static final int COMPUTER = 0;
   static final int PLAYER = 1;
   
   private JLabel[] computerLabels   = new JLabel[NUM_CARDS_PER_HAND];
   private JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS]; 
   private JLabel[] playLabelText    = new JLabel[NUM_PLAYERS]; 
   private JButton[] humanLabels     = new JButton[NUM_CARDS_PER_HAND]; 
   
   private JPanel hmnPlayArea;
   private JPanel cmpPlayArea;
   private JPanel pnlComputerHandTop;
   private JPanel pnlComputerHandBtm;
   private JPanel pnlHumanHandTop;
   private JPanel pnlHumanHandBtm;
   
   private GUICard  guicard          = new GUICard();
   private int numPacksPerDeck        = 1;
   private int numJokersPerPack       = 0;
   private int numUnusedCardsPerPack  = 0;
   private int humWin                 = 0;
   private int compWin                = 0;
   private Card[] unusedCardsPerPack  = null;
   private CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);     
   private CardTable myCardTable; 
            
   private Hand computerHand;
   private Hand playerHand;
   

   public static void main(String[] args) {
      
      Assig5Part3 newgame        = new Assig5Part3();
      newgame.myCardTable.setVisible(true);
      
   }
   
   public Assig5Part3() {
  
      this.myCardTable = buildTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      this.highCardGame.deal();
      this.computerHand = highCardGame.getHand(COMPUTER);
      this.playerHand = highCardGame.getHand(PLAYER);
      this.computerHand.sort();
      this.playerHand.sort();
      
      this.myCardTable.setLocationRelativeTo(null);
      this.myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      this.playedCardLabels[0] = new JLabel(guicard.getBackCardIcon());
      this.playedCardLabels[1] = new JLabel(guicard.getBackCardIcon());
      this.playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      this.playLabelText[1] = new JLabel("You", JLabel.CENTER);
      
      updateGameTable();
      
      JButton resetButton = new JButton("Reset Game");
      resetButton.addActionListener(new ButtonListener());
      this.pnlHumanHandBtm.add(resetButton);
      
      this.myCardTable.pack();
      
   }

   private void updateGameTable() {
      
      // Update JLabels for Computer.
      this.pnlComputerHandTop.removeAll();
      for (int x = 0; x < computerHand.getNumCards(); x++) {
         this.pnlComputerHandTop.add(new JLabel(guicard.getBackCardIcon()));
      }

      
      
      //Draw Play Area
      this.cmpPlayArea.removeAll();
      this.cmpPlayArea.add(playedCardLabels[0]);
      this.cmpPlayArea.add(playLabelText[0]);
      
      this.hmnPlayArea.removeAll();
      this.hmnPlayArea.add(playedCardLabels[1]);
      this.hmnPlayArea.add(playLabelText[1]);
      

      //Update JButtons for Player
      this.pnlHumanHandTop.removeAll();
      
      for (int x = 0; x < playerHand.getNumCards(); x++) {
         humanLabels[x] = new JButton(guicard.getIcon(
            this.playerHand.inspectCard(x)));
         humanLabels[x].setActionCommand(String.valueOf(x));
         humanLabels[x].addActionListener( new CardListener() );
         this.pnlHumanHandTop.add(humanLabels[x]);
      }
      
      myCardTable.pnlComputerHand.validate();
      myCardTable.pnlComputerHand.repaint();
      myCardTable.pnlPlayArea.validate();
      myCardTable.pnlPlayArea.repaint();
      myCardTable.pnlHumanHand.validate();
      myCardTable.pnlHumanHand.repaint();
      

     
   }
   
   private void resetgame() {
      this.highCardGame.newGame();
      this.highCardGame.deal();
      this.computerHand = highCardGame.getHand(COMPUTER);
      this.playerHand = highCardGame.getHand(PLAYER);
      this.computerHand.sort();
      this.playerHand.sort();
      try {
         this.pnlComputerHandBtm.remove(0);
         playedCardLabels[0] = new JLabel(guicard.getBackCardIcon());
         playedCardLabels[1] = new JLabel(guicard.getBackCardIcon());
      } catch(ArrayIndexOutOfBoundsException e) {
         
      }
      updateGameTable();
   }
   
   private class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         resetgame();
      }
   }
   
   private class CardListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         Card cardPlayed = playerHand.playCard(
               Integer.valueOf(e.getActionCommand()));
         playerPlays(cardPlayed);         
      }
   }
    
   private void playerPlays(Card cardPlayed) {
      playedCardLabels[1] = new JLabel(guicard.getIcon(cardPlayed));
      computerPlays(cardPlayed);      
   }
   
   private void decidePlayWinner(Card playerCard, Card computerCard) {
      String winner = "";
      
      if(playerCard.getValue() == 'A' && computerCard.getValue() == 'X') {
         winner = "Computer wins this round.";
         compWin += 1;
      } else if (playerCard.compareTo(computerCard) == 1) {
         winner = "Player wins this round.";
         humWin += 1;
      } else if (playerCard.compareTo(computerCard) == -1) {
         winner = "Computer wins this round.";
         compWin += 1;
      } else {
         winner = "It's a tie!";
      }
      this.pnlComputerHandBtm.removeAll();
      this.pnlComputerHandBtm.add(new JLabel(winner));
   }
   
   private String decideGameWinner() {
      String winner = "";
      if (humWin < compWin) {
         winner = "You lost. Hang your head in shame.";
      }
      else if (humWin == compWin) {
         winner = "It was a tie.";
      }
      else {
         winner = "You won! Congratulations!";
      }
      return winner; 
   }
   
   private void computerPlays(Card cardPlayerPlayed) {
      Card computerCard = computerPicksACard(cardPlayerPlayed);
      playedCardLabels[0] = new JLabel(guicard.getIcon(computerCard));
      decidePlayWinner(cardPlayerPlayed, computerCard);
      updateGameTable();
   }
   
   private Card computerPicksACard(Card cardPlayerPlayed) {
      
      Card cardToPlay = null;
      if (cardPlayerPlayed.getValue() == 'A') {
         // Card was an Ace, play the lowest card, 
         // Which could be a joker
            cardToPlay = this.computerHand.playCard(this.computerHand.getNumCards() - 1);
      } else {
         for ( int x = this.computerHand.getNumCards()-1; x >=0; x--) {
            if (this.computerHand.inspectCard(x).compareTo(cardPlayerPlayed) == 1) {
               cardToPlay = this.computerHand.playCard(x);
               break;
            }
         }
         if (cardToPlay == null) {
            cardToPlay = this.computerHand.playCard(this.computerHand.getNumCards() - 1);
         }
      }
      return cardToPlay;
      
   }
   
   private CardTable buildTable(String title, int cardsPerHand, int numPlayers) {
      
      CardTable theTable = new CardTable(title, cardsPerHand, numPlayers);
      
      theTable.pnlComputerHand.setLayout(new GridLayout(2, 1));
      theTable.pnlPlayArea.setLayout(new GridLayout(1, 2));
      theTable.pnlHumanHand.setLayout(new GridLayout(2, 1));
      
      this.pnlComputerHandTop = new JPanel(new FlowLayout());
      this.pnlComputerHandBtm = new JPanel();
      this.pnlHumanHandTop = new JPanel(new FlowLayout());
      this.pnlHumanHandBtm = new JPanel();
      this.hmnPlayArea = new JPanel(new GridLayout(2,1));
      this.cmpPlayArea = new JPanel(new GridLayout(2,1));
      
      theTable.pnlComputerHand.add(pnlComputerHandTop);
      theTable.pnlComputerHand.add(pnlComputerHandBtm);
      theTable.pnlPlayArea.add(cmpPlayArea);
      theTable.pnlPlayArea.add(hmnPlayArea);
      theTable.pnlHumanHand.add(pnlHumanHandTop);
      theTable.pnlHumanHand.add(pnlHumanHandBtm);
      
      return theTable;
      
   }
}