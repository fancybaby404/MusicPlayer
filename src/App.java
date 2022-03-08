package com.musicplayer;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.*;

public class App {

    public static void main(String[] args) {

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
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
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

        // Create Buttons
        JButton playButton = new JButton();
        playButton.setText("Play");
        playButton.setBounds(0, 411, 166, 50);
        playButton.addActionListener(e -> {
            if (fileName[0] == null) {
                // show error box that there is no file dropped
                showError();
            }

            System.out.println("play button clicked");
        });

        JButton pauseButton = new JButton();
        pauseButton.setText("Pause");
        pauseButton.setBounds(166, 411, 166, 50);
        pauseButton.addActionListener(e -> {
            if (fileName[0] == null) {
                // show error box that there is no file dropped
                showError();
            }
            System.out.println("pause button clicked");
        });

        JButton resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setBounds(332, 411, 166, 50);
        resetButton.addActionListener(e -> {
            if (fileName[0] == null) {
                // show error box that there is no file dropped
                showError();
            }
            System.out.println("reset button clicked");
        });

        // Add components
        AppFrame frame = new AppFrame();
        frame.add(titleHeader);
        frame.add(myPanel);
        frame.add(playButton);
        frame.add(pauseButton);
        frame.add(resetButton);
        frame.add(fileNameLabel);

        // ------------------------------ Music Player
        // AppMusic music = new AppMusic();

        // if (playButton.isEna) {

        // }
    }

    static void showError() {
        String message = "Please drag a mp3/wav file into the application first before starting!";
        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                JOptionPane.ERROR_MESSAGE);
    }
}
