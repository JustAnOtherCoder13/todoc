package com.cleanup.todoc.utils;

import java.util.concurrent.Executor;

public class CurrentThreadExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}
