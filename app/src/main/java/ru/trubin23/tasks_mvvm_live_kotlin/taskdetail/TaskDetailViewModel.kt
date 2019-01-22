package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksRepository

class TaskDetailViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {


}