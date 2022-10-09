package game.Items;


import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Dinosaurs.Dinosaur;


/***
 * A unique corpse class from which all corpse functions can run internally
 *
 */
public class Corpse extends PortableItem {
    private Status status;
    private int despawnCounter = 0;
    private DinosaurType dinosaurType;
    private int foodLevel = 100;

    public Corpse(String name, char displayChar) {
        super(name, displayChar);
        this.addCapability(status.CORPSE);
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        this.despawnCounter++;
        despawnCorpse(currentLocation);
        if (this.hasCapability(status.EATENFROM)){
            this.removeCapability(status.EATENFROM);
            this.foodLevel -= 10;
        }
        if (this.foodLevel == 0){
            currentLocation.removeItem(this);
        }
    }


    public void despawnCorpse(Location currentLocation) {
        if (this.hasCapability(dinosaurType.STEGOSAUR)) {
            if (this.despawnCounter == 20) {
                currentLocation.removeItem(this);
            }
        }
        if (this.hasCapability(dinosaurType.BRACHIOSAUR)) {
            if (this.despawnCounter == 40) {
                currentLocation.removeItem(this);
            }
        }
        if (this.hasCapability(dinosaurType.ALLOSAUR)) {
            if (this.despawnCounter == 50) {
                currentLocation.removeItem(this);
            }
        }
        if (this.hasCapability(dinosaurType.PTERODACTYL)) {
            if (this.despawnCounter == 20) {
                currentLocation.removeItem(this);
            }
        }
    }
}
