package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//  Реализует обработку нажатия клавиш в приложении.
public class KeyHandler implements KeyListener{

	GamePanel gp;

	// Объявляются булевы переменные для отслеживания состояния клавиш передвижения.
	public boolean upPressed, downPressed, leftPressed, rightPressed; // на будущее enterPressed

	// Отладка
	boolean checkDrawTime = false;

	// Создание конструктора
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	// Играть музыку
	public boolean playMusic = false;


	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Метод получения клавиши
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode(); // Возвращает значение нажатой клавиши

		// Титульный экран
		if (gp.gameState == gp.titleState) {

			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) { // Если нажата клавиша W
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}
			}

			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { // Если нажата клавиша S
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
			}

			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					if (!this.playMusic) {
						gp.playMusic(0);
						this.playMusic = true;
					}
				}
				if (gp.ui.commandNum == 1) {
					System.exit(0);
				}

			}

		}

//		// Состояние gameFinished
//		if (gp.gameState == gp.gameFinished) {
//
//			if (code == KeyEvent.VK_ESCAPE) {
//				gp.gameState = gp.titleState;
//			}
//		}

		// Состояние игры
		if (gp.gameState == gp.playState) {

			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.titleState;
				if (this.playMusic) {
					gp.stopMusic();
					this.playMusic = false;
				}
			}

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

			// При нажатии на P пауза
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
			}

//			//При нажатии на Enter
//			if(code == KeyEvent.VK_ENTER) {
//				enterPressed = true;
//			}

			//При нажатии на N
            if(code == KeyEvent.VK_N) { // Если нажата клавиша
				playMusic = !playMusic;
			}
		}

		// Состояние пауза
		else if(gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P) { // Если нажата клавиша P
				gp.gameState = gp.playState;
			}
		}
		// Состояние диалог
		else if(gp.gameState == gp.dialogState) {
			if(code == KeyEvent.VK_ENTER){
				gp.gameState = gp.playState;
			}
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
