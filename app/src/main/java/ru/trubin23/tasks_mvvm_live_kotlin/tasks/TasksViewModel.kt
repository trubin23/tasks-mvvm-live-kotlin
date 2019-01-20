package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
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

    fun start(){
        loadTasks(false)
    }

    fun loadTasks(forceUpdate: Boolean) {
        loadTasks(forceUpdate, true)
    }

    private fun loadTasks(forceUpdate: Boolean, showLoadingUI: Boolean){
        if (showLoadingUI){
            //dataLoading.set(true)
        }
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
}