package com.dotawang.sophixdemo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created on 2018/9/7
 * Title:
 * Description:
 *阿里公有云的稳健接入集成
 * @author Android-汪洋
 *         update 2018/9/7
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(WinnerApplication.class)
    static class RealApplicationStub {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用，并去除原Applicaton中的MultiDex方法。
         MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAesKey(null)
                .setAppVersion(appVersion)
                //设置null默认取AndroidManifest中设置的值
                .setSecretMetaData(null, null, null)
                //调试状态下true，上线模式下false
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.i(TAG, "表明补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "用户可以监听进入后台事件, 然后应用自杀");
                        }else if (code == PatchStatus.CODE_REQ_CLEARPATCH){
                            //一键清除补丁状态  回退版本
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.i(TAG," 其它错误信息, 查看PatchStatus类说明");
                        }
                    }
                }).initialize();

    }

    //在WinnerApplicationn中调用了这里就不需要了
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限
//        SophixManager.getInstance().queryAndLoadNewPatch();//再原有的Application中加入获取补丁包的方法onCreate()
//
//    }
}
