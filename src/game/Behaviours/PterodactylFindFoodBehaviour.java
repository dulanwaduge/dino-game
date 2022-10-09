package game.Behaviours;

import edu.monash.fit2099.engine.*;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Capability.TerrainData;
import game.Dinosaurs.Dinosaur;


/***
 * Special class for the Pterodactyl's unique food needs
 *
 */
public class PterodactylFindFoodBehaviour implements Behaviour {
    private Status status;
    private TerrainData terrainData;
    private DinosaurType dinosaurType;
    private Location target;

    @Override
    public Action getAction(Actor actor, GameMap map) {
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();

        int minDistance = xRange * yRange;
        Location currentLocation = map.at(0, 0);
        for (int i = 0; i <= xRange; i++) {
            for (int j = 0; j <= yRange; j++) {
                currentLocation = map.at(i, j);
                for (Item item : currentLocation.getItems()) {
                    if (item.hasCapability(status.CORPSE)) {
                        if (checkForDinosaurs(currentLocation, map)) {
                            if (distance(map.locationOf(actor), currentLocation) < minDistance) {
                                minDistance = distance(map.locationOf(actor), currentLocation);
                                target = currentLocation;
                            }
                        }
                    }
                }
                if (currentLocation.getGround().hasCapability(terrainData.LAKE)){
                    if (distance(map.locationOf(actor), currentLocation) < minDistance){
                        minDistance = distance(map.locationOf(actor), currentLocation);
                        target = currentLocation;

                    }
                }
            }
        }
        if (actor != null && target != null && distance(map.locationOf(actor), target) > 0) {
            return new TerrainGoToBehaviour(target).getAction(actor, map);
        }
        if (target != null) {
            for (Item item : target.getItems()) {
                if (item.hasCapability(status.CORPSE)) {
                    item.addCapability(status.EATENFROM);
                    actor.heal(10);
                    System.out.println(actor + " at " + '(' + map.locationOf(actor).x() + ',' + map.locationOf(actor).y() + ')' + " is eating a corpse");
                    return new DoNothingAction();
                }
            }
        }
        return new WanderBehaviour().getAction(actor, map);
    }


    private Boolean checkForDinosaurs(Location location, GameMap map) {
        Location currentSquare = map.at(0, 0);
        for (int i = location.x() - 5; i <= location.x() + 5; i++) {
            for (int j = location.y() - 2; j <= location.y() + 2; j++) {
                if ((i >= 0 && i <= map.getXRange().max()) && j >= 0 && j <= map.getYRange().max()) {
                    currentSquare = map.at(i, j);
                    if (currentSquare.containsAnActor() && !currentSquare.getActor().hasCapability(dinosaurType.PTERODACTYL)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}

