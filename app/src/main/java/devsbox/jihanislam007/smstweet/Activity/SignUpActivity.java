package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsCatagorySelectorActivity;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

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


    OfflineInfo offlineInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        offlineInfo=new OfflineInfo(this);

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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((passwordEditText.getText().toString()).equals(confirmPassEditText.getText().toString())){
                    SignUp_server(usernameEditText.getText().toString(),passwordEditText.getText().toString(),confirmPassEditText.getText().toString());
            }else{
                    Toast.makeText(SignUpActivity.this,"Password doesn't match",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void SignUp_server(final String email, String fullName, final String password) {
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
        params.add("FullName",fullName);
        params.add("Password",password);

        final ProgressDialog finalProgressDialog1 = progressDialog;

        client.post(ServerInfo.BASE_ADDRESS+"CreateUser",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                offlineInfo.setUserInfo(response.toString());

                try {
                    if(response.getBoolean("result")){
                        Toast.makeText(SignUpActivity.this,"Sign up successful", Toast.LENGTH_LONG).show();
                        Login( email,  password);

                    }
                    else {

                        Toast.makeText(SignUpActivity.this,response.getString("errorMsg"),Toast.LENGTH_LONG).show();

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
                Toast.makeText(SignUpActivity.this, "User name or password invalid....", Toast.LENGTH_SHORT).show();

            }

        });


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

                Intent intent=new Intent(SignUpActivity.this,UploadSmsCatagorySelectorActivity.class);
                Toast.makeText(SignUpActivity.this,"Login successful", Toast.LENGTH_LONG).show();
                try {
                    if(response.getBoolean("result")){
                        startActivity(intent);}
                    else {Toast.makeText(SignUpActivity.this, "User name or password invalid....", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(SignUpActivity.this, "User name or password invalid....", Toast.LENGTH_SHORT).show();

            }

        });


    }
}
