package ru.trubin23.tasks_mvvm_live_kotlin.addedittask

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.addedittask.AddEditTaskActivity.Companion.ARGUMENT_EDIT_TASK_ID
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.AddedittaskFragBinding
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupSnackbar

class AddEditTaskFragment : Fragment() {

    private lateinit var viewDataBinding: AddedittaskFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.addedittask_frag, container, false)
        viewDataBinding = AddedittaskFragBinding.bind(root).apply {
            viewmodel = (activity as AddEditTaskActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)

        retainInstance = false

        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_task_done)?.apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener { viewDataBinding.viewmodel?.saveTask() }
        }

        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }

        setupActionBar()

        return viewDataBinding.root
    }

    private fun setupActionBar() {
        val title = if (arguments?.get(ARGUMENT_EDIT_TASK_ID) != null) {
            R.string.title_edit_task
        } else {
            R.string.title_add_task
        }

        (activity as AppCompatActivity).supportActionBar?.setTitle(title)
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start(arguments?.getString(ARGUMENT_EDIT_TASK_ID))
    }

    companion object {
        fun newInstance(taskId: String?) = AddEditTaskFragment().apply {
            taskId ?: return@apply
            arguments = Bundle().apply {
                putString(ARGUMENT_EDIT_TASK_ID, taskId)
            }
        }
    }
}