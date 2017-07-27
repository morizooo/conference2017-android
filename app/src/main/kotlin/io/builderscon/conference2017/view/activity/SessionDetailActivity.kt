package io.builderscon.conference2017.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import io.builderscon.conference2017.R
import kotlinx.android.synthetic.main.activity_session_detail.*

class SessionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_detail)

        setSupportActionBar(tool_bar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_title.text = getString(R.string.title_session)

        Picasso.with(this)
                .load(intent.getStringExtra("avatarURL"))
                .into(profile_image)

        val abstract = intent.getStringExtra("abstract")

        markdown_view.loadMarkdown(abstract)
    }
}
