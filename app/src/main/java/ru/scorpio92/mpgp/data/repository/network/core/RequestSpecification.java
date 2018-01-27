package ru.scorpio92.mpgp.data.repository.network.core;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import ru.scorpio92.mpgp.data.entity.message.base.BaseMessage;

/**
 * Created by scorpio92 on 1/3/18.
 */

public abstract class RequestSpecification {

    public enum REQUEST_TYPE {
        GET,
        POST
    }

    private REQUEST_TYPE requestType;
    private String url;
    private List<Pair<String, String>> headers = new ArrayList<>();
    private BaseMessage body;
    private int connectionTimeout = 30000;
    private int readTimeout = 30000;

    public RequestSpecification(String url) {
        this.requestType = REQUEST_TYPE.GET;
        this.url = url;
    }

    public RequestSpecification(String url, BaseMessage body) {
        this.requestType = REQUEST_TYPE.POST;
        this.url = url;
        this.body = body;
    }

    public void setHeaders(List<Pair<String, String>> headers) {
        this.headers = headers;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public REQUEST_TYPE getRequestType() {
        return requestType;
    }

    public String getUrl() {
        return url;
    }

    public List<Pair<String, String>> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body.toString();
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }
}
