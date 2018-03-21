package devsbox.jihanislam007.smstweet.Activity;

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

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Adaptor.CategoryAdapter;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryList> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    //    getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //////////////recyclerView load////////////////////////////
        recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryAdapter = new CategoryAdapter(this,categoryList);
        recyclerView.setAdapter(categoryAdapter);

        testingLoadData();

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
            // Handle the camera action
            Intent in = new Intent(this,MainActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_english_sms) {
            Intent in = new Intent(this,MainActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_banglish_sms) {
            Intent in = new Intent(this,MainActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_favorite_sms) {
            Intent in = new Intent(this,FavoriteSMSActivity.class);
            startActivity(in);

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
