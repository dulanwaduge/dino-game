package game.Behaviours;

import edu.monash.fit2099.engine.*;
import game.Capability.Gender;
import game.Capability.Status;
import game.Capability.TerrainData;
import game.ImplimentedActions.BreedingAction;

/***
 * Special class for the Pterodactyl's unique mating behaviour
 *
 */
public class PterodactylBreedingBehaviour implements Behaviour {
    private Gender gen;
    private TerrainData terrainData;
    private Status status;
    private Actor target;
    private Location targetLocation;

    @Override
    public Action getAction(Actor actor, GameMap map) {
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();
        int minDistance = (xRange*yRange)*2;
        Location closestLocation = map.at(0,0);
        Location currentLocation = new Location(map, 0, 0);
        for (int i = 0; i <= xRange; i++) {
            for (int j = 0; j <= yRange; j++) {
                currentLocation = map.at(i, j);
                if(actor.hasCapability(gen.FEMALE)){
                    if(currentLocation.getGround().hasCapability(terrainData.TREE)
                        && currentLocation.getGround().hasCapability(terrainData.ADJTREE)){
                        if (minDistance > distance(map.locationOf(actor), currentLocation)){
                            minDistance = distance(map.locationOf(actor), currentLocation);
                            closestLocation = currentLocation;
                        }

                    }

                }

                if(actor.hasCapability(gen.MALE)){
                    if(currentLocation.getActor() != null && currentLocation.getActor().hasCapability(status.NESTED)){
                        if(minDistance > distance(map.locationOf(actor), map.locationOf(currentLocation.getActor()))){
                            minDistance = distance(map.locationOf(actor), map.locationOf(currentLocation.getActor()));
                            target = currentLocation.getActor();
                            for(Exit exit: map.locationOf(target).getExits()){
                                if(exit.getDestination().getGround().hasCapability(terrainData.TREE)){
                                    targetLocation = exit.getDestination();
                                }
                            }
                        }

                    }

                }

            }
        }

        if(actor.hasCapability(gen.FEMALE)){
            if(distance(map.locationOf(actor), closestLocation) == 0){
                if(!actor.hasCapability(status.NESTED)){
                    actor.addCapability(status.NESTED);
                }

                if(actor.hasCapability(status.SELECTEDASMATE) && !actor.hasCapability(status.MOVEDTONEST)){
                    for (Exit exit : closestLocation.getExits()) {
                        if (exit.getDestination().getGround().hasCapability(terrainData.TREE)) {
                            actor.addCapability(status.MOVEDTONEST);
                            return new MoveActorAction(exit.getDestination(), " to nest ");
                        }
                    }
                }
                return new DoNothingAction();
            }
            return new TerrainGoToBehaviour(closestLocation).getAction(actor, map);
        }

        if(map.locationOf(target) != null && actor.hasCapability(gen.MALE)){
            if(distance(map.locationOf(actor), map.locationOf(target)) <= 2
                && map.locationOf(actor).getGround().hasCapability(terrainData.TREE)){
                return new BreedingAction(target);

            }
            if(!target.hasCapability(status.SELECTEDASMATE)){
                target.addCapability(status.SELECTEDASMATE);
            }
            if(targetLocation != null){
                return new TerrainGoToBehaviour(targetLocation).getAction(actor, map);
            }
        }
        return new WanderBehaviour().getAction(actor,map);
    }

    /***
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
