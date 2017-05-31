/****************************
* Kevin Hendershott
* Jason Lloyd
* Oscar Alba
* Jenn Engblom
* 
* 11MAY2017
* 
* CST338
* 
****************************/

import java.util.*;


public class Ass3 {

   public static void main(String[] args) {
      //Created by Kevin Hendershott.

      //Notes to self:
      //No Jokers.
      //Not concerned WHICH card is played.
      //Up to caller how many cards are dealt.
      //Deck will always contains 52x cards.
      //Client will set numPacks.
      //Client will call dealCard().
      //Client will shuffle.
      //Client will restock deck with init() but don't reinstantiate.
      
      //Phase 1 - Test run of Card class:
      System.out.println("***** Phase 1 - Test run of Card class: *****\n");
      
      Card card1 = new Card();                                    //Legal card.
      Card card2 = new Card('X', Card.Suit.valueOf("DIAMONDS"));  //Illegal card.
      Card card3 = new Card('8', Card.Suit.valueOf("HEARTS"));    //Legal card.
      
      System.out.println(card1);
      System.out.println(card2);
      System.out.println(card3);
      System.out.println();
      
      card1.set('Z', card1.suit);                      //Card1 now illegal card.
      card2.set('K', Card.Suit.valueOf("DIAMONDS"));   //Card2 now legal card.

      System.out.println(card1);
      System.out.println(card2);
      System.out.println(card3);                       //No change to card3.
      System.out.println("\n");
      
      
      //Phase 2 - Test run of Hand class:
      System.out.println("***** Phase 2 - Test run of Hand class: *****\n");
      
      card1.set('A',  card1.suit);   //Make card1 legal again,
      Card card4 = new Card('2', Card.Suit.valueOf("CLUBS"));
      Card card5 = new Card('J', Card.Suit.valueOf("SPADES"));
      Hand hand1 = new Hand();
      boolean stillTakingCards = true;
      while (stillTakingCards) {
         stillTakingCards = hand1.takeCard(card1);
         stillTakingCards = hand1.takeCard(card2);
         stillTakingCards = hand1.takeCard(card3);
         stillTakingCards = hand1.takeCard(card4);
         stillTakingCards = hand1.takeCard(card5);
      }
      System.out.println("Hand full.");
      System.out.println("After deal:");
      //Wrap the hand's string so it displays better:
      StringBuilder sb = new StringBuilder(hand1.toString());
      int x = 0;
      while ((x = sb.indexOf(" ", x + 80)) != -1)
         sb.replace(x,  x + 1, "\n");  //Add a newline every 80 characters.
      System.out.println(sb.toString());
      System.out.println("\n");
      
      System.out.println("Testing inspectCard() with just two cards:");
      System.out.println(hand1.inspectCard(4));
      System.out.println(hand1.inspectCard(5));
      System.out.println("\n");

      System.out.println("Testing playCard() with all cards:");
      while (hand1.getNumCards() > 0)
         System.out.println(hand1.playCard());
      System.out.println("\n");

      System.out.println("Testing that Hand is now empty:");
      System.out.println(hand1.toString());
      System.out.println("\n");
      
      
      //Phase 3 - Test run of Deck class:
      System.out.println("***** Phase 3 - Test run of Deck class: *****\n");

      System.out.println("Testing 2 Decks, unshuffled:\n");
      Deck twofer = new Deck();
      String twoferString = "";

      twofer.Deck(2);
      while (twofer.getTopCard() >= 0) {
         twoferString += twofer.dealCard().toString();
         if (twofer.getTopCard() > -1)
            twoferString += ", ";
      }
      //Wrap the hand's string so it displays better:
      StringBuilder sb2 = new StringBuilder(twoferString);
      x = 0;
      while ((x = sb2.indexOf(" ", x + 80)) != -1)
         sb2.replace(x,  x + 1, "\n");  //Add a newline every 80 characters.
      System.out.println(sb2.toString());
      System.out.println("\n");

      System.out.println("Testing 2 Decks, shuffled:\n");
      twofer.init(2);
      twofer.shuffle();
      twoferString = "";

      while (twofer.getTopCard() >= 0) {
         twoferString += twofer.dealCard().toString();
         if (twofer.getTopCard() > -1)
            twoferString += ", ";
      }
      //Wrap the hand's string so it displays better:
      StringBuilder sb3 = new StringBuilder(twoferString);
      x = 0;
      while ((x = sb3.indexOf(" ", x + 80)) != -1)
         sb3.replace(x,  x + 1, "\n");  //Add a newline every 80 characters.
      System.out.println(sb3.toString());
      System.out.println("\n");
      
      System.out.println("Testing 1 Deck, unshuffled:\n");
      Deck onefer = new Deck();
      String oneferString = "";

      onefer.Deck(1);
      while (onefer.getTopCard() >= 0) {
         oneferString += onefer.dealCard().toString();
         if (onefer.getTopCard() > -1)
            oneferString += ", ";
      }
      //Wrap the hand's string so it displays better:
      StringBuilder sb4 = new StringBuilder(oneferString);
      x = 0;
      while ((x = sb4.indexOf(" ", x + 80)) != -1)
         sb4.replace(x,  x + 1, "\n");  //Add a newline every 80 characters.
      System.out.println(sb4.toString());
      System.out.println("\n");

      System.out.println("Testing 1 Deck, shuffled:\n");
      onefer.init(1);
      onefer.shuffle();
      oneferString = "";
      
      while (onefer.getTopCard() >= 0) {
         oneferString += onefer.dealCard().toString();
         if (onefer.getTopCard() > -1)
            oneferString += ", ";
      }
      //Wrap the hand's string so it displays better:
      StringBuilder sb5 = new StringBuilder(oneferString);
      x = 0;
      while ((x = sb5.indexOf(" ", x + 80)) != -1)
         sb5.replace(x,  x + 1, "\n");  //Add a newline every 80 characters.
      System.out.println(sb5.toString());
      System.out.println("\n\n");
      
      
      //Phase 4
      System.out.println("***** Phase 4 - Test run of Deck and Hand combined: *****\n");
 
      int numberOfPlayers = 0;
      boolean invalidInput = true;
      Scanner keyboard = new Scanner(System.in);
      
      while (invalidInput) {
         System.out.print("Please enter the number of players (1 - 10): ");
         numberOfPlayers = keyboard.nextInt();
         if (0 < numberOfPlayers && numberOfPlayers < 11) {
             invalidInput = false;
         } else {
             System.out.println("Please enter a number between 1 and 10.");  
         }
      }
      
      Hand player[] = new Hand[10]; 
      
      Deck phase4Deck = new Deck();
      phase4Deck.init(1);
      
      for (int i = 0; i < numberOfPlayers; i++) {
         player[i] = new Hand();
      }
      
      while (phase4Deck.getTopCard() >= 0) {
         for (int i = 0; i < numberOfPlayers; i++) {
            Card cardDealt = new Card();
            cardDealt = phase4Deck.dealCard();
            if (cardDealt.getErrorFlag() == true) {
               //exit the loop, we are out of cards
            } else {
               player[i].takeCard(cardDealt);
            }
         }
      }
   
      System.out.println("The hands contain:\n");
      
      for (int i = 0; i < numberOfPlayers; i++) {
         System.out.println("Player " + (i + 1) + ":");
         System.out.println(player[i].toString());
      }  
      
      System.out.println("\nNow to shuffle up and deal...");
      for (int i = 0; i < numberOfPlayers; i++) {
         player[i].resetHand();
      }
      phase4Deck.init(1);
      phase4Deck.shuffle();
      
       while (phase4Deck.getTopCard() >= 0) {
         for (int i = 0; i < numberOfPlayers; i++) {
            Card cardDealt = new Card();
            cardDealt = phase4Deck.dealCard();
            if (cardDealt.getErrorFlag() == true) {
               //exit the loop, we are out of cards
            } else {
               player[i].takeCard(cardDealt);
            }
         }
      }
   
      System.out.println("The hands contain:\n");
      
      for (int i = 0; i < numberOfPlayers; i++) {
         System.out.println("Player " + (i + 1) + ":");
         System.out.println(player[i].toString());
      } 

   }

   
   static class Deck {
      //Created by Jenn Engblom, et al
      public final int MAX_PACKS = 6;
      public final int MAX_CARDS = MAX_PACKS * 52;
      
      private static Card masterPack[] = new Card[52];
      private Card[] cards; 
      private int topCard;
      private int numPacks;
      
      private static boolean initializeMP = false;
      
      //empty constructor
      public void Deck () {
         this.numPacks = 1;
         cards = new Card[numPacks * 52];
         
         allocateMasterPack();
         init(numPacks);
         
      }
      

      //populates arrays and assigns initial values to members
      //Deck(int numPacks) - a constructor that populates the arrays and 
      //assigns initial values to members.
      public void Deck (int numberOfPacks) {
            
         if (numberOfPacks > MAX_PACKS) {
             this.numPacks = MAX_PACKS;
             System.out.println("The number was greater than 6. Reducing the number to 6.");
         }
         else {
            this.numPacks = numberOfPacks;      
         }
         init(this.numPacks);
      }
      
      //repopulate cards[] with 52*numPacks cards
      public void init (int numOfPacks) {
         allocateMasterPack();
         cards = new Card[numOfPacks * 52];
         topCard = -1; // No cards are in the deck to start
       
         for ( int i = 0; i < numOfPacks; i++ ) {
            for ( int j = 0; j < masterPack.length; j++ ) {
               topCard++;
               cards[topCard] = new Card(masterPack[j].getValue(), masterPack[j].getSuit());
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
            cards[i] = cardsList.get(nextCard);
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
         if (k > topCard)
         {
            // this is a fake card to induce an error in the Card class...blammo
            card = new Card('B', Card.Suit.SPADES);
         }
         else
         {
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
                Card card = new Card(values[x], suit);
                masterPack[i] = card;
                i++;
                
             }
         }
         initializeMP = true;
      }
   }
   
   
   static class Card {
      //Created by Jason Lloyd.
      public static enum Suit { DIAMONDS, HEARTS, SPADES, CLUBS };
      
      private char value; // the value of the card
      private Suit suit; // the suit of the card
      private boolean errorFlag;
      
      //Constructors
      public Card() {
         this.set('A', Suit.SPADES);
      }
      
      public Card(char value, Suit suit) {
         this.set(value, suit);
      }
      
      //Accessors
      public char getValue() {
         return this.value;
      }
      
      public Suit getSuit() {
         return this.suit;
      }
      
      public boolean getErrorFlag() {
         return this.errorFlag;
      }
      
      //Mutators
      //set() calls isValid() to check on value and suit
      // if isValid() returns true the members are set
      // set() returns the value of errorFlag, which
      // would actually be "false" if set succeeds
      public boolean set(char value, Suit suit) {
         
         // Test to see if the values passed are valid
         if (isValid(value, suit)) {     
            // Values are OK, now set both values
            this.suit = suit;
            this.value = value;  
            this.errorFlag = false;
         }
         else {
            this.errorFlag = true;
         }
         return this.errorFlag;
      }
     
      public String toString() {
         String output = "";
         
         if (errorFlag) {
            output = "[illegal]";
         } 
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
      
   // Test to see if "card2" value and suit matches the current object
      public boolean equals(Card card2) {
         boolean isEquals = false;
         
         if ((this.value == card2.getValue()) && (this.suit == card2.suit)) {
            isEquals = true;
         }
         
         return isEquals;
      }
      
      
      // Test value and suit to make sure they have valid entries.
      private boolean isValid(char value, Suit suit) {
         boolean output = false;
         
         if (suit == Suit.CLUBS  || suit == Suit.DIAMONDS || 
             suit == Suit.HEARTS || suit == Suit.SPADES) {
            if (value == 'A' || value == '2' || value == '3' || value == '4' ||
                value == '5' || value == '6' || value == '7' || value == '8' ||
                value == '9' || value == 'T' || value == 'J' || value == 'Q' ||
                value == 'K') {
               output = true;
            }
         }
         return output;
      }
   }
   

   static class Hand {
      //Created by Oscar Alba.
      public static final int MAX_CARDS = 50;
      
      private ArrayList<Card> myCards = new ArrayList<Card>();
      private int numCards;
      
      
      public Hand() {
         myCards = new ArrayList<Card>();
         numCards = 0;
         
      }
     
      public void resetHand() {
         myCards = new ArrayList<Card>();
         numCards = 0;
      }
      
      public boolean takeCard(Card card) {
         boolean validCheck;
         
         if (numCards >= MAX_CARDS) {
            validCheck = false;
         }
         else {
            Card newCard = new Card(card.getValue(), card.getSuit());
            myCards.add(newCard);
            numCards++;
            validCheck = true;
         }
         
         return validCheck;
      }
      
      public Card playCard() {
         Card card = myCards.get(myCards.size() - 1);
         //System.out.println("Playing " + card);
         myCards.remove(card);
         numCards--;
         return card;
   
      }
      
      
      public String toString() {
         String string = "Hand = ";
         if (numCards == 0) {
            string += "[ ]";   //A visual indicator that the list is empty.
         }
         else {
           string += myCards;
         }
         return string;
      }
   
      public int getNumCards() {
         return numCards;
      }
      
      public Card inspectCard(int k) {
         Card card;
         if (0 < k && k <= numCards) {
            // Out of bounds. Return an invalid card
            //card = new Card('B', Card.Suit.SPADES);
            card = myCards.get(k - 1);
         }
         else {
            card = new Card('B', Card.Suit.SPADES);
            //card = myCards.get(k - 1);
         }
         return card;
      }    
   }
}
