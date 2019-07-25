package com.yhy.bannerview;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class OvalViewOutlineProvider extends ViewOutlineProvider {

    public OvalViewOutlineProvider() {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getOutline(final View view, final Outline outline) {
        Rect selfRect;
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        selfRect = getOvalRect(rect);
        outline.setOval(selfRect);
    }

    /**
     * 以矩形的中心点为圆心,较短的边为直径画圆
     * <p>
     *      * @param rect
     *      * @return
     *    
     */
    private Rect getOvalRect(Rect rect) {
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        int left, top, right, bottom;
        int dW = width / 2;
        int dH = height / 2;
        if (width > height) {
            left = dW - dH;
            top = 0;
            right = dW + dH;
            bottom = dH * 2;
        } else {
            left = dH - dW;
            top = 0;
            right = dH + dW;
            bottom = dW * 2;
        }
        return new Rect(left, top, right, bottom);
    }
}

