package com.edonoxako.asker.app.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.edonoxako.asker.app.caller.mobileoperator.MobileOperator;
import com.edonoxako.asker.app.caller.mobileoperator.MobileOperatorCallback;
import com.edonoxako.asker.app.caller.mobileoperator.Operator;
import com.edonoxako.asker.app.gui.contactsadapter.ContactsAdapter;
import com.edonoxako.asker.app.gui.contactsadapter.ContactsListener;
import com.edonoxako.asker.app.R;
import com.edonoxako.asker.app.gui.dialogs.NumberChoosingDialog;
import com.edonoxako.asker.app.gui.dialogs.OperatorChoosingDialog;
import com.edonoxako.asker.app.gui.dialogs.OperatorVerifyingDialog;


public class MainActivity extends ActionBarActivity implements ContactsListener, NumberChoosingDialog.NumberChoosingListener, MobileOperatorCallback,
        OperatorVerifyingDialog.OperatorVerifyingListener {

    private final String APP_PREFERENCE = "options";
    private final String FIRST_LAUNCH_OPTION = "firstLaunch";

    private boolean isFirstLaunch;

    private ListView mContactsList;
    private ContactsAdapter contactsAdapter;
    private MobileOperator mobileOperator;

    private String LOG_TAG = "magic";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSettings();

        mContactsList = (ListView) findViewById(R.id.contactList);

        if (isFirstLaunch) {
            mobileOperator = new Operator(this);
            mobileOperator.init(this);
            SharedPreferences.Editor editor = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).edit();
            editor.putBoolean(FIRST_LAUNCH_OPTION, false).apply();
        } else {
            contactsAdapter = new ContactsAdapter(this, this);
            contactsAdapter.start();
        }
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContactsAdapterObtained(CursorAdapter adapter) {
        mContactsList.setAdapter(adapter);
    }

    //Handling buttons clicks
    public void OnButtonClicked(View v) {
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

    @Override
    public void onNumberSelected(String number) {
        contactsAdapter.selectedNumber(number);
    }

    @Override
    public void onOperatorNameObtained(String operatorName) {
        OperatorVerifyingDialog dialog = OperatorVerifyingDialog.createInstance(operatorName);
        dialog.show(getFragmentManager(), "operatorVerifying");
        contactsAdapter = new ContactsAdapter(this, this);
        contactsAdapter.start();
    }

    @Override
    public void onSelectOperatorBtn() {
        OperatorChoosingDialog dialog = new OperatorChoosingDialog();
        dialog.show(getFragmentManager(), "operatorChoosing");
    }

    private void getSettings() {
        SharedPreferences sPrefs = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        isFirstLaunch = sPrefs.getBoolean(FIRST_LAUNCH_OPTION, true);
    }

}
