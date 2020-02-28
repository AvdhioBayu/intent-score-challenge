package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {
    Validator validator;
    public static final String HOME_KEY = "home";
    public static final String AWAY_KEY = "away";
    public static final String HOMEIMG_KEY = "homeURL";
    public static final String AWAYIMG_KEY = "awayURL";

    private static final int HOME_REQUEST_CODE = 1;
    private static final int AWAY_REQUEST_CODE = 2;

    @NotEmpty
    private EditText homeInput;
    @NotEmpty
    private EditText awayInput;

    private ImageView homeImage;
    private ImageView awayImage;

    private String homeURL;
    private String awayURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity

        homeInput = findViewById(R.id.home_team);
        awayInput = findViewById(R.id.away_team);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public void handleMatch(View view) {validator.validate();}

    public void handlehomeImg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, HOME_REQUEST_CODE);
    }

    public void handleawayImg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AWAY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }



        if (requestCode == HOME_REQUEST_CODE) {
            if (data != null) {
                try {
                    homeURL = data.getDataString();
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == AWAY_REQUEST_CODE) {
            if (data != null) {
                try {
                    awayURL = data.getDataString();
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awayImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onValidationSucceeded() {

        Intent intent = new Intent(this, MatchActivity.class);
        String home = homeInput.getText().toString();
        String away = awayInput.getText().toString();
        intent.putExtra(HOME_KEY, home);
        intent.putExtra(AWAY_KEY, away);
        intent.putExtra(HOMEIMG_KEY, homeURL);
        intent.putExtra(AWAYIMG_KEY, awayURL);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
