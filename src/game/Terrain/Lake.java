package game.Terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Capability.TerrainData;
import game.Dinosaurs.Dinosaur;
import game.Items.Fish;

import javax.sound.midi.Soundbank;
import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class which represents a lake block
 *
 */

public class Lake extends Ground {
    private double water;
    private int capacity = 25;
    private int turns;
    private Status status;
    private TerrainData terrainData;
    private DinosaurType dinosaurType;
    private ArrayList<Fish> fishList = new ArrayList<>();

    /**
     * Constructor.
     */
    public Lake() {
        super('~');
        this.addCapability(terrainData.LAKE);
    }

    @Override
    public void tick(Location location) {
        rainfall(location);
        currentFish(location);
        turns++;

        if (this.hasCapability(terrainData.SIPPED)) {
            this.removeCapability(terrainData.SIPPED);
            this.capacity--;
        }

        if (this.capacity == 0) {
            if (this.hasCapability(status.HASWATER)) {
                this.removeCapability(status.HASWATER);
            }
        }

        if (!(this.capacity == 0)) {
            if (!this.hasCapability(status.HASWATER)) {
                this.addCapability(status.HASWATER);
            }
        }

        feedPterodactyl(location);
    }

    /**
     * This method implements the rainfall; when the sky rain, water is added to all lakes
     *
     * @param currentLocation
     */
    private void rainfall(Location currentLocation) {
        Random rand = new Random();
        int rain = rand.nextInt(11);

        double random = new Random().nextDouble();
        double rainfallMax = 0.6;
        double rainfallMin = 0.1;

        double randomRain = rainfallMin + (random * (rainfallMax - rainfallMin));

        if (this.turns >= 10) {
            if (rain == 1) {
                water = randomRain * 20;
            }
        }
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(dinosaurType.PTERODACTYL)
                && actor.hasCapability(status.FLYING)) {
            return true;
        }
        return false;
    }

    /**
     * This method implements the number of fish spawned then the game starts
     *
     * @param location
     */
    private void currentFish(Location location) {
        int start = 5;
        for (int i = 0; i < start; i++) {
            Fish f = new Fish("Fish", '$', true);
            fishList.add(f);
            this.addCapability(status.HASFISH);
        }
    }

    /**
     * This method implements new fish being born when the game is being played
     *
     * @param location
     */
    private void newFish(Location location) {
        Random rand = new Random();
        int step = 10;
        int birth = rand.nextInt(6);

        for (int i = 0; i <= 100; i += step) {
            if (birth == 1 && fishList.size() < 25) {
                Fish f = new Fish("Fish", '$', true);
                fishList.add(f);
            } else if (fishList.size() >= 25) {
                return;
            }
        }
    }

    /**
     * Pterodactlys can feed on fish using this method
     * @param location location of the fish
     */
    private void feedPterodactyl(Location location) {
        if (this.hasCapability(status.HASFISH)) {
            if (location.getActor() != null && location.getActor().hasCapability(dinosaurType.PTERODACTYL)) {
                Random rand = new Random();
                if (fishList.size() > 1) {
                    int fishEaten = rand.nextInt(3);
                    for (int i = 0; i < fishEaten; i++) {
                        fishList.remove(0);
                        location.getActor().heal(20);
                    }
                }
                if (fishList.size() == 1) {
                    int fishEaten = rand.nextInt(2);
                    for (int i = 0; i < fishEaten; i++) {
                        fishList.remove(0);
                        location.getActor().heal(20);
                    }

                }
                System.out.println(location.getActor() + " at (" + location.x() + ',' + location.y() + ')' + " is diving for fish");
            }
        }
    }

}
