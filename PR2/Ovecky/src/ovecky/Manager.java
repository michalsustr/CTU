/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import ovecky.entity.Chaser;
import ovecky.entity.Dog;
import ovecky.entity.Entity;
import ovecky.entity.Sheep;
import ovecky.entity.StaticEntity;

/**
 *
 * @author michal
 */
public class Manager {
	private Dimension screenSize, mapSize;
	private Point mapPosition;
	private ArrayList<Entity> entities;
	private ArrayList<Sheep> sheeps;
	private ArrayList<Chaser> chasers;
	private ArrayList<Dog> dogs;
	private ArrayList<Entity> statics;


	/**
	 * Determines what percentage of screen is used to define the edge of screen
	 */
	private int edgeOfScreenPercent = 10;
	private int edgeOfScreenHeight;
	private int edgeOfScreenWidth;

	private static Manager instance;

	public static Manager getInstance(Dimension screenSize, Dimension mapSize, Point mapPosition) {
		if(instance == null) {
			instance = new Manager(screenSize, mapSize, mapPosition);
		}
		return instance;
	}

	public static Manager getInstance() {
		if(instance == null) {
			throw new RuntimeException("No manager has been created yet");
		}
		return instance;
	}

	private Manager(Dimension screenSize, Dimension mapSize, Point mapPosition) {
		this.screenSize = screenSize;
		this.mapSize = mapSize;
		this.mapPosition = mapPosition;
		this.entities = new ArrayList<Entity>();
		this.sheeps = new ArrayList<Sheep>();
		this.chasers = new ArrayList<Chaser>();
		this.dogs = new ArrayList<Dog>();
		this.statics = new ArrayList<Entity>();

		edgeOfScreenHeight = (int) (screenSize.height * edgeOfScreenPercent / 100.0D);
		edgeOfScreenWidth = (int) (screenSize.width * edgeOfScreenPercent / 100.0D);
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public ArrayList<Chaser> getChasers() {
		return chasers;
	}

	public ArrayList<Dog> getDogs() {
		return dogs;
	}

	public ArrayList<Sheep> getSheep() {
		return sheeps;
	}

	public ArrayList<Entity> getStatics() {
		return statics;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
		if(entity instanceof Sheep) {
			sheeps.add((Sheep) entity);
		}
		if(entity instanceof Chaser) {
			chasers.add((Chaser) entity);
		}
		if(entity instanceof Dog) {
			dogs.add((Dog) entity);
		}
		if(entity instanceof StaticEntity) {
			statics.add((Entity) entity);
		}
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
		if(entity instanceof Sheep) {
			sheeps.remove((Sheep) entity);
		}
		if(entity instanceof Chaser) {
			chasers.remove((Chaser) entity);
		}
		if(entity instanceof Dog) {
			dogs.remove((Dog) entity);
		}
		if(entity instanceof StaticEntity) {
			statics.remove((Entity) entity);
		}
	}



	/**
	 * Search for other entities from specified "center" to the distance of "radius"
	 * @param center
	 * @param radius
	 * @return list of entities within specified radius
	 */
	public ArrayList<Entity> lookupEntities(Entity center, Integer radius) {
		Point coor = center.getCoordinate();
		ArrayList<Entity> arr = new ArrayList<Entity>();
		for(Entity obj : entities) {
			if(coor.distance(obj.getCoordinate()) <= radius && !obj.equals(center)) {
				arr.add(obj);
			}
		}
		return arr;
	}

	/**
	 * Determine whether given entity can be displayed on screen
	 * @param obj
	 * @return
	 */
	public boolean isDisplayable(Entity obj) {
		Point coor = obj.getCoordinate();
		Dimension size = obj.getSize();

		return !(
			// right edge
			coor.x > mapPosition.x + screenSize.width ||
			// bottom edge
			coor.y > mapPosition.y + screenSize.height ||
			// left edge
			coor.x+size.width < mapPosition.x ||
			// top edge
			coor.y+size.height < mapPosition.y
		);
	}

	public Edge getEdgeOfScreen(Point pt) {
		Point onScreen = getPositionOnScreen(pt);

		if(onScreen.y-edgeOfScreenHeight < 0) {
			if(onScreen.x-edgeOfScreenWidth < 0) {
				return Edge.TOP_LEFT;
			}
			if(onScreen.x > screenSize.width-edgeOfScreenWidth) {
				return Edge.TOP_RIGHT;
			}
			return Edge.TOP;
		}

		if(onScreen.y > screenSize.height-edgeOfScreenHeight) {
			if(onScreen.x-edgeOfScreenWidth < 0) {
				return Edge.BOTTOM_LEFT;
			}
			if(onScreen.x > screenSize.width-edgeOfScreenWidth) {
				return Edge.BOTTOM_RIGHT;
			}

			return Edge.BOTTOM;
		}
		
		if(onScreen.x-edgeOfScreenWidth < 0) {
			return Edge.LEFT;
		}
		if(onScreen.x > screenSize.width-edgeOfScreenWidth) {
			return Edge.RIGHT;
		}
		
		return null;
	}

	public boolean isOutOfMap(Point pt) {
		return (
			pt.x < 0 ||
			pt.y < 0 ||
			pt.x > mapSize.width ||
			pt.y > mapSize.height
		);
	}

	public boolean correctPosition(Entity ent) {
		Point point = ent.getCoordinate();
		Dimension size = ent.getSize();
		boolean anyCorrectionDone = false;

		// screen edges
		if(point.x < 0) {
			point.x = 0;
			anyCorrectionDone = true;
		}
		if(point.y < 0) {
			point.y = 0;
			anyCorrectionDone = true;
		}
		if(point.x+ent.getSize().width > mapSize.width) {
			point.x = mapSize.width-ent.getSize().width;
			anyCorrectionDone = true;
		}
		if(point.y+ent.getSize().height > mapSize.height) {
			point.y = mapSize.height-ent.getSize().height;
			anyCorrectionDone = true;
		}

		Point position = point.clone();
		// static objects
		for(Entity st: statics) {
			if(st.isOverlapping(ent)) {
				// ent is to the left of st
				if(point.x < st.getCoordinate().x
					&& point.x+size.width < st.getCoordinate().x+st.getSize().width
					&& point.y > st.getCoordinate().y
					&& point.y+size.height < st.getCoordinate().y+st.getSize().height) {
					point.x = st.getCoordinate().x-size.width;
				}
				// ent is to the right of st
				if(point.x < st.getCoordinate().x + st.getSize().width
					&& point.x+size.width > st.getCoordinate().x+st.getSize().width
					&& point.y > st.getCoordinate().y
					&& point.y+size.height < st.getCoordinate().y+st.getSize().height) { 
					point.x = st.getCoordinate().x+st.getSize().width;
				}
				// ent is on top of st
				if(point.x > st.getCoordinate().x
					&& point.x+size.width < st.getCoordinate().x+st.getSize().width
					&& point.y < st.getCoordinate().y
					&& point.y+size.height < st.getCoordinate().y+st.getSize().height) {
					point.y = st.getCoordinate().y-size.height;
				}
				// ent is on bottom of st
				if(point.x > st.getCoordinate().x
					&& point.x+size.width < st.getCoordinate().x+st.getSize().width
					&& point.y > st.getCoordinate().y
					&& point.y+size.height > st.getCoordinate().y+st.getSize().height) {
					point.y = st.getCoordinate().y+st.getSize().height;
				}
				
				anyCorrectionDone = true;
			}
		}

		return anyCorrectionDone;
	}

	public void moveMapPosition(int dx, int dy) {
		mapPosition.translate(dx, dy);
		if(mapPosition.x < 0) {
			mapPosition.x = 0;
		}
		if(mapPosition.y < 0) {
			mapPosition.y = 0;
		}
		if(mapPosition.x+screenSize.width > mapSize.width) {
			mapPosition.x = mapSize.width-screenSize.width;
		}
		if(mapPosition.y+screenSize.height > mapSize.height) {
			mapPosition.y = mapSize.height-screenSize.height;
		}
		
	}

	public Point getPositionOnScreen(Point map) {
		return new Point(
			map.x-mapPosition.x,
			map.y-mapPosition.y
		);
	}

	public Point getPositionOnMap(Point screen) {
		return new Point(
			screen.x+mapPosition.x,
			screen.y+mapPosition.y
		);
	}

	void keyPressed(KeyEvent e) {
		for(Dog dog : dogs) {
			KeyOptions keys = dog.getKeys();
			keys.updateKeyPressedState(e);
			dog.updateKeyState();
		}
	}

	void keyReleased(KeyEvent e) {
		for(Dog dog : dogs) {
			KeyOptions keys = dog.getKeys();
			keys.updateKeyReleasedState(e);
			dog.updateKeyState();
		}
	}

	void mouseMoved(MouseEvent e) {
		for(Dog dog : dogs) {
			dog.updateMouseState(e, false);
		}
	}

	void mouseDragged(MouseEvent e) {
		for(Dog dog : dogs) {
			dog.updateMouseState(e, true);
		}
	}

	public ArrayList<Sheep> findSheepOverlap(Sheep sheep) {
		ArrayList<Sheep> sheepList = new ArrayList<Sheep>();

		for(Sheep other : sheeps) {
			
		}

		return sheepList;
	}

	public enum Edge {
		RIGHT,TOP_RIGHT,TOP,TOP_LEFT,
		LEFT,BOTTOM_LEFT,BOTTOM,BOTTOM_RIGHT
	}
}
