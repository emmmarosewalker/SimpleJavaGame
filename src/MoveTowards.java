import bos.NoMove;
import bos.RelativeMove;

import java.util.ArrayList;
import java.util.List;

public class MoveTowards implements Behaviour {
    Character target;

    public MoveTowards(Character character){
        this.target = character;
    }


    @Override
    public ArrayList<RelativeMove> chooseMove(Stage stage, Character mover) {
        ArrayList<RelativeMove> movesToTarget = new ArrayList<>();
        movesToTarget.addAll(stage.grid.movesBetween(mover.location,target.location, mover));

        if (movesToTarget.size() == 0) {
            //movesToTarget = new ArrayList<>();
            movesToTarget.add(new NoMove(stage.grid, mover));
        }
        return movesToTarget;
    }



}
