package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Capability.Status;

import java.util.Map;

/***
 * Allows a Pterodactyl to take off from a tree
 *
 */
public class LiftoffAction extends Action {
    private Status status;
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.addCapability(status.TAKEOFF);
        return actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' + " has taken flight!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
