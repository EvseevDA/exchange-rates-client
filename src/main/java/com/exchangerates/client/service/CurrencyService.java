package com.exchangerates.client.service;

import com.exchangerates.client.controller.ClientRequestController;
import com.exchangerates.util.Validator;
import com.exchangerates.util.pojo.Currency;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class CurrencyService {
    private static final Set<Currency> exchangeRates = new ClientRequestController().getAllCurrencies();

    public static Set<Currency> getAll() {
        return exchangeRates;
    }

    public static Currency getByDigitalCode(String digitalCode) {
        return getCurrencyByProperty(digitalCode, Currency::getDigitalCode);
    }

    public static Set<Currency> getAllMatchedWithDigitalCodes(List<String> digitalCodes) {
        return getAllMatchedWithProperties(digitalCodes, Currency::getDigitalCode);
    }

    public static Currency getByLetterCode(String letterCode) {
        return getCurrencyByProperty(letterCode, Currency::getLetterCode);
    }

    public static Set<Currency> getAllMatchedWithLetterCodes(List<String> letterCodes) {
        return getAllMatchedWithProperties(letterCodes, Currency::getLetterCode);
    }

    public static Currency getByName(String currencyName) {
        return getCurrencyByProperty(currencyName, Currency::getCurrencyName);
    }

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
