package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class SelectShowDialog extends DialogFragment {
    private okClickListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select what you want to show ");
        builder.setSingleChoiceItems(R.array.show, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onOkClick(which);
                Intent intent = new Intent(getContext(), PeriodicTable.class);
            }
        });
        builder.setNegativeButton("CanCle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builder.create();
    }

    public interface okClickListener {
        void onOkClick(int n);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (okClickListener) context;
        }catch (ClassCastException ex) {
            throw new ClassCastException(context.toString()
                    + "must implement methods");
        }
    }
}
