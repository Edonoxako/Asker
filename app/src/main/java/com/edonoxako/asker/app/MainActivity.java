package com.edonoxako.asker.app;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    ListView mContactsList;

    //Query parameters
    String contactProjection[] = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    String contactClause = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?";

    String contactArgs[] = new String[] {"1"};

    String mColumns[] = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    int mViews[] = {
            R.id.nameTextView,
            R.id.isPhoneTextView
    };

    private String LOG_TAG = "magic";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsList = (ListView) findViewById(R.id.contactList);

        Cursor mCursor = getContactsNames();
        try {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.contact_list_item,
                    mCursor,
                    mColumns,
                    mViews,
                    0
            );
            mContactsList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getMessage());
        }

//        for(String name : mCursor.getColumnNames()) {
//            log(name);
//        }

//        mCursor.moveToFirst();
//        while(mCursor.moveToNext()) {
//            String data = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
//            log(data);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Cursor getContactsNames() {
        return getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                contactProjection,
                contactClause,
                contactArgs,
                null
        );
    }

//    private void log (String msg) {
//        Log.d(LOG_TAG, msg);
//    }
}
