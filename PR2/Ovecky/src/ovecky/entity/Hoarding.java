/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky.entity;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import ovecky.GamePanel;
import ovecky.Manager.Edge;
import ovecky.Point;

/**
 *
 * @author michal
 */
public class Hoarding extends Entity {
	private Edge entrance;
	private ArrayList<Fence> fences;
	private int fenceWidth = 5;
	private double entranceSize = .80;

	public Hoarding(Point coor, Dimension sz, Edge entr) {
		this.coordinate = coor;
		this.size = sz;
		this.entrance = entr;
		
		// create fences
		fences = new ArrayList<Fence>();

		if(entrance == Edge.RIGHT) {
			fences.add(new Fence(coordinate, new Dimension(fenceWidth, size.height)));
			fences.add(new Fence(new Point(coordinate.x+fenceWidth, coordinate.y), new Dimension(size.width-2*fenceWidth, fenceWidth)));
			fences.add(new Fence(new Point(coordinate.x+fenceWidth, coordinate.y+size.height-fenceWidth), new Dimension(size.width-2*fenceWidth, fenceWidth)));
			fences.add(new Fence(new Point(coordinate.x+size.width-fenceWidth, coordinate.y), new Dimension(fenceWidth, (int) (size.height * (1-entranceSize)/2))));
			fences.add(new Fence(new Point(coordinate.x+size.width-fenceWidth, coordinate.y+size.height*(1+entranceSize)/2), new Dimension(fenceWidth, (int) (size.height * (1-entranceSize)/2))));
		} else {
			throw new UnsupportedOperationException("This edge is not supported yet. ("+entrance+")");
		}
	}

	public ArrayList<Fence> getFences() {
		return fences;
	}
	
	@Override
	public void run() {
		// find if any sheep are inside hoarding
		int count = 0;
		for(Sheep sheep : getManager().getSheep()) {
			if(contains(sheep)) {
				count++;
			}
		}

		if(count == getManager().getSheep().size()) {
			GamePanel.getInstance().endOfGame();
		}
		
	}

	@Override
	public void paint(Graphics g) {
//		for(Fence fence : fences) {
//			fence.paint(g);
//		}
	}

}
