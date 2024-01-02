// Класс пользовательского интерфейса, обработка всего экранного интерфейса (текстовые сообщения, значки предметов и тд.)

package org.game;

import org.objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

// Инициализирует класс UI, отвечающий за интерфейс пользователя в игре
public class UI {

    GamePanel gp; // ссылка на игровую панель GamePanel
    Graphics2D g2;
    Font yanoneKaffeesatzL, yanoneKaffeesatzB; // Инициализируются переменные шрифтов
    BufferedImage keyImage;  // Создается объект ключа из класса OBJ_Key и сохраняется его изображение в переменную keyImage

    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    public String currentDialog = "";
    public int commandNum = 0;
    int messageCounter = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00"); // Форматирование отображения времени

    public UI(GamePanel gp) {

        this.gp = gp;

        // Загрузка шрифтов
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/YanoneKaffeesatz-Bold.ttf");
            assert is != null;
            yanoneKaffeesatzB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/fonts/YanoneKaffeesatz-Light.ttf");
            assert is != null;
            yanoneKaffeesatzL = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

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

        g2.setFont(yanoneKaffeesatzL);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);


        // Состояние титульного экрана
        if(gp.gameState == gp.titleState || gp.gameState == gp.pauseState) {
            drawTitleScreen();
        }

        // Состояние игра
        if(gp.gameState == gp.playState) {

            // Методы отрисовки элементов Ui
            g2.setFont(g2.getFont().deriveFont(42F));
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 60);

            // Картинка ключа для счетчика
            OBJ_Key key = new OBJ_Key(gp);
            keyImage = key.image;

            // Время прохождения уровня
            playTime +=(double)1/60;

            // Сообщение при взаимодействии с объектом
            if(messageOn) {
                // Смена размера шрифта для сообщения
                g2.setFont(g2.getFont().deriveFont(24F));
                // Координаты отрисовки шрифта сообщения
                drawString (g2, message, gp.tileSize/2, gp.tileSize*4);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }

            }
        }

        // Состояние пауза
//        if(gp.gameState == gp.pauseState){
//            drawPauseScreen();
//        }

        // Состояние диалог
        if(gp.gameState == gp.dialogState) {
            drawDialogScreen();
        }

        // При окончании игры
        if (gameFinished) { // Проверка закончена ли игра, если игра завершена - рисуем финальный текст:

//            String titleTimeText;
//            int titleTimeTextLength;
//            int titleTimeTextX;
//            int titleTimeTextY;
//
//            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
//            g2.setColor(Color.orange);
//            titleTimeText = "БЕЗУМНАЯ СКОРОСТЬ";
//            // Получение длины строки
//            titleTimeTextLength = (int)g2.getFontMetrics().getStringBounds(titleTimeText, g2).getWidth();
//            // Выравнивание текста
//            titleTimeTextX = gp.screenWidth/2 - titleTimeTextLength/2;
//            titleTimeTextY = gp.screenHeight/2 + (gp.tileSize);
//            // Отрисовка текста
//            drawString(g2, titleTimeText, titleTimeTextX, titleTimeTextY);

            // Переменные для текста времени прохождения
            String timeText;
            int timeTextLength;
            int timeTextX;
            int timeTextY;
            gp.gameState = gp.gameFinished;

            // Текст для времени прохождения
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.orange);
            timeText = "Время: " + dFormat.format(playTime) + " сек.";
            // Получение длины строки
            timeTextLength = (int)g2.getFontMetrics().getStringBounds(timeText, g2).getWidth();
            // Выравнивание текста
            timeTextX = gp.screenWidth/2 - timeTextLength/2;
            timeTextY = gp.screenHeight/2 + gp.tileSize;
            // Отрисовка текста
            drawString(g2, timeText, timeTextX, timeTextY);

            // Переменные для поздравительного текста
            String mainText;
            int mainTextLength;
            int mainTextX;
            int mainTextY;

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 84F));
            g2.setColor(Color.orange);
            mainText = "ТЫ НАШЁЛ КОРОБКУ!"; // текст, который появляется при взаимодействии
            mainTextLength = (int)g2.getFontMetrics().getStringBounds(mainText, g2).getWidth(); // получение длины строки
            // Выравнивание текста
            mainTextX = gp.screenWidth/2 - mainTextLength/2;
            mainTextY = gp.screenHeight/2 - (gp.tileSize*3);
            drawString(g2, mainText, mainTextX, mainTextY);
            // Остановка потока игры
//            gp.gameThread = null;
//            gp.startGameThread();
        }

    }

    // Метод рисования титульного экрана
    public void drawTitleScreen() {

        // Переменные для пунктов меню
        String exitText;
        String gameText;

        // Основной цвет фона
        g2.setColor(new Color(57, 135, 92));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Заголовок
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,108F));
        String text = "Practice_Game";
        int x = getXCenterText(text);
        int y = gp.tileSize*3;

        // Тень от текста
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        // Цвет текста
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Изображение(любая плитка)
        x = gp.screenWidth/2 - gp.tileSize;
        y += gp.tileSize*2 - gp.tileSize;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // Меню
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        if (gp.gameState == gp.pauseState) {
            gameText = "ПРОДОЛЖИТЬ";
        }
        else gameText = "НОВАЯ ИГРА";
        x = getXCenterText(gameText);
        y += gp.tileSize*4;
        g2.drawString(gameText, x, y);
        if(commandNum == 0) {
            g2.drawString("~", x-gp.tileSize, y);
        }

        exitText = "ВЫХОД";
        x = getXCenterText(exitText);
        y += gp.tileSize;
        g2.drawString(exitText, x, y);
        if(commandNum == 1) {
            g2.drawString("~", x-gp.tileSize, y);
        }

    }

    // Метод рисования окна диалогов
    public void drawDialogScreen() {

        // Диалоговое окно
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialog.split("\n")) {
            g2.drawString(line, x, y);
            y +=  40;
        }
    }

    // Стили диалогового окна
    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0,0,0,205); // номер цвета
        g2.setColor(color); // цвет
        g2.fillRoundRect(x, y, width, height, 50, 50); // скругление краев

        color = new Color(255,255,255); // номер цвета
        g2.setColor(color); // установка шрифта
        g2.setStroke(new BasicStroke(6)); // обводка
        g2.drawRoundRect(x, y, width, height, 50, 50); // скругление краев
    }

    // Метод получения центра текста
    public int getXCenterText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
