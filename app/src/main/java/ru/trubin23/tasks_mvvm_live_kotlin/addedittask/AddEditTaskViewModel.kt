package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class AddEditTaskViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val dataLoading = ObservableBoolean(false)

    fun start(taskId: String?) {
    }
}