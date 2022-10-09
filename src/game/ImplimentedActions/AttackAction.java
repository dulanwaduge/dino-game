package game.ImplimentedActions;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.Capability.DinosaurType;
import game.Dinosaurs.Dinosaur;
import game.Items.Corpse;
import game.Items.PortableItem;
import game.Capability.Status;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {
	private Status status;
	private DinosaurType dinosaurType;

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			this.target.addCapability(status.WASATTACKED);
			return actor + " misses " + target + ".";

		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		if(target.hasCapability(dinosaurType.PTERODACTYL)){
			damage = 1000;
			target.hurt(damage);
			actor.heal(100);
		}

		target.hurt(damage);


		if (!target.isConscious()) {
			if(target.hasCapability(dinosaurType.STEGOSAUR)) {
				Corpse corpse = new Corpse("dead " + target, '%');
				corpse.addCapability(dinosaurType.STEGOSAUR);
				map.locationOf(target).addItem(corpse);
			}

			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
