package game.Terrain;

import edu.monash.fit2099.engine.*;
import game.Capability.Status;
import game.Capability.TerrainData;
import game.Items.Fruit;
import game.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class which represents a tree
 */
public class Tree extends Ground {
    private ArrayList<Integer> ages = new ArrayList<>();
    private int age;
    private int groundedFood;
    private int treeFood;
    private Status status;
    private TerrainData terrainData;

    public Tree() {
        super('+');
        this.addCapability(terrainData.TREE);
    }


    @Override
    public void tick(Location location) {
        super.tick(location);

        groundedFood = 0;
        treeFood = 0;
        for (Item item : location.getItems()) {
            if (item.hasCapability(status.STEGOSAUREDIBLE)) {
                groundedFood++;
            }
        }

        for (Item item : location.getItems()) {
            if (item.hasCapability(status.BRACHIOSAUREDIBLE)) {
                treeFood++;
            }
        }

        age++;
        if (age == 10)
            displayChar = 't';
        if (age == 20)
            displayChar = 'T';

        produceFruit(location);

        fruitFall(location);

        adjacentTree(location);

        if (this.treeFood == 0) {
            this.removeCapability(status.FEEDBRACHIOSAUR);
        }

        if (this.groundedFood == 0) {
            this.removeCapability(status.FEEDSTEGOSAUR);
        }


        if (this.groundedFood == 0 && this.treeFood == 0) {
            this.removeCapability(status.HASFRUIT);
        }

        discardFruit(location);


    }

    /***
     * Produces fruits on a tree
     *
     */
    private boolean produceFruit(Location location) {
        Random rand = new Random();
        int growFruit = rand.nextInt(2);

        if (growFruit == 1) {
            Fruit f = new Fruit("fruit", 'F', false);
            if (!this.hasCapability(status.FEEDBRACHIOSAUR)) {
                this.addCapability(status.FEEDBRACHIOSAUR);
            }

            if (!this.hasCapability(status.HASFRUIT)) {
                this.addCapability(status.HASFRUIT);
            }
            f.addCapability(status.ISFRUIT);
            f.addCapability(status.BRACHIOSAUREDIBLE);
            location.addItem(f);
            Player.ecoPoints += 1;
            return true;
        }
        return false;
    }

    /***
     * drops a fruit from a tree
     *
     */
    private void fruitFall(Location location) {
        if (treeFood == 0) {
            return;
        }
        Random rand = new Random();
        int fall = rand.nextInt(10);
        if (fall == 1) {
            Fruit f = new Fruit("fruit", 'F', true);
            for (int i = 0; i < location.getItems().size(); i++) {
                if (location.getItems().get(i).hasCapability(status.BRACHIOSAUREDIBLE)) {
                    location.removeItem(location.getItems().get(i));
                    if (!this.hasCapability(status.FEEDSTEGOSAUR)) {
                        this.addCapability(status.FEEDSTEGOSAUR);
                    }
                    f.addCapability(status.STEGOSAUREDIBLE);
                    f.addCapability(status.ISFRUIT);
                    f.addCapability(status.ROTTABLE);
                    location.addItem(f);
                    return;
                }
            }


        }

    }


    /**
     * A function which determines if any fruit off the ground has rotten
     *
     * @param location
     */
    private void discardFruit(Location location) {
        if (location.getItems().size() == 0) {
            return;
        }
        int incrementer = 0;
        while (incrementer < location.getItems().size()) {
            Item currentItem = location.getItems().get(incrementer);
            if (currentItem.hasCapability(status.ISFRUIT)
                    && currentItem.hasCapability(status.ROTTABLE)) {
                if (currentItem.hasCapability(status.ROTTEN)) {
                    location.removeItem(currentItem);
                }
            }
            incrementer++;
        }
    }

    /**
     * Allows for a check to see if a tree has any adjacent trees. Aids in finding a suitable
     * place for the Pterodactyls to breed
     */
    private void adjacentTree(Location currentLocation) {
        for (Exit exit : currentLocation.getExits()) {
            if (exit.getDestination() != currentLocation &&
                    exit.getDestination().getGround().hasCapability(terrainData.TREE)) {
                this.addCapability(terrainData.ADJTREE);
                return;
            }
        }
        if (this.hasCapability(terrainData.ADJTREE)) {
            this.removeCapability(terrainData.ADJTREE);
        }
    }


    @Override
    public void addCapability(Enum<?> capability) {
        super.addCapability(capability);
    }

    @Override
    public void removeCapability(Enum<?> capability) {
        super.removeCapability(capability);
    }

    @Override
    public boolean hasCapability(Enum<?> capability) {
        return super.hasCapability(capability);
    }
}

