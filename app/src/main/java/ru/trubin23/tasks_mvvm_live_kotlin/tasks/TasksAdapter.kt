package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

class TasksAdapter(
        private var tasks: List<Task>,
        private val tasksViewModel: TasksViewModel
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int) = tasks[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = tasks.size

    fun setList(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}