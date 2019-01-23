package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.trubin23.tasks_mvvm_live_kotlin.R

class AddEditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addedittask_act)
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"
    }
}
