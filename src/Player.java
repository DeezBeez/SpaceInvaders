import java.awt.*;

public class Player extends Rectangle{
    String path = System.getProperty("user.dir") + "\\";

    int direction;
    int speed = 15;
    static int links = 1;
    static int rechts = 2;
    static int hoch = 3;
    static int runter = 4;
    Image playerpic;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 120;
        this.height = 120;
        playerpic = Toolkit.getDefaultToolkit().getImage(path + "player.gif");
    }

    public void move(){
        if(direction == hoch){
            y = y - speed;
            if(y < 0){
                y = 0;
            }
        }
        if(direction == runter){
            y = y + speed;
            if(y > 960){
                y = 960;
            }
        }
        if(direction == links){
            x = x - speed;
            if(x < 0){
                x = 0;
            }
        }
        if(direction == rechts){
            x = x + speed;
            if(x > 1780){
                x = 1780;
            }
        }
    }

    public void draw(Graphics g, Component c){
        g.drawImage(playerpic, this.x, this.y, 120,120, c);
        return;
    }
}
