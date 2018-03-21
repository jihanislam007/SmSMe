package devsbox.jihanislam007.smstweet.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import devsbox.jihanislam007.smstweet.R;

public class SmsFullViewActivity extends AppCompatActivity {

    Button copy;
    TextView title,
            textBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_full_view);

        title = findViewById(R.id.smsTitleTV);
        textBody = findViewById(R.id.smsBodyTV);
        copy = findViewById(R.id.uploadNowButton);

        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        title.setTypeface(roboto);
        textBody.setTypeface(roboto);
        copy.setTypeface(roboto);

        ///////////////////// xx //////////////////////
    }
}
