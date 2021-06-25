import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Vector;

public class SpaceInvaders extends JPanel implements Runnable, ActionListener, KeyListener{
    JFrame fenster;
    Player player;
    Image explosion;
    Image background;
    Image erde;
    Image earthbackground;
    Image selection;
    String path;
    private Timer timer;
    int delay;
    private long lastTime;
    private double fps;

    //Keystates
    Boolean uppressed = false;
    Boolean downpressed = false;
    Boolean leftpressed = false;
    Boolean rightpressed = false;

    //Gamestates Init
    int level;
    static int ERDE = 1;
    Boolean startscreen = true;
    Boolean levelselection = false;

    //Gameobjects
    Erde erdeObj;
    Vector<Shoot> missiles;
    Vector<Alien> aliens;
    Vector<Explosion> explosions;

    //NPC
    Alien alien;

    public static void main(String[] args)
    {
        new SpaceInvaders();
    }

    public SpaceInvaders(){
        //Init
        path = System.getProperty("user.dir") + "\\";

        //Images
        background = new ImageIcon(path + "sibackground.gif").getImage();
        erde = new ImageIcon(path + "earth.gif").getImage();
        earthbackground = new ImageIcon(path + "earthbackground.gif").getImage();
        selection = new ImageIcon(path + "Selection.gif").getImage();
        explosion = Toolkit.getDefaultToolkit().getImage(path + "explosion.gif");

        //Vectors
        missiles = new Vector<Shoot>();
        aliens = new Vector<Alien>();
        explosions = new Vector<Explosion>();

        //Init
        System.out.println(path);
        fenster = new JFrame("SpaceInvaders");
        fenster.setSize(1920,1080);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        fenster.add(this);
        fenster.setResizable(false);
        fenster.setVisible(true);
        fenster.addKeyListener(this);
        fenster.requestFocusInWindow();
        fenster.setLayout(null);

        delay = 1000;
        timer = new Timer(delay, this);
        timer.start();
        Thread gamethread = new Thread(this);
        gamethread.start();

    }

    protected void paintComponent(Graphics g){
        //Startscreen
        if(startscreen){
            super.paintComponent(g);
            g.drawImage(background,0,0,this);
            g.setColor(Color.RED);
            g.setFont(new Font("Rockwell", Font.PLAIN, 100));
            FontMetrics metrics = g.getFontMetrics();
            g.drawString("SPACE INVADERS",960 - metrics.stringWidth("SPACE INVADERS")/2, 340);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Leelawadee", Font.PLAIN, 30));
            metrics = g.getFontMetrics();
            g.drawString("(PRESS <SPACE> TO PLAY)", 960 - metrics.stringWidth("(PRESS <SPACE> TO PLAY)")/2, 540);
        }

        //Levelselection
        if(levelselection){
            super.paintComponent(g);
            g.drawImage(background,0,0,this);
            FontMetrics metrics;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Leelawadee", Font.BOLD, 30));
            metrics = g.getFontMetrics();
            g.drawString("ERDE", 480 - metrics.stringWidth("ERDE")/2, 378);
            if(erdeObj != null){
                erdeObj.draw(g,this);
            }
            if(player != null){
                player.draw(g,this);
                if(player.intersects(erdeObj)){
                    g.drawImage(selection, 330, 410, 300,300,this);
                }
            }
        }

        //Level Erde
        if(!levelselection && !startscreen && level == ERDE){
            super.paintComponent(g);
            this.setBackground(Color.BLUE);
            g.drawImage(earthbackground,0,0, 1920, 1080,this);
            if(player != null){
                player.draw(g,this);
            }
            Enumeration<Alien> eAlien = aliens.elements();
            while(eAlien.hasMoreElements()){
                eAlien.nextElement().draw(g,this);
            }
            Enumeration<Shoot> eShoot = missiles.elements();
            while (eShoot.hasMoreElements()){
                eShoot.nextElement().draw(g, this);
            }
            Enumeration<Explosion> eExplosion = explosions.elements();
            while(eExplosion.hasMoreElements()){
                Explosion e = eExplosion.nextElement();
                e.draw(g,this);
            }
        }
    }

    public void movethings(){
        Enumeration<Shoot> eShoot = missiles.elements();
        while (eShoot.hasMoreElements()){
            Shoot m = eShoot.nextElement();
            m.y = m.y - 20;
            if(m.y < 0){
                missiles.remove(m);
            }
        }
        Enumeration<Alien> eAlien = aliens.elements();
        while (eAlien.hasMoreElements()){
            Alien a = eAlien.nextElement();
            a.move();
            Enumeration<Shoot> eShoot2 = missiles.elements();
            while (eShoot2.hasMoreElements()){
                Shoot m2 = eShoot2.nextElement();
                if(a.intersects(m2)) {
                    aliens.remove(a);
                    missiles.remove(m2);
                    explosions.addElement(new Explosion(a.x,a.y));
                    explosions.lastElement().x = explosions.lastElement().x - (explosions.lastElement().width - a.width)/2;
                    explosions.lastElement().y = explosions.lastElement().y - (explosions.lastElement().height - a.height)/2;
                }
            }
        }
        Enumeration<Explosion> eExplosion = explosions.elements();
        while(eExplosion.hasMoreElements()){
            Explosion e = eExplosion.nextElement();
            if(e.destroy == true){
                explosions.remove(e);
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        aliens.addElement(new Alien(0,0, Alien.EASY, 1));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Playermovement
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.direction = Player.hoch;
            uppressed = true;

        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            player.direction = Player.runter;
            downpressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            player.direction = Player.links;
            leftpressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            player.direction = Player.rechts;
            rightpressed = true;
        }

        //Shooting
        if(level != 0 && e.getKeyCode() == KeyEvent.VK_SPACE){
            missiles.addElement(new Shoot(player.x, player.y - 20));
            missiles.lastElement().x = missiles.lastElement().x - (missiles.lastElement().width - player.width)/2;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Startscreen
        if(startscreen && e.getKeyCode() == KeyEvent.VK_SPACE){
            startscreen = false;
            levelselection = true;
            erdeObj = new Erde(330,410);
            player = new Player(810,800);
        }

        //Level Selection
        //Erde
        if(levelselection && player.intersects(erdeObj) && e.getKeyCode() == KeyEvent.VK_SPACE){
            startscreen = false;
            levelselection = false;
            level = ERDE;
            player.x = 810;
            player.y = 800;
            aliens.addElement(new Alien(0,0, Alien.EASY,1));
        }

        //Playermovement

        if(e.getKeyCode() == KeyEvent.VK_W){
            uppressed = false;
            if(player.direction == Player.hoch){
                player.direction = 0;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            downpressed = false;
            if(player.direction == Player.runter){
                player.direction = 0;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftpressed = false;
            if(player.direction == Player.links){
                player.direction = 0;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightpressed = false;
            if(player.direction == Player.rechts){
                player.direction = 0;
            }
        }
        if(!uppressed && !downpressed && !leftpressed && !rightpressed){
            player.direction = 0;
        }
        if(player.direction == 0 && uppressed){
            player.direction = Player.hoch;
        }
        if(player.direction == 0 && downpressed){
            player.direction = Player.runter;
        }
        if(player.direction == 0 && leftpressed){
            player.direction = Player.links;
        }
        if(player.direction == 0 && rightpressed){
            player.direction = Player.rechts;
        }
    }

    @Override
    public void run() {
        while(true){
            movethings();
            if (player != null && levelselection || player != null && level != 0){
                player.move();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e){
                System.out.println("NEEEEIN!!! Der Thread mag dich nicht! >:c");
            }
            repaint();
        }
    }
}
