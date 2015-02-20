package com.edonoxako.asker.app.gui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.edonoxako.asker.app.gui.contactsadapter.ContactsAdapter;
import com.edonoxako.asker.app.gui.contactsadapter.ContactsListener;
import com.edonoxako.asker.app.R;


public class MainActivity extends ActionBarActivity implements ContactsListener {

    private ListView mContactsList;
    private ContactsAdapter contactsAdapter;

    private String LOG_TAG = "magic";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsList = (ListView) findViewById(R.id.contactList);

        contactsAdapter = new ContactsAdapter(this, this);
        contactsAdapter.start();
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

    @Override
    public void onContactsAdapterObtained(CursorAdapter adapter) {
        mContactsList.setAdapter(adapter);
    }

    //Handling buttons clicks
    public void OnButtonClicked(View v) {
        LinearLayout textLayout;
        String id;

        switch (v.getId()) {
            case R.id.askForCallBtn:
                contactsAdapter.askForCall(v);
                break;

            case R.id.askForMoneyBtn:
                contactsAdapter.askForMoney(v);
                break;
        }
    }

    private void log (String msg) {
        Log.d(LOG_TAG, msg);
    }
}
