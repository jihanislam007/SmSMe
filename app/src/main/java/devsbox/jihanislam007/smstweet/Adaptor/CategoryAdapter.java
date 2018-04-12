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

import devsbox.jihanislam007.smstweet.Activity.Upload_Sms.UploadSmsActivity;
import devsbox.jihanislam007.smstweet.ModelClass.CategoryList;
import devsbox.jihanislam007.smstweet.R;
import devsbox.jihanislam007.smstweet.Server_info.ServerInfo;

/**
 * Created by muhmmod on 3/21/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.myViewHolder>{

    Context mcontext;
    ArrayList<CategoryList> categoryList;

    public CategoryAdapter(Context context, ArrayList<CategoryList> categoryList){
        this.mcontext = context;
        this.categoryList = categoryList;
    }

    @Override
    public CategoryAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_category_view_layout_list,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.myViewHolder holder, final int position) {

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
                view.getContext().startActivity(new Intent(mcontext, UploadSmsActivity.class).putExtra("id",categoryList.get(position).categoryId));
            }
        });
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
