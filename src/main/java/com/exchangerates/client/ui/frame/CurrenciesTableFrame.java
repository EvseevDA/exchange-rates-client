package com.exchangerates.client.ui.frame;

import com.exchangerates.client.service.CurrencyService;
import com.exchangerates.client.ui.UI;
import com.exchangerates.client.ui.frame.util.Frame;
import com.exchangerates.util.Validator;
import com.exchangerates.util.pojo.Currency;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Class of frame on which currencies table is displayed.
 * @see Frame
 * @see AbstractFrame
 * @see StartFrame
 * @see UI
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public final class CurrenciesTableFrame extends AbstractFrame {

    private CurrenciesTableFrame() {
    }

    /**
     * title of this frame
     */
    public static final String TITLE = "Currencies";

    /**
     * @return title of this frame
     */
    @Override
    protected String title() {
        return TITLE;
    }

    /**
     * Creates and configures currencies table frame
     * @param chosenCurrencies currencies which will be displayed in the table
     * @param parent needed to be able to go back to parent page
     * @return created frame
     * @throws NullPointerException if chosenCurrencies or parent is null
     */
    public static CurrenciesTableFrame
    getNewFrame(List<String> chosenCurrencies, StartFrame parent) {
        Validator.requireNonNull(chosenCurrencies, parent);

        CurrenciesTableFrame frame = new CurrenciesTableFrame();
        JPanel panel = new JPanel();

        List<Currency> currenciesToDisplay = restoreCurrencies(chosenCurrencies);

        JTable currenciesTable = new JTable();
        initCurrenciesTable(currenciesTable, currenciesToDisplay);

        JButton goToHomePageButton = new JButton(Constants.GO_TO_HOME_PAGE_LABEL);
        initActionListenerForGoToHomePageButton(goToHomePageButton, parent, frame);

        panel.add(new JScrollPane(currenciesTable));
        panel.add(goToHomePageButton);
        frame.setContentPane(panel);

        return frame;
    }

    private static void
    initActionListenerForGoToHomePageButton(JButton goToStartPageButton,
                                            StartFrame parent,
                                            CurrenciesTableFrame current) {
        Validator.requireNonNull(goToStartPageButton, parent, current);

        goToStartPageButton.addActionListener(e -> {
            parent.setVisible(true);
            current.setVisible(false);
        });
    }

    private static void initCurrenciesTable(JTable table, List<Currency> neededCurrencies) {
        Validator.requireNonNull(table, neededCurrencies);

        DefaultTableModel tableModel = new DefaultTableModel();
        table.setModel(tableModel);

        initTableColumnNames(tableModel);
        initTableData(tableModel, neededCurrencies);

        table.setModel(tableModel);
        table.setEnabled(false);
    }

    private static void initTableColumnNames(DefaultTableModel tableModel) {
        Validator.requireNonNull(tableModel);

        tableModel.addColumn(Constants.DIGITAL_CODE_COLUMN_NAME);
        tableModel.addColumn(Constants.LETTER_CODE_COLUMN_NAME);
        tableModel.addColumn(Constants.UNIT_COLUMN_NAME);
        tableModel.addColumn(Constants.CURRENCY_COLUMN_NAME);
        tableModel.addColumn(Constants.RATE_COLUMN_NAME);
    }

    private static void initTableData(DefaultTableModel tableModel, List<Currency> neededCurrencies) {
        Validator.requireNonNull(tableModel, neededCurrencies);

        for (Currency c : neededCurrencies) {
            String[] rowData = new String[5];
            rowData[0] = c.getDigitalCode();
            rowData[1] = c.getLetterCode();
            rowData[2] = String.valueOf(c.getUnit());
            rowData[3] = c.getCurrencyName();
            rowData[4] = Util.toUiFormat(c.getRate());
            tableModel.addRow(rowData);
        }
    }

    private static List<String> getCurrenciesLetterCodesFromChosenCurrencies(List<String> chosenCurrencies) {
        Validator.requireNonNull(chosenCurrencies);

        return chosenCurrencies.stream().map(c -> {
            int endIndex = c.length() - 1;
            int beginIndex = endIndex - 3; // 3 is currency code length

            return c.substring(beginIndex, endIndex); // currency code
        }).toList();
    }

    private static List<Currency> restoreCurrencies(List<String> chosenCurrencies) {
        Validator.requireNonNull(chosenCurrencies);

        List<String> codes = getCurrenciesLetterCodesFromChosenCurrencies(chosenCurrencies);

        return CurrencyService.getAllMatchedWithLetterCodes(codes).stream().toList();
    }

    private static class Util {
        private static String toUiFormat(BigDecimal rate) {
            Validator.requireNonNull(rate);

            return String.valueOf(rate.setScale(Constants.RATE_SCALE, RoundingMode.UP)) + Constants.RUBLE_CHAR;
        }
    }

    private static class Constants {
        private static final String GO_TO_HOME_PAGE_LABEL = "go to home page";
        private static final String DIGITAL_CODE_COLUMN_NAME = "Digital code";
        private static final String LETTER_CODE_COLUMN_NAME = "Letter code";
        private static final String UNIT_COLUMN_NAME = "Unit";
        private static final String CURRENCY_COLUMN_NAME = "Currency";
        private static final String RATE_COLUMN_NAME = "Rate";
        private static final char RUBLE_CHAR = '₽';
        private static final int RATE_SCALE = 2;

    }
}
