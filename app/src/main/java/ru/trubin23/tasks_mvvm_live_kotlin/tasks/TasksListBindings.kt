package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.databinding.BindingAdapter
import android.widget.ListView
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

object TasksListBindings {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: ListView, items: List<Task>){
        with(listView.adapter as TasksAdapter){
            setList(items)
        }
    }
}