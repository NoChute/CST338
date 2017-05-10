
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
