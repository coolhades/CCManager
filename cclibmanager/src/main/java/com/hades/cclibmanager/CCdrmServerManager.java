package com.hades.cclibmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bokecc.sdk.mobile.drm.DRMServer;
import com.bokecc.sdk.mobile.util.DWSdkStorage;
import com.bokecc.sdk.mobile.util.DWStorageUtil;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hades on 2016/11/18.
 * CC视频初始化设置类
 */
public class CCdrmServerManager {


    private DRMServer drmServer;

    private int drmServerPort;



    // CC视频帐号信息 账户信息  加密账号
    public  String CC_Account_id ;
    public  String CC_Account_Key ;


    // CC视频帐号信息 账户信息 非加密账号
    public  String CC_Account_NO_id ;
    public  String CC_Account_NO_Key;


    public String getCC_Account_id() {
        return CC_Account_id;
    }

    public void setCC_Account_id(String CC_Account_id, String CC_Account_Key) {
        this.CC_Account_id = CC_Account_id;
        this.CC_Account_Key = CC_Account_Key;
    }

    public String getCC_Account_Key() {
        return CC_Account_Key;
    }

    public String getCC_Account_NO_id() {
        return CC_Account_NO_id;
    }

    public void setCC_Account_NO_id(String CC_Account_NO_id, String CC_Account_NO_Key) {
        this.CC_Account_NO_id = CC_Account_NO_id;
        this.CC_Account_NO_Key = CC_Account_NO_Key;
    }

    public String getCC_Account_NO_Key() {
        return CC_Account_NO_Key;
    }


    public int getDrmServerPort() {
        return drmServerPort;
    }

    public void setDrmServerPort(int drmServerPort) {
        this.drmServerPort = drmServerPort;
    }

    public DRMServer getDRMServer() {
        return drmServer;
    }

    public static CCdrmServerManager getInstance() {
        return Instance.instance;
    }


    private CCdrmServerManager() {

    }

    public void stopDrmServer() {
        if (drmServer != null) {
            Log.d("TAG-drmServer", drmServer.toString());
            drmServer.stop();
        }
    }

    public void initDrmServer() {
        //cc加密
        // 启动DRMServer
        if (drmServer == null) {
            drmServer = new DRMServer();
        }

        try {
            drmServer.start();
            setDrmServerPort(drmServer.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("TAG-drmServer", drmServer.toString());

    }

    /**
    * 创建时间 2017/4/5
    * auther Hades
    * 描述 MainActivity 中设置
    **/
    private void initDWStorage(final Context applicationContext) {
        DWSdkStorage myDWSdkStorage = new DWSdkStorage() {
            private SharedPreferences sp = applicationContext.getSharedPreferences("mystorage", MODE_PRIVATE);

            @Override
            public void put(String key, String value) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(key, value);
                editor.commit();
            }

            @Override
            public String get(String key) {
                return sp.getString(key, "");
            }
        };

        DWStorageUtil.setDWSdkStorage(myDWSdkStorage);
    }

    private static class Instance {
        private static CCdrmServerManager instance = new CCdrmServerManager();
    }


}
