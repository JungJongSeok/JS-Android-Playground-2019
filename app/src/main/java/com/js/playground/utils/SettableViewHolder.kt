package com.js.playground.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SettableViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), Settable<T>