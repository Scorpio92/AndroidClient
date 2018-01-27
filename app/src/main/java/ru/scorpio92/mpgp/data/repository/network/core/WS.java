package ru.scorpio92.mpgp.data.repository.network.core;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import ru.scorpio92.mpgp.Constants;
import ru.scorpio92.mpgp.data.entity.message.base.BaseMessage;
import ru.scorpio92.mpgp.util.JsonWorker;
import ru.scorpio92.mpgp.util.Logger;

/**
 * Created by scorpio92 on 1/7/18.
 */

public class WS {

    private static WebSocketClient webSocketClient;

    private Callback callback;

    public WS() {

    }

    public void registerCallback(Callback callback) {
        this.callback = callback;
    }

    public void unregisterCallback() {
        this.callback = null;
    }

    public void open()  {
        if(webSocketClient == null || !webSocketClient.isOpen()) {
            URI uri;
            try {
                uri = new URI("ws://" + Constants.MAIN_SERVER + ":" + Constants.MAIN_SERVER_PORT);
            } catch (URISyntaxException e) {
                Logger.error(e);
                return;
            }

            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Logger.log("Websocket", "Opened");
                    if(callback != null)
                        callback.onOpen();
                }

                @Override
                public void onMessage(String s) {
                    if (s != null) {
                        Logger.log("Websocket", "onMessage: " + s);
                        if(callback != null)
                            callback.onMessage(s);
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Logger.log("Websocket", "Closed " + s);
                }

                @Override
                public void onError(Exception e) {
                    Logger.log("Websocket", "Error " + e.getMessage());
                    if(callback != null)
                        callback.onError(e);
                }
            };
            webSocketClient.connect();
        }
    }

    public void sendMessage(BaseMessage message) {
        webSocketClient.send(JsonWorker.getSerializeJson(message));
    }

    public static void finish() {
        if(webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }

    public interface Callback {
        void onOpen();
        void onMessage(String message);
        void onError(Exception e);
    }
}
