import java.awt.*;
import java.util.List;
import java.util.Optional;
import bos.*;

public class Sheep extends Character {

    public Sheep(Cell location) {
        super(location);
        display = Optional.of(Color.WHITE);
    }

    public RelativeMove aiMove(Stage stage){
        List<RelativeMove> movesToShepherd = stage.grid.movesBetween(this.location, stage.shepherd.location, this);
        if (movesToShepherd.size() == 0)
            return new NoMove(stage.grid, this);
        else
            return movesToShepherd.get(0);
    }

}