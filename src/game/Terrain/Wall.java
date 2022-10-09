package game.Terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.Capability.DinosaurType;
import game.Capability.Status;
import game.Capability.TerrainData;

import java.util.ArrayList;

/**
 * A class which represents a wall object
 *
 */
public class Wall extends Ground {
	private DinosaurType dinosaurType;
	private Status status;
	private TerrainData terrainData;

	public Wall() {
		super('#');
		this.addCapability(terrainData.WALL);
	}

	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}


}
