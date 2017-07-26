package io.builderscon.conference2017.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gigamole.navigationtabstrip.NavigationTabStrip
import io.builderscon.conference2017.R
import io.builderscon.conference2017.model.repository.TimetableRepository
import io.builderscon.conference2017.view.SessionsFragment
import kotlinx.android.synthetic.main.fragment_timetable.*
import java.text.SimpleDateFormat

class TimetableFragment : Fragment() {

    private val fmt = SimpleDateFormat("M/d")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val titles = TimetableRepository().read().map { (schedule) ->
            schedule.let { fmt.format(it.open) }
        }

        navigationTabStrip.apply {
            setTitles(*titles.toTypedArray())
            setTabIndex(0, true)
            onTabStripSelectedIndexListener = (object : NavigationTabStrip.OnTabStripSelectedIndexListener {
                override fun onStartTabSelected(title: String?, index: Int) {
                    val bundle = Bundle()
                    bundle.putInt("tabIndex", index)
                    val sessionsFragment = SessionsFragment()
                    sessionsFragment.arguments = bundle
                    childFragmentManager.beginTransaction().replace(R.id.timetable, sessionsFragment, "SessionsFragment").commit()
                }

                override fun onEndTabSelected(title: String?, index: Int) {}
            })
        }
    }

}