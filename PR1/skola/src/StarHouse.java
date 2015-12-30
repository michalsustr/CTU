import java.util.Scanner;

/**
 * @author Michal Sustr
 */
public class StarHouse {
    public static void main(String[] args) {
	int size;
	Scanner sc = new Scanner(System.in);

	System.out.println("Zadejte velikost:");
	while((size = sc.nextInt()) <= 1 || size > 80) {
	    System.out.println("Chyba! Zadejte znovu velikost:");
	}

	System.out.println("---Vysledky---");
	
	// print roof
	for(int row = 1; row <= size; row++) {
	    System.out.print( repeat(" ", size-row-1) );
	    if(row == size) {
		System.out.print("*");
		System.out.println( repeat(" *", row-1) );
	    } else {
		System.out.println( repeat(" *", row) );
	    }
	}

	// print body
	for(int row = 2; row < size; row ++) {
	    System.out.print("*");
	    System.out.print( repeat(" ", (size-2)*2+1));
	    System.out.println("*");
	}

	// print base
	System.out.print( repeat("* ", size-1) );
	System.out.println("*");
    }

    /**
     * Repeat given string cnt times
     * @param what
     * @param cnt
     * @return String
     */
    protected static String repeat(String what, int cnt) {
	String ret = "";
	for(int i = 0; i < cnt; i++) {
	    ret += what;
	}
	return ret;
    }

}
