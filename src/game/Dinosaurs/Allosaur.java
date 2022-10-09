package game.Dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Attacks.Bite;
import game.Attacks.Scratch;
import game.Attacks.TailWhip;
import game.Behaviours.*;
import game.ImplimentedActions.LayEggAction;
import game.Items.Corpse;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class which represents an Allosaur, a carnivorous dinosaur
 *
 */
public class Allosaur extends Dinosaur {
    private ArrayList<Behaviour> behaviour = new ArrayList<>();
    private Types type;

    /***
     *
     * Constructor.
     * All Allosaurs are represented by an 'A' and have 100 hit points.
     *
     * @param name the name of this Allosaur
     */
    public Allosaur(String name) {
        super(name, 'A', 100, 100);
        this.type = Allosaur.Types.ADULT;
        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thirstPoints;
        this.maxThirstPoints = 100;
        this.age = type.age;
        this.addCapability(dinosaurType.ALLOSAUR);
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
    public Allosaur(String name, String state) {
        super(name, 'A', 100, 100);
        if (state == "ADULT") {
            this.type = Allosaur.Types.ADULT;
        }
        if (state == "BABY") {
            this.type = Allosaur.Types.BABY;
        }

        if (state == "CORPSE") {
            this.type = Allosaur.Types.BABY;
        }
        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thirstPoints;
        this.name = type.name;
        this.age = type.age;
        this.addCapability(dinosaurType.ALLOSAUR);

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

    /***
     * Randomly selects from a variety of attacks which attack the allosaur will perform
     * @return Attack action that the allosaur can perform on a target dinosaur
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        Random rand = new Random();
        int attack = rand.nextInt(11);

        if (attack < 3) {
            return new Bite(45, "bites");
        }

        if (attack > 3 && attack < 10) {
            return new Scratch(20, "scratches");
        }

        if (attack == 10) {
            return new TailWhip(70, "tailwhips");
        }
        return new Bite(10, "bites");
    }

    @Override
    public void addBehaviours() {
        //0 = wander
        behaviour.add(new WanderBehaviour());
        //1 = findFood
        behaviour.add(new HuntBehaviour());
        //2 = Breeding
        behaviour.add(new BreedingBehaviour());
        //3 = unconciousness
        behaviour.add(new UnconciousBehaviour());
        //4 = Pregnant
        behaviour.add(new PregnantBehaviour(false));
        //5 = Finished Pregnancy
        behaviour.add(new PregnantBehaviour(true));
        //6 = Thirst behaviour
        behaviour.add(new ThirstyBehaviour());
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

        Action wander = behaviour.get(0).getAction(this, map);
        if (wander != null) {
            return wander;
        }
        return new DoNothingAction();
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

    /**
     * enables the dinosaur to mature into an adult
     */
    @Override
    public void mature(Actor actor, GameMap map) {
        super.mature(actor, map);
        this.type = Allosaur.Types.ADULT;
        this.name = Allosaur.Types.ADULT.name;
    }
    //_______________________________________________________________________________

    /**
     * A list of enumerable types of dinosaur with each of their own properties
     */
    enum Types {
        BABY("Allosaur Baby", 'A', 20, 0, 30),
        ADULT("Allosaur", 'A', 50, 30,50);

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


