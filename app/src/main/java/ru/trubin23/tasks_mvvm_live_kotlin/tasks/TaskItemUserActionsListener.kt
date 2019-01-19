package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.view.View
import ru.trubin23.tasks_mvvm_live_kotlin.data.Task

interface TaskItemUserActionsListener {

    fun onCompleteChanged(task: Task, view: View)

    fun onTaskClicked(task: Task)
}