package devsbox.jihanislam007.smstweet.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import devsbox.jihanislam007.smstweet.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    sleep(1000);

                    startActivity(new Intent(getApplicationContext(),
                            MainActivity.class));

                    finish();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
}