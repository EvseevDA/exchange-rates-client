package com.exchangerates.client.ui;

import com.exchangerates.client.ui.frame.AbstractFrame;
import com.exchangerates.client.ui.frame.CurrenciesTableFrame;
import com.exchangerates.client.ui.frame.StartFrame;

import java.awt.*;

/**
 * Util class which defines general properties and methods related to user interface.
 * @see com.exchangerates.client.ui.frame.util.Frame
 * @see AbstractFrame
 * @see StartFrame
 * @see CurrenciesTableFrame
 */
public class UI {

    private UI() {
        throw new AssertionError("No " + UI.class.getName() + " instances for you!");
    }

    /**
     * Util class which defines the window properties e.g. width and height of the window.
     */
    public static class WindowProperties {

        private WindowProperties() {
            throw new AssertionError("No " + WindowProperties.class.getName() + " instances for you!");
        }

        private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
        private static final Dimension DIMENSION = TOOLKIT.getScreenSize();

        /**
         * The width of the window
         */
        public static final int WIDTH = DIMENSION.width / 4 * 3;

        /**
         * The height of the window
         */
        public static final int HEIGHT = DIMENSION.height / 4 * 3;

        /**
         * X position of the window
         */
        public static final int POSITION_X = WIDTH / 5;

        /**
         * Y position of the window
         */
        public static final int POSITION_Y = HEIGHT / 5;
    }

    /**
     * Creates and shows the first frame which displayed on user's monitor
     */
    public static void showStartFrame() {
        StartFrame startFrame = StartFrame.getNewFrame();
        startFrame.display();
    }
}
