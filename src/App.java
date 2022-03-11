package com.musicplayer;


import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class App {

    public static void main(String[] args) {

        // States
        AtomicBoolean isPlaying = new AtomicBoolean(false);


        // Create Drag and Drop
        JTextPane myPanel = new JTextPane();
        myPanel.setText("Drag a audio file here!");
        myPanel.setBounds(0, 200, 500, 50);
        myPanel.setEditable(false);

        // label for music name
        JLabel fileNameLabel = new JLabel();
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/pictures/musicicon.png");
        ImageIcon imageIcon = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        fileNameLabel.setBounds(0, 360, 500, 50);
        fileNameLabel.setFont(new Font("Arial", Font.BOLD, 10));

//        myPanel.setLayout(null);

        // ------------------------------ Instantiating the AppMusic class
        // ain't done

        // ------------------------------ GUI

        // Create Labels
        JLabel titleHeader = new JLabel();
        titleHeader.setText("MusicPlayer");
        titleHeader.setBounds(10, 0, 200, 50);
//        titleHeader.setLayout(null);
        titleHeader.setFont(new Font("Arial", Font.BOLD, 20));

        // music
        AppMusic music = new AppMusic();


        // Create Buttons
        JButton playButton = new JButton();
        playButton.setText("Play");
        playButton.setBounds(0, 411, 166, 50);

        JButton pauseButton = new JButton();
        pauseButton.setText("Pause");
        pauseButton.setBounds(166, 411, 166, 50);

        JButton resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setBounds(332, 411, 166, 50);

        // Add components
        AppFrame frame = new AppFrame();
        frame.add(titleHeader);
        frame.add(myPanel);
        frame.add(playButton);
        frame.add(pauseButton);
        frame.add(resetButton);
        frame.add(fileNameLabel);


        String[] fileName = new String[1];
        myPanel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        fileName[0] = file.toString();
                        String splittedFileName[] = fileName[0].split("/");
                        fileNameLabel.setIcon(imageIcon);
                        fileNameLabel.setText(splittedFileName[splittedFileName.length - 1]);
                        System.out.println(fileName[0]);

                        frame.add(fileNameLabel);
                        frame.revalidate();
                        frame.repaint();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // BUTTON ACTION LISTENERS
        playButton.addActionListener(e -> {
            System.out.println(isPlaying.get() + " current state");

            if (isPlaying.get() == false) {
                if (fileName[0] == null) {
                    showError("Please drag a mp3/wav file into the application first before starting!");
                } else {

                    try {
                        music.playMusic(fileName[0]);
                        isPlaying.set(true);
                    } catch (UnsupportedAudioFileException ex) {
                        showError("Unsupported Audio File");
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        showError("Improper Data");
                        ex.printStackTrace();
                    } catch (LineUnavailableException ex) {
                        showError("Audio file is unavailable");
                        ex.printStackTrace();
                    }

                }
            }

            System.out.println("play button clicked");
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

                // frame stuff
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
}
