package devsbox.jihanislam007.smstweet.DB;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import devsbox.jihanislam007.smstweet.ModelClass.AppUser;

/**
 * Created by muhammod on 3/21/18.
 */

public class OfflineInfo {SharedPreferences sharedpreferences;
    Context context;

    public OfflineInfo(Context context){
        if(context==null){
            System.out.println("Context is null....");
        }
        this.context=context;
        sharedpreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public void setUserInfo(String userInfo){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userInfo",userInfo);
        editor.commit();
        System.out.println("Successfully save user info....");
    }
    public AppUser getUserInfo(){
        Gson gson=new Gson();
        AppUser appUser=gson.fromJson(sharedpreferences.getString("userInfo",""),AppUser.class);
        return appUser;
    }





    public void clearAll(){
        sharedpreferences.edit().clear().apply();
    }
}