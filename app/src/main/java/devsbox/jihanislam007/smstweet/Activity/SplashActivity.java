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

import java.util.Random;

import devsbox.jihanislam007.smstweet.R;

public class SplashActivity extends AppCompatActivity {

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
}