package com.edonoxako.asker.app.gui.contactsadapter;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.edonoxako.asker.app.R;

/**
 * Created by EugeneM on 20.02.2015.
 *
 * This binder is used for saving the ID of the contact in the tag of name's TextView
 */
public class TagViewBinder implements SimpleCursorAdapter.ViewBinder {

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
