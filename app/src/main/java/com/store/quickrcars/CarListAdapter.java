package com.store.quickrcars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.store.quickrcars.api.CarDetailModel;

import java.util.List;

/**
 * Created by hp pc on 11-07-2015.
 */
public class CarListAdapter extends ArrayAdapter<CarDetailModel> {

    private List<CarDetailModel> gameList;
    private Context mContext;


    public CarListAdapter(Context context, List<CarDetailModel> objects) {
        super(context, R.layout.car_list_item, objects);
        mContext = context;
        gameList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.car_list_item, parent, false);
            holder = new CustomViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.textView = (TextView) convertView.findViewById(R.id.title);
            holder.price = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        final CarDetailModel detailModel = getItem(position);
        holder.textView.setText(detailModel.getName());
        holder.price.setText("Price: "+detailModel.getPrice()+" Lakh");
        Picasso.with(mContext).load(detailModel.getImage()).into(holder.imageView);
        return convertView;
    }

    class CustomViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView price;
    }
}