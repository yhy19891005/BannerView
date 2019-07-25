package com.yhy.bannerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private BannerView       mBannerView;
    private List<BannerInfo> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {

        mBannerView = findViewById(R.id.banner_view);

    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new BannerInfo("http://m.tfb8.com/img/ad//unoinQR.png","http://www.baidu.com","百度百度百度百度"));
        mList.add(new BannerInfo("http://m.tfb8.com/img/ad//cloudpay.png","http://www.hao123.com","好123好123好123好123"));
        mList.add(new BannerInfo("http://m.tfb8.com/img/ad//unoinQR.png","http://www.baidu.com","百度百度百度百度"));
        mList.add(new BannerInfo("http://m.tfb8.com/img/ad//cloudpay.png","http://www.hao123.com","好123好123好123好123"));

        mBannerView.bindData(mList);
    }
}
