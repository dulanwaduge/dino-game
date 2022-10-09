package game.Terrain;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A class that represents bare dirt.
 *
 */
public class Dirt extends Ground {
	private Boolean bushesNear = false;


	public Dirt() {
		super('.');
	}


	@Override
	public void tick(Location location) {
		super.tick(location);
		transformBush(location);

	}


	/***
	 * transforms dirt into a bush with 0.02% chance
	 * @param location
	 */
	private void transformBush(Location location){
		boolean available = gridCheck(location);
		if (available == true && !this.bushesNear){
		Random rand = new Random();
		int growBush = rand.nextInt(1000);
		if (growBush == 0) {
			Bush newBush = new Bush();
			location.setGround(newBush);
		}
		}
		if (available == true && this.bushesNear){
			Random rand = new Random();
			int growBush = rand.nextInt(500);
			if (growBush == 0) {
				Bush newBush = new Bush();
				location.setGround(newBush);
			}
		}
	}

	/***
	 * Checks if a tree exists around the location of the dirt
	 * @param location location around which we check
	 * @return true or false depending on the existence or non-existence of a tree
	 */
	private boolean gridCheck(Location location){
		int bushCount = 0;
		for (Exit exit: location.getExits()){
			Location locationCheck = exit.getDestination();
			if (locationCheck.getGround() instanceof Tree){
				return false;
			}
			if (locationCheck.getGround() instanceof Bush){
				bushCount ++;
			}
			}
		if (bushCount >= 2){
			this.bushesNear = true;
		}
		return true;
	}

}
