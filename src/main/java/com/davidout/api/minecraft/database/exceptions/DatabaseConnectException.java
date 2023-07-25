package com.davidout.api.minecraft.database.exceptions;

import com.davidout.api.minecraft.database.result.DatabaseDetails;

public class DatabaseConnectException extends Exception {

    public DatabaseConnectException(DatabaseDetails details, String e) throws Exception {
        throw new Exception("Could not connect to database: " + details.getDatabaseName() + " because of an exception: " + e);
    }
}
