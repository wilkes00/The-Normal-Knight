package entidad;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heart {
    BufferedImage fullHeart, halfHeart, emptyHeart;
    GamePanel gP;
    public Heart(GamePanel gP) {
        this.gP = gP;
        try {
            this.fullHeart = ImageIO.read(getClass().getResourceAsStream("/resources/spritesjugador/heart_full.png"));
            this.halfHeart = ImageIO.read(getClass().getResourceAsStream("/resources/spritesjugador/heart_half.png"));
            this.emptyHeart = ImageIO.read(getClass().getResourceAsStream("/resources/spritesjugador/heart_empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getFullHeart() {
        return this.fullHeart;
    }
    public BufferedImage getHalfHeart() {
        return this.halfHeart;
    }
    public BufferedImage getEmptyHeart() {
        return this.emptyHeart;
    }


}
