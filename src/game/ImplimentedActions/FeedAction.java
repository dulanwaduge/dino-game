package game.ImplimentedActions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.Capability.ItemCapable;
import game.Capability.Status;
import game.Player;

/**
 * A class which allows the player to feed a dinosaur
 */
public class FeedAction extends Action {
    protected Actor target;
    protected Item item;
    private ItemCapable itemCapable;
    private Status status;

    /**
     * constructor for a feeding action
     * @param target the target dinosaur
     * @param item the item which it is to be fed
     */
    public FeedAction(Actor target, Item item){
        this.target = target;
        this.item = item;

    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (item.hasCapability(status.ISFRUIT)) {
            target.heal(20);
            actor.removeItemFromInventory(item);
            Player.ecoPoints += 10;
            return menuDescription(actor);
        }

        if (item.hasCapability(itemCapable.VEGMEALKIT)){
            target.heal(1000);
            actor.removeItemFromInventory(item);
            Player.ecoPoints += 10;
            return menuDescription(actor);
        }

        if (item.hasCapability(itemCapable.CARNMEALKIT)){
            target.heal(1000);
            actor.removeItemFromInventory(item);
            Player.ecoPoints += 10;
            return menuDescription(actor);
        }

        if (item.hasCapability(itemCapable.WATERBOTTLE)){
            target.addCapability(status.DRANKWATERBOTTLE);
            actor.removeItemFromInventory(item);
            Player.ecoPoints += 10;
            return menuDescription(actor);
        }



        return null;

    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds "+ item +" to " + target;
    }

}
