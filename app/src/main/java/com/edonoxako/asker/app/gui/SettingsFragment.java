package com.edonoxako.asker.app.gui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.edonoxako.asker.app.R;

/**
 * Created by EugeneM on 24.03.2015.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
