package devsbox.jihanislam007.smstweet.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import de.hdodenhof.circleimageview.CircleImageView;
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsActivity;
import devsbox.jihanislam007.smstweet.Adaptor.ProfileAdaptor;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.Interface.GoFullScreen;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class ProfileActivity extends AppCompatActivity implements GoFullScreen {

    TextView userName,
            textview_one,
            uploadSmsCount;

    String selectedId="";
    int currentPage=0;
    int selectId=0;

    ImageView back,
            coverImageView,
            settingPopUp;
    CircleImageView profile_image,
                    SettingprofileImage;
    Dialog mDialog;

    EditText SettingUserNameTV,
            SettingPasswordTV,
            SettingConfirmePasswordTV;
    Button SettingDoneButton;

    RecyclerView userSMSRecyclerView;
    ProfileAdaptor profileAdaptor;
    ArrayList<ProfileData> profileData = new ArrayList<>();

    OfflineInfo offlineInfo;
    File selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        offlineInfo=new OfflineInfo(this);

        userName = findViewById(R.id.UserNameTextView);
        textview_one = findViewById(R.id.textview_one);
        uploadSmsCount = findViewById(R.id.SMSCountTV);
        settingPopUp = findViewById(R.id.settingPopUp);
        profile_image = findViewById(R.id.profile_image);
        coverImageView = findViewById(R.id.coverImageView);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<Uri> results = Matisse.obtainResult(data);
            if(results.size()>0){
                selectedFile=new File(getPath(results.get(0)));
                Glide
                        .with(this)
                        .load(selectedFile)
                        .into(SettingprofileImage);
                System.out.println(selectedFile);

            }

        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public void checkPermission(){
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            return;
        }

        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                /*.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))*/
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(2);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

        SettingprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
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

        if(selectedFile!=null){
            try {
                params.put("UserImage",selectedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

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
            //    currentPage++;
             //   isLoading=false;
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        profileData.add(new ProfileData(jsonObject.getInt("smsId")+"",jsonObject.getString("title"),jsonObject.getString("text")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                profileAdaptor.notifyDataSetChanged();
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


        client.get(ServerInfo.BASE_ADDRESS+"GetUserInfo?userId="+offlineInfo.getUserInfo().user.id,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("user");

                    String user_name =jsonObject.getString("fullName");
                    userName.setText(user_name);

                    /*Glide
                            .with(ProfileActivity.this)
                            .load(ServerInfo.MEDIA_ADDRESS+"userId="+profileData.get(response).getLayoutImageURL())
                            .into(profile_image);*/
                   /* Glide
                            .with(ProfileActivity.this)
                            .load("profilePhoto")
                            .into(coverImageView);*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    int smsCount =response.getInt("smsCount");
                    uploadSmsCount.setText(Integer.toString(smsCount));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {


            }

        });

    }

    public void profileBackIV(View view) {
        finish();
    }

    @Override
    public void GoFullScreen(int position) {
        Gson gson=new Gson();
        String data=gson.toJson(profileData);
        Intent intent=new Intent(this,SmsFullViewActivity.class);
        intent.putExtra("selectedIndex",position);
        intent.putExtra("data",data);
        intent.putExtra("selectedId",selectId);
        intent.putExtra("currentPage",currentPage);
        startActivity(intent);
    }
}
