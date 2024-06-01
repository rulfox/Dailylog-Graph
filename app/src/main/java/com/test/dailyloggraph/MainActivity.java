package com.test.dailyloggraph;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private DailyLogGraph dailyLogGraph;
    private DutyStatus[] dutyStatusLogs = new DutyStatus[25];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        dailyLogGraph = findViewById(R.id.dailyLogGraph);
        for (int i = 0; i < dutyStatusLogs.length; i++) {
            dutyStatusLogs[i] = DutyStatus.ON_DUTY;
        }
        dutyStatusLogs[0] = DutyStatus.ON_DUTY;
        dutyStatusLogs[1] = DutyStatus.OFF_DUTY;
        dutyStatusLogs[2] = DutyStatus.DRIVING;
        dutyStatusLogs[3] = DutyStatus.DRIVING;
        dutyStatusLogs[4] = DutyStatus.SLEEPER_BERTH;
        dailyLogGraph.setDutyStatusLogs(dutyStatusLogs);
    }
}