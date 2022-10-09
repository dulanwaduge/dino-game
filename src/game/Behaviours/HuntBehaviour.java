package game.Behaviours;

import edu.monash.fit2099.engine.*;
import game.Capability.DinosaurType;
import game.Capability.ItemCapable;
import game.Capability.State;
import game.Capability.Status;
import game.ImplimentedActions.AttackAction;

import java.security.PrivateKey;


/**
 * constitutes how a carnivore hunts for food such as a herbivore, egg or corpse
 *
 */
public class HuntBehaviour implements Behaviour {
    private Status status;
    private DinosaurType dinosaurType;
    private Actor target;
    private Location targetLocation;
    private Location there;
    private ItemCapable itemCapable;
    private State state;

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location currentLocation = new Location(map, 0, 0);
        Location here = map.locationOf(actor);
        int xRange = map.getXRange().max();
        int yRange = map.getYRange().max();
        int minDistance = (xRange*yRange)*2;


        for (int i = 0; i < xRange; i++) {
            for (int j = 0; j < yRange; j++) {
                boolean containsCorpse = false;
                boolean containsEgg = false;
                currentLocation = map.at(i, j);

                    for(Item item: currentLocation.getItems()){
                        if(item.hasCapability(status.CORPSE)){
                            containsCorpse = true;
                        }
                        if(item.hasCapability(itemCapable.EGG)){
                            containsEgg = true;
                        }
                    }


                    //If there is a stegosaur at a location being searched, set it as the target
                    if (currentLocation.containsAnActor()) {
                        if (currentLocation.getActor().hasCapability(dinosaurType.STEGOSAUR)
                        && !currentLocation.getActor().hasCapability(status.WASATTACKED)) {
                            there = map.locationOf(currentLocation.getActor());
                            if(minDistance > distance(here, there)){
                                minDistance = distance(here, there);
                                target = currentLocation.getActor();
                                targetLocation = null;

                            }
                        }
                    }

                if (currentLocation.containsAnActor()) {
                    if (currentLocation.getActor().hasCapability(dinosaurType.PTERODACTYL)
                            && !currentLocation.getActor().hasCapability(status.FLYING)) {
                        there = map.locationOf(currentLocation.getActor());
                        if(minDistance > distance(here, there)){
                            minDistance = distance(here, there);
                            target = currentLocation.getActor();
                            targetLocation = null;

                        }
                    }
                }

                    //If there is a corpse at a location being searched, set it as the target
                    if (containsCorpse == true) {
                            if (minDistance > distance(here, currentLocation)){
                                minDistance = distance(currentLocation, here);
                                targetLocation = currentLocation;
                                target = null;
                            }
                    }

                    //If there is an egg at a location being searched, set it as the target
                    if (containsEgg == true) {
                        if (minDistance > distance(here, currentLocation)){
                            minDistance = distance(currentLocation, here);
                            targetLocation = currentLocation;
                            target = null;
                        }
                    }
                }
            }

        //code from follow behaviour allowing the actor to get closer to the target
        if(target != null && distance(map.locationOf(actor),map.locationOf(target)) > 1){
            return new FollowBehaviour(target).getAction(actor, map);
        }

        if(targetLocation != null && distance(map.locationOf(actor),targetLocation) > 0){
            return new TerrainGoToBehaviour(targetLocation).getAction(actor, map);
        }

        //If the actor is next to the allosaur, the allosaur attacks or eats the actor
        if (target != null && !target.hasCapability(status.WASATTACKED) && distance(map.locationOf(actor), map.locationOf(target)) <= 3){
            Action attack = new AttackAction(target);
            if(!target.hasCapability(status.WASATTACKED)){
                actor.heal(20);
                return attack;
            }
            return attack;
        }

        if (targetLocation != null && distance(map.locationOf(actor),targetLocation) == 0){
            for(Item item: targetLocation.getItems()){
                if(item.hasCapability(dinosaurType.STEGOSAUR)){
                    actor.heal(50);
                    System.out.println("Allosaur eats from Stegosaur corpse");
                    targetLocation.removeItem(item);
                    return new DoNothingAction();
                }

                if(item.hasCapability(dinosaurType.BRACHIOSAUR)){
                    actor.heal(100);
                    System.out.println("Allosaur eats from Brachiosaur corpse");
                    targetLocation.removeItem(item);
                    return new DoNothingAction();
                }

                if(item.hasCapability(itemCapable.EGG)){
                    actor.heal(10);
                    System.out.println("Allosaur eats an egg");
                    targetLocation.removeItem(item);
                    return new DoNothingAction();
                }

            }
        }


        return new DoNothingAction();
    }
    private int distance(Location a, Location b){return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());}
}

