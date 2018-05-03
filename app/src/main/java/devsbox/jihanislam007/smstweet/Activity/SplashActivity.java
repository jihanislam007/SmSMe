package devsbox.jihanislam007.smstweet.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import java.util.Random;

import devsbox.jihanislam007.smstweet.R;

/*public class SplashActivity extends AppCompatActivity {

    TextView TryAgainTV;
    ImageView backgroundImageView;
    //   private TextView mLoadingText;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TryAgainTV = findViewById(R.id.TryAgainTV);
        backgroundImageView = findViewById(R.id.backgroundImageView);
        Glide
                .with(this)
                .load(R.drawable.icon)
                .into(backgroundImageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus++;
                    SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //   mLoadingText.setVisibility(View.VISIBLE);

                        if (isOnline()) {
                            //  Toast.makeText(SplashActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),
                                    MainActivity.class));

                            TryAgainTV.setVisibility(View.INVISIBLE);
                            finish();

                        } else {
                            Toast.makeText(SplashActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

                            TryAgainTV.setVisibility(View.VISIBLE);

                            TryAgainTV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                        }


                    }
                });
            }
        }).start();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}*/

    //extends AwesomeSplash!
    public class SplashActivity extends AwesomeSplash {

        //DO NOT OVERRIDE onCreate()!
        //if you need to start some services do it in initSplash()!


        @Override
        public void initSplash(ConfigSplash configSplash) {

			/* you don't have to override every property */

            //Customize Circular Reveal
            configSplash.setBackgroundColor(R.color.colorScreenBottom); //any color you want form colors.xml

            configSplash.setAnimCircularRevealDuration(2000); //int ms
            configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
            configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

            //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

            //Customize Logo
         //   configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
            configSplash.setLogoSplash(R.drawable.icon); //or any other drawable
            configSplash.setAnimLogoSplashDuration(2000); //int ms
            configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


          /*  //Customize Path
            configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
            configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
            configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
            configSplash.setAnimPathStrokeDrawingDuration(3000);
            configSplash.setPathSplashStrokeSize(3); //I advise value be <5
            configSplash.setPathSplashStrokeColor(R.color.accent); //any color you want form colors.xml
            configSplash.setAnimPathFillingDuration(3000);
            configSplash.setPathSplashFillColor(R.color.Wheat); //path object filling color
*/

            //Customize Title
            configSplash.setTitleSplash("SMS ME");
            configSplash.setTitleTextColor(R.color.colorWhite);
            configSplash.setTitleTextSize(30f); //float value
            configSplash.setAnimTitleDuration(2000);
            configSplash.setAnimTitleTechnique(Techniques.FlipInX);
            configSplash.setTitleFont("fonts/Roboto-Light.ttf"); //provide string to your font located in assets/fonts/

        }

        @Override
        public void animationsFinished() {

            //transit to another activity here
            //or do whatever you want
            startActivity(new Intent(getApplicationContext(),
                    MainActivity.class));
            finish();

        }
    }