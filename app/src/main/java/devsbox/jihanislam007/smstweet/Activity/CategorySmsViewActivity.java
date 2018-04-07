package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cz.msebera.android.httpclient.entity.mime.Header;
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsCatagorySelectorActivity;
import devsbox.jihanislam007.smstweet.Adaptor.ProfileAdaptor;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

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

        //testLoadData();

        //loading recyclerView//
        CategorySmsViewDataFromServer();

    }

    /*public void testLoadData() {

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
    }*/

    private void CategorySmsViewDataFromServer() {
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

        client.get(ServerInfo.BASE_ADDRESS+"GetAllSmsInList?subCategoryId=1&page=0",params,new JsonHttpResponseHandler(){
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
                Toast.makeText(CategorySmsViewActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();

            }
            /***************************************/
        });


    }

}
