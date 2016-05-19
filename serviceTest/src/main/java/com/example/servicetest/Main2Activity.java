package com.example.servicetest;

import android.app.Activity;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PullToRefreshListView listView=new PullToRefreshListView(this);
    }
}
