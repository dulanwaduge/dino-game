package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import game.Application;
import game.Capability.GameEnvironment;

/**
 * An action class which allows a player to go to a new map
 *
 */
public class GoToNewMapAction extends Action {
    private GameEnvironment gameEnvironment;

    @Override
    public String execute(Actor actor, GameMap map) {
        actor.addCapability(gameEnvironment.MAPSWITCH);
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " went to a new map";
    }
}
