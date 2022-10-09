package game.Behaviours;

import edu.monash.fit2099.engine.*;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Dinosaurs.Stegosaur;
import game.ImplimentedActions.HerbivoreEatAction;


/***
 * Constitutes how a hungry herbivore looks for food
 */
public class HerbivoreFindFoodBehaviour implements Behaviour {
    private Status status;
    private DinosaurType dinosaurType;


    @Override
    public Action getAction(Actor actor, GameMap map) {
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();
        int newDistance = 1000000;

        Location here = map.locationOf(actor);
        Location there = new Location(map, -1, -1);
        Location currentLocation = new Location(map, 0, 0);

            for (int i = 0; i <= xRange; i++) {
                for (int j = 0; j <= yRange; j++) {
                    currentLocation = map.at(i, j);


                    if (actor.hasCapability(dinosaurType.STEGOSAUR)) {
                        if ((currentLocation.getGround().hasCapability(status.FEEDSTEGOSAUR))) {
                            if (!currentLocation.containsAnActor() || currentLocation.containsAnActor() && currentLocation.getActor() == actor) {
                                if (currentLocation != null && here != null) {
                                    if (distance(currentLocation, here) < newDistance) {
                                        newDistance = distance(currentLocation, here);
                                        there = currentLocation;
                                    }
                                }
                            }
                        }
                    }

                    if (actor.hasCapability(dinosaurType.BRACHIOSAUR)) {
                        if ((currentLocation.getGround().hasCapability(status.FEEDBRACHIOSAUR))) {
                            if (!currentLocation.containsAnActor() || currentLocation.containsAnActor() && currentLocation.getActor() == actor) {
                                if (currentLocation != null && here != null) {
                                    if (distance(currentLocation, here) < newDistance) {
                                        newDistance = distance(currentLocation, here);
                                        there = currentLocation;
                                    }
                                }
                            }
                        }
                    }
                }
            }


        //code from follow behaviour allowing the actor to get closer to the target
        if (here != null && there != null){
        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
                }
            }
        }
        return new HerbivoreEatAction();
    }
    private int distance(Location a, Location b) {return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());}
}
