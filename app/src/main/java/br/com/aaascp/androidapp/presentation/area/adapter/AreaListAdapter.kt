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
    ) = when (viewType) {
        HEADER_TYPE -> AreaHeaderViewHolder.create(parent)
        LIST_TYPE -> AreaListViewHolder.create(parent)
        else -> throw IllegalArgumentException("unknown viewType: $viewType")
    }

    override fun onBindViewHolder(holder: ViewHolderBase<Area>, position: Int) {
        val index = if (position == 0) 0 else position - 1
        val area = getItem(index)
        area?.let { holder.bind(area) }
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> HEADER_TYPE
        else -> LIST_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    companion object {
        private const val HEADER_TYPE = R.layout.row_last_update
        private const val LIST_TYPE = R.layout.row_area_item

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Area>() {
            override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean =
                    oldItem.id == newItem.id
        }
    }
}
