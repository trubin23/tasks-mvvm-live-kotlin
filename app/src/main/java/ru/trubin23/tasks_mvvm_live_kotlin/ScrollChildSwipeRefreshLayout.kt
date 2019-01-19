package ru.trubin23.tasks_mvvm_live_kotlin

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null
) : SwipeRefreshLayout(context, attributeSet) {

    var scrollUpChild: View? = null

    override fun canChildScrollUp() =
            scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()

}