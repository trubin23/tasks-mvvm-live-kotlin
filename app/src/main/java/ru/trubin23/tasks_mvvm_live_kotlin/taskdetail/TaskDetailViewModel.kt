package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import ru.trubin23.tasks_mvvm_live_kotlin.SingleLiveEvent
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksDataSource
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class TaskDetailViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    private val task = ObservableField<Task>()

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val completed = ObservableBoolean()

    private val editTaskCommand = SingleLiveEvent<Void>()
    private val deleteTaskCommand = SingleLiveEvent<Void>()
    private val snackbarText = SingleLiveEvent<Int>()

    var isDataLoading = false
        private set

    val isDataAvailable: Boolean
        get() = task.get() != null

    fun start(taskId: String?) {
        taskId ?: return

        isDataLoading = true
        tasksRepository.getTask(taskId, object : TasksDataSource.GetTaskCallback{
            override fun onTaskLoaded(task: Task) {
                //TODO: implement
                isDataLoading = false
            }

            override fun onDataNotAvailable() {
                //TODO: implement
                isDataLoading = false
            }
        })
    }

    fun deleteTask() {
        task.get()?.let {
            tasksRepository.deleteTask(it.id)
            deleteTaskCommand.call()
        }
    }

    fun editTask() {
        editTaskCommand.call()
    }


}