package com.js.playground

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        requestManager.load(R.mipmap.ic_launcher)
                .into(activity_main_image)

        viewModel.testResult.observe(this, Observer {
            activity_main_text.text = it?.results?.toString()
        })
        viewModel.throwable.observe(this, Observer {
            Toast.makeText(this, it?.message ?: "", Toast.LENGTH_SHORT).show()
        })

        activity_main_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchApi(s?.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        activity_main_search.requestFocus()
    }
}
