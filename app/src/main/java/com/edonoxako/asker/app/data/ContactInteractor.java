package com.edonoxako.asker.app.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by Eugene on 18.02.2015.
 */
public class ContactInteractor implements ContactDataRepository {

    private Context mContext;

    String LOG_TAG = "magic";

    public ContactInteractor(Context context){
        this.mContext = context;
    }

    String contactProjection[] = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    String contactClause = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?";
    String contactArgs[] = new String[] {"1"};


    @Override
    public Cursor getContacts() {
        return mContext.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                contactProjection,
                contactClause,
                contactArgs,
                null
        );
    }

    @Override
    public String[] getContactNumber(String id) {
        Cursor cursor = mContext.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.Data.CONTACT_ID + " = ?" + " AND "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
                new String[] {id},
                null
        );
        log("" + cursor.getCount());

        String[] numbers = new String[cursor.getCount()];
        int i = 0;
        cursor.moveToFirst();
        do {
            numbers[i] = (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))).trim();
            i++;
        }while (cursor.moveToNext());

        return numbers;
    }

    private void log (String msg) {
        Log.d(LOG_TAG, msg);
    }
}
