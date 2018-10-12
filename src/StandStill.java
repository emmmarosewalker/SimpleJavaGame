import bos.NoMove;
import bos.RelativeMove;

import java.util.ArrayList;

public class StandStill implements Behaviour {
    @Override
    public ArrayList<RelativeMove> chooseMove(Stage stage, Character mover) {
        ArrayList<RelativeMove> moves = new ArrayList<>();
        moves.add(new NoMove(stage.grid, mover));

        return moves;
    }
}
