package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

//Родительский класс для класса Player и любых других классов персонажей
public class Entity {
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
	public Rectangle solidArea; // Класс создаёт абстрактный прямоугольник

	public int solidAreaDefaultX, solidAreaDefaultY; // Координаты этой области по умолчанию.
	public boolean collisionOn = false; // Флаг регистрации столкновений collisionOn
}
