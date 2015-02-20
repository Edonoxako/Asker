package com.edonoxako.asker.app.gui.contactsadapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import com.edonoxako.asker.app.R;
import com.edonoxako.asker.app.logic.AppCallback;
import com.edonoxako.asker.app.logic.AppLogic;

/**
 * Created by EugeneM on 20.02.2015.
 */
public class ContactsAdapter implements AppCallback {

    private Context mContext;
    private ContactsListener mListener;
    private TagViewBinder mBinder;
    private AppLogic mLogic;

    private String mColumns[] = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts._ID
    };
    private int mViews[] = {
            R.id.nameTextView,
    };


    public ContactsAdapter(Context context, ContactsListener listener) {
        this.mContext = context;
        this.mListener = listener;

        mBinder = new TagViewBinder();
        mLogic = new AppLogic(context, this);
    }

    public void start() {
        mLogic.start();
    }

    public void askForCall(View v) {
        String id = extractID(v);
        mLogic.askForPhoneCall(id);
    }

    public void askForMoney(View v) {
        String id = extractID(v);
        mLogic.askForMoney(id);
    }

    private String extractID(View v) {
        LinearLayout textLayout = (LinearLayout) ((LinearLayout) v.getParent().getParent()).getChildAt(0);
        String id = (String) textLayout.getChildAt(0).getTag();
        return id;
    }

    @Override
    public void onContactObtained(Cursor contacts) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                mContext,
                R.layout.contact_list_item,
                contacts,
                mColumns,
                mViews,
                0
        );
        adapter.setViewBinder(mBinder);
        mListener.onContactsAdapterObtained(adapter);
    }

}
