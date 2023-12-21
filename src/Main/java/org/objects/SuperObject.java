package org.objects;

import org.game.GamePanel;
import org.game.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

// Определяет общие свойства игрового объекта на карте для последующего наследования конкретными типами объектов
public class SuperObject {

    public BufferedImage image; // изображение объекта
    public String name; // имя, может использоваться для задания типа
    public boolean collision = false; // установка флага наличия коллизии с указанным значением
    public  int worldX, worldY; // координаты объекта на игровой карте мира
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // прямоугольная область для проверки столкновений

    // Задают смещение зоны проверки столкновений относительно изображения
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool(); // вспомогательный класс для масштабирования изображений


    // В методе draw() происходит отрисовка объекта в экранных координатах с учетом смещения камеры игрока
    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }

}
