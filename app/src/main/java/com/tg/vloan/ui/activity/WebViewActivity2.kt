package com.tg.vloan.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import com.azhon.appupdate.listener.OnDownloadListenerAdapter
import com.azhon.appupdate.manager.DownloadManager
import com.tg.vloan.App
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.config.Constants
import com.tg.vloan.databinding.ActivityWebview2Binding
import com.tg.vloan.utils.Utils
import java.io.File
import java.util.*

class WebViewActivity2 : BaseActivity<ActivityWebview2Binding>() {
    private val URL = "url"
    private val TITLE = "title"


    companion object {
        fun gotoActivity(context: Context?, url:String?, title:String?,isAudio:Int) {
            val starter = Intent(context, WebViewActivity2::class.java)
            starter.putExtra(Constants.URL, url)
            starter.putExtra(Constants.TITLE, title)
            starter.putExtra(Constants.IS_AUDIO, isAudio)
            context!!.startActivity(starter)
        }
    }

    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var mCameraPhotoPath: String? = null

    private var url: String? = null
    private var title: String? = null

    override fun initData() {
        readIntent(intent)

        binding!!.webView.setWebViewClient(webViewClient)
        binding!!.webView.setWebChromeClient(webChromeClient)
        val settings: WebSettings = binding!!.webView.getSettings()
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.loadWithOverviewMode = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.blockNetworkImage = false
        settings.useWideViewPort = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        binding!!.webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY)
        binding!!.webView.setDownloadListener(MyDownloadListener(binding!!))
        if (!TextUtils.isEmpty(url)) {
            binding!!.webView.loadUrl(url!!)
        }
    }

    private fun readIntent(intent: Intent) {
        url = intent.getStringExtra(URL)
        formatUrl()
        title = intent.getStringExtra(TITLE)
        when(intent.getIntExtra(Constants.IS_AUDIO,0)){
            0->{ binding!!.tvTitle.text = "${getString(R.string.app_name)}-$title"}
            1->{ binding!!.tvTitle.text = title}
        }
    }

    private fun formatUrl() {
        if (TextUtils.isEmpty(url)) {
            return
        }
        if (url!!.endsWith("/")) {
            url = url!!.substring(0, url!!.length - 1)
        }
        url = url + "?appKey=" + Constants.BASE_URL
        if (!url!!.endsWith("/")) {
            url = "$url/"
        }
    }

    private val webViewClient: android.webkit.WebViewClient =
        object : android.webkit.WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }
        }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                binding!!.progress.setVisibility(View.GONE)
            } else {
                if (binding!!.progress.getVisibility() === View.GONE) {
                    binding!!.progress.setVisibility(View.VISIBLE)
                }
                binding!!.progress.setProgress(newProgress)
            }
        }

        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            if (mFilePathCallback != null) {
                mFilePathCallback!!.onReceiveValue(null)
            }
            mFilePathCallback = filePathCallback
            val intent = gotoChooseFile()
            activityResultLauncher.launch(intent)
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        }
    }


    var activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        var results: Array<Uri>? = null
        val data = result.data
        if (result.resultCode == RESULT_OK) {
            if (data == null) {
                if (mCameraPhotoPath != null) {
                    results = Utils.asList(Uri.parse(mCameraPhotoPath)).toTypedArray()
                }
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    results =
                        Utils.asList(Uri.parse(dataString)).toTypedArray()
                }
            }
        }
        if (mFilePathCallback != null) {
            mFilePathCallback!!.onReceiveValue(results) // 当获取要传图片的Uri，通过该方法回调通知
            mFilePathCallback = null
        }
    }

    private fun gotoChooseFile(): Intent {
        val saveName = (Environment.getExternalStorageDirectory().path + "/"
                + Environment.DIRECTORY_DCIM + "/Camera/")
        var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent!!.resolveActivity(packageManager) != null) {
            val photoFile = File(saveName + randomFileName() + ".jpg")
            if (!photoFile.exists()) {
                mCameraPhotoPath = "file:" + photoFile.absolutePath
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
            } else {
                takePictureIntent = null
            }
        }
        val takeoutList = Utils.asList(takePictureIntent) as ArrayList<Intent>
        val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
        contentSelectionIntent.type = "image/*"
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, takeoutList)
        return chooserIntent
    }

    private fun randomFileName(): String {
        return UUID.randomUUID().toString()
    }

    override fun onBackPressedSupport() {
        if (binding!!.webView.canGoBack()) {
            binding!!.webView.goBack()
            return
        }
        super.onBackPressedSupport()
    }
    private fun resetPb() {
        binding!!.numberProgressBar.progress = 0
        binding!!.tvPercent.text = "0%"
    }



    private class MyDownloadListener(binding: ActivityWebview2Binding) : DownloadListener {

        override fun onDownloadStart(
            url: String,
            userAgent: String,
            contentDisposition: String,
            mimetype: String,
            contentLength: Long
        ) {
            //跳转浏览器
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.addCategory(Intent.CATEGORY_BROWSABLE)
//            intent.data = Uri.parse(url)
//            App.Companion.mCurrentActivity?.get()?.startActivity(intent)

            val manager = DownloadManager.Builder(App.Companion.mCurrentActivity?.get()!!).run {
                    apkUrl(url)
                    apkName("appupdate.apk")
                    smallIcon(R.mipmap.ic_launcher)
                    onDownloadListener(listenerAdapter)
                    build()
                }
                manager!!.download()
        }


        private val listenerAdapter: OnDownloadListenerAdapter = object : OnDownloadListenerAdapter() {

            override fun downloading(max: Int, progress: Int) {
                val curr = (progress / max.toDouble() * 100.0).toInt()
                binding!!.numberProgressBar.max = 100
                binding!!.numberProgressBar.progress = curr
                binding!!.tvPercent.text = "$curr%"
            }
            override fun start() {
                binding!!.llProgress.visibility = View.VISIBLE
                binding!!.numberProgressBar.progress = 0
                binding!!.tvPercent.text = "$0%"
            }
            override fun done(apk: File) {
                binding!!.llProgress.visibility = View.GONE
            }
            override fun error(e: Throwable) {
                Log.e("error: ", e.toString())
                binding!!.llProgress.visibility = View.GONE
            }
        }
    }

    fun back(view: View?) {
        setResult(RESULT_OK)
        finish()
    }

    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }

    override fun initSubscription() {
    }

    override fun generateBinding(): ViewBinding? {
        return ActivityWebview2Binding.inflate(layoutInflater)
    }

    override fun initView() {
    }
}
