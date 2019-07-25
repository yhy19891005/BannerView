package com.yhy.bannerview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final int AUTO_PLAY = 100;
    private static final int TO_END = 200;
    private Context mContext;
    private ViewPager mBannerViewPager;
    private LinearLayout mBannerDotLayout;
    private TextView mTvTitle;
    private int mDotMargin = 10;
    private int mDotPadding = 2;
    private int lastIndex;
    private ImageView[] dots;
    private boolean mIsAutoPlay = true;
    private Timer mTimer;
    private int mPosition;
    private List<BannerInfo> mData;
    private long time = 2000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*实现viewpager自动播放效果*/
            switch (msg.what){
                case AUTO_PLAY:
                    mBannerViewPager.setCurrentItem(mPosition + 1);
                    break;
                case TO_END:
                    mBannerViewPager.setCurrentItem(0);
                    break;
            }
        }
    };

    public BannerView(@NonNull Context context) {
        this(context, null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.banner_layout, this, true);
        mContext = context;
        initView();
    }

    private void initView() {
        mBannerViewPager = findViewById(R.id.banner_viewPager);
        mBannerDotLayout = findViewById(R.id.banner_dot_layout);
        mTvTitle = findViewById(R.id.tv_title);
    }

    public void bindData(List<BannerInfo> list) {
        mData = list;
        if(list != null) mTvTitle.setText(list.get(0).getTitle());
        setDots(list);
        IndexViewPagerAdapter adapter = new IndexViewPagerAdapter(mContext, list);
        mBannerViewPager.setAdapter(adapter);
        mBannerViewPager.addOnPageChangeListener(this);
        startAutoPlay(list,mData.size() * time);
        setCorner(30);
    }

    private void setDots(List<BannerInfo> list) {
        if (list != null && list.size() > 1) {
            dots = new ImageView[list.size()];
            int dotMargin = DensityUtil.dp2px(mContext, mDotMargin);
            int dotPadding = DensityUtil.dp2px(mContext, mDotPadding);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dotMargin, dotPadding);
            lp.setMargins((int)(dotMargin * 0.5), 0, 0, 0);
            mBannerDotLayout.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                ImageView iv = new ImageView(mContext);
                iv.setBackgroundResource(R.drawable.banner_dot);
                iv.setPadding(dotPadding, dotPadding, dotPadding, dotPadding);
                iv.setLayoutParams(lp);
                dots[i] = iv;
                mBannerDotLayout.addView(iv);
            }
            // 进入第一页，第一个小圆点设置不可点击
            mBannerViewPager.setCurrentItem(0);
            dots[lastIndex].setEnabled(false);
        } else {
            mBannerDotLayout.removeAllViews();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:// 正在滑动
                mIsAutoPlay = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:// 界面切换中
                mIsAutoPlay = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:// 滑动结束
                if (mBannerViewPager.getCurrentItem() == mBannerViewPager.getAdapter().getCount() - 1 && !mIsAutoPlay) {
                    mBannerViewPager.setCurrentItem(0);
                } else if (mBannerViewPager.getCurrentItem() == 0 && !mIsAutoPlay) {
                    mBannerViewPager.setCurrentItem(mBannerViewPager.getAdapter().getCount() - 1);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        mTvTitle.setText(mData.get(position).getTitle());
        setCurrentDot(position);
        if(position == mData.size() -1){
            mHandler.sendEmptyMessageDelayed(TO_END,time);
        }
    }

    private void setCurrentDot(int position) {
        if (dots != null) {
            dots[position].setEnabled(false);
            dots[lastIndex].setEnabled(true);
            lastIndex = position;
        }
    }

    /*
     * 开启自动轮播
     *@param period banner的轮播周期
     * */
    public void startAutoPlay(List<BannerInfo> list, long period) {
        if (list != null && list.size() > 1) {
            mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(AUTO_PLAY);
                }
            };
            mTimer.schedule(timerTask, time, time);
        }
    }

    /*
     * 关闭自动轮播
     * */
    public void stopAutoPlay() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mHandler.removeMessages(AUTO_PLAY);
        mHandler.removeMessages(TO_END);
    }

    //画圆角
    public void setCorner(float angle) {
        ViewStyleSetter viewStyleSetter = new ViewStyleSetter(this);
        viewStyleSetter.setRound(angle);
    }

}
