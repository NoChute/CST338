import java.util.*;
import javax.swing.*;
import java.awt.*;

public class TestClass extends JFrame {

   public static void main(String[] args) {
   
      Card[] testCards = new Card[10];
      
      // Test 1 /
      populateCards(testCards);
      test1(testCards);
      
      // Test 2            
      populateCards(testCards);
      TestClass test2 = new TestClass("Test 2: GUICard", testCards);
      test2.setVisible(true);     
      
      // Test 3
      populateCards(testCards);
      test3(testCards);
      
      // Test 4
      test4();
      
      // Test 5
      System.out.println("\n*** Test 5 ***\n");
      System.out.println("Let's generate a card randomly");
      System.out.println("Here is is: " + generateRandomCard() );
   }
   
   static Card generateRandomCard() {
      return new Deck().inspectCard(new Random().nextInt(56));
   }
   
   public TestClass(String title, Card[] cards) {
      super();
      setTitle(title);       
      setSize(400, 400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      setLayout(layout);
      
      GUICard g = new GUICard();
      JLabel[] labels = new JLabel[cards.length];
      
      for (int c = 0; c < cards.length; c++) {
         Icon cIcon = g.getIcon(cards[c]);
         if (cIcon == null) {
            System.out.println("NULL FOUND");
         } else {
            labels[c] = new JLabel(cIcon);
            add(labels[c]);
         }
      }
      
   }
   
   static void populateCards( Card[] theArray ) {
      
      theArray[0] = new Card();
      theArray[1] = new Card('X', Card.Suit.HEARTS);
      theArray[2] = new Card('J', Card.Suit.DIAMONDS);
      theArray[3] = new Card('5', Card.Suit.CLUBS);
      theArray[4] = new Card('6', Card.Suit.SPADES);
      theArray[5] = new Card('K', Card.Suit.HEARTS);
      theArray[6] = new Card('4', Card.Suit.HEARTS);
      theArray[7] = new Card('Q', Card.Suit.DIAMONDS);
      theArray[8] = new Card('2', Card.Suit.CLUBS);
      theArray[9] = new Card('8', Card.Suit.SPADES);
      
      
   }

   static void printCardArray( Card[] theArray ) {
      for (int n = 0; n < theArray.length; n++) {
         System.out.println(theArray[n]);
      }
   }
   
   static void test1(Card[] test1Cards) {
      
      System.out.println("\n*** Test 1*** \n");
      System.out.println("Before Sort:");
      printCardArray(test1Cards);
      
      Card.arraySort(test1Cards, test1Cards.length);
      System.out.println("\nAfter Sort:");
      printCardArray(test1Cards);
   
   
      Hand myHand = new Hand();
      
      myHand.takeCard(test1Cards[6]);
      myHand.takeCard(test1Cards[5]);
      
      System.out.println("My hand contains:");
      System.out.println(myHand);
      
      System.out.print("These 2 cards are ");
      if (!myHand.playCard(0).equals(myHand.playCard(0))) {
         System.out.print("not ");
      }
      System.out.println("the same");
      
      System.out.println("My hand contains:");
      System.out.println(myHand);
      System.out.println(" *** End Test 1 ***\n\n");
   }
 
   static void test3(Card[] test3Cards) {
      System.out.println("/n*** Test 3 ***/n");
      Hand myHand = new Hand();
      for (Card c : test3Cards) {
         myHand.takeCard(c);
      }
      System.out.println("Before Sort:");
      System.out.println(myHand);
      myHand.sort();
      System.out.println("After Sort:");
      System.out.println(myHand);      
   }

   static void test4() {
      
      System.out.println("\n*** Test 4 ***\n");
      Deck myDeck = new Deck();
      myDeck.shuffle();
      
      System.out.println("Number of cards: " + myDeck.getNumCards() );
      for (int i = 0; i <= myDeck.getTopCard(); i++) {
         System.out.println(myDeck.inspectCard(i));
      }
      
      // Lets remove the jokers
      Card[] jokers = new Card[4];
      jokers[0]   = new Card('X', Card.Suit.SPADES);
      jokers[1]   = new Card('X', Card.Suit.HEARTS);
      jokers[2]   = new Card('X', Card.Suit.DIAMONDS);
      jokers[3]   = new Card('X', Card.Suit.CLUBS);
      
      for (Card c : jokers) {
         if(myDeck.removeCard(c)) {
            System.out.println(c + " removed.");
         }
         
      }
  
      System.out.println("Now :");
      while( 0 < myDeck.getNumCards() ) {
         System.out.println(myDeck.dealCard());
      }
      
      System.out.println("Deck is now empty. Topcard is " + myDeck.getTopCard());
      System.out.println("Let's add the jokers");
      
      for (Card c : jokers) {
         myDeck.addCard(c);
      }
      
      System.out.println("Now show the deck:");
      while( myDeck.getNumCards() > 0) {
         System.out.println(myDeck.dealCard());
      }
      
   }
}