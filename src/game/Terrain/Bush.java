package game.Terrain;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Items.Fruit;
import game.Terrain.Dirt;

import java.util.Random;

/**
 * A class which represents a bush
 */
public class Bush extends Ground {
    private int fruitCount;
    private boolean stegFood = false;
    private boolean bracFood = false;
    private Status status;
    private DinosaurType dinosaurType;

    /**
     * Constructor for the bush
     */
    public Bush() {
        super('^');
    }

    @Override
    public void tick(Location location) {
        growFruit(location);

        this.fruitCount = 0;
        for (int i=0; i < location.getItems().size(); i++){
            if (location.getItems().get(i).hasCapability(status.ISFRUIT)){
                this.fruitCount++;
            }

        }

        if (this.fruitCount == 0){
            this.removeCapability(status.HASFRUIT);
            this.removeCapability(status.FEEDSTEGOSAUR);
        }

        stomped(location);

    }


    /***
     * grows fruit on a bush and adds it to the onBush
     *
     */
    private void growFruit(Location location) {
        Random rand = new Random();
        int grow = rand.nextInt(11);
        if (grow == 1) {
            Fruit f = new Fruit("fruit", 'F', true);
            f.addCapability(status.STEGOSAUREDIBLE);
            f.addCapability(status.ISFRUIT);
            location.addItem(f);
            if (!this.hasCapability(status.HASFRUIT)){
                this.addCapability(status.HASFRUIT);
            }

            if (!this.hasCapability(status.FEEDBRACHIOSAUR)) {
                this.addCapability(status.FEEDSTEGOSAUR);
            }
            this.fruitCount++;
            return;


        }

    }




    /***
     *
     * Gives a 50% chance of a bush reverting to dirt if stepped on by a brachiosaurus
     * @param location
     */
    private void stomped(Location location){
        if (location.containsAnActor() && (location.getActor().hasCapability(dinosaurType.BRACHIOSAUR))){
            Random rand = new Random();
            int stomp = rand.nextInt(2);
            if (stomp == 0){
                Dirt dirt = new Dirt();
                location.setGround(dirt);
            }


        }

    }

}
