package ru.trubin23.tasks_mvvm_live_kotlin.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.statistics.StatisticsActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.addFragmentToActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.obtainViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupActionBar

class TasksActivity : AppCompatActivity(), TaskItemNavigator, TasksNavigator {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var viewModel: TasksViewModel

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
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.statistics_nav_menu_item) {
                val intent = Intent(this@TasksActivity, StatisticsActivity::class.java)
                startActivity(intent)
            }

            drawerLayout.closeDrawers()

            true
        }
    }

    override fun openTaskDetails(taskId: String) {

    }

    override fun addNewTask() {

    }

    fun obtainViewModel(): TasksViewModel = obtainViewModel(TasksViewModel::class.java)

    companion object {
        const val REQUEST_CODE = 1

        const val DELETE_RESULT_OK = 1
        const val EDIT_RESULT_OK = 2
        const val ADD_RESULT_OK = 3
    }
}
