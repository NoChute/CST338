import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CardTable extends JFrame {
   
   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
    
   public CardTable(String title, int numCardsPerHand, int numPlayers) {

      super();      
      setTitle(title);
      Border blackline = BorderFactory.createLineBorder(Color.black);
      
      setLayout(new GridLayout( numPlayers + 1, 1));         
      
      pnlComputerHand = new JPanel(new FlowLayout());
      pnlPlayArea = new JPanel(new GridLayout(2, 2));
      pnlHumanHand = new JPanel(new FlowLayout());

      TitledBorder cmpBorder = BorderFactory.createTitledBorder(blackline,
         "Computer Hand");
      pnlComputerHand.setBorder(cmpBorder);
      add(pnlComputerHand);
      
      TitledBorder playBorder = BorderFactory.createTitledBorder(blackline,
         "Play Area");
      pnlPlayArea.setBorder(playBorder);
      add(pnlPlayArea);
      
      TitledBorder hmnBorder = BorderFactory.createTitledBorder(blackline,
         "Your Hand");
      pnlHumanHand.setBorder(hmnBorder);
      add(pnlHumanHand);
      
   }
   
   public int getNumCardsPerHand() {
      return numCardsPerHand;
   }
   
   public int getNumPlayers() {
      return numPlayers;
   }
}