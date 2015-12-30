/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexeso;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author michal
 */
public class TileMap extends JPanel implements MouseListener {
	public static int HIDDEN_TILE = 0;
	private static int showingTime = 1500;
	private MainWindow mw;
	private Computer comp;
	private int compDifficulty = Computer.MEDIUM;

	public TileMap(MainWindow mw) {
		this.mw = mw;

		setTheme("default");
		icons[HIDDEN_TILE] = new ImageIcon("/usr/lib/pexeso/img/hidden.png");

		GridLayout gl = new GridLayout();
		gl.setHgap(0);
		gl.setVgap(0);
		setLayout(gl);
	}

	void newGame() {
		pairsFound = 0;
		computerPairsFound = 0;
		createTiles();
		shuffleTiles();
		comp = new Computer();
		comp.setDifficulty(compDifficulty);
		System.out.println("Nová hra");
		System.out.println("--------");
	}

	void repeatGame() {
		pairsFound = 0;
		computerPairsFound = 0;
		undoTiles();
		comp = new Computer();
		comp.setDifficulty(compDifficulty);
		System.out.println("Opakovanie hry");
		System.out.println("--------");
	}

	void gameOver() {
		mw.gameOver(pairsFound, computerPairsFound);
		blockMouse = false;
	}

	void pauseGame() {
		Component[] components = getComponents();
		for(int i = 0; i < components.length; i++) {
			((Tile) components[i]).setEnabled(false);
		}
		blockMouse = true;
	}

	void resumeGame() {
		Component[] components = getComponents();
		for(int i = 0; i < components.length; i++) {
			((Tile) components[i]).setEnabled(true);
		}
		blockMouse = false;
	}

	void showTiles() {
		blockMouse = true;
		Component[] components = getComponents();
		for(int i = 0; i < components.length; i++) {
			((Tile) components[i]).setShow(true);
		}
		new Timer().schedule(new TimerTask() {
			public void run() {
				hideTiles();
				blockMouse = false;
			}
		}, showingTime);
	}

	/**
	 * Size of tile map
	 */
	private int tileRows = 8, tileCols = 8;
	public int pairsFound, computerPairsFound;


	public void setTileSize(int rows, int cols) throws PexesoException {
		if(rows*cols % 2 == 1) {
			throw new PexesoException("Počet kariet pexesa musí byť deliteľný dvomi.");
		}
		if(rows*cols > 64) {
			throw new PexesoException("Najviac môže byť 64 kariet");
		}
		if(rows > 8 || cols > 8) {
			throw new PexesoException("Najviac môže byť 8 riadkov a 8 stĺpcov");
		}

		tileRows = rows;
		tileCols = cols;

		createTiles();
		setMinimumSize(new Dimension(tileRows*50+40, tileCols*50+40));
	}

	public int getTileRows() {
		return tileRows;
	}

	public int getTileCols() {
		return tileCols;
	}


	private int getNumberOfPairs() {
		return tileRows*tileCols/2;
	}

	public int getCompDifficulty() {
		return compDifficulty;
	}

	public void setCompDifficulty(int compDifficulty) {
		this.compDifficulty = compDifficulty;
	}

	

	private void createTiles() {
		GridLayout layout = (GridLayout) getLayout();
		layout.setRows(tileRows);
		layout.setColumns(tileCols);
		// clear all remaining tiles
		removeAll();

		int k = 1;
		Tile tile;
		for(int i = 0; i < tileRows; i++) {
			for(int j = 0; j < tileCols; j++) {
				tile = new Tile(
					((i*tileCols+j+1) % 2 == 0)
						? k++
						: k
				);
				tile.addMouseListener(this);
				tile.setPosition(i*tileCols+j);
				add(tile);
			}
		}
		updateUI();
		//MainWindow.getInstance().pack();
	}

	private void shuffleTiles() {
		Component[] components = getComponents();
		List list = Arrays.asList(components);
		Iterator it = list.iterator();
		Collections.shuffle(list);
		removeAll();
		int i = 0;
		while(it.hasNext()) {
			Tile tile = (Tile) it.next();
			tile.setPosition(i);
			add(tile);
			i++;
		}
	}

	public void hideTiles() {
		Component[] components = getComponents();
		for(int i = 0; i < components.length; i++) {
			((Tile) components[i]).setShow(false);
		}
	}

	
	/**
	 * Set all tiles to be not done and not shown
	 */
	public void undoTiles() {
		Component[] components = getComponents();
		for(int i = 0; i < components.length; i++) {
			Tile tile = (Tile) components[i];
			tile.setShow(false);
			tile.setDone(false);
		}
	}

	private static String theme;
	public static String getTheme() {
		return theme;
	}

	public boolean setTheme(String theme) {
		if(!checkTheme(theme)) return false;

		this.theme = theme;
		for(int i = 1; i <= 32; i++) {
			icons[i]= new ImageIcon("/usr/lib/pexeso/img/"+getTheme()+"/"+i+".png");
			if(icons[i].getIconHeight() != 50 || icons[i].getIconWidth() != 50) {
				throw new RuntimeException("Icon size must be 50x50px (is "
					+icons[i].getIconHeight()+"x"+icons[i].getIconWidth()+"px for icon "+getTheme()+"/"+i+".png)");
			}
		}

		return true;
	}

	public List<String> getThemes() {
		File dir = new File("/usr/lib/pexeso/img");
		List<String> themes = new ArrayList<String>();
		for(String item : dir.list()) {
			if(checkTheme(item)) themes.add(item);
		}
		return themes;
	}

	/**
	 * Check if given directory can be used as a theme
	 * @param theme
	 * @return
	 */
	private boolean checkTheme(String theme) {
		File themeDir = new File("/usr/lib/pexeso/img/"+theme);
		if(!themeDir.isDirectory()) return false;
		
		int imgCnt = 0;
		for(String item : themeDir.list()) {
			if(item.endsWith(".png")) {
				try {
					int index = Integer.parseInt(item.substring(0, item.indexOf(".png")));
					if(index >= 1 && index <= 32) imgCnt++;
				} catch(NumberFormatException e) {}
			}
		}
		return imgCnt == 32;
	}


	private static ImageIcon icons[] = new ImageIcon[33];
	public static ImageIcon getIcon(int id) {
		if(icons[id] == null) {
			icons[id]= new ImageIcon("/usr/lib/pexeso/img/"+getTheme()+"/"+id+".png");
		}

		return icons[id];
	}

	private Tile lastTile;
	private boolean stopTimer = false;
	private boolean blockMouse = false;
	public void mouseClicked(MouseEvent e) {
		final Tile tile = (Tile) e.getComponent();
		mw.setPairsFound(pairsFound, computerPairsFound);
		if(blockMouse) return;

		if(lastTile == null) {
			lastTile = tile;
			hideTiles();
			stopTimer = true;
			tile.setShow(true);
		} else {
			if(lastTile == tile) return;

			tile.setShow(true);

			if(lastTile.getId() != tile.getId()) {
				comp.rememberPosition(tile);
				comp.rememberPosition(lastTile);
				blockMouse = true;
				stopTimer = false;
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						if(!stopTimer) {
							tile.setShow(false);
							lastTile.setShow(false);
							lastTile = null;
							computerTurn();
						}
					}
				}, showingTime);
			} else {
				tile.setDone(true);
				lastTile.setDone(true);
				System.out.println("Našiel si "+tile.getId());
				pairsFound++;
				if(pairsFound+computerPairsFound == getNumberOfPairs()) {
					gameOver();
				} else {
					mw.setPairsFound(pairsFound, computerPairsFound);
					comp.forgetPosition(tile);
					comp.forgetPosition(lastTile);
				}
				lastTile = null;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		Tile tile = (Tile) e.getComponent();
		tile.setFocused(true);
	}

	public void mouseExited(MouseEvent e) {
		Tile tile = (Tile) e.getComponent();
		tile.setFocused(false);
	}

	/**
	 * Generate computer turn
	 */
	private void computerTurn() {
		final Tile[] turn = comp.takeTurn(this);
		if(turn[0] == null) return;
		blockMouse = true;
		turn[0].setShow(true);
		new Timer().schedule(new TimerTask() {
			public void run() {
				turn[1].setShow(true);
				new Timer().schedule(new TimerTask() {
					public void run() {
						if(turn[0].getId() == turn[1].getId()) {
							turn[0].setDone(true);
							turn[1].setDone(true);
							comp.forgetPosition(turn[0]);
							comp.forgetPosition(turn[1]);
							System.out.println("Počítač našiel "+turn[0].getId());
							computerPairsFound++;
							if(pairsFound+computerPairsFound == getNumberOfPairs()) {
								gameOver();
							} else {
								mw.setPairsFound(pairsFound, computerPairsFound);
								new Timer().schedule(new TimerTask() {
									public void run() {
										computerTurn();
									}
								},700);
							}
						} else {
							turn[0].setShow(false);
							turn[1].setShow(false);
							blockMouse = false;
						}
					}
				}, turn[0].getId() == turn[1].getId() ? 200 : showingTime);
			}
		}, 200);
	}	
}
