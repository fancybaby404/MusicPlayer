package com.musicplayer;



import jaco.mp3.player.MP3Player;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

import static com.musicplayer.AppTools.getFileExtension;
import static com.musicplayer.AppTools.showError;


public class App {

    static String[] fileName = new String[1];
    static Integer intCurrentSeconds = 0;
    static int currentMinutes = 0;
    static JButton volumeButton;
    static boolean isMP3;
    static boolean isPaused;

    public static void main(String[] args) {

        final String[] currentSecondsFormatted = new String[1];

        // States
        AtomicBoolean isPlaying = new AtomicBoolean(false);

        // Create Drag and Drop
        JTextPane myPanel = new JTextPane();
        myPanel.setText("Drag a audio file here!");
        myPanel.setBounds(0, 80, 500, 50);
        myPanel.setEditable(false);

        // ------------------------------ GUI

        // Create Labels
        JLabel titleHeader = new JLabel();
        titleHeader.setText("MusicPlayer");
        titleHeader.setBounds(10, 0, 200, 50);
        titleHeader.setFont(new Font("Arial", Font.BOLD, 20));

        // label for audio length
        JLabel audioLengthLabel = new JLabel();
        audioLengthLabel.setBounds(220, 170, 200, 50);

        // label for music name
        JLabel fileNameLabel = new JLabel();
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\musicicon.png");
        ImageIcon imageIcon = new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        fileNameLabel.setBounds(0, 170, 500, 50);
        fileNameLabel.setFont(new Font("Arial", Font.BOLD, 10));

        // imageicons for buttons
        ImageIcon playIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\play.png");
        ImageIcon playImageIcon = new ImageIcon(playIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        ImageIcon pauseIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\pause.png");
        ImageIcon pauseImageIcon = new ImageIcon(pauseIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        ImageIcon resetIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\reset.png");
        ImageIcon resetImageIcon = new ImageIcon(resetIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        ImageIcon volumeIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\volume.png");
        ImageIcon volumeImageIcon = new ImageIcon(volumeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        System.out.println(System.getProperty("user.dir") + "\\pictures\\stop-outline.png");

        // ============= music instantiation

        // wav
        AppMusic music = new AppMusic();

        // mp3
        MP3Player mp3 = new MP3Player();


        // Slider
        JSlider volumeSlider = new JSlider();
        volumeSlider.setPreferredSize(new Dimension(400, 200));
        volumeSlider.setBounds(330, -12, 150, 80);
        volumeSlider.setValue(10);
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(10);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                music.setVolume(Float.valueOf(volumeSlider.getValue() / 10f));
            }
        });

        // Create Buttons
        JButton playButton = new JButton();
        playButton.setIcon(playImageIcon);
        playButton.setBounds(0, 145, 166, 50);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setUI(new BasicButtonUI());

        JButton pauseButton = new JButton();
        pauseButton.setIcon(pauseImageIcon);
        pauseButton.setBounds(166, 145, 166, 50);
        pauseButton.setBorderPainted(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setUI(new BasicButtonUI());

        JButton resetButton = new JButton();
        resetButton.setIcon(resetImageIcon);
        resetButton.setBounds(332, 145, 166, 50);
        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setUI(new BasicButtonUI());

        volumeButton = new JButton();
        volumeButton.setIcon(volumeImageIcon);
        volumeButton.setBounds(370, 0, 166, 50);
        volumeButton.setBorderPainted(false);
        volumeButton.setContentAreaFilled(false);
        volumeButton.setUI(new BasicButtonUI());

        // frame
        volumeButton.addActionListener(e -> {
            System.out.println("Volume button clicked");

            AppVolumeFrame volumeFrame = new AppVolumeFrame();
            volumeFrame.add(volumeSlider);

            volumeFrame.setVisible(true);
        });


        // Add components
        AppFrame frame = new AppFrame();
        frame.add(titleHeader);
        frame.add(myPanel);
        frame.add(playButton);
        frame.add(pauseButton);
        frame.add(resetButton);
        frame.add(fileNameLabel);


        // ======================= AUDIO DURATION FUNCTIONALITY (WAV ONLY)
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (music.clip == null) return;

                if (isPlaying.get() == true) {
                    currentSecondsFormatted[0] = intCurrentSeconds.toString();

                    if (intCurrentSeconds <= 9) currentSecondsFormatted[0] = "0" + currentSecondsFormatted[0];

                    // Increase values
                    if (intCurrentSeconds == 60) {
                        intCurrentSeconds = 0;
                        currentMinutes++;
                    }

                    // Audio duration
                    Double audioLength = music.clip.getMicrosecondLength() / 1e+6;
                    String audioLengthFormatted = String.valueOf(audioLength.intValue() / 60) + ":" + String.valueOf(audioLength.intValue() / 60 * 60 - audioLength.intValue()).replace("-", "");

                    // change gui
                    audioLengthLabel.setText(currentMinutes + ":" + currentSecondsFormatted[0] + " - " + audioLengthFormatted);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }).start();

        // ======================= AUDIO DURATION (for seconds, the delay is set to every second so every second the current second will be incremented by 1, the if statement here is that if is duration is > than the audio length, stop counting.
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying.get() && !isMP3) {
                    if (Math.round(music.clip.getMicrosecondPosition() / 1e+6) >= Math.round(music.clip.getMicrosecondLength() / 1e+6 )) isPlaying.set(false);
                    intCurrentSeconds++;
                }
            }
        }).start();

        // ======================= Drop Functionality
        myPanel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {

                    System.out.println("CURRENT STATE: " + isPlaying.get());

                    if (!isPlaying.get() && !isPaused) {
                        evt.acceptDrop(DnDConstants.ACTION_COPY);
                        List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                        for (File file : droppedFiles) {

                            // === CHECK IF FILE IS VALID
                            String fileExtension = getFileExtension(file);
                            if (fileExtension.equals("mp3")) {
                                isMP3 = true;
                            } else if (fileExtension.equals("wav")) {
                                isMP3 = false;
                            } else {
                                System.out.println("FileExtension: " + fileExtension);
                                System.out.println(fileExtension + " is not wav or mp3");
                                showError("Invalid file extension");
                                return;
                            }
                            // ==============================



                            // display fileName on drop
                            fileName[0] = file.toString();
                            fileName[0] = fileName[0].replace('\\', '/');
                            String[] splittedFileName = fileName[0].split("/");
                            fileNameLabel.setIcon(imageIcon);
                            fileNameLabel.setText(splittedFileName[splittedFileName.length - 1]);

                            frame.add(fileNameLabel);

                            if (!isMP3) { // (WAV)

                                // change volume slider value
                                volumeSlider.setValue(10);

                                // add audio length/duration
                                frame.add(audioLengthLabel);

                                // add volume button
                                frame.add(volumeButton);
                            } else { // (MP3)
                                // Remove Volume button if present
                                try {
                                    frame.remove(volumeButton);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            frame.revalidate();
                            frame.repaint();
                        }
                    } else {
                        showError("There is an audio currently playing, reset first.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ================= BUTTON ACTION LISTENERS
        playButton.addActionListener(e -> {
            if (!isMP3) {
            // (WAV)
                System.out.println(isPlaying.get() + " current state");
                if (!isPlaying.get()) {
                    if (fileName[0] == null) {
                        showError("Please drag a mp3/wav file into the application first before starting!");
                    } else {
                        music.playMusic(fileName[0]);
                        isPlaying.set(true);
                        isPaused = false;
                    }
                }
                System.out.println(isPlaying.get());
                System.out.println("(wav) play button clicked");

            } else {
            // (MP3)
                if (!isPlaying.get()) {
                    if (fileName[0] == null) {
                        showError("Please drag a mp3/wav file into the application first before starting!");
                    } else {
                        mp3.addToPlayList(new File(fileName[0]));
                        mp3.play();
                        isPlaying.set(true);
                        isPaused = false;

                        System.out.println("(mp3) play button clicked");
                    }
                }
            }
        });

        pauseButton.addActionListener(e -> {
            if (!isMP3) {
                // (WAV)
                if (fileName[0] == null) {
                    // show error box that there is no file dropped
                    showError("Please drag a mp3/wav file into the application first before starting!");
                } else {
                    if (isPlaying.get()) {
                        System.out.println(isPlaying.get() + " current state");
                        music.pauseMusic();
                        isPlaying.set(false);
                        isPaused = true;
                    }
                }

                System.out.println("(wav) pause button clicked");
            } else {
                // (MP3)
                if (fileName[0] == null) {
                    // show error box that there is no file dropped
                    showError("Please drag a mp3/wav file into the application first before starting!");
                } else {
                    if (isPlaying.get()) {
                        mp3.pause();
                        System.out.println("(mp3) pause button clicked");
                        isPlaying.set(false);
                        isPaused = true;
                    }
                }
            }
        });

        resetButton.addActionListener(e -> {
            if (!isMP3) {
                // (WAV)
                if (fileName[0] == null) {
                    // show error box that there is no file dropped
                    showError("Please drag a mp3/wav file into the application first before starting!");
                } else {
                    System.out.println(isPlaying.get() + " current state");

                    // reset
                    music.resetMusic();
                    fileName[0] = null;
                    isPlaying.set(false);
                    intCurrentSeconds = 0;
                    currentMinutes = 0;
                    currentSecondsFormatted[0] = "00";
                    audioLengthLabel.setText("0:00 - 0:00");
                    isPaused = false;

                    // ================== frame stuff

                    // audio duration
                    frame.remove(audioLengthLabel);
                    // file name
                    frame.remove(fileNameLabel);

                    frame.revalidate();
                    frame.repaint();

                    System.out.println("(wav) reset button clicked");
                }
            } else {
                // (MP3)
                if (fileName[0] == null) {
                    // show error box that there is no file dropped
                    showError("Please drag a mp3/wav file into the application first before starting!");
                } else {
                    mp3.getPlayList().clear();
                    fileName[0] = null;
                    isPlaying.set(false);
                    isPaused = false;

                    mp3.stop();

                    // ================== frame stuff

                    // file name
                    frame.remove(fileNameLabel);
                    frame.revalidate();
                    frame.repaint();

                    System.out.println("(mp3) reset button clicked");
                }
            }
        });

    }


}
