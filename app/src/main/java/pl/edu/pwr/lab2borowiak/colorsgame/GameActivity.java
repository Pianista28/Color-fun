package pl.edu.pwr.lab2borowiak.colorsgame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.textViewColor) TextView colorText;
    @BindView(R.id.textViewTime) TextView timeText;

    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        setCdt();
        cdt.start();
        setDisplayedColor();
    }

    public void setCdt(){
        cdt = new CountDownTimer(1000,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText(String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                timeText.setText("0");
            }
        };
    }


    public void setDisplayedColor(){
        Random r = new Random();

        int[] arrayColors = {R.color.yellow_600, R.color.blue_900, R.color.red_900, R.color.light_green_A700};
        colorText.setTextColor(arrayColors[r.nextInt(4)]);
    }

}
