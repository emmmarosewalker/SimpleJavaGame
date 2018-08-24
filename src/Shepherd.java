import java.awt.*;

public class Shepherd extends Character {

    public Shepherd(Cell location) {
        super(location);
        display = java.util.Optional.of(Color.GREEN);
    }

}