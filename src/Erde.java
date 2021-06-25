import java.awt.*;

public class Erde extends Rectangle{
    String path = System.getProperty("user.dir") + "\\";
    Image pic;

    public Erde(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 300;
        this.height = 300;
        pic = Toolkit.getDefaultToolkit().getImage(path + "earth.gif");
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, this.x, this.y, 300,300, c);
        return;
    }
}
