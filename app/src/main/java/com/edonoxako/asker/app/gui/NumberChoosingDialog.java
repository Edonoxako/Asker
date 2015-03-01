package com.edonoxako.asker.app.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.edonoxako.asker.app.R;

/**
 * Created by Eugene on 01.03.2015.
 */
public class NumberChoosingDialog extends DialogFragment {

    public static NumberChoosingDialog createInstance(String[] numbers) {
        NumberChoosingDialog f = new NumberChoosingDialog();

        Bundle args = new Bundle();
        args.putStringArray("numbers", numbers);
        f.setArguments(args);

        return f;
    }

    public interface NumberChoosingListener {
        public void onNumberSelected(String number);
    }

    public NumberChoosingListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NumberChoosingListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NumberChoosingListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] numbers = getArguments().getStringArray("numbers");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.specify_number));
        builder.setItems(numbers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onNumberSelected(numbers[which]);
            }
        });

        return builder.create();
    }
}
