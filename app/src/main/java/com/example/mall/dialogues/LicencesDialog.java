package com.example.mall.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mall.R;
import com.example.mall.Utils;

public class LicencesDialog extends DialogFragment {

    private TextView txtLicences;
    private Button btnLicences;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.licences_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        txtLicences = view.findViewById(R.id.txtLicences);
        btnLicences = view.findViewById(R.id.btnLicences);
        txtLicences.setText(Utils.getLicences());
        btnLicences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return builder.create();
    }
}
