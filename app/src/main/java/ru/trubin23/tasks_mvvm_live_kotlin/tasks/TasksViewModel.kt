package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.SingleLiveEvent
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksDataSource
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class TasksViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    private val context: Context = context.applicationContext

    val openTaskEvent = SingleLiveEvent<String>()
    val newTaskEvent = SingleLiveEvent<Void>()
    val snackbarMessage = SingleLiveEvent<Int>()

    val items: ObservableList<Task> = ObservableArrayList()
    val empty = ObservableBoolean(false)

    val currentFilteringLabel = ObservableField<String>()
    val noTasksLabel = ObservableField<String>()
    val noTaskIconRes = ObservableField<Drawable>()
    val tasksAddViewVisible = ObservableBoolean()

    val dataLoading = ObservableBoolean(false)
    val isDataLoadingError = ObservableBoolean(false)

    var currentFiltering = TasksFilterType.ALL_TASKS
        set(value) {
            field = value
            updateFiltering()
        }

    init {
        updateFiltering()
    }

    fun start() {
        loadTasks(true)
    }

    fun loadTasks(forceUpdate: Boolean) {
        loadTasks(forceUpdate, true)
    }

    private fun loadTasks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            dataLoading.set(true)
        }
        if (forceUpdate) {
            tasksRepository.refreshTasks()
        }

        tasksRepository.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                val tasksToShow: List<Task> = when (currentFiltering) {
                    TasksFilterType.ALL_TASKS ->
                        tasks
                    TasksFilterType.ACTIVE_TASKS ->
                        tasks.filter { it.isActive }
                    TasksFilterType.COMPLETED_TASKS ->
                        tasks.filter { it.isCompleted }
                }

                with(items) {
                    clear()
                    addAll(tasksToShow)
                    empty.set(isEmpty())
                }

                if (showLoadingUI) {
                    dataLoading.set(false)
                }
                isDataLoadingError.set(false)
            }

            override fun onDataNotAvailable() {
                if (showLoadingUI) {
                    dataLoading.set(false)
                }
                isDataLoadingError.set(true)
            }
        })
    }

    private fun updateFiltering() {
        when (currentFiltering) {
            TasksFilterType.ALL_TASKS -> {
                setFilter(R.string.label_all, R.string.no_tasks_all,
                        R.drawable.ic_verified, true)
            }
            TasksFilterType.ACTIVE_TASKS -> {
                setFilter(R.string.label_active, R.string.no_tasks_active,
                        R.drawable.ic_check_circle, false)
            }
            TasksFilterType.COMPLETED_TASKS -> {
                setFilter(R.string.label_all, R.string.no_tasks_all,
                        R.drawable.ic_check_box, false)
            }
        }
    }

    private fun setFilter(@StringRes filteringLabelStringId: Int,
                          @StringRes noTasksLabelStringId: Int,
                          @DrawableRes noTaskIconDrawable: Int, tasksAddVisible: Boolean) {
        with(context.resources) {
            currentFilteringLabel.set(getString(filteringLabelStringId))
            noTasksLabel.set(getString(noTasksLabelStringId))
            noTaskIconRes.set(getDrawable(noTaskIconDrawable))
            tasksAddViewVisible.set(tasksAddVisible)
        }
    }

    fun clearCompletedTasks() {
        tasksRepository.clearCompletedTasks()
        snackbarMessage.value = R.string.completed_tasks_cleared
        loadTasks(false, false)
    }

    fun completeTask(task: Task, completed: Boolean) {
        task.isCompleted = completed

        tasksRepository.completedTask(task.id, completed)
        if (completed) {
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    private fun showSnackbarMessage(messageId: Int) {
        snackbarMessage.value = messageId
    }

    fun addNewTask() {
        newTaskEvent.call()
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int) {
        if (TasksActivity.REQUEST_CODE == requestCode) {
            snackbarMessage.value = when (resultCode) {
                TasksActivity.DELETE_RESULT_OK -> R.string.successfully_deleted_task_message
                TasksActivity.EDIT_RESULT_OK -> R.string.successfully_saved_task_message
                TasksActivity.ADD_RESULT_OK -> R.string.successfully_added_task_message
                else -> return
            }
        }
    }
}