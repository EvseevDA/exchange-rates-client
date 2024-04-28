package com.exchangerates.client.ui.frame.util;

import com.exchangerates.client.ui.UI;
import com.exchangerates.util.Validator;

import javax.swing.*;
import java.util.Objects;

public class Frame {

    private Frame() {
        throw new AssertionError("No " + Frame.class.getName() + " instances for you!");
    }

    public static void prepare(JFrame frame, String title) {
        Validator.requireNonNull(frame, title);

        frame.setBounds(UI.WindowProperties.POSITION_X,
                UI.WindowProperties.POSITION_Y,
                UI.WindowProperties.WIDTH,
                UI.WindowProperties.HEIGHT);

        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void display(JFrame frame) {
        Objects.requireNonNull(frame).setVisible(true);
    }

    public static void prepareAndDisplay(JFrame frame, String title) {
        prepare(frame, title);
        display(frame);
    }
}
