package com.edonoxako.asker.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Eugene on 18.02.2015.
 */
public class PhoneCaller implements PhoneActionPerformer {
    private String CALL_CODE = "*144*";
    private String MONEY_CODE = "*143*";

    private Context mContext;

    public PhoneCaller(Context context) {
        this.mContext = context;
    }

    @Override
    public void callAction(String number) {
        String request = "tel:" + CALL_CODE + number.trim() + "%23";
        makeRequest(request);
    }

    @Override
    public void moneyAction(String number) {
        String request = "tel:" + MONEY_CODE + number.trim() + "%23";
        makeRequest(request);
    }

    private void makeRequest(String request) {
        String uri = request;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        mContext.startActivity(intent);
    }
}
