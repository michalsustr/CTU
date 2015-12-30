/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky.entity;

import ovecky.Manager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import ovecky.Point;
import java.util.ArrayList;
import ovecky.MainGameFrame;

/**
 *
 * @author michal
 */
public class Sheep extends Entity implements DynamicEntity {
	/**
	 * Radius to which a sheep looks up for other sheep and objects
	 * and behaves according to what it sees
	 * @var Integer
	 */
	private int sightRadius = 75;

	private boolean isOtherSheepsSeeChasers = false;

	/**
	 * Determines whether sheep should not go feeding, because it has been frightened by
	 * a chaser (dog) or other sheep. When it is frightened, isDisturbed is set to
	 * {@see disturbedByChaserFrameCount} and isDisturbed lowers by each frame
	 * back to zero when the sheep is left alone.
	 */
	private int isDisturbed = 0;

	private int disturbedByChaserFrameCount = 100;

	/**
	 * Determines whether this sheep sees a chaser within sightRadius
	 */
	private boolean seesChaser;

	private Point gotoPoint;

	/**
	 * When the sheep is not disturbed, it goes from time to time
	 * to some other place to feed on
	 */
	private boolean isFeedingOnGrass = false;

	/**
	 * Probability in 0 to 100 (100 is 100% prob) whether the sheep will
	 * stay on the same place feeding on grass
	 */
	private int stayingOnTheSamePlaceProbab = 5;

	/**
	 * Radius into which sheep will go to look for feeding
	 * @see findWhereToFeed
	 */
	private int feedingMovementRadius = 50;


	/**
	 * Speed at which sheep will try to get to the new random feeding place
	 *
	 * @min 2
	 */
	private int sheepFeedingSpeed = 2;

	/**
	 * Speed at which sheep will try to come to the closest sheep
	 */
	private int sheepGoBackToHerdSpeed = 3;

	private int sheepRunningAwaySpeed = 5;

	private boolean isGoingBackToHerd = false;

	private boolean alone = true;

	private ArrayList<Sheep> sheepsInSight;

	Point lastCoordinate;

	private static int id = 0;
	@SuppressWarnings("PublicField")
	public int myId;

	public Sheep(Point pt) {
		myId = id++;

		coordinate = pt;
		size = new Dimension(20,20);

		assert feedingMovementRadius < sightRadius;
	}

	@Override
	public void run() {
		Sheep closestSheepInSight = null;
		Sheep closestSheep = null;
		sheepsInSight = new ArrayList<Sheep>();
		// <editor-fold defaultstate="collapsed" desc=" find  sheep in sight, closest sheep and closest sheep in sight ">
		double closestSheepInSightDistance = Double.MAX_VALUE,
			closestSheepDistance = Double.MAX_VALUE;
		double sheepDistance;
		ArrayList<Sheep> sheeps = getManager().getSheep();

		for(Sheep sheep : sheeps) {
			if(!sheep.equals(this)) {
				sheepDistance = coordinate.distance(sheep.getCoordinate());
				if(sheepDistance < closestSheepDistance) {
					closestSheepDistance = sheepDistance;
					closestSheep = sheep;
				}
				if(sheepDistance < sightRadius) {
					sheepsInSight.add(sheep);
					if(sheepDistance < closestSheepInSightDistance) {
						closestSheepInSightDistance = sheepDistance;
						closestSheepInSight = sheep;
					}
				}
			}
		}

		// if closestSheepInSight is set, then it must be the same as closestSheep
		assert closestSheepInSight == null || closestSheep.equals(closestSheepInSight);
		// if closestSheepInSight is set, then it must be in sheepsInSight as well
		assert closestSheepInSight == null || sheepsInSight.contains(closestSheepInSight);
		// </editor-fold>

		ArrayList<Chaser> chasersInSight = new ArrayList<Chaser>();
		// <editor-fold defaultstate="collapsed" desc=" find chasers that are in sight, seesChaser ">
		seesChaser = false;
		for(Chaser chaser : getManager().getChasers()) {
			if(coordinate.distance(chaser.getCoordinate()) < sightRadius) {
				chasersInSight.add(chaser);
				seesChaser = true;
			}
		}
		// </editor-fold>


		if(chasersInSight.isEmpty()) { // sheep doesnt see other chasers
			Point otherSheepPoint = otherSheepsAreDisturbed(sheepsInSight);
			if(otherSheepPoint == null) { // no sheeps in sight can see any chasers
				isOtherSheepsSeeChasers = false;

				if(isDisturbed > 0) { // dont start feeding until this sheep is calm :)
					isDisturbed--;
				} else {
					assert isDisturbed == 0;
				}
				// start feeding if other sheeps are in sightRadius and this
				// sheep is not returning back to herd from distance
				if(closestSheepInSight != null && !isGoingBackToHerd) {
					alone = false;
					if(!isFeedingOnGrass) {
						// random decision whether to go
						boolean go = Math.random()*100 < stayingOnTheSamePlaceProbab && isDisturbed == 0;

						if(go) {
							isFeedingOnGrass = true;
							gotoPoint = findWhereToFeed();
						}
					}

					// in each frame move by {@see sheepFeedingSpeed} towards the destination
					if(isFeedingOnGrass) {
						if(animateGoToPoint(gotoPoint, sheepFeedingSpeed)) {
							isFeedingOnGrass = false;
						}
					}
				} else { // this sheep cannot see any other sheep
					alone = true;
					if(!isGoingBackToHerd) {
						isGoingBackToHerd = true;
						gotoPoint = closestSheep.coordinate.clone();
						gotoPoint.translate(
							MainGameFrame.randomNumber(-50, 50),
							MainGameFrame.randomNumber(-50, 50)
						);
					} else {
						if(animateGoToPoint(gotoPoint, sheepGoBackToHerdSpeed)) {
							isGoingBackToHerd = false;
						}

					}
				}

			} else { // other sheeps in sight can see chasers
				isOtherSheepsSeeChasers = true;
				isFeedingOnGrass = false;
				isGoingBackToHerd = false;

				animateGoToPoint(otherSheepPoint, sheepRunningAwaySpeed);
				isDisturbed = disturbedByChaserFrameCount;
			}
		} else { // sheep sees other chasers
			// calculate relative vectors of chasers positions to the position
			// of this sheep
			Point moveTo = new Point();
			for(Chaser chaser: chasersInSight) {
				moveTo.translate(
					coordinate.x-chaser.getCoordinate().x,
					coordinate.y-chaser.getCoordinate().y
				);
			}
			gotoPoint = moveTo;
			moveTo.translate(coordinate);
			animateGoToPoint(moveTo, sheepRunningAwaySpeed);
			isDisturbed = disturbedByChaserFrameCount;
		}

		
	}

	@Override
	public void paint(Graphics g) {
		Point position = getManager().getPositionOnScreen(coordinate);
		
		g.setColor(Color.gray);
		g.fillRect((int) position.x, (int) position.y, size.width, size.height);
		g.setColor(Color.black);
		g.drawString(""+myId, (int) position.x+3, (int) position.y+15);
	}

	/**
	 * Finds point where sheep randomly goes to feed
	 *
	 * This point is randomly chosen inside of the {@see feedingMovementRadius}
	 * circle. It may happen that going somewhere the sheep gets lost (out of
	 * sightRadius). In this case, it will find other sheep and go back to the
	 * herd.
	 *
	 * @param closestSheep
	 * @return destination point
	 */
	private Point findWhereToFeed() {
		// random angle, and random % distance where sheep should go
		double ra = MainGameFrame.randomNumber(0, 360) * Math.PI / 180;
		double rd = MainGameFrame.randomNumber(1, 100)/100.0; // avoid zero
		assert rd != 0;
		
		return new Point(
			Math.cos(ra)*feedingMovementRadius*rd + coordinate.x,
			Math.sin(ra)*feedingMovementRadius*rd + coordinate.y
		);
	}

	/**
	 * Animate this sheep to go to a specified destination at specified speed
	 * pixels/frame
	 *
	 * @param destination
	 * @param speed
	 * @return boolean is animation completed
	 */
	private boolean animateGoToPoint(Point destination, int speed) {
		double moveX, moveY;
		// points are not in the same vertical line
		if(coordinate.x != destination.x) {
			double angle = Math.atan(
				(destination.y - coordinate.y) /
				(destination.x - coordinate.x)
			);
			moveX = Math.abs(Math.cos(angle) * speed);
			moveY = Math.abs(Math.sin(angle) * speed);
		} else { // same vertical line
			moveX = 0;
			moveY = speed;
		}

		// x/y correction
		if(destination.x < coordinate.x) {
			moveX *= -1;
		}
		if(destination.y < coordinate.y) {
			moveY *= -1;
		}

		lastCoordinate = coordinate.clone();
		coordinate.translate(moveX, moveY);
		pushOtherSheep();

		// if any correction needs to be done, it means that destination point
		// goes to beyond the edge of the map or to a stone, so set the destination
		// to null value thus cancelling going to that destination
		if(getManager().correctPosition(this)) {
			destination = null;
			return true;
		}

		// sheep finally got where it wanted to get
		if(destination.distance(coordinate) < speed) {
			coordinate.setLocation(destination);
			pushOtherSheep();
			destination = null;
			return true;
		}

		return false;
	}

	/**
	 * Find if other sheeps in sight are disturbed. If they are, return a point
	 * where this sheep should go to (a vector sum of other sheeps relocations)
	 * If not, return null.
	 * @param sheepsInSight
	 * @return
	 */
	private Point otherSheepsAreDisturbed(ArrayList<Sheep> sheepsInSight) {
		Point moveVector = new Point();
		for(Sheep sheep : sheepsInSight) {
			if(sheep.seesChaser) {
				Point translationVector = sheep.locationDifference();
				// change the "weight" of this vector
				// vectors that are closest to this sheep are the most significant
				// those that are furthest are the least
				double multFactor = 5.0 - coordinate.distance(sheep.coordinate)/sightRadius;
				translationVector.x *= multFactor;
				translationVector.y *= multFactor;

				assert coordinate.distance(sheep.coordinate)/sightRadius <= 1;
				moveVector.translate(translationVector);
			}
		}
		// no sheeps are disturbed
		if(moveVector.x == 0 && moveVector.y == 0) {
			return null;
		}
		moveVector.translate(coordinate);
		return moveVector;
	}

	/**
	 * Find if any other sheep are at the same space as this one
	 * If that is so, this sheep tries to push them away
	 * TODO: pushOtherSheep
	 */
	private void pushOtherSheep() {
//		ArrayList<Sheep> sheepToPush = new ArrayList<Sheep>();
//		for(Sheep sheep : getInstance().getSheep()) {
//			if(!
//				(coordinate.x < sheep.coordinate.x+sheep.size.width
//				&& coordinate.x+size.width > sheep.coordinate.x
//				&& coordinate.y < sheep.coordinate.y+sheep.size.height
//				&& coordinate.y+size.height > sheep.coordinate.y)
//			) { // they do not overlap
//				continue;
//			}
//
//			Point newCoor = sheep.coordinate.clone();
//			newCoor.translate(locationDifference());
//			sheep.setCoordinate(newCoor);
//		}
	}

	
	private void setCoordinate(Point coor) {
		coordinate = coor;
		pushOtherSheep();
	}

	public Point locationDifference() {
		if(lastCoordinate == null) {
			return new Point(0,0);
		}
		
		return new Point(coordinate.x - lastCoordinate.x, coordinate.y - lastCoordinate.y);
	}

	@Override
	public String toString() {
		return "SheepEntity{" + "coordinate=" + coordinate + "isDisturbed=" + isDisturbed + "disturbedByChaserFrameCount=" + disturbedByChaserFrameCount + "feedingOnGrassGoToPoint=" + isFeedingOnGrass + "myId=" + myId + "alone=" + alone + '}';
	}
}