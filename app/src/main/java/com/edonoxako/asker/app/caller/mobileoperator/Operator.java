package com.edonoxako.asker.app.caller.mobileoperator;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.edonoxako.asker.app.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EugeneM on 02.03.2015.
 */
public class Operator implements MobileOperator, SharedPreferences.OnSharedPreferenceChangeListener {
    private Map <String, String> symbols;

    private final String APP_PREFERENCE = "options";
    private final String MOBILE_OPERATOR_NAME = "operatorName";

    private Context mContext;
    private String mOperatorName;
    private SharedPreferences sPrefs;


    public Operator(Context context) {
        this.mContext = context;

        symbols = new HashMap<String, String>();
        String[] opNames = context.getResources().getStringArray(R.array.operators);
        String[] specSymbols = context.getResources().getStringArray(R.array.specSymbols);

        for (int i = 0; i < opNames.length; i++) {
            symbols.put(opNames[i], specSymbols[i]);
        }

        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        mOperatorName = prefs.getString(MOBILE_OPERATOR_NAME, null);
    }

    @Override
    public void init(MobileOperatorCallback callback) {
        if (mOperatorName == null) {
            TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String operatorName = manager.getNetworkOperatorName();
            setOperator(operatorName);
            callback.onOperatorNameObtained(operatorName);
        }
    }

    @Override
    public String[] getSpecSymbols() {
        String[] res = symbols.get(mOperatorName).split("_");
        return res;
    }

    @Override
    public void setOperator(String operatorName) {
        SharedPreferences.Editor e = mContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).edit();
        e.putString(MOBILE_OPERATOR_NAME, operatorName).apply();
        mOperatorName = operatorName;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(MOBILE_OPERATOR_NAME)) {
            Log.d("magic", "ура, работает");
        }
    }
}
