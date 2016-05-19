package com.example.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by thunder on 2016/4/21.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // PackageManager pm=getPackageManager();
        /*
        specify the activity which is first open in manifest file
        which is declare as<category andradoid:name="android.intent.category.LAUNCHER" />

         */
        //ComponentName componentName=new ComponentName(this,com.example.servicetest.MainActivity.class);
       // pm.setApplicationEnabledSetting();
       /* pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/
        Toast.makeText(this,"Creat Activity",Toast.LENGTH_SHORT).show();
    }
}
