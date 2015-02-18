package com.edonoxako.asker.app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class MainActivity extends ActionBarActivity implements AppCallback {

    ListView mContactsList;

    AppLogic logic;

    String mColumns[] = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts._ID
    };
    int mViews[] = {
            R.id.nameTextView,
            //R.id.isPhoneTextView
    };

    private String LOG_TAG = "magic";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsList = (ListView) findViewById(R.id.contactList);

        logic = new AppLogic(this, this);
        logic.start();
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

    //Handling buttons clicks
    public void OnButtonClicked(View v) {
        LinearLayout textLayout;
        String id;

        switch (v.getId()) {
            case R.id.askForCallBtn:
                textLayout = (LinearLayout) ((LinearLayout) v.getParent().getParent()).getChildAt(0);
                id = (String) textLayout.getChildAt(0).getTag();
                logic.askForPhoneCall(id);
                break;

            case R.id.askForMoneyBtn:
                textLayout = (LinearLayout) ((LinearLayout) v.getParent().getParent()).getChildAt(0);
                id = (String) textLayout.getChildAt(0).getTag();
                logic.askForMoney(id);
                break;
        }
    }

    private void log (String msg) {
        Log.d(LOG_TAG, msg);
    }

    @Override
    public void onContactObtained(Cursor contacts) {
        try {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.contact_list_item,
                    contacts,
                    mColumns,
                    mViews,
                    0
            );
            adapter.setViewBinder(new TagViewBinder());
            mContactsList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    //This binder is used for saving the ID of the contact in the tag of name's TextView
    class TagViewBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() == R.id.nameTextView) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                view.setTag(id);
                ((TextView) view).setText(name);
                return true;
            }
            return false;
        }
    }

}
