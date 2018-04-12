package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
import devsbox.jihanislam007.smstweet.Interface.GoFullScreen;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class CategorySmsViewActivity extends AppCompatActivity implements GoFullScreen {

    RecyclerView recyclerView;
    ProfileAdaptor profileAdaptor;
    ArrayList<ProfileData> profileData = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    boolean isLoading=false;
    ProgressDialog progressDialog = null;
    int currentPage=0;
    int selectedId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_view);


        recyclerView = findViewById(R.id.CategorySmsView);

        //loading recyclerView//
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        profileAdaptor = new ProfileAdaptor(this,profileData);
        recyclerView.setAdapter(profileAdaptor);

        //testLoadData();

        selectedId=getIntent().getIntExtra("id",0);

        //loading recyclerView//
        CategorySmsViewDataFromServer();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    //int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    CategorySmsViewDataFromServer();
                }
            }
        });

    }

    private void CategorySmsViewDataFromServer() {

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

        final ProgressDialog finalProgressDialog1 = progressDialog;
        RequestParams params=new RequestParams();

        client.get(ServerInfo.BASE_ADDRESS+"GetAllSmsInList?subCategoryId="+selectedId+"&page="+currentPage,params,new JsonHttpResponseHandler(){
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
                Toast.makeText(CategorySmsViewActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();

            }
            /***************************************/
        });


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

    public void categoryViewBackIV(View view) {
        finish();
    }
}
