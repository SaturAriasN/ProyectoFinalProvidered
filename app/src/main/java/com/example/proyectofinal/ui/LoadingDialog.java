package com.example.proyectofinal.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;

public class LoadingDialog {


    private static Dialog dialog;


    public static void load(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_loading, null));

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    public static void cancelLoading() {
        dialog.cancel();
    }


}
