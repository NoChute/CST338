
import java.util.*;

class Deck {
   //Created by Jenn Engblom, et al
   public static final int MAX_PACKS = 6;
   public static final int CARDS_IN_A_PACK = 56;
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
      char values[] = {'X', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
      for (Card.Suit suit : Card.Suit.values()) {
          for (int x = 0; x < 14; x++) {
             Card card = new Card(values[x], suit);
             masterPack[i] = card;
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