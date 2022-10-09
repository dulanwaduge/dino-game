package game.Items;

import edu.monash.fit2099.engine.*;
import game.Capability.ItemCapable;
import game.Capability.State;
import game.Capability.Status;
import game.Dinosaurs.Allosaur;
import game.Dinosaurs.Stegosaur;
import game.Player;

import java.util.List;

/**
 * A class which represents an allosaur egg
 *
 */

public class AllosaurEgg extends Egg {
    private int age;
    private State state;
    private Status status;
    private ItemCapable itemCapable;

    /***
     * Constructor.
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public AllosaurEgg(String name, char displayChar, boolean portable, int status) {
        super(name, '0', true, status);
        this.addCapability(itemCapable.ALLOEGG);
        this.addCapability(itemCapable.EGG);
    }

    @Override
    public void hatch(Location currentLocation) {
            this.age++;
            if (this.age > 2) {
                Allosaur allosaur = new Allosaur("Allosaur Baby", "BABY");
                if (currentLocation.containsAnActor()) {
                    return;
                }
                currentLocation.removeItem(this);
                currentLocation.addActor(allosaur);
                Player.ecoPoints += 1000;
                System.out.println("Allosaur egg has hatched");
            }
    }
}
