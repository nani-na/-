package com.dckj.runnablea.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dckj.runnablea.R;
import com.dckj.runnablea.ui.home.bean.CityBean;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CityPopupWindow extends PopupWindow {
    private Context mContext;
    private TabLayout tablayout;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private CityAdapter cityAdapter;
    private CityAdapter cityAdapter2;
    private CityAdapter cityAdapter3;
    private List<CityBean> data;
    private List<CityBean> data2;
    private List<CityBean> data3;
    private CityBean cityBean;
    private CityBean cityBean2;
    private CityBean cityBean3;

    private CityPopListener mListener;

    public CityPopListener getmListener() {
        return mListener;
    }

    public void setmListener(CityPopListener mListener) {
        this.mListener = mListener;
    }

    public interface CityPopListener{
        void sel(CityBean cityBean, CityBean cityBean2, CityBean cityBean3);
    }


    public CityPopupWindow(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_window, null, false);
        setContentView(view);
        tablayout = view.findViewById(R.id.tablayout);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView2 = view.findViewById(R.id.recycler2);
        recyclerView3 = view.findViewById(R.id.recycler3);
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> dismiss());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(30000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.my_dialog);
        init();
    }
    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView3.setLayoutManager(new LinearLayoutManager(mContext));
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        cityAdapter = new CityAdapter(data);
        cityAdapter2 = new CityAdapter(data2);
        cityAdapter3 = new CityAdapter(data3);
        recyclerView.setAdapter(cityAdapter);
        recyclerView2.setAdapter(cityAdapter2);
        recyclerView3.setAdapter(cityAdapter3);

        tablayout.addTab(tablayout.newTab().setText("请选择"));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.GONE);
                        recyclerView3.setVisibility(View.GONE);
                        break;
                    case 1:
                        recyclerView.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.VISIBLE);
                        recyclerView3.setVisibility(View.GONE);
                        break;
                    case 2:
                        recyclerView.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                        recyclerView3.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        data = LitePal.where("class_type = 1").find(CityBean.class);
        cityAdapter.setNewData(data);
        setListener();
    }
    private void setListener(){
        cityAdapter.setmListener(new CityAdapter.CityListener() {
            @Override
            public void itemclick(CityBean item) {
                cityBean = item;
//                cityAdapter.setCityBean(cityBean);
//                cityAdapter.notifyDataSetChanged();
                tablayout.getTabAt(0).setText(cityBean.getClass_name());
                if (tablayout.getTabCount() == 1){
                    tablayout.addTab(tablayout.newTab().setText("请选择"));
                    tablayout.getTabAt(1).select();
                    data2 = LitePal.where("class_parent_id = " + cityBean.getClass_id()).find(CityBean.class);
                    cityAdapter2.setNewData(data2);
                } else if (tablayout.getTabCount() >= 2) {
                    cityBean2 = null;
                    tablayout.getTabAt(1).setText("请选择").select();
                    data2 = LitePal.where("class_parent_id = " + cityBean.getClass_id()).find(CityBean.class);
                    cityAdapter2.setCityBean(cityBean2);
                    cityAdapter2.setNewData(data2);
                    if (tablayout.getTabCount() == 3){
                        cityBean3 = null;
                        tablayout.getTabAt(2).setText("请选择");
                        data3.clear();
                        cityAdapter3.setCityBean(cityBean3);
                        cityAdapter3.notifyDataSetChanged();
                    }
                }
            }
        });
        cityAdapter2.setmListener(new CityAdapter.CityListener() {
            @Override
            public void itemclick(CityBean item) {
                cityBean2 = item;
//                cityAdapter2.setCityBean(cityBean2);
//                cityAdapter2.notifyItemChanged(position);
                tablayout.getTabAt(1).setText(cityBean2.getClass_name());
                if (tablayout.getTabCount() == 2){
                    tablayout.addTab(tablayout.newTab().setText("请选择"));
                    tablayout.getTabAt(2).select();
                    data3 = LitePal.where("class_parent_id = "+ cityBean2.getClass_id()).find(CityBean.class);
                    cityAdapter3.setNewData(data3);
                } else {
                    cityBean3 = null;
                    tablayout.getTabAt(2).setText("请选择").select();
                    data3 = LitePal.where("class_parent_id = "+ cityBean2.getClass_id()).find(CityBean.class);
                    cityAdapter3.setNewData(data3);
                }
            }
        });
        cityAdapter3.setmListener(new CityAdapter.CityListener() {
            @Override
            public void itemclick(CityBean item) {
                cityBean3 = item;
//                cityAdapter3.setCityBean(cityBean3);
//                cityAdapter3.notifyItemChanged(position);
                tablayout.getTabAt(2).setText(cityBean3.getClass_name());
                mListener.sel(cityBean,cityBean2,cityBean3);
            }
        });
    }
}
