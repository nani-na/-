package com.dckj.runnablea.base;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.litepal.LitePal;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("MyTAG")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
        LitePal.initialize(this);

    }
}
