/* CST 338
 * Build Card Game (with a useless timer!)
 * @Authors: 
 * Jennifer Engblom
 * Jason Lloyd
 * Kevin Hendershot
 * Oscar Alba
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class Build {
   
   public static void main(String[] args) {
      
      BuildCardGame model = new BuildCardGame();
      BuildCardTable view = new BuildCardTable(model);
      
      view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      view.setVisible(true);
      
   }
   
}

class BuildCardGame {
   
   private static final int NUM_PLAYERS = 2;
   private static final int CARDS_PER_HAND = 7;
   
   public Hand computerHand;
   public Hand playerHand;
   public CardGameFramework cardFramework;
   private Card leftCard;
   private Card rightCard;
   private int computerPass;
   public  int playerPass;
   private boolean computerCantPlay;
   private boolean playerCantPlay;

   public BuildCardGame() {

      newGame();
      
   }
   
   private void newGame() {
   
      this.cardFramework = new CardGameFramework(1,0,0,null,
                                 NUM_PLAYERS,CARDS_PER_HAND);

      this.cardFramework.deal();      
      this.computerHand = cardFramework.getHand(0);
      this.playerHand = cardFramework.getHand(1);
      
      this.computerHand.sort();
      this.playerHand.sort();
      
      this.leftCard = cardFramework.getCardFromDeck();
      this.rightCard = cardFramework.getCardFromDeck();      
      
      this.computerPass = 0;
      this.playerPass = 0;
      
      this.computerCantPlay = false;
      this.playerCantPlay = false;
      
   }
   
   public int getNumPlayers() {
      return NUM_PLAYERS;
   }
   
   public int getNumCards() {
      return CARDS_PER_HAND;
   }
   
   public boolean playerPasses() {
   //When the player can't play 
      this.playerPass++;
      playerCantPlay = true;
      chooseNextMove("player");
      return playerCantPlay;
   }
   
   private boolean computerPasses() {
   //When the computer doesn't have a move
      this.computerPass++;
      computerCantPlay = true;
      chooseNextMove("computer");
      return computerCantPlay;
   }
   
   private boolean addToTheStacks() {
      boolean continuePlaying = false;
      
      if (this.cardFramework.getNumCardsRemainingInDeck() > 1) {
         this.leftCard = this.cardFramework.getCardFromDeck();
         this.rightCard = this.cardFramework.getCardFromDeck();
         continuePlaying = true;
      }
      
      return continuePlaying;
   }
   
   public void playCardOnLeft(Card card) {
   //Place selected card on the left pile 
      this.leftCard = card; 
   }
   
   public void playCardOnRight(Card card) {
   //Place selected card on the right pile
      this.rightCard = card;      
   }
   
   public boolean playableCard(Card cardOnTable, Card cardToBePlayed) {
   // Determine if the card and the pile are compatible.
      boolean isPlayable = false;
      int valueOfBoard = cardOnTable.getIntValue();
      int valueOfCard = cardToBePlayed.getIntValue();
      
      switch (valueOfCard) {
         case 13:
            if (valueOfBoard == 1 || valueOfBoard == 12) {
               isPlayable = true;
            }
            break;
         case 1:
            if (valueOfBoard == 13 || valueOfBoard == 2) {
               isPlayable = true;
            }
            break;
         default:
            if (Math.abs(valueOfBoard - valueOfCard) == 1) {
               isPlayable = true;
            }
            break;
      }  
      return isPlayable;      
      
   }
   
   public void computerChoosesAPlay() {

      boolean canPlay = false;
 
      for (int x = 0; x < this.computerHand.getNumCards(); x++) {
         if (playableCard(this.leftCard, this.computerHand.inspectCard(x))) {
            canPlay = true;
            playCardOnLeft(cardFramework.playCard(0, x));
            cardFramework.takeCard(0);
         } else if (playableCard(this.rightCard, this.computerHand.inspectCard(x))) {
            canPlay = true;
            playCardOnRight(cardFramework.playCard(0, x));
            cardFramework.takeCard(0);
         }
      }
      if (!canPlay) {
         computerPasses();
      }      
   }
   
   private void chooseNextMove(String caller) {
      if (this.playerCantPlay && this.computerCantPlay) {
         if(addToTheStacks()) {
            this.playerCantPlay = false;
            this.computerCantPlay = false;
         } else {
            endTheGame();
         }
      } else if (playerCantPlay) {
         computerChoosesAPlay();  
         this.playerCantPlay = true;
      } 
   }
   
   public Card getLeftSideCard() {
      return leftCard;
   }
   
   public Card getRghtSideCard() {
      return rightCard;
   }
   
   public void endTheGame() {
      if (this.computerPass < this.playerPass) {
         JOptionPane.showMessageDialog(null, "Computer Wins!", "Game Over", 
                  JOptionPane.INFORMATION_MESSAGE);
      } else if (this.computerPass > this.playerPass) {
         JOptionPane.showMessageDialog(null, "You Win!", "Game Over", 
                  JOptionPane.INFORMATION_MESSAGE);
      } else {
         JOptionPane.showMessageDialog(null, "It's a tie!", "Game Over", 
                  JOptionPane.INFORMATION_MESSAGE);
      }
      System.exit(0);
   }
}

class BuildCardTable extends CardTable {

   private JPanel leftSide;
   private JPanel rghtSide;
   
  // private JRadioButton[] rb;
   public ButtonGroup cardSelected;
   
   private BuildCardGame gamedata;
   
   public JButton leftPileBtn;
   public JButton rightPileBtn;
   public JButton playerPassBtn;

   public BuildCardTable(BuildCardGame gamedata ) {
      super("Build Card Game", gamedata.getNumCards(), gamedata.getNumPlayers());
      this.gamedata = gamedata;
      GUICard.loadCardIcons();
      
      //Create a buttongroup for the player's hand
      cardSelected = new ButtonGroup();
      
      //The center panel has 3 columns
      pnlPlayArea.setLayout(new GridLayout(1,3));
      //Initialize the Left and Right Panels
      leftSide = new JPanel();
      rghtSide = new JPanel();
      //Create the "Pass Turn" and the Timer for the center 
      JPanel centerPanel = new JPanel(new BorderLayout());
      
      Clock clock = new Clock();
      JPanel clockPnl = new JPanel();
      clockPnl.add(clock.timerText);
      centerPanel.add(clockPnl, BorderLayout.PAGE_START);
      centerPanel.add(clock.startStopButton, BorderLayout.CENTER);
      playerPassBtn = new JButton("Pass Turn");
      playerPassBtn.addActionListener(new BuildCardController(gamedata, this));
      
      centerPanel.add(playerPassBtn, BorderLayout.PAGE_END);
      
      pnlPlayArea.add(leftSide);
      pnlPlayArea.add(centerPanel);
      pnlPlayArea.add(rghtSide);
      // Two rows of for the player
      pnlHumanHand.setLayout(new GridLayout(2,gamedata.getNumCards()));
      
      updateTable();
      pack();
   }

   public void updateTable() {
      Hand cmpHand = gamedata.cardFramework.getHand(0);
      Hand plrHand = gamedata.cardFramework.getHand(1);
      // Display card backs for the computer's hand
      pnlComputerHand.removeAll();
      for (int x = 0; x < cmpHand.getNumCards(); x++) {
         JLabel label = new JLabel(GUICard.getBackCardIcon());
         this.pnlComputerHand.add(label);
      }
      // Create buttons for the left and right card piles
      this.leftPileBtn = new JButton(GUICard.getIcon(gamedata.getLeftSideCard()));
      this.leftPileBtn.setActionCommand("getLeftSideCard");
      this.leftPileBtn.addActionListener(new BuildCardController(gamedata, this));
      this.rightPileBtn = new JButton(GUICard.getIcon(gamedata.getRghtSideCard()));
      this.rightPileBtn.setActionCommand("getRightSideCard");
      this.rightPileBtn.addActionListener(new BuildCardController(gamedata, this));
      this.leftSide.removeAll();
      this.leftSide.add(this.leftPileBtn);
      this.rghtSide.removeAll();
      this.rghtSide.add(this.rightPileBtn);
      
      this.pnlHumanHand.removeAll();
      // Display the players cards
      for (int x = 0; x < plrHand.getNumCards(); x++) {
         JLabel label = new JLabel(GUICard.getIcon(plrHand.inspectCard(x)));
         this.pnlHumanHand.add(label);
      }
      // Install radio buttons for the user to select a card
      for (int x = 0; x < plrHand.getNumCards(); x++) {

         JRadioButton rb = new JRadioButton();
         if (x == 0) {
            rb.setSelected(true);
         }
         rb.setActionCommand(Integer.toString(x));
         cardSelected.add(rb);
         JPanel panel = new JPanel();
         panel.add(rb);
         this.pnlHumanHand.add(panel);
      }
      
      // Redraw the table
      this.pnlComputerHand.validate();
      this.pnlComputerHand.repaint();
      this.pnlPlayArea.validate();
      this.pnlPlayArea.repaint();
      this.pnlHumanHand.validate();
      this.pnlHumanHand.repaint();
      
      
   }
   
}

class CardTable extends JFrame {
   
   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand;
   public JPanel pnlHumanHand; 
   public JPanel pnlPlayArea;
 
   public CardTable(String title, int numCardsPerHand, int numPlayers) {

      super();      
      setTitle(title);
      Border blackline = BorderFactory.createLineBorder(Color.black);
      
      setLayout(new GridLayout( numPlayers + 1, 1));         
      
      pnlComputerHand   = new JPanel(new FlowLayout() );
      pnlPlayArea       = new JPanel(new FlowLayout() ); 
      pnlHumanHand      = new JPanel(new FlowLayout() );
     
      TitledBorder cmpBorder = BorderFactory.createTitledBorder(blackline,
         "Computer Hand");
      pnlComputerHand.setBorder(cmpBorder);
      add(pnlComputerHand);  
      
      TitledBorder playBorder = BorderFactory.createTitledBorder(blackline,
         "Play Area");
      pnlPlayArea.setBorder(playBorder);
      add(pnlPlayArea);
      
      TitledBorder hmnBorder = BorderFactory.createTitledBorder(blackline,
         "Your Hand");
      pnlHumanHand.setBorder(hmnBorder);
      add(pnlHumanHand);
      
   }
   
   public int getNumCardsPerHand() {
      return numCardsPerHand;
   }
   
   public int getNumPlayers() {
      return numPlayers;
   }
}

class Clock extends JFrame {
   private int counter = 0;
   private boolean runTimer = false; //used for run method
   private final int PAUSE = 100; //milliseconds
   private String start = "START";
   private String stop = "STOP";

   public Timer clockTimer;
   public JButton startStopButton;
   public JLabel timerText;

   // The default constructor for the GUI
   public Clock()
   {
      // Set timer action to 1000 milliseconds
      clockTimer = new Timer(1000, timerEvent);

      timerText = new JLabel("" + formatTimer(counter));

      startStopButton = new JButton(start);
      startStopButton.addActionListener(buttonEvent);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(150, 150);
   }

   // minutes:seconds will be result of formatting method
   public String formatTimer(long seconds)
   {
      long s = seconds % 60;
      long m = (seconds / 60) % 60;
      return String.format("%01d:%02d", m, s);
   }

   private ActionListener timerEvent = new ActionListener()
   {
      public void actionPerformed(ActionEvent e)
      {
         counter++;
         timerText.setText("" + formatTimer(counter));
      }
   };

   // Will create the Timer and run the method
   private ActionListener buttonEvent = new ActionListener()
   {
      public void actionPerformed(ActionEvent e)
      {
         TimerClass timeThread = new TimerClass();
         timeThread.start();
      }
   };

   // Called by ActionListener to start, stop, and display time and buttons
   private class TimerClass extends Thread
   {
      public void run()
      {
         if (runTimer)
         {
            startStopButton.setText(start);
            clockTimer.stop();
            runTimer = false;
            timerText.setText("" + formatTimer(counter));
         }
         else if (!runTimer)
         {
            startStopButton.setText(stop);
            clockTimer.start();
            counter = 0;
            runTimer = true;
            timerText.setText("" + formatTimer(counter));
         }
         doNothing(PAUSE);
      }

      
      public void doNothing(int milliseconds)
      {
         try
         {
            Thread.sleep(milliseconds);
         } catch (InterruptedException e)
         {
            System.out.println("Unexpected interrupt");
            System.exit(0);
         }
      }
   } 
}

class Card {
   //Created by Jason Lloyd
   public static enum Suit { DIAMONDS, HEARTS, SPADES, CLUBS };
   public static char[] valueRanks = {'X', '2', '3', '4', '5', '6', '7',
                                      '8', '9', 'T', 'J', 'Q', 'K', 'A'};
   
   private char value;
   private Suit suit;
   private boolean errorFlag;
   
   //Constructors
   public Card() {
      this.value = 'A';
      this.suit = Suit.SPADES; 
      this.errorFlag = false;
   }
   
   public Card(char value, Suit suit) {
      this.set(value, suit);
   }
   
   //Accessors
   public char getValue() {
      return value;
   }
   
   public Suit getSuit() {
      return this.suit;
   }
   
   public boolean getErrorFlag() {
      return this.errorFlag;
   }
   
   //Mutators
   public boolean set(char value, Suit suit) {
      boolean output = false;
      
      // Test to see if the values passed are valid
      if (isValid(value, suit)) {     
         // Values are OK, now set both values
         this.suit = suit;
         this.value = value;
         this.errorFlag = false;
         output = true;    
      } else {
         this.errorFlag = true;
      }
      return output;
   }
     
   public String toString() {
   // toString outputs the value and suit of the card as a string
      String output = "";
      
      if (errorFlag == true) {
         output = "[invalid]";
      } else {
         output = getValue() + " of ";
         switch (suit) {
            case SPADES:
               output += "spades";
               break;
            case DIAMONDS:
               output += "diamonds";
               break;
            case HEARTS:
               output += "hearts";
               break;
            case CLUBS:
               output += "clubs";
               break;
         }
      }
      return output;
   }
      
   public boolean equals(Card card2) {
   // Test to see if "card2" value and suit matches the current object
      boolean isEquals = false;
      
      if ( (this.value == card2.getValue()) && (this.suit == card2.suit) ) {
         isEquals = true;
      }
      return isEquals;
   }
   
   private boolean isValid(char value, Suit suit) {
   // Test value and suit to make sure they have valid entries.
      boolean output = false;
      
      if (suit == Suit.CLUBS  || suit == Suit.DIAMONDS || 
          suit == Suit.HEARTS || suit == Suit.SPADES) {
         if (value == 'X' || value == 'A' || value == '2' || value == '3' || 
             value == '4' || value == '5' || value == '6' || value == '7' || 
             value == '8' || value == '9' || value == 'T' || value == 'J' || 
             value == 'Q' || value == 'K') {
            output = true;
         }
      }
      return output;
   }
 
   public int getIntValue() {
      int value = -1;
      for (int n = 0; n < valueRanks.length; n++) {
         if ( getValue() == valueRanks[n] ) {
            value = n;
         } 
      }
      return value;
   } 
   public int compareTo(Card card2) {
      //Implements the compareTo function for Card
      //If the value of "this" is equal to card2.value 0 is returned
      //If the value of "this" is greater than card2, 1 is returned
      //If the value of "this" is lesser than card2, -1 is returned
      
      int output;
      
      if ( getIntValue() == card2.getIntValue() ) {
         output =  0;
      } else if ( getIntValue() > card2.getIntValue() ) {
         output =  1;
      } else {
         output = -1;
      }
      return output;
   }
      
   static void arraySort(Card[] cardsToSort, int arraySize) {
      // Bubble Sort Needed
      Card tempCard;
      boolean swap = true;
      
      try {
         while(swap) {
            swap = false;
            for (int n = 0; n < arraySize - 1; n++) {
               if (cardsToSort[n].compareTo(cardsToSort[n+1]) == -1) {
                  tempCard         = cardsToSort[n];
                  cardsToSort[n]   = cardsToSort[n+1];
                  cardsToSort[n+1] = tempCard;  
                  swap = true;
               }
            } 
         }
      } catch (NullPointerException e) {
         //The cardsToSort array contains a null card
         //End the sort
      }
   }
}

class Hand {
   //Created by Oscar Alba.
   public static final int MAX_CARDS = 50;

   private Card[] myCards;
   private int numCards;


   public Hand() {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   public void resetHand() {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   public boolean takeCard(Card card) {
      boolean validCheck;

      if (numCards >= MAX_CARDS) {
         validCheck = false;
      } else {
         myCards[numCards] = card;
         numCards++;
         validCheck = true;
      }
      return validCheck;
   }
/*
   public Card playCard(int cardIndex) {
      Card cardToPlay;
      
      if (this.numCards > 0 && cardIndex < this.numCards) {       
         cardToPlay = myCards[cardIndex];
         for (int x = cardIndex; x < numCards; x++) {
            myCards[x] = myCards[x+1];
         }
         numCards--;
      } else {
         cardToPlay = new Card('B', Card.Suit.SPADES);
      }
      return cardToPlay;
   }*/
   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];
      
      numCards--;
      for(int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i+1];
      }
      
      myCards[numCards] = null;
      
      return card;
   }
    
   public String toString() {
      String string = "[ ";
      for (int c = 0; c < numCards; c++) {
         string += myCards[c] + " ";
      }
      string += "]";
      return string;
   }

   public int getNumCards() {
      return numCards;
   }

   public Card inspectCard(int k) {
      Card card;
      if (k > this.numCards) {
         card = new Card('B' , Card.Suit.SPADES);
      } else {
         card = myCards[k];
      }
      return card;
   } 
   
   public void sort() {
      Card.arraySort(myCards, numCards);
   }
}

class BuildCardController implements ActionListener {

   private BuildCardGame aModel;
   private BuildCardTable aView;

   BuildCardController(BuildCardGame theModel, BuildCardTable theView) {
   
      this.aModel = theModel;
      this.aView = theView;
   
   }

   @Override
   public void actionPerformed(ActionEvent arg0) {
      // Created by Kevin Hendershott, et al.

      // Replaces functionality of both ButtonListener and CardListener from
      // Assignment 5.
      // Adds functionality for RadioButtons and CardPiles in Assignment 6.

      int cardIndex;
      Card cardToPlay;
      
      switch (arg0.getActionCommand()) {
      case "getLeftSideCard":
         cardIndex = Integer.parseInt(aView.cardSelected.getSelection().getActionCommand());
         cardToPlay = aModel.playerHand.inspectCard(cardIndex);
         if (aModel.playableCard(aModel.getLeftSideCard(), cardToPlay)) {
            aModel.playCardOnLeft(aModel.cardFramework.playCard(1, cardIndex));
            aModel.cardFramework.takeCard(1);
            aModel.computerChoosesAPlay();
         } else {
            JOptionPane.showMessageDialog(null, "You cannot place this card here",
            "Bad Play", JOptionPane.INFORMATION_MESSAGE);
         }
         
         aView.updateTable();
         break; // Don't allow to flow to next case statement.
      case "getRightSideCard":
         cardIndex = Integer.parseInt(aView.cardSelected.getSelection().getActionCommand());
         cardToPlay = aModel.playerHand.inspectCard(cardIndex);
         if (aModel.playableCard(aModel.getRghtSideCard(), cardToPlay)) {
            aModel.playCardOnRight(aModel.cardFramework.playCard(1, cardIndex));  
            aModel.cardFramework.takeCard(1);
            aModel.computerChoosesAPlay();
         } else {
            JOptionPane.showMessageDialog(null, "You cannot place this card here",
            "Bad Play", JOptionPane.INFORMATION_MESSAGE);
         }
         
         aView.updateTable();
         break; // Don't allow to flow to next case statement.
         
      case "Pass Turn":
         aModel.playerPasses();
         aView.updateTable();
         break;
      }
   }

}

class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
                                    // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
                                    // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                        // in the game.  e.g. pinochle does not
                                        // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
         int numPlayers, int numCardsPerHand)
   {
      int k;
      
      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if  (numCardsPerHand < 1 ||
            numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
            / numPlayers )
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);
 
      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);
      
      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard( unusedCardsPerPack[k] );

      // add jokers
      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard( new Card('X', Card.Suit.values()[j]) );

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard( deck.dealCard() );
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
          cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);      
      }
   
      // return the card played
      return hand[playerIndex].playCard(cardIndex);
   
   }


   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;
     
       // Are there enough Cards?
       if (deck.getNumCards() <= 0)
          return false;

       return hand[playerIndex].takeCard(deck.dealCard());
   }

}

class GUICard {

   private static final int SUITS = 4;
   private static final int VALUES = 14;
   private static Icon[][] iconCards = new ImageIcon[VALUES][SUITS];
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   
   private static String[] values = { "X", "2", "3", "4", "5", "6", "7",
                                      "8", "9", "T", "J", "Q", "K", "A"};
/*   public GUICard() {
      if (!iconsLoaded) {        
         loadCardIcons();
      }
   }*/
   
   static public void loadCardIcons() {
      if(!iconsLoaded){
         for(int suit = 0; suit < SUITS; suit++) {
            for (int value = 0; value < VALUES; value++) {
               String iconFile = "images\\" + turnIntIntoCardValue(value) +
                  turnIntIntoCardSuit(suit) + ".gif";
               iconCards[value][suit] = new ImageIcon(iconFile);
            }
         }
         iconBack = new ImageIcon("images\\BK.gif");
         iconsLoaded = true;
      }
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
      if(iconsLoaded && card != null && !card.getErrorFlag() ) {
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

class Deck {
   //Created by Jenn Engblom, et al
   public static final int MAX_PACKS = 6;
   public static final int CARDS_IN_A_PACK = 52;
   public static final int MAX_CARDS = MAX_PACKS * CARDS_IN_A_PACK;
   
   private static Card masterPack[] = new Card[CARDS_IN_A_PACK];
   private Card[] cards; 
   private int topCard; // Always points to the last member of cards
   private int numPacks;
   
   private static boolean initializeMP = false;
   
   //empty constructor
   public Deck () {
      this.numPacks = 1;
      cards = new Card[numPacks * CARDS_IN_A_PACK];
      allocateMasterPack();
      init(this.numPacks);
      
   }

   //populates arrays and assigns initial values to members
   //Deck(int numPacks) - a constructor that populates the arrays and 
   //assigns initial values to members.
   public Deck (int numberOfPacks) {
               
      if (numberOfPacks > MAX_PACKS) {
          this.numPacks = MAX_PACKS;
          System.out.println("The number was greater than 6. Reducing the number to 6.");
      } else {
         this.numPacks = numberOfPacks;      
      }
      init(this.numPacks);
   }
   
   //repopulate cards[] with 52*numPacks cards
   public void init (int numOfPacks) {
      allocateMasterPack();
      this.numPacks = numOfPacks;
      this.cards = new Card[numPacks * CARDS_IN_A_PACK];
      this.topCard = -1; // No cards are in the deck to start
      for ( int i = 0; i < this.numPacks; i++ ) {
         for ( int j = 0; j < masterPack.length; j++ ) {
            this.topCard++;
            this.cards[this.topCard] = new Card(masterPack[j].getValue(), masterPack[j].getSuit());
         }
      }
   }
   
   //mixes up the cards with standard random number generator
   public void shuffle () {
      //An arraylist was easier to remove elements from
      //I took the top of the deck to be the 0th position, not the last position
      ArrayList<Card> cardsList = new ArrayList<>(Arrays.asList(cards));
      //the random number's range is reduced by 1 each time a card has been shuffled
      for (int i = 0; i < cards.length; i++) {
         Random rand = new Random();
         int nextCard = rand.nextInt(cardsList.size());
         this.cards[i] = cardsList.get(nextCard);
         cardsList.remove(nextCard);
      }
      
   }
   
   
   //returns and removes the card in the top occupied position of cards[]
   //calling topCard as that is the card we want
   public Card dealCard() {
      //give a fake card if there are no more cards in the deck
      if (topCard < 0) {
         Card card = new Card('B', Card.Suit.SPADES);
         return card;
      }
      else {
         Card cardToReturn = cards[topCard];
         topCard--;
         return cardToReturn;
      }

   }
   
   //a getter for topCard
   public int getTopCard() {
      return topCard;
   }
   
   //accessor for individual card and return a card with errorFlag = true if k is bad
   public Card inspectCard(int k) {
      Card card;
      if (k > topCard) {
         // this is a fake card to induce an error in the Card class...blammo
         card = new Card('B', Card.Suit.SPADES);
      } else {
         card = cards[k] ;
      }
      return card;
   }
   
   //will break if it has already been called once
   //create counter that increments if there has been a call
   //to it and if the counter is greater than 1, it breaks
   private static void allocateMasterPack() {
      int i = 0;
      if (initializeMP == true) {
         return;
      }
      char values[] = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
      for (Card.Suit suit : Card.Suit.values()) {
          for (int x = 0; x < 13; x++) {
             masterPack[i] = new Card(values[x], suit);
             i++;
             
          }
      }
      initializeMP = true;
   }
   
   private void sort() {
      init(this.numPacks);
   }
   
   public boolean addCard(Card card) {
      int numOfFounds = 0;
      boolean cardAdded = false;
      
      for (int c = 0; c <= topCard; c++) {
         if (this.cards[c].equals(card)) {
            numOfFounds++;
         }
      }
      
      if (numOfFounds < this.numPacks) {
         this.cards[++this.topCard] = card;
         cardAdded = true;
      }
      return cardAdded;
   }
   
   public boolean removeCard(Card card) {
      boolean foundTheCard = false;
      for (int c = 0; c <= this.topCard; c++) {
         if ( this.cards[c].equals(card) ) {
            cards[c] = this.cards[this.topCard];
            this.topCard--;
            foundTheCard = true;
            break;
         } 
      }
      return foundTheCard;
   }
   
   public int getNumCards() {
      return (this.topCard + 1);
   }
}
