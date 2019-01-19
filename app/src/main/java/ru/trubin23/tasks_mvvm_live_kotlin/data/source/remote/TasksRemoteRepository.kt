package ru.trubin23.tasks_mvvm_live_kotlin.data.source.remote

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.data.source.TasksDataSource
import ru.trubin23.tasks_mvvm_live_kotlin.util.AppExecutors

class TasksRemoteRepository private constructor(
        private val mAppExecutors: AppExecutors
) : TasksDataSource {

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        mAppExecutors.networkIO.execute {
            RetrofitClient.getTasks(
                    object : ProcessingResponse<List<NetworkTask>>() {
                        override fun responseBody(body: List<NetworkTask>) {
                            val tasks = TaskMapper.networkTaskListToTaskList(body)
                            mAppExecutors.mainThread.execute { callback.onTasksLoaded(tasks) }
                        }

                        override fun dataNotAvailable() {
                            mAppExecutors.mainThread.execute { callback.onDataNotAvailable() }
                        }
                    })
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        mAppExecutors.networkIO.execute {
            RetrofitClient.getTask(taskId,
                    object : ProcessingResponse<NetworkTask>() {
                        override fun responseBody(body: NetworkTask) {
                            val task = TaskMapper.networkTaskToTask(body)
                            mAppExecutors.mainThread.execute { callback.onTaskLoaded(task) }
                        }

                        override fun dataNotAvailable() {
                            mAppExecutors.mainThread.execute { callback.onDataNotAvailable() }
                        }
                    })
        }
    }

    override fun saveTask(task: Task) {
        val networkTask = TaskMapper.taskToNetworkTask(task)

        mAppExecutors.networkIO.execute {
            RetrofitClient.addTask(networkTask, ProcessingResponse())
        }
    }

    override fun updateTask(task: Task) {
        val networkTask = TaskMapper.taskToNetworkTask(task)

        mAppExecutors.networkIO.execute {
            RetrofitClient.updateTask(networkTask, ProcessingResponse())
        }
    }

    override fun deleteTask(taskId: String) {
        mAppExecutors.networkIO.execute {
            RetrofitClient.deleteTask(taskId, ProcessingResponse())
        }
    }

    override fun completedTask(taskId: String, completed: Boolean) {
        val statusOfTask = StatusOfTask(completed)

        mAppExecutors.networkIO.execute {
            RetrofitClient.completeTask(taskId, statusOfTask, ProcessingResponse())
        }
    }

    override fun clearCompletedTasks() {
        mAppExecutors.networkIO.execute {
            RetrofitClient.deleteCompletedTasks(ProcessingResponse())
        }
    }

    companion object {
        private var INSTANCE: TasksRemoteRepository? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors): TasksRemoteRepository {
            if (INSTANCE == null) {
                synchronized(TasksRemoteRepository::javaClass) {
                    if (INSTANCE == null) {
                        INSTANCE = TasksRemoteRepository(appExecutors)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}