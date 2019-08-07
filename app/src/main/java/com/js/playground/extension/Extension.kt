package com.js.playground.extension

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import java.util.concurrent.Executor
import java.util.concurrent.Executors
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

object UiThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        handler.post(command)
    }
}

object BackgroundThreadExecutor : Executor {
    private val executorService = Executors.newCachedThreadPool()

    override fun execute(command: Runnable) {
        executorService.execute(command)
    }
}