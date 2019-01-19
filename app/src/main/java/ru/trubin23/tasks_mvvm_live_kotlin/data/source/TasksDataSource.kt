package ru.trubin23.tasks_mvvm_live_kotlin.data.source

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

interface TasksDataSource {

    interface LoadTasksCallback {

        fun onTasksLoaded(tasks: List<Task>)

        fun onDataNotAvailable()
    }

    interface GetTaskCallback {

        fun onTaskLoaded(task: Task)

        fun onDataNotAvailable()
    }

    fun getTasks(callback: LoadTasksCallback)

    fun getTask(taskId: String, callback: GetTaskCallback)

    fun saveTask(task: Task)

    fun updateTask(task: Task)

    fun deleteTask(taskId: String)

    fun completedTask(taskId: String, completed: Boolean)

    fun clearCompletedTasks()
}