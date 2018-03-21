package devsbox.jihanislam007.smstweet.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import devsbox.jihanislam007.smstweet.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView forgot,
            tv;
    Button send;
    EditText send_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot = findViewById(R.id.forgotPasswordTV);
        tv = findViewById(R.id.textview);

        send_mail = findViewById(R.id.emailEditText);
        send = findViewById(R.id.send_btn);

        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        forgot.setTypeface(roboto);
        tv.setTypeface(roboto);
        send_mail.setTypeface(roboto);
        send.setTypeface(roboto);

        ///////////////////// xx //////////////////////
    }
}
