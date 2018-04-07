package devsbox.jihanislam007.smstweet.Activity.Upload_Sms;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpResponse;
import devsbox.jihanislam007.smstweet.Activity.MainActivity;
import devsbox.jihanislam007.smstweet.Activity.ProfileActivity;
import devsbox.jihanislam007.smstweet.DB.OfflineInfo;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

public class UploadSmsActivity extends AppCompatActivity {

    EditText smsTitle,
            smsBody;

    Button uplodNow ,
            DoneButton;
    String selectedId="";
    OfflineInfo offlineInfo;

    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sms);

        offlineInfo=new OfflineInfo(this);

        selectedId=getIntent().getIntExtra("id",0)+"";

        smsTitle = findViewById(R.id.smsTitleEditText);
        smsBody = findViewById(R.id.smsBodyEditText);

        uplodNow = findViewById(R.id.uploadNowButton);

        // Font path
        String fontPath = "fonts/Roboto-Light.ttf";
        // Loading Font Face
        Typeface roboto = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        smsTitle.setTypeface(roboto);
        smsBody.setTypeface(roboto);

        uplodNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadSmsDataserver(selectedId,
                        smsTitle.getText().toString(),smsBody.getText().toString());
            }
        });

    }

    private void uploadSmsDataserver(String SubCategoryId,String Title,String Text) {

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
        client.addHeader("Authorization",offlineInfo.getUserInfo().token);

        RequestParams params=new RequestParams();
        params.add("SubCategoryId",SubCategoryId);
        params.add("Title",Title);
        params.add("Text",Text);
        System.out.println(params);

        final ProgressDialog finalProgressDialog1 = progressDialog;

        client.post(ServerInfo.BASE_ADDRESS+"UploadSms",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                    try {

                        /*String SubCategoryId = response.getString("SubCategoryId");
                        String Title = response.getString("Title");
                        int Text = response.getInt("Text");*/

                        Toast.makeText(UploadSmsActivity.this,"Upload successfully",Toast.LENGTH_LONG).show();

                        pop();
                        mDialog.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                finalProgressDialog1.dismiss();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(UploadSmsActivity.this, "Data didn't uploaded "+responseString, Toast.LENGTH_SHORT).show();

            }

        });

    }

    public void pop() {

        mDialog = new Dialog(UploadSmsActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.popup_layout);

     //   mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        DoneButton = mDialog.findViewById(R.id.DoneButton);
        /*DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/


    }
}
