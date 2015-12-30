package pexerver;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mullins
 */
public class ArtificialIntelligence {

    private ArrayList<Card> memory;
    private int[] cardsPicked;
    private ArrayList<Card> gS;
    private Random ranGen;

    public ArtificialIntelligence() {
        this.memory = new ArrayList<Card>(5);
        this.memory = new ArrayList<Card>(100);
        this.cardsPicked = new int[]{-1, -1};
        this.ranGen = new Random();
    }

    /**
     * Firstly, sets gamestate to work with. Cuts excess elements in memory and
     * checks available elements in gamestate. After that, it calls
     * <b>checkMem()</b> and either returns, or picks a random card. Repeats
     * from beginning of last sentence.
     *
     * @param gameState
     */
    public void play(ArrayList<Card> gameState) {

        cardsPicked[0] = -1;
        cardsPicked[1] = -1;
        for (int i = 0; i < gameState.size(); i++) {
            gameState.get(i).setPosition(i);
        }
        gS = gameState;
        if (memory.size() > 5) {
            memory.remove(0);
        }
        setAvailable();
        if (checkMem()) {
            return;
        }
        int r = ranGen.nextInt(gS.size() - 1);
        cardsPicked[0] = gS.get(r).getPosition();
        memory.add(gS.get(r));
        if (checkMem()) {
            return;
        }
        gS.remove(r);
        r = ranGen.nextInt(gS.size() - 1);
        cardsPicked[1] = gS.get(r).getPosition();
        memory.add(gS.get(r));
    }

    /**
     * Picks cards from gamestate, which are not owned by any player so far.
     * Also removes cards which are in memory.
     *
     */
    private void setAvailable() {

        for (int i = 0; i < gS.size(); i++) {
            if (!gS.get(i).getOwner().equals(Owner.GAME)) {
                memory.remove(gS.get(i));
                gS.remove(i--);
            }
            for (int j = 0; j < memory.size(); j++) {
                if (gS.get(i).getPosition() == memory.get(j).getPosition()) {
                    gS.remove(i--);
                }
            }
        }
    }

    /**
     * Checks whether there are two cards of same group in memory. If they do,
     * it picks these cards and returns true.
     *
     * @return boolean
     */
    private boolean checkMem() {

        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).getPosition() == memory.get(memory.size() - 1).getPosition()) {
                cardsPicked[0] = memory.get(i).getPosition();
                cardsPicked[1] = memory.get(memory.size() - 1).getPosition();
                return true;
            }
        }
        return false;
    }

    public int[] pickCards() {
        return cardsPicked;
    }
}
