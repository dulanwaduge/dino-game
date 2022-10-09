package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Capability.DinosaurType;
import game.Capability.Status;

/**
 * Allows a herbivore to eat fruit and heal itself
 */
public class HerbivoreEatAction extends Action {
    private DinosaurType dinosaurType;
    private Status status;
    private int fruitEaten;

    @Override
    public String execute(Actor actor, GameMap map) {
        //If the actor is a brachiosaur, then it can eat up to 2 fruits per turn to heal to minimise breed rate
        if (actor.hasCapability(dinosaurType.BRACHIOSAUR)) {
            Location foodLocation = map.locationOf(actor);
            for (int i=0; i < foodLocation.getItems().size(); i++){
                if (foodLocation.getItems().get(i).hasCapability(status.ISFRUIT) &&
                        foodLocation.getItems().get(i).hasCapability(status.BRACHIOSAUREDIBLE)){
                    foodLocation.removeItem(foodLocation.getItems().get(i));
                    actor.heal(5);
                    fruitEaten++;
                    if (fruitEaten == 20){
                        return menuDescription(actor, map);
                    }
                }

            }
            return menuDescription(actor, map);
        }

        //If the actor is a stegosaur, then it can eat up to 8 fruits per turn to heal
        if (actor.hasCapability(dinosaurType.STEGOSAUR)) {
            Location foodLocation = map.locationOf(actor);
            for (int i=0; i < foodLocation.getItems().size(); i++){
                if (foodLocation.getItems().get(i).hasCapability(status.ISFRUIT) &&
                        foodLocation.getItems().get(i).hasCapability(status.STEGOSAUREDIBLE)){
                    foodLocation.removeItem(foodLocation.getItems().get(i));
                    actor.heal(10);
                    fruitEaten++;
                    if (fruitEaten == 8) {
                        return menuDescription(actor, map);
                    }

                }

            }

        }
        return menuDescription(actor, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }


    public String menuDescription(Actor actor, GameMap map) {
        return actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' +  " is enjoying some fruit";
    }
}
