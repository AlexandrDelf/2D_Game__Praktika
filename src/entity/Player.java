package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;

//Класс Player наследующий  Entity
public class Player extends Entity{
	
	GamePanel gp; // Объявление переменной gp с типом GamePanel. Использование класса GamePanel
	KeyHandler keyH; // Объявление переменной gp с типом KeyHandler. Использование класса управления KeyHandler

	// Создание переменных позиции отрисовки игрока на экране
	public final int screenX;
	public final int screenY;
	public int hasKey = 0; // Число указывающее сколько ключей есть у игрока


	// Метод конструктор для Player с аргументами (GamePanel gp, KeyHandler keyH)
	public Player (GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		// Область столкновения
		// Создание экземпляра абстрактного прямоугольника созданного в Entity и передача параметров в него
		solidArea = new Rectangle();
		solidArea.x = 12;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 16;
		solidArea.height = 16;

		
		setDefaultValues(); // Вызов метода для значений по умолчанию
		getPlayerImage(); // Вызов метода ддя получения спрайтов анимации Player
	}
	
	// Метод для установки значений игрока по умолчанию
	public void setDefaultValues() {

		// Позиция игрока на карте мира
		worldX = gp.tileSize * 31;
		worldY = gp.tileSize * 6;

		//Скорость игрока
		speed = 4;
		direction = "down";
	}

	// Метод получения спрайтов
	public void getPlayerImage() {

		up1 = setup("player_up_1");
		up2 = setup("player_up_2");
		up3 = setup("player_up_3");
		up4 = setup("player_up_4");
		up5 = setup("player_up_5");
		up6 = setup("player_up_6");

		down1 = setup("player_down_1");
		down2 = setup("player_down_2");
		down3 = setup("player_down_3");
		down4 = setup("player_down_4");
		down5 = setup("player_down_5");
		down6 = setup("player_down_6");

		right1 = setup("player_right_1");
		right2 = setup("player_right_2");
		right3 = setup("player_right_3");
		right4 = setup("player_right_4");
		right5 = setup("player_right_5");
		right6 = setup("player_right_6");

		left1 = setup("player_left_1");
		left2 = setup("player_left_2");
		left3 = setup("player_left_3");
		left4 = setup("player_left_4");
		left5 = setup("player_left_5");
		left6 = setup("player_left_6");

	}

	public BufferedImage setup(String imageName) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName +".png")));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	// Метод обновления изменений в классе игрока Player
	public void update () {

		// Условие чтобы анимация спрайтов была заморожена пока не нажаты клавиши управления
		if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

			if(keyH.upPressed) {
				direction = "up";
			}
			else if(keyH.downPressed) {
				direction = "down";
			}
			else if(keyH.leftPressed) {
				direction = "left";
			}
			else {
				direction = "right";
			}

			// Проверка столкновений плиток
			collisionOn = false;
			gp.cCheck.checkTile(this);

			// Проверка столкновения объектов
			int objIndex =  gp.cCheck.checkObject(this, true);
			pickUpObject(objIndex);

			// Если у collision ложное значение, игрок не может двигаться
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

	}
	// Метод который на основании индекса опишет действие при касании игроком объекта
	public void pickUpObject(int i) {
		// Условие если индекс не равен 999 это значит что к объекту прикоснулись
		if(i != 999) {

			String objectName = gp.obj[i].name;

            switch (objectName) {

                case "Key" -> {
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Какой-то ключ от чего-то,\nможет пригодиться!");
                }

                case "Door" -> {
                    gp.playSE(3);
                    if (hasKey > 0) { // Проверка есть ли у игрока ключ
                        gp.obj[i] = null; // Объект дверь исчезает
                        hasKey--;
						gp.ui.showMessage("Калитка отперта!");
                    }
                    else {
						gp.ui.showMessage("Без ключа,\nне откроешь ворота!");
					}
                }

                case "Sneakers" -> {
                    gp.playSE(2);
                    speed += 6;
                    gp.obj[i] = null;
					gp.ui.showMessage("Кто-то оставил\nещё годные кроссовки!");
                }

				case "Box" -> {
					gp.ui.gameFinished = true;
					gp.stopMusic();
					gp.playSE(4);
					break;
				}
            }
		}

	}
	
	// Метод отрисовки
	public void draw(Graphics2D g2) {

		// Инициализация переменной image типа BufferedImage
		BufferedImage image = null;

		// Условная конструкция. Перебирает варианты направления движения player, для выбора нужного спрайта
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
		g2.drawImage(image, screenX, screenY, null); // Вызов метода отрисовки и передача необходимых аргументов.

	}
}
