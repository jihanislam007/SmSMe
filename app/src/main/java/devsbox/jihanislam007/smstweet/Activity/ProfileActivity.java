package devsbox.jihanislam007.smstweet.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import devsbox.jihanislam007.smstweet.Adaptor.ProfileAdaptor;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class ProfileActivity extends AppCompatActivity {

    TextView userName,
            textview_one,
            uploadSmsCount;
    ImageView back,
            settingPopUp;
    Dialog mDialog;

    EditText SettingUserNameTV,
            SettingPasswordTV,
            SettingConfirmePasswordTV;
    Button SettingDoneButton;

    RecyclerView userSMSRecyclerView;
    ProfileAdaptor profileAdaptor;
    ArrayList<ProfileData> profileData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.UserNameTextView);
        textview_one = findViewById(R.id.textview_one);
        uploadSmsCount = findViewById(R.id.SMSCountTV);
        settingPopUp = findViewById(R.id.settingPopUp);

        userSMSRecyclerView = findViewById(R.id.userSMSRecyclerView);

        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        userName.setTypeface(roboto);
        textview_one.setTypeface(roboto);
        uploadSmsCount.setTypeface(roboto);

        ///////////////////// xx //////////////////////

        settingPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pop();
                mDialog.show();
            }
        });

        ////////////////for recycleview list///////////

        userSMSRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileAdaptor = new ProfileAdaptor(this, profileData);
        userSMSRecyclerView.setAdapter(profileAdaptor);

        ProfileDataFromServer();


    }

    public void pop() {

        mDialog = new Dialog(ProfileActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.popup_setting_layout);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        SettingUserNameTV =mDialog.findViewById(R.id.SettingUserNameTV);
        SettingPasswordTV = mDialog.findViewById(R.id.SettingPasswordTV);
        SettingConfirmePasswordTV = mDialog.findViewById(R.id.SettingConfirmePasswordTV);
        SettingDoneButton = mDialog.findViewById(R.id.SettingDoneButton);


    }

    private void ProfileDataFromServer() {
        String tag_string_req = "req_login";
        ProgressDialog progressDialog = null;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        final ProgressDialog finalProgressDialog = progressDialog;

        /*************Must write*************************************/
        AsyncHttpClient client=new AsyncHttpClient();

        final ProgressDialog finalProgressDialog1 = progressDialog;
        RequestParams params=new RequestParams();

        client.get(ServerInfo.BASE_ADDRESS+"UserUploadedList",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                for(int i=0; i<=response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        profileData.add(new ProfileData(jsonObject.getString("title"),jsonObject.getString("text")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //   String

                }
                profileAdaptor.notifyDataSetChanged();
            }

            /*****************Must write*****************************/
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();

            }
            /***************************************/
        });


    }

    public void testLoadData() {

        ProfileData a = new ProfileData("sms one", "this is first sms. hope we have make fun from this app");
        profileData.add(a);

        ProfileData b = new ProfileData("sms one", "this is first sms. hope we have make fun from this app");
        profileData.add(b);

        ProfileData c = new ProfileData("sms one", "this is first sms. hope we have make fun from this app");
        profileData.add(c);

        ProfileData d = new ProfileData("sms one", "this is first sms. hope we have make fun from this app." +
                "this is first sms. hope we have make fun from this app.this is first sms. " +
                "hope we have make fun from this app.this is first sms. hope we have make fun from this app." +
                "this is first sms. hope we have make fun from this app.this is first sms. " +
                "hope we have make fun from this app");
        profileData.add(d);

        ProfileData e = new ProfileData("sms one", "this is first sms. hope we have make fun from this app");
        profileData.add(e);
    }
}
