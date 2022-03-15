package com.musicplayer;

import javax.swing.*;
import java.awt.*;

public class AppVolumeFrame extends JFrame {

    AppVolumeFrame() {
//        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(170, 90);
        setResizable(false);
        setLocationRelativeTo(App.volumeButton);
    }
}
