package com.example.jinshubao.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.model.ImageModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private RequestOptions requestOptions;
    private List<ImageModel.ImageVo> imageVoList;

    public ImageListAdapter(@NonNull Context context, RequestOptions requestOptions) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageVoList = new ArrayList<>();
        if (requestOptions != null) {
            this.requestOptions = requestOptions;
        } else {
            this.requestOptions = new RequestOptions()
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(new ColorDrawable(Color.BLUE))
                    .fallback(new ColorDrawable(Color.RED))
                    .centerCrop()
//                    .fitCenter()
                    .timeout(1000)
                    .theme(context.getTheme());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.image_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        ImageModel.ImageVo model = imageVoList.get(index);
        Glide.with(context).load(model.getUrl()).apply(requestOptions).into(holder.imageView);
        holder.imageView.setContentDescription(model.getWho());
        holder.textView.setText(model.getDesc());
    }

    @Override
    public int getItemCount() {
        return imageVoList.size();
    }

    /**
     * 添加元素
     *
     * @param list
     */
    public void addAll(Collection<ImageModel.ImageVo> list) {
        this.imageVoList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清空列表
     */
    public void clear() {
        this.imageVoList.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_title);
        }
    }
}
