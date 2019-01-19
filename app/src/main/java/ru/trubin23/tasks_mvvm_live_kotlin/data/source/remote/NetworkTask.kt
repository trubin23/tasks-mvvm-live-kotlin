package ru.trubin23.tasks_mvvm_live_kotlin.data.source.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class NetworkTask(@field:SerializedName("id")
                  @field:Expose
                  var id: String = UUID(0, 0).toString(),
                  @field:SerializedName("title")
                  @field:Expose
                  var title: String = "",
                  @field:SerializedName("description")
                  @field:Expose
                  var description: String = "",
                  completed: Boolean) {

    @SerializedName("completed")
    @Expose
    var completed: Int = StatusOfTask.booleanToInteger(completed)
}