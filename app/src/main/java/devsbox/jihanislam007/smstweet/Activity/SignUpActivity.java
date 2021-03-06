package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

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
            googleButton/*,
            tweeterButton*/;


    OfflineInfo offlineInfo;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=121;
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Twitter.initialize(this);

        offlineInfo=new OfflineInfo(this);

        textView_one = findViewById(R.id.textview_one);
        TextView_or = findViewById(R.id.textOr);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPassEditText = findViewById(R.id.confirmPasswordEditText);

        signUp = findViewById(R.id.logInButton);
        fbButton = findViewById(R.id.login_fb_button);
        googleButton = findViewById(R.id.googleButton);
       // tweeterButton = findViewById(R.id.tweeterButton);

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
        //  fbButton.setTypeface(roboto);
        googleButton.setTypeface(roboto);
        //tweeterButton.setTypeface(roboto);

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



        /*private static final String EMAIL = "email";

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/

        /*****************google sign in**********************************/
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
        /*****************************************************************/


        /***************************Twitter****************************************/
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });
        /*******************************************************************/


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


    /*************************Google sign in****************************************/

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }



        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            ExtarnalLogin(account.getEmail());
        } catch (ApiException e) {
           e.printStackTrace();
        }
    }
    /********************************************************************************/



    public void ExtarnalLogin(String email){
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
    }
}
