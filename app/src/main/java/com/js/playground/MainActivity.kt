package com.js.playground

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.js.playground.extension.initRequestManager
import com.js.playground.extension.initViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var requestManager: RequestManager
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = initViewModel(MainViewModel::class)
        requestManager = initRequestManager()
        searchAdapter = SearchAdapter(requestManager)

        activity_main_search_recycler_view.layoutManager = LinearLayoutManager(this)
        activity_main_search_recycler_view.adapter = searchAdapter

        viewModel.testResult.observe(this, Observer {
        })
        viewModel.submitList.observe(this, Observer {
            searchAdapter.submitList(it)
        })
        viewModel.throwable.observe(this, Observer {
            Toast.makeText(this, it?.message ?: "", Toast.LENGTH_SHORT).show()
        })

        activity_main_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.search(s?.toString())
//                viewModel.setPagedList()
                viewModel.setPagedListAddFooter()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        activity_main_search.requestFocus()
    }
}
