package game.Dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviours.*;
import game.ImplimentedActions.LayEggAction;
import game.Items.Corpse;

import java.util.ArrayList;
import java.util.Random;


/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
    private ArrayList<Behaviour> behaviour = new ArrayList<>();
    private Types type;
    private int attackRefresh;


    /***
     *
     * Constructor.
     * All Stegosaurs are represented by a 'd' and have 100 hit points.
     *
     * @param name the name of this Stegosaur
     */
    public Stegosaur(String name) {
        super(name, 'd', 100, 50);
        this.type = Types.ADULT;
        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thirstPoints;
        this.maxThirstPoints = 100;
        this.age = type.age;
        this.attackRefresh = 0;
        this.addCapability(dinosaurType.STEGOSAUR);
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
    public Stegosaur(String name, String state) {
        super(name, 'd', 100, 100);
        addBehaviours();
        if (state == "ADULT") {
            this.type = Types.ADULT;
            this.addCapability(status.ADULT);
        }
        if (state == "BABY") {
            this.type = Types.BABY;
            this.addCapability(status.BABY);
        }

        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thirstPoints;
        this.name = type.name;
        this.age = type.age;
        this.addCapability(dinosaurType.STEGOSAUR);

        Random random = new Random();
        int birthGender = random.nextInt(2);
        if (birthGender == 1) {
            this.addCapability(gen.MALE);
        } else {
            this.addCapability(gen.FEMALE);
        }


    }

    /**
     * Adds all the dinosaur's behaviours to an arraylist
     */
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
        behaviour.add(new PregnantBehaviour(false));
        //5 = Finished Pregnancy
        behaviour.add(new PregnantBehaviour(true));
        //6 = Thirst behaviour
        behaviour.add(new ThirstyBehaviour());

    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return super.getAllowableActions(otherActor, direction, map);
    }


    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(status.DRANKWATERBOTTLE)) {
            this.removeCapability(status.DRANKWATERBOTTLE);
            hydrate(1000);
        }

        hurt(1);
        this.age++;
        hydrate(-1);

        if (this.hasCapability(status.WASATTACKED)) {
            this.attackRefresh++;
            if (attackRefresh == 20) {
                this.removeCapability(status.WASATTACKED);
                this.attackRefresh = 0;
            }
        }

        if (!this.isConscious()) {
            this.consciousCounter ++;
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

    @Override
    public boolean isConscious() {
        return super.isConscious();
    }


    //additional class methods_______________________________________________________________________________
    public Action behaviourLogic(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(status.DRINKING)) {
            this.removeCapability(status.DRINKING);
            this.hydrate(30);
        }

        if (this.hitPoints >= 90 && this.thirstPoints > 60) {
            if (!this.hasCapability(status.BREEDREADY)) {
                this.addCapability(status.BREEDREADY);
                Action wander = behaviour.get(0).getAction(this, map);
                if (wander != null) {
                    return wander;
                }
            }
        }
        if (this.hitPoints >= 50 && this.hasCapability(status.BREEDREADY)) {
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

        if (this.hitPoints < 90) {
            Action findFood = behaviour.get(1).getAction(this, map);
            if (findFood != null) {
                return findFood;
            }
        }
        return new WanderBehaviour().getAction(this, map);
    }


    @Override
    public Action unconscious(Actor actor, GameMap map, int timeUnconcious, int consciousCounter) {
        return super.unconscious(actor, map, timeUnconcious, consciousCounter);
    }

    @Override
    public void mature(Actor actor, GameMap map) {
        super.mature(actor, map);
        this.type = Types.ADULT;
        this.name = Stegosaur.Types.ADULT.name;
    }


    //______________________________________________________________________________________________________________

    //enum class that establishes values for all states of the stegosaur

    /**
     * A list of enumerable types of dinosaur with each of their own properties
     */
    enum Types {
        BABY("Stegosaur Baby", 'd', 10, 0, 30),
        ADULT("Stegosaur", 'd', 40, 30, 50);


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

