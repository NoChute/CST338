import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Model {
   // Created by Jenn Engblom, Jason Lloyd, Oscar Alba & Kevin Hendershott.

   // Not a "true" MVC pattern organization as we learned from other resources
   // (especially from the excellent http://www.austintek.com/mvc/) that MVC
   // implements many paradigms we did not learn in class (such as Observable
   // and Observer), and in any case, there was certainly not enough time in the
   // week, let alone the "4 hours" predicted for this entire assignment. As a
   // result, we as a team only scratched the surface of using an MVC pattern,
   // and changed only our main class into a Model and our obvious listener
   // classes into a single Controller class. All other classes proved too
   // difficult to reorganize in the time remaining and can be considered as
   // View classes for now. While there is much crossover between the View
   // classes and the Model class because we did not have the time to segregate,
   // at least the Controller class is pristine. We feel what we accomplished,
   // albeit not an entire reorganization, is in the spirit of the assignment's
   // Phase 1.

   static final int NUM_PLAYERS = 2;
   static final int NUM_CARDS_PER_HAND = 5;
   static final int COMPUTER = 0;
   static final int PLAYER = 1;

   // private JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   private JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   private JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
   private JButton[] humanLabels = new JButton[NUM_CARDS_PER_HAND];

   private JPanel hmnPlayArea;
   private JPanel cmpPlayArea;
   private JPanel pnlComputerHandTop;
   private JPanel pnlComputerHandBtm;
   private JPanel pnlHumanHandTop;
   private JPanel pnlHumanHandBtm;

   private int numPacksPerDeck = 1;
   private int numJokersPerPack = 0;
   private int numUnusedCardsPerPack = 0;
   private int humWin = 0;
   private int compWin = 0;
   private Card[] unusedCardsPerPack = null;
   private CardGameFramework highCardGame = new CardGameFramework(
            numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
            unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);
   private CardTable myCardTable;

   private Hand computerHand;
   private Hand playerHand;

   public static void main(String[] args) {
      Model newgame = new Model();
      newgame.resetGame(); // Just to keep the compiler quiet about a "local
                           // variable never used".
   }

   public Model() {
      GUICard.loadCardIcons();
      myCardTable = buildTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      highCardGame.deal();
      computerHand = highCardGame.getHand(COMPUTER);
      playerHand = highCardGame.getHand(PLAYER);
      computerHand.sort();
      playerHand.sort();

      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      playedCardLabels[0] = new JLabel(GUICard.getBackCardIcon());
      playedCardLabels[1] = new JLabel(GUICard.getBackCardIcon());

      updateGameTable();

      JButton resetGame = new JButton("Reset Game");
      resetGame.addActionListener(new Controller());
      pnlHumanHandBtm.add(resetGame);

      JButton newHandButton = new JButton("New Hand");
      newHandButton.addActionListener(new Controller());
      pnlHumanHandBtm.add(newHandButton);

      myCardTable.pack();
      myCardTable.setVisible(true); // Moved from main() in Assignment 5.
   }

   private void updateGameTable() {
      // Update JLabels for Computer.
      pnlComputerHandTop.removeAll();
      for (int x = 0; x < computerHand.getNumCards(); x++)
         pnlComputerHandTop.add(new JLabel(GUICard.getBackCardIcon()));

      // Draw Play Area
      playLabelText[0] = new JLabel("Computer: " + compWin, JLabel.CENTER);
      playLabelText[1] = new JLabel("You: " + humWin, JLabel.CENTER);
      cmpPlayArea.removeAll();
      cmpPlayArea.add(playedCardLabels[0]);
      cmpPlayArea.add(playLabelText[0]);

      hmnPlayArea.removeAll();
      hmnPlayArea.add(playedCardLabels[1]);
      hmnPlayArea.add(playLabelText[1]);

      // Update JButtons for Player
      pnlHumanHandTop.removeAll();

      for (int x = 0; x < playerHand.getNumCards(); x++) {
         humanLabels[x] = new JButton(
                  GUICard.getIcon(playerHand.inspectCard(x)));
         humanLabels[x].setActionCommand(String.valueOf(x));
         humanLabels[x].addActionListener(new Controller());
         pnlHumanHandTop.add(humanLabels[x]);
      }

      myCardTable.pnlComputerHand.validate();
      myCardTable.pnlComputerHand.repaint();
      myCardTable.pnlPlayArea.validate();
      myCardTable.pnlPlayArea.repaint();
      myCardTable.pnlHumanHand.validate();
      myCardTable.pnlHumanHand.repaint();
   }

   private void resetGame() {
      highCardGame.newGame();
      highCardGame.deal();
      computerHand = highCardGame.getHand(COMPUTER);
      playerHand = highCardGame.getHand(PLAYER);
      computerHand.sort();
      playerHand.sort();
      try {
         pnlComputerHandBtm.remove(0);
         playedCardLabels[0] = new JLabel(GUICard.getBackCardIcon());
         playedCardLabels[1] = new JLabel(GUICard.getBackCardIcon());
      } catch (ArrayIndexOutOfBoundsException e) {
      }
      updateGameTable();
   }

   private class Controller implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent arg0) {
         // Replaces both ButtonListener and CardListener from Assignment 5.

         switch (arg0.getActionCommand()) {
         case "Reset Game":
            humWin = 0;
            compWin = 0;
            // Continues to flow through next case statement to reset game...
         case "New Hand":
            resetGame();
            break; // Don't allow to flow to next case statement.
         default:
            // This is less than ideal because I didn't have time to test that
            // arg0.getActionCommand() is truly an integer or wrap the code in a
            // try/catch block.
            Card cardPlayed = playerHand
                     .playCard(Integer.valueOf(arg0.getActionCommand()));
            playerPlays(cardPlayed);
         }
      }

   }

   private void playerPlays(Card cardPlayed) {
      playedCardLabels[1] = new JLabel(GUICard.getIcon(cardPlayed));
      computerPlays(cardPlayed);
   }

   private void decidePlayWinner(Card playerCard, Card computerCard) {
      String winner = "";
      if (playerCard.getValue() == 'A' && computerCard.getValue() == 'X') {
         winner = "Computer wins this round.";
         compWin += 1;
      } else if (playerCard.compareTo(computerCard) == 1) {
         winner = "Player wins this round.";
         humWin += 1;
      } else if (playerCard.compareTo(computerCard) == -1) {
         winner = "Computer wins this round.";
         compWin += 1;
      } else
         winner = "It's a tie!";
      pnlComputerHandBtm.removeAll();
      pnlComputerHandBtm.add(new JLabel(winner));
   }

   private void computerPlays(Card cardPlayerPlayed) {
      Card computerCard = computerPicksACard(cardPlayerPlayed);
      playedCardLabels[0] = new JLabel(GUICard.getIcon(computerCard));
      decidePlayWinner(cardPlayerPlayed, computerCard);
      updateGameTable();
   }

   private Card computerPicksACard(Card cardPlayerPlayed) {
      Card cardToPlay = null;
      if (cardPlayerPlayed.getValue() == 'A') // Card was an Ace, play the
                                              // lowest card, which could be
                                              // a
                                              // joker
         cardToPlay = computerHand.playCard(computerHand.getNumCards() - 1);
      else {
         for (int x = computerHand.getNumCards() - 1; x >= 0; x--)
            if (computerHand.inspectCard(x).compareTo(cardPlayerPlayed) == 1) {
               cardToPlay = computerHand.playCard(x);
               break;
            }
         if (cardToPlay == null)
            cardToPlay = computerHand.playCard(computerHand.getNumCards() - 1);
      }
      return cardToPlay;
   }

   private CardTable buildTable(String title, int cardsPerHand,
            int numPlayers) {
      CardTable theTable = new CardTable(title, cardsPerHand, numPlayers);

      theTable.pnlComputerHand.setLayout(new GridLayout(2, 1));
      theTable.pnlPlayArea.setLayout(new GridLayout(1, 2));
      theTable.pnlHumanHand.setLayout(new GridLayout(2, 1));

      pnlComputerHandTop = new JPanel(new FlowLayout());
      pnlComputerHandBtm = new JPanel();
      pnlHumanHandTop = new JPanel(new FlowLayout());
      pnlHumanHandBtm = new JPanel();
      hmnPlayArea = new JPanel(new GridLayout(2, 1));
      cmpPlayArea = new JPanel(new GridLayout(2, 1));

      theTable.pnlComputerHand.add(pnlComputerHandTop);
      theTable.pnlComputerHand.add(pnlComputerHandBtm);
      theTable.pnlPlayArea.add(cmpPlayArea);
      theTable.pnlPlayArea.add(hmnPlayArea);
      theTable.pnlHumanHand.add(pnlHumanHandTop);
      theTable.pnlHumanHand.add(pnlHumanHandBtm);

      return theTable;
   }

}

class GUICard {

   private static final int SUITS = 4;
   private static final int VALUES = 14;
   private static Icon[][] iconCards = new ImageIcon[VALUES][SUITS];
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   private static String[] values = { "X", "2", "3", "4", "5", "6", "7", "8",
            "9", "T", "J", "Q", "K", "A" };

   static public void loadCardIcons() {
      if (iconsLoaded == false) {
         for (int suit = 0; suit < SUITS; suit++)
            for (int value = 0; value < VALUES; value++) {
               String iconFile = "images/" + turnIntIntoCardValue(value)
                        + turnIntIntoCardSuit(suit) + ".gif";
               iconCards[value][suit] = new ImageIcon(iconFile);
            }
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }
   }

   static private String turnIntIntoCardValue(int k) {
      // Returns a one character string based upon the int value passed
      String output = "";
      if (0 <= k && k < VALUES)
         output = values[k];
      return output;
   }

   static private String turnIntIntoCardSuit(int j) {
      // Returns a one character String based upon the int passed
      String output = "";
      String[] suits = { "D", "H", "S", "C" };
      if (0 <= j && j < SUITS)
         output = suits[j];
      return output;
   }

   static private int valueAsInt(Card card) {
      char valueAsChar = card.getValue();
      int valueAsInt = -1;
      for (int i = 0; i < VALUES; i++)
         if (values[i].charAt(0) == valueAsChar) {
            valueAsInt = i;
            break;
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
      if (iconsLoaded && card != null && card.getErrorFlag() == false)
         return iconCards[valueAsInt(card)][suitAsInt(card)];
      else
         return null;
   }

   static public Icon getBackCardIcon() {
      if (iconsLoaded)
         return iconBack;
      else
         return null;
   }

}

class CardTable extends JFrame {

   private static final long serialVersionUID = 1L; // Added to silence
                                                    // compile
                                                    // error.
   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand;
   public JPanel pnlHumanHand;
   public JPanel pnlPlayArea;

   public CardTable(String title, int numCardsPerHand, int numPlayers) {
      super();
      setTitle(title);
      Border blackline = BorderFactory.createLineBorder(Color.black);

      setLayout(new GridLayout(numPlayers + 1, 1));

      pnlComputerHand = new JPanel(new FlowLayout());
      pnlPlayArea = new JPanel(new FlowLayout());
      pnlHumanHand = new JPanel(new FlowLayout());

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

class CardGameFramework {
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks; // # standard 52-card packs per deck ignoring jokers
                         // or
                         // unused cards
   private int numJokersPerPack; // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack; // # cards removed from each pack
   private int numCardsPerHand; // # cards to deal each player
   private Deck deck; // holds the initial full deck and gets smaller
                      // (usually)
                      // during play
   private Hand[] hand; // one Hand for each player
   private Card[] unusedCardsPerPack; // an array holding the cards not used
                                      // in
                                      // the game. e.g. pinochle does not use
                                      // cards 2-8 of any suit

   public CardGameFramework(int numPacks, int numJokersPerPack,
            int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
            int numPlayers, int numCardsPerHand) {
      // Constructor.
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) // > 1
                                                                   // card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if (numCardsPerHand < 1 || numCardsPerHand > numPacks
               * (52 - numUnusedCardsPerPack) / numPlayers)
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         hand[k] = new Hand();
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

   public CardGameFramework() {
      // constructor overload/default for game like bridge
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k) {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck() {
      return deck.dealCard();
   }

   public int getNumCardsRemainingInDeck() {
      return deck.getNumCards();
   }

   public void newGame() {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard(unusedCardsPerPack[k]);

      // add jokers
      for (k = 0; k < numPacks; k++)
         for (j = 0; j < numJokersPerPack; j++)
            deck.addCard(new Card('X', Card.Suit.values()[j]));

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal() {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards; k++)
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard(deck.dealCard());
            else {
               enoughCards = false;
               break;
            }

      return enoughCards;
   }

   void sortHands() {
      for (int k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   Card playCard(int playerIndex, int cardIndex) {
      if (playerIndex < 0 || playerIndex > numPlayers - 1 || cardIndex < 0
               || cardIndex > numCardsPerHand - 1) // returns bad card if
                                                   // either
                                                   // argument is bad
         return new Card('M', Card.Suit.SPADES); // Creates a card that does
                                                 // not
                                                 // work
      else
         return hand[playerIndex].playCard(cardIndex); // return the card
                                                       // played
   }

   boolean takeCard(int playerIndex) {
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;// returns false if either argument is bad
      else if (deck.getNumCards() <= 0) // Are there enough Cards?
         return false;
      else
         return hand[playerIndex].takeCard(deck.dealCard());
   }

}

class Card {
   // Created by Jason Lloyd

   public static enum Suit {
      DIAMONDS, HEARTS, SPADES, CLUBS
   };

   public static char[] valueRanks = { 'X', '2', '3', '4', '5', '6', '7', '8',
            '9', 'T', 'J', 'Q', 'K', 'A' };

   private char value;
   private Suit suit;
   private boolean errorFlag;

   // Constructors
   public Card() {
      value = 'A';
      suit = Suit.SPADES;
      errorFlag = false;
   }

   public Card(char value, Suit suit) {
      set(value, suit);
   }

   // Accessors

   public char getValue() {
      return value;
   }

   public Suit getSuit() {
      return suit;
   }

   public boolean getErrorFlag() {
      return errorFlag;
   }

   // Mutators

   public boolean set(char value, Suit suit) {
      // Test to see if the values passed are valid
      if (isValid(value, suit)) {
         // Values are OK, now set both values
         this.suit = suit;
         this.value = value;
         errorFlag = false;
      } else
         errorFlag = true;
      return !errorFlag; // If no error, then card was valid.
   }

   public String toString() {
      // toString outputs the value and suit of the card as a string
      String output = "";

      if (errorFlag)
         output = "[invalid]";
      else {
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
      return ((value == card2.getValue()) && (suit == card2.suit));
   }

   private boolean isValid(char value, Suit suit) {
      // Test value and suit to make sure they have valid entries.
      if (suit == Suit.CLUBS || suit == Suit.DIAMONDS || suit == Suit.HEARTS
               || suit == Suit.SPADES)
         if (value == 'X' || value == 'A' || value == '2' || value == '3'
                  || value == '4' || value == '5' || value == '6'
                  || value == '7' || value == '8' || value == '9'
                  || value == 'T' || value == 'J' || value == 'Q'
                  || value == 'K')
            return true; // Both value and suit are valid.
         else
            return false; // Invalid value.
      else
         return false; // Invalid suit.
   }

   public int compareTo(Card card2) {
      // Implements the compareTo function for Card
      // If the value of "this" is equal to card2.value 0 is returned
      // If the value of "this" is greater than card2, 1 is returned
      // If the value of "this" is lesser than card2, -1 is returned

      char valueOfCard = card2.getValue();
      int thisValue = -1;
      int cardValue = -1;
      int output;

      for (int n = 0; n < valueRanks.length; n++)
         if (valueOfCard == valueRanks[n])
            cardValue = n;

      for (int n = 0; n < valueRanks.length; n++)
         if (value == valueRanks[n])
            thisValue = n;

      if (thisValue == cardValue)
         output = 0;
      else if (thisValue > cardValue)
         output = 1;
      else
         output = -1;

      return output;
   }

   static void arraySort(Card[] cardsToSort, int arraySize) {
      // Bubble Sort Needed
      Card tempCard;
      boolean swap = true;

      try {
         while (swap) {
            swap = false;
            for (int n = 0; n < arraySize - 1; n++)
               if (cardsToSort[n].compareTo(cardsToSort[n + 1]) == -1) {
                  tempCard = cardsToSort[n];
                  cardsToSort[n] = cardsToSort[n + 1];
                  cardsToSort[n + 1] = tempCard;
                  swap = true;
               }
         }
      } catch (NullPointerException e) {
         // The cardsToSort array contains a null card
         // End the sort
      }
   }
}

class Deck {
   // Created by Jenn Engblom, et al

   public static final int MAX_PACKS = 6;
   public static final int CARDS_IN_A_PACK = 56;
   public static final int MAX_CARDS = MAX_PACKS * CARDS_IN_A_PACK;

   private static Card masterPack[] = new Card[CARDS_IN_A_PACK];
   private Card[] cards;
   private int topCard; // Always points to the last member of cards
   private int numPacks;

   private static boolean initializeMP = false;

   public Deck() {
      // constructor
      numPacks = 1;
      cards = new Card[numPacks * CARDS_IN_A_PACK];
      allocateMasterPack();
      init(numPacks);
   }

   public Deck(int numberOfPacks) {
      // populates arrays and assigns initial values to members
      // Deck(int numPacks) - a constructor that populates the arrays and
      // assigns initial values to members.

      if (numberOfPacks > MAX_PACKS) {
         numPacks = MAX_PACKS;
         System.out.println(
                  "The number was greater than 6. Reducing the number of packs to 6.");
      } else
         numPacks = numberOfPacks;
      init(numPacks);
   }

   public void init(int numOfPacks) {
      // repopulate cards[] with 52*numPacks cards
      allocateMasterPack();
      numPacks = numOfPacks;
      cards = new Card[numPacks * CARDS_IN_A_PACK];
      topCard = -1; // No cards are in the deck to start
      for (int i = 0; i < numPacks; i++)
         for (int j = 0; j < masterPack.length; j++) {
            topCard++;
            cards[topCard] = new Card(masterPack[j].getValue(),
                     masterPack[j].getSuit());
         }
   }

   public void shuffle() {
      // mixes up the cards with standard random number generator

      // An arraylist was easier to remove elements from
      // I took the top of the deck to be the 0th position, not the last
      // position
      ArrayList<Card> cardsList = new ArrayList<>(Arrays.asList(cards));
      // the random number's range is reduced by 1 each time a card has been
      // shuffled
      for (int i = 0; i < cards.length; i++) {
         Random rand = new Random();
         int nextCard = rand.nextInt(cardsList.size());
         cards[i] = cardsList.get(nextCard);
         cardsList.remove(nextCard);
      }
   }

   public Card dealCard() {
      // returns and removes the card in the top occupied position of cards[]
      // calling topCard as that is the card we want

      // give a fake card if there are no more cards in the deck
      if (topCard < 0) {
         Card card = new Card('B', Card.Suit.SPADES);
         return card;
      } else {
         Card cardToReturn = cards[topCard];
         topCard--;
         return cardToReturn;
      }
   }

   public int getTopCard() {
      // a getter for topCard
      return topCard;
   }

   public Card inspectCard(int k) {
      // accessor for individual card and return a card with errorFlag = true
      // if
      // k is bad
      if (k > topCard) // this is a fake card to induce an error in the Card
                       // class...blammo
         return new Card('B', Card.Suit.SPADES);
      else
         return cards[k];
   }

   private static void allocateMasterPack() {
      // will break if it has already been called once
      // create counter that increments if there has been a call
      // to it and if the counter is greater than 1, it breaks
      int i = 0;
      if (initializeMP == true)
         return;
      else {
         char values[] = { 'X', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                  'J', 'Q', 'K', 'A' };
         for (Card.Suit suit : Card.Suit.values())
            for (int x = 0; x < 14; x++) {
               Card card = new Card(values[x], suit);
               masterPack[i] = card;
               i++;
            }
         initializeMP = true;
      }
   }

   // private void sort() {
   // init(numPacks);
   // }

   public boolean addCard(Card card) {
      int numOfFounds = 0;
      boolean cardAdded = false;

      for (int c = 0; c <= topCard; c++)
         if (cards[c].equals(card))
            numOfFounds++;

      if (numOfFounds < numPacks) {
         cards[++topCard] = card;
         cardAdded = true;
      }

      return cardAdded;
   }

   public boolean removeCard(Card card) {
      boolean foundTheCard = false;

      for (int c = 0; c <= topCard; c++)
         if (cards[c].equals(card)) {
            cards[c] = cards[topCard];
            topCard--;
            foundTheCard = true;
            break;
         }

      return foundTheCard;
   }

   public int getNumCards() {
      return (topCard + 1);
   }

}

class Hand {
   // Created by Oscar Alba.

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

      if (numCards >= MAX_CARDS)
         validCheck = false;
      else {
         myCards[numCards] = card;
         numCards++;
         validCheck = true;
      }
      return validCheck;
   }

   public Card playCard(int cardIndex) {
      Card cardToPlay;

      if (numCards > 0 && cardIndex < numCards) {
         cardToPlay = myCards[cardIndex];
         for (int x = cardIndex; x < numCards - 1; x++)
            myCards[x] = myCards[x + 1];
         numCards--;
      } else
         cardToPlay = new Card('B', Card.Suit.SPADES);
      return cardToPlay;
   }

   public String toString() {
      // Overrides java.lang.Object.toString().
      String string = "[ ";
      for (int c = 0; c < numCards; c++)
         string += myCards[c] + " ";
      string += "]";
      return string;
   }

   public int getNumCards() {
      return numCards;
   }

   public Card inspectCard(int k) {
      if (k > numCards)
         return new Card('B', Card.Suit.SPADES); // Return purposely-invalid
                                                 // card.
      else
         return myCards[k];
   }

   public void sort() {
      Card.arraySort(myCards, numCards);
   }

}