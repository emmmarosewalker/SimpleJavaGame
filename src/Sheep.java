import java.awt.*;

public class Sheep extends Character {

    public Sheep(Cell location) {
        super(location);
        display = java.util.Optional.of(Color.WHITE);
    }

}