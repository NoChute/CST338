
import java.util.*;
import java.lang.Math;

interface BarcodeIO {

   public boolean scan(BarcodeImage bc);

   public boolean readText(String text);

   public boolean generateImageFromText();

   public boolean translateImageToText();

   public void displayTextToConsole();
   
   public void displayImageToConsole();
}

class DataMatrix implements BarcodeIO {
   
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;
   
   //Empty constructor
   public DataMatrix() {
      this.text = "";
      this.actualHeight = 0;
      this.actualWidth = 0;
      image = new BarcodeImage();
   }
   
   //Non-empty contructor 
   //Takes a BarcodeImage, cleans it up, and stores it in this.image
   public DataMatrix(BarcodeImage image) {
	  this();
      scan(image);
   }  
   
   //Non-empty contructor
   //Takes a String, and stores it in this.text
   public DataMatrix(String text) {
      this();
      readText(text);
   }
   
   //Print the this.imaage to the console
   public void displayImageToConsole() {
      // Print out a top border
      for (int x = 0; x < actualWidth + 2; x++) {
         System.out.print('-');
      }
      System.out.println();
      // Print out a row, bordered by '|' chars
      for (int y = actualHeight-1; y >= 0; y--){
         System.out.print('|');
         for (int x = 0; x < actualWidth; x++) {
            if (image.getPixel(x,y)) {
               System.out.print(BLACK_CHAR);
            } else {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.print('|');
         System.out.println();
      }
      //Print out a bottom border
      for (int x = 0; x < actualWidth + 2; x++) {
         System.out.print('-');
      }
      System.out.println();
   }
   
   //Left and bottom justify this.image 
   public void cleanImage() {
         
      //Search the image from the bottom up for the first false.
      int bottom = findBottom();
      //Now that bottom is found, find the left edge of the image.
      int left = findLeft(bottom);
      
      //Make a clone of this.image so that we can now manipulate this.image
      BarcodeImage copyOfImage;
      copyOfImage = this.image.clone();
      
      //Reset this.image to all false
      clearImage();
      
      //With the left and bottom edges known, copy the useful image out of 
      //copyOfImage. 
      //Now copy the pixel at copyOfImage[x+left] into image[x]
      for (int x = 0; (x + left) < BarcodeImage.MAX_WIDTH; x++) {
         if (copyOfImage.getPixel((x+left), bottom)) {
            //continue to the second for loop
         } else {
            // We've reached the end of the closed border, exit the for
            this.actualWidth = x;
            break;
         }
         for (int y = 0; y + bottom < BarcodeImage.MAX_HEIGHT; y++) {
            if (copyOfImage.getPixel(left, y + bottom)) {
               image.setPixel(x, y, 
                  copyOfImage.getPixel(x + left, y + bottom));
            } else {
               //We've reached the top of the image, exit the for
               this.actualHeight = y;
               break;
            }
         }
      }
   }
  
  // clearImage() sets the entire this.image to false
   private void clearImage() {
      this.actualHeight = 0;
      this.actualWidth = 0;
      for (int x = 0; x < image.MAX_WIDTH; x++) {
         for (int y = 0; y < image.MAX_HEIGHT; y++) {
            image.setPixel(x,y, false);
         }
      }
   }
  
  // findBottom() will scan the image to find the first true value
   // Output: the index of the first true "y" coordinate 
   private int findBottom() {
      int bottom = 0;
      for (int x = 0; x < image.MAX_WIDTH; x++) {
         for (int y = 0; y < image.MAX_HEIGHT; y++) {
            if (image.getPixel(x,y)) {
               bottom = y;
               break;
            }
         }
      }
      return bottom;
   }
      
   // findLeft() looks for the first "x" coordinate in the image
   // the "y" coordinate is required from findBottom()
   private int findLeft(int y) {
      int left = 0;
      for (int x = 0; x < image.MAX_WIDTH; x++) {
         if (image.getPixel(x,y)) {
            left = x;
            break;
         }
      }
      return left;
   }
   
   // Decodes the this.image and stores it into this.text
   public boolean translateImageToText() {      
      // decodedCharacters[] is two characters less than
      // actualWidth, as the first and last columns is border
      char[] decodedCharacters = new char[actualWidth-2];
      // Initialize the accumulator
      int columnValue = 0;
      for (int x = 1; x < actualWidth - 1; x++) {
         for (int y = 1; y < actualHeight - 1; y++) {
            if (image.getPixel(x,y)) {
               columnValue += Math.pow(2, y-1);
            }
         }
         //Convert the integer into a char, then add to the char array
         decodedCharacters[x - 1] = (char)columnValue;
         //Reset the accumulator back to 0
         columnValue = 0;
      }
      //Convert the char array into a string and store into this.text
      this.text = String.valueOf(decodedCharacters);
      return true;
   }
   
   // Encodes the this.text string into this.image
   public boolean generateImageFromText() {
      char[] charArray = this.text.toCharArray();
      //System.out.println("At start: height = " + this.actualHeight);
      if (charArray.length > 0) {
      
         clearImage();
 
         this.actualWidth = charArray.length + 2;
        
         // Populate the image (without borders)
         for (int x = 0; x < charArray.length; x++) {
            // Convert the char to encode into an int.
            int numberToEncode = (int)charArray[x];
            // Column (x + 1) is used because the first column
            // is already in use
            writeCharToCol((x+1), numberToEncode);
         }         
          
         //Create the bottom row          
         for (int x = 0; x < this.actualWidth; x++) {
            this.image.setPixel(x, 0, true);
         }      
         this.actualHeight++;
         
         // Create the top row
         for (int x = 0; x < actualWidth + 2; x++) {
            if ((x % 2) == 0) {
               this.image.setPixel(x, actualHeight+1, true);
            } else {
               this.image.setPixel(x, actualHeight+1, false);
            }
         }
         this.actualHeight += 2;
         
         // Create the right column
         for (int y = 0; y < actualHeight; y++) {
            if ((y % 2) == 0) {
               this.image.setPixel(actualWidth - 1, y, true);
            } else {
               this.image.setPixel(actualWidth - 1, y, false);
            }            
         }
         
         // Finish by adding the first column
         for (int y = 0; y < actualHeight; y++) {
            this.image.setPixel(0, y, true);
         }       
         return true;
      } else {
         return false;
      }
   }
   
   private boolean writeCharToCol(int col, int code) {
      int bitmask = 0;
      for (int powerOf2 = 0; powerOf2 < image.MAX_HEIGHT; powerOf2++) {
         // Calcuate a bitmask to AND to our number
         bitmask = (int)Math.pow(2,powerOf2);
         if (bitmask <= code) {
            // If the result of a bitwise AND equals the bitmask, 
            // then we need to set the bit that cooresponds to that power
            if ((code & bitmask) == bitmask) {
            // (powerOf2+1) is used as the first row of the image is a border
               this.image.setPixel( (col), (powerOf2 + 1), true);
               if ( powerOf2 >= getActualHeight() ) {
                  this.actualHeight = (powerOf2 + 1);
               }
            }
         } else {
            continue;
         }
      }
      return true;
   }

   public boolean scan(BarcodeImage image) {
      this.image = image.clone();
      cleanImage();
      return true;
   }

   public int getActualWidth() {
      return actualWidth;
   }

   public int getActualHeight() {
      return actualHeight;
   }
   
   public void displayTextToConsole() {
      System.out.println(this.text);
   }
   
   public boolean readText(String text) {
      if (text.length() < image.MAX_WIDTH) {
         this.text = text;
         return true;
      } else {
         this.text = "";
         return false;
      }
   }

   private int computeSignalWidth() {
      int accumulator = 0;
      
      while (this.image.getPixel(accumulator, 0)) {
         accumulator++;
      }
      return accumulator;
   }
   
   private int computeSignalHeight() {
      int accumulator = 0;
	  
      while (this.image.getPixel(0, accumulator)) {
         accumulator++;
      }
      return accumulator;
   }
}
   
class BarcodeImage implements Cloneable {
 /*********************
Class Name: BarcodeImage
Public Methods: 
   boolean setPixel(int, int, boolean)
   boolean getPixel(int, int)
   void displayToConsole()
   BarcodeImage clone()
Public Members:
   MAX_HEIGHT
   MAX_WIDTH
Function: BarcodeImage stores the 2D scan as a multidimensional array of booleans
*********************/  

   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   
   // image_data[0][0] is the bottom left of the text image
   private boolean[][] image_data = new boolean[MAX_WIDTH][MAX_HEIGHT];

   // Empty constructor. All cells are set to false
   public BarcodeImage() {
      for (int x = 0; x < MAX_WIDTH; x++) {
         for (int y = 0; y < MAX_HEIGHT; y++) {
            setPixel(x, y, false);
         }
      }
   }
   
   // Non empty constructor Takes a String array
   // and converts it into image_data[][]
   public BarcodeImage(String[] str_data) {
      this();
      
      // Validate that the String[] can fie inside this.image
	  // If not, remain empty.
      if (checkSize(str_data)) {
		  //The String[] Y axis is opposite of image_data
		  //So some creativity is needed
		  //topOfInput will be the index of the top row in image_data
		  int topOfInput = str_data.length - 1;
		  boolean pixelValue;
         //Now loop through each String in str_data 
         // and copy into this.image_data.
         //The str_data[0] is at the "top" of the picture, 
         // but image_data[x][0] is at the bottom.  
         for (int y = 0; y <= topOfInput; y++) {
            for (int x = 0; x < str_data[y].length(); x++) {
               if (str_data[y].charAt(x) == '*') {
                  pixelValue = true;
               } else {
                  pixelValue = false;
               }
               // topOfInput - y will set the y axis correctly
               setPixel( x, topOfInput - y, pixelValue );
            }
         }
      } 
   }
   
   // Set the value of this.image_data[row][col] to value.
   // If either row or col is out of range, return false.
   // Otherwise, set the pixel and return true.
   public boolean setPixel(int row, int col, boolean value) {
      boolean returnValue;
   
      if (row > MAX_WIDTH || col > MAX_HEIGHT) {
         returnValue = false;
      } else {
         this.image_data[row][col] = value;
         returnValue = true;
      }
      return returnValue;
   }     
   
   // Retrieve the value located in this.image_data[row][col].
   // The spec allows for returning false if either row or col
   //  is out of bounds.
   public boolean getPixel(int row, int col) {
      boolean returnValue;
   
      if (row > MAX_WIDTH || col > MAX_HEIGHT) {
         returnValue = false;
      } else {
         returnValue = this.image_data[row][col];
      }
      return returnValue;
   }
   
   // checkSize() validates that the String[] passed
   // can fit inside this.image_data.
   private boolean checkSize(String[] data) {
      boolean isGoodInput = false;
      //data.length must be between 0 and MAX_HEIGHT
      if (0 < data.length && data.length < MAX_HEIGHT) {
         for (int i = 0; i < data.length; i++) {
            //Each String inside data must be between 0 & MAX_WIDTH
            // characters long.
            if (0 < data[i].length() && data[i].length() < MAX_WIDTH) {
               isGoodInput = true;
            }
         }
      }
      return isGoodInput;
   }
   
   // Prints this.image_data to the screen.
   public void displayToConsole() {
      // Start at the top of this.data, and work down
      for (int y = MAX_HEIGHT - 1; y >= 0; y--) {
         for (int x = 0; x < MAX_WIDTH; x++) {
            if (getPixel(x, y)) {
               System.out.print('*');
            } else {
               System.out.print(' ');
            }
         }
         //Print a new line
         System.out.println();
      }
   }
  
  // Implement the Clonable interface
   public BarcodeImage clone() {
      BarcodeImage copy;
      try {
         copy = (BarcodeImage) super.clone();
         //clone() can't handle 2D arrays
         // so clone() is run over each column instead
         copy.image_data = new boolean[MAX_WIDTH][];
         for (int x = 0; x < MAX_WIDTH; x++) {
            copy.image_data[x] = this.image_data[x].clone();
         }
         return copy;
      } catch (CloneNotSupportedException e) {
         return this;
      }
   }     
}

public class Assign4 {
   
   public static void main(String[] arg) {
      
      String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "

      };      
            
         
      
      String[] sImageIn_2 =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "

      };
     
      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);
     
      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
   }
}
