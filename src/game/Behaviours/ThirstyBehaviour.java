package game.Behaviours;

import edu.monash.fit2099.engine.*;
import game.Capability.Status;
import game.Capability.TerrainData;
import game.ImplimentedActions.DrinkAction;


/***
 * Causes all thirsty dinosaurs to go to the nearest lake block
 *
 */
public class ThirstyBehaviour implements Behaviour{
    private TerrainData terrainData;
    private Status status;
    private Location target;


    public Action getAction(Actor actor, GameMap map) {
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();
        int minDistance = (xRange * yRange)*2;
        Location currentLocation = map.at(0,0);
        for (int i = 0; i <= xRange; i++){
            for(int j = 0; j <= yRange; j++){
                currentLocation = map.at(i,j);
                if(currentLocation.getGround().hasCapability(terrainData.LAKE)
                && currentLocation.getGround().hasCapability(status.HASWATER)){
                    if (minDistance > distance(currentLocation, map.locationOf(actor))){
                        this.target = currentLocation;
                        minDistance = distance(currentLocation, map.locationOf(actor));
                    }
                }
            }
        }
        if(this.target != null && distance(map.locationOf(actor), this.target) > 1){
            return new TerrainGoToBehaviour(this.target).getAction(actor, map);
        }

        if(this.target != null && distance(map.locationOf(actor), this.target) == 1
            ||this.target != null && distance(map.locationOf(actor), this.target) == 0){
            return new DrinkAction(this.target);
        }
        return new DoNothingAction();
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
