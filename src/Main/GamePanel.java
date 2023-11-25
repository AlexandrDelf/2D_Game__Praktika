package Main;

// Импорт библиотек\пакетов

import java.awt.Color; //  Библиотека для предоставления различных способов работы с цветом.

// Библиотеки для отрисовки графики
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel; // Импортируется класс JPanel из библиотеки Swing для создания панелей

// Импортируются пользовательские пакеты entity, object, tile, содержащие классы игровых объектов, изображений и т.д.
import entity.Player;
import object.SuperObject;
import tile.TileManager;

// Новый класс GamePanel, подкласс JPanel
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
	public final int maxWorldCol = 60; // Максимальное количество тайлов\плиток (колонок) по вертикали.
	public final int maxWorldRow = 60; // Максимальное количество тайлов\плиток (строк) по горизонтали.


	// FPS (кадры в секунду) определяет скорость игры. В данном случае игра работает при 60 кадрах в секунду.
	int FPS = 60;

	// Новый объект класса TileManager, используется для управления тайлами на карте
	TileManager tileM = new TileManager(this);

	// Инициализация управления с клавиатуры
	KeyHandler keyH = new KeyHandler();

	// Инициализация класса музыки
	Sound music = new Sound();

	// Инициализация класса звуков
	Sound se = new Sound();


	// Проверка столкновений (CollisionCheck)
	public CollisionCheck cCheck = new CollisionCheck(this);

	// Создание экземпляра AssetSetter, используются для работы со спрайтами
	public AssetSetter aSetter = new AssetSetter(this);

	// Экземпляр класса пользовательского интерфейса, используются для работы с пользовательским интерфейсом.
	public  UI ui = new UI(this);

	// Игровое время
	Thread gameThread; // Thread - поток отвечает за выполнение инструкций запущенного процесса

	// Инициализация класса Player
	public Player player = new Player(this, keyH); // В скобках передаём GamePanel и KeyHandler

	// Инициализация класса SuperObject
	// Ограниченное число слотов для объектов
	public SuperObject[] obj = new SuperObject[10];


	// Конструктор игровой панелиGamePanel
	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Установка размеров экрана, используя высоту screenHeight и ширину screenWidth
		this.setBackground(Color.black); // Цвет фона GamePanel
		this.setDoubleBuffered(true); // Производительность при рендер(ходят слухи, но чет не уверен)
		this.addKeyListener(keyH); // добавляет KeyHandler к GamePanel, обрабатывает\слушает нажатие клавиш
		this.setFocusable(true); // установка фокуса на компоненте?
	}

	// Метод setupGame() инициализирует игру.
	public void setupGame () {

		aSetter.setObject(); // Инициализация объектов на карте
		playMusic(0); // Проигрывание музыки
	}

	// Метод startGameThread() создает новый поток Thread и вызывает его метод start() для запуска.
	public void startGameThread () {

		gameThread = new Thread(this); // Инициализация потока Thread
		gameThread.start(); // запуск потока Thread
	}

	// Метод run() запускает игровой цикл.
	public void run() {
		// игровой поток (gameThread) продолжает выполняться, пока он не будет остановлен или уничтожен.
		// он повторяет процесс внутри фигурных скобок,
			while(gameThread != null) {

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

					Thread.sleep((long) remainingTime); // Останавливает игру пока ничего не происходит.  Поток “засыпает” на определенное время, чтобы обеспечить плавную работу игры и избежать перегрузки процессора.


					nextDrawTime += drawInterval; // Когда время сна заканчивается и возобновляется поток, добавляем интервал рисования

					// Обработка исключения InterruptedException.
					// Если возникает такое исключение, то управление передается этому блоку кода.
					// Здесь выводится сообщение об ошибке.
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.fillInStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}

		// Метод обновления
		public void update() {

			player.update(); // обновление данных об игроке, вызывая метод update() у объекта player.
		}

		// Метод отрисовки, один из стандартных методов рисования на Jpanel
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g; // Преобразование объекта графики g в Graphics2D для возможности использования расширенных методов отрисовки.

			// Отладка
			long drawStart = 0;
			if(keyH.checkDrawTime) {
				drawStart = System.nanoTime(); // проверка времени
			}



			// Вызов метода рисования внутри TileManager
			tileM.draw(g2);

			// Вызов метода рисования объектов
            for (SuperObject superObject : obj) {
                if (superObject != null) {
                    superObject.draw(g2, this);
                }

            }

			//  Вызов метода рисования игрока
			player.draw(g2);

			// Вызов метода пользовательского интерфейса
			ui.draw(g2);

			// Отладка, выводит время, которое потребовалось для отрисовки последнего кадра.
			// Время отображается в наносекундах.
			if (keyH.checkDrawTime) {

				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
				g2.setColor(Color.white);
				g2.drawString("Время отрисовки: " + passed, 10, 400);
				System.out.println("Время отрисовки: "+passed);
			}


			g2.dispose(); // Утилизирует Graphics2D g2, код будет отрабатывать и без данной строчки, но это экономит ресурс

		}

		// Метод playMusic() проигрывает музыкальный файл, выбранный с помощью параметра i.
		// Если файл не указан (например, если i равен 0), будет проигран файл по умолчанию.
		public void playMusic(int i) {

			music.setFile(i); // Выбор необходимого файла
			music.play(); // Запуск выбранного файла
			music.loop(); // Зацикливание файла

		}

		public void stopMusic() {

			music.stop(); // Остановка музыки

		}

		// Метод playSE() проигрывает звуковой эффект, выбранный с помощью параметра i.
		public void playSE(int i) {

			se.setFile(i); // Выбор необходимого файла
			se.play(); // Запуск выбранного файла
		}

}

