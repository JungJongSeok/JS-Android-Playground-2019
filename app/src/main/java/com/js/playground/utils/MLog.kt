package com.js.playground.utils

import android.util.Log
import com.js.playground.BuildConfig

class MLog {
    companion object {
        private const val TARGET_INDEX = 5
        private val methodName: String = Thread.currentThread().stackTrace[TARGET_INDEX].methodName
        private val lineNumber: Int = Thread.currentThread().stackTrace[TARGET_INDEX].lineNumber
        private val className: String = Thread.currentThread().stackTrace[TARGET_INDEX].className
        private val tag: String = className.substring(className.lastIndexOf('.') + 1)

        fun v(vararg obj: Any) {
            if (BuildConfig.DEBUG) {
                val tag = tag
                val message = getMessage(*obj)
                Log.v(tag, message)
            }
        }

        fun w(vararg obj: Any) {
            if (BuildConfig.DEBUG) {
                val tag = tag
                val message = getMessage(*obj)
                Log.w(tag, message)
            }
        }

        fun e(vararg obj: Any) {
            if (BuildConfig.DEBUG) {
                val tag = tag
                val message = getMessage(*obj)
                Log.e(tag, message)
            }
        }

        fun i(vararg obj: Any) {
            if (BuildConfig.DEBUG) {
                val tag = tag
                val message = getMessage(*obj)
                Log.i(tag, message)
            }
        }

        fun d(vararg obj: Any) {
            if (BuildConfig.DEBUG) {
                val tag = tag
                val message = getMessage(*obj)
                Log.d(tag, message)
            }
        }

        private fun getMessage(vararg obj: Any): String {
            val messageBuilder = StringBuilder()
            messageBuilder.append("[")
            messageBuilder.append(lineNumber)
            messageBuilder.append("]")
            messageBuilder.append("[")
            messageBuilder.append(Thread.currentThread())
            messageBuilder.append("]")
            messageBuilder.append("[")
            messageBuilder.append(methodName)
            messageBuilder.append("]")

            for (i in obj.indices) {
                messageBuilder.append(obj[i])
                if (i < obj.size - 1) {
                    messageBuilder.append(",")
                    messageBuilder.append(" ")
                }
            }
            return messageBuilder.toString()
        }
    }
}
