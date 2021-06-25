import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheetManager {
    String path = System.getProperty("user.dir") + "\\";
    private BufferedImage spriteSheet;
    private Image sprite;
    private int sprite_width;
    private int sprite_height;
    private int spritesheet_width;
    private int spritesheet_height;
    private int x = 0;
    private int y = 0;
    private int maxNum = 0;

    public SpriteSheetManager() {
        spriteSheet = null;
    }

    public SpriteSheetManager(String file) {
        setSpriteSheet(file);
    }

    public void setSpriteSheet(String file){
        try {
            spriteSheet = ImageIO.read(new File(path + file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSpriteSheetDimensions(int w, int h){
        spritesheet_width = w;
        spritesheet_height = h;
    }

    public void setSpriteDimensions(int w, int h){
        sprite_width = w;
        sprite_height = h;
    }

    public void setNumberOfSprites(){
        maxNum+= (spritesheet_height/sprite_height) * (spritesheet_width/sprite_width);
    }

    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }

    public Image getSprite(int num){
        if( (x*sprite_width) > (spritesheet_width - sprite_width) ){
            x = 0; y++;
        }
        if(num%maxNum == 0 && num != 0){x=0; y=0;}
        sprite = spriteSheet.getSubimage(x*sprite_width, y*sprite_height, sprite_width, sprite_height);
        x++;
        return sprite;
    }
}
