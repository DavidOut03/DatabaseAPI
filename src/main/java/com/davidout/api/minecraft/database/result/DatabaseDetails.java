package com.davidout.api.minecraft.database.result;

public class DatabaseDetails {

    private final String name;
    private final String host;
    private final int port;
    private final String userName;
    private final String password;

    public DatabaseDetails(String host, String databaseName, int port, String userName, String password) {
        this.name = databaseName;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getDatabaseName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
