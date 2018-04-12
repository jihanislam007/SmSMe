package devsbox.jihanislam007.smstweet.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Activity.SmsFullViewActivity;
import devsbox.jihanislam007.smstweet.Interface.GoFullScreen;
import devsbox.jihanislam007.smstweet.Animation.AnimationUtil;
import devsbox.jihanislam007.smstweet.Interface.GoFullScreen;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;

/**
 * Created by muhmmod on 3/21/18.
 */

public class ProfileAdaptor extends RecyclerView.Adapter<ProfileAdaptor.ViewHolder> {

    Context mcontext;
    ArrayList<ProfileData> profileData;

    public ProfileAdaptor(Context context, ArrayList<ProfileData> profileData) {
        this.mcontext = context;
        this.profileData = profileData;
    }

    @Override
    public ProfileAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_sms_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileAdaptor.ViewHolder holder, final int position) {

        holder.SmsTitleTextView.setText(profileData.get(position).getSmsTitle());
        holder.SmsBodyTextView.setText(profileData.get(position).getSmsBody());



     /*   holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GoFullScreen)(mcontext)).GoFullScreen(position);
            }
        });

        holder.SmsBodyTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

             //   Toast.makeText(context, "OnLongClick Called at position " + position, Toast.LENGTH_SHORT).show();

                removeItem(infoData);

                return true;
            }


        });*/

        ////////////////for animation finish//////////////////////////


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GoFullScreen)(mcontext)).GoFullScreen(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView SmsTitleTextView,
                SmsBodyTextView;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            SmsTitleTextView = itemView.findViewById(R.id.SmsTitleTextView);
            SmsBodyTextView = itemView.findViewById(R.id.SmsBodyTextView);
            container = itemView.findViewById(R.id.container);

            /////////// Font path ////////////////////

            String fontPath = "fonts/Roboto-Light.ttf";
            // Loading Font Face
            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), fontPath);
            // Applying font
            SmsTitleTextView.setTypeface(roboto);
            SmsBodyTextView.setTypeface(roboto);


        }



    }
}
