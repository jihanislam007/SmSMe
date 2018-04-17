package devsbox.jihanislam007.smstweet.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import devsbox.jihanislam007.smstweet.Activity.CategorySmsViewActivity;
import devsbox.jihanislam007.smstweet.Activity.SmsFullViewActivity;
import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsActivity;
import devsbox.jihanislam007.smstweet.Animation.AnimationUtil;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

/**
 * Created by muhmmod on 4/5/18.
 */

public class SubCatSMS_viewAdapter extends RecyclerView.Adapter<SubCatSMS_viewAdapter.myViewHolder>{

    Context mcontext;
    ArrayList<CategoryList> categoryList;

    public SubCatSMS_viewAdapter(Context context, ArrayList<CategoryList> categoryList){
        this.mcontext = context;
        this.categoryList = categoryList;
    }

    @Override
    public SubCatSMS_viewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_category_view_layout_list,parent,false);
        return new SubCatSMS_viewAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubCatSMS_viewAdapter.myViewHolder holder, final int position) {

        /*Glide.with(mcontext)
                .load(categoryList.get(position).getLayoutImageURL())
                .centerCrop()
                .placeholder(R.drawable.demo)
                .into(backgroundLayout);*/
        //Glide.with(mcontext).load(categoryList.get(position).getLayoutImageURL()).into(holder.backgroundImageView);
        Glide.with(mcontext)
                .load(ServerInfo.MEDIA_ADDRESS+categoryList.get(position).getLayoutImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.backgroundImageView);
        holder.catagoryNameTV.setText(categoryList.get(position).getCategoryName());
        holder.backgroundImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(mcontext, CategorySmsViewActivity.class).putExtra("id",categoryList.get(position).categoryId));
            }
        });

        /*///////////for animation///////////////
        int previousPosition =0;
        if(position>previousPosition){

            AnimationUtil.animate(holder,true);
        }else {
            AnimationUtil.animate(holder,true);
        }

        previousPosition = position;*/
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout backgroundLayout;
        TextView catagoryNameTV;
        ImageView backgroundImageView;

        public myViewHolder(View itemView) {
            super(itemView);

            backgroundImageView = itemView.findViewById(R.id.backgroundImageView);
            catagoryNameTV = itemView.findViewById(R.id.catagoryNameTV);

            /////////// Font path ////////////////////

            String fontPath = "fonts/Roboto-Light.ttf";
            // Loading Font Face
            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), fontPath);
            // Applying font
            catagoryNameTV.setTypeface(roboto);

            ///////////////////// xx //////////////////////

            //backgroundImageView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
