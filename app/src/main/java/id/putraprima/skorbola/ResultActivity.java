package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView result;
    private TextView pemenang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = findViewById(R.id.textView2);
        pemenang = findViewById(R.id.textView3);

        Bundle extras = getIntent().getExtras();
        int homeResult = extras.getInt("HomeTextScore");
        int awayResult = extras.getInt("AwayTextScore");
        String homename = extras.getString("homename");
        String awayname = extras.getString("awayname");


        if (extras != null) {
            result.setText(String.valueOf(homeResult)+"-"+String.valueOf(awayResult));
            if (homeResult > awayResult){
                pemenang.setText(homename + " memenangkan pertandingan");
            } else if (homeResult < awayResult){
                pemenang.setText(awayname+" menangkan pertandingan");
            } else {
                pemenang.setText(" pertandingan imbang");
            }
        }
    }
}
