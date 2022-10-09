package game.Items;

import edu.monash.fit2099.engine.*;
import game.Capability.ItemCapable;


/**
 * a class which represents a lasergun weapon
 */
public class LaserGun extends WeaponItem {
    private ItemCapable itemCapable;
    private int points;

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     */
    public LaserGun(String name, char displayChar, int damage, String verb) {
        super(name, '}', damage, verb);
        this.addCapability(itemCapable.LASERGUN);
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
