package com.davidout.api.minecraft.database.exceptions;

import com.davidout.api.minecraft.database.result.DatabaseDetails;

public class DatabaseDisconnectException extends Exception {

    public DatabaseDisconnectException(DatabaseDetails details, String e) throws Exception {
        throw new Exception("Could not disconnect from the database: " + details.getDatabaseName() + " because of an exception: " + e);
    }
}
