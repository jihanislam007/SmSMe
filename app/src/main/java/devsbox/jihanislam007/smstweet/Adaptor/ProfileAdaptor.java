package devsbox.jihanislam007.smstweet.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Activity.SmsFullViewActivity;
import devsbox.jihanislam007.smstweet.Animation.AnimationUtil;
import devsbox.jihanislam007.smstweet.ModelClass.ProfileData;
import devsbox.jihanislam007.smstweet.R;

/**
 * Created by muhmmod on 3/21/18.
 */

public class ProfileAdaptor extends RecyclerView.Adapter<ProfileAdaptor.ViewHolder> {

    Context mcontext;
    ArrayList<ProfileData> profileData;

    private int previousPosition = 0;

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
    public void onBindViewHolder(ProfileAdaptor.ViewHolder holder, int position) {

        holder.SmsTitleTextView.setText(profileData.get(position).getSmsTitle());
        holder.SmsBodyTextView.setText(profileData.get(position).getSmsBody());

        ////////////////for animation start//////////////////////////
        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(holder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(holder, false);


        }

        previousPosition = position;


        final int currentPosition = position;
        final ProfileData infoData = profileData.get(position);

        /*holder.SmsTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Toast.makeText(context, "OnClick Called at position " + position, Toast.LENGTH_SHORT).show();
                addItem(currentPosition, profileData);
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
    }

    @Override
    public int getItemCount() {
        return profileData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView SmsTitleTextView,
                SmsBodyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            SmsTitleTextView = itemView.findViewById(R.id.SmsTitleTextView);
            SmsBodyTextView = itemView.findViewById(R.id.SmsBodyTextView);

            /////////// Font path ////////////////////

            String fontPath = "fonts/Roboto-Light.ttf";
            // Loading Font Face
            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), fontPath);
            // Applying font
            SmsTitleTextView.setTypeface(roboto);
            SmsBodyTextView.setTypeface(roboto);


            ///////////////////// xx //////////////////////

            SmsBodyTextView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //ToDo : go to sms viewer page//
            view.getContext().startActivity(new Intent(mcontext, SmsFullViewActivity.class));

        }
    }
}
