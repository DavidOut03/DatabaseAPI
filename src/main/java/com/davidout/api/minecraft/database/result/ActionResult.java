package com.davidout.api.minecraft.database.result;


import com.davidout.api.minecraft.database.DatabaseManager;
import com.davidout.api.minecraft.database.Table;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class ActionResult<T> {

    private Consumer<T> whenFinished;
    private Consumer<Throwable> whenFailed;
    private CallBack queryResult;


    public ActionResult(Callable<T> task) {
        executeTask(task);
    }

    public ActionResult(Callable<T> task, CallBack callBack) {
        this.queryResult = callBack;
        executeTask(task);
    }

    public ActionResult(Callable<T> task, Consumer<T> whenFinished) {
        this.whenFinished = whenFinished;
        executeTask(task);
    }

    public ActionResult(Callable<T> task, Consumer<T> whenFinished, Consumer<Throwable> whenFailed) {
        this.whenFinished = whenFinished;
        this.whenFailed = whenFailed;
        executeTask(task);
    }


    // Method to execute the asynchronous task and complete the future
    private void executeTask(Callable<T> task) {
        DatabaseManager.getDatabaseTaskHandler().execute(() -> {
            try {
                if(whenFinished != null) whenFinished.accept(task.call());
                if(queryResult != null) queryResult.onComplete((Table) task.call());
            } catch (Exception e) {
                if(whenFailed != null) whenFailed.accept(e);
            }
        });
    }

    // Method to get the result of the action (blocking)
}
