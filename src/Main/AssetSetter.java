package Main;

import object.OBJ_Box;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Sneakers;

// Создание класса для обработки размещения объектов
public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    // Метод для получения(загрузки) объектов
    public void setObject(){

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 28 * gp.tileSize;
        gp.obj[0].worldY = 15 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 28 * gp.tileSize;
        gp.obj[1].worldY = 16 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 28 * gp.tileSize;
        gp.obj[2].worldY = 17 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 29 * gp.tileSize;
        gp.obj[3].worldY = 17 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 29 * gp.tileSize;
        gp.obj[4].worldY = 16 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 29 * gp.tileSize;
        gp.obj[5].worldY = 15 * gp.tileSize;

        gp.obj[6] = new OBJ_Box(gp);
        gp.obj[6].worldX = 30 * gp.tileSize;
        gp.obj[6].worldY = 14 * gp.tileSize;

        gp.obj[7] = new OBJ_Sneakers(gp);
        gp.obj[7].worldX = 31 * gp.tileSize;
        gp.obj[7].worldY = 13 * gp.tileSize;



    }
}
