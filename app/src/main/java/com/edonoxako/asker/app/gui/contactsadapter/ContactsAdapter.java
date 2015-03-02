package com.edonoxako.asker.app.gui.contactsadapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import com.edonoxako.asker.app.R;
import com.edonoxako.asker.app.gui.dialogs.NumberChoosingDialog;
import com.edonoxako.asker.app.logic.AppCallback;
import com.edonoxako.asker.app.logic.AppLogic;

/**
 * Created by EugeneM on 20.02.2015.
 */
public class ContactsAdapter implements AppCallback {

    private Context mContext;
    private Activity mActivity;
    private ContactsListener mListener;
    private TagViewBinder mBinder;
    private AppLogic mLogic;
    private boolean isPhoneCallAsking;

    private String mColumns[] = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts._ID
    };
    private int mViews[] = {
            R.id.nameTextView,
    };


    public ContactsAdapter(Activity activity, ContactsListener listener) {
        this.mActivity = activity;
        this.mContext = (Context) activity;
        this.mListener = listener;

        mBinder = new TagViewBinder();
        mLogic = new AppLogic(mContext, this);
    }

    public void start() {
        mLogic.start();
    }

    public void askForCall(View v) {
        isPhoneCallAsking = true;
        String id = extractID(v);
        mLogic.askForPhoneCall(id);
    }

    public void askForMoney(View v) {
        isPhoneCallAsking = false;
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

    @Override
    public void onTwoPhoneNumbers(String[] numbers) {
        NumberChoosingDialog dialog = NumberChoosingDialog.createInstance(numbers);
        dialog.show(mActivity.getFragmentManager(), "NumberChoosing");
    }

    public void selectedNumber (String number) {
        if (isPhoneCallAsking) mLogic.phoneCall(number);
        else mLogic.moneyCall(number);
    }
}
