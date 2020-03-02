package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {

    public static final int HOME_ACTIVITY_REQUEST_CODE = 1;
    public static final int AWAY_ACTIVITY_REQUEST_CODE = 2;

    private TextView homeText;
    private TextView awayText;
    private TextView homeTextScore;
    private TextView awayTextScore;
    String homename;
    String awayname;
    String homescorer = "";
    String awayscorer = "";

    private ImageView homeImage;
    private ImageView awayImage;

    private int homeScore;
    private int awayScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"

        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        homeTextScore = findViewById(R.id.score_home);
        awayTextScore = findViewById(R.id.score_away);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            homename = extras.getString(MainActivity.HOME_KEY);
            awayname = extras.getString(MainActivity.AWAY_KEY);
            String homeURL = extras.getString(MainActivity.HOMEIMG_KEY);
            String awayURL = extras.getString(MainActivity.AWAYIMG_KEY);
            homeText.setText(homename);
            awayText.setText(awayname);
            try {
                Bitmap homebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(homeURL));
                Bitmap awaybitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(awayURL));
                homeImage.setImageBitmap(homebitmap);
                awayImage.setImageBitmap(awaybitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void homeScore(View view) {
        homeScore++;
        homeTextScore.setText(String.valueOf(homeScore));
    }

    public void awayScore(View view) {
        awayScore++;
        awayTextScore.setText(String.valueOf(awayScore));
    }

    public void handleResult(View view) {
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("homename", homename);
        intent.putExtra("awayname", awayname);
        intent.putExtra("HomeTextScore",homeScore);
        intent.putExtra("AwayTextScore",awayScore);
        startActivity(intent);
        // intent.putExtra(AWAYIMG_KEY, awayURL);

        startActivity(intent);
    }

    public void HomeScoreHandle(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
    }

    public void AwayScoreHandle(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, AWAY_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == HOME_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                homeScore++;
                homeTextScore.setText(String.valueOf(homeScore));

                // Get String data from Intent
                String tmp = "\n" + data.getStringExtra("scorer_key") + " " +
                data.getStringExtra("minute_key") + " \"";

                homescorer = homescorer + tmp;
                // Set text view with string
                TextView textView = findViewById(R.id.textView7);
                textView.setText(homescorer);
            }
        }
        else if (requestCode == AWAY_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                awayScore++;
                awayTextScore.setText(String.valueOf(awayScore));
                // Get String data from Intent
                String tmp = "\n" + data.getStringExtra("scorer_key") + " " +
                data.getStringExtra("minute_key") + " \"" ;

                awayscorer = awayscorer + tmp;

                // Set text view with string
                TextView textView = findViewById(R.id.textView8);
                textView.setText(awayscorer);
            }
        }
    }
}
