package com.exchangerates.client.ui;

import com.exchangerates.client.ui.frame.StartFrame;

import java.awt.*;

public class UI {

    private UI() {
        throw new AssertionError("No " + UI.class.getName() + " instances for you!");
    }

    public static class WindowProperties {

        private WindowProperties() {
            throw new AssertionError("No " + WindowProperties.class.getName() + " instances for you!");
        }

        private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
        private static final Dimension DIMENSION = TOOLKIT.getScreenSize();
        public static final int WIDTH = DIMENSION.width / 4 * 3;
        public static final int HEIGHT = DIMENSION.height / 4 * 3;
        public static final int POSITION_X = WIDTH / 5;
        public static final int POSITION_Y = HEIGHT / 5;
    }

    public static void showStartFrame() {
        StartFrame startFrame = StartFrame.getNewFrame();
        startFrame.display();
    }
}
