package com.dotawang.sophixdemo;

import android.app.Application;

import com.taobao.sophix.SophixManager;

/**
 * Created on 2018/9/13
 * Title:
 * Description:
 *
 * @author Android-汪洋
 *         update 2018/9/13
 */
public class WinnerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //获取阿里热修复的补丁包  获取控制台状态
        SophixManager.getInstance().queryAndLoadNewPatch();

    }
}
