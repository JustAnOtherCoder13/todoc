package com.cleanup.todoc.utils;

import android.os.Bundle;

import androidx.test.runner.AndroidJUnitRunner;

public class MyAndroidJunitRunner extends AndroidJUnitRunner {
    @Override
    public void onCreate(Bundle arguments) {
        arguments.putString("notPackage", "net.bytebuddy");
        super.onCreate(arguments);
    }
}
