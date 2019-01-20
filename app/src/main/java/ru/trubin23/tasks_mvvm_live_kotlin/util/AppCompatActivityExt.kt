package ru.trubin23.tasks_mvvm_live_kotlin.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.ViewModelFactory

fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transaction {
        add(frameId, fragment)
    }
}

private fun FragmentManager.transaction(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

//fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
//        ViewModelProviders
//                .of(this, ViewModelFactory.getInstance(application))
//                .get(viewModelClass)