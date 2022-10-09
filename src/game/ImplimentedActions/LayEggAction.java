package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Items.AllosaurEgg;
import game.Items.BrachiosaurEgg;
import game.Items.PterodactylEgg;
import game.Items.StegosaurEgg;


/**
 * Allows a dinosaur to lay an egg at a given location once the pregnancy period is over
 */
public class LayEggAction extends Action {
    private Status status;
    private DinosaurType dinosaurType;

    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasCapability(dinosaurType.STEGOSAUR)) {
            StegosaurEgg stegosaurEgg = new StegosaurEgg("StegosaurEgg", '0', true, 1);
            map.locationOf(actor).addItem(stegosaurEgg);
        }
        if (actor.hasCapability(dinosaurType.BRACHIOSAUR)) {
            BrachiosaurEgg brachiosaurEgg = new BrachiosaurEgg("BrachiosaurEgg", '0', true, 1);
            map.locationOf(actor).addItem(brachiosaurEgg);
        }
        if (actor.hasCapability(dinosaurType.ALLOSAUR)) {
            AllosaurEgg allosaurEgg = new AllosaurEgg("AllosaurEgg", '0', true, 1);
            map.locationOf(actor).addItem(allosaurEgg);
        }

        if (actor.hasCapability(dinosaurType.PTERODACTYL)) {
            PterodactylEgg pterodactylEgg = new PterodactylEgg("PterodactylEgg", '0', true, 1);
            map.locationOf(actor).addItem(pterodactylEgg);
        }
        return menuDescription(actor, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }

    public String menuDescription(Actor actor, GameMap map) {
        return actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' + " Has layed an egg";
    }
}
