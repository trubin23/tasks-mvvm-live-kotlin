package ru.trubin23.tasks_mvvm_live_kotlin.data.source.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatusOfTask(completed: Boolean) {

    @SerializedName("completed")
    @Expose
    var mCompleted: Int = booleanToInteger(completed)

    companion object {

        fun booleanToInteger(completed: Boolean): Int {
            return if (completed) 1 else 0
        }

        fun integerToBoolean(completed: Int?): Boolean {
            return completed != null && completed != 0
        }
    }
}