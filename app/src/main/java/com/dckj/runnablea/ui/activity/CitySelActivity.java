package com.dckj.runnablea.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dckj.runnablea.R;
import com.dckj.runnablea.bean.CharBean;
import com.dckj.runnablea.ui.adapter.CharRightAdapter;
import com.dckj.runnablea.ui.adapter.CityCharAdapter;
import com.dckj.runnablea.ui.home.bean.CityBean;
import com.dckj.runnablea.widget.TopSmoothScroller;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;

import org.litepal.LitePal;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CitySelActivity extends AppCompatActivity {

    private List<CityBean> data;
    private List<CharBean> chars;
    private RecyclerView recycler;
    private RecyclerView recycler_right;
    private CityCharAdapter cityCharAdapter;
    private CharRightAdapter charRightAdapter;
    private LinearLayoutManager manager;
    private EditText et_search;
    private TextView btn_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_sel);
        recycler = findViewById(R.id.recycler);
        recycler_right = findViewById(R.id.recycler_right);
        et_search = findViewById(R.id.et_search);
        btn_cancle = findViewById(R.id.btn_cancle);

        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        GroupListener groupListener = position -> {
            if (data.size()>0) {
                return data.get(position).getInitial();
            } else {
                return "";
            }
        };
        StickyDecoration decoration1 = StickyDecoration.Builder
                .init(groupListener)
                .setGroupBackground(Color.parseColor("#F6F8F5"))
                .setGroupHeight(80)
                .setGroupTextColor(Color.parseColor("#03DAC5"))
                .setGroupTextSize(40)
                .setDivideColor(Color.parseColor("#F6F8F5"))
                .setDivideHeight(4)
                .setTextSideMargin(20)
                .build();
        recycler.addItemDecoration(decoration1);
        recycler_right.setLayoutManager(new LinearLayoutManager(this));

        init("");
        setListener();
    }

    private void init(String content){
        data = new ArrayList<>();
        chars = new ArrayList<>();
        if (TextUtils.isEmpty(content)) {
            data = LitePal.where("class_type = 2").find(CityBean.class);
        } else {
            data = LitePal.where("class_type = 2 and class_name like ?", "%" + content + "%").find(CityBean.class);
            if (data.size() == 0){
                data = LitePal.where("class_type = 2 and lowercase like ?", "%" + content + "%").find(CityBean.class);
            }
            if (data.size() == 0){
                data = LitePal.where("class_type = 2 and uppercase like ?", "%" + content + "%").find(CityBean.class);
            }
            if (data.size() == 0){
                Toast.makeText(this, "查询结果不存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Comparator comparator = Collator.getInstance(Locale.CHINA);
        Collections.sort(data, new Comparator<CityBean>() {
            @Override
            public int compare(CityBean cityBean, CityBean t1) {
                return comparator.compare(cityBean.getClass_name(), t1.getClass_name());
            }
        });
        List<String> cnames = new ArrayList<>();
        for (int i = 0;i < data.size(); i++){
            if (!cnames.contains(data.get(i).getInitial())) {
                cnames.add(data.get(i).getInitial());
                chars.add(new CharBean(data.get(i).getInitial(), i));
            }
        }
        Log.d("______",chars.toString());
        cityCharAdapter = new CityCharAdapter(data);
//        DividerItemDecoration decoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        decoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.linear_layout_item));  //把样式放进去
//        recycler.addItemDecoration(new StickyDecoration(new StickyDecoration.OnTagListener() {
//            @Override
//            public String getTag(int position) {
//                return data.get(position).getInitial();
//            }
//        }));
        recycler.setAdapter(cityCharAdapter);
        charRightAdapter = new CharRightAdapter(chars);
        recycler_right.setAdapter(charRightAdapter);

        charRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            //滑动到指定位置并置顶
//            LinearSmoothScroller smoothScroller = new TopSmoothScroller(CitySelActivity.this);
//            smoothScroller.setTargetPosition(chars.get(position).getPo());
            Log.d("_____","position:" + position);
            //闪到指定位置
            manager.scrollToPositionWithOffset(chars.get(position).getPo(),0);
        });
        cityCharAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("cityname",data.get(position).getClass_name());
                intent.putExtra("cityid",data.get(position).getClass_id()+"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void setListener(){
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("_____",charSequence.toString());
//                if (!TextUtils.isEmpty(charSequence.toString())){
                    init(charSequence.toString());
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_cancle.setOnClickListener(view -> finish());
    }
}