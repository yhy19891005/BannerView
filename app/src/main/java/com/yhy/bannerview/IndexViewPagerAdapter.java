package com.yhy.bannerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class IndexViewPagerAdapter extends PagerAdapter {
    private final String TAG = IndexViewPagerAdapter.class.getSimpleName();
    private Context mContext;
    private List<BannerInfo> mUrlList;

    public IndexViewPagerAdapter(Context context, List<BannerInfo> urlList) {
        mContext = context;
        mUrlList = urlList;
    }

    @Override
    public int getCount() {
        return mUrlList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String jump = mUrlList.get(position).getJump();
              Intent intent = new Intent(mContext,BannerDetailActivity.class);
              intent.putExtra("jump",jump);
              mContext.startActivity(intent);
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Log.d(TAG, "instantiateItem: 图片的路径 = " + mUrlList.get(position).getUrl());
        Log.d(TAG, "instantiateItem: 跳转地址 = " + mUrlList.get(position).getJump());

        Glide.with(mContext)
             .load(mUrlList.get(position)
             .getUrl())
             .placeholder(R.drawable.home_scroll_default)
             .error(R.drawable.home_scroll_default)
             .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setData(List<BannerInfo> data) {
        this.mUrlList = data;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

}

