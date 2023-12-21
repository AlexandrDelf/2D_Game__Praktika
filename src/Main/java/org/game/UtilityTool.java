//  Масштабирование изображений под нужный размер при отрисовке sprite в играх. Масштабируется сглажено, не теряя качества.

package org.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    // Класс масштабирующий все буферизованные изображения
    public BufferedImage scaleImage(BufferedImage original, int width, int height) { // scaleImage принимает оригинальное изображение, желаемую ширину и высоту.

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); // Создается новое пустое изображение scaledImage с указанными размерами.
        Graphics2D g2 = scaledImage.createGraphics(); // Получаем объект Graphics2D для рисования на этом изображении.
        g2.drawImage(original, 0, 0, width, height, null); // Масштабирование изображения
        g2.dispose(); // Освобождаем ресурсы, завершаем рисование.

        return scaledImage; // Возвращаем новое масштабированное изображение.
    }
}
