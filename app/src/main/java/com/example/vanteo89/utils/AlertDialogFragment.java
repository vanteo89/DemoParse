package com.example.vanteo89.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by vanteo89 on 14/08/2015.
 */
public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Opp ! Please fill full data !").setTitle("Warning").setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
