package com.dckj.runnablea.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dckj.runnablea.R;
import com.dckj.runnablea.dialog.CityPopupWindow;
import com.dckj.runnablea.net.RetrofitUtil;
import com.dckj.runnablea.ui.activity.CitySelActivity;
import com.dckj.runnablea.ui.home.bean.CityBean;
import com.dckj.runnablea.util.Utils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<CityBean> beans;
    private TextView btn_sel;
    private TextView btn_city_sel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btn_sel = root.findViewById(R.id.btn_sel);
        btn_city_sel = root.findViewById(R.id.btn_city_sel);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        beans = LitePal.findAll(CityBean.class);
        if (beans != null && beans.size() > 0){
            Logger.d(beans.toString());
        } else {
            getCity();
        }
        btn_sel.setOnClickListener(view -> {
            CityPopupWindow popupWindow = new CityPopupWindow(getActivity());
            popupWindow.setmListener((cityBean, cityBean2, cityBean3) -> {
                btn_sel.setText(cityBean.getClass_name()+cityBean2.getClass_name()+cityBean3.getClass_name()+"");
                popupWindow.dismiss();
            });
            popupWindow.setOnDismissListener(() -> {
                if (getActivity()!= null){
                    Utils.setBackgroundAlpha(getActivity(), 1f);
                }
            });
//            popupWindow.showAsDropDown(btn_sel);
            popupWindow.showAtLocation(btn_sel,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            if (getActivity() != null) {
                Utils.setBackgroundAlpha(getActivity(), 0.5f);
            }
        });
        btn_city_sel.setOnClickListener(view -> {
            //城市选择
            startActivityForResult(new Intent(getActivity(), CitySelActivity.class), 1);
        });
    }

    private void getCity(){
        RetrofitUtil.getInstance().getDataService().get_city()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            JSONObject jsonObject = new JSONObject(body.string());
                            if (jsonObject.optInt("code") == 200){
                                JSONObject data = jsonObject.optJSONObject("data");
                                Iterator<String> keys = data.keys();
                                String key = "";
                                while (keys.hasNext()){
                                    key = keys.next();
                                    CityBean cityBean = new Gson().fromJson(data.get(key).toString(), CityBean.class);
                                    String citycase = Utils.getTerm(cityBean.getClass_name());
                                    cityBean.setInitial(citycase.split(",")[0]);
                                    cityBean.setLowercase(citycase.split(",")[1]);
                                    cityBean.setUppercase(citycase.split(",")[2]);
                                    Log.d("______",cityBean.toString());
                                    cityBean.save();
//                                    beans.add(cityBean);
                                }
                                init();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                btn_city_sel.setText(data.getStringExtra("cityname") +",id=" + data.getStringExtra("cityid"));
            }
        }
    }
}