package com.example.jinshubao.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.model.NewsModel;

import java.util.Objects;

public class NewsListAdapter extends ArrayAdapter<NewsModel> {

    private int resource;

    private RequestOptions requestOptions;

    public NewsListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(new ColorDrawable(Color.BLUE))
                .fallback(new ColorDrawable(Color.RED));

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsModel model = Objects.requireNonNull(getItem(position));
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
        Glide.with(getContext()).load(model.getImgsrc()).apply(requestOptions).into(viewHolder.imageView);
        viewHolder.imageView.setContentDescription(model.getTitle());
        viewHolder.textView.setText(model.getTitle());
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
