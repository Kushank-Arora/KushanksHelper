package com.kushank.kushankshelper;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAnAppProfile extends AppCompatActivity {

    @BindView(R.id.tvAppName)
    TextView appName;

    @BindView(R.id.sbMaxDuration)
    SeekBar maxDurationSb;

    @BindView(R.id.tvThumbMaxValue)
    TextView thumbMaxValueTv;

    @BindView(R.id.etStartTime)
    EditText startTimeEt;

    @BindView(R.id.etEndTime)
    EditText endTimeEt;

    String appPackageName;
    Timer scheduledDisappearance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_an_app_profile);
        ButterKnife.bind(this);
        scheduledDisappearance = new Timer();

        findViewById(R.id.bSelectApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddAnAppProfile.this, SelectApp.class), 1);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showValueOnSeekBar();
        setOnClickListenerForTime();
    }

    private void setOnClickListenerForTime() {
        startTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v);
            }
        });
        endTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v);
            }
        });
    }

    private void showTimePicker(final View v) {
        String[] timeValues = ((EditText) v).getText().toString().split(":");
        int initialHour=0, initialMinute=0;
        if (timeValues.length == 2){
            initialHour = Integer.parseInt(timeValues[0]);
            initialMinute = Integer.parseInt(timeValues[1]);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddAnAppProfile.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                switch (v.getId()) {
                    case R.id.etStartTime:
                        startTimeEt.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                        Toast.makeText(AddAnAppProfile.this, "Now set the end time!", Toast.LENGTH_LONG).show();
                        endTimeEt.performClick();
                        break;
                    case R.id.etEndTime:
                        endTimeEt.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                        break;
                }
            }
        }, initialHour, initialMinute, false);
        timePickerDialog.show();
    }

    private void showValueOnSeekBar() {
        maxDurationSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int widthSeekBar = seekBar.getWidth() - 4 * seekBar.getThumbOffset();
                int xPosition = progress * widthSeekBar / seekBar.getMax();
                thumbMaxValueTv.setText(String.valueOf(progress));
                thumbMaxValueTv.setX(seekBar.getX() + xPosition + seekBar.getThumbOffset());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                thumbMaxValueTv.setVisibility(View.VISIBLE);
                scheduledDisappearance.cancel();
                scheduledDisappearance = new Timer();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                scheduledDisappearance.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() { thumbMaxValueTv.setVisibility(View.GONE); }
                                });
                            }
                        }, 3000
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String appName = data.getStringExtra("appName");

            this.appPackageName = data.getStringExtra("appPackage");
            this.appName.setText(appName);
        }
    }
}
