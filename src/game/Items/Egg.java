package game.Items;


import edu.monash.fit2099.engine.*;
import game.Capability.State;

import java.util.List;

/**
 * An abstract class which holds all the behaviours for an egg
 */
public abstract class Egg extends Item {
    private State state;
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Egg(String name, char displayChar, boolean portable, int status) {
        super(name, displayChar, portable);
        if (status == 1) {
            this.addCapability(state.ACTIVE);
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
    }

    @Override
    public void tick(Location currentLocation) {
        if (this.hasCapability(state.ACTIVE)) {
            hatch(currentLocation);
        }
    }

    @Override
    public char getDisplayChar() {
        return super.getDisplayChar();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PickUpItemAction getPickUpAction() {
        return super.getPickUpAction();
    }

    @Override
    public DropItemAction getDropAction() {
        this.addCapability(state.ACTIVE);
        return super.getDropAction();
    }

    @Override
    public List<Action> getAllowableActions() {
        return super.getAllowableActions();
    }

    @Override
    public Weapon asWeapon() {
        return super.asWeapon();
    }

    @Override
    public boolean hasCapability(Enum<?> capability) {
        return super.hasCapability(capability);
    }

    @Override
    public void addCapability(Enum<?> capability) {
        super.addCapability(capability);
    }

    @Override
    public void removeCapability(Enum<?> capability) {
        super.removeCapability(capability);
    }

    /**
     * Hatches an egg producing a baby dionsaur and placing it at the location of the egg
     * @param currentLocation the location where the egg will hatch
     */
    public void hatch(Location currentLocation){}
}





