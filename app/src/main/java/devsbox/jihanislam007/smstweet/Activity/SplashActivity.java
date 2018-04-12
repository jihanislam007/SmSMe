package devsbox.jihanislam007.smstweet.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import devsbox.jihanislam007.smstweet.R;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
 //   private TextView mLoadingText;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
    ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        backgroundImageView=findViewById(R.id.backgroundImageView);
        Glide
                .with(this)
                .load(R.drawable.icon)
                .into(backgroundImageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(50);
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

                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        finish();
                    }
                });
            }
        }).start();
    }
}