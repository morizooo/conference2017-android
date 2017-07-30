package io.builderscon.conference2017.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.builderscon.conference2017.R
import io.builderscon.conference2017.extension.toMD
import io.builderscon.conference2017.model.repository.TimetableRepository
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class TimetableFragment : Fragment() {

    lateinit var title: List<String>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        launch(UI) {
            frame_loading?.visibility = View.VISIBLE
            title = async(CommonPool) {
                TimetableRepository().read().map { (schedule) ->
                    schedule.open.toMD()
                }
            }.await()

            navigationTabStrip?.apply {
                setTitles(*title.toTypedArray())
                val adapter = SessionsViewPagerAdapter(childFragmentManager)
                viewpager.adapter = adapter
                viewpager.offscreenPageLimit = titles.size - 1
                setViewPager(viewpager)
            }
            frame_loading?.visibility = View.GONE
        }
    }

    private fun loadSession(index: Int): SessionsFragment {
        val bundle = Bundle()
        bundle.putInt("tabIndex", index)
        val sessionsFragment = SessionsFragment()
        sessionsFragment.arguments = bundle
        return sessionsFragment
    }

    inner class SessionsViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return title.size
        }

        override fun getItem(position: Int): Fragment? {
            return loadSession(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return title[position]
        }
    }
}


