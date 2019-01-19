package ru.trubin23.tasks_mvvm_live_kotlin.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DiskIOThreadExecutor: Executor {

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }
}