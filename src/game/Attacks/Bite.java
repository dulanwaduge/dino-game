package game.Attacks;

import edu.monash.fit2099.engine.IntrinsicWeapon;

/**
 * Represents the bite attack for the Allosaur
 */
public class Bite extends IntrinsicWeapon {
    public Bite(int damage, String verb) {
        super(damage, verb);
    }

    @Override
    public int damage() {
        return super.damage();
    }

    @Override
    public String verb() {
        return super.verb();
    }
}
