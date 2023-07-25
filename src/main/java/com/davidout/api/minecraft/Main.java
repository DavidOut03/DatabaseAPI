package com.davidout.api.minecraft;

import com.davidout.api.minecraft.database.DatabaseManager;
import com.davidout.api.minecraft.database.Table;
import com.davidout.api.minecraft.database.result.ActionResult;
import com.davidout.api.minecraft.database.result.CallBack;
import com.davidout.api.minecraft.database.type.MongoDatabase;
import com.davidout.api.minecraft.database.type.MySQLDatabase;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {




    public static void main(String[] args) throws Exception {
        String databaseName = "your_database_name";
        String host = "localhost";
        int mongoPort = 27017;
        int mysqlPort = 3306;
        String username = "root";
        String password = "admin";


        MySQLDatabase database = (MySQLDatabase) DatabaseManager.getDatabase(DatabaseManager.DatabaseType.MYSQL, databaseName, host, mysqlPort, username, password);
        database.connect(aBoolean -> {
            System.out.println("Connected to mysql database.");
            database.query("SELECT * FROM your_database_name.your_table_name;", table -> {
                System.out.println("These are the mysql values;");
                System.out.println(table.toString());
            }, Throwable::printStackTrace);
        });


        MongoDatabase database1 = (MongoDatabase) DatabaseManager.getDatabase(DatabaseManager.DatabaseType.MongoDB, databaseName, host, mongoPort, null, null);
        database1.connect(aBoolean -> {
            if (aBoolean) {
                System.out.println("Connected to mongo database.");

                database1.query("your_collection_name", "", table -> {
                    System.out.println("These are the mongo values;");
                    System.out.println(table.toString());
                }, throwable -> {
                    System.out.println("ERROR");
                });

            }
        }, Throwable::printStackTrace);

    }
}