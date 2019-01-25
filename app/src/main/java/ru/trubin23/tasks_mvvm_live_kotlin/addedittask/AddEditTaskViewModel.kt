package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

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

class AddEditTaskViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    private var completed = false

    val dataLoading = ObservableBoolean(false)

    private var taskId: String? = null
    private val isNewTask
        get() = taskId == null

    private var isDataLoaded = false

    val snackbarMessage = SingleLiveEvent<Int>()
    val taskUpdatedEvent = SingleLiveEvent<Void>()

    fun start(taskId: String?) {
        if (dataLoading.get()) {
            return
        }

        this.taskId = taskId

        if (isNewTask || isDataLoaded) {
            return
        }

        dataLoading.set(true)

        taskId ?: return
        tasksRepository.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                title.set(task.title)
                description.set(task.description)
                completed = task.isCompleted
                dataLoading.set(false)
                isDataLoaded = true
            }

            override fun onDataNotAvailable() {
                dataLoading.set(false)
            }
        })
    }

    fun saveTask() {
        val task = Task(title.get() ?: "", description.get() ?: "")
        if (task.isEmpty) {
            showSnackbarMessage(R.string.empty_task_message)
            return
        }
        if (isNewTask) {
            tasksRepository.saveTask(task)
        } else {
            val updateTask = Task(title.get() ?: "", description.get() ?: "",
                    taskId ?: "", completed)
            tasksRepository.saveTask(updateTask)
        }
        taskUpdatedEvent.call()
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        snackbarMessage.value = message
    }
}