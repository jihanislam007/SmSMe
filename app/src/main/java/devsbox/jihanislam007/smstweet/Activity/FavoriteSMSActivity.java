package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
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
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.Interface.GoFullScreen;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class FavoriteSMSActivity extends AppCompatActivity implements GoFullScreen{

    RecyclerView recyclerView;
    ProfileAdaptor profileAdaptor;
    ArrayList<ProfileData> profileData = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    ProgressDialog progressDialog = null;
    int currentPage=0;
    int selectedId=0;
    OfflineInfo offlineInfo;
    private boolean isLoading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_sms);

        /////////////////ToDo :Banner Add/////////////////
        BannerAdd();

        recyclerView = findViewById(R.id.FavouriteSMS);
        offlineInfo=new OfflineInfo(this);
        //loading recyclerView//

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        profileAdaptor = new ProfileAdaptor(this,profileData);
        recyclerView.setAdapter(profileAdaptor);

        //////////////ToDo :for animation////////////
        LayoutAnimationController controller = null;
        controller = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

        //////////////for animation////////////

        FavoriteDataFromServer();

    }

    private void FavoriteDataFromServer() {

        if(isLoading)
            return;
        if(currentPage==0){
            String tag_string_req = "req_login";

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            profileData.clear();
        }

        isLoading=true;
        final ProgressDialog finalProgressDialog = progressDialog;

        /*************Must write*************************************/
        AsyncHttpClient client=new AsyncHttpClient();

        client.addHeader("Authorization",offlineInfo.getUserInfo().token);

        final ProgressDialog finalProgressDialog1 = progressDialog;
        RequestParams params=new RequestParams();

        client.get(ServerInfo.BASE_ADDRESS+"UserFavouriteList",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                currentPage++;
                isLoading=false;
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        profileData.add(new ProfileData(jsonObject.getInt("smsId")+"",jsonObject.getString("title"),jsonObject.getString("text")));

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
                isLoading=false;
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(FavoriteSMSActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();

            }
            /***************************************/
        });


    }

    public void favoriteBackIV(View view) {
        finish();
    }

    @Override
    public void GoFullScreen(int position) {
        Gson gson=new Gson();
        String data=gson.toJson(profileData);
        Intent intent=new Intent(this,SmsFullViewActivity.class);
        intent.putExtra("selectedIndex",position);
        intent.putExtra("data",data);
        intent.putExtra("selectedId",selectedId);
        intent.putExtra("currentPage",currentPage);
        startActivity(intent);
    }

    public void BannerAdd(){
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Request for Ads
        AdRequest adRequest1 = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("947B975E60AF133A105A2C362E253C35") //Random Text
                .build();

        mAdView.loadAd(adRequest1);
    }
}
