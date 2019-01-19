package ru.trubin23.tasks_mvvm_live_kotlin.data.source.local

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksDataSource

interface TasksLocalDataSource : TasksDataSource {

    fun setTasks(tasks: List<Task>)

    fun deleteAllTasks()
}