package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.PopupMenu
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
            setColorSchemeColors(
                    ContextCompat.getColor(context ?: return, R.color.colorPrimary),
                    ContextCompat.getColor(context ?: return, R.color.colorAccent),
                    ContextCompat.getColor(context ?: return, R.color.colorPrimaryDark)
            )

            scrollUpChild = viewDataBinding.tasksList
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.tasks_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear -> {
                viewDataBinding.viewmodel?.clearCompletedTasks()
                true
            }
            R.id.menu_filter -> {
                showFilteringPopMenu()
                true
            }
            R.id.menu_refresh -> {
                viewDataBinding.viewmodel?.loadTasks(true)
                true
            }
            else -> false
        }
    }

    private fun showFilteringPopMenu() {
        PopupMenu(context ?: return, (activity ?: return).findViewById(R.id.menu_filter)).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener {
                viewDataBinding.viewmodel?.run {
                    currentFiltering = when (it.itemId) {
                        R.id.menu_active -> TasksFilterType.ACTIVE_TASKS
                        R.id.menu_completed -> TasksFilterType.COMPLETED_TASKS
                        else -> TasksFilterType.ALL_TASKS
                    }
                }
                true
            }
            show()
        }
    }

    companion object {
        fun newInstance() = TasksFragment()
    }
}