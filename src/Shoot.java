import java.awt.*;

public class Shoot extends Rectangle {
    String path = System.getProperty("user.dir") + "\\";

    int type;
    Image pic;
    public int speed;

    static int LEVEL1 = 1;
    static int LEVEL2 = 1;
    static int LEVEL3 = 1;
    static int LEVEL4 = 1;
    static int LEVEL5 = 1;
    int WIDTH = 50;
    int HEIGHT = 50;
    public Shoot(int x, int y){
        this.x = x;
        this.y = y;
        width = WIDTH;
        height = HEIGHT;
        speed = 20;
        pic = Toolkit.getDefaultToolkit().getImage(path + "missile.gif");
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, this.x, this.y, width, height, c);
        return;
    }
}
