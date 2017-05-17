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
class BarcodeImage implements Cloneable {
	
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
		//The String[] Y axis is opposite of image_data
		//So some creativity is needed
		//topOfInput will be the index of the top row in image_data
		int topOfInput = str_data.length - 1;
		boolean pixelValue;
		
		// Validate that the String[] can fit inside this.image
		if (checkSize(str_data)) {
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
	
	// Set the value of this.image_data[row][col] to given value.
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
