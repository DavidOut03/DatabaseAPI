package com.davidout.api.minecraft.database;

import com.davidout.api.minecraft.database.result.DatabaseDetails;
import com.davidout.api.minecraft.database.type.MongoDatabase;
import com.davidout.api.minecraft.database.type.MySQLDatabase;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DatabaseManager {

    private static final ThreadPoolExecutor databaseThread = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    public static ThreadPoolExecutor getDatabaseTaskHandler() {
        return databaseThread;
    }


    public static Database getDatabase(DatabaseType type, String databaseName, String host, int port, String userName, String password) {
        return getDatabase(type, new DatabaseDetails(host, databaseName, port, userName, password));
    }

    public static Database getDatabase(DatabaseType type, DatabaseDetails details) {
        return switch (type) {
            case MYSQL -> new MySQLDatabase(details);
            case MongoDB -> new MongoDatabase(details);
        };
    }


        public enum DatabaseType {
            MYSQL, MongoDB;
        }
}

