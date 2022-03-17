package com.musicplayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static com.musicplayer.AppTools.showError;


/*
This is the main music player for WAV files, it contains
the main methods such as, playMusic, pauseMusic, resetMusic
and other methods that would be responsible for getting
the current volume and setting the volume.
*/

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
            showError("Please drag a mp3/wav file into the application first!");
        } else {
            currentMicro = clip.getMicrosecondPosition();
            clip.stop();
        }
}

    void resetMusic() {

        if (clip == null) {}
        else {
            System.out.println("RESETTING MUSIC.");
            clip.setMicrosecondPosition(0);
            currentMicro = 0L;
            clip.stop();
        }

    }

    public float getVolume() {
        if (clip == null) {
            return 0f;
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (clip == null) {
            return;
        }
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}