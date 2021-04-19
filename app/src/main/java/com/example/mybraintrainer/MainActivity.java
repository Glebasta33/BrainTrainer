package com.example.mybraintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView button0, button1, button2, button3;
    private TextView textViewAttempts, textViewTimer, textViewExample;
    private int seconds;
    private int sumRight, sumResult, sumRandom, num1, num2;
    private int numberOfRightAnswer;
    private boolean isSum;
    String symbol;
    private float counterRight = 0;
    private float counterAll = 0;
    private float percent;
    private ArrayList<TextView> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewExample = findViewById(R.id.textViewExapmle);
        textViewAttempts = findViewById(R.id.textViewAttempts);
        button0 = findViewById(R.id.textView0);
        button1 = findViewById(R.id.textView1);
        button2 = findViewById(R.id.textView2);
        button3 = findViewById(R.id.textView3);
        buttons = new ArrayList<>();
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        Intent intentFromRecord = getIntent();

        checkQA();
        timer.start();

    }


    public void onClick(View view) {
            TextView textView = (TextView) view;
            String tag = textView.getTag().toString();
            if (Integer.parseInt(tag) == numberOfRightAnswer) {
                Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
                counterRight++;
                counterAll++;
            } else {
                Toast.makeText(this, String.format("Неверно. Правильный ответ: %s", sumRight), Toast.LENGTH_SHORT).show();
                counterAll++;
            }
            checkQA();
    }
    public void generateQA() {
        int r = (int) (Math.random() * 10);
        if (r < 7) {
            isSum = true;
            num1 = (int) (Math.random() * 50 + 1);
            num2 = (int) (Math.random() * 50 + 1);
            sumRight = num1 + num2;
        } else {
            isSum = false;
            num1 = (int) (Math.random() * 50 + 20);
            num2 = (int) (Math.random() * 20 + 1);
            sumRight = num1 - num2;
        }
        if (isSum) {
            symbol = "+";
        } else {
            symbol = "-";
        }
        textViewExample.setText(String.format("%s %s %s", num1, symbol, num2));
        numberOfRightAnswer = (int) (Math.random() * buttons.size());
    }

    private void checkQA() {
        generateQA();
        for (int i = 0; i<buttons.size(); i++){
            if (i == numberOfRightAnswer) {
                buttons.get(i).setText(Integer.toString(sumRight));
            } else {
                buttons.get(i).setText(Integer.toString((int) (Math.random() * 50 )));
            }
        }
        textViewAttempts.setText(String.format("%s/%s", (int) counterRight, (int) counterAll));
        percent = (counterRight/counterAll) * 100;
    }

    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            seconds = (int) (millisUntilFinished / 1000);
            seconds++;
            if (seconds < 21) {
                textViewTimer.setTextColor(getResources().getColor(R.color.yellow));
            }
            if (seconds < 11) {
                textViewTimer.setTextColor(getResources().getColor(R.color.red));
            }
            textViewTimer.setText(Integer.toString(seconds));
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
            intent.putExtra("percent", percent);
            intent.putExtra("record", counterRight);
            startActivity(intent);
        }
    };
}