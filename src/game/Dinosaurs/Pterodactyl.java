package game.Dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviours.*;
import game.Capability.TerrainData;
import game.ImplimentedActions.LayEggAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class which represents a Pterodactyl, a dinosaur which can fly and is carnivorous
 *
 */

public class Pterodactyl extends Dinosaur {
    private Types type;
    private ArrayList<Behaviour> behaviour = new ArrayList<>();
    private int flyCount;
    private TerrainData terrainData;

    public Pterodactyl(String name) {
        super("Pterodactyl", '(', 100, 100);
        this.addCapability(dinosaurType.PTERODACTYL);
        this.addCapability(status.ADULT);
        this.addCapability(status.FLYING);
        this.type = Types.ADULT;
        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thristPoints;
        this.flyCount = 30;
        addBehaviours();

        Random random = new Random();
        int birthGender = random.nextInt(2);
        if (birthGender == 1) {
            this.addCapability(gen.MALE);
        } else {
            this.addCapability(gen.FEMALE);
        }
    }

    public Pterodactyl(String name, String state) {
        super("Pterodactyl", '(', 100, 100);
        this.addCapability(dinosaurType.PTERODACTYL);
        if (state == "ADULT") {
            this.addCapability(status.ADULT);
            this.type = Types.ADULT;
        }

        if (state == "BABY") {
            this.addCapability(status.BABY);
            this.type = Types.BABY;
        }
        this.hitPoints = type.hitPoints;
        this.thirstPoints = type.thristPoints;
        this.name = type.name;
        this.age = type.age;
        this.addCapability(status.FLYING);
        this.flyCount = 30;

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
    public Action behaviourLogic(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(status.DRINKING)) {
            this.removeCapability(status.DRINKING);
            this.hydrate(30);
        }

        if (this.hasCapability(status.TAKEOFF)) {
            this.removeCapability(status.TAKEOFF);
            this.addCapability(status.FLYING);
            this.flyCount = 30;
        }


        if (this.flyCount == 0) {
            if (map.locationOf(this).getGround().hasCapability(terrainData.LAKE)
                    || map.locationOf(this).getGround().hasCapability(terrainData.WALL)) {
                System.out.println("Pterodactyl at " + '(' + map.locationOf(this).x() +',' + map.locationOf(this).y()+')' + " Has crashed and died");
                map.removeActor(this);
                return new DoNothingAction();

            } else {
                this.removeCapability(status.FLYING);
                return behaviour.get(6).getAction(this, map);
            }
        }

        if (this.hitPoints < 40){
            return behaviour.get(7).getAction(this,map);
        }


        if (this.thirstPoints < 40) {
            Action findWater = behaviour.get(5).getAction(this, map);
            if (findWater != null) {
                return findWater;
            }
        }


        if (this.hasCapability(status.FLYING)) {
            this.flyCount--;
        }

        if (this.hitPoints >= 90 && this.thirstPoints > 60) {
            this.addCapability(status.BREEDREADY);
        }
        if (this.hasCapability(status.BREEDREADY) && this.hitPoints >= 50) {
            return behaviour.get(1).getAction(this, map);
        }


            return new WanderBehaviour().getAction(this, map);
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

        if (this.age == 30 && this.hasCapability(status.BABY)) {
            mature(this, map);
        }

        if (!this.isConscious()) {
            this.consciousCounter ++;
            return unconscious(this, map, 20, this.consciousCounter);

        }

        if (this.hasCapability(status.PREGNANT)) {
            this.pregnantTimer++;
            Action pregnantStage = pregnancy(this, this.pregnantTimer, map);
            if (pregnantStage instanceof LayEggAction) {
                this.pregnantTimer = 0;
                return pregnantStage;
            }
        }

        return behaviourLogic(actions, lastAction, map, display);
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
    public void addBehaviours() {
        //0 = wander (fly)
        behaviour.add(new WanderBehaviour());
        //1 = Breeding
        behaviour.add(new PterodactylBreedingBehaviour());
        //2 = Unconciousness
        behaviour.add(new UnconciousBehaviour());
        //3 = Pregnant
        behaviour.add(new PregnantBehaviour(false));
        //4 = Finished Pregnancy
        behaviour.add(new PregnantBehaviour(true));
        //5 = Thirst behaviour
        behaviour.add(new ThirstyBehaviour());
        //6 = Find liftoff spot
        behaviour.add(new FindLaunchPlaceBehaviour());
        //7 = find corpse or lake to eat from
        behaviour.add(new PterodactylFindFoodBehaviour());

    }

    @Override
    public void mature(Actor actor, GameMap map) {
        super.mature(actor, map);
        this.type = Pterodactyl.Types.ADULT;
        this.name = Pterodactyl.Types.ADULT.name;

    }

    @Override
    public Action unconscious(Actor actor, GameMap map, int timeUnconcious, int consciousCounter) {
        return super.unconscious(actor, map, timeUnconcious, consciousCounter);
    }
    /***
     * A list of enumerable types of dinosaur with each of their own properties
     */
    enum Types {
        BABY("Pterodactyl Baby", '(', 10, 0, 30),
        ADULT("Pterodactyl", '(', 50, 30, 50);


        private ArrayList<Behaviour> behaviour = new ArrayList<>();
        private int hitPoints;
        private String name;
        private int age;
        private int thristPoints;


        Types(String name, char displayChar, int hitPoints, int age, int thristPoints) {
            this.hitPoints = hitPoints;
            this.name = name;
            this.age = age;
            this.thristPoints = thristPoints;
        }

    }
}
