package com.lichkin.framework.app.android.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import com.lichkin.android.main.R;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKDialog;
import com.lichkin.framework.app.android.utils.LKLog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基本Activity
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class BasePermissionActivity extends AppCompatActivity {

    /**
     * 获取需要的权限数组
     * @return 权限数组
     */
    protected abstract String[] getNeedPermissions();

    /** 权限列表 */
    private List<String> permissionNames = new ArrayList<>();

    @Override
    protected void onResume() {
        try {
            super.onResume();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermissions(getNeedPermissions());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions(String... permissions) {
        try {
            if (permissions == null) {
                return;
            }
            permissionNames = Arrays.asList(permissions);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
                List<String> needRequestPermissionList = findDeniedPermissions(permissions);
                if (null != needRequestPermissionList) {
                    int size = needRequestPermissionList.size();
                    if (size > 0) {
                        try {
                            String[] array = needRequestPermissionList.toArray(new String[size]);
                            Method method = getClass().getMethod("requestPermissions", String[].class, int.class);
                            method.invoke(this, array, 0);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private List<String> findDeniedPermissions(String[] permissions) {
        try {
            List<String> needRequestPermissionList = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
                for (String permission : permissions) {
                    if (checkMySelfPermission(permission) != PackageManager.PERMISSION_GRANTED || shouldShowMyRequestPermissionRationale(permission)) {
                        needRequestPermissionList.add(permission);
                    }
                }
            }
            return needRequestPermissionList;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private int checkMySelfPermission(String perm) {
        try {
            return (Integer) getClass().getMethod("checkSelfPermission", String.class).invoke(this, perm);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean shouldShowMyRequestPermissionRationale(String perm) {
        try {
            return (Boolean) getClass().getMethod("shouldShowRequestPermissionRationale", String.class).invoke(this, perm);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取权限名
            String permissionName = null;
            if (requestCode < permissionNames.size()) {
                permissionName = permissionNames.get(requestCode);
            }

            if (permissionName == null) {
                LKLog.e("onRequestPermissionsResult -> can't find permission by code " + requestCode + ", you must invoke dontNeedRequestPermission to judge permissions.");
                return;
            }

            LKLog.d("onRequestPermissionsResult -> permissionName=" + permissionName);

            if (checkGranted(grantResults)) {
                LKLog.d("onRequestPermissionsResult -> requestPermissionName=" + permissionName + " granted.");
                //授权通过
                onRequestPermissionResultGranted(permissionName);
            } else {
                LKLog.d("onRequestPermissionsResult -> requestPermissionName=" + permissionName + " not granted.");
                //授权未通过
                onRequestPermissionResultNotGranted(permissionName);
            }
        }
    }

    /**
     * 教研是否授权通过
     * @param grantResults 授权结果
     * @return 授权通过返回true，否则返回false。
     */
    private boolean checkGranted(int[] grantResults) {
        try {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 不需要申请权限
     * @param permissionName 权限名。Manifest.permission.XXX。
     * @return 不需要申请权限返回true，判断后直接实现具体内容即可；需要申请权限返回false，需要在回调方法中实现具体内容。
     */
    @SuppressLint("Range")
    protected boolean dontNeedRequestPermission(String permissionName) {
        //获取权限索引值
        int idx = -1;
        for (int i = 0; i < permissionNames.size(); i++) {
            if (permissionNames.get(i).equals(permissionName)) {
                idx = i;
                break;
            }
        }

        //没有该权限则增加该权限
        if (idx == -1) {
            permissionNames.add(permissionName);
            idx = permissionNames.size() - 1;
        }

        //校验权限
        boolean checked = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LKAndroidStatics.targetSdkVersion() >= Build.VERSION_CODES.M) {
                checked = this.checkSelfPermission(permissionName) == PackageManager.PERMISSION_GRANTED;
            } else {
                checked = PermissionChecker.checkSelfPermission(this, permissionName) == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        if (!checked) {
            LKLog.w("need requestPermission -> " + permissionName);
            //权限没有被授权，则申请该权限。
            ActivityCompat.requestPermissions(this, new String[]{permissionName}, idx);
            return false;
        }

        //不需要申请权限
        LKLog.i("don't need requestPermission -> " + permissionName);
        return true;
    }

    /**
     * 授权通过
     * @param permissionName 权限名
     */
    protected void onRequestPermissionResultGranted(String permissionName) {
        LKLog.i("onRequestPermissionResultGranted -> " + permissionName);
    }

    /**
     * 授权不通过
     * @param permissionName 权限名
     */
    protected void onRequestPermissionResultNotGranted(String permissionName) {
        LKLog.w("onRequestPermissionResultNotGranted -> " + permissionName);
        new LKDialog(this, true, LKAndroidUtils.getString(R.string.Tip), LKAndroidUtils.getString(R.string.required_permissions))
                .setCancelable(false)
                .setNegativeButton(new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        finish();
                    }
                })
                .setPositiveButton(LKAndroidUtils.getString(R.string.Setting), new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .show();
    }

}
