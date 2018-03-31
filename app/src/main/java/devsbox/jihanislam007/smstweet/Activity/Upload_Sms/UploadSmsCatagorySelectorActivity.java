package devsbox.jihanislam007.smstweet.Activity.Upload_Sms;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import devsbox.jihanislam007.smstweet.Activity.MainActivity;
import devsbox.jihanislam007.smstweet.Adaptor.CategoryAdapter;
import devsbox.jihanislam007.smstweet.Adaptor.CategorySpinnerAdaptor;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryName;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class UploadSmsCatagorySelectorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryList> categoryList = new ArrayList<>();

    private List<CategoryName> categoryName;

    private Spinner spinner;
    private CategorySpinnerAdaptor categorySpinnerAdaptor;

    OfflineInfo offlineInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sms_catagory_selector);

        offlineInfo=new OfflineInfo(this);

        //////////////Category Spinner load////////////////////////////

        spinner = (Spinner) findViewById(R.id.categorySpinner);

        categoryName = new ArrayList<>();

        CategoryName a = new CategoryName("Bangla SMS");
        CategoryName b = new CategoryName("English SMS");
        CategoryName c = new CategoryName("Banglish SMS");

        categoryName.add(a);
        categoryName.add(b);
        categoryName.add(c);

        categorySpinnerAdaptor = new CategorySpinnerAdaptor(this, R.layout.spinner_item_layout, categoryName);
        categorySpinnerAdaptor.setDropDownViewResource(R.layout.spinner_item_layout);

        spinner.setAdapter(categorySpinnerAdaptor);
        spinner.setOnItemSelectedListener(this);



        //////////////recyclerView load////////////////////////////
        recyclerView = findViewById(R.id.outlet_rcview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryAdapter = new CategoryAdapter(this,categoryList);
        recyclerView.setAdapter(categoryAdapter);

    //    testingLoadData();

        //////////////recyclerView load////////////////////////////

    }

    /*public void testingLoadData(){

        CategoryList a = new CategoryList("LOVE SMS","");
        categoryList.add(a);

        CategoryList b = new CategoryList("Friendship SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_friendship.png");
        categoryList.add(b);


        CategoryList c = new CategoryList("Funny SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_funny.png");
        categoryList.add(c);


        CategoryList d = new CategoryList("Birthday SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_birthday.png");
        categoryList.add(d);

        CategoryList e = new CategoryList("New Year SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_new_year.png");
        categoryList.add(e);

        CategoryList f = new CategoryList("Eid SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_eid.png");
        categoryList.add(f);

        CategoryList g = new CategoryList("Morning SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_morning.png");
        categoryList.add(g);

        CategoryList h = new CategoryList("Night SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_night.png");
        categoryList.add(h);
    }*/

    //////// Spinner click listener////////
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(UploadSmsCatagorySelectorActivity.this,"you clicked on"+i,Toast.LENGTH_LONG).show();

        if(categoryName.get(i).getCategory().equals("Bangla SMS")){
            CategoryDataserver("bangla");
        }else if(categoryName.get(i).getCategory().equals("English SMS")){
            CategoryDataserver("english");
        }else{
            CategoryDataserver("banglish");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    /////// Spinner click listener////////

    private void CategoryDataserver(String category) {

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
        client.addHeader("Authorization","Bearer "+offlineInfo.getUserInfo().token);

        RequestParams params=new RequestParams();

        final ProgressDialog finalProgressDialog1 = progressDialog;

        client.get(ServerInfo.BASE_ADDRESS+"SubCategoryList?categoryName="+category,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                categoryList.clear();
                categoryAdapter.notifyDataSetChanged();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        String catagory = jsonObject.getString("category");
                        String subCategoryName = jsonObject.getString("subCategoryName");

                        int subCategoryId = jsonObject.getInt("subCategoryId");
                        String photo = jsonObject.getString("photo");

                        CategoryList category_data = new CategoryList(subCategoryName,photo,subCategoryId);

                        categoryList.add(category_data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }categoryAdapter.notifyDataSetChanged();


            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(UploadSmsCatagorySelectorActivity.this, "There is no data", Toast.LENGTH_SHORT).show();

            }

        });


    }
}

