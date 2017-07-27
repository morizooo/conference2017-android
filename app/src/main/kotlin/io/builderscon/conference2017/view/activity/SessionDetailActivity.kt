package io.builderscon.conference2017.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        txt_title.text = intent.getStringExtra("title")
        txt_session_start.text = intent.getStringExtra("start")
        txt_speaker.text = intent.getStringExtra("speakerName")

        Picasso.with(this)
                .load(intent.getStringExtra("avatarURL"))
                .into(img_speaker)

        val abstract = intent.getStringExtra("abstract")


        markdown_view.loadMarkdown(abstract)
    }
}
