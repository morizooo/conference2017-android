package io.builderscon.conference2017.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.builderscon.conference2017.R
import kotlinx.android.synthetic.main.activity_license.*

class LicenseActivity : AppCompatActivity() {

    private val LICENSES_HTML_PATH =  LicenseActivity::class.java.getResource("/assets/html/license.html").readText()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        setSupportActionBar(tool_bar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_title.text = getString(R.string.title_lisense)

        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.loadData(LICENSES_HTML_PATH, "text/html", "utf-8")
    }
}