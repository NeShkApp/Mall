package com.example.mall.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mall.R;

import java.util.Calendar;

public class ShopInfoDialog extends DialogFragment {
    private static final String TAG = "ShopInfoDialog";

    private TextView txtMonFriTime, txtSaturdayTime, txtSundayTime, txtStreetName, txtCloseOrOpen;

    private ImageView btnCross;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.shop_info_dialogue, null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (null != bundle){
            String address = bundle.getString("street_name");
            String workingTimeMonFri = bundle.getString("mon_fri_time");
            String workingTimeSat = bundle.getString("saturday_time");
            String workingTimeSun = bundle.getString("sunday_time");

            if(address != null){
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                String workingHours;
                switch (dayOfWeek) {
                    case Calendar.MONDAY:
                    case Calendar.TUESDAY:
                    case Calendar.WEDNESDAY:
                    case Calendar.THURSDAY:
                    case Calendar.FRIDAY:
                        workingHours = workingTimeMonFri;
                        break;
                    case Calendar.SATURDAY:
                        workingHours = workingTimeSat;
                        break;
                    case Calendar.SUNDAY:
                        workingHours = workingTimeSun;
                        break;
                    default:
                        workingHours = "";
                        break;
                }

                if (!workingHours.isEmpty() && !workingHours.equalsIgnoreCase("closed")) {
                    String[] workingHoursParts = workingHours.split("-");
                    if (workingHoursParts.length == 2) {
                        String startWorkingHour = workingHoursParts[0].trim();
                        String endWorkingHour = workingHoursParts[1].trim();
                        try {
                            int startHour = Integer.parseInt(startWorkingHour.split(":")[0]);
                            int endHour = Integer.parseInt(endWorkingHour.split(":")[0]);

                            if (currentHour >= startHour && currentHour < endHour) {
                                    txtCloseOrOpen.setVisibility(View.VISIBLE);
                                    txtCloseOrOpen.setText("Open");
                                    txtCloseOrOpen.setTextColor(Color.GREEN);
//                                Log.d(TAG, "onCreateShopDialog: Shop open");
                            } else {
                                txtCloseOrOpen.setVisibility(View.VISIBLE);
                                txtCloseOrOpen.setText("Closed");
                                txtCloseOrOpen.setTextColor(Color.RED);
//                                Log.d(TAG, "onCreateShopDialog: Shop closed");
                            }
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Invalid hour format: " + e.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Invalid working hours format: " + workingHours);
                    }
                } else {
                    Log.d(TAG, "onCreateDialog: Shop closed");
                }

                txtStreetName.setText(address);
                txtMonFriTime.setText(workingTimeMonFri);
                txtSaturdayTime.setText(workingTimeSat);
                txtSundayTime.setText(workingTimeSun);
            }
        }

        btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void initViews(View view) {
        txtMonFriTime = view.findViewById(R.id.txtMonFriTime);
        txtSaturdayTime = view.findViewById(R.id.txtSaturdayTime);
        txtSundayTime = view.findViewById(R.id.txtSundayTime);
        txtStreetName = view.findViewById(R.id.txtStreetName);
        txtCloseOrOpen = view.findViewById(R.id.txtCloseOrOpen);
        btnCross = view.findViewById(R.id.btnCross);
    }
}
