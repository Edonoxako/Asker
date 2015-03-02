package com.edonoxako.asker.app.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by EugeneM on 02.03.2015.
 */
public class OperatorVerifyingDialog extends DialogFragment {

    public interface OperatorVerifyingListener {
        public void onSelectOperatorBtn();
    }

    public static OperatorVerifyingDialog createInstance(String operatorName) {
        OperatorVerifyingDialog dialog = new OperatorVerifyingDialog();

        Bundle args = new Bundle();
        args.putString("operatorName", operatorName);
        dialog.setArguments(args);

        return dialog;
    }

    OperatorVerifyingListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OperatorVerifyingListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OperatorVerifyingListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String operatorName = getArguments().getString("operatorName");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ваш оператор " + operatorName + "?");
        builder.setPositiveButton("Да", null);
        builder.setNegativeButton("Выбрать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onSelectOperatorBtn();
            }
        });

        return builder.create();
    }
}
