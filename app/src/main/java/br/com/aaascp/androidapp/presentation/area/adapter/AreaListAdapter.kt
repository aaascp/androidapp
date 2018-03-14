package br.com.aaascp.androidapp.presentation.area.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.presentation.ViewHolderBase


class AreaListAdapter : PagedListAdapter<Area, ViewHolderBase<Area>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolderBase<Area> = when (viewType) {
        HEADER_ITEM -> {
            AreaHeaderViewHolder.create(parent)
        }
        LIST_ITEM -> {
            AreaListViewHolder.create(parent)
        }
        else -> throw IllegalArgumentException("unknown view type $viewType")
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> {
            HEADER_ITEM
        }
        else -> {
            LIST_ITEM
        }
    }

    override fun onBindViewHolder(holder: ViewHolderBase<Area>, position: Int) {
        val area = getItem(position)
        area?.let { holder.bind(it) }
    }

    companion object {
        private const val HEADER_ITEM = R.layout.row_last_update
        private const val LIST_ITEM = R.layout.row_area_item

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Area>() {
            override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean =
                    oldItem.id == newItem.id
        }
    }
}
