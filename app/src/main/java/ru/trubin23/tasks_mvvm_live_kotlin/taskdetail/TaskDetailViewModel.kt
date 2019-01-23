package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.annotation.StringRes
import ru.trubin23.tasks_mvvm_live_kotlin.R
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

    val editTaskCommand = SingleLiveEvent<Void>()
    val deleteTaskCommand = SingleLiveEvent<Void>()
    val snackbarMessage = SingleLiveEvent<Int>()

    var isDataLoading = false
        private set

    val isDataAvailable: Boolean
        get() = task.get() != null

    fun start(taskId: String?) {
        taskId ?: return

        isDataLoading = true

        tasksRepository.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                this@TaskDetailViewModel.task.set(task)
                title.set(task.title)
                description.set(task.description)
                completed.set(task.isCompleted)

                isDataLoading = false
            }

            override fun onDataNotAvailable() {
                task.set(null)

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

    fun onRefresh() {
        if (task.get() != null) {
            start(task.get()?.id)
        }
    }

    fun setCompleted(completed: Boolean) {
        if (isDataLoading) {
            return
        }

        val task = this.task.get()?.apply {
            isCompleted = completed
        }

        tasksRepository.completedTask(task?.id ?: return, completed)
        if (completed) {
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        snackbarMessage.value = message
    }
}