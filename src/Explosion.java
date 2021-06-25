import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Explosion extends Rectangle implements ActionListener {
    String path = System.getProperty("user.dir") + "\\";
    Image pic = Toolkit.getDefaultToolkit().getImage(path + "explosion.gif");
    boolean destroy = false;
    Timer timer = new Timer(1000, this);

    public Explosion (int x, int y){
        this.x = x;
        this.y = y;
        width = 80;
        height = 80;
        timer.start();
    }
    public void draw(Graphics g, Component c){
        //g.drawImage(Toolkit.getDefaultToolkit().getImage(path + "explosion.gif"),this.x,this.y,width, height, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        destroy = true;
    }
}
