package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsCatagorySelectorActivity;
import devsbox.jihanislam007.smstweet.Adaptor.CategoryAdapter;
import devsbox.jihanislam007.smstweet.Adaptor.SubCatSMS_viewAdapter;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    SubCatSMS_viewAdapter SubCatSMS_viewAdapter;
    ArrayList<CategoryList> categoryList = new ArrayList<>();

    OfflineInfo offlineInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        offlineInfo=new OfflineInfo(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("বাংলা SMS");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //////////////recyclerView load////////////////////////////
        recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        SubCatSMS_viewAdapter = new SubCatSMS_viewAdapter(this,categoryList);
        recyclerView.setAdapter(SubCatSMS_viewAdapter);

    //    testingLoadData();
        CategoryDataserver("bangla");

        //////////////recyclerView load////////////////////////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bangla_sms) {

            CategoryDataserver("bangla");
            setTitle("বাংলা SMS");

        } else if (id == R.id.nav_english_sms) {

            CategoryDataserver("english");
            setTitle("English SMS");

        } else if (id == R.id.nav_banglish_sms) {
            CategoryDataserver("banglish");
            setTitle("Banglish SMS");

        } else if (id == R.id.nav_favorite_sms) {


            if(offlineInfo.getUserInfo()!=null && offlineInfo.getUserInfo().token!=null && offlineInfo.getUserInfo().token.length()>0){
                Intent intent=new Intent(this,FavoriteSMSActivity.class);
                startActivity(intent);
            }else{
                Intent in = new Intent(this,LogInActivity.class);
                startActivity(in);
            }

        } else if (id == R.id.nav_More_Apps) {
            Intent in = new Intent(this,MainActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_rating) {
            Intent in = new Intent(this,MainActivity.class);
            startActivity(in);

        }else if (id == R.id.nav_upload) {
            Intent in = new Intent(this,LogInActivity.class);
            startActivity(in);

        }else if (id == R.id.nav_setting) {
            Intent in = new Intent(this,ProfileActivity.class);
            startActivity(in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
        //client.addHeader("Authorization","Bearer "+offlineInfo.getUserInfo().token);

        RequestParams params=new RequestParams();

        final ProgressDialog finalProgressDialog1 = progressDialog;

        client.get(ServerInfo.BASE_ADDRESS+"SubCategoryList?categoryName="+category,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                categoryList.clear();
                SubCatSMS_viewAdapter.notifyDataSetChanged();
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
                }SubCatSMS_viewAdapter.notifyDataSetChanged();


            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "There is no data", Toast.LENGTH_SHORT).show();

            }

        });


    }

    /*public void testingLoadData(){

        CategoryList a = new CategoryList("LOVE SMS","",0);
        categoryList.add(a);

        CategoryList b = new CategoryList("Friendship SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_friendship.png",1);
        categoryList.add(b);


        CategoryList c = new CategoryList("Funny SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_funny.png",1);
        categoryList.add(c);


        CategoryList d = new CategoryList("Birthday SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_birthday.png",1);
        categoryList.add(d);

        CategoryList e = new CategoryList("New Year SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_new_year.png",1);
        categoryList.add(e);

        CategoryList f = new CategoryList("Eid SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_eid.png",1);
        categoryList.add(f);

        CategoryList g = new CategoryList("Morning SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_morning.png",1);
        categoryList.add(g);

        CategoryList h = new CategoryList("Night SMS","https://github.com/jihanislam007/SmSTweet/blob/master/app/src/main/res/drawable/delete_night.png",1);
        categoryList.add(h);
    }*/
}
