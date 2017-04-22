package pl.edu.pwr.lab2borowiak.colorsgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    public final static String PREFERANCES_NAME = "preferences";
    public final static String RECORD = "record";
    public final static int yellow = 0;
    public final static int red = 1;
    public final static int green = 2;
    public final static int blue = 3;


    @BindView(R.id.textViewColor) TextView colorText;
    @BindView(R.id.textViewTime) TextView timeText;
    @BindView(R.id.pointsTextView) TextView pointsText;
    @BindView(R.id.recordPointsView) TextView recordPointsView;

    SharedPreferences preferences;
    CountDownTimer cdt;
    ArrayList<Integer> arrayColors;
    ArrayList<Integer> arrayColorsNames;
    int points;
    int record;
    int correctButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        preferences = getSharedPreferences(PREFERANCES_NAME, Activity.MODE_PRIVATE);

        setArrayColors();
        setArrayColorsNames();
        points = 0;
        correctButton = -1;
        pointsText.setText(String.valueOf(points));
        restoreData();
        setCdt();
        startTurn();
    }


    @Override
    protected void onResume() {
        super.onResume();
        restoreData();
        points = 0;
        correctButton = -1;
        pointsText.setText(String.valueOf(points));
        //Toast.makeText(getApplicationContext(),"resume", Toast.LENGTH_SHORT).show();
        startTurn();
    }


    public void startTurn(){
        setDisplayedColor();
        cdt.start();
    }


    public boolean ifClickCorrect(int clickedButton, int correctButton){
        return clickedButton == correctButton;
    }


    public void onClickCorrect(){
        points++;
        pointsText.setText(String.valueOf(points));
        startTurn();
    }


    public void onLose(){
        cdt.cancel();
        if(points > record){
            record = points;
            saveData();
        }
        setAlertDialog();
    }


    @OnClick(R.id.buttonRed)
    public void onClickRed(){
        cdt.cancel();
        if(ifClickCorrect(red, correctButton)){
            onClickCorrect();
        }
        else{
            onLose();
        }
    }


    @OnClick(R.id.buttonGreen)
    public void onClickGreen(){
        cdt.cancel();
        if(ifClickCorrect(green, correctButton)){
            onClickCorrect();
        }
        else{
            onLose();
        }
    }


    @OnClick(R.id.buttonBlue)
    public void onClickBlue(){
        cdt.cancel();
        if(ifClickCorrect(blue, correctButton)){
            onClickCorrect();
        }
        else{
            onLose();
        }
    }


    @OnClick(R.id.buttonYellow)
    public void onClickYellow(){
        cdt.cancel();
        if(ifClickCorrect(yellow, correctButton)){
            onClickCorrect();
        }
        else{
            onLose();
        }
    }


    private void saveData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RECORD, record);
        editor.apply();
    }

    private void restoreData(){
        record = preferences.getInt(RECORD,0);
        recordPointsView.setText(String.valueOf(record));
    }


    public void setArrayColors(){
        arrayColors = new ArrayList<>();
        arrayColors.add(R.color.yellow_600);
        arrayColors.add(R.color.blue_900);
        arrayColors.add(R.color.light_green_A700);
        arrayColors.add(R.color.red_900);
    }


    public void setArrayColorsNames(){
        arrayColorsNames = new ArrayList<>();
        arrayColorsNames.add(red);
        arrayColorsNames.add(green);
        arrayColorsNames.add(yellow);
        arrayColorsNames.add(blue);
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
                onLose();
            }
        };
    }


    public void setDisplayedColor(){
        Random r = new Random();

        colorText.setTextColor(getResources().getColor(arrayColors.get(r.nextInt(4))));
        switch (r.nextInt(4)){
            case yellow:
                colorText.setText(R.string.yellow);
                correctButton = yellow;
                break;
            case red:
                colorText.setText(R.string.red);
                correctButton = red;
                break;
            case blue:
                colorText.setText(R.string.blue);
                correctButton = blue;
                break;
            case green:
                colorText.setText(R.string.green);
                correctButton = green;
                break;
            default:
                break;
        }
    }

    public void setAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.lose);
        builder.setMessage("Points:" + String.valueOf(points) + "\nRecord: " + String.valueOf(record));
        builder.setPositiveButton("Play again!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
                Intent it = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(it);
            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}