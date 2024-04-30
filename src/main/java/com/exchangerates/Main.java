package com.exchangerates;

import com.exchangerates.client.controller.ClientRequestController;
import com.exchangerates.client.service.CurrencyService;
import com.exchangerates.client.ui.UI;
import com.exchangerates.client.ui.frame.AbstractFrame;
import com.exchangerates.client.ui.frame.CurrenciesTableFrame;
import com.exchangerates.client.ui.frame.StartFrame;
import com.exchangerates.client.ui.frame.util.Frame;

/**
 * This defines a method that executes the client's work.
 * @see ClientRequestController
 * @see CurrencyService
 * @see UI
 * @see Frame
 * @see AbstractFrame
 * @see StartFrame
 * @see CurrenciesTableFrame
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public class Main {

    /**
     * Shows the start frame (aka home page)
     * @param args not used
     */
    public static void main(String[] args) {
        UI.showStartFrame();
    }
}