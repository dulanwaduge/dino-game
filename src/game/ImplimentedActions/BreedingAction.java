package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Dinosaurs.Allosaur;
import game.Items.AllosaurEgg;
import game.Items.BrachiosaurEgg;
import game.Items.StegosaurEgg;

/**
 * Allows a dinosaur to perform a breeding act with its target mate
 */
public class BreedingAction extends Action {
    private Actor target;
    private Status status;
    private DinosaurType dinosaurType;

    public BreedingAction(Actor target){
        this.target = target;

    }
    @Override
    public String execute(Actor actor, GameMap map) {
        //Make the target pregnant and the actor also pregnant so that they both do nothing
        target.addCapability(status.PREGNANT);
        actor.addCapability(status.PREGNANT);

        return menuDescription(actor, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }


    public String menuDescription(Actor actor, GameMap map) {return actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' + " is mating";}
}
