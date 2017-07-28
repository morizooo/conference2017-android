package io.builderscon.conference2017.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.builderscon.client.model.Sponsor
import io.builderscon.conference2017.R
import io.builderscon.conference2017.model.repository.SponsorRepository
import kotlinx.android.synthetic.main.activity_sponsor.*

class SponsorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsor)
        setSupportActionBar(tool_bar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_title.text = getString(R.string.title_sponsor)

        val sponsor = SponsorRepository().read()
        val map = sponsor.groupBy { it.groupName }.toSortedMap()

        grid_tier1.columnCount = 2
        grid_tier2.columnCount = 3
        grid_tier3.columnCount = 4
        val tier1 = map["tier-1"]
        val tier2 = map["tier-2"]
        val tier3 = map["tier-3"]

        tier1?.let { drawGridLayout(grid_tier1, it) }
        tier2?.let { drawGridLayout(grid_tier2, it) }
        tier3?.let { drawGridLayout(grid_tier3, it) }
    }

    private fun drawGridLayout(gridLayout: GridLayout, sponsors: List<Sponsor>) {
        sponsors.forEach {
            val imageView = ImageView(this)
            Picasso.with(this)
                    .load(it.logoURL)
                    .resize(200, 200)
                    .into(imageView)
            gridLayout.addView(imageView)
        }
    }
}
