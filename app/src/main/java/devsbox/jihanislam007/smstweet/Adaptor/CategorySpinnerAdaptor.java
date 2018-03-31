package devsbox.jihanislam007.smstweet.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import devsbox.jihanislam007.smstweet.ModelClass.CategoryName;
import devsbox.jihanislam007.smstweet.R;

/**
 * Created by muhmmod on 3/22/18.
 */

public class CategorySpinnerAdaptor extends ArrayAdapter<CategoryName> {
    private Context context;
    private List<CategoryName> categoryList;

    public CategorySpinnerAdaptor(Context context, int textViewResourceId, List<CategoryName> beatList) {
        super(context,textViewResourceId,beatList);
        this.context = context;
        this.categoryList = beatList;
    }

    public int getCount() {
        return categoryList.size();
    }

    public CategoryName getItem(int position) {
        return categoryList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item_layout, null);

            TextView category_name = (TextView) convertView.findViewById(R.id.categoryNameTextView);
            final CategoryName categoryName= getItem(position);            category_name.setText(categoryName.getCategory());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(android.R.layout.simple_spinner_item, parent,false);
        }

        TextView label = (TextView)row.findViewById(android.R.id.text1);
        label.setText(getItem(position).getCategory());
        label.setPadding(10, 15, 0, 15);

        label.setTextColor(Color.BLACK);

        ((TextView) row).setGravity(Gravity.CENTER);
        return row;
    }

}