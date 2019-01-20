package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.SingleLiveEvent
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class TasksViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    private val context: Context = context.applicationContext

    val openTaskEvent = SingleLiveEvent<String>()
    val newTaskEvent = SingleLiveEvent<Void>()
    val snackbarMessage = SingleLiveEvent<Int>()

    var currentFiltering = TasksFilterType.ALL_TASKS
        set(value) {
            field = value
            updateFiltering()
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

        }
    }

}