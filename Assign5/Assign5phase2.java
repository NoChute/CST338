import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Assign5phase2 {

   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JButton[] humanLabels = new JButton[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
   
   public static void main(String[] args)
   {
      
      GUICard guicard = new GUICard();
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CST 338 Card Table", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(700, 700);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      // Computer Labels first
      for (int x = 0; x < computerLabels.length; x++) {
         computerLabels[x] = new JLabel(guicard.getBackCardIcon());
      }
      
      for (int x = 0; x < humanLabels.length; x++) {
         humanLabels[x] = new JButton(guicard.getIcon(generateRandomCard()));
         humanLabels[x].setBorderPainted(false);
      }
      
      for (int x = 0; x < playedCardLabels.length; x++ ) {
         playedCardLabels[x] = new JLabel(guicard.getIcon(generateRandomCard()));
      }
      
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);
      
      // ADD LABELS TO PANELS -----------------------------------------
      
      for (JLabel computerLabel : computerLabels) {
         myCardTable.pnlComputerHand.add(computerLabel);
      }
      
      for (JButton humanLabel : humanLabels) {
         myCardTable.pnlHumanHand.add(humanLabel);
      }
      // and two random cards in the play region (simulating a computer/hum ply)
      
      myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      myCardTable.pnlPlayArea.add(playedCardLabels[1]);
      
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);
      
      // show everything to the user
      myCardTable.pack();
      myCardTable.setVisible(true);
   }

   static Card generateRandomCard() {
      return new Deck().inspectCard(new Random().nextInt(56));
   }
   
}