package com.js.playground.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlin.reflect.KClass

fun <T : ViewModel> FragmentActivity?.viewModel(clazz: KClass<out T>): T {
    return this?.run {
        ViewModelProviders.of(this)[clazz.java]
    } ?: throw Exception("Invalid Activity")
}

fun <T : ViewModel> Fragment?.viewModel(clazz: KClass<out T>): T {
    return this?.run {
        ViewModelProviders.of(this)[clazz.java]
    } ?: throw Exception("Invalid Fragment")
}

fun FragmentActivity.requestManager(): RequestManager {
    return Glide.with(this)
}

fun Fragment.requestManager(): RequestManager {
    return Glide.with(this)
}