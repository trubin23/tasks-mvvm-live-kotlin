package ru.trubin23.tasks_mvvm_live_kotlin.statistics

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksDataSource
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class StatisticsViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    val dataLoading = ObservableBoolean(false)

    val empty = ObservableBoolean(false)

    val numberOfActiveTasksString = ObservableField<String>()
    val numberOfCompletedTasksString = ObservableField<String>()

    private var numberOfActiveTasks = 0
    private var numberOfCompletedTasks = 0

    fun start(){
        dataLoading.set(true)
        tasksRepository.getTasks(object : TasksDataSource.LoadTasksCallback{
            override fun onTasksLoaded(tasks: List<Task>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}