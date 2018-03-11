package br.com.aaascp.androidapp.presentation.area

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.aaascp.androidapp.R
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.presentation.lesson.LessonListActivity
import kotlinx.android.synthetic.main.row_area_item.view.*


class AreaListAdapter(
        private val context: Context,
        private var areas: List<Area> = listOf()
) : Adapter<AreaListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.row_area_item,
                                parent,
                                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val area = areas[position]
        val index = position + 1
        holder?.let {
            it.count.text = "$index"
            it.id.text = area.id
            it.title.text = area.title
            it.root.setOnClickListener {
                LessonListActivity.startForArea(context, area.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return areas.size
    }

    fun setAreaList(areas: List<Area>) {
        this.areas = areas
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.area_item_root
        val count: TextView = itemView.area_item_count
        val id: TextView = itemView.area_item_id
        val title: TextView = itemView.area_item_title
    }
}
