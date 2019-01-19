package ru.trubin23.tasks_mvvm_live_kotlin.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {

        private var INSTANCE: TasksDatabase? = null

        private val mLock = Any()

        fun getInstance(context: Context): TasksDatabase {
            synchronized(mLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TasksDatabase::class.java, "Tasks.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}