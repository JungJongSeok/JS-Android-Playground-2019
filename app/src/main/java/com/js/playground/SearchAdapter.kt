package com.js.playground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.js.playground.extension.EMPTY
import com.js.playground.service.search.SearchResult
import com.js.playground.utils.SettableViewHolder
import kotlinx.android.synthetic.main.viewholder_search.view.*

class SearchAdapter(private val requestManager: RequestManager)
    : PagedListAdapter<TypeSearchResult, SettableViewHolder<SearchResult>>(object : DiffUtil.ItemCallback<TypeSearchResult>() {
    override fun areItemsTheSame(oldItem: TypeSearchResult, newItem: TypeSearchResult): Boolean {
        return oldItem == oldItem
    }

    override fun areContentsTheSame(oldItem: TypeSearchResult, newItem: TypeSearchResult): Boolean {
        return oldItem == oldItem
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, @Type viewType: Int): SettableViewHolder<SearchResult> {
        return if (viewType == Type.SEARCH_TYPE) {
            SearchViewHolder.getInstance(parent, requestManager)
        } else {
            SearchMoreViewHolder.getInstance(parent)
        }
    }

    override fun onBindViewHolder(holder: SettableViewHolder<SearchResult>, position: Int) {
        if (getItem(position)?.type == Type.SEARCH_TYPE) {
            getItem(position)?.run {
                holder.setData(searchResult)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.type == Type.SEARCH_TYPE) {
            Type.SEARCH_TYPE
        } else {
            Type.MORE_TYPE
        }
    }
}

private class SearchViewHolder(itemView: View, private val requestManager: RequestManager) : SettableViewHolder<SearchResult>(itemView) {
    private val thumbnailView = itemView.vh_search_thumbnail
    private val titleView = itemView.vh_search_title
    private val addressView = itemView.vh_search_address

    companion object {
        fun getInstance(parent: ViewGroup, requestManager: RequestManager): SettableViewHolder<SearchResult> {
            return SearchViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.viewholder_search, parent, false), requestManager)
        }
    }

    override fun setData(t: SearchResult) {
        requestManager.load(ContextCompat.getDrawable(itemView.context, R.mipmap.ic_launcher_round))
                .into(thumbnailView)
        titleView.text = t.title
        addressView.text = t.link
    }
}

private class SearchMoreViewHolder(itemView: View) : SettableViewHolder<SearchResult>(itemView) {

    companion object {
        fun getInstance(parent: ViewGroup): SettableViewHolder<SearchResult> {
            return SearchMoreViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.viewholder_search_more, parent, false))
        }
    }

    override fun setData(t: SearchResult) {
    }
}

data class TypeSearchResult(@Type val type: Int, val searchResult: SearchResult) {
    constructor() : this(Type.MORE_TYPE, SearchResult(-1, String.EMPTY, String.EMPTY))
}

@Retention(AnnotationRetention.SOURCE)
annotation class Type {
    companion object {
        const val SEARCH_TYPE = 0
        const val MORE_TYPE = 1
    }
}