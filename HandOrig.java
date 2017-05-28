import java.util.ArrayList;

public class HandOrig {
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
      } else {
         myCards.add(card);
         numCards++;
         validCheck = true;
      }
      return validCheck;
   }

   public Card playCard() {
      Card cardToPlay;
      if (myCards.size() > 0) {
         cardToPlay = myCards.get(myCards.size()-1);
         //System.out.println("Playing " + cardToPlay);
         myCards.remove(cardToPlay);
      } else {
         cardToPlay = new Card('B', Card.Suit.SPADES);
      }
      return cardToPlay;
   }

   public String toString() {
      String string = "";
      if (numCards == 0) {
         string += " ";
      } else {
         string  += myCards;
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
      } else {
         card =  myCards.get(myCards.size()-1);
      }
      return card;
   } 
   
   public void sort() {
      System.out.println("Sort got called");
      Card.arraySort(myCards.toArray(new Card[numCards]), numCards);
   }
}