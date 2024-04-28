package com.exchangerates.client.ui.frame;

import com.exchangerates.client.ui.frame.util.Frame;
import com.exchangerates.util.Validator;

import javax.swing.*;

public abstract class AbstractFrame extends JFrame {

    public final void display() {
        Validator.requireNonNull(title());
        Frame.prepareAndDisplay(this, title());
    }

    protected abstract String title();
}
