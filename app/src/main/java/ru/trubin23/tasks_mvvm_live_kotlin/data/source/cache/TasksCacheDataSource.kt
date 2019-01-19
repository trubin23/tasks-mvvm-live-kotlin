package ru.trubin23.tasks_mvvm_live_kotlin.data.source.cache

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

interface TasksCacheDataSource {

    fun getTasks(): List<Task>?

    fun setTasks(tasks: List<Task>)

    fun getTaskById(taskId: String): Task?

    fun addTask(task: Task)

    fun removeTask(taskId: String)

    fun completedTask(taskId: String, completed: Boolean)

    fun clearCompletedTasks()

    fun irrelevantState()
}