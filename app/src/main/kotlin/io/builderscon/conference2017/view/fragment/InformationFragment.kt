package io.builderscon.conference2017.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import io.builderscon.conference2017.R
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
        list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, _, pos, _ ->
            Toast.makeText(activity, parent.getItemAtPosition(pos) as String, Toast.LENGTH_SHORT).show()
        }

        super.onViewCreated(view, savedInstanceState)
    }

}
