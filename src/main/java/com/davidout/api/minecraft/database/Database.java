package com.davidout.api.minecraft.database;

import com.davidout.api.minecraft.database.result.ActionResult;
import com.davidout.api.minecraft.database.result.CallBack;
import com.davidout.api.minecraft.database.result.DatabaseDetails;

import java.util.function.Consumer;

public abstract class Database {

    private final DatabaseDetails details;
    private String collection;

    public Database(String name, String host, int port, String userName, String password) {
       this.details = new DatabaseDetails(name, host, port, userName, password);
    }

    public Database(DatabaseDetails details) {
        this.details = details;
    }








    /**
     *
     *  Connect to database
     *
     */

    public abstract ActionResult<Boolean> connect(Consumer<Boolean> whenFinished, Consumer<Throwable> whenFailed) throws Exception;
    public  ActionResult<Boolean> connect(Consumer<Boolean> whenFinished) throws Exception {return this.connect(whenFinished, null);}
    public  ActionResult<Boolean> connect() throws Exception { return this.connect(null, null);}

    /**
     *
     *  Disconnect from database
     *
     */

    public abstract ActionResult<Boolean> disconnect(Consumer<Boolean> whenFinished, Consumer<Throwable> whenFailed) throws Exception;
    public ActionResult<Boolean> disconnect(Consumer<Boolean> whenFinished) throws Exception {return this.disconnect(whenFinished, null);}
    public ActionResult<Boolean> disconnect() throws Exception {return this.disconnect(null, null);}


    /**
     *
     *  Query data
     *
     */

    public abstract ActionResult<Table> query(String collection, String query, CallBack callBack);


    /* Query without functions */

    public ActionResult<Table> query(String collection, String query) {
        return this.query(collection, query, (CallBack) null);
    }

    public ActionResult<Table> query(String query) {
        return this.query("", query, (CallBack) null);
    }

    public ActionResult<Table> query(String query, CallBack callBack) {
        return this.query("", query, callBack);
    }

    /* Query with with custom fallback */

    public ActionResult<Table> query(String collectionName, String query, Consumer<Table> whenFinished, Consumer<Throwable> whenFailed) {
        return this.query(collectionName, query, new CallBack() {
            @Override
            public void onComplete(Table table) {
                if(whenFinished != null) whenFinished.accept(table);
            }

            @Override
            public void onFailure(Throwable error) {
               if(whenFailed != null) whenFailed.accept(error);
            }
        });
    }

    public ActionResult<Table> query(String collectionName, String query, Consumer<Table> whenFinished) {
        return this.query(collection, query, whenFinished, null);
    }


    public ActionResult<Table> query(String query, Consumer<Table> whenFinished, Consumer<Throwable> whenFailed) {
        return this.query("", query, whenFinished, whenFailed);
    }

    public ActionResult<Table> query(String query, Consumer<Table> whenFinished) {
        return this.query(collection, query, whenFinished, null);
    }






    public DatabaseDetails getDetails() {
        return details;
    }



}
