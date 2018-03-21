package devsbox.jihanislam007.smstweet.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Adaptor.ProfileAdaptor;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;

public class CategorySmsViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProfileAdaptor profileAdaptor;
    ArrayList<ProfileData> profileData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_view);

        recyclerView = findViewById(R.id.CategorySmsView);

        //loading recyclerView//
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileAdaptor = new ProfileAdaptor(this,profileData);
        recyclerView.setAdapter(profileAdaptor);

        testLoadData();

        //loading recyclerView//
    }

    public void testLoadData() {

        ProfileData a1 = new ProfileData("SMS one", "this is first sms. hope we have make fun from this app");
        profileData.add(a1);

        ProfileData b1 = new ProfileData("SMS two", "this is first sms. hope we have make fun from this app");
        profileData.add(b1);

        ProfileData c1 = new ProfileData("SMS Three", "this is first sms. hope we have make fun from this app");
        profileData.add(c1);

        ProfileData d1 = new ProfileData("SMS Four", "this is first sms. hope we have make fun from this app." +
                "this is first sms. hope we have make fun from this app.this is first sms. " +
                "hope we have make fun from this app.this is first sms. hope we have make fun from this app." +
                "this is first sms. hope we have make fun from this app.this is first sms. " +
                "hope we have make fun from this app");
        profileData.add(d1);

        ProfileData e1 = new ProfileData("SMS Five", "this is first sms. hope we have make fun from this app");
        profileData.add(e1);
    }
}
