package com.dckj.runnablea.dialog;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dckj.runnablea.R;
import com.dckj.runnablea.ui.home.bean.CityBean;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {

    private CityBean cityBean;
    private CityListener mListener;

    public void setmListener(CityListener mListener) {
        this.mListener = mListener;
    }

    public CityBean getCityBean() {
        return cityBean;
    }

    public void setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
    }

    public CityAdapter(@Nullable List<CityBean> data) {
        super(R.layout.item_city, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CityBean item) {
        helper.setText(R.id.tv_pname, item.getClass_name());
        if (item.equals(cityBean)){
            helper.setVisible(R.id.iv_duihao, true);
            helper.setTextColor(R.id.tv_pname,mContext.getResources().getColor(R.color.color_d81e06));
        } else {
            helper.setVisible(R.id.iv_duihao, false);
            helper.setTextColor(R.id.tv_pname,mContext.getResources().getColor(R.color.color_333333));
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityBean = item;
                notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.itemclick(item);
                    }
                },200);
            }
        });
    }

    public interface CityListener{
        void itemclick(CityBean item);
    }
}
