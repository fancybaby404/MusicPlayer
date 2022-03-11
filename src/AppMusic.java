package com.musicplayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class AppMusic {

    Long currentMicro;
    Clip clip;
    File file;
    AudioInputStream audioStream;
    public boolean failed;

    AppMusic() {
        // instantiate

        // global vars
        currentMicro = (long) 0;
    }


    void playMusic(String pathFile) {

        try {

            file = new File(pathFile);


            if (currentMicro == 0) {
                audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.start();
                System.out.println("current micro is 0");
            } else {
                System.out.println("current micro is NOT 0");
                clip.setMicrosecondPosition(currentMicro);
                clip.start();
            }

        } catch (LineUnavailableException e) {
            e.printStackTrace();
            this.failed = true;
        } catch (IOException e) {
            e.printStackTrace();
            this.failed = true;
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            this.failed = true;
        }
    }

    void pauseMusic() {
        if (clip == null) {
            App.showError("Please drag a mp3/wav file into the application first!");
        } else {
            currentMicro = clip.getMicrosecondPosition();
            clip.stop();
        }

    }

    void resetMusic() {

        if (clip == null) {
            App.showError("Successfully reset");
        } else {
//            clip.setMicrosecondPosition(0);
            clip.stop();
//            clip.close();
        }

    }

}

//     try {
//         String response = "";

//         while (!response.equalsIgnoreCase("0")) {
// System.out.println(
//         "Welcome to JulianAudio\nType (0) to stop\n  Type (1) to reset\n  Type (2) to pause\nType (3) to resume");
// Scanner scanner = new Scanner(System.in);
// response = scanner.next();

//             switch (response) {
//                 case "0":
// stop
//                     clip.stop();
//                     break;

//                 case "1":
//reset i think?
//                     clip.setMicrosecondPosition(0);
//                     break;

//                 case "2":
// pause?
//                     currentMicro = clip.getMicrosecondPosition();
//                     clip.stop();
//                     break;

//                 case "3":
// resume?

//                     try {
//                         clip.start();
//                         clip.setMicrosecondPosition(currentMicro);
//                     } catch (Exception e) {

//                     }

//             }
//         }
//     }

//     catch (UnsupportedAudioFileException e1) {
//         System.out.println(e1);
//     } catch(IOException e2) {
//         System.out.println(e2);
//     } catch (LineUnavailableException e3) {
//         System.out.println(e3);
//     }

// }
// public static void main(String args[]) {

// Scanner scanner = Scanner(System.in);

// File file = new File("audio.wav");
// AudioInputStream audioStream = AudioInputStream(file)

// Clip clip = AudioSystem.getClip();
// clip.open(audioStream);

// String response = scanner.next();
// }