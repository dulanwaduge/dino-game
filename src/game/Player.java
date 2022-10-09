package game;

import edu.monash.fit2099.engine.*;
import game.Behaviours.Behaviour;
import game.Capability.*;
import game.Dinosaurs.Pterodactyl;
import game.Dinosaurs.Stegosaur;
import game.ImplimentedActions.*;
import game.Items.*;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Class representing the Player.
 */

public class Player extends Actor {


    private Menu menu = new Menu();
    private Behaviour behaviour;
    private Buyables buyables;
    private Status status;
    private DinosaurType dinosaurType;
    private ItemCapable itemCapable;
    private int fruitInitial = 0;
    private int fruitFinal = 0;
    private GameEnvironment gameEnvironment;
    public static int ecoPoints = 0;
    public static int playerTurns;

    public static int specifiedMoves;
    public static int targetEcoPoints;


    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return super.getAllowableActions(otherActor, direction, map);
    }

    @Override
    public Weapon getWeapon() {
        for (Item item : this.inventory) {
            if (item.hasCapability(itemCapable.LASERGUN)) {
                return new LaserGun("LaserGun", '}', 80, "zaps");
            }
        }
        return null;
    }

    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return super.getIntrinsicWeapon();
    }

    /**
     * Action method
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // Handle multi-turn Actions
        System.out.println("___________________________");
        System.out.println("EcoPoints: " + Player.ecoPoints);
        System.out.println("___________________________");

        harvestProfit();

        dinosaurInteract(map, actions);

        if ((map.locationOf(this).y() == 0 && map.locationOf(this).map() == Application.gameMap)
                || (map.locationOf(this).y() == map.getYRange().max() && map.locationOf(this).map() == Application.gameMap2)) {
            actions.add(new GoToNewMapAction());
        }

        if (this.hasCapability(gameEnvironment.MAPSWITCH)) {
            this.removeCapability(gameEnvironment.MAPSWITCH);
            switchMap(map);
            return new DoNothingAction();
        }


        playerTurns++;

        if (Player.specifiedMoves >= Player.playerTurns && Player.targetEcoPoints <= Player.ecoPoints) {
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.println("************** YOU WON! ****************");
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.exit(0);
        } else if (Player.playerTurns > Player.specifiedMoves && Player.ecoPoints < Player.targetEcoPoints) {
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.println("************** YOU LOST! ****************");
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.exit(0);
        }


        //If the player is on the vending machine, we use Buy action to replace PickupItemAction
        for (Item item : map.locationOf(this).getItems()) {
            if (item.hasCapability(itemCapable.VENDINGMACHINE)) {
                Action purchaseChoice = purchase();
                if (!(purchaseChoice instanceof DoNothingAction)) {
                    return purchaseChoice;
                }

            }
        }

        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();
        return menu.showMenu(this, actions, display);
    }

    /**
     * Assesses whether the player is in acting range of a dinosaurs
     *
     * @param map
     */
    public void dinosaurInteract(GameMap map, Actions actions) {
        for (int i = (map.locationOf(this).x() - 1); i < (map.locationOf(this).x() + 2); i++) {
            for (int j = (map.locationOf(this).y() - 1); j < (map.locationOf(this).y() + 2); j++) {
                if ((i <= map.getXRange().max() && i >= map.getXRange().min()) && (j <= map.getYRange().max() && j >= map.getYRange().min())) {
                    Location currentLocation = map.at(i, j);
                    if (currentLocation.containsAnActor()) {
                        if (currentLocation.getActor().hasCapability(dinosaurType.STEGOSAUR) ||
                                (currentLocation.getActor().hasCapability(dinosaurType.BRACHIOSAUR))) {
                            for (Item item : this.inventory) {
                                if (item.hasCapability(status.ISFRUIT)) {
                                    actions.add(new FeedAction(currentLocation.getActor(), item));
                                }
                                if (item.hasCapability(itemCapable.VEGMEALKIT)) {
                                    actions.add(new FeedAction(currentLocation.getActor(), item));
                                }
                                if (item.hasCapability(itemCapable.LASERGUN)) {
                                    actions.add(new AttackAction(currentLocation.getActor()));
                                }
                                if (item.hasCapability(itemCapable.WATERBOTTLE)) {
                                    actions.add(new FeedAction(currentLocation.getActor(), item));
                                }
                            }
                        }
                        if (currentLocation.containsAnActor()) {
                            if (currentLocation.getActor().hasCapability(dinosaurType.ALLOSAUR)) {
                                for (Item item : this.inventory) {
                                    if (item.hasCapability(itemCapable.CARNMEALKIT)) {
                                        actions.add(new FeedAction(currentLocation.getActor(), item));
                                    }

                                    if (item.hasCapability(itemCapable.LASERGUN)) {
                                        actions.add(new AttackAction(currentLocation.getActor()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Getter for ecoPoints
     *
     * @return amount of ecoPoints returned
     */
    public int getEcoPoints() {
        return ecoPoints;
    }

    /**
     * Setter for ecoPoints
     *
     * @param ecoPoints sets the amount of ecoPoints
     */
    public void setEcoPoints(int ecoPoints) {

    }

    public void switchMap(GameMap map) {
        if (map.locationOf(this).map() == Application.gameMap) {
            int xPosition = map.locationOf(this).x();
            Application.gameMap.removeActor(this);
            Application.gameMap2.at(xPosition, map.getYRange().max()).addActor(this);
        } else if (map.locationOf(this).map() == Application.gameMap2) {
            int xPosition = map.locationOf(this).x();
            Application.gameMap2.removeActor(this);
            Application.gameMap.at(xPosition, 0).addActor(this);
        }


    }


    public static void pay(int points) {
        ecoPoints += points;
    }


    /**
     * if a player harvests fruit from a tree or bush, it gains 10 ecopoints. This
     * method checks to see if a player harvested fruit on the previous turn
     */


    public void harvestProfit() {
        for (Item item : this.inventory) {
            if (item.hasCapability(status.ISFRUIT)) {
                this.fruitFinal++;
            }

            if (this.fruitFinal > fruitInitial) {
                this.pay(10);
            }
            fruitInitial = fruitFinal;
        }
    }

    //TESTING PURPOSE
    public int getSpecifiedMoves() {
        return specifiedMoves;
    }

    public void setSpecifiedMoves(int specifiedMoves) {
        this.specifiedMoves = specifiedMoves;
    }

    private Action purchase() {
        System.out.println("Purchase Menu: ");
        System.out.println("1) Purchase LaserGun ($500)");
        System.out.println("2) Purchase Fruit ($100)");
        System.out.println("3) Purchase Vegitarian MealKit ($100)");
        System.out.println("4) Purchase Carnivore MealKit ($500)");
        System.out.println("5) Purchase Stegosaur Egg ($200)");
        System.out.println("6) Purchase Brachiosaur Egg ($500)");
        System.out.println("7) Purchase Allosaur Egg ($1000)");
        System.out.println("8) Purchase Pterodactyl Egg ($200)");
        System.out.println("9) Purchase Water Bottle ($50)");
        System.out.println("10) Exit Menu");

        Scanner input = new Scanner(System.in);
        System.out.println("Select an option:");
        String choice = input.nextLine();


        while (Integer.parseInt(choice) != 10) {

            if (Integer.parseInt(choice) == 1) {
                LaserGun laserGun = new LaserGun("LaserGun", '}', 80, "zaps");
                laserGun.addCapability(itemCapable.LASERGUN);
                return new BuyAction(laserGun);
            }

            if (Integer.parseInt(choice) == 2) {
                Fruit fruit = new Fruit("fruit", 'F', true);
                fruit.addCapability(status.ISFRUIT);
                return new BuyAction(fruit);
            }

            if (Integer.parseInt(choice) == 3) {
                VegetarianMealKit vegetarianMealKit = new VegetarianMealKit("VegetarianMealKit", 'v', true, 100);
                vegetarianMealKit.addCapability(itemCapable.VEGMEALKIT);
                return new BuyAction(vegetarianMealKit);
            }

            if (Integer.parseInt(choice) == 4) {
                CarnivoreMealKit carnivoreMealKit = new CarnivoreMealKit("CarnivoreMealKit", 'c', true, 500);
                carnivoreMealKit.addCapability(itemCapable.CARNMEALKIT);
                return new BuyAction(carnivoreMealKit);
            }

            if (Integer.parseInt(choice) == 5) {
                StegosaurEgg stegosaurEgg = new StegosaurEgg("StegosaurEgg", '0', true, 1);
                stegosaurEgg.addCapability(itemCapable.STEGEGG);
                return new BuyAction(stegosaurEgg);
            }

            if (Integer.parseInt(choice) == 6) {
                BrachiosaurEgg brachiosaurEgg = new BrachiosaurEgg("BrachiosaurEgg", '0', true, 1);
                brachiosaurEgg.addCapability(itemCapable.BRACHEGG);
                return new BuyAction(brachiosaurEgg);
            }

            if (Integer.parseInt(choice) == 7) {
                AllosaurEgg allosaurEgg = new AllosaurEgg("AllosaurEgg", '0', true, 1);
                allosaurEgg.addCapability(itemCapable.ALLOEGG);
                return new BuyAction(allosaurEgg);
            }

            if (Integer.parseInt(choice) == 8) {
                PterodactylEgg pterodactylEgg = new PterodactylEgg("PterodactylEgg", '0', true, 1);
                pterodactylEgg.addCapability(itemCapable.PTEROEGG);
                return new BuyAction(pterodactylEgg);
            }

            if (Integer.parseInt(choice) == 9) {
                WaterBottle waterBottle = new WaterBottle("Water Bottle", '8', true);
                return new BuyAction(waterBottle);
            }

            if (Integer.parseInt(choice) == 10) {
                return new DoNothingAction();
            }

        }
        return new DoNothingAction();

    }


}
