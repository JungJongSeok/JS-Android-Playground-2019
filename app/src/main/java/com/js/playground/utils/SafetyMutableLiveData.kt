package com.js.playground.utils

import android.arch.lifecycle.MutableLiveData
import android.os.Looper
import io.reactivex.functions.Consumer

class SafetyMutableLiveData<T> : MutableLiveData<T>(), Consumer<T> {
    override fun accept(t: T) {
        setValueSafety(t)
    }

    fun setValueSafety(value: T) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            setValue(value)
        } else {
            postValue(value)
        }
    }
}