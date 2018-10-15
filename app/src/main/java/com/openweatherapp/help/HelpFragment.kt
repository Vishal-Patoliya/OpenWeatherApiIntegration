package com.openweatherapp.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openweatherapp.utils.BaseFragment
import com.openweatherapp.R
import kotlinx.android.synthetic.main.fragment_help.*
import com.gitprofile.utils.Constants

/**
 * Created by vishal on 10/14/2018.
 * Use for dislpaying Webview for OpenweatherMap App.
 */
class HelpFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_help, container, false)
    }

    override fun init(view: View?) {

    }

    override fun setListener() {

    }

    override fun process() {

        //webView.webViewClient = WebViewClientDemo()
        //webView.settings.javaScriptEnabled = true
        webView.loadUrl(Constants.URL.openWeatherWebPage)

    }

    override fun onUpdate(`object`: Any?) {

    }
}