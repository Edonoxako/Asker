package com.edonoxako.asker.app.data;

import android.database.Cursor;

/**
 * Created by Eugene on 18.02.2015.
 */
public interface ContactDataRepository {
    public Cursor getContacts();
    public String getContactNumber(String id);
}
