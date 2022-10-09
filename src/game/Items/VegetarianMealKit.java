package game.Items;

import edu.monash.fit2099.engine.Item;
import game.Capability.ItemCapable;
/**
 * A class which represents a vegitarian meal kit
 *
 */

public class VegetarianMealKit extends Item {
    private ItemCapable itemCapable;

    private int points;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public VegetarianMealKit(String name, char displayChar, boolean portable, int points) {
        super(name, 'v', portable);
        this.points = points;
        this.addCapability(itemCapable.VEGMEALKIT);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
