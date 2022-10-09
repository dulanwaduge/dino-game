package game.Dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviours.PregnantBehaviour;
import game.Behaviours.UnconciousBehaviour;
import game.Capability.DinosaurType;
import game.Capability.Gender;
import game.Capability.Status;
import game.Items.Corpse;


/**
 * An abstract class from which dinosaur actors inherit their abilities
 *
 */
public abstract class Dinosaur extends Actor {
    protected int age;
    protected int pregnantTimer;
    protected DinosaurType dinosaurType;
    protected Status status;
    protected int consciousCounter;
    protected Gender gen;
    protected int thirstPoints;
    protected int maxThirstPoints;

    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return super.getIntrinsicWeapon();
    }

    public Dinosaur(String name, char displayChar, int hitPoints, int thirstPoints) {
        super(name, displayChar, hitPoints);
        this.maxThirstPoints = thirstPoints;

    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return super.getAllowableActions(otherActor, direction, map);
    }

    /**
     * governs usual dinosaur behaviours and how a dinosaur should interact with the environment depending on its attributes
     *
     * @param actions    A list of possible actions the dinosaur can make
     * @param lastAction The last action the dinosaur made
     * @param map        The game map
     * @param display    The menu display for all actions
     * @return an action for the dinosaur to do
     */
    public Action behaviourLogic(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    public void corpseDespawn(Actor actor, GameMap map) {
    }

    @Override
    public void heal(int points) {
        super.heal(points);
    }

    @Override
    public void hurt(int points) {
        super.hurt(points);
    }

    /**
     * Adds all dinosaur behaviours to a behaviour arrayList
     */
    public void addBehaviours() {
    }

    /**
     * enables the dinosaur to mature into an adult
     */
    public void mature(Actor actor, GameMap map) {
        actor.removeCapability(status.BABY);
        actor.addCapability(status.ADULT);
        System.out.println(actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' + " has matured into an adult");
    }

    /**
     * Hydrates the dinosaur for positive points values
     * removes thirst points for negative point values
     *
     * @param points
     */
    public void hydrate(int points) {
        this.thirstPoints += points;
        if (this.thirstPoints > maxThirstPoints) {
            this.thirstPoints = maxThirstPoints;
        }
    }

    @Override
    public boolean isConscious() {
        return hitPoints > 0 && thirstPoints > 0;
    }



    /**
     * performs the logic for an unconcious dinosaur
     *
     * @param map the game map
     * @return
     */
    public Action unconscious(Actor actor, GameMap map, int timeUnconcious, int consciousCounter) {
        consciousCounter++;
        ;
        if (this.hasCapability(status.BABY)) {
            this.removeCapability(status.BABY);
        }
        if (this.hasCapability(status.ADULT)) {
            this.removeCapability(status.ADULT);
        }
        if (this.consciousCounter == timeUnconcious) {
            Corpse corpse = new Corpse("dead " + this.name, '%');
            if (actor.hasCapability(dinosaurType.ALLOSAUR)) {
                corpse.addCapability(dinosaurType.ALLOSAUR);
            }

            if (actor.hasCapability(dinosaurType.STEGOSAUR)) {
                corpse.addCapability(dinosaurType.STEGOSAUR);
            }

            if (actor.hasCapability(dinosaurType.BRACHIOSAUR)) {
                corpse.addCapability(dinosaurType.BRACHIOSAUR);
            }

            if (actor.hasCapability(dinosaurType.PTERODACTYL)) {
                corpse.addCapability(dinosaurType.PTERODACTYL);
            }

            System.out.println(deathString(this, map));
            map.locationOf(actor).addItem(corpse);
            map.removeActor(actor);
        }

        return new UnconciousBehaviour().getAction(actor, map);

    }

    /**
     * The pregnancy function governs how a pregnant dinosaur acts
     * @param actor the actor that is pregnant
     * @param pregnantTimer the time spent pregnant
     * @param map the map on which the pregnancy is occuring
     * @return
     */
    public Action pregnancy(Actor actor, int pregnantTimer, GameMap map){
        if (pregnantTimer == 30) {
            actor.removeCapability(status.PREGNANT);
            if (actor.hasCapability(gen.FEMALE)) {
                return new PregnantBehaviour(true).getAction(actor, map);
            }
            return new PregnantBehaviour(false).getAction(actor, map);
        }
        return new PregnantBehaviour(false).getAction(actor, map);
    }

    /**
     *
     * @param actor the actor that has died
     * @param map the map they died on
     * @return A string about an actor's death
     */
    public String deathString(Actor actor, GameMap map) {
        return actor + " at " + '(' + map.locationOf(actor).x() +',' + map.locationOf(actor).y()+')' + " has died";
    }
}

