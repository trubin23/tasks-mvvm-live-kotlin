package ru.trubin23.tasks_mvvm_live_kotlin.statistics

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.trubin23.tasks_mvvm_live_kotlin.R
import ru.trubin23.tasks_mvvm_live_kotlin.databinding.StatisticsFragBinding

class StatisticsFragment : Fragment() {

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewDataBinding: StatisticsFragBinding = DataBindingUtil.inflate(inflater,
                R.layout.statistics_frag, container, false)

        viewModel = (activity as StatisticsActivity).obtainViewModel()
        viewDataBinding.viewmodel = viewModel

        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}