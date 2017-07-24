package io.builderscon.conference2017.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.builderscon.conference2017.R
import io.builderscon.conference2017.model.repository.TimetableRepository
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
            schedule.let { fmt.format(it.open) } }



        navigationTabStrip.setTitles(titles[0], titles[1], titles[2])
        navigationTabStrip.setTabIndex(0, true)
    }

}
