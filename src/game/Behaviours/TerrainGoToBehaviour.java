package game.Behaviours;

import edu.monash.fit2099.engine.*;

/***
 * A class which sets a target location for the actor to go to
 *
 */
public class TerrainGoToBehaviour implements Behaviour{
    private Location target;

    /**
     * Constructor.
     *
     * @param subject the the target location for the actor to go to
     */
    public TerrainGoToBehaviour(Location subject) {
        this.target = subject;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {


        Location here = map.locationOf(actor);
        Location there = target;

        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
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
