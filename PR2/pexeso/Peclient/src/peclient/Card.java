
package peclient;

/**
 *
 * @author Mullins
 */
public class Card {
    
    private int group;
    private Owner owner;

    public Card(int group, Owner owner) {
        this.group = group;
        this.owner = owner;
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

    @Override
    public String toString() {
        return group + ":" + owner;
    }
    
    
}
