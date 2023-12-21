package org.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

// класс для загрузки и воспроизведения звуков в игре по индексу из массива ресурсов
public class Sound {
    Clip clip; // объект класса Clip для воспроизведения аудио
    URL[] soundURL = new URL[30]; // содержит ссылки на аудио ресурсы в папке проекта

    // В конструкторе происходит инициализация элементов этого массива на конкретные wav файлы звуков
    public Sound() {

        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");

    }

    public void setFile(int i) { // Получение целого числа в качестве аргумента, принимает индекс звука в массиве URL[]

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]); // по индексу получает ресурс в виде потока AudioInputStream
            // На основе этого потока создается объект Clip и вызывает метод open() для загрузки данных
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception ignored) {
        }

    }

    // Метод play() запускает воспроизведение звука один раз
    public void play() {

        clip.start();
    }

    // Метод loop() зацикливает звук с помощью флага Clip.LOOP_CONTINUOUSLY
    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Метод stop() останавливает воспроизведение
    public void stop() {

        clip.stop();
    }

}
