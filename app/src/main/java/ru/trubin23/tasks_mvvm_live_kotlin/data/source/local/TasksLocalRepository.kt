package ru.trubin23.tasks_mvvm_live_kotlin.data.source.local

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksDataSource
import ru.trubin23.tasks_mvvm_live_kotlin.util.AppExecutors

class TasksLocalRepository private constructor(
        private val mAppExecutors: AppExecutors,
        private val mTasksDao: TasksDao
) : TasksLocalDataSource {
    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        mAppExecutors.diskIO.execute {
            val tasks = mTasksDao.getTasks()
            if (tasks.isEmpty()) {
                mAppExecutors.mainThread.execute { callback.onDataNotAvailable() }
            } else {
                mAppExecutors.mainThread.execute { callback.onTasksLoaded(tasks) }
            }
        }
    }

    override fun setTasks(tasks: List<Task>) {
        mAppExecutors.diskIO.execute {
            mTasksDao.deleteTasks()
            mTasksDao.insertTasks(tasks)
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        mAppExecutors.diskIO.execute {
            val task = mTasksDao.getTaskById(taskId)
            if (task == null) {
                mAppExecutors.mainThread.execute { callback.onDataNotAvailable() }
            } else {
                mAppExecutors.mainThread.execute { callback.onTaskLoaded(task) }
            }
        }
    }

    override fun saveTask(task: Task) {
        mAppExecutors.diskIO.execute { mTasksDao.insertTask(task) }
    }

    override fun updateTask(task: Task) {
        mAppExecutors.diskIO.execute { mTasksDao.updateTask(task) }
    }

    override fun deleteTask(taskId: String) {
        mAppExecutors.diskIO.execute { mTasksDao.deleteTaskById(taskId) }
    }

    override fun deleteAllTasks() {
        mAppExecutors.diskIO.execute { mTasksDao.deleteTasks() }
    }

    override fun completedTask(taskId: String, completed: Boolean) {
        mAppExecutors.diskIO.execute { mTasksDao.updateCompleted(taskId, completed) }
    }

    override fun clearCompletedTasks() {
        mAppExecutors.diskIO.execute { mTasksDao.deleteCompletedTasks() }
    }

    companion object {
        private var INSTANCE: TasksLocalRepository? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors,
                        tasksDao: TasksDao): TasksLocalRepository {
            if (INSTANCE == null) {
                synchronized(TasksLocalRepository::javaClass) {
                    if (INSTANCE == null) {
                        INSTANCE = TasksLocalRepository(appExecutors, tasksDao)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}