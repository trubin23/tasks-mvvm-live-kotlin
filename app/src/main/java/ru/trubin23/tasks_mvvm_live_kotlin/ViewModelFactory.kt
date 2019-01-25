package ru.trubin23.tasks_mvvm_live_kotlin

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.trubin23.tasks_mvvm_live_kotlin.addedittask.AddEditTaskViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository
import ru.trubin23.tasks_mvvm_live_kotlin.statistics.StatisticsViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.taskdetail.TaskDetailViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.tasks.TasksViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.util.Injection

class ViewModelFactory private constructor(
        private val application: Application,
        private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(TaskDetailViewModel::class.java) ->
                    TaskDetailViewModel(application, tasksRepository)
                isAssignableFrom(TasksViewModel::class.java) ->
                    TasksViewModel(application, tasksRepository)
                isAssignableFrom(AddEditTaskViewModel::class.java) ->
                    AddEditTaskViewModel(application, tasksRepository)
                isAssignableFrom(StatisticsViewModel::class.java) ->
                    StatisticsViewModel(application, tasksRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(application,
                        Injection.provideTasksRepository(application.applicationContext))
                        .also { INSTANCE = it }
            }
        }
    }
}