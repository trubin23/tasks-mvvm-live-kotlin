package ru.trubin23.tasks_mvvm_live_kotlin.data.source

interface TasksMainDataSource : TasksDataSource {

    fun refreshTasks()
}