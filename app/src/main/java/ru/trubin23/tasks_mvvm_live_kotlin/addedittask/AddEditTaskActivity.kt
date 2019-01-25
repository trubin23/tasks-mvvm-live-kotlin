package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.util.ADD_RESULT_OK
import ru.trubin23.tasks_mvvm_live_kotlin.util.addFragmentToActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.obtainViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupActionBar

class AddEditTaskActivity : AppCompatActivity(), AddEditTaskNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addedittask_act)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupViewFragment()

        obtainViewModel().taskUpdatedEvent.observe(this, Observer {
            onTaskSaved()
        })
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: AddEditTaskFragment.newInstance(
                        intent.getStringExtra(ARGUMENT_EDIT_TASK_ID)).let {
                    addFragmentToActivity(it, R.id.contentFrame)
                }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onTaskSaved() {
        setResult(ADD_RESULT_OK)
        finish()
    }

    fun obtainViewModel(): AddEditTaskViewModel = obtainViewModel(AddEditTaskViewModel::class.java)

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"
    }
}
