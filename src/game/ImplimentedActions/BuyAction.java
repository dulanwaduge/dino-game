package game.ImplimentedActions;

import edu.monash.fit2099.engine.*;
import game.Capability.Buyables;
import game.Capability.ItemCapable;
import game.Capability.Status;
import game.Dinosaurs.Dinosaur;
import game.Items.*;
import game.Player;


/**
 * A modified form of the PickupItemAction that allows the player to make a purchase from the vending machine
 */
public class BuyAction extends Action {
    private Buyables buyables;
    private Item item;
    private Status status;
    private ItemCapable itemCapable;
    private int payed;

    /**
     * A constructor for a buy action
     * @param item the item that can be bought
     */

    //Get the item price
    public BuyAction(Item item){
        this.item = item;

        if (this.item.hasCapability(status.ISFRUIT)){
            this.payed = 100;
        }

        if (this.item.hasCapability(itemCapable.LASERGUN)) {
            this.payed = 500;
        }

        if (this.item.hasCapability(itemCapable.CARNMEALKIT)) {
            this.payed = 500;
        }

        if (this.item.hasCapability(itemCapable.VEGMEALKIT)) {
            this.payed = 100;
        }

        if (this.item.hasCapability(itemCapable.BRACHEGG)) {
            this.payed = 500;
        }

        if (this.item.hasCapability(itemCapable.STEGEGG)) {
            this.payed = 200;
        }

        if (this.item.hasCapability(itemCapable.ALLOEGG)) {
            this.payed = 1000;
        }

        if (this.item.hasCapability(itemCapable.PTEROEGG)) {
            this.payed = 200;
        }

        if (this.item.hasCapability(itemCapable.WATERBOTTLE)) {
            this.payed = 50;
        }


    }


    /**
     * This method allows player to buy any item from the vending machine if the player has enough ecopoints to purchase an item
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        if (this.item.hasCapability(status.ISFRUIT)){
            if (Player.ecoPoints >= 100) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-100);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.LASERGUN)) {
            if (Player.ecoPoints >= 500) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    payed = 500;
                    Player.pay(-500);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.CARNMEALKIT)) {
            if (Player.ecoPoints >= 500) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-500);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.VEGMEALKIT)) {
            if (Player.ecoPoints >= 100) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-100);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.BRACHEGG)) {
            if (Player.ecoPoints >= 500) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-500);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.STEGEGG)) {
            if (Player.ecoPoints >= 200) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-200);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.ALLOEGG)) {
            if (Player.ecoPoints >= 1000) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-1000);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.PTEROEGG)) {
            if (Player.ecoPoints >= 200) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-200);
                }
                return menuDescription(actor);
            }
        }

        if (this.item.hasCapability(itemCapable.WATERBOTTLE)) {
            if (Player.ecoPoints >= 50) {
                actor.addItemToInventory(this.item);
                if (actor instanceof Player) {
                    Player.pay(-50);
                }
                return menuDescription(actor);
            }
        }

        return "Insufficient funds";
    }

    /**
     *
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + this.item + " for " + "$"+this.payed;
    }
}
