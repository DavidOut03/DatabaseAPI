package com.davidout.api.minecraft.database.type;

import com.davidout.api.minecraft.database.Database;
import com.davidout.api.minecraft.database.Table;
import com.davidout.api.minecraft.database.result.ActionResult;
import com.davidout.api.minecraft.database.result.CallBack;
import com.davidout.api.minecraft.database.result.DatabaseDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class MySQLDatabase extends Database {

    private Connection connection;

    public MySQLDatabase(String name, String host, int port, String userName, String password) {
        super(name, host, port, userName, password);
    }

    public MySQLDatabase(DatabaseDetails details) {
        super(details);
    }

    @Override
    public ActionResult<Boolean> connect(Consumer<Boolean> connected, Consumer<Throwable> error) throws Exception {
        return new ActionResult<>(() -> {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://$host/$database".replace("$host", getDetails().getHost()).replace("$database", getDetails().getDatabaseName()), getDetails().getUserName(), getDetails().getPassword());
            return true;
        }, connected, error);
    }

    @Override
    public ActionResult<Boolean> disconnect(Consumer<Boolean> connected, Consumer<Throwable> error) throws Exception {
        return new ActionResult<>( () -> {
            connection.close();
            connection = null;
            return true;
        }, connected, error);
    }


    @Override
    public ActionResult<Table> query(String collection, String query, CallBack callBack) {
        return new ActionResult<>(() -> {
            if (connection == null) return new Table();
            ResultSet set = doMySQLQuery(query);;
            return new Table(getDatabaseData(set));
        }, callBack);
    }


    private HashMap<String, List<Object>> getDatabaseData(ResultSet result) throws SQLException {
        HashMap<String, List<Object>> returned = new HashMap<>();

        List<String> labels = new ArrayList<>();
        for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
            labels.add(result.getMetaData().getColumnLabel(i + 1));
        }



        while (result.next()) {
            for (String label : labels) {
                String newRaw = result.getString(label);
                List<Object> data = (returned.get(label) == null) ? new ArrayList<>() : returned.get(label);
                data.add(newRaw);
                returned.put(label, data);
            }
        }

        return returned;
    }


    private ResultSet doMySQLQuery(String query, Object... parameters) throws Exception {
            PreparedStatement ps = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
           return ps.executeQuery();
    }

    public ActionResult<Boolean> update(String query, Object... parameters) throws Exception {
        return new ActionResult<>(() -> {
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                for (int i = 0; i < parameters.length; i++) {
                    ps.setObject(i + 1, parameters[i]);
                }
                ps.executeUpdate();
                return true;
            } catch (Exception exception) {
                return false;
            }
        });
    }


}
