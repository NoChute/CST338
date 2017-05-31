
import java.util.ArrayList;

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

   public Card playCard(int cardIndex) {
      Card cardToPlay;
      
      if (this.numCards > 0 && cardIndex < this.numCards) {       
         cardToPlay = myCards[cardIndex];
         for (int x = cardIndex; x < numCards - 1; x++) {
            myCards[x] = myCards[x+1];
         }
         numCards--;
      } else {
         cardToPlay = new Card('B', Card.Suit.SPADES);
      }
      return cardToPlay;
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