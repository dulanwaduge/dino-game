package game.Behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Capability.TerrainData;
import game.ImplimentedActions.LiftoffAction;

/***
 * Allows a downed Pterodactyl to find a tree from which to launch itself
 *
 */
public class FindLaunchPlaceBehaviour implements Behaviour {
    private TerrainData terrainData;
    private Location target;

    @Override
    public Action getAction(Actor actor, GameMap map) {
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();
        int minDistance = (xRange*yRange);
        Location currentLocation = map.at(0, 0);
        for (int i = 0; i <= xRange; i++) {
            for (int j = 0; j <= yRange; j++) {
                currentLocation = map.at(i, j);
                if (currentLocation.getGround().hasCapability(terrainData.TREE)) {
                    if (distance(currentLocation, map.locationOf(actor)) < minDistance){
                        target = currentLocation;
                        minDistance = distance(currentLocation, map.locationOf(actor));
                    }

                }
            }
        }
        if (target != null && actor != null && distance(target, map.locationOf(actor)) > 0) {
            return new TerrainGoToBehaviour(target).getAction(actor, map);
        }
        return new LiftoffAction();
    }

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
