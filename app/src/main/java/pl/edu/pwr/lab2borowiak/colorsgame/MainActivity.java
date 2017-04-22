package pl.edu.pwr.lab2borowiak.colorsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setAlertDialog();
    }

    @OnClick(R.id.buttonS)
    public void startNew(){
        Intent it = new Intent(this, GameActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.buttonHowToPlay)
    public void howToPlay(){
       alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    private void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.how_to_play);
        builder.setMessage(R.string.how_to_play_message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
    }
}
