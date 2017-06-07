package com.example.lotuscomputer.biker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lotus Computer on 03-Jun-17.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<RowItem> rowItems;

    CustomAdapter(Context context,List<RowItem> rowItems){
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder{
        ImageView bike_image;
        TextView bike_name;
        TextView bike_price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.custom_layout,null);
            holder = new ViewHolder();
            holder.bike_image = (ImageView)convertView.findViewById(R.id.imageView4);
            holder.bike_name = (TextView)convertView.findViewById(R.id.bike_name);
            holder.bike_price = (TextView)convertView.findViewById(R.id.bike_price);
            RowItem row_pos = rowItems.get(position);
            byte[] image = row_pos.getBike_image();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.bike_image.setImageBitmap(bitmap);
            holder.bike_name.setText(row_pos.getBike_name());
            holder.bike_price.setText(row_pos.getBike_price());
        }
        return convertView;
    }
}
