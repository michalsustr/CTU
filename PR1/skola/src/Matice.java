import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author michal
 */
public class Matice {
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Load all necessary data from stdin
	 */
	public static void main(String[] args) {
		int dimX, dimY; // dim of wanted matrix
						// [0,0] is the top left corner of the matrix
						// [n,n] is the bottom right corner of the matrix
		System.out.println("Pocet radek:");
		dimX = nextInt(1);
		
		System.out.println("Pocet sloupcu:");
		dimY = nextInt(1);

		int[] wantedItems = new int[dimX*dimY];
		int row = -1;

		for(int i = 0; i < dimX*dimY; i++) {
			if(i % dimY == 0) row++;
			System.out.println("Zadej prvek ["+row+"]["+i%dimY+"]: ");
			wantedItems[i] = sc.nextInt();
		}

		// create new matrix object
		Matrix wanted = new Matrix(dimX, dimY, wantedItems);

		System.out.println("---Vysledky---");
		System.out.println("Nactena matice:");
		wanted.output();

		System.out.println("Maxima sloupcu: "+wanted.columnMax());
		if(dimX == dimY) {
			System.out.println("Soucet prvku dolniho kvadradrantu je: "+wanted.sumLowerQuadrant());
		}
    }

	/**
	 * Get input from stdin with checking of value (must be greater than minValue)
	 * @param minValue
	 * @return int from stdin
	 */
	protected static int nextInt(int minValue) {
		int in;
		while(true) {
			in = sc.nextInt();
			if(in < minValue) {
				System.out.println("Chyba! Opakujte zadani");
			} else {
				break;
			}
		}
		return in;
	}
}

/**
 * Matrix
 * @author michal
 */
class Matrix {
	protected int x, y;
	protected int[][] items;

	class Occurence {
		int x,y;

		public Occurence(int x, int y) {
			this.x = x;
			this.y = y;
		}


	}

	Matrix(int dimX, int dimY, int[] matrixItems) {
		this.x = dimX;
		this.y = dimY;
		this.items = new int[x][y];

		int row = -1;
		for(int i = 0; i < x*y; i++) {
			if(i % y == 0) row++;
			items[row][i%y] = matrixItems[i];
		}
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}

	public int itemAt(int x, int y) {
		if(x > this.x || y > this.y || x < 0 || y < 0) {
			throw new ArrayIndexOutOfBoundsException("Item does not exist at position x: "+x+" y:"+y);
		}
		return this.items[x][y];
	}

	public int searchMatrix(Matrix matrix) {
		// find if given matrix is not larger than this matrix
		// if it is so, it cannot be a sub-matrix
		if(matrix.getX() > this.x && matrix.getY() > this.y ) {
			return 0;
		}

		// find out if matrices are identical
		if(matrix.getX() == this.x && matrix.getY() == this.y) {
			for(int i = 0; i < this.x; i++) {
				for(int j = 0; j < this.y; j++) {
					// if single item is not the same, matrices are not identical
					// and there are no sub-matrices
					if(matrix.itemAt(i, j) != this.items[i][j]) {
						return 0;
					}
				}
			}
			return 1;
		}

		Set occurences = new HashSet<Occurence>();
		int first = matrix.itemAt(0, 0);
		Occurence occurence;

		for(int i = 0; i < this.x; i++) {
			for(int j = 0; j < this.y; j++) {
				//System.out.println("Item "+i+":"+j);
				// loop all occurences
				for(Iterator it = occurences.iterator(); it.hasNext();) {
					occurence = (Occurence) it.next();
					//System.out.println("\titerator "+occurence.x+":"+occurence.y);
					// check if current i,j is still in bounds of the searched matrix
					if(i - occurence.x < matrix.getX() && i - occurence.x >= 0 &&
						j - occurence.y < matrix.getY() && j - occurence.y >= 0) {
						if(matrix.itemAt(i - occurence.x,j - occurence.y) != this.items[i][j]) {
							//System.out.println("\t\t removed");
							it.remove();
						} else {
							//System.out.println("\t\t not removed");
						}
					} else {
						//System.out.println("\t\tnot looked");
					}
				}

				// create new occurence
				if(first == items[i][j]) {
					// check if sub-matrix can be placed here
					if((this.x - i) >= matrix.getX() && (this.y - j) >= matrix.getY()) {
						occurences.add(new Occurence(i,j));
						//System.out.println("\t created");
					}
				}
			}
		}

		return occurences.size();
	}

	public String columnMax() {
		int[] max = new int[y];

		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(i == 0) { // first row, initiliaze max
					max[j] = items[i][j];
					continue;
				}

				if(items[i][j] > max[j]) {
					max[j] = items[i][j];
				}
			}
		}

		// return string
		String ret = "[";
		for(int i = 0; i < max.length; i++) {
			if(i == 0) ret += max[i];
			else ret += ", " +max[i];
		}
		return ret+"]";
	}

	public int sumLowerQuadrant() {
		int middle = (int) Math.ceil(x / 2);
		int sum = 0;
		// go from the bottom
		for(int i = (x-1); i >= middle; i--) {
			for(int j = (x-1-i); j <= (x-1)-(x-1-i); j++) {
				sum+=items[i][j];
			}
		}
		return sum;
	}

	public void output() {
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				System.out.printf("%3d", items[i][j]);
			}
			System.out.println("");
		}
	}

	@Override
	public String toString() {
		String returnString = "";
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				returnString = returnString + items[i][j] + " ";
			}
			returnString += "\n";
		}

		return returnString;
	}


}