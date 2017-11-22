package mvphelper.qiang.com.mvphelper.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import mvphelper.qiang.com.mvphelper.base.App;
import mvphelper.qiang.com.mvphelper.domin.UuidToFile;

import static android.util.Base64.NO_WRAP;
import static mvphelper.qiang.com.mvphelper.base.Constant.hpUuid;

/**
 * Created by sh on 2016/10/26 15:44.
 */

public class SystemUtil {

    public static int getAppVersion() {
        try {
            PackageInfo info = App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {

        boolean root = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        return root;
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(200);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        if (checkPermission(ctx, Manifest.permission.READ_PHONE_STATE)) {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
            if (tm != null) {
                return tm.getDeviceId();
            }
        }
        return null;
    }

    /**
     * 版本号
     */
    public static String showVersion(Context content) {
        // 在Activity中可以直接调用getPackageManager()，获取PackageManager实例。
        PackageManager packageManager = content.getPackageManager();
        // 在Activity中可以直接调用getPackageName()，获取安装包全名。
        String packageName = content.getPackageName();
        // flags提供了10种选项，及其组合，如果只是获取版本号，flags=0即可
        int flags = 0;
        PackageInfo packageInfo = null;
        try {
            // 通过packageInfo即可获取AndroidManifest.xml中的信息。
            packageInfo = packageManager.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo != null) {
            // 这里就拿到版本信息了。
            int versionCode = packageInfo.versionCode;
            return packageInfo.versionName;
        }
        return packageInfo.versionName;
    }


    public static int getFreeMemory() {
        //应用程序已获得内存中未使用内存
        int freeMemory = ((int) Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        return freeMemory;
    }

    public static int getTotalMemory() {
        //应用程序已获得内存中未使用内存
        int totalMemory = ((int) Runtime.getRuntime().totalMemory()) / 1024 / 1024;
        return totalMemory;
    }

    public static int getMaxMemory() {
        //应用程序已获得内存中未使用内存
        int maxMemory = ((int) Runtime.getRuntime().maxMemory()) / 1024 / 1024;
        return maxMemory;
    }



    public static int getUid() {
        try {
            PackageManager pm = App.getInstance().getPackageManager();
            //修改
            ApplicationInfo ai = pm.getApplicationInfo(App.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }



    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.get(key) + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 应用程序是否已安装
     *
     * @param context
     * @param packageName 应用程序的包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {

        if (packageName == null || "".equals(packageName))

            return false;

        try {
            context.getPackageManager().getApplicationInfo(packageName,

                    PackageManager.GET_UNINSTALLED_PACKAGES);

            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;

        }

    }



    public static String getVersionName(Context context) {
//获取包管理器
        PackageManager pm = context.getPackageManager();
//获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
//返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDevicedId(Context mContext) {
        if (TextUtils.isEmpty(hpUuid)) {
            String spUuid = SharedPrefUtil.getString("hpUuid", null);
            String sdUuid = getUuidFromSD();
            if (!TextUtils.isEmpty(spUuid)) {
                hpUuid = spUuid;
                if (!spUuid.equals(sdUuid)) {
                    saveToSD(spUuid);
                }
            } else {
                if (!TextUtils.isEmpty(sdUuid)) {
                    hpUuid = sdUuid;
                    SharedPrefUtil.putString("hpUuid", sdUuid);
                } else {
                    hpUuid = getUuidFromNew(mContext);
                    SharedPrefUtil.putString("hpUuid", hpUuid);
                    saveToSD(hpUuid);
                }
            }
        }
        return hpUuid;
    }

    private static void saveToSD(String spUuid) {
        try {
            File uidFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "hpuuid298347692.txt");
            if (!uidFile.exists()) {
                uidFile.getParentFile().mkdirs();
                uidFile.createNewFile();
            }
            FileWriter fw = new FileWriter(uidFile, false);
            String uuidString = new Gson().toJson(new UuidToFile(spUuid));
            fw.write(uuidString);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getUuidFromNew(Context mContext) {
        String deviceid;
        deviceid = getIMEI(mContext);
        if (TextUtils.isEmpty(deviceid)) {
            String uuid = (int) (Math.random() * 99999999) + "";
            deviceid = "33" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10 + uuid;
        }
        return deviceid;
    }


    private static String getUuidFromSD() {
        try {
            File uidFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "hpuuid298347692.txt");
            if (!uidFile.exists()) {
                uidFile.getParentFile().mkdirs();
                uidFile.createNewFile();
            }
            FileReader fileReader = new FileReader(uidFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer sb = new StringBuffer();
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = bufferedReader.readLine()) != null) {
                // 显示行号
                sb.append(tempString);
            }
            bufferedReader.close();
            String content = sb.toString();

            UuidToFile uuidToFile = new Gson().fromJson(content, UuidToFile.class);
            if (uuidToFile != null && !TextUtils.isEmpty(uuidToFile.getUuid())) {
                return uuidToFile.getUuid();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Gzip 压缩数据
     *
     * @param unGzipStr
     * @return
     */
    public static String compressForGzip(String unGzipStr) {
        if (TextUtils.isEmpty(unGzipStr)) {
            return "";
        }
        LogUtil.i("sdddddddddd", unGzipStr);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DeflaterOutputStream gzip = new DeflaterOutputStream(baos,
                    new Deflater(9, true));
            gzip.write(unGzipStr.getBytes());
            gzip.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();

            String base64Sting = Base64.encodeToString(encode, NO_WRAP);
            base64Sting = base64Sting.replace("+", "(");
            base64Sting = base64Sting.replace("/", ")");
            base64Sting = base64Sting.replace("=", "@");
            LogUtil.i("sdddddddddd", base64Sting);
//            SELFINFO_STATISTICS = base64Sting;
            return base64Sting;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
