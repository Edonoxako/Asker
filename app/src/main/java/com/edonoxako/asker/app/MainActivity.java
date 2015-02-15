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


public class MainActivity extends ActionBarActivity {

    private String CALL_CODE = "*144*";
    private String MONEY_CODE = "*143*";

    ListView mContactsList;

    //Query parameters
    String contactProjection[] = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    String contactClause = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?";
    String contactArgs[] = new String[] {"1"};

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

        Cursor mCursor = getContactsNames();
//        mCursor.moveToFirst();
//        while (mCursor.moveToNext()) {
//            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
//            String id = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
//            log(name + " " + id);
//        }

        try {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.contact_list_item,
                    mCursor,
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

    //Get contacts data here
    private Cursor getContactsNames() {
        return getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                contactProjection,
                contactClause,
                contactArgs,
                null
        );
    }

    private String getNumberForContact(String id) {
        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.Data.CONTACT_ID + " = ?" + " AND "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
                new String[] {id},
                null
        );
        log("" + cursor.getCount());
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    }

    //Handling buttons clicks
    public void OnButtonClicked(View v) {
        LinearLayout textLayout;
        String text;
        String id;
        String phoneNumber;

        switch (v.getId()) {
            case R.id.askForCallBtn:
                textLayout = (LinearLayout) ((LinearLayout) v.getParent().getParent()).getChildAt(0);
                text = (String) ((TextView) textLayout.getChildAt(0)).getText();
                id = (String) textLayout.getChildAt(0).getTag();
                phoneNumber = getNumberForContact(id);
                log("Позвони мне " + text + " number = " + phoneNumber);
                createAndSendRequest(phoneNumber, true);
                break;

            case R.id.askForMoneyBtn:
                textLayout = (LinearLayout) ((LinearLayout) v.getParent().getParent()).getChildAt(0);
                text = (String) ((TextView) textLayout.getChildAt(0)).getText();
                id = (String) textLayout.getChildAt(0).getTag();
                phoneNumber = getNumberForContact(id);
                log("Дай мне денег " + text);
                createAndSendRequest(phoneNumber, false);
                break;
        }
    }

    private void log (String msg) {
        Log.d(LOG_TAG, msg);
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

    private void createAndSendRequest(String number, boolean isCall) {
        if (isCall) {
            number = "tel:" + CALL_CODE + number.trim() + "%23";
        } else {
            number = "tel:" + MONEY_CODE + number.trim() + "%23";
        }
        Log.d(LOG_TAG, number);

        String uri = number;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}
