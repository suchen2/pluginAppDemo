package com.botao.pluginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.botao.startder.Constant;
import com.botao.startder.IActivity;

import java.lang.reflect.Constructor;

import javax.security.auth.login.LoginException;

public class ProxyActivity extends Activity {

    private Class<?> mPluginActivityClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_proxy);

        String className = getIntent().getStringExtra(Constant.CLASS_NAME);
        if(className == null){
            System.out.println( "ProxyActivity: 接收来自MainActivity的className为null");
            return;
        }
        try {
            mPluginActivityClass = getClassLoader().loadClass(className);

            // 实例化插件apk里的Activity
            Constructor<?> constructor = mPluginActivityClass.getConstructor(new Class[]{});
            Object         instance           = constructor.newInstance(new Object[] {});
            IActivity iActivity = (IActivity) instance;

            // 注入
            iActivity.insertAppContext(this);

            // 执行插件apk里的onCreate
            iActivity.onCreate(null);
            System.out.println( "ProxyActivity: 插件apk内onCreate已执行");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "ProxyActivity: 插件apk注入内容出错===" + e.getMessage());
        }
    }

    @Override
    public void startActivity(Intent intent) {
        // 重写方法,拦截intent

        // 启动自身Activity,再次走onCreate方法循环(模拟任务栈)
        Intent newIntent = new Intent(this, ProxyActivity.class);
        newIntent.putExtra(Constant.CLASS_NAME, intent.getStringExtra(Constant.CLASS_NAME));// 携带参数为插件APP传递过来要跳转的Activity.class

        super.startActivity(newIntent);// 一定要调用super的启动方法,不然就是递归调用本方法
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getClassLoader();
    }
}
