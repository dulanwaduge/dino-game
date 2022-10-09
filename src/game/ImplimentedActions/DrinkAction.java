package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Capability.Status;
import game.Capability.TerrainData;


/***
 * Allows a thirsty dinosaur to drink from a lake
 *
 */
public class DrinkAction extends Action {
    private Status status;
    private Location lake;
    private TerrainData terrainData;

    /***
     * constructor
     *
     * @param lake the lake from which the dinosaur drinks
     */
    public DrinkAction(Location lake){
        this.lake = lake;
    }


    @Override
    public String execute(Actor actor, GameMap map) {
        actor.addCapability(status.DRINKING);
        this.lake.getGround().addCapability(terrainData.SIPPED);
        return actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' + " is drinking from the lake";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
