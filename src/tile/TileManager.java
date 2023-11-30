package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp; // Использование методов рисования из игровой панели
    public Tile[] tile; //
    public int[][] mapTileNum; //Создание переменной массива;

    // Конструктор
    public TileManager(GamePanel gp) {

        this.gp = gp;

        // Количество видов плитки
        tile = new Tile[99];

        //Создание экземпляра MapTileNum
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        // Вызов метода получения плитки из конструктора
        getTileImage();

        // Вызов метода загрузки карты
        loadMap("/maps/world02.txt");
    }

    // Метод для получения(загрузки) плитки
    public void getTileImage() {

            // placeholder
            setup(0, "grass00", false);

            setup(1, "grass00", false);

            setup(2, "grass00", false);

            setup(3, "grass00", false);

            setup(4, "grass00", false);

            setup(5, "grass00", false);

            setup(6, "grass00", false);

            setup(7, "grass00", false);

            setup(8, "grass00", false);

            setup(9, "grass00", false);
            // placeholder

            // Плитки травы
            setup(10, "grass00", false);
            setup(11, "grass01", false);
            setup(12, "grass02", false);
            setup(13, "grass03", false);
            setup(14, "grass04", false);
            setup(15, "grass05", false);
            setup(16, "grass06", false);
            setup(17, "grass07", false);

            // Плитка цветов
            setup(18, "flowers00", false);
            setup(19, "flowers01", false);

            // Плитка кусты
            setup(20, "bush00", true);
            setup(21, "bush01", true);

            // Плитка деревья
            setup(22, "tree00", true);
            setup(23, "tree01", true);
            setup(24, "tree02", true);

            // Плитка воды
            setup(30, "water00", true);
            setup(31, "waterEdge1", true);
            setup(32, "waterEdge2", true);
            setup(33, "waterEdge3", true);
            setup(34, "waterEdge4", true);
            setup(35, "waterCornerOut1", true);
            setup(36, "waterCornerOut2", true);
            setup(37, "waterCornerOut3", true);
            setup(38, "waterCornerOut4", true);
            setup(39, "waterCornerIn1", true);
            setup(40, "waterCornerIn2", true);
            setup(41, "waterCornerIn3", true);
            setup(42, "waterCornerIn4", true);

            // Плитка поверхностей
            setup(80, "asphalt00", false);



    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile (); // Экземпляр новой плитки
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName +".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

   // Загрузка карты
   // Текстовое описание карты разбирается и загружается в программный массив. В дальнейшем по этому массиву отрисовываются тайлы карты
   public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath); // Считывает файл карты в поток InputStream
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Считываем построчно данные при помощи BufferedReader

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine(); // Чтение одной строки map01.txt, помещает её в строковую line

                while(col < gp.maxWorldCol) {
                String[] numbers = line.split(" "); // Разделяет строку на отдельные tileId при помощи метода split()

                int num = Integer.parseInt(numbers[col]); // Парсим tileId в integer число при помощи метода parseInt()

                mapTileNum[col][row] = num; // Записывает это число как номер тайла в массив карты mapTileNum по координатам col и row
                col++;
            }
            // Переходим к следующему tileId в строке и следующей строке циклами
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        br.close();

        } catch(Exception e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }
   }

    //  Метод рисования, отвечает за отрисовку игрового поля в 2D игре
    //  Отрисовка видимой игроку части карты на экране в зависимости от его координат
    public void draw(Graphics2D g2) {

        // Создание переменных и цикла while для автоматизации процесса рисования плитки
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow]; // Получает номер тайла в массиве карты mapTileNum по координатам.

            // Вычисляем мировые координаты тайла и экранные относительно игрока.
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Проверяем, попадает ли тайл в видимую игроком область экрана
            // Если да - отрисовываем его изображение в соответствующих экранных координатах
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            // Инкрементируем счётчики тайлов и переходим к следующему

            worldCol ++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow ++;

            }
        }

    }
}
