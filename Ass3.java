
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
      Scanner sc = new Scanner(System.in);
      System.out.println("Number of packs: ");
      int i = sc.nextInt();
      System.out.println("You chose " + i + " packs.");
      Deck pile = new Deck();
      pile.Deck(i);
      
      
   }
   
   static class Deck {
      //Created by Jenn Engblom
      
      public final int MAX_CARDS = 6 * 52;
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
         
         for (topCard = 0; topCard < cards.length; topCard++) {
            cards[topCard].set(masterPack[topCard].getValue(), masterPack[topCard].getSuit());
         }
      }
      

      //populates arrays and assigns initial values to members
      //Deck(int numPacks) - a constructor that populates the arrays and 
      //assigns initial values to members.
      public void Deck (int numPacks) {

         if (numPacks > 6) {
             this.numPacks = 6;
             init(numPacks);
         }
         else{
            init(numPacks);
         }
      }
      
      
      //repopulate cards[] with 52*numPacks cards
      public void init (int numPacks) {
         allocateMasterPack();
         cards = new Card[numPacks * 52];
         topCard = 0;
       
         for ( int i = 0; i < numPacks; i++ ) {
            for ( int j = 0; j < masterPack.length; j++ ) {
               cards[topCard] = new Card(masterPack[j].getValue(), masterPack[j].getSuit());
               topCard++;
            }
         }
         System.out.println(Arrays.toString(cards));
         System.out.println(cards.length);
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
      //top card means at index 0, not the last index in array
      public Card dealCard() {
         int used = 0; //cards used up til now
         used++;
         return cards[used - 1];
         
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
      //create counter that increments if there has been a call to it and if the counter is greater than 1, it breaks
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
         //this.value = 'A';
         //this.suit = Suit.SPADES; 
         //this.errorFlag = false;
         set('A', Suit.valueOf("SPADES"));
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
      public boolean set(char value, Suit suit) {
         boolean output = false;
         
         // Test to see if the values passed are valid
         if (isValid(value, suit)) {     
            // Values are OK, now set both values
            this.suit = suit;
            this.value = value;
            this.errorFlag = false;
            output = true;    
         }
         else {
            this.errorFlag = true;
         }
         return output;
      }
     
      public String toString() {
         String output = "";
         
         if (errorFlag == true) {
            output = "[invalid]";
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
         
         if (suit == Suit.CLUBS || suit == Suit.DIAMONDS || 
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
   

   public class Hand {
      //Created by Oscar Alba.
      public static final int MAX_CARDS = 50;
      
      private ArrayList<Card> myCards = new ArrayList<Card>();
      private int numCards;
      
      
      public Hand()
      {
         myCards = new ArrayList<Card>();
         numCards = 0;
         
      }
     
      public void resetHand()
      {
         myCards = new ArrayList<Card>();
         numCards = 0;
      }
      
      public boolean takeCard(Card card)
      {
         boolean validCheck;
         
         if (numCards >= MAX_CARDS) {
            validCheck = false;
         }
         else {
            myCards.add(card);
            numCards++;
            validCheck = true;
         }
         
         return validCheck;
      }
      
      public Card playCard() {
         Card card = myCards.get(myCards.size()-1);
         System.out.println("Playing " + card);
         myCards.remove(card);
   
         return card;
   
      }
      
      public String toString() {
         String string = "Hand = ( ";
         if (numCards == 0)
         {
            string = ")";
         }
         else
         {
           string  = "(" + myCards + ")";
         }
         return string;
      }
   
      public int getNumCards() {
         return numCards;
      }
      
      public Card inspectCard(int k) {
         Card card;
         if (k <= numCards) {
            card = new Card('B' , Card.Suit.SPADES);
         }
         else {
            card =  myCards.get(myCards.size()-1);
         }
         return card;
      } 
   
   
   }
}
