package com.js.playground.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlin.reflect.KClass

fun <T : ViewModel> FragmentActivity?.initViewModel(clazz: KClass<out T>): T {
    return this?.run {
        ViewModelProviders.of(this)[clazz.java]
    } ?: throw Exception("Invalid Activity")
}

fun <T : ViewModel> Fragment?.initViewModel(clazz: KClass<out T>): T {
    return this?.run {
        ViewModelProviders.of(this)[clazz.java]
    } ?: throw Exception("Invalid Fragment")
}

fun FragmentActivity.initRequestManager(): RequestManager {
    return Glide.with(this)
}

fun Fragment.initRequestManager(): RequestManager {
    return Glide.with(this)
}