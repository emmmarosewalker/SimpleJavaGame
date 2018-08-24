import java.awt.*;

public class Wolf extends Character {

    public Wolf(Cell location) {
        super(location);
        display = java.util.Optional.of(Color.RED);
    }

}