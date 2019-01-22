package ru.trubin23.tasks_mvvm_live_kotlin.taskdetail

import android.view.View

interface TaskDetailUserActionsListener {

    fun onCompleteChanged(view: View)
}