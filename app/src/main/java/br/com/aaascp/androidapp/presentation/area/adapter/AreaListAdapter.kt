package br.com.aaascp.androidapp.presentation.area.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.presentation.ViewHolderBase


class AreaListAdapter : PagedListAdapter<Area, ViewHolderBase<Area>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolderBase<Area> {
        return AreaListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolderBase<Area>, position: Int) {
        val area = getItem(position)
        area?.let { holder.bind(area) }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Area>() {
            override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean =
                    oldItem.id == newItem.id
        }
    }
}
