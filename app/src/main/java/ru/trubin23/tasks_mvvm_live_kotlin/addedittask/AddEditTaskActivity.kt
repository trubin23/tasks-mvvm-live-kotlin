package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.util.ADD_RESULT_OK

class AddEditTaskActivity : AppCompatActivity(), AddEditTaskNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addedittask_act)
    }

    override fun onTaskSaved() {
        setResult(ADD_RESULT_OK)
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"
    }
}
