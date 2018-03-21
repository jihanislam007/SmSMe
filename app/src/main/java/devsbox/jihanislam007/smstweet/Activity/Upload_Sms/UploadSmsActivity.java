package devsbox.jihanislam007.smstweet.Activity.Upload_Sms;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import devsbox.jihanislam007.smstweet.R;

public class UploadSmsActivity extends AppCompatActivity {

    EditText smsTitle,
            smsBody;

    Button uplodNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sms);

        smsTitle = findViewById(R.id.smsTitleEditText);
        smsBody = findViewById(R.id.smsBodyEditText);

        uplodNow = findViewById(R.id.uploadNowButton);

        // Font path
        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        smsTitle.setTypeface(roboto);
        smsBody.setTypeface(roboto);
        smsBody.setTypeface(roboto);
    }
}
