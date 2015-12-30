import java.util.Scanner;

/**
 * @author Michal Sustr
 */
public class PointsOnLine {

    public static void main(String[] args) {
	double ax,ay,bx,by,cx,cy;
	Scanner sc = new Scanner(System.in);

	System.out.println("Zadejte souradnice bodu A:");
	ax = sc.nextInt();
	ay = sc.nextInt();
	System.out.println("Zadejte souradnice bodu B:");
	bx = sc.nextInt();
	by = sc.nextInt();
	System.out.println("Zadejte souradnice bodu C:");
	cx = sc.nextInt();
	cy = sc.nextInt();

	System.out.println("---Vysledky---");

	// calculate vector
	double ab_x, ab_y;
	ab_x = bx-ax;
	ab_y = by-ay;

	// A and B are the same
	if(ab_x == 0 && ab_y == 0) {
	    System.out.println("Body lezi na jedne primce.");
	    System.out.println("Nektere body splyvaji - zadny neni uprostred.");
	    return;
	}

	// prevent division by zero
	if(ab_x == 0) { // vertical line
	    if(cx == ax) { // or could be bx
		System.out.println("Body lezi na jedne primce.");
		getMiddlePoint(ax,ay,bx,by,cx,cy);
	    } else {
		System.out.println("Body nelezi na jedne primce.");
	    }
	    return;
	}
	if(ab_y == 0) { // horizontal line
	    if(cy == ay) { // or could be by
		System.out.println("Body lezi na jedne primce.");
		getMiddlePoint(ax,ay,bx,by,cx,cy);
	    } else {
		System.out.println("Body nelezi na jedne primce.");
	    }
	    return;
	}
	// division of vector AC / AB
	double multiplierX = (cx-ax) / ab_x;
	double multiplierY = (cy-ay) / ab_y;

	if(multiplierX == multiplierY) {
	    System.out.println("Body lezi na jedne primce.");
	    getMiddlePoint(ax,ay,bx,by,cx,cy);
	} else {
	    System.out.println("Body nelezi na jedne primce.");
	}
    }

    public static void getMiddlePoint(double ax, double ay, double bx, double by, double cx, double cy) {
	// on the same horizontal line
	if(ay == by && by == cy) {
	    if( (bx < ax && ax < cx) || (cx < ax && ax < bx) ) {
		System.out.println("Prostredni je bod A.");
		return;
	    } else if((ax < bx && bx < cx) || (cx < bx && bx < ax)) {
		System.out.println("Prostredni je bod B.");
		return;
	    } else if( (ax < cx && cx < bx) || (bx < cx && cx < ax) ) {
		System.out.println("Prostredni je bod C.");
		return;
	    } else {
		System.out.println("Nektere body splyvaji - zadny neni uprostred.");
	    }
	}

	// on the same vertical line
	if(ax == bx && bx == cx) {
	    if( (by < ay && ay < cy) || (cy < ay && ay < by) ) {
		System.out.println("Prostredni je bod A.");
		return;
	    } else if( (ay < by && by < cy) || (cy < by && by < ay) ) {
		System.out.println("Prostredni je bod B.");
		return;
	    } else if( (ay < cy && cy < by) || (by < cy && cy < ay) ) {
		System.out.println("Prostredni je bod C.");
		return;
	    } else {
		System.out.println("Nektere body splyvaji - zadny neni uprostred.");
	    }
	}

	// on the same sloped line
	if( (bx < ax && ax < cx)
	    || (cx < ax && ax < bx) ) {
	    System.out.println("Prostredni je bod A.");
	} else if( (ax < bx && bx < cx)
	    || (cx < bx && bx < ax) ) {
	    System.out.println("Prostredni je bod B.");
	} else if( (bx < cx && cx < ax)
	    || (ax < cx && cx < bx) ) {
	    System.out.println("Prostredni je bod C.");
	} else {
	    System.out.println("Nektere body splyvaji - zadny neni uprostred.");
	}
    }
}
