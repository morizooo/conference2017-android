package io.builderscon.conference2017.extension

import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast

fun AppCompatActivity.getScreenWidth(): Int {
    val display = this.windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun AppCompatActivity.openWebView(linkUrl: String?) {
    if (linkUrl?.startsWith("http") ?: false) {
        val url = Uri.parse(linkUrl)
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    } else {
        Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
    }
}

fun AppCompatActivity.initSupportActionBar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayShowTitleEnabled(false)
}
