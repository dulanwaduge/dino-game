package game.Behaviours;

import edu.monash.fit2099.engine.*;
import game.Capability.DinosaurType;
import game.Capability.Gender;
import game.Capability.Status;
import game.Capability.TerrainData;
import game.ImplimentedActions.BreedingAction;

/**
 * Constitutes how dinosaurs breed with each other and find potential mates
 */
public class BreedingBehaviour implements Behaviour {
    private Actor target;
    private Status status;
    private DinosaurType dinosaurType;
    private Actor potentialMate;
    private Gender gen;
    private TerrainData terrainData;


    public BreedingBehaviour() {
        this.target = target;
    }

    //impliments the follow behaviour movement to find its suitable mate
    @Override
    public Action getAction(Actor actor, GameMap map) {
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();
        Location currentLocation = new Location(map, 0, 0);
        for (int i = 0; i <= xRange; i++) {
            for (int j = 0; j <= yRange; j++) {
                currentLocation = map.at(i, j);
                potentialMate = currentLocation.getActor();

                //If the actor is a stegosaur male or female it can only breed with an adult stegosaur of the opposite sex that
                //isn't pregnant
                if (actor.hasCapability(dinosaurType.STEGOSAUR)) {
                    if (potentialMate != null && potentialMate.hasCapability(dinosaurType.STEGOSAUR) &&
                            potentialMate.hasCapability(gen.FEMALE) != actor.hasCapability(gen.FEMALE) &&
                            potentialMate.hasCapability(status.ADULT) && !potentialMate.hasCapability(status.PREGNANT)) {
                        target = potentialMate;

                    }
                }


                //If the actor is a brachiosaur male or female it can only breed with an adult brachiosaur of the opposite sex that
                //isn't pregnant
                if (actor.hasCapability(dinosaurType.BRACHIOSAUR)) {
                    if (potentialMate != null && potentialMate.hasCapability(dinosaurType.BRACHIOSAUR) &&
                            potentialMate.hasCapability(gen.FEMALE) != actor.hasCapability(gen.FEMALE) &&
                            potentialMate.hasCapability(status.ADULT) && !potentialMate.hasCapability(status.PREGNANT)) {
                        target = potentialMate;

                    }
                }

                //If the actor is a stegosaur male or female it can only breed with an adult stegosaur of the opposite sex that
                //isn't pregnant
                if (actor.hasCapability(dinosaurType.ALLOSAUR)) {
                    if (potentialMate != null && potentialMate.hasCapability(dinosaurType.ALLOSAUR) &&
                            potentialMate.hasCapability(gen.FEMALE) != actor.hasCapability(gen.FEMALE) &&
                            potentialMate.hasCapability(status.ADULT) && !potentialMate.hasCapability(status.PREGNANT)) {
                        target = potentialMate;

                    }
                }

            }
        }

        //Employs the code from followBehaviour which allows tha actor to follow its breeding partner

        if (map.locationOf(target) != null && distance(map.locationOf(actor), map.locationOf(target)) > 1) {
            return new FollowBehaviour(target).getAction(actor, map);
        }

        //Once impregnated, A dinosaur is no longer breed ready
        if (target != null && !target.hasCapability(status.PREGNANT)) {
            actor.removeCapability(status.BREEDREADY);
            return new BreedingAction(target);
        }
        return new DoNothingAction();
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

