package com.example.jinshubao.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.model.Banner;

import java.util.Objects;

public class BannerListAdapter extends ArrayAdapter<Banner> {

    private int resource;

    public BannerListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Banner banner = Objects.requireNonNull(getItem(position));
        // 若无可重用的 view 则进行加载
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.banner_image);
            viewHolder.textView = convertView.findViewById(R.id.banner_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.textView.setText(banner.getDesc());
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
