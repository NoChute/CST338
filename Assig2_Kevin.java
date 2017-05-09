//Created by Kevin Hendershott, Jason Lloyd, Oscar Alba & Jennifer Engblom.
//CST338, Module 2, Assignment - Casino.

import java.util.*;
import java.lang.Math;

public class Assig2 {

   public static void main(String[] args) {
      int bet;
      TripleString pullString;
      int winning;
      do {
         bet = getBet();
         pullString = pull();
         winning = bet * getPayMultiplier(pullString);
         pullString.displayWinnings();   //Display any winnings at game's end.
         if (bet != 0 && pullString.saveWinnings(winning))
            display(pullString, winning);
         else        //User chose exit or MAX_PULLS was reached, so
            break;   //exit do-while loop.
      } while (bet != 0);  //User keeps betting & MAX_PULLS has not been reached.
      System.out.println(pullString.displayWinnings());   //Display any winnings.
   }
   
   public static int getBet() {
      //Prompts user for keyboard input, and
      //   returns input number to caller, if it's valid per specs.
      //Does not mix I/O with calculations, per CST338 Style Rules, therefore
      //   does not perform any calculations in this method, other than
      //   validation of the number input.
      //Per specs, validation of number is performed on input text.
      
      Scanner keyboard = new Scanner(System.in);
      int bet;
      do {
         System.out.print(
                  "How much would you like to bet (1 - 100) or 0 to quit? ");
         bet = keyboard.nextInt();   //Get only the first integer input.
      } while (bet < 0 || bet > 100);
      //keyboard.close();  //Blows up at runtime.  Don't know why yet.
      return bet;
   }
   
   public static TripleString pull() {
      // I would have made this private.
      TripleString thisPull = new TripleString();
      thisPull.setString1(randString());  //Pick first wheel's value.
      thisPull.setString2(randString());  //Pick second wheel's value.
      thisPull.setString3(randString());  //Pick third wheel's value.
      return thisPull;
   }
   
   private static String randString() {
      //Compiler forced me to make static?  Don't know why yet.
      double r = Math.random();
      if (0.000 <= r && r < 0.500)        //50.0% probability.
         return "BAR";
      else if (0.500 <= r && r < 0.750)   //25.0% probability.
         return "cherries";
      else if (0.750 <= r && r < 0.875)   //12.5% probability.
         return "space";
      else                                //12.5% probability.
         return "7";
   }
   
   private static int getPayMultiplier(TripleString thePull) {
      //Compiler forced me to make static?  Don't know why yet.
      String left = thePull.getString1();
      String center = thePull.getString2();
      String right = thePull.getString3();
      if (left == "cherries" && center != "cherries")
         return 5;
      else if (left == "cherries" && center == "cherries" && right != "cherries")
         return 15;
      else if (left == "cherries" && center == "cherries" && right == "cherries")
         return 30;
      else if (left == "BAR" && center == "BAR" && right == "BAR")
         return 50;
      else if (left == "7" && center == "7" && right == "7")
         return 100;
      else
         return 0;
   }
   
   public static void display(TripleString thePull, int winnings) {
      System.out.println("whirrrrrr .... and your pull is ...");
      System.out.println(" " + thePull.toString());
      if (winnings > 0)
         System.out.println("congratulations, you win: " + winnings + "\n");
      else
         System.out.println("sorry, you lose.\n");
   }

}

class TripleString {

   private String string1;
   private String string2;
   private String string3;
   public static final int MAX_LEN = 20;     //I would have made this private.
   public static final int MAX_PULLS = 40;   //I would have made this private.
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls = 0;
   
   public TripleString() {
      //Constructor for TripleString class.
      string1 = "";
      string2 = "";
      string3 = "";
   }
    
   private boolean validString(String str) {

      //In the if statement that follows, if the l-operand of && is true, then
      //   we don't need to worry about a run-time error resulting from the
      //   r-operand of && being undefined because the r-operand will never get
      //   evaluated (if I'm understanding Java correctly, or maybe
      //   str.length(null) is actually defined in Java - I haven't looked yet).
      //
      //CHECK ME TEAM!!
      //
      //Honestly, I probably would not have written this if statement this
      //   way, but this way matches the specs exactly (note: Professor said it
      //   was okay to use isEmpty() in lieu of == null).  I strongly dislike
      //   checking for "not" or negative conditions in a conditional since I
      //   feel they're NEVER intuitive!  I would have also isolated the call to
      //   isEmpty() so that the chance of encountering an undefined value is
      //   impossible, and therefore does not depend so much on a language's
      //   personality or quirks.  But like I say, this way matches the specs:

      if (!str.isEmpty() && str.length() <= MAX_LEN)
         return true;
      else
         return false;
   }
   
   public boolean setString1(String str) {
      
      //"Mutator" sets class' private string1.
      //Parameter is string value to be set.
      //Returns whether private string was updated or not.
      
      if (validString(str)) {
         string1 = str;
         return true;   //Input string was valid and updated.
      }
      else
         return false;  //Input string was invalid and not updated.
   }
   
   public boolean setString2(String str) {
      
      //"Mutator" sets class' private string2.
      //Parameter is string value to be set.
      //Returns whether private string was updated or not.
      
      if (validString(str)) {
         string2 = str;
         return true;   //Input string was valid and updated.
      }
      else
         return false;  //Input string was invalid and not updated.
   }
   
   public boolean setString3(String str) {
      
      //"Mutator" sets class' private string3.
      //Parameter is string value to be set.
      //Returns whether private string was updated or not.
      
      if (validString(str)) {
         string3 = str;
         return true;   //Input string was valid and updated.
      }
      else
         return false;  //Input string was invalid and not updated.
   }
   
   public String getString1() {
      
      //Accessor retrieves value from class' private string1.
      //Returns what ever value is stored therein.
      
      return string1;
   }
   
   public String getString2() {
      
      //Accessor retrieves value from class' private string2.
      //Returns what ever value is stored therein.
      
      return string2;
   }
   
   public String getString3() {
      
      //Accessor retrieves value from class' private string3.
      //Returns what ever value is stored therein.
      
      return string3;
   }
   
   @Override public String toString() {
      //Overrides Object.toString, but specs says to use same name toString().
      return getString1() + "  " + getString2() + "  " + getString3();
   }
   
   public boolean saveWinnings(int winnings) {
      if (numPulls <= MAX_PULLS) {
         pullWinnings[numPulls] = winnings;
         numPulls++;
         return true;
      }
      else
         return false;  //Reached MAX_PULLS.
   }
   
   public String displayWinnings() {
      int accumulator = 0;
      String temp = "";
      if (numPulls == 0)
         temp += "Good bye!\n\n";
      else {
         temp += "Thanks for playing at the Casino!\n";
         temp += "Your individual winnings were:\n";
         for (int i = 0; i < numPulls; i++) {
            accumulator += pullWinnings[i];
            temp += pullWinnings[i];
            temp += " ";   //Don't care if last number also gets trailing space.
         }
         temp += "\n";
         temp += "Your total winnings were: $" + accumulator + "\n\n";
      }
      return temp;
   }

}

/* Output *

How much would you like to bet (1 - 100) or 0 to quit? 0
Good bye!

How much would you like to bet (1 - 100) or 0 to quit? -1
How much would you like to bet (1 - 100) or 0 to quit? 555
How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 7  space  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 BAR  BAR  BAR
congratulations, you win: 1250

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 cherries  BAR  space
congratulations, you win: 125

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 cherries  cherries  cherries
congratulations, you win: 750

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 BAR  7  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 7  BAR  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 cherries  space  BAR
congratulations, you win: 125

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 BAR  cherries  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 25
whirrrrrr .... and your pull is ...
 7  BAR  7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 BAR  cherries  7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 cherries  cherries  BAR
congratulations, you win: 900

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 BAR  space  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 BAR  cherries  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 space  BAR  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 cherries  BAR  BAR
congratulations, you win: 300

How much would you like to bet (1 - 100) or 0 to quit? 60
whirrrrrr .... and your pull is ...
 7  BAR  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 22
whirrrrrr .... and your pull is ...
 BAR  BAR  BAR
congratulations, you win: 1100

How much would you like to bet (1 - 100) or 0 to quit? 22
whirrrrrr .... and your pull is ...
 cherries  space  7
congratulations, you win: 110

How much would you like to bet (1 - 100) or 0 to quit? 22
whirrrrrr .... and your pull is ...
 cherries  BAR  7
congratulations, you win: 110

How much would you like to bet (1 - 100) or 0 to quit? 22
whirrrrrr .... and your pull is ...
 space  BAR  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 22
whirrrrrr .... and your pull is ...
 BAR  space  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 22
whirrrrrr .... and your pull is ...
 BAR  BAR  space
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  BAR  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  BAR  BAR
congratulations, you win: 1700

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 cherries  cherries  7
congratulations, you win: 510

How much would you like to bet (1 - 100) or 0 to quit? 34
3whirrrrrr .... and your pull is ...
 BAR  BAR  BAR
congratulations, you win: 1700

How much would you like to bet (1 - 100) or 0 to quit? 4
whirrrrrr .... and your pull is ...
 BAR  BAR  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 space  BAR  BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
3whirrrrrr .... and your pull is ...
 cherries  BAR  BAR
congratulations, you win: 170

How much would you like to bet (1 - 100) or 0 to quit? 4
3whirrrrrr .... and your pull is ...
 cherries  BAR  BAR
congratulations, you win: 170

How much would you like to bet (1 - 100) or 0 to quit? 4
whirrrrrr .... and your pull is ...
 space  BAR  space
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  BAR  BAR
congratulations, you win: 1700

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  BAR  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  BAR  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  7  cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 34
whirrrrrr .... and your pull is ...
 BAR  7  space
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit? 
0
Thanks for playing at the Casino!
Your individual winnings were:
0 1250 125 750 0 0 125 0 0 0 900 0 0 0 300 0 1100 110 110 0 0 0 0 1700 510 1700 0 0 170 170 0 1700 0 0 0 0 
Your total winnings were: $10720

*/
