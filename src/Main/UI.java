// Класс пользовательского интерфейса, обработка всего экранного интерфейса (текстовые сообщения, значки предметов и тд.)

package Main;

import object.OBJ_Key;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

// Инициализирует класс UI, отвечающий за интерфейс пользователя в игре
public class UI {

    GamePanel gp; // ссылка на игровую панель GamePanel
    Graphics2D g2;
    Font sitka_30, sitka_50B; // Инициализируются переменные шрифтов
    BufferedImage keyImage;  // Создается объект ключа из класса OBJ_Key и сохраняется его изображение в переменную keyImage

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    // Форматирование отображения времени
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {

        this.gp = gp;

        sitka_30 = new Font("sitka", Font.ITALIC, 30);
        sitka_50B = new Font("sitka", Font.BOLD, 50);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    // Переопределение метода drawString, для возможности переноса строк
    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(sitka_30);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            //
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

        if (gameFinished) { // Проверка закончена ли игра, если игра завершена - рисуем финальный текст:

            // Устанавливаем шрифт, цвет
            g2.setFont(sitka_30);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "Ты нашёл коробку!"; // текст, который появляется при взаимодействии

            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // получение длины строки

            // Выравнивание текста
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize);


            // Отрисовка текста
            g2.setColor(Color.orange);
            text = "время: " + dFormat.format(playTime) + " сек.";
            // Получение длины строки
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            // Выравнивание текста
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize * 3);
            // Отрисовка текста
            g2.drawString(text, x, y);

            g2.setFont(sitka_50B);
            g2.setColor(Color.orange);
            text = "ЧИСТАЯ ПОБЕДА";
            // Получение длины строки
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            // Выравнивание текста
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize * 2);
            // Отрисовка текста
            g2.drawString(text, x, y);

            // Остановка потока игры
            gp.gameThread = null;
        }

        else {
            // Методы отрисовки элементов Ui
            g2.setFont(sitka_30);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            drawString(g2, "x "+ gp.player.hasKey, 74, 22);

            // Время прохождения уровня
            playTime +=(double)1/60;
//            g2.drawString("Time:"+ dFormat.format(playTime), gp.tileSize * 12, 65);

            // Сообщение
            if(messageOn) {

                // Смена размера шрифта для сообщения
                g2.setFont(g2.getFont().deriveFont(21F));

                // Координаты отрисовки шрифта сообщения
                drawString (g2, message, gp.tileSize/2, gp.tileSize*4);

                messageCounter++;

                if(messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60));
        String text = "ПАУЗА";
        int x = getXCenterText(text);
        int y = gp.screenHeight/2;

        g2.drawString (text, x, y);
    }

    public int getXCenterText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
