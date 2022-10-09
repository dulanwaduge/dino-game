package game.Items;

import edu.monash.fit2099.engine.Item;
import game.Capability.Status;


/**
 * A class which represents a fish
 *
 */
public class Fish extends Item {

    private Status status;
    private int foodPoints;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Fish(String name, char displayChar, boolean portable) {
        super("Fish", '$', portable);
        this.foodPoints = 5;
        this.addCapability(status.ISFISH);
    }
}
