package org.objects;

import org.game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Box extends SuperObject{

    GamePanel gp;
    public OBJ_Box(GamePanel gp) {

        name = "Box";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/box.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
