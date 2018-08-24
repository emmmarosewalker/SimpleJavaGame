import java.awt.*;
import java.util.Optional;

import bos.GamePiece;
import java.awt.*;
import java.util.Optional;

public abstract class Character implements GamePiece<Cell> {
    Optional<Color> display;
    Cell location;

    public Character(Cell location){
        this.location = location;
        this.display = Optional.empty();
    }

    public  void paint(Graphics g){
        if(display.isPresent()) {
            g.setColor(display.get());
            g.fillOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
        }
    }

    public void setLocationOf(Cell loc){
        this.location = loc;
    }

    public Cell getLocationOf(){
        return this.location;
    }
}
