package io.builderscon.conference2017.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import io.builderscon.conference2017.R
import kotlinx.android.synthetic.main.activity_qrcode_reader.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QRCodeReaderActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    override fun handleResult(result: Result?) {
        val text: String? = result?.text
        AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(text ?: "")
                .setPositiveButton("Open", { _, _ ->
                    if (text?.startsWith("http") ?: false) {
                        val url = Uri.parse(result?.text)
                        val intent = Intent(Intent.ACTION_VIEW, url)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
                    }

                })
                .setNegativeButton("Cancel", { _, _ ->
                    mScannerView?.resumeCameraPreview(this)
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
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }
}
