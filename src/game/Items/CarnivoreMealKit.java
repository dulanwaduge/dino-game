package game.Items;

import edu.monash.fit2099.engine.Item;
import game.Capability.ItemCapable;


/**
 * A class which represents a carnivore meal kit
 *
 */
public class CarnivoreMealKit extends Item {
    private ItemCapable itemCapable;
    private int points;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public CarnivoreMealKit(String name, char displayChar, boolean portable, int points) {
        super(name, 'c', portable);
        this.points = points;
        this.addCapability(itemCapable.CARNMEALKIT);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
