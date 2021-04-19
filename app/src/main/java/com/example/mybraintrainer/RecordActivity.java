package com.example.mybraintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {

    TextView textViewRecord;
    private float record = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        textViewRecord = findViewById(R.id.textViewRecord);
        Intent intentByMain = getIntent();
        float counterRight = intentByMain.getFloatExtra("record", (float) 1.0);
        float percent = intentByMain.getFloatExtra("percent", (float) 1.0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        record = preferences.getInt("newRecord", 0);
        if (counterRight > record) {
            record = counterRight;
            preferences.edit().putFloat("newRecord", counterRight).apply();
        }

        textViewRecord.setText(String.format("Количество верных ответов: %s\nПроцент верных ответов: %.4s %%\n\nРекорд: %s\n\n\n", (int) counterRight, percent, (int) record));

    }
//2
    public void onClickBackToMain(View view) {
        Intent intentToMain = new Intent(this, MainActivity.class);
        startActivity(intentToMain);
    }
}