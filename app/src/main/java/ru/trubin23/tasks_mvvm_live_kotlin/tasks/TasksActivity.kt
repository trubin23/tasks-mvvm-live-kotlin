package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.util.addFragmentToActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupActionBar

class TasksActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_act)

        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        setupViewFragment()

        setupNavigationDrawer()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: TasksFragment.newInstance().let {
                    addFragmentToActivity(it, R.id.contentFrame)
                }
    }

    private fun setupNavigationDrawer() {

    }

    companion object {
        const val REQUEST_CODE = 1

        const val DELETE_RESULT_OK = 1
        const val EDIT_RESULT_OK = 2
        const val ADD_RESULT_OK = 3
    }
}
