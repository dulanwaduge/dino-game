package game.Behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import game.Capability.Status;


/**
 * Describes how an unconcious actor behaves
 */
public class UnconciousBehaviour implements Behaviour{
    private Status status;
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (map.locationOf(actor) != null) {
            System.out.println(actor + " at " +  "(" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") is unconscious");
            if (actor.hasCapability(status.BABY)) {
                actor.removeCapability(status.BABY);
            }
            if (actor.hasCapability(status.ADULT)) {
                actor.removeCapability(status.ADULT);
            }
            return new DoNothingAction();
        }
        return new DoNothingAction();
    }
}
