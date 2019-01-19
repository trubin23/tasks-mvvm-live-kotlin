package ru.trubin23.tasks_mvvm_live_kotlin.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors constructor(
        val diskIO: Executor = DiskIOThreadExecutor(),
        val networkIO: Executor = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT),
        val mainThread: Executor = MainThreadExecutor()
) {

    private class MainThreadExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        const val NETWORK_THREAD_COUNT = 3
    }
}