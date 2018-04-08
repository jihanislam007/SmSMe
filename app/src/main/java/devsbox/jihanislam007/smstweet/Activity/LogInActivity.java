package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.mime.Header;
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsCatagorySelectorActivity;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class LogInActivity extends AppCompatActivity {

    TextView text_one,
            forgotPassword;
    EditText username,
            password;
    Button logIn,
            signUp;

    OfflineInfo offlineInfo;
    private final String TAG = "LoginActivity";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initialize();
        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        text_one.setTypeface(roboto);
        username.setTypeface(roboto);
        password.setTypeface(roboto);
        forgotPassword.setTypeface(roboto);
        logIn.setTypeface(roboto);
        signUp.setTypeface(roboto);

        ///////////////////// xx //////////////////////

        offlineInfo=new OfflineInfo(this);

        //Auto login if previously login
        if(offlineInfo.getUserInfo()!=null && offlineInfo.getUserInfo().token!=null && offlineInfo.getUserInfo().token.length()>0){
            Intent intent=new Intent(LogInActivity.this,UploadSmsCatagorySelectorActivity.class);
            startActivity(intent);
        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login(username.getText().toString(), password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in =new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(in);
            }
        });

    }

    private void initialize() {
        text_one = findViewById(R.id.TextView_one);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);

        forgotPassword = findViewById(R.id.forgotPasswordTV);

        logIn = findViewById(R.id.logInButton);
        signUp = findViewById(R.id.SignupButton);

    }

    private void Login(final String email, final String password) {
        String tag_string_req = "req_login";
        ProgressDialog progressDialog = null;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        final ProgressDialog finalProgressDialog = progressDialog;

        AsyncHttpClient client=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.add("Email",email);
        params.add("Password",password);

        final ProgressDialog finalProgressDialog1 = progressDialog;
        client.post(ServerInfo.BASE_ADDRESS+"Token",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                offlineInfo.setUserInfo(response.toString());

                Intent intent=new Intent(LogInActivity.this,UploadSmsCatagorySelectorActivity.class);
                try {
                    if(response.getBoolean("result")){
                        Toast.makeText(LogInActivity.this,"Login successful", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    finish();
                    }
                        else {Toast.makeText(LogInActivity.this, "User name or password invalid....", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(LogInActivity.this, "User name or password invalid....", Toast.LENGTH_SHORT).show();

            }

        });


    }

}
