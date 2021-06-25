import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

public class Alien extends Rectangle{
    String path = System.getProperty("user.dir") + "\\";

    SpriteSheetManager ss;
    int timercounter;

    private Thread thread;

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
    boolean dead;

    Timer timer;


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
        if(type == EASY && !dead){
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

    public void death() {
        ss = new SpriteSheetManager("explosionSheet.png");
        ss.setSpriteSheetDimensions(5120, 2048);
        ss.setSpriteDimensions(1024, 1024);
        ss.setNumberOfSprites();
        dead = true;

        timercounter = 1;
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pic = ss.getSprite(timercounter);
                timercounter++;
            }
        },0, 150);
    }
}
