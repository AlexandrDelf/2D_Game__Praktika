package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//  Реализует обработку нажатия клавиш в приложении.
public class KeyHandler implements KeyListener{

	// Объявляются булевые переменные для отслеживания состояния клавиш передвижения.
	public boolean upPressed, downPressed, leftPressed, rightPressed;

	// Отладка
	boolean checkDrawTime = false;


	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode(); // Возвращает значение нажатой клавиши
		
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) { // Если нажата клавиша W
			upPressed = true;			
		}
		
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { // Если нажата клавиша S
			downPressed = true;
		}
		
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) { // Если нажата клавиша A
			leftPressed = true;
		}
		
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) { // Если нажата клавиша D
			rightPressed = true;
		}

		// При нажатии на T включаем/выключаем режим отладки checkDrawTime.
		if(code == KeyEvent.VK_T) { // Если нажата клавиша T
            checkDrawTime = !checkDrawTime;
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
		// Клавиша не нажата
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) { // Если не нажата клавиша W
			upPressed = false;			
		}
		
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { // клавиша S
			downPressed = false;
		}
		
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) { // клавиша A
			leftPressed = false;
		}
		
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) { // клавиша D
			rightPressed = false;
		}
		
	}

}
