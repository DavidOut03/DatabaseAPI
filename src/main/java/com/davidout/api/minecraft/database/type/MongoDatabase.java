package com.davidout.api.minecraft.database.type;

import com.davidout.api.minecraft.database.Database;
import com.davidout.api.minecraft.database.Table;
import com.davidout.api.minecraft.database.result.ActionResult;
import com.davidout.api.minecraft.database.result.CallBack;
import com.davidout.api.minecraft.database.result.DatabaseDetails;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class MongoDatabase extends Database {

    private com.mongodb.client.MongoDatabase database;
    private MongoClient mongoClient;


    public MongoDatabase(String name, String host, int port, String userName, String password) {
        super(name, host, port, userName, password);
    }

    public MongoDatabase(DatabaseDetails details) {
        super(details);
    }

    @Override
    public ActionResult<Boolean> connect(Consumer<Boolean> connected, Consumer<Throwable> error) throws Exception {
        return new ActionResult<>(() -> {
            MongoClientURI connectionString;
            if(getDetails().getUserName() == null || getDetails().getPassword() == null) {
                connectionString= new MongoClientURI("mongodb://" + getDetails().getHost() + ":" + getDetails().getPort() + "/" + getDetails().getDatabaseName());
            } else {
                connectionString= new MongoClientURI("mongodb://" + getDetails().getUserName() + ":" + getDetails().getPassword() + "@" + getDetails().getHost() + ":" + getDetails().getPort() + "/" + getDetails().getDatabaseName());
            }

            this.mongoClient = new MongoClient(connectionString);
            this.database = mongoClient.getDatabase(getDetails().getDatabaseName());
            return true;
        }, connected, error);
    }

    @Override
    public ActionResult<Boolean> disconnect(Consumer<Boolean> connected, Consumer<Throwable> error) throws Exception {
        return new ActionResult<>(() -> {
                mongoClient.close();
                database = null;
                mongoClient = null;
                return true;
        }, connected, error);
    }

    @Override
    public ActionResult<Table> query(String tableName, String query, CallBack callBack) {
        return new ActionResult<>(() -> {
            MongoCollection<Document> collection = database.getCollection(tableName);
            FindIterable<Document> result = (query == null || query.equalsIgnoreCase("")) ? collection.find() : collection.find(Document.parse(query));
            return new Table(getDatabaseData(result));
        }, callBack);
    }


    private HashMap<String, List<Object>> getDatabaseData(FindIterable<Document> result) throws SQLException {
        HashMap<String, List<Object>> returned = new HashMap<>();

        for (Document document : result) {
            addColumns(returned, document.keySet());

            returned.forEach((column, values) -> {
                Object docValue = document.getOrDefault(column, null);
                addValue(returned, column, docValue);
            });

        }

        return returned;
    }

    private void addColumns(HashMap<String, List<Object>> map, Set<String> columns) {
        columns.forEach(s -> {
            if(map.containsKey(s)) return;
            map.put(s, new ArrayList<>());
        });
    }

    private void addValue(HashMap<String, List<Object>> map, String col, Object value) {
        if(map.containsKey(col)) {
            map.get(col).add(value);
            return;
        }

        map.put(col, Collections.singletonList(value));
    }

}
