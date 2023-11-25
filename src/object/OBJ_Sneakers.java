package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Sneakers extends SuperObject{

    GamePanel gp;

    public OBJ_Sneakers(GamePanel gp) {

        name = "Sneakers";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/sneakers.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}