package com.musicplayer;

import javax.swing.*;
import java.io.File;

public class AppTools {
    /*
    This method displays a dialog and requires 1 parameter as a string
    as the message of the error.
     */
    static void showError(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                JOptionPane.ERROR_MESSAGE);
    }

    /*
    This method allows you to get the file extension of a file.
     */
    static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf).replace(".", "");
    }
}
