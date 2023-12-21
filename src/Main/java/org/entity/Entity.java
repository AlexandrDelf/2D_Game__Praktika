package org.entity;

import org.game.GamePanel;
import org.game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

//Родительский класс для класса Player и любых других классов персонажей
public class Entity {

	GamePanel gp;
	public int worldX, worldY; // Позиция в мировых координатах: worldX, worldY
	public int speed; // Скорость передвижения

	// Набор спрайтов для анимации движения в разных направлениях.
	public BufferedImage up1, up2, up3, up4, up5, up6,
			down1, down2, down3, down4, down5, down6,
			left1, left2, left3, left4, left5, left6,
			right1, right2, right3, right4, right5, right6;

	public String direction; // Положение

	// Анимация движения, переход от спрайта к спрайту
	public int spriteCounter = 0; // Переменная счётчика
	public int spriteNum = 1; // Переменная текущего спрайта

	// Граница для тайла персонажа
	// Область столкновения объекта типа Rectangle
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Класс создаёт абстрактный прямоугольник

	public int solidAreaDefaultX, solidAreaDefaultY; // Координаты этой области по умолчанию.
	public boolean collisionOn = false; // Флаг регистрации столкновений collisionOn
	public int actionLockCounter = 0; // сброс счётчика

	// Массив строк dialogues размером 20 элементов
	String[] dialogues = new String[20];
	int dialogIndex = 0;



	// Конструктор для абстрактной сущности
	public Entity (GamePanel gp) {

		this.gp = gp;
	}

	public void setAction() {
	}

	// Метод для диалогов npc с игроком
	public void speak() {

			// После последнего по индексу диалога переходит к первому по индексу
			if (dialogues[dialogIndex] == null) {
				dialogIndex = 0;
			}
			gp.ui.currentDialog = dialogues[dialogIndex];
			dialogIndex++;

			// В зависимости от направления игрока устанавливается направление
			// Пример - если направление игрока вверх, то направление будет вниз
			switch (gp.player.direction) {
				case "up" -> direction = "down";
				case "down" -> direction = "up";
				case "left" -> direction = "right";
				case "right" -> direction = "left";
			}
	}

	// Метод обновления игрового объекта
	public void update() {

		setAction(); // вызов метода для установки текущего действия (описан в классе npcMan)

		collisionOn = false; //  флаг отслеживающий столкновения в false перед проверкой столкновений

		// Проверяет, столкнулся ли этот объект с какими-либо плитками на карте, устанавливает collisionOn в true если да
		gp.cCheck.checkTile(this);

		// Проверяет, столкнулся ли этот объект с другими игровыми объектами, значение false означает,
		// что этот объект не должен "уничтожаться" если произойдёт столкновение.
		gp.cCheck.checkObject(this, false);

		gp.cCheck.checkPlayer(this); // Проверяет, столкнулся ли этот объект конкретно с игроком.

		// Проверяется collisionOn - если столкновений не было,
		// обновит позицию в игровом мире в соответствии с направлением движения.
		if(!collisionOn) {

			switch (direction) {
				case "up" -> worldY -= speed;
				case "down" -> worldY += speed;
				case "left" -> worldX -= speed;
				case "right" -> worldX += speed;
			}
		}
		// Счётчик спрайтов, условие для смены спрайтов
		spriteCounter++;

		if (spriteCounter > 6) { // Скорость смены спрайтов на кадр
			if(spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 4;
			} else if (spriteNum == 4) {
				spriteNum = 5;
			} else if (spriteNum == 5) {
				spriteNum = 6;
			} else if (spriteNum == 6) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	public void draw(Graphics2D g2) {

		// Инициализация переменной image типа BufferedImage
		BufferedImage image = null;

		// Координатами спрайта на экране относительно текущего положения игрока
		int screenX = worldX - gp.player.worldX + gp.player.screenX; // вычисляются координаты screenX
		int screenY = worldY - gp.player.worldY + gp.player.screenY; // вычисляются координаты screenY

		// Условие, которое проверяет, находится ли спрайт в пределах видимости игрока
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			// Перебирает варианты направления движения npc, для выбора нужного спрайта
			switch (direction) {
				case "up" -> {
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
					if (spriteNum == 3) {
						image = up3;
					}
					if (spriteNum == 4) {
						image = up4;
					}
					if (spriteNum == 5) {
						image = up5;
					}
					if (spriteNum == 6) {
						image = up6;
					}
				} // Выход из условия
				case "down" -> {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
					if (spriteNum == 3) {
						image = down3;
					}
					if (spriteNum == 4) {
						image = down4;
					}
					if (spriteNum == 5) {
						image = down5;
					}
					if (spriteNum == 6) {
						image = down6;
					}
				}
				case "left" -> {
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
					if (spriteNum == 3) {
						image = left3;
					}
					if (spriteNum == 4) {
						image = left4;
					}
					if (spriteNum == 5) {
						image = left5;
					}
					if (spriteNum == 6) {
						image = left6;
					}
				}
				case "right" -> {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
					if (spriteNum == 3) {
						image = right3;
					}
					if (spriteNum == 4) {
						image = right4;
					}
					if (spriteNum == 5) {
						image = right5;
					}
					if (spriteNum == 6) {
						image = right6;
					}
				}
			}

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

	// Метод осуществляет загрузку графических ресурсов в игру с предобработкой
	public BufferedImage setup(String imagePath) {

		UtilityTool uTool = new UtilityTool(); // вспомогательный объект для масштабирования
		BufferedImage image = null; // объявляется переменная для хранения загруженного изображения

		// В try блоке происходит чтение изображения из папки ресурсов по переданному имени
		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath +".png")));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize); // вызывается масштабирование изображения под размер тайла карты

		}catch(IOException e) {
			e.printStackTrace(); // в случае ошибки выводим traceback
		}
		return image; // возвращаем загруженное и масштабированное изображение
	}

}

