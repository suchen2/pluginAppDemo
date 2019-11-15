package com.botao.plugin_package.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.botao.startder.Constant;
import com.botao.startder.IActivity;

/**
 * Author: su2
 * Date: 2019/11/7 16:01
 * Describe: baseActivity
 **/
public class BaseActivity extends Activity implements IActivity {

    public Activity appActivity;

    @SuppressLint("MissingSuperCall")
    @Override
    public void insertAppContext(Activity appActivity) {
        this.appActivity = appActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    public void setContentView(int resId) {
        appActivity.setContentView(resId);
    }

    public View findViewById(int viewId){
        return appActivity.findViewById(viewId);
    }

    public void startActivity(Intent intent) {
        ComponentName component = getComponentName(intent);
        if (component == null) return;

        System.out.println("BaseActivity: 要跳转的Activity===" + component.getClassName());

        Intent newIntent = new Intent();// class 是带包名的全类名
        newIntent.putExtra(Constant.CLASS_NAME, component.getClassName());
        appActivity.startActivity(newIntent);// 通过宿主的Activity环境启动,方法调用到宿主的startActivity方法里(到宿主Activity重写此方法)
    }



    @Override
    public ComponentName startService(Intent service) {
        ComponentName component = getComponentName(service);
        if (component == null) return null;

        System.out.println("BaseActivity: 要启动的service===" + component.getClassName());

        Intent newIntent = new Intent();
        newIntent.putExtra(Constant.CLASS_NAME, component.getClassName());
        return appActivity.startService(newIntent);
    }

    private ComponentName getComponentName(Intent intent) {
        ComponentName component = intent.getComponent();
        if(component == null){
            System.out.println("BaseActivity: component为null");
            return null;
        }
        return component;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }
}
