//Created 2017-05-18 by Kevin Hendershott, et al.

//Assumptions (and a few of the Specifications reiterated):
//   All input strings will be an array of rows of identical element length.
//   Each row consists of characters, the position of which represent columns.
//   The bottom-most row is the origin row and resides in the last array element.
//   The left-most column is the origin column and resides in each row[0].
//   The origin of the barcode will be the lower-left corner.
//   All input rectangles will be symmetrical, regardless of whitespace.
//   The input will contain at least one correctly formed barcode.
//   The input may NOT contain more than one correctly formed barcode, such as
//      the example graphic near the top of the specifications which contained
//      four barcodes adjacent to each other.  We don't handle that scenario
//      herein because we don't test for any Open Borderlines to identify other
//      barcodes, or adjacent barcodes, contained within the same image.
//   The input barcode will have a correctly formed "Closed Limitation Lines".
//   The input barcode will have a correctly formed "Open Borderlines".
//   "White" characters are represented by the ASCII space character " ".
//   "Black" characters are represented by the ASCII apostrophe character "*".
//   A row of all "white" characters is not part of the barcode "signal".
//   A column of all "white" characters is not part of the barcode "signal".
//   The input image will be in the form a of single-dimensional String array as
//      described above.  This single-dimensional array will need to be
//      parsed and "rotated" to fit into the internal multi-dimensional array
//      wherein the first array index represents the barcode's Y coordinate and
//      the second array index represents the barcode's X coordinate, and each
//      element is a single "pixel" represented by a single boolean value (white
//      or black).


public class Assignment4 { //Outer class per specifications, "only one file".

   public static void main(String[] args) {
      //This main method was prescribed by the specifications and is
      //   unaltered, except:
      //      1. Added the "sample" barcode the specs also contained.
      //      2. Added a test of the "create your own message" produced barcode
      //         to ensure it translated back to the same text that created the
      //         image.

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
     
      String[] sImageSample = {
         "* * * * * * * * * * * * * * * * * *",
         "*                                 *",
         "***** ** * **** ****** ** **** **  ",
         "* **************      *************",
         "**  *  *        *  *   *        *  ",
         "* **  *     **    * *   * ****   **",
         "**         ****   * ** ** ***   ** ",
         "*   *  *   ***  *       *  ***   **",
         "*  ** ** * ***  ***  *  *  *** *   ",
         "***********************************"
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
      
      // sample message from specifications (not originally part of this method):
      bc = new BarcodeImage(sImageSample);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      // TEST your own message (not originally part of this method):
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
   }
}


//Phase 1 - BarcodeIO:
interface BarcodeIO {   //Inner class per specifications, "only one file".
   //This interface was prescribed by the specifications and is unaltered.

   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
   }


//Phase 2 - BarcodeImage:
class BarcodeImage implements Cloneable { //Inner class per specs, "only one file".
   //Created 2017-05-18 by Kevin Hendershott, et al.
   
   public static final int MAX_WIDTH = 65;
   public static final int MAX_HEIGHT = 30;
   private boolean[][] image_data;
   
   public BarcodeImage() {
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];   //Default values will be false (white).
   }
   
   public BarcodeImage(String[] str_data) {
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];   //Default values will be false (white).
      if (checkSize(str_data))                           //If incoming image not too big.
         for (int row = MAX_HEIGHT - 1; row >= 0; row--) //Start from "bottom".
            for (int col = 0; col < MAX_WIDTH; col++) {  //Start from "left".
               setPixel(row, col, false);                //Default insert white (false).
               if (row < str_data.length)                //If there are still rows to process.
                  if (col < str_data[row].length())      //If there are still cols to process.
                     if (str_data[row].charAt(col) == DataMatrix.BLACK_CHAR)   //If black is detected.
                        setPixel(row, col, true);        //Replace white (false) with black (true).
            }
      else;
         //Leave image_data filled with all "white" values to indicate bad input size.
   }
   
   public boolean getPixel(int row, int col) {
      //Return pixel value or false on error condition per specifications.
      try {
         return image_data[row][col];
      }
      catch (IndexOutOfBoundsException ex) {
         return false;
      }
   }
   
   public boolean setPixel(int row, int col, boolean value) {
      //Return success (not value) or false on error condition per specifications.
      try {
         image_data[row][col] = value;
         return true;
      }
      catch (IndexOutOfBoundsException ex) {
         return false;
      }
   }
   
   private boolean checkSize(String[] data) {
      if (data == null || data.length == 0)
         return false;
      else
         //Assumes all elements are of equal length.
         if (data.length <= MAX_HEIGHT && data[0].length() <= MAX_WIDTH)
            return true;
         else
            return false;
   }
   
   public void displayToConsole() {
      //Assumes all rows contains the same number of columns.
      //Emulates "surrounding box of special chars" format seen in specifications.
      String s = "";
      s += new String(new char[image_data[0].length + 2]).replace("\0", "-"); //Add 2 for "surrounding box".
      s += "\n";
      for (int row = 0; row < image_data.length; row++) {
         s += "|";
         for (int col = 0; col < image_data[0].length; col++)
            if (image_data[row][col])
               s += DataMatrix.BLACK_CHAR;
            else
               s += DataMatrix.WHITE_CHAR;
         s += "|\n";
      }
      s += new String(new char[image_data[0].length + 2]).replace("\0", "-"); //Add 2 for "surrounding box".
      s += "\n";
      System.out.print(s); //String s already ends with a newline.
   }
   
   public Object clone() throws CloneNotSupportedException {
      //Overrides Object.clone() & safely handles mutables.
      try {
         BarcodeImage copy = (BarcodeImage) super.clone();
         copy.image_data = (boolean[][]) image_data.clone(); //Safely handle mutable.
         return copy;
      }
      catch (CloneNotSupportedException e) { //This should not happen.
         return null;                        //To keep the compiler happy.
      }
   }
   
}


//Phase 3 - DataMatrix:
class DataMatrix implements BarcodeIO {   //Inner class per specs, "only one file".
   //Created 2017-05-18 by Kevin Hendershott, et al.
   
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text;
   private int actualWidth, actualHeight;
   
   public DataMatrix() {
      image = new BarcodeImage();
      text = "";
      actualWidth = actualHeight = 0;
   }
   
   public DataMatrix(BarcodeImage image) {
      scan(image);   //Specs do not say what to do with return value, so discard.
      text = "";
   }
   
   public DataMatrix(String text) {
      image = new BarcodeImage();
      readText(text);   //Specs do not say what to do with return value, so discard.
      actualWidth = actualHeight = 0;
   }
   
   public int getActualWidth() {
      //Value returned does NOT include the delimiting lines, only the signal.
      return actualWidth;
   }
   
   public int getActualHeight() {
      //Value returned does NOT include the delimiting lines, only the signal.
      return actualHeight;
   }
   
   private int computeSignalWidth() {
      //Assumes correctly formed and delineated barcode already in lower-left corner of image.
      //Read past the Closed Limitation Line in row(MAX_HEIGHT - 1) to find the signal width.
      int width = 0;
      for (int col = 0; col < image.MAX_WIDTH; col++)
         if (image.getPixel(image.MAX_HEIGHT - 1, col) == false) { //First "white" char is end of signal.
            width = col - 2;   //Subtract 1 for left Closed Limitation Line and 1 for right Open Border.
            break;             //End of Closed Limitation Line found, so stop looking.
         }
      if (width <= 0)
         return -1;  //Denotes error condition: Missing Closed Limitation Line, or signal width < 1.
      else
         return width;
   }
   
   private int computeSignalHeight() {
      //Assumes correctly formed and delineated barcode already in lower-left corner of image.
      //Read past the Closed Limitation Line in col(0) to find the signal height.
      int height = 0;
      for (int row = image.MAX_HEIGHT - 1; row >= 0; row--)
         if (image.getPixel(row, 0) == false) { //First "white" char is end of signal.
            height = ((image.MAX_HEIGHT - 1) - row) - 2;   //Subtract 1 for bottom Closed Limitation Line and 1 for top Open Border.
            break;             //End of Closed Limitation Line found, so stop looking.
         }
      if (height <= 0)
         return -1;  //Denotes error condition: Missing Closed Limitation Line, or signal width < 1.
      else
         return height;
   }
   
   private char readCharFromCol(int col) {
      int rowStart = (image.MAX_HEIGHT - 1) - 1;        //Index of "bottom" row - the 1 delimiting line on bottom.
      int rowStop = rowStart - getActualHeight() + 1;   //Index of "top" row + the 1 delimiting line at top.
      int exponent = 0;                                 //Used to compute column's total value.
      double value = 0.0;                               //Used to compute column's total value.
      for (int row = rowStart; row >= rowStop; row--) { //For every row in the signal's column, in order:
         if (image.getPixel(row, col))                  //if true (black), then
            value += Math.pow(2, exponent);             //convert base 2 to base 10 and accumulate.
         exponent++;                                    //Increment exponent, whether used this iteration or not.
      }
      return (char) value;
   }
   
   private boolean writeCharToCol(int col, int code) {
      //Return success (not value) or false on error condition per
      //   (the setPixel) specifications, namely, "...will act like our
      //   usual [methods that return a boolean for no apparent reason], returning
      //   true if the [conditions are okay], and false if not."
      try {
         int rowStart = 0;                                //Index of "top" row includes the delimiting line at top.
         int rowStop = rowStart + getActualHeight() + 1;  //Index of "bottom" row includes the delimiting line at bottom.
         int exponent = 7;                                //Used to convert code's base 10 into image's base 2.
         image.setPixel(rowStart, col, (col % 2 == 0));   //Set the solid delimiting line at the top row on odd columns.
         for (int row = rowStart + 1; row <= rowStop; row++) { //For every row in the signal's column, in top to bottom order:
            if (code - Math.pow(2, exponent) >= 0) {      //If this power of 2 fits evenly into the base 10 number, then
               image.setPixel(row, col, true);            //set this pixel to black, and
               code -= Math.pow(2, exponent);             //subtract this power of 2 from the base 10 number if it fits and loop again.
            }
            else
               image.setPixel(row, col, false);           //Else this base 2 number is not part of the base 10 number.
            exponent--;                                   //Decrement exponent, whether used this iteration or not, and loop again.
         image.setPixel(rowStop, col, true);              //Set the solid delimiting line at the bottom row on all columns.
         }
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }
   
   public void displayRawImage() {
      //Assumes all rows contains the same number of columns.
      //Emulates "surrounding box of special chars" format seen in specifications.
      //Displays entire image including whitespace surrounding the signal, per the specifications.
      String s = "";
      s += new String(new char[image.MAX_WIDTH + 2]).replace("\0", "-");
      s += "\n";
      for (int row = 0; row < image.MAX_HEIGHT; row++) {
         s += "|";
         for (int col = 0; col < image.MAX_WIDTH; col++)
            if (image.getPixel(row, col))
               s += BLACK_CHAR;
            else
               s += WHITE_CHAR;
         s += "|\n";
      }
      s += new String(new char[image.MAX_WIDTH + 2]).replace("\0", "-");
      s += "\n";
      System.out.print(s); //String s already ends with a newline.
   }
   
   private void cleanImage() {
      moveImageToLowerLeft();
   }
   
   private void moveImageToLowerLeft() {
      boolean rowAllWhite = true;
      boolean colAllWhite = true;
      int rowOffset = 0;
      int colOffset = 0;

      //Check for "blank" rows, starting from "bottom" row:
      for (int row = image.MAX_HEIGHT - 1; row >= 0; row--) {
         for (int col = 0; col < image.MAX_WIDTH; col++)
            if (image.getPixel(row, col))  //Black encountered.
               rowAllWhite = false;
         if (rowAllWhite)
            rowOffset++;   //Identified one more row to shift.
         else
            break;   //Row found that contains black, so stop looking at rows.
      }
      shiftImageDown(rowOffset);
      
      //Check for "blank" columns, starting from "left" column:
      for (int col = 0; col < image.MAX_WIDTH; col++) {
         for (int row = 0; row < image.MAX_HEIGHT; row++)
            if (image.getPixel(row, col))  //Black encountered.
               colAllWhite = false;
         if (colAllWhite)
            colOffset++;   //Identified one more column to shift.
         else
            break;   //Column found that contains black, so stop looking at columns.
      }
      shiftImageLeft(colOffset);
   }
   
   private void shiftImageDown(int offset) {
      //Assumes all rows contain the same number of columns.
      //(I would rather use a "wrap-around" algorithm, but was having trouble with it.)
      for (int n = 1; n <= offset; n++) //n is a qty, not an index, so I choose 1-based.
         for (int col = 0; col < image.MAX_WIDTH; col++)
            for (int row = image.MAX_HEIGHT - 1; row > 0; row--) {
               //Okay to "blow away" value at row, col because we know it's blank.
               image.setPixel(row, col,  image.getPixel(row - 1, col));
               image.setPixel(row - 1, col, false);   //Blank the "from" pixel.  A compromise instead of wrap-around.
            }
   }
   
   private void shiftImageLeft(int offset) {
      //Assumes all rows contain the same number of columns.
      //(I would rather use a "wrap-around" algorithm, but was having trouble with it.)
      for (int n = 1; n <= offset; n++) //n is a qty, not an index, so I choose 1-based.
         for (int row = 0; row < image.MAX_HEIGHT; row++)
            for (int col = 0; col < image.MAX_WIDTH - 1; col++) {
               //Okay to "blow away" value at row, col because we know it's blank.
               image.setPixel(row, col,  image.getPixel(row, col + 1));
               image.setPixel(row, col + 1, false);   //Blank the "from" pixel.  A compromise instead of wrap-around.
            }
   }
   
   private void clearImage() {
      for (int row = 0; row < image.MAX_HEIGHT; row++)
         for (int col = 0; col < image.MAX_WIDTH; col++)
            image.setPixel(row,  col,  false);   //False = white.
   }
   
   //Implementation of all six of interface BarcodeIO's methods here:
   
   public boolean scan(BarcodeImage image) {
      //Implements part of the BarcodeIO interface.
      //Return success (not value) or false on error condition per
      //   (the setPixel) specifications, namely, "...will act like our
      //   usual [methods that return a boolean for no apparent reason], returning
      //   true if the [conditions are okay], and false if not."
      try {
         this.image = (BarcodeImage)image.clone(); //"Copy" in the input image.
      }
      catch (CloneNotSupportedException ex) {
         //This catch clause does nothing per the specifications.
      }
      try {
         cleanImage();
         actualWidth = computeSignalWidth();    //Specs do not say what to do with returned error condition, so disregard.
         actualHeight = computeSignalHeight();  //Specs do not say what to do with returned error condition, so disregard.
         //displayRawImage();                   //For troubleshooting only.  Leave commented out for production.
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }
   
   public boolean readText(String text) {
      //Implements part of the BarcodeIO interface.
      //Return success (not value) or false on error condition per
      //   (the setPixel) specifications, namely, "...will act like our
      //   usual mutators, returning true if the [conditions are okay], and
      //   false if not."
      //Would have been more consistent if this method was named "setText", because
      //   "readText" sounds like an accessor but the specs say this is a mutator.
      try {
         this.text = text;
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }
   
   public boolean generateImageFromText() {
      //Implements part of the BarcodeIO interface.
      //Return success (not value) or false on error condition per
      //   (the setPixel) specifications, namely, "...will act like our
      //   usual [methods that return a boolean for no apparent reason], returning
      //   true if the [conditions are okay], and false if not."
      try {
         clearImage();                                        //Empty existing image, as a precaution.
         writeCharToCol(0, 255);                              //Create Closed Limitation Line before left-most character (col(0)).
         for (int col = 1; col <= text.length(); col++)       //For every character in between the delimiting lines (cols(1 to text.length)),
            writeCharToCol(col, (int) text.charAt(col - 1));  //create barcode from the ASCII character cast to integer (col(x) == text(x - 1)).
         writeCharToCol(text.length() + 1, 170);              //Create Open Borderline after right-most character (col(text.length + 1)).
         scan(image);                                         //Process new image.
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }
   
   public boolean translateImageToText() {
      //Implements part of the BarcodeIO interface.
      //Assumes correctly formed and delineated barcode already in lower-left corner of image.
      //Return success (not value) or false on error condition per
      //   (the setPixel) specifications, namely, "...will act like our
      //   usual [methods that return a boolean for no apparent reason], returning
      //   true if the [conditions are okay], and false if not."
      try {
         String text = "";
         for (int col = 1; col <= getActualWidth(); col++)   //Start after left delimiting line.
            text += readCharFromCol(col);
         readText(text);   //Pass constructed text to (poorly named) mutator, per specifications.  Ignore any returned error.
         return true;
      }
      catch (Exception ex) {
         return false;
      }
   }
   
   public void displayTextToConsole() {
      //Implements part of the BarcodeIO interface.
      //Assumes translateImageToText() has already been called, but not necessarily I suppose.
      System.out.println(this.text);
   }
   
   public void displayImageToConsole() {
      //Implements part of the BarcodeIO interface.
      //Assumes all rows contains the same number of columns.
      //Displays "surrounding box of special chars" format per the specifications.
      //Displays only the signal and delimiting lines, per the specifications, no whitespace.
      String s = "";
      s += new String(new char[getActualWidth() + 4]).replace("\0", "-"); // Add 2 for delimiting lines, and 2 more for "surrounding box".
      s += "\n";
      for (int row = image.MAX_HEIGHT - 2 - getActualHeight(); row < image.MAX_HEIGHT; row++) { // Subtract 2 for delimiting lines.
         s += "|";
         for (int col = 0; col < getActualWidth() + 2; col++)             // Add 2 for delimiting lines.
            if (image.getPixel(row, col))
               s += BLACK_CHAR;
            else
               s += WHITE_CHAR;
         s += "|\n";
      }
      s += new String(new char[getActualWidth() + 4]).replace("\0", "-"); // Add 2 for delimiting lines, and 2 more for "surrounding box".
      s += "\n";
      System.out.print(s); //String s already ends with a newline.
   }
   
}

/* Test output from main():

CSUMB CSIT online program is top notch.
-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|
-------------------------------------------
You did it!  Great work.  Celebrate.
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
----------------------------------------
This is a good SAMPLE to look at.
-------------------------------------
|* * * * * * * * * * * * * * * * * *|
|*                                 *|
|***** ** * **** ****** ** **** **  |
|* **************      *************|
|**  *  *        *  *   *        *  |
|* **  *     **    * *   * ****   **|
|**         ****   * ** ** ***   ** |
|*   *  *   ***  *       *  ***   **|
|*  ** ** * ***  ***  *  *  *** *   |
|***********************************|
-------------------------------------
What a great resume builder this is!
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|***** * ***** ****** ******* **** **  |
|* ************************************|
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *  *|
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  * *|
|** *  * * **   *****  **  *    ** *** |
|**************************************|
----------------------------------------
What a great resume builder this is!
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|***** * ***** ****** ******* **** **  |
|* ************************************|
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *  *|
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  * *|
|** *  * * **   *****  **  *    ** *** |
|**************************************|
----------------------------------------

*/
