package com.js.playground

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.RequestManager
import com.js.playground.extension.viewModel
import com.js.playground.service.TestService
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModel(MainViewModel::class)
        viewModel.testApi()
    }
}
