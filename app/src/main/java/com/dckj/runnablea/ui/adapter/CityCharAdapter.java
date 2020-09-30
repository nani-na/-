package com.dckj.runnablea.ui.adapter;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dckj.runnablea.R;
import com.dckj.runnablea.ui.home.bean.CityBean;

import java.util.List;

public class CityCharAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {

    private List<String> chars;
    private int charitem = 0;  //当前首字母下标
    private boolean isSet = false;  //是否已设置

    public CityCharAdapter(@Nullable List<CityBean> data) {
        super(R.layout.item_city_char, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CityBean item) {
        helper.setText(R.id.tv_cname, item.getClass_name());
//        if (charitem < chars.size()) {
//            if (chars.get(charitem).equals(item.getInitial()) && isSet == false) {
//                helper.setGone(R.id.tv_char, true);
//                helper.setText(R.id.tv_char, item.getInitial());
//                isSet = true;
//            } else if (!chars.get(charitem).equals(item.getInitial())) {
//                if (charitem < chars.size()) {
//                    charitem++;
//                }
//                isSet = true;
//                helper.setGone(R.id.tv_char, true);
//                helper.setText(R.id.tv_char, item.getInitial());
//            } else {
//                helper.setGone(R.id.tv_char, false);
//            }
//        }
    }
}
