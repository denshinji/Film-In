package com.example.filmin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.filmin.Notification.DailyReminder;
import com.example.filmin.Notification.ReleaseReminder;

public class SettingActivity extends AppCompatActivity {
    private static final String PREF_REMINDER = "prefReminder";
    private static final String SWITCH_DAILY = "switch1";
    private static final String SWITCH_RELEASE = "switch2";
    private String title;
    private Switch dailyremindersw, releaseremindersw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.setting_tool);
        setSupportActionBar(toolbar);
        title = getApplicationContext().getString(R.string.setting);
        dailyremindersw = findViewById(R.id.sw_daily);
        releaseremindersw = findViewById(R.id.sw_movie);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setActionBarTitle(title);

        final DailyReminder dailyReminder = new DailyReminder();
        final ReleaseReminder releaseReminder = new ReleaseReminder();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_REMINDER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean switchDaily = sharedPreferences.getBoolean(SWITCH_DAILY, false);
        dailyremindersw.setChecked(switchDaily);
        boolean switchMovie = sharedPreferences.getBoolean(SWITCH_RELEASE, false);
        releaseremindersw.setChecked(switchMovie);

        dailyremindersw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dailyReminder.setReminder(getApplicationContext());
            } else
                dailyReminder.cancelReminder(getApplicationContext());
            editor.putBoolean(SWITCH_DAILY, dailyremindersw.isChecked());
            editor.apply();
        });

        releaseremindersw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                releaseReminder.setReminderMovie(getApplicationContext());
            } else releaseReminder.cancelReminderMovie(getApplicationContext());
            editor.putBoolean(SWITCH_RELEASE, releaseremindersw.isChecked());
            editor.apply();
        });
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
