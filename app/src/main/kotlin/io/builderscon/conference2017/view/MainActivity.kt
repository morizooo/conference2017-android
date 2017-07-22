package io.builderscon.conference2017.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import io.builderscon.client.model.Conference
import io.builderscon.conference2017.R
import io.builderscon.conference2017.model.service.SponsorService
import io.builderscon.conference2017.model.service.TimetableService
import io.builderscon.conference2017.repository.file.FileRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_timeline -> {
                message.setText(R.string.title_timeline)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_information -> {
                returnTenAsync()
                message.setText(R.string.title_information)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun returnTenAsync() = async(CommonPool) {
        val timetable = TimetableService().read()
        println(timetable)
        return@async 10
    }

}
