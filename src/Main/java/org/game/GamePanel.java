package org.game;

// Импорт библиотек\пакетов
import org.entity.Entity;
import org.entity.Player;
import org.objects.SuperObject;
import org.tile.TileManager;

import java.awt.Color; //  Библиотека для предоставления различных способов работы с цветом

// Библиотеки для отрисовки графики
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel; // Импортируется класс JPanel из библиотеки Swing для создания панелей

// Импортируются пользовательские пакеты entity, object, tile, содержащие классы игровых объектов, изображений и т.д.


//import static java.lang.Thread.sleep;

// Новый класс является основным классом игры, который отвечает за её запуск и отрисовку.
// Он расширяет класс JPanel, что позволяет использовать его в качестве компонента в приложении Java Swing.
public class GamePanel extends JPanel implements Runnable{

	// Настройки экрана приложения
	final int originalTileSize = 16; // Размер стандартной плитки по умолчанию 16x16
	final int scale = 3; // Переменная масштабирования

	public final int tileSize = originalTileSize * scale; // Размер выводимой на экран плитки 48x48
	public final int maxScreenCol = 16; // Определение размера всего экрана по горизонтали, колонки
	public final int maxScreenRow = 12; // Определение размера всего экрана по вертикали, строки

	// Размер экрана по вертикале и горизонтали
	public final int screenWidth = tileSize * maxScreenCol; // 768px
	public  final  int screenHeight = tileSize * maxScreenRow; // 576px



	//  Параметры карты мира
	public final int maxWorldCol = 60; // Максимальное количество тайлов\плиток (колонок) по вертикали
	public final int maxWorldRow = 60; // Максимальное количество тайлов\плиток (строк) по горизонтали


	// FPS (кадры в секунду) определяет скорость игры. В данном случае игра работает при 60 кадрах в секунду
	int FPS = 60;

	// Новый объект класса TileManager, используется для управления тайлами на карте
	TileManager tileM = new TileManager(this);

	// Инициализация управления с клавиатуры
	public KeyHandler keyH = new KeyHandler(this);

	// Инициализация класса музыки
	Sound music = new Sound();

	// Инициализация класса звуков
	Sound se = new Sound();


	// Проверка столкновений (CollisionCheck)
	public CollisionCheck cCheck = new CollisionCheck(this);

	// Создание экземпляра AssetSetter, используются для работы с обработкой размещения объектов
	public AssetSetter aSetter = new AssetSetter(this);

	// Экземпляр класса пользовательского интерфейса
	public  UI ui = new UI(this);

	// Игровое время
	Thread gameThread; // Thread - поток отвечает за выполнение инструкций запущенного процесса

	// Инициализация класса Player
	public Player player = new Player(this, keyH); // В скобках передаём GamePanel и KeyHandler

	// Инициализация класса SuperObject
	// Ограниченное число слотов для объектов
	public SuperObject[] obj = new SuperObject[10];
	public Entity[] npc = new Entity[10]; // NPC

	// Состояния игры
	public int gameState;
	public final int titleState = 0; // титульный экран
	public final int playState = 1; // игра запущена
	public final int pauseState = 2; // игра остановлена
	public final int dialogState = 3; // диалоги
	public final int gameFinished = 4; // победа


	// Конструктор игровой панели GamePanel
	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Установка размеров экрана, используя высоту screenHeight и ширину screenWidth
		this.setBackground(Color.black); // Цвет фона GamePanel
		this.setDoubleBuffered(true); // Производительность при рендер(ходят слухи, но чет не уверен)
		this.addKeyListener(keyH); // добавляет KeyHandler к GamePanel, обрабатывает\слушает нажатие клавиш
		this.setFocusable(true); // установка фокуса на компоненте?
	}

	// Настройки игры при запуске
	public void setupGame () {

		aSetter.setObject(); // Инициализация метода обработки размещения объектов на карте
		aSetter.setNPC(); // Инициализация метода npc
		this.playMusic(0); // Проигрывание музыки
		this.stopMusic(); // Остановка музыки
		gameState = titleState; // Состояние игры
		player.setDefaultValues(); // Настройки для игрока по умолчанию
		ui.playTime = 0; // Обнуление счётчика времени прохождения
		player.hasKey = 0; // Обнуление количества ключей у игрока в начале игры
		ui.messageCounter = 120; // Сброс сообщения для предметов
	}

	// Метод startGameThread() создает новый поток Thread и вызывает его метод start() для запуска
	public void startGameThread () {

		gameThread = new Thread(this); // Инициализация потока Thread
		gameThread.start(); // запуск потока Thread
	}

	/*
 Метод run() запускает игровой цикл.
	public void run() {


		// игровой поток (gameThread) продолжает выполняться, пока он не будет остановлен или уничтожен
		// он повторяет процесс внутри фигурных скобок
			while(gameThread != null) {

				if(keyH.playMusic) {
					music.play();
					music.loop();
				} else {
					music.stop();
				}

				double drawInterval = (double) 1000000000 / FPS; // Так как используются наносекунды 1 млрд = 1 сек, делим секунду на переменную FPS
				double nextDrawTime = System.nanoTime() + drawInterval; // System.nanoTime() Возвращает текущее время и прибавляет значение drawInterval

				// Обновление информации о позиции персонажа
				update();

				// Нарисовать экран с обновлением информации
				repaint();



				// Переводит оставшееся время (remainingTime) в миллисекунды и проверяет, является ли оно меньше нуля.
				// Если это так, то оставшееся время устанавливается равным нулю.
				// Это связано с тем, что метод sleep() не может принять отрицательное значение времени.
				try {
					double remainingTime = nextDrawTime - System.nanoTime();
					remainingTime = remainingTime / 1000000; // Перевод наносекунд в миллисекунды, так как метод sleep работает с миллисекундами

					if(remainingTime < 0) {
						remainingTime = 0;
					}

					sleep((long) remainingTime); // Останавливает игру пока ничего не происходит.  Поток “засыпает” на определенное время, чтобы обеспечить плавную работу игры.


					nextDrawTime += drawInterval; // Когда время сна заканчивается и возобновляется поток, добавляем интервал рисования

					// Обработка исключения InterruptedException.
					// Если возникает такое исключение, то управление передается этому блоку кода.
					// Здесь выводится сообщение об ошибке.
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
*/

	// Метод реализации основного игрового цикла (game loop)
	// Игра обновляет свое состояние и отрисовывает себя на экране постепенно, по частям. Это позволяет игре работать более плавно
    public void run () {

		double drawInterval = (double) 1000000000 / FPS; //  задание констант желаемого количества кадров в секунду (FPS) и интервала между отрисовкой кадров в наносекундах

		// Переменные для подсчета времени и количества кадров:
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		// запускает бесконечный цикл while, который будет выполняться до тех пор, пока поток игры не будет остановлена
		while (gameThread != null) {

			// Проигрывает музыку, если флаг playMusic включен. В противном случае музыка останавливается
			if(keyH.playMusic) {
				music.play();
				music.loop();
			} else {
				music.stop();
			}


			currentTime = System.nanoTime(); // Получает текущее время в наносекундах

			// Вычисление разницы между текущим временем и прошлым временем, затем добавление разницы к счетчику времени.
			// Обновление прошлого время на текущее время.
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update(); // обновляет состояние игры
				repaint(); // отрисовывает игру на экране
				delta--; //  уменьшает значение дельты на единицу
				drawCount++; // увеличивает значение счетчика кадров на единицу
			}

			// Проверка прошла ли 1 секунду. Если да, то она выводит в консоль текущее количество кадров.
			// Полезно на этапах оптимизации
			if(timer > 1000000000) {
				System.out.println("FPS:" + drawCount); // отображает сообщение в консоли
				drawCount = 0; // переменная drawCount обнуляется, чтобы начать новый цикл подсчета FPS
				timer = 0; // Переменная timer обнуляется, чтобы начать новый цикл измерения времени
			}

		}
	}

		// Метод обновления
		public void update() {

			if (gameState == playState){
				player.update(); // обновление данных об игроке, вызывая метод update() у объекта player
                for (Entity entity : npc) {
                    if (entity != null) {
                        entity.update();
					}
                }
			}
		}

		// Метод отрисовки, один из стандартных методов рисования на Jpanel
		public void paintComponent(Graphics g) {

			super.paintComponent(g); // Формат при использовании метода на Jpanel
			Graphics2D g2 = (Graphics2D)g; // Преобразование объекта графики g в Graphics2D для возможности использования расширенных методов отрисовки.

			// Отладка
			long drawStart = 0;
			if(keyH.checkDrawTime) {
				drawStart = System.nanoTime(); // проверка времени
			}

			// Титульный экран (экран меню)
			if(gameState == titleState || gameState == pauseState) {
				ui.draw(g2);
			}
//			// Состояние игра закончена
//			if(gameState == gameFinished) {
//				ui.draw(g2);
//			}
			// Игровые объекты
			else {
				// Вызов метода рисования TileManager
				tileM.draw(g2);

				// Вызов метода рисования объектов
				for (SuperObject superObject : obj) {
					if (superObject != null) {
						superObject.draw(g2, this);
					}
				}

				// Вызов метода рисования npc
				for (Entity entity : npc) {
					if (entity != null) {
						entity.draw(g2);
					}
				}

				//  Вызов метода рисования игрока
				player.draw(g2);

				// Вызов метода пользовательского интерфейса
				ui.draw(g2);
			}

			// Отладка, выводит время, которое потребовалось для отрисовки последнего кадра
			// Время отображается в наносекундах
			if (keyH.checkDrawTime) {

				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
				g2.setColor(Color.white);
				g2.drawString("Время отрисовки: " + passed, 10, 400);
				System.out.println("Время отрисовки: " + passed);
			}

			g2.dispose(); // Утилизирует Graphics2D g2, код будет отрабатывать и без данной строчки, но это экономит ресурс

		}

		// Метод playMusic() проигрывает музыкальный файл, выбранный с помощью параметра i
		// Если файл не указан (например, если i равен 0), будет проигран файл по умолчанию
		public void playMusic(int i) {
			music.setFile(i); // Выбор необходимого файла
			music.play(); // Запуск выбранного файла
			music.loop(); // Зацикливание файла
		}

		public void stopMusic() {

			music.stop(); // Остановка музыки
		}

		// Метод playSE() проигрывает звуковой эффект, выбранный с помощью параметра i
		public void playSE(int i) {

			se.setFile(i); // Выбор необходимого файла
			se.play(); // Запуск выбранного файла
		}

}

