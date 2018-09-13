package com.dotawang.sophixdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author DotaWang
 */
public class MainActivity extends Activity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
        initData();

    }

    private void initData() {
        textView.setText("这是修复前的效果！！！");
//        textView.setText("这是修复后的效果！！！");
//        textView.setText("这是第二次修复后的效果！！！");
    }
}
