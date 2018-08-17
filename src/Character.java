import java.awt.*;

public abstract class Character {
    Color display;
    Cell location;

    public Character(Cell location){
        this.location = location;
        this.display = Color.DARK_GRAY;
    }

    public  void paint(Graphics g){
        g.setColor(display);
        g.fillOval(location.x + location.width/4, location.y+ location.height/4, location.width/2, location.height/2);
    }
}
