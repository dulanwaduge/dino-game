package game.Items;

import edu.monash.fit2099.engine.Location;
import game.Capability.ItemCapable;
import game.Capability.State;
import game.Capability.Status;
import game.Dinosaurs.Allosaur;
import game.Dinosaurs.Pterodactyl;
import game.Player;

/**
 * A class which represents a pterodactyl egg
 *
 */
public class PterodactylEgg extends Egg{
    private int age;
    private State state;
    private Status status;
    private ItemCapable itemCapable;


    /***
     * Constructor.
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     * @param status
     */
    public PterodactylEgg(String name, char displayChar, boolean portable, int status) {
        super("Pterodactyl Egg", '0', true, 1);


    }
    @Override
    public void hatch(Location currentLocation) {
        this.age++;
        if (this.age > 10) {
            Pterodactyl pterodactyl = new Pterodactyl("Pterodactyl Baby", "BABY");
            if (currentLocation.containsAnActor()) {
                return;
            }
            currentLocation.removeItem(this);
            currentLocation.addActor(pterodactyl);
            Player.ecoPoints += 100;
            System.out.println("Pterodactyl egg has hatched");
        }
    }
}

