package com.exchangerates.client.ui.frame.util;

import com.exchangerates.client.ui.UI;
import com.exchangerates.client.ui.frame.AbstractFrame;
import com.exchangerates.client.ui.frame.CurrenciesTableFrame;
import com.exchangerates.client.ui.frame.StartFrame;
import com.exchangerates.util.Validator;

import javax.swing.*;
import java.util.Objects;

/**
 * A util class that aggregates methods that set common parameters for all frames in this project.
 * @see AbstractFrame
 * @see StartFrame
 * @see CurrenciesTableFrame
 * @see UI
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public final class Frame {

    private Frame() {
        throw new AssertionError("No " + Frame.class.getName() + " instances for you!");
    }

    /**
     * Sets the position and size of the frame relative to the user's monitor settings.
     * Also sets the frame title and default close operation.
     * @param frame on which the parameters will be set
     * @param title which will be set on gaven frame
     * @throws NullPointerException if frame or title is null
     */
    public static void prepare(JFrame frame, String title) {
        Validator.requireNonNull(frame, title);

        frame.setBounds(UI.WindowProperties.POSITION_X,
                UI.WindowProperties.POSITION_Y,
                UI.WindowProperties.WIDTH,
                UI.WindowProperties.HEIGHT);

        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets the frame visibility to true
     * @param frame on which visibility will be set to true
     * @throws NullPointerException if frame is null
     */
    public static void display(JFrame frame) {
        Objects.requireNonNull(frame).setVisible(true);
    }

    /**
     * Sets the position and size of the frame relative to the user's monitor settings.
     * Also sets the frame title, default close operation and visibility (to true).
     * @param frame on which parameters will be set
     * @param title which will be set on gaven frame
     * @throws NullPointerException if frame or title is null
     */
    public static void prepareAndDisplay(JFrame frame, String title) {
        prepare(frame, title);
        display(frame);
    }
}
