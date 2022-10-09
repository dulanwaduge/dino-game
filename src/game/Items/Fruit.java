package game.Items;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.interfaces.GroundInterface;
import game.Capability.Status;

/**
 * A class which represents a fruit item
 *
 */

public class Fruit extends Item {
    private int age;
    private Status status;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Fruit(String name, char displayChar, boolean portable) {
        super("fruit", 'F', portable);
        this.addCapability(status.ISFRUIT);
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        if (this.hasCapability(status.ROTTABLE)){
            age++;
        }
        if(age == 15){
            this.addCapability(status.ROTTEN);
        }
    }


    public void setAge(int age) {
        this.age = age;
    }


    public int getAge() {
        return age;
    }

}
