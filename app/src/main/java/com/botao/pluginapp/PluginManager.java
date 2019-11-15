package com.botao.pluginapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.botao.startder.Constant;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Author: su2
 * Date: 2019/11/7 16:09
 * Describe: manager
 **/
public class PluginManager {

    private static final String TAG = PluginManager.class.getSimpleName();

    private static PluginManager sPluginManager;
    private DexClassLoader mDexClassLoader;
    private Resources mResources;
    private Context mContext;

    public static PluginManager getInstance(Context context) {
        if (sPluginManager == null) {
            synchronized (PluginManager.class) {
                if ((sPluginManager == null)) {
                    sPluginManager = new PluginManager(context);
                }
            }
        }
        return sPluginManager;
    }

    public PluginManager(Context context) {
        mContext = context;
    }

    /**
     * 加载插件
     */
    public void  loadPlugin(){
        try {
            File pluginPak = getPluginApkFilePath();
            if (pluginPak == null) return;

            // 加载插件class

            File cacheDir = mContext.getDir(Constant.APK_CACHE_DIR, Context.MODE_PRIVATE);// apk缓存目录

            mDexClassLoader = new DexClassLoader(pluginPak.getAbsolutePath(),cacheDir.getAbsolutePath(),null, mContext.getClassLoader());
            System.out.println( "loadPlugin: 插件apk class加载成功===" + mDexClassLoader.toString());


            // 加载插件资源
            AssetManager assetManager = AssetManager.class.newInstance();
            Method       addAssetPathMethod = assetManager.getClass().getMethod(Constant.ADD_ASSET_PATH, String.class);

            System.out.println( "loadPlugin: 插件apk的路径===" + pluginPak.getAbsolutePath() + "  插件apk的缓存路径===" + cacheDir.getAbsolutePath());

            addAssetPathMethod.invoke(assetManager, pluginPak.getAbsolutePath());

            mResources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(),mContext.getResources().getConfiguration());
            System.out.println( "loadPlugin: 插件apk resource加载成功===" + mResources);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "loadPlugin: 加载插件内容出错===" + e.getMessage());
        }
    }

    /**
     * 获取插件apk的 FilePath
     * @return
     */
    private File getPluginApkFilePath() {
        File pluginPak = new File(Environment.getExternalStorageDirectory() + File.separator + Constant.APK_NAME);
        if (!pluginPak.exists()) {
            System.out.println( "loadPlugin: 插件apk不存在!");
            return null;
        }
        return pluginPak;
    }

    public ClassLoader getClassLoader() {
        return mDexClassLoader;
    }

    public Resources getResources(){
        return mResources;
    }
}
