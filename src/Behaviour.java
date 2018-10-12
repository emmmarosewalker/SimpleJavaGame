import bos.RelativeMove;

import java.util.ArrayList;

public interface Behaviour {
    public ArrayList<RelativeMove> chooseMove(Stage stage, Character mover);
}
