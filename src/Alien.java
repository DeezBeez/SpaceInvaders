import java.awt.*;

public class Alien extends Rectangle {
    String path = System.getProperty("user.dir") + "\\";

    static int EASY = 1;
    static int NORMAL = 2;
    static int HARD = 3;
    static int EXTREME = 4;

    static int UP = 1;
    static int DOWN = 2;
    static int RIGHT = 3;
    static int LEFT = 4;

    int type;
    Image pic;
    int direction = 0;
    int speed;
    int health;

    public Alien(int x, int y, int type, int health){
        this.health = health;
        this.type = type;
        this.x = x;
        this.y = y;

        if(type == EASY){
            width = 120;
            height = 120;
            direction = RIGHT;
            speed = 5;
            pic = Toolkit.getDefaultToolkit().getImage(path + "alienspaceship.gif");
        }
    }
    public void draw(Graphics g, Component c){
        g.drawImage(pic, this.x, this.y, width, height, c);
        return;
    }
    public void move(){
        if(type == EASY){
            if(direction == RIGHT){
                x = x + speed;
                if(x > 1780){
                    direction = LEFT;
                    y = y + 120;
                    x = 1780;
                    return;
                }
            }
            if(direction == LEFT) {
                x = x - speed;
                if (x < 0) {
                    direction = RIGHT;
                    y = y + 120;
                    x = 0;
                    return;
                }
            }
        }
    }
    public void death(){
        pic = Toolkit.getDefaultToolkit().getImage(path + "explosion.gif");
    }
}
