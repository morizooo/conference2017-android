package io.builderscon.conference2017.view.activity

import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.builderscon.client.model.Sponsor
import io.builderscon.conference2017.R
import io.builderscon.conference2017.extension.getScreenWidth
import io.builderscon.conference2017.extension.openWebView
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

        getScreenWidth()

        tier1?.let { drawGridLayout(grid_tier1, it) }
        tier2?.let { drawGridLayout(grid_tier2, it) }
        tier3?.let { drawGridLayout(grid_tier3, it) }
    }

    private fun drawGridLayout(gridLayout: GridLayout, sponsors: List<Sponsor>) {
        val columnSize = gridLayout.columnCount
        val cellSize = this.getScreenWidth() /columnSize
        sponsors.forEach { sponsor ->
            val imageView = ImageView(this)
            imageView.background = ContextCompat.getDrawable(this, R.color.lightGray)
            val borderSize = resources.getDimension(R.dimen.sponsor_border_size).toInt()
            imageView.setPadding(borderSize,borderSize,borderSize,borderSize)
            Picasso.with(this)
                    .load(sponsor.logoURL)
                    .resize(cellSize, cellSize)
                    .into(imageView)
            gridLayout.addView(imageView, gridLayoutParams(cellSize))

            imageView.setOnClickListener {
                this.openWebView(sponsor.linkURL)
            }
        }
    }

    private fun gridLayoutParams(size: Int): GridLayout.LayoutParams {
        val gridMargin = resources.getDimension(R.dimen.sponsor_margin_size).toInt()
        val cellMargin = gridMargin * 2
        val gridParams = GridLayout.LayoutParams()
        gridParams.width = size - cellMargin
        gridParams.height = size - cellMargin
        gridParams.setMargins(gridMargin, gridMargin, gridMargin, gridMargin)
        return gridParams
    }
}

