package io.builderscon.conference2017.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import io.builderscon.conference2017.R
import kotlinx.android.synthetic.main.activity_floor_map.*

class FloorMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floor_map)
        setSupportActionBar(tool_bar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_title.text = getString(R.string.title_floormap)

        img_floormap.setImage(ImageSource.resource(R.mipmap.floor_map))
    }
}
