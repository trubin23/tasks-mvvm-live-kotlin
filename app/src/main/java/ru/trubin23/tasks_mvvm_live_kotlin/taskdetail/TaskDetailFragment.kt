package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.TaskdetailFragBinding
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupSnackbar

class TaskDetailFragment : Fragment() {

    private lateinit var viewDataBinding: TaskdetailFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.taskdetail_frag, container, false)
        viewDataBinding = TaskdetailFragBinding.bind(view).apply {
            viewmodel = (activity as TaskDetailActivity).obtainViewModel()
            listener = object : TaskDetailUserActionsListener{
                override fun onCompleteChanged(view: View) {
                    viewmodel?.setCompleted((view as CheckBox).isChecked)
                }
            }
        }

        setHasOptionsMenu(true)

        activity?.findViewById<View>(R.id.fab_edit_task)?.setOnClickListener {
            viewDataBinding.viewmodel?.editTask()
        }

        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        arguments?.getString(ARGUMENT_TASK_ID).let {
            viewDataBinding.viewmodel?.start(it)
        }
    }

    companion object {
        private const val ARGUMENT_TASK_ID = "TASK_ID"
    }
}