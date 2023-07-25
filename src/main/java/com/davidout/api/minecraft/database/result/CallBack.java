package com.davidout.api.minecraft.database.result;

import com.davidout.api.minecraft.database.Table;

public interface CallBack {
    abstract void onComplete(Table table);
    abstract void onFailure(Throwable error);
}
