package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ScorerActivity extends AppCompatActivity {
    private EditText scorer;
    private EditText minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
    }
    public void handleMatch(View view) {
        EditText scorer =(EditText) findViewById(R.id.edit_Scorer);
        EditText minute =(EditText) findViewById(R.id.edit_Minute);
        String stringToPassBack = scorer.getText().toString();
        String stringToPassBack2 = minute.getText().toString();

        // Put the String to pass back into an Intent and close this activity
        Intent intent = new Intent();
        intent.putExtra("scorer_key", stringToPassBack);
        intent.putExtra("minute_key", stringToPassBack2);
        setResult(RESULT_OK, intent);
        finish();
    }
}
