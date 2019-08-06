package com.js.playground

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.js.playground.extension.initRequestManager
import com.js.playground.extension.initViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = initViewModel(MainViewModel::class)
        requestManager = initRequestManager()

        viewModel.testApi()
        requestManager.load(R.mipmap.ic_launcher)
                .into(activity_main_image)

        viewModel.testResult.observe(this, Observer {
            activity_main_text.text = it?.results?.size?.toString()
        })
        viewModel.throwable.observe(this, Observer {
            Toast.makeText(this, it?.message ?: "", Toast.LENGTH_SHORT).show()
        })
    }
}
