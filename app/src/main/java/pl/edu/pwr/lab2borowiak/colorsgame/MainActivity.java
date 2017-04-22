package pl.edu.pwr.lab2borowiak.colorsgame;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static String musicOnOff = "music";
    @BindView(R.id.buttonMusic) ImageButton musicButton;
    AlertDialog alertDialog;
    boolean music = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setAlertDialog();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4411562214841710~8412621584");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("391B7E422EF2D7D3378C3E9C5F96DD09").build();
        mAdView.loadAd(adRequest);
    }

    @OnClick(R.id.buttonMusic)
    public void switchMusicOnOff(){
        if(music) {
            musicButton.setImageResource(R.drawable.music_note_off_black);
            music = false;
        }
        else {
            musicButton.setImageResource(R.drawable.music_note_black);
            music = true;
        }

    }


    @OnClick(R.id.buttonS)
    public void startNew(){
        Intent it = new Intent(this, GameActivity.class);
        it.putExtra(musicOnOff, music);
        startActivity(it);
    }

    @OnClick(R.id.buttonHowToPlay)
    public void howToPlay(){
       alertDialog.show();
    }


    @OnClick(R.id.buttonRateApp)
    public void rateApp(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=pl.edu.pwr.lab2borowiak.colorsgame"));
        if (!MyStartActivity(intent)) {
            //Market (Google play) app seems not installed, let's try to open a webbrowser
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=pl.edu.pwr.lab2borowiak.colorsgame"));
            if (!MyStartActivity(intent)) {
                //Well if this also fails, we have run out of options, inform the user.
                Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean MyStartActivity(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e) {
            return false;
        }
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
