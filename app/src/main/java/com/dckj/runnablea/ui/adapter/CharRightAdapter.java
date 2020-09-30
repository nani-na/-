package com.dckj.runnablea.ui.adapter;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dckj.runnablea.R;
import com.dckj.runnablea.bean.CharBean;
import com.dckj.runnablea.ui.home.bean.CityBean;

import java.util.List;

public class CharRightAdapter extends BaseQuickAdapter<CharBean, BaseViewHolder> {

    public CharRightAdapter(@Nullable List<CharBean> data) {
        super(R.layout.item_char_right, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CharBean item) {
        helper.setText(R.id.tv_item_char, item.getCharname());
    }
}
