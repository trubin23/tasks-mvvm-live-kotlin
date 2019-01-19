package ru.trubin23.tasks_mvvm_live_kotlin.data.source

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.cache.TasksCacheDataSource
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.local.TasksLocalDataSource

class TasksRepository private constructor(
        private val mTasksRemoteDataSource: TasksDataSource,
        private val mTasksLocalDataSource: TasksLocalDataSource,
        private val mTasksCacheDataSource: TasksCacheDataSource
) : TasksMainDataSource {

    private var mForceRefresh: Boolean = false

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        val tasks = mTasksCacheDataSource.getTasks()
        if (tasks != null) {
            callback.onTasksLoaded(tasks)
            return
        }

        if (mForceRefresh) {
            getTasksFromRemoteDataSource(callback, true)
        } else {
            getTasksFromLocalDataSource(callback, true)
        }
    }

    private fun getTasksFromLocalDataSource(callback: TasksDataSource.LoadTasksCallback,
                                            handleErrors: Boolean) {
        mTasksLocalDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                mTasksCacheDataSource.setTasks(tasks)
                callback.onTasksLoaded(tasks)
            }

            override fun onDataNotAvailable() {
                if (handleErrors) {
                    getTasksFromRemoteDataSource(callback, false)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        })
    }

    private fun getTasksFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback,
                                             handleErrors: Boolean) {
        mTasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                mTasksCacheDataSource.setTasks(tasks)
                mTasksLocalDataSource.setTasks(tasks)
                mForceRefresh = false
                callback.onTasksLoaded(tasks)
            }

            override fun onDataNotAvailable() {
                if (handleErrors) {
                    getTasksFromLocalDataSource(callback, false)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        })
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val task = mTasksCacheDataSource.getTaskById(taskId)
        if (task != null) {
            callback.onTaskLoaded(task)
            return
        }

        mTasksLocalDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                mTasksCacheDataSource.addTask(task)
                callback.onTaskLoaded(task)
            }

            override fun onDataNotAvailable() {
                getOneTaskFromRemoteDataSource(taskId, callback)
            }
        })
    }

    private fun getOneTaskFromRemoteDataSource(taskId: String,
                                               callback: TasksDataSource.GetTaskCallback) {
        mTasksRemoteDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                mTasksCacheDataSource.addTask(task)
                mTasksLocalDataSource.saveTask(task)
                callback.onTaskLoaded(task)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun saveTask(task: Task) {
        mTasksRemoteDataSource.saveTask(task)
        mTasksLocalDataSource.saveTask(task)
        mTasksCacheDataSource.addTask(task)
    }

    override fun updateTask(task: Task) {
        mTasksRemoteDataSource.updateTask(task)
        mTasksLocalDataSource.updateTask(task)
        mTasksCacheDataSource.addTask(task)
    }

    override fun deleteTask(taskId: String) {
        mTasksRemoteDataSource.deleteTask(taskId)
        mTasksLocalDataSource.deleteTask(taskId)
        mTasksCacheDataSource.removeTask(taskId)
    }

    override fun completedTask(taskId: String, completed: Boolean) {
        mTasksRemoteDataSource.completedTask(taskId, completed)
        mTasksLocalDataSource.completedTask(taskId, completed)
        mTasksCacheDataSource.completedTask(taskId, completed)
    }

    override fun clearCompletedTasks() {
        mTasksRemoteDataSource.clearCompletedTasks()
        mTasksLocalDataSource.clearCompletedTasks()
        mTasksCacheDataSource.clearCompletedTasks()
    }

    override fun refreshTasks() {
        mTasksCacheDataSource.irrelevantState()
        mForceRefresh = true
    }

    companion object {

        private var INSTANCE: TasksRepository? = null

        @JvmStatic
        fun getInstance(tasksRemoteDataSource: TasksDataSource,
                        tasksLocalDataSource: TasksLocalDataSource,
                        tasksCacheDataSource: TasksCacheDataSource): TasksRepository {
            return INSTANCE ?: TasksRepository(tasksRemoteDataSource,
                    tasksLocalDataSource, tasksCacheDataSource).apply { INSTANCE = this }
        }
    }
}