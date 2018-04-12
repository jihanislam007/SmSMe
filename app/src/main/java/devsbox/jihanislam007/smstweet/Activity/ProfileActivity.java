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
import de.hdodenhof.circleimageview.CircleImageView;
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsActivity;
import devsbox.jihanislam007.smstweet.Adaptor.ProfileAdaptor;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class ProfileActivity extends AppCompatActivity {

    TextView userName,
            textview_one,
            uploadSmsCount;

    String selectedId="";

    ImageView back,
            settingPopUp;
    CircleImageView SettingprofileImage;
    Dialog mDialog;

    EditText SettingUserNameTV,
            SettingPasswordTV,
            SettingConfirmePasswordTV;
    Button SettingDoneButton;

    RecyclerView userSMSRecyclerView;
    ProfileAdaptor profileAdaptor;
    ArrayList<ProfileData> profileData = new ArrayList<>();

    OfflineInfo offlineInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        offlineInfo=new OfflineInfo(this);

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

        ProfileDataServer();

    }

    public void pop() {

        mDialog = new Dialog(ProfileActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.popup_setting_layout);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        SettingprofileImage = mDialog.findViewById(R.id.SettingprofileImage);
        SettingUserNameTV =mDialog.findViewById(R.id.SettingUserNameTV);
        SettingPasswordTV = mDialog.findViewById(R.id.SettingPasswordTV);
        SettingConfirmePasswordTV = mDialog.findViewById(R.id.SettingConfirmePasswordTV);
        SettingDoneButton = mDialog.findViewById(R.id.SettingDoneButton);

        SettingDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SettingProfileUploadDataserver(selectedId,SettingUserNameTV.getText().toString(),SettingPasswordTV.getText().toString(),SettingConfirmePasswordTV.getText().toString());

            }
        });


    }

    private void SettingProfileUploadDataserver(String UserId,String user_name,String password ,String confirm_pass) {

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
        client.addHeader("Authorization",offlineInfo.getUserInfo().token);

        RequestParams params=new RequestParams();
        params.put("Email",offlineInfo.getUserInfo().user.email);
        params.put("FullName",user_name);
        params.put("OldPassword",password);
        params.put("NewPassword",confirm_pass);

        final ProgressDialog finalProgressDialog1 = progressDialog;

        client.post(ServerInfo.BASE_ADDRESS+"UpdateUser",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {

                    Toast.makeText(ProfileActivity.this,"Upload successfully",Toast.LENGTH_LONG).show();

                    pop();
                    mDialog.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Data didn't uploaded "+responseString, Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void ProfileDataServer() {

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
        client.addHeader("Authorization",offlineInfo.getUserInfo().token);

        RequestParams params=new RequestParams();

        final ProgressDialog finalProgressDialog1 = progressDialog;

        client.get(ServerInfo.BASE_ADDRESS+"UserUploadedList",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                for(int i=0;i<=response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                 //       profileData.add(new ProfileData(jsonObject.getString("title"),jsonObject.getString("text")));

                        String user_name =jsonObject.getString("uploadedUserName");
                        userName.setText(user_name);

                        int smsCount =jsonObject.getInt("count");
                        uploadSmsCount.setText(Integer.toString(smsCount));

                        profileData.add(new ProfileData(jsonObject.getInt("smsId")+"",jsonObject.getString("title"),jsonObject.getString("text")));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Conection Error "+responseString, Toast.LENGTH_SHORT).show();

            }

        });

    }

    public void profileBackIV(View view) {
        finish();
    }
}
