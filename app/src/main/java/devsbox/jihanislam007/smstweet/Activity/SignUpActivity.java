package devsbox.jihanislam007.smstweet.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import devsbox.jihanislam007.smstweet.R;

public class SignUpActivity extends AppCompatActivity {

    TextView textView_one,
            TextView_or;

    EditText usernameEditText,
            passwordEditText,
            confirmPassEditText;

    Button signUp,
            fbButton,
            googleButton,
            tweeterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textView_one = findViewById(R.id.textview_one);
        TextView_or = findViewById(R.id.textOr);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPassEditText = findViewById(R.id.confirmPasswordEditText);

        signUp = findViewById(R.id.logInButton);
        fbButton = findViewById(R.id.facebookButton);
        googleButton = findViewById(R.id.googleButton);
        tweeterButton = findViewById(R.id.tweeterButton);

        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        textView_one.setTypeface(roboto);
        TextView_or.setTypeface(roboto);
        usernameEditText.setTypeface(roboto);
        passwordEditText.setTypeface(roboto);
        confirmPassEditText.setTypeface(roboto);
        signUp.setTypeface(roboto);
        fbButton.setTypeface(roboto);
        googleButton.setTypeface(roboto);
        tweeterButton.setTypeface(roboto);

        ///////////////////// xx //////////////////////
    }
}
