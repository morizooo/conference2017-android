package io.builderscon.conference2017.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import io.builderscon.conference2017.R
import io.builderscon.conference2017.view.fragment.InformationFragment
import io.builderscon.conference2017.view.fragment.TimelineFragment
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
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            timelineItemId -> {
                toolbar_title.text = getString(R.string.title_timeline)
                this.supportFragmentManager.beginTransaction().replace(R.id.fragment, TimelineFragment(), "SessionFragment").commit()
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

}
