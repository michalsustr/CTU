
import java.util.Scanner;

/**
 * @author Michal Sustr
 */
public class LinEq {
    public static void main(String[] args) {
	float a11, a12, b1, a21, a22, b2;
	Scanner sc = new Scanner(System.in);

	System.out.println("Zadej a11:");
	a11 = sc.nextFloat();
	System.out.println("Zadej a12:");
	a12 = sc.nextFloat();
	System.out.println("Zadej b1:");
	b1 = sc.nextFloat();
	System.out.println("Zadej a21:");
	a21 = sc.nextFloat();
	System.out.println("Zadej a22:");
	a22 = sc.nextFloat();
	System.out.println("Zadej b2:");
	b2 = sc.nextFloat();

	float x, y;

	System.out.println("---Vysledky---");

	if( (a11 == 0 && a12 == 0) ||
	    (a21 == 0 && a22 == 0) ||
	    (a11 == 0 && a21 == 0) ||
	    (a12 == 0 && a22 == 0)
	) {
	    System.out.println("nema reseni");
	}
	
	if(a11 == 0) {
	    y = b1 / a12;
	    x = (b2 - y*a22) / a21;
	} else if(a21 == 0) {
	    y = b2 / a22;
	    x = (b1 - y*a12) / a11;
	} else if(a12 == 0) {
	    x = b1 / a11;
	    y = (b2 - a21*x) / a22;
	} else if(a22 == 0) {
	    x = b2 / a21;
	    y = (b1 - a11*x) / a12;
	} else {
	    y = (b2*a11 - a21*b1) / (a22 * a11 - a21*a12);
	    x = (b1 - a12*y) / a11;
	}

	if(Double.isInfinite(y)) {
	    System.out.println("nema reseni");
	} else if(Double.isNaN(y)) {
	    System.out.println("ma nekonecne mnoho reseni");
	} else {
	    System.out.printf("x = %.2f\n", x);
	    System.out.printf("y = %.2f", y);
	}
    }
}
