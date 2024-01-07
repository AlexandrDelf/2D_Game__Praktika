package org.game;

import org.entity.NPC_npcMan;
import org.objects.OBJ_Box;
import org.objects.OBJ_Door;
import org.objects.OBJ_Key;
import org.objects.OBJ_Sneakers;

// AssetSetter отвечает за размещение объектов в игровом мире.
// Он загружает объекты из файлов ресурсов и устанавливает их на игровую карту.
public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    // Метод для получения(загрузки) объектов
    public void setObject(){

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 12 * gp.tileSize;
        gp.obj[0].worldY = 9 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 48 * gp.tileSize;
        gp.obj[1].worldY = 52 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 15 * gp.tileSize;
        gp.obj[2].worldY = 40 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 49 * gp.tileSize;
        gp.obj[3].worldY = 38 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 12 * gp.tileSize;
        gp.obj[4].worldY = 12 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 49 * gp.tileSize;
        gp.obj[5].worldY = 46 * gp.tileSize;

        gp.obj[6] = new OBJ_Box(gp);
        gp.obj[6].worldX = 49 * gp.tileSize;
        gp.obj[6].worldY = 48 * gp.tileSize;

        gp.obj[7] = new OBJ_Sneakers(gp);
        gp.obj[7].worldX = 10 * gp.tileSize;
        gp.obj[7].worldY = 52 * gp.tileSize;
    }

    // Метод размещения npc
    public void setNPC() {

        gp.npc[0] = new NPC_npcMan(gp); // создание экземпляра
        gp.npc[0].worldX = gp.tileSize*33; // размещение на карте по x
        gp.npc[0].worldY = gp.tileSize*20; // размещение на карте по y
    }
}
