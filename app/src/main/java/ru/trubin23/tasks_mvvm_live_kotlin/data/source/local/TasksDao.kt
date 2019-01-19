package ru.trubin23.tasks_mvvm_live_kotlin.data.source.local

import android.arch.persistence.room.*
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE entryId = :taskId")
    fun getTaskById(taskId: String): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(tasks: List<Task>)

    @Update
    fun updateTask(task: Task): Int

    @Query("UPDATE tasks SET completed = :completed WHERE entryId = :taskId")
    fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM tasks WHERE entryId = :taskId")
    fun deleteTaskById(taskId: String)

    @Query("DELETE FROM tasks")
    fun deleteTasks()

    @Query("DELETE FROM tasks WHERE completed=1")
    fun deleteCompletedTasks(): Int
}