package io.builderscon.conference2017.view.activity

import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import io.builderscon.conference2017.R
import io.builderscon.conference2017.view.fragment.InformationFragment
import io.builderscon.conference2017.view.fragment.TimetableFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val informationItemId = R.id.navigation_information
    private val timelineItemId = R.id.navigation_timeline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = timelineItemId

        changeIconSize()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            timelineItemId -> {
                toolbar_title.text = getString(R.string.title_timetable)
                this.supportFragmentManager.beginTransaction().replace(R.id.fragment, TimetableFragment(), "SessionFragment").commit()
                return@OnNavigationItemSelectedListener true
            }
            informationItemId -> {
                toolbar_title.text = getString(R.string.title_information)
                this.supportFragmentManager.beginTransaction().replace(R.id.fragment, InformationFragment(), "InformationFragment").commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun changeIconSize() {
        val menuView = navigation.getChildAt(0) as BottomNavigationMenuView
        for (i in 0..menuView.childCount - 1) {
            val iconView = menuView.getChildAt(i).findViewById<View>(android.support.design.R.id.icon)
            val displayMetrics = resources.displayMetrics
            val layoutParams = iconView.layoutParams.apply {
                height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, displayMetrics).toInt()
                width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, displayMetrics).toInt()
            }
            iconView.layoutParams = layoutParams
        }
    }

}
