import bos.*;

import java.awt.*;
import java.util.Optional;
import java.util.List;

public class Wolf extends Character {

    public Wolf(Cell location) {
        super(location);
        display = Optional.of(Color.RED);
    }
    public RelativeMove aiMove(Stage stage){
        List<RelativeMove> movesToSheep = stage.grid.movesBetween(this.location, stage.sheep.location, this);
        if (movesToSheep.size() == 0)
            return new NoMove(stage.grid, this);
        else
            return movesToSheep.get(0);
    }

}