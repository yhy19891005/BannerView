package com.yhy.bannerview;


public class BannerInfo {

    private String url; //广告图片地址
    private String jump; //点击广告图片跳转链接
    private String title; //广告标题

    public BannerInfo(String url, String jump, String title) {
        this.url = url;
        this.jump = jump;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJump() {
        return jump;
    }

    public void setJump(String jump) {
        this.jump = jump;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
