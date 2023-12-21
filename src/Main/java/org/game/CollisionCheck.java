package org.game;

// класс CollisionCheck содержит метод для проверки столкновений объектов с плитками карты.

import org.entity.Entity;

// Проверка для столкновений
public class CollisionCheck {

    GamePanel gp; // в конструктор передается ссылка на игровую панель для доступа к переменным
    public CollisionCheck(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x; // левая граница объекта entity в мировых координатах
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // правая граница объекта entity в мировых координатах
        int entityTopWorldY = entity.worldY + entity.solidArea.y; // верхняя граница объекта entity в мировых координатах
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; // нижняя граница объекта entity в мировых координатах

        // преобразование координат в номера плиток карты по горизонтали и вертикали
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2; // номера конкретных плиток, которые будут проверяться на наличие препятствия

        // Оператор переключения
        // В каждом из четырех направлений движения проверяются свои плитки на столкновение
        switch (entity.direction) {
            // при движении вверх проверяются плитки сверху от объекта после его предполагаемого смещения
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                // Если хотя бы в одной плитке есть коллизия - устанавливается флаг столкновения
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }
    // Метод столкновений для объектов
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) { // цикл по массиву объектов gp.obj[].

            if(gp.obj[i] != null) { // проверка, что текущий объект не null
                // вычисление координат прямоугольника коллизии entity с учетом смещения относительно позиции
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //  Определение позиции solidArea объекта
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;


                // В зависимости от направления движения происходит смещение прямоугольника entity
                switch (entity.direction) {
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // проверка на пересечение с прямоугольником объекта методом intersects()
                            if(gp.obj[i].collision) { // если есть пересечение, проверка объекта на наличие коллизии и выставление флага у entity.
                                entity.collisionOn = true;
                            }
                            // Проверка, является ли движущимся объектом игрок. Если да - сохраняем индекс объекта.
                            if(player) {
                                index = i;
                            }
                        }
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            // Проверка объекта на твёрдость
                            if(gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            // Проверка игрок ли это
                            if(player) {
                                index = i;
                            }
                        }
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            // Проверка объекта на твёрдость
                            if(gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            // Проверка игрок ли это
                            if(player) {
                                index = i;
                            }

                        }
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            // Проверка объекта на твёрдость
                            if(gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            // Проверка игрок ли это
                            if(player) {
                                index = i;
                            }

                        }
                    }
                }
                // Сброс solidArea объекта, для того чтобы worldX и worldY перестали увеличиваться
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // Метод проверки столкновения для NPC или монстра
    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for(int i = 0; i < target.length; i++) { // цикл по массиву объектов gp.obj[].

            if(target[i] != null) { // проверка, что текущий объект не null
                // вычисление координат прямоугольника коллизии entity с учетом смещения относительно позиции
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //  Определение позиции solidArea объекта
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;


                // В зависимости от направления движения происходит смещение прямоугольника entity
                switch (entity.direction) {
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) { // проверка на пересечение с прямоугольником объекта методом intersects()
                                entity.collisionOn = true;
                                index = i;
                        }
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                            }
                    }
                }
                // Сброс solidArea объекта, для того чтобы worldX и worldY перестали увеличиваться
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {

        // вычисление координат прямоугольника коллизии entity с учетом смещения относительно позиции
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //  Определение позиции solidArea объекта
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;


        // В зависимости от направления движения происходит смещение прямоугольника entity
        switch (entity.direction) {
            case "up" -> {
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) { // проверка на пересечение с прямоугольником объекта методом intersects()
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;
                }
            }
        }
        // Сброс solidArea объекта, для того чтобы worldX и worldY перестали увеличиваться
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
}
