package com.exchangerates.client.controller;

import com.exchangerates.util.Validator;
import com.exchangerates.util.constants.Constants;
import com.exchangerates.util.pojo.Currency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Set;

/**
 * Controls client requests to the server.
 * @since 19.0.1
 * @author Evseev Dmitry
 */
public class ClientRequestController {

    /**
     * Sends a request to the server to receive exchange rates
     * @return currencies set if request was successfully handled by server
     * @throws ClassCastException if the response received is not instance if Set (can happen if server logic is changed)
     */
    @SuppressWarnings("unchecked")
    public Set<Currency> getAllCurrencies() {
        Set<Currency> exchangeRates;
        try (Socket socket = new Socket(Constants.HOST, Constants.PORT)) {
            try (BufferedWriter writer = createWriter(socket);
                 ObjectInputStream reader = createReader(socket)) {
                sendRequest(Constants.GET_NEW_RATES_REQUEST, writer);
                Object response = getResponse(reader);
                exchangeRates = requireResponseInstanceOfT(response, Set.class);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return exchangeRates;
    }

    private static BufferedWriter createWriter(Socket socket) throws IOException {
        Validator.requireNonNull(socket);

        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private static ObjectInputStream createReader(Socket socket) throws IOException {
        Validator.requireNonNull(socket);

        return new ObjectInputStream(socket.getInputStream());
    }

    private static void sendRequest(String request, BufferedWriter writer) throws IOException {
        Validator.requireNonNull(request, writer);

        writer.write(request);
        writer.newLine();
        writer.flush();
    }

    private static Object getResponse(ObjectInputStream reader) throws IOException, ClassNotFoundException {
        Validator.requireNonNull(reader);

        return reader.readObject();
    }

    @SuppressWarnings("unchecked")
    private static <T> T requireResponseInstanceOfT(Object response, Class<T> t) {
        Validator.requireNonNull(response, t);

        if (t.isAssignableFrom(response.getClass())) {
            return (T) response;
        } else {
            throw new ClassCastException("response is not instance of " + t.getName());
        }
    }
}
