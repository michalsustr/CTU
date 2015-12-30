import java.util.Scanner;

/**
 * @author Michal Sustr
 */
public class Triangle {
    public static void main(String[] args) {
	int a,b,c;
	Scanner sc = new Scanner(System.in);

	System.out.println("Zadejte velikost strany a:");
	a = sc.nextInt();
	System.out.println("Zadejte velikost strany b:");
	b = sc.nextInt();
	System.out.println("Zadejte velikost strany c:");
	c = sc.nextInt();

	System.out.println("---Vysledky---");
	
	// check if triangle can be constructed
	if(a+b > c && b+c > a && a+c > b) {
	    System.out.println("jde sestrojit");
	} else {
	    System.out.println("nejde sestrojit");
	    System.out.println("neni rovnostranny");
	    System.out.println("neni rovnoramenny");
	    System.out.println("neni pravouhly");
	    return;
	}

	// check if triangle is equilateral
	if(a == b && b == c) {
	    System.out.println("je rovnostranny");
	} else {
	    System.out.println("neni rovnostranny");
	}

	// check if triangle is isosceles
	if((a == b && b != c) || (a == c && a != b) || (b == c && b != a)) {
	    System.out.println("je rovnoramenny");
	} else {
	    System.out.println("neni rovnoramenny");
	}

	// check if triangle is rectangular
	if(Math.pow(a, 2) == (Math.pow(b,2) + Math.pow(c, 2))
	   || Math.pow(b, 2) == (Math.pow(a,2) + Math.pow(c, 2))
	   || Math.pow(c, 2) == (Math.pow(b,2) + Math.pow(a, 2))) {
	    System.out.println("je pravouhly");
	} else {
	    System.out.println("neni pravouhly");
	}

    }

}
