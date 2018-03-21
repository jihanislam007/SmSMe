package devsbox.jihanislam007.smstweet.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import devsbox.jihanislam007.smstweet.R;

public class LogInActivity extends AppCompatActivity {

    TextView text,
            forgotPassword;
    EditText username,
            password;
    Button logIn,
            signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        text = findViewById(R.id.textTV);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);

        forgotPassword = findViewById(R.id.forgotPasswordTV);

        logIn = findViewById(R.id.logInButton);
        signUp = findViewById(R.id.SignupButton);

        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        text.setTypeface(roboto);
        username.setTypeface(roboto);
        password.setTypeface(roboto);
        forgotPassword.setTypeface(roboto);
        logIn.setTypeface(roboto);
        signUp.setTypeface(roboto);

        ///////////////////// xx //////////////////////
    }
}
