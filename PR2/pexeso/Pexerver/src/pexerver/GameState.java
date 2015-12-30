/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexerver;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class GameState {

	private ArrayList<Card> cards;

	GameState(int numberOfCards) {
		cards = new ArrayList<Card>(numberOfCards);
	}

}
