package io.builderscon.conference2017.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import com.google.zxing.Result
import io.builderscon.conference2017.R
import io.builderscon.conference2017.extension.openWebView
import kotlinx.android.synthetic.main.activity_qrcode_reader.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QRCodeReaderActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    lateinit var mScannerView: ZXingScannerView

    override fun handleResult(result: Result?) {
        val text: String? = result?.text
        AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(text ?: "")
                .setPositiveButton("Open", { _, _ ->
                    this.openWebView(text)
                })
                .setNegativeButton("Cancel", { _, _ ->
                    mScannerView.resumeCameraPreview(this)
                })
                .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_reader)
        setSupportActionBar(tool_bar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_title.text = getString(R.string.title_qrcodereader)

        val frame = fragment as ViewGroup
        mScannerView = ZXingScannerView(this)
        frame.addView(mScannerView)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return ActivityCompat.requestPermissions(this,
                    listOf(Manifest.permission.CAMERA).toTypedArray(), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            mScannerView.setResultHandler(this)
            mScannerView.startCamera()
            return
        }

        this.finish()
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }
}
