package com.davidout.api.minecraft.database.exceptions;

public class TableCreateException extends Exception {

    public TableCreateException(String table, String error) throws Exception {
        throw new Exception("Could not create the table: " + table + " because of an exception: " + error);
    }
}
