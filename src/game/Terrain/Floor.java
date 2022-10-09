package game.Terrain;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.Capability.DinosaurType;
import game.Capability.Status;

import java.util.ArrayList;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {
	private DinosaurType dinosaurType;


	public Floor() {
		super('_');
	}

}
