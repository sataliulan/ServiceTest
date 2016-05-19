package com.example.servicetest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context,Intent intent) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		Toast.makeText(context,"start service from receiver",Toast.LENGTH_LONG).show();
		Log.e("receiver","BroadCast received!!!!!!!!!!!!!!!!!!!!!!!!");
		synchronized (this) {
			Intent service = new Intent();
			intent.setClass(context, MyService.class);
			context.startService(intent);
		}
		Toast.makeText(context,"start service from receiver",Toast.LENGTH_SHORT).show();
	}

	
}
