package br.com.aaascp.androidapp.presentation.area

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.presentation.lesson.LessonListActivity
import kotlinx.android.synthetic.main.row_area_item.view.*


class AreaListAdapter(
        private val context: Context
) : PagedListAdapter<Area, AreaListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.row_area_item,
                                parent,
                                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val area = getItem(position)

        area?.let {
            val index = position + 1

            holder.count.text = "$index"
            holder.id.text = area.id
            holder.title.text = area.title
            holder.root.setOnClickListener {
                LessonListActivity.startForArea(
                        context,
                        area.id,
                        area.title)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.area_item_root
        val count: TextView = itemView.area_item_count
        val id: TextView = itemView.area_item_id
        val title: TextView = itemView.area_item_title
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
