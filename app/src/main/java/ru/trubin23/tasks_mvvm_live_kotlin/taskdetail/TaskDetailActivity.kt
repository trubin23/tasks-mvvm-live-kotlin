package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.trubin23.tasks_mvvm_live_kotlin.R

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.taskdetail_act)
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}
