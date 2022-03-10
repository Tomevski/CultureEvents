package com.example.cultureevents.ui.profile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cultureevents.R;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import static com.example.cultureevents.utils.Constants.COUNTDOWN_INTERVAL;
import static com.example.cultureevents.utils.Constants.MILIS_IN_FUTURE;
import static java.lang.Thread.sleep;

/**
 * Fragment used in [ProfileActivity]
 */
public class UserFragment extends Fragment {

    Button btn;
    View view;
    EditText text;
    CircleProgressbar circleProgressbar;
    CheckBox checkBox;
    Dialog location_dialog;
    int progressbar_ctr = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initViews();
        initListeners();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showPopUp();
                circleProgressbar.setProgress(15);
            }
        });


        return view;
    }

    private void initViews() {
        btn = view.findViewById(R.id.button);
        circleProgressbar = view.findViewById(R.id.progress_circular);
        location_dialog = new Dialog(getContext());
        circleProgressbar.enabledTouch(false);
        text = view.findViewById(R.id.editTextTextPersonName);
        checkBox = view.findViewById(R.id.checkBox);
    }

    private void initListeners() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");

                while (true) {
                    progressbar_ctr += 1;
                    break;
                }
                circleProgressbar.setProgress(5 * progressbar_ctr);

            }
        });
    }


    public void showPopUp() {
        location_dialog.setContentView(R.layout.location_check);
        TextView txtClose;
        txtClose = location_dialog.findViewById(R.id.txtclose);


        new CountDownTimer(MILIS_IN_FUTURE, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                //No-op

            }

            @Override
            public void onFinish() {
                location_dialog.dismiss();
            }
        }.start();

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sleep(COUNTDOWN_INTERVAL);
                    location_dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // close splash activity
                location_dialog.dismiss();
            }
        });

        location_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        location_dialog.show();
    }
}