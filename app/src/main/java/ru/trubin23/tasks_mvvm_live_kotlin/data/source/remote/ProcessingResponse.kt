package ru.trubin23.tasks_mvvm_live_kotlin.data.source.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ProcessingResponse<T> : Callback<T> {
    override fun onResponse(call: Call<T>?, response: Response<T>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                responseBody(body)
            } else {
                dataNotAvailable()
            }
        } else {
            dataNotAvailable()
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        dataNotAvailable()
    }

    open fun responseBody(body: T) {

    }

    open fun dataNotAvailable() {

    }
}