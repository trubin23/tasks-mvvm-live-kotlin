package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.util.obtainViewModel

class TaskDetailActivity : AppCompatActivity(), TaskDetailNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.taskdetail_act)
    }

    override fun onTaskDeleted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartEditTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun obtainViewModel(): TaskDetailViewModel = obtainViewModel(TaskDetailViewModel::class.java)

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}
