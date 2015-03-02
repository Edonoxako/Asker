package com.edonoxako.asker.app.caller.mobileoperator;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import java.util.Map;

/**
 * Created by EugeneM on 02.03.2015.
 */
public class Operator implements MobileOperator {

    private final String APP_PREFERENCE = "options";
    private final String MOBILE_OPERATOR_NAME = "operatorName";

    private Context mContext;
    private String mOperatorName;



    public Operator(Context context) {
        this.mContext = context;

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        mOperatorName = prefs.getString(MOBILE_OPERATOR_NAME, "MegaFon");
    }

    @Override
    public void init(MobileOperatorCallback callback) {
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String operatorName = manager.getNetworkOperatorName();
        callback.onOperatorNameObtained(operatorName);
    }

    @Override
    public Map<String, String> getSpecSymbols() {
        return null;
    }

    @Override
    public void setOperator(String operatorName) {
        SharedPreferences.Editor e = mContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).edit();
        e.putString(MOBILE_OPERATOR_NAME, operatorName).apply();
        mOperatorName = operatorName;
    }
}
