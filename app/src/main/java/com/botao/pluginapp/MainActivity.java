package com.botao.pluginapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.botao.startder.Constant;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 1. 宿主APP一定要动态申请权限
         * 2. 插件APP内的Activity除入口Activity以外其他的可以不用注册, 也不需要注册权限或动态申请权限
         * 3. 插件内使用到context的地方一律使用用宿主的环境
         * 4. 重写startActivity方法时调用super的方法,不然会递归调用自己
         * 5. 不能在插件Activity的xml里使用onClick直接设置点击事件,需要用id的方式
         */
        /*
        // 获取已安装应用,过滤系统应用
        PackageManager    packageManager = getApplication().getPackageManager();
        List<PackageInfo> packageInfos   = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                System.out.println("系统应用" + appName + "  " +  packageInfo.packageName);
                continue;// 系统应用
            }
            System.out.println(appName + "  " +  packageInfo.packageName);
        }*/

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }else {
            System.out.println( "MainActivity: 已授权,开始预加载插件");
            loadPlugin();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0) {
            System.out.println( "MainActivity: 动态授权成功,开始预加载插件");
            loadPlugin();
        }
    }

    /**
     * 加载插件
     */
    public void loadPlugin() {
        PluginManager.getInstance(this).loadPlugin();
    }

    /**
     * 启动插件内页面
     * @param view v
     */
    public void startPluginActivity(View view) {
        // 为了不干扰宿主APP的扩展, 通过宿主代理的Activity去处理插件内所有相关的Activity事务

        File         pluginApk          = new File(Environment.getExternalStorageDirectory() + File.separator + Constant.APK_NAME);
        System.out.println( "MainActivity: pluginApkPath===" + pluginApk.getAbsolutePath());

        PackageInfo  packageArchiveInfo = getPackageManager().getPackageArchiveInfo(pluginApk.getAbsolutePath(), PackageManager.GET_ACTIVITIES);

        if (packageArchiveInfo == null) {
            System.out.println( "MainActivity: packageInfo 为null");
            return;
        }

        if (packageArchiveInfo.activities.length == 0) {
            System.out.println( "MainActivity: 插件APP内没有Activity");
            return;
        }

        ActivityInfo activityInfo         = packageArchiveInfo.activities[0];
        System.out.println( "MainActivity: activityInfo===" + activityInfo.toString());

        Intent intent = new Intent(this,ProxyActivity.class);
        intent.putExtra(Constant.CLASS_NAME, activityInfo.name);
        startActivity(intent);
    }
}
