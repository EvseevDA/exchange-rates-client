package com.exchangerates.client.ui.frame;

import com.exchangerates.client.ui.UI;
import com.exchangerates.client.ui.frame.util.Frame;
import com.exchangerates.util.Validator;

import javax.swing.*;

/**
 * Is the skeleton class for all frames in this project.
 * @see Frame
 * @see StartFrame
 * @see CurrenciesTableFrame
 * @see UI
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public abstract class AbstractFrame extends JFrame {

    /**
     * General method for displaying a frame.
     * It is declared final because the frame display format has been clearly selected and tested.
     * @throws NullPointerException if title() returns null
     */
    public final void display() {
        Validator.requireNonNull(title());
        Frame.prepareAndDisplay(this, title());
    }

    /**
     * Every frame that inherits this class must override this method because the display() method needs it.
     * @return title of this frame.
     */
    protected abstract String title();
}
