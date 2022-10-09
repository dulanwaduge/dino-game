package game.Dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviours.*;
import game.Capability.DinosaurType;
import game.ImplimentedActions.LayEggAction;
import game.Items.Corpse;

import java.util.ArrayList;
import java.util.Random;


/**
 * A class which represents a Brachiosaur, a herbivorous dinosaur
 *
 */
public class Brachiosaur extends Dinosaur {
    private ArrayList<Behaviour> behaviour = new ArrayList<>();
    private DinosaurType dinosaurType;
    private Types type;

    /***
     *
     * Constructor.
     * All Brachiosaurs are represented by a 'B' and have 160 hit points.
     *
     * @param name the name of this Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'B', 160, 200);
        this.addCapability(dinosaurType.BRACHIOSAUR);
        this.type = Brachiosaur.Types.ADULT;
        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thirstPoints;
        this.addCapability(status.MALE);
        this.age = type.age;
        this.addCapability(status.ADULT);

        Random random = new Random();
        int birthGender = random.nextInt(2);
        if (birthGender == 1) {
            this.addCapability(gen.MALE);
        } else {
            this.addCapability(gen.FEMALE);
        }
        addBehaviours();
    }

    /**
     * Seperate constructor which allows for a choice of the state the dinosaur is in
     *
     * @param name
     * @param state
     */
    public Brachiosaur(String name, String state) {
        super(name, 'B', 160, 200);
        if (state == "ADULT") {
            this.type = Brachiosaur.Types.ADULT;
            this.addCapability(status.ADULT);
        }
        if (state == "BABY") {
            this.type = Brachiosaur.Types.BABY;
            this.addCapability(status.BABY);
        }


        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thirstPoints;
        this.name = type.name;
        this.age = type.age;
        this.addCapability(dinosaurType.BRACHIOSAUR);

        Random random = new Random();
        int birthGender = random.nextInt(2);
        if (birthGender == 1) {
            this.addCapability(gen.MALE);
        } else {
            this.addCapability(gen.FEMALE);
        }

        addBehaviours();


    }

    @Override
    public void addBehaviours() {
        //0 = wander
        behaviour.add(new WanderBehaviour());
        //1 = findFood
        behaviour.add(new HerbivoreFindFoodBehaviour());
        //2 = breeding
        behaviour.add(new BreedingBehaviour());
        //3 = Unconciousness
        behaviour.add(new UnconciousBehaviour());
        //4 = Pregnant
        behaviour.add(new PregnantBehaviour(true));
        //5 = Finished Pregnancy
        behaviour.add(new PregnantBehaviour(false));
        //6 = Thirst behaviour
        behaviour.add(new ThirstyBehaviour());
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return super.getAllowableActions(otherActor, direction, map);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(status.DRANKWATERBOTTLE)) {
            this.removeCapability(status.DRANKWATERBOTTLE);
            hydrate(1000);
        }
        hurt(1);
        hydrate(-1);
        this.age++;

        if (!this.isConscious()) {
            this.consciousCounter++;
            return unconscious(this, map, 20, this.consciousCounter);
        }


        if (this.age == 30 && this.hasCapability(status.BABY)) {
            mature(this, map);
        }

        if (this.hasCapability(status.PREGNANT)) {
            this.pregnantTimer++;
            Action pregnantStage = pregnancy(this, this.pregnantTimer, map);
            if (pregnantStage instanceof LayEggAction) {
                this.pregnantTimer = 0;
                return pregnantStage;
            }
        }

        return (behaviourLogic(actions, lastAction, map, display));

    }


    @Override
    public void heal(int points) {
        super.heal(points);
    }

    @Override
    public void hurt(int points) {
        super.hurt(points);
    }

    //additional class methods_______________________________________________________________________________

    /**
     * governs usual dinosaur behaviours and how a dinosaur should interact with the environment depending on its attributes
     *
     * @param actions
     * @param lastAction
     * @param map
     * @param display
     * @return
     */
    public Action behaviourLogic(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(status.DRINKING)) {
            this.removeCapability(status.DRINKING);
            this.hydrate(80);
        }

        if (this.hitPoints >= 140 && this.thirstPoints > 100) {
            if (!this.hasCapability(status.BREEDREADY)) {
                this.addCapability(status.BREEDREADY);
                Action wander = behaviour.get(0).getAction(this, map);
                if (wander != null) {
                    return wander;
                }
            }
        }

        if (this.hitPoints >= 100 && this.hasCapability(status.BREEDREADY)) {
            Action breed = behaviour.get(2).getAction(this, map);
            if (breed != null) {
                return breed;
            }
        }

        if (this.thirstPoints < 40) {
            Action findWater = behaviour.get(6).getAction(this, map);
            if (findWater != null) {
                return findWater;
            }
        }

        if (this.hitPoints < 140) {
            Action findFood = behaviour.get(1).getAction(this, map);
            if (findFood != null) {
                return findFood;
            }
        }
        return new WanderBehaviour().getAction(this, map);
    }


    /**
     * performs the logic for an unconcious dinosaur
     *
     * @param map the game map
     * @return
     */
    @Override
    public Action unconscious(Actor actor, GameMap map, int timeUnconcious, int consciousCounter) {
        return super.unconscious(actor, map, timeUnconcious, consciousCounter);
    }

    @Override
    public Action pregnancy(Actor actor, int pregnantTimer, GameMap map) {
        return super.pregnancy(actor, pregnantTimer, map);
    }

    /**
     * enables the dinosaur to mature into an adult
     */
    @Override
    public void mature(Actor actor, GameMap map) {
        super.mature(actor, map);
        this.type = Brachiosaur.Types.ADULT;
        this.name = Brachiosaur.Types.ADULT.name;
    }
    //_______________________________________________________________________________

    /**
     * A list of enumerable types of dinosaur with each of their own properties
     */
    enum Types {
        BABY("Brachiosaur Baby", 'B', 10, 0, 60),
        ADULT("Brachiosaur", 'B', 140, 30, 100);


        private ArrayList<Behaviour> behaviour = new ArrayList<>();
        private int hitPoints;
        private String name;
        private int age;
        private int thirstPoints;


        Types(String name, char displayChar, int hitPoints, int age, int thirstPoints) {
            this.hitPoints = hitPoints;
            this.name = name;
            this.age = age;
            this.thirstPoints = thirstPoints;
        }

    }
}
