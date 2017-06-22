package com.windy.second.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.windy.second.R;

/**
 * Created by windy on 2017/6/18.
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    public ContentAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = mInflater.inflate(R.layout.item_content_layout, parent, false);
        return new ItemViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder baseHolder, int position) {
        if (baseHolder instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder) baseHolder;
            Picasso.with(mContext)
                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497767718425&di=249e4bc423ed22139f7a6a17855640ad&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fpiebbs%2F1411%2F09%2Fc0%2F40727186_1415513405764_1024x1024it.jpg")
                    .fit()
                    .centerCrop()
                    .into(holder.mCover);
            holder.mTittle.setText("这里写一个非常吸引人的标题，简单描述一下是什么手机");
            holder.mOldPrice.setText("￥5888");
            holder.mNowPrice.setText("￥1799");
            holder.mBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "我滴天呐，有人购买啦！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCover;
        private TextView mTittle;
        private TextView mOldPrice;
        private TextView mNowPrice;
        private TextView mBuy;
        public ItemViewHolder(View view){
            super(view);
            mCover = (ImageView) view.findViewById(R.id.iv_cover);
            mTittle = (TextView) view.findViewById(R.id.tv_title);
            mOldPrice = (TextView) view.findViewById(R.id.tv_old_price);
            mNowPrice = (TextView) view.findViewById(R.id.tv_now_price);
            mBuy = (TextView) view.findViewById(R.id.tv_buy);

            ViewGroup.LayoutParams params = mCover.getLayoutParams();
            if (params != null) {
                params.width = 1020;
                params.height = 510;
            }

            mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); //中划线、抗锯齿
        }
    }
}
