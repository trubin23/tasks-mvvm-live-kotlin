package ru.trubin23.tasks_mvvm_live_kotlin.data.source.cache

import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

class TasksCacheRepository : TasksCacheDataSource {

    private val mCachedTask: HashMap<String, Task> = LinkedHashMap()

    private var mCacheIsDirty = false

    override fun getTasks(): List<Task>? {
        return if (cacheNotAvailable()){
            null
        } else {
            ArrayList(mCachedTask.values)
        }
    }

    override fun setTasks(tasks: List<Task>) {
        mCachedTask.clear()

        for (task in tasks){
            mCachedTask[task.id] = task
        }
        mCacheIsDirty = false
    }

    override fun getTaskById(taskId: String): Task? {
        return if (cacheNotAvailable()){
            null
        } else {
            mCachedTask[taskId]
        }
    }

    override fun addTask(task: Task) {
        mCachedTask[task.id] = task
    }

    override fun removeTask(taskId: String) {
        mCachedTask.remove(taskId)
    }

    override fun completedTask(taskId: String, completed: Boolean) {
        val task: Task? = getTaskById(taskId)
        if (task != null){
            task.isCompleted = completed
        }
    }

    override fun clearCompletedTasks() {
        val iterator = mCachedTask.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (entry.value.isCompleted) {
                iterator.remove()
            }
        }
    }

    override fun irrelevantState() {
        mCacheIsDirty = true
    }

    private fun cacheNotAvailable(): Boolean {
        return mCacheIsDirty || mCachedTask.isEmpty()
    }

    companion object {
        private var INSTANCE: TasksCacheRepository? = null

        @JvmStatic
        fun getInstance(): TasksCacheRepository {
            if (INSTANCE == null) {
                synchronized(TasksCacheRepository::javaClass) {
                    if (INSTANCE == null) {
                        INSTANCE = TasksCacheRepository()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}