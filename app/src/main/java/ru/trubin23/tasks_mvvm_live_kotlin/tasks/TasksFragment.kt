package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.TasksFragBinding

class TasksFragment : Fragment() {

    private lateinit var viewDataBinding: TasksFragBinding
    private lateinit var listAdapter: TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun newInstance() = TasksFragment()
    }
}