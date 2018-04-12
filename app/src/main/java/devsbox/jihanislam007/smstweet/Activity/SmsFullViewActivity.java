package devsbox.jihanislam007.smstweet.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class SmsFullViewActivity extends AppCompatActivity {

    Button copy;
    TextView title,
            textBody;
    int selectedPosition=0;

    ImageView FavaroitImageView;

    LinearLayout leftArrow;
    LinearLayout rightArrow;
    ArrayList<ProfileData> profileData;
    int selectedId=0;
    int currentPage=0;
    private boolean isLoading=false;
    OfflineInfo offlineInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_full_view);

        offlineInfo=new OfflineInfo(this);

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

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=title.getText().toString()+"\n"+textBody.getText().toString();
                setClipboard(SmsFullViewActivity.this,s);
            }
        });

        FavaroitImageView = findViewById(R.id.FavaroitImageView);

        FavaroitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeSms();
            }
        });


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
    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Successfully copy..", Toast.LENGTH_SHORT).show();
    }


    private void likeSms() {

        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization",offlineInfo.getUserInfo().token);
        RequestParams params=new RequestParams();
        client.get(ServerInfo.BASE_ADDRESS+"AddToLike?smsId="+profileData.get(selectedPosition).getSmsId(),params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try {
                    if(response.getBoolean("isLike")){
                        Toast.makeText(SmsFullViewActivity.this, "Successfully like", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SmsFullViewActivity.this, "Successfully unlike", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

                        profileData.add(new ProfileData(jsonObject.getInt("smsId")+"",jsonObject.getString("title"),jsonObject.getString("text")));

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

    public void smsFullViewBackIV(View view) {
        finish();
    }
}
