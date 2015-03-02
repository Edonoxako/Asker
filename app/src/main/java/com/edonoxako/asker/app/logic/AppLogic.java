package com.edonoxako.asker.app.logic;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import com.edonoxako.asker.app.caller.PhoneActionPerformer;
import com.edonoxako.asker.app.caller.PhoneCaller;
import com.edonoxako.asker.app.data.ContactDataRepository;
import com.edonoxako.asker.app.data.ContactInteractor;

/**
 * Created by Eugene on 18.02.2015.
 */
public class AppLogic {

    private ContactDataRepository contactRepository;
    private AppCallback appCallback;
    private PhoneActionPerformer phone;
    private String[] numbers;

    public AppLogic(Context context, AppCallback callback) {
        this.contactRepository = new ContactInteractor(context);
        this.phone = new PhoneCaller(context);
        this.appCallback = callback;
    }

    private boolean isOneNumber(String id) {
        numbers = contactRepository.getContactNumber(id);
        Log.d("magic", numbers[0]);
        if (numbers.length > 1) {
            appCallback.onTwoPhoneNumbers(numbers);
            return false;
        } else {
            return true;
        }
    }

    public void start() {
        Cursor cursor = contactRepository.getContacts();
        appCallback.onContactObtained(cursor);
    }

    public void askForPhoneCall(String id) {
        if (isOneNumber(id)) phoneCall(numbers[0]);
    }

    public void askForMoney(String id) {
        if (isOneNumber(id)) moneyCall(numbers[0]);
    }

    public void phoneCall(String number) {
        phone.callAction(number);
    }

    public void moneyCall(String number) {
        phone.moneyAction(number);
    }
}
