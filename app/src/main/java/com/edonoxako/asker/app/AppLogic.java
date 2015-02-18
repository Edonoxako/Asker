package com.edonoxako.asker.app;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Eugene on 18.02.2015.
 */
public class AppLogic {

    private ContactDataRepository contactRepository;
    private AppCallback appCallback;
    private PhoneActionPerformer phone;

    public AppLogic(Context context, AppCallback callback) {
        this.contactRepository = new ContactInteractor(context);
        this.phone = new PhoneCaller(context);
        this.appCallback = callback;
    }

    public void start() {
        Cursor cursor = contactRepository.getContacts();
        appCallback.onContactObtained(cursor);
    }

    public void askForPhoneCall(String id) {
        String number = contactRepository.getContactNumber(id);
        phone.callAction(number);
    }

    public void askForMoney(String id) {
        String number = contactRepository.getContactNumber(id);
        phone.moneyAction(number);
    }

}
