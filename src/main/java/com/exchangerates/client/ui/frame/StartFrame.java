package com.exchangerates.client.ui.frame;

import com.exchangerates.client.service.CurrencyService;
import com.exchangerates.client.ui.UI;
import com.exchangerates.client.ui.frame.util.Frame;
import com.exchangerates.util.Validator;
import com.exchangerates.util.pojo.Currency;

import javax.swing.*;
import java.util.*;

/**
 * Class of first frame which displayed on user's monitor.
 * @see Frame
 * @see AbstractFrame
 * @see CurrenciesTableFrame
 * @see UI
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public final class StartFrame extends AbstractFrame {

    /**
     * title of this frame
     */
    public static final String TITLE = "Home";

    private StartFrame() {
    }

    /**
     * @return title of this frame
     */
    @Override
    protected String title() {
        return TITLE;
    }

    /**
     * Creates and configures a new frame.
     * @return created frame
     */
    public static StartFrame getNewFrame() {
        StartFrame startFrame = new StartFrame();
        JPanel panel = new JPanel();

        Set<Currency> currencies = CurrencyService.getAll();
        JList<String> currenciesToChoose = new JList<>();
        DefaultListModel<String> currenciesToChooseModel = new DefaultListModel<>();
        initCurrenciesToChooseList(currenciesToChoose, currenciesToChooseModel, currencies);

        JList<String> chosenCurrencies = new JList<>();
        DefaultListModel<String> chosenCurrenciesModel = new DefaultListModel<>();
        chosenCurrencies.setModel(chosenCurrenciesModel);

        JButton addButton = new JButton(Constants.ADD_BUTTON_LABEL);
        initActionListenerForAddButton(addButton, currenciesToChoose, currenciesToChooseModel, chosenCurrenciesModel);

        JButton removeButton = new JButton(Constants.REMOVE_BUTTON_LABEL);
        initActionListenerForRemoveButton(removeButton, chosenCurrencies, chosenCurrenciesModel, currenciesToChooseModel);

        JButton goToCurrenciesTableButton = new JButton(Constants.GO_TO_CURRENCIES_TABLE_BUTTON_LABEL);
        initActionListenerForGoToCurrenciesTableButton(goToCurrenciesTableButton, chosenCurrenciesModel, startFrame);

        panel.add(new JScrollPane(currenciesToChoose));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(new JScrollPane(chosenCurrencies));
        panel.add(goToCurrenciesTableButton);
        startFrame.setContentPane(panel);

        return startFrame;
    }

    private static void
    initCurrenciesToChooseList(JList<String> currenciesToChoose,
                               DefaultListModel<String> currenciesToChooseModel,
                               Set<Currency> allCurrencies) {
        Validator.requireNonNull(currenciesToChoose, currenciesToChooseModel, allCurrencies);

        currenciesToChooseModel.addAll(allCurrencies.stream()
                .map(StartFrame.Util::toUiFormat)
                .toList());
        currenciesToChoose.setModel(currenciesToChooseModel);
    }

    private static void initActionListenerForAddButton(JButton addButton, JList<String> currenciesToChoose,
                                                       DefaultListModel<String> currenciesToChooseModel,
                                                       DefaultListModel<String> chosenCurrenciesModel) {
        Validator.requireNonNull(addButton, currenciesToChoose, currenciesToChooseModel, chosenCurrenciesModel);

        addButton.addActionListener(e -> {
            Util.transferAndSort(new Util.TransferComponents(currenciesToChoose,
                    currenciesToChooseModel, chosenCurrenciesModel));
        });
    }

    private static void
    initActionListenerForRemoveButton(JButton removeButton, JList<String> chosenCurrencies,
                                      DefaultListModel<String> chosenCurrenciesModel,
                                      DefaultListModel<String> currenciesToChooseModel) {
        Validator.requireNonNull(removeButton, chosenCurrencies, currenciesToChooseModel, chosenCurrenciesModel);

        removeButton.addActionListener(e -> {
            Util.transferAndSort(new Util.TransferComponents(chosenCurrencies,
                    chosenCurrenciesModel, currenciesToChooseModel));
        });
    }

    private static void
    initActionListenerForGoToCurrenciesTableButton(JButton goToCurrenciesTableButton,
                                                   DefaultListModel<String> chosenCurrenciesModel,
                                                   StartFrame current) {
        Validator.requireNonNull(goToCurrenciesTableButton, chosenCurrenciesModel, current);

        goToCurrenciesTableButton.addActionListener(e -> {
            List<String> currenciesToDisplay = new ArrayList<>();
            int size = chosenCurrenciesModel.size();
            for (int i = 0; i < size; i++) {
                currenciesToDisplay.add(chosenCurrenciesModel.get(i));
            }

            Frame.prepareAndDisplay(
                    CurrenciesTableFrame.getNewFrame(currenciesToDisplay, current),
                    CurrenciesTableFrame.TITLE);
            current.setVisible(false);
        });
    }

    private static class Constants {
        private Constants() {
            throw new AssertionError("No " + Constants.class.getName() + " instances for you!");
        }

        private static final String ADD_BUTTON_LABEL = "->";
        private static final String REMOVE_BUTTON_LABEL = "<-";
        private static final String GO_TO_CURRENCIES_TABLE_BUTTON_LABEL = "Go to currencies table";
    }

    private static class Util {

        private Util() {
            throw new AssertionError("No " + Util.class.getName() + " instances for you!");
        }

        private static String toUiFormat(Currency currency) {
            Validator.requireNonNull(currency);
            return currency.getCurrencyName() + " (" + currency.getLetterCode() + ")";
        }

        private record TransferComponents(JList<String> source, DefaultListModel<String> sourceModel,
                                          DefaultListModel<String> destModel) {

            TransferComponents(JList<String> source, DefaultListModel<String> sourceModel,
                               DefaultListModel<String> destModel) {
                this.source = Objects.requireNonNull(source);
                this.sourceModel = Objects.requireNonNull(sourceModel);
                this.destModel = Objects.requireNonNull(destModel);
            }

        }

        private static void transfer(TransferComponents transferComponents) {
            Validator.requireNonNull(transferComponents);

            List<String> selectedItems = transferComponents.source.getSelectedValuesList();
            transferComponents.destModel.addAll(selectedItems);

            for (String s : selectedItems) {
                transferComponents.sourceModel.removeElement(s);
            }
        }

        private static void transferAndSort(TransferComponents transferComponents) {
            transfer(transferComponents);

            sort(transferComponents.sourceModel);
            sort(transferComponents.destModel);
        }

        private static void sort(DefaultListModel<String> model) {
            Validator.requireNonNull(model);

            int size = model.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (model.get(i).compareTo(model.get(j)) > 0) {
                        String temp = model.get(i);
                        model.set(i, model.get(j));
                        model.set(j, temp);
                    }
                }
            }
        }
    }
}
