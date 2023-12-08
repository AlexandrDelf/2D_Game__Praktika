package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

// Создание класса объекта ключа
// Класс наследуется от родительского SuperObject
public class OBJ_Key extends SuperObject{

    GamePanel gp; // сохраняет ссылку на игровую панель для доступа к переменным и методам
    public OBJ_Key(GamePanel gp) {

        name = "Key";
        try {
            // возвращает поток изображения из папки res
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));

            // масштабирование объекта под размер плитки
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
