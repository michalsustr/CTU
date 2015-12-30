import java.util.Scanner;

/**
 *
 * @author michal
 */
public class RotateArray {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);

	int size;

	System.out.println("Zadej velikost pole");
	size = sc.nextInt();

	int rotate[] = new int[size];
	int lastElement;

	for(int i = 0; i < size; i++) {
	    System.out.println("Zadej prvek ["+ (i+1) +"]:");
	    rotate[i] = sc.nextInt();
	}

	lastElement = rotate[size-1];

	System.out.println("Puvodni pole:");
	// print original array
	System.out.println(arrayAsString(rotate));

	System.out.println("Orotovane pole:");
	System.out.println("---Vysledky---");
	
	// rotate the last element to the front
	for(int i = size-1; i > 0; i--) {
	    rotate[i] = rotate[i-1];
	}
	rotate[0] = lastElement;

	System.out.println(arrayAsString(rotate));
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
