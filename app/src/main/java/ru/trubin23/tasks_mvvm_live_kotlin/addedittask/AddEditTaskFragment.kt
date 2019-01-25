package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.addedittask.AddEditTaskActivity.Companion.ARGUMENT_EDIT_TASK_ID
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.AddedittaskFragBinding

class AddEditTaskFragment : Fragment() {

    private lateinit var viewDataBinding: AddedittaskFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.addedittask_frag, container, false)


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun newInstance(taskId: String) = AddEditTaskFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_EDIT_TASK_ID, taskId)
            }
        }
    }
}