
import java.util.*;

public class Ass3 {

   public static void main(String[] args) {
      
   }
   
   static class Deck {
      
      public final int MAX_CARDS = 6 * 52;
      private static Card masterPack[] = new Card[52];
      private Card[] cards;
      private int topCard;
      private int numPacks;
      
      //empty constructor
      public Deck () {
         numPacks = 1;
      }
      
      //populates arrays and assigns initial values to members
      public Deck (int numPacks) {
         
      }
      
      //repopulate cards[] with 52*numPacks cards
      public void init (int numPacks) {
         
      }
      
      //mixes up the cards with standard random number generator
      public void shuffle () {
         for (int i = 0; i < cards.length; i++) {
            Random rand = new Random(cards.length);
            
         }
         
      }
      
      //returns and removes the card in the top occupied position of cards[]
      //turn the array into an arraylist in order to easily remove the first card.
      public Card dealCard() {
         Card card = cards[0];
         ArrayList<Card> cardsList = new ArrayList<>(Arrays.asList(card));
         int newCard = cardsList[0];
         
      }
      
      
      //a getter for topCard
      public int getTopCard() {
         return topCard;
      }
      
      
      //accessor for individual card and return a card with errorFlag = true if k is bad
      public Card inspectCard(int k) {
         
      }
      
      //will break if it has already been called once
      private static void allocateMasterPack(){
         
      }
   }
   
   
   
   
   static class Card
   {
      public static enum Suit { DIAMONDS, HEARTS, SPADES, CLUBS };
      
      private char value;
      private Suit suit;
      private boolean errorFlag;
      
      //Constructors
      public Card() 
      {
         this.value = 'A';
         this.suit = Suit.SPADES; 
         this.errorFlag = false;
      }
      
      public Card(char value, Suit suit)
      {
         this.set(value, suit);
      }
      
      //Accessors
      public char getValue()
      {
         return value;
      }
      
      public String getSuit()
      {
         String theSuit = "";
         
         switch (suit)
         {
            case SPADES:
               theSuit = "spades";
               break;
            case DIAMONDS:
               theSuit = "diamonds";
               break;
            case HEARTS:
               theSuit = "hearts";
               break;
            case CLUBS:
               theSuit = "clubs";
               break;
         }
         return theSuit;
      }
      
      public boolean getErrorFlag()
      {
         return this.errorFlag;
      }
      
      //Mutators
      public boolean set(char value, Suit suit)
      {
         boolean output = false;
         
         // Test to see if the values passed are valid
         if (isValid(value, suit))
         {     
            // Values are OK, now set both values
            this.suit = suit;
            this.value = value;
            this.errorFlag = false;
            output = true;    
         }
         else
         {
            this.errorFlag = true;
         }
         return output;
      }
     
      public String toString()
      {
         String output = "";
         
         if (errorFlag == true)
         {
            output = "[invalid]";
         } 
         else
         {
            output = getValue() + " of " + getSuit();
         }
         return output;
      }
      
      public boolean equals(Card card2)
      // Test to see if "card2" value and suit matches the current object
      {
         boolean isEquals = false;
         
         if ( (this.value == card2.getValue()) && (this.suit == card2.suit) )
         {
            isEquals = true;
         }
         
         return isEquals;
      }
      
      private boolean isValid(char value, Suit suit)
      // Test value and suit to make sure they have valid entries.
      {
         boolean output = false;
         
         if (suit == Suit.CLUBS || suit == Suit.DIAMONDS || 
               suit == Suit.HEARTS || suit == Suit.SPADES)
         {
            if (value == 'A' || value == '2' || value == '3' || value == '4' ||
               value == '5' || value == '6' || value =='7' || value == '8' ||
                  value == '9' || value == 'T' || value == 'J' || value == 'Q' ||
                     value == 'K')
            {
               output = true;
            }
         }
         return output;
      }
   }
   
   public class Hand
   {
      public static final int MAX_CARDS = 50;
      
      private Card[] myCards;
      private int numCards;
      
      
      public Hand()
      {
         myCards = new Card[MAX_CARDS];
         numCards = 0;
         
      }
      
      public void resetHand()
      {
         myCards = new Card[MAX_CARDS];
         numCards = 0;
      }
      
      public boolean takeCard(Card card)
      {
         boolean validCheck;
         
         if (numCards >= MAX_CARDS)
         {
            validCheck = false;
         }
         else
         {
            myCards[numCards] = card;
            numCards++;
            validCheck = true;
         }
         
         return validCheck;
      }
      
      public Card playCard()
      {


      }
      
      public String toString()
      {
         String string = "Hand = ( ";
         if (numCards == 0)
         {
            string += ")";
         }
         else
         {
            for(int k = 0; k < numCards-1; k++)
            {
             string += myCards[k] + ", " ;  
            }
            string += myCards[numCards - 1] + ")";
         }
         return string;
      }

      public int getNumCards()
      {
         return numCards;
      }
      
      public Card inspectCard(int k)
      {

      } 


   }
}
