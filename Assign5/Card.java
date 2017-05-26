public class Card
{
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
      
   public int compareTo(Card card2) {
      //Implements the compareTo function for Card
      //If the value of "this" is equal to card2.value 0 is returned
      //If the value of "this" is greater than card2, 1 is returned
      //If the value of "this" is lesser than card2, -1 is returned
      
      char valueOfCard = card2.getValue();
      int thisValue = -1;
      int cardValue = -1;
      int output;
      
      for (int n = 0; n < valueRanks.length; n++) {
         if (valueOfCard == valueRanks[n]) {
            cardValue = n;
         }
      }
      
      for (int n = 0; n < valueRanks.length; n++) {
         if (this.value == valueRanks[n]) {
            thisValue = n;
         }
      }
      
      if (thisValue == cardValue) {
         output =  0;
      } else if (thisValue > cardValue) {
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
               if (cardsToSort[n].compareTo(cardsToSort[n+1]) == 1) {
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