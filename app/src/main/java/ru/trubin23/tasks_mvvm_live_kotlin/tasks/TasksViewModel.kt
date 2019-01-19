package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class TasksViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    private val context: Context = context.applicationContext


}