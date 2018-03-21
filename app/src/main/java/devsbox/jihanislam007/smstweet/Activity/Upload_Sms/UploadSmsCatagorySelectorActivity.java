package devsbox.jihanislam007.smstweet.Activity.Upload_Sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Adaptor.CategoryAdapter;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.R;

public class UploadSmsCatagorySelectorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryList> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sms_catagory_selector);

        //////////////recyclerView load////////////////////////////
        recyclerView = findViewById(R.id.outlet_rcview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryAdapter = new CategoryAdapter(this,categoryList);
        recyclerView.setAdapter(categoryAdapter);

        testingLoadData();

        //////////////recyclerView load////////////////////////////

    }

    public void testingLoadData(){

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
    }
}
