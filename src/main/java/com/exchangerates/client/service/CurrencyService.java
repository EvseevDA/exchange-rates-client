package com.exchangerates.client.service;

import com.exchangerates.client.controller.ClientRequestController;
import com.exchangerates.util.Validator;
import com.exchangerates.util.pojo.Currency;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Aggregates methods related to obtaining exchange rates: a complete set or by any parameter.
 * @see ClientRequestController
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public class CurrencyService {

    private static final Set<Currency> exchangeRates = new ClientRequestController().getAllCurrencies();

    /**
     * @return currencies set which got from ClientRequestController
     * @see ClientRequestController
     */
    public static Set<Currency> getAll() {
        return exchangeRates;
    }

    /**
     * @param digitalCode by which an attempt will be made to search for currency
     * @return currency with gaven digital code
     * @throws NoSuchElementException if currency wasn't found
     */
    public static Currency getByDigitalCode(String digitalCode) {
        return getCurrencyByProperty(digitalCode, Currency::getDigitalCode);
    }

    /**
     * @param digitalCodes by which an attempt will be made to search for currencies
     * @return currencies with given digital codes
     * @throws NoSuchElementException if at least one currency wasn't found
     */
    public static Set<Currency> getAllMatchedWithDigitalCodes(List<String> digitalCodes) {
        return getAllMatchedWithProperties(digitalCodes, Currency::getDigitalCode);
    }

    /**
     * @param letterCode by which an attempt will be made to search for currency
     * @return currency with gaven letter code
     * @throws NoSuchElementException if currency wasn't found
     */
    public static Currency getByLetterCode(String letterCode) {
        return getCurrencyByProperty(letterCode, Currency::getLetterCode);
    }

    /**
     * @param letterCodes by which an attempt will be made to search for currencies
     * @return currencies with given letter codes
     * @throws NoSuchElementException if at least one currency wasn't found
     */
    public static Set<Currency> getAllMatchedWithLetterCodes(List<String> letterCodes) {
        return getAllMatchedWithProperties(letterCodes, Currency::getLetterCode);
    }

    /**
     * @param currencyName by which an attempt will be made to search for currency
     * @return currency with gaven currency name
     * @throws NoSuchElementException if currency wasn't found
     */
    public static Currency getByName(String currencyName) {
        return getCurrencyByProperty(currencyName, Currency::getCurrencyName);
    }

    /**
     * @param currencyNames by which an attempt will be made to search for currencies
     * @return currencies with given currency names
     * @throws NoSuchElementException if at least one currency wasn't found
     */
    public static Set<Currency> getAllMatchedWithNames(List<String> currencyNames) {
        return getAllMatchedWithProperties(currencyNames, Currency::getCurrencyName);
    }

    private static <T> Currency
    getCurrencyByProperty(
            T property,
            CurrencyPropertySupplier<T> currencyPropertySupplier
    ) {
        Validator.requireNonNull(property, currencyPropertySupplier);

        for (Currency c : exchangeRates) {
            if (property.equals(currencyPropertySupplier.get(c))) {
                return c;
            }
        }

        throw new NoSuchElementException("Currency not found.");
    }

    private static <T> Set<Currency>
    getAllMatchedWithProperties(
            List<T> properties,
            CurrencyPropertySupplier<T> currencyPropertySupplier
    ) {
        Validator.requireNonNull(properties);
        return properties.stream()
                .map(p -> getCurrencyByProperty(p, currencyPropertySupplier))
                .collect(Collectors.toSet());
    }

    @FunctionalInterface
    private interface CurrencyPropertySupplier<T> {
        T get(Currency c);
    }
}
