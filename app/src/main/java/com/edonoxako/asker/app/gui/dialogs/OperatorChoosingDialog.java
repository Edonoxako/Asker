package com.edonoxako.asker.app.gui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.edonoxako.asker.app.R;
import com.edonoxako.asker.app.caller.mobileoperator.MobileOperator;
import com.edonoxako.asker.app.caller.mobileoperator.Operator;

/**
 * Created by EugeneM on 24.03.2015.
 */
public class OperatorChoosingDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите оператора");
        builder.setItems(R.array.operators, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newOperator = getResources().getStringArray(R.array.operators)[which];
                new Operator(getActivity()).setOperator(newOperator);
            }
        });
        return builder.create();
    }
}
