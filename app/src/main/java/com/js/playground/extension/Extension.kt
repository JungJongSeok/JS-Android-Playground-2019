package com.js.playground.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

fun FragmentActivity?.viewModel(clazz: Class<out ViewModel>) : ViewModel {
    return this?.run {
        ViewModelProviders.of(this)[clazz]
    } ?: throw Exception("Invalid Activity")
}

fun Fragment?.viewModel(clazz: Class<out ViewModel>) : ViewModel {
    return this?.run {
        ViewModelProviders.of(this)[clazz]
    } ?: throw Exception("Invalid Fragment")
}