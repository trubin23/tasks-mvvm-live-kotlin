package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.TaskItemBinding

class TasksAdapter(
        private var tasks: List<Task>,
        private val tasksViewModel: TasksViewModel
) : BaseAdapter() {

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val binding: TaskItemBinding = if (view == null) {
            val inflater = LayoutInflater.from(parent?.context)

            TaskItemBinding.inflate(inflater, parent, false)
        } else {
            DataBindingUtil.getBinding(view)!!
        }

        val userActionsListener = object : TaskItemUserActionsListener {
            override fun onCompleteChanged(task: Task, view: View) {
                val checked = (view as CheckBox).isChecked
                tasksViewModel.completeTask(task, checked)
            }

            override fun onTaskClicked(task: Task) {
                tasksViewModel.openTaskEvent.value = task.id
            }
        }

        with(binding) {
            task = getItem(position)
            listener = userActionsListener
            executePendingBindings()
        }

        return binding.root
    }

    override fun getItem(position: Int) = tasks[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = tasks.size

    fun setList(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}