package io.builderscon.conference2017.view.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.builderscon.conference2017.R
import io.builderscon.conference2017.view.activity.FloorMapActivity
import io.builderscon.conference2017.view.activity.LicenseActivity
import io.builderscon.conference2017.view.activity.QRCodeReaderActivity
import io.builderscon.conference2017.view.activity.SponsorActivity
import kotlinx.android.synthetic.main.fragment_information.*


class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val menu: MutableList<String> = mutableListOf("Sponsor", "FloorMap", "QRCodeReader", "License")
        list_view.adapter =
                ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, menu)
        list_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            when (pos) {
                0 -> {
                    val intent = Intent(context, SponsorActivity::class.java)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, FloorMapActivity::class.java)
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, QRCodeReaderActivity::class.java)
                    context.startActivity(intent)
                }
                3 -> {
                    val intent = Intent(context, LicenseActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}
