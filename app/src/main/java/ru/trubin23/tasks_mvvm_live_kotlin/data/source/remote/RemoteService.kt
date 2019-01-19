package ru.trubin23.tasks_mvvm_live_kotlin.data.source.remote

import retrofit2.Call
import retrofit2.http.*

interface RemoteService {

    @GET("/api_mvp_kotlin_mvvm/tasks")
    fun getTasks(): Call<List<NetworkTask>>

    @GET("/api_mvp_kotlin_mvvm/tasks/{id}")
    fun getTask(@Path("id") id: String): Call<NetworkTask>

    @POST("/api_mvp_kotlin_mvvm/tasks")
    fun addTask(@Body task: NetworkTask): Call<NetworkTask>

    @PUT("/api_mvp_kotlin_mvvm/tasks/{id}")
    fun updateTask(@Path("id") id: String, @Body task: NetworkTask): Call<NetworkTask>

    @PUT("/api_mvp_kotlin_mvvm/tasks/{id}")
    fun completeTask(@Path("id") id: String, @Body task: StatusOfTask): Call<NetworkTask>

    @DELETE("/api_mvp_kotlin_mvvm/tasks/{id}")
    fun deleteTask(@Path("id") id: String): Call<NetworkTask>

    @DELETE("/api_mvp_kotlin_mvvm/tasks/completed")
    fun deleteCompletedTasks(): Call<Int>
}