package com.bluemoonl.lottoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class LottoMainActivity extends AppCompatActivity {

    List<String> InputNumber = new ArrayList();
    List<TextView> lotteryNumber = new ArrayList();
    LottieAnimationView lotteryButton;
    TextView num1;
    TextView num2;
    TextView num3;
    TextView num4;
    TextView num5;
    TextView num6;
    TextView qrcodeButton;
    TextView randomLottoNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomLottoNumber = findViewById(R.id.randomLottoNumber);
        qrcodeButton = findViewById(R.id.qrcodeButton);
        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomLottoNumber.setEnabled(true);
                qrcodeButton.setEnabled(false);

                Intent intent = new Intent(getApplicationContext(), QRcodeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);

        InputNumber.add(num1.getText().toString());
        InputNumber.add(num2.getText().toString());
        InputNumber.add(num3.getText().toString());
        InputNumber.add(num4.getText().toString());
        InputNumber.add(num5.getText().toString());
        InputNumber.add(num6.getText().toString());

        lotteryNumber.add(num1);
        lotteryNumber.add(num2);
        lotteryNumber.add(num3);
        lotteryNumber.add(num4);
        lotteryNumber.add(num5);
        lotteryNumber.add(num6);

        CountDownTimer countDownTimer = new CountDownTimer(3000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                print();
            }

            @Override
            public void onFinish() {
            }
        };

        lotteryButton = (LottieAnimationView) findViewById(R.id.lotteryButton);
        lotteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lotteryButton.isAnimating()) {
                    lotteryButton.cancelAnimation();
                    countDownTimer.cancel();
                }
                else {
                lotteryButton.playAnimation();
                countDownTimer.start();
                }
            }
        });
    }

    public int RandomNumber() {
        int randomNumber = (int) (Math.random() * 45 + 1);

        return randomNumber;
    }

    public void filter() {

        InputLotteryNumber();

        boolean duplication = true;

        for(int i = 0; i < InputNumber.size(); i++) {
            for(int j = 0; j < InputNumber.size(); j++) {
                if (i != j &&InputNumber.get(i).equals(InputNumber.get(j))) {
                    duplication = true;
                    InputNumber.set(j, String.valueOf(RandomNumber()));
                }
                else {
                    duplication = false;
                }
            }

            if(duplication) i--;
        }
    }

    public void InputLotteryNumber() {
        int i = 0;

        for (String t : InputNumber) {
            InputNumber.set(i, String.valueOf(RandomNumber()));
            i++;
        }
    }

    public int getNumberBackGround(int random_num) {

        if(random_num < 10) return R.drawable.yellow_ball;
        else if(random_num < 20) return R.drawable.blue_ball;
        else if(random_num < 30) return R.drawable.red_ball;
        else if(random_num < 40) return R.drawable.gray_ball;

        return R.drawable.green_ball;
    }

    public void print() {
        filter();

        for(int i = 0; i < lotteryNumber.size(); i++) {
//            lotteryNumber.get(i).setText(InputNumber.get(i));
//
//            if(Integer.valueOf(InputNumber.get(i)) < 10) {
//                lotteryNumber.set()
//            }

            TextView lottery_randomNumber = lotteryNumber.get(i);

            String random_num = InputNumber.get(i);

            lottery_randomNumber.setText(random_num);
            lottery_randomNumber.setBackgroundResource(getNumberBackGround(Integer.valueOf(random_num)));

            lotteryNumber.set(i, lottery_randomNumber);
        }
    }
}