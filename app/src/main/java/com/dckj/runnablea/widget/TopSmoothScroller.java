package com.dckj.runnablea.widget;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

public class TopSmoothScroller extends LinearSmoothScroller {
    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }
}
