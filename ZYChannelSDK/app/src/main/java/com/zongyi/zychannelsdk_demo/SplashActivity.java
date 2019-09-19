package com.zongyi.zychannelsdk_demo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zongyi.checkpermission.CheckPermissionActivity;
import com.zongyi.checkpermission.PermissionListener;

import java.util.List;

;

/**
 * 开屏
 *
 * @author YYY
 */
public class SplashActivity extends CheckPermissionActivity {

    public String[] permissions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在调用SDK之前，如果您的App的targetSDKVersion >= 23，那么一定要把"READ_PHONE_STATE"、"WRITE_EXTERNAL_STORAGE"、"ACCESS_FINE_LOCATION"这几个权限申请到
        permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE};

        requestRunTimePermission(permissions, new PermissionListener() {

            @Override
            public void onGranted(List<String> grantedPermission) {
                this.onGranted();
            }

            @Override
            public void onGranted() {
                requestSpreadAd();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toast.makeText(SplashActivity.this, deniedPermission.get(0) + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        });
    }

    private void requestSpreadAd() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
