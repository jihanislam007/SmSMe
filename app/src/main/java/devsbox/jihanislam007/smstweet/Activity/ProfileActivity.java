package devsbox.jihanislam007.smstweet.Activity;

import android.app.Dialog;
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

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Adaptor.ProfileAdaptor;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;

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

        testLoadData();
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
