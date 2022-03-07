package com.musicplayer;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class App {
    public static void main(String[] args) {
        // ------------------------------ GUI

        // Create Labels
        JLabel titleHeader = new JLabel();
        titleHeader.setText("MusicPlayer");
        titleHeader.setBounds(10, 0, 200, 50);
        titleHeader.setLayout(null);
        titleHeader.setFont(new Font("Arial", Font.BOLD, 20));

        // Create Buttons
        JButton playButton = new JButton();
        playButton.setText("Play");
        playButton.setBounds(0, 411, 166, 50);
        playButton.addActionListener(e -> System.out.println("play button clicked"));

        JButton pauseButton = new JButton();
        pauseButton.setText("Pause");
        pauseButton.setBounds(166, 411, 166, 50);
        pauseButton.addActionListener(e -> System.out.println("pause button clicked"));

        JButton resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setBounds(332, 411, 166, 50);
        resetButton.addActionListener(e -> System.out.println("reset button clicked"));

        // Create Drag and Drop
        // JTextArea myPanel = new JTextArea();
        // myPanel.setDropTarget(new DropTarget() {
        // public synchronized void drop(DropTargetDropEvent evt) {
        // try {
        // evt.acceptDrop(DnDConstants.ACTION_COPY);
        // List<File> droppedFiles = (List<File>) evt.getTransferable()
        // .getTransferData(DataFlavor.javaFileListFlavor);
        // for (File file : droppedFiles) {
        // process files
        // }
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        // }
        // });

        // Add components
        AppFrame frame = new AppFrame();
        frame.add(titleHeader);
        frame.add(playButton);
        frame.add(pauseButton);
        frame.add(resetButton);

        // ------------------------------ Music Player
        // AppMusic music = new AppMusic();

        // if (playButton.isEna) {

        // }
    }
}
