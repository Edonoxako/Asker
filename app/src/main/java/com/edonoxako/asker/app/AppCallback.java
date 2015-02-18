package com.edonoxako.asker.app;

import android.database.Cursor;

/**
 * Created by Eugene on 18.02.2015.
 */
public interface AppCallback {
    public void onContactObtained(Cursor contacts);
}
