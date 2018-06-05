package ru.scorpio92.mpgp.data.model;

public class BaseMessage {

    public enum Type {
        UNKNOWN,
        REGISTER,
        AUTHORIZE,
        DEAUTHORIZE,
        CHECK_TOKEN
    }

    public enum Status {
        SUCCESS,
        ERROR
    }

    private String type;
    private String status;
    private String clientData;
    private String serverData;

    public BaseMessage() {
    }

    public BaseMessage(Type type) {
        this.type = type.name();
    }

    public Type getType() throws Exception {
        return Enum.valueOf(Type.class, type);
    }

    public Status getStatus() throws Exception {
        return Enum.valueOf(Status.class, status);
    }

    public String getClientData() {
        return clientData;
    }

    public String getServerData() {
        return serverData;
    }


    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    public void setServerData(String serverData) {
        this.serverData = serverData;
    }
}
