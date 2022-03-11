package com.musicplayer;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import de.sciss.jump3r.Main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class test {

    private static void convertWavFileToMp3File(File source, File target) throws IOException {
        String[] mp3Args = {
                "--preset","standard",
                "-q","5",
                "-m","s",
                source.getAbsolutePath(),
                target.getAbsolutePath()
        };
        (new Main()).run(mp3Args);
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        // JAVAFX
        String fileName = System.getProperty("user.dir") + "/audio/lil.mp3";
        Media hit = new Media(new File(fileName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

        // CONVERTING
//        File file = new File("/home/fancybaby/Downloads/lil.mp3");
        // ----------------------------------------------------------------
//        convertWavFileToMp3File(file, new File("./audio/" + "test"));

//        // ----------------------------------------------------------------
//        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioStream);
//        clip.start();
//
//        String response = "";
//        Long currentMicro = (long) 0;
//
//        while (!response.equalsIgnoreCase("0")) {
//            System.out.println(
//                    "Welcome to JulianAudio\nType (0) to stop\n  Type (1) to reset\n  Type (2) to pause\nType (3) to resume");
//            Scanner scanner = new Scanner(System.in);
//            response = scanner.next();
//
//            switch (response) {
//                case "0":
//                    clip.stop();
//                    break;
//
//                case "1":
//                    clip.setMicrosecondPosition(0);
//                    break;
//
//                case "2":
//                    currentMicro = clip.getMicrosecondPosition();
//                    clip.stop();
//                    break;
//
//                case "3":
//
//                    try {
//                        clip.start();
//                        clip.setMicrosecondPosition(currentMicro);
//                    } catch (Exception e) {
//
//                    }
//
//            }
//        }
    }
}
