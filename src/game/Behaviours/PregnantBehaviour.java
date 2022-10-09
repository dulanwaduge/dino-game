package game.Behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import game.ImplimentedActions.LayEggAction;


/**
 * Decides how a pregnant dinosaur behaves
 */
public class PregnantBehaviour implements Behaviour{
    private boolean finishedPregancy;

    public PregnantBehaviour(Boolean finishedPregnancy){
        this.finishedPregancy = finishedPregnancy;
    }
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if(finishedPregancy){
            return new LayEggAction();
        }
        return new DoNothingAction();
    }
}
