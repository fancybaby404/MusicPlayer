import javax.sound.sampled.*;

class AppMusic {

    Long currentMicro;

    AppMusic (String pathFile) {
        // instantiate
        File file = new File(pathFile);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

        // global vars
        currentMicro = (long) 0;
    }

    void playMusic () {
        if (currentMicro == 0) {
            clip.play();
        } else {
            try {
                clip.start();
                clip.setMicrosecondPosition(currentMicro);
            } catch (Exception e) {

            }
        }
    }

    void pauseMusic () {
        currentMicro = clip.getMicrosecondPosition();
        clip.stop();

    }

    void resetMusic () {
        clip.setMicrosecondPosition(0);
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