/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexeso;

import java.io.Serializable;

/**
 *
 * @author michal
 */
public class Save implements Serializable {
	Integer tilesIds[];
	Boolean tilesDone[];
	String theme;
	Integer compDifficulty;
	Integer rows, cols;
	Integer pairsFound, computerPairsFound;
}
