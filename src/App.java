package com.musicplayer;



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
import javax.swing.plaf.basic.BasicButtonUI;


import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class App {

    static String[] fileName = new String[1];
    static Integer intCurrentSeconds = 0;
    static int currentMinutes = 0;

    public static void main(String[] args) {

        final String[] currentSecondsFormatted = new String[1];

        // States
        AtomicBoolean isPlaying = new AtomicBoolean(false);

        // Create Drag and Drop
        JTextPane myPanel = new JTextPane();
        myPanel.setText("Drag a audio file here!");
        myPanel.setBounds(0, 200, 500, 50);
        myPanel.setEditable(false);

        // ------------------------------ GUI

        // Create Labels
        JLabel titleHeader = new JLabel();
        titleHeader.setText("MusicPlayer");
        titleHeader.setBounds(10, 0, 200, 50);
        titleHeader.setFont(new Font("Arial", Font.BOLD, 20));

        // label for audio length
        JLabel audioLengthLabel = new JLabel();
        audioLengthLabel.setBounds(220, 380, 200, 50);

        // label for music name
        JLabel fileNameLabel = new JLabel();
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\musicicon.png");
        ImageIcon imageIcon = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        fileNameLabel.setBounds(0, 360, 500, 50);
        fileNameLabel.setFont(new Font("Arial", Font.BOLD, 10));

        // imageicons for buttons
        ImageIcon playIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\play.png");
        ImageIcon playImageIcon = new ImageIcon(playIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        ImageIcon pauseIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\pause.png");
        ImageIcon pauseImageIcon = new ImageIcon(pauseIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        ImageIcon resetIcon = new ImageIcon(System.getProperty("user.dir") + "\\pictures\\reset.png");
        ImageIcon resetImageIcon = new ImageIcon(resetIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

        System.out.println(System.getProperty("user.dir") + "\\pictures\\stop-outline.png");

        // music
        AppMusic music = new AppMusic();

        // Create Buttons
        JButton playButton = new JButton();
//        playButton.setText("Play");
        playButton.setIcon(playImageIcon);
        playButton.setBounds(0, 411, 166, 50);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setUI(new BasicButtonUI());

        JButton pauseButton = new JButton();
//        pauseButton.setText("Pause");
        pauseButton.setIcon(pauseImageIcon);
        pauseButton.setBounds(166, 411, 166, 50);
        pauseButton.setBorderPainted(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setUI(new BasicButtonUI());

        JButton resetButton = new JButton();
//        resetButton.setText("Reset");
        resetButton.setIcon(resetImageIcon);
        resetButton.setBounds(332, 411, 166, 50);
        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setUI(new BasicButtonUI());

        // Add components
        AppFrame frame = new AppFrame();
        frame.add(titleHeader);
        frame.add(myPanel);
        frame.add(playButton);
        frame.add(pauseButton);
        frame.add(resetButton);
        frame.add(fileNameLabel);


        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (music.clip == null) {
                    return;
                }

                if (isPlaying.get() == true) {
                    System.out.println("HERHSERSHE: " + isPlaying.get());
                    System.out.println("IS PLAYING!!!");

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
                    // System.out.println(audioLengthFormatted);

                    // change gui
                    audioLengthLabel.setText(currentMinutes + ":" + currentSecondsFormatted[0] + " - " + audioLengthFormatted);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }).start();

        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying.get()) {
                    intCurrentSeconds++;
                }
            }
        }).start();

        // drop functionality
        myPanel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {

                        // === CHECK IF FILE IS VALID
                        String fileExtension = getFileExtension(file);
                        if (fileExtension.equals("wav") || fileExtension.equals("mp3")) {
                            // do nothing
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
                        frame.add(audioLengthLabel);

                        frame.revalidate();
                        frame.repaint();




                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ================= BUTTON ACTION LISTENERS
        playButton.addActionListener(e -> {
            System.out.println(isPlaying.get() + " current state");

            if (!isPlaying.get()) {
                if (fileName[0] == null) {
                    showError("Please drag a mp3/wav file into the application first before starting!");
                } else {
                    music.playMusic(fileName[0]);
                    isPlaying.set(true);

                }
            }
            System.out.println("play button clicked");
            System.out.println(isPlaying.get());
        });

        pauseButton.addActionListener(e -> {
            if (fileName[0] == null) {
                // show error box that there is no file dropped
                showError("Please drag a mp3/wav file into the application first before starting!");
            } else {
                if (isPlaying.get() == true) {
                    System.out.println(isPlaying.get() + " current state");
                    music.pauseMusic();
                    isPlaying.set(false);
                }
            }

            System.out.println("pause button clicked");
        });

        resetButton.addActionListener(e -> {
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

                // ================== frame stuff

                // audio duration
                frame.remove(audioLengthLabel);
                // file name
                frame.remove(fileNameLabel);

                frame.revalidate();
                frame.repaint();

                System.out.println("reset button clicked");
            }

        });
    }


    static void showError(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                JOptionPane.ERROR_MESSAGE);
    }

    static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf).replace(".", "");
    }
}
