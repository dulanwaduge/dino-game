package game.Items;

import edu.monash.fit2099.engine.*;
import game.Capability.ItemCapable;

import java.util.List;


/**
 * A class representing a water bottle item
 */
public class WaterBottle extends Item {
    private ItemCapable itemCapable;

    public WaterBottle(String name, char displayChar, boolean portable) {
        super("Water Bottle", '8', true);
        this.addCapability(itemCapable.WATERBOTTLE);
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);

    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
    }

}
