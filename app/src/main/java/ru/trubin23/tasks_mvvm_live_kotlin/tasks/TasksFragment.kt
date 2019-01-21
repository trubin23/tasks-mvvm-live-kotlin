package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.TasksFragBinding
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupSnackbar

class TasksFragment : Fragment() {

    private lateinit var viewDataBinding: TasksFragBinding
    private lateinit var listAdapter: TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = TasksFragBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as TasksActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)

        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }
        setupFab()
        setupListAdapter()
        setupRefreshLayout()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start()
    }

    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.fab_add_task)?.run {
            setImageResource(R.drawable.ic_add)
            setOnClickListener {
                viewDataBinding.viewmodel?.addNewTask()
            }
        }
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = TasksAdapter(ArrayList(), viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
        }
    }

    private fun setupRefreshLayout() {
        viewDataBinding.refreshLayout.run {
            val linkContext = context ?: return

            setColorSchemeColors(
                    ContextCompat.getColor(linkContext, R.color.colorPrimary),
                    ContextCompat.getColor(linkContext, R.color.colorAccent),
                    ContextCompat.getColor(linkContext, R.color.colorPrimaryDark)
            )

            scrollUpChild = viewDataBinding.tasksList
        }
    }

    companion object {
        fun newInstance() = TasksFragment()
    }
}