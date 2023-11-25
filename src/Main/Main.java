package Main;

import javax.swing.JFrame;

// создается графическое окно, в него добавляется игровая панель, после чего запускается игровой процесс.
public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame(); // Создается экземпляр окна JFrame. Открывает окно
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Закрывает окно, закрытие по крестику.
		window.setResizable(false); // Фиксирует размер окна
		window.setTitle("Practice Adventure"); // Название игры
		
		GamePanel gamePanel = new GamePanel(); // Создается экземпляр панели GamePanel, которая содержит игровую логику и графику.
		window.add(gamePanel); // Добавление в это окно
		
		window.pack(); // Подгоняет размер окна под предпочтительный размер панели.
		
		window.setLocationRelativeTo(null); // Расположение окна по центру экрана
		window.setVisible(true); // Окно становится видимым с помощью метода setVisible().

		gamePanel.setupGame(); // Метод объектов вызывается перед запуском игры
		gamePanel.startGameThread(); // Вызов метода startGameThread из GamePanel

	}

}
