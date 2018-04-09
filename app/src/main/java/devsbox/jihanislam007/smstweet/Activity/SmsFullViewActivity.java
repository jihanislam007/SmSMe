package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class SmsFullViewActivity extends AppCompatActivity {

    Button copy;
    TextView title,
            textBody;
    int selectedPosition=0;

    LinearLayout leftArrow;
    LinearLayout rightArrow;
    ArrayList<ProfileData> profileData;
    int selectedId=0;
    int currentPage=0;
    private boolean isLoading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_full_view);

        title = findViewById(R.id.smsTitleTV);
        textBody = findViewById(R.id.smsBodyTV);
        copy = findViewById(R.id.uploadNowButton);
        leftArrow=findViewById(R.id.leftArrow);
        rightArrow=findViewById(R.id.rightArrow);

        /////////// Font path ////////////////////

        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        title.setTypeface(roboto);
        textBody.setTypeface(roboto);
        copy.setTypeface(roboto);

        ///////////////////// xx //////////////////////


        try {
            String data=getIntent().getStringExtra("data");
            System.out.println(data);
            selectedPosition=getIntent().getIntExtra("selectedIndex",0);
            selectedId=getIntent().getIntExtra("selectedId",0);
            currentPage=getIntent().getIntExtra("currentPage",0);
            Gson gson=new Gson();
            Type type = new TypeToken<ArrayList<ProfileData>>() {}.getType();
            profileData=gson.fromJson(data,type);
            title.setText(profileData.get(selectedPosition).getSmsTitle());
            textBody.setText(profileData.get(selectedPosition).getSmsBody());
        } catch (JsonSyntaxException e) {

        }


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPosition>0){
                    selectedPosition--;
                    title.setText(profileData.get(selectedPosition).getSmsTitle());
                    textBody.setText(profileData.get(selectedPosition).getSmsBody());
                    CategorySmsViewDataFromServer();
                }
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPosition<profileData.size()-1){
                    selectedPosition++;
                    title.setText(profileData.get(selectedPosition).getSmsTitle());
                    textBody.setText(profileData.get(selectedPosition).getSmsBody());

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
        }

        isLoading=true;
        /*************Must write*************************************/
        AsyncHttpClient client=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        client.get(ServerInfo.BASE_ADDRESS+"GetAllSmsInList?subCategoryId="+selectedId+"&page="+currentPage,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                currentPage++;
                isLoading=false;
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        profileData.add(new ProfileData(jsonObject.getString("title"),jsonObject.getString("text")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //   String

                }
            }

            /*****************Must write*****************************/
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                isLoading=false;
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SmsFullViewActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();

            }
            /***************************************/
        });


    }
}
