import bos.*;
import java.awt.*;
import java.util.Optional;

public class Shepherd extends Character {

    public Shepherd(Cell location) {
        super(location);
        display = Optional.of(Color.GREEN);
    }
    public RelativeMove aiMove(Stage stage){
        return new NoMove(stage.grid, this);
    }

}