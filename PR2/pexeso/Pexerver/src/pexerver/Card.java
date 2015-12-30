
package pexerver;

/**
 *
 * @author Mullins
 */
public class Card {
    
    private int group;
    private Owner owner;
    private int position;

    public Card(int group, Owner owner) {
        this.group = group;
        this.owner = owner;
    }

    public Card(int group, Owner owner, int position) {
        this.group = group;
        this.owner = owner;
        this.position = position;
    }
    
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getGroup() {
        return group;
    }
    
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public String toString() {
        return group + ":" + owner;
    }
    
    
}
