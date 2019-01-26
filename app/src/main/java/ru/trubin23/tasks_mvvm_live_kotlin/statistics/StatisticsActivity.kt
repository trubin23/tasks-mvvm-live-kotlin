package ru.trubin23.tasks_mvvm_live_kotlin.statistics

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.tasks.TasksActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.addFragmentToActivity
import ru.trubin23.tasks_mvvm_live_kotlin.util.obtainViewModel
import ru.trubin23.tasks_mvvm_live_kotlin.util.setupActionBar

class StatisticsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics_act)

        setupActionBar(R.id.toolbar) {
            setTitle(R.string.statistics)
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        setupNavigationDrawer()

        setupViewFragment()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.list_nav_menu_item) {
                val intent = Intent(this@StatisticsActivity, TasksActivity::class.java)
                startActivity(intent)
            }

            drawerLayout.closeDrawers()

            true
        }
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: StatisticsFragment.newInstance().let {
                    addFragmentToActivity(it, R.id.contentFrame)
                }
    }

    fun obtainViewModel(): StatisticsViewModel = obtainViewModel(StatisticsViewModel::class.java)
}
