package ru.trubin23.tasks_mvvm_live_kotlin.util

import android.content.Context
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.cache.TasksCacheRepository
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.local.TasksDatabase
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.local.TasksLocalRepository
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.remote.TasksRemoteRepository

object Injection {
    fun provideTasksRepository(context: Context): TasksRepository {
        val database = TasksDatabase.getInstance(context)

        val appExecutors = AppExecutors()

        return TasksRepository.getInstance(
                TasksRemoteRepository.getInstance(appExecutors),
                TasksLocalRepository.getInstance(appExecutors, database.tasksDao()),
                TasksCacheRepository.getInstance())
    }
}