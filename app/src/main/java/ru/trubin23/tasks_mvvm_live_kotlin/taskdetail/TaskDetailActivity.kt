package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.addedittask.AddEditTaskActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.*

class TaskDetailActivity : AppCompatActivity(), TaskDetailNavigator {

    private lateinit var taskDetailViewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.taskdetail_act)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupViewFragment()

        taskDetailViewModel = obtainViewModel()

        val activity = this@TaskDetailActivity
        taskDetailViewModel.run {
            editTaskCommand.observe(activity, Observer { activity.onStartEditTask() })
            deleteTaskCommand.observe(activity, Observer { activity.onTaskDeleted() })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_EDIT_TASK && resultCode == ADD_RESULT_OK){
            setResult(EDIT_RESULT_OK)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: TaskDetailFragment.newInstance(intent.getStringExtra(EXTRA_TASK_ID)).let {
                    addFragmentToActivity(it, R.id.contentFrame)
                }
    }

    override fun onTaskDeleted() {
        setResult(DELETE_RESULT_OK)
        finish()
    }

    override fun onStartEditTask() {
        val taskId = intent.getStringExtra(EXTRA_TASK_ID)
        val intent = Intent(this, AddEditTaskActivity::class.java).apply {
            putExtra(AddEditTaskActivity.ARGUMENT_EDIT_TASK_ID, taskId)
        }
        startActivityForResult(intent, REQUEST_EDIT_TASK)
    }

    fun obtainViewModel(): TaskDetailViewModel = obtainViewModel(TaskDetailViewModel::class.java)

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
        const val REQUEST_EDIT_TASK = 1
    }
}
