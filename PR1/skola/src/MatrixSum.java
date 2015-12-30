import java.util.Scanner;

/**
 *
 * @author michal
 */
public class MatrixSum {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);

	int size;

	System.out.println("Zadej velikost ctvercove matice:");
	size = sc.nextInt();

	int matrix[][] = new int[size][size];

	// get data
	for(int i = 0; i < size; i++) {
	    for(int j = 0; j < size; j++) {
		System.out.println("Zadej prvek ["+ (i+1) +"]["+ (j+1) +"]:");
		matrix[i][j] = sc.nextInt();
	    }
	}

	// show matrix
	System.out.println("Zadana matice:");
	for(int i = 0; i < size; i++) {
	    System.out.println(arrayAsString(matrix[i]));
	}

	int sum = 0;
	// calculate the sum
	for(int i = 0; i < size; i++) {
	    for(int j = (i+1); j < size; j++) {
		sum+= matrix[i][j];
	    }
	}

	System.out.println("---Vysledky---");
	System.out.println("Soucet je: "+sum);
    }

    /**
     * Create array as string
     * @param array
     * @return
     */
    public static String arrayAsString(int array[]) {
	String ret = "";
	for(int i = 0; i < array.length; i++) {
	    if(i == array.length-1) {
		ret += array[i];
	    } else {
		ret += array[i] + " ";
	    }
	}
	return ret;
    }
}
