package ru.scorpio92.mpgp.data.repository.network.core;

import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.scorpio92.mpgp.util.Logger;


/**
 * Created by scorpio92 on 1/3/18.
 */

public abstract class NetworkRepository {

    protected void makeRequest(RequestSpecification requestSpecification, NetworkCallback callback) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(requestSpecification.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            for (Pair<String, String> header : requestSpecification.getHeaders())
                connection.setRequestProperty(header.first, header.second);
            connection.setConnectTimeout(requestSpecification.getConnectionTimeout());
            connection.setReadTimeout(requestSpecification.getReadTimeout());
            switch (requestSpecification.getRequestType()) {
                case GET:
                    Logger.log("request", "url: " + requestSpecification.getUrl());
                    connection.setDoOutput(false);
                    connection.setRequestMethod("GET");
                    break;
                case POST:
                    Logger.log("request", "url: " + requestSpecification.getUrl() + ", data: " + requestSpecification.getBody());
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-MessageType","application/json");
                    OutputStream os = connection.getOutputStream();
                    byte[] data = requestSpecification.getBody().getBytes("UTF-8");
                    os.write(data);
                    break;
            }
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf8"));
                StringBuilder response = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                try {
                    reader.close();
                } catch (IOException ioe) {
                    Logger.error(ioe);
                }

                String responseFinal = response.toString();
                Logger.log("response", responseFinal);

                if (callback != null)
                    callback.onSuccess(responseFinal);

            } else {
                if (callback != null)
                    callback.onError(new BadResponseCodeException(responseCode));
            }

        } catch (Exception e) {
            Logger.error(e);
            if (callback != null)
                callback.onError(new BadRequestException());
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
}
